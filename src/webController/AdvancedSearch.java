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
 * Servlet implementation class AdvancedSearch
 */
@WebServlet("/AdvancedSearch")
public class AdvancedSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdvancedSearch() {
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
		
		String param=request.getParameter("searchtype");
		if(param!=null&&param.equals("object"))
		{
			mySQLConnector con=new mySQLConnector();
			String getFilteredobjectSql="select * from geomodel.objectinfo,geomodel.objecttype_view where ID=objectID and 目标名称 like ? ";
			String ObjectNameCondition=null;
			String ObjectType1Condition=null;
			String ObjectType2Condition=null;
			int coditionCount=0;
			
			if(request.getParameter("ObjectNameFilter")!=null&&!request.getParameter("ObjectNameFilter").equals("")){
				String[] ObjectNameFilters=request.getParameter("ObjectNameFilter").split(" ");
				if(ObjectNameFilters.length>0){ 
					ObjectNameCondition="%";
					for(String filter :ObjectNameFilters){
						ObjectNameCondition+=filter+"%";
					}
				}
			}
			
			if(request.getParameter("ObjectType1Filter")!=null&&!request.getParameter("ObjectType1Filter").equals("")){
				getFilteredobjectSql+="and 一级类别=? ";
				ObjectType1Condition=request.getParameter("ObjectType1Filter");
			}
			
			if(request.getParameter("ObjectType2Filter")!=null&&!request.getParameter("ObjectType2Filter").equals("")){
				getFilteredobjectSql+="and 二级类别=? ";
				ObjectType2Condition=request.getParameter("ObjectType2Filter");
			}
			
			con.readyPreparedStatement(getFilteredobjectSql);

			if(ObjectNameCondition!=null){con.setString(1, ObjectNameCondition);}
			else {con.setString(1, "%%");}
			coditionCount++;
			if(ObjectType1Condition!=null)
			{
				coditionCount++;
				con.setString(coditionCount, ObjectType1Condition);
				
			}
			if(ObjectType2Condition!=null)
			{
				coditionCount++;
				con.setString(coditionCount, ObjectType2Condition);
			}
			
			
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
		else if(param!=null&&param.equals("modelfile"))
		{
			mySQLConnector con=new mySQLConnector();
			String getFilteredmodelfileSql="select * from geomodel.fileinfo where 目标=? and 模型文件 like ? ";
			
			String FileNameCondition=null;
			String FileSourceCondition=null;
			String ContactCompCondition=null;
			int coditionCount=0;
			if(request.getParameter("FileNameFilter")!=null&&!request.getParameter("FileNameFilter").equals("")){
				String[] FileNameFilters=request.getParameter("FileNameFilter").split(" ");
				if(FileNameFilters.length>0){ 
					FileNameCondition="%";
					for(String filter :FileNameFilters){
						FileNameCondition+=filter+"%";
					}
				}
			}
			if(request.getParameter("FileSourceFilter")!=null&&!request.getParameter("FileSourceFilter").equals("")){
				getFilteredmodelfileSql+="and 来源=? ";
				FileSourceCondition=request.getParameter("FileSourceFilter");
			}
			
			if(request.getParameter("ContactCompFilter")!=null&&!request.getParameter("ContactCompFilter").equals("")){
				getFilteredmodelfileSql+="and 单位名称=? ";
				ContactCompCondition=request.getParameter("ContactCompFilter");
			}
			
			con.readyPreparedStatement(getFilteredmodelfileSql);

			con.setInt(1, Integer.parseInt(request.getParameter("ObjectID")));
			coditionCount++;
			if(FileNameCondition!=null){con.setString(2, FileNameCondition);}
			else {con.setString(2, "%%");}
			coditionCount++;
			if(FileSourceCondition!=null)
			{
				coditionCount++;
				con.setString(coditionCount, FileSourceCondition);
				
			}
			if(ContactCompCondition!=null)
			{
				coditionCount++;
				con.setString(coditionCount, ContactCompCondition);
			}
			
			
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
		else if(param!=null&&param.equals("soft"))
		{
			mySQLConnector con=new mySQLConnector();
			String getFilteredSoftSql="select softID,softname,LOGO,简介,一级类型,二级类型  "
					+ "from objectmodelingsoft.softinfo where softname like ? and 英文名称 like ? ";
			
			String SoftNameCondition=null;
			String SoftNameENCondition=null;
			String SoftType1Condition=null;
			String SoftType2Condition=null;
			int coditionCount=0;
			if(request.getParameter("SoftNameFilter")!=null&&!request.getParameter("SoftNameFilter").equals("")){
				String[] SoftNameFilters=request.getParameter("SoftNameFilter").split(" ");
				if(SoftNameFilters.length>0){ 
					SoftNameCondition="%";
					for(String filter :SoftNameFilters){
						SoftNameCondition+=filter+"%";
					}
				}
			}
			if(request.getParameter("SoftNameENFilter")!=null&&!request.getParameter("SoftNameENFilter").equals("")){
				String[] SoftNameENFilters=request.getParameter("SoftNameENFilter").split(" ");
				if(SoftNameENFilters.length>0){ 
					SoftNameENCondition="%";
					for(String filter :SoftNameENFilters){
						SoftNameENCondition+=filter+"%";
					}
				}
			}
			
			if(request.getParameter("SoftType1Filter")!=null&&!request.getParameter("SoftType1Filter").equals("")){
				getFilteredSoftSql+="and 一级类型=? ";
				SoftType1Condition=request.getParameter("SoftType1Filter");
				//System.out.println(request.getParameter("SoftType1Filter"));
			}
			
			if(request.getParameter("SoftType2Filter")!=null&&!request.getParameter("SoftType2Filter").equals("")){
				getFilteredSoftSql+="and 二级类型=? ";
				SoftType2Condition=request.getParameter("SoftType2Filter");
				//System.out.println(request.getParameter("SoftType1Filter"));
			}
			
			con.readyPreparedStatement(getFilteredSoftSql);

			if(SoftNameCondition!=null){con.setString(1, SoftNameCondition);}
			else {con.setString(1, "%%");}
			coditionCount++;
			if(SoftNameENCondition!=null){con.setString(2, SoftNameENCondition);}
			else {con.setString(2, "%%");}
			coditionCount++;
			if(SoftType1Condition!=null)
			{
				coditionCount++;
				con.setString(coditionCount, SoftType1Condition);
				
			}
			if(SoftType2Condition!=null)
			{
				coditionCount++;
				con.setString(coditionCount, SoftType2Condition);
			}
			
			
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
	}

}
