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

    <title>数据录入</title>
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
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
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
        .ne-but{
            margin-bottom: 0px;
            margin-top: 10px;
            float: right;
            margin-right: 100px;
        }
        .check-area{
            margin-bottom: 0px;
            margin-top: 10px;
            float: right;
            margin-right: 600px;
            width: 300px;
            height: 400px;
            overflow:auto;
        }
        .jiexi-area{
            margin-bottom: 0px;
            margin-top: 330px;
            float: left;
            width: 400px;
            height: 200px;
            overflow:auto;
        }
        .download-area{
            margin-bottom: 0px;
            margin-top: 553px;
            float: left;
            width: 400px;
            height: 200px;
            position: absolute;
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
        .fixed-table-container{
            height: 250px;
        }
    </style>
</head>
<body class="site-navbar-small dashboard" >
<!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<%-- 顶部右侧菜单导航开始 --%>
<%@include file="/WEB-INF/jsp/admin/include/nav.jsp"%>
<%-- 顶部右侧菜单导航结束 --%>
<!-- 主界面开始 -->
<div class="page animsition">
    <%@include file="/WEB-INF/jsp/admin/include/breadcrumb.jsp"%>
    <%--<div class="page-content container-fluid">--%>
        <%--<div class="row" data-plugin="matchHeight" data-by-row="true">--%>
            <%--<div class="col-xlg-12 col-md-12">--%>
                <%--<div class="widget widget-shadow">--%>
                    <%--<div class="widget-content margin-10">--%>
                        <%--<div class="panel-body">--%>
                            <%--<form enctype="multipart/form-data" action="${ctx}/admin/uploadResourceFile" method="post">--%>
                                <%--<label for="input-43">上传epub文件</label>--%>
                                <%--<div class="file-loading">--%>
                                    <%--<input id="input-43" name="multipartFile" type="file" multiple>--%>
                                <%--</div>--%>
                            <%--</form>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="page-content container-fluid">
        <div class="row" data-plugin="matchHeight" data-by-row="true">
            <div class="col-xlg-12 col-md-12">
                <div class="widget widget-shadow">
                    <div class="widget-content margin-10">
                        <div class="panel-body">

                            <form enctype="multipart/form-data" action="${ctx}/admin/uploadXMLFile" method="post">
                                <label for="input-44">上传zip资源文件</label>
                                <div class="file-loading">
                                    <input id="input-44" name="multipartFile" type="file" multiple>
                                </div>
                            </form>
                        </div>
                        <div class="ne-but" >
                            <!--   disabled  控制按钮是否可选 -->
                            <button type="button" class="btn btn-success btn-icon" id="btn-check">
                                检查
                            </button>
                            <button type="button" class="btn btn-success btn-icon" id="btn-resolve" >
                                解析
                            </button>
                            <button type="button" class="btn btn-success btn-icon" id="btn-refresh" >
                                 刷新
                            </button>
                        </div>
                    </div>
                    <div id="check-area" class="check-area">
                    </div>
                    <div id="jiexi-area" class="jiexi-area ">
                    </div>
                    <div id="download-area" class="download-area">
                        <div class="form">
                            <select id="download-log" class="select" style="width: 400px;height:30px;">
                                <%--<option value="贵阳">贵阳</option>--%>
                            </select>
                        </div>
                        <button type="button" class="btn btn-success btn-icon" id="btn-download" style="margin-left: 430px;margin-top:-50px;">
                            下载
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" data-backdrop="false" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
    <div class="modal-dialog" role="document" style="margin-top: 300px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">提示</h4>
            </div>
            <div class="modal-body">
                <img src="${ctx}/static/admin/images/loging.gif">您上传的资源较大，需要处理一段时间，请等待...
            </div>
            <div class="modal-footer">
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
<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/global/js/components/material.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}/static/admin/js/epub/addepub.js"></script>
<%--上传插件--%>
<link href="${ctx}/static/admin/css/ztree/fileinput.min.css" media="all" rel="stylesheet" type="text/css"/>
<link href="${ctx}/static/admin/css/ztree/themes/explorer/theme.min.css" media="all" rel="stylesheet" type="text/css"/>
<script src="${ctx}/static/admin/js/ztree/plugins/sortable.min.js" type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ztree/fileinput.min.js" type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ztree/locales/zh.js" type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ztree/themes/explorer/theme.min.js" type="text/javascript"></script>
<script>
    $(function () {
        $("#input-43").fileinput({
            showPreview: false,
            maxFileCount:1,
            language: 'zh',
            allowedFileExtensions: ["epub"],
            elErrorContainer: "#errorBlock",
        });
        $("#input-44").fileinput({
            showPreview: false,
            maxFileCount: 1,
            language: 'zh',
            allowedFileExtensions: ["zip"],
            elErrorContainer: "#errorBlock",
        });

        $(".fileinput-upload-button").click(function () {
            $('#myModal').modal('show')
        })
    })
</script>
<script>

    (function (document, window, $) {
        'use strict';
        var Site = window.Site;
        $(document).ready(function () {
            Site.run();
        });
    })(document, window, jQuery);

    var msg='${msg}';
    var obj = document.getElementById('file_input') ;
    if(msg=="2"){
        obj.outerHTML = obj.outerHTML;
        alert("请上传.epub文件");
        window.location.href="${ctx}/admin/admin-epub";
    }else if(msg=="3"){
        obj.outerHTML = obj.outerHTML;
        alert("上传失败");
        window.location.href="${ctx}/admin/admin-epub";
    }else if(msg=="0"){
        alert("请选择要上传的文件");
    }else if(msg=="1"){
        var file = $("#file_input")
        file.after(file.clone().val(""));
        file.remove();
//        obj.outerHTML = obj.outerHTML;
        alert("上传成功");
        window.location.href="${ctx}/admin/admin-epub";
    }else if (msg=="4"){
        obj.outerHTML = obj.outerHTML;
        alert("请上传.zip文件");
        window.location.href="${ctx}/admin/admin-epub";
    }else if (msg=="5"){
        obj.outerHTML = obj.outerHTML;
        alert("解压失败重新上传");
        window.location.href="${ctx}/admin/admin-epub";
    }
</script>
</body>
</html>
