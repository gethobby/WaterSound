function newAccount()
{
	document.getElementById("NewAccount").style.display="block";
	$("#NewAccount a").css("display","none");
	document.getElementById("AccountName").value="";
	document.getElementById("Password").value="";
	document.getElementById("PswRepeat").value="";
	document.getElementById("ListenPort").value="";
}

function saveAccount()
{
	var accountname=document.getElementById("AccountName");
	var psw=document.getElementById("Password");
	var pswrepeat=document.getElementById("PswRepeat");
	var listenport=document.getElementById("ListenPort");
	if(!(accountname.value&&psw.value&&pswrepeat.value&&listenport.value)){
		accountname.nextSibling.style.display="none";
		psw.nextSibling.style.display="none";
		pswrepeat.nextSibling.style.display="none";
		if(!accountname.value){ accountname.nextSibling.style.display="inline";}
		else{accountname.nextSibling.style.display="none";}
		if(!psw.value){ psw.nextSibling.style.display="inline";}
		else{psw.nextSibling.style.display="none";}
		if(!pswrepeat.value) {pswrepeat.nextSibling.style.display="inline";}
		else{pswrepeat.nextSibling.style.display="none";}
		if(!listenport.value){ listenport.nextSibling.style.display="inline";}
		else{listenport.nextSibling.style.display="none";}
		return;
	}
	else{
		if(psw.value.toString()!=pswrepeat.value.toString()){pswrepeat.nextSibling.style.display="inline";return;}
		else{pswrepeat.nextSibling.style.display="none";}
		if(listenport.value instanceof Number||!isNaN(parseInt(listenport.value))){ listenport.nextSibling.style.display="none"; }
		else{listenport.nextSibling.style.display="inline";return;}
	}

	var tr = createAccountTR(accountname.value,psw.value,listenport.value);
	document.getElementById("accountitem").appendChild(tr);
	document.getElementById("NewAccount").style.display="none";
	
	function createAccountTR(Account,Password,Port)
	{
		var tdcol1Template='<h1>{{%account}}</h1><input type="hidden" value="{{%password}}"><input type="hidden" value="{{%port}}">';
		var tdcol2Template='<a onclick="modifyAccount(this)">修改</a><a onclick="removeAccount(this)">删除</a>';
		
		var tr=document.createElement('tr');
		var td1=document.createElement('td');
		var td2=document.createElement('td');
		
		td1.innerHTML=new t(tdcol1Template).render({account:Account,password:Password,port:Port});
		td2.innerHTML=new t(tdcol2Template).render({});
		tr.appendChild(td1);
		tr.appendChild(td2);
		
		return tr;
	}
}
function removeAccount(source)
{
	var tr=source.parentNode.parentNode;
	var table=tr.parentNode;
	table.removeChild(tr);
}
function modifyAccount(source)
{
	newAccount();
	var targetTd=source.parentNode.parentNode.getElementsByTagName("td")[0];
	document.getElementById("AccountName").value=targetTd.getElementsByTagName("h1")[0].innerHTML;
	document.getElementById("Password").value=targetTd.getElementsByTagName("input")[0].value;
	document.getElementById("PswRepeat").value=targetTd.getElementsByTagName("input")[0].value;
	document.getElementById("ListenPort").value=targetTd.getElementsByTagName("input")[1].value;
	removeAccount(source);
}