/*加载节点信息*/
function loadNodeInfo(nodeid)
{
	if(nodeid){
		$("#NodeID").val(nodeid);
		$.ajax({
	    	url: '/WaterSound/GetDetailInfo?infotype=node&id='+nodeid+"&tid="+Math.random(),
	    	success:function(data){	
	    		if(data.length>0){
	    			var nodeinfo=data[0];
	    			$("#NodeIPAddress").html(nodeinfo["IPAddress"]);
	    			$("#NodeSoft").html(nodeinfo["softname"]);
	    			if(nodeinfo["status"]){$("#NodeStatus").html("停用");}
	    			else{$("#NodeStatus").html("正常");}
	    			$("#NodeMaxUse").html(nodeinfo["maxcount"]);
	    		}
	    	}
		});
	}

}
/*加载节点资源信息*/
function loadResourceInfo(nodeid)
{
	if(nodeid){
		$("#NodeID").val(nodeid);
		$.ajax({
	    	url: '/WaterSound/GetDetailInfo?infotype=resource&id='+nodeid+"&tid="+Math.random(),
	    	success:function(data){	
	    		if(data.length>0){
	    			var tbody=document.getElementById("NodeAccount").getElementsByTagName("tbody")[0];
	    			tbody.innerHTML="";
	    			for(var i=0;i<data.length;i++)
	    			{
	    				var tr=createAccountTR(i+1,data[i]["ID"],data[i]["account"],data[i]["password"],data[i]["port"]);
	    				tbody.appendChild(tr);
	    			}
	    		}
	    	}
		});
	}
	function createAccountTR(index,accountid,accountname,password,port)
	{
		var tdcol1Tempalte='{{%NO}}<input type="hidden" value="{{%id}}">';
		var tdcol2Tempalte='<label>{{%name}}</label><input type="hidden" value="{{%psw}}">';
		var tdcol3Tempalte='<label>{{%portNO}}</label>';
		var tdcol4Tempalte='<a href="#" onclick="showResourceinfo_M(this)">修改</a>';
		
		var tr = document.createElement('tr');
		tr.style.height="30px";
		
		var td1=document.createElement('td');
		var td2=document.createElement('td');
		var td3=document.createElement('td');
		var td4=document.createElement('td');
		td1.innerHTML=new t(tdcol1Tempalte).render({NO:index,id:accountid});
		td2.innerHTML=new t(tdcol2Tempalte).render({name:accountname,psw:password});
		td3.innerHTML=new t(tdcol3Tempalte).render({portNO:port});
		td4.innerHTML=new t(tdcol4Tempalte).render({});

		tr.appendChild(td1);
		tr.appendChild(td2);
		tr.appendChild(td3);
		tr.appendChild(td4);
		
		return tr;
	}

}
/*修改可用节点资源信息*/
function showResourceinfo_M(source)
{
	$("#NodeAccount_Modify").css("display","");
	if(source)
	{//修改已有账户
		var tr=source.parentNode.parentNode;
		var hiddeninputs=tr.getElementsByTagName("input");
		var labels=tr.getElementsByTagName("label");
		$("#AccountID").val(hiddeninputs[0].value);
		$("#AccountName").val(labels[0].innerHTML);
		$("#Password").val(hiddeninputs[1].value);
		$("#PswRepeat").val(hiddeninputs[1].value);
		$("#ListenPort").val(labels[1].innerHTML);
	}
	else{//添加账户
		$("#AccountID").val("");
		$("#AccountName").val("");
		$("#Password").val("");
		$("#PswRepeat").val("");
		$("#ListenPort").val("");
	}
}
function cancelResourceinfo_M(){
	$("#NodeAccount_Modify").css("display","none");
}
function modifyResourceinfo()
{
	if(!checkInput()){return;}
	var accountID=document.getElementById("AccountID").value;
	var jsonObject=new Object();
	var updateurl="/WaterSound/UpdateNodeinfo?type=resource&id="+$("#NodeID").val();
	if(accountID){
		jsonObject.AccountID=accountID;
	}

	jsonObject.AccountName=$("#AccountName").val();
	jsonObject.Password=$("#Password").val();
	jsonObject.Port=$("#ListenPort").val();
	jsonObject.Softs=$("#NodeSoft").html();
	
	$.ajax({   
	     type: "POST",   
	     url: updateurl,   
	     data:jsonObject,
	     success:function(str_response){
	    	 if("success"==str_response)
	    	 {
	    		 cancelResourceinfo_M();
	    		 loadResourceInfo($("#NodeID").val());
	    	 }

	     }
	});
	function checkInput()
	{
		var verify=true;
		if(!$("#AccountName").val())
		{
			$("#AccountName").next().css("display","");
			verify=false;
		}
		else
		{
			$("#AccountName").next().css("display","none");
		}
		if(!$("#Password").val())
		{
			$("#Password").next().css("display","");
			verify=false;
		}
		else
		{
			$("#Password").next().css("display","none");
		}
		if(!$("#PswRepeat").val()||$("#Password").val()!=$("#PswRepeat").val())
		{
			$("#PswRepeat").next().css("display","");
			verify=false;
		}
		else
		{
			$("#PswRepeat").next().css("display","none");
		}
		if(!$("#ListenPort").val()||!(new Number($("#ListenPort").val()) instanceof Number))
		{
			$("#ListenPort").next().css("display","");
			verify=false;
		}
		else
		{
			$("#ListenPort").next().css("display","none");
		}
		
		return verify;
	}
}
/*修改节点信息*/
function showNodeinfo_M()
{
	if($("#NodeAccount_Modify").css("display")!="none"){
		alert("请先取消或者保存账户的编辑！");
		return;
	}
	$("#Nodeinfo_Modify").css("display","");
	$("#Nodeinfo").css("display","none");
	
	$("#NodeIPAddress_Modify").val($("#NodeIPAddress").html());
	loadAllSoft("NodeSoft");
	$("#NodeStatus_Modify").val($("#NodeStatus").html());
	$("#NodeMaxUse_Modify").val($("#NodeMaxUse").html());
}
function cancelNodeinfo_M(){

	$("#Nodeinfo_Modify").css("display","none");
	$("#Nodeinfo").css("display","");
}
function modifyNodeinfo()
{
	if(!checkInput()){return;}
	var nodeID=$("#NodeID").val();
	var softs=$('#AllSoft div input').toArray();
	var AccessSoftNames=[];
	for(i=0;i<softs.length-1;i+=2)
	{
		if(softs[i].checked==true){
			AccessSoftNames.push(softs[i].value);
		}
	}
	$("#NodeSoft_Modify").val(AccessSoftNames.join(","));
	var jsonObject=new Object();
	var updateurl="/WaterSound/UpdateNodeinfo?type=node&id="+nodeID;
	jsonObject.NodeIPAddress=$("#NodeIPAddress_Modify").val();
	jsonObject.Softs=$("#NodeSoft_Modify").val();
	if($("#NodeStatus_Modify").val()=="正常"){jsonObject.NodeStatus=0;}
	else {jsonObject.NodeStatus=1;}
	jsonObject.NodeMaxUse=$("#NodeMaxUse_Modify").val();
	
	$.ajax({   
	     type: "POST",   
	     url: updateurl,   
	     data:jsonObject,
	     success:function(str_response){
	    	 if("success"==str_response)
	    	 {
	    		 cancelNodeinfo_M();
	    		 loadNodeInfo(nodeID);
	    		 alert("修改成功！");
	    	 }
	    	 else{
	    		 alert("修改失败！");
	    	 }
	     }
	});
	function checkInput()
	{
		var verified=true;
		
		//此处只是检查IP的格式的错误
		if($("#NodeIPAddress_Modify").val())
		{
			var numbers=$("#NodeIPAddress_Modify").val().split(".");
			if(numbers.length!=4)
			{ 
				$("#NodeIPAddress_Modify").next().css("display","");
				verified=false;
			}
			else{
				for(var i=0;i<4;i++)
				{
					if(numbers[i]<0&&numbers[i]>255){
						$("#NodeIPAddress_Modify").next().css("display","");
						verified=false;
					}
				}
				if(verified){$("#NodeIPAddress_Modify").next().css("display","none");}
			}
		}
		else{
			$("#NodeIPAddress_Modify").next().css("display","");
			verified=false;
		}
		return verified;
	}
}