package webController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.NodeMachine;

/**
 * Servlet implementation class NodeResourceManage
 */
@WebServlet("/NodeResourceManage")
public class NodeResourceManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NodeResourceManage() {
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
		
		String param=request.getParameter("type");
		if(param!=null&&param.equals("release"))
		{
			NodeMachine node=new NodeMachine();
			if(node.releaseNode(Integer.parseInt(request.getParameter("resourceId"))))
				response.getWriter().append("success");
			else response.getWriter().append("Release node failed!");
		}
		else if(param!=null&&param.equals("lock"))
		{
			NodeMachine node=new NodeMachine();
			if(node.lockNodeResource(Integer.parseInt(request.getParameter("resourceId"))))
				response.getWriter().append("success");
			else response.getWriter().append("Release node failed!");
		}
	}

}
