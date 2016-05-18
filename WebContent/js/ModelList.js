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
	var objpage=window.open("ModelFileinfoModify.jsp?fileID="+fileID);
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
 * 返回：选中模型文件的ID
 */
function findCheckedModelfile()
{
	var modelfiles=document.getElementsByName("files");
	var index=0;
	while(index<modelfiles.length)
	{
		if(modelfiles[index].checked==true) break;
		index++;
	}
	if(index<modelfiles.length){
		return modelfiles[index].value;
	}
	else return undefined;
}
/*
 * 根据模型文件显示可用目标软件
 * */
function jianmo(){
	var ModelfileID=findCheckedModelfile();
	if(ModelfileID){
		$.ajax({
			type: "post",
			url:'/WaterSound/GetAvailableSoft?fileID='+ModelfileID,//模型文件ID（文件名fileinfo_ID）
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
function insertNewUserRecord()
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
	
	alert(modelName);
	if(modelName!=null){
		
		$.ajax({   
		     type: "POST",   
		     url: "/WaterSound/downloadModelFile?modelName="+modelName,   
		     //traditional: true,
		     success:function(str_response){
		    	 if("success"==str_response)
		    	 {alert("下载成功！");}
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
	
	var tby = document.getElementById("filename"); //定位tbody
	
	if(nodeIP!=null){

		$.ajax({   
		     type: "POST",   
		     url: "/WaterSound/GetModellist?nodeIP="+nodeIP,   
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
	 
	var tdcol1template='<input name="model" type="radio" style="width: 20px" ><strong>NO.</strong>{{%modelID}}';
	var tdcol2template='<a href="#" onclick="ModifyModelFileinfo(\'{{%filename}}\')">{{%filename}}</a>' ;
	var tdcol3template='{{%filepath}}';
	var tdcol4template='{{%filesoft}}';
	var tdcol5template='<a href="#" onclick="downloadModelFile(\'{{%filename}}\')">下载</a>';
	
	var tr = document.createElement('tr');

	var td1=document.createElement('td');
	var td2=document.createElement('td');
	var td3=document.createElement('td');
	var td4=document.createElement('td');
	var td5=document.createElement('td');
	
	td1.innerHTML=new t(tdcol1template).render({modelID:ModelID});
	td2.innerHTML=new t(tdcol2template).render({filename:FileName});
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

