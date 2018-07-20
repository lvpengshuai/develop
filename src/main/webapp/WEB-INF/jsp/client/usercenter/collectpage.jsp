<%--
  User: lcy
  Date: 2017/5/26
  Time: 14:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tags.jsp" %>
<ul>
<c:if test="${not empty items.myCollects}">
        <c:forEach varStatus="i" items="${items.myCollects}" var="myCollect">
            <li>
                <span class="his-td1">${i.count}</span>
                <!--标题和作品属性--->
                <span class="his-td2" style="text-align: center"><a href="${pageContext.request.contextPath}/<c:if test="${myCollect.type == 1}">book/</c:if><c:if test="${myCollect.type == 2}">journal/</c:if><c:if test="${myCollect.type == 3}">standard/</c:if>${myCollect.nameId}" target="_blank">${myCollect.name}</a></span>
                <span class="his-td3" style="width: 100px">
                        <c:if test="${myCollect.type == 1}">
                            图书
                        </c:if>
                        <c:if test="${myCollect.type == 2}">
                            期刊
                        </c:if>
                        <c:if test="${myCollect.type == 3}">
                            标准
                        </c:if>
                        <c:if test="${myCollect.type == 4}">
                            知识元
                        </c:if>
                    </span>
                <span class="his-td4"><fmt:formatDate value="${myCollect.gmtCreate}" type="date" pattern="yyyy-MM-dd"/></span>
                <span class="his-td5" id="clearCollect" onclick="userCenterManager.clearCollect('${userName}', '${myCollect.nameId}', '${myCollect.type}')">取消收藏</span>
            </li>
        </c:forEach>
</c:if>
</ul>
<!--分页-->
<c:if test="${PageCount > 1}">
    <%@ include file="page.jsp"%>
</c:if>

<%--隐藏存值--%>
<input type="hidden" id="type" value="${type}"/>
<input type="hidden" id="userName" value="${name}"/>
<input type="hidden" id="PageIndex" value="${PageIndex}"/>
<input type="hidden" id="PageSize" value="${PageSize}"/>
<input type="hidden" id="PageCount" value="${PageCount2}"/>
<input type="hidden" id="myCollects" value="${myCollects}"/>