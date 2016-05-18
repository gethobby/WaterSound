<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理平台——添加模型</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="robots" content="">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" href="../css/style.css" media="all">
<link rel="stylesheet" href="../css/ui.css" media="all">
<script src="../js/jquery-1.11.1.min.js"></script>
<!--[if IE]><link rel="stylesheet" href="../css/ie.css" media="all" /><![endif]-->
<!--[if lt IE 9]><link rel="stylesheet" href="../css/lt-ie-9.css" media="all" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" href="../css/IE/ie7.css" media="all" /><![endif]-->
<!--[if IE 8]><link rel="stylesheet" href="../css/IE/ie8.css" media="all" /><![endif]-->
</head>
<body  onload="startTime()">
<div class="testing">
	<header class="main">
		<h1><strong>水声数据</strong> 管理平台</h1>
		<span class="button blue" style="margin-top: -23px; float: right; position: relative;">查询</span>
		<input type="text" value="" id="search">
	</header>
	<section class="user" >
		<div class="profile-img">
			<p><img src="../image/button_logout.jpg" alt="" height="40" width="40"> 您好！用户</p>
			<p id="day" style="display:inline">2014.07.24</p>
		</div>
		<div class="buttons">
			<button class="ico-font">⏶</button>
			<span class="button">设置</span>
			<span class="button">帮助</span>
			<span class="button blue"><a href="../login.jsp">登出</a></span>
		</div>
	</section>
</div>
<nav>
	<ul id="navBar">
		<li class="section">
			<a href="ModelMain.jsp" class="models" title="Models"><span class="icon">&#59214;</span> 数据管理</a>
			<ul class="submenu" style="display:block">
				<li><a href="ModelList.jsp">几何模型库</a></li>
				<li><a href="ModelMain.jsp">Benchmark库</a></li>
				<li class="last"><a href="ModelMain.jsp">仿真结果库</a></li>
			</ul>
		</li>
		<li><a href="ComputeMain.jsp" class="computes" title="Computes"><span class="icon">&#59214;</span> 模型计算</a></li>
		<li><a href="ResultMain.jsp" class="results" title="Results"><span class="icon">&#59214;</span> 结果验证</a></li>
		<li></li>
	</ul>
</nav>
<div id="alert">
	<section class="alert">
		<div class="green">	
			<p>当前模型计算进行中 <a href="#">3 项</a>和等待计算 <a href="#">7项</a>,详情参看计算任务管理</p>
				<span class="close" onclick="document.getElementById('alert').close();">✖</span>
			</div>
	</section>
</div>
<section id="NewModelfile" class="content">
	<div class="widget-container">
		<div style="margin-left:auto;margin-right:auto;width:30%">
			<h1><strong>上传模型文件</strong></h1><br>
			目标名称<strong style="color:red">*</strong>：<input id="TargetObject" type="text" disabled="disabled" style="width:150px" value="<%=new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8") %>">
			<input id="TargetObjectID" type="hidden" value="<%=request.getParameter("ID") %>">
			<br>
			模型文件<strong style="color:red">*</strong>:
			<div id="dropzone">
				<form action="../UploadServlet?targetID=<%=request.getParameter("ID") %>" class="dropzone" id="my-awesome-dropzone" enctype="multipart/form-data" method="post" name="form" target="hidden_frame">
					<input id="file" name="file" type="file" style="width:180px"><input id="FileToUpload" type="hidden"><a style="color:red;display:none">请选择模型文件</a>
					<div id="msg" style="display:inline"></div>
					<input type="submit" value="上传文件" style="width:80px">
					<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe> 
				</form>
				文件大小：<input id="FileSize" type="text" disabled="disabled" style="width:80px">K
			</div>
			简介：<input id="FileDescription" type="text" style="width:150px"><br>
			密级<strong style="color:red">*</strong>：
			<select id="FileSecretLevel">
				<option></option>
				<option>绝密</option>
				<option>机密</option>
				<option>秘密</option>
				<option>内部</option>
				<option>公开</option>
			</select><a style="color:red;display:none">✖</a><br>
			适用软件：
			<div> 
				<div id="SelectedSoft"></div>
				<div id="AllSoft">
				</div>
			</div>
			<div style="clear:both"></div>
			来源<strong style="color:red">*</strong>：
			<select id="FileSource">
				<option></option>
				<option>采购</option>
				<option>自主开发</option>
				<option>情报</option>
				<option>其他</option>
			</select><a style="color:red;display:none">✖</a><br>
			联系人<strong style="color:red">*</strong>：<input id="ContactPerson" type="text" style="width:150px"><a style="color:red;display:none">✖</a><br>
			单位名称<strong style="color:red">*</strong>：<div style="display:inline"><select id="ContactComp"><option>………………公司</option></select></div><a style="color:red;display:none">✖</a><br>
			通信地址<strong style="color:red">*</strong>：<input id="ContactAddress" type="text" style="width:150px"><a style="color:red;display:none">✖</a><br>
			邮政编码<strong style="color:red">*</strong>：<input id="ContactZip" type="text" style="width:150px"><a style="color:red;display:none">✖</a><br>
			电话<strong style="color:red">*</strong>：<input id="ContactTel" type="text" style="width:150px"><a style="color:red;display:none">✖</a><br>
			电子邮件<strong style="color:red">*</strong>：<input id="ContactEmail" type="text" style="width:150px"><a style="color:red;display:none">✖</a>
			<div>
				<button class="green" id="uploadFile"onclick="uploadModelFile()" value="上传文件">确认上传</button>
			</div>
		</div>
							
	</div>
</section>
<div id="PageCover" style="width: 100%; left: 0px; top: 0px; height: 100%; position: fixed; -webkit-user-select: none; z-index: 99999;background: tan;filter: alpha(Opacity=80);-moz-opacity: 0.5;opacity: 0.5;display:none">
</div>
<div id="ks-component702" role="dialog" aria-labelledby="ks-stdmod-header-ks-component702" style="width: 600px; height: 360px;  left: 35%; top:30%;position: absolute;z-index: 99999;display:none;" aria-hidden="false">
	<section id="notify"class="widget">
		<header>
			<span class="icon">&#9888;</span>
			<hgroup>
				<h1>系统提示</h1>
				<h2>系统运到一些需要您了解的事情</h2>
			</hgroup>
			<aside>
				<span onclick="modelWindowClose()">
					<a href="#">&#10006;</a>
				</span>
			</aside>
		</header>
		<div class="content">
			创建失败！
		</div>
	</section>
</div>
<script src="../js/t.min.js"></script>
<script src="../js/custom.js"></script>
<script src="../js/jquery.checkbox.min.js"></script>
<script src="../js/jquery.tablesorter.min.js"></script>
<script src="../js/timerFunction.js"></script>
<script src="../js/fileUpload.js"></script>
<script>
$(document).ready(function(){
	if(document.getElementById("softitem")!=undefined)
		document.getElemntById("selectall").css.display="inline";
});
loadContactCompany("ContactComp");
loadAllSoft();
function callback(msg,filePath,fileSize)   
{   
	if(filePath){
    	document.getElementById("FileToUpload").value = filePath;   
    	document.getElementById("FileSize").value=fileSize;
    	document.getElementById("msg").innerHTML = '<font color="green">'+msg+'</font>';
    	
	}
	else{
		document.getElementById("msg").innerHTML = '<font color="red">'+msg+'</font>';
		document.getElementById("FileToUpload").value = ""; 
		document.getElementById("FileSize").value="";
	}
   
} 

</script>
</body>
</html>