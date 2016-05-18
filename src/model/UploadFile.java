package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UploadFile {//模型文件的实体类
	int FileID;//文件编号
	int TargetID;//目标
	String FileName;//模型文件
	String StorePath;//storepath
	int size=0;
	String FileDescription;//简介
	String FileSecretLevel;//密级
	String ObjectiveSoft;//适用软件，暂存成字符串形式
	String FileSource;//来源
	String ContactPerson;//联系人
	String ContactComp;//单位名称
	String ContactAddress;//通信地址
	String ContactZip;//邮政编码
	String ContactTel;//联系电话
	String ContactEmail;//电子邮件
	String type;//临时的字段，后续可能改动删除
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTargetID() {
		return TargetID;
	}
	public void setTargetID(int targetID) {
		TargetID = targetID;
	}
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
	public String getFileSecretLevel() {
		return FileSecretLevel;
	}
	public void setFileSecretLevel(String fileSecretLevel) {
		FileSecretLevel = fileSecretLevel;
	}
	public String getObjectiveSoft() {
		return ObjectiveSoft;
	}
	public void setObjectiveSoft(String objectiveSoft) {
		ObjectiveSoft = objectiveSoft;
	}
	public String getFileSource() {
		return FileSource;
	}
	public void setFileSource(String fileSource) {
		FileSource = fileSource;
	}
	public String getContactPerson() {
		return ContactPerson;
	}
	public void setContactPerson(String contactPerson) {
		ContactPerson = contactPerson;
	}
	public String getContactComp() {
		return ContactComp;
	}
	public void setContactComp(String contactComp) {
		ContactComp = contactComp;
	}
	public String getContactAddress() {
		return ContactAddress;
	}
	public void setContactAddress(String contactAddress) {
		ContactAddress = contactAddress;
	}
	public String getContactZip() {
		return ContactZip;
	}
	public void setContactZip(String contactZip) {
		ContactZip = contactZip;
	}
	public String getContactTel() {
		return ContactTel;
	}
	public void setContactTel(String contactTel) {
		ContactTel = contactTel;
	}
	public String getContactEmail() {
		return ContactEmail;
	}
	public void setContactEmail(String contactEmail) {
		ContactEmail = contactEmail;
	}
	public int getFileID() {
		return FileID;
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
	public boolean checkInsert()
	{
		boolean flag=true;
		mySQLConnector con=new mySQLConnector();
		String selectSql="select * from geomodel.fileinfo,geomodel.objectinfo where geomodel.objectinfo.ObjectID=? and 模型文件=? and ObjectID=目标";
		con.readyPreparedStatement(selectSql);
		con.setInt(1,this.TargetID);
		con.setString(2, this.FileName);
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
	public boolean recordInsert()
	{
		boolean flag=true;
		mySQLConnector con=new mySQLConnector();
		String insertSql="insert into geomodel.fileinfo(目标,模型文件,storepath,size,简介,密级,适用软件,来源,联系人,单位名称,通信地址,邮政编码,电话,电子邮件) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		con.readyPreparedStatement(insertSql);
		con.setInt(1, this.TargetID);
		con.setString(2,this.FileName);
		con.setString(3,this.StorePath);
		con.setInt(4,this.size);
		con.setString(5,this.FileDescription);
		con.setString(6,this.FileSecretLevel);
		con.setString(7,this.ObjectiveSoft);//暂时用字符串表示
		con.setString(8,this.FileSource);
		con.setString(9,this.ContactPerson);
		con.setString(10,this.ContactComp);
		con.setString(11,this.ContactAddress);
		con.setString(12,this.ContactZip);
		con.setString(13,this.ContactTel);
		con.setString(14,this.ContactEmail);
		if(con.executeUpdate()<=0) {return false;}
		
		String getnewfileidSql="select fileID from geomodel.fileinfo where 目标=? and 模型文件=?";
		con.readyPreparedStatement(getnewfileidSql);
		con.setInt(1, this.TargetID);
		con.setString(2, this.FileName);
		ResultSet rs=con.executeQuery();
		try {
			if(rs.next()) this.FileID=rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this.FileID==0){return false;}
		con.close();
		return flag;
	}
	
	public boolean UpdateModelfileInfo(int fileID)
	{
		if(fileID<=0) return false;
		boolean flag=true;
		mySQLConnector con=new mySQLConnector();
		String updateModelfileinfoSql="update geomodel.fileinfo "
				+ "set 简介=?,密级=?,适用软件=?,来源=?,联系人=?,单位名称=?,通信地址=?,邮政编码=?,电话=?,电子邮件=? "
				+ "where fileID=?";
		con.readyPreparedStatement(updateModelfileinfoSql);
		con.setString(1, this.FileDescription);
		con.setString(2, this.FileSecretLevel);
		con.setString(3, this.ObjectiveSoft);
		con.setString(4, this.FileSource);
		con.setString(5, this.ContactPerson);
		con.setString(6, this.ContactComp);
		con.setString(7, this.ContactAddress);
		con.setString(8, this.ContactZip);
		con.setString(9, this.ContactTel);
		con.setString(10, this.ContactEmail);
		con.setInt(11, fileID);
		if(con.executeUpdate()<=0) flag=false;
		con.close();
		return flag;
	}
}

