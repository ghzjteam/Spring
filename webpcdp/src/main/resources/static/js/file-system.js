$(document).ready(function(){
	$(".hoverShow").find('.btn-group').hide();
	$(function () { 
		$('#uploadFileModal').on('hide.bs.modal', function () {
	      	//alert('嘿，我听说您喜欢模态框...');
			window.location.reload();
	      })
	      
	});
	$(".hoverShow").mouseover(function (){ 
		//alert($(this).children().length);
//		var item = $(this).children('.hoverHide');
//		item.children("input:first-child").show();
		$(this).find('.btn-group').show();
    }).mouseout(function (){  
    	$(this).find('.btn-group').hide();  
    });  
	
	//设置文件类型的选择框
	$(".selType").click(function(){
 		$("#cfileType").val($(this).text());
    });
	$(".a-fileDownload").click(function(){
		window.open("/fileDownload?fileID="+$(this).attr("id"));
    });
	//点击设置文件，查询文件信息
	$(".setFileBtn").click(function(){
		$("#setFileID").val($(this).attr("id"));
 		$.ajax({
			url:"getFileByID/"+$(this).attr("id")
		}).then(function(data) {
			$("#setFilePath").text(data.name);
			$("#setFileName").val(data.name);
			$("#setFileNote").val(data.note);
	    });
    });
	//点击设置文件提交按钮，查询文件是否重名。
	$("#setFileSubmitBtn").click(function(){
		var parentID = $("#setParentID").val();
        var name = $("#setFileName").val();
        var fileID = $("#setFileID").val();
        
 		$.ajax({
			url:"isSameName?"+"parentID="+parentID+"&fileID="+fileID+"&name="+name
		}).then(function(data) {
			if(data == true){
				alert("此处已有名为“"+name+"”的节点。");
			}
			else{
				//alert(data);
				$("#setfile").submit();
			}
	    });
 		
    });
	
	// 删除确认框 delfilesystem?file_id=fs.fileID
	$(".deleteFile").click(function(){
		$("#fileID").val($(this).attr("id"));
		$("#alertForm").attr("action","delfilesystem");
 		$.ajax({
			url:"getFileByID/"+$(this).attr("id")
		}).then(function(data) {
			$("#text1").text(data.name);
	    });
    });
	
	
	/* 点击上传按钮，表单初始化*/
	$("#uploadBtn").click(function(){
 		$("#upOK").show();
 		$("#upInput").show();
 		$("#upInfo").hide();
 		$("#upReset").show();
    });
	/* 文件夹上传表单，提交*/
	$("#upOK").click(function(){
 		$("#upOK").hide();
 		$("#upInput").hide();
 		$("#upReset").hide();
 		$("#upInfo").show();
 		$("#upInfo").empty();
 		
 		var parentID = $("#upParentID").val();//获取表单的输入值;
        var note = $("#upNote").val();//获取表单的输入值;
        //var folder = $("#folder").val();
        var formData = new FormData();
        //formData.append("folder",$("#folder")[0].files[0]);
        //for(var i=0; i<$('#folder')[0].files.length;i++){
        //    formData.append('folder', $('#folder')[0].files[i]);
        //}
        $.each($('#folder')[0].files,function(key,val){
        	formData.append('folder', val);
   		});
        //formData.append("folder",$("#folder")[0].files);
        formData.append("note",note);
        formData.append("parentID",parentID);
        
        $.ajax({
            type: "post",  //数据提交方式（post/get）
            url: "/uploadFolder",  //提交到的url
            processData : false,// 告诉jQuery不要去处理发送的数据，用于对data参数进行序列化处理 这里必须false
            contentType : false, //必须, 告诉jQuery不要去设置Content-Type请求头
            data: formData
            //data: {"folder":folder,"parentID":parentID,"note":note}//提交的数据
            //dataType: "json"//返回的数据类型格式
        }).then(function(data) {
        	//alert(data);
        	if($.trim(data) == $.trim("未选中文件")){
        		$("#upInfo").append("<p>"+data+" </p>");
        	}
        	else{
        		$.each(data,function(key,val){
           		 	//回调函数有两个参数,第一个是元素索引,第二个为当前值
        			//alert('数组中 ,索引 : '+key+' 对应的值为: '+val);
        			$("#upInfo").append("<li>"+val+" <small class='text-success'>（成功）</small> </li>");
           		});
        	}
        	
	    });
    });
	//文件后边 加入对比按钮
	$(".addCompare").click(function(){
		var number = $("#compareFileForm").children().length;
		
		if (number < 2) {
			var input = "<input name='fileIDs' type='text' hidden='hidden'  readonly='readonly'  style='background: none;' value='"+$(this).attr("id")+"'>";
			var name = "<label class='bg-info rounded'>"+$(this).attr("name")+"</label>";
			var item = "<div class='compareItem'>" + input + name +"</div'>"
			$("#compareFileForm").append(item);
		}
		else{
			alert("对比栏已满，点击选择的选项重新选择。");
		}
		
    });
	//已添加文件的删除
	$("#compareFileForm").click(function(){
		$(this).empty();
    });
	//对比文件，表单的提交
	$("#compareFormSubmitBtn").click(function(){
		var number = $("#compareFileForm").children().length;
		if (number == 2) {
			$("#compareFileForm").submit();
		}
		else{
			alert("没有足够的文件进行对比。");
		}
    });
});
	
	/*th:href="@{/}"
		$.ajax({
        type: "post",  //数据提交方式（post/get）
        url: "/uploadFolder",  //提交到的url
        data: {"parentID":parentID,"note":note},//提交的数据
        dataType: "json",//返回的数据类型格式
        success: function(msg){
            if (msg.success){  //修改成功
                //修改成功处理代码...
            	$("#upInfo").show();
            
            }else {  //修改失败
                //修改失败处理代码...
					alert("上传失败");
            }
        }
    });
		
		*/