<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/18
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
﻿﻿<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<c:if test="${entry=='人物'}">
    <!-- 人物 -->
    <div class="search-results-links">
        为您找到相关人物约<b>${trscount}</b> 个
    </div>
    <div class="searchbox01">
        <div class="renwu renwustyle">
            <dl>
                <c:forEach items="${searchresult}" var="sub">
                    <dd><span><img src="${ctx}/static/client/img/userphoto_1.jpg"></span><h3>${sub.title}</h3><p style="width: 259px;">${sub.source}</p><a onclick='relatedProduct("${sub.hc}")'>相关内容</a></dd>
                </c:forEach>
            </dl>
        </div>
    </div>
</c:if>
<!-- 论文 -->
<c:if test="${entry=='论文'}">
    <div class="search-results-links">
        为您找到相关论文约<b>${trscount}</b> 个
    </div>
    <div class="searchbox04">
        <div class="wenxian wenxianstyle">
            <dl>
                <c:forEach items="${searchresult}" var="sub">
                    <dd>
                        <span>${sub.source}</span>
                        <span>${sub.people}</span>
                        <a style="width: 370px;overflow: hidden">
                                ${sub.title} </a>
                    </dd>
                </c:forEach>
            </dl>
        </div>
    </div>
</c:if>
<!-- 课题 -->
<c:if test="${entry=='课题'}">
    <div class="search-results-links">
        为您找到相关课题约<b>${trscount}</b> 个
    </div>
    <div class="searchbox05">
        <div class="keti-list">
            <dl>

                <c:forEach items="${searchresult}" var="sub">
                    <dd>
                        <h3><a>${sub.title}</a></h3>
                        <table width="100%" border="0">
                            <tbody>
                            <c:if test="${sub.exmap['项目区分']=='立项'}">
                                <tr>
                                    <td>立项资助：${sub.exmap["立项资助"]}</td>
                                    <td>编号：${sub.exmap["编号"]}</td>
                                    <td>负责人：${sub.exmap["负责人"]}</td>

                                </tr>
                                <tr>
                                    <td>职称：${sub.exmap["职称"]}  </td>
                                    <td>机构：${sub.exmap["机构"]}</td>
                                    <td>资助金额：${sub.exmap["资助金额"]}</td>
                                </tr>
                            </c:if>
                            <c:if test="${sub.exmap['项目区分']=='结项'}">
                                <tr>
                                    <td>立项资助：${sub.exmap["立项资助"]}</td>
                                    <td>编号：${sub.exmap["编号"]}</td>
                                    <td>负责人：${sub.exmap["负责人"]}</td>

                                </tr>
                                <tr>
                                    <td>职称：${sub.exmap["职称"]}  </td>
                                    <td>单位：${sub.exmap["单位"]}</td>
                                    <td>成果名称：
                                        <c:if test="${!empty sub.exmap['成果名称']}">
                                            ${sub.exmap["成果名称"]}
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td>成果形式：${sub.exmap["成果形式"]}  </td>
                                    <td>评级：${sub.exmap["评级"]}</td>
                                    <td>立项时间：${sub.exmap["立项时间"]}</td>
                                </tr>
                                <tr>
                                    <td>备注：${sub.exmap["备注"]}  </td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </dd>
                </c:forEach>

            </dl>
        </div>
    </div>
</c:if>
<!-- 会议 -->
<c:if test="${entry=='会议'}">
    <div class="search-results-links">
        为您找到相关会议约<b>${trscount}</b> 个
    </div>
    <div class="searchbox06">
        <div class="news">
            <dl>
                <c:forEach items="${searchresult}" var="sub">
                    <dd>
                        <h3><span>${sub.exdate}</span><a>${sub.title}</a></h3>
                        <p>${sub.hc}</p>
                    </dd>
                </c:forEach>
            </dl>
        </div>
    </div>
</c:if>
<!-- 图书 -->
<c:if test="${entry=='图书'}">
    <div class="search-results-links">
        为您找到相关图书约<b>${trscount}</b> 个
    </div>
    <div class="searchbox07">
        <div class="wenxian wenxianstyle">
            <dl>
                <c:forEach items="${searchresult}" var="sub">
                    <dd>
                        <span>${sub.source}</span>
                        <span>${sub.people}</span>
                        <a style="width: 370px;overflow: hidden" >${sub.title} </a>
                    </dd>
                </c:forEach>
            </dl>
        </div>
    </div>
</c:if>
<!-- 大事记 -->
<c:if test="${entry=='大事记'}">
    <div class="search-results-links">
        为您找到相关大事记约<b>${trscount}</b> 个
    </div>
    <div class="searchbox08">
        <div class="news">
            <dl>
                <c:forEach items="${searchresult}" var="result">
                    <dd>
                        <h3><span>${result.exdate}</span><a>${result.title}</a></h3>
                            ${result.hc}
                    </dd>
                </c:forEach>
            </dl>
        </div>
    </div>
</c:if>
<jsp:include page="../include/page.jsp"></jsp:include>
<script>
    var entry = $("#entry").val();

    //相关作品div显示
    function relatedProduct(conent){
        var newPP = conent.replace(/<p>/g, "<dd>").replace(/<\/p>/g, "</dd>");
        $(".layer-renwu .renwudetail dl").html(newPP);
        $(".layer-renwu .renwudetail dl dd:odd").css("backgroundColor", "#e6e6e6");
        $(".layer-renwu").show();
    }
    $(".newclose, .btn-cancle").click(function(){
        $(".layer-favedelete, .layer-renwu").hide();
    });
</script>
