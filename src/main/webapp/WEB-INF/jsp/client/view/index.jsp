<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<%
    String serviceRoot=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    request.setAttribute("path",serviceRoot);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>中国社会科学年鉴数据库</title>
    <!-- 共用 js -->
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/customer/publicJS.js"></script>
    <!-- 图片切换 插件 -->
    <link href="${ctx}/static/client/css/flexslider.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/customer/search.js"></script>
    <script type="text/javascript" src="${ctx}/static/remark/global/vendor/bootstrap/bootstrap.min.js"></script>
    <!-- 共用css -->
    <link href="${ctx}/static/client/css/guide.css" rel="stylesheet" type="text/css"/>
    <!-- 0925新增css -->
    <link href="${ctx}/static/client/css/guide0925.css" rel="stylesheet" type="text/css" />
    <!-- 0925新增js -->
    <script type="text/javascript" src="${ctx}/static/client/js/common0925.js"></script>
    <!-- 图片切换 插件 -->
    <%--<script type="text/javascript">--%>
    <%--function search(){--%>
    <%--window.location.href='${view}/searchresult?w=' + $("#scontent").val();--%>
    <%--}--%>
    <%--</script>--%>
    <style>
        /*a{*/
            /*color: #3d3d3d;*/
        /*}*/
        body{font-size:14px;font-family:Microsoft YaHei,Simsum,serif;margin:0;color:#6a6a6a}
        .footer{width:100%; text-align:center; padding:20px 0;background:#313131; font-size:12px; }
        .footer ul{ display:inline-block}
        .footer ul li{ padding:0 5px; display:inline}
        .footer ul li a{}
        .footer p{color:#8b8b8b;font-size:14px;text-align:center;margin-bottom:12px}
        .footer span{ display:inline-block; padding:0 50px;}
        .footer{ clear:both; }
        .loginbg .footer a{ color:#3e3e3e }
        .wrapin{ position:relative; z-index:10; overflow:visible}
        a {
            text-decoration: none;
            cursor: pointer;
            color: #878787;
        }
    </style>
</head>
<body>
<div class="wrap">
    <div class="head">
        <ul class="header_list">
            <c:if test="${userName == null}">
                <li><div style="float: left;margin-top: 2px;">欢迎来到社会科学年鉴数据库</div><a href="javascript:void(0)" onclick=" window.location.href = encodeURI('${ctx}/login?SKT='+window.location.pathname+window.location.search+'')" class="logout">请登录</a></li>
            </c:if>
            <c:if test="${userName != null}">
                <li><span style="line-height: 20px">${userName}</span><a href="javascript:void(0)" onclick="window.location.href= encodeURI('${ctx}/logout?SKT='+window.location.pathname+window.location.search+'')" class="logout">[退出]</a></li>
                |
                <li><a href="${ctx}/user/center" target="_blank">个人中心</a></li>
            </c:if>
        </ul>
    </div>
    <div class="seacrhbox">
        <div class="logo"><img src="${ctx}/static/client/img/guidelogo.png"/></div>
        <div class="seacrh">
            <form action="${pageContext.request.contextPath}/search" id="search" method="get" onsubmit="return check()" target="_blank">

                <div class="seacrh">
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

                <%--<div class="seach-chose">--%>
                        <%--<strong>文章</strong>--%>
                        <%--<dl>--%>
                            <%--<dd onclick="setButtonValue('wz')" class="searchclick01">文章</dd>--%>
                            <%--<dd onclick="setButtonValue('nj')" class="searchclick02">年鉴</dd>--%>
                            <%--<dd onclick="setButtonValue('ct')" class="searchclick03">词条</dd>--%>
                        <%--</dl>--%>
                    <%--</div>--%>
                    <input id="code" name="tp" type="hidden" value="nj">
                    <input id="cttype" name="cttype" type="hidden">
                    <div style="padding-left: 5px;"><input type="text" class="btn_text" name="kw" id="kw" placeholder="请输入关键字" ></div>
                    <input type="button" class="btn_sub" onclick="submitsearch()" >
                </div>
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
                                                        <span><a href="${ctx}/book/book?bookcode=${booksByHotSmall.bookcode}" target="_blank">
                                                            <img src="${ctx}${booksByHotSmall.cover}"/></a></span>
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
            <span class="indexclose"><img src="${ctx}/static/client/img/indexclose.png"/></span>
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

    <!-- 底部 -->
    <div class="footer" style="margin:0; position:absolute; bottom:1px;">
        <div class="wrapin" >

            <span>Chinese Social Sciences Yearbook database</span>
            <ul>
                <li><a href="http://www.csspw.com.cn/jianjie/index.jhtml" target="_blank">关于我们</a></li>|
                <li><a href="http://www.csspw.com.cn/other_15914_42320.jhtml" target="_blank">友情链接</a></li>|
                <li class="li_last"><a href="http://www.csspw.com.cn/other_15914_42321.jhtml" target="_blank">法律声明</a></li>
            </ul>
            <span>版权所有：中国社会科学出版社</span>
            <span>地址：北京西城区鼓楼西大街甲158号</span>
        </div>
    </div>
</div>
<script id="_trs_ta_js" src="//ta.trs.cn/c/js/ta.js?mpid=1931" async="async" defer="defer"></script>
</body>
<script>

</script>
</html>
