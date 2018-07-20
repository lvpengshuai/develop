<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<%
    String serviceRoot=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    request.setAttribute("path",serviceRoot);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>中国社会科学年鉴数据库</title>
    <!-- 图片切换 插件 -->
    <link href="${ctx}/static/client/css/flexslider.css" type="text/css" rel="stylesheet"/>
    <!-- 0925新增js -->
    <!-- 共用 js -->
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/customer/search.js"></script>
    <link href="${ctx}/static/client/css/wrapslide.css" rel="stylesheet">
    <script src="${ctx}/static/client/js/wrapslide.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
    <link rel="stylesheet" href="${ctx}/static/client/css/alertify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/alertify.js"></script>
    <style type="text/css">
        a:hover{ color:#d31215;text-decoration:none;}
    </style>
    <script>
        //var loginFlag = false;
        //var loginHelper = null;
        $(document).ready(function(){
            $('.carousel').carousel(
                {
                    "pause":"false",
                    "interval":2000000
                }
            );

            $('.carousel').on('slide.bs.carousel', function (event) {
                var tag = $(event.relatedTarget).attr("tag");
                var progress = $(event.relatedTarget).attr("progress");

                $('#img_home').attr("src", "${ctx}/static/client/img/home.png");
                $('#img_intro').attr("src", "${ctx}/static/client/img/intro.png");
                $('#img_gift').attr("src", "${ctx}/static/client/img/gift.png");
                $('#img_feedback').attr("src", "${ctx}/static/client/img/feedback.png");
                $('#img_' + tag).attr("src", "${ctx}/static/client/img/" + tag + "_selected.png");
                $("#progress").animate({width: progress, left: (progress / 2) - 400}, 500);
                playAnimation(tag);
            });

            $("body,html").animate({
                scrollTop:($("#lanrenzhijia_home .splash-container").offset().top)
            },10);

            playAnimation("home");

        });

        function switchlanrenzhijia(lanrenzhijiaIndex){
            $('.carousel').carousel(lanrenzhijiaIndex);
        }

        function playAnimation(tag){
            var ani1 = $("#lanrenzhijia_" + tag + " .ani_step_1");
            var ani2 = $("#lanrenzhijia_" + tag + " .ani_step_2");
            var ani3 = $("#lanrenzhijia_" + tag + " .ani_step_3");
            var pre_start = ((tag == "home") ? 0 : 300);
            if(ani1 != null){
                ani1.css("opacity", 0);
                setTimeout(function(){
                    ani1.animate({opacity: 1}, 1000);
                }, 10 + pre_start);
            }
            if(ani2 != null){
                ani2.css("opacity", 0);
                setTimeout(function(){
                    ani2.animate({opacity: 1}, 1000);
                }, 1500 + pre_start);
            }
            if(ani3 != null){
                ani3.css("opacity", 0);
                setTimeout(function(){
                    $("#lanrenzhijia_" + tag + " .ani_step_3").animate({opacity: 1}, 1000);
                }, 3000 + pre_start);
            }
        }
    </script>
</head>
<body>
<div class="head" >
    <ul class="header_list">
        <c:if test="${ONLINESTATUS == 'ON'}">
            <c:if test="${userName == null}">
                <c:if test="${loginSign == 1 }">
                    <li>${org}，欢迎您！</li>
                    <li><a style="font-size:14px;   font-weight: bold;" href="javascript:void(0)" onclick=" window.location.href = encodeURI('${ctx}/login?SKT='+window.location.pathname+window.location.search+'')" >【登录】</a><a style="font-size:14px;   font-weight: bold;" href="javascript:void(0)" onclick=" window.location.href = encodeURI('${ctx}/onlineRegister')">【注册】</a></li>
                </c:if>
                <c:if test="${loginSign == 2}">
                    <li>欢迎来到社会科学年鉴数据库</li>
                    <li><a style="font-size:14px;   font-weight: bold;" href="javascript:void(0)" onclick=" window.location.href = encodeURI('${ctx}/login?SKT='+window.location.pathname+window.location.search+'')" >【登录】</a><a style="font-size:14px;   font-weight: bold;" href="javascript:void(0)" onclick=" window.location.href = encodeURI('${ctx}/onlineRegister')">【注册】</a></li>
                </c:if>
            </c:if>

            <c:if test="${userName != null}">
                <c:if test="${loginSign == 3}">
                    <li>${org}，${userName}，欢迎您！<a style="font-size:14px;   font-weight: bold;" href="javascript:void(0)" onclick="window.location.href= encodeURI('${ctx}/logout?SKT='+window.location.pathname+window.location.search+'')" >【退出】</a></li>
                    |
                    <li><a href="${ctx}/user/center" target="_blank">个人中心</a></li>
                </c:if>
                <c:if test="${loginSign == 4}">
                    <li>${userName}，欢迎您！<a style="font-size:14px;   font-weight: bold;" href="javascript:void(0)" onclick="window.location.href= encodeURI('${ctx}/logout?SKT='+window.location.pathname+window.location.search+'')" >【退出】</a></li>
                    |
                    <li><a href="${ctx}/user/center" target="_blank">个人中心</a></li>
                </c:if>
            </c:if>
        </c:if>



        <c:if test="${ONLINESTATUS == 'OFF'}">
            <c:if test="${userName == null}">
                <li>欢迎来到社会科学年鉴数据库</li>
                <li><a style="font-size:14px;   font-weight: bold;" href="javascript:void(0)" onclick=" window.location.href = encodeURI('${ctx}/login?SKT='+window.location.pathname+window.location.search+'')" >【登录】</a><a style="font-size:14px;   font-weight: bold;" href="javascript:void(0)" onclick=" window.location.href = encodeURI('${ctx}/onlineRegister')">【注册】</a></li>
            </c:if>
            <c:if test="${userName != null}">
                <li>${userName}<a style="font-size:14px;   font-weight: bold;" href="javascript:void(0)" onclick="window.location.href= encodeURI('${ctx}/logout?SKT='+window.location.pathname+window.location.search+'')" >[退出]</a></li>
                |
                <li><a href="${ctx}/user/center" target="_blank">个人中心</a></li>
            </c:if>
        </c:if>
    </ul>
</div>
<div class="wrap" style="z-index: 0">
    <div id="carousel_container" class="carousel slide full_height">
        <div class="carousel-inner full_height">
                <%--<!-- 首页 -->--%>
                <div id="lanrenzhijia_home" class="lanrenzhijia item active" tag="home" progress="150">
                <div id="main">
                    <div id="splash">
                        <div class="container splash-container">
                            <div class="seacrhbox">
                                <div class="logo"><img src="${ctx}/static/client/img/guidelogo.png"/></div>
                                <div class="seacrh">
                                    <form action="${pageContext.request.contextPath}/search" id="search" method="get" onsubmit="return check()" target="_blank">
                                            <div class="seach-cate">
                                                <dl>
                                                    <dd  value="nj" class="active"  onclick="setButtonValue('nj')">年鉴</dd>
                                                    <dd  value="wz" onclick="setButtonValue('wz')">章节</dd>
                                                    <dd value="rw" onclick="setButtonValue('rw')">人物</dd>
                                                    <dd value="lw" onclick="setButtonValue('lw')">论文</dd>
                                                    <dd value="kt" onclick="setButtonValue('kt')">课题</dd>
                                                    <dd value="hy" onclick="setButtonValue('hy')">会议</dd>
                                                    <dd value="ts" onclick="setButtonValue('ts')">图书</dd>
                                                    <dd value="dsj" onclick="setButtonValue('dsj')">大事记</dd>
                                                </dl>
                                            </div>
                                            <input id="code" name="tp" type="hidden" value="nj">
                                            <input id="cttype" name="cttype" type="hidden">
                                            <input type="text" class="btn_text" name="kw" id="kw" placeholder="请输入关键字" >
                                            <input type="button" class="btn_sub" onclick="submitsearch()"  value="搜索">
                                    </form>
                                </div>
                                <c:if test="${!empty split}">
                                    <div class="searchtext">
                                        <span>热门搜索：</span>
                                        <c:forEach items="${split}" var="s" varStatus="i" begin="0" end="${split.size()}">
                                            <c:if test="${i.index+1==split.size()}">
                                                <a href="/search?kw=${s.key}" target="_blank">${s.value}</a>
                                            </c:if>
                                            <c:if test="${i.index+1!=split.size()}">
                                                <a href="/search?kw=${s.key}" target="_blank">${s.value}</a>|
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                            <!-- 热门年鉴 -->
                            <div class="course-slide">
                                <div class="colum1">
                                    <div class="base-box">
                                        <div class="course-slide">
                                            <div class="flexslider">
                                                <ul class="slides">
                                                    <c:forEach items="${booksByHotBigs}" var="booksByHotBig" varStatus="i">
                                                        <li>
                                                            <div class="guidelist">
                                                                <dl>
                                                                    <dd>
                                                                        <span><a href="${ctx}/book/book?bookcode=${booksByHotBig.bookcode}" target="_blank"><img src="${booksByHotBig.cover}"/></a></span>
                                                                        <h5 ><a href="${ctx}/book/book?bookcode=${booksByHotBig.bookcode}" target="_blank">${booksByHotBig.title}</a></h5>
                                                                    </dd>
                                                                    <c:forEach items="${booksByHotSmalls}" var="booksByHotSmall" begin="${i.index*3}" end="${i.index*3+2}">
                                                                        <dd>
                                                                            <span>
                                                                                <a href="${ctx}/book/book?bookcode=${booksByHotSmall.bookcode}" target="_blank">
                                                                                <img src="${ctx}${booksByHotSmall.cover}"/>
                                                                                </a>
                                                                            </span>
                                                                            <h5 title="${booksByHotSmall.title}"><a href="${ctx}/book/book?bookcode=${booksByHotSmall.bookcode}" target="_blank">${booksByHotSmall.title}</a></h5>
                                                                        </dd>
                                                                    </c:forEach>
                                                                </dl>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 弹出图片切换 -->
                                    <div class="indexlayer">
                                    <div class="layer-slide">
                                        <%--<span class="indexclose"><img src="${ctx}/static/client/img/indexclose.png"/></span>--%>
                                        <div class="flexslider">
                                            <ul class="slides">
                                                <li>
                                                    <dl class="layerdl">
                                                        <c:forEach items="${books1}" var="book1">
                                                            <dd>
                                                                <span><a href="${ctx}/book/book?bookcode=${book1.bookcode}" target="_blank"><img src="${ctx}${book1.cover}"/></a></span>
                                                                <p>${book1.title}</p>
                                                            </dd>
                                                        </c:forEach>
                                                    </dl>
                                                </li>
                                                <li>
                                                    <dl class="layerdl">
                                                        <c:forEach items="${books2}" var="book2">
                                                            <dd>
                                                                <span><a href="${ctx}/book/book?bookcode=${book2.bookcode}" target="_blank"><img src="${ctx}${book2.cover}"/></a></span>
                                                                <p>${book2.title}</p>
                                                            </dd>
                                                        </c:forEach>
                                                    </dl>
                                                </li>
                                                <li>
                                                    <dl class="layerdl">
                                                        <c:forEach items="${books4}" var="book4">
                                                            <dd>
                                                                <span><a href="${ctx}/book/book?bookcode=${book4.bookcode}" target="_blank"><img src="${ctx}${book4.cover}"/></a></span>
                                                                <p>${book4.title}</p>
                                                            </dd>
                                                        </c:forEach>
                                                    </dl>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                <!-- 年鉴类别 -->
                <div id="lanrenzhijia_intro" class="lanrenzhijia item" tag="intro" progress="300">
                        <div>
                            <div>
                                <div class="container splash-container">
                                    <div class="logo"><img src="${ctx}/static/client/img/guidelogo.png" /></div>
                                    <div class="layer-slide">
                                        <div class="flexslider">
                                            <ul class="slides">
                                                <c:forEach items="${bookClassifies}" var="bookClassifies" varStatus="i">
                                                    <c:choose>
                                                        <c:when test="${i.first || i.index % 10 ==0}">
                                                            <li>
                                                                <dl class="layerdl">
                                                                    <dd>
                                                                        <span><a href="${ctx}/book/book?bookabbreviate=${bookClassifies.bookabbreviate}" target="_blank"><img src="${ctx}${bookClassifies.image}" /></a><b>共<c:choose><c:when test="${empty bookClassifies.counts}">0</c:when><c:otherwise>${bookClassifies.counts}</c:otherwise></c:choose>卷</b></span>
                                                                    </dd>
                                                        </c:when>
                                                        <c:when test="${i.last || (i.index+1) % 10 ==0}">
                                                                    <dd>
                                                                        <span><a href="${ctx}/book/book?bookabbreviate=${bookClassifies.bookabbreviate}" target="_blank"><img src="${ctx}${bookClassifies.image}" /></a><b>共<c:choose><c:when test="${empty bookClassifies.counts}">0</c:when><c:otherwise>${bookClassifies.counts}</c:otherwise></c:choose>卷</b></span>
                                                                    </dd>
                                                                </dl>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <dd>
                                                                <span><a href="${ctx}/book/book?bookabbreviate=${bookClassifies.bookabbreviate}" target="_blank"><img src="${ctx}${bookClassifies.image}" /></a><b>共<c:choose><c:when test="${empty bookClassifies.counts}">0</c:when><c:otherwise>${bookClassifies.counts}</c:otherwise></c:choose>卷</b></span>
                                                            </dd>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                <!-- 词条 -->
                <div id="lanrenzhijia_gift" class="lanrenzhijia item" tag="gift" progress="450">
                        <div>
                            <div>
                                <div class="container splash-container">
                                    <div class="logo"><img src="${ctx}/static/client/img/guidelogo.png" /></div>
                                    <div class="citiao">
                                        <dl>
                                            <dd>
                                                <a href="${ctx}/search?tp=wz&nu=sy" target="_blank">
                                                    <span><img src="${ctx}/static/client/img/citiao1.jpg" /></span>
                                                    <p><b>${wzCount}+</b>篇</p>
                                                </a>
                                            </dd>
                                            <dd>
                                                <a href="${ctx}/search?tp=kt&nu=sy" target="_blank">
                                                    <span><img src="${ctx}/static/client/img/citiao2.jpg" /></span>
                                                    <p><b>${ktCount}+</b>个</p>
                                                </a>
                                            </dd>
                                            <dd>
                                                <a href="${ctx}/search?tp=dsj&nu=sy" target="_blank">
                                                    <span><img src="${ctx}/static/client/img/citiao3.jpg" /></span>
                                                    <p><b>${dsjCount}+</b>件</p>
                                                </a>
                                            </dd>
                                            <dd>
                                                <a href="${ctx}/search?tp=ts&nu=sy" target="_blank">
                                                    <span><img src="${ctx}/static/client/img/citiao4.jpg" /></span>
                                                    <p><b>${tsCount}+</b>本</p>
                                                </a>
                                            </dd>
                                            <dd>
                                                <a href="${ctx}/search?tp=rw&nu=sy" target="_blank">
                                                    <span><img src="${ctx}/static/client/img/citiao5.jpg" /></span>
                                                    <p><b>${rwCount}+</b>位</p>
                                                </a>
                                            </dd>
                                            <dd>
                                                <a href="${ctx}/search?tp=lw&nu=sy" target="_blank">
                                                    <span><img src="${ctx}/static/client/img/citiao6.jpg" /></span>
                                                    <p><b>${lwCount}+</b>篇</p>
                                                </a>
                                            </dd>
                                            <dd>
                                                <a href="#" style="cursor:default;">
                                                    <span><img src="${ctx}/static/client/img/citiao7.jpg" /></span>
                                                    <p><b>${jgCount}+</b>所</p>
                                                </a>
                                            </dd>
                                            <dd>
                                                <a href="${ctx}/search?tp=hy&nu=sy" target="_blank">
                                                    <span><img src="${ctx}/static/client/img/citiao8.jpg" /></span>
                                                    <p><b>${hyCount}+</b>次</p>
                                                </a>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 购买 -->
                    <div id="lanrenzhijia_feedback" class="lanrenzhijia item" tag="feedback" progress="450">
                        <div id="main">
                            <div id="splash">
                                <div class="container splash-container">
                                    <div class="logo"><img src="${ctx}/static/client/img/guidelogo.png" /></div>
                                    <div class="buyimg"><img src="${ctx}/static/client/img//buyimg.png" /></div>
                                </div>
                            </div>
                        </div>
                    </div>


        </div>
    </div>

</div>
<div class="footer" >
    <div id="navbar">
        <div id="dash" ></div>
        <div id="progress"></div>
        <div id="navbtns" class="container" >
            <div class="navbtn">
                <a href="javascript:switchlanrenzhijia(0)">
                    <img id="img_home" src="${ctx}/static/client/img/home_selected.png" alt="首页">
                </a>
            </div>
            <div class="navbtn">
                <a href="javascript:switchlanrenzhijia(1)" class="intro">
                    <img id="img_intro" src="${ctx}/static/client/img/intro.png" alt="年鉴">
                </a>
            </div>

            <div class="navbtn">
                <a href="javascript:switchlanrenzhijia(2)">
                    <img id="img_gift" src="${ctx}/static/client/img/gift.png" alt="词条">
                </a>
            </div>
            <div class="navbtn">
                <a href="javascript:switchlanrenzhijia(3)"><!-- ${ctx}/index_buy -->
                    <img id="img_feedback" src="${ctx}/static/client/img/feedback.png" alt="购买">
                </a>
            </div>
        </div>
    </div>
</div>
<!-- 底部 -->
<div class="foot">
        <span>Chinese Social Sciences Yearbook database</span>
        <span>
            <a href="http://www.csspw.com.cn/jianjie/index.jhtml" target="_blank">关于我们</a>|
            <a href="http://www.csspw.com.cn/other_15914_42320.jhtml" target="_blank">友情链接</a>|
            <a href="http://www.csspw.com.cn/other_15914_42321.jhtml" target="_blank">法律声明</a>
        </span>
        <span>版权所有：中国社会科学出版社</span>
        <span>备案号：京ICP备05032912号-3</span>
        <span>地址：北京西城区鼓楼西大街甲158号</span>
</div>
<script id="_trs_ta_js" src="//ta.trs.cn/c/js/ta.js?mpid=1931" async="async" defer="defer"></script>
</body>
<script type="text/javascript">
    $(function(){


        //首页弹出图片切换
        if($(".layer-slide").length != 0){

            $(".layer-slide .flexslider").flexslider({
                animation : "slide"
            });
        }

        //首页图片切换
        if($(".course-slide").length != 0){

            $(".course-slide .flexslider").flexslider({
                animation : "slide"
            });
        }

        $(".intro").click(function(){
            $(".layer-slide .flexslider .slides > li").animate({width: '760px', opacity: '1'}, 500);
        });

        //词条分类选择
        $(".seach-cate dd").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });

    })

</script>

</html>
