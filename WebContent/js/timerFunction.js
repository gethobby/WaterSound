/**
 * 此文件存放母版页所需要的各种函数
 */



	/*
	 * startTime函数是用于动态显示当前时刻的函数
	 */
	function startTime() {
		var today = new Date();
		var week = new Array("Sunday", "Monday", "Tuesday", "Wednesday",
				"Thursday", "Friday", "Saturday");
		var year = today.getFullYear();
		var month = today.getMonth() + 1;
		var date = today.getDate();
		var day = today.getDay();
		var h = today.getHours();
		var m = today.getMinutes();
		var s = today.getSeconds();
		// add a zero in front of numbers<10
		h = checkTime(h);
		m = checkTime(m);
		s = checkTime(s);
		document.getElementById('day').innerHTML = " " + year + "." + month+ "." + date+" " + week[day]+" " + h + ":" + m + ":" + s+ " ";
		var t = setTimeout('startTime()', 500);
	};
	/*
	 *checkTime函数服务于startTime函数，是用于显示时间在未满10的数字前添加0 
	 */
	function checkTime(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	};
	
	/*
	 * selectTab函数是mainpage页面下上传任务子页面的弹出模态页面切换模型文件来源的函数
	 */
//	function selectTab(showSource,e){
//		e=e||window.event;
//		e.target=e.target||e.srcElement;
//		var i=0;
//		var x=document.getElementById("modelsource");
//		var y=x.getElementsByTagName("li");			
//		while(i<2){
//			y[i].className="";
//			i++;
//		}
//		e.target.className="selected";
//		
//		var j=0;
//    	for(j=0;j<2;j++){
//    		document.getElementById(sourceLocation[j]).style.display="none"; 
//    	}
//    	document.getElementById(sourceLocation[showSource]).style.display="block";
//	}
	
	
	/*
	 * modelWindowClose、modelWindowShow函数是mainpage页面下计算任务子页面的上传任务弹出模态页面和关闭的函数
	 */	
	function modelWindowClose()
	{
		document.getElementById("PageCover").style.display="none";
		document.getElementById("ks-component702").style.display="none";
	}
	function modelWindowShow()
	{
		document.getElementById("PageCover").style.display="block";
		document.getElementById("ks-component702").style.display="block";
		document.getElementById("ks-component702").focus();
	}
	
	/*
	 * 
	 */
	//start compute
	function StartCompute(url)
	{
	if (window.XMLHttpRequest)
	  {// code for IE7, Firefox, Opera, etc.
	  var xmlhttp=new XMLHttpRequest();
	  }
	else if (window.ActiveXObject)
	  {// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	if (xmlhttp!=null)
	  {
	  xmlhttp.onreadystatechange=function(){ 
		  if (xmlhttp.readyState==4)
		  {// 4 = "loaded"
		  if (xmlhttp.status==200)
		    {// 200 = "OK"
			  alert(xmlhttp.responseText);
		    }
		  else
		    {
		    alert("Problem retrieving XML data:" + xmlhttp.statusText);
		    }
		  }};
	  xmlhttp.open("GET",url,true);
	  xmlhttp.send(null);
	  }
	else
	  {
	  alert("Your browser does not support XMLHTTP.");
	  }
	}
	function StartThisCompute(taskname,solver)
	{
		alert(taskname);
		StartCompute('/Model3D/StartCompute?taskname='+taskname+'&solver='+solver);
	}
	
	/*
	 * 模型查看中建模
	 */
	function gototoolpage()
	{
//		selectSubContent(4);
//		if(modelid=="null")
//		{
//			document.getElementById('existModel').style.display='none';
//			document.getElementById('newModel').style.display='block';
//		}
//		else{
//			document.getElementById('newModel').style.display='none';
//			document.getElementById('existModel').style.display='block';
//			var x=document.getElementById('modelID');
//			x.style.display='inline';
//			x.innerHTML=modelname;
//		}
	}


	/*
	 *模型工具控制显示
	 */
	function jumptotoolpage(){
		
	}
	
	/*
	 * checkbox全选和取消全选的操作
	 * 参数1.全选checkbox
	 * 参数2.需要操作的checkbox的组名称
	 */
	function toggle(source,checkboxName) {
		var checkboxes = document.getElementsByName(checkboxName);
		for (var i = 0, n = checkboxes.length; i < n; i++) {
			checkboxes[i].checked = source.checked;
		}
	}
	/*
	 * 实现同name的checkbox的单选
	 * */
	function clearOtherSelect(source,checkboxname)
	{
		var selectedstatus=source.checked;
		var items=document.getElementsByName(checkboxname);
		for(var i=0;i<items.length;i++)
		{
			items[i].checked=false;
		}
		source.checked=selectedstatus;
	}
	/*
	 * 弹出网站内部消息模态提示框
	 * 参数1.需要显示的提示框的ID
	 * 参数2.提示内容  //可选
	 */
	function showNotification(elementId,content)
	{
		modelWindowShow();
		var note=document.getElementById(elementId);
		note.style.display='block';
		note.getElementsByTagName("div")[0].innerHTML=content;
	}
	/*加载所有在库单位名称，作为option添加到select里面*/
	function loadContactCompany(selectID)
	{
		$.ajax({
	    	url: '/WaterSound/GetContactCompany',
	    	success:function(data){	
	    		
	    		var selectCompany=document.getElementById(selectID);
	    		var parent=selectCompany.parentNode;
				if(selectCompany){
					parent.removeChild(selectCompany);
				}
				
				selectCompany=document.createElement('select');
				selectCompany.id=selectID;
				
				var option=document.createElement('option');
				selectCompany.add(option);
				
	    		if(data&&data.length>0){
		    		for(var index=0;index<data.length;index++){
			    		 var option=document.createElement('option');
			    		 option.text=data[index]["单位名称"];
			    		 selectCompany.add(option);
		    		}
	    		}
	    		parent.appendChild(selectCompany);
	    	}
		});
	}
	/*加载所有在库软件名称和ID，作为checkbox*/
	function loadAllSoft(target)
	{
		$.ajax({
	    	url: '/WaterSound/GetSoftList?softinfotype=summary',
	    	success:function(data){	
	    		$("#AllSoft").html("");
	    		if(data&&data.length>0){
	    			for(var i=0;i<data.length;i++)
	    			{
	    				var div=createSoftCheckbox(data[i]["softID"],data[i]["softname"]);
	    				document.getElementById("AllSoft").appendChild(div);
	    			}
	    			div=createCheckallCheckbox();
	    			document.getElementById("AllSoft").appendChild(div);
	    			if(loadSelectedSoft&&target){loadSelectedSoft(target);}
	    		}
	    	}
		});
		function createSoftCheckbox(id,name){
			var softtemplate='<input type="checkbox" name="soft" style="padding:0;width:20px" value="{{%softname}}" >{{%softname}}'
				+'<input type="hidden" value="{{%softid}}">';
			var div = document.createElement('div');
			div.id="softitem";
			div.style.display="inline";
			div.style.float="left";
			var softItem = new t(softtemplate);
			div.innerHTML=softItem.render({
				softid:id,
				softname:name
			});
			return div;
		}
		function createCheckallCheckbox(){
			var selectall='<input type="checkbox" style="padding:0;width:20px" id="checkall" onclick="toggle(this,\'soft\')" >全选';
			var div = document.createElement('div');
			div.id="softitem";
			div.style.display="inline";
			div.style.float="right";
			var selectallItem = new t(selectall);
			div.innerHTML=selectallItem.render({});
			return div;
		}
		function loadSelectedSoft(targetHtml){
			var selectedsofts=$("#"+targetHtml).html().split(",");
			var softCheckboxes=document.getElementById("AllSoft").getElementsByTagName("input");
			var len=softCheckboxes.length;
			if(len>0){
				for(var index=0;index<selectedsofts.length;index++){
					for(var i=0;i<(len-1)/2;i++)
					{
						if(selectedsofts[index]==softCheckboxes[i*2].value){
							softCheckboxes[i*2].checked=true;
							break;
						}
					}
				}
			} 
		}
	}
	/*
	 * 根据目标的一级类别获取二级类别
	 */
	function getObjectType2(source)
	{
		var type1value=source.value.trim();
		$.ajax({   
				type: "post",
				url:'/WaterSound/GetType',   
				data:{"type1":type1value},
				success:function(data){
					var divType=document.getElementById("ObjectType");
					var selectType2=divType.getElementsByTagName("select")[1];
					if(selectType2){
						divType.removeChild(selectType2);
					}

					var type2=document.createElement('select');
					type2.id="ObjectType2";
					var option=document.createElement('option');
					type2.add(option);
			    	if(data&&data.length>0)
			    	{
			    		for(var index=0;index<data.length;index++){
				    		 option=document.createElement('option');
				    		 option.text=data[index]["ObjectType2"];
				    		 type2.add(option);
			    		}
			    	}
			    	divType.appendChild(type2);
			     }
		});
	}