/**
 * 打开对话框
 * @param dlgId   对话框节点id
 * @param title   对话框标题
 */
function openDialog(dlgId,title) {
    $("#"+dlgId).dialog("open").dialog("setTitle",title);
}

function closeDialog(dlgId) {
    $("#"+dlgId).dialog("close");
}

/**
 * 添加与更新记录
 * @param saveUrl    添加记录后端url 地址
 * @param updateUrl   更新记录后端url 地址
 * @param dlgId     对话框id
 * @param search    多条件搜索方法名
 * @param clearData   清除表单方法名
 */
function saveOrUpdateRecode(saveUrl,updateUrl,dlgId,search,clearData) {
    var url = saveUrl;
    if(!(isEmpty($("input[name='id']").val()))){
        url = updateUrl;
    }
    /**
     *使普通表单成为ajax提交的表单
     * 提交的参数为表单的内容
     */
    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            /**
             * Json.parse()将json格式的字符串转换成Json对象
             * @type {any}
             */
            data =JSON.parse(data);
            if(data.code==200){
                closeDialog(dlgId);
                search();
                clearData();
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}


/**
 *
 * @param dataGridId  表格id
 * @param formId      表单id
 * @param dlgId      对话框id
 * @param title       对话框标题
 */
function openModifyDialog(dataGridId,formId,dlgId,title) {
    var rows=$("#"+dataGridId).datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待修改的数据!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量修改!","error");
        return;
    }

    $("#"+formId).form("load",rows[0]);
    openDialog(dlgId,title);
}

/**
 *
 * @param dataGridId  表格id
 * @param deleteUrl   后端删除url 地址
 * @param search     搜索方法
 */
function deleteRecode(dataGridId,deleteUrl,search) {
    var rows=$("#"+dataGridId).datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待删除的数据!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量删除!","error");
        return;
    }
    $.messager.confirm("来自crm","确定删除选中的记录?",function (r) {
        if(r){
            /*
            确认删除走aja请求,后台对应的url删除数据库相关信息
             */
            $.ajax({
                type:"post",
                url:deleteUrl,
                data:{
                    /**
                     * 得到当前行的id并作为参数传到后台
                     * 调用后台的delete方法
                     */
                    id:rows[0].id
                },
                dataType:"json",
                success:function (data) {
                    /**
                     * data是后台传过来的ResultInfo对象
                     */
                    if(data.code==200){
                        search();
                    }else{
                        $.messager.alert("来自crm",data.msg,"error");
                    }
                }
            })

        }
    })
}