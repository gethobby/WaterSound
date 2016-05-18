
/*
 * 只显示部分模型文件
 * 选中目标行会显示或者隐藏选中目标中根据条件删选后的模型文件
 * */
function showFilteredFiles(source)
{
	var modelfiles=source.nextElementSibling;//表格奇数行是目标的信息，偶数行是目标中包含的模型文件的信息
	var TargetObjectID=source.getElementsByTagName("input")[0].value;//目标信息行隐藏了目标的ID
	
	
	if(modelfiles.style.display=="none"){//如果模型文件部分为隐藏状态
		modelfiles.style.display="";	
		
		var jsonObject=new Object();
		jsonObject.FileNameFilter=$("#FileName").val();
		jsonObject.ObjectID=TargetObjectID;
		jsonObject.FileSourceFilter=$("#FileSource").val();
		jsonObject.ContactCompFilter=$("#ContactComp").val();
		
		$.ajax({
		     type: "POST",   
		     url: "/WaterSound/AdvancedSearch?searchtype=modelfile",   
		     data:jsonObject,
		     success:function(data){
		    	 modelfiles.getElementsByTagName("tbody")[0].innerHTML="";
		    	 if(data&&data.length>0){
		    		 //加载tr的模版
		    		for(var i=0;i<data.length;i++){
		   				var tr =createFileTR(data[i]["模型文件"],data[i]["fileID"],data[i]["来源"],data[i]["简介"]);
			    		modelfiles.getElementsByTagName("tbody")[0].appendChild(tr);
		    		}
		    		//增加包含搜索结果的section的长度
		    		$("section.content section").animate({height:$("section.content section").height()+$(modelfiles).height()},20);
		    		
		    	 }
		    	 else{
		    		var tr = document.createElement('tr');
		   			tr.innerHTML='<td colspan="4">没有符合筛选条件的模型文件！</td>';
		    		 modelfiles.getElementsByTagName("tbody")[0].appendChild(tr);
		    		 $("section.content section").animate({height:$("section.content section").height()+$(modelfiles).height()},20);
		    	 }
		     }
		});
	}
	else{//模型文件部分为显示状态
		modelfiles.style.display="none";
		$("section.content section").animate({height:$("section.content section").height()-$(modelfiles).height()},20);//减少包含搜索结果的section的长度
	}
}

/*
 * 显示符合筛选条件的目标
 * */
function loadSearchObject()
{
	$("#filename").html("");
	$("section.content section").animate({height:350},20);
	var jsonObject=new Object();
	jsonObject.ObjectNameFilter=$("#ObjectName").val();
	jsonObject.ObjectType1Filter=$("#ObjectType1").val();
	jsonObject.ObjectType2Filter=$("#ObjectType2").val();
	
	$.ajax({
	     type: "POST",   
	     url: "/WaterSound/AdvancedSearch?searchtype=object&tid="+Math.random(),   
	     data:jsonObject,
	     success:function(data){
	    	 
	    	 if(data&&data.length>0){
	    		 
	    		 for(var i=0;i<data.length;i++)
	    		 {
	    			 var tr=createObjectTR(i+1,data[i]["objectID"],data[i]["目标名称"],data[i]["一级类别"],data[i]["二级类别"],data[i]["所属国家"]);
	    			 document.getElementById('filename').appendChild(tr);
	    			 $("section.content section").animate({height:$("section.content section").height()+$(tr).height()+5},20);
	    			 tr=createFilesTR();
	    			 document.getElementById('filename').appendChild(tr);
	    		 }
	    	 }
	    	 else{
	    		tr = document.createElement('tr');
		   		tr.innerHTML='<td colspan="5">没有符合筛选条件的目标！</td>';
		   		document.getElementById('filename').appendChild(tr);
		    	$("section.content section").animate({height:$("section.content section").height()+$(tr).height()},20);
	    	 }		    		
	     }
	});
	
	
	function createObjectTR(index,objId,objname,objtype1,objtype2,objbelong)
	{
		var tdcol1template='<strong>NO.</strong>{{%i}}<input type="hidden" value="{{%ObjectID}}">';
		var tdcol2template='<a href="#" onclick="ModifyObjectinfo({{%ObjectID}})">{{%ObjectName}}</a>';
		var tdcol3template='{{%ObjectType1}}.{{%ObjectType2}}';
		var tdcol4template='{{%ObjectBelong}}';
		var tdcol5template='<span onclick="addModelFile({{%ObjectID}},\'{{%ObjectName}}\')"><a href="#" >添加模型</a></span>';

		var tr = document.createElement('tr');
		$(tr).bind("click", function(){ 
			showFilteredFiles(this); 
			});
		var td1=document.createElement('td');
		var td2=document.createElement('td');
		var td3=document.createElement('td');
		var td4=document.createElement('td');
		var td5=document.createElement('td');
		
		td1.innerHTML=new t(tdcol1template).render({i:index,ObjectID:objId});
		td2.innerHTML=new t(tdcol2template).render({ObjectID:objId,ObjectName:objname});
		td3.innerHTML=new t(tdcol3template).render({ObjectType1:objtype1,ObjectType2:objtype2});
		td4.innerHTML=new t(tdcol4template).render({ObjectBelong:objbelong});
		td5.innerHTML=new t(tdcol5template).render({ObjectID:objId,ObjectName:objname});
		
		tr.appendChild(td1);
		tr.appendChild(td2);
		tr.appendChild(td3);
		tr.appendChild(td4);
		tr.appendChild(td5);
		
		return tr;
	}
	function createFilesTR()
	{
		var FilesTDTemplate='<div><table id="myTable" border="0"><tbody></tbody></table></div>';
		var tr = document.createElement('tr');
		tr.style.display="none";
		var td = document.createElement('td');
		td.colSpan=5;
		td.innerHTML=new t(FilesTDTemplate).render({});
		tr.appendChild(td);
		return tr;
	}
}

/*
 * 显示符合筛选条件的特性软件
 * */
function loadSearchSoft()
{
	$("#toolist").html("");
	//$("section.content section").animate({height:200},20);
	var jsonObject=new Object();
	jsonObject.SoftNameFilter=$("#SoftName").val();
	jsonObject.SoftNameENFilter=$("#SoftNameEN").val();
	jsonObject.SoftType1Filter=$("#SoftType1").val();
	jsonObject.SoftType2Filter=$("#SoftType2").val();
	
	$.ajax({
	     type: "POST",   
	     url: "/WaterSound/AdvancedSearch?searchtype=soft&tid="+Math.random(),   
	     data:jsonObject,
	     success:function(data){
	    	 if(data&&data.length>0){
	    		 
	    		 for(var i=0;i<data.length;i++)
	    		 {
	    			 var div=createSoftListitemDIV(data[i]["softname"],data[i]["LOGO"],data[i]["一级类型"],data[i]["二级类型"],data[i]["简介"]);
	    			 document.getElementById('toolist').appendChild(div);
	    			 //$("section.content section").animate({height:$("section.content section").height()+$(div).height()+10},20);
	    		 }
	    		 
	    	 }
	    	 else{
	    		div = document.createElement('div');
		   		div.innerHTML='<strong>没有符合筛选条件的特性软件！</strong>';
		   		document.getElementById('toolist').appendChild(div);
		   		$("section.content section").animate({height:300},20);
		    	//$("section.content section").animate({height:$("section.content section").height()+30},20);
	    	 }		    		
	     }
	});
	function createSoftListitemDIV(softname,logopath,softtype1,softtype2,description)
	{
		var SoftItemTemplate='<div style="display:inline;float:left">'
					+'<button id="{{%SoftName}}" class="listsoft" style="background-image:url(../{{%SoftLOGO}});" onclick="applyNode(\'{{%LoginUser}}\',\'{{%SoftName}}\',\'null\')"></button>'
					+'<div class="tip"></div></div>'
					+'<div><label>软件名称：</label><p style="display:inline;width:80px">{{%SoftName}}</p><br><label>软件类型：</label><label>{{%SoftType1}}</label><label>{{%SoftType2}}</label></div><br>'
					+'<div><label>简介：</label>	<div style="width:250px;font-size:12px">{{%SoftDesc}}</div></div>'
		
		var div=document.createElement('div');
		div.className="listitem";
		var SoftItem = new t(SoftItemTemplate);
		div.innerHTML=SoftItem.render({
			SoftName:softname,
			SoftLOGO:logopath,
			SoftType1:softtype1,
			SoftType2:softtype2,
			SoftDesc:description,
			LoginUser:$("#loginuser").val()
		});
		return div;
		
	}
}