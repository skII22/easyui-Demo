	function ExcelWrite(){
		// $.ajax({
		// 	url: 'excel/export',
		// 	success: function(result) {
		// 		if (result.errorMsg){
		// 			$.messager.show({
		// 				title: 'Error',
		// 				msg: result.errorMsg
		// 			});
		// 		} else {
		//
		// 		}
		// 	}
		//
		// });

		location.href="excel/export";



		// $.ajax({
		// fileDownload(content, name = '导出信息', suffix = 'xls'){
		// 	// 添加字节序标识，避免乱码
		// 	const data = `uFEFF${content}`;
		// 	const blob = new Blob([data], { type: 'text/xls,charset=UTF-8' });
		// 	const downloadElement = document.createElement('a');
		// 	// 创建下载链接
		// 	const href = window.URL.createObjectURL(blob);
		// 	downloadElement.href = href;
		// 	// 下载文件名
		// 	downloadElement.download = `${name}.${suffix}`;
		// 	document.body.appendChild(downloadElement);
		// 	downloadElement.click();
		// 	// 移除元素
		// 	document.body.removeChild(downloadElement);
		// 	// 释放blob对象
		// 	window.URL.revokeObjectURL(href);
		// }
		// });

		/**
		 content 创建文件的内容
		 blob 类文件对象
		 name 创建的文件名 suffix 文件后缀
		 下面的就是创建一个a标签然后设置href以及download属性，并执行下载操作，然后移除a标签
		 */

	}





	function ExcelTemp(){

		$.ajax({
		    url: 'excel/temp',
			success: function(result) {
				if (result.errorMsg){
					$.messager.show({
						title: 'Error',
						msg: result.errorMsg
					});
				} else {

				}
			}
		});
		$.ajax({
		fileDownload(content, name = '模板', suffix = 'xls'){
			// 添加字节序标识，避免乱码
			const data = `uFEFF${content}`;
			const blob = new Blob([data], { type: 'text/xls,charset=UTF-8' });
			const downloadElement = document.createElement('a');
			// 创建下载链接
			const href = window.URL.createObjectURL(blob);
			downloadElement.href = href;
			// 下载文件名
			downloadElement.download = `${name}.${suffix}`;
			document.body.appendChild(downloadElement);
			downloadElement.click();
			// 移除元素
			document.body.removeChild(downloadElement);
			// 释放blob对象
			window.URL.revokeObjectURL(href);
		}
	});

	}