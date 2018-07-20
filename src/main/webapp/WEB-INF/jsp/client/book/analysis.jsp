<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${bookType}年鉴</title>
    <!-- 共用 js -->
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>
    <!-- 公用搜索进入文件 -->
    <script type="text/javascript" src="${ctx}/static/client/customer/publicJS.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/customer/search.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/customer/js/book/analysis.js"></script>
    <!-- 公用搜索进入文件 -->

    <!-- 共用css -->
    <link rel="stylesheet" href="${ctx}/static/client/css/style.css" />
    <link rel="stylesheet" href="${ctx}/static/client/css/style0925.css" />
    <link rel="stylesheet" href="${ctx}/static/client/css/alertify.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/alertify.js"></script>

    <!-- banner切换 -->
    <link rel="stylesheet" href="${ctx}/static/client/css/normalize.css" />
    <script src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
    <script src="${ctx}/static/client/js/demo.js"></script>

    <!-- 滚动条 -->
    <script src="${ctx}/static/client/js/scrollbar/jquery.mousewheel.js"></script>
    <script src="${ctx}/static/client/js/scrollbar/perfect-scrollbar.js"></script>
    <link href="${ctx}/static/client/js/scrollbar/perfect-scrollbar.css" rel="stylesheet">
    <script>
        var appPath = "${pageContext.request.contextPath}";
        $(function(){
            //滚动条
            $('.slidebox, .fenxilist').perfectScrollbar({
                wheelSpeed: 20,
                wheelPropagation: false
            });
        });
    </script>

    <!-- 目录对比阅读插件 -->
    <script src="${ctx}/static/client/js/slides.min.jquery.js"></script>
    <script>
        $(function(){
            //滚动条
            $('.slidebox, .fenxilist').perfectScrollbar({
                wheelSpeed: 20,
                wheelPropagation: false
            });
        });
        var appPath = '${ctx}';
        var username='${userName}';
        $(function(){
            $('.fenxislides').slides({
                preload: true,
                preloadImage: '${ctx}/static/client/img/loading.gif',
                play: 10000,
                pause: 2500,
                hoverPause: true,
                animationStart: function(){
                    $('.caption').animate({
                        bottom:-35
                    },100);
                },
                animationComplete: function(current){
                    $('.caption').animate({
                        bottom:0
                    },200);
                    if (window.console && console.log) {
                        // example return of current slide number
                        console.log(current);
                    }
                }
            });

        });
    </script>

    <script type="text/javascript" src="${ctx}/static/client/js/jcarousellite_1.js"></script>
    <!-- 热点事件 -->
    <script>
        $(function(){
            var liLength = $("#rtList li").size();
            //alert(liLength);
            if(liLength>=10){
                $("#rtList").jCarouselLite({
                    btnNext: "#rt_next",
                    btnPrev: "#rt_pre",
                    visible:10,
                    auto:3000,
                    speed: 500
                });
            }
        })

    </script>
    <!-- E-charts -->
    <script src="${ctx}/static/client/js/ECharts/echarts.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/ECharts/word/echarts-wordcloud.js"></script>
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
    <div class="colum_center">

        <!-- 当前位置 -->
        <div class="statue">
            当前位置：<a href="${ctx}/index">首页</a> &gt;&gt;<span>${book.bookType}年鉴</span>
        </div>

        <!-- 详细 -->
        <div class="pro_des">

            <div class="pro_img_img">
                <em></em>
                <img src="${book.cover}" />
            </div>

            <div class="shuzi_right">

                <div class="shuzi_right_top">
                    <h5 class="tit">${bookType}年鉴<p>${title_en}</p></h5>
                </div>

                <div class="add_buy">
                    <div class="yearbox">
                        所属类别：${bookType}
                    </div>
                    <div class="color2 yearbox">
                        <em><img src="${ctx}/static/client/img/more.png" alt=""></em>
                        年鉴卷次：
                        <c:forEach items="${bookList}" var="book">
                            <a href="${ctx}/book/book?bookcode=${book.bookcode}" target="_blank">${book.bookYear}</a>
                            <input type="hidden" value="${book.bookcode}" id="bookcode">
                        </c:forEach>
                    </div>
                </div>

            </div>
        </div>

        <!-- 切换 -->
        <div class="package newtab">
            <ul class="tab-hd-center">
                <li class="click01 active">目录对比阅读</li>
                <li class="click02"><a  style="color: #ffffff;">年鉴热词</a></li>
                <li class="click03"><a  style="color: #ffffff;">热门人物</a></li>
                <li class="click04">热点事件</li>
            </ul>
            <ul class="tab-bd-center">
                <li class="clickshow01 show">
                    <!-- 目录对比阅读 -->
                    <div class="readpj"><a onclick="mySpliceAdd()">拼接阅读</a></div>
                    <div class="fenxislides">
                        <div class="slides_container">
                            <c:forEach begin="0" var="i" end="${(listSize-1)/4}">
                                <!--出现两个div才会滚动-->
                                <div>
                                    <div class="fenxislideC">
                                        <dl>
                                            <c:forEach items="${list}" var="item1" begin="${i*4}" end="${i*4+3}">
                                                <dd>
                                                    <h3>${item1[0]}</h3>
                                                    <div class="slidebox">
                                                        <c:forEach items="${item1[1]}" var="item2" varStatus="status">
                                                            <a href="##" class="fenxitit" title="${item2[0].title}">${fn:substring(item2[0].title,0,8)}</a>
                                                            <div class="fenxidl">
                                                                <c:forEach items="${item2[1]}" var="secondCatalog" varStatus="loop">
                                                                    <p><input name="titleID" type="checkbox" value="${secondCatalog.zid}|${secondCatalog.title}|${secondCatalog.bookcode}">
                                                                            <%--<input name="titleName" type="checkbox" value="${secondCatalog.title}">--%>
                                                                        <span title="${secondCatalog.title}">${status.count}.${loop.count}-${fn:substring(secondCatalog.title,0,5)}</span>
                                                                    </p>
                                                                </c:forEach>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </dd>
                                            </c:forEach>
                                        </dl>
                                    </div>
                                </div>
                                <%--<!--出现两个div才会滚动-->--%>
                                <%--<div>--%>
                                    <%--<div class="fenxislideC">--%>
                                        <%--<dl>--%>
                                            <%--<c:forEach items="${list}" var="item1" begin="${i*4}" end="${i*4+3}">--%>
                                                <%--<dd>--%>
                                                    <%--<h3>${item1[0]}</h3>--%>
                                                    <%--<div class="slidebox">--%>
                                                        <%--<c:forEach items="${item1[1]}" var="item2" varStatus="status">--%>
                                                            <%--<a href="##" class="fenxitit" title="${item2[0].title}">${fn:substring(item2[0].title,0,8)}</a>--%>
                                                            <%--<div class="fenxidl">--%>
                                                                <%--<c:forEach items="${item2[1]}" var="secondCatalog" varStatus="loop">--%>
                                                                    <%--<p><input name="titleID" type="checkbox" value="${secondCatalog.zid}|${secondCatalog.title}">--%>
                                                                            <%--&lt;%&ndash;<input name="titleName" type="checkbox" value="${secondCatalog.title}">&ndash;%&gt;--%>
                                                                        <%--<span title="${secondCatalog.title}">${status.count}.${loop.count}-${fn:substring(secondCatalog.title,0,5)}</span>--%>
                                                                    <%--</p>--%>
                                                                <%--</c:forEach>--%>
                                                            <%--</div>--%>
                                                        <%--</c:forEach>--%>
                                                    <%--</div>--%>
                                                <%--</dd>--%>
                                            <%--</c:forEach>--%>
                                        <%--</dl>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </c:forEach>
                        </div>
                        <a href="javascript:void(0);" class="prev"><img src="${ctx}/static/client/img/fenxi_prev.png" alt="Arrow Prev"></a>
                        <a href="javascript:void(0);" class="next"><img src="${ctx}/static/client/img/fenxi_next.png" alt="Arrow Next"></a>
                    </div>
                </li>
                <li class="clickshow02">
                    <!-- 年鉴热词 -->
                    <div class="changeicon" style="display: none;"><a href="#"><img src="${ctx}/static/client/img/icon_1.png" alt=""></a><a href="#"><img src="${ctx}/static/client/img/icon_2.png" alt=""></a></div>
                    <div class="graph">
                        <div class="fenxilist">
                            <dl id="yearnjrc">
                                <c:forEach items="${bookList}" var="book"  varStatus="status">
                                    <c:if test="${status.first}">
                                        <dd  class="on" value="${book.bookcode}">${book.bookYear}</dd>
                                    </c:if>
                                    <c:if test="${!status.first}">
                                        <dd class="" value="${book.bookcode}">${book.bookYear}</dd>
                                    </c:if>
                                </c:forEach>
                            </dl>
                        </div>
                        <div class="fenxiimg">
                            <jsp:include page="hotword.jsp"></jsp:include>
                        </div>

                    </div>
                    <%--<jsp:include page="../include/hotWords.jsp"></jsp:include>--%>
                </li>
                <li class="clickshow03">
                    <!-- 热门人物 -->
                    <%--<div class="changeicon"><a href="#"><img src="${ctx}/static/client/img/icon_1.png" alt=""></a><a href="#"><img src="${ctx}/static/client/img/icon_2.png" alt=""></a></div>--%>
                    <div class="graph">
                        <div class="fenxilist">
                            <dl id="yearrmrw">
                                <c:forEach items="${bookList}" var="book" varStatus="status">
                                    <c:if test="${status.first}">
                                        <dd class="on" value="${book.bookcode}">${book.bookYear}</dd>
                                    </c:if>
                                    <c:if test="${!status.first}">
                                        <dd value="${book.bookcode}">${book.bookYear}</dd>
                                    </c:if>
                                </c:forEach>
                            </dl>
                        </div>
                        <div class="fenxiimg">
                            <jsp:include page="hotperson.jsp"></jsp:include>
                        </div>
                    </div>
                </li>
                <li class="clickshow04">
                    <!-- 热点事件 -->
                    <div class="h_pro_pic_con">
                        <div class="fl"><a href="javascript:" class="h_control" id="rt_pre"><img src="${ctx}/static/client/img/h_prev.png" height="16" width="9"></a>
                        </div>
                        <div class="fm h_pro_pic_outer">
                            <div id="rtList">
                                <ul>
                                    <c:forEach items="${bookList}" var="book" varStatus="status">
                                        <li>
                                            <a class="active"  id="booklist${book.bookYear}" onclick="gethotevent('1',${book.bookYear},'${bookAbbreviate}')">${book.bookYear}</a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div class="fr"><a href="javascript:" class="h_control" id="rt_next"><img src="${ctx}/static/client/img/h_next.png" height="16" width="9"></a>
                        </div>
                    </div>
                    <script>
                            $("#rtList > ul > li > a").click(function () {
                                $("#rtList > ul > li > a").css({"color":"#ca0000","font-size":"14px"});
                                $(this).css({"color":"blue","font-size":"20px"});
                            })

                    </script>
                    <div id = "hotevents">

                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!-- 底部 -->
<%@include file="/WEB-INF/jsp/client/include/publicFoot.jsp" %>
<script src="${ctx}/static/client/customer/js/hovertreescroll.js"></script>
<input type="hidden" id="maxbookcode" value="${maxbookcode}"/>
<script>
    $(function () {
        gethotevent(1,'${book.bookYear}','${bookAbbreviate}');

    })
</script>
</body>
</html>
