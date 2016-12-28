package model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;



public class GirdfileList {

	String checkedFileSql="select fileID,模型文件,storepath,适用软件  "
			+ "from modelinfo.fileinfo ";
	String SavedNodeDBSql="select nodeID,IPAddress,class  "
			+ "from savednode.nodeinfo ";
//	String unCheckedFileSql="select ID,filename from gridfile where verified=0;";
//	String searchFileSql="select ID,filename from gridfile where filename LIKE ?;";
//	String RecentModelFileSql="SELECT ID,filename FROM gridfile limit 0,3; ";
//	String ProjectSql="SELECT computetaskId,taskname,time,status,solver FROM computetask; ";
//	String NodemachineSql="SELECT * from nodeMachine";
	String UserlistSql="select userID,username,role,status from usermanager.account;";
	String SoftlistSql="select * from softnode.softinfo";
	ArrayList<String> arrayList1;
	ArrayList<String[]> arrayList2;
	
	
	/*
	 * getSavedNodeDBList
	 * @return    返回数据库中现存的存储节点机信息记录
	 */
	public ArrayList<String[]> getSavedNodeDBList()
	{
		arrayList1 = new ArrayList<String>();
		arrayList2 = new ArrayList<String[]>();
		ArrayList<String[]> list = null;
		mySQLConnector con=new mySQLConnector();
		ResultSet rs=con.executeQuery(SavedNodeDBSql);
		try
		{		
			convertList(rs);
			list=arrayList2;
		}catch(Exception e)
		{ 
			e.printStackTrace();
			}
		con.close();
		return list;
	}
	
	
	/*
	 * getSoftList
	 * @return    返回数据库中现存的所有模型信息记录
	 */
	public ArrayList<String[]> getObjectModelList(String nodeIP)
	{
		arrayList1 = new ArrayList<String>();
		arrayList2 = new ArrayList<String[]>();
		ArrayList<String[]> list = null;
		mySQLConnector con=new mySQLConnector(nodeIP);
		ResultSet rs=con.executeQuery(checkedFileSql);
		try
		{		
			convertList(rs);
			list=arrayList2;
		}catch(Exception e)
		{ 
			e.printStackTrace();
			}
		con.close();
		return list;
	}
	/*
	 * getUserList
	 * @return    返回数据库中现存的所有用户信息记录
	 */
	public ArrayList<String[]> getUserList()
	{
		arrayList1 = new ArrayList<String>();
		arrayList2 = new ArrayList<String[]>();
		ArrayList<String[]> list = null;
		mySQLConnector con=new mySQLConnector();		
		ResultSet rs=con.executeQuery(UserlistSql);
		try
		{		
			convertList(rs);
			list=arrayList2;
		}catch(Exception e)
		{ 
			e.printStackTrace();
			}
		con.close();
		return list;
	}
	/*
	 * getNodeMachineList
	 * @return    返回数据库中现存的所有节点机信息记录
	 */
	public ArrayList<String[]> getNodeMachineList()
	{
		arrayList1 = new ArrayList<String>();
		arrayList2 = new ArrayList<String[]>();
		ArrayList<String[]> list = null;
//		mySQLConnector con=new mySQLConnector();
//		ResultSet rs=con.executeQuery(NodemachineSql);
//		try
//		{		
//			convertList(rs);
//			list=arrayList2;
//		}catch(Exception e)
//		{ 
//			e.printStackTrace();
//			}
//		con.close();
		return list;
	}
	/*
	 * convertList
	 * 提取ResultSet数据并将数据转化成ArrayList类型
	 * @param    ResulrSet类型数据，待转化的数据库查询结果
	 * @exception  可能抛出数据库访问异常SQLException
	 */
	public  void convertList(ResultSet rs) throws SQLException {

		//List list = new ArrayList();

		ResultSetMetaData md = rs.getMetaData();

		int columnCount = md.getColumnCount(); // Map rowData;

		while (rs.next()){ // rowData = new HashMap(columnCount);
			//Map rowData = new HashMap();
			String[] temp=new String[columnCount];
			for (int i = 1; i <= columnCount; i++) {
				arrayList1.add(md.getColumnName(i));
				temp[i-1]=rs.getString(i);
				//rowData.put(md.getColumnName(i), rs.getObject(i));
			}

			arrayList2.add(temp);

		} 
		//return list;

	}
	
	/*
	 * getSoftList
	 * @return    返回数据库中现存的所有软件记录
	 */
	public ArrayList<String[]> getSoftList(String searchParams)
	{
		arrayList1 = new ArrayList<String>();
		arrayList2 = new ArrayList<String[]>();
		ArrayList<String[]> list = null;
		mySQLConnector con=new mySQLConnector();
		
		String querysql = SoftlistSql;
		if (searchParams!=""&&searchParams!=null) {
			//System.out.println("查询参数==="+searchParams);
			querysql = querysql+" where softname like '%"+searchParams+"%'";
		}
		ResultSet rs=con.executeQuery(querysql);
		try
		{		
			convertList(rs);
			list=arrayList2;
		}catch(Exception e)
		{ 
			e.printStackTrace();
			}
		con.close();
		return list;
	}

	//下面为测试代码
//	public static void main(String[] args)
//	{
//		GirdfileList gf=new GirdfileList();
//		ArrayList<String[]> list=gf.getFileList();
//		if(list!=null){
//			System.out.println(list.get(1));
//		}
//		
//	}
}
