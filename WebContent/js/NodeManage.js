
function loadReleaseResource(nodeid)
{
	if(nodeid){
		$("#NodeID").val(nodeid);
		$.ajax({
	    	url: '/WaterSound/GetDetailInfo?infotype=resource&id='+nodeid+"&tid="+Math.random(),
	    	success:function(data){	
	    		if(data.length>0){
	    			var tbody=document.getElementById("ReleaseNode").getElementsByTagName("tbody")[0];
	    			tbody.innerHTML="";
	    			for(var i=0;i<data.length;i++)
	    			{
	    				var tr=createReleaseTR(i+1,data[i]["ID"],data[i]["account"],data[i]["occupied"]);
	    				tbody.appendChild(tr);
	    			}
	    		}
	    	}
		});
	}
	function createReleaseTR(index,accountid,accountname,accountstatus)
	{
		var tdcol1Tempalte='{{%NO}}<input type="hidden" value="{{%id}}">';
		var tdcol2Tempalte='<label>{{%name}}</label>';
		
		var tdcol3Tempalte1='<label style="color:red">使用中</label>';
		var tdcol3Tempalte2='<label style="color:green">空闲</label>';
		
		var tdcol4Tempalte1='<a href="#" onclick="changeResourceStatus({{%id}},\'release\')">释放资源</a>';
		var tdcol4Tempalte2='<a href="#" onclick="changeResourceStatus({{%id}},\'lock\')">锁定资源</a>';
		
		var tr = document.createElement('tr');
		tr.style.height="30px";
		
		var td1=document.createElement('td');
		var td2=document.createElement('td');
		var td3=document.createElement('td');
		var td4=document.createElement('td');
		
		td1.innerHTML=new t(tdcol1Tempalte).render({NO:index,id:accountid});
		td2.innerHTML=new t(tdcol2Tempalte).render({name:accountname});
		
		if(accountstatus){
			td3.innerHTML=new t(tdcol3Tempalte1).render({});
			td4.innerHTML=new t(tdcol4Tempalte1).render({id:accountid});
		}
		else {
			td3.innerHTML=new t(tdcol3Tempalte2).render({});
			td4.innerHTML=new t(tdcol4Tempalte2).render({id:accountid});
		}

		tr.appendChild(td1);
		tr.appendChild(td2);
		tr.appendChild(td3);
		tr.appendChild(td4);
		
		return tr;
	}

}
function changeResourceStatus(resID,type)
{
	$.ajax({
		url:"/WaterSound/NodeResourceManage?type="+type+"&tid="+Math.random(),
		type:"POST",
		data:{resourceId:resID},
		success:function(res){
			if(res.split(",")=="success"){
				alert("操作成功！")
				loadReleaseResource($("#nodeID").val());
			}
			else{
				alert("操作失败！")
			}
		}
	});

}
