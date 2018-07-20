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

    <title>添加角色</title>
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
            height:36px;
        }
        #username,#pwd,#qpassword,#realname,#email,#telephone,#address{
            width: 50%;
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
                            <form id="addUser" method="post">
                                <input type="hidden" name="id" value="${user.id}">
                                <div class="form-group height-div">
                                    <label>账户</label>
                                    <div>
                                        <input type="text" class="form-control username" placeholder="请填写账户(必填)" id="username" name="username" value="${user.username}" <c:if test="${not empty user.username}">readonly</c:if> required/>
                                        <span id="nameTip" style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'></span>
                                    </div>
                                </div>
                                <c:if test="${empty user.pwd}">
                                    <br>
                                    <br>
                                    <div class="form-group height-div">
                                        <label>密码</label>
                                        <div>
                                            <input type="password" class="form-control pwd" placeholder="请填写密码(必填)" id="pwd"
                                                   name="pwd" required/>
                                        </div>
                                    </div>
                                    <br>
                                    <br>
                                    <div class="form-group height-div">
                                        <label>确认密码</label>
                                        <div>
                                            <input type="password" class="form-control qpassword" id="qpassword"
                                                   name="qpassword" placeholder="请再次填写密码(必填)" equalTo="#pwd" required/>
                                        </div>
                                    </div>
                                </c:if>

                                <br>
                                <br>
                                <div class="form-group height-div">
                                    <label>真实姓名</label>
                                    <div>
                                        <input type="text" class="form-control realname" placeholder="请填写真实姓名(必填)" id="realname"
                                               name="realname" value="${user.realname}" required/>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="form-group height-div">
                                    <label>邮箱</label>
                                    <div>
                                        <input type="text" class="form-control email" placeholder="请填写邮箱(必填)" id="email"
                                               name="email" value="${user.email}" required/>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="form-group height-div">
                                    <label>电话</label>
                                    <div>
                                        <input type="text" class="form-control telephone" placeholder="请填写电话(必填)" id="telephone"
                                               name="telephone" value="${user.telephone}" required/>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="form-group height-div">
                                    <label>地址</label>
                                    <div>
                                        <input type="text" class="form-control" placeholder="请填写地址" id="address"
                                               name="address" value="${user.address}"/>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="form-group">
                                    <label>用户角色</label>
                                    <select class="form-control" id="roleId" name="roleId" onchange="" style="width:120px;">
                                        <option value=0>请选择角色</option>
                                        <c:forEach items="${roleList}" var="roles">
                                            <option value=${roles.id} ${role.id eq roles.id ? "selected": ""}>${roles.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <c:if test="${line == 'ON'}">
                                <br>
                                <div class="form-group">
                                    <label>机构</label>
                                    <select class="form-control" id="organizeId" name="organizeId" onchange="" style="width:120px;">
                                        <option value=0>请选择机构</option>
                                        <c:forEach items="${orgList}" var="orgList">
                                            <option value=${orgList.id} ${orgainsizeid eq orgList.id ? "selected": ""}>${orgList.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                </c:if>
                                <br>
                                <div class="form-group">
                                    <label>是否启用</label>
                                    <select class="form-control" id="status" name="status" onchange="" style="width:120px;">
                                        <option value=0 ${user.status eq 0 ? "selected": ""}>是</option>
                                        <option value=1 ${user.status eq 1 ? "selected": ""}>否</option>
                                    </select>
                                </div>
                                <br>
                                <br>
                                <div class="row" style="margin-bottom: 20px;margin-top: 20px;">
                                    <div class="col-md-1 col-sm-2">
                                        <button class="btn btn-default" type="button" onclick="window.history.go(-1)">取消</button>
                                    </div>
                                    <div class="col-md-1 col-sm-2">
                                        <button class="btn btn-default" type="reset">重置</button>
                                    </div>
                                    <div class="col-md-4 col-sm-6">
                                        <div class="pull-right">
                                            <button class="btn btn-success" id="save" type="button" onclick="">保存</button>
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
<script src="${ctx}/static/admin/js/user/addUser.js"></script>

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