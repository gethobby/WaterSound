package webController;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GirdfileList;

/**
 * Servlet implementation class PrepareForNodeList
 */
@WebServlet("/PrepareForNodeList")
public class PrepareForNodeList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepareForNodeList() {
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
		GirdfileList gf=new GirdfileList();
		ArrayList<String[]> list=gf.getNodeMachineList();
		if(list!=null){
			//System.out.println(list.get(1));
			request.setAttribute("list", list);
			try {
				request.getRequestDispatcher("/softinfo.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
			System.out.println("success In loading nodelist");
		}
	}

}
