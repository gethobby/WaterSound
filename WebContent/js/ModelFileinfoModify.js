/*加载模型文件信息*/
function loadModelfileInfo(fileid,nodeIP)
{  
	//alert(nodeIP)
	if(fileid&&nodeIP){
		$("#ModelFileID").val(fileid);
		$.ajax({
	    	url: '/WaterSound/GetDetailInfo?infotype=modelfile&id='+fileid+"&nodeIP="+nodeIP+"&tid="+Math.random(),
	    	success:function(data){	
	    		if(data.length>0){
	    			var fileinfo=data[0];
	    			$("#FileName").html(fileinfo["模型文件"]);
	    			$("#FileStorePath").html(fileinfo["storepath"]);	    			    	
	    			$("#FileSelectedSoft").html(fileinfo["适用软件"]);
	    			$("#FileDescription").html(fileinfo["简介"]);
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
	
	
	$("#FileName_Modify").html($("#FileName").html());		
	$("#AllSoft").html("");
	loadAllSoft("FileSelectedSoft");
	//$("#FileSelectedSoft_Modify").val($("#FileSelectedSoft").html());
	$("#FileSource_Modify").val($("#FileSource").html());
    $("#FileDescription_Modify").html($("#FileDescription").html());
}
function cancelModelFileinfo_M(){
	$("#Fileinfo_Modify").css("display","none");
	$("#Fileinfo").css("display","");
}

function modifyModelFileinfo(nodeIP)
{
	if (nodeIP) {
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
		jsonObject.FileSelectedSoft=$("#FileSelectedSoft_Modify").val();
		jsonObject.FileDescription=document.getElementById("FileDescription_Modify").value.trim();
		
		$.ajax({   
		     type: "POST",   
		     url: "/WaterSound/UpdateModelFile?id="+fileID+"&nodeIP="+nodeIP,   
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
	else {
		alert("IP地址为空，不能修改");
	}
	
}