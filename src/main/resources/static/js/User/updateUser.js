	
	function editUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$('#dlg').dialog('open').dialog('center').dialog('setTitle','Edit User');
			$('#fm').form('load',row);
			url = '/user/update/'+row.id;
		}
	}