<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
if(session.getAttribute("username")==null) {
    response.sendRedirect("/WaterSound/login.jsp?frompage=ModelList.jsp");
 }
else if (request.getAttribute("SavedNodeDBList") == null) {
%>
<jsp:forward page="/GetSavedNodeList">
<jsp:param value="SavedNodeDB" name="type"/>
</jsp:forward>
<% 
}
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理平台——目标信息</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="robots" content="">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" href="../css/style.css" media="all">
<link rel="stylesheet" href="../css/ui.css" media="all">
<link rel="stylesheet" href="../css/toolist.css" media="all">
<script charset="UTF-8" src="../js/jquery-1.11.1.min.js"></script>
<!--[if IE]><link rel="stylesheet" href="../css/ie.css" media="all" /><![endif]-->
<!--[if lt IE 9]><link rel="stylesheet" href="../css/lt-ie-9.css" media="all" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" href="../css/IE/ie7.css" media="all" /><![endif]-->
<!--[if IE 8]><link rel="stylesheet" href="../css/IE/ie8.css" media="all" /><![endif]-->
</head>
<body onload="startTime()"> 
<div class="testing">
	<header class="main">
		<h1><strong>水声数据</strong> 管理平台</h1>
		<span class="button blue" style="margin-top: -23px; float: right; position: relative;" onclick="window.open('SearchModelFile.jsp')">查询</span>
		<input type="text" value="模型名称" id="search">
	</header>
	<section class="user" >
		<div class="profile-img">
			<p><img src="../image/button_logout.jpg" alt="" height="40" width="40"> 您好！<%=session.getAttribute("username")%></p>
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
			<a href="ModelList.jsp" class="models" title="Models"><span class="icon">&#59214;</span> 模型管理</a>
			<!-- <ul class="submenu" style="display:block">
				<li><a href="ModelList.jsp">几何模型库</a></li>
				<li><a href="ModelMain.jsp">Benchmark库</a></li>
				<li class="last"><a href="ModelMain.jsp">仿真结果库</a></li>
			</ul> -->
		</li>
		<li><a href="ComputeMain.jsp" class="computes" title="Computes"><span class="icon">&#59214;</span> 建模软件</a></li>
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
<section id="ObjectList" class="content">
	<div class="widget-container" style="height:auto">
		<section class="widget" style="min-height:300px;width:80%;margin-left:5%" > 
		
		   	<div title="存储信息" style="position: relative; width: 90%; margin: 20px 13px;">
					<div>
					<label>存储节点:</label> 
					<select id="DB_NO">
					   <c:forEach items="${SavedNodeDBList}" var="DB" varStatus="status">
					        <option  value='${DB[1]}'>${DB[1]}</option>
					    </c:forEach>
					</select>
						<button id="DB_Bnt" name="load" value="加载" class="right-btn" onclick="GetModelList()" style="font-size: 10px; background: #719e37;margin-top:-15px;width:60px">加载</button>				   
					</div>			
			</div>
		    
			<header> 
				<span class="icon">📈</span> 
				<hgroup>
					<h1>模型文件</h1>
					<h2>当前表中文件列表</h2>
				</hgroup> 
				<aside>
				<button class="left-btn" style="font-size: 10px; background: #719e37;margin-top:-15px" onclick="document.getElementById('notify').style.display='none';jianmo();">建模</button>
					<button class="right-btn" style="font-size: 10px; background: #719e37;margin-top:-15px" onclick="newObjectinfo()">+上传模型</button>
				</aside> 
			</header>
			<div class="content no-padding timeline" style="margin-left:20px；height:auto">
			<table id="myTable" border="0">
				<thead>
					 <tr>
						<th class="header" style="min-width:50px;padding-left:20px;">编号</th>
						<th class="header" style="min-width:50px;padding-left:20px;">模型名称</th>
						<th class="header" style="min-width:50px;padding-left:20px;">存储地址</th>
						<th class="header" style="min-width:50px;padding-left:20px;">适用软件</th>
						<th class="header" style="min-width:50px;padding-left:20px;">操作</th>
					</tr>
				</thead>
				<tbody id="filename">
					<c:forEach items="${Modellist}" var="item" varStatus="status">
					<tr >
						<td><input name="model" type="radio" style="width: 20px" ><strong>NO.</strong>${item[0]}<input type="hidden" value="${item[0]}"></td>
						<td><a href="#" onclick="ModifyModelFileinfo('${item[0]}')">${item[1]}</a></td>
						<td>${item[2]}</td>
						<td>${item[3]}</td>
						<!-- <td><span onclick="viewmodel(${status.index})"><a href="#" >查看模型</a></span></td> -->
						<td><span onclick="downloadModelFile('${item[1]}')"><a href="#" >下载</a></span></td>
					</tr>
					<tr style="display:none">
						<td colspan="5">
							<div>
								<table id="myTable" border="0">
									<tbody>
									</tbody>
								</table>
							</div>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		</section>
	</div>
</section>
<div id="PageCover" style="width: 100%; left: 0px; top: 0px; height: 100%; position: fixed; -webkit-user-select: none; z-index: 99999;background: tan;filter: alpha(Opacity=80);-moz-opacity: 0.5;opacity: 0.5;display:none">
</div>
<div id="ks-component702" role="dialog" aria-labelledby="ks-stdmod-header-ks-component702" style="width: 600px; height: 360px;  left: 35%; top:30%;position: absolute;display:none;z-index: 99999;" aria-hidden="false;">
	<section class="widget" style="min-height: 360px;">
		<header>
			<span class="icon">&#59153;</span>
			<hgroup>
				<h1>建模仿真</h1>
				<h2>修改模型或仿真计算</h2>
			</hgroup>
			<aside>
				<span onclick="modelWindowClose()">
					<a href="#">✖</a>
				</span>
			</aside>
		</header>
		<div id="availableSoft" class="content">
			当前选中模型可用工具：
			<div id="softlist">
				<div><button class="listsoft"></button></div>
				<div><button class="listsoft"></button></div>
	 			<div><button class="listsoft"></button></div>
	 			<div><button class="listsoft"></button></div>
 			</div>
 			<input type="hidden" id="selectedmodelfile">
 			<input type="hidden" id="loginuser" value="<%=session.getAttribute("username")%>">
 			<section id="notify"class="widget"  style="margin-top:10px;margin-left:auto;margin-right:auto;">
				<header>
					<span class="icon">&#9888;</span>
					<hgroup>
						<h1>系统提示</h1>
						<h2>系统运到一些需要您了解的事情</h2>
					</hgroup>
					<aside>
						<span onclick="document.getElementById('notify').style.display='none'">
							<a href="#">&#10006;</a>
						</span>
					</aside>
				</header>
				<div class="content">
					No Node available！
				</div>
			</section>
		</div>
	</section>
	<section id="NewObject" class="widget" style="min-height: 360px;display:none">
		<header>
			<span class="icon">&#59153;</span>
			<hgroup>
				<h1>新模型</h1>
				<h2>上传模型文件到当前存储节点</h2>
			</hgroup>
			<aside>
				<span onclick="modelWindowClose();closenewObjectinfo();">
					<a href="#">✖</a>
				</span>
			</aside>
		</header>
		<div class="content">
		模型位置：
			<form action="" id="form1" name="form1" encType="multipart/form-data"  method="post" target="hidden_frame" >   
    			<input type="file" id="file" name="file"  onchange="filledModelName()" style="width:150px"><input id="ObjectLogo" type="hidden">   
    			<span id="msg"></span><br><font color="red">请选择系统指定模型文件夹内文件上传</font>                 
    			<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>   
			</form> <a style="color:red;display:none">✖</a><br>  
		   模型名称：<input type="text"  id="ObjectName" readOnly="true" style="width:250px"><a style="color:red;display:none">✖</a><br>
		     适用软件：
			<div> 
				<div id="SelectedSoft"></div>
				<div id="AllSoft">
				</div>
			</div><a style="color:red;display:none">✖</a><br>
		   <input type="text" id="modelDes"  style="width:250px"  value="简介"/><a style="color:red;display:none">✖</a><br>
		<button class="blue" onclick="upmodelfile()">确认上传</button>
		</div>
	</section>
</div>
<div id="rdpinfo">
	<input type="hidden" id="saveforIPAddress">
	<input type="hidden" id="saveforAccount">
	<input type="hidden" id="saveforPassword">
	<input type="hidden" id="saveforPort">
	<input type="hidden" id="saveforSoft">
	<input type="hidden" id="saveforLoginuser">
	<input type="hidden" id="saveforModel">
</div>
<script src="../js/custom.js"></script>
<script src="../js/t.min.js"></script>
<script src="../js/cycle.js"></script>
<script src="../js/jquery.checkbox.min.js"></script>
<script src="../js/timerFunction.js"></script>
<script src="../js/tableData.js"></script>
<script src="../js/Rdp.js"></script>
<script src="../js/ModelList.js"></script>
<script type="text/javascript">
// Feature slider for graphs
$('.cycle').cycle({
	fx: "scrollHorz",
	timeout: 0,
    slideResize: 0,
    prev:    '.left-btn', 
    next:    '.right-btn'
});
loadAllSoft(); // 仿照fileUpload,加载所有可选的软件信息
function callback(msg,picPath)   
{   
	if(picPath){
    	document.getElementById("ObjectLogo").value = picPath;   
    	document.getElementById("msg").innerHTML = '<font color="green">'+msg+'</font>';  
	}
	else{
		document.getElementById("msg").innerHTML = '<font color="red">'+msg+'</font>';
	}
} 

function closenewObjectinfo(){
	var targetsection=document.getElementById('NewObject');
	targetsection.previousElementSibling.style.display='';
	targetsection.style.display='none';
}
</script>
</body>
</html>