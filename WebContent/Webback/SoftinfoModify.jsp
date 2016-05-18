<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>水声数据管理平台——后台——修改软件</title>
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
<section id="SoftModify" class="content">
	<div class="widget-container">
		<div style="margin-left:auto;margin-right:auto;width:600px">
			<strong style="font-size:20px">软件信息</strong>
			<ul id="navinfo" class="listsoftinfo" style="margin-top:20px">
				<li class="selectsection" onclick="selectSubContent(0)">基本信息</li>
				<li onclick="selectSubContent(1)">软件功能</li>
				<li onclick="selectSubContent(2)">典型算例</li>
				<li onclick="selectSubContent(3)">应用效果</li>
				<li onclick="selectSubContent(4)">通讯信息</li>
			</ul>
			<input type="hidden" id="SoftID">
			<div id="BasicinfoSection" style="margin-top:20px;width: inherit">
				<table id="Basicinfo" style="width: inherit">
					<tr style="height:30px">
						<td align="right" style="width:150px;"><label style="margin-right:20px">中文名称：</label></td>
						<td><label id="SoftName"></label></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">英文名称：</label></td>
						<td><label id="SoftNameEN"></label></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">版本号：</label></td>
						<td><label id="SoftVersion"></label></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">发布/更新时间：</label></td>
						<td><label id="SoftUpdateTime"></label></td>
					</tr>
					<tr style="height:30px">
						<td align="right" vAlign="top"><label style="margin-right:20px">LOGO：</label></td>
						<td><img id="SoftLogo" height="180px" width="180px"></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">类型：</label></td>
						<td><label id="SoftType1"></label>——<label id="SoftType2"></label></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">密级：</label></td>
						<td><label id="SoftSecretLevel"></label></td>
					</tr>
					<tr style="height:30px">
						<td align="right" vAlign="top"><label style="margin-right:20px">简介：</label></td>
						<td><div id="SoftDescription" style="min-height: 100px;border: 1px solid gainsboro;overflow:hidden"></div></td>
					</tr>
					<tr style="height:30px">
						<td align="right" ></td>
						<td><button class="green" onclick="showBasicinfo_M()" value="修改">修改</button></td>
					</tr>
				</table>
				<table id="Basicinfo_Modify" style="display:none;width:800px">
					<tr style="height:30px">
						<td align="right" style="width:150px;"><label style="margin-right:20px">中文名称：</label></td>
						<td><label id="SoftName_Modify"></label></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">英文名称：</label></td>
						<td><input type="text" style="width:200px;"  id="SoftNameEN_Modify"></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">版本号：</label></td>
						<td><input type="text" style="width:200px;"  id="SoftVersion_Modify"></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">发布/更新时间：</label></td>
						<td>
							<input id="SoftUpdateTime_Modify" style="width:200px;" onclick="document.getElementById('datepicker').style.display='';">
							<div id="datepicker" style="display:none"></div>
						</td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">LOGO：</label></td>
						<td>
							<form action="../Upload.jsp" id="form1" name="form1" encType="multipart/form-data"  method="post" target="hidden_frame" >   
	    						<input type="file" id="file" name="file" style="width:150px"><input id="SoftLogo_Modify" type="hidden">   
	    						<input type="submit" value="上传文件" style="width:80px"><span id="msg"></span><br><font color="red">支持JPG,JPEG,BMP,PNG文件的上传</font>                 
	    						<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>   
							</form> 
						</td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">类型：</label></td>
						<td>
							<select id="SoftType1_Modify">
								<option>光学</option>
								<option>电磁</option>
								<option>水声</option>
								<option>工具</option>
							</select>
							<select id="SoftType2_Modify">
								<option>目标类</option>
								<option>环境类</option>
								<option>目标与环境复合类</option>
								<option>数据处理与应用类</option>
								<option>其他类</option>
							</select>
						</td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">密级：</label></td>
						<td>
							<select id="SoftSecretLevel_Modify">
								<option>绝密</option>
								<option>机密</option>
								<option>秘密</option>
								<option>内部</option>
								<option>公开</option>
							</select>
						</td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">简介：</label></td>
						<td style="width:400px"><textarea id="SoftDescription_Modify"></textarea></td>
					</tr>
					<tr style="height:30px">
						<td align="right" ></td>
						<td>
							<button class="green" onclick="modifyBasicinfo()" value="保存">保存</button>
							<button class="orange" onclick="cancelBasicinfo_M()" value="取消">取消</button>
						</td>
					</tr>
				</table>
			</div>
			<div id="SoftFunctionDescSection" style="margin-top:20px;display:none">
				<div>
					<div id="SoftFunctionDesc" style="min-height: 100px;border: 1px solid gainsboro;overflow:hidden"></div>
					<button class="green" onclick="showSoftFuncDesc_M()" value="修改">修改</button>
				</div>
				<div style="display:none">
					<textarea id="SoftFunctionDesc_Modify"></textarea>
					<button class="green" onclick="modifySoftFuncDesc()" value="保存">保存</button>
					<button class="orange" onclick="cancelSoftFuncDesc_M()" value="取消">取消</button>
				</div>
			</div>
			<div id="SoftComputeExampleSection" style="margin-top:20px;display:none">
				<div>
					<div id="SoftComputeExample" style="min-height: 100px;border: 1px solid gainsboro;overflow:hidden"></div>
					<button class="green" onclick="showSoftComputeExample_M()" value="修改">修改</button>
				</div>
				<div style="display:none">
					<textarea id="SoftComputeExample_Modify"></textarea>
					<button class="green" onclick="modifySoftComputeExample()" value="保存">保存</button>
					<button class="orange" onclick="cancelSoftComputeExample_M()" value="取消">取消</button>
				</div>
			</div>
			<div id="SoftResultExampleSection" style="margin-top:20px;display:none">
				<div>
					<div id="SoftResultExample" style="min-height: 100px;border: 1px solid gainsboro;overflow:hidden"></div>
					<button class="green" onclick="showSoftResultExample_M()" value="修改">修改</button>
				</div>
				<div style="display:none">
					<textarea id="SoftResultExample_Modify"></textarea>
					<button class="green" onclick="modifySoftResultExample()" value="保存">保存</button>
					<button class="orange" onclick="cancelSoftResultExample_M()" value="取消">取消</button>
				</div>
			</div>
			<div id="ContactinfoSection" style="margin-top:20px;display:none">
				<table id="Contactinfo">
					<tr style="height:30px">
						<td align="right" style="width:100px;"><label style="margin-right:20px">联系人：</label></td>
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
						<td><button class="green" onclick="showContactinfo_M()" value="修改">修改</button></td>
					</tr>
				</table>		
				<table id="Contactinfo_Modify" style="display:none">
					<tr style="height:30px">
						<td align="right" style="width:100px;"><label style="margin-right:20px">联系人：</label></td>
						<td><input type="text"  id="ContactPerson_Modify"></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">单位名称：</label></td>
						<td><select id="ContactComp_Modify"></select></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">通信地址：</label></td>
						<td><input type="text" id="ContactAddress_Modify"></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">邮政编码：</label></td>
						<td><input type="text"  id="ContactZip_Modify"></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">电话：</label></td>
						<td><input type="text" id="ContactTel_Modify"></td>
					</tr>
					<tr style="height:30px">
						<td align="right"><label style="margin-right:20px">电子邮件：</label></td>
						<td><input type="text" id="ContactEmail_Modify"></td>
					</tr>
					<tr style="height:30px">
						<td align="right" ></td>
						<td>
							<button class="green" onclick="modifyContactinfo()" value="保存">保存</button>
							<button class="orange" onclick="cancelContactinfo_M()" value="取消">取消</button>
						</td>
					</tr>
				</table>
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
<script src="../js/SoftinfoModify.js"></script>
<script type="text/javascript">
$('.cycle').cycle({
	fx: "scrollHorz",
	timeout: 0,
    slideResize: 0,
    prev:    '.left-btn', 
    next:    '.right-btn'
});
loadSoftInfo('<%=request.getParameter("softID")%>');

$( "#datepicker" ).datepicker({
	inline: true,
	changeMonth: true,
	changeYear: true,
	onSelect:function(dateText,inst){ 
        var time = $.datepicker.formatDate( "yy-mm-dd", new Date(dateText) );
         document.getElementById("SoftUpdateTime_Modify").value=time;
         document.getElementById("datepicker").style.display="none";
	} 
});
function callback(msg,picPath)   
{   
	if(picPath){
    	document.getElementById("SoftLogo_Modify").value = picPath;   
    	document.getElementById("msg").innerHTML = '<font color="green">'+msg+'</font>';
	}
	else{
		document.getElementById("msg").innerHTML = '<font color="red">'+msg+'</font>';
	}
   
} 
</script>
</body>
</html>