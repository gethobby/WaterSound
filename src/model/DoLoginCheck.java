package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;



public class DoLoginCheck {
	String username;
	String password;
	String role;
	String sql;
	String accessLog="insert into platformuser.userlog( username,logintime,logouttime ) values(?,?,?)";
	public boolean isSuccess()
	{
		boolean flag=false;

		mySQLConnector con=new mySQLConnector();
		con.readyPreparedStatement(sql);
		con.setString(1, this.username);
		con.setString(2, this.password);
		if(this.role!=null&&!role.equals(""))
		{
			con.setString(3, this.role);
		}
		ResultSet rs= con.executeQuery();
		try
		{		
			if(rs.next())
				flag= true;
		}catch(Exception e)
		{ 
			e.printStackTrace();
			}
		con.close();
		return flag;
	}
	public DoLoginCheck(String u,String p)
	{
		username=u;
		password=p;
		sql="select * from platformuser.account where username=? and password=?;";
	}
	public DoLoginCheck(String u,String p,String r)
	{
		username=u;
		password=p;
		role=r;
		sql="select * from platformuser.account where username=? and password=? and role=?;";
	}
	public DoLoginCheck(){}
	/**
	 * @param username 登录用户名
	 * @param tstart   登录时间
	 * @param tend     登出时间
	 * @return         用户访问记录是否插入成功
	 */
	public boolean InsertAccessLog(String username,Date tstart,Date tend)
	{
		boolean flag=false;
		
		mySQLConnector con=new mySQLConnector();
		con.readyPreparedStatement(accessLog);
		con.setString(1, username);
		String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tstart);//将时间格式转换成符合Timestamp要求的格式.
        Timestamp s = Timestamp.valueOf(startTime);//把时间转换
		con.setTimeStamp(2, s);
		String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tend);//将时间格式转换成符合Timestamp要求的格式.
        Timestamp e = Timestamp.valueOf(endTime);//把时间转换
		con.setTimeStamp(3, e);
		if(con.executeUpdate()>0)
			flag=true;
		con.close();
		return flag;
	}
	public boolean InsertNewUser()
	{
		boolean flag=false;
		
		mySQLConnector con=new mySQLConnector();
		String insertnewuserSql="insert into platformuser.account(username,password,role,status) values(?,?,?,?);";
		con.readyPreparedStatement(insertnewuserSql);
		con.setString(1, this.username);
		con.setString(2, this.password);
		if(this.role!=null&&!this.role.equals(""))
		{
			con.setString(3, this.role);
		}
		else{
			con.setString(3, "user");
		}
		con.setString(4, "offline");
		if(con.executeUpdate()>0)
			flag=true;
		con.close();
		
		return flag;
		
	}
//	public static void main(String[] args)
//	{
//		DoLoginCheck temp=new DoLoginCheck();
//		temp.InsertAccessLog("username", new Date(), new Date());
//		
//	}
}
