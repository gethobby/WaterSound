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
import model.*;

/**
 * Servlet implementation class Getplatformlog
 */
@WebServlet("/Getplatformlog")
public class Getplatformlog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Getplatformlog() {
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
		//主要功能：获取后台平台访问日志信息
		/*
		 *Parameter: 
		 */
		//此处接收的参数都是以查询字符串的形式发送的，所以第一步解析url中logname参数
		String logname=request.getParameter("logname");
		//testcode System.out.print(logname);
		Loginfo li=new Loginfo();
		JSONArray jsonArray=new JSONArray();
		if(logname!=null){
			if(logname.equals("userlog"))
			{
				String username=request.getParameter("username");
				//System.out.print(username);
				//if(username==null);
				try{
					jsonArray=ResultSetConverter.convert(li.getUserstayinterval(username));
					//System.out.print(jsonArray);
				}catch(Exception e){
					e.printStackTrace();
				}
				response.setContentType("application/json");
				// Get the printwriter object from response to write the required json object to the output stream      
				PrintWriter out = response.getWriter();
				// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				out.print(jsonArray);
				out.flush();
			}
			if(logname.equals("platformlog"))
			{
				
				try{
					jsonArray=ResultSetConverter.convert(li.getUserActivelog());
				}catch(Exception e){
					e.printStackTrace();
				}
				response.setContentType("application/json");
				// Get the printwriter object from response to write the required json object to the output stream      
				PrintWriter out = response.getWriter();
				// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				out.print(jsonArray);
				out.flush();
			}
			if(logname.equals("nodelog"))
			{
				try{
					jsonArray=ResultSetConverter.convert(li.getAvgNodeusetime());
				}catch(Exception e){
					e.printStackTrace();
				}
				response.setContentType("application/json");
				// Get the printwriter object from response to write the required json object to the output stream      
				PrintWriter out = response.getWriter();
				// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				out.print(jsonArray);
				out.flush();
			}
			if(logname.equals("datastatistic"))
			{
				String username=request.getParameter("username");
				//testcode System.out.print(username);
				if(username==null)
				{
					int totalmodelDS=li.getPlatformModelDS();
					int totalresultDS=li.getPlatformResultDS();
					PrintWriter out = response.getWriter();
					out.print(""+totalmodelDS+','+totalresultDS);
					
				}
				if(username!=null&&username.equals(""))
				{}
				if(username!=null&&!username.equals(""))
				{
					int usermodelDS=li.getUserModelDS(username);
					int userresultDS=li.getUserResultDS(username);
					PrintWriter out = response.getWriter();
					out.print(""+usermodelDS+','+userresultDS);
				}
			}

		}
	}

}
