/**
 * 
 */
require.config({
    paths: {
        echarts: '../js/bootstrap/www/js'
    }
});
loadNodeLog();
function loadNodeLog()
{
	//load node use statistic
	loadNodeStatistics();
	//load soft use statistic
	loadSoftStatistic();
}
function loadNodeStatistics()
{
	var xdate=[];
	var yusetime=[];
    $.ajax({
    	url: '/NodesoftManage/Getplatformlog?logname=nodelog',
    	success:function(data){	
    		var len = data.length-1;
    		while(len>=0)
    		{
    			xdate.push(data[len].days);
    			yusetime.push(data[len].avgusetime);
    			len--;
    		}	
    	},
    	async:false
  });
	var nodelogChart;
	var option = {
			title : {
                'text':'节点机器使用情况',
                'subtext':''
            },
		    tooltip : {
		        trigger: 'axis'
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    legend: {
		        data:['使用频数','平均占用时间']
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : xdate//['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : '使用频数',
		            axisLabel : {
		                formatter: '{value} /用户'
		            }
		        },
		        {
		            type : 'value',
		            name : '平均占用时间',
		            axisLabel : {
		                formatter: '{value} /min'
		            }
		        }
		    ],
		    series : [

		        {
		            name:'使用频数',
		            type:'bar',
		            data:[3, 0, 1, 2, 1, 0, 1, 2, 1, 1, 0, 3]
		        },
		        {
		            name:'平均占用时间',
		            type:'line',
		            yAxisIndex: 1,
		            data:yusetime//[20, 22, 33, 45, 63, 102, 203, 234, 230, 165, 120, 62]
		        }
		    ]
		};
	require([
             'echarts',
             'echarts/chart/line',
             'echarts/chart/bar',
             ],
             function (ec) 
             {
				nodelogChart=ec.init(document.getElementById('nodeStatistics'));
				nodelogChart.setOption(option);
             });

		                    
}
function loadSoftStatistic()
{
	var nodelogChart2;
	var option2 = {
			title : {
                'text':'各节点软件使用情况',
                'subtext':'当前'
            },
		    tooltip : {
		        trigger: 'axis'
		    },
		    toolbox: {
		        show : true,
		        y: 'bottom',
		        feature : {
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    legend: {
		        data:['node1','node2','node3']
		    },
		    yAxis : [
		        {
		            type : 'category',
		            splitLine : {show : false},
		            data : ['FEKO','COMSOL','CST','HFSS'],
		            position: 'left'
		        }
		    ],
		    xAxis : [
		        {
		            type : 'value',
		            position: 'top'
		        }
		    ],
		    series : [
		        {
		            name:'node1',
		            type:'bar',
		            tooltip : {trigger: 'item'},
		            stack: '软件使用频数',
		            data:[3, 5, 1,2]
		        },
		        {
		            name:'node2',
		            type:'bar',
		            tooltip : {trigger: 'item'},
		            stack: '软件使用频数',
		            data:[2, 1, 3, 6]
		        },
		        {
		            name:'node3',
		            type:'bar',
		            tooltip : {trigger: 'item'},
		            stack: '软件使用频数',
		            data:[4, 1, 1, 2]
		        }
		    ]
		};
	require([
             'echarts',
             'echarts/chart/bar',
             ],
             function (ec) 
             {
				nodelogChart2=ec.init(document.getElementById('softStatistics'));
				nodelogChart2.setOption(option2);
             });                    
}