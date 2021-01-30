function login() {
    /*var userName=$("input[name='userName']").val();
    var userPwd=$("input[name='password']").val();*/
    var userName=$("#userName").val();
    var userPwd=$("#userPwd").val();

    if(isEmpty(userName)){
        alert("请输入用户名!");
        return;
    }
    if(isEmpty(userPwd)){
        alert("请输入密码!");
        return;
    }

    $.ajax({
        type:"post",//告诉后台自己的请求方式
        url:ctx+"/user/login",//后台返回对应URL的资源给前台
        data:{
            userName:userName,
            userPwd:userPwd
        },
        dataType:"json",//前台传递给后台的数据是json形式
        success:function (data) {
            console.log(data);
            if(data.code==200){
                var result =data.result;
                /**
                 * 写入cookie 到浏览器 省去了服务器端创建cookie,通过response.addCookie()发送给浏览器,并且
                 * 通过Cookie[] cookies = request.getCookies()获取cookie的过程,封装的jquery.cookie直接将
                 * cookie存在浏览器
                 * data就是后台传过来的resultInfodate.result,拿到的是后台存在UserModel信息
                 */
                $.cookie("userIdStr",result.userIdStr);
                $.cookie("userName",result.userName);
                $.cookie("trueName",result.trueName);
                window.location.href=ctx+"/main";
            }else{
                alert(data.msg);
            }
        }
    })
}