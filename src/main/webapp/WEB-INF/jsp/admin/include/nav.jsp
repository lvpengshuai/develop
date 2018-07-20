<%--
  User: zly
  Date: 2017-3-3
  Time: 10:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<c:set var="menus" value="${fn:split('数据中心:md-compass,资源库:md-library,用户:md-account,统计:md-filter-list,日志:md-view-compact,系统管理:md-settings,资源管理:glyphicon glyphicon-book', ',')}"></c:set>
<nav class="site-navbar navbar navbar-inverse navbar-fixed-top navbar-mega" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle hamburger hamburger-close navbar-toggle-left hided" data-toggle="menubar">
            <span class="sr-only">Toggle navigation</span>
            <span class="hamburger-bar"></span>
        </button>
        <button type="button" class="navbar-toggle collapsed" data-target="#site-navbar-collapse"
                data-toggle="collapse">
            <i class="icon md-more" aria-hidden="true"></i>
        </button>
        <div class="navbar-brand navbar-brand-center site-gridmenu-toggle" data-toggle="gridmenu">
            <%--<img class="navbar-brand-logo" src="${ctx}/static/admin/images/hglogo.png" title="Remark">--%>
            <span class="navbar-brand-text hidden-xs"> 中国社会科学年鉴数据库</span>
        </div>
    </div>
    <div class="navbar-container container-fluid">
        <!-- 顶部开始 -->
        <div class="collapse navbar-collapse navbar-collapse-toolbar" id="site-navbar-collapse">
            <!-- 顶部左侧按钮开始 -->
            <ul class="nav navbar-toolbar">
                <li class="hidden-float" id="toggleMenubar">
                    <a data-toggle="menubar" href="#" role="button" style="/*padding-top: 24px;*/height: 60px;">
                        <i class="icon hamburger hamburger-arrow-left">
                            <span class="sr-only">Toggle menubar</span>
                            <span class="hamburger-bar"></span>
                        </i>
                    </a>
                </li>
                <li class="hidden-xs" id="toggleFullscreen">
                    <a class="icon icon-fullscreen" data-toggle="fullscreen" href="#" role="button">
                        <span class="sr-only">Toggle fullscreen</span>
                    </a>
                </li>
            </ul>
            <!-- 顶部左侧按钮结束 -->
            <!-- 顶部右侧按钮开始 -->
            <ul class="nav navbar-toolbar navbar-right navbar-toolbar-right">
                <li class="dropdown">
                    <a class="navbar-avatar dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false"
                       data-animation="scale-up" role="button">
                      <span class="avatar avatar-online">
                        <img src="${ctx}/static/admin/images/portraits/100.jpg" alt="...">
                        <i></i>
                      </span>
                        您好，${adminUserName}
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li role="presentation">
                            <a href="/admin/admin-edit" role="menuitem"><i class="icon md-account"
                                                                            aria-hidden="true"></i> 个人信息</a>
                        </li>
                        <li role="presentation">
                            <a href="/admin/admin-reset" role="menuitem"><i class="icon md-settings"
                                                                            aria-hidden="true"></i> 修改密码</a>
                        </li>
                        <li class="divider" role="presentation"></li>
                        <li role="presentation">
                            <a href="javascript:void(0)" onclick="window.location.href='${ctx}/admin/logout'" role="menuitem"><i class="icon md-power"
                                                                            aria-hidden="true"></i> 退出</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- 顶部右侧按钮结束 -->
        </div>
        <!-- 顶部结束 -->
    </div>
</nav>

<%-- 右侧菜单导航开始 --%>
<div class="site-menubar">
    <ul class="site-menu">
        <li class="site-menu-item">
            <a class="animsition-link" href="${ctx}/admin/home">
                <i class="site-menu-icon md-view-dashboard" aria-hidden="true"></i>
                <span class="site-menu-title">概览</span>
            </a>
        </li>

        <c:forEach var="function" items="${nav}">
            <li class="site-menu-item has-sub">
                <a href="javascript:void(0)">
                    <c:forEach var="menu" items="${menus}">
                        <c:if test="${fn:split(menu, ':')[0] eq function.key}">
                            <i class="site-menu-icon ${fn:split(menu, ':')[1]}"></i>
                        </c:if>
                    </c:forEach>
                    <span class="site-menu-title">${function.key}</span>
                    <span class="site-menu-arrow"></span>
                </a>
                <ul class="site-menu-sub">
                    <c:forEach var="menu" items="${function.value}">
                        <li class="site-menu-item">
                            <c:if test="${menu.name == '资源统计'}">
                                <a class="animsition-link" href="${menu.url}">
                                    <span class="site-menu-title">${menu.name}</span>
                                </a>
                            </c:if>
                            <c:if test="${menu.name != '资源统计'}">
                                <a class="animsition-link" href="${pageContext.request.contextPath}/admin${menu.url}">
                                    <span class="site-menu-title">${menu.name}</span>
                                </a>
                            </c:if>

                        </li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
    </ul>
</div>
<%-- 右侧菜单导航结束 --%>
