function checkInput()
{
	var verified=true;
	//检查用户名
	if(!document.getElementById("UserName").value)
	{
		$("#UserName").next().css("display","inline");
		verified=false;
	}
	else{
		$("#UserName").next().css("display","none");
	}
	
	//检查密码
	if(!document.getElementById("PSW").value)
	{
		$("#PSW").next().css("display","inline");
		verified=false;
	}
	else{
		$("#PSW").next().css("display","none");
	}
	//检查密码重复
	if(!document.getElementById("PSWRepeat").value&&document.getElementById("PSW").value==document.getElementById("PSWRepeat").value)
	{
		$("#PSWRepeat").next().css("display","inline");
		verified=false;
	}
	else{
		$("#PSWRepeat").next().css("display","none");
	}
	//检查角色
	if(!document.getElementById("Role").value)
	{
		$("#Role").next().css("display","inline");
		verified=false;
	}
	else{
		$("#Role").next().css("display","none");
	}
	
	return verified;
	
}
function insertNewUserRecord()
{
	var jsonObject=new Object();
	if(checkInput())
	{
		jsonObject.username=document.getElementById("UserName").value.trim();
		jsonObject.password=document.getElementById("PSW").value.trim();
		jsonObject.role=document.getElementById("Role").value.trim();
		
		$.ajax({   
		     type: "POST",   
		     url: "/WaterSound/CreateNewRecord?type=user",   
		     data:jsonObject,
		     success:function(str_response){
		    	 if("success"==str_response)
		    	 {
		    		 document.getElementById("UserName").value="";
		    		 document.getElementById("PSW").value="";
		    		 document.getElementById("Role").value="";
		    		 document.getElementById("PSWRepeat").value="";
		    		 alert("创建成功！");
		    	 }
		    	 else{alert("创建失败！用户已存在");}
		     }
		});
	}
} 

/** 删除用户 **/

var deleteBnt = document.getElementById("deleteBnt");
deleteBnt.onclick = deleteOldUserRecord;

function deleteOldUserRecord()
{
	   var IDs = {};
	   var id_str = "";
	   var checkNum= 0 ; // 选中的记录数
	  // 通过以下方式找到table对象，在获取tr，td。然后获取td的html内容  
		var table = document.getElementById("myTable");//获取第一个表格  
		var child = table.getElementsByTagName("tr");//获取行数组  		  ;  
		for(var i = 1; i<child.length;i++)    // 跳过表的标题栏
		 {		    		 
			 var col =  child[i].getElementsByTagName("td"); // 获取某行的列数组
			 var check =  child[i].getElementsByTagName("input"); // 获取该行中input checkbox
			  if(check[0].checked)
			  {
				 var userid = check[0].nextSibling.nodeValue;
				 id_str = id_str.concat(userid+",");
				 checkNum++;
				// alert("插入用户ID："+userid);
			  }
			
		 }
		 if(checkNum==0)
		 {
		   alert("请选择要删除的记录");
		 }
		 else {
			
			 IDs.IDs = id_str;
			// alert(id_str);  	
			 if(confirm("确认要删除选中的记录")){ 
				 
				 $.ajax({   
					 type: "POST",   
					 url: "/WaterSound/DeleteOldRecord?type=user",   
					 data:IDs,
					 success:function(str_response){
						// alert(str_response);
						 var deleteNum = parseInt(str_response);
						 if(deleteNum>0)
						 {		    		
							 alert("成功删除"+deleteNum+"个用户记录");
						 }
						 else{alert("删除失败！");}
					 }
				 });
			 } 
		}
		 
			
}

