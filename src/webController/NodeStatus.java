package webController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import model.NodeSnmp;
import model.ResultSetConverter;
import model.mySQLConnector;

/**
 * Servlet implementation class NodeStatus
 */
@WebServlet("/NodeStatus")
public class NodeStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NodeStatus() {
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
		
		if(request.getParameter("nodeid")==null)
		{//获取节点机器信息列表
			mySQLConnector con=new mySQLConnector();
			String sql="select nodeID,IPAddress,status,maxcount,sum(occupied) as usecount "
					+ "from objectmodelingsoft.nodeinfo,objectmodelingsoft.availableresource "
					+ "where nodeID=FK_node group by nodeID";
			
			JSONArray jsonArray=new JSONArray();
			try {
				jsonArray=ResultSetConverter.convert(con.executeQuery(sql));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				con.close();
			}
			response.setContentType("application/json");
			// Get the printwriter object from response to write the required json object to the output stream      
			PrintWriter out = response.getWriter();
			// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
			out.print(jsonArray);
			out.flush();
		}
		else{
			int id=Integer.parseInt(request.getParameter("nodeid"));
			mySQLConnector con=new mySQLConnector();
			String sql="select IPAddress from objectmodelingsoft.nodeinfo where nodeID=?";
			con.readyPreparedStatement(sql);
			con.setInt(1, id);
			ResultSet rs=con.executeQuery();
			String ip=null;
			try {
				if(rs.next())
				{
					ip=new String(rs.getString(1));
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(ip);
			if(ip!=null){
				NodeSnmp node=new NodeSnmp(ip);
				double cpuUsage=-1,memoryUsage=0;
				try{
					node.initCommforNode();
					cpuUsage=node.CpuUsageforNode();
					//memoryUsage=node.MemoryUsageforNode();
				}catch(IOException e){
					e.printStackTrace();
				}
				System.out.println(cpuUsage+"  "+memoryUsage);
				if(cpuUsage>=0&&memoryUsage>=0){
					//System.out.println("success,"+cpuUsage+","+memoryUsage);
				response.getWriter().append("success,"+cpuUsage+","+memoryUsage);
				}else response.getWriter().append("failed");
			}
		}
	}

}
