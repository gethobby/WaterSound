package webController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ObjectSoft;

/**
 * Servlet implementation class UpdateSoftinfo
 */
@WebServlet("/UpdateSoftinfo")
public class UpdateSoftinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateSoftinfo() {
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
		
		String updateScope=request.getParameter("scope");
		if(request.getParameter("id")!=null){
			int softID=Integer.parseInt(request.getParameter("id"));
			ObjectSoft soft=new ObjectSoft();
			if(updateScope!=null&&updateScope.equals("basicinfo"))
			{
				soft.setSoftNameEN(request.getParameter("SoftNameEN"));
				soft.setSoftVersion(request.getParameter("SoftVersion"));
				soft.setSoftUpdateTime(request.getParameter("SoftUpdateTime"));
				soft.setSoftType1(request.getParameter("SoftType1"));
				soft.setSoftType2(request.getParameter("SoftType2"));
				soft.setSoftLogo(request.getParameter("SoftLogo"));
				soft.setSoftSecretLevel(request.getParameter("SoftSecretLevel"));
				soft.setSoftDescription(request.getParameter("SoftDescription"));
				if(soft.UpdateSoftBasicinfo(softID)) response.getWriter().append("success");
				else response.getWriter().append("failed");
			}
			else if(updateScope!=null&&updateScope.equals("softfunctiondesc"))
			{
				soft.setSoftFunctionDesc(request.getParameter("FunctionDesc"));
				
				if(soft.UpdateSoftFuncDesc(softID)) response.getWriter().append("success");
				else response.getWriter().append("failed");
				
			}
			else if(updateScope!=null&&updateScope.equals("softcomputeexample"))
			{
				soft.setSoftComputeExample(request.getParameter("ComputeExample"));
				
				if(soft.UpdateSoftComputeExample(softID)) response.getWriter().append("success");
				else response.getWriter().append("failed");
			}
			else if(updateScope!=null&&updateScope.equals("softresultexample"))
			{
				soft.setSoftResultExample(request.getParameter("ResultExample"));
				
				if(soft.UpdateSoftResultExample(softID)) response.getWriter().append("success");
				else response.getWriter().append("failed");
			}
			else if(updateScope!=null&&updateScope.equals("contactinfo"))
			{
				soft.setContactPerson(request.getParameter("ContactPerson"));
				soft.setContactComp(request.getParameter("ContactComp"));
				soft.setContactAddress(request.getParameter("ContactAddress"));
				soft.setContactZip(request.getParameter("ContactZip"));
				soft.setContactTel(request.getParameter("ContactTel"));
				soft.setContactEmail(request.getParameter("ContactEmail"));
				
				if(soft.UpdateSoftContactinfo(softID)) response.getWriter().append("success");
				else response.getWriter().append("failed");
			}
		}

		
	}

}
