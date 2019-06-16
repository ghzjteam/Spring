/**
 * @file 播放音乐插件
 */

(function()
{
	var musicDialog = function( editor, dialogType )
	{
		return {
			title : '播放音乐',
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
					label : '播放音乐',
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
									label : '（选填）音乐名',
									style : 'width: 60%',
									'default' : ''
								},
								{
									id : 'txtUrl',
									type : 'text',
									label : '（必填）选择音乐文件或填写文件地址',
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

	CKEDITOR.dialog.add( 'music', function( editor )
		{
			return musicDialog( editor, 'music' );
		});
})();
