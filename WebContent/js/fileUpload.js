function checkInput()
{
	var verified=true;
	//检查目标名称
	if(document.getElementById("TargetObject").value) //此处需要用ajax确认是否存在该目标
	{}
	//检查模型文件
	if(!document.getElementById("FileToUpload").value)
	{
		$("#FileToUpload").next().css("display","inline");
		//此处还应该检查一下模型文件是否选择正确，比如检查后缀名之类的
		verified=false;
	}
	else
	{
		$("#FileToUpload").next().css("display","none");
	}
//	//检查密级
//	if(!document.getElementById("FileSecretLevel").value)
//	{
//		$("#FileSecretLevel").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#FileSecretLevel").next().css("display","none");
//	}
//	//检查来源
//	if(!document.getElementById("FileSource").value)
//	{
//		$("#FileSource").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#FileSource").next().css("display","none");
//	}
//	//检查联系人
//	if(!document.getElementById("ContactPerson").value)
//	{
//		$("#ContactPerson").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#ContactPerson").next().css("display","none");
//	}
//	//检查联系公司
//	if(!document.getElementById("ContactComp").value)
//	{
//		$("#ContactComp").parent().next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#ContactComp").next().css("display","none");
//	}
//	//检查通信地址
//	if(!document.getElementById("ContactAddress").value)
//	{
//		$("#ContactAddress").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#ContactAddress").next().css("display","none");
//	}
//	//检查邮编
//	if(!document.getElementById("ContactZip").value)
//	{
//		$("#ContactZip").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#ContactZip").next().css("display","none");
//	}
//	//检查联系电话
//	if(!document.getElementById("ContactTel").value)
//	{
//		$("#ContactTel").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#ContactTel").next().css("display","none");
//	}
//	//检查电子邮件
//	if(!document.getElementById("ContactEmail").value)
//	{
//		$("#ContactEmail").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#ContactEmail").next().css("display","none");
//	}
	
	return verified;
	
}
function uploadModelFile()
{
	var temp=document.form;
	var jsonObject=new Object();
	if(checkInput()){
		jsonObject.TargetObjectID=document.getElementById("TargetObjectID").value;
		var filefullpath=document.getElementById("file").value;
		jsonObject.FileName=filefullpath.substring(filefullpath.lastIndexOf("\\")+1);
		jsonObject.FileSize=document.getElementById("FileSize").value;
		jsonObject.FileStorePath=document.getElementById("FileToUpload").value;
		jsonObject.FileDescription=document.getElementById("FileDescription").value;
		jsonObject.FileSecretLevel=document.getElementById("FileSecretLevel").value;
		//适用软件 暂时以字符串形式存放，各软件名称以英文逗号隔开
		jsonObject["ObjectiveSoftNames[]"]=[];
		var softs=$('#AllSoft div input').toArray();
		for(i=0;i<softs.length-1;i+=2)
		{
			if(softs[i].checked==true){
				jsonObject["ObjectiveSoftNames[]"].push(softs[i].value);
			}
		}
		jsonObject.FileSource=document.getElementById("FileSource").value;
		jsonObject.ContactPerson=document.getElementById("ContactPerson").value.trim();
		jsonObject.ContactComp=document.getElementById("ContactComp").value.trim();
		jsonObject.ContactAddress=document.getElementById("ContactAddress").value.trim();
		jsonObject.ContactZip=document.getElementById("ContactZip").value.trim();
		jsonObject.ContactTel=document.getElementById("ContactTel").value.trim();
		jsonObject.ContactEmail=document.getElementById("ContactEmail").value.trim();

		$.ajax({
		     type: "POST",   
		     url: "/WaterSound/CreateNewRecord?type=modelfile",   
		     data:jsonObject,
		     success:function(str_response){
		    	 var param=str_response.split(",");
		    	 if("success"==param[0])
		    	 {
		    		 showNotification('notify',"新建成功！");
		    	 }
		    	 else{
		    		 showNotification('notify',"新建失败！模型已存在");
		    	 }
		     }
		});

	}
	
}