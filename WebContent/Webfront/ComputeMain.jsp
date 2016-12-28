<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% 
if(session.getAttribute("username")==null) 
    	response.sendRedirect("/WaterSound/login.jsp?frompage=ComputeMain.jsp");
else if (request.getAttribute("softlist") == null) {
	request.setAttribute("description", "true");
%>
<jsp:forward page="/GetSoftList">
<jsp:param value="1" name="getdata"/>
</jsp:forward>
<% 
}
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理软件</title>
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
		<span class="button blue" style="margin-top: -23px; float: right; position: relative;" onclick="searchSoft()">查询</span>
		<input id="search"  type="text"  >
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
		<li>
			<a href="ModelList.jsp" class="models" title="Models"><span class="icon">&#59214;</span> 模型管理</a>
			<!-- <ul class="submenu">
				<li><a href="ModelList.jsp">几何模型库</a></li>
				<li><a href="ModelMain.jsp">Benchmark库</a></li>
				<li class="last"><a href="ModelMain.jsp">仿真结果库</a></li>
			</ul> -->
		</li>
		<li class="section"><a href="ComputeMain.jsp" class="computes" title="Computes"><span class="icon">&#59214;</span> 建模软件</a></li>
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
<section id="hostPage" class="content">
	<div class="widget-container" style="padding-left:30px">
		<div id="toolist">
			<h1>工具列表：</h1>	
			<c:forEach items="${softlist}" var="item" varStatus="status">
			<div class="listitem">
				<div style="display:inline;float:left">
					<button id="${item[1]}" class="listsoft" style="background-image:url(../${item[6]});"
					 onclick="applyNode('<%=session.getAttribute("username")%>','${item[1]}','null')"></button>
					<div class="tip"></div>
				</div>
				<div>
					<label>软件名称：</label><p style="display:inline;width:80px">${item[1]}</p>
					<br>
					<label>软件类型：</label><label>${item[7]}</label><label>${item[8]}</label>
				</div>
				<br>
				<div>
					<label>简介：</label>
					<div style="width:250px;font-size:12px">
					${item[2]}
					</div>
				</div>
			</div>
			</c:forEach>	
		<!-- 
			<div class="listitem">
				<div style="display:inline;float:left">
					<button id="cst" style="width:50px;height:50px;margin-right:20px;background-image:url(../image/CST_button.png);background-size:100% 100%;"
					 onclick="applyNode('%=session.getAttribute("username")%>','cst','null')"></button>
					<div class="tip"></div>
				</div>
				<div>
					<label>软件名称：</label><label>CST</label>
					<br>
					<label>软件类型：</label><label>仿真、建模</label>
				</div>
				<br>
				<div>
					<label>简介：</label>
					<p style="width:250px;font-size:12px">
					CST工作室套装®是面向3D电磁、电路、温度和结构应力设计工程师的一款全面、精确、集成度极高的专业仿真软件包。包含八个工作室子软件，集成在同一用户界面内，为用户提供完整的系统级和部件级的数值仿真优化。
					软件覆盖整个电磁频段，提供完备的时域和频域全波电磁算法和高频算法。典型应用包含电磁兼容、天线/RCS、高速互连SI/EMI/PI/眼图、手机、核磁共振、电真空管、粒子加速器、高功率微波、非线性光学、电气、场路、电磁-温度及温度-形变等各类协同仿真。
					</p>
				</div>
			</div>
 		-->
		
		</div>
	</div>
</section>
<div id="rdpinfo">
	<input type="hidden" id="saveforIPAddress">
	<input type="hidden" id="saveforAccount">
	<input type="hidden" id="saveforPassword">
	<input type="hidden" id="saveforPort">
	<input type="hidden" id="saveforSoft">
	<input type="hidden" id="saveforLoginuser">
	<input type="hidden" id="saveforModel">
</div>
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
			No Node available！
		</div>
	</section>
</div>
<script src="../js/custom.js"></script>
<script src=".../js/cycle.js"></script>
<script src="../js/jquery.checkbox.min.js"></script>
<script src="../js/jquery.tablesorter.min.js"></script>
<script src="../js/timerFunction.js"></script>
<script src="../js/tableData.js"></script>
<script src="../js/Rdp.js"></script>
<script type="text/javascript">
// Feature slider for graphs
$('.cycle').cycle({
	fx: "scrollHorz",
	timeout: 0,
    slideResize: 0,
    prev:    '.left-btn', 
    next:    '.right-btn'
});
// search soft like % key %
function searchSoft(){
	
	var params = document.getElementById("search").value;
	//alert(params);	
	window.open("ComputeMain.jsp?searchParams="+params);
}


</script>
</body>
</html>