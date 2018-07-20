<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="public_page">
	<span><a href="javascript:void(0);" onclick="setPage(${PageIndex-1})" class="long">上一页</a></span>
    <c:if test="${PageCount>1&&PageCount<=5}">
        <c:forEach var="i" begin="1" end="${PageCount }">
            <c:if test="${i==PageIndex }">
	            <span><a id="a_${PageIndex}" onclick="return false;" href="javascript:void(0);" class="page_now">${PageIndex}</a></span>
            </c:if>
            <c:if test="${i!=PageIndex }">
	            <span><a id="a_${i}" onclick="setPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
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
                         <c:if test="${PageIndex<100}">class="page_now"</c:if>>${PageIndex}</a>
                </span>
            </c:when>
            <c:otherwise>
	            <span><a id="a_1" onclick="setPage(1);return false;" href="javascript:void(0);">1</a></span>
                <span><a href="#" onclick="return false;" style="cursor: default;">...</a></span>
                <c:forEach var="i" begin="${PageIndex-1}" end="${PageIndex-1}">
	                <span><a id="a_${i}" onclick="setPage(${i});return false;" href="javascript:void(0);">${i}</a></span>
                </c:forEach>
                <span><a id="a_${PageIndex}" onclick="return false;" href="javascript:void(0);"
                         <c:if test="${PageIndex<100}">class="page_now"</c:if>
                         <c:if test="${PageIndex>=100}">class="page_now"</c:if>>${PageIndex}</a>
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
        <span><a id="a_1" onclick="return false;" href="javascript:void(0);" class="page_now">1</a></span>
    </c:if>
    <span><a href="javascript:void(0);" onclick="setPage(${PageIndex+1})">下一页</a></span>
    <span>跳转到</span>
    <span id="page-value"><input type="text" class="page_txt" id="pageNum-1"></span>
    <span><a class="page_btn" href="javascript:void(0);" onclick="pageJumpValue();">GO</a></span>

    <input type="hidden" id="pageCount2" value="${PageCount}"/>
</div>
<script type="text/javascript">
    function pageJumpValue() {
        var page = $("#pageNum-1").val();
        if (page != "" && /^[0-9]*$/.test(page)) {
            setPage(page);
        } else {
            alert("请输入正确的页码!");
        }
    }

    var pageIndex = $('#PageIndex').val();
    function setPage(pageIndex) {
        var sHistoryClass = $('#searchHistory').attr("class");
        if(sHistoryClass == "p-ico3 on") {
            var count = $('#pageCount').val();
        }else {
            var count = $('#pageCount2').val();
        }
        if (parseInt(pageIndex) < 1) {
            pageIndex = 1
        }
        if (parseInt(pageIndex) > parseInt(count)) {
            pageIndex = count
        }
        $('#PageIndex').val(pageIndex);
        if(sHistoryClass == "p-ico3 on"){
            userCenterManager.getSearchHistory()
            return
        }
        userCenterManager.getResource($('#type').val());
    }
</script>
