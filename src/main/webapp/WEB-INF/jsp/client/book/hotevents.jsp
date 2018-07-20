<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/27
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/client/customer/js/book/analysis.js"></script>

<dl class="activitylist">
    <c:if test="${bookList== null || fn:length(bookList) == 0}">
        <div style="width: 100%;text-align: center;font-size: 38px;height: 200px;padding-top: 50px;">本年无热点事件记录</div>
    </c:if>
    <c:forEach items="${bookList}" var="item">
        <dd>
        <span>
            <b><fmt:formatDate value="${item.exdate}" pattern="yyyy"/></b>
            <em><fmt:formatDate value="${item.exdate}" pattern="MM-dd"/></em><strong style="padding:7px;font-size:14px;">${item.exarea==""?"其他":item.exarea}</strong>
        </span>
            <h3><a style="cursor: default">${item.title}</a></h3>
            <p>${item.htmlContent}</p>
        </dd>
    </c:forEach>
</dl>
<jsp:include page="../include/page.jsp"></jsp:include>
<script>
    //会议列表
//    $(function () {
//        $(".activitylist dd:gt(1)").hide();
//        $(".showmore").click(function(){
//            $(".activitylist dd:gt(1)").slideDown();
//            $(this).html("");
//        });
//    })
    var count = '${PageCount}';
    function setPage(pageIndex){

        if(pageIndex<1){pageIndex=1}
        if(parseInt(pageIndex)>parseInt(count)){pageIndex=count}
        gethotevent(pageIndex,'${year}','${bookAbbreviate}');
    }


</script>
<script src="${ctx}/static/client/customer/js/hovertreescroll.js"></script>