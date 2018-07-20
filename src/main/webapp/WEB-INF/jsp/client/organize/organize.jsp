<%--
  Created by IntelliJ IDEA.
  User: zly
  Date: 2017-6-5
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tags.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>机构管理-化工知识服务平台</title>
    <link href="${pageContext.request.contextPath}/static/client/css/base.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/client/customer/css/organize/jgyh.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/client/plugin/layer/layer.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/client/css/login.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/client/plugin/jqcloud/jqcloud.css" rel="stylesheet"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/customer/index.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/jquery-validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/echarts3/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/custominput.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/login.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/customer/js/organize/organize.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/jqcloud/jqcloud-1.0.4.min.js"></script>
</head>

<body class="home">
<!--header-->
<%@ include file="../include/header.jsp" %>
<!--/header-->

<!--main-->
<!--bar-->
<div class="jgyh-bar">
    <div class="container">
        <div class="pull-left name">${organize.name}</div>
        <div class="pull-right bar-info">
            <%--<span>一级用户</span>--%>
            <span>有效期至：<fmt:formatDate value="${organize.expiration}" pattern="yyyy-MM-dd"/></span>
        </div>
    </div>
</div>
<!--/bar-->
<div class="container">
    <!--box-info-->
    <div class="jgyh-menu-box mt-20">
        <div class="jgyh-menu-li">
            <ul>
                <li class="li1" name="今日访问量">
                    <span class="data"></span>
                    <span class="name">今日访问量</span>
                </li>
                <li class="li2" name="历史访问量">
                    <span class="data"></span>
                    <span class="name">历史访问量</span>
                </li>
                <li class="li3" name="今日搜索量">
                    <span class="data"></span>
                    <span class="name">今日搜索量</span>
                </li>
                <li class="li4" name="历史搜索量">
                    <span class="data"></span>
                    <span class="name">历史搜索量</span>
                </li>
                <li class="li5" name="今日下载量">
                    <span class="data"></span>
                    <span class="name">今日下载量</span>
                </li>
                <li class="li6" name="历史下载量">
                    <span class="data"></span>
                    <span class="name">历史下载量</span>
                </li>
            </ul>
        </div>
    </div>
    <!--/box-info-->
    <!--使用情况-->
    <div class="jgyh-syqk mt-20">
        <div class="jgyh-head">
            <span>使用情况</span>
            <%--<a class="jgyh-more" href="#">更多&gt;&gt;</a>--%>
        </div>
        <div class="jqyh-syqk-pannel">
            <div class="month-tab" id="syqk-time">
                <span class="on">一周内</span>
                <span>一月内</span>
                <span>一年内</span>
            </div>
            <div class="jqyh-tabs-li" id="syqkTab">
                <span class="on">访问</span>
                <span>检索</span>
                <span>下载</span>
            </div>
            <div class="jqyh-tabs-main" id="syqkTabMain">
                <div class="canvas" id="syqkMain">
                </div>
            </div>
        </div>
    </div>
    <!--/使用情况-->
    <!--热门资源、热门搜索词-->
    <div class="col2-pannel mt-20">
        <!--热门资源-->
        <div class="jgyh-rmzy">
            <div class="jgyh-head">
                <span>热门资源</span>
                <a class="jgyh-more" id="rmzy-more" href="javascript:void(0);">更多&gt;&gt;</a>
            </div>
            <div class="rmzy-pannel">
                <div class="month-tab" id="rmzy-opt">
                    <span class="on">电子书</span>
                    <span>期刊</span>
                    <span>标准</span>
                </div>
                <div class="rmzy-tabs-li" id="rmzy-tabs-li">
                    <span class="on">点击</span>
                    <span>下载</span>
                </div>
                <div class="rmzy-tabs-main" id="rmzy-tabs-main"></div>
            </div>
        </div>
        <!--/热门资源-->
        <!--热门搜索词-->
        <div class="jgyh-rmssc">
            <div class="jgyh-head">
                <span>热门搜索词</span>
                <div class="month-tab" id="rmssc-time">
                    <span class="on">一周内</span>
                    <span>一月内</span>
                    <span>一年内</span>
                </div>
            </div>
            <div class="rmssc-pannel" id="rmsscMain" style="width: 488px;height: 274px;"></div>
        </div>
        <!--/热门搜索词-->
    </div>
    <!--/热门资源、热门搜索词-->
    <!--用户记录-->
    <div class="jgyh-yhjl mt-20">
        <div class="jgyh-head">
            <span>用户记录</span>
            <%--<a class="jgyh-more" href="#">更多&gt;&gt;</a>--%>
        </div>
        <div class="yhjl-pannel" id="yhjlMain">

        </div>
    </div>
    <!--/用户记录-->

</div>

<!--/main-->
<!--menu选择弹层-->
<div class="jmenu-layer" id="menu-layer" style="display:none;">
    <div class="jmenu-layer-head"><span id="name"></span>
        <div class="jmenu-closed"></div>
    </div>
    <div class="jmenu-layer-pannel" id="menuMaim"></div>
</div>
<!--menu选择弹层-->
<!--热门资源弹层-->
<div class="jmenu-layer" id="rmzy-layer" style="display:none;">
    <div class="jmenu-layer-head"><span>热门资源</span>
        <div class="jmenu-closed"></div>
    </div>
    <div class="jmenu-layer-pannel">
        <div class="rm-layer-list">
            <div class="rmzy-pannel">
                <div class="month-tab" id="rm-layer-opt">
                    <span class="on">电子书</span>
                    <span>期刊</span>
                    <span>标准</span>
                </div>
                <div class="rmzy-tabs-li" id="rm-layer">
                    <span class="on">点击</span>
                    <span>下载</span>
                </div>
                <div class="rmzy-tabs-main" id="rm-layer-main">
                </div>
            </div>
        </div>
    </div>
</div>
<!--/热门资源弹层-->
<input type="hidden" id="ipStart" value="${ipStart}"/>
<input type="hidden" id="ipEnd" value="${ipEnd}"/>
<input type="hidden" id="orgId" value="${organize.id}"/>
<%@include file="../include/wangmai.jsp"%>
<%@ include file="../include/footer.jsp" %>
</body>
</html>
