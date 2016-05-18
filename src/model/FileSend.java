package model;

import java.io.*;
import java.net.Socket;

public class FileSend {

	public static void main(String args[]) throws Exception {
		// 为了简单起见，所有的异常都直接往外抛
		String host = "127.0.0.1"; // 要连接的服务端IP地址
		int port = 6666; // 要连接的服务端对应的监听端口
		// 与服务端建立连接
		Socket client = new Socket(host, port);
		// 建立连接后就可以往服务端写数据了
		String message = "0";
		//OutputStream writer = client.getOutputStream();
		
		Writer writer = new OutputStreamWriter(client.getOutputStream());
		writer.write(message);
		writer.write("\0");
		writer.flush();
		// 写完以后进行读操作
		//DataInputStream reader = new DataInputStream(client.getInputStream());
		Reader reader = new InputStreamReader(client.getInputStream());
		char chars[] = new char[64];
		int len= 0;
		StringBuffer sb = new StringBuffer();
		String temp=null;
		if ((len = reader.read(chars)) != -1) {
			//temp = new String(chars,"GB2312").trim();
			temp=new String(chars);
			if (temp == "ready") {
				message = "test.txt";
				writer.write(message);
				writer.write("\0");
				writer.flush();
				message="feko";
				writer.write("\0");
				writer.write(message);
			    writer.flush();
				temp="";
			    if ((len = reader.read(chars)) != -1)
			    	System.out.println("order complete "+temp);
			}
		}
		writer.close();
		reader.close();
		client.close();
	}

}
