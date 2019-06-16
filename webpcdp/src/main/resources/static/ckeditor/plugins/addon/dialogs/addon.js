/**
 * @file 播放音乐插件
 */

(function()
{
	var addonDialog = function( editor, dialogType )
	{
		return {
			title : '添加视频',
			minWidth : 420,
			minHeight : 160,
			onOk : function()
			{
				var musicUrl = this.getValueOf( 'Link', 'txtUrl' );
				var musicTitle = this.getValueOf( 'Link', 'txtTitle');
				// musicUrl 视频路径
				// musicTitle 视频的名称
				var tempvar = '<div class="photo_shows">'+
						'<div style="width:625px; height:400px;padding-top: 20px; margin-left: 33px;margin-bottom: 26px;position:relative;">'+
						'<video width="100%" controls="">'+
						    '<source src="'+musicUrl+'">'+
						    '<source src="32930965f351ad064175a0e9.ogg" type="video/ogg">'+
						    '<source src="32930965f351ad064175a0e9.webm" type="video/webm">'+
						    '<object data="'+musicUrl+'" width="320" height="240">'+
						    '<embed src="{dede:global.cfg_templets_skin/}/img/expressInstall.swf" width="320" height="240">'+
						    '</object>'+ 
						'</video>'+	
						'</div>'+
						'<p>'+musicTitle+'</p>'+		
					'</div>';

				// var tempvar='<td>'+musicTitle+'</td></br><audio controls><source src="'+musicUrl+'" type="audio/ogg"><source src="'+musicUrl+'" type="audio/mpeg"></audio>';
				
				editor.insertHtml(tempvar);				
			},
			contents : [
				{
					id : 'Link',
					label : '添加视频',
					padding : 0,
					type : 'vbox',
					elements :
					[
						{
							type : 'vbox',
							padding : 0,
							children :
							[
								{
									id : 'txtTitle',
									type : 'text',
									label : '（选填）视频名',
									style : 'width: 60%',
									'default' : ''
								},
								{
									id : 'txtUrl',
									type : 'text',
									label : '（必填）选择视频文件或填写文件地址',
									style : 'width: 100%',
									'default' : ''
								},
								{
									type : 'button',
									id : 'browse',
									filebrowser :
									{
										action : 'Browse',
										target: 'Link:txtUrl',
										url: '../include/dialog/select_media.php'
									},
									style : 'float:right',
									hidden : true,
									label : editor.lang.common.browseServer
								}
							]
						}
					]
				}
			]
		};
	};

	CKEDITOR.dialog.add( 'addon', function( editor )
		{
			return addonDialog( editor, 'addon' );
		});
})();
