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
    <script src="../kindeditor/kindeditor-all-min.js"></script>
    <script src="../kindeditor/lang/zh-CN.js"></script>
    <script src="../echarts/echarts.min.js"></script>
    <!-- 将https协议改为http协议 -->
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        // KindEditor初始化时必须放在head标签中,不然会出现无法初始化的情况
        KindEditor.ready(function (K) {
            //K.create("textarea的id")
            //如需自定义配置 在id后使用，{}的形式声明
            window.editor = K.create('#content',{
                width : '850px',
                //上传文章中的图片
                uploadJson:'${pageContext.request.contextPath}/article/uploadImg',
                //展示文章已上传的所有图片
                allowFileManager:true,
                fileManagerJson:'${pageContext.request.contextPath}/article/showAllImg',
                //失去焦点之后触发的事件
                afterBlur:function () {
                    //同步数据方法
                    this.sync();
                }
            });
        });
    </script>
</head>
<body>
<%-- 导航栏 --%>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法州后台管理系统</a>
        </div>
        <div>
            <!--向右对齐-->
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎<span class="glyphicon glyphicon-user"></span>：${admin.username}</a> </li>
                <li><a href="${pageContext.request.contextPath}/login.jsp" ><span class="glyphicon glyphicon-log-in"></span>退出登录</a> </li>
            </ul>
        </div>
    </div>
</nav>
<%-- 栅格 --%>
<div class="container-fluid">
    <div class="row">
        <%-- 手风琴 --%>
        <div class="col-xs-2">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#contentLay').load('${pageContext.request.contextPath}/jsp/user.jsp')">用户信息</a></li>
                                <li><a href="javascript:$('#contentLay').load('${pageContext.request.contextPath}/jsp/userEchart.jsp')">用户注册趋势</a></li>
                                <li><a href="javascript:$('#contentLay').load('${pageContext.request.contextPath}/jsp/map.jsp')">用户所在地分布</a></li>
                                <li><a href="javascript:$('#contentLay').load('${pageContext.request.contextPath}/jsp/userChat.jsp')">聊天</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                上师管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#contentLay').load('${pageContext.request.contextPath}/jsp/guru.jsp')">上师信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#contentLay').load('${pageContext.request.contextPath}/jsp/article.jsp')">文章信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#contentLay').load('${pageContext.request.contextPath}/jsp/album.jsp')">专辑信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#contentLay').load('${pageContext.request.contextPath}/jsp/banner.jsp')">轮播图信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%-- 巨幕 --%>
        <div class="col-xs-10" id="contentLay">
            <div class="jumbotron" style="text-align: center">
                <div class="container" style="text-align: center">
                    <h3>欢迎使用持明法州后台管理系统</h3>
                </div>
                <div id="myCarousel" class="carousel slide">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner" style="text-align: center">
                        <div class="item active">
                            <img src="${pageContext.request.contextPath}/pic/1.jpeg" alt="First slide" width="1200px" height="283px">
                            <div class="carousel-caption">标题 1</div>
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/pic/2.jpg" alt="Second slide"  width="1200px" height="283px">
                            <div class="carousel-caption">标题 2</div>
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/pic/3.jpg" alt="Third slide"  width="1200px" height="283px">
                            <div class="carousel-caption">标题 3</div>
                        </div>
                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel-footer">
    <h5 style="text-align: center">@百知教育 @baizhiedu.com.cn</h5>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-titlezzz" id="myModalLabel">编辑</h4>
            </div>
            <div class="modal-body">
                <form id="artForm">
                    <div class="form-group">
                        <input type="hidden" class="form-control" name="id" id="id" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="title">标题:</label>
                        <input type="text" class="form-control" name="title" id="title" placeholder="请输入：">
                    </div>
                    <div class="form-group">
                        <label for="imgs">封面:</label>
                        <!-- name不能起名和实体类一致 会出现使用String类型接收二进制文件的情况 -->
                        <input type="file" name="imgs" id="imgs"/>
                    </div>
                    <div class="form-group">
                        <label for="content">内容:</label>
                        <textarea id="content" name="content" style="width:500px;height:300px;">
							&lt;strong&gt;HTML内容&lt;/strong&gt;
						</textarea>
                    </div>
                    <div class="form-group">
                        <label for="status">状态:</label>
                        <select class="form-control" id="status" name="status">
                            <option value="1">显示</option>
                            <option value="0">冻结</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="guruId">上师列表:</label>
                        <select class="form-control" id="guruId" name="guruId">
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="sub()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
</body>
</html>