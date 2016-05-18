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
import model.SavedNodeSQLConnector;
import model.mySQLConnector;

/**
 * Servlet implementation class GetModellist
 */
@WebServlet("/GetModellist")
public class GetModellist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetModellist() {
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
		
			String nodeIP =request.getParameter("nodeIP"); 
			SavedNodeSQLConnector con=new SavedNodeSQLConnector(nodeIP);
			String filesummaryinfoSql="select fileID,模型文件,storepath,适用软件  "
					+ "from modelinfo.fileinfo ";;
			con.readyPreparedStatement(filesummaryinfoSql);
			JSONArray Modelfiles=null;
			try {
				Modelfiles=ResultSetConverter.convert(con.executeQuery());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				con.close();
			}
			if(Modelfiles!=null)
			{
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				
				// Get the printwriter object from response to write the required json object to the output stream      
				PrintWriter out = response.getWriter();
				// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				out.print(Modelfiles);
				out.flush();
			}
			
	}

}
