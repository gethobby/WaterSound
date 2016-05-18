package webController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import model.GirdfileList;
import model.ResultSetConverter;
import model.mySQLConnector;

/**
 * Servlet implementation class GetSoftList
 */
@WebServlet("/GetSoftList")
public class GetSoftList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSoftList() {
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
		
		String param=request.getParameter("softinfotype");
		if(param!=null&&param.equals("summary")){
			mySQLConnector con=new mySQLConnector();
			String getSummarySoftinfoSql="select softID,softname from objectmodelingsoft.softinfo";
			con.readyPreparedStatement(getSummarySoftinfoSql);
			JSONArray jsonArray=new JSONArray();
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
		else{
			
			GirdfileList gf = new GirdfileList();
			List list = gf.getSoftList();
			if (list != null) {
				// System.out.println(list);
				request.setAttribute("softlist", list);
			}
			else {
				request.setAttribute("softlist", 0);
			}
			try {
				if (request.getAttribute("newfile") != null) {
					request.removeAttribute("newfile");
					request.getRequestDispatcher("/Webfront/fileUpload.jsp")
							.forward(request, response);
					} 
				else if(request.getAttribute("description") != null){
					request.removeAttribute("description");
					request.getRequestDispatcher("/Webfront/ComputeMain.jsp")
							.forward(request, response);
				}
				else request.getRequestDispatcher("/Webback/SoftManage.jsp")
							.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}

		}

	}

}
