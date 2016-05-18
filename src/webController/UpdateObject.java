package webController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GeoModel;
import model.UploadFile;

/**
 * Servlet implementation class UpdateObject
 */
@WebServlet("/UpdateObject")
public class UpdateObject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateObject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		if(request.getParameter("id")!=null){
			int objectID=Integer.parseInt(request.getParameter("id"));
			GeoModel object=new GeoModel();
			object.setObjectName(request.getParameter("ObjectName"));
			object.setObjectLOGO(request.getParameter("ObjectPicPath"));
			object.setObjectType1(request.getParameter("ObjectType1"));
			object.setObjectType2(request.getParameter("ObjectType2"));
			object.setObjectBelong(request.getParameter("ObjectBelong"));

			if(object.UpdateObjectInfo(objectID)) response.getWriter().append("success");
			else response.getWriter().append("failed");
		}
	}

}
