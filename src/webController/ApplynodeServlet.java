package webController;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.NodeMachine;
import model.mySQLConnector;

/**
 * Servlet implementation class ApplynodeServelt
 */
@WebServlet("/ApplynodeServlet")
public class ApplynodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplynodeServlet() {
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
		//下面的操作是为了申请操作节点做准备的
		/*需要参数：1.申请的工具名称 2.模型文件的名称（为空的时候代表新建模型）*/
		/*返回数据：1.节点IP 2.节点用户名 3.节点登录密码*/
		/*流程为：取参数1，查询数据库是否有空闲节点记录。
			如果没有，给用户通知；
			如果有空闲节点，返回数据1、2和3，打开节点操作新页面.
			（此时用户还只能看到新页面处于准备状态中，等待节点机器发出ready信号后，节点操作页面才对用户可见）			
		 */
		request.setCharacterEncoding("utf-8");
		
		String toolname=request.getParameter("toolname");
		String filename=null;
		if(request.getParameter("modelID")!=null&&!request.getParameter("modelID").equals("null")){
			
			System.out.println("===文件信息ID :"+request.getParameter("modelID"));
			
			mySQLConnector con=new mySQLConnector();
			String getmodelfilenameSql="select 模型文件 from geomodel.fileinfo where fileID=?";
			con.readyPreparedStatement(getmodelfilenameSql);
			con.setInt(1, Integer.parseInt(request.getParameter("modelID")));
			ResultSet rs=con.executeQuery();
			try {
				if(rs.next())
				{
					filename=rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("start^^^^^^^^^"+toolname);
		
		NodeMachine node=new NodeMachine();
		boolean availablenode = node.checkIdleNode(toolname);
		if (availablenode) {
			if(filename==null){
				System.out.println("5555555555555555555success,"+node.getIPAddress()+","+node.getUserName()+","+node.getPassWord()+","+node.getPort()+",");
				response.getWriter().append("success,"+node.getIPAddress()+","+node.getUserName()+","+node.getPassWord()+","+node.getPort());
			}
			else{
				System.out.println("66666666666success,"+node.getIPAddress()+","+node.getUserName()+","+node.getPassWord()+","+node.getPort()+","+filename);
				response.getWriter().append("success,"+node.getIPAddress()+","+node.getUserName()+","+node.getPassWord()+","+node.getPort()+","+filename);
			}
		}else response.getWriter().append("Node apply failed!");
		
	}

}
