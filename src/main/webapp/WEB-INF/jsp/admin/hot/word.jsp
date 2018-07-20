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

    <title>热词管理</title>
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
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:void(0)">首页</a></li>
            <li class="active">热词管理</li>
        </ol>
    </div>
    <div class="page-content container-fluid padding-top-5">
        <div class="row" data-plugin="matchHeight" data-by-row="true">
            <div class="col-xlg-12 col-md-12">
                <div class="widget widget-shadow padding-bottom-5">
                    <div class="widget-content margin-10">


                        <div class="bootstrap-table">
                            <div class="fixed-table-toolbar">
                                <div class="bs-bars pull-left">
                                    <div class="form-group" id="role_table_toolbars" role="group">
                                        <c:if test="${hotWords.size()==1}">
                                            <button type="button" class="btn btn-success btn-icon waves-effect waves-light" id="btn-add" disabled="">
                                                <i class="icon md-plus" aria-hidden="true"></i> 添加
                                            </button>
                                        </c:if>
                                        <c:if test="${hotWords.size()<1}">
                                            <button type="button" class="btn btn-success btn-icon waves-effect waves-light" id="btn-add">
                                                <i class="icon md-plus" aria-hidden="true"></i> 添加
                                            </button>
                                        </c:if>
                                        <c:if test="${hotWords[0].status==1}">
                                            <button type="button" class="btn btn-success btn-icon waves-effect waves-light" id="btn-un-publish" >
                                                <i class="icon fa-ban" aria-hidden="true"></i> 禁用
                                            </button>
                                        </c:if>
                                        <c:if test="${hotWords[0].status==0}">
                                            <button type="button" class="btn btn-success btn-icon waves-effect waves-light" id="btn-un-publish" >
                                                <i class="icon fa-ban" aria-hidden="true"></i> 启用
                                            </button>
                                        </c:if>
                                        <c:if test="${hotWords.size()==1}">
                                            <button id="remove" type="button" class="btn btn-danger btn-icon waves-effect waves-light">
                                                <i class="icon md-delete" aria-hidden="true"></i> 删除
                                            </button>
                                        </c:if>
                                        <c:if test="${hotWords.size()<1}">
                                            <button id="remove" type="button" class="btn btn-danger btn-icon waves-effect waves-light" disabled="">
                                                <i class="icon md-delete" aria-hidden="true"></i> 删除
                                            </button>
                                        </c:if>
                                        <div class="sousuo clearfix">
                                            <div class="select-result clearfix">
                                                <ul>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="columns columns-right btn-group pull-right">
                                    <button class="btn btn-default waves-effect waves-light" type="button" name="refresh" aria-label="refresh" title="刷新" onclick="location.reload()"><i class="glyphicon md-refresh"></i></button>
                                </div>
                            </div>
                            <div class="fixed-table-container" style="padding-bottom: 0px;">
                                <div class="fixed-table-header" style="display: none;">
                                    <table></table>
                                </div>
                                <div class="fixed-table-body">
                                    <div class="fixed-table-loading" style="top: 41px; display: none;">正在努力地加载数据中，请稍候……</div>
                                    <table id="role_table" class="table table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th class="hidden" style="" data-field="id">
                                                <div class="th-inner "></div>
                                                <div class="fht-cell"></div>
                                            </th>
                                            <th style="" data-field="word">
                                                <div class="th-inner ">热搜词</div>
                                                <div class="fht-cell"></div>
                                            </th>
                                            <th style="" data-field="gmtCreate">
                                                <div class="th-inner sortable both">创建时间</div>
                                                <div class="fht-cell"></div>
                                            </th>
                                            <th style="" data-field="gmtModified">
                                                <div class="th-inner sortable both">修改时间</div>
                                                <div class="fht-cell"></div>
                                            </th>
                                            <th style="" data-field="status">
                                                <div class="th-inner ">状态</div>
                                                <div class="fht-cell"></div>
                                            </th>
                                            <th style="" data-field="6">
                                                <div class="th-inner ">操作</div>
                                                <div class="fht-cell"></div>
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${hotWords}" var="hotWord">
                                            <tr>
                                                <td>${hotWord.word}</td>
                                                <td>${hotWord.gmt_create}</td>
                                                <td>${hotWord.gmt_modified}</td>
                                                <td>
                                                    <c:if test="${hotWord.status==1}">
                                                        <span class="label label-success">启用</span>
                                                    </c:if>
                                                    <c:if test="${hotWord.status==0}">
                                                        <span class="label label-warning">禁用</span>
                                                    </c:if>
                                                </td>
                                                <td><a class="edit" href="${ctx}/admin/hot/word/update" style="color: #757575;" title="修改"><span class="glyphicon glyphicon-edit"></span></a></td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${hotWords.size()==0}">
                                            <tr class="no-records-found"><td colspan="5">没有找到匹配的记录</td></tr>
                                        </c:if>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="fixed-table-footer" style="display: none;">
                                    <table>
                                        <tbody>
                                        <tr></tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="fixed-table-pagination" style="display: none;">
                                    <div class="pull-left pagination-detail"><span class="pagination-info">显示第 1 到第 0 条记录，总共 0 条记录</span><span class="page-list" style="display: none;">每页显示 <span
                                            class="btn-group dropup"><button type="button" class="btn btn-default dropdown-toggle waves-effect waves-light" data-toggle="dropdown"><span
                                            class="page-size">10</span> <span class="caret"></span></button><ul class="dropdown-menu" role="menu"><li role="menuitem" class="active"><a href="#">10</a></li></ul></span> 条记录</span>
                                    </div>
                                    <div class="pull-right pagination" style="display: none;">
                                        <ul class="pagination">
                                            <li class="page-pre"><a href="#">‹</a></li>
                                            <li class="page-next"><a href="#">›</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
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
    })(document, window, jQuery);remove
    $("#btn-add").click(function () {
        window.location.href = appPath + '/admin/hot/word/add';
    })
    $("#remove").click(function () {
        window.location.href = appPath + '/admin/hot/word/remove/${hotWords[0].id}';
    })
    $("#btn-un-publish").click(function () {
        window.location.href = appPath + '/admin/hot/word/publish/${hotWords[0].id}/${hotWords[0].status}';
    })
</script>
</body>
</html>