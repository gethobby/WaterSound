package model;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


import developConfig.Gobal;



public class mySQLConnector {
		
		/*以下是定义一些字段*/
		//private String url = "jdbc:mysql://localhost:3306/model3d?user=root&password=blue";	//数据库URL
		private static String className = "com.mysql.jdbc.Driver";	//数据库驱动类路径
		private Connection conn = null;	//声明一个Connection对象
		private Statement stmt = null;	//声明一个Statement对象
		private PreparedStatement pstmt = null;	//声明一个PreparedStatement对象
		
		/*初始化一些东西：加载数据库驱动，并建立连接*/
		public void init(){
			try {
				//加载数据库驱动
				Class.forName("com.mysql.jdbc.Driver");
				//System.out.println("driver load success");
				//建立连接，连接到由属性url指定的数据库URL，并指定登录数据库的用户名和密码
				conn = DriverManager.getConnection(Gobal.QUERY_DB_URL,Gobal.QUERY_DB_USER,Gobal.QUERY_DB_PASSWORD);
				//System.out.println("connect success");
				//在初始化方法里面首先获取Statement对象
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("初始化方法init()出现错误，加载数据库驱动失败 或者 建立连接失败！！");
			}
		}
		
		/*构造方法*/
		public mySQLConnector(){
			init();
		}

		/*********************************以下方法是针对Statement的****************************************/
		
		/*查询数据库，返回结果集ResultSet*/
		public ResultSet executeQuery(String sql){
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("executeQuery(String sql)方法出错！！");
			}
			return rs;
		}
		
		/*对数据库中的数据进行增加、修改或删除，返回受影响的行数*/
		public int executeUpdate(String sql){
			int iCount = 0;
			try {
				iCount = stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("executeUpdate(String sql)方法出错！！");
			}
			return iCount;
		}
		
		/*********************************以下方法是针对PreparedStatement的****************************/
		
		/*依据SQL语句给pstmt赋上值*/
		public void readyPreparedStatement(String sql){
			try {
				pstmt =  conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("readyPreparedStatement()方法出错！！");
			}
		}
		
		/*以下是一些 PreparedStatement 设置的方法*/
		public void setString(int index, String value){
			try {
				if(value.trim().equals("")) pstmt.setNull(index, index);
				else pstmt.setString(index, value);
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("PreparedStatement的设置方法setString()出错！！");
			}
		}
		public void setInt(int index, int value){
			try {
				pstmt.setInt(index, value);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("PreparedStatement的设置方法setInt()出错！！");
			}
		}
		public void setBoolean(int index, boolean value){
			try {
				pstmt.setBoolean(index, value);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("PreparedStatement的设置方法setBoolean()出错！！");
			}
		}
		public void setDate(int index, Date value){
			try {
				pstmt.setDate(index, value);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("PreparedStatement的设置方法setDate()出错！！");
			}
		}
		public void setLong(int index, long value){
			try {
				pstmt.setLong(index, value);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("PreparedStatement的设置方法setLong()出错！！");
			}
		}
		public void setFloat(int index, float value){
			try {
				pstmt.setFloat(index, value);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("PreparedStatement的设置方法setFloat()出错！！");
			}
		}
		public void setBytes(int index, byte[] value){
			try {
				pstmt.setBytes(index, value);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("PreparedStatement的设置方法setBytes()出错！！");
			}
		}
		public void setTimeStamp(int index,Timestamp value){
			try {
				pstmt.setTimestamp(index, value);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("PreparedStatement的设置方法setTimeStamp()出错！！");
			}
		}
		
		/*查询数据库，返回结果集ResultSet*/
		public ResultSet executeQuery(){
			if (pstmt != null) {
				try {
					return pstmt.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("executeQuery()方法出错！！");
					return null;
				}
			} else{
				System.out.println("PreparedStatement 未给定值！！");
				return null;
			}
		}
		
		/*对数据库中的数据进行增加、修改或删除，返回受影响的行数*/
		public int executeUpdate(){
			int iCount = 0;
			if (pstmt != null){
				try {
					iCount = pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("executeUpdate()方法出错！！");
				}
			} else{
				System.out.println("PreparedStatement 未给定值！！");
			}
			return iCount;
		}
		
		/*********************************************************************************************************/
		
		/*关闭所有连接*/
		public void close(){
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("关闭所有连接的方法close()出错了！！");
			}
		}
		
		protected void finalize()
		{
			this.close();
		}
		
		
		/////////////////////////////
		/**
		 * 
		 * @param cmd 服务器发出的命令：0表示上传，表示下载
		 * @param IP  节点机的IP(因为节点机socket是通信的S端)
		 * @param port 节点机socket的IP(config.txt确定)
		 * @param filepath 要上传的文件完整路径
		 * @param user  当前服务器上登录的用户
		 * @return
		 */
		 public  boolean SendModelFile(String cmd,String IP,int port,String filepath,String user) {
			    Runtime rn = Runtime.getRuntime();
				Process p = null;
				String[] commons = new String[6];
				commons[0]=Gobal.NODE_COMMUNICATE_APP;
				commons[1]=cmd;//"0:上传";
				commons[2]=IP;//"127.0.0.1";
				commons[3]=Integer.toString(port);//"6666";
				
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
		  /**
			 * 
			 * @param cmd 服务器发出的命令：0表示上传，表示下载
			 * @param IP  节点机的IP(因为节点机socket是通信的S端)
			 * @param port 节点机socket的IP(config.txt确定)
			 * @param filepath 要从节点机中下载下来的文件(包含后缀)
			 * @param user  当前服务器上登录的用户
			 * @param callbackpath 下载的文件保存到服务器上的存储路径
			 * @param callbackrecord  下载过程的记录文件(与callbackpath参数同一路经)
			 * @return 返回下载是否成功
			 */
		   public  boolean ReceiveModelFile(String cmd,String IP,int port,String filepath,String user, String callbackpath, String callbackrecord) {
			    Runtime rn = Runtime.getRuntime();
				Process p = null;
				String[] commons = new String[8];
				commons[0]=Gobal.NODE_COMMUNICATE_APP;
				commons[1]=cmd;//"1:下载";
				commons[2]=IP;//"127.0.0.1";
				commons[3]=Integer.toString(port);//"6666";
				
				if(filepath==null) commons[4]="NULL";
				else commons[4]=filepath;//"D:\\file\\testSN.txt";
				commons[5]=user;
				commons[6]=callbackpath;
				commons[7]=callbackrecord;
				
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
		   
		  		   
			public static void main(String args[])
			{
		/*		mySQLConnector con=new mySQLConnector();
				String checkedFileSql="select fileID,模型文件,storepath,适用软件 "
			    + "from geomodel.fileinfo ";
				ResultSet rs=con.executeQuery(checkedFileSql);
				ResultSetMetaData rsmd;
				try {
					rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();   
					// 输出列名   
					for (int i=1; i<=columnCount; i++){   
						System.out.print(rsmd.getColumnName(i));   
						System.out.print("(" + rsmd.getColumnTypeName(i) + ")");   
						System.out.print(" | ");   
					}   
					System.out.println();   
					// 输出数据   
					while (rs.next()){   
						for (int i=1; i<=columnCount; i++){   
							System.out.print(rs.getString(i) + " | ");   
						}   
						System.out.println();   
					}   
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					con.close();
				} */ 
				mySQLConnector mysql = new mySQLConnector();
				System.out.println("上传文件");
				mysql.SendModelFile("0","223.3.79.4", 6000,  Gobal.OBJECT_ROOT_DIR, "zhang");
				System.out.println("上传成功");
			    /*System.out.println("下载文件");
			    // 端口号后面的模型文件，只需要文件名即可，因为节点机上的工作空间统一了
				mysql.ReceiveModelFile("1","223.3.79.4", 6000,  "watersound22.stl", "shanshan3344555","D:\\WaterSound\\ModelCallBack\\","Result.xml");
				System.out.println("下载成功");*/
			}


}
