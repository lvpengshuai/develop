<%--
  User: lcy
  Date: 2017/5/26
  Time: 14:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tags.jsp" %>
<ul>
    <c:if test="${not empty items.searchHistory}">
        <c:forEach varStatus="i" items="${items.searchHistory}" var="searchHistory">
                <li>
                    <span class="his-td1">${i.count}</span>
                    <span class="his-td2"><a href="${searchHistory.searchurl}" target="_blank">${searchHistory.searchname}</a></span>
                    <span class="his-td3">${searchHistory.createtime}</span>
                    <span onclick="userCenterManager.removeHistory(${searchHistory.id},0)" style="width: 234px;" class="his-td4">删除</span>
                </li>
        </c:forEach>

    </c:if>
</ul>
<!--分页-->
<c:if test="${PageCount > 1}">
    <%@ include file="../include/page.jsp"%>
</c:if>

<%--隐藏存值--%>
<input type="hidden" id="userName" value="${name}"/>
<input type="hidden" id="PageIndex" value="${PageIndex}"/>
<input type="hidden" id="PageSize" value="${PageSize}"/>
<input type="hidden" id="PageCount" value="${PageCount}"/>