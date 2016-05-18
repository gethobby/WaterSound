package webController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.NodeSnmp;

/**
 * Servlet implementation class ServerStatus
 */
@WebServlet("/ServerStatus")
public class ServerStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerStatus() {
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
		
		NodeSnmp ser=new NodeSnmp();
		double cpuUsage=-1,memoryUsage=-1;
		try{
			ser.initComm();
			cpuUsage=ser.CpuUsage();
			memoryUsage=ser.MemoryUsage();
		}catch(IOException e){
			e.printStackTrace();
		}
		if(cpuUsage>=0&&memoryUsage>=0){
			//System.out.println("success,"+cpuUsage+","+memoryUsage);
		response.getWriter().append("success,"+cpuUsage+","+memoryUsage);
		}else response.getWriter().append("failed");
	}

}
