package webController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.ObjectSoft;
import model.mySQLConnector;

/**
 * Servlet implementation class GetAvailableSoft
 */
@WebServlet("/GetAvailableSoft")
public class GetAvailableSoft extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAvailableSoft() {
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
		
		if(request.getParameter("fileID")!=null)
		{
			/*
			 * 此处为用户选中要操作的模型文件获取合适的目标特性软件
			 * step1.获取对应模型文件适用的目标特性软件
			 * step2.根据软件是否有空闲资源筛选软件
			 * step3.返回所有可用软件的软件编号、名称、logo图片
			 * */
			mySQLConnector con=new mySQLConnector();
			String getObjectivesoftsSql="select 适用软件 from geomodel.fileinfo where fileID=?";
			con.readyPreparedStatement(getObjectivesoftsSql);
			con.setInt(1, Integer.parseInt(request.getParameter("fileID")));
			ResultSet rs=con.executeQuery();
			String[] softs=null;
			try {
				if(rs.next()){
					softs=rs.getString(1).split(",");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(softs!=null&&softs.length>0)
			{
				int[] weights=new int[softs.length];
				String checkAvailablesoftSql="select availablecount "
						+ "from objectmodelingsoft.availableresource as a,objectmodelingsoft.availablenode_view as n "
						+ "where n.nodeID=a.FK_node "
						+ "and softname Like ? "//节点支持该软件
						+ "and occupied=0;";
				con.readyPreparedStatement(checkAvailablesoftSql);
				
				for(int i=0;i<softs.length;i++)
				{
					con.setString(1, "%"+softs[i]+"%");
					rs=con.executeQuery();
					try {
						if(rs!=null&&rs.next()){
							//软件的权重由可用资源数量决定
							weights[i]+=rs.getInt(1);
						}
						else weights[i]=0;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				sortSoftwithWeight(softs,weights);//根据权重对软件进行排序
				
				JSONArray jsonArray=new JSONArray();
				String getpopersoftdetailSql="select softID,softname,LOGO from objectmodelingsoft.softinfo where softname=?";
				con.readyPreparedStatement(getpopersoftdetailSql);
				for(int i=0;i<softs.length;i++)
				{
					if(weights[i]==0) break;
					con.setString(1, softs[i]);
					JSONObject temp=new JSONObject();
					ResultSet rssoft=con.executeQuery();
					try {
						if(rssoft.next()){
							ResultSetMetaData rsmd = rssoft.getMetaData();
							String column_name = rsmd.getColumnName(1);
							temp.put(column_name,rssoft.getInt(1));
							column_name = rsmd.getColumnName(2);
							temp.put(column_name,rssoft.getString(2));
							column_name = rsmd.getColumnName(3);
							temp.put(column_name,rssoft.getString(3));	
						}
						else{temp=null;}
					} catch (SQLException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(temp!=null)
					{
						jsonArray.put(temp);
					}
				}
				if(jsonArray.length()>0)
				{
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/json");
					
					// Get the printwriter object from response to write the required json object to the output stream      
					PrintWriter out = response.getWriter();
					// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
					out.print(jsonArray);
					out.flush();
				}
				con.close();
			}
			
		}	
	}
	
	/*
	 * 根据权重对软件进行排序
	 * 参数1.软件数组
	 * 参数2.权重数组
	 * */
	void sortSoftwithWeight(String[] s,int[] w)
	{
		for(int i=0;i<w.length;i++)
		{
			for(int j=w.length-1;j>i;j--)
			{
				if(w[j-1]<w[j])
				{
					String stemp=s[j-1];
					s[j-1]=s[j];
					s[j]=stemp;
					int wtemp=w[j-1];
					w[j-1]=w[j];
					w[j]=wtemp;
				}
			}
		}
	}
}

