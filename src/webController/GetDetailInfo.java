package webController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import model.ResultSetConverter;
import model.mySQLConnector;

/**
 * Servlet implementation class GetDetailInfo
 */
@WebServlet("/GetDetailInfo")
public class GetDetailInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDetailInfo() {
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
		
		String param=request.getParameter("infotype");
		JSONArray jsonArray=new JSONArray();
		if(param!=null&&param.equals("soft"))
		{
			if(request.getParameter("id")!=null&&!request.getParameter("id").equals("")){
				int softid=Integer.parseInt(request.getParameter("id"));
				mySQLConnector con=new mySQLConnector();
				con.readyPreparedStatement("select * from objectmodelingsoft.softinfo where softID=?");
				con.setInt(1, softid);
				try {
					jsonArray=ResultSetConverter.convert(con.executeQuery());
				} catch (SQLException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					con.close();
				}
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				
				// Get the printwriter object from response to write the required json object to the output stream      
				PrintWriter out = response.getWriter();
				// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				out.print(jsonArray);
				out.flush();
			}
		}
		else if(param!=null&&param.equals("modelfile"))
		{
			if(request.getParameter("id")!=null&&!request.getParameter("id").equals(""))
			{
				int fileid=Integer.parseInt(request.getParameter("id"));
				mySQLConnector con=new mySQLConnector();
				con.readyPreparedStatement("select * from geomodel.fileinfo,geomodel.objectinfo where fileID=? and 目标=ObjectID");
				con.setInt(1, fileid);
				try {
					jsonArray=ResultSetConverter.convert(con.executeQuery());
				} catch (SQLException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					con.close();
				}
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				
				// Get the printwriter object from response to write the required json object to the output stream      
				PrintWriter out = response.getWriter();
				// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				out.print(jsonArray);
				out.flush();
				
			}
		}
		else if(param!=null&&param.equals("node")){
			if(request.getParameter("id")!=null&&!request.getParameter("id").equals(""))
			{
				int nodeid=Integer.parseInt(request.getParameter("id"));
				mySQLConnector con=new mySQLConnector();
				con.readyPreparedStatement("select * from objectmodelingsoft.nodeinfo,objectmodelingsoft.availableresource where nodeID=? and nodeID=FK_node");
				con.setInt(1, nodeid);
				try {
					jsonArray=ResultSetConverter.convert(con.executeQuery());
				} catch (SQLException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					con.close();
				}
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				
				// Get the printwriter object from response to write the required json object to the output stream      
				PrintWriter out = response.getWriter();
				// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				out.print(jsonArray);
				out.flush();
			}
		}
		else if(param!=null&&param.equals("resource"))
		{
			if(request.getParameter("id")!=null&&!request.getParameter("id").equals(""))
			{
				int nodeid=Integer.parseInt(request.getParameter("id"));
				mySQLConnector con=new mySQLConnector();
				con.readyPreparedStatement("select * from objectmodelingsoft.availableresource where FK_node=?");
				con.setInt(1, nodeid);
				try {
					jsonArray=ResultSetConverter.convert(con.executeQuery());
				} catch (SQLException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					con.close();
				}
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				
				// Get the printwriter object from response to write the required json object to the output stream      
				PrintWriter out = response.getWriter();
				// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				out.print(jsonArray);
				out.flush();
			}
		}
		else if(param!=null&&param.equals("object"))
		{
			if(request.getParameter("id")!=null&&!request.getParameter("id").equals(""))
			{
				int objectid=Integer.parseInt(request.getParameter("id"));
				mySQLConnector con=new mySQLConnector();
				con.readyPreparedStatement("select * from geomodel.objecttype_view,geomodel.objectinfo where objectID=? and 目标分类=ID");
				con.setInt(1, objectid);
				try {
					jsonArray=ResultSetConverter.convert(con.executeQuery());
				} catch (SQLException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					con.close();
				}
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				
				// Get the printwriter object from response to write the required json object to the output stream      
				PrintWriter out = response.getWriter();
				// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				out.print(jsonArray);
				out.flush();
			}
		}
	}

}
