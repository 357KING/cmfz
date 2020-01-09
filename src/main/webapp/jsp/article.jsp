<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#articletable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/article/queryAll",
                datatype : "json",
                colNames : [ 'ID', '文章标题', '封面', '内容', '创建时间','发布时间', '状态', '所属上师ID','操作' ],
                colModel : [
                    {name : 'id',hidden: true},
                    {name : 'title',align:"center"},
                    {
                        name : 'img',align:"center",
                        formatter : function(data){
                            return "<img width='80px' height='50px' src='"+data+"'></img>";
                        },edittype:"file"
                    },
                    {name : 'content',align:"center"},
                    {name : 'createDate',align:"center"},
                    {name : 'publishDate',align:"center"},
                    {name : 'status',align:"center",formatter:function (data) {
                            if (data=="1"){
                                return "显示";
                            } else{
                                return "冻结";
                            }}
                    },
                    {name : 'guruId',align:"center"},
                    {
                        name : 'option',align:"center",
                        formatter:function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('"+rowObject.id+"')\">修改</button>&nbsp;&nbsp;";
                             button+= "<button type=\"button\" class=\"btn btn-danger\" onclick=\"delete('"+rowObject.id+"')\">删除</button>";
                            return button;
                        }
                    }
                ],

                rowNum : 5,
                rowList : [ 1,3,5,10, 20, 30 ],
                pager : '#articlePage',
                page : 1,
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",
                caption : "文章列表",
                autowidth: true,
                styleUI:"Bootstrap",
                multiselect:true,  //复选框
                height:"500px",
                // 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
                editurl:"${pageContext.request.contextPath}/article/delete"
            });
        $("#articletable").jqGrid('navGrid', '#articlePage',
            {
                del : true,refresh:true, deltext:"删除"
            }, {
                closeAfterDel: true
            });
    });


    //点击添加文章触发事件
     function addArticle() {
         //清除缓存
         $("#artForm")[0].reset();
         KindEditor.html("#content","");
        $.ajax({
            url:"${pageContext.request.contextPath}/guru/showAllGuru",
            datatype: "json",
            type:"post",
            success:function (data) {
                var option = "<option value='3'>请选择所属上师</option>";
                //遍历，获取上师列表
                data.forEach(function (guru) {
                    option += "<option value='"+guru.id+"'>"+guru.name+"</option>";
                })
                $("#guruId").html(option);
            }
        });
         $("#myModal").modal("show");
     }

     //点击修改时触发事件
    function update(id) {
        // 使用jqGrid("getRowData",id) 目的是屏蔽使用序列化的问题
        // $("#articleTable").jqGrid("getRowData",id); 该方法表示通过Id获取当前行数据
        var data = $("#articletable").jqGrid("getRowData",id);
        $("#id").val(data.id);
        $("#title").val(data.title);
        // 更替KindEditor 中的数据使用KindEditor.html("#editor_id",data.content) 做数据替换
        KindEditor.html("#content",data.content);
        //处理状态信息
        $("#status").val(data.status);
        var option ="";
        if (data.status=="显示"){
            option += "<option selected value='1'>显示</option>";
            option += "<option value='0'>冻结</option>";
        } else{
            option += "<option value='1'>显示</option>";
            option += "<option selected value='0'>冻结</option>";
        }
        $("#status").html(option);

        //处理上师信息
        $.ajax({
            url:"${pageContext.request.contextPath}/guru/showAllGuru",
            datatype:"json",
            type:"post",
            success:function (gurus) {
                var option2 = "<option value='3'>请选择所属上师</option>";
                gurus.forEach(function (guru) {
                        console.log("选中id="+guru.id);
                        console.log(data.guruId);
                    if (guru.id==data.guruId) {
                        option2 += "<option selected value="+guru.id+">"+guru.name+"</option>"
                    }
                    option2 += "<option value="+guru.id+">"+guru.name+"</option>"
                });
                $("#guruId").html(option2);
            }
        });
        $("#myModal").modal("show");
    }

     //文件添加及修改方法
    function sub() {
        $.ajaxFileUpload({
            url:"${pageContext.request.contextPath}/article/editArticle",
            type:"post",
            // ajaxFileUpload 不支持serialize() 格式化形式
            // 只支持{"id":1,XXX:XX}
            // 解决: 1. 手动封装  2. 更改ajaxFileUpload的源码
            // 异步提交时 无法传输修改后的kindeditor内容,需要刷新
            data:{
                "id":$("#id").val(),
                "title":$("#title").val(),
                "img":$("#imgs").val(),
                "content":$("#content").val(),
                "status":$("#status").val(),
                "guruId":$("#guruId").val()
            },
            datatype:"json",
            fileElementId:"imgs",
            success:function (data) {
                $("#articletable").trigger("reloadGrid");
            }
        });
    }
</script>
<div class="page-header">
    <h3>文章管理</h3>
</div>
<ul class="nav nav-tabs">
    <li><a>文章信息</a></li>
    <li><a onclick="addArticle()">添加文章</a></li>
</ul>

<div class="panel">
    <table id="articletable" border="1"></table>
    <div id="articlePage" style="height: 90px"></div>
</div>