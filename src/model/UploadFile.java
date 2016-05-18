package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UploadFile {//模型文件的实体类

	int ModelID; // 模型
	String FileName;//模型文件
	String StorePath;//storepath
	String ObjectiveSoft;//适用软件，暂存成字符串形式
	String FileDescription;//简介

	String type;//临时的字段，后续可能改动删除
	
	
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getStorePath() {
		return StorePath;
	}
	public void setStorePath(String storePath) {
		StorePath = storePath;
	}
	public String getFileDescription() {
		return FileDescription;
	}
	public void setFileDescription(String fileDescription) {
		FileDescription = fileDescription;
	}

	public String getObjectiveSoft() {
		return ObjectiveSoft;
	}
	public void setObjectiveSoft(String objectiveSoft) {
		ObjectiveSoft = objectiveSoft;
	}
	
	

	public static void InsertModelFileuselog(String user,int fileID,int filesize)
	{
		//判断参数是否合法，要记录某个模型的使用必须
		if(fileID<=0||filesize<=0) return;

		mySQLConnector con=new mySQLConnector();
		String modeluseinsert="insert into platformuser.modeluse( username,FK_model,usetime,modelsize) values(?,?,?,?);";
		con.readyPreparedStatement(modeluseinsert);
		con.setString(1, user);
		con.setInt(2,fileID);
		String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//将时间格式转换成符合Timestamp要求的格式.
        Timestamp s = Timestamp.valueOf(startTime);//把时间转换
		con.setTimeStamp(3, s);
		con.setLong(4, filesize);
		if(con.executeUpdate()>0) System.out.println("model use log insert success!");
		else  System.out.println("model use log insert failed!");
		con.close();
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean checkInsert(String nodeIP)
	{
		boolean flag=true;
		SavedNodeSQLConnector con=new SavedNodeSQLConnector(nodeIP);
		String selectSql="select * from modelinfo.fileinfo where 模型文件=? ";
		con.readyPreparedStatement(selectSql);
		con.setString(1, this.FileName);
		ResultSet rs=con.executeQuery();
		try
		{		
			if(rs.next())
				flag= false;
		}catch(Exception e)
		{ 
			e.printStackTrace();
			flag=false;
		}
		con.close();
		return flag;
	}
	public boolean recordInsert(String nodeIP)
	{
		boolean flag=true;
		SavedNodeSQLConnector con=new SavedNodeSQLConnector(nodeIP);
		String insertSql="insert into modelinfo.fileinfo(模型文件,storepath,适用软件,简介) "
				+ "values(?,?,?,?) ";
		con.readyPreparedStatement(insertSql);
		con.setString(1,this.FileName);
		con.setString(2,this.StorePath);
		con.setString(3,this.ObjectiveSoft);//暂时用字符串表示
		con.setString(4,this.FileDescription);

		if(con.executeUpdate()<=0) {return false;}
		
		String getnewfileidSql="select fileID from modelinfo.fileinfo where 模型文件=?";
		con.readyPreparedStatement(getnewfileidSql);
		con.setString(1, this.FileName);
		ResultSet rs=con.executeQuery();
		try {
			if(rs.next()) this.ModelID=rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this.ModelID==0){return false;}
		con.close();
		return flag;
	}
	
	public boolean UpdateModelfileInfo(int fileID)
	{
		if(fileID<=0) return false;
		boolean flag=true;
		mySQLConnector con=new mySQLConnector();
		String updateModelfileinfoSql="update modelinfo.fileinfo "
				+ "set 适用软件=?,简介=? "
				+ "where fileID=?";
		con.readyPreparedStatement(updateModelfileinfoSql);
		
		con.setString(1, this.ObjectiveSoft);
		con.setString(2, this.FileDescription);
		con.setInt(3, fileID);
		if(con.executeUpdate()<=0) flag=false;
		con.close();
		return flag;
	}
}

