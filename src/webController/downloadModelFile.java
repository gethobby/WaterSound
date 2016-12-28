package webController;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import developConfig.Gobal;
import model.Util;
import model.mySQLConnector;


/**
 * Servlet implementation class downloadModelFile
 */
@WebServlet("/DownloadModelFile")
public class DownloadModelFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadModelFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}
    
	protected ResultSet GetModelRecordByNodeIPAndModelName(String nodeIP,String modelName) {
		mySQLConnector con = new mySQLConnector(nodeIP);
		System.out.println("节点机"+nodeIP+", 下载模型文件："+modelName);
		String getObjectivesoftsSql="select fileID,模型文件,size,storepath,适用软件,简介  from modelinfo.fileinfo where 模型文件=?";
		con.readyPreparedStatement(getObjectivesoftsSql);
		con.setString(1, modelName);
		ResultSet rs=con.executeQuery();
		return rs;
	}
    protected void IfExistDeleteByMOdelID(int modelID) {
    	  // 以下代码是删除softnode.fileinfo中可能存在的之前关于file的一条记录	
    	  mySQLConnector local	 = new mySQLConnector();	   
   		   System.out.println("如果存在，则删除模型ID= "+modelID+"对应记录");
   		   String deletesql = "delete from softnode.fileinfo where fileID=?";
   		   local.readyPreparedStatement(deletesql);
   			local.setInt(1, modelID);
   			if (local.executeUpdate()>0) {
   				System.out.println("softnode.fileinfo原先存在一条记录，已删除");
   			}else {
   				System.out.println("softnode.fileinfo原先不存在一条记录，可以直接插入");
   			}
    
	}
	
	protected boolean InsertRecordToSoftNodeFileInfo(ResultSet rs) {
		mySQLConnector local = new mySQLConnector();		
		
		String insertSql="insert into softnode.fileinfo(fileID,模型文件,size,storepath,适用软件,简介) "
				+ "values(?,?,?,?,?,?) ";
			
		try {
			System.out.println("获取记录数"+rs.getRow());	
			while (rs.next()) {	
			 local.readyPreparedStatement(insertSql);
			 
			 int modelID = rs.getInt("fileID");
			 IfExistDeleteByMOdelID(modelID);//如果存在则删除原先softnode.fileinfo表里的model
			 local.setInt(1,modelID);
			 
			 local.setString(2,rs.getString("模型文件"));
			 local.setInt(3,rs.getInt("size"));
			 local.setString(4,Gobal.OBJECT_ROOT_DIR);
			 local.setString(5, rs.getString("适用软件"));
			 local.setString(6, rs.getString("简介"));
			  if(local.executeUpdate()<=0) {return false;}		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
		
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		String modelName=request.getParameter("modelName");
		String nodeIP=request.getParameter("nodeIP");
		HttpSession session=request.getSession();
		String username = (String) session.getAttribute("username");
		String name = (username==null||username.equals(""))? "server":username;
		
		if (modelName!=""&&!modelName.equals("")) {		
			
			//System.out.println("服务器下载模型文件："+modelName);
			if(Util.ReceiveModelFile("1",nodeIP, Gobal.NODE_SOCKET_PORT,  modelName, name,Gobal.SERVER_RECV_CALLBACK,Gobal.SERVER_RECV_XML))
			{
				//以下需要将下载的模型信息都插入到softnode.fileinfo这张表中，便于服务器端和软件节点端通信
		        ResultSet rs = GetModelRecordByNodeIPAndModelName(nodeIP, modelName);
		        if(InsertRecordToSoftNodeFileInfo(rs)){	
		        	// 插入记录成功后，立即解压zip包
		        	//String modelZip = modelName.substring(0, modelName.lastIndexOf('.'))+".zip";
		        	//System.out.println("解压"+modelZip);
		        	while(!(new File(Gobal.OBJECT_ROOT_DIR+modelName).exists())){ 	          	   
			        	  try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	}		        	
		        	//Util.Unzip(Gobal.OBJECT_ROOT_DIR+modelZip, Gobal.OBJECT_ROOT_DIR);
		        	
			    	System.out.println("download model file success and insert to table softnode.fileinfo success!");				
				    response.getWriter().append("success");
		        }else{
		        	System.out.println("向服务器中表softnode.fileinfo插入记录失败");
		        }

			}
			else{
				System.out.println("download Model File failed!");
				response.getWriter().append("failed"+","+"下载模型文件失败");
			}
		}
	}

}
