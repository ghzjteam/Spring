/**
 * @file 播放音乐插件
 */

(function()
{
	var videoDialog = function( editor, dialogType )
	{
		return {
			title : '添加视频',
			minWidth : 420,
			minHeight : 160,
			onOk : function()
			{
				var musicUrl = this.getValueOf( 'Link', 'txtUrl' );
				var musicTitle = this.getValueOf( 'Link', 'txtTitle');
				var tempvar='<td>'+musicTitle+'</td></br><audio controls><source src="'+musicUrl+'" type="audio/ogg"><source src="'+musicUrl+'" type="audio/mpeg"></audio>';
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

	CKEDITOR.dialog.add( 'video', function( editor )
		{
			return videoDialog( editor, 'video' );
		});
})();
