<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理平台——后台——修改节点</title>
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
		<span class="button blue" style="margin-top: -23px; float: right; position: relative;" >查询</span>
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
				<li><a href="PlatformlogNodeconcern.jsp">节点使用情况</a></li>
				<li class="last"><a href="Nodeinfo.jsp">添加节点</a></li>
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
<section id="NodeModify" class="content">
	<div class="widget-container">
		
		<input type="hidden" id="NodeID">
		<div id="NodeinfoSection" style="width:500px;margin-left:auto;margin-right:auto">
			<div style="margin-left:auto;margin-right:auto;width:100px">
				<strong style="font-size:20px;">节点信息</strong>
			</div>
			<table id="Nodeinfo" style="margin-top:20px">
				<tr style="height:30px">
					<td align="right"  style="width:200px;"><label style="margin-right:20px">节点IP：</label></td>
					<td><label id="NodeIPAddress"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">软件：</label></td>
					<td><div id="NodeSoft"></div></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">节点状态：</label></td>
					<td><label id="NodeStatus"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">同时使用用户数：</label></td>
					<td><label id="NodeMaxUse"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">节点账户：</label></td>
					<td>
						<div id="NodeAccountSection">
							<table id="NodeAccount" style="min-width:300px">
								<thead>
									<tr>
										<th></th>
										<th><strong>用户账号</strong></th>
										<th><strong>端口</strong></th>	
										<th><a href="#" onclick="showResourceinfo_M()">添加</a></th>								
									</tr>
								</thead>
								<tbody align="center">
									<tr style="height:30px">
										<td>1<input type="hidden" value="1"></td>
										<td><label>ComputeAccount</label><input type="hidden"></td>
										<td><label>6000</label></td>
										<td><a href="#" onclick="">修改</a></td>
									</tr>
								</tbody>
							</table>
							<input type="hidden" id="AccountID">
							<table id="NodeAccount_Modify" style="min-width:300px;display:none" >
								<tr style="height:30px">
									<td align="right"><label style="margin-right:20px">账号：</label></td>
									<td><input id="AccountName" style="width:90%"><a style="color:red;display:none">✖</a></td>
								</tr>
								<tr style="height:30px">
									<td align="right"><label style="margin-right:20px">密码：</label></td>
									<td><input id="Password" style="width:90%"><a style="color:red;display:none">✖</a></td>
								</tr>
								<tr style="height:30px">
									<td align="right"><label style="margin-right:20px">密码重复：</label></td>
									<td><input id="PswRepeat" style="width:90%"><a style="color:red;display:none">✖</a></td>
								</tr>
								<tr style="height:30px">
									<td align="right"><label style="margin-right:20px">配置端口：</label></td>
									<td><input id="ListenPort" style="width:90%"><a style="color:red;display:none">✖</a></td>
								</tr>
								<tr style="height:30px">
									<td align="right" ></td>
									<td>
										<button class="green" onclick="modifyResourceinfo()" value="保存">保存</button>
										<button class="orange" onclick="cancelResourceinfo_M()" value="取消">取消</button>
									</td>
								</tr>																
							</table>							
						</div>
					</td>
				</tr>
				<tr style="height:30px">
					<td align="right" ></td>
					<td><button class="green" onclick="showNodeinfo_M()" value="修改">修改</button></td>
				</tr>
			</table>
			<table id="Nodeinfo_Modify" style="display:none;margin-top:20px">
				<tr style="height:30px">
					<td align="right" style="width:150px;"><label style="margin-right:20px">节点IP：</label></td>
					<td><input id="NodeIPAddress_Modify" style="width:90%"><a style="color:red;display:none">✖</a></td>
				</tr>
				<tr style="height:30px">
					<td align="right" ><label style="margin-right:20px">软件：</label></td>
					<td>
						<div id="AllSoft">
						</div>
						<input id="NodeSoft_Modify" type="hidden">
					</td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">节点状态：</label></td>
					<td>
						<select id="NodeStatus_Modify">
							<option>正常</option>
							<option>停用</option>
						</select>
					</td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">同时使用用户数：</label></td>
					<td>						
						<select id="NodeMaxUse_Modify">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>							
							<option>5</option>
							<option>6</option>
							<option>7</option>
							<option>8</option>
							<option>9</option>
							<option>10</option>							
						</select>
					</td>
				</tr>
				<tr style="height:30px">
					<td align="right" ></td>
					<td>
						<button class="green" onclick="modifyNodeinfo()" value="保存">保存</button>
						<button class="orange" onclick="cancelNodeinfo_M()" value="取消">取消</button>
					</td>
				</tr>								
			</table>
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
<script src="../js/NodeinfoModify.js"></script>
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
loadNodeInfo('<%=request.getParameter("nodeID")%>');
loadResourceInfo('<%=request.getParameter("nodeID")%>');
</script>
</body>
</html>