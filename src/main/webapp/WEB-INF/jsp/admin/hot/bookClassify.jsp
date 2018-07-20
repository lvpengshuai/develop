<%--
  User: lcy
  Date: 2017-3-2
  Time: 13:51
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>

    <title>书籍类别管理</title>
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

        .table {
            width: 70%;
        }

        .table td {
            border-bottom: 1px solid #e0e0e0;
        }

        .up, .down, .top {
            border: solid 0px;
            border-radius: 5px;
            padding: 4px 12px;
        }

        .table a {
            text-decoration: none;
            width: 100px;
            background-color: #4caf50;
            color: #fff;
            font-family: inherit;
            font-weight: 500;
        }

        .sbm {
            padding: 1px 25px;
            font-size: 25px;
            border: solid 0px;
            border-radius: 5px;
            background-color: #3f51b5;
            color: #fff;
            margin-bottom: 22px;
            margin-left: 15%;
        }
    </style>
</head>
<body class="site-navbar-small dashboard site-menubar-unfold">
<!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->
<%-- 顶部右侧菜单导航开始 --%>
<%@include file="/WEB-INF/jsp/admin/include/nav.jsp" %>
<%-- 顶部右侧菜单导航结束 --%>
<!-- 主界面开始 -->
<div class="page animsition">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:void(0)">首页</a></li>
            <li class="active">书籍分类管理</li>
        </ol>
    </div>
    <div class="page-content container-fluid padding-top-5">
        <div class="row" data-plugin="matchHeight" data-by-row="true">
            <div class="col-xlg-12 col-md-12">
                <div class="widget widget-shadow padding-bottom-5" style="padding: 45px;">
                    <form action="${ctx}/admin/hot/bookClassify" method="post">
                        <table class="table">
                            <c:forEach items="${list}" var="list">
                                <tr>
                                    <td>
                                        <img src="${list.image}" style="height: 20px;">&nbsp;&nbsp;&nbsp;<b>·</b>
                                        <input name="title" style="border:none;width: 260px;" readonly="true" value="${list.name}">
                                        <input name="bookabbreviate" type="hidden" value="${list.bookabbreviate}">
                                    </td>
                                    <td align="right">
                                        <a href="#" class="up">上移<span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span></a>
                                        <a href="#" class="down">下移<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></a>
                                        <a href="#" class="top">置顶<span class="glyphicon glyphicon-open" aria-hidden="true"></span></a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <input type="hidden" name="isSubmit" value="submit">
                        <input type="button" value="取消" class="sbm" onclick="cancle()">
                        <input type="submit" value="确定" class="sbm">
                    </form>
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
<!-- Core  -->
<!-- 通用js脚本 -->
<%@include file="../include/scrpit.jsp" %>

<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script src="${ctx}/static/remark/global/js/components/material.js"></script>
<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>

<script>
    (function (document, window, $) {
        'use strict';
        var Site = window.Site;
        $(document).ready(function () {
            Site.run();
        });
    })(document, window, jQuery);
    $("#btn-add").click(function () {
        window.location.href = appPath + '/admin/hot/word/add';
    })
    $("#remove").click(function () {
        window.location.href = appPath + '/admin/hot/word/remove/${hotWords[0].id}';
    })
    $("#btn-un-publish").click(function () {
        window.location.href = appPath + '/admin/hot/word/publish/${hotWords[0].id}/${hotWords[0].status}';
    })


    $(function () {
        //上移
        var $up = $(".up")
        $up.click(function () {
            var $tr = $(this).parents("tr");
            if ($tr.index() != 0) {
                $tr.fadeOut("slow").fadeIn("slow");
                $tr.prev().before($tr);
                return false;
            }
        });
        //下移
        var $down = $(".down");
        var len = $down.length;
        $down.click(function () {
            var $tr = $(this).parents("tr");
            if ($tr.index() != len - 1) {
                $tr.fadeOut().fadeIn();
                $tr.next().after($tr);
                return false;
            }
        });
        //置顶
        var $top = $(".top");
        $top.click(function () {
            var $tr = $(this).parents("tr");
            $(".table").prepend($tr);
            var $toptr = $(".table").find("tr:first");
            $toptr.fadeOut("slow").fadeIn("slow");
            $("html, body").animate({
                scrollTop:  "110px"
            }, {
                duration: 500,
                easing: "swing"
            });
            return false;
        });
    });
    function cancle() {
        location.reload();
    }
</script>
</body>
</html>