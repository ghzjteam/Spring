var idL = $('#fileIDL').text();
var idR = $('#fileIDR').text();
$(document).ready(function() {

	//初始化左右侧的代码
	initMergelyContent(idL, 'lhs');
	initMergelyContent(idR, 'rhs');

});
function initMergelyContent(fileID, side) {
	$.ajax({
		url : "getFileString?fileID=" + fileID
	}).then(function(data) {
		$('#mergely').mergely(side, data);
	});
};
function saveToServer(side) {
	var fileID;
	var text;
	var formData = new FormData();
	if(side=='rhs'){
		fileID=idR;
	}
	else{
		fileID=idL;
	}
	text = $('#mergely').mergely('get',side);
	formData.append("fileID",fileID);
    formData.append("text",text);
	$.ajax({
		type: "post", 
         url: "/saveToServer",
         processData : false,
         contentType : false,
         data: formData
	}).then(function(data) {
		alert(data);
	});
};

//下载
function funDownload(filename,content) {
    const eleLink = document.createElement('a');
    eleLink.download = filename;
    eleLink.style.display = 'none';
    const blob = new Blob([content]);
    eleLink.href = URL.createObjectURL(blob);
    document.body.appendChild(eleLink);
    eleLink.click();
    document.body.removeChild(eleLink);
};

//这些按钮因为被原来的点击事件覆盖，只能采用这种方式
document.getElementById('left_save_server').addEventListener('click', function () {
	saveToServer("lhs");
},true);

document.getElementById('right_save_server').addEventListener('click', function () {
	saveToServer("rhs");
},true);

document.getElementById('left_reimport').addEventListener('click', function () {
	initMergelyContent(idL, 'lhs');
},true);

document.getElementById('right_reimport').addEventListener('click', function () {
	initMergelyContent(idR, 'rhs');
},true);

document.getElementById('left_save_local').addEventListener('click', function () {
	funDownload($('#fileNameL').text(),$('#mergely').mergely('get','lhs'));
},true);

document.getElementById('right_save_local').addEventListener('click', function () {
	funDownload($('#fileNameR').text(),$('#mergely').mergely('get','rhs'));
},true);

document.getElementById('new_window').addEventListener('click', function () {
	//alert(window.location.href);
	window.open(window.location.href,"_blank");
},true);

document.getElementById('code-import').addEventListener('click', function () {
	alert("把文件拖入相应的位置即可自动导入代码");
},true);
/*
//$('#mergely').mergely('swap');//左右交换
//$('#mergely').mergely('rhs',"public\nACC");//设置右侧的内容
//$('#mergely').mergely('clear','rhs');//右侧清空
//$('#mergely').mergely('diff');//获得diff文件
window.location.href(设置或获取整个 URL 为字符串)
window.location.protocol(设置或获取 URL 的协议部分)
window.location.host(设置或获取 URL 的主机部分)
window.location.port(设置或获取与 URL 关联的端口号码)
window.location.pathname(设置或获取与 URL 的路径部分（就是文件地址）)
window.location.search(设置或获取 href 属性中跟在问号后面的部分)
window.location.hash(设置或获取 href 属性中在井号“#”后面的分段)

*/