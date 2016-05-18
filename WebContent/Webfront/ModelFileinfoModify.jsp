<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理平台——修改模型</title>
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
<section id="FileinfoModify" class="content">
	<div class="widget-container">
		<input type="hidden" id="ModelFileID">
		<div id="FileinfoSection" style="width:400px;margin-left:auto;margin-right:auto;">
			<div style="margin-left:auto;margin-right:auto;width:inherit">
				<strong style="font-size:20px">模型文件信息</strong>
			</div>
			<table id="Fileinfo" style="margin-top:20px">
				<tr style="height:30px">
					<td align="right" style="width:100px;"><label style="margin-right:20px">目标名称：</label></td>
					<td>
					<label id="TargetObject"></label>
					<input id="TargetObjectID" type="hidden">
					</td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">模型文件：</label></td>
					<td><label id="FileName"></label><input type="hidden" id="FileStorePath"></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">简介：</label></td>
					<td><div id="FileDescription"></div></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">密级：</label></td>
					<td><label id="FileSecretLevel"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">适用软件：</label></td>
					<td><div id="FileSelectedSoft"></div></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">来源：</label></td>
					<td><label id="FileSource"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">联系人：</label></td>
					<td><label id="ContactPerson"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">单位名称：</label></td>
					<td><label id="ContactComp"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">通信地址：</label></td>
					<td><label id="ContactAddress"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">邮政编码：</label></td>
					<td><label id="ContactZip"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">电话：</label></td>
					<td><label id="ContactTel"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">电子邮件：</label></td>
					<td><label id="ContactEmail"></label></td>
				</tr>				
				<tr style="height:30px">
					<td align="right" ></td>
					<td><button class="green" onclick="showModelFileinfo_M()" value="修改">修改</button></td>
				</tr>
			</table>
			<table id="Fileinfo_Modify" style="display:none;margin-top:20px">
				<tr style="height:30px">
					<td align="right"  style="width:100px;"><label style="margin-right:20px">目标名称：</label></td>
					<td>
					<label id="TargetObject_Modify"></label>
					</td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">模型文件：</label></td>
					<td><label id="FileName_Modify"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">简介：</label></td>
					<td><textarea id="FileDescription_Modify"></textarea></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">密级：</label></td>
					<td>
						<select id="FileSecretLevel_Modify">
							<option>绝密</option>
							<option>机密</option>
							<option>秘密</option>
							<option>内部</option>
							<option>公开</option>
						</select>
					</td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">适用软件：</label></td>
					<td>
						<div id="AllSoft">
						</div>
						<input type="hidden" id="FileSelectedSoft_Modify">
					</td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">来源：</label></td>
					<td>
						<select id="FileSource_Modify">
							<option>采购</option>
							<option>自主开发</option>
							<option>情报</option>
							<option>其他</option>
						</select>
					</td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">联系人：</label></td>
					<td><input id="ContactPerson_Modify"></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">单位名称：</label></td>
					<td><input id="ContactComp_Modify"></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">通信地址：</label></td>
					<td><input id="ContactAddress_Modify"></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">邮政编码：</label></td>
					<td><input id="ContactZip_Modify"></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">电话：</label></td>
					<td><input id="ContactTel_Modify"></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">电子邮件：</label></td>
					<td><input id="ContactEmail_Modify"></td>
				</tr>
				<tr style="height:30px">
					<td align="right" ></td>
					<td>
						<button class="green" onclick="modifyModelFileinfo()" value="保存">保存</button>
						<button class="orange" onclick="cancelModelFileinfo_M()" value="取消">取消</button>
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
<script src="../js/jquery.checkbox.min.js"></script>
<script src="../js/jquery.tablesorter.min.js"></script>
<script src="../js/timerFunction.js"></script>
<script src="../js/ModelFileinfoModify.js"></script>
<script>
$(document).ready(function(){
	if(document.getElementById("softitem")!=undefined)
		document.getElemntById("selectall").css.display="inline";
	
});
loadModelfileInfo('<%=request.getParameter("fileID")%>');
</script>
</body>
</html>