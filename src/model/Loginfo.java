package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Loginfo {
	String useractivesql="SELECT count(distinct(username) ) as counts,avg(TIME_TO_SEC(TIMEDIFF(logouttime,logintime))) as avgs,DATE_FORMAT(logintime,'%Y-%m-%d') days FROM usermanager.userlog  group by days";
	String userstaysql="SELECT sum(TIME_TO_SEC(TIMEDIFF(logouttime,logintime))) as staytime,DATE_FORMAT(logintime,'%Y-%m-%d') days FROM usermanager.userlog where username=? group by days";
	String usermdssql="SELECT sum(modelsize) as totalsize FROM usermanager.modeluse where username=?";
	String userrdssql="SELECT sum(size) as totalsize FROM usermanager.resultcreate,softnode.computeresult where username=? and FK_result=taskname";
	String totalmdssql="SELECT sum(modelsize) as totalsize FROM usermanager.modeluse";
	String totalrdssql="SELECT sum(size) as totalsize FROM usermanager.resultcreate,softnode.computeresult where FK_result=taskname";
	String avgnodeusesql="SELECT avg(TIME_TO_SEC(TIMEDIFF(endtime,starttime))) as avgusetime,DATE_FORMAT(starttime,'%Y-%m-%d') days FROM usermanager.softuse group by days";
	
	
	public ResultSet getUserActivelog()
	{
		mySQLConnector con=new mySQLConnector();
		ResultSet rs=con.executeQuery(useractivesql);
		return rs;
	}
	 public ResultSet getUserstayinterval(String username)
	 {
		 mySQLConnector con=new mySQLConnector();
		 con.readyPreparedStatement(userstaysql);
		 con.setString(1, username);
		 return con.executeQuery();
	 }
	 public int getUserModelDS(String username)
	 {
		 mySQLConnector con=new mySQLConnector();
		 con.readyPreparedStatement(usermdssql);
		 con.setString(1, username);
		 ResultSet rs=con.executeQuery();
		 try {
			 if(rs.next()){
				return rs.getInt(1);
			}
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return 0;
	 }
	 public int getUserResultDS(String username)
	 {
		 mySQLConnector con=new mySQLConnector();
		 con.readyPreparedStatement(userrdssql);
		 con.setString(1, username);
		 ResultSet rs=con.executeQuery();
		 try {
			 if(rs.next()){
				return rs.getInt(1);
			}
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return 0;
	 }
	 public int getPlatformModelDS()
	 {
		 mySQLConnector con=new mySQLConnector();
		 ResultSet rs=con.executeQuery(totalmdssql);
		 try {
			 if(rs.next()){
				return rs.getInt(1);
			}
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return 0;
	 }
	 public int getPlatformResultDS()
	 {
		 mySQLConnector con=new mySQLConnector();
		 ResultSet rs=con.executeQuery(totalrdssql);
		 try {
			 if(rs.next()){
				return rs.getInt(1);
			}
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return 0;
	 }
	public ResultSet getAvgNodeusetime()
	{
		mySQLConnector con=new mySQLConnector();
		ResultSet rs=con.executeQuery(avgnodeusesql);
		return rs;
	}
}



