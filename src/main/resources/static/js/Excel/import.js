function upExcel() {
		$('#importExcel').dialog('open').dialog('center').dialog('setTitle','导入Excel');

	}
	function uploadExcel() {
		$("#uploadExcel").form('submit');


		/* 配置导入框 */
		$("#uploadExcel").form({
			type : 'post',
			url : '/excel/import',
			dataType : "json",
			// contentType: 'application/vnd.ms-excel;charset=UTF-8',
			onSubmit: function() {
				var fileName= $('#excel').filebox('getValue');
				//对文件格式进行校验
				var d1=/\.[^\.]+$/.exec(fileName);
				if (fileName == "") {
					$.messager.alert('Excel批量用户导入', '请选择将要上传的文件!');
					return false;
				}else if(d1!=".xlsx"){
					$.messager.alert('提示','请选择xlsx格式文件！','info');
					return false;
				}
				$("#booten").linkbutton('disable');
				return true;
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.success) {
					$.messager.alert('提示!', '导入成功','info',
							function() {
								$("#booten").linkbutton('enable');
								$('#importExcel').dialog('close');
								$('#user').datagrid('reload');
						    });


				} else {
					$.messager.confirm('提示',"导入失败!");
					$("#booten").linkbutton('enable');
					$.messager.show({
						title: 'Error',
						msg: result.errorMsg
					});

				}
			}
		});

	}