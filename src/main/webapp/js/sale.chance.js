//匹配分配状态
function formatState(value, row, index) {
//	JSON.parse(json字符串); eval
//	console.log(value + '----' + JSON.stringify(row) + '---' + index);
	if(value == null) {
		return '未知';
	} else if (value == 0) {
		return '未分配';
	} else if (value == 1) {
		return '已分配';
	}
}
//搜索
function searchSaleChance() {
	var customerName = $("#s_customerName").val();
	var overview = $("#s_overview").val();
	var createMan = $("#s_createMan").val();
	var state = $("#s_state").combobox('getValue');
	$('#dg').datagrid('reload',{
		customerName: customerName,
		overview: overview,
		createMan: createMan,
		state: state
	});
}
//弹出添加框
function openSaleChanceAddDialog(){
	$('#dlg').dialog('open').dialog('setTitle','添加营销机会');
}
//保存营销机会
function saveSaleChance(){
	//非空判断
	var customerName = $('#customerId').combobox('getText');
	if(customerName==null||$.trim(customerName).length==0){
		$.messager.alert("提示","请选择客户!");
		return
	}
	$('#customerName').val(customerName);
	//提交事务
	$('#fm').form('submit',{
		//请求提交方法
		url:'add',
		//提交前验证
		onSubmit:function(){
			return $(this).form('validate');
		},
		//对请求结果做处理
		success:function(data){
			//转为json对象
			data=JSON.parse(data);
			//成功
			if(data.resultCode==1){
				$.messager.alert("提示",data.result);
				resetValue();
				$('#dlg').dialog('close');
				$('#dg').datagrid('reload');
			}else{
			//失败
				alert(data.resultMessage);
			}
		}
	})
}
//关闭弹出框
function closeSaleChanceDialog() {
	resetValue();
	$("#dlg").dialog('close');
}
//置空
function resetValue(){
	$('#fm').form('reset');
}
//删除
function deleteSaleChance(){
	var ids = [];
	//获取选中行
	var selectedRows=$('#dg').datagrid('getSelections');
	//非空判断
	if(selectedRows.length==0){
		$.messager.alert('提示','请选择要删除的记录！');
		return
	}
	
	for(var i = 0 ;i<selectedRows.length;i++){
		var row = selectedRows[i];
		ids.push(row.id);
	}
	$.messager.confirm('删除记录！','您确认想要删除<span style="color:red">'+ids.length+'</span>记录吗？',function(r){    
	    if (r){   
	    	//ajax请求删除
	    	$.post(
	    			'delete',
	    			{ids:ids.join(',')},
	    			function(resp){
	    				if(resp.resultCode==0){
	    					$.messager.alert("提示",resp.resultMessage)
	    				}else{
	    					$.messager.alert("提示",resp.result);
	    					$('#dg').datagrid('reload');
	    				}
	    			});
	    }    
	}); 
}
//修改
function openSaleChanceModifyDialog(){
	//判断：选中且只选中一条记录
	var selecteRows = $("#dg").datagrid('getSelections');
	if(selecteRows!=1){
		$.messager.alert("提示","请选择一条要修改的记录！");
		return
	}
	//将选中行的记录添加到form表单
	var row = selecteRows[0];
	$("#fm").form("load",row);
	var createMan = $.cookie("userName");
	$("#createMan").val(createMan);
	$("#dlg").dialog("open").dialog("setTitle","修改营销机会");
}