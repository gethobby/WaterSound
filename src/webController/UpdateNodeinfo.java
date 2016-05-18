package webController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.NodeMachine;
import model.UploadFile;
import model.mySQLConnector;

/**
 * Servlet implementation class UpdateNodeinfo
 */
@WebServlet("/UpdateNodeinfo")
public class UpdateNodeinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateNodeinfo() {
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
		
		String param=request.getParameter("type");
		if(param!=null&&param.equals("resource")){
			if(request.getParameter("id")!=null){
				int nodeID=Integer.parseInt(request.getParameter("id"));
				if(request.getParameter("AccountID")!=null&&!request.getParameter("AccountID").equals(""))
				{
					int accountID=Integer.parseInt(request.getParameter("AccountID"));
					mySQLConnector con=new mySQLConnector();
					String updateresourceSql="update objectmodelingsoft.availableresource "
							+ "set account=?,password=?,port=?,softname=? where ID=?";
					con.readyPreparedStatement(updateresourceSql);
					con.setString(1, request.getParameter("AccountName"));
					con.setString(2, request.getParameter("Password"));
					con.setInt(3, Integer.parseInt(request.getParameter("Port")));
					con.setString(4, request.getParameter("Softs"));
					con.setInt(5, accountID);
					if(con.executeUpdate()>0){
						response.getWriter().append("success");
					}
					else{
						 response.getWriter().append("failed");
					}
					con.close();
				}
				else{
					mySQLConnector con=new mySQLConnector();
					String insertnewresourceSql="insert into objectmodelingsoft.availableresource"
							+ "(FK_node,account,password,port,softname) values(?,?,?,?,?)";
					con.readyPreparedStatement(insertnewresourceSql);
					con.setInt(1, nodeID);
					con.setString(2, request.getParameter("AccountName"));
					con.setString(3, request.getParameter("Password"));
					con.setInt(4, Integer.parseInt(request.getParameter("Port")));
					con.setString(5, request.getParameter("Softs"));
					if(con.executeUpdate()>0){
						response.getWriter().append("success");
					}
					else{
						 response.getWriter().append("failed");
					}
					con.close();
				}

			}
		}
		else if(param!=null&&param.equals("node")){
			if(request.getParameter("id")!=null){
				int nodeID=Integer.parseInt(request.getParameter("id"));
				NodeMachine node=new NodeMachine();
				node.setIPAddress(request.getParameter("NodeIPAddress"));
				node.setNodeStatus(Integer.parseInt(request.getParameter("NodeStatus")));
				node.setNodeMaxuseCount(Integer.parseInt(request.getParameter("NodeMaxUse")));
				node.setAvailableSofts(request.getParameter("Softs"));
				if(node.UpdateNodeinfo(nodeID)&&node.UpdateResourceinfo(nodeID)){
					response.getWriter().append("success");
				}
				else{
					 response.getWriter().append("failed");
				}
			}
		}

	}

}
