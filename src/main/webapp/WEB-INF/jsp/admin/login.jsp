<%--
  User: zly
  Date: 2017-2-27
  Time: 15:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>
<html class="no-js css-menubar" lang="en">
<head>
    <title>社科年鉴服务平台-后台登录</title>

    <!-- Stylesheets -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/css/bootstrap-extend.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/assets/css/site.min.css">
    <!-- Plugins -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/animsition/animsition.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/asscrollable/asScrollable.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/switchery/switchery.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/intro-js/introjs.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/slidepanel/slidePanel.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/jquery-mmenu/jquery-mmenu.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/flag-icon-css/flag-icon.css">
    <link rel="stylesheet" href="${ctx}/static/remark/assets/examples/css/pages/login-v3.css">
    <!-- Fonts -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/web-icons/web-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/brand-icons/brand-icons.min.css">
    <%--<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Roboto:300,400,500,300italic'>--%>
    <!--[if lt IE 9]>
    <script src="${ctx}/static/remark/global/vendor/html5shiv/html5shiv.min.js"></script>
    <![endif]-->
    <!--[if lt IE 10]>
    <script src="${ctx}/static/remark/global/vendor/media-match/media.match.min.js"></script>
    <script src="${ctx}/static/remark/global/vendor/respond/respond.min.js"></script>
    <![endif]-->
    <!-- Scripts -->
    <script src="${ctx}/static/remark/global/vendor/modernizr/modernizr.js"></script>
    <script src="${ctx}/static/remark/global/vendor/breakpoints/breakpoints.js"></script>
    <script>
        Breakpoints();
        var appPath = '${pageContext.request.contextPath}';
    </script>
    <style>
        .page-login-v3 form .form-material label {
            float: left;
        }
    </style>
</head>
<body class="page-login-v3 layout-full">
<div class="page animsition vertical-align text-center" data-animsition-in="fade-in"
     data-animsition-out="fade-out">
    <div class="page-content vertical-align-middle">
        <div class="panel">
            <div class="panel-body">
                <div class="brand">
                    <h2 class="brand-text font-size-18">社科年鉴服务平台</h2>
                </div>
                <form id="login">
                    <%--<input type="hidden" name="CSRFToken" value="${csrf}"/>--%>
                    <div class="form-group form-material floating">
                        <input id="userName" type="text" class="form-control username" name="username" required/>
                        <label class="floating-label" for="userName" style="color: #a3afb7">账号</label>
                        <span style="color: red;font-size: 12px;float: left" id="checkValue"></span>
                    </div>
                    <div class="form-group form-material floating">
                        <input id="password" type="password" class="form-control pwd" name="pwd" required/>
                        <label class="floating-label" for="password" style="color: #a3afb7">密码</label>
                    </div>
                    <button type="button" id="submit" class="btn btn-primary btn-block btn-lg margin-top-40">登录</button>
                </form>
            </div>
        </div>
        <footer class="page-copyright page-copyright-inverse">
            <p>WEBSITE BY TRS</p>
            <p>© 2017. All RIGHT RESERVED.</p>
        </footer>
    </div>
</div>

<!-- 通用js脚本 -->
<%@include file="include/scrpit.jsp"%>
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script src="${ctx}/static/remark/global/js/components/material.js"></script>
<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script src="${ctx}/static/admin/js/login.js"></script>

<script>
    (function (document, window, $) {
        'use strict';
        var Site = window.Site;
        $(document).ready(function () {
            Site.run();
        });
    })(document, window, jQuery);
</script>
</body>
</html>
