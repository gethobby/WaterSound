package model;


public class DoDeleteByID {

	private static final String DeleteSoftSql = "delete from objectmodelingsoft.softinfo where softID=?";
	private static final String DeleteUserSql="delete from platformuser.account where userID=?";


	/*
	 * DeleteUserByIDs
	 * @return    返回成功删除的用户记录数
	 */
	public static int deleteUserByIds(String idsstr)
	{ 
		String[] ids = idsstr.split(",");
		mySQLConnector con=new mySQLConnector();
		int deleteNum =0 ;
		
		for (String id : ids) {		
			int id_int = Integer.valueOf(id);
			con.readyPreparedStatement(DeleteUserSql);
			con.setInt(1, id_int);
		    deleteNum+=con.executeUpdate();	
		}
	    con.close();
	    return deleteNum;
	}
   
	public static int deleteSoftByIds(String idsstr)
	{ 
		String[] ids = idsstr.split(",");
		mySQLConnector con=new mySQLConnector();
		int deleteNum =0 ;
		
		for (String id : ids) {		
			int id_int = Integer.valueOf(id);
			con.readyPreparedStatement(DeleteSoftSql);
			con.setInt(1, id_int);
		    deleteNum+=con.executeUpdate();	
		}
	    con.close();
	    return deleteNum;
	}
}
