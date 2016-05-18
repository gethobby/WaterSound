<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.jspsmart.upload.SmartUpload"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.SimpleDateFormat;"%>
<%!
/*
 * 生成文件名
 * 按时间生成文件名
 */
private String generateFileName() {
    long currentTime = System.currentTimeMillis();
    int i = (int) (Math.random() * 1000D + 1.0D);
    long result = currentTime + i;
    String filename = String.valueOf(result);
    return filename;
}

%>
<%   

//此jsp处理添加新软件时软件的LOGO图片上传

    //新建一个SmartUpload对象   
    SmartUpload su = new SmartUpload();   
  
    //上传初始化   
    su.initialize(pageContext);   
  
    // 设定上传限制   
    //1.限制每个上传文件的最大长度。   
    su.setMaxFileSize(10000000);   
  
    //2.限制总上传数据的长度。   
    su.setTotalMaxFileSize(20000000);   
  
    //3.设定允许上传的文件（通过扩展名限制）,仅允许指定后缀文件。   
    su.setAllowedFilesList("bmp,png,jpeg,jpg,gif,ico"); 
    

       
    boolean sign = true;   
    String relativePath="";
    String errorMessage="";
    //4.设定禁止上传的文件（通过扩展名限制）,禁止上传带有exe,bat,jsp,htm,html扩展名的文件和没有扩展名的文件。   
    try {   
        su.setDeniedFilesList("exe,bat,jsp,htm,html");   
  
        //上传文件   
        su.upload();   
        if(su.getFiles().getFile(0).getFilePathName().equals(""))
        {
        	sign=false;
        	errorMessage="请选择需要上传的图片";
        }
        else if(su.getFiles().getFile(0).getSize()>15*1024*1024)
        {
        	sign=false;
        	errorMessage="图片不能超过15M";
        }else{
        	//将上传文件保存到指定目录   
            String UploadTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//将时间格式转换成符合Timestamp要求的格式.
        	String ext = su.getFiles().getFile(0).getFileExt();// 获取上传文件的后缀， 0 表示第一个上传的文件
        	String name = generateFileName();
        	System.out.println(this.getServletContext().getRealPath("/"));
        	String path = this.getServletContext().getRealPath("/")+"upload/image/"+name+ "." +ext;// 获取完整的保存路径//+UploadTime+"/"权限问题
        	File directory=new File(this.getServletContext().getRealPath("/")+"upload/image/");//+UploadTime权限问题
        	if(!directory.exists()  && !directory.isDirectory()){
        		directory.mkdir();
        	}
        	//System.out.print("============="+path);
        	relativePath="upload/image/"+name+ "." +ext;//图片存储的相对路径//+UploadTime+"/"权限问题
        	su.getFiles().getFile(0).saveAs(path);// 保存文件， saveAs 中的参数必须为完整的保存路径名
            //su.save("c:\\");  
        }

 
    } catch (Exception e) {  
        e.printStackTrace();  
        sign = false;  
        errorMessage="上传出现问题";
    }  
    if(sign==true)  
    { 
        out.println("<script>parent.callback('upload file success','"+relativePath+"')</script>");  
    }else  
    {  
        out.println("<script>parent.callback('"+errorMessage+"')</script>");   
    }   
%> 