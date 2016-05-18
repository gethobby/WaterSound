
var g_aData = 
	[ 
        '鼠标提示符---1111',
		'鼠标提示符---2222',
		'鼠标提示符---3333' 
	]; 


var g_oTimerHide=null; 





function bindTopic(aElement) {
	var i = 0;

	for (i = 0; i < aElement.length; i++) {
		aElement[i].miaovIndex = i;
		aElement[i].onmouseover = function(ev) {
			showTopic(this.miaovIndex, window.event || ev);
		};
		aElement[i].onmouseout = function() {
			hideTopic();
		};
		aElement[i].onmousemove = function(ev) {
			var oEvent = window.event || ev;
			setPosition(oEvent.clientX, oEvent.clientY);
		};
	}
} 




function showTopic(index, oEvent) {
	var oTopic = document.getElementById('topic');

	if (g_oTimerHide) {
		clearTimeout(g_oTimerHide);
	}
	var SoftId;
	if(oEvent.currentTarget){SoftId=oEvent.currentTarget.getElementsByTagName("input")[0].value;}
	else{SoftId=oEvent.srcElement.parentNode.getElementsByTagName("input")[0].value;}
	
	$.ajax({
    	url: '/WaterSound/GetDetailInfo?infotype=soft&id='+SoftId,
    	success:function(data){	
    		var len = data.length-1;
    		var showitems=oTopic.getElementsByTagName('div')[1].getElementsByTagName('h1')
    		if(data[0]["英文名称"]){showitems[0].innerHTML = data[0]["英文名称"];}
    		else{showitems[0].innerHTML="--";}
    		if(data[0]["版本"]){showitems[1].innerHTML = data[0]["版本"];}
    		else{showitems[1].innerHTML="--";}
    		if(data[0]["发布更新时间"]){showitems[2].innerHTML = data[0]["发布更新时间"];}
    		else{showitems[2].innerHTML="--";}
    		if(data[0]["一级类型"]){showitems[3].innerHTML = data[0]["一级类型"];}
    		else{showitems[3].innerHTML="--";}
    		if(data[0]["二级类型"]){showitems[4].innerHTML = data[0]["二级类型"];}
    		else{showitems[4].innerHTML="--";}
    		if(data[0]["密级"]){showitems[5].innerHTML = data[0]["密级"];}
    		else{showitems[5].innerHTML="--";}
    		if(data[0]["联系人"]){showitems[6].innerHTML = data[0]["联系人"];}
    		else{showitems[6].innerHTML="--";}
    	}
	});

	oTopic.style.display = 'block';

	setPosition(oEvent.clientX, oEvent.clientY);
} 




function hideTopic() {
	var oTopic = document.getElementById('topic');

	if (g_oTimerHide) {
		clearTimeout(g_oTimerHide);
	}
	g_oTimerHide = setTimeout(function() {
		oTopic.style.display = 'none';
	}, 50);
} 




function setPosition(x, y) {
	var top = document.body.scrollTop || document.documentElement.scrollTop;
	var left = document.body.scrollLeft || document.documentElement.scrollLeft;

	x += left;
	y += top;

	var oTopic = document.getElementById('topic');
	var l = x + 20;
	var t = y - (oTopic.offsetHeight - 20);
	var bRight = true;
	var iPageRight = left + document.documentElement.clientWidth;

	if (l + oTopic.offsetWidth > iPageRight) {
		bRight = false;

		l = x - (oTopic.offsetWidth + 20);
		oTopic.getElementsByTagName('div')[0].className = 'adorn_r';
	} else {
		oTopic.getElementsByTagName('div')[0].className = 'adorn';
	}

	oTopic.style.left = l + 'px';
	oTopic.style.top = t + 'px';
}
