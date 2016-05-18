// 删除软件根据Id
var deleteBnt = document.getElementById("deleteBnt");
deleteBnt.onclick = deleteOldSoftRecord;

function deleteOldSoftRecord()
{
	 
	   var IDs = {};
	   var id_str = "";
	   var checkNum= 0 ; // 选中的记录数
	  // 通过以下方式找到table对象，在获取tr，td。然后获取td的html内容  
		var table = document.getElementById("myTable");//获取第一个表格  
		var child = table.getElementsByTagName("tr");//获取行数组  		  ;  
		for(var i = 1; i<child.length;i++)    // 跳过表的标题栏
		 {		    		 
			 var col =  child[i].getElementsByTagName("td"); // 获取某行的列数组
			 var check =  child[i].getElementsByTagName("input"); // 获取该行中input checkbox
			  if(check[0].checked)
			  {
				 var softid = check[0].nextSibling.nodeValue;
				 id_str = id_str.concat(softid+",");
				 checkNum++;
				// alert("插入软件ID："+softid);
			  }
			
		 }
		 if(checkNum==0)
		 {
		   alert("请选择要删除的记录");
		 }
		 else {
			
			 IDs.IDs = id_str;
			// alert(id_str);  	
			 if(confirm("确认要删除选中的记录")){ 
				 
				 $.ajax({   
					 type: "POST",   
					 url: "/WaterSound/DeleteOldRecord?type=soft",   
					 data:IDs,
					 success:function(str_response){
						// alert(str_response);
						 var deleteNum = parseInt(str_response);
						 if(deleteNum>0)
						 {		    		
							 alert("成功删除"+deleteNum+"个软件记录");
						 }
						 else{alert("删除失败！");}
					 }
				 });
			 } 
		}
		 			
}