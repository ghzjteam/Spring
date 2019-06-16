/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

/**
 * @file Image plugin
 */

CKEDITOR.plugins.add( 'mp3',
{
	init : function( editor )
	{
		var pluginName = 'mp3';

		// Register the dialog.
		CKEDITOR.dialog.add( pluginName, this.path + 'dialogs/mp3.js' );

		// Register the command.
		editor.addCommand( pluginName, new CKEDITOR.dialogCommand( pluginName ) );

		// Register the toolbar button.
		editor.ui.addButton( 'mp3',
			{
				label : '音乐',
				icon : 'images/mp3.gif',
				command : pluginName
			});
	}
} );
