<%--
  Created by IntelliJ IDEA.
  User: LiangYong
  Date: 2017/11/21
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>

    <title>后台用户</title>
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
    <script>
        Breakpoints();
        var appPath = '${ctx}';
    </script>
    <style>
        .form-group {
            margin-bottom: 0px;
        }

        .height-div {
            height: 45px;
            margin: 0 auto;
        }

        /*#oldpassword,#newpassword,#qnewpassword{*/
        /*width: 90%;*/
        /*float: left;*/
        /*}*/
    </style>
</head>
<body class="site-navbar-small dashboard site-menubar-unfold">
<!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<%-- 顶部右侧菜单导航开始 --%>
<%@include file="/WEB-INF/jsp/admin/include/nav.jsp" %>
<%-- 顶部右侧菜单导航结束 --%>
<!-- 主界面开始 -->
<div class="page animsition">
    <%@include file="/WEB-INF/jsp/admin/include/breadcrumb.jsp" %>

    <input type="hidden" id="state" value="${state}">
    <div class="page-content container-fluid padding-top-5">
        <div class="row" data-plugin="matchHeight" data-by-row="true">
            <div class="col-xlg-12 col-md-12">
                <div class="widget widget-shadow padding-bottom-5">
                    <div class="widget-content margin-10">
                        <%-- 表格功能按钮开始 --%>
                        <div class="form-group" id="user_table_toolbars" role="group">
                            <%--<button type="button" class="btn btn-success btn-icon" id="btn-add">--%>
                            <%--<i class="icon md-plus" aria-hidden="true"></i> 添加--%>
                            <%--</button>--%>
                            <%--<button type="button" class="btn btn-success btn-icon" id="btn_enable_user" disabled>--%>
                            <%--<i class="icon fa-circle-o" aria-hidden="true"></i> 启用--%>
                            <%--</button>--%>
                            <%--<button type="button" class="btn btn-success btn-icon" id="btn_disable_user" disabled>--%>
                            <%--<i class="icon fa-ban" aria-hidden="true"></i> 禁用--%>
                            <%--</button>--%>
                            <button id="remove" type="button" class="btn btn-danger btn-icon" disabled>
                                <i class="icon md-delete" aria-hidden="true"></i> 删除
                            </button>
                            <span class="page-list2">
                            <span class="btn-group dropdown">
                            <button class="btn btn-default  btn-outline dropdown-toggle" type="button"
                                    data-toggle="dropdown">
                            <span class="page-size2" name="pageSize" id="pageSize">立项课题</span>
                            <span class="caret"/>
                            </button>
                            <ul class="dropdown-menu" id="userstate" role="menu">
                            <li class="active">
                            <a href="javascript:void(0)" value="">立项课题</a>
                            </li>
                            <li>
                            <a href="javascript:void(0)" value="0">结项课题</a>
                            </li>
                            </ul>
                            </span>
                            </span>
                            <div class="sousuo clearfix">
                                <div class="select-result clearfix">
                                    <ul>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <%-- 表格功能按钮结束 --%>
                        <table id="user_table"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="mySmallModalLabel">提示信息</h4>
            </div>
            <div class="modal-body">
                ${manager}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script src="${ctx}/static/admin/js/resource/topicEntry.js"></script>

<script src="${ctx}/static/admin/js/resource/publicSearch.js"></script>
<script>
    (function (document, window, $) {
        'use strict';
        var Site = window.Site;
        $(document).ready(function () {
            Site.run();
        });
    })(document, window, jQuery);
    $(function () {
        if ("${model}" != "") {
            $(".bs-example-modal-sm").modal('show');
            setTimeout(ssss, 3000);
        }

        function ssss() {
            $(".bs-example-modal-sm").modal('hide')
        }
    })
</script>
</body>
</html>
