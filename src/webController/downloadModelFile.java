package webController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.corba.se.spi.activation.Server;

import developConfig.Gobal;
import model.Util;


/**
 * Servlet implementation class downloadModelFile
 */
@WebServlet("/DownloadModelFile")
public class DownloadModelFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadModelFile() {
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
		
		String modelName=request.getParameter("modelName");
		String nodeIP=request.getParameter("nodeIP");
		HttpSession session=request.getSession();
		String username = (String) session.getAttribute("username");
		String name = (username==null||username.equals(""))? "server":username;
		
		if (modelName!=""&&!modelName.equals("")) {		
			
			System.out.println("服务器下载模型文件："+modelName);
			if(Util.ReceiveModelFile("1",nodeIP, Gobal.NODE_SOCKET_PORT,  modelName, name,Gobal.SERVER_RECV_CALLBACK,Gobal.SERVER_RECV_XML))
			{
				/*//下载至用户本地
				 //获得请求文件名  
		        System.out.println(modelName);  
		          
		        //设置文件MIME类型  
		        response.setContentType(getServletContext().getMimeType(modelName));  
		        //设置Content-Disposition  
		        response.setHeader("Content-Disposition", "attachment;filename="+modelName);  
		        //读取目标文件，通过response将目标文件写到客户端  
		        //获取目标文件的绝对路径  
		        String fullFileName = getServletContext().getRealPath("/download/" + modelName);  
		        System.out.println(fullFileName);  
		        //读取文件  
		        InputStream in = new FileInputStream(fullFileName);  
		        OutputStream out = response.getOutputStream();  
		          
		        //写文件  
		        int b;  
		        while((b=in.read())!= -1)  
		        {  
		            out.write(b);  
		        }  
		          
		        in.close();  
		        out.close();  */
				
				
				System.out.println("download model file success");				
				response.getWriter().append("success");

			}
			else{
				System.out.println("download Model File failed!");
				response.getWriter().append("failed"+","+"下载模型文件失败");
			}
		}
	}

}
