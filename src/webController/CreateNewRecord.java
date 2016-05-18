package webController;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import model.DoLoginCheck;
import model.GeoModel;
import model.NodeMachine;
import model.ObjectSoft;
import model.UploadFile;


/**
 * Servlet implementation class CreateNewRecord
 */
@WebServlet("/CreateNewRecord")
public class CreateNewRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewRecord() {
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
		
		if(recordtype!=null&&recordtype.equals("soft"))
		{
			ObjectSoft newsoft=new ObjectSoft();

			newsoft.setSoftName(request.getParameter("SoftName"));
			newsoft.setSoftNameEN(request.getParameter("SoftNameEN"));
			newsoft.setSoftVersion(request.getParameter("SoftVersion"));
			newsoft.setSoftUpdateTime(request.getParameter("SoftUpdateTime"));
			newsoft.setSoftType1(request.getParameter("SoftType1"));
			newsoft.setSoftType2(request.getParameter("SoftType2"));
			newsoft.setSoftLogo(request.getParameter("SoftLogo"));
			newsoft.setSoftSecretLevel(request.getParameter("SoftSecretLevel"));
			newsoft.setSoftDescription(request.getParameter("SoftDescription"));
			newsoft.setSoftFunctionDesc(request.getParameter("FunctionDesc"));
			newsoft.setSoftComputeExample(request.getParameter("ComputeExample"));
			newsoft.setSoftResultExample(request.getParameter("ResultExample"));
			newsoft.setContactPerson(request.getParameter("ContactPerson"));
			newsoft.setContactComp(request.getParameter("ContactComp"));
			newsoft.setContactAddress(request.getParameter("ContactAddress"));
			newsoft.setContactZip(request.getParameter("ContactZip"));
			newsoft.setContactTel(request.getParameter("ContactTel"));
			newsoft.setContactEmail(request.getParameter("ContactEmail"));

			if(newsoft.insertNewSoft()){ 
				System.out.println("create new soft success");
				response.getWriter().append("success");
			}
			else{ 
				System.out.println("create new soft failed!");
				response.getWriter().append("failed");
			}
			
			
		}
		if(recordtype!=null&&recordtype.equals("node"))
		{
			NodeMachine node=new NodeMachine();
			
			node.setIPAddress(request.getParameter("IPAddress"));
			node.setNodeMaxuseCount(Integer.parseInt(request.getParameter("MaxCount")));
			
			String[] tempaccount=request.getParameterValues("AccountNames[]");
			String[] temppsw=request.getParameterValues("AccountPSWs[]");
			String[] tempport=request.getParameterValues("ListenPorts[]");
			int i=tempaccount.length;
			int[] ports=new int[i];
			for(i=i-1;i>=0;i--)
			{
				ports[i]=Integer.parseInt(tempport[i]);
			}
			node.setAccounts(tempaccount);
			node.setPSWs(temppsw);
			node.setListenPorts(ports);

			
			String softlist="";
			String[] accesssofts=request.getParameterValues("AccessSoftNames[]");
			if(accesssofts!=null&&accesssofts.length>0)
			{

				int len=accesssofts.length;
				
				for(int j=len-1;j>=0;j--)
				{
					softlist+=accesssofts[j]+",";
				}
			}
			
			if(node.insertNewNode(softlist)){ 
				System.out.println("create new node success");
				response.getWriter().append("success");
			}
			else{ 
				System.out.println("create new node failed!");
				response.getWriter().append("failed");
			}
		}
		if(recordtype!=null&&recordtype.equals("modelfile"))
		{
			UploadFile file=new UploadFile();
			
			file.setTargetID(Integer.parseInt(request.getParameter("TargetObjectID")));
			file.setFileName(request.getParameter("FileName"));
			file.setSize(Integer.parseInt(request.getParameter("FileSize")));
			file.setFileDescription(request.getParameter("FileDescription"));
			file.setStorePath(request.getParameter("FileStorePath"));//模型文件存储的位置，后续根据要求修改
			file.setFileSecretLevel(request.getParameter("FileSecretLevel"));
			String softlist="";
			String[] accesssofts=request.getParameterValues("ObjectiveSoftNames[]");
			if(accesssofts!=null)
			{

				int len=accesssofts.length;
				
				for(int j=len-1;j>=0;j--)
				{
					softlist+=accesssofts[j]+",";
				}
			}
			file.setObjectiveSoft(softlist);
			file.setFileSource(request.getParameter("FileSource"));
			file.setContactPerson(request.getParameter("ContactPerson"));
			file.setContactComp(request.getParameter("ContactComp"));
			file.setContactAddress(request.getParameter("ContactAddress"));
			file.setContactZip(request.getParameter("ContactZip"));
			file.setContactTel(request.getParameter("ContactTel"));
			file.setContactEmail(request.getParameter("ContactEmail"));
			if(!file.checkInsert()){
				System.out.println("模型文件已经存在！");
				response.getWriter().append("failed"+","+"模型文件已存在！");
			}
			else{
				if(file.recordInsert()){ 
					System.out.println("upload Model File success");
					response.getWriter().append("success");
				}
				else{ 
					System.out.println("upload Model File failed!");
					response.getWriter().append("failed"+","+"Modelfile already exists！");
				}
			}
		}
		if(recordtype!=null&&recordtype.equals("user"))
		{
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String role=request.getParameter("role");
			DoLoginCheck newUser=null;
			if(role!=null&&!role.equals("")){
				newUser=new DoLoginCheck(username,password,role);
			}else{
				newUser=new DoLoginCheck(username,password);
			}
			if(newUser.InsertNewUser())
			{
				System.out.println("Create user success");
				response.getWriter().append("success");
			}
			else{
				System.out.println("upload Model File failed!");
				response.getWriter().append("failed"+","+"添加失败！用户已存在");
			}
		}
		if(recordtype!=null&&recordtype.equals("object")){
			GeoModel object=new GeoModel();
			object.setObjectName(request.getParameter("ObjectName"));
			object.setObjectLOGO(request.getParameter("ObjectLogo"));
			object.setObjectType1(request.getParameter("ObjectType1"));
			object.setObjectType2(request.getParameter("ObjectType2"));
			object.setObjectBelong(request.getParameter("ObjectBelong"));
			if(object.InsertNewObject())
			{
				System.out.println("Create object success");
				response.getWriter().append("success");
			}
			else{
				System.out.println("Create new object failed!");
				response.getWriter().append("failed"+","+"添加失败！目标已存在");
			}
		}
	}

}
