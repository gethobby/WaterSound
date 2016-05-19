/** 
* <p> Title: Global </p> 
* <p> Description: 定义系统中使用的常量 </p> 
* <p> Copyright: Copyright (c) 2004 </p> 
* <p> Company: seu </p> 
* @author Reena 
* @version 1.0,2004/1/12 
*/ 

package developConfig;

/**
 * @author ZF
 *
 */
public interface Gobal {
	//存放MySQL数据库连接所需信息
	String DEFUALT_QUERY_DB_URL = "jdbc:mysql://localhost:3306";
	//连接MySQL数据库协议
	String QUERY_DB_PREFIX="jdbc:mysql://"; 
	String QUERY_DB_PORT = ":3306";
	String QUERY_DB_USER="root";
	String QUERY_DB_PASSWORD="123456";
	
	/*以下若干行配置为服务器配置*/
	//存放与节点机交流的C++软件的路径
	String NODE_COMMUNICATE_APP="D:\\WaterSound\\WaterSound_Deploy\\SavedServerSide.exe";
	String RESULT_RECEIVE_APP="D:\\WaterSound\\WaterSound_Deploy\\SavedResultServer.exe";
	//存放从节点机接受的模型文件以及操作记录路径
	String SERVER_RECV_CALLBACK = "D:\\WaterSound\\ModelCallBack\\" ;
	String SERVER_RECV_XML = "Result.xml";
	//所有模型文件存放路径
	String OBJECT_ROOT_DIR="D:\\WaterSound\\ObjectModelfile\\";
	//所有图片存放路径
	String PICTURE_ROOT_DIR="D:\\WaterSound\\Pictures";
	
	//存放snmp所需数据信息
	String SERVER_SNMP_IP="udp:127.0.0.1/161";
	String SERVER_SNMP_MEMORY=".1.3.6.1.2.1.25.2.2.0";
	String SERVER_SNMP_CPU_START=".1.3.6.1.2.1.25.3.3.1.2";
	String SERVER_SNMP_CPU_END="1.3.6.1.2.1.25.3.3.1.3";
	String SERVER_SNMP_PROCESS_START=".1.3.6.1.2.1.25.5.1.1.2.1";
	String SERVER_SNMP_PROCESS_END="1.3.6.1.2.1.25.5.1.1.3";

	String NODE_SNMP_MEMORY=".1.3.6.1.2.1.25.2.2.0";
	String NODE_SNMP_CPU_START=".1.3.6.1.2.1.25.3.3.1.2";
	String NODE_SNMP_CPU_END="1.3.6.1.2.1.25.3.3.1.3";
	String NODE_SNMP_PROCESS_START=".1.3.6.1.2.1.25.5.1.1.2.1";
	String NODE_SNMP_PROCESS_END="1.3.6.1.2.1.25.5.1.1.3";
	

	
	//存储节点机统一存储模型文件的位置 
	String NODE_OBJECT_DIR = "D:\\WaterSound\\";
	int NODE_SOCKET_PORT = 6000;
	
	//软件节点机工作空间 
	String SOFT_WORK_DIR = "D:\\WaterSound\\";
	int SOFT_SOCKET_PORT = 5000;
	
}
