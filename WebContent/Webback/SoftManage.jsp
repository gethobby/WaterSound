<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% if (request.getAttribute("softlist") == null) {
%>
<jsp:forward page="/GetSoftList">
<jsp:param value="1" name="getdata"/>
</jsp:forward>
<% 
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理平台——后台——软件管理</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="robots" content="">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" href="../css/style.css" media="all">
<link rel="stylesheet" href="../css/ui.css" media="all">
<link rel="stylesheet" href="../css/webUIs.css" media="all">
<link rel="stylesheet" href="../css/toolist.css" media="all">
<script src="../js/jquery-1.11.1.min.js"></script>
<!--[if IE]><link rel="stylesheet" href="../css/ie.css" media="all" /><![endif]-->
<!--[if lt IE 9]><link rel="stylesheet" href="../css/lt-ie-9.css" media="all" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" href="../css/IE/ie7.css" media="all" /><![endif]-->
<!--[if IE 8]><link rel="stylesheet" href="../css/IE/ie8.css" media="all" /><![endif]-->
</head>
<body> 
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
			<span class="button blue"><a href="../login_back.jsp">登出</a></span>
		</div>
	</section>
</div>
<nav>
	<ul id="navBar">
		<li>
			<a href="UserManage.jsp" class="users" title="Users"><span class="icon">&#128711;</span>用户管理</a>
		</li>
		<li>
			<a href="NodeManage.jsp" class="nodes" title="Nodes"><span class="icon">&#128711;</span> 节点管理</a>
			<ul class="submenu">
				<li><a href="PlatformlogNodeconcern.jsp">节点使用情况</a></li>
				<li class="last"><a href="Nodeinfo.jsp">添加节点</a></li>
			</ul>
		</li>
		<li  class="section">
			<a href="SoftManage.jsp" class="softs" title="Softs"><span class="icon">&#128711;</span>软件管理</a>
			<ul class="submenu">
				<li class="last"><a href="Softinfo.jsp">添加软件</a></li>
			</ul>
		</li>
		<li>
			<a href="PlatformlogUserconcern.jsp" class="logs" title="Logs"><span class="icon">&#128711;</span>日志管理</a>
		</li>
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
<section class="content">
	<div class="widget-container">
		<section class="widget" style="min-height:300px;height:auto;width:80%;margin-left:5%" > 
			<header> 
				<span class="icon">📈</span> 
				<hgroup>
					<h1>目标特性软件</h1>
					<h2>当前平台拥有的建模仿真、验证工具</h2>
				</hgroup> 
				<aside>
					<!-- <button class="left-btn" style="font-size: 10px; background: #719e37;margin-top:-15px" ><a>添加</button> -->
					<button id="deleteBnt" class="green" style="font-size: 10px; background: #719e37;">删除</button>
				</aside> 
			</header>
			<div class="content no-padding timeline" style="margin-left:20px">
			<table id="myTable" border="0" width="80px">
				<thead>
					 <tr>
						<th class="header" style="min-width:80px;padding-left:20px;">软件编号</th>
						<th class="header" style="min-width:50px;padding-left:20px;">软件名称</th>
						<th class="header" style="min-width:50px;padding-left:20px;">英文名称</th>
						<th class="header" style="min-width:50px;padding-left:20px;">图标</th>
						<th class="header" style="min-width:50px;padding-left:20px;">类型</th>
					</tr>
				</thead>
				<tbody id="softname">
					<c:forEach items="${softlist}" var="item" varStatus="status">
					<tr>
						<td><input type="checkbox" value="${item[0]}">${item[0]}</td>
						<td><a href="#" onclick="ModifySoftinfo(${item[0]})">${item[1]}</a></td>
						<td>${item[3]}</td>
						<td><input class="listsoft" style="background-image:url(../${item[6]});"></td>
						<td>${item[7]}.${item[8]}</td>
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
				<h1>新软件</h1>
				<h2>添加新的建模或者验证软件</h2>
			</hgroup>
			<aside>
				<span onclick="modelWindowClose()">
					<a href="#">✖</a>
				</span>
			</aside>
		</header>
		<div class="content">
			<div style="width:70%;margin:auto">
				软件名称*：<input type="text"><br>
				类型*：<input type="radio" name="softtype" value="建模" style="width:20px" >建模<input type="radio" name="softtype" value="验证" style="width:20px">验证<br/>
				接受模型类型*：请用逗号隔开（如stl,obj……）<input type="text"><br>
				描述：<textarea style="height:80px"></textarea>
				<button class="blue" style="margin-left:auto;margin-right:auto">添加</button>
			</div>
			
		</div>
	</section>
</div>
<div id="topic" style="display: none; z-index:2"> 
	<div class="adorn"></div> 
	<div class="inner_html">
		英文名称：<h1></h1>
		版本：<h1></h1>
		发布/更新时间：<h1></h1>
		类型：<h1></h1><h1></h1>
		密级：<h1></h1>
		联系人：<h1></h1>
	</div> 
</div> 
<script src="../js/custom.js"></script>
<script src="../js/cycle.js"></script>
<script src="../js/jquery.checkbox.min.js"></script>
<script src="../js/jquery.tablesorter.min.js"></script>
<script src="../js/timerFunction.js"></script>
<script src="../js/tableData.js"></script>
<script src="../js/flowtip.js"></script>
<script src="../js/SoftManage.js"></script>
<script type="text/javascript">
// Feature slider for graphs
$('.cycle').cycle({
	fx: "scrollHorz",
	timeout: 0,
    slideResize: 0,
    prev:    '.left-btn', 
    next:    '.right-btn'
});
window.onload = function() {
	var aLi = document.getElementById('softname').getElementsByTagName('tr');

	bindTopic(aLi);
	
	startTime();
};
function ModifySoftinfo(softID)
{
	var objpage=window.open("SoftinfoModify.jsp?softID="+softID);
}
</script>
</body>
</html>