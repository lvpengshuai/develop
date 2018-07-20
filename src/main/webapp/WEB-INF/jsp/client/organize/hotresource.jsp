<%--
  Created by IntelliJ IDEA.
  User: zly
  Date: 2017-6-9
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tags.jsp" %>
<div class="canvas">
    <c:if test="${not empty items}">
        <ul>
            <c:forEach var="i" items="${items}" begin="0" end="5">
                <li>
                    <a href="javascript:void(0);" style="width: 320px;display: inline-block;overflow: hidden;text-overflow: ellipsis;white-space:nowrap" onclick="openResource('${i.resource_id}',$('#hotresourcetype').val())" title="${i.name}" style="width: 200px;overflow:hidden;">《${i.name}》<%--《<c:choose><c:when test="${fn:length(i.name) > 20}">${fn:substring(i.name, 0, 20)}...</c:when><c:otherwise>${i.name}</c:otherwise></c:choose>》--%></a>
                    <span>点击量：${i.count}</span></li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty items}">
        暂无数据。
    </c:if>
</div>

<input type="hidden" id="hotresourcetype" value="${hotopt}"/>