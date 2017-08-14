//开发计划展示
function searchSaleChance() {
	var data = {
			customerName: $("#s_customerName").val(),
			overview: $("#s_overview").val(),
			devResult:$("#s_devResult").combobox('getValue')
	}
	$("#dg").datagrid('load', data);
}
//格式化开发状态
function formatDevResult(val, row) {
	if(val==0||val==null){
		return "未开发";
	}else if(val==1){
		return "开发中";
	}else if(val==2){
		return "开发成功";
	}else if(val==3){
		return "开发失败";
	}
}
//格式化操作状态
function formatOptBtn(val, row) {
	var devResult = row.devResult;
	if (devResult < 1 ) {
		return '<a href="javascript:openDev(0, '+ row.id +')" >开发</a>';
	} else {
		return '<a href="javascript:openDev(1, '+ row.id +')" >查看详情</a>';
	}
}
//打开开发计划详情
function openDev (devStatus, saleChanceId) {
	var text = "";
	if (devStatus == 0) { // 开发
		text = "客户开发计划项管理";
	} else {
		text = "查看客户开发计划项";
	}
	//var url = "../cus_dev_plan/index?saleChanceId=" + saleChanceId;
	var url = ctx + "cus_dev_plan/index?saleChanceId=" + saleChanceId + "&show=" + devStatus; 
	window.parent.openTab(text, url, "icon-khkfjh");
	
}