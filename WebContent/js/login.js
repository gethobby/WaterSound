var curIndex=0;
var autoChange = setInterval(function(){ 
    if(curIndex <  $(".imgList li").length-1){ 
        curIndex ++; 
    }else{ 
        curIndex = 0;
    }
    //调用变换处理函数
    changeTo(curIndex);  
},2500);
function changeTo(num){ 
    $(".imgList").find("li").removeClass("imgOn").hide().eq(num).fadeIn().addClass("imgOn");
    $(".infoList").find("li").removeClass("infoOn").eq(num).addClass("infoOn");
    $(".indexList").find("li").removeClass("indexOn").eq(num).addClass("indexOn");
}
$(".indexList").find("li").each(function(item){ 
    $(this).hover(function(){ 
        clearInterval(autoChange);
        changeTo(item);
        curIndex = item;
    },function(){ 
        autoChange = setInterval(function(){ 
            if(curIndex <  $(".imgList li").length-1){ 
                curIndex ++; 
            }else{ 
                curIndex = 0;
            }
            //调用变换处理函数
            changeTo(curIndex);  
        },2500);
    });
});