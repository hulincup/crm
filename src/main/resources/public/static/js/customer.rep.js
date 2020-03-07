$(function () {
    $("#dg").edatagrid({
        url:ctx+"/customer_rep/list?lossId="+$("#lossId").val(),
        saveUrl:ctx+"/customer_rep/save?lossId="+$("#lossId").val(),
        updateUrl:ctx+"/customer_rep/update",
        destroyUrl:ctx+"/customer_rep/delete"
    })
})

function saveCustomerRep() {
    $('#dg').edatagrid('saveRow');
    $('#dg').edatagrid("load");
}

function delCustomerRep() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择确认删除的数据记录!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量删除操作!","error");
        return;
    }
    $('#dg').edatagrid("destroyRow");
    $('#dg').edatagrid("load");
}

function confirmLoss() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择确认流失的数据记录!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量确认流失操作!","error");
        return;
    }
    $.messager.confirm("来自crm","确认流失该客户吗?",function (r) {
        if (r){
            $.messager.prompt("来自crm","请输入客户流失原因",function (R) {
                if (R){
                    alert(R);
                }
            })
        }
    })
}