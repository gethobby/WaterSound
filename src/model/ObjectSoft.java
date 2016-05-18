package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.CORBA.portable.InputStream;

public class ObjectSoft {
	int SoftID;//软件编号
	String SoftName;//测试时期暂时是单列，以后指中文名称*
	String SoftNameEN;//英文名称
	String SoftVersion;//版本
	String SoftUpdateTime;//发布/更新时间 实际上应该是Date的类型，为了转化插入方便暂用String
	String SoftLogo;//软件LOGO
	String SoftType1;//一级类型
	String SoftType2;//二级类型
	String SoftSecretLevel;//密级
	String SoftDescription;//简介
	String SoftFunctionDesc;//软件功能
	String SoftComputeExample;//典型算例
	String SoftResultExample;//应用效果
	String ContactPerson;//联系人
	String ContactComp;//单位名称
	String ContactAddress;//通信地址
	String ContactZip;//邮政编码
	String ContactTel;//联系电话
	String ContactEmail;//电子邮件
	
	String SoftType;
	String softlist="";
	public String getSoftNameEN() {
		return SoftNameEN;
	}

	public void setSoftNameEN(String softNameEN) {
		SoftNameEN = softNameEN;
	}

	public String getSoftVersion() {
		return SoftVersion;
	}

	public void setSoftVersion(String softVersion) {
		SoftVersion = softVersion;
	}

	public String getSoftUpdateTime() {
		return SoftUpdateTime;
	}

	public void setSoftUpdateTime(String softUpdateTime) {
		SoftUpdateTime = softUpdateTime;
	}

	public String getSoftLogo() {
		return SoftLogo;
	}

	public void setSoftLogo(String softLogo) {
		SoftLogo = softLogo;
	}

	public String getSoftType1() {
		return SoftType1;
	}

	public void setSoftType1(String softType1) {
		SoftType1 = softType1;
	}

	public String getSoftType2() {
		return SoftType2;
	}

	public void setSoftType2(String softType2) {
		SoftType2 = softType2;
	}

	public String getSoftSecretLevel() {
		return SoftSecretLevel;
	}

	public void setSoftSecretLevel(String softSecretLevel) {
		SoftSecretLevel = softSecretLevel;
	}

	public String getSoftDescription() {
		return SoftDescription;
	}

	public void setSoftDescription(String softDescription) {
		SoftDescription = softDescription;
	}

	public String getSoftFunctionDesc() {
		return SoftFunctionDesc;
	}

	public void setSoftFunctionDesc(String softFunctionDesc) {
		SoftFunctionDesc = softFunctionDesc;
	}

	public String getSoftComputeExample() {
		return SoftComputeExample;
	}

	public void setSoftComputeExample(String softComputeExample) {
		SoftComputeExample = softComputeExample;
	}

	public String getSoftResultExample() {
		return SoftResultExample;
	}

	public void setSoftResultExample(String softResultExample) {
		SoftResultExample = softResultExample;
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

	public void setSoftName(String softName) {
		SoftName = softName;
	}


	/**
	 * @param 
	 * @return 如果新软件记录添加成功就返回true，否则返回false
	 */
	public boolean insertNewSoft()
	{
		boolean flag=false;
		mySQLConnector con=new mySQLConnector();
		String insertNewsoftSql="insert into objectmodelingsoft.softinfo"
				+ "(softname,英文名称,版本,发布更新时间,LOGO,一级类型,二级类型,密级,简介,软件功能,典型算例,应用效果,联系人,单位名称,通信地址,邮政编码,电话,电子邮件) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		con.readyPreparedStatement(insertNewsoftSql);
		con.setString(1, this.SoftName);
		con.setString(2, this.SoftNameEN);
		con.setString(3, this.SoftVersion);
		//String UpdateTime = new SimpleDateFormat("yyyy-MM-dd").format(this.SoftUpdateTime);//将时间格式转换成符合Timestamp要求的格式.
        //Timestamp upt = Timestamp.valueOf(UpdateTime);//把时间转换
		con.setString(4, this.SoftUpdateTime);		
		con.setString(5, this.SoftLogo);
		con.setString(6, this.SoftType1);		
		con.setString(7, this.SoftType2);
		con.setString(8, this.SoftSecretLevel);
		con.setString(9, this.SoftDescription);
		con.setString(10, this.SoftFunctionDesc);		
		con.setString(11, this.SoftComputeExample);
		con.setString(12, this.SoftResultExample);		
		con.setString(13, this.ContactPerson);
		con.setString(14, this.ContactComp);
		con.setString(15, this.ContactAddress);
		con.setString(16, this.ContactZip);		
		con.setString(17, this.ContactTel);
		con.setString(18, this.ContactEmail);
		
		if(con.executeUpdate()>0)
			flag=true;
		con.close();
		return flag;

	}
	
	/**
	 * @param filetype  模型文件的类型，即后缀名
	 * @return 如果搜索到可用的软件就返回true
	 */
	public boolean getProperSoft(String[] filetype) {

		boolean flag=false;
		
		mySQLConnector con=new mySQLConnector();
		String querystring="";
		//根据模型类型参数构造查询语句
		 for (int i = 0 ; i <filetype.length ; i++ ) { 
			 querystring+="'%";
			 if(i==filetype.length-1)
				 querystring+=(filetype[i]+"%';");
			 else
				 querystring+=(filetype[i]+"%' or acceptmodeltype like ");
		} 
		ResultSet rs= con.executeQuery("select softID,softname,type from objectmodelingsoft.softinfo where acceptmodeltype like"+querystring);
		try
		{		
			while(rs.next()){
				
				SoftID=rs.getInt("softID");
				SoftName=rs.getString("softname");
				SoftType=rs.getString("type");
				//能接受该模型类型的软件信息列表，用&隔开不同软件，用逗号隔开软件各项信息
				softlist+=Integer.toString(SoftID)+','+SoftName+','+SoftType+'&';
			}
		}catch(Exception e)
		{ 
			e.printStackTrace();
			}
		con.close();
		
		if(softlist!="")
			flag=true;
		return flag;

	}

	public int getSoftID() {
		return SoftID;
	}

	public String getSoftName() {
		return SoftName;
	}

	public String getSoftType() {
		return SoftType;
	}

	public String getSoftlist() {
		return softlist;
	}

	public void InsertSoftuselog(String soft,String user,Date starttime)
	{
		mySQLConnector con=new mySQLConnector();
		String softuseinsert="insert into platformuser.softuse( username,FK_soft,starttime,endtime) values(?,?,?,?);";
		con.readyPreparedStatement(softuseinsert);
		con.setString(1, user);
		con.setString(2, soft);
		String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(starttime);//将时间格式转换成符合Timestamp要求的格式.
        Timestamp s = Timestamp.valueOf(startTime);//把时间转换
		String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//将时间格式转换成符合Timestamp要求的格式.
        Timestamp e = Timestamp.valueOf(endTime);//把时间转换
        con.setTimeStamp(3, s);
        con.setTimeStamp(4, e);
        if(con.executeUpdate()<=0) System.out.println("insert soft use log");
        else System.out.println("insert soft use log failed!");
		con.close();
		
	}
	public boolean UpdateSoftBasicinfo(int softID)
	{
		boolean flag=true;
		if(softID<=0) return false;
		mySQLConnector con= new mySQLConnector();
		String updatesoftbasicinfoSql="update objectmodelingsoft.softinfo "
				+ "set 英文名称=?,版本=?,发布更新时间=?,LOGO=?,一级类型=?,二级类型=?,密级=?,简介=? where softID=? ;";
		con.readyPreparedStatement(updatesoftbasicinfoSql);
		con.setString(1, this.SoftNameEN);
		con.setString(2, this.SoftVersion);
		con.setString(3,this.SoftUpdateTime);
		con.setString(4, this.SoftLogo);
		con.setString(5, this.SoftType1);		
		con.setString(6, this.SoftType2);
		con.setString(7, this.SoftSecretLevel);
		con.setString(8, this.SoftDescription);
		con.setInt(9,softID);
		if(con.executeUpdate()<=0) flag=false;
		con.close();
		return flag;
	}
	public boolean UpdateSoftFuncDesc(int softID)
	{
		boolean flag=true;
		if(softID<=0) return false;
		mySQLConnector con= new mySQLConnector();
		String updatesoftfuncdescSql="update objectmodelingsoft.softinfo set 软件功能=? where softID=? ;";
		con.readyPreparedStatement(updatesoftfuncdescSql);
		con.setString(1, this.SoftFunctionDesc);
		con.setInt(2,softID);
		if(con.executeUpdate()<=0) flag=false;
		con.close();
		return flag;
	}
	public boolean UpdateSoftComputeExample(int softID)
	{
		boolean flag=true;
		if(softID<=0) return false;
		mySQLConnector con= new mySQLConnector();
		String updatesoftcomputeexampleSql="update objectmodelingsoft.softinfo set 典型算例=? where softID=? ;";
		con.readyPreparedStatement(updatesoftcomputeexampleSql);
		con.setString(1, this.SoftComputeExample);
		con.setInt(2,softID);
		if(con.executeUpdate()<=0) flag=false;
		con.close();
		return flag;
	}
	public boolean UpdateSoftResultExample(int softID)
	{
		boolean flag=true;
		if(softID<=0) return false;
		mySQLConnector con= new mySQLConnector();
		String updatesoftresultexampleSql="update objectmodelingsoft.softinfo set 应用效果=? where softID=? ;";
		con.readyPreparedStatement(updatesoftresultexampleSql);
		con.setString(1, this.SoftResultExample);
		con.setInt(2,softID);
		if(con.executeUpdate()<=0) flag=false;
		con.close();
		return flag;
	}
	public boolean UpdateSoftContactinfo(int softID)
	{
		boolean flag=true;
		if(softID<=0) return false;
		mySQLConnector con= new mySQLConnector();
		String updatesoftbasicinfoSql="update objectmodelingsoft.softinfo "
				+ "set 联系人=?,单位名称=?,通信地址=?,邮政编码=?,电话=?,电子邮件=? where softID=? ;";
		con.readyPreparedStatement(updatesoftbasicinfoSql);
		con.setString(1, this.ContactPerson);
		con.setString(2, this.ContactComp);
		con.setString(3, this.ContactAddress);
		con.setString(4, this.ContactZip);		
		con.setString(5, this.ContactTel);
		con.setString(6, this.ContactEmail);
		con.setInt(7,softID);
		if(con.executeUpdate()<=0) flag=false;
		con.close();
		return flag;
	}
//	public static void main(String[] args)
//	{
//		ObjectSoft os=new ObjectSoft();
//		os.testruntimeexec();
//	}
}
