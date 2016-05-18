package webController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.JSONObject;

import sun.security.timestamp.TSRequest;
import model.UploadFile;
import model.mySQLConnector;
import developConfig.*;
/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getRootLogger();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		RequestContext requestContext = new ServletRequestContext(request);
		String imgstr = Gobal.OBJECT_ROOT_DIR;
		System.out.println(request.getParameter("targetID"));
		if(request.getParameter("targetID")!=null&&!request.getParameter("targetID").equals(""))
		{imgstr = imgstr+"\\"+request.getParameter("targetID")+"\\";}
		else imgstr+="\\";
		String savePath = generateDir(imgstr);
		//int fileID=Integer.parseInt(request.getParameter("ID"));
		processUpload(requestContext, response, savePath);
        //System.out.println("http://localhost:8080"+imgstr);
		System.out.println("文件上传结束");
	}
    /*
     * 模型文件上传处理方法
     */
    public void processUpload(RequestContext request,
            HttpServletResponse response, String savePath) throws IOException {
        //测试期间数据                                                                                   
        //String owner="admin";
        String fullpath = "";
        String filename = "";
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 缓存大小为512KB
        factory.setSizeThreshold(524288);
        // 临时文件夹
        factory.setRepository(new File(savePath + "\\temp"));
        // 初始化上传控件
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 文件大小最大3MB
        upload.setFileSizeMax(3145728);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> fileList = null;
        try {
            fileList = upload.parseRequest(request);
        } 
        catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator<FileItem> it = fileList.iterator();
        
        while (it.hasNext()) {
            FileItem item = it.next();
            if (!item.isFormField()) {
                fullpath = item.getName();
                if (fullpath != null && !fullpath.trim().equals("")) {
                	System.out.println(fullpath.substring(fullpath.lastIndexOf('\\')+1));//测试
                    //filename = generateFileName(name);//测试期间按时间随机生成文件名
                    filename=fullpath.substring(fullpath.lastIndexOf('\\')+1);

                    File file = new File(savePath + "/" + filename);
                    
                    if(!file.exists()){
						System.out.println(filename + "文件上传开始……");

						try{
							InputStream is = item.getInputStream();
							FileOutputStream os = new FileOutputStream(file);
							byte[] content = new byte[1024];
							int length = 0;
							while ((length = is.read(content)) > 0) 
							{
								os.write(content, 0, length);
							}
							is.close();
							os.close();
							
							//更新模型文件记录的size字段
//							mySQLConnector con=new mySQLConnector();
//							String updatesizeSql="update geomodel.fileinfo set size=? where fileID=?";
//							con.readyPreparedStatement(updatesizeSql);
//							con.setInt(1, count/1024);
//							con.setInt(2,fileID);
//							con.executeUpdate();
//							con.close();
							int size=(int)(item.getSize()/1024);
							if(size==0) size=1;//文件大小小于1k按1k计算
							logger.debug("admin upload file:" + filename + " success!");
							response.getWriter().write("<script>parent.callback('模型文件上传成功！','"+savePath.replace('\\', '/')+"',"+size+")</script>");
						} catch (Exception e) {
							e.printStackTrace();
						}
                    }
                    else{
                    	logger.debug("admin upload file:"+filename+" failed!");

            			response.getWriter().write("<script>parent.callback('Failed!文件名称冲突,请重新命名！')</script>");
                    	System.out.println(filename+"文件名称冲突,请重新命名！");
                    }
                }
            }
        }
    }
    /*
     * 初始化文件存储路径
     */
    private String generateDir(String p) {
        String pathString = p;
        String tempString = p+"\\temp";
        File dirPath = new File(pathString);
        File dirTemp = new File(tempString);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        if (!dirTemp.exists()) {
            dirTemp.mkdirs();
        }
        return pathString;
    }
    /*
     * 生成文件名
     * 按时间生成文件名
     */
    private String generateFileName(String name) {
        long currentTime = System.currentTimeMillis();
        int i = (int) (Math.random() * 1000D + 1.0D);
        long result = currentTime + i;
        String filename = String.valueOf(result) + getFileExt(name);
        return filename;
    }
    /*
     * 获取文件格式
     */
    private String getFileExt(String name) {
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            return name.substring(pos);
        } else {
            return name;
        }
    }

}