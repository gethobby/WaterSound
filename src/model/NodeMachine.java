package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import developConfig.*;

public class NodeMachine {
	int NodeID=0;
	String IPAddress;
	int NodeStatus;
	int NodeMaxuseCount;
	String[] Accounts;
	String[] PSWs;
	int[] ListenPorts;
	String AvailableSofts;

	String Username;
	String Password;
	int port=0;
	
	

	public String getAvailableSofts() {
		return AvailableSofts;
	}
	public void setAvailableSofts(String availableSofts) {
		AvailableSofts = availableSofts;
	}
	public String[] getAccounts() {
		return Accounts;
	}
	public void setAccounts(String[] accounts) {
		Accounts = accounts;
	}
	public String[] getPSWs() {
		return PSWs;
	}
	public void setPSWs(String[] pSWs) {
		PSWs = pSWs;
	}
	public int[] getListenPorts() {
		return ListenPorts;
	}
	public void setListenPorts(int[] listenPorts) {
		ListenPorts = listenPorts;
	}
	public String getIPAddress(){return IPAddress;}
	public String getUserName(){return Username;}
	public String getPassWord(){return Password;}
	public int getPort(){return port;}
	public int getNodeStatus() {
		return NodeStatus;
	}
	public void setNodeStatus(int nodeStatus) {
		NodeStatus = nodeStatus;
	}
	public int getNodeMaxuseCount() {
		return NodeMaxuseCount;
	}
	public void setNodeMaxuseCount(int nodeMaxuseCount) {
		NodeMaxuseCount = nodeMaxuseCount;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public int getNodeID() {
		return NodeID;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	
	/**
	 * @param 节点上可用的软件
	 * @return 如果新软件记录添加成功就返回true，否则返回false
	 */
	public boolean insertNewNode(String softlist)
	{
		boolean flag=true;
		
		mySQLConnector con=new mySQLConnector();
		String insertNewnodeSql="insert into objectmodelingsoft.nodeinfo "
				+ "(IPAddress,status,maxcount) values(?,0,?)";
		con.readyPreparedStatement(insertNewnodeSql);
		con.setString(1, this.IPAddress);
		con.setInt(2, this.NodeMaxuseCount);
		if(con.executeUpdate()<=0) return false; 
		
		String getNewnodeID="select nodeID from objectmodelingsoft.nodeinfo where IPAddress=?;";
		con.readyPreparedStatement(getNewnodeID);
		con.setString(1, this.IPAddress);
		try {
			ResultSet rs=con.executeQuery();
			if(rs.next()){this.NodeID=rs.getInt("nodeID");}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this.NodeID==0) return false;
		else{
			
			String insertNewnoderesourceSql="insert into objectmodelingsoft.availableresource (FK_node,account,password,port,softname) values(?,?,?,?,?)";
			con.readyPreparedStatement(insertNewnoderesourceSql);
			con.setInt(1, this.NodeID);
			for(int i=0;i<this.Accounts.length;i++)
			{
				con.setString(2, this.Accounts[i]);
				con.setString(3, this.PSWs[i]);
				con.setInt(4, this.ListenPorts[i]);//端口号在创建账号的时候也需要给出
				con.setString(5, softlist);
				if(con.executeUpdate()<=0) flag=false;
			}
		}
		con.close();
		
		return flag;
	}
	
	 /**
	 * @param toolname
	 * @return
	 */
	public boolean checkIdleNode(String toolname){
		boolean flag=true;
		/*
		 * 空闲节点的选择标准
		 * 1.空闲节点存在指定的目标特性软件
		 * 2.空闲节点当前的RAM使用率
		 * 3.空闲节点当前可使用的资源数
		 * 4.节点当前活跃用户与总共支持并发用户数的比例
		 * */
		
		//获取对应目标特性软件有空闲资源的节点使用信息
		mySQLConnector con=new mySQLConnector();
		String idleNodesqlpre="SELECT nodeID,IPAddress,count(ID) as targetsoftcount, availablecount,maxcount "
				+ "FROM objectmodelingsoft.availableresource as a,objectmodelingsoft.availablenode_view as n "
				+ "where n.nodeID=a.FK_node and softname Like '%";
		String idleNodesqlpos="%' group by nodeID;";
		System.out.println(idleNodesqlpre+toolname+idleNodesqlpos);
		con.readyPreparedStatement(idleNodesqlpre+toolname+idleNodesqlpos);
		ResultSet rs= con.executeQuery();
		
		//记录下节点的使用情况
		ArrayList<Integer> nodeID=new ArrayList<>();//暂存节点ID
		ArrayList<String> ipAddress=new ArrayList<>();//暂存节点IP
		ArrayList<Integer> avaiResource=new ArrayList<>();//暂存单个节点上对应目标特性软件的资源数
		ArrayList<Float> usageRate=new ArrayList<>();//各节点空闲资源与允许并行资源数的比值
		try
		{		
			while(rs.next()){
				nodeID.add(rs.getInt("nodeID"));
				ipAddress.add(rs.getString("IPAddress"));
				avaiResource.add(rs.getInt("targetsoftcount"));
				usageRate.add((float)rs.getInt("availablecount")/rs.getInt("maxcount"));
			}
		}catch(Exception e)
		{ 
			flag=false;
			e.printStackTrace();
		}
		
		//获取保存各节点机器的RAM占用率
		ArrayList<Double> ramUsagerate=new ArrayList<>();
		for(int i=0;i<ipAddress.size();i++)
		{
			System.out.println(ipAddress.get(i));
			NodeSnmp node=new NodeSnmp(ipAddress.get(i));
			double temp=1.0;
			try {
				node.initCommforNode();
				temp=node.CpuUsageforNode();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(temp);
			ramUsagerate.add(temp);
		}
		
		int index=getBestNodeindex(usageRate,avaiResource,ramUsagerate);
		if(index<0){flag=false;}
		else{
			this.IPAddress=ipAddress.get(index);
			String getresourceinfoSql="select account,password,port from objectmodelingsoft.availableresource "
					+ "where FK_node=? and occupied=0 and softname like ?;";
			con.readyPreparedStatement(getresourceinfoSql);
			con.setInt(1, nodeID.get(index));
			con.setString(2, "%"+toolname+"%");
			rs=con.executeQuery();
			try {
				if(rs.next()){
					this.Username=rs.getString("account");
					this.Password=rs.getString("password");
					this.port=rs.getInt("port");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			if(this.Username!=null){
				String updateaccountstatusSql="update objectmodelingsoft.availableresource "
						+ "set occupied=1 where FK_node=? and account=?";
				con.readyPreparedStatement(updateaccountstatusSql);
				con.setInt(1, nodeID.get(index));
				con.setString(2, this.Username);
				if(con.executeUpdate()<=0) flag=false;
			}
		}
		
		con.close();
		
		return flag;
	}
	
	private int getBestNodeindex(ArrayList<Float> usageRate,ArrayList<Integer> avaiResource,ArrayList<Double> ramUsagerate)
	{
		ArrayList<Float> weight=new ArrayList<>();
		//ramUsagerate是RAM占用率,值范围为[0,1],比重为5，因为节点机的RAM剩余可用空间直接影响到用户操作的成败
		//avaiResource是节点可以分配的资源数量，比重为3，因为节点当前可用资源数影响到系统对用户节点使用请求的并行响应
		//usageRate是节点现在使用中的用户与总允许同时使用用户数的比值,值范围为[0,1],比重为2,更进一步均衡各节点的负载
		//RAM占用率权重算法，0~30%算占用率低权重0.5，30~60%算占用率中等权重0.3，60%~80%算较高占用率权重0.2，80%以上算高占用权重0.1
		//节点可以分配的资源数量权重算法，5个以上权重0.3，3~5个权重0.2，1~2个权重0.1
		//节点现在使用中的用户与总允许同时使用用户数的比值权重算法，如果比值超过0.7权重为0，比值0.7~0.4之间权重为0.1，少于0.4权重为0.2
				
		if(usageRate.size()==0||avaiResource.size()==0||ramUsagerate.size()==0) return -1;
		if(usageRate.size()==avaiResource.size()&&avaiResource.size()==ramUsagerate.size()){
			for(int i=0;i<usageRate.size();i++)
			{
				float ramweight=0;
				if(ramUsagerate.get(i)>=0&&ramUsagerate.get(i)<0.3) ramweight=0.5f;
				if(ramUsagerate.get(i)>=0.3&&ramUsagerate.get(i)<0.6) ramweight=0.3f;
				if(ramUsagerate.get(i)>=0.6&&ramUsagerate.get(i)<0.8) ramweight=0.2f;
				if(ramUsagerate.get(i)>=0.8) ramweight=0.1f;
				
				float avaiweight=0;
				if(avaiResource.get(i)>=1&&avaiResource.get(i)<=2) avaiweight=0.1f;
				if(avaiResource.get(i)>=3&&avaiResource.get(i)<=5) avaiweight=0.2f;
				if(avaiResource.get(i)>5) avaiweight=0.3f;
				
				float usageweight=0;
				if(usageRate.get(i)>=0&&usageRate.get(i)<0.4) usageweight=0.2f;
				if(usageRate.get(i)>=0.4&&usageRate.get(i)<0.7) usageweight=0.1f;
				if(usageRate.get(i)>=0.7) usageweight=0;
				
				weight.add(ramweight+avaiweight+usageweight);	
			}
		}
		else return -1;

		ArrayList<Integer> sortedweightindex=nodepriority(weight);
		
		return sortedweightindex.get(0);
	}
	
	private <T extends Comparable<? super T>> ArrayList<Integer> nodepriority(ArrayList<T> weight)
	{
		ArrayList<Integer> sortedindex=new ArrayList<>();
		for(int i=0;i<weight.size();i++)
		{
			sortedindex.add(i);
		}
		ArrayList<T> w=new ArrayList<>(weight);
		for(int i=0;i<w.size();i++)
		{
			for(int j=w.size()-1;j>i;j--)
			{
				if(w.get(j-1).compareTo(w.get(j))<0)
				{
					int stemp=sortedindex.get(j-1);
					sortedindex.set(j-1,sortedindex.get(j));
					sortedindex.set(j, stemp);
					T wtemp=w.get(j-1);
					w.set(j-1,w.get(j));
					w.set(j,wtemp);
				}
			}
		}
		return sortedindex;
	}
	
	/*
	 * 打开传输和监听节点机的程序，如果有模型文件就传输模型文件
	 * 完成后给出是否成功的信号
	 * */
	public boolean startPreparing(String IP,int port,String toolname,String filepath,String user) {
		Runtime rn = Runtime.getRuntime();
		Process p = null;
		String[] commons = new String[6];
		commons[0]=Gobal.NODE_COMMUNICATE_APP;
		commons[1]=IP;//"127.0.0.1";
		commons[2]=Integer.toString(port);//"6666";
		commons[3]=toolname;//"feko";
		if(filepath==null) commons[4]="NULL";
		else commons[4]=filepath;//"D:\\file\\testSN.txt";
		commons[5]=user;
		try {
			p = rn.exec(commons);
			System.out.println("connect node exec!");
			int exitVal = p.waitFor();
			//System.out.println("Process exitValue: " + exitVal);
			if(exitVal==0) return true;
		} catch (Exception e) {
			System.out.println("Error exec when connecting node!");
			return false;
		}

		return true;
	}
//	public boolean resultReceive(int port,String dir,String xmlfile) 
//	{
//		boolean flag=false;
//		try {
//	        ServerSocket server = new ServerSocket(port);
//	        System.out.println("The port is available.");
//	        flag=true;     
//	    } catch (IOException e) {
//	    	flag=false;
//	    	System.out.println("The port is occupied.");
//	    }
//		finally
//		{
//			if(flag){
//				Runtime rn = Runtime.getRuntime();
//				Process p = null;
//				String[] commons = new String[4];
//				commons[0]=Gobal.RESULT_RECEIVE_APP;
//				commons[1]=Integer.toString(port);//"126";
//				commons[2]=dir;//"D:\\file\\";
//				commons[3]=xmlfile;//"computeresults.xml";
//
//				try {
//						p = rn.exec(commons);
//						System.out.println("exec!");
//						//int exitVal = p.waitFor();
//						//System.out.println("Process exitValue: " + exitVal);
//					}
//				catch(Exception e)
//				{
//					System.out.println("Error exec!");
//					return false;
//				}
//			}
//		}
//		System.out.println("success!");
//	    return true;
//	}
	public boolean releaseNode(String ip,String account)
	{
		boolean flag=true;
		mySQLConnector con=new mySQLConnector();
		con.readyPreparedStatement("select nodeID from objectmodelingsoft.nodeinfo where IPAddress=?;");
		con.setString(1,ip);
		ResultSet rs= con.executeQuery();
		try
		{		
			if(rs.next()){
				con.readyPreparedStatement("update objectmodelingsoft.availableresource set occupied=0 where FK_node=? and account=?;");
				con.setInt(1,rs.getInt("nodeID"));
				con.setString(2,account);
				if(con.executeUpdate()<=0){
					System.out.println("release node account error!");
					flag=false;
				}
			}
		}catch(Exception e)
		{ 
			e.printStackTrace();
			flag=false;
		}
		
		con.close();

		return flag;
		
	}
	public boolean releaseNode(int resourceId)
	{
		boolean flag=true;
		mySQLConnector con=new mySQLConnector();
		con.readyPreparedStatement("update objectmodelingsoft.availableresource set occupied=0 where ID=?;");
		con.setInt(1,resourceId);
		if(con.executeUpdate()<=0){
			System.out.println("release node account resource error!");
			flag=false;
		}

		con.close();
		return flag;
		
	}
	public boolean lockNodeResource(int resourceId)
	{
		boolean flag=true;
		mySQLConnector con=new mySQLConnector();
		con.readyPreparedStatement("update objectmodelingsoft.availableresource set occupied=1 where ID=?;");
		con.setInt(1,resourceId);
		if(con.executeUpdate()<=0){
			System.out.println("lock node account resource error!");
			flag=false;
		}

		con.close();
		return flag;
	}
	public boolean UpdateNodeinfo(int nodeID)
	{
		if(nodeID<=0) return false;
		boolean flag=true;
		mySQLConnector con=new mySQLConnector();
		String updateNodeinfoSql="update objectmodelingsoft.nodeinfo set IPAddress=?,status=?,maxcount=? where nodeID=?";
		con.readyPreparedStatement(updateNodeinfoSql);
		con.setString(1, this.IPAddress);
		con.setInt(2, this.NodeStatus);
		con.setInt(3, this.NodeMaxuseCount);
		con.setInt(4, nodeID);
		if(con.executeUpdate()<=0) flag=false;
		con.close();
		return flag;
	}
	public boolean UpdateResourceinfo(int nodeID)
	{
		if(nodeID<=0) return false;
		boolean flag=true;
		mySQLConnector con=new mySQLConnector();
		String updateResourceinfoSql="update objectmodelingsoft.availableresource set softname=? where FK_node=?";
		con.readyPreparedStatement(updateResourceinfoSql);
		con.setString(1, this.AvailableSofts);
		con.setInt(2, nodeID);
		if(con.executeUpdate()<=0) flag=false;
		con.close();
		return flag;
	}
//	public boolean getNodeSignal()
//	{
//		mySQLConnector con=new mySQLConnector();
//		
//		
//		return false;
//	}
//	public void testruntimeexec()
//	{
//		try {//测试打开软件，以及带参数打开软件
//			Runtime rt = Runtime.getRuntime();
//			Process proc = rt.exec("cmd /k C:/Windows/notepad.exe D:/floder/testSN.txt");
//
//		} catch (Throwable t) {
//			t.printStackTrace();
//		}
//	}


//	public static void main(String[] args)
//	{
//		NodeMachine os=new NodeMachine();
//		String[] softs={"hfss","feko","comsol"};
//		int[] weight={0,2,1};
//		os.sortSoftwithWeight(softs,weight);
//		
//	}
}
