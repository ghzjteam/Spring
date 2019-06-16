/*
Copyright (c) 2003-2010, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/
CKEDITOR.editorConfig = function( config ) {
    config.language = 'zh-cn';
    config.uiColor = '#AADC6E';
    config.height = 300;
    config.enterMode = CKEDITOR.ENTER_BR;// 去掉<p>
    config.shiftEnterMode = CKEDITOR.ENTER_BR;// 去掉<p>
    config.toolbarCanCollapse = true; //工具栏可折叠
    config.toolbarGroups = [
        { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
        { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
        { name: 'forms', groups: [ 'forms' ] },
        { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
        { name: 'insert', groups: [ 'insert' ] },
        '/',
        { name: 'styles', groups: [ 'styles' ] },
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'colors', groups: [ 'colors' ] },

        { name: 'others', groups: [ 'others' ] },
        { name: 'links', groups: [ 'links' ] },
        { name: 'about', groups: [ 'about' ] },
        { name: 'tools', groups: [ 'tools' ] }
    ];
    config.removeButtons = 'About,Flash,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,NewPage,Templates,Scayt,Language,Smiley,Iframe,Save,SelectAll,CreateDiv,BidiRtl,BidiLtr,ShowBlocks';
    config.filebrowserBrowseUrl = 'ckfinder/ckfinder.html';
    config.filebrowserImageBrowseUrl = 'ckfinder/ckfinder.html?type=Images';
    config.filebrowserFlashBrowseUrl = 'ckfinder/ckfinder.html?type=Flash';
    config.filebrowserUploadUrl = 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
    config.filebrowserImageUploadUrl = 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
    config.filebrowserFlashUploadUrl = 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';
};
