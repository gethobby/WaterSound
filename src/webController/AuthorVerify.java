package webController;

import model.DoLoginCheck;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;




/**
 * Servlet implementation class AuthorVerify
 */
@WebServlet("/AuthorVerify")
public class AuthorVerify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getRootLogger();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorVerify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
//	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
//	}

	/**
	 * @see Servlet#destroy()
	 */
//	public void destroy() {
		// TODO Auto-generated method stub
//	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
	
		String frompage=request.getParameter("frompage");
		//testcode System.out.println(frompage);
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		DoLoginCheck ch=null;
		if(request.getParameter("backsystem")==null){
			ch=new DoLoginCheck(username,password);
		}
		else{
			ch=new DoLoginCheck(username,password,"admin");
		}
		
		if(ch.isSuccess())
		{
			HttpSession session=request.getSession();
			if(!session.isNew()){
				session.invalidate();
				session=request.getSession();
			}
			session.setAttribute("username", username);   
			session.setAttribute("tstartSession", new Date());
			session.setMaxInactiveInterval(5*60);
			if(request.getParameter("backsystem")==null){
				if(frompage==null||frompage=="")
					response.sendRedirect("/WaterSound/Webfront/ComputeMain.jsp");
				else response.sendRedirect("/WaterSound/Webfront/"+frompage);
				logger.debug(username+" Login in success!");
			}
			else{
				if(frompage==null||frompage=="")
					response.sendRedirect("/WaterSound/Webback/UserManage.jsp");
				else response.sendRedirect("/WaterSound/Webback/"+frompage);
				logger.debug(username+" Login in webback success!");
			}
		}
		else
		{
			request.setAttribute("loginerror", "true");
			if(request.getParameter("backsystem")==null){
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			else{
				request.getRequestDispatcher("login_back.jsp").forward(request, response);
			}
			//response.sendRedirect("/WaterSound/login.jsp?loginerror=true");
		}


	}


}
