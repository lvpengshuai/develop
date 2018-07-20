<%--
  User: zly
  Date: 2017-4-12
  Time: 15:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/common.js"></script>
<%@ include file="../include/tags.jsp" %>
<input type="hidden" id="userName" value="${sessionScope.userName}"/>
<ul>
    <c:if test="${not empty items.books}">
        <c:set var="i" value="0"/>
        <c:forEach items="${items.books}" var="book">
            <c:set var="i" value="${i+1}"/>
            <li>
                <!--图标共三种 分别对应相对的class--->
                <i class="type-icon1"></i>
                <!--标题和作品属性--->
                <div class="pro-info">
                    <h2><a href="javascript:void(0);" onclick="window.open(encodeURI(appPath+'/book/'+${book.id}))" title="${book.bookName}">
                        <c:choose>
                            <c:when test="${fn:length(book.bookName) > 30}">
                                ${fn:substring(book.bookName, 0, 30)}...
                            </c:when>
                            <c:otherwise>
                                ${book.bookName}
                            </c:otherwise>
                        </c:choose>
                    </a></h2>
                    <div class="book-theme" style="display: none"> ${book.cipTheme}</div>
                    <div class="book-zone" style="display: none"> ${book.publisher}</div>
                    <div class="book-author" style="display: none"> ${book.bookAuthor}</div>
                    <div class="book-pubDate" style="display: none"> ${book.pubDate}</div>
                    <div class="pro-attr"><span>ISBN：<b>${book.isbnFull}</b></span><span>出版时间：<b>${book.pubDate}</b></span><span>被引：<b>${book.yzwx}次</b></span></div>
                </div>
                <!--用户交互按钮--->
                <div class="pro-interact">
                    <a class="module-title-cite" href="javascript:void(0);"></a>
                    <div class="bdsharebuttonbox">
                        <a class="module-title-share bds_more" data-cmd="more" href="javascript:void(0);"></a>
                    </div>
                    <c:if test="${book.pubYear == 'false'}">
                        <a class="module-title-collect collect-book" value="${book.bookName}" name="${book.id}" id="myCollect-show${i}" href="javascript:void(0)"><i></i></a>
                    </c:if>
                    <c:if test="${book.pubYear == 'true'}">
                        <a class="module-title-collect" id="myCollect-show" style="background-image: url('../../../../static/client/images/knowledge-title-icon-collect.png');color: #faa91e" href="javascript:void(0)" onclick="return false;"><i></i></a>
                    </c:if>
                </div>
            </li>
        </c:forEach>
    </c:if>
    <c:if test="${not empty items.journals}">
        <c:set var="i" value="0"/>
        <c:forEach items="${items.journals}" var="journal">
            <c:set var="i" value="${i+1}"/>
            <li>
                <!--图标共三种 分别对应相对的class--->
                <i class="type-icon2"></i>
                <!--标题和作品属性--->
                <div class="pro-info">
                    <div class="journal-author" style="display: none"> ${journal.lianXiZuoZhe}</div>
                    <div class="journal-article-name" style="display: none"> ${journal.periodicalName}</div>
                    <div class="journal-name" style="display: none"> ${journal.chName}</div>
                    <div class="journal-year" style="display: none"> ${journal.years},${journal.roll} (${journal.phase})</div>

                    <h2>
                        <a href="javascript:void(0);" onclick="window.open(encodeURI(appPath+'/journal/'+${journal.id}))" title="${journal.chName}" style="display:block;width:370px;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">
                                ${journal.chName}
                        </a>
                    </h2>
                    <div class="pro-attr"><c:if test="${not empty journal.lururenyuan}"><span>刊名：<b>《${journal.lururenyuan}》</b></span></c:if><span>年 · 卷（期）：<b>${journal.years}.${journal.roll}（${journal.phase}）</b></span><span>被引：<b>0次</b></span></div>
                </div>
                <!--用户交互按钮--->
                <div class="pro-interact">
                    <a class="module-title-cite" href="javascript:void(0);"></a>
                    <div class="bdsharebuttonbox">
                        <a class="module-title-share bds_more" data-cmd="more" href="javascript:void(0);" id="share"></a>
                    </div>
                    <input type="hidden" id="journal-name" value="${journal.chName}"/>
                    <input type="hidden" id="journal-name-id" value="${journal.id}"/>
                    <input type="hidden" id="journal-type" value="2"/>
                    <c:if test="${journal.quanwen == 'false'}">
                        <a class="module-title-collect collect-journal" name="${journal.id}" value="${journal.chName}" id="myCollect-show${i}" href="javascript:void(0)"><i></i></a>
                    </c:if>
                    <c:if test="${journal.quanwen == 'true'}">
                        <a class="module-title-collect" id="myCollect-show" style="background-image: url('../../../../static/client/images/knowledge-title-icon-collect.png');color: #faa91e" href="javascript:void(0)" onclick="return false;"><i></i></a>
                    </c:if>
                </div>
            </li>
        </c:forEach>
    </c:if>
    <c:if test="${not empty items.standards}">
        <c:set var="i" value="0"/>
        <c:forEach items="${items.standards}" var="standard">
            <c:set var="i" value="${i+1}"/>
            <li>
                <!--图标共三种 分别对应相对的class--->
                <i class="type-icon3"></i>
                <!--标题和作品属性--->
                <div class="pro-info">
                    <div class="standard-code" style="display: none"> ${standard.standardId}</div>
                    <div class="standard-name" style="display: none"> ${standard.bookId}</div>
                    <div class="standard-zone" style="display: none"> ${standard.pushCompany}</div>
                    <div class="standard-author" style="display: none">${standard.drafter}</div>
                    <div class="standard-year" style="display: none">${standard.pubDate}</div>
                    <h2><a href="javascript:void(0);" onclick="window.open(encodeURI(appPath+'/standard/'+${standard.id}))" title="${standard.chName}">
                        <c:choose>
                            <c:when test="${fn:length(standard.chName) > 30}">
                                ${fn:substring(standard.chName, 0, 30)}...
                            </c:when>
                            <c:otherwise>
                                ${standard.chName}
                            </c:otherwise>
                        </c:choose>
                    </a></h2>
                    <div class="pro-attr"><span>编号：<b>${standard.standardId}</b></span><span>状态：<b>${standard.standardState} </b></span><span>被引：<b>0次</b></span></div>
                </div>
                <!--用户交互按钮--->
                <div class="pro-interact">
                    <a class="module-title-cite" href="javascript:void(0);"></a>
                    <div class="bdsharebuttonbox"><a class="module-title-share bds_more" data-cmd="more" href="javascript:void(0);"></a></div>
                    <input type="hidden" id="standard-name" value="${standard.chName}"/>
                    <input type="hidden" id="standard-id" value="${standard.id}"/>
                    <input type="hidden" id="standard-type" value="3"/>
                    <c:if test="${standard.pdfUrl == 'false'}">
                        <a class="module-title-collect collect-standard" name="${standard.id}" value="${standard.chName}" id="myCollect-show${i}" href="javascript:void(0)"><i></i></a>
                    </c:if>
                    <c:if test="${standard.pdfUrl == 'true'}">
                        <a class="module-title-collect"  id="myCollect-show" style="background-image: url('../../../../static/client/images/knowledge-title-icon-collect.png');color: #faa91e" href="javascript:void(0)" onclick="return false;"><i></i></a>
                    </c:if>
                </div>
            </li>
        </c:forEach>
    </c:if>
</ul>
<!--分页-->
<c:if test="${PageCount > 1}">
    <%@ include file="../include/page.jsp"%>
</c:if>

<%--隐藏存值--%>
<input type="hidden" id="type" value="${type}"/>
<input type="hidden" id="author" value="${author.name}"/>
<input type="hidden" id="PageIndex" value="${PageIndex}"/>
<input type="hidden" id="PageSize" value="${PageSize}"/>
<input type="hidden" id="PageCount" value="${PageCount}"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/customer/js/author/quote.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/collect/collect.js"></script>
