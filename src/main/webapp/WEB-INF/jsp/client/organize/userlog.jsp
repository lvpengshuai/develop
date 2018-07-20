<%--
  Created by IntelliJ IDEA.
  User: zly
  Date: 2017-6-12
  Time: 9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tags.jsp" %>
<div class="yhjl-list" style="margin-bottom: 20px">
    <ul><li class="li-head"><span>IP</span><span>访问时间</span><span>检索词</span><span>资源下载</span></li></ul>
    <ul id="yhjlData">
        <c:if test="${not empty items}">
            <c:forEach items="${items}" var="log">
                <li><span>${log.ip}</span><span><fmt:formatDate value="${log.time}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    <span><c:if test="${not empty log.word}"><c:choose><c:when test="${fn:length(log.word) > 16}">${fn:substring(log.word, 0, 16)}...</c:when><c:otherwise>${log.word}</c:otherwise></c:choose></c:if><c:if test="${empty log.word}">无</c:if></span>
                    <span><c:if test="${not empty log.resource}"><c:choose><c:when test="${fn:length(log.resource) > 16}">${fn:substring(log.resource, 0, 16)}...</c:when><c:otherwise>${log.resource}</c:otherwise></c:choose></c:if><c:if test="${empty log.resource}">无</c:if></span>
                </li>
            </c:forEach>
        </c:if>
    </ul>
</div>
<c:if test="${userPageCount > 1}">
<div class="public_page">


        <span><a href="javascript:void(0);" onclick="usersetPage(${userPageIndex-1})" class="long">上一页</a></span>
        <c:if test="${userPageCount>1&&userPageCount<=5}">
            <c:forEach var="i" begin="1" end="${userPageCount }">
                <c:if test="${i==userPageIndex }">
                    <span><a id="a_${userPageIndex}" onclick="return false;" href="javascript:void(0);" class="page_now">${userPageIndex}</a></span>
                </c:if>
                <c:if test="${i!=userPageIndex }">
                    <span><a id="a_${i}" onclick="usersetPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                </c:if>
            </c:forEach>
        </c:if>
        <c:if test="${userPageCount>5}">
            <c:choose>
                <c:when test="${userPageIndex<4}">
                    <c:forEach var="i" begin="1" end="${userPageIndex-1}">
                        <span><a id="a_${i}" onclick="usersetPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                    </c:forEach>
                    <span><a id="a_${userPageIndex}" onclick="return false;" href="javascript:void(0);"
                             <c:if test="${userPageIndex<100}">class="page_now"</c:if>>${userPageIndex}</a>
                </span>
                </c:when>
                <c:otherwise>
                    <span><a id="a_1" onclick="usersetPage(1);return false;" href="javascript:void(0);">1</a></span>
                    <span><a href="#" onclick="return false;" style="cursor: default;">...</a></span>
                    <c:forEach var="i" begin="${userPageIndex-1}" end="${userPageIndex-1}">
                        <span><a id="a_${i}" onclick="usersetPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                    </c:forEach>
                    <span><a id="a_${userPageIndex}" onclick="return false;" href="javascript:void(0);"
                             <c:if test="${userPageIndex<100}">class="page_now"</c:if>
                             <c:if test="${userPageIndex>=100}">class="page_now"</c:if>>${userPageIndex}</a>
                </span>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${userPageIndex>=userPageCount-2 || userPageCount-2<=0}">
                    <c:forEach var="i" begin="${userPageIndex+1}" end="${userPageCount}">
                        <span><a id="a_${i}" onclick="usersetPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach var="i" begin="${userPageIndex+1}" end="${userPageIndex+1}">
                        <span><a id="a_${i}" onclick="usersetPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                    </c:forEach>
                    <span><a href="javascript:void(0);" onclick="return false;" style="cursor: default;">...</a></span>
                    <span><a id="a_${userPageCount}" onclick="usersetPage(${userPageCount});return false;" href="javascript:void(0);">${userPageCount}</a></span>
                </c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${userPageCount==1}">
            <span><a id="a_1" onclick="return false;" href="javascript:void(0);" class="page_now">1</a></span>
        </c:if>
        <span><a href="javascript:void(0);" onclick="usersetPage(${userPageIndex+1})">下一页</a></span>
        <span>跳转到</span>
        <span id="page-value"><input type="text" class="page_txt" id="userpageNum"></span>
        <span><a class="page_btn" href="javascript:void(0);" onclick="userpageJump();">GO</a></span>

        <input type="hidden" id="userpageCount" value="${userPageCount}"/>
    </div>
</c:if>

<%--隐藏存值--%>
<input type="hidden" id="userPageIndex" value="${userPageIndex}"/>
<input type="hidden" id="userPageSize" value="${userPageSize}"/>
<input type="hidden" id="userPageCount" value="${userPageCount}"/>