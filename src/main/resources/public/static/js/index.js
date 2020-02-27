function login() {
    var userName=$("input[name='userName']").val();
    var userPwd=$("input[name='password']").val();

    if(isEmpty(userName)){
        alert("请输入用户名!");
        return;
    }
    if(isEmpty(userPwd)){
        alert("请输入密码!");
        return;
    }

    $.ajax({
        type:"post",
        url:ctx+"/user/login",
        data:{
            userName:userName,
            userPwd:userPwd
        },
        dataType:"json",
        success:function (data) {
            console.log(data);
            if(data.code==200){
                var result =data.result;
                /**
                 * 写入cookie 到浏览器 省去了服务器端创建cookie,通过response.addCookie()发送给浏览器,并且
                 * 通过Cookie[] cookies = request.getCookies()获取cookie的过程
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