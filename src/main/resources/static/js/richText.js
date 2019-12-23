var allEditors = document.querySelectorAll('textarea');

for (var i = 0; i < allEditors.length; ++i) {

	CKEDITOR.replace(allEditors[i], {
		
		toolbarGroups:[
			{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
			{ name: 'paragraph', groups: [ 'list', 'align', 'indent', 'blocks', 'bidi', 'paragraph' ] },
			{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
			{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
			{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
			{ name: 'forms', groups: [ 'forms' ] },
			{ name: 'links', groups: [ 'links' ] },
			{ name: 'insert', groups: [ 'insert' ] },
			{ name: 'styles', groups: [ 'styles' ] },
			{ name: 'colors', groups: [ 'colors' ] },
			{ name: 'tools', groups: [ 'tools' ] },
			{ name: 'others', groups: [ 'others' ] },
			{ name: 'about', groups: [ 'about' ] }
		],

		removeButtons : 'Templates,Save,Source,NewPage,Preview,Print,About,Image,Flash,Table,Iframe,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField',
        
    })
    
    
}
//https://ckeditor.com/latest/samples/toolbarconfigurator/index.html#help-content