/**
 * 
 */
// <!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
// <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
// <!--Step:2 Import echarts.js-->
// <!--Step:2 引入echarts.js-->
// Step:3 conifg ECharts's path, link to echarts.js from current page.
// Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
var nodeChart1,nodeChart2,nodeChart3;
var nodeoption1,nodeoption2,nodeoption3;
var serverChart1,serverChart2,serverChart3;
var serveroption1,serveroption2,serveroption3;
var SystemChart;
var systemoption ;
require.config({
    paths: {
        echarts: '../js/bootstrap/www/js'
    }
});
autoLoadSystemArchi();
function autoLoadSystemArchi()
{
	//var NodeIDs=[];
	//var IPAddresses=[];
	//var UsersCount=[];
	//var MaxUsersCount=[];
	//var Statuses=[];
	var Nodes=[];
	Nodes.push({category:0, name: 'Server', value : 10, label: 'Server\n（服务器）'});
	Nodes.push({category:3, name: 'StorageNode1',value : 7});
	var Links=[];
	Links.push({source : 'StorageNode1', target : 'Server', weight : 2});
    $.ajax({
    	url: '/NodesoftManage/NodeStatus',
    	success:function(data){	
    		var len = data.length-1;
    		while(len>=0)
    		{
    			//NodeIDs.push(data[len].nodeID);
    			//IPAddresses.push(data[len].ip);
    			if(data[len].usecount>0){
    				Nodes.push({category:2,name:'ComputeNode'+
    					data[len].nodeID,value:data[len].maxcount,label:'ComputeNode'+data[len].nodeID+'\n使用中',status:data[len].status});
    				
    			}
    			else{
    				Nodes.push({category:1,name:'ComputeNode'+
    					data[len].nodeID,value:data[len].maxcount,label:'ComputeNode'+data[len].nodeID+'\n空闲',status:data[len].status});
    			}
    			if(data[len].status==0){
    				Links.push({source :'ComputeNode'+data[len].nodeID , target : 'Server', weight : 1,name: '正常连接'});
    			}
    			len--;
    		}	
    	},
    	async:false
  });
    
    // Step:4 require echarts and use it in the callback.
    // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/force'
        ],
        function (ec) {
        	
        	SystemChart=ec.init(document.getElementById('SystemArchi'));
        	SystemChart.setOption(systemoption={
        	    title : {
        	        text: '系统架构：管理平台硬件设备',
        	        x:'right',
        	        y:'bottom'
        	    },
        	    tooltip : {
        	        trigger: 'item',
        	        formatter: '{a} : {b}'
        	    },
        	    legend: {
        	        x: 'left',
        	        data:['空闲节点','使用中节点','存储节点']
        	    },
        	    series : [
        	        {
        	            type:'force',
        	            name : "系统架构",
        	            ribbonType: false,
        	            categories : [
        	                {
        	                    name: '服务器'
        	                },
        	                {
        	                    name: '空闲节点'
        	                },
        	                {
        	                    name:'使用中节点'
        	                },
        	                {
        	                	name:'存储节点'
        	                }
        	            ],
        	            itemStyle: {
        	                normal: {
        	                    label: {
        	                        show: true,
        	                        textStyle: {
        	                            color: '#333'
        	                        }
        	                    },
        	                    nodeStyle : {
        	                        brushType : 'both',
        	                        borderColor : 'rgba(255,215,0,0.4)',
        	                        borderWidth : 1
        	                    },
        	                    linkStyle: {
        	                        type: 'curve'
        	                    }
        	                },
        	                emphasis: {
        	                    label: {
        	                        show: false
        	                        // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
        	                    },
        	                    nodeStyle : {
        	                        //r: 30
        	                    },
        	                    linkStyle : {}
        	                }
        	            },
        	            useWorker: false,
        	            minRadius : 15,
        	            maxRadius : 25,
        	            gravity: 1.1,
        	            scaling: 1.1,
        	            roam: 'move',
        	            nodes:Nodes,
//        	            nodes:[
//        	                {category:0, name: 'Server', value : 10, label: 'Server\n（服务器）'},
//        	                {category:1, name: 'ComputeNode1',value : 2,label:'ComputeNode1\n空闲'},
//        	                {category:2, name: 'ComputeNode2',value : 3,label:'ComputeNode2\n使用中'},
//        	                {category:2, name: 'ComputeNode3',value : 3,label:'ComputeNode3\n使用中'},
//        	                {category:3, name: 'StorageNode1',value : 7}
//        	            ],
        	            links:Links
//        	            links : [
//        	                {source : 'ComputeNode1', target : 'Server', weight : 1, name: '正常连接'},
//        	                {source : 'ComputeNode2', target : 'Server', weight : 2, name: '正常连接'},
//        	                {source : 'ComputeNode3', target : 'Server', weight : 1, name: '正常连接'},
//        	                {source : 'StorageNode1', target : 'Server', weight : 2}
//
//        	            ]
        	           
        	        }
        	    ]
        	});
            
            var ecConfig = require('echarts/config');
            function focus(param) {//图标选中事件
                var data = param.data;
                var links = systemoption.series[0].links;
                var nodes = systemoption.series[0].nodes;
                if (
                    data.source !== undefined
                    && data.target !== undefined
                ) { //点击的是边
                    var sourceNode = nodes.filter(function (n) {return n.name == data.source})[0];
                    var targetNode = nodes.filter(function (n) {return n.name == data.target})[0];
                    console.log("选中了边 " + sourceNode.name + ' -> ' + targetNode.name + ' (' + data.weight + ')');
                } 
                else { // 点击的是点
                	if(data.name.match(/^ComputeNode/)!=undefined){
                		if(data.status!=undefined&&data.status==0){
                			loadNodeStatus(data.name);//加载节点状态详细信息
                			loadReleaseResource(data.name.substring(11));
                		}
                		else if(data.status!=undefined&&data.status==1)
                		{
                			window.open("NodeinfoModify.jsp?nodeID="+data.name.substring(11));
                		}
                	}
                	else {
                		loadServerStatus();//加载服务器状态详细信息
                	}
                	SystemChart.resize();
                    console.log("选中了" + data.name + '(' + data.value + ')');
                }
            }
            SystemChart.on(ecConfig.EVENT.CLICK, focus)

            SystemChart.on(ecConfig.EVENT.FORCE_LAYOUT_END, function () {
                console.log(SystemChart.chart.force.getPosition());
            });
            
        });
	
}

function loadNodeStatus(nodename){
    	document.getElementById('nodestatus').style.display='block';
    	document.getElementById('serverstatus').style.display='none';
    	document.getElementById('SystemArchi').style.width='60%';
    	document.getElementById('nodename').innerHTML=nodename;
    	document.getElementById('nodeID').value=nodename.substring(11);
    	require(
                [
                    'echarts',
                    'echarts/chart/gauge'
                    //'echarts/chart/bar'
                ],function (ec) {
                	// --- 仪表盘---
                	nodeChart1 = ec.init(document.getElementById('nodeCPU'));
                    var option1 = {
                    	    tooltip : {
                    	        formatter: "{a} <br/>{b} : {c}%"
                    	    },
                    	    series : [
                    	        {
                    	            name:'cpu占用率',
                    	            type:'gauge',
                    	            center : ['50%', '50%'],    // 默认全局居中
                    	            radius : [0, '65%'],
                    	            startAngle: 140,
                    	            endAngle : -140,
                    	            min: 0,                     // 最小值
                    	            max: 100,                   // 最大值
                    	            precision: 0,               // 小数精度，默认为0，无小数点
                    	            splitNumber: 10,             // 分割段数，默认为5
                    	            axisLine: {            // 坐标轴线
                    	                show: true,        // 默认显示，属性show控制显示与否
                    	                lineStyle: {       // 属性lineStyle控制线条样式
                    	                    color: [[0.2, 'lightgreen'],[0.4, 'orange'],[0.8, 'skyblue'],[1, '#ff4500']], 
                    	                    width: 10
                    	                }
                    	            },
                    	            axisTick: {            // 坐标轴小标记
                    	                show: true,        // 属性show控制显示与否，默认不显示
                    	                splitNumber: 5,    // 每份split细分多少段
                    	                length :8,         // 属性length控制线长
                    	                lineStyle: {       // 属性lineStyle控制线条样式
                    	                    color: '#eee',
                    	                    width: 1,
                    	                    type: 'solid'
                    	                }
                    	            },
                    	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                    	                show: true,
                    	                formatter: function(v){
                    	                    switch (v+''){
                    	                        case '10': return '弱';
                    	                        case '30': return '低';
                    	                        case '60': return '中';
                    	                        case '90': return '高';
                    	                        default: return '';
                    	                    }
                    	                },
                    	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    	                    color: '#333',
                    	                    fontSize : 10
                    	                }
                    	            },
                    	            splitLine: {           // 分隔线
                    	                show: true,        // 默认显示，属性show控制显示与否
                    	                length :30,         // 属性length控制线长
                    	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                    	                    color: '#eee',
                    	                    width: 2,
                    	                    type: 'solid'
                    	                }
                    	            },
                    	            pointer : {
                    	                length : '80%',
                    	                width : 8,
                    	                color : 'auto'
                    	            },
                    	            title : {
                    	                show : true,
                    	                offsetCenter: ['-65%', -10],       // x, y，单位px
                    	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    	                    color: '#333',
                    	                    fontSize : 20
                    	                }
                    	            },
                    	            detail : {
                    	                show : true,
                    	                backgroundColor: 'rgba(0,0,0,0)',
                    	                borderWidth: 0,
                    	                borderColor: '#ccc',
                    	                width: 100,
                    	                height: 40,
                    	                offsetCenter: ['-60%', 10],       // x, y，单位px
                    	                formatter:'{value}%',
                    	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    	                    color: 'auto',
                    	                    fontSize : 15
                    	                }
                    	            },
                    	            data:[{value: 50, name: 'CPU'}]
                    	        }
                    	    ]
                    	};
                    nodeoption1=option1;
                    nodeChart1.setOption(option1,true);
                    
                    nodeChart2 = ec.init(document.getElementById('nodeRAM'));
                    var option2 ={
                    	    tooltip : {
                    	        formatter: "{a} <br/>{b} : {c}%"
                    	    },
                    	    series : [
                    	        {
                    	            name:'cpu占用率',
                    	            type:'gauge',
                    	            center : ['50%', '50%'],    // 默认全局居中
                    	            radius : [0, '65%'],
                    	            startAngle: 140,
                    	            endAngle : -140,
                    	            min: 0,                     // 最小值
                    	            max: 100,                   // 最大值
                    	            precision: 0,               // 小数精度，默认为0，无小数点
                    	            splitNumber: 10,             // 分割段数，默认为5
                    	            axisLine: {            // 坐标轴线
                    	                show: true,        // 默认显示，属性show控制显示与否
                    	                lineStyle: {       // 属性lineStyle控制线条样式
                    	                    color: [[0.2, 'lightgreen'],[0.4, 'orange'],[0.8, 'skyblue'],[1, '#ff4500']], 
                    	                    width: 10
                    	                }
                    	            },
                    	            axisTick: {            // 坐标轴小标记
                    	                show: true,        // 属性show控制显示与否，默认不显示
                    	                splitNumber: 5,    // 每份split细分多少段
                    	                length :8,         // 属性length控制线长
                    	                lineStyle: {       // 属性lineStyle控制线条样式
                    	                    color: '#eee',
                    	                    width: 1,
                    	                    type: 'solid'
                    	                }
                    	            },
                    	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                    	                show: true,
                    	                formatter: function(v){
                    	                    switch (v+''){
                    	                        case '10': return '弱';
                    	                        case '30': return '低';
                    	                        case '60': return '中';
                    	                        case '90': return '高';
                    	                        default: return '';
                    	                    }
                    	                },
                    	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    	                    color: '#333',
                    	                    fontSize : 10
                    	                }
                    	            },
                    	            splitLine: {           // 分隔线
                    	                show: true,        // 默认显示，属性show控制显示与否
                    	                length :30,         // 属性length控制线长
                    	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                    	                    color: '#eee',
                    	                    width: 2,
                    	                    type: 'solid'
                    	                }
                    	            },
                    	            pointer : {
                    	                length : '80%',
                    	                width : 8,
                    	                color : 'auto'
                    	            },
                    	            title : {
                    	                show : true,
                    	                offsetCenter: ['-65%', -10],       // x, y，单位px
                    	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    	                    color: '#333',
                    	                    fontSize : 20
                    	                }
                    	            },
                    	            detail : {
                    	                show : true,
                    	                backgroundColor: 'rgba(0,0,0,0)',
                    	                borderWidth: 0,
                    	                borderColor: '#ccc',
                    	                width: 100,
                    	                height: 40,
                    	                offsetCenter: ['-60%', 10],       // x, y，单位px
                    	                formatter:'{value}%',
                    	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    	                    color: 'auto',
                    	                    fontSize : 15
                    	                }
                    	            },
                    	            data:[{value: 30, name: 'RAM'}]
                    	        }
                    	    ]
                    	};
                    nodeoption2=option2;
                    nodeChart2.setOption(option2,true);
                    
                    //设置每隔固定时间更新图标数据 for node
                    clearInterval(timeTicket);
                    timeTicket = setInterval(function (){
                    	if(document.getElementById('nodestatus').style.display!="none"){
                	    	var nstatusurl="/NodesoftManage/NodeStatus?nodeid="+nodename.substring(11)+"&tid="+Math.random();
                	    	var htmlobj=$.ajax({url:nstatusurl,success:function(res){
                	    		var param=res.split(',');
                	        	if(param[0]=="success"){
                	        		nodeoption1.series[0].data[0].value = new Number(param[1]).toFixed(2);
                	        		nodeoption2.series[0].data[0].value = new Number(param[2]).toFixed(2);
                	                nodeChart1.setOption(nodeoption1);
                	                nodeChart2.setOption(nodeoption2);
                	            }//else alert(param[0]);
                	    	}});
                    	}
                    },10000);
                    
//                    nodeChart3 = ec.init(document.getElementById('nodeSoftuse'));
//                    var option3 ={
//                    	    title : {
//                    	        text: '节点软件使用情况',
//                    	        subtext: '当前',
//                	            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
//            	                    fontSize : 10
//            	                }
//                    	    },
//                    	    tooltip : {
//                    	        trigger: 'axis'
//                    	    },
//                    	    legend: {
//                	            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
//            	                    fontSize : 10
//            	                },
//                    	        data:['当前并发用户','最多并发用户' ]
//                    	    },
//                    	    calculable : false,
//                    	    xAxis : [
//                    	        {
//                    	            type : 'value',
//                    	            boundaryGap : [0, 0.01]
//                    	        }
//                    	    ],
//                    	    yAxis : [
//                    	        {
//                    	            type : 'category',
//                    	            data : ['feko','cst','comsol','hfss','总并发(个)']
//                    	        }
//                    	    ],
//                    	    series : [
//                    	        {
//                    	            name:'当前并发用户',
//                    	            type:'bar',
//                    	            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
//                	                    fontSize : 8
//                	                },
//                    	            data:[0, 0, 0, 1, 1]                	        
//                    	        },
//                    	        {
//                    	            name:'最多并发用户',
//                    	            type:'bar',
//                    	            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
//                	                    fontSize : 8
//                	                },
//                    	            data:[1, 1, 1, 1, 2]
//                    	        }
//                    	    ],
//            	            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
//        	                    fontSize : 8
//        	                }
//                    	};
//                    nodeChart3.setOption(option3);
                    
                });

    }
    
function loadServerStatus(){
    	document.getElementById('nodestatus').style.display='none';
    	document.getElementById('serverstatus').style.display='block';
    	document.getElementById('SystemArchi').style.width='60%';
    	
    	require([
                 'echarts',
                 'echarts/chart/gauge'               
             ],function (ec) {
        	// --- 仪表盘---
        	serverChart1 = ec.init(document.getElementById('serverCPU'));
            var option1 = {
            	    tooltip : {
            	        formatter: "{a} <br/>{b} : {c}%"
            	    },
            	    series : [
            	        {
            	            name:'cpu占用率',
            	            type:'gauge',
            	            center : ['50%', '50%'],    // 默认全局居中
            	            radius : [0, '65%'],
            	            startAngle: 140,
            	            endAngle : -140,
            	            min: 0,                     // 最小值
            	            max: 100,                   // 最大值
            	            precision: 0,               // 小数精度，默认为0，无小数点
            	            splitNumber: 10,             // 分割段数，默认为5
            	            axisLine: {            // 坐标轴线
            	                show: true,        // 默认显示，属性show控制显示与否
            	                lineStyle: {       // 属性lineStyle控制线条样式
            	                    color: [[0.2, 'lightgreen'],[0.4, 'orange'],[0.8, 'skyblue'],[1, '#ff4500']], 
            	                    width: 10
            	                }
            	            },
            	            axisTick: {            // 坐标轴小标记
            	                show: true,        // 属性show控制显示与否，默认不显示
            	                splitNumber: 5,    // 每份split细分多少段
            	                length :8,         // 属性length控制线长
            	                lineStyle: {       // 属性lineStyle控制线条样式
            	                    color: '#eee',
            	                    width: 1,
            	                    type: 'solid'
            	                }
            	            },
            	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
            	                show: true,
            	                formatter: function(v){
            	                    switch (v+''){
            	                        case '10': return '弱';
            	                        case '30': return '低';
            	                        case '60': return '中';
            	                        case '90': return '高';
            	                        default: return '';
            	                    }
            	                },
            	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
            	                    color: '#333',
            	                    fontSize : 10
            	                }
            	            },
            	            splitLine: {           // 分隔线
            	                show: true,        // 默认显示，属性show控制显示与否
            	                length :30,         // 属性length控制线长
            	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
            	                    color: '#eee',
            	                    width: 2,
            	                    type: 'solid'
            	                }
            	            },
            	            pointer : {
            	                length : '80%',
            	                width : 8,
            	                color : 'auto'
            	            },
            	            title : {
            	                show : true,
            	                offsetCenter: ['-65%', -10],       // x, y，单位px
            	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
            	                    color: '#333',
            	                    fontSize : 20
            	                }
            	            },
            	            detail : {
            	                show : true,
            	                backgroundColor: 'rgba(0,0,0,0)',
            	                borderWidth: 0,
            	                borderColor: '#ccc',
            	                width: 100,
            	                height: 40,
            	                offsetCenter: ['-60%', 10],       // x, y，单位px
            	                formatter:'{value}%',
            	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
            	                    color: 'auto',
            	                    fontSize : 15
            	                }
            	            },
            	            data:[{value: 50, name: 'CPU'}]
            	        }
            	    ]
            	};
            serveroption1=option1;
            serverChart1.setOption(option1,true);
            
            serverChart2 = ec.init(document.getElementById('serverRAM'));
            var option2 ={
            	    tooltip : {
            	        formatter: "{a} <br/>{b} : {c}%"
            	    },
            	    series : [
            	        {
            	            name:'内存占用率',
            	            type:'gauge',
            	            center : ['50%', '50%'],    // 默认全局居中
            	            radius : [0, '65%'],
            	            startAngle: 140,
            	            endAngle : -140,
            	            min: 0,                     // 最小值
            	            max: 100,                   // 最大值
            	            precision: 0,               // 小数精度，默认为0，无小数点
            	            splitNumber: 10,             // 分割段数，默认为5
            	            axisLine: {            // 坐标轴线
            	                show: true,        // 默认显示，属性show控制显示与否
            	                lineStyle: {       // 属性lineStyle控制线条样式
            	                    color: [[0.2, 'lightgreen'],[0.4, 'orange'],[0.8, 'skyblue'],[1, '#ff4500']], 
            	                    width: 10
            	                }
            	            },
            	            axisTick: {            // 坐标轴小标记
            	                show: true,        // 属性show控制显示与否，默认不显示
            	                splitNumber: 5,    // 每份split细分多少段
            	                length :8,         // 属性length控制线长
            	                lineStyle: {       // 属性lineStyle控制线条样式
            	                    color: '#eee',
            	                    width: 1,
            	                    type: 'solid'
            	                }
            	            },
            	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
            	                show: true,
            	                formatter: function(v){
            	                    switch (v+''){
            	                        case '10': return '弱';
            	                        case '30': return '低';
            	                        case '60': return '中';
            	                        case '90': return '高';
            	                        default: return '';
            	                    }
            	                },
            	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
            	                    color: '#333',
            	                    fontSize : 10
            	                }
            	            },
            	            splitLine: {           // 分隔线
            	                show: true,        // 默认显示，属性show控制显示与否
            	                length :30,         // 属性length控制线长
            	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
            	                    color: '#eee',
            	                    width: 2,
            	                    type: 'solid'
            	                }
            	            },
            	            pointer : {
            	                length : '80%',
            	                width : 8,
            	                color : 'auto'
            	            },
            	            title : {
            	                show : true,
            	                offsetCenter: ['-65%', -10],       // x, y，单位px
            	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
            	                    color: '#333',
            	                    fontSize : 20
            	                }
            	            },
            	            detail : {
            	                show : true,
            	                backgroundColor: 'rgba(0,0,0,0)',
            	                borderWidth: 0,
            	                borderColor: '#ccc',
            	                width: 100,
            	                height: 40,
            	                offsetCenter: ['-60%', 10],       // x, y，单位px
            	                formatter:'{value}%',
            	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
            	                    color: 'auto',
            	                    fontSize : 15
            	                }
            	            },
            	            data:[{value: 30, name: 'RAM'}]
            	        }
            	    ]
            	};
            serveroption2=option2;
            serverChart2.setOption(option2,true);
            
            //设置每隔固定时间更新图标数据 for server
            clearInterval(timeTicket);
            timeTicket = setInterval(function (){
            	if(document.getElementById('serverstatus').style.display!="none"){
        	    	var sstatusurl="/NodesoftManage/ServerStatus?tid="+Math.random();
        	    	var htmlobj=$.ajax({url:sstatusurl,async:true,success:function(){
        	    		var param=htmlobj.responseText.split(',');
        	        	if(param[0]=="success"){
        	        		serveroption1.series[0].data[0].value = new Number(param[1]).toFixed(2);
        	        		serveroption2.series[0].data[0].value = new Number(param[2]).toFixed(2);
        	                serverChart1.setOption(serveroption1);
        	                serverChart2.setOption(serveroption2);
        	            }//else alert(param[0]);
        	    	}});
            	}
            },10000);
        	
           
            serverChart3 = ec.init(document.getElementById('serverHarddisk'));
            var option3 ={
            	    tooltip : {
            	        formatter: "{a} <br/>{b} : {c}%"
            	    },
            	    series : [
            	        {
            	            name:'硬盘使用率',
            	            type:'gauge',
            	            center : ['50%', '50%'],    // 默认全局居中
            	            radius : [0, '65%'],
            	            startAngle: 140,
            	            endAngle : -140,
            	            min: 0,                     // 最小值
            	            max: 100,                   // 最大值
            	            precision: 0,               // 小数精度，默认为0，无小数点
            	            splitNumber: 10,             // 分割段数，默认为5
            	            axisLine: {            // 坐标轴线
            	                show: true,        // 默认显示，属性show控制显示与否
            	                lineStyle: {       // 属性lineStyle控制线条样式
            	                    color: [[0.2, 'lightgreen'],[0.4, 'orange'],[0.8, 'skyblue'],[1, '#ff4500']], 
            	                    width: 10
            	                }
            	            },
            	            axisTick: {            // 坐标轴小标记
            	                show: true,        // 属性show控制显示与否，默认不显示
            	                splitNumber: 5,    // 每份split细分多少段
            	                length :8,         // 属性length控制线长
            	                lineStyle: {       // 属性lineStyle控制线条样式
            	                    color: '#eee',
            	                    width: 1,
            	                    type: 'solid'
            	                }
            	            },
            	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
            	                show: true,
            	                formatter: function(v){
            	                    switch (v+''){
            	                        case '10': return '弱';
            	                        case '30': return '低';
            	                        case '60': return '中';
            	                        case '90': return '高';
            	                        default: return '';
            	                    }
            	                },
            	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
            	                    color: '#333',
            	                    fontSize : 10
            	                }
            	            },
            	            splitLine: {           // 分隔线
            	                show: true,        // 默认显示，属性show控制显示与否
            	                length :30,         // 属性length控制线长
            	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
            	                    color: '#eee',
            	                    width: 2,
            	                    type: 'solid'
            	                }
            	            },
            	            pointer : {
            	                length : '80%',
            	                width : 8,
            	                color : 'auto'
            	            },
            	            title : {
            	                show : true,
            	                offsetCenter: ['-65%', -10],       // x, y，单位px
            	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
            	                    color: '#333',
            	                    fontSize : 20
            	                }
            	            },
            	            detail : {
            	                show : true,
            	                backgroundColor: 'rgba(0,0,0,0)',
            	                borderWidth: 0,
            	                borderColor: '#ccc',
            	                width: 100,
            	                height: 40,
            	                offsetCenter: ['-60%', 10],       // x, y，单位px
            	                formatter:'{value}%',
            	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
            	                    color: 'auto',
            	                    fontSize : 15
            	                }
            	            },
            	            data:[{value: 60, name: 'Hard\nDisk'}]
            	        }
            	    ]
            	};
            serverChart3.setOption(option3,true);        	
        });
    	
    };    
    
function loadnewstatus()
    {            
    	if(document.getElementById('serverstatus').style.display!="none"){
	    	var sstatusurl="/NodesoftManage/ServerStatus?tid="+Math.random();
	    	var htmlobj=$.ajax({url:sstatusurl,async:true,success:function(){
	    		var param=htmlobj.responseText.split(',');
	        	if(param[0]=="success"){
	        		serveroption1.series[0].data[0].value = new Number(param[1]).toFixed(2);
	        		serveroption2.series[0].data[0].value = new Number(param[2]).toFixed(2);
	                serverChart1.setOption(serveroption1);
	                serverChart2.setOption(serveroption2);
	            }else alert(param[0]);
	    	}});
    	}
    }

function refresh(isBtnRefresh){
        if (isBtnRefresh) {
            needRefresh = true;
            focusGraphic();
            return;
        }
        needRefresh = false;
        if (myChart && myChart.dispose) {
            myChart.dispose();
        }
        myChart = echarts.init(domMain, curTheme);
        window.onresize = myChart.resize;
        (new Function(editor.doc.getValue()))();
        myChart.setOption(option, true)
        domMessage.innerHTML = '';
    }

function focusGraphic() {
        if (needRefresh) {
            myChart.showLoading();
            setTimeout(refresh, 1000);
        }
    }