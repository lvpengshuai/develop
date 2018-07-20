<%--
  User: zly
  Date: 2017-3-2
  Time: 13:51
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>

    <title>本体资源库</title>
    <!-- Stylesheets -->
    <link rel="stylesheet" href="${ctx}/static/remark/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/css/bootstrap-extend.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/assets/css/site.min.css">
    <!-- Plugins -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/animsition/animsition.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/asscrollable/asScrollable.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/switchery/switchery.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/intro-js/introjs.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/slidepanel/slidePanel.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/jquery-mmenu/jquery-mmenu.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/flag-icon-css/flag-icon.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/waves/waves.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/bootstrap-sweetalert/sweet-alert.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/glyphicons/glyphicons.css">
    <!-- Fonts -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/weather-icons/weather-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/web-icons/web-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/open-iconic/open-iconic.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/brand-icons/brand-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/material-design/material-design.min.css">
    <!--[if lt IE 9]>
    <script src="${ctx}/static/remark/vendor/html5shiv/html5shiv.min.js"></script>
    <![endif]-->
    <!--[if lt IE 10]>
    <script src="${ctx}/static/remark/vendor/media-match/media.match.min.js"></script>
    <script src="${ctx}/static/remark/vendor/respond/respond.min.js"></script>
    <![endif]-->
    <!-- Scripts -->
    <script src="${ctx}/static/remark/global/vendor/modernizr/modernizr.min.js"></script>
    <script src="${ctx}/static/remark/global/vendor/breakpoints/breakpoints.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/vue/vue.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/vue/vue-resource.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/echarts3/echarts.min.js"></script>

    <script>
        Breakpoints();
        var appPath = '${ctx}';
    </script>
</head>
<body class="site-navbar-small dashboard site-menubar-unfold">
<!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->
<%-- 顶部右侧菜单导航开始 --%>
<%@include file="/WEB-INF/jsp/admin/include/nav.jsp" %>
<%-- 顶部右侧菜单导航结束 --%>

<div class="page animsition">
    <%@include file="/WEB-INF/jsp/admin/include/breadcrumb.jsp" %>

    <div class="page-content container-fluid padding-top-5">
        <div class="row" id="content">
                <div class="widget widget-shadow padding-bottom-5">
                    <div class="widget-content margin-10 row">
                        <div class="col-md-4 col-sm-4 col-xs-4 hidden-xs margin-10 padding-0 margin-left-0">
                            <button class="btn btn-success btn-icon" id="btn-import-ontology">
                                <i class="icon md-upload" aria-hidden="true"></i> 导入本体文件
                            </button>
                        </div>
                        <div class="col-md-4 col-sm-4 col-xs-12 pull-right margin-10 padding-0 margin-right-0">
                            <div class="input-group">
                                <input placeholder="请输入您要查找的知识点" v-model="word" v-on:keyup.enter="search" class="form-control" title="Search">
                                <span class="input-group-btn">
                                <button @click="search" class="btn btn-primary waves-effect waves-light" id="">
                                    <i class="icon md-search" aria-hidden="true"></i>
                                </button>
                            </span>
                            </div>
                        </div>
                    </div>
                    <div class="margin-20 widget-content row" style="margin-top:10px;">
                        <div class=" col-md-8">
                            <div id="main" style="min-height: 500px" class="kn-tupu-con"></div>
                        </div>
                        <div id="userControl" class="col-md-4">
                            <div>
                                <button type="button" class="btn btn-success" @click="selectedAll">全选</button>
                                <button type="button" class="btn btn-success" @click="saveSetting">应用</button>
                            </div>
                            <div class="margin-top-10">
                                <div class="checkbox-custom checkbox-success" style="float: left;width: 150px;" v-for="property in properties">
                                    <input type="checkbox" v-model="selected" :value="property" >
                                    <label>{{ property }}</label>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
        </div>

    </div>
    <!-- 主界面结束 -->
    <!-- 脚部开始 -->
    <%@include file="/WEB-INF/jsp/admin/include/footer.jsp" %>
    <%-- 脚部结束 --%>
    <!-- 通用模态框开始 -->
    <%@include file="/WEB-INF/jsp/admin/include/modal.jsp" %>
    <%-- 通用模态框结束 --%>

    <!-- 通用js脚本 -->
    <%@include file="../include/scrpit.jsp" %>
    <script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
    <script src="${ctx}/static/remark/global/js/components/material.js"></script>
    <script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
    <script>
        (function (document, window, $) {
            'use strict';
            var Site = window.Site;
            $(document).ready(function () {
                Site.run();
            });
        })(document, window, jQuery);
    </script>
    <!---知识图谱--->
    <script src="${ctx}/static/admin/js/ontology/atlas.js"></script>
    <!--本体上传-->
    <script src="${ctx}/static/admin/js/ontology/ontology.js"></script>
</body>
</html>