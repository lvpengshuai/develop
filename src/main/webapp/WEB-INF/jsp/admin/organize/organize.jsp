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

    <title>机构用户</title>
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

        var adminList = [];
        <c:forEach var="a" items="${organizeAdmins}">
        adminList.push({'id':'${a.id}','username':'${a.username}','realname':'${a.realname}'});
        </c:forEach>
    </script>
    <style>
        .form-group{
            margin-bottom: 0px;
        }
        .height-div{
            height:45px;
            margin:0 auto;
        }
         .form-group{
             margin-bottom: 0px;
         }
        .height-div{
            height:36px;
        }
        #name,#address,#telephone,#expiration,#maxOnline,#file_input,#file_input_line,#fileupload{
            width: 50%;
            float: left;
        }
        .ip{
            width: 15%;
            float: left;
        }
        #member{
            width: 50%;
        }
        #AddMoreFileBox{
            text-decoration: none;
            line-height:36px;
            margin-left: 10px;
            color: #4caf50
        }
        .pagination-detail{
            display: none;
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
    <input type="hidden" value="${online}" id="online">
    <input type="hidden" id="state" value="${state}">
    <div class="page-content container-fluid padding-top-5">
        <div class="row" data-plugin="matchHeight" data-by-row="true">
            <div class="col-xlg-12 col-md-12">
                <div class="widget widget-shadow padding-bottom-5">
                    <div class="widget-content margin-10">
                        <%-- 表格功能按钮开始 --%>
                        <div class="form-group" id="organize_table_toolbars" role="group">
                            <button type="button" class="btn btn-success btn-icon" id="btn-add">
                                <i class="icon md-plus" aria-hidden="true"></i> 添加
                            </button>
                            <button type="button" class="btn btn-success btn-icon" id="btn_enable_organize" disabled>
                                <i class="icon fa-circle-o" aria-hidden="true"></i> 启用
                            </button>
                            <button type="button" class="btn btn-success btn-icon" id="btn_disable_organize" disabled>
                                <i class="icon fa-ban" aria-hidden="true"></i> 禁用
                            </button>
                            <%--<c:if test="${online == OFF}">--%>
                            <button id="remove" type="button" class="btn btn-danger btn-icon" disabled>
                                <i class="icon md-delete" aria-hidden="true"></i> 删除
                            </button>
                            <%--</c:if>--%>
                            <span class="page-list2">
                                <span class="btn-group dropdown">
                                    <button class="btn btn-default  btn-outline dropdown-toggle" type="button"
                                            data-toggle="dropdown">
                                        <span class="page-size2" name="pageSize" id="pageSize">全部</span>
                                        <span class="caret"/>
                                    </button>
                                    <ul class="dropdown-menu menu" role="menu">
                                        <li class="active">
                                            <a href="javascript:void(0)" value="">全部</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0)" value="0">启用</a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0)" value="1">禁用</a>
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
                        <table id="organize_table"></table>
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
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script src="${ctx}/static/remark/global/js/components/material.js"></script>
<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script src="${ctx}/static/admin/js/organize/organize.js"></script>
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