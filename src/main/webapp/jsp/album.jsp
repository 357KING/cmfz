<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script type="text/javascript">
        //页面加载后
        $(function () {
            $("#ftable").jqGrid(
                {
                    url : "${pageContext.request.contextPath}/album/queryAll",
                    datatype : "json",
                    height : "350px",
                    colNames : [ 'ID', '标题', '评分', '作者', '播音','集数', '简介', '封面', '发布时间', '状态' ],
                    colModel : [
                        {name : 'id',hidden: true},
                        {name : 'title',editable:true},
                        {name : 'score',editable:true},
                        {name : 'author',editable:true},
                        {name : 'broadcast',editable:true},
                        {name : 'count',editable:true},
                        {name : 'description',editable:true},
                        {
                            name : 'conver',editable:true,edittype: "file",
                            formatter:function (data) {
                                return "<img width='70px' height='40px' src='"+data+"'></img>"
                            }
                        },
                        {name : 'createDate',editable:true,edittype:"date"},
                        {name : 'status',editable:true,
                            formatter:function (data) {
                                if (data==1){
                                    return "显示";
                                } else{
                                    return "冻结";
                                }
                            },
                            edittype:"select",editoptions:{value:"1:显示;2:冻结"}
                        }
                    ],
                    rowNum : 5,
                    rowList : [ 1,2,8, 10, 20, 30 ],
                    pager : '#fpage',
                    sortname : 'id',
                    viewrecords : true,
                    autowidth : true,
                    styleUI:"Bootstrap",
                    multiselect : true,
                    subGrid : true,
                    caption : "专辑信息",
                    editurl: "${pageContext.request.contextPath}/album/editAlbum",
                    // subgrid_id:父级行的Id  row_id:当前的数据Id
                    subGridRowExpanded : function(subgrid_id, row_id) {
                        //调用生成子表格的方法
                        addSubgrid(subgrid_id, row_id);
                    },
                    //删除表格的方法
                    subGridRowColapsed : function(subgrid_id, row_id) {

                    }
                });
            $("#ftable").jqGrid('navGrid', '#fpage', {
                add : true,
                edit : true,
                del : true,
                edittext:"编辑",
                addtext:"添加",
                deltext:"删除"
            },{
                closeAfterEdit: true,
                afterSubmit:function (response,postDate) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/uploadAlbum",
                        type: "post",
                        dataType: "json",
                        data:{albumId:albumId},
                        fileElementId: "status",
                        success:function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    return postDate;
                }
            },{
                closeAfterAdd: true,
                afterSubmit:function (response,postDate) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        //指定上传的路径
                        url:"${pageContext.request.contextPath}/album/uploadAlbum",
                        type:"post",
                        dataType:"json",
                        //将添加的图片id传给controller
                        data:{albumId:albumId},
                        //指定上传的input框id
                        fileElementId:"status",
                        success:function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    return postDate;
                }
            },{
                closeAfterDel: true
            });
        });

        //生成子表格
        function addSubgrid(subgrid_id, row_id) {
            //声明子表格id
            var sid = subgrid_id + "table";
            //声明子表格工具栏
            var spage = subgrid_id + "page";
            $("#" + subgrid_id).html("<table id='" + sid + "' class='scroll'></table><div id='"  + spage + "' style='height: 50px'></div>");
            jQuery("#" + sid).jqGrid(
                {
                    url : "${pageContext.request.contextPath}/chapter/queryAll?albumId="+row_id,
                    datatype : "json",
                    colNames : [ 'ID', '章节名称', '音频大小','音频时长','发布时间','所属专辑', '操作' ],
                    colModel : [
                        {name : "id",hidden: true},
                        {name : "title",editable:true},
                        {name : "size"},
                        {name : "time"},
                        {name : "createTime",editable:true,edittype:"date"},
                        {name : "albumId",hidden: true},
                        {name : "url",editable:true,
                            formatter:function (cellvalue, options, rowObject) {
                                var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"downloads('"+cellvalue+"')\">下载</button>&nbsp;&nbsp;";
                                //                                                                声明一个onPlay方法 --> 显示模态框 ---> 为audio标签添加src  需要url路径作为参数传递
                                //                                                              'onPlay(参数)' ---> \"onPlay('"+cellvalue+"')\"
                                button+= "<button type=\"button\" class=\"btn btn-danger\" onclick=\"onPlay('"+cellvalue+"')\">在线播放</button>";
                                return button;
                            },edittype:"file",eidtoptions:{enctype:"multipart/form-data"}
                        }
                    ],
                    rowNum : 20,
                    pager : spage,
                    autowidth: true,
                    styleUI: "Bootstrap",
                    multiselect: true,   //复选框
                    editurl: "${pageContext.request.contextPath}/chapter/editChapter?albumId="+row_id

                });
            $("#" + sid).jqGrid('navGrid',
                "#" + spage, {
                    edit : true,
                    add : true,
                    del : true,
                    refresh : true,
                    edittext: "编辑",
                    addtext: "添加",
                    deltext: "删除"
                },{
                    closeAfterEdit: true,
                    afterSubmit:function (response, postDate) {
                        var chapterId = response.responseJSON.chapterId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chapter/uploadChapter",
                            type:"post",
                            dataType:"json",
                            fileElementId:"url",
                            data:{chapterId:chapterId},
                            success:function (data) {
                                $("#sid").trigger("reloadGrid");
                            }
                        });
                        return postDate;
                    }
                },{
                    closeAfterAdd: true,
                    afterSubmit:function (response, postDate) {
                        var chapterId = response.responseJSON.chapterId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chapter/uploadChapter",
                            type:"post",
                            dataType:"json",
                            fileElementId:"url",
                            data:{chapterId:chapterId},
                            success:function (data) {
                                $("#sid").trigger("reloadGrid");
                            }
                        });
                        return postDate;
                    }
                },{
                    closeAfterDel: true
                });
        }

        //在线播放
        function onPlay(cellvalue) {
            //将音频路径传给src
            $("#music").attr("src",cellvalue);
            $("#myModal").modal("show");
        }
        
        //下载
        function downloads(cellvalue) {
            location.href = "${pageContext.request.contextPath}/chapter/downChapter?url="+cellvalue;
        }
    </script>
</head>
<body>
<div class="page-header">
    <h3>专辑管理</h3>
</div>
<ul class="nav nav-tabs">
    <li><a>专辑信息</a></li>
</ul>
<div class="panel">
    <table id="ftable" border="1" style="text-align: center"></table>
    <div id="fpage" style="height: 90px"></div>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <audio id="music" src="" controls="controls">
        </audio>
    </div><!-- /.modal -->
</div>
</body>
</html>