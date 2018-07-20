<%--
  User: zly
  Date: 2017-3-3
  Time: 13:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="page-header  padding-bottom-5">
    <ol class="breadcrumb">
        <li><a href="/admin/home" id="home">概览</a></li>
        <c:if test="${empty stitle}">
            <li class="active">${title}</li>
        </c:if>
        <c:if test="${not empty stitle}">
            <li><a href="${href}" id="title">${title}</a></li>
            <li class="active">${stitle}</li>
        </c:if>
    </ol>
</div>
