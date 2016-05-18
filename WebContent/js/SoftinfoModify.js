//editor1是软件功能，editor2是典型算例，editor3是应用效果，editor4是简介
var editor1,editor2,editor3,editor4;

/*加载软件信息*/
function loadSoftInfo(softid)
{
	if(softid){
		$("#SoftID").val(softid);
		$.ajax({
	    	url: '/WaterSound/GetDetailInfo?infotype=soft&id='+softid+"&tid="+Math.random(),
	    	success:function(data){	
	    		if(data.length>0){
	    			var softinfo=data[0];
	    			$("#SoftName").html(softinfo["softname"]);
	    			$("#SoftNameEN").html(softinfo["英文名称"]);
	    			$("#SoftVersion").html(softinfo["版本"]);
	    			$("#SoftUpdateTime").html(softinfo["发布更新时间"]);
	    			$("#SoftLogo").attr("src","../"+softinfo["LOGO"]);
	    			$("#SoftType1").html(softinfo["一级类型"]);
	    			$("#SoftType2").html(softinfo["二级类型"]);
	    			$("#SoftSecretLevel").html(softinfo["密级"]);
	    			$("#SoftDescription").html(softinfo["简介"]);

	    			$("#SoftFunctionDesc").html(softinfo["软件功能"]);
	    			
	    			$("#SoftComputeExample").html(softinfo["典型算例"]);
	    			
	    			$("#SoftResultExample").html(softinfo["应用效果"]);
	    			
	    			$("#ContactPerson").html(softinfo["联系人"]);
	    			$("#ContactComp").html(softinfo["单位名称"]);
	    			$("#ContactAddress").html(softinfo["通信地址"]);
	    			$("#ContactZip").html(softinfo["邮政编码"]);
	    			$("#ContactTel").html(softinfo["电话"]);
	    			$("#ContactEmail").html(softinfo["电子邮件"]);
	    		}
	    	}
		});
	}

}
/*修改软件基本信息*/
function showBasicinfo_M()
{
	$("#Basicinfo_Modify").css("display","");
	$("#Basicinfo").css("display","none");
	
	$("#SoftName_Modify").html($("#SoftName").html());
	$("#SoftNameEN_Modify").val($("#SoftNameEN").html());
	$("#SoftVersion_Modify").val($("#SoftVersion").html());
	$("#SoftUpdateTime_Modify").val($("#SoftUpdateTime").html().split(" ")[0]);
	$("#SoftLogo_Modify").val($("#SoftLogo").attr("src").substring(3));
	$("#SoftType1_Modify").val($("#SoftType1").html());
	$("#SoftType2_Modify").val($("#SoftType2").html());
	$("#SoftSecretLevel_Modify").val($("#SoftSecretLevel").html());
	
	editor4 = KindEditor.create('textarea[id="SoftDescription_Modify"]', {
		uploadJson : '../upload_json.jsp',
		items : [
			'bold','italic','underline','fontname','fontsize','forecolor','hilitecolor','link','wordpaste', 
			'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist',
			'insertunorderedlist', 'indent', 'outdent','|','preview', 'fullscreen', '/',
			 '|', 'image','table', 'hr'
		]
	});
	editor4.html($("#SoftDescription").html());
	
}
function cancelBasicinfo_M(){
	$("#Basicinfo_Modify").css("display","none");
	$("#Basicinfo").css("display","");
}
function modifyBasicinfo()
{
	var softID=document.getElementById("SoftID").value;
	var jsonObject=new Object();
	jsonObject.SoftNameEN=document.getElementById("SoftNameEN_Modify").value.trim();
	jsonObject.SoftVersion=document.getElementById("SoftVersion_Modify").value.trim();
	jsonObject.SoftUpdateTime=document.getElementById("SoftUpdateTime_Modify").value;
	jsonObject.SoftLogo=document.getElementById("SoftLogo_Modify").value;//"server/testpath/test.jpg";//LOGO是图片考虑先上传返回在服务器上的地址然后存储
	jsonObject.SoftType1=document.getElementById("SoftType1_Modify").value;
	jsonObject.SoftType2=document.getElementById("SoftType2_Modify").value;
	jsonObject.SoftSecretLevel=document.getElementById("SoftSecretLevel_Modify").value;
	jsonObject.SoftDescription=editor4.html();
	$.ajax({   
	     type: "POST",   
	     url: "/WaterSound/UpdateSoftinfo?scope=basicinfo&id="+softID+"&tid="+Math.random(),   
	     data:jsonObject,
	     success:function(str_response){
	    	 if("success"==str_response)
	    	 {
	    		 alert("修改成功！");
	    		 document.getElementById("Basicinfo_Modify").style.display="none";
	    		 document.getElementById("Basicinfo").style.display="";
	    		 loadSoftInfo(softID);
	    	 }
	    	 else{alert("修改失败！");}
	     }
	});
}
/*修改软件功能*/
function showSoftFuncDesc_M(){
	
	$("#SoftFunctionDesc_Modify").parent().css("display","");
	$("#SoftFunctionDesc").parent().css("display","none");

	editor1 = KindEditor.create('textarea[id="SoftFunctionDesc_Modify"]', {
		uploadJson : '../upload_json.jsp',
		items : [
			'bold','italic','underline','fontname','fontsize','forecolor','hilitecolor','link','wordpaste', 
			'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist',
			'insertunorderedlist', 'indent', 'outdent','|','preview', 'fullscreen', '/',
			 '|', 'image','table', 'hr'
		]
	});
	editor1.html($("#SoftFunctionDesc").html());
}
function cancelSoftFuncDesc_M(){
	$("#SoftFunctionDesc_Modify").parent().css("display","none");
	$("#SoftFunctionDesc").parent().css("display","");
}
function modifySoftFuncDesc()
{
	var softID=document.getElementById("SoftID").value;
	var jsonObject=new Object();
	jsonObject.FunctionDesc=editor1.html();
	$.ajax({   
	     type: "POST",   
	     url: "/WaterSound/UpdateSoftinfo?scope=softfunctiondesc&id="+softID+"&tid="+Math.random(),   
	     data:jsonObject,
	     success:function(str_response){
	    	 if("success"==str_response)
	    	 {
	    		 alert("修改成功！");
	    		 cancelSoftFuncDesc_M();
	    		 loadSoftInfo(softID);
	    	 }
	    	 else{alert("修改失败！");}
	     }
	});
}
/*修改典型算例*/
function showSoftComputeExample_M(){
	$("#SoftComputeExample_Modify").parent().css("display","");
	$("#SoftComputeExample").parent().css("display","none");

	editor2 = KindEditor.create('textarea[id="SoftComputeExample_Modify"]', {
		allowFileManager : false,
		uploadJson : '../upload_json.jsp',
		items : [
					'bold','italic','underline','fontname','fontsize','forecolor','hilitecolor','link','wordpaste', 
					'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist',
					'insertunorderedlist', 'indent', 'outdent','|','preview', 'fullscreen', '/',
					 '|', 'image','table', 'hr'
				]
	});
	editor2.html($("#SoftComputeExample").html());
}
function cancelSoftComputeExample_M(){
	$("#SoftComputeExample_Modify").parent().css("display","none");
	$("#SoftComputeExample").parent().css("display","");
}
function modifySoftComputeExample()
{
	var softID=document.getElementById("SoftID").value;
	var jsonObject=new Object();
	jsonObject.ComputeExample=editor2.html();
	$.ajax({   
	     type: "POST",   
	     url: "/WaterSound/UpdateSoftinfo?scope=softcomputeexample&id="+softID+"&tid="+Math.random(),   
	     data:jsonObject,
	     success:function(str_response){
	    	 if("success"==str_response)
	    	 {
	    		 alert("修改成功！");
	    		 cancelSoftComputeExample_M();
	    		 loadSoftInfo(softID);
	    	 }
	    	 else{alert("修改失败！");}
	     }
	});
}
/*修改应用效果*/
function showSoftResultExample_M(){
	$("#SoftResultExample_Modify").parent().css("display","");
	$("#SoftResultExample").parent().css("display","none");

	editor3 = KindEditor.create('textarea[id="SoftResultExample_Modify"]', {
		allowFileManager : false,
		uploadJson : '../upload_json.jsp',
		items : [
					'bold','italic','underline','fontname','fontsize','forecolor','hilitecolor','link','wordpaste', 
					'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist',
					'insertunorderedlist', 'indent', 'outdent','|','preview', 'fullscreen', '/',
					 '|', 'image','table', 'hr'
				]
	});
	editor3.html($("#SoftResultExample").html());
}
function cancelSoftResultExample_M(){
	$("#SoftResultExample_Modify").parent().css("display","none");
	$("#SoftResultExample").parent().css("display","");
}
function modifySoftResultExample()
{
	var softID=document.getElementById("SoftID").value;
	var jsonObject=new Object();
	jsonObject.ResultExample=editor3.html();
	$.ajax({   
	     type: "POST",   
	     url: "/WaterSound/UpdateSoftinfo?scope=softresultexample&id="+softID+"&tid="+Math.random(),   
	     data:jsonObject,
	     success:function(str_response){
	    	 if("success"==str_response)
	    	 {
	    		 alert("修改成功！");
	    		 cancelSoftResultExample_M();
	    		 loadSoftInfo(softID);
	    	 }
	    	 else{alert("修改失败！");}
	     }
	});
}
/*修改通讯信息*/
function showContactinfo_M()
{
	$("#Contactinfo_Modify").css("display","");
	$("#Contactinfo").css("display","none");
	
	$("#ContactPerson_Modify").val($("#ContactPerson").html());
	loadContactCompany("ContactComp_Modify");
	$("#ContactComp_Modify").val($("#ContactComp").html());
	$("#ContactAddress_Modify").val($("#ContactAddress").html());
	$("#ContactZip_Modify").val($("#ContactZip").html());
	$("#ContactTel_Modify").val($("#ContactTel").html());
	$("#ContactEmail_Modify").val($("#ContactEmail").html());	
}
function cancelContactinfo_M(){
	$("#Contactinfo_Modify").css("display","none");
	$("#Contactinfo").css("display","");
}
function modifyContactinfo()
{
	var softID=document.getElementById("SoftID").value;
	var jsonObject=new Object();
	jsonObject.ContactPerson=document.getElementById("ContactPerson_Modify").value.trim();
	jsonObject.ContactComp=document.getElementById("ContactComp_Modify").value.trim();
	jsonObject.ContactAddress=document.getElementById("ContactAddress_Modify").value.trim();
	jsonObject.ContactZip=document.getElementById("ContactZip_Modify").value.trim();
	jsonObject.ContactTel=document.getElementById("ContactTel_Modify").value.trim();
	jsonObject.ContactEmail=document.getElementById("ContactEmail_Modify").value.trim();
	
	$.ajax({   
	     type: "POST",   
	     url: "/WaterSound/UpdateSoftinfo?scope=contactinfo&id="+softID+"&tid="+Math.random(),   
	     data:jsonObject,
	     success:function(str_response){
	    	 if("success"==str_response)
	    	 {
	    		 alert("修改成功！");
	    		 cancelContactinfo_M();
	    		 loadSoftInfo(softID);
	    	 }
	    	 else{alert("修改失败！");}
	     }
	});
}
var subContent=new Array();       //记录子页面的名称
subContent[0]="BasicinfoSection";
subContent[1]="SoftFunctionDescSection";
subContent[2]="SoftComputeExampleSection";
subContent[3]="SoftResultExampleSection";
subContent[4]="ContactinfoSection";
//-----------------------------------------------------------------------------
/*
 * selectSubContent函数是softinfomodify页面的各信息区域切换的函数
 */
function selectSubContent(showContent){
// 操作标签
	var i=0;
	var targetContent=document.getElementById('navinfo').getElementsByTagName('li');
	for(i=0;i<subContent.length;i++){
		document.getElementById(subContent[i]).style.display="none"; 
		targetContent[i].className='';
	}
	document.getElementById(subContent[showContent]).style.display="block";
	targetContent[showContent].className='selectsection';
}