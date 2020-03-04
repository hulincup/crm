function formatterState(value) {
    if (value==0){
        return "未流失";
    }else if(value==1){
        return "已流失";
    }else {
        return "未知";
    }
}

function searchCustomerLoss() {
    $("#dg").datagrid("load",{
        cusNo:$("#s_cusNo").val(),
        cusName:$("#s_cusName").val(),
        state:$("#s_state").combobox("getValue")
    })
}

function formatterOp(val,rowData) {
    var state=rowData.state;
    var title=rowData.cusName+"_暂缓措施";
    var href='javascript:openCustomerRepTab("'+title+'","'+rowData.cusNo+'")';
    if(state==0){
        return "<a href='"+href+"'>添加暂缓</a>";
    }
    if(state==1){
        return "<a href='"+href+"'>查看详情</a>";
    }
}

function openCustomerRepTab(title,cusNo) {
    window.parent.openTab(title,ctx+"/customer_rep/index?cusNo="+cusNo);
}