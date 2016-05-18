package webController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.NodeMachine;

/**
 * Servlet implementation class ReleasenodeServlet
 */
@WebServlet("/ReleasenodeServlet")
public class ReleasenodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReleasenodeServlet() {
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
		
		//String toolname=request.getParameter("toolname");
		String nodeip=request.getParameter("nodeip");
		String nodeaccount=request.getParameter("account");
		System.out.println("release^^^^^^^^^"+nodeip);
		NodeMachine node=new NodeMachine();
		if(node.releaseNode(nodeip,nodeaccount))
			response.getWriter().append("success");
		else response.getWriter().append("Release node failed!");
	}

}
