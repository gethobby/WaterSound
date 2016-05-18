function checkInput()
{
	var verified=true;
	
	//此处只是检查IP的格式的错误
	if(document.getElementById('newIpAddress').value)
	{
		var numbers=document.getElementById('newIpAddress').value.split(".");
		if(numbers.length!=4)
		{ 
			document.getElementById('newIpAddress').nextSibling.style.display='inline';
			verified=false;
		}
		else{
			for(var i=0;i<4;i++)
			{
				if(numbers[i]<0&&numbers[i]>255){
					document.getElementById('newIpAddress').nextSibling.style.display='inline';
					verified=false;
				}
			}
			if(verified){document.getElementById('newIpAddress').nextSibling.style.display='none';}
		}
	}
	else{
		document.getElementById('newIpAddress').nextSibling.style.display='inline';
		verified=false;
	}
	//每个新节点至少要有一个账号
	var accounts=$('#accountitem tr').toArray();
	if(accounts.length<1){
		$("#accountlist button").next().css("display","inline");
		verified=false;
	}
	else{
		$("#accountlist button").next().css("display","none");
	}
	
	return verified;
	
}
function insertNewNodeRecord()
{
	var jsonObject={
		"IPAddress":"",
		"AccountNames[]":[],
		"AccountPSWs[]":[],
		"ListenPorts[]":[],
		"MaxCount":0,
		"AccessSoftNames[]":[]
	}
	if(checkInput())
	{
		jsonObject["IPAddress"]=document.getElementById("newIpAddress").value;
		var accountsName=$('#accountitem tr td h1').toArray();
		var accountsInfo=$('#accountitem tr td input').toArray();
		for(var i=0;i<accountsName.length;i++)
		{
			jsonObject["AccountNames[]"].push(accountsName[i].innerHTML);
			jsonObject["AccountPSWs[]"].push(accountsInfo[i*2].value);
			jsonObject["ListenPorts[]"].push(accountsInfo[i*2+1].value);
		}
		jsonObject["MaxCount"]=accountsName.length;
		var softs=$('#AllSoft div input').toArray();
		for(i=0;i<softs.length-1;i+=2)
		{
			if(softs[i].checked==true){
				jsonObject["AccessSoftNames[]"].push(softs[i].value);
			}
		}
		
		$.ajax({   
		     type: "POST",   
		     url: "/WaterSound/CreateNewRecord?type=node",   
		     data:jsonObject,
		     //traditional: true,
		     success:function(str_response){
		    	 if("success"==str_response)
		    	 {showNotification("notify","创建成功！");}
		    	 else{showNotification("notify","创建失败！");}
		     }
		});
		
	}
	
}