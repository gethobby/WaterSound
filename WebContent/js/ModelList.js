/*
 * 为目标添加模型文件，跳转到上传模型文件的页面
 * */
function addModelFile(TargetId,TargetName)
{
	var objpage=window.open("fileUpload.jsp?ID="+TargetId+"&name="+TargetName);
}
/*
 * 打开相应模型文件的修改信息页面
 * */
function ModifyModelFileinfo(fileID)
{
	var obj = document.getElementById("DB_NO"); //定位id
	var index = obj.selectedIndex; // 选中索引
	var nodeIP = obj.options[index].value; // 选中值
	var objpage=window.open("ModelFileinfoModify.jsp?fileID="+fileID+"&nodeIP="+nodeIP);
}
/*
 * 打开相应目标的修改信息页面
 * */
function ModifyObjectinfo(objectID)
{
	var objpage=window.open("ObjectinfoModify.jsp?objectID="+objectID);
}
/*
 * 选中目标行会显示或者隐藏选中目标现有的模型文件
 * */
function showModelFiles(source)
{
	var modelfiles=source.nextElementSibling;//表格奇数行是目标的信息，偶数行是目标中包含的模型文件的信息
	var TargetObjectID=source.getElementsByTagName("input")[0].value;//目标信息行隐藏了目标的ID
	//document.getElementById('selectedmodelfile').value=TargetObjectID;//保险，没什么作用
	
	if(modelfiles.style.display=="none"){//如果模型文件部分为隐藏状态
		modelfiles.style.display="";	
		$.ajax({
		     type: "POST",   
		     url: "/WaterSound/GetModellist?objId="+TargetObjectID,   
		     success:function(data){
		    	 modelfiles.getElementsByTagName("tbody")[0].innerHTML="";
		    	 if(data&&data.length){
		    		for(var i=0;i<data.length;i++){
		   				var tr =createFileTR(data[i]["模型文件"],data[i]["fileID"],data[i]["来源"],data[i]["简介"]);
			    		modelfiles.getElementsByTagName("tbody")[0].appendChild(tr);
		    		}
		    		$("section.content section").animate({height:$("section.content section").height()+$(modelfiles).height()},20);
		    		
		    	 }
		    	 else{
		    		var tr = document.createElement('tr');
		   			tr.innerHTML='<td colspan="4">该目标还没有模型文件！</td>';
		    		 modelfiles.getElementsByTagName("tbody")[0].appendChild(tr);
		    		 $("section.content section").animate({height:$("section.content section").height()+$(modelfiles).height()},20);
		    	 }
		     }
		});
	}
	else{//模型文件部分为显示状态
		modelfiles.style.display="none";
		$("section.content section").animate({height:$("section.content section").height()-$(modelfiles).height()},20);
	}
}
function createFileTR(FileName,FileId,FileSource,FileDescription)
{
	var tdcol1template='<input name="files" type="checkbox" onclick="clearOtherSelect(this,\'files\')" value="{{%fileID}}">{{%filename}}';
	var tdcol2template='{{%filesource}}';
	var tdcol3template='{{%filedescription}}';
	var tdcol4template='<a href="#" onclick="ModifyModelFileinfo({{%fileID}})">修改</a><a onclick="">删除</a>';
	
	var tr = document.createElement('tr');

	var td1=document.createElement('td');
	var td2=document.createElement('td');
	var td3=document.createElement('td');
	var td4=document.createElement('td');
	
	td1.innerHTML=new t(tdcol1template).render({fileID:FileId,filename:FileName});
	td2.innerHTML=new t(tdcol2template).render({filesource:FileSource});
	td3.innerHTML=new t(tdcol3template).render({filedescription:FileDescription});
	td4.innerHTML=new t(tdcol4template).render({fileID:FileId});
	
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	tr.appendChild(td4);
	
	return tr;
	
}
/*
 * 寻找选中的模型文件
 * 返回：选中模型文件的(ID,modelName)
 */
function findCheckedModelfile()
{
	var modelfiles=document.getElementsByName("model"); // files改成了model，因为不存在object
	var index=0;
	while(index<modelfiles.length)
	{
		if(modelfiles[index].checked==true) break;
		index++;
	}
	if(index<modelfiles.length){
		// alert(modelfiles[index].value);
		return modelfiles[index].value;
	}
	else return undefined;
}

/*
 * 根据模型文件显示可用目标软件
 * */

var isjianmo =false;
function jianmo(){
    var modeid_name = findCheckedModelfile();
    var arr = modeid_name.split(",");
	var ModelfileID=arr[0];
	var ModelfileName = arr[1];
	//alert(ModelfileID+","+ModelfileName);
	//接下来，先按照fileName把模型从存储节点机下载下来(本地磁盘文件后复制到webcontent),然后，调用socket发送到软件节点机
	isjianmo = true;
	
	downloadModelFile(ModelfileName);
	var obj = document.getElementById("DB_NO"); //定位id
	var index = obj.selectedIndex; // 选中索引
	var nodeIP = obj.options[index].value; // 选中值
	//alert(ModelfileID+","+nodeIP);
	
	if(ModelfileID){
		$.ajax({
			type: "post",
			url:'/WaterSound/GetAvailableSoft?fileID='+ModelfileID+"&nodeIP="+nodeIP,//模型文件ID（文件名fileinfo_ID,文件所属nodeIP）
			success:function(data){
				document.getElementById("softlist").innerHTML="";
				if(data&&data.length)
				{
					for(var i=0;i<data.length;i++)
					{
						addSoft(data[i]["softID"],data[i]["softname"],data[i]["LOGO"],ModelfileID);
					}
				}
				else{
					document.getElementById("softlist").innerHTML="暂时没有可用的目标特性软件！";
				}
				modelWindowShow();
		     }
		});
	}
	else{alert("请选择需要操作的模型文件");}


	function addSoft(id,name,logoimage,model){

		var templatesoftbutton='<button class="listsoft" onclick="selectSoft(this,{{%ModelID}})" value="{{%softname}}" style="background-image:url({{%logo}});"></button>'
				+'<input type="hidden" value="{{%softID}}">';
		var div = document.createElement('div');
		div.style.display="inline";
		var softbuttonItem = new t(templatesoftbutton);
		div.innerHTML=softbuttonItem.render({
			softID:id,
			softname:name,
			logo:"../"+logoimage,
			ModelID:model
		});
		document.getElementById("softlist").appendChild(div);			
	}	

}
/*
 * 用户选择使用某目标软件
 * */
function selectSoft(source,modelID)
{
	document.getElementById('notify').style.display='none';
	applyNode(document.getElementById("loginuser").value,source.value,modelID);
}
/*
 * 添加新目标时的输入检查
 * 
 */
function checkInput()
{
	var verified=true;
	//检查目标名称
	if(!document.getElementById("ObjectName").value)
	{
		$("#ObjectName").next().css("display","inline");
		verified=false;
	}
	else{
		$("#ObjectName").next().css("display","none");
	}
	
	//检查目标图片
	if(!document.getElementById("ObjectLogo").value)
	{
		$("#ObjectLogo").parent().next().css("display","inline");
		verified=false;
	}
	else{
		$("#ObjectLogo").parent().next().css("display","none");
	}
	//检查目标分类
	if(!document.getElementById("ObjectType1").value||$("#ObjectType2").val()=="")
	{
		$("#ObjectType").next().css("display","inline");
		verified=false;
	}
	else{
		
		$("#ObjectType").next().css("display","none");
	}
	//检查所属国家
	if(!document.getElementById("ObjectBelong").value)
	{
		$("#ObjectBelong").next().css("display","inline");
		verified=false;
	}
	else{
		$("#ObjectBelong").next().css("display","none");
	}
	
	return verified;
}
/*
 * 添加新目标
 * */
function insertNewObjectRecord()
{
	var jsonObject={
			"ObjectName":"",
			"ObjectLogo":"",
			"ObjectType1":"",
			"ObjectType2":"",
			"ObjectBelong":""
		}
	
	if(checkInput()){
		jsonObject["ObjectName"]=document.getElementById("ObjectName").value;
		jsonObject["ObjectLogo"]=document.getElementById("ObjectLogo").value;
		jsonObject["ObjectType1"]=document.getElementById("ObjectType1").value;
		jsonObject["ObjectType2"]=document.getElementById("ObjectType2").value;
		jsonObject["ObjectBelong"]=document.getElementById("ObjectBelong").value;
		$.ajax({   
		     type: "POST",   
		     url: "/WaterSound/CreateNewRecord?type=object",   
		     data:jsonObject,
		     //traditional: true,
		     success:function(str_response){
		    	 if("success"==str_response)
		    	 {alert("创建成功！");}
		    	 else{alert("创建失败！");}
		     }
		});
	}
}
/*
 * 添加目标按钮的点击操作
 * */
function newObjectinfo()
{
	
	var targetsection=document.getElementById("NewObject");
	targetsection.style.display="";
	targetsection.previousElementSibling.style.display="none";
	modelWindowShow();
}

/*
 * 点击下载模型文件:ModelList.jsp ${item[1]}
 */
function downloadModelFile(modelName) {
	
	var obj = document.getElementById("DB_NO"); //定位id
	var index = obj.selectedIndex; // 选中索引
	var nodeIP = obj.options[index].value; // 选中值
	
	//alert(modelName);//模型名带后缀
	if(modelName!=null){
		//将存储节点机模型文件下载至服务器磁盘目录
		$.ajax({   
		     type: "POST",   
		     url: "/WaterSound/DownloadModelFile?modelName="+modelName+"&nodeIP="+nodeIP,   
		     //traditional: true,
		     success:function(str_response){
		    	 if("success"==str_response)
		    	 {
		    		 alert("下载成功！");
		    		// 指定磁盘的模型文件(从存储节点机下载的压缩模型文件)复制到webcontent目录
		    			$.ajax({   
		    			     type: "POST",   
		    			     url: "/WaterSound/UploadServlet?type=copy&modelName="+modelName, 
		    			     //traditional: true,
		    			     success:function(str_response){
		    			    	 if("success"==str_response)
		    			    	 {
		    			    		 if (!isjianmo) {
									 //alert("复制成功！"); // 复制成功后，把webcontent里的压缩文件选择保存到本地
		    			    		 var path = "/WaterSound/Down/"+modelName.replace(/.\w+$/,"")+".zip";
		    			    		 //alert(path);
		    			    		 window.location.href=path;
									}else {
										isjianmo = false;
									}		    			    		
		    				  	 }
		    			    	 else{alert("复制失败！");}
		    			     }
		    			});
		    		 
			 }
		    	 else{alert("下载失败！");}
		     }
		});
			
	}
}

/*
 * 点击加载按钮，加载对应节点机上的模型文件
 * 
 */
function GetModelList() {
  
	var obj = document.getElementById("DB_NO"); //定位id
	var index = obj.selectedIndex; // 选中索引
	var nodeIP = obj.options[index].value; // 选中值
	
	var params = document.getElementById("search").value; // 模型查询条件
	//alert(params);
	
	var tby = document.getElementById("filename"); //定位tbody
	
	if(nodeIP!=null){

		$.ajax({   
		     type: "POST",   
		     url: "/WaterSound/GetModellist?nodeIP="+nodeIP+"&searchParams="+params,   
		     //traditional: true,
		     success:function(data){
		    	 
		    	 tby.innerHTML="";
		    	 if(data&&data.length){
		    		for(var i=0;i<data.length;i++){
		    			//alert(data[i]["fileID"]+data[i]["模型文件"]+data[i]["storepath"]+data[i]["适用软件"]);
		    			var tr =createModelTR(data[i]["fileID"],data[i]["模型文件"],data[i]["storepath"],data[i]["适用软件"]);
			    		tby.appendChild(tr);
		    		}
		    			    		
		    	 }

		     }
		});
	}
}


// 加入模型文件表的一行
function createModelTR(ModelID, FileName,SortPath,MatchSotf)
{
	 
	var tdcol1template='<input name="model" type="radio" style="width: 20px" value="{{%modelID}},{{%filename}}"><strong>NO.</strong>{{%modelID}}';
	var tdcol2template='<a href="#" onclick="ModifyModelFileinfo(\'{{%modelID}}\')">{{%filename}}</a>' ;
	var tdcol3template='{{%filepath}}';
	var tdcol4template='{{%filesoft}}';
	var tdcol5template='<a href="#" onclick="downloadModelFile(\'{{%filename}}\')">下载</a>';
	
	var tr = document.createElement('tr');

	var td1=document.createElement('td');
	var td2=document.createElement('td');
	var td3=document.createElement('td');
	var td4=document.createElement('td');
	var td5=document.createElement('td');
	
	td1.innerHTML=new t(tdcol1template).render({modelID:ModelID,filename:FileName});//
	td2.innerHTML=new t(tdcol2template).render({modelID:ModelID,filename:FileName});
	td3.innerHTML=new t(tdcol3template).render({filepath:SortPath});
	td4.innerHTML=new t(tdcol4template).render({filesoft:MatchSotf});
	td5.innerHTML=new t(tdcol5template).render({filename:FileName});
	
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	tr.appendChild(td4);
	tr.appendChild(td5);
	
	return tr;
	
}


	 
function filledModelName() { //讲用户本地上传的模型文件保存到服务器目录，并复制到服务器端Socket上传的目录
	 var file = document.getElementById("file");
	var fileName = document.getElementById("ObjectName");
	//alert(file.value)
	var url=file.value;
	url=url.split("\\");//这里要将 \ 转义一下
	fileName.value = url[url.length-1];
	alert("文件名 "+url[url.length-1]);
}

//(上传)添加新的模型
function upmodelfile()
{
	var obj = document.getElementById("DB_NO"); //定位id
	var index = obj.selectedIndex; // 选中索引
	var nodeIP = obj.options[index].value; // 选中值
	if (nodeIP!=null) {
		
		//适用软件 暂时以字符串形式存放，各软件名称以英文逗号隔开
	 	var jsonObject=new Object();
	   jsonObject.nodeIP=nodeIP;
	   jsonObject.modelName=document.getElementById("ObjectName").value;
	 
		jsonObject["ObjectiveSoftNames[]"]=[];
		var softs=$('#AllSoft div input').toArray();
		for(i=0;i<softs.length-1;i+=2)
		{
			if(softs[i].checked==true){
				jsonObject["ObjectiveSoftNames[]"].push(softs[i].value);
				//alert(softs[i].value);
			}
		}
		  jsonObject.modelDes=document.getElementById("modelDes").value;
		  
		$.ajax({
		     type: "POST",   
		     url: "/WaterSound/CreateNewRecord?type=modelfile",   
		     data:jsonObject,
		     success:function(str_response){
		    	 var param=str_response.split(",");
		    	 if("success"==param[0])
		    	 {
		    		 alert("上传成功！");
		    	 }
		    	 else{
		    		alert("上传失败！模型已存在或其他错误");
		    	 }
		     }
		});
	}
	 	
}



