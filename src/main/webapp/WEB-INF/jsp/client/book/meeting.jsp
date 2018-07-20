<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/27
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <c:if test="${bookList== null || fn:length(bookList) == 0}">
        <div style="width: 100%;text-align: center;font-size: 38px;height: 200px;padding-top: 50px;">本月无会议活动</div>
    </c:if>
    <c:forEach items="${bookList}" var="item">
        <dd>
            <span>
                <b><fmt:formatDate value="${item.exdate}" pattern="yyyy"/></b>
                <em><fmt:formatDate value="${item.exdate}" pattern="MM-dd"/></em>
                <strong style="padding:7px;font-size:15px;">${item.exarea==""?"其他":item.exarea}</strong>
            </span>
            <h3><a style="cursor: default">${item.title}</a></h3>
            <div class="activeinfo">${item.htmlContent}</div>
            <a href="##" class="activemore"><b><img src="${ctx}/static/client/img/upmore.jpg" alt=""></b><img src="${ctx}/static/client/img/lookmore.jpg" alt=""></a>
        </dd>
    </c:forEach>
<c:if test="${bookList== null || fn:length(bookList) >= 6}">
    <div class="showmore"><a>+ 显示更多信息(共有${bookListCount}条信息)</a></div>
</c:if>
<script>
    //会议列表
    $(function () {
        $(".activitylist dd:gt(4)").hide();
        $(".showmore").click(function(){
            $(".activitylist dd:gt(4)").slideDown();
            $(this).html("");
        });
    })
//    //会议点击加载更多
//    $(".activemore").click(function(){
//        alert("meet");
//        $(this).prev(".activeinfo").toggleClass("active");
//        $(this).children("b").toggle();
//    });
</script>
