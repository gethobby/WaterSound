package webController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DoDeleteByID;



/**
 * Servlet implementation class DeleteOldRecord
 */
@WebServlet(description = "delete users, soft and so on.", urlPatterns = { "/DeleteOldRecord" })
public class DeleteOldRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteOldRecord() {
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
		
		String recordtype=request.getParameter("type");
		
		if(recordtype!=null&&recordtype.equals("user"))
		{
			int deleteNum =0;
			String ids=request.getParameter("IDs");
		    if (ids!=null||ids!="") {
				deleteNum = DoDeleteByID.deleteUserByIds(ids);
			}
		    if(deleteNum>0)
		      response.getWriter().append(String.valueOf(deleteNum));
		}
		
		if(recordtype!=null&&recordtype.equals("soft"))
		{
			int deleteNum =0;
			String ids=request.getParameter("IDs");
		    if (ids!=null||ids!="") {
				deleteNum = DoDeleteByID.deleteSoftByIds(ids);
			}
		    if(deleteNum>0)
		      response.getWriter().append(String.valueOf(deleteNum));
		}
	
	}

}
