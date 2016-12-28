package webController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import developConfig.Gobal;

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
		 String type = request.getParameter("type");
		 
		 
		 if (type.equals("copy")) {
			  String modelName = request.getParameter("modelName");
			 if (modelName!=null) {
				 String modelZip = modelName.substring(0, modelName.lastIndexOf('.'))+".zip";
				 System.out.println(modelZip);
				 String from = Gobal.SERVER_RECV_CALLBACK+modelZip;
				 System.out.println("form=="+from);
				 String to = getServletContext().getRealPath("/Down")+"/"+modelZip;
				 System.out.println("to=="+to);
				
				 copyFile(from, to);
				 
			     System.out.println("复制完成！");
			    response.getWriter().append("success");
			}		
			
		}
		else if (type.equals("upload")) {
			//原因：上传文件是通过字节流进行传输的，表单的其它项是通过文字来传输的，所以表单一起传输时，就会出现获取到的值为null的结果
			response.setContentType("text/html;charset=UTF-8");
			RequestContext requestContext = new ServletRequestContext(request);
			String savePath = getServletContext().getRealPath("/Up");
			System.out.println(savePath);
			String fileName = processUpload(requestContext, response, savePath);
	        //System.out.println("http://localhost:8080"+savePath);
			System.out.println("文件上传至服务器结束");
			
			// 用户上传至服务器后，开始复制到socket指定运行目录
			 System.out.println(fileName); 
			 
			 String from = savePath+"/"+fileName;
			 System.out.println("form=="+from);
			 
			 String to = Gobal.OBJECT_ROOT_DIR+fileName;
			 System.out.println("to=="+to);
			 
			 copyFile(from, to);
			
		}
		
	    
	}
	/*
	 * 复制文件
	 */
	
	public void copyFile(String from,String to) {
		 File fromPath =new File(from);
		 while (!fromPath.exists()) {
			System.out.println("文件未准备好，请稍等...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 

		try {
	     	FileInputStream fis = new FileInputStream(from);
			BufferedInputStream bufis = new BufferedInputStream(fis);
			 
		     FileOutputStream fos = new FileOutputStream(to);
		     BufferedOutputStream bufos = new BufferedOutputStream(fos);
		 
		        int len = 0;
		        while ((len = bufis.read()) != -1) {
		            bufos.write(len);
		        } 
		        
		       bufis.close();
	           bufos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	 	       
	}
	
	 /*
     * 模型文件上传处理方法
     */
    public String processUpload(RequestContext request,
            HttpServletResponse response, String savePath) throws IOException {
        //测试期间数据                                                                                   
        //String owner="admin";
        String fullpath = "";
        String filename = "";
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 缓存大小为512KB
        factory.setSizeThreshold(524288);
         // 初始化上传控件
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 文件大小最大1B
        upload.setFileSizeMax(1024*1024*1024);
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
                System.out.println("上传文件的位置===="+fullpath);
                if (fullpath != null && !fullpath.trim().equals("")) {
                	System.out.println(fullpath.substring(fullpath.lastIndexOf('\\')+1));//测试
                    //filename = generateFileName(name);//测试期间按时间随机生成文件名
                    filename=fullpath.substring(fullpath.lastIndexOf('\\')+1);

                    File file = new File(savePath + "/" + filename);
                    if (file.exists()) {
                    	response.getWriter().write("<script>parent.callback('Warn!文件名称冲突,将覆盖原有文件！')</script>");
                    	System.out.println(filename+"文件名称冲突,将删除原有文件，上传最新的！");
                    	file.delete();                    	
					}
                 
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

							int size=(int)(item.getSize()/1024);
							if(size==0) size=1;//文件大小小于1k按1k计算
							logger.debug("admin upload file:" + filename + " success!");
							response.getWriter().write("<script>parent.callback('模型文件上传至服务器成功！','"+savePath.replace('\\', '/')+"',"+size+")</script>");
							
						   return filename;		
							
							
							
						} catch (Exception e) {
							e.printStackTrace();
						}        
                }
                else{
                	return "";
                }
            }
           else { //没有选中上传文件
			return "";
		  }
        }
		return "";
    }
}