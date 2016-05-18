package model;

import java.io.IOException;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class NodeSnmp {

	private Snmp snmp = null;

	private Address targetAddress = null;
	public NodeSnmp(){}
	public NodeSnmp(String targetIP)
	{
		StringBuffer temp=new StringBuffer("udp:");
		temp.append(targetIP).append("/161");
		String address=temp.toString();
		targetAddress = GenericAddress.parse(address);
	}

	public void initComm() throws IOException {

		// 设置Agent方的IP和端口
		targetAddress = GenericAddress.parse("udp:127.0.0.1/161");
		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		transport.listen();
	}
	public void initCommforNode() throws IOException
	{
		if(targetAddress!=null){
			// 设置Agent方的IP和端口 此处为节点端 //!!!此方法是临时的，以后要和上面的方法合并
			TransportMapping transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			transport.listen();
		}
		else{ this.initComm();}
	}


	private CommunityTarget sendPDU() throws IOException {
		// 设置 target
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		target.setAddress(targetAddress);
		// 通信不成功时的重试次数
		target.setRetries(2);
		// 超时时间
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version1);
		
		return target;
	}
	
	public double CpuUsage() throws IOException{
		CommunityTarget target=sendPDU();
	
		// 创建 PDU
		PDU pdu = new PDU();

		// for CPU
		//多核CPU搜索起点
		String endOID = "1.3.6.1.2.1.25.3.3.1.3";
		//搜索终点
		String startOID = ".1.3.6.1.2.1.25.3.3.1.2";
		pdu.add(new VariableBinding(new OID(startOID)));
		// MIB的访问方式
		pdu.setType(PDU.GETNEXT);
		// 向Agent发送PDU，并接收Response
		ResponseEvent respEvnt = snmp.send(pdu, target);
		
		int sum=0,count=0;
		while (respEvnt != null && respEvnt.getResponse() != null) {
			@SuppressWarnings("unchecked")
			Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.elementAt(i);
				String currentOID=recVB.getOid().toDottedString();
				int  temp=currentOID.compareTo(endOID);
				if(temp<=0){
					sum+=recVB.getVariable().toInt();
					count++;
					//testcode System.out.println(recVB.getOid() + " : " + recVB.getVariable());
					pdu.remove(0);
					pdu.add(new VariableBinding(recVB.getOid()));
					// MIB的访问方式
					pdu.setType(PDU.GETNEXT);
					respEvnt = snmp.send(pdu, target);
				}else {respEvnt=null;break;}
			}
		}
		
		return sum*1.0/count;

	}
	
	public double CpuUsageforNode() throws IOException{
		CommunityTarget target=sendPDU();
	
		// 创建 PDU
		PDU pdu = new PDU();

		// for CPU
		//多核CPU搜索起点
		String endOID = "1.3.6.1.2.1.25.3.3.1.3";
		//搜索终点
		String startOID = ".1.3.6.1.2.1.25.3.3.1.2";
		pdu.add(new VariableBinding(new OID(startOID)));
		// MIB的访问方式
		pdu.setType(PDU.GETNEXT);
		// 向Agent发送PDU，并接收Response
		ResponseEvent respEvnt = snmp.send(pdu, target);
		
		int sum=0,count=0;
		while (respEvnt != null && respEvnt.getResponse() != null) {
			@SuppressWarnings("unchecked")
			Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.elementAt(i);
				String currentOID=recVB.getOid().toDottedString();
				int  temp=currentOID.compareTo(endOID);
				if(temp<=0){
					sum+=recVB.getVariable().toInt();
					count++;
					//testcode System.out.println(recVB.getOid() + " : " + recVB.getVariable());
					pdu.remove(0);
					pdu.add(new VariableBinding(recVB.getOid()));
					// MIB的访问方式
					pdu.setType(PDU.GETNEXT);
					respEvnt = snmp.send(pdu, target);
				}else {respEvnt=null;break;}
			}
		}
		
		return sum*1.0/count;

	}
	
	@SuppressWarnings("unchecked")
	public double MemoryUsageforNode() throws IOException{
		
		CommunityTarget target=sendPDU();
		
		// 创建 PDU
		PDU pdu = new PDU();

		// for Memory
		String totalMemoryOID = ".1.3.6.1.2.1.25.2.2.0";
		pdu.add(new VariableBinding(new OID(totalMemoryOID)));
		// MIB的访问方式
		pdu.setType(PDU.GET);
		// 向Agent发送PDU，并接收Response
		ResponseEvent respEvnt = snmp.send(pdu, target);
		long memorySize=0;
		// 解析Response,获得内存大小
		Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
		memorySize=(recVBs.elementAt(0).getVariable()).toLong();
		//testcode System.out.println(memorySize);
		
//		pdu.remove(0);
//		String totalProcessesOID = ".1.3.6.1.2.1.25.1.6.0";
//		pdu.add(new VariableBinding(new OID(totalProcessesOID)));
//		pdu.setType(PDU.GET);
//		respEvnt = snmp.send(pdu, target);
//		int totalProcesses = 0;//获取进程总数
//		recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
//		totalProcesses=(recVBs.elementAt(0).getVariable()).toInt();
		
		pdu.remove(0);
		//进程搜索终点
		String endOID = "1.3.6.1.2.1.25.5.1.1.3";
		//搜索起点
		String startOID = ".1.3.6.1.2.1.25.5.1.1.2.1";
		pdu.add(new VariableBinding(new OID(startOID)));
		// MIB的访问方式
		pdu.setType(PDU.GET);
		// 向Agent发送PDU，并接收Response
		respEvnt = snmp.send(pdu, target);
		// 解析Response
		int count=1;
		long sum=0;
		while (respEvnt != null && respEvnt.getResponse() != null ) {
			recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.elementAt(i);
				String currentOID=recVB.getOid().toDottedString();
				int  temp=currentOID.compareTo(endOID);
				if(temp<0){
					sum+=recVB.getVariable().toLong();
					//testcode System.out.println(recVB.getOid() + " : " + recVB.getVariable());
					pdu.remove(0);
					pdu.add(new VariableBinding(recVB.getOid()));
					count++;
					// MIB的访问方式
					pdu.setType(PDU.GETNEXT);
					respEvnt = snmp.send(pdu, target);
				}else {respEvnt=null;break;}
			}
		}
		//testcode System.out.println(sum);
		return (sum*1.0/memorySize*100);
	}
	
	@SuppressWarnings("unchecked")
	public double MemoryUsage() throws IOException{
		
		CommunityTarget target=sendPDU();
		
		// 创建 PDU
		PDU pdu = new PDU();

		// for Memory
		String totalMemoryOID = ".1.3.6.1.2.1.25.2.2.0";
		pdu.add(new VariableBinding(new OID(totalMemoryOID)));
		// MIB的访问方式
		pdu.setType(PDU.GET);
		// 向Agent发送PDU，并接收Response
		ResponseEvent respEvnt = snmp.send(pdu, target);
		long memorySize=0;
		// 解析Response,获得内存大小
		Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
		memorySize=(recVBs.elementAt(0).getVariable()).toLong();
		//testcode System.out.println(memorySize);
		
//		pdu.remove(0);
//		String totalProcessesOID = ".1.3.6.1.2.1.25.1.6.0";
//		pdu.add(new VariableBinding(new OID(totalProcessesOID)));
//		pdu.setType(PDU.GET);
//		respEvnt = snmp.send(pdu, target);
//		int totalProcesses = 0;//获取进程总数
//		recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
//		totalProcesses=(recVBs.elementAt(0).getVariable()).toInt();
		
		pdu.remove(0);
		//进程搜索终点
		String endOID = "1.3.6.1.2.1.25.5.1.1.3";
		//搜索起点
		String startOID = ".1.3.6.1.2.1.25.5.1.1.2.1";
		pdu.add(new VariableBinding(new OID(startOID)));
		// MIB的访问方式
		pdu.setType(PDU.GET);
		// 向Agent发送PDU，并接收Response
		respEvnt = snmp.send(pdu, target);
		// 解析Response
		int count=1;
		long sum=0;
		while (respEvnt != null && respEvnt.getResponse() != null ) {
			recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.elementAt(i);
				String currentOID=recVB.getOid().toDottedString();
				int  temp=currentOID.compareTo(endOID);
				if(temp<0){
					sum+=recVB.getVariable().toLong();
					//testcode System.out.println(recVB.getOid() + " : " + recVB.getVariable());
					pdu.remove(0);
					pdu.add(new VariableBinding(recVB.getOid()));
					count++;
					// MIB的访问方式
					pdu.setType(PDU.GETNEXT);
					respEvnt = snmp.send(pdu, target);
				}else {respEvnt=null;break;}
			}
		}
		//testcode System.out.println(sum);
		return (sum*1.0/memorySize*100);
	}

//	public static void main(String[] args) {
//		 try {
//		 NodeSnmp util = new NodeSnmp();
//		 util.initComm();
//		 System.out.println(util.MemoryUsage()+","+util.CpuUsage());
//		 } catch (IOException e) {
//		 e.printStackTrace();
//		 }
//	}
}