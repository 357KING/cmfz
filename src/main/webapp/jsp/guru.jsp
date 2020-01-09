<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#guruTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/guru/queryAllGuru",
                datatype : "json",
                colNames : [ 'ID', '姓名', '头像', '状态', '法号' ],
                colModel : [
                    {name : 'id',hidden: true},
                    {name : 'name',editable:true},
                    {
                        name : 'photo',editable:true,edittype:"file",
                        formatter : function(data){
                            return "<img width='80px' height='50px' src='"+data+"'></img>";
                        }
                    },
                    {name : 'status',editable:true,formatter:function (data) {
                            if (data=="1"){
                                return "显示";
                            } else{
                                return "冻结";
                            }},editrules:{required:true},edittype:"select",editoptions:{value:"1:显示;2:冻结"}
                    },
                    {name : 'nickName',editable:true}
                ],

                rowNum : 5,
                rowList : [ 1,3,5,10, 20, 30 ],
                pager : '#guruPage',
                page : 1,
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",
                caption : "上师列表",
                autowidth: true,
                styleUI:"Bootstrap",
                multiselect:true,  //复选框
                height:"500px",
                // 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
                editurl:"${pageContext.request.contextPath}/guru/editGuru"
            });
        $("#guruTable").jqGrid('navGrid', '#guruPage',
            {
                edit : true,add : true,del : true,refresh:true,

                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            {
                closeAfterEdit: true,
                afterSubmit:function (response,postDate) {
                    var guruId = response.responseJSON.guruId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/guru/uploadGuru",
                        type:"post",
                        dataType:"json",
                        data:{guruId:guruId},
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
                    var guruId = response.responseJSON.guruId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/guru/uploadGuru",
                        type:"post",
                        dataType:"json",
                        data:{guruId:guruId},
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
    <h3>上师管理</h3>
</div>
<ul class="nav nav-tabs">
    <li><a>上师信息</a></li>
</ul>
<div class="panel">
    <table id="guruTable" border="1" style="text-align: center"></table>
    <div id="guruPage" style="height: 90px"></div>
</div>