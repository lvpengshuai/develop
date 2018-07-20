<%--
  User: zly
  Date: 2017-6-8
  Time: 10:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tags.jsp" %>
<div class="jmenu-layer-list">
    <ul>
        <li><span>IP</span><span>访问时间</span></li>
    </ul>
    <ul id="jmenu-layer">
        <c:if test="${not empty items}">
            <c:forEach var="log" items="${items.items}">
                <li><span>${log.ip}</span><span><fmt:formatDate value="${log.gmt_create}" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            </c:forEach>
        </c:if>
    </ul>
</div>
<!--分页-->
<c:if test="${PageCount > 1}">
    <%@ include file="../include/page.jsp" %>
</c:if>
<!--/分页-->

<%--隐藏存值--%>
<input type="hidden" id="type" value="${type}"/>
<input type="hidden" id="PageIndex" value="${PageIndex}"/>
<input type="hidden" id="PageSize" value="${PageSize}"/>
<input type="hidden" id="PageCount" value="${PageCount}"/>