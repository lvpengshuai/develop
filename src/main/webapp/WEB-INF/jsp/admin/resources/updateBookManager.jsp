<%--
  Created by IntelliJ IDEA.
  User: LiangYong
  Date: 2017/11/22
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>

    <title>修改资料</title>
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
            height: 36px;
        }

        #username, #pwd, #qpassword, #realname, #email, #telephone, #address {
            width: 50%;
            float: left;
        }
    </style>
</head>
<body class="site-navbar-small dashboard">
<!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->
<%-- 顶部右侧菜单导航开始 --%>
<%@include file="/WEB-INF/jsp/admin/include/nav.jsp" %>
<%-- 顶部右侧菜单导航结束 --%>
<!-- 主界面开始 -->
<div class="page animsition">
    <%@include file="/WEB-INF/jsp/admin/include/breadcrumb.jsp" %>
    <div class="page-content container-fluid">
        <div class="row" data-plugin="matchHeight" data-by-row="true">
            <div class="col-xlg-12 col-md-12">
                <div class="widget widget-shadow">
                    <div class="widget-content margin-10">
                        <div class="panel-body" style="width: 50%;">
                            <form action="${ctx}/admin/updateBookManager" method="post">
                                <input type="hidden" value=" ${book.id}" name="id">
                                <div class="form-group">
                                    <label>标题</label>
                                    <input type="text" class="form-control" placeholder="标题" value=" ${book.title}" id="title_cn" name="title">
                                </div>
                                <div class="form-group">
                                    <label>标题(英)</label>
                                    <input type="text" class="form-control" placeholder="标题(英)" value="${book.title_en}" id="title_en" name="title_en">
                                </div>
                                <div class="form-group">
                                    <label>简介</label>
                                    <textarea class="form-control" rows="7" placeholder="简介" name="abs" id="abs">${book.abs}</textarea>
                                </div>
                                <div class="form-group">
                                    <label>出版时间</label>
                                    <input type="text" class="form-control" placeholder="出版时间" value="${book.pubdate}" name="pubdate">
                                </div>
                                <div class="form-group">
                                    <label>编著单位</label>
                                    <input type="text" class="form-control" placeholder="编著单位" value="${book.author}" name="author">
                                </div>
                                <div class="form-group">
                                    <label>出版单位</label>
                                    <input type="text" class="form-control" placeholder="出版单位" value="${book.publisherName}" name="publisherName">
                                </div>
                                <div class="form-group">
                                    <label>ISBN</label>
                                    <input type="text" class="form-control" placeholder="ISBN" value="${book.isbn}" name="isbn">
                                </div>
                                <div class="form-group">
                                    <label>字数</label>
                                    <input type="text" class="form-control" placeholder="字数" value="${book.charCount}" name="charCount">
                                </div>
                                <br>
                                <button type="submit" class="btn btn-primary" id="submit">提交</button>
                                <a href="${href}" class="btn btn-warning">返回</a>
                            </form>
                        </div>
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
            <div class="modal-body" id="modal-body">

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

<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script src="${ctx}/static/admin/js/user/editUser.js"></script>

<script>
    (function (document, window, $) {
        'use strict';
        var Site = window.Site;
        $(document).ready(function () {
            Site.run();
        });
    })(document, window, jQuery);
    $(function () {
        $("#submit").click(function () {
            var title_cn = $("#title_cn").val();
            var title_en = $("#title_en").val();
            var abs = $("#abs").val();
            if (title_cn == null || title_cn == "") {
                $("#modal-body").text("标题不能为空！！！");
                $(".bs-example-modal-sm").modal('show');
                return false;
            } else if (title_en == null || title_en == "") {
                $("#modal-body").text("标题（英）不能为空！！！");
                $(".bs-example-modal-sm").modal('show');
                return false;
            } else if (abs == null || abs == "") {
                $("#modal-body").text("简介不能为空！！！");
                $(".bs-example-modal-sm").modal('show');
                return false;
            }else {
                return true;
            }
        })
    })
</script>
</body>
</html>
