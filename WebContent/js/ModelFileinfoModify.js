/*加载模型文件信息*/
function loadModelfileInfo(fileid)
{
	if(fileid){
		$("#ModelFileID").val(fileid);
		$.ajax({
	    	url: '/WaterSound/GetDetailInfo?infotype=modelfile&id='+fileid+"&tid="+Math.random(),
	    	success:function(data){	
	    		if(data.length>0){
	    			var fileinfo=data[0];
	    			$("#TargetObject").html(fileinfo["目标名称"]);
	    			$("#TargetObjectID").val(fileinfo["目标"]);
	    			$("#FileName").html(fileinfo["模型文件"]);
	    			$("#FileStorePath").html(fileinfo["storepath"]);
	    			$("#FileDescription").html(fileinfo["简介"]);
	    			$("#FileSecretLevel").html(fileinfo["密级"]);
	    			$("#FileSelectedSoft").html(fileinfo["适用软件"]);
	    			$("#FileSource").html(fileinfo["来源"]);
	    			
	    			$("#ContactPerson").html(fileinfo["联系人"]);
	    			$("#ContactComp").html(fileinfo["单位名称"]);
	    			$("#ContactAddress").html(fileinfo["通信地址"]);
	    			$("#ContactZip").html(fileinfo["邮政编码"]);
	    			$("#ContactTel").html(fileinfo["电话"]);
	    			$("#ContactEmail").html(fileinfo["电子邮件"]);
	    		}
	    	}
		});
	}
}
/*修改模型文件基本信息*/
function showModelFileinfo_M()
{
	$("#Fileinfo_Modify").css("display","");
	$("#Fileinfo").css("display","none");
	
	$("#TargetObject_Modify").html($("#TargetObject").html());
	$("#FileName_Modify").html($("#FileName").html());
	$("#FileDescription_Modify").html($("#FileDescription").html());
	$("#FileSecretLevel_Modify").val($("#FileSecretLevel").html());
	$("#AllSoft").html("");
	loadAllSoft("FileSelectedSoft");
	//$("#FileSelectedSoft_Modify").val($("#FileSelectedSoft").html());
	$("#FileSource_Modify").val($("#FileSource").html());
	$("#ContactPerson_Modify").val($("#ContactPerson").html());
	loadContactCompany("ContactComp_Modify");
	$("#ContactComp_Modify").val($("#ContactComp").html());
	$("#ContactAddress_Modify").val($("#ContactAddress").html());
	$("#ContactZip_Modify").val($("#ContactZip").html());
	$("#ContactTel_Modify").val($("#ContactTel").html());
	$("#ContactEmail_Modify").val($("#ContactEmail").html());	
	
	

}
function cancelModelFileinfo_M(){
	$("#Fileinfo_Modify").css("display","none");
	$("#Fileinfo").css("display","");
}
function modifyModelFileinfo()
{
	var fileID=document.getElementById("ModelFileID").value;
	var softs=$('#AllSoft div input').toArray();
	var AccessSoftNames=[];
	for(i=0;i<softs.length-1;i+=2)
	{
		if(softs[i].checked==true){
			AccessSoftNames.push(softs[i].value);
		}
	}
	$("#FileSelectedSoft_Modify").val(AccessSoftNames.join(","));
	var jsonObject=new Object();
	jsonObject.FileDescription=document.getElementById("FileDescription_Modify").value.trim();
	jsonObject.FileSecretLevel=document.getElementById("FileSecretLevel_Modify").value.trim();
	jsonObject.FileSelectedSoft=$("#FileSelectedSoft_Modify").val();
	jsonObject.FileSource=document.getElementById("FileSource_Modify").value;
	jsonObject.ContactPerson=document.getElementById("ContactPerson_Modify").value.trim();
	jsonObject.ContactComp=document.getElementById("ContactComp_Modify").value.trim();
	jsonObject.ContactAddress=document.getElementById("ContactAddress_Modify").value.trim();
	jsonObject.ContactZip=document.getElementById("ContactZip_Modify").value.trim();
	jsonObject.ContactTel=document.getElementById("ContactTel_Modify").value.trim();
	jsonObject.ContactEmail=document.getElementById("ContactEmail_Modify").value.trim();
	$.ajax({   
	     type: "POST",   
	     url: "/WaterSound/UpdateModelFile?id="+fileID,   
	     data:jsonObject,
	     success:function(str_response){
	    	 if("success"==str_response)
	    	 {
	    		 alert("修改成功！");
	    		 document.getElementById("Fileinfo_Modify").style.display="none";
	    		 document.getElementById("Fileinfo").style.display="";
	    		 loadModelfileInfo(fileID);
	    	 }
	    	 else{alert("修改失败！");}
	     }
	});
}