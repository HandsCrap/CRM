
$(function() {
    var saleChanceId = $("#saleChanceId").val();
    $("#dg").edatagrid({
    	//查询开发计划
        url:'list?saleChanceId=' + saleChanceId,
        //添加开发计划 
        saveUrl:'add?saleChanceId=' + saleChanceId,
        //修改开发计划
        updateUrl:'update',
        //删除
        destroyUrl:'delete',
        
        onBeforeSave:function () {
        	// 执行前。。。
        }
    });
});
//结束机会开发
function updateSaleChanceDevResult(devResult) {
    var param = {};
    param.saleChanceId = $("#saleChanceId").val();
    param.devResult = devResult;
    $.post("update_dev_result", param, function(result) {
        if(result.resultCode == 1) {
            $.messager.alert("系统提示", "操作成功！");
        } else {
            $.messager.alert("系统提示","操作失败！");
        }
    });
}

function addPlan() {
	$('#dg').edatagrid('addRow');
}

function save() {
	$('#dg').edatagrid('saveRow');//调用saveurl
	$('#dg').datagrid('acceptChanges');
	$('#dg').edatagrid('reload');
}
//删除行
function deletePlan() {
	$('#dg').edatagrid('destroyRow');
}

function cancelRow () {
	$('#dg').edatagrid('cancelRow');
}
