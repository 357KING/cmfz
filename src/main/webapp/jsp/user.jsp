<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#userTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/user/queryAllUser",
                datatype : "json",
                colNames : [ 'ID', '手机号', '密码', '盐值', '状态' , '头像', '姓名', '法号', '性别', '签名', '地址','注册时间', '最后登陆时间'],
                colModel : [
                    {name : 'id',hidden: true},
                    {name : 'phone',editable:true},
                    {name : 'password',editable:true},
                    {name : 'salt',editable:true},
                    {name : 'status',editable:true,formatter:function (data) {
                            if (data=="1"){
                                return "显示";
                            } else{
                                return "冻结";
                            }},editrules:{required:true},edittype:"select",editoptions:{value:"1:显示;2:冻结"}
                    },
                    {
                        name : 'photo',editable:true,edittype:"file",
                        formatter : function(data){
                            return "<img width='80px' height='50px' src='"+data+"'></img>";
                        }
                    },
                    {name : 'name',editable:true},
                    {name : 'nickName',editable:true},
                    {name : 'sex',editable:true},
                    {name : 'sign',editable:true},
                    {name : 'location',editable:true},
                    {name : 'rigestDate',editable:true,edittype:"date"},
                    {name : 'lastLogin',hidden: true},
                ],

                rowNum : 5,
                rowList : [ 1,3,5,10, 20, 30 ],
                pager : '#userPage',
                page : 1,
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",
                caption : "用户列表",
                autowidth: true,
                styleUI:"Bootstrap",
                multiselect:true,  //复选框
                height:"500px",
                // 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
                editurl:"${pageContext.request.contextPath}/user/editUser"
            });
        $("#userTable").jqGrid('navGrid', '#userPage',
            {
                edit : true,add : true,del : true,refresh:true,

                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            {
                closeAfterEdit: true,
                afterSubmit:function (response,postDate) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/uploadUser",
                        type:"post",
                        dataType:"json",
                        data:{userId:userId},
                        fileElementId:"photo",
                        success:function (data) {
                            $("#guruTable").trigger("reloadGrid");
                        }
                    });
                    return postDate;
                }

            },{
                closeAfterAdd: true,
                afterSubmit:function (response,postDate) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/uploadUser",
                        type:"post",
                        dataType:"json",
                        data:{userId:userId},
                        fileElementId:"photo",
                        success:function (data) {
                            $("#guruTable").trigger("reloadGrid");
                        }
                    });
                    return postDate;
                }

            },{
                closeAfterDel: true
            });
    });


</script>
<div class="page-header">
    <h3>用户管理</h3>
</div>
<ul class="nav nav-tabs">
    <li><a>用户信息</a></li>
</ul>
<div class="panel">
    <table id="userTable" border="1" style="text-align: center"></table>
    <div id="userPage" style="height: 90px"></div>
</div>
