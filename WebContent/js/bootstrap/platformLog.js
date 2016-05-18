/**
 * 
 */
require.config({
    paths: {
        echarts: '../js/bootstrap/www/js'
    }
});
var dataMap = {};

loadPlatformLog();
function loadPlatformLog()
{
	//近期活跃用户
	loadPlatformUS();
	//近期产生数据
	loadPlatformDS();
}
function loadPlatformDS()
{
	var htmlobj=$.ajax({
		url:'/NodesoftManage/Getplatformlog?logname=datastatistic',
		async:false
		});
	var param=htmlobj.responseText.split(',');
	var platformChart1;
	var option1= {
		    title : {
		        text: '网站总数据量',
		        subtext: '本周',
		        x:'left'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        y : 'center',
		        data:['计算结果','模型访问','其他']
		    },
		    calculable : true,
		    series : [
		        {
		            name:'数据产生',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
		                {value:param[1], name:'计算结果'},
		                {value:param[0], name:'模型访问'},
		                {value:234, name:'其他'}
		            ]
		        }
		    ]
		};
	
	//chart2	                    
	dataMap.dataGDP = dataFormatter({
		'7':[11,67,161],
	    '6':[38,52,136],
	    '5':[51,44,114],
	    '4':[49,39,100],
	    '3':[60,31,84],
	    '2':[50,25,69],
	    '1':[43,21,60]
	});

	dataMap.dataPI = dataFormatter({
		'7':[26,37,87],
	    '6':[25,28,72],
	    '5':[21,24,61],
	    '4':[20,21,52],
	    '3':[18,16,43],
	    '2':[14,13,34],
	    '1':[12,10,29]
	});

	dataMap.dataSI = dataFormatter({
		'7':[11,51,20],
	    '6':[10,71,18],
	    '5':[8,15,14],
	    '4':[8,17,14],
	    '3':[8,17,13],
	    '2':[8,18,10],
	    '1':[8,28,9]
	});
	var platformChart2;
	var option2 = {
		    timeline:{
		        data:[
		           '0000','0001','0002','0003','0004','0005','0006'
		        ],
		        label : {
		            formatter : function(s) {
		            	var temp=['周一','周二','周三','周四','周五','周六','周日'];
		                return temp[s.slice(3,4)];
		            }
		        },
		        autoPlay : true,
		        playInterval : 1000
		    },
		    options:[
		        {
		            title : {
		                'text':'周一数据量统计',
		                'subtext':''
		            },
		            tooltip : {
		            	'trigger':'axis'
		            },
		            legend : {
		                x:'center',
		                'data':['计算结果','模型访问','其他'],
		                'selected':{
		                    '计算结果':true,
		                    '模型访问':true,
		                    '其他':true
		                }
		            },
		            toolbox : {
		                'show':true, 
		                x: 'right', 
		                orient : 'vertical',
		                'feature':{
		                    'restore':{'show':true},
		                    'saveAsImage':{'show':true}
		                }
		            },
		            calculable : true,
		            grid : {'y':80},
		            xAxis : [{
		                'type':'category',
		                'axisLabel':{'interval':0},
		                'data':[
		                    '数据量'
		                ]
		            }],
		            yAxis : [
		                {
		                    'type':'value',
		                    'name':'（K）'
		                    //'max':8000
		                },
		            ],
		            series : [
		                {
		                    'name':'计算结果',
		                    'type':'bar',
		                    'data': dataMap.dataGDP['1']
		                },
		                {
		                    'name':'模型访问','type':'bar',
		                    'data': dataMap.dataPI['1']
		                },
		                {
		                    'name':'其他','type':'bar',
		                    'data': dataMap.dataSI['1']
		                }
		            ]
		        },
		        {
		            title : {'text':'周二数据量统计'},
		            series : [
		                {'data': dataMap.dataGDP['2']},
		                {'data': dataMap.dataPI['2']},
		                {'data': dataMap.dataSI['2']}

		            ]
		        },
		        {
		            title : {'text':'周三数据量统计'},
		            series : [
		                {'data': dataMap.dataGDP['3']},
		                {'data': dataMap.dataPI['3']},
		                {'data': dataMap.dataSI['3']}

		            ]
		        },
		        {
		            title : {'text':'周四数据量统计'},
		            series : [
		                {'data': dataMap.dataGDP['4']},
		                {'data': dataMap.dataPI['4']},
		                {'data': dataMap.dataSI['4']}
		            ]
		        },
		        {
		            title : {'text':'周五数据量统计'},
		            series : [
		                {'data': dataMap.dataGDP['5']},
		                {'data': dataMap.dataPI['5']},
		                {'data': dataMap.dataSI['5']}
		            ]
		        },
		        {
		            title : {'text':'周六数据量统计'},
		            series : [
		                {'data': dataMap.dataGDP['6']},
		                {'data': dataMap.dataPI['6']},
		                {'data': dataMap.dataSI['6']}
		            ]
		        },
		        {
		            title : {'text':'周日数据量统计'},
		            series : [
		                {'data': dataMap.dataGDP['7']},
		                {'data': dataMap.dataPI['7']},
		                {'data': dataMap.dataSI['7']}
		            ]
		        }

		    ]
		};
	require([
             'echarts',
             'echarts/chart/line',
             'echarts/chart/bar',
             'echarts/chart/pie'
             ],
             function (ec) 
             {
				platformChart1=ec.init(document.getElementById('dataStatistics1'));
				platformChart1.setOption(option1);
				platformChart2=ec.init(document.getElementById('dataStatistics2'));
				platformChart2.setOption(option2);
				platformChart2.connect(platformChart1);
				platformChart1.connect(platformChart2);
             });
	                    
	
}
function dataFormatter(obj) {
    var pList = ['计算结果','模型访问','其他'];
    var temp;
    //var max = 0;
    //var week=['周一','周二','周三','周四','周五','周六','周日'];
    for (var day = 1; day <= 7; day++) {
        temp = obj[day];
        for (var i = 0, l = temp.length; i < l; i++) {
            //max = Math.max(max, temp[i]);
            obj[day][i] = {
                name : pList[i],
                value : temp[i]
            }
        }
        //obj[day+'max'] = Math.floor(max/100) * 100;
    }
    return obj;
}
function loadPlatformUS()
{
	var platformChart3;
	var option = {
		    title : {
		        text : '用户活跃情况',
		        subtext : ''
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter : function (params) {
		            var date = new Date(params.value[0]);
		            data = date.getFullYear() + '-'
		                   + (date.getMonth() + 1) + '-'
		                   + date.getDate();
		            return data + '<br/>'+
		               '访问人数：'+ params.value[1] + ', <br/>' +
		               '平均停留时间'+params.value[2]+"min";
		        }
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    dataZoom: {
		        show: true,
		        start : 0
		    },
		    grid: {
		        y2: 80
		    },
		    xAxis : [
		        {
		            type : 'time',
		            splitNumber:10
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            type: 'line',
		            showAllSymbol: true,
		            symbolSize: function (value){
		                return Math.round(value[2]/10) + 2;
		            },
		            data: (function () {
		                var d = [];
		                $.ajax({
		                	url: '/NodesoftManage/Getplatformlog?logname=platformlog&tid='+Math.random(),
		                	dataType: 'json',
		                	async:false,
		                	success:function(data){	
		                		var len = data.length-1;
		                		while(len>=0)
		                		{
		                			d.push([new Date(data[len].days),data[len].counts,(data[len].avgs/60).toFixed(2)]);
		                			len--;
		                		}	
		                	}
		              });
		                
		                return d;
		            })()
		        }
		    ]
		};
	require([
             'echarts',
             'echarts/chart/line'
             ],
             function (ec) 
             {
				platformChart3=ec.init(document.getElementById('userActive'));
				platformChart3.setOption(option);

             });
		                    
}


