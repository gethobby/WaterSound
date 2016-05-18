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
 * Servlet implementation class GetType
 */
@WebServlet("/GetType")
public class GetType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetType() {
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
		
		String ObjectType1=request.getParameter("type1");
		if(ObjectType1!=null)
		{
			JSONArray jsonArray=null;
			mySQLConnector con=new mySQLConnector();
			String getObjectType2Sql="select 二级类别 as ObjectType2 from geomodel.objecttype_view where 一级类别=?";
			con.readyPreparedStatement(getObjectType2Sql);
			con.setString(1, ObjectType1);
			try {
				jsonArray=ResultSetConverter.convert(con.executeQuery());
			} catch (SQLException |JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonArray=null;
			}finally{
				con.close();
			}
			if(jsonArray!=null){
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
