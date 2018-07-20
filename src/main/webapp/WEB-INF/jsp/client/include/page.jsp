<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<link rel="stylesheet" href="${ctx}/static/client/css/page.css" />
<link rel="stylesheet" href="${ctx}/static/client/css/alertify.css" />
<script type="text/javascript" src="${ctx}/static/client/js/alertify.js"></script>

<div class="pre_next">
	<span><a href="javascript:void(0);" onclick="setPage('${PageIndex-1}')" class="pre">&lt; 上一页</a></span>
    <c:if test="${PageCount>1&&PageCount<=5}">
        <c:forEach var="i" begin="1" end="${PageCount}">
            <c:if test="${i==PageIndex }">
	            <a id="a_${PageIndex}" onclick="return false;" href="javascript:void(0);" class="cur">${PageIndex}</a>
            </c:if>
            <c:if test="${i!=PageIndex }">
	            <a id="a_${i}" onclick="setPage('${i}');return false;" href="javascript:void(0);">${i}</a>
            </c:if>
        </c:forEach>
    </c:if>

    <c:if test="${PageCount>5}">
        <c:choose>
            <c:when test="${PageIndex<4}">
                <c:forEach var="i" begin="1" end="${PageIndex-1}">
	                <span><a id="a_${i}" onclick="setPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                </c:forEach>
                <span><a id="a_${PageIndex}" onclick="return false;" href="javascript:void(0);"
                         <c:if test="${PageIndex<100}">class="cur"</c:if>>${PageIndex}</a>
                </span>
            </c:when>
            <c:otherwise>
	            <span><a id="a_1" onclick="setPage(1);return false;" href="javascript:void(0);">1</a></span>
                <span><a onclick="return false;" style="cursor: default;">...</a></span>
                <c:forEach var="i" begin="${PageIndex-1}" end="${PageIndex-1}">
	                <span><a id="a_${i}" onclick="setPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                </c:forEach>
                <span><a id="a_${PageIndex}" onclick="return false;" href="javascript:void(0);"
                         <c:if test="${PageIndex<100}">class="cur"</c:if>
                         <c:if test="${PageIndex>=100}">class="cur"</c:if>>${PageIndex}</a>
                </span>
            </c:otherwise>
        </c:choose>
        
        <c:choose>
            <c:when test="${PageIndex>=PageCount-2 || PageCount-2<=0}">
                <c:forEach var="i" begin="${PageIndex+1}" end="${PageCount}">
	                <span><a id="a_${i}" onclick="setPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach var="i" begin="${PageIndex+1}" end="${PageIndex+1}">
	                <span><a id="a_${i}" onclick="setPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                </c:forEach>
                    <span><a href="javascript:void(0);" onclick="return false;" style="cursor: default;">...</a></span>
                    <span><a id="a_${PageCount}" onclick="setPage(${PageCount});return false;"href="javascript:void(0);">${PageCount}</a></span>
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:if test="${PageCount==1}">
        <span><a id="a_1" onclick="return false;" href="javascript:void(0);" class="cur">1</a></span>
    </c:if>
        <a href="#" class="next" onclick="setPage('${PageIndex+1}')">下一页 &gt;</a>
        <span>共${PageCount}页</span>
        <span>到第</span>
        <input id="pageNum" name="" class="pre_txt" type="text">
        <span>页</span>
        <input name="" class="pre_sub" value="确定" onclick="pageJump()" type="button">

    <input type="hidden" id="pageCount" value="${PageCount}"/>
</div>
<script type="text/javascript">
    function pageJump(){
        var page = $("#pageNum").val();
        var pageCount = $("#pageCount").val();
        if(page!=""&&/^[0-9]*$/.test(page)){
            if(parseInt(pageCount)<parseInt(page)){
                page=$("#pageCount").val();
            }
            setPage(page);
        }else{
            alertify.alert("请输入正确的页码!");
        }
    }
</script>
