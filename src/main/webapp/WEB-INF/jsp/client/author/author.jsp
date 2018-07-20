<%--
  User: zly
  Date: 2017-4-5
  Time: 13:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${author.name}-作者详情-化工百科知识服务平台</title>
    <link href="${pageContext.request.contextPath}/static/client/css/base.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/client/css/pubstyle.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/client/plugin/layer/layer.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/client/css/login.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/jquery/jquery-1.11.1.min.js"></script>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/common.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/jquery-validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/custominput.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/login.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/collect/collect.js"></script>

    <script>
        var appPath = '${ctx}';
    </script>
</head>

<body class="searchbody">

<%@ include file="../include/header.jsp"%>
<%@ include file="../include/search.jsp"%>

<!--main-->
<div class="container author">
    <!--左侧区域-->
    <div class="left-frame">
        <!--栏目名称-->
        <div class="module-title">
            <div class="module-title-name layer-h-name">基本信息</div>
            <div class="module-title-info">
                <div class="bdsharebuttonbox"><a class="module-title-share bds_more" data-cmd="more" href="javascript:void(0);">分享<i></i></a></div>
            </div>
        </div>
        <!--/栏目名称-->
        <!--知识信息-->
        <div class="sku-box">
            <div class="sku-full"><h2>${author.name}</h2></div>
            <div class="sku-full"><span>单位机构：</span><b title="${author.organization}">
                    <c:choose>
                        <c:when test="${fn:length(author.organization) > 43}">
                            ${fn:substring(author.organization, 0, 43)}...
                        </c:when>
                        <c:otherwise>
                            ${author.organization}
                        </c:otherwise>

                    </c:choose>
            </b></div>
            <div class="sku-half"><span>收录图书：</span><b class="sku-color">${total.bookTotal}</b></div>
            <div class="sku-half"><span>收录期刊：</span><b class="sku-color">${total.journalTotal}</b></div>
            <div class="sku-half"><span>收录标准：</span><b class="sku-color">${total.standardTotal}</b></div>
            <div class="sku-full">
                <span>关注领域：</span>
                <c:if test="${not empty keyword}">
                    <div class="sku-summary">
                        <c:forEach items="${keyword}" var="k" begin="0" end="30" varStatus="state">
                            <c:if test="${not empty k}">
                            <a href="javascript:void(0);" onclick="window.open(encodeURI(appPath+'/search?keyWord='+'${k}'))" title="${k}">
                                <c:choose>
                                    <c:when test="${fn:length(keyword)<5}">
                                        ${k}
                                    </c:when>
                                    <c:otherwise>
                                        <%--<c:choose>--%>
                                            <%--<c:when test="${fn:length(k) > 10}">--%>
                                                <%--${fn:substring(k, 0, 10)}...--%>
                                            <%--</c:when>--%>
                                            <%--<c:otherwise>--%>
                                                ${k}
                                            <%--</c:otherwise>--%>
                                        <%--</c:choose>--%>
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
        <!--/知识信息-->
        <!--作品详情-->
        <!--栏目名称-->
        <div class="module-title">
            <div class="module-title-name layer-h-name">作品详情</div>
            <div class="sr_sort">
                <ul>
                    <li class="a-s-types">
                        <span class="sort_bg">全部</span>
                        <div class="type-li">
                            <a href="javascript:void(0);" onclick="resource(0)">全部</a>
                            <a href="javascript:void(0);" onclick="resource(1)">电子书</a>
                            <a href="javascript:void(0);" onclick="resource(2)">期刊</a>
                            <a href="javascript:void(0);" onclick="resource(3)">标准</a>
                        </div>
                    </li>
                    <%--<li><span class="sort_bg1">发表日期</span></li>--%>
                    <%--<li><span class="sort_bg2">用户评价</span></li>--%>
                </ul>
            </div>
        </div>
        <!--/栏目名称-->
        <!--作品列表-->
        <div class="pro-list"></div>
        <!--/作品列表-->
        <!--/作品详情-->
    </div>
    <!--/左侧区域-->
    <!--右侧区域-->
    <div class="right-frame">
        <!--合作学者-->
        <div  class="qtxgzy">
            <div class="zs"><p class="z_title">合作学者</p></div>
            <ul class="media-txt">
                <c:if test="${not empty relatedauthor}">
                    <c:forEach items="${relatedauthor}" var="authors">
                        <li><a href="javascript:void(0);" onclick="window.open(encodeURI('${ctx}/author/'+'${authors}'))"><b></b>${authors}</a></li>
                    </c:forEach>
                </c:if>
                <c:if test="${empty relatedauthor}">
                    <li><a href="javascript:void(0);" style="cursor: default" onclick="return false;"><b></b>无</a></li>
                </c:if>
            </ul>
        </div>
        <!--/合作学者-->
    </div>
    <!--/右侧区域-->
    <div class="clear"></div>
</div>
<!--main-->
<%--隐藏存值--%>
<input type="hidden" id="author" value="${author.name}"/>

<%@ include file="../include/footer.jsp"%>
<%@ include file="../include/layer-pub.jsp"%>

<script src="${ctx}/static/client/customer/js/author/author.js"></script>
<script>
    getResource(0);
</script>
<%@include file="../include/wangmai.jsp"%>
</body>
</html>
