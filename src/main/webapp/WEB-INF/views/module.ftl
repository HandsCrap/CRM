<!doctype html>
<html>
<head>
	<#include "include/common.header.ftl" >
</head>
<body style="margin: 1px">
    <table id="dg" title="模块管理" class="easyui-datagrid"
           fitColumns="true" pagination="true" rownumbers="true"
           url="${ctx}/module/list" fit="true" toolbar="#tb" singleSelect="false">
        <thead>
        <tr>
            <th field="cb" checkbox="true" align="center"></th>
            <th field="id" width="50" align="center">编号</th>
            <th field="moduleName" width="100" align="center">模块名称</th>
            <th field="moduleStyle" width="100" align="center">模块样式</th>
            <th field="url" width="150" align="center">路径/方法
            <th field="optValue" width="50" align="center">操作权限</th>
            <th field="grade" width="50" align="center" formatter="formatGrade">层级</th>
            <th field="orders" width="50" align="center">排序</th>
            <th field="updateDate" width="80" align="center">修改时间</th>
        </tr>
        </thead>
    </table>
    
    <div id="tb">
        <div>
            <a href="javascript:openAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
            <a href="javascript:openModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
            <a href="javascript:deleteModule()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
        </div>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:620px;height:250px;padding: 10px 20px"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
            <input type="hidden" name="id" id="id" />
            <table cellspacing="8px">
                <tr>
                    <td>模块名称：</td>
                    <td>
                        <input type="text" id="moduleName" name="moduleName" class="easyui-validatebox" required="true"/>&nbsp;
                        <font color="red">*</font>
                    </td>
                    <td>模块样式：</td>
                    <td>
                        <input type="text" id="moduleStyle" name="moduleStyle" />
                    </td>
                </tr>
                <tr>
                    <td>排序：</td>
                    <td>
                        <input type="text" id="orders" name="orders" class="easyui-validatebox" required="true"/>&nbsp;
                        <font color="red">*</font>
                    </td>
                    <td>路径/方法：</td>
                    <td>
                        <input type="text" id="url" name="url" class="easyui-validatebox" required="true"/>&nbsp;
                        <font color="red">*</font>
                    </td>
                </tr>
                <tr>
                    <td>操作权限：</td>
                    <td>
                        <input type="text" id="optValue" name="optValue" class="easyui-validatebox" required="true"/>&nbsp;
                        <font color="red">*</font>
                    </td>

                    <td>层级：</td>
                    <td>
                        <select class="easyui-combobox" id="grade" name="grade" >
                            <option value=0 >根级</option>
                            <option value=1 >第一级</option>
                            <option value=2 >第二级</option>
                        </select>
                    </td>
                </tr>
                <tr id="parentIdDiv">
                    <td>父级菜单</td>
                    <td>
                        <input type="text" id="parentId" name="parentId" class="easyui-combobox" />
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:saveModule()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
        <a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
    </div>
    
	<script src="${ctx}/js/module.js"></script>
</body>
</html>