<%--
  Created by IntelliJ IDEA.
  User: zly
  Date: 2017-3-9
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>

    <title>${title}</title>
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
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/bootstrap-datepicker/bootstrap-datepicker.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/jt-timepicker/jquery-timepicker.min.css">
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
        .form-group{
            margin-bottom: 0px;
        }
        .bootstrap-select {
            width: 100% !important;
        }
        .datepair-wrap {
            position: relative;
            overflow: hidden;
        }
        .input-daterange-wrap {
            float: left;
        }
        .input-daterange-to {
            float: left;
            width: 40px;
            height: 40px;
            line-height: 40px;
            text-align: center;
        }
        @media (max-width: 1360px) {
            .input-daterange-wrap,
            .input-daterange-to {
                display: block;
                float: none;
            }
        }

    </style>
</head>
<body class="site-navbar-small dashboard site-menubar-unfold">
<!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<%-- 顶部右侧菜单导航开始 --%>
<%@include file="/WEB-INF/jsp/admin/include/nav.jsp"%>
<%-- 顶部右侧菜单导航结束 --%>
<!-- 主界面开始 -->
<div class="page animsition">
    <%@include file="/WEB-INF/jsp/admin/include/breadcrumb.jsp"%>

    <input type="hidden" id="targetType" value="${targetType}">
    <input type="hidden" id="operType" value="${operType}">
    <div class="page-content container-fluid padding-top-5">
        <div class="row" data-plugin="matchHeight" data-by-row="true">
            <div class="col-xlg-12 col-md-12">
                <div class="widget widget-shadow padding-bottom-5">
                    <div class="widget-content margin-10">
                        <%-- 表格功能按钮开始 --%>
                        <div class="form-group" id="log_table_toolbars" role="group">
                            <button id="remove" type="button" class="btn btn-danger btn-icon" disabled>
                                <i class="icon md-delete" aria-hidden="true"></i> 删除
                            </button>
                            <%--对象类型下拉开始--%>
                            <span>
                                <span class="btn-group dropdown">
                                    <button class="btn btn-default  btn-outline dropdown-toggle" type="button"
                                            data-toggle="dropdown">
                                        <span id="target">全部</span>
                                        <span class="caret"/>
                                    </button>
                                    <ul class="dropdown-menu" id="t" role="menu">
                                        <li>
                                            <a href="javascript:void(0)" value="">全部</a>
                                        </li>
                                    </ul>
                                </span>
                            </span>
                            <%--对象类型下拉结束--%>
                            <%--操作类型下拉开始--%>
                            <span>
                                <span class="btn-group dropdown">
                                    <button class="btn btn-default  btn-outline dropdown-toggle" type="button"
                                            data-toggle="dropdown">
                                        <span id="oper">全部</span>
                                        <span class="caret"/>
                                    </button>
                                    <ul class="dropdown-menu" id="o" role="menu">
                                        <li>
                                            <a href="javascript:void(0)">全部</a>
                                        </li>
                                    </ul>
                                </span>
                            </span>
                            <%--操作类型下拉结束--%>
                            <%--<span style="clear: both">--%>
                            <%--<div class="input-daterange" id="datep" style="width:70%">--%>
                                <%--<div class="input-group">--%>
                                          <%--<span class="input-group-addon">--%>
                                            <%--<i class="icon md-calendar" aria-hidden="true"></i>--%>
                                          <%--</span>--%>
                                    <%--<input type="text" class="form-control" name="start" id="start" value="${start}"--%>
                                           <%--readonly="readonly" style="background-color: white"/>--%>
                                <%--</div>--%>
                                <%--<div class="input-group">--%>
                                    <%--<span class="input-group-addon">to</span>--%>
                                    <%--<input type="text" class="form-control" name="end" id="end" value="${ends}"--%>
                                           <%--readonly="readonly" style="background-color: white"/>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--</span>--%>

                            <div class="sousuo clearfix">
                                <div class="select-result clearfix">
                                    <ul>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <%-- 表格功能按钮结束 --%>
                        <table id="log_table"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 主界面结束 -->
<!-- 脚部开始 -->
<%@include file="/WEB-INF/jsp/admin/include/footer.jsp"%>
<%-- 脚部结束 --%>
<!-- 通用模态框开始 -->
<%@include file="/WEB-INF/jsp/admin/include/modal.jsp"%>
<%-- 通用模态框结束 --%>
<!-- 通用js脚本 -->
<%@include file="../include/scrpit.jsp"%>
<%--<!-- Plugins -->--%>
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/jt-timepicker/jquery.timepicker.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/datepair-js/datepair.min.js"></script>
<!-- Scripts -->
<script src="${ctx}/static/remark/global/js/components/bootstrap-datepicker.min.js"></script>
<script src="${ctx}/static/remark/global/js/components/jt-timepicker.min.js"></script>
<script src="${ctx}/static/remark/global/js/components/datepair-js.min.js"></script>
<script src="${ctx}/static/admin/js/log/log.js"></script>

<script>
    (function (document, window, $) {
        'use strict';
        var Site = window.Site;
        $(document).ready(function () {
            Site.run();
        });
    })(document, window, jQuery);

    $.fn.datepicker.dates['zh-CN'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
        daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
        daysMin: ["日", "一", "二", "三", "四", "五", "六"],
        months:  ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        today: "今天",
        clear: "清除",
        format: "yyyy-mm-dd",
        titleFormat: "yyyy MM", /* Leverages same syntax as 'format' */
        weekStart: 1
    };

    $('#datep').datepicker({
        language: 'zh-CN',
        autoclose: 1,
    });
    $('#datep').datepicker().on('changeDate',function () {
        $('#log_table').bootstrapTable("refresh");
    });
</script>
</body>
</html>