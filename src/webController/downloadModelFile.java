package webController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
