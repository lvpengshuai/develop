<%--
  User: 李春雨
  Date: 2017/3/27
  Time: 18:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>

    <title>修改期刊</title>
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
    <link rel="stylesheet" href="${ctx}/static/admin/js/datetime/bootstrap-datetimepicker.min.css">
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
    z
</head>
<body class="site-navbar-small dashboard">
<!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<%-- 顶部右侧菜单导航开始 --%>
<%@include file="/WEB-INF/jsp/admin/include/nav.jsp"%>
<%-- 顶部右侧菜单导航结束 --%>
<!-- 主界面开始 -->
<div class="page animsition">
    <%@include file="/WEB-INF/jsp/admin/include/breadcrumb.jsp"%>
    <div class="page-content container-fluid">
        <div class="row" data-plugin="matchHeight" data-by-row="true">
            <div class="col-xlg-12 col-md-12">
                <div class="widget widget-shadow">
                    <div class="widget-content margin-10">
                        <div class="panel-body">
                           <%-- <div id="cover">
                                <img class="img-thumbnail" src="${ctx}/static/client/images/pic1.jpg" >
                            </div>--%>
                            <form id="editJournal" method="post">
                                <input type="hidden" name="id" value="${periodical.id}">
                                <div class="form-group height-div">
                                    <label>期刊名称</label>
                                    <div>
                                        <input type="text" class="form-control bookName" placeholder=""
                                               id="bookName" name="chName" value="${periodical.chName}" style="width: 65%"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>作者</label>
                                    <div>
                                        <input type="text" class="form-control bookAuthor" placeholder="" id="bookAuthor"
                                               name="lianXiZuoZhe" value="${periodical.lianXiZuoZhe}" style="width: 65%"/>
                                    </div>
                                </div>
                                <br>
                               <%-- <div class="form-group height-div">
                                    <label>中图分类号</label>
                                    <div>
                                        <input type="text" class="form-control clc" id="clc"
                                               name="clc" placeholder=""/>
                                    </div>
                                </div>--%>

                                <br>
                                <div class="form-group height-div">
                                    <label>发布日期</label>
                                    <div>
                                        <input type="text" class="form-control input-sm pubDate" placeholder=""
                                               id="pubDate" name="date" value="<fmt:formatDate value='${periodical.pubDate}' pattern='yyyy-MM-dd'/>" style="width: 7%;font-size: 16px;text-align: center" readonly/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>doi</label>
                                    <div>
                                        <input type="text" class="form-control isbnFull" placeholder="" id="isbnFull"
                                               name="doi" value="${periodical.doi}" style="width: 65%"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>关键字</label>
                                    <div>
                                        <input type="text" class="form-control keyword" placeholder="" id="keyword"
                                               name="chKeyword" value="${periodical.chKeyword}" style="width: 65%"/>
                                    </div>
                                </div>
                                <br>

                                <div class="form-group">
                                    <label>内容简介</label>
                                    <textarea class="form-control textarea summary" id="summary"
                                              name="chAbstract" data-toggle="modal" placeholder="" style="width: 65%;height: 125px"
                                    >${periodical.chAbstract}</textarea>
                                </div>
                                <br>
                                <br>
                                <div class="row" style="margin-bottom: 20px;margin-top: 20px;">
                                    <div class="col-md-1 col-sm-2">
                                        <button class="btn btn-default" type="button" onclick="window.history.go(-1)">
                                            取消
                                        </button>
                                    </div>
                                    <div class="col-md-1 col-sm-2">
                                        <button class="btn btn-default" type="reset">重置</button>
                                    </div>
                                    <div class="col-md-8 col-sm-6">
                                        <div class="pull-right">
                                            <button class="btn btn-success" id="save" type="button" onclick="">保存
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-md-2 col-sm-3">
                                        <div class="pull-right">
                                            <button class="btn btn-success" id="savepub" type="button" onclick="">保存并发布
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
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

<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script src="${ctx}/static/admin/js/datetime/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}/static/admin/js/datetime/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}/static/admin/js/periodical/editperiodical.js"></script>

<script>
    (function (document, window, $) {
        'use strict';
        var Site = window.Site;
        $(document).ready(function () {
            Site.run();
        });
    })(document, window, jQuery);
    $("#pubDate").datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        language: 'zh-CN',
        todayHighlight: 1,
        startView: 2,
        forceParse: 1,
        minView: "month",
    });

</script>
</body>
</html>