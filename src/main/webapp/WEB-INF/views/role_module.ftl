<!DOCTYPE html>
<HTML>
<HEAD>
    <TITLE> ZTREE DEMO - checkbox</TITLE>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx}/ztree/css/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/ztree/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${ctx}/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${ctx}/ztree/js/jquery.ztree.excheck.js"></script>
    <SCRIPT type="text/javascript">
        var setting = {
            check: {
                enable: true,
                chkboxType: { "Y" : "ps", "N" : "ps" }
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onCheck: onCheck
            }
        };

        function onCheck(e, treeId, treeNode) {
            console.log(treeNode.id + "--" + treeNode.checked)
            var data = {roleId:${roleId?c}, moduleId:treeNode.id, 
            			checked:treeNode.checked};
            $.post('dorelate', data, function (resp) {
            	if (resp.resultCode != 1) {
            		alert(resp.resultMessage);
            	}
            });
        }
		
		var zNodes = [];
		<#if modules?has_content >
			<#list modules as module >
				zNodes[${module_index}] = { 
					id:${module.id?c}, 
					pId:${module.parentId?default('0')}, 
					name:"${module.moduleName}", 
					open:true,
					checked:${module.checked?string('true', 'false')}
				};
			</#list>
		</#if>
		/*
        var zNodes =[
            { id:1, pId:0, name:"随意勾选 1", open:true},
            { id:11, pId:1, name:"随意勾选 1-1", open:true},
            { id:111, pId:11, name:"随意勾选 1-1-1", open:true},
            { id:1111, pId:111, name:"随意勾选 1-1-1-1"},

            { id:112, pId:11, name:"随意勾选 1-1-2"},
            { id:12, pId:1, name:"随意勾选 1-2", open:true},
            { id:121, pId:12, name:"随意勾选 1-2-1"},
            { id:122, pId:12, name:"随意勾选 1-2-2"},
            { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
            { id:21, pId:2, name:"随意勾选 2-1"},
            { id:22, pId:2, name:"随意勾选 2-2", open:true},
            { id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
            { id:222, pId:22, name:"随意勾选 2-2-2"},
            { id:23, pId:2, name:"随意勾选 2-3"}
        ];*/

        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        });
    </SCRIPT>
</HEAD>

<BODY>
<div class="content_wrap">
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>

</div>
</BODY>
</HTML>