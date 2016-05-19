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
 * Servlet implementation class GetSavedNodeList
 */
@WebServlet("/GetSavedNodeList")
public class GetSavedNodeList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSavedNodeList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    request.setCharacterEncoding("utf-8");
	    
		if(request.getParameter("type").equals("SavedNodeDB")){
			GirdfileList gf = new GirdfileList();
			List<String[]> list = gf.getSavedNodeDBList();
			if (list != null) {
				// System.out.println(list);
				request.setAttribute("SavedNodeDBList", list);
				try {
					request.getRequestDispatcher("/Webfront/ModelList.jsp").forward(request,
							response);
				} catch (ServletException e) {
					e.printStackTrace();
				}
				//System.out.println("success");
			} else {
				request.setAttribute("SavedNodeDBList", 0);
				try {
					request.getRequestDispatcher("/Webfront/ModelList.jsp").forward(request,
							response);
				} catch (ServletException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
