var url;
function newUser(){
	$('#dlg').dialog('open').dialog('center').dialog('setTitle','New User');
	$('#fm').form('clear');
	$('#idtype').combobox('select', '身份证');
	$('#sex').combobox('select', '男');

	url = '/user/save';
}

function saveUser(){
	$('#fm').form('submit',{
		url: url,
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.errorMsg){
				$.messager.show({
					title: 'Error',
					msg: result.errorMsg
				});
			} else {
				$('#dlg').dialog('close');		// close the dialog
				$('#dg').datagrid('reload');	// reload the user data
			}
		}
	});

}

