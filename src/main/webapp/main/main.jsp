<%@ page pageEncoding="UTF-8" contentType="text/html;UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>

</head>
<body>
<!--顶部导航-->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">应学视频App后台管理系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎：<span class="text text-primary">${sessionScope.admin.username}</span></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/exit">退出 <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span></a></li>

            </ul>
        </div>
    </div>
</nav>
<!--栅格系统-->
<!--左边手风琴部分-->
<div class="container-fluid">
    <!--左边-->
    <div class="row">
        <div class="col-sm-2">
            <!--手风琴-->
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true" align="center">

                <!--用户菜单-->
                <div class="panel panel-info" >
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>

                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <button class="btn btn-info">
                                        <a href="javascript:$('#centerLayout').load('${path}/user/user.jsp')">用户展示</a>
                                    </button>
                                </li><br>
                                <li><button class="btn btn-info"><a href="javascript:$('#centerLayout').load('${path}/test/TestEchartsGoEasy.jsp')">用户统计</a></button></li><br>
                                <li><button class="btn btn-info"><a href="javascript:$('#centerLayout').load('${path}/test/TestMapContr.jsp')">用户分布</a></button></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <hr>

                <!--类别菜单-->
                <div class="panel panel-success" >
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                分类管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <button class="btn btn-success">
                                        <a href="javascript:$('#centerLayout').load('${path}/category/category.jsp')">分类展示</a>
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <hr>

                <!--视频管理-->
                <div class="panel panel-warning">
                    <div class="panel-heading"  role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                视频管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><button class="btn btn-warning"><a href="javascript:$('#centerLayout').load('${path}/video/video.jsp')">视频展示</a></button></li><br>
                                <li><button class="btn btn-warning">视频搜索</button></li>
                            </ul>
                        </div>
                    </div>
                </div>


                <hr>

                <div class="panel panel-danger">
                    <div class="panel-heading" role="tab" id="headingFour">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                反馈管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><button class="btn btn-danger">反馈展示</button></li><br>
                            </ul>
                        </div>
                    </div>
                </div>

                <hr>

                <!--日志管理-->
                <div class="panel panel-primary" >
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                日志管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><button class="btn btn-primary">日志展示</button></li><br>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--巨幕开始-->
        <div class="col-sm-10">
            <div id="centerLayout">
                <div class="jumbotron" align="center">
                    <h2 align="center">欢迎来到应学视频App后台管理系统</h2>
                </div>

                <!--右边轮播图部分-->
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" align="center">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                        <li data-target="#myCarousel" data-slide-to="3"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner" align="center">
                        <div class="item active">
                            <img src="../bootstrap/img/pic4.jpg" alt="First slide"/>
                        </div>
                        <div class="item">
                            <img src="../bootstrap/img/pic2.jpg" alt="Second slide">
                        </div>
                        <div class="item">
                            <img src="../bootstrap/img/pic3.jpg" alt="Third slide">
                        </div>
                        <div class="item">
                            <img src="../bootstrap/img/pic1.jpg" alt="Fourth slide">
                        </div>

                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
                <br/>
            </div>
        </div>
    </div>
</div>

<!--页脚-->
<!--栅格系统-->
<div class="panel panel-footer" align="center">
    <div>@百知教育 chenyf@zparkhr.com</div>
</div>

</body>
</html>
