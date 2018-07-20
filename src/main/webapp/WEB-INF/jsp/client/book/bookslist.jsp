<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>中国社会科学年鉴数据库</title>
    <!-- 共用 js -->
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/common0925.js"></script>
    <!-- 公用搜索进入文件 公用退出js-->
    <script type="text/javascript" src="${ctx}/static/client/customer/publicJS.js"></script>
    <!-- 公用搜索进入文件 -->

    <!-- 共用css -->
    <link rel="stylesheet" href="${ctx}/static/client/css/style.css"/>

    <!-- banner切换 -->
    <link rel="stylesheet" href="${ctx}/static/client/css/normalize.css"/>
    <script src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>

    <!-- 下拉框插件 -->
    <%--<link rel="stylesheet" href="${ctx}/static/client/css/drop-down.css"/>--%>
    <%--<script src="${ctx}/static/client/js/jquery-ui.min.js"></script>--%>
    <script src="${ctx}/static/client/js/select-widget-min.js"></script>

    <script>


        $(document).ready(function(){
            $(".list_nj").css({"background":"url(../../../../static/client/img/haed_li.png) repeat center center","color":" #878787","display":"block"});

            $(".ui-select").selectWidget({
                change: function (changes) {
                    window.open("${ctx}/book/book?bookcode=" + changes);
                    return changes;
                },
                effect       : "slide",
                keyControl   : true,
                speed        : 200,
                scrollHeight : 250
            });

        });

        $(function () {
            $(".select-main").click(function () {
                var v=$(this).prev().val();
                var s=$(this).parents("dd").find("a");
                s.attr("href","${ctx}/book/book?bookcode="+v);
            })
        })
    </script>
</head>
<body>
<!--顶部-->
<%@include file="/WEB-INF/jsp/client/include/publicTop.jsp" %>
<!--导航-->
<%@include file="/WEB-INF/jsp/client/include/publicNavigation.jsp" %>
<!-- banner -->
<%@include file="/WEB-INF/jsp/client/include/publicBanner.jsp" %>
<!--精品书籍推荐-->
<div class="wrapin">

    <!-- 左边 -->
    <div class="colum_left">

        <!-- 当前位置 -->
        <div class="statue">
            当前位置：<a href="${ctx}/index">首页</a> &gt;&gt;<span>年鉴</span>
        </div>

        <!-- 学科类年鉴 -->
        <%--<div class="morebox">--%>
            <%--<h3>${navigation}--%>
            <%--</h3>--%>
            <%--<div class="moreinfo">--%>
                <%--<c:if test="${bookListCategorys.size()>8}">--%>
                    <%--<em>更多</em>--%>
                <%--</c:if>--%>
                <%--<c:forEach items="${bookListCategorys}" var="bookListCategory">--%>
                    <%--<a href="${ctx}/book/analysis?bookAbbreviate=${bookListCategory.bookAbbreviate}">${bookListCategory.bookType}年鉴</a>--%>
                <%--</c:forEach>--%>
                <%--<c:if test="${bookListCategorys.size()==0}">--%>
                    <%--暂无数据--%>
                <%--</c:if>--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="nianjianhot">
            <c:if test="${bookListCategorys.size()>0}">
            <%--<h3><a href="${ctx}/book/booksList?order=desc"><img src="${ctx}/static/client/img/hot_2.png" alt="">热度排序</a></h3>--%>
            </c:if>
            <h3 style="float: right;">共<b style="color:#ca0000">${bookListCategorys.size()}</b>种年鉴,共<b style="color:#ca0000">${books.size()}</b>卷次</h3>
        </div>
        <!-- 年鉴列表 -->
        <dl class="nianjianlist">
            <c:forEach items="${bookList}" var="book">
                <dd>
                    <div class="nianjian-total"><em>共${book.bookYearsList.size()}卷</em></div>
                    <span><a href="${ctx}/book/book?bookcode=${book.bookcode}" target="_blank"><img src="${book.cover}" alt=""></a></span>
                    <h3><a href="${ctx}/book/book?bookcode=${book.bookcode}" target="_blank">${book.bookname}</a></h3>
                    <h4>${book.title_en}</h4>
                    <div class="nianjianlistinfo">
                        <p>${book.author}</p>
                        <p>出版日期：${book.pubdate}</p>
                        <p>字数（千字）：${book.charCount}</p>
                    </div>
                    <div class="nianjianother">
                        其他卷次：
                        <p>
                            <c:forEach items="${book.bookYearsList}" var="bookYears">
                                <a href="${ctx}/book/book?bookcode=${bookYears.bookcode}" value="${bookYears.bookcode}" target="_blank">${bookYears.bookyear}</a>
                            </c:forEach>
                            <em>&gt;&gt;</em>
                            <i>&lt;&lt;</i>
                        </p>
                    </div>
                </dd>
            </c:forEach>
            <c:if test="${bookList.size()==0}">
                暂无数据
            </c:if>
        </dl>
        <c:if test="${bookListCategorys.size()>0}">
            <%@ include file="../include/page.jsp" %>
        </c:if>

    </div>


    <!-- 右边 -->
    <div class="colum_right">

        <div class="right-box">
            <div class="right-hd"><h3>热门年鉴</h3></div>
            <div class="right-bd">
                <dl class="njhot">
                    <c:forEach items="${booksByHot}" var="book" varStatus="i">
                        <dd>
                            <span><em>${i.index+1}</em><a href="${ctx}/book/book?bookcode=${book.bookcode}" target="_blank"><img src="${book.cover}" alt=""></a></span>
                            <a href="${ctx}/book/book?bookcode=${book.bookcode}" target="_blank"><div style="height: 90px;font-size: 18px;">${book.title}</div></a>
                            <p>点击量：<b>${book.hot}</b></p>
                        </dd>
                    </c:forEach>
                </dl>
            </div>
        </div>

    </div>

</div>

<!-- 弹出目录 -->
<div class="layer">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="layerinfo">
            <dl class="mululist">
                <dt>第一篇 特辑</dt>
                <dd><a href="#">在联合国教科文组织总部的演讲</a></dd>
                <dd><a href="#">推动人类文明交流互鉴，促世界和平谱写新篇章</a></dd>
                <dd><a href="#">中国民俗学会进入联合国教科文组织非遗申报“审查机构”</a></dd>
                <dd><a href="#">朝戈金当选国际哲学与人文科学理事会主席</a></dd>
                <dd><a href="#">我们站在文艺发展的历史新起点上</a></dd>
                <dd><a href="#">民间文艺永恒价值的诘问——学习习近平总书记在文艺工作座谈会上的讲话</a></dd>
                <dd><a href="#">推动人类文明交流互鉴，促世界和平谱写新篇章</a></dd>
                <dd><a href="#">中国民俗学会进入联合国教科文组织非遗申报“审查机构”</a></dd>
                <dd><a href="#">朝戈金当选国际哲学与人文科学理事会主席</a></dd>
                <dd><a href="#">我们站在文艺发展的历史新起点上</a></dd>
                <dd><a href="#">民间文艺永恒价值的诘问——学习习近平总书记在文艺工作座谈会上的讲话</a></dd>
            </dl>
        </div>
    </div>
</div>
<script>
    // 导航栏选定方法
    set_class(".nav .navbox a", "on", '${navigation}');
    function setPage(page) {
        if (page<=0){
            page=1;
        }else if(page>=${PageCount}){
            page=${PageCount};
        }
        <c:if test="${order==null}">
        location.href = "${ctx}/book/booksList?page="+page;
        </c:if>
        <c:if test="${order!=null}">
        location.href = "${ctx}/book/booksList?page="+page+"&order=desc";
        </c:if>
    }
</script>
<!-- 底部 -->
<%@ include file="../include/footer.jsp" %>
</body>
</html>
