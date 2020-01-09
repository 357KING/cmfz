<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    //页面加载时
    $(function () {
        $("#bannertable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/banner/queryByPage",
                datatype : "json",
                colNames : [ 'ID', '标题', '图片', '链接', '时间','描述', '状态' ],
                colModel : [
                    {name : 'id',hidden: true},
                    {name : 'title',align:"center", editable: true},
                    {
                        name : 'url',align:"center",editable: true,edittype: 'file',
                        editoptions: {enctype:'multipart/form -data'},
                        formatter : function(data){
                            return "<img width='80px' height='50px' src='"+data+"'></img>";
                        }
                    },
                    {name : 'href',align:"center",editable: true},
                    {name : 'createDate',align:"center",editable: true,edittype:"date"},
                    {name : 'description',align:"center",editable: true},
                    {name : 'status',align:"center",editable: true,formatter:function (data) {
                        if (data=="1"){
                            return "显示";
                        } else{
                            return "冻结";
                        }},editrules:{required:true},edittype:"select",editoptions:{value:"1:显示;2:冻结"}
                    }
                ],

                rowNum : 5,
                rowList : [ 1,3,5,10, 20, 30 ],
                pager : '#bannerPage',
                page : 1,
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",
                caption : "轮播图",
                autowidth: true,
                styleUI:"Bootstrap",
                multiselect:true,  //复选框
                height:"500px",
                // 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
                editurl:"${pageContext.request.contextPath}/banner/save"
            });
        $("#bannertable").jqGrid('navGrid', '#bannerPage',
            {
                edit : true,add : true,del : true,refresh:true,

                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            {
                closeAfterEdit: true,
                afterSubmit: function (response,postDate) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url:"${pageContext.request.contextPath}/banner/uploadBanner",
                        type:"post",
                        dataType:"json",
                        //发送添加图片的id到controller
                        data:{bannerId:bannerId},
                        //指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {
                            $("#bannertable").trigger("reloadGrid");
                        }
                    });
                    //防止页面报错
                    return postDate;
                }
            },{
                closeAfterAdd: true,
                //数据库添加轮播图之后上传，上传完成后需要更改url路径，需要获取添加的轮播图id
                afterSubmit: function (response,postDate) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url:"${pageContext.request.contextPath}/banner/uploadBanner",
                        type:"post",
                        dataType:"json",
                        //发送添加图片的id到controller
                        data:{bannerId:bannerId},
                        //指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {
                            $("#bannertable").trigger("reloadGrid");
                        }
                    });
                    //防止页面报错
                    return postDate;
                }
            },{
                closeAfterDel: true
        });
    });

    //导出轮播图
    $("#outBanner").click(function () {
        $.post(
            "${pageContext.request.contextPath}/banner/outBanner",
            function (data) {
                if (data == "ok"){
                    alert("导出成功！")
                } else{
                    alert("导出失败！")
                }
            },
            "json"
        )
    });

    //轮播图导入前打开模态框
    $("#inBanner").click(function () {
        $("#myModals").modal("show");
    });

    //导入轮播图
    function subBanner() {
        $.ajaxFileUpload({
            url:"${pageContext.request.contextPath}/banner/inBanner",
            type:"post",
            datatype: "json",
            fileElementId:"banners",
            success:function (data) {
                alert(data);
            }
        })
    }

    //轮播图模板
    //导出轮播图
    $("#anBanner").click(function () {
        $.post(
            "${pageContext.request.contextPath}/banner/anBanner",
            function (data) {
                alert(data);
            },
            "json"
        )
    });
</script>
<div class="page-header">
    <h3>轮播图管理</h3>
</div>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>
    <li><a id="inBanner">轮播图导入</a></li>
    <li><a id="outBanner">轮播图导出</a></li>
    <li><a id="anBanner">轮播图模板</a></li>
</ul>
<div class="panel">
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModals" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-titlezzz">导入轮播图</h4>
                </div>
                <div class="modal-body">
                    <form id="artForm" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="banners">请选择文件:</label>
                            <input type="file" name="banners" id="banners" value=""/>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="subBanner()">保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <table id="bannertable" border="1" style="text-align: center"></table>
    <div id="bannerPage" style="height: 90px"></div>
</div>
