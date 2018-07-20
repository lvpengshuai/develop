<%--
  Created by IntelliJ IDEA.
  User: zly
  Date: 2017-3-9
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>

    <title>修改电子书</title>
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
        .form-group{
            margin-bottom: 0px;
        }
        .height-div{
            height:auto;
        }
        .textarea{
            min-height: 120px;
            resize: none;
        }
        #cover{
            float: right;
            width:160px;
            height:220px;
        }
        #editBook{
            width: 70%;
            float: left;
        }
    </style>
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
                            <div id="cover">
                                <img class="img-thumbnail" src="${ctx}/static/client/images/pic1.jpg" >
                            </div>
                            <form id="editBook" method="post">
                                <input type="hidden" name="id" value="${book.id}">
                                <div class="form-group height-div">
                                    <label>电子书名称</label>
                                    <div>
                                        <input type="text" class="form-control bookName" placeholder=""
                                               id="bookName" name="bookName" value="${book.bookName}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>作者</label>
                                    <div>
                                        <input type="text" class="form-control bookAuthor" placeholder="" id="bookAuthor"
                                               name="bookAuthor" value="${book.bookAuthor}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>中图分类号</label>
                                    <div>
                                        <input type="text" class="form-control clc" id="clc"
                                               name="clc" placeholder="" value="${book.clc}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>所属丛书</label>
                                    <div>
                                        <input type="text" class="form-control booksName" placeholder=""
                                               id="booksName" name="booksName" value="${book.booksName}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>丛书作者</label>
                                    <div>
                                        <input type="text" class="form-control booksAuthor" placeholder="" id="booksAuthor"
                                               name="booksAuthor" value="${book.booksAuthor}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>出版日期</label>
                                    <div>
                                        <input type="text" class="form-control pubDate" placeholder=""
                                               id="pubDate" name="pubDate" value="${book.pubDate}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>ISBN</label>
                                    <div>
                                        <input type="text" class="form-control isbnFull" placeholder="" id="isbnFull"
                                               name="isbnFull" value="${book.isbnFull}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>图书在版编目</label>
                                    <div>
                                        <input type="text" class="form-control cipTheme" placeholder="" id="cipTheme"
                                               name="cipTheme" value="${book.cipTheme}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>图书在版编目编号</label>
                                    <div>
                                        <input type="text" class="form-control cipNum" placeholder="" id="cipNum"
                                               name="cipNum" value="${book.cipNum}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>出版社</label>
                                    <div>
                                        <input type="text" class="form-control publisher" placeholder="" id="publisher"
                                               name="publisher" value="${book.publisher}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>定价</label>
                                    <div>
                                        <input type="text" class="form-control price" placeholder="" id="price"
                                               name="price" value="${book.price}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>关键字</label>
                                    <div>
                                        <input type="text" class="form-control keyword" placeholder="" id="keyword"
                                               name="keyword" value="${book.keyword}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>适读人群</label>
                                    <div>
                                        <input type="text" class="form-control reader" placeholder="" id="reader"
                                               name="reader" value="${book.reader}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group height-div">
                                    <label>图书编号</label>
                                    <div>
                                        <input type="text" class="form-control bookCode" placeholder="" id="bookCode"
                                               name="bookCode" value="${book.bookCode}"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <label>内容简介</label>
                                    <textarea class="form-control textarea introduction" id="introduction"
                                              name="introduction" data-toggle="modal" placeholder=""
                                              >${book.introduction}</textarea>
                                </div>
                                <br>
                                <div class="form-group">
                                    <label>前言</label>
                                    <textarea class="form-control textarea summary" id="summary"
                                              name="summary" data-toggle="modal" placeholder=""
                                              >${book.summary}</textarea>
                                </div>
                                <br>
                                <div class="form-group">
                                    <label>目录</label>
                                    <textarea class="form-control textarea catalog" id="catalog"
                                              name="catalog" data-toggle="modal" placeholder=""
                                              >${book.catalog}</textarea>
                                </div>
                                <br>
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
<script src="${ctx}/static/admin/js/book/editbook.js"></script>

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