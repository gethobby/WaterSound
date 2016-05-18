package webController;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GeoModel;
import model.NodeMachine;
import model.ObjectSoft;
import model.UploadFile;
import model.mySQLConnector;

/**
 * Servlet implementation class NodeReadySignal
 */
@WebServlet("/NodeReady")
public class NodeReadySignal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NodeReadySignal() {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		//从request中取出参数：1.nodeIP,2.port,3.softname,4.modelID,5.username
		String nodeIP=request.getParameter("nodeIP");
		int port=0;
		if(request.getParameter("port")!=null)
			port=Integer.parseInt(request.getParameter("port"));
		String softname=request.getParameter("soft");
		String modelID=request.getParameter("model");
		String username=request.getParameter("user");
		//testcode 
		System.out.println("node param!"+nodeIP+port+softname+username+modelID);
		//需要使用模型，获取需要传输的模型文件的路径
		String filepath=null;
		String filename=null;
		int filesize=0;
		
		if((nodeIP==null)||(port==0)||(softname==null)||(username==null)){//这四个参数必须不为null，modelID可以为null
			System.out.println("node param error!");
		}
		else
		{
			if(modelID!=null&&!modelID.equals("null")&&!(modelID.trim().equals(""))){
				System.out.println("start file prepare");
				mySQLConnector con=new mySQLConnector();
				String getmodelfilepathSQL="select 模型文件, storepath,size from geomodel.fileinfo where fileID=?";
				con.readyPreparedStatement(getmodelfilepathSQL);
				con.setInt(1, Integer.parseInt(modelID));
				ResultSet rs=con.executeQuery();
				//gm=new GeoModel(modelname,softname);
				try {
					if(rs.next()){
						filename=rs.getString(1);
						filepath=rs.getString(2);
						filepath=filepath.replace('/', '\\')+filename;
						filesize=rs.getInt(3);
					}else{
						filepath=null;
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					filepath=null;
				}
				finally{con.close();}
				if(filepath==null||filepath.equals("")) filepath="NULL";
				System.out.println("start file transmit filepath:"+filepath);
			}
			else{modelID=null;}
			
			NodeMachine nm=new NodeMachine();
			if(nm.startPreparing(nodeIP,port,softname,filepath,username))
			{
				response.getWriter().append("ready");
				//此处节点机器准备完成后，插入模型的使用记录
				if(modelID!=null){
					UploadFile.InsertModelFileuselog(username, Integer.parseInt(modelID), filesize);
				}
//				if(nm.resultReceive(port,"D:\\file\\","computeresult.xml"))
					System.out.println("node prepare ready!");
			}
			else System.out.println("node prepare failed!");
		}
	}

}
