function loadObjectInfo(objectid)
{
	if(objectid){
		$("#ObjectID").val(objectid);
		$.ajax({
	    	url: '/WaterSound/GetDetailInfo?infotype=object&id='+objectid+"&tid="+Math.random(),
	    	success:function(data){	
	    		if(data.length>0){
	    			var objectinfo=data[0];
	    			$("#ObjectName").html(objectinfo["目标名称"]);
	    			$("#ObjectPicture").attr("src","../"+objectinfo["目标图片"]);
	    			$("#ObjectPicPath").val(objectinfo["目标图片"]);
	    			$("#Type1").html(objectinfo["一级类别"]);
	    			$("#Type2").html(objectinfo["二级类别"]);
	    			$("#ObjectBelong").html(objectinfo["所属国家"]);
	    			$("#ObjectType1").val($("#Type1").html());
	    			getObjectType2(document.getElementById("ObjectType1"));
	    		}
	    	}
		});
	}
}
/*修改模型文件基本信息*/
function showObjectinfo_M()
{
	$("#Objectinfo_Modify").css("display","");
	$("#Objectinfo").css("display","none");
	
	$("#ObjectName_Modify").val($("#ObjectName").html());
	$("#ObjectPicPath_Modify").val($("#ObjectPicPath").val());
	$("#ObjectType1").val($("#Type1").html());
	if(document.getElementById("ObjectType2")){$("#ObjectType2").val($("#Type2").html());}
	else{
		getObjectType2(document.getElementById("ObjectType1"));
		
	}
	$("#ObjectType2").val($("#Type2").html());
	$("#ObjectBelong_Modify").val($("#ObjectBelong").html());
	
}
function cancelObjectinfo_M(){
	$("#Objectinfo_Modify").css("display","none");
	$("#Objectinfo").css("display","");
}
function modifyObjectinfo()
{
	var objectID=document.getElementById("ObjectID").value;
	var jsonObject=new Object();
	jsonObject.ObjectName=document.getElementById("ObjectName_Modify").value.trim();
	jsonObject.ObjectPicPath=document.getElementById("ObjectPicPath_Modify").value.trim();
	jsonObject.ObjectType1=$("#ObjectType1").val();
	jsonObject.ObjectType2=document.getElementById("ObjectType2").value;
	jsonObject.ObjectBelong=document.getElementById("ObjectBelong_Modify").value;
	$.ajax({   
	     type: "POST",   
	     url: "/WaterSound/UpdateObject?id="+objectID,   
	     data:jsonObject,
	     success:function(str_response){
	    	 if("success"==str_response)
	    	 {
	    		 alert("修改成功！");
	    		 document.getElementById("Objectinfo_Modify").style.display="none";
	    		 document.getElementById("Objectinfo").style.display="";
	    		 loadObjectInfo(objectID);
	    	 }
	    	 else{alert("修改失败！");}
	     }
	});
}