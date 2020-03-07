function saveCustomerServe() {
    $('#fm').form('submit', {
        url:ctx+"/customer_serve/saveOrUpdateCustomerServe",
        onSubmit: function(param){
            param.createPeople=$.cookie("trueName");
            return $("fm").form("validate");
        },
        success:function(data){
            data=JSON.parse(data);
            if (data==200){
                $.messager.alert("来自crm","服务创建成功");
                clearFormData();
            }else{
                $.messager.alert("来自crm",data.message,"error");
            }
        }
    });

}

function clearFormData() {
    $("input").val("");
    $("#serveType").combobox("setValue","");
}