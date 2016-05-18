<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理平台——数据管理——软件搜索</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="robots" content="">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" href="../css/style.css" media="all">
<link rel="stylesheet" href="../css/ui.css" media="all">
<link rel="stylesheet" href="../css/toolist.css" media="all">
<script src="../js/jquery-1.11.1.min.js"></script>
<!--[if IE]><link rel="stylesheet" href="../css/ie.css" media="all" /><![endif]-->
<!--[if lt IE 9]><link rel="stylesheet" href="../css/lt-ie-9.css" media="all" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" href="../css/IE/ie7.css" media="all" /><![endif]-->
<!--[if IE 8]><link rel="stylesheet" href="../css/IE/ie8.css" media="all" /><![endif]-->
</head>
<body onload="startTime()"> 
<div class="testing">
	<header class="main">
		<h1><strong>水声数据</strong> 管理平台</h1>
	</header>
	<section class="user" >
		<div class="profile-img">
			<p><img src="../image/button_logout.jpg" alt="" height="40" width="40"> 您好！<%=session.getAttribute("username")%></p>
			<input type="hidden" id="loginuser" value="<%=session.getAttribute("username")%>">
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
		<li>
			<a href="SearchModelFile.jsp" class="models" title="Models"><span class="icon">&#59214;</span>搜索模型文件</a>
		</li>
		<li  class="section">
			<a href="SearchObjectiveSoft.jsp" class="computes" title="Computes"><span class="icon">&#59214;</span>搜索特性软件</a>
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
		<section class="widget" style="min-height:200px;width:80%;margin-left:5%" > 
			<header> 
				<span class="icon">📈</span> 
				<hgroup>
					<h1>搜索结果</h1>
					<h2>匹配的特性软件</h2>
				</hgroup> 
				<aside>
				</aside> 
			</header>
			<div class="content no-padding timeline" style="margin-left:20px；height:auto;overflow: visible;">
				<div id="AdvancedSearchSection">
					<table>
						<tr style="height:30px">
							<td align="right"><label style="margin-right:20px;margin-left:20px">中文名称：</label></td>
							<td><input id="SoftName"></td>
							<td align="right"><label style="margin-right:20px;margin-left:20px">英文名称：</label></td>
							<td><input id="SoftNameEN"></td>
						</tr>
						<tr style="height:30px">
							<td align="right"><label style="margin-right:20px;margin-left:20px">类型：</label></td>
							<td><div id="SoftType">
								<select id="SoftType1">
									<option></option>
									<option>光学</option>
									<option>电磁</option>
									<option>水声</option>
								</select>
								<select id="SoftType2">
									<option></option>
									<option>目标类</option>
									<option>环境类</option>
									<option>目标与环境复合类</option>
									<option>数据处理与应用类</option>
									<option>其他类</option>
								</select>
								</div>
							</td>
							<td align="right"></td>
							<td><button id="Search" class="blue" onclick="loadSearchSoft()" value="高级搜索">高级搜索</button></td>
						</tr>											
					</table>
				</div>
			</div>
		</section>
		<div id="toolist" style="width:80%;margin-left:6%">
		</div>
	</div>
</section>
<div id="PageCover" style="width: 100%; left: 0px; top: 0px; height: 100%; position: fixed; -webkit-user-select: none; z-index: 99999;background: tan;filter: alpha(Opacity=80);-moz-opacity: 0.5;opacity: 0.5;display:none">
</div>
<div id="ks-component702" role="dialog" aria-labelledby="ks-stdmod-header-ks-component702" style="width: 600px; height: 360px;  left: 35%; top:30%;position: absolute;z-index: 99999;display:none;" aria-hidden="false">

	<input type="hidden" id="loginuser" value="<%=session.getAttribute("username")%>">
	<section id="notify"class="widget"  style="margin-top:10px;margin-left:auto;margin-right:auto;">
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
		在此导入系统提示信息
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
<script src="../js/jquery.tablesorter.min.js"></script>
<script src="../js/timerFunction.js"></script>
<script src="../js/tableData.js"></script>
<script src="../js/Rdp.js"></script>
<script src="../js/Search.js"></script>
<script type="text/javascript">
// Feature slider for graphs
$('.cycle').cycle({
	fx: "scrollHorz",
	timeout: 0,
    slideResize: 0,
    prev:    '.left-btn', 
    next:    '.right-btn'
});
loadSearchSoft();
</script>
</body>
</html>