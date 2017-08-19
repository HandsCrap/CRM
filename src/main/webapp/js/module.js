$(document).ready(function(){
	$("#grade").combobox({
		onChange:function(grade){
			if(grade==0){
				$("#parentIdDiv").hide();
			}else{
				$("#parentIdDiv").show();
			}
			$("#parentId").combobox({
				valueField:'id', //值字段
                textField:'moduleName', //显示的字段
                url:'find_by_grade?grade=' + (grade - 1),
                panelHeight:'auto',
                value:'',//初始值归零
                editable:true//不可编辑，只能选择
			});
		}
	});
})

//打开添加窗体
function openAddDialog(){
	$("#dlg").dialog("open");
	$("#dlg").dialog("setTitle","添加模块信息");
}
function formatGrade(value){
	switch (value) {
	case 0:
		return "根级"	;
	case 1:
		return "第一级"	;
	case 2:
		return "第二级"	;
	}
} 
//保存
function saveModule(){
	//获取id，没有则为添加，有则为修改
	var id = $("#id").val();
	
	var url = "add";
	if(id !=null && $.trim(id).length>0 && !isNaN(id)){
		url="update";
	}
	$("#fm").form("submit",{
		url:url,
		onSubmit:function(){
			return $(this).form("validate");//参数校验
		},
		success:function(resp){
			var data =JSON.parse(resp);
			if(data.resultCode==0){
				$.messager.alert("错误提示",data.resultMsg);
			}else{
				$.messager.alert("提示",data.resultMsg);
				closeDialog();
				$("#dg").datagrid("reload");
			}
		}
	})
}
function closeDialog(){
	$("#dlg").dialog('close');
	$("#fm").form('clear');
}
//修改
function openModifyDialog() {
	var slectedRows = $("#dg").datagrid('getSelections');
	if(slectedRows.length !=1){
		$.messager.alert("提示","请选择一条记录进行修改!")
		return;
	}
	var row = slectedRows[0];
	//给表单赋值
	$("#fm").form("load",row);
	$("#dlg").dialog("open").dialog('setTitle','修改模块信息');
	// 展示父级菜单
	if (row.grade != 0) { // 显示父级菜单
		$("#parentIdDiv").show();
		$("#parentId").combobox('clear'); // 先清空父级菜单
		// 调用后台获取父级菜单
		$("#parentId").combobox({
			url:'find_by_grade?grade=' + (row.grade - 1),
			valueField:'id',
			textField:'moduleName',
			panelHeight:'auto',
			value:row.parentId
		});
	} else {
		$("#parentId").combobox('setValue', '');
	}
	
}
//删除
function deleteModule(){
	var ids = [];
	var slectedRows = $("#dg").datagrid('getSelections');
	if(slectedRows.length==0){
		$.messageer.alert("提示","请选择要删除的记录")
		return;
	}
	for (var i = 0; i < slectedRows.length; i++) {
		var row = slectedRows[i];
		ids.push(row.id);
	}
	
	$.messager.confirm('确认删除', '您确定要删除<span style="color:red">' + ids.length + "</span>条记录吗？", function(r){
		if (r) {
			// 然后执行ajax请求删除
			$.post('delete', {ids: ids.join(',')}, function(resp) {
				if (resp.resultCode == 0) {
					$.messager.alert("提示", resp.resultmsg);
				} else {
					$.messager.alert("提示", resp.result);
					$("#dg").datagrid('reload');
				}
			});
		}
	});
}
