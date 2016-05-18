package webController;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GirdfileList;

/**
 * Servlet implementation class GetUserlist
 */
@WebServlet("/GetUserlist")
public class GetUserlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserlist() {
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
		GirdfileList gf = new GirdfileList();
		List list = gf.getUserList();
		if (list != null) {
			// System.out.println(list);
			request.setAttribute("userlist", list);
			try {
				request.getRequestDispatcher("/Webback/UserManage.jsp").forward(request,
						response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
			//testcode System.out.println("success");
		} else {
			request.setAttribute("userlist", 0);
			try {
				request.getRequestDispatcher("/Webback/UserManage.jsp").forward(request,
						response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}

}
