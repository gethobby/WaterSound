//editor1是软件功能，editor2是典型算例，editor3是应用效果，editor4是简介
var editor1,editor2,editor3,editor4;
KindEditor.ready(function(K) {
	editor1 = K.create('textarea[id="FunctionDesc"]', {
		uploadJson : '../upload_json.jsp',
		items : [
			'bold','italic','underline','fontname','fontsize','forecolor','hilitecolor','link','wordpaste', 
			'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist',
			'insertunorderedlist', 'indent', 'outdent','|','preview', 'fullscreen', '/',
			 '|', 'image','table', 'hr'
		]
	});
	editor2 = K.create('textarea[id="ComputeExample"]', {
		allowFileManager : false,
		uploadJson : '../upload_json.jsp',
		items : [
					'bold','italic','underline','fontname','fontsize','forecolor','hilitecolor','link','wordpaste', 
					'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist',
					'insertunorderedlist', 'indent', 'outdent','|','preview', 'fullscreen', '/',
					 '|', 'image','table', 'hr'
				]
	});
	editor3 = K.create('textarea[id="ResultExample"]', {
		allowFileManager : false,
		uploadJson : '../upload_json.jsp',
		items : [
					'bold','italic','underline','fontname','fontsize','forecolor','hilitecolor','link','wordpaste', 
					'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist',
					'insertunorderedlist', 'indent', 'outdent','|','preview', 'fullscreen', '/',
					 '|', 'image','table', 'hr'
				]
	});
	editor4 = K.create('textarea[id="Description"]', {
		allowFileManager : false,
		uploadJson : '../upload_json.jsp',
		items : [
					'bold','italic','underline','fontname','fontsize','forecolor','hilitecolor','link','wordpaste', 
					'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist',
					'insertunorderedlist', 'indent', 'outdent','|','preview', 'fullscreen', '/',
					 '|', 'image','table', 'hr'
				]
	});
});
function checkInput()
{
	var verified=true;
	
	//检查软件名称
	if(!document.getElementById("SoftName").value)
	{
		$("#SoftName").next().css("display","inline");
		verified=false;
	}
	else
	{
		$("#SoftName").next().css("display","none");
	}
	
//	//检查软件版本号
//	if(!document.getElementById("SoftVersion").value)
//	{
//		$("#SoftVersion").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#SoftVersion").next().css("display","none");
//	}
//	
//	//检查软件更新时间
//	if(!document.getElementById("SoftUpdateTime").value)
//	{
//		$("#SoftUpdateTime").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#SoftUpdateTime").next().css("display","none");
//	}
//	//检查软件类别
//	if(!document.getElementById("SoftType1").value||!document.getElementById("SoftType2").value)
//	{
//		$("#SoftType2").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#SoftType2").next().css("display","none");
//	}
//	//检查软件密级
//	if(!document.getElementById("SoftSecretLevel").value)
//	{
//		$("#SoftSecretLevel").next().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#SoftSecretLevel").next().css("display","none");
//	}
//	//检查软件功能*富文本编辑框
//	if(!editor1.html())
//	{
//		$("#SoftFunctionDesc").prev().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#SoftFunctionDesc").prev().css("display","none");
//	}
//	//检查典型算例*富文本编辑框
//	if(!editor2.html())
//	{
//		$("#SoftComputeExample").prev().css("display","inline");
//		verified=false;
//	}
//	else
//	{
//		$("#SoftComputeExample").prev().css("display","none");
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
//		$("#ContactComp").next().css("display","inline");
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
//	
	return verified;
}
function insertNewSoftRecord()
{
	var jsonObject=new Object();
	if(checkInput())
	{
		jsonObject.SoftName=document.getElementById("SoftName").value.trim();
		jsonObject.SoftNameEN=document.getElementById("SoftNameEN").value.trim();
		jsonObject.SoftVersion=document.getElementById("SoftVersion").value.trim();
		jsonObject.SoftUpdateTime=document.getElementById("SoftUpdateTime").value;
		jsonObject.SoftLogo=document.getElementById("SoftLogo").value;//"server/testpath/test.jpg";//LOGO是图片考虑先上传返回在服务器上的地址然后存储
		jsonObject.SoftType1=document.getElementById("SoftType1").value;
		jsonObject.SoftType2=document.getElementById("SoftType2").value;
		jsonObject.SoftSecretLevel=document.getElementById("SoftSecretLevel").value;
		jsonObject.SoftDescription=editor4.html();
		jsonObject.FunctionDesc=editor1.html();
		jsonObject.ComputeExample=editor2.html();
		jsonObject.ResultExample=editor3.html();
		jsonObject.ContactPerson=document.getElementById("ContactPerson").value.trim();
		jsonObject.ContactComp=document.getElementById("ContactComp").value.trim();
		jsonObject.ContactAddress=document.getElementById("ContactAddress").value.trim();
		jsonObject.ContactZip=document.getElementById("ContactZip").value.trim();
		jsonObject.ContactTel=document.getElementById("ContactTel").value.trim();
		jsonObject.ContactEmail=document.getElementById("ContactEmail").value.trim();
		
		$.ajax({   
		     type: "POST",   
		     url: "/WaterSound/CreateNewRecord?type=soft",   
		     data:jsonObject,
		     success:function(str_response){
		    	 if("success"==str_response)
		    	 {alert("创建成功！");}
		    	 else{alert("创建失败！");}
		     }
		});
	}
}


/*// 删除软件根据Id
var deleteBnt = document.getElementById("deleteBnt");
deleteBnt.onclick = deleteOldSoftRecord;

function deleteOldSoftRecord()
{
	   alert("hello");
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
				 var softid = check[0].nextSibling.nodeValue;
				 id_str = id_str.concat(softid+",");
				 checkNum++;
				// alert("插入软件ID："+softid);
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
					 url: "/WaterSound/DeleteOldRecord?type=soft",   
					 data:IDs,
					 success:function(str_response){
						// alert(str_response);
						 var deleteNum = parseInt(str_response);
						 if(deleteNum>0)
						 {		    		
							 alert("成功删除"+deleteNum+"个软件记录");
						 }
						 else{alert("删除失败！");}
					 }
				 });
			 } 
		}
		 			
}*/