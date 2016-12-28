<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理平台——后台——添加节点</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="robots" content="">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" href="../css/style.css" media="all">
<link rel="stylesheet" href="../css/ui.css" media="all">
<link rel="stylesheet" href="../css/toolist.css" media="all">
<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/t.min.js"></script>
<!--[if IE]><link rel="stylesheet" href="../css/ie.css" media="all" /><![endif]-->
<!--[if lt IE 9]><link rel="stylesheet" href="../css/lt-ie-9.css" media="all" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" href="../css/IE/ie7.css" media="all" /><![endif]-->
<!--[if IE 8]><link rel="stylesheet" href="../css/IE/ie8.css" media="all" /><![endif]-->

</head>
<body onload="startTime()">
<div class="testing">
	<header class="main">
		<h1><strong>水声数据</strong> 管理平台</h1>
		<span class="button blue" style="margin-top: -23px; float: right; position: relative;" onclick="">查询</span>
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
		<li  class="section">
			<a href="NodeManage.jsp" class="nodes" title="Nodes"><span class="icon">&#128711;</span> 节点管理</a>
			<ul class="submenu">
				<li><a href="PlatformlogNodeconcern.jsp">计算节点使用情况</a></li>
				<li class="last"><a href="Nodeinfo.jsp">添加计算节点</a></li>
			</ul>
		</li>
		<li>
			<a href="SoftManage.jsp" class="users" title="Users"><span class="icon">&#128711;</span>软件管理</a>
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
<section id="NewNode" class="content">
	<div class="widget-container">
		<div style="margin-top:20px;margin-left:auto;margin-right:auto;width:500px">
			<strong style="font-size:20px;display:block">新节点</strong><br>
			节点IP：<input id="newIpAddress" type="text" style="width:70%"><a style="color: red; display: none;">✖</a>
			<br>
			关联软件：
			<div> 
				<div id="SelectedSoft"></div>
				<div id="AllSoft">

				</div>
			</div>
			<div style="clear:both">
				节点账户：
				<div id="accountlist" style="display:inline">
					<button class="blue" onclick="newAccount()" value="添加">添加</button>
					<a style="color: red; display: none;">✖至少添加一个账户</a>
					<table id="accountitem" style="width:300px;margin-top:20px">
					</table>
				</div>
				<div id="NewAccount" style="display:none">
					<div>
					用户账号：<input id="AccountName" type='text' style="width:180px" value=""><a style="color:red;display:none">✖</a>
					</div>
					<div>
					设置密码：<input id="Password" type='password' style="width:180px" value=""><a style="color:red;display:none">✖</a>
					</div>
					<div>
					密码重复：<input id="PswRepeat" type='password' style="width:180px" value=""><a style="color:red;display:none">✖</a>
					</div>
					<div>
					配置端口：<input id="ListenPort" type='text' style="width:180px" value=""><a style="color:red;display:none">✖</a>
					</div>
					<div>
						<button class="green" style="margin-left: 30%;" onclick="saveAccount()" value="保存">保存</button>
					</div>
				</div>
				<button class="green" onclick="insertNewNodeRecord()" value="新建" style="margin-top:20px">确认新建</button>
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
<script src="../js/cycle.js"></script>
<script src="../js/jquery.checkbox.min.js"></script>
<script src="../js/jquery.tablesorter.min.js"></script>
<script src="../js/timerFunction.js"></script>
<script src="../js/template.js"></script>
<script src="../js/Nodeinfo.js"></script>
<script src="../js/tableData.js"></script>
<script type="text/javascript">
$('.cycle').cycle({
	fx: "scrollHorz",
	timeout: 0,
    slideResize: 0,
    prev:    '.left-btn', 
    next:    '.right-btn'
});
$(document).ready(function(){
	if(document.getElementById("softitem")!=undefined)
		document.getElemntById("selectall").css.display="inline";
	
});
loadAllSoft();
</script>
</body>
</html>