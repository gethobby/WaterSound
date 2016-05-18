<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String frompage= request.getParameter("frompage");
	if(frompage==null) frompage="";
	String loginerror="none";
	if(request.getAttribute("loginerror")!=null&&request.getAttribute("loginerror").equals("true")) 
	{
		loginerror="block";
		request.removeAttribute("loginerror");
	}
%>
<html>
<head>
	<meta charset="utf-8">
	<title>水声数据管理平台</title>
	<meta name="description" content="">
	<meta name="keywords" content="">
	<meta name="robots" content="">
	<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
	<link rel="stylesheet" href="css/style.css" media="all">
	<link rel="stylesheet" href="css/webUIs.css" media="all">
	<script src="js/jquery-1.11.1.min.js"></script>
	<!--[if IE]><link rel="stylesheet" href="css/ie.css" media="all" /><![endif]-->
</head>
<body class="login">
	<section>
		<h1><strong>水声数据管理</strong>后台</h1>
			<div class="wrapper" style="display: inline-block;float: left;"><!-- 最外层部分 -->
        <div class="banner"><!-- 轮播部分 -->
            <ul class="imgList"><!-- 图片部分 -->
                <li class="imgOn"><a href="#"><img src="image/hfss_button.png" width="300px" height="300px" alt=""></a></li>
            <li><a href="#"><img src="image/CST_button.png" width="300px" height="300px" alt=""></a></li>
            <li><a href="#"><img src="image/FEKO_button.png" width="300px" height="300px" alt=""></a></li>
            <li><a href="#"><img src="image/shutdown.png" width="300px" height="300px" alt=""></a></li>
            <li><a href="#"><img src="image/cancel.jpg" width="300px" height="300px" alt=""></a></li>
            </ul>
            <div class="bg"></div> <!-- 图片底部背景层部分-->
            <ul class="infoList"><!-- 图片左下角文字信息部分 -->
                <li class="infoOn"></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
            <ul class="indexList"><!-- 图片右下角序号部分 -->
                <li class="indexOn"></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>
	</div>
	<div style="float: right;display: inline;margin-top:80px">
		
		<form name="user_info" method="post" action="AuthorVerify?backsystem=true&frompage=<%=frompage%>">
			<input type="text" name="username" value="账号">
			<input name="password" value="password" type="password">
			<button class="blue">登录</button>
		</form>
		<label id="loginerror" style="font-size:12px;color:red;display:<%=loginerror %>">用户名或者密码错误	</label>
	</div>
	</section>
<script src="js/login.js"></script>
</body>
</html>