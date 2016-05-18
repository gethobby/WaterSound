/**
 * 
 */

require.config({
    paths: {
        echarts: '../js/bootstrap/www/js'
    }
});
function loadUserLog(username)
{

	loadTimeLog(username);
	loadRecentDataStatistics(username);
}
function loadTimeLog(user){
	var xdate=[];
	var ytime=[];
    $.ajax({
    	url: '/NodesoftManage/Getplatformlog?logname=userlog&username='+user,
    	success:function(data){	
    		var len = data.length-1;
    		while(len>=0)
    		{
    			xdate.push(data[len].days);
    			ytime.push((data[len].staytime/60).toFixed(2));
    			len--;
    		}	
    	},
    	async:false
  });
	var userlogChart1;
    var option1;
	require([
             'echarts',
             'echarts/chart/line',
             'echarts/chart/bar'
             ],
             function (ec) 
             {
		        	// --- 带提示框的折线图/柱状图---
		        	userlogChart1 = ec.init(document.getElementById('timeInterval'));
		        	userlogChart1 .setOption(option1 =
		        	{
		            	    title : {
		            	        text: '使用时间统计(min)',
		            	        subtext: '本周',
		        	            textStyle: 
		        	            {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		    	                    fontSize : 10
		    	                }
		            	    },
		            	    tooltip : 
		            	    {
		            	        trigger: 'axis',
		            	    },
		            	    legend: 
		            	    {
		        	            textStyle: 
		        	            {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		    	                    fontSize : 10
		    	                },
		            	        data:['用户登录停留时间','使用软件资源时间' ],
		                        selected:{
		                        	'用户登录停留时间':true,
		                        	'使用软件资源时间':true
		                        }
		            	    },
		            	    toolbox: {
		            	        show : true,
		            	        feature : {
		            	            magicType : {show: true, type: ['line', 'bar']},
		            	            restore : {show: true},
		            	            saveAsImage : {show: true}
		            	        }
		            	    },
		            	    calculable : true,
		            	    yAxis : 
		            	    [
		            	        {
		            	            type : 'value',
		            	            boundaryGap : [0, 0.1]
		            	        }
		            	    ],
		            	    xAxis : 
		            	    [
		            	        {
		            	            type : 'category',
		            	            data : xdate//['周一','周二','周三','周四','周五','周六','周日']
		            	        }
		            	    ],
		            	    series : 
		            	    [
		            	        {
		            	            name:'用户登录停留时间',
		            	            type:'bar',
		            	            textStyle: 
		            	            {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		        	                    fontSize : 8
		        	                },
		            	            data : ytime//(function(){
//		            	            	var d[];
//		            	            	//return d;
//		            	            })               	        
		            	        },
		            	        {
		            	            name:'使用软件资源时间',
		            	            type:'bar',
		            	            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		        	                    fontSize : 8
		        	                },
		            	            data:[]
		            	        }
		            	    ],
		    	            textStyle: 
		    	            {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                        fontSize : 8
		                    }
		            });
             });

}
function loadRecentTool(user)
{}
function loadRecentDataStatistics(user)
{
	var htmlobj=$.ajax({
		url:'/NodesoftManage/Getplatformlog?logname=datastatistic&username='+user,
		async:false
		});
	var param=htmlobj.responseText.split(',');
	var userlogChart2;
	var option2 = {
    	    title : {
    	        text: '使用数据统计',
    	        subtext: '本周',
	            textStyle: 
	            {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontSize : 10
                }
    	    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} K ({d}%)"
		    },
		    legend: {
		        orient : 'horizontal',
		        x : 'center',
		        data:['计算结果','模型访问','其他']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'数据统计',
		            type:'pie',
		            radius : ['50%', '70%'],
		            itemStyle : {
		                normal : {
		                    label : {
		                        show : false
		                    },
		                    labelLine : {
		                        show : false
		                    }
		                },
		                emphasis : {
		                    label : {
		                        show : true,
		                        position : 'center',
		                        textStyle : {
		                            fontSize : '15',
		                            fontWeight : 'bold'
		                        }
		                    }
		                }
		            },
		            data:[
		                {value:param[1], name:'计算结果'},
		                {value:param[0], name:'模型访问'},
		                {value:135, name:'其他'},
		            ]
		        }
		    ]
		};
	require([
             'echarts',
             'echarts/chart/pie',
             ],
             function (ec) 
             {
		        	// --- 带提示框的折线图/柱状图---
		        	userlogChart2 = ec.init(document.getElementById('dataStatistics'));   
		        	userlogChart2.setOption(option2);
             });
}
function loadRecentFile()
{}
function loadRecentResult()
{}