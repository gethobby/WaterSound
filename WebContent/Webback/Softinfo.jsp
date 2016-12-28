<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理平台——后台——添加软件</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="robots" content="">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" href="../css/style.css" media="all">
<link rel="stylesheet" href="../css/ui.css" media="all">
<link rel="stylesheet" href="../css/toolist.css" media="all">
<link rel="stylesheet" href="../js/kindeditor/core/themes/default/default.css" />
<link rel="stylesheet" href="../css/jquery-ui.css">
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
		<li>
			<a href="NodeManage.jsp" class="nodes" title="Nodes"><span class="icon">&#128711;</span> 节点管理</a>
			<ul class="submenu">
				<li><a href="PlatformlogNodeconcern.jsp">计算节点使用情况</a></li>
				<li class="last"><a href="Nodeinfo.jsp">添加计算节点</a></li>
			</ul>
		</li>
		<li  class="section">
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
<section id="NewSoft" class="content">
	<div class="widget-container">
		<div style="margin-left:auto;margin-right:auto;width:500px">
			<h1><strong style="font-size:20px">新软件</strong></h1><br><input type="hidden" id="SoftID">
			中文名称:<strong style="color:red">*</strong>：<input id="SoftName" type="text" style="width:70%">
			<a style="color:red;display:none">✖</a><br>
			英文名称:<input id="SoftNameEN" type="text" style="width:70%">
			<a style="color:red;display:none">✖</a><br>
			版本号<strong style="color:red">*</strong>：<input id="SoftVersion" type="text" style="width:70%">
			<a style="color:red;display:none">✖</a><br>
			发布/更新时间<strong style="color:red">*</strong>：
			<input id="SoftUpdateTime" type="text" onclick="document.getElementById('datepicker').style.display='';" style="width:70%">
			<a style="color:red;display:none">✖</a><br>
			<div id="datepicker" style="display:none"></div>
			LOGO：
			<form action="../Upload.jsp" id="form1" name="form1" encType="multipart/form-data"  method="post" target="hidden_frame" >   
    			<input type="file" id="file" name="file" style="width:70%"><input id="SoftLogo" type="hidden">   
    			<input type="submit" value="上传文件" style="width:80px"><span id="msg"></span><br><font color="red">支持JPG,JPEG,BMP,PNG文件的上传</font>                 
    			<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>   
			</form> 
			<br>
			类型<strong style="color:red">*</strong>：
			<select id="SoftType1">
				<option></option>
				<option>光学</option>
				<option>电磁</option>
				<option>水声</option>
				<option>工具</option>
			</select>
			<select id="SoftType2">
				<option></option>
				<option>目标类</option>
				<option>环境类</option>
				<option>目标与环境复合类</option>
				<option>数据处理与应用类</option>
				<option>其他类</option>
			</select>
			<a style="color:red;display:none">✖</a><br><br>
			密级<strong style="color:red">*</strong>：
			<select id="SoftSecretLevel">
				<option></option>
				<option>绝密</option>
				<option>机密</option>
				<option>秘密</option>
				<option>内部</option>
				<option>公开</option>
			</select><a style="color:red;display:none">✖</a><br><br>
			简介：
			<div id="SoftDescription" style="width:70%">
				<textarea id="Description" name="content" rows="10" cols="10"></textarea>
			</div><br>
			软件功能<strong style="color:red">*</strong>：<a style="color:red;display:none">✖</a><br>
			<div id="SoftFunctionDesc" style="width:70%">
				<textarea id="FunctionDesc" name="content" rows="10" cols="10"></textarea>
			</div><br>
			典型算例<strong style="color:red">*</strong>：<a style="color:red;display:none">✖</a>
			<div id="SoftComputeExample" style="width:30%">
				<textarea id="ComputeExample" name="content" rows="10" cols="10"></textarea>
			</div><br>
			应用效果<strong style="color:red">*</strong>：<a style="color:red;display:none">✖</a>
			<div id="SoftResultExample" style="width:70%">
				<textarea id="ResultExample" name="content" rows="10" cols="10"></textarea>
			</div><br>
			联系人<strong style="color:red">*</strong>：<input id="ContactPerson" type="text" style="width:70%"><a style="color:red;display:none">✖</a><br>
			单位名称<strong style="color:red">*</strong>：
			  <div style="display:inline"> 
			      <select id="ContactComp">
			         <option></option>
			         <option>A公司</option>
			         <option>B公司</option>
			      </select>
			    </div>
			   <a style="color:red;display:none">✖</a>
			   <br><br>
			通信地址<strong style="color:red">*</strong>：<input id="ContactAddress" type="text" style="width:70%"><a style="color:red;display:none">✖</a><br>
			邮政编码<strong style="color:red">*</strong>：<input id="ContactZip" type="text" style="width:70%"><a style="color:red;display:none">✖</a><br>
			电话<strong style="color:red">*</strong>：<input id="ContactTel" type="text" style="width:70%"><a style="color:red;display:none">✖</a><br>
			电子邮件<strong style="color:red">*</strong>：<input id="ContactEmail" type="text" style="width:70%"><a style="color:red;display:none">✖</a>
			<div>
				<button class="green" onclick="insertNewSoftRecord()" value="新建">确认新建</button>
			</div>
		</div>
	</div>
</section>

<script src="../js/t.min.js"></script>
<script src="../js/jquery-ui.min.js"></script>
<script charset="utf-8" src="../js/kindeditor/core/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor/lang/zh_CN.js"></script>
<script src="../js/custom.js"></script>
<script src="../js/cycle.js"></script>
<script src="../js/jquery.checkbox.min.js"></script>
<script src="../js/jquery.tablesorter.min.js"></script>
<script src="../js/timerFunction.js"></script>
<script src="../js/tableData.js"></script>
<script src="../js/template.js"></script>
<script src="../js/Softinfo.js"></script>
<script type="text/javascript">
$('.cycle').cycle({
	fx: "scrollHorz",
	timeout: 0,
    slideResize: 0,
    prev:    '.left-btn', 
    next:    '.right-btn'
});
loadContactCompany("ContactComp");
$( "#datepicker" ).datepicker({
	inline: true,
	changeMonth: true,
	changeYear: true,
	onSelect:function(dateText,inst){ 
        var time = $.datepicker.formatDate( "yy-mm-dd", new Date(dateText) );
         document.getElementById("SoftUpdateTime").value=time;
         document.getElementById("datepicker").style.display="none";
	} 
});
function callback(msg,picPath)   
{   
	if(picPath){
    	document.getElementById("SoftLogo").value = picPath;   
    	document.getElementById("msg").innerHTML = '<font color="green">'+msg+'</font>';
	}
	else{
		document.getElementById("msg").innerHTML = '<font color="red">'+msg+'</font>';
	}
   
} 
</script>
</body>
</html>