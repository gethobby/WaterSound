<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>建模仿真工具</title>
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
<!--[if IE]><script language="javascript" type="text/javascript" src="../js/jqplot/excanvas.js"></script><![endif]-->
</head>
<body onload="startTime()"> 
<div class="testing">
	<header class="main">
		<h1><strong>水声数据</strong> 管理平台</h1>
		<span class="button blue" style="margin-top: -23px; float: right; position: relative;" onclick="window.open('SearchFile.jsp?filename='+document.getElementById('search').value)">查询</span>
		<input type="text" value="模型名称" id="search">
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
			<span class="button blue"><a href="login.jsp">登出</a></span>
		</div>
	</section>
</div>
<nav>
	<ul id="navBar">
		<li class="section" onclick="selectSubContent(0);"><a><span class="icon">&#128711;</span>工具列表</a></li>
		<li onclick="selectSubContent(1);"><a><span class="icon">&#59214;</span> 节点管理</a></li>
		<li onclick="selectSubContent(2);"><a><span class="icon">&#59214;</span> 节点监控</a></li>
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
	<div class="widget-container">
		<div id="toolist">
			<h1>工具列表：</h1>		
			<div class="listitem">
				<div style="display:inline;float:left">
					<button id="feko" style="width:50px;height:50px;margin-right:20px;background-image:url(../image/FEKO_button.png);background-size:100% 100%;"
					 onclick="applyNode('feko','null')"></button>
					<div class="tip"></div>
				</div>
				<div>
					<label>软件名称：</label><label>FEKO</label>
					<br>
					<label>软件类型：</label><label>仿真、建模</label>
				</div>
				<br>
				<div>
					<label>简介：</label>
					<p style="width:250px;font-size:12px">
					FEKO软件是一款强大的三维全波电磁仿真软件。FEKO仿真基于著名的矩量法（MoM）对Maxwell方程组的求解。
					FEKO实现了非常全面的MoM代码，可以解决任何结构类型的问题；FEKO还针对许多特定问题，例如平面多层介质结构、金属表面的涂覆等等，开发了量身定制的代码，在保证精度的同时获得最佳的效率。
					</p>
				</div>
			</div>
		
			<div class="listitem">
				<div style="display:inline;float:left">
					<button id="cst" style="width:50px;height:50px;margin-right:20px;background-image:url(../image/CST_button.png);background-size:100% 100%;"
					 onclick="applyNode('cst','null')"></button>
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
		
			<div class="listitem">
				<div style="display:inline;float:left">
					<button id="hfss" style="width:50px;height:50px;margin-right:20px;background-image:url(../image/hfss_button.png);background-size:100% 100%;"
					 onclick="applyNode('hfss','null')"></button>
					<div class="tip"></div>
				</div>
				<div>
					<label>软件名称：</label><label>HFSS</label>
					<br>
					<label>软件类型：</label><label>仿真、建模</label>
				</div>
				<br>
				<div>
					<label>简介：</label>
					<p style="width:250px;font-size:12px">
					High Frequency Structure Simulator, Ansoft 公司推出的三维电磁仿真软件；是世界上第一个商业化的三维结构电磁场仿真软件，业界公认的三维电磁场设计和分析的工业标准。
					HFSS提供了一简洁直观的用户设计界面、精确自适应的场解器、拥有空前电性能分析能力的功能强大后处理器，能计算任意形状三维无源结构的S参数和全波电磁场。
					</p>
				</div>
			</div>
			<div class="listitem">
				<div style="display:inline;float:left">
					<button id="comsol" style="width:50px;height:50px;margin-right:20px;background-image:url(../image/COMSOL_button.png);background-size:100% 100%;"
					 onclick="applyNode('comsol','null')"></button>
					<div class="tip"></div>
				</div>
				<div>
					<label>软件名称：</label><label>COMSOL</label>
					<br>
					<label>软件类型：</label><label>仿真、建模</label>
				</div>
				<br>
				<div>
					<label>简介：</label>
					<p style="width:250px;font-size:12px">
					COMSOL 是 COMSOL Multiphysics 多物理场仿真软件的生产商，致力于为科学技术和工程领域的工程师和研发人员提供交互式的建模仿真平台。
					</p>
				</div>
			</div>
		
		</div>
	</div>
</section>
<section id="modelView" class="content" style="display:none">
	<div class="widget-container">
		<section class="widget" style="margin-left:5%">
			<header>
			
				<span class="icon">&#128196;</span>
				<hgroup>
					<h1>节点列表</h1>
					<h2>系统内节点机器概览</h2>
				</hgroup>
				<aside>
						<!-- <a href="#">&#9881;</a> -->
						<button class="green" onclick="modelWindowShow()">添加节点</button>
				</aside>
				
			</header>
			<div class="content">
				<table id="myTable" border="0" width="100">
					<thead>
						<tr>
							<th class="header">编号</th>
							<th class="header">节点IP</th>
							<th class="header">节点状态</th>
							<th class="header">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="item">
						<tr>
							<td>${item[0]}</td>
							<td>${item[1]}</td>
							<td>
							<c:choose>
							<c:when test="${item[2]==0}">
							<p style="color:Green">空闲</p>
							</c:when>
							<c:otherwise>
							<p style="color:Red">使用中</p>
							</c:otherwise>
							</c:choose>
							</td>
							<td>
								<a href="#" onclick="{if(window.confirm('确定删除节点？')) return true;else return false;}">delete</a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</section>
	</div>
</section>
<section id="computeTask" class="content"  style="display:none">
	<div class="widget-container">
		<section class="widget">
			<header>
				<span class="icon">&#128200;</span>
				<hgroup>
					<h1>节点访问</h1>
					<h2>各节点的使用情况统计</h2>
				</hgroup>
				<aside>
					<button class="left-btn">&#59229;</button><button class="right-btn">&#59230;</button>
				</aside>
			</header>
			<div class="content cycle">
				<div id="chart1" class="graph-area">
					<img src="image/webchart.png" alt="" width="800px">
				</div>
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
				<h1>新建节点</h1>
				<h2>添加新的节点机器信息</h2>
			</hgroup>
			<aside>
				<span onclick="modelWindowClose()">
					<a href="#">⚙</a>
				</span>
			</aside>
		</header>
		<div class="content">
		<div>
		<label>节点IP:</label><input type="text" id="IP" style="width:200px">
		<br>
		<label>工具类型:</label>
		<select>
			<option label="feko">feko</option>
			<option label="cst">cst</option>
		</select>
		<br>
		<label>分配账户:</label><input type="text" style="width:200px">
		<br>
		<button class="blue" onclick="AddOneRecordtoNodelist(2,document.getElementById('IP').value,0)">添加</button>
		</div>
		</div>
	</section>
</div>
<script src="../js/custom.js"></script>
<script src="../js/cycle.js"></script>
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
</script>
<script language="javascript">
function AddOneRecordtoNodelist(nodeid,nodeip,status)
{
	var x=document.getElementById("myTable");
	var y=x.insertRow(1);
	var z=new Array();
	for(var i=0;i<7;i++)
	{
		z[i]=y.insertCell(i);
	}
	var c = document.createElement("input");   
    c.setAttribute("type","checkbox");    
	z[0].appendChild(c);
    z[0].innerHTML=nodeid;
    z[1].innerHTML=nodeip;
	var statusnote = document.createElement("p");   
    
    if(status=='0'){
    	statusnote.style.color="Green"; 
    	statusnote.innerHTML="空闲";
    }
    else{
       	statusnote.style.color=""; 
    	statusnote.innerHTML="使用中";
    }
    z[2].appendChild(statusnote);
}
</script>
</body>
</html>