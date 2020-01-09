<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script>

        //验证码刷新
        $(function(){
            $("#but1").click(function(){
                $("#img").prop("src","${pageContext.request.contextPath}/admin/getAdminCode?xxx="+Math.random());
            })
            $("#img").click(function () {
                $(this).prop("src","${pageContext.request.contextPath}/admin/getAdminCode?xxx="+Math.random());
            })
        })

        //登录
        function login() {
            var uname = $("#uname").val();
            var upass = $("#upass").val();
            var ucode = $("#vcode").val();
            $.post(
                "${pageContext.request.contextPath}/admin/loginAdmin",
                "username="+uname+"&password="+upass+"&code="+ucode,
                function (reg) {
                    if (reg == "验证码错误！") {
                        $("#msg").html("验证码错误！");
                    }else if (reg == "用户不存在！"){
                        $("#msg").html("用户不存在！");
                    }else if (reg == "密码错误！"){
                        $("#msg").html("密码错误！");
                    }else if (reg == "登录成功！"){
                        location.href="${pageContext.request.contextPath}/jsp/main.jsp";
                    }
                },
                "json"
            );
        }


    </script>
</head>
<body style=" background: url(${pageContext.request.contextPath}/img/timg.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="${pageContext.request.contextPath}/admin/loginAdmin">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username" id="uname">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password" id="upass">
            </div>
            <div class="form-group">
                <div >
                    <input type="text" class="form-control"  placeholder="验证码" autocomplete="off" name="code" id="vcode">
                </div>
                <div class="text-center">
                    <img id="img" src="${pageContext.request.contextPath}/admin/getAdminCode" >
                    <br/><input id="but1" type="button" value="看不清换一张"/>
                    <br/>
                </div>
            </div>
            <span id="msg" style="color: red"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
            </div>
        </div>
        </form>
    </div>
</div>
</body>
</html>
