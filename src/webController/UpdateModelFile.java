package webController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UploadFile;

/**
 * Servlet implementation class UpdateModelFile
 */
@WebServlet("/UpdateModelFile")
public class UpdateModelFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateModelFile() {
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
		
		if(request.getParameter("id")!=null){
			int fileID=Integer.parseInt(request.getParameter("id"));
			UploadFile modelfile=new UploadFile();
			modelfile.setFileDescription(request.getParameter("FileDescription"));
			modelfile.setFileSecretLevel(request.getParameter("FileSecretLevel"));
			modelfile.setObjectiveSoft(request.getParameter("FileSelectedSoft"));
			modelfile.setFileSource(request.getParameter("FileSource"));
			modelfile.setContactPerson(request.getParameter("ContactPerson"));
			modelfile.setContactComp(request.getParameter("ContactComp"));
			modelfile.setContactAddress(request.getParameter("ContactAddress"));
			modelfile.setContactZip(request.getParameter("ContactZip"));
			modelfile.setContactTel(request.getParameter("ContactTel"));
			modelfile.setContactEmail(request.getParameter("ContactEmail"));
			if(modelfile.UpdateModelfileInfo(fileID)) response.getWriter().append("success");
			else response.getWriter().append("failed");
		}
	}

}
