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
 * @author Reena
 *
 */
public interface Gobal {
	//存放MySQL数据库连接所需信息
	String QUERY_DB_URL="jdbc:mysql://localhost:3306";
	String QUERY_DB_USER="root";
	String QUERY_DB_PASSWORD="123456";
	
	//存放与节点机交流的C++软件的路径
	String NODE_COMMUNICATE_APP="D:\\WaterSound\\WaterSound_Deploy\\SavedServerSide.exe";
	String RESULT_RECEIVE_APP="D:\\WaterSound\\WaterSound_Deploy\\SavedResultServer.exe";
	
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
	
	//所有模型文件存放路径
	String OBJECT_ROOT_DIR="D:\\WaterSound\\ObjectModelfile\\";
	//所有图片存放路径
	String PICTURE_ROOT_DIR="D:\\WaterSound\\Pictures";
	
	//节点机统一存储模型文件的位置 
	String NODE_OBJECT_DIR = "D:\\WaterSound\\";
	
}
