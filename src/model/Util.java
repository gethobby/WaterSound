package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import developConfig.Gobal;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Util {
	
	public static void Unzip(String fromPath,String toPath) {
		  // TODO Auto-generated method stub  
      long startTime=System.currentTimeMillis();  
      try {  
          ZipInputStream Zin=new ZipInputStream(new FileInputStream(  
                  fromPath));//输入源zip路径  
          BufferedInputStream Bin=new BufferedInputStream(Zin);  
          String Parent=toPath; //输出路径（文件夹目录）  
          File Fout=null;  
          ZipEntry entry;  
          try {  
              while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){  
                  Fout=new File(Parent,entry.getName());  
                  if(!Fout.exists()){  
                      (new File(Fout.getParent())).mkdirs();  
                  }  
                  FileOutputStream out=new FileOutputStream(Fout);  
                  BufferedOutputStream Bout=new BufferedOutputStream(out);  
                  int b;  
                  while((b=Bin.read())!=-1){  
                      Bout.write(b);  
                  }  
                  Bout.close();  
                  out.close();  
                  System.out.println(Fout+"解压成功");      
              }  
              Bin.close();  
              Zin.close();  
          } catch (IOException e) {  
              // TODO Auto-generated catch block  
              e.printStackTrace();  
          }  
      } catch (FileNotFoundException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
      }  
      long endTime=System.currentTimeMillis();  
      System.out.println("耗费时间： "+(endTime-startTime)+" ms");
	}
	
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	public static Date formatString(String str,String format) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}

	public static JSONArray formatRsToJsonArray(ResultSet rs)throws Exception{
		ResultSetMetaData md=rs.getMetaData();
		int num=md.getColumnCount();
		JSONArray array=new JSONArray();
		while(rs.next()){
			JSONObject mapOfColValues=new JSONObject();
			for(int i=1;i<=num;i++){
				Object o=rs.getObject(i);
				if(o instanceof Date){
					mapOfColValues.put(md.getColumnName(i), Util.formatDate((Date)o, "yyyy-MM-dd"));
				}else{
					mapOfColValues.put(md.getColumnName(i), rs.getObject(i));					
				}
			}
			array.add(mapOfColValues);
		}
		return array;
	}
	
	public static void write(HttpServletResponse response,Object object)throws Exception{
		response.setContentType("text/html;charset=utf-8");

		PrintWriter out=response.getWriter();
		out.println(object.toString());
		out.flush();
		out.close();
				
	}
	
	
	
	/////////////////////////////
	/**
	 * 
	 * @param cmd 服务器发出的命令：0表示上传，表示下载
	 * @param IP  节点机的IP(因为节点机socket是通信的S端)
	 * @param port 节点机socket的Port(config.txt确定)
	 * @param filepath 要上传的文件完整路径
	 * @param user  当前服务器上登录的用户
	 * @return
	 */
	 public static boolean SendModelFile(String cmd,String IP,int port,String filepath,String user) {
		    Runtime rn = Runtime.getRuntime();
			Process p = null;
			String[] commons = new String[6];
			commons[0]=Gobal.NODE_COMMUNICATE_APP;
			commons[1]=cmd;//"0:上传";
			commons[2]=IP;//"127.0.0.1";
			commons[3]=Integer.toString(port);//"6000";
			
			if(filepath==null) commons[4]="NULL";
			else commons[4]=filepath;//"D:\\file\\te stSN.txt";
			commons[5]=user;
			
			try {
				p = rn.exec(commons);
				System.out.println("connect node exec!");
				int exitVal = p.waitFor();
				//System.out.println("Process exitValue: " + exitVal);
				if(exitVal==0) return true;
			} catch (Exception e) {
				System.out.println("Error exec when connecting node!");
				return false;
			}

			return true;
	   }
	  /**
		 * 
		 * @param cmd 服务器发出的命令：0表示上传，1表示下载
		 * @param IP  节点机的IP(因为节点机socket是通信C端)
		 * @param port 服务器端的socket的Port(为了方便，统一由config.txt确定)
		 * @param filepath 要从节点机中下载下来的文件(包含后缀)
		 * @param user  当前服务器上登录的用户
		 * @param callbackpath 下载的文件保存到服务器上的存储路径
		 * @param callbackrecord  下载过程的记录文件(与callbackpath参数同一路经)
		 * @return 返回下载是否成功
		 */
	   public static boolean  ReceiveModelFile(String cmd,String IP,int port,String filepath,String user, String callbackpath, String callbackrecord) {
		    Runtime rn = Runtime.getRuntime();
			Process p = null;
			String[] commons = new String[8];
			commons[0]=Gobal.NODE_COMMUNICATE_APP;
			commons[1]=cmd;//"1:下载";
			commons[2]=IP;//"127.0.0.1";
			commons[3]=Integer.toString(port);//"6666";
			
			if(filepath==null) commons[4]="NULL";
			else commons[4]=filepath;//"D:\\file\\testSN.txt";
			commons[5]=user;
			commons[6]=callbackpath;
			commons[7]=callbackrecord;
			
			try {
				p = rn.exec(commons);
				System.out.println("connect node exec!");
				int exitVal = p.waitFor();
				//System.out.println("Process exitValue: " + exitVal);
				if(exitVal==0) return true;
			} catch (Exception e) {
				System.out.println("Error exec when connecting node!");
				return false;
			}

			return true;
	  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("上传文件");
		Util.SendModelFile("0","223.3.79.4", Gobal.NODE_SOCKET_PORT,  Gobal.OBJECT_ROOT_DIR+"watersoundzhangfeng.stl", "zhang");
		System.out.println("上传成功");
	   /* System.out.println("下载文件");
	    // 端口号后面的模型文件，只需要文件名即可，因为节点机上的工作空间统一了
		Util.ReceiveModelFile("1","223.3.79.4", Gobal.NODE_SOCKET_PORT,  "watersound22.stl", "shanshan3344555","D:\\WaterSound\\ModelCallBack\\","Result.xml");
		System.out.println("下载成功");*/
	}

}
