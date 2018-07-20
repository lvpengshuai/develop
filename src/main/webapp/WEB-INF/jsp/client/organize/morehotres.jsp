<%--
  Created by IntelliJ IDEA.
  User: zly
  Date: 2017-6-9
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tags.jsp" %>
<div class="canvas">
    <c:if test="${not empty items}">
        <ul>
            <c:forEach var="i" items="${items}" begin="0" end="5">
                <li>
                    <a href="javascript:void(0);" style="width: 450px;display: inline-block;overflow: hidden;text-overflow: ellipsis;white-space:nowrap" onclick="openResource('${i.resource_id}',$('#hotresourcetype').val())" title="${i.name}">《${i.name}》</a>
                    <span>点击量：${i.count}</span></li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty items}">
        暂无数据。
    </c:if>
</div>
<c:if test="${hotPageCount > 1}">
<div class="public_page">
                    <span><a href="javascript:void(0);" onclick="hotsetPage(${hotPageIndex-1})" class="long">上一页</a></span>
                    <c:if test="${hotPageCount>1&&hotPageCount<=5}">
                        <c:forEach var="i" begin="1" end="${hotPageCount }">
                            <c:if test="${i==hotPageIndex }">
                                <span><a id="a_${hotPageIndex}" onclick="return false;" href="javascript:void(0);" class="page_now">${hotPageIndex}</a></span>
                            </c:if>
                            <c:if test="${i!=hotPageIndex }">
                                <span><a id="a_${i}" onclick="hotsetPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${hotPageCount>5}">
                        <c:choose>
                            <c:when test="${hotPageIndex<4}">
                                <c:forEach var="i" begin="1" end="${hotPageIndex-1}">
                                    <span><a id="a_${i}" onclick="hotsetPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                                </c:forEach>
                                <span><a id="a_${hotPageIndex}" onclick="return false;" href="javascript:void(0);"
                                         <c:if test="${hotPageIndex<100}">class="page_now"</c:if>>${hotPageIndex}</a>
                </span>
                            </c:when>
                            <c:otherwise>
                                <span><a id="a_1" onclick="hotsetPage(1);return false;" href="javascript:void(0);">1</a></span>
                                <span><a href="#" onclick="return false;" style="cursor: default;">...</a></span>
                                <c:forEach var="i" begin="${hotPageIndex-1}" end="${hotPageIndex-1}">
                                    <span><a id="a_${i}" onclick="hotsetPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                                </c:forEach>
                                <span><a id="a_${hotPageIndex}" onclick="return false;" href="javascript:void(0);"
                                         <c:if test="${hotPageIndex<100}">class="page_now"</c:if>
                                         <c:if test="${hotPageIndex>=100}">class="page_now"</c:if>>${hotPageIndex}</a>
                </span>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${hotPageIndex>=hotPageCount-2 || hotPageCount-2<=0}">
                                <c:forEach var="i" begin="${hotPageIndex+1}" end="${hotPageCount}">
                                    <span><a id="a_${i}" onclick="hotsetPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="i" begin="${hotPageIndex+1}" end="${hotPageIndex+1}">
                                    <span><a id="a_${i}" onclick="hotsetPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                                </c:forEach>
                                <span><a href="javascript:void(0);" onclick="return false;" style="cursor: default;">...</a></span>
                                <span><a id="a_${hotPageCount}" onclick="hotsetPage(${hotPageCount});return false;"href="javascript:void(0);">${hotPageCount}</a></span>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${hotPageCount==1}">
                        <span><a id="a_1" onclick="return false;" href="javascript:void(0);" class="page_now">1</a></span>
                    </c:if>
                    <span><a href="javascript:void(0);" onclick="hotsetPage(${hotPageIndex+1})">下一页</a></span>
                    <span>跳转到</span>
                    <span id="page-value"><input type="text" class="page_txt" id="hotpageNum"></span>
                    <span><a class="page_btn" href="javascript:void(0);" onclick="hotpageJump();">GO</a></span>

                    <input type="hidden" id="hotpageCount" value="${hotPageCount}"/>
                </div>
</c:if>

<%--隐藏存值--%>
<input type="hidden" id="hottab" value="${hottab}"/>
<input type="hidden" id="hotopt" value="${hotopt}"/>
<input type="hidden" id="hotPageIndex" value="${hotPageIndex}"/>
<input type="hidden" id="hotPageSize" value="${hotPageSize}"/>
<input type="hidden" id="hotPageCount" value="${hotPageCount}"/>