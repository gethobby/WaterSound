<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理平台——修改目标</title>
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
<section id="ObjectinfoModify" class="content">
	<div class="widget-container">
		<div id="ObjectinfoSection" style="width:400px;margin-left:auto;margin-right:auto;">
			<div style="margin-left:auto;margin-right:auto;width:inherit">
				<strong style="font-size:20px">目标信息</strong>
			</div>
			<table id="Objectinfo" style="margin-top:20px">
				<tr style="height:30px">
					<td align="right" style="width:100px;"><label style="margin-right:20px">目标名称：</label></td>
					<td>
					<label id="ObjectName"></label>
					<input id="ObjectID" type="hidden">
					</td>
				</tr>
				<tr style="height:20px">
					<td align="right"><label style="margin-right:20px">目标图片：</label></td>
					<td></td>
				</tr>
				<tr style="height:30px">
					<td align="right"></td>
					<td><img id="ObjectPicture"  height="180px"><input type="hidden" id="ObjectPicPath"></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">目标分类：</label></td>
					<td><label id="Type1"></label>——<label id="Type2"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">所属国家：</label></td>
					<td><label id="ObjectBelong"></label></td>
				</tr>
				<tr style="height:30px">
					<td align="right" ></td>
					<td><button class="green" onclick="showObjectinfo_M()" value="修改">修改</button></td>
				</tr>
			</table>
			<table id="Objectinfo_Modify" style="display:none;margin-top:20px">
				<tr style="height:30px">
					<td align="right" style="width:100px;"><label style="margin-right:20px">目标名称：</label></td>
					<td><input id="ObjectName_Modify"></td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">目标图片：</label></td>
					<td>
						<form action="../Upload.jsp" id="form1" name="form1" encType="multipart/form-data"  method="post" target="hidden_frame" >   
			    			<input type="file" id="file" name="file" style="width:150px"><input type="hidden" id="ObjectPicPath_Modify">   
			    			<input type="submit" value="上传文件" style="width:80px"><br><span id="msg"></span><br><font color="red">支持JPG,JPEG,BMP,PNG文件的上传</font>                 
			    			<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>   
						</form>					
					</td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">目标分类：</label></td>
					<td>
					<div id="ObjectType" style="display:inline">
						<select id="ObjectType1" onchange="getObjectType2(this)" >
							<option></option>
							<option>轨道目标类</option>
							<option>导弹类</option>
							<option>飞机类</option>
							<option>地面装备类</option>
							<option>水平目标类</option>
							<option>其他类</option>
						</select>
					</div>
				</td>
				</tr>
				<tr style="height:30px">
					<td align="right"><label style="margin-right:20px">所属国家：</label></td>
					<td>
						<select id="ObjectBelong_Modify" >
							<option>我军</option>
							<option>外军</option>
						</select>
					</td>
				</tr>			
				<tr style="height:30px">
					<td align="right" ></td>
					<td>
						<button class="green" onclick="modifyObjectinfo()" value="保存">保存</button>
						<button class="orange" onclick="cancelObjectinfo_M()" value="取消">取消</button>
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
<script src="../js/ObjectinfoModify.js"></script>
<script>
$(document).ready(function(){
	if(document.getElementById("softitem")!=undefined)
		document.getElemntById("selectall").css.display="inline";
	
});
loadObjectInfo('<%=request.getParameter("objectID")%>');
function callback(msg,picPath)   
{   
	if(picPath){
    	document.getElementById("ObjectPicPath_Modify").value = picPath;   
    	document.getElementById("msg").innerHTML = '<font color="green">'+msg+'</font>';
	}
	else{
		document.getElementById("msg").innerHTML = '<font color="red">'+msg+'</font>';
	}
   
} 
</script>
</body>
</html>