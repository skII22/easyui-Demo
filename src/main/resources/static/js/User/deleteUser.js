function destroyUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(result){
				if (result){
					$.post('user/delete',{id:row.id},function(result){
						if (result.success){
							$('#dg').datagrid('reload');	// reload the user data
							// $.messager.show({	// show error message
							// 	title: 'Error',
							// 	msg: result.errorMsg
							// });
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: result.errorMsg
							});
							// $('#dg').datagrid('reload');	// reload the user data
						}
					},'json');
				}
			});
		}
	}