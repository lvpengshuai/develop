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

    <title>添加机构</title>
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
        .height-div{
            height:36px;
        }
        #name,#address,#telephone,#expiration,#maxOnline,#file_input,#file_input_line,#fileupload{
            width: 50%;
            float: left;
        }
        /*.ip{*/
            /*width: 15%;*/
            /*float: left;*/
        /*}*/
        #member{
            width: 50%;
        }

        #AddMoreFileBox, #AddMoreFileBoxOne {
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
<body class="site-navbar-small dashboard" onload="addOrganizeManager.reloadpage()">
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
                            <form id="addOrganize" method="post" enctype="multipart/form-data">
                                <input type="hidden" id="orgId" name="id" value="${organize.id}">
                                <input type="hidden" name="roleId" value="${organize.roleId eq null ? "0": organize.roleId}">
                                <input type="hidden" name="file" value="${organize.file eq null ? "0": organize.file}">
                                <div class="form-group height-div">
                                    <label>机构名称</label>
                                    <div>
                                        <input type="text" class="form-control name" placeholder="请填写机构名称" id="name" name="name" value="${organize.name}" required/>
                                        <span id="nameTip" style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'></span>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="form-group height-div">
                                    <label>机构地址</label>
                                    <div>
                                        <input type="text" class="form-control address" placeholder="请填写机构地址" id="address" name="address" value="${organize.address}" required/>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="form-group height-div">
                                    <label>机构电话</label>
                                    <div>
                                        <input type="text" class="form-control telephone" placeholder="请填写机构电话" id="telephone" name="telephone" value="${organize.telephone}" required/>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <%--<div class="form-group height-div">--%>
                                    <%--<label>允许最大用户在线数</label>--%>
                                    <%--<div>--%>
                                        <%--<input type="text" class="form-control maxOnline" placeholder="请填写最大用户在线数" id="maxOnline" name="maxOnline" value="${organize.maxOnline}" required/>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <input type="hidden" class="form-control maxOnline" placeholder="请填写最大用户在线数" id="maxOnline" name="maxOnline" value="500" required/>
                                <div class="form-group height-div">
                                    <label>有效时间</label>
                                    <div>
                                        <input type="text" class="form-control expiration" placeholder="请选择机构使用有效时间" id="expiration" name="expiration" readonly value="${expiration}" required/>
                                    </div>
                                </div>


                                <br>
                              <%--  <br>
                                <div class="form-group height-div">
                                    <label>上传合同</label><c:if test="${not empty organize.file}">
                                    <a href="${pageContext.request.contextPath}/admin/organize/download/${organize.id}">查看附件</a>
                                </c:if>
                                    <div class="form-group form-material floating" id="updiv" style="margin-top: 10px;">
                                        <input type="text" class="form-control" id="file_input_line" readonly=""/>
                                        <input type="file" id="file_input" name="files" multiple=""/>
                                        <label class="floating-label">请选择..</label>
                                    </div>
                                </div>
                                <br>--%>
                                <br>
                                <div class="form-group" style="width: 50%">
                                    <label>机构IP范围</label>
                                    <textarea class="form-control ips" rows="5" id="ipd" name="ipStartOne" value="">${organizeIp}</textarea>
                                    <span id="errorIps" ></span>
                                    <%--<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>"+errorIp+"</span>--%>
                                    <%--<div>--%>
                                    <%--<c:set value="${organizeIp}" var="organizeIp"/>--%>
                                    <%--<c:forEach var="i" begin="0" end="0">--%>
                                    <%--<input type="text" class="form-control ip" placeholder="请填写机构IP范围开始" id="ipStart1" name="ipStart" value="${organizeIp[i].ipStart}" required/>--%>
                                    <%--<i style="float:left;line-height: 36px" aria-hidden="true">—</i>--%>
                                    <%--<input type="text" class="form-control ip" placeholder="请填写机构IP范围结束" id="ipEnd1" name="ipEnd" value="${organizeIp[i].ipEnd}" required/>--%>
                                    <%--</c:forEach>--%>
                                    <%--<a href="javascript:void(0);" rel="external nofollow" rel="external nofollow" rel="external nofollow" id="AddMoreFileBox" class="glyphicon glyphicon-plus"></a>--%>
                                    <%--</div>--%>
                                </div>
                                <%--<br>--%>
                                <%--<br>--%>
                                <%--<div id="InputsWrapper">--%>
                                <%--<c:if test="${fn:length(organizeIp)>1}">--%>
                                <%--<c:forEach var="i" begin="1" end="${fn:length(organizeIp)-1}">--%>
                                <%--<c:if test="${organizeIp[i].ipStart!=organizeIp[i].ipEnd}">--%>
                                <%--<div class="form-group height-div" style="margin-bottom: 10px">--%>
                                <%--<input type="text" class="form-control ip" placeholder="请填写机构IP范围开始"--%>
                                <%--name="ipStart" value="${organizeIp[i].ipStart}" required/>--%>
                                <%--<i style="float:left;line-height: 36px" aria-hidden="true">—</i>--%>
                                <%--<input type="text" class="form-control ip" placeholder="请填写机构IP范围结束"--%>
                                <%--name="ipEnd" value="${organizeIp[i].ipEnd}" required/>--%>
                                <%--<a href="javascript:void(0);" style="text-decoration: none;line-height: 36px;margin-left: 10px;color: #4caf50" rel="external nofollow" rel="external nofollow" rel="external nofollow" class="glyphicon glyphicon-minus removeclass"></a>--%>
                                <%--</div>--%>
                                <%--</c:if>--%>
                                <%--</c:forEach>--%>
                                <%--</c:if>--%>
                                <%--</div>--%>
                                <%--<div class="form-group height-div">--%>
                                <%--<label>单个机构IP</label>--%>
                                <%--<div>--%>
                                <%--<c:set value="${organizeIpOne}" var="organizeIpOne"/>--%>
                                <%--<c:forEach var="i" begin="0" end="0">--%>
                                <%--<input type="text" class="form-control ip" placeholder="请填写机构IP" id="ipStart2" name="ipStartOne" value="${organizeIpOne[i]}" required/>--%>
                                <%--</c:forEach>--%>
                                <%--<a href="javascript:void(0);" rel="external nofollow" rel="external nofollow" rel="external nofollow" id="AddMoreFileBoxOne" class="glyphicon glyphicon-plus"></a>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--<br>--%>
                                <%--<br>--%>
                                <%--<div id="InputsWrapperOne">--%>
                                <%--<c:if test="${fn:length(organizeIp)>1}">--%>
                                <%--<c:forEach var="i" begin="1" end="${fn:length(organizeIpOne)-1}">--%>
                                <%--<div class="form-group height-div" style="margin-bottom: 10px">--%>
                                <%--<input type="text" class="form-control ip" placeholder="请填写机构IP范围开始"--%>
                                <%--name="ipStartOne" value="${organizeIpOne[i]}" required/>--%>
                                <%--<a href="javascript:void(0);" style="text-decoration: none;line-height: 36px;margin-left: 10px;color: #4caf50" rel="external nofollow" rel="external nofollow" rel="external nofollow" class="glyphicon glyphicon-minus removeclass"></a>--%>
                                <%--</div>--%>
                                <%--</c:forEach>--%>
                                <%--</c:if>--%>
                                <%--</div>--%>
                                <c:if test="${online=='OFF'}">
                                <div class="form-group">
                                    <label>机构所属用户</label>
                                    <div>
                                        <input type="text" class="form-control member" placeholder="请选择机构会员" id="member" name="member"
                                               value="<c:forEach var="a" items='${organizeAdmins}'>${a.username},</c:forEach>" readonly />
                                        <input type="hidden" id="memberId" name="memberId" value="<c:forEach var="a" items='${organizeAdmins}'>${a.id},</c:forEach>">
                                        <%--<span id="admin"
                                              style='font-size: 12px;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>* 如需创建新的机构管理员，点击<a href="${ctx}/register" target="_blank">创建</a></span>--%>
                                    </div>
                                </div>
                                </c:if>
                                <br>
                                <div class="form-group" id="attribute-menu-div">
                                    <label for="attribute-menu">机构权限分配:</label>
                                    <table id="attribute-menu">
                                        <c:set var="i" value="50"></c:set>
                                        <c:forEach items="${functionMap}" var="map">
                                            <c:set var="i" value="${i+1}"></c:set>
                                            <tr>
                                                <td>
                                                    <label name="par">
                                                        <input type="checkbox" name="par" id="bid${i}" onclick="addOrganizeManager.CheckAll(this.getAttribute('id'))">${map.key}权限:
                                                    </label>
                                                </td>
                                                <td>
                                            <tr>
                                                <c:set var="j" value="0"></c:set>
                                                <c:forEach items="${map.value}" var="val">
                                                    <c:set var="j" value="${j+1}"></c:set>
                                                    <td>
                                                        <label name="permission" style="padding-left: 100px;width: 215px">
                                                            <c:if test="${stitle == '修改机构'}">
                                                            <input
                                                                    id="bid${i}_${j}" type="checkbox" name="permission"
                                                                    value="${val.id}"
                                                                    onclick="addOrganizeManager.CheckAll(this.getAttribute('id'))"
                                                                    <c:if test="${fn:indexOf(functions, val.id) != -1}">checked</c:if>>${val.name}
                                                            </c:if>
                                                            <c:if test="${stitle == '添加机构'}">
                                                                <input
                                                                        id="bid${i}_${j}" type="checkbox" name="permission"
                                                                        value="${val.id}"
                                                                        onclick="addOrganizeManager.CheckAll(this.getAttribute('id'))"
                                                                      checked>${val.name}
                                                            </c:if>
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
                                <div class="form-group">
                                    <label>是否启用</label>
                                    <select class="form-control" id="status" name="status" onchange="" style="width:120px;">
                                        <option value=0 ${organize.status eq 0 ? "selected": ""}>是</option>
                                        <option value=1 ${organize.status eq 1 ? "selected": ""}>否</option>
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
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/global/js/components/material.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}/static/admin/js/organize/addorganize.js"></script>

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