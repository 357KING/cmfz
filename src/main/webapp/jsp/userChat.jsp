<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function () {
    //goeasy
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-55c2cbbdd73e4c49940147d6a9c6ddc5", //替换为您的应用appkey
    });
    goEasy.subscribe({
        channel: "cmfz", //替换为您自己的channel
        onMessage: function (message) {
            var data = JSON.parse(message.content);
            alert(data)
        }
    });

    $("#send").click(function(){
        $.ajax({
            url:"${pageContext.request.contextPath}/chat/chat",
            type:"post",
            datatype:"json",
            data: {"message":$("#news").val()},
            success:function (data) {

            }
        })
    })

})
</script>
<div class="page-header">
    <h3>用户管理</h3>
</div>
<ul class="nav nav-tabs">
    <li><a>聊天</a></li>
</ul>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <textarea id="contents" style="width: 1000px;height:200px;"></textarea>
<div class="row">
    <div style="padding: 100px 100px 10px;">
    <form  method="post" id="formContent" enctype="multipart/form-data">
        <div class="input-group col-xs-3">
            <input type="text" class="form-control" id="news"/>
        </div>
         <div class="input-group col-xs-1">
            <button class="btn btn-primary form-control" id="send">发送</button>
         </div>
    </form>
    </div>
</div>
