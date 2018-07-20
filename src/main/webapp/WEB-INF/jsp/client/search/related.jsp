<%--
  Created by IntelliJ IDEA.
  User: xubo
  Date: 2017/7/3
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tags.jsp"%>
<c:if test="${!empty wordList}">
    <p>您是否还要查询:&nbsp;&nbsp;</p>
    <c:forEach items="${wordList}" var="sub" varStatus="i">

        <a  href="${pageContext.request.contextPath}/search?keyWord=${sub}"><span style="color: #0079e9">${sub}</span></a>&nbsp;&nbsp;
    </c:forEach>
</c:if>
