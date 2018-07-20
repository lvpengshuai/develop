<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/25
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="search-results-link">
    <a href="#"><img src="${ctx}/static/client/img/result1.png" alt="">相关度排序</a>
    <a href="#"><img src="${ctx}/static/client/img/result2.png" alt="">热度排序</a>
    <a href="#"><img src="${ctx}/static/client/img/result3.png" alt="">出版日期排序</a>
    <a href="结果分析.html"><img src="${ctx}/static/client/img/result4.png" alt="">可视化分析</a>
</div>
<div class="search-results-num">为您找到相关条目约  <b>${trscount}</b> 个</div>
<div class="favolist">
    <dl>
        <c:forEach items="${searchresult}" var="items" >
            <dd>
                <em class="click_pj">拼接阅读</em>
                <h3><a href="/article/articleInfo?id=${items.id}" target="_blank"><b>${items.title}</b></a></h3>
                <h4>来源：
                    <c:forEach items="${items.listZT}" var="item" varStatus="status">
                    <b><a href="#">${item.titleSource}</a><c:if test="${!status.last}">\</c:if>
                        <b>
                            </c:forEach>
                </h4>
                <p>
                        ${items.content}
                </p>
            </dd>
        </c:forEach>
    </dl>
</div>
