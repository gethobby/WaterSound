//$(document).ready(function(){
//  $("#feko").click(applyNode("feko",document.getElementById("editObjModel").value));
//  $("#cst").click(applyNode("cst",document.getElementById("editObjModel").value));
//  });
//用applyNode函数的页面必须有notify的提醒页面，还有暂存申请的节点信息的hidden型input元素
function applyNode(username,toolname,objmodelID)
{
	function isIe(){
	       return ("ActiveXObject" in window);//判断是否为IE，兼容IE11
	}
	   
	if(!isIe()){
		modelWindowShow();
		var note=document.getElementById("notify");
		note.style.display='block';
		note.getElementsByTagName("div")[0].innerHTML="节点使用仅支持IE浏览器，请更换浏览器！";
		return;
	}
	else if(username==null)
	{ 
		//alert("登录超时！请刷新页面！");
		modelWindowShow();
		var note=document.getElementById("notify");
		note.style.display='block';
		note.getElementsByTagName("div")[0].innerHTML="登录超时！请刷新页面！";
		return;
	}
	else{
		modelWindowShow();
		note=document.getElementById("notify");
		note.style.display="block";
		note.getElementsByTagName("div")[0].innerHTML="节点分配中，请稍等！";
		
		var applyurl="/WaterSound/ApplynodeServlet";
		if(objmodelID){
			applyurl+=("?modelID="+objmodelID.toString());
		}
		applyurl+="&tid="+Math.random();
		var htmlobj=$.ajax({
			type: "POST",
			url:applyurl,
			async:false,
			data:{"toolname":toolname},
			success:function(data){
				var param=data.split(",");
				if("success"==param[0])
				{
					note.style.display="none";
					note.getElementsByTagName("div")[0].innerHTML="";
					modelWindowClose();
					if(objmodelID){document.getElementById("saveforModel").value=objmodelID;}//这里存储的是模型文件的ID
					else{document.getElementById("saveforModel").value="";}
					if(param.length==6){
						opennode(param[1],param[2],param[3],param[4],toolname,username,objmodelID);
					}
					if(param.length==5)
					{
						opennode(param[1],param[2],param[3],param[4],toolname,username);
					}
				}
				else {
					//alert(htmlobj.responseText);
					modelWindowShow();
					note=document.getElementById("notify");
					note.style.display='block';
					note.getElementsByTagName("div")[0].innerHTML="<strong>"+toolname+"</strong> 当前没有该工具资源，请等待或者尝试其他工具！";
				}
			}
		});
		
	}


		//alert("Node Apply success!"+username);//testcode
		function opennode(ip,user,psw,port,tool,loginuser,model){
			document.getElementById("saveforIPAddress").value=ip;
			document.getElementById("saveforAccount").value=user;
			document.getElementById("saveforPassword").value=psw;
			document.getElementById("saveforPort").value=port;
			document.getElementById("saveforSoft").value=tool;
			document.getElementById("saveforLoginuser").value=loginuser;
			
			var newnodeurl="/WaterSound/Webfront/Tool.html";
			$.ajax({   
			     type: "POST",   
			     url: newnodeurl,   
			     data: [ip,user,psw,port],   
			     success: function(str_response) {   
			        var obj = window.open("soft.html"); 
			        obj.document.write(str_response);
			        
			        //obj.window.onload=function(){BtnConnect();}
//			        obj.window.onbeforeunload=function(){
//			        	var dontleave="请确定已经关闭软件后再关闭！";
//			        	
//			        	var relseaeServletUrl="/WaterSound/ReleasenodeServlet?nodeip="+ip+"&account="+user+
//			        	"&user="+username+"&starttime="+new Date()+"&tid="+Math.random();
//			        	var subhtmlobj=$.ajax({url:relseaeServletUrl,async:false});
//				        
//			        	obj.window.onbeforeunload=null;
//			        	return dontleave;
//			        };
			        
			     }   
			 });  
			
		}
		

	
}
