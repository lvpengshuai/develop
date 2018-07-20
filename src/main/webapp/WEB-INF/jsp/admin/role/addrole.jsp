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
        .form-group {
            margin-bottom: 0px;
        }
    </style>
</head>
<body class="site-navbar-small dashboard" onload="addRoleManager.reloadpage()">
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
                        <div class="panel-body">
                            <form id="addRole" method="post">
                                <input type="hidden" id="status2" name="status" value="${role.status}">
                                <input type="hidden" id="id" name="flag" value="${id}"/>
                                <input type="hidden" id="attribute-menu-txt" name="attribute"
                                       value="${role.attribute}"/>
                                <input type="hidden" id="usedValue" value="${role.used}"/>
                                <div class="form-group" style="height: 36px;">
                                    <label for="pageSize">角色属性</label>
                                    <div id="unbind">
                                        <span class="page-list2">
                                            <span id="attribute-span" class="btn-group dropdown">
                                                <button class="btn btn-success  btn-outline dropdown-toggle"
                                                        id="attribute-button" type="button"
                                                        data-toggle="dropdown">
                                                    <span class="attribute_value" name="pageSize"
                                                          id="attribute">后台角色</span>
                                                    <span id="logo" class="caret"/>
                                                </button>
                                                <ul class="dropdown-menu attribute-value" role="menu">
                                                    <li>
                                                        <a href="javascript:void(0)">后台角色</a>
                                                    </li>
                                                    <li>
                                                        <a href="javascript:void(0)">前台角色</a>
                                                    </li>
                                                </ul>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                                <br/><br/><b/>
                                <div class="form-group" style="height: 36px;">
                                    <label for="roleName">角色名称</label>
                                    <div>
                                        <input type="text" class="form-control roleName" id="roleName"
                                               placeholder="请填写角色名称" name="name" value="${role.name}"
                                               style="width: 50%;float: left"
                                               required/>
                                        <span id="realNameTip"
                                              style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'></span>
                                    </div>
                                </div>
                                <br/><br/><b/>
                                <div class="form-group" id="test-div" style="margin-top: 20px">
                                    <label for="test">菜单功能分配:</label>
                                    <table id="test">
                                        <c:set var="i" value="8"></c:set>
                                        <c:forEach items="${functionMap}" var="map">
                                            <c:set var="i" value="${i+1}"></c:set>
                                            <tr>
                                                <td>
                                                    <label name="par"><input type="checkbox" name="par" id="bid${i}"
                                                                             onclick="addRoleManager.CheckAll(this.getAttribute('id'))">${map.key}权限:
                                                    </label>
                                                </td>
                                                <td>
                                            <tr>
                                                <c:set var="j" value="0"></c:set>
                                                <c:forEach items="${map.value}" var="val">
                                                    <c:set var="j" value="${j+1}"></c:set>
                                                    <td>
                                                        <c:if test="${online == 'OFF'}">
                                                            <c:if test="${val.name != '机构用户'}">
                                                                <label name="chkfunctions"
                                                                       style="padding-left: 100px;width: 200px"><input
                                                                        id="bid${i}_${j}" type="checkbox"
                                                                        name="chkfunctions"
                                                                        value="${val.id}"
                                                                        onclick="addRoleManager.CheckAll(this.getAttribute('id'))"
                                                                        <c:if test="${fn:indexOf(functions, val.id) != -1}">checked</c:if>>${val.name}
                                                                </label><br>
                                                            </c:if>
                                                        </c:if>

                                                        <c:if test="${online == 'ON'}">
                                                            <label name="chkfunctions"
                                                                   style="padding-left: 100px;width: 200px"><input
                                                                    id="bid${i}_${j}" type="checkbox"
                                                                    name="chkfunctions"
                                                                    value="${val.id}"
                                                                    onclick="addRoleManager.CheckAll(this.getAttribute('id'))"
                                                                    <c:if test="${fn:indexOf(functions, val.id) != -1}">checked</c:if>>${val.name}
                                                            </label><br>
                                                        </c:if>
                                                        <hr>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            </td>
                                            </tr>
                                        </c:forEach>
                                    </table>

                                </div>
                                <div class="form-group" id="attribute-menu-div" style="margin-top: 20px;display: none">
                                    <label for="attribute-menu">菜单功能分配:</label>
                                    <table id="attribute-menu">
                                        <c:set var="i" value="50"></c:set>
                                        <c:forEach items="${functionMap_2}" var="map">
                                            <c:set var="i" value="${i+1}"></c:set>
                                            <tr>
                                                <td>
                                                    <label name="par"><input type="checkbox" name="par" id="bid${i}"
                                                                             onclick="addRoleManager.CheckAll(this.getAttribute('id'))">${map.key}权限：
                                                    </label>
                                                </td>
                                                <td>
                                            <tr>
                                                <c:set var="j" value="0"></c:set>
                                                <c:forEach items="${map.value}" var="val">
                                                    <c:set var="j" value="${j+1}"></c:set>
                                                    <td>
                                                        <label name="chkfunctions"
                                                               style="padding-left: 100px;width: 215px"><input
                                                                id="bid${i}_${j}" type="checkbox" name="chkfunctions"
                                                                value="${val.id}"
                                                                onclick="addRoleManager.CheckAll(this.getAttribute('id'))"
                                                                <c:if test="${fn:indexOf(functions, val.id) != -1}">checked</c:if>>${val.name}
                                                        </label><br>
                                                        <hr>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                                <br/><br/><b/>
                                <div class="form-group">
                                    <label for="pageSize">是否启用</label>
                                    <div>
                                        <span class="page-list2">
                                            <span class="btn-group dropdown">
                                                <button class="btn btn-default  btn-outline dropdown-toggle"
                                                        type="button"
                                                        data-toggle="dropdown">
                                                    <span class="page-size2" name="pageSize" id="pageSize">启用</span>
                                                    <span class="caret"/>
                                                </button>
                                                <ul class="dropdown-menu state" role="menu">
                                                    <li>
                                                        <a href="javascript:void(0)">启用</a>
                                                    </li>
                                                    <li>
                                                        <a href="javascript:void(0)">禁用</a>
                                                    </li>
                                                </ul>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group" style="height: 100px;margin-top: 20px;">
                                    <label for="remark">角色描述</label>
                                    <textarea class="form-control remark" id="remark"
                                              style="min-height: 100px;resize: none;width: 50%;"
                                              name="remark" data-toggle="modal" placeholder="请添加角色描述"
                                              required></textarea>
                                    <%--<span id="remark_tip" style="font-size: 12px;color: red;float: right">nihoam</span>--%>
                                </div>
                                <br/><br/><b/>
                                <div class="row" style="margin-bottom: 20px;margin-top: 20px;">
                                    <div class="col-md-1 col-sm-2">
                                        <button class="btn btn-default" type="button" onclick="window.history.go(-1)">
                                            取消
                                        </button>
                                    </div>
                                    <div class="col-md-1 col-sm-2">
                                        <button class="btn btn-default" type="reset">重置</button>
                                    </div>

                                    <div class="col-md-9 col-sm-6">
                                        <div class="pull-right">
                                            <button class="btn btn-success" id="save" type="button" onclick="">保存
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
<%@include file="/WEB-INF/jsp/admin/include/footer.jsp" %>
<%-- 脚部结束 --%>
<!-- 通用模态框开始 -->
<%@include file="/WEB-INF/jsp/admin/include/modal.jsp" %>
<%-- 通用模态框结束 --%>

<!-- 通用js脚本 -->
<%@include file="../include/scrpit.jsp" %>

<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script src="${ctx}/static/remark/global/js/components/material.js"></script>

<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script src="${ctx}/static/admin/js/role/addRole.js"></script>

<script>
    (function (document, window, $) {
        'use strict';
        var Site = window.Site;
        $(document).ready(function () {
            Site.run();
        });

    })(document, window, jQuery);

    /* 判断状态信息 */
    if ('${role.status}' == '0') {
        $("#pageSize").text("启用");
    } else if ('${role.status}' == '1') {
        $("#pageSize").text('禁用');
    }

    /* 判断属性信息 */
    if ('${role.attribute}' == '1') {
        $("#attribute").text("后台角色");
        $("#test-div").show();
        $("#attribute-menu-div").hide();
        $("#attribute-menu-div input[type='checkbox']").removeAttr('checked');
        $("#attribute-button").attr("data-toggle", "");
        $("#logo").attr("class", "");
    } else if ('${role.attribute}' == '2') {
        $("#attribute").text('前台角色');
        $("#test-div").hide();
        $("#attribute-menu-div").show();
        $("#test-div input[type='checkbox']").removeAttr('checked');
        $("#attribute-button").attr("data-toggle", "");
        $("#logo").attr("class", "");
    }
    $("#remark").val('${role.remark}');
</script>
</body>
</html>