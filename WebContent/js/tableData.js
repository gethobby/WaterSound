/**
 * 此文件存放表格操作的一系列函数
 */
var trNum=0;
var model=new Array();           //临时定义的模型文件名称数组，之后可删除
model[0]="ball.stl";            
model[1]="slotted_disk.stl";
model[2]="pr2_head_pan.stl";
model[3]="f002.stl";
var modelindex;
//----------------------------------------------------------------------------------
//以下为页面加载执行函数
/*
 * 
 */
$(document).ready(function () {
	
    //奇偶行不同颜色
	$("#myTable tbody tr:odd").addClass("odd");
    //或者
    //$("#table2 tbody tr:odd").css("background-color", "#bbf"),
    //$("#table2 tbody tr:even").css("background-color", "#ffc")
});
//----------------------------------------------------------------------------------
//以下为操作函数
/*
 * tableclick函数是一个测试函数，测试表格每行的点击惭怍和显示的行号
 */
function tableclick(rowindex){
	trNum=rowindex+1;
	modelindex=rowindex;
	//alert("第" + (modelindex) + "行");

}
/*
 * viewmodel、quickview都是临时的，用于预览3D模型的函数，其中z数组的不同代表表格中stl文件名称显示在不同的列上
 * viewmodel用于mainpage页面
 * quickview用于searchfile页面
 */
function viewmodel(rowindex)
{
	
	var x=document.getElementById("filename");
	var y=x.getElementsByTagName("tr");
	var z=y[rowindex].getElementsByTagName("td");
	var m=z[1].innerHTML;
	//alert("第" + (m) + "行");
	window.open('testmodel.jsp?modelname='+m);
}
function quickview(rowindex)
{
	
	var x=document.getElementById("searchlist");
	var y=x.getElementsByTagName("tr");
	var z=y[rowindex].getElementsByTagName("td");
	var m=z[2].innerHTML;
	//alert("第" + (m) + "行");
	window.open('testmodel.jsp?modelname='+m);
}
function viewrecentmodel(name)
{
	window.open('testmodel.jsp?modelname='+name);
}



