function searchOrders() {
    $("#dg").datagrid("load",{
        orderNo:$("#s_orderNo").val(),
        state:$("#s_state").combobox("getValue")
    })
}

function formatterState(value) {
    if (value==0){
        return "未支付";
    }else if (value==1) {
        return "已支付";
    }else {
        return "未知";
    }
    
}

function formatterOp() {
    var href="javascript:openOrderDetailDialog()";
    return "<a href='"+href+"'>查看详情</a>";
}



function openOrderDetailDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待查看的订单数据!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量查看!","error");
        return;
    }
    $.ajax({
        type:"post",
        url:ctx+"/order/queryOrderDetailByOrderId",
        dataType:"json",
        data:{
            orderId:rows[0].id
        },
        success:function (data) {
            $("#fm").form("load",data);
        }
    })
/*
    $("#fm").form("load",ctx+"/order/queryOrderDetailByOrderId?orderId="+rows[0].id);
*/
    /**
     * load方法背后走的是ajax请求
     */
    $("#dg02").datagrid("load",{
       orderId:rows[0].id
    });
    openDialog("dlg","订单详情查看");
}

function searchOrderDetails() {
    $("#dg02").datagrid("load",{
       orderId:$("#dg").datagrid("getSelections")[0].id,
       goodsName:$("#s_goodsName").val(),
        type:$("#s_price").combobox("getValue")
    });
}