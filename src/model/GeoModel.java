package model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class GeoModel {//目标的实体类
	int ObjectID;
	String ObjectName=null;//目标名称
	String ObjectLOGO=null;//目标图片
	private int ObjectTypeID=0;//目标分类号
	String ObjectType1=null;//目标分类1
	String ObjectType2=null;//目标分类2
	String ObjectBelong=null;//所属国家

	
	public String getObjectName() {
		return ObjectName;
	}


	public void setObjectName(String objectName) {
		ObjectName = objectName;
	}


	public String getObjectLOGO() {
		return ObjectLOGO;
	}


	public void setObjectLOGO(String objectLOGO) {
		ObjectLOGO = objectLOGO;
	}


	public String getObjectType1() {
		return ObjectType1;
	}


	public void setObjectType1(String objectType1) {
		ObjectType1 = objectType1;
	}


	public String getObjectType2() {
		return ObjectType2;
	}


	public void setObjectType2(String objectType2) {
		ObjectType2 = objectType2;
	}


	public String getObjectBelong() {
		return ObjectBelong;
	}


	public void setObjectBelong(String objectBelong) {
		ObjectBelong = objectBelong;
	}


	public int getObjectID() {
		return ObjectID;
	}

	/*
	 * 创建新目标
	 * */
	public void setObjectTypeID()
	{
		mySQLConnector con=new mySQLConnector();
		String getobjectclassidSql="select ID from geomodel.objecttype_view where 一级类别=? and 二级类别=?";
		con.readyPreparedStatement(getobjectclassidSql);
		con.setString(1, this.ObjectType1);
		con.setString(2, this.ObjectType2);
		ResultSet rs=con.executeQuery();
		try {
			if(rs.next())
			{
				this.ObjectTypeID=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{con.close();}
	}
	public boolean InsertNewObject()
	{
		boolean flag=true;
		mySQLConnector con=new mySQLConnector();
		
		String insertNewObjectSql="insert into geomodel.objectinfo(目标名称,目标图片,目标分类,所属国家) values(?,?,?,?) ";
		con.readyPreparedStatement(insertNewObjectSql);
		con.setString(1, this.ObjectName);
		con.setString(2,this.ObjectLOGO);
		setObjectTypeID();
		if(this.ObjectTypeID>0)	con.setInt(3, this.ObjectTypeID);
		else{
			con.close();
			return false;
		}
		con.setString(4, this.ObjectBelong);
		if(con.executeUpdate()<=0){
			flag=false;
		}
		con.close();
		return flag;
	}
	
	public boolean UpdateObjectInfo(int objectID)
	{
		if(objectID<=0) return false;
			
		boolean flag=true;
		
		mySQLConnector con=new mySQLConnector();
		
		String updateobjectinfoSql="update geomodel.objectinfo "
				+ "set 目标名称=?,目标图片=?,目标分类=?,所属国家=? "
				+ "where objectID=?";
		con.readyPreparedStatement(updateobjectinfoSql);
		con.setString(1, this.ObjectName);
		con.setString(2, this.ObjectLOGO);
		setObjectTypeID();
		con.setInt(3, this.ObjectTypeID);
		con.setString(4, this.ObjectBelong);
		con.setInt(5, objectID);

		if(con.executeUpdate()<=0) flag=false;
		con.close();
		return flag;
	}
	
//	public GeoModel(String ObjectName,String softname){
//		this.ObjectName=ObjectName;
//		
//		//利用软件名称找出软件接受的模型类型
//		setGeoModel(softname);
//	}


	/**
	 * @param softname 当前用户即将使用的软件名称
	 */
//	private void setGeoModel( String softname) {
//		mySQLConnector con=new mySQLConnector();
//		
//		//利用软件名称找出软件接受的模型类型
//		String modeltypesql="SELECT acceptmodeltype as amt FROM objectmodelingsoft.softinfo where softID=?;";
//		String[] toolaccpettypes=null;
//		con.readyPreparedStatement(modeltypesql);
//		con.setString(1, softname);
//		ResultSet rs=con.executeQuery();
//		try {
//			if(rs.next()){
//				toolaccpettypes=rs.getString("amt").split(",");//amt为数据库中相应软件匹配的模型类型字段
//			}
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		//利用模型名称以及上面得到的软件接受模型类型，找到与软件匹配的模型文件的存放位置，获取模型文件大小等信息
//		String sql="SELECT * FROM geomodel.fileinfo where filename=?;";
//		con.readyPreparedStatement(sql);
//		con.setString(1, this.ObjectName);
//		rs=con.executeQuery();
//		try {
//			if(rs.next()){
//				String[] modeltypes=rs.getString("type").split(",");//type为数据库中现有模型类型的字段
//				int i=0,j=0;
//				if(toolaccpettypes!=null){
//					while (i < modeltypes.length) {
//						while (j < toolaccpettypes.length) {
//							if (modeltypes[i].compareTo(toolaccpettypes[j]) == 0) {
//								this.modeltype = modeltypes[i];
//								break;
//							}
//							j++;
//						}
//						if(this.modeltype!=null) break;
//						i++;
//					}
//				}
//				if(this.modeltype!=null){
//					//还原路径，数据库存放的是模型所在的路径，相同名称不同类型的模型存在与模型名称的文件夹下，文件夹存在前面的路径下
//					this.filepath=rs.getString("storepath")+"\\"+rs.getString("filename")+"\\"+rs.getString("filename")
//							+'.'+modeltype;
//					//由模型文件路径获取文件大小，即数据量
//					File f = new File(this.filepath);
//					if(f.exists()&&f.isFile())
//						this.modelsize	= f.length();	// 大小 bytes
//				}
//				
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 在节点准备完成后调用，记录用户对模型的使用
	 * @param user 当前需要使用模型的用户名称
	 */

//	public boolean verifyGeoModel()
//	{
//		if(this.ObjectName==null&&this.filepath==null)
//			return false;
//		return true;
//	}
}
