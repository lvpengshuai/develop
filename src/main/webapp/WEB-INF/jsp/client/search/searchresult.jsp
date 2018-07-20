﻿
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <title>中国社会科学年鉴数据库</title>
    <!-- 共用css -->

    <link rel="stylesheet" href="${ctx}/static/client/css/style.css"/>
    <link rel="stylesheet" href="${ctx}/static/client/css/alertify.css"/>
    <!-- 共用 js -->
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>
    <link rel="stylesheet" href="${ctx}/static/client/css/normalize.css"/>
    <script src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
    <script src="${ctx}/static/client/js/demo.js"></script>


    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/client/js/ECharts/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/customer/publicJS.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/alertify.js"></script>

    <script>
        var appPath = '${pageContext.request.contextPath}';
        var username = '${userName}';
        var kwd='${keyWord}';
            $.ajax({
                url: "http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=${keyWord}&bk_length=600",
                type: "get",
                dataType: "jsonp",
                success: function (data) {
//                alert(data);
                    if(kwd == '王伟光'){
                        var bkv="王伟光，男，汉族，1950年2月生，山东乳山人，1967年11月参加工作，1972年11月加入中国共产党，博士研究生，教授，博士生导师。现任十八届中央委员，中国社会科学院院长、党组书记，中国社会科学院大学校长。";
                        $("#baikeName").html('王伟光');
                        $("#baikeValue").text(bkv);
                        $("#baikehref").attr("href", "https://baike.baidu.com/item/${keyWord}/9829683");
                        $("#baike").show();
                    }if(kwd == '赵剑英'){
                        var zjy="赵剑英，现任中国社会科学出版社社长兼总编辑，中共党员，哲学博士，编审。中国社会科学院研究生院哲学系教授，博士生导师。主要研究领域为马克思主义哲学、中国特色社会主义理论研究。在《中国社会科学》、《哲学研究》、《马克思主义研究》、《人民日报》、《光明日报》等报刊上发表论文和文章近百篇，代表作有：《试析实践活动运行机制》、《人文学科的地位和作用》、《论人类实践形态的当代发展》、《建构中国化马克思主义哲学新形态的再思考》、《深刻变化的世界与当代马克思主义哲学的使命》、《坚持生产力发展和人的自由全面发展的统一——论科学发展观的哲学蕴涵》、《现代性与近代以来中国人的文化认同危机及重建》等。著有和主编多部著作，代表作有：《哲学的力量》、《复兴中国》、《论中国模式》等。";
                        $("#baikeName").text('赵剑英');
                        $("#baikeValue").text(zjy);
                        $("#baikehref").attr("href", "https://baike.baidu.com/item/${keyWord}/3173880");
                        $("#baike").show();
                    }else if(kwd != '王伟光' && kwd != '赵剑英'){
                        if (data == null || data == undefined || data["abstract"] == null) {
                            $("#baike").hide();
                        } else {
                            $("#baikeName").html(data["title"]);
                            $("#baikeValue").html(data["abstract"]);
                            $("#baike").show();
                        }
                    }

                }
            });

    </script>
    <script language="javascript" type="text/javascript">
        function showdiv() {
            document.getElementById("bg").style.display = "block";
            document.getElementById("show").style.display = "block";
            //document
        }

        function hidediv() {
            document.getElementById("bg").style.display = 'none';
            document.getElementById("show").style.display = 'none';
        }
    </script>
    <style type="text/css">
        #bg {
            display: none;
            position: absolute;
            top: 0%;
            left: 0%;
            width: 100%;
            height: 3709px;
            background-color: black;
            z-index: 1001;
            -moz-opacity: 0.7;
            opacity: .70;
            filter: alpha(opacity=70);
            position: fixed;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            z-index: 100;
        }

        #show {
            display: none;
            position: absolute;
            top: 25%;
            left: 22%;
            width: 53%;
            height: 49%;
            padding: 8px;
            z-index: 1002;
            overflow: auto;
            font-size: 55px;
            left: 790px;
            top: 400px;
        }
    </style>
</head>
<body>

<!--顶部-->
<%@include file="/WEB-INF/jsp/client/include/publicTop.jsp" %>
<!--精品书籍推荐-->
<div class="wrapin">

    <!-- 左边 -->
    <div class="colum_left" style="margin:1px 0">

        <div class="searchtj">
            <h5>全部结果：</h5>
            <c:if test="${!empty bookname}">
                <a href="javascript:void(0)">年鉴名称：${bookname}<em onclick="searchGroup('bookname','')">x</em></a>
            </c:if>
            <c:if test="${ pubdate != '1' && ! empty pubdate && pubdate!='-1'}">
                <a href="javascript:void(0)">出版时间: ${pubdate}<em onclick="searchGroup('pubdate','')">x</em></a>
            </c:if>
            <c:if test="${!empty organ}">
                <a href="javascript:void(0)">机构: ${organ}<em onclick="searchGroup('organ','')">x</em></a>
            </c:if>
            <c:if test="${!empty author}">
                <a href="javascript:void(0)">人物: ${author}<em onclick="searchGroup('author','')">x</em></a>
            </c:if>
            <c:if test="${!empty source}">
                <a href="javascript:void(0)">作者: ${source}<em onclick="searchGroup('source','')">x</em></a>
            </c:if>
            <c:if test="${!empty keyWord}">
                >"
                <c:if test="${fn:length(keyWord)>12 }">
                    <b href="javascript:void(0)" title="${keyWord}">${fn:substring(keyWord,0,12)}</b>...
                </c:if>
                <c:if test="${fn:length(keyWord)<=12 }">
                    <b href="javascript:void(0)">${keyWord}</b>
                </c:if>
                "
            </c:if>
            <c:if test="${empty keyWord}">
                >
            </c:if>

        </div>

        <div class="morebox newmorebox">
            <c:if test="${! empty bnamemaps or  ! empty  yearmaps or ! empty  personmaps  or ! empty  organmaps }">
                <div class="moreclick"><em>更多选项</em><i>收起选项</i></div>
            </c:if>
            <div class="moreinfo">
                <h5>内容分类：</h5>
                <a class="search_click01" id="nj" onclick="searchType('nj')">年鉴</a>
                <a class="search_click02" id="wz" onclick="searchType('wz')">章节</a>
                <a class="search_click03" id="rw" onclick="searchType('rw','人物')">人物</a>
                <a class="search_click04" id="lw" onclick="searchType('lw','论文')">论文</a>
                <a class="search_click05" id="kt" onclick="searchType('kt','课题')">课题</a>
                <a class="search_click06" id="hy" onclick="searchType('hy','会议')">会议</a>
                <a class="search_click07" id="ts" onclick="searchType('ts','图书')">图书</a>
                <a class="search_click08" id="dsj" onclick="searchType('dsj','大事记')">大事记</a>
            </div>
            <c:if test="${type =='wz'}">
            <%--<div class="moreinfo">--%>
                <%--<h5>文章筛选：</h5>--%>
                <%--<a class="search_click10" id="wzTextContent"  onclick="searchType('wz')">全文</a>--%>
                <%--<a class="search_click09" id="wzTitle" onclick="searchType('wz','title')">标题</a>--%>
                <%--<a class="search_click11" id="wzAuthor" onclick="searchType('wz','author')">作者</a>--%>
                <%--<a class="search_click12" id="wzKeyword" onclick="searchType('wz','Keyword')">关键词</a>--%>
            <%--</div>--%>
            </c:if>

            <c:if test="${empty bookname}">
                <c:if test="${bnamemaps.size()>0}">
                    <div class="moreinfo moreinfofont bookname">
                        <h5>年鉴名称：</h5>
                        <c:if test="${bnamemaps.size()>4}">
                            <em>更多</em>
                        </c:if>
                        <c:forEach items="${bnamemaps}" var="sub">
                            <a href="javascript:void(0)"
                               onclick="searchGroup('bookname','${sub.key}')">${sub.key}（${sub.value}）</a>
                        </c:forEach>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${empty pubdate }">
                <c:if test="${yearmaps.size()>0}">
                    <div class="moreinfo moreinfofont pubdate">
                        <h5>出版时间：</h5>
                        <c:if test="${yearmaps.size()>7}">
                            <em>更多</em>
                        </c:if>
                        <c:forEach items="${yearmaps}" var="sub" end="10">
                            <a href="javascript:void(0)"
                               onclick="searchGroup('pubdate','${sub.key}')">${sub.key}（${sub.value}）</a>
                        </c:forEach>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${type=='wz'}">
                <c:if test="${empty organ}">
                    <c:if test="${organmaps.size()>0}">
                        <div class="moreinfo moreinfofont organ">
                            <h5>机构：</h5>
                            <c:if test="${organmaps.size()>6}">
                                <em>更多</em>
                            </c:if>
                            <c:forEach items="${organmaps}" var="sub">
                                <a href="javascript:void(0)"
                                   onclick="searchGroup('organ','${sub.key}')">${sub.key}（${sub.value}）</a>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:if>

                <c:if test="${empty author}">
                    <c:if test="${personmaps.size()>0}">
                        <div class="moreinfo moreinfofont author">
                            <h5>人物：</h5>
                            <c:if test="${personmaps.size()>6}">
                                <em>更多</em>
                            </c:if>
                            <c:forEach items="${personmaps}" var="sub">
                                <a href="javascript:void(0)"
                                   onclick="searchGroup('author','${sub.key}')">${sub.key}（${sub.value}）</a>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:if>

                <c:if test="${empty source}">
                    <c:if test="${authorss.size()>0}">
                        <div class="moreinfo moreinfofont source">
                            <h5>作者：</h5>
                            <c:if test="${authorss.size()>6}">
                                <em>更多</em>
                            </c:if>
                            <c:forEach items="${authorss}" var="sub">
                                <a href="javascript:void(0)"
                                   onclick="searchGroup('source','${sub.key}')">${sub.key}（${sub.value}）</a>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:if>

            </c:if>


        </div>

        <div class="seach-box new-seach-box">
            <!-- 条目 -->

            <c:if test="${type =='wz'}">
                <div class="search-results-links">
                    <c:if test="${bnamemaps.size()>0 or yearmaps.size()>0 or organmaps.size()>0 or personmaps.size()>0 or authorss.size()>0}">
                        <a href="javascript:void(0)" onclick="softType(this.id,'${re}')" id="re"><img
                                src="${ctx}/static/client/img/result1.png" alt="">相关度排序</a>
                        <a href="javascript:void(0)" onclick="softType(this.id,'${hot}')" id="hot"><img
                                src="${ctx}/static/client/img/result2.png" alt="">热度排序</a>
                        <a href="javascript:void(0)" onclick="softType(this.id,'${pubd}')" id="pubd"><img
                                src="${ctx}/static/client/img/result3.png" alt="">出版日期排序</a>
                        <a href="${ctx}/bookclass/1/${keyWord}/${trscount}"><img src="${ctx}/static/client/img/result4.png"
                                                                                 alt="">可视化分析</a>
                        <a href="${ctx}/resultDataExcel/${type}/<c:forEach items='${searchresult}' var='result'>${result.bookcode},</c:forEach>/<c:forEach items='${searchresult}' var='result'>${result.zid},</c:forEach>">
                            <img style="width: 15px;" src="${ctx}/static/client/img/result5.png" alt="">搜索结果下载</a>
                    </c:if>
                    为您找到相关章节约 <b>${trscount}</b> 个
                </div>
            </c:if>
            <c:if test="${type =='nj'}">
                <div class="search-results-links">
                    <a href="javascript:void(0)" onclick="softType(this.id,'${pubd}')" id="pubd"><img
                            src="${ctx}/static/client/img/result3.png" alt="">出版日期排序</a>
                    <a href="${ctx}/bookclass/1/${keyWord}/${trscount}"><img src="${ctx}/static/client/img/result4.png"
                                                                             alt="">可视化分析</a>
                    <%--<a href="${ctx}/resultDataExcel/${type}/<c:forEach items='${searchresult}' var='result'>${result.bookcode},</c:forEach>/null">--%>
                        <%--<img src="${ctx}/static/client/img/result4.png" alt="">搜索结果下载</a>--%>
                    为您找到相关年鉴约<b>${trscount}</b> 个
                </div>
            </c:if>


            <!-- 文章 -->
            <div class="searchbox01">
                <c:if test="${ctType=='wz'}">
                    <div class="favolists">
                        <dl>
                            <c:forEach items="${searchresult}" var="items">
                                <dd>
                                    <em class="click_pj">拼接阅读</em>
                                    <h3>
                                        <a href="/article/articleInfo?zid=${items.zid}&bookcode=${items.bookcode}"
                                           target="_blank">

                                            <b zid="${items.zid}" bookcode="${items.bookcode}"
                                               style="color: #0558ce">${items.title}</b>

                                        </a>
                                    </h3>
                                        <%--overflow: hidden;text-overflow: ellipsis;white-space: nowrap;display: block;margin-bottom: 20px   overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;--%>
                                    <p class="searchWz">${items.content}</p>
                                    <h4>来源：
                                        <c:forEach items="${items.listZT}" var="item" varStatus="status">
                                        <input type="hidden" id="bookcode" value="${items.bookcode}">
                                        <b>
                                            <c:choose>
                                            <c:when test="${status.first}">
                                            <a target="_blank" class="wzsource" href="/book/book?bookcode=${item.id}">
                                                    ${item.titleSource}
                                            </a>
                                            <c:if test="${!status.last}">\</c:if>
                                            </c:when>
                                            <c:when test="${!status.first}">
                                            <a class="wzsource"
                                               onclick="mulushow('${items.bookcode}','${item.id}','${item.titleSource}')">
                                                    ${item.titleSource}
                                            </a>
                                            <c:if test="${!status.last}">\</c:if>
                                            </c:when>
                                            </c:choose>
                                            <b>
                                                </c:forEach>
                                    </h4>
                                    <h5>关键词：
                                        <b>
                                            <c:if test="${fn:length(items.keyword)<=5 }">
                                                <c:forEach items="${items.keyword}" var="kw" varStatus="status">
                                                    <%--href='window.location.href ='/user/center/adminConcern'"--%>
                                                    <%--<a href="${ctx}/search?tp=wz&kw=${kw}" target="_blank">${kw}</a>--%>
                                                    <a href="javascript:window.open(encodeURI('${ctx}/search?tp=wz&kw=${kw}'));void(0);">${kw}</a>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${fn:length(items.keyword)>5 }">
                                                <c:forEach items="${items.keyword}" var="kw" varStatus="status"
                                                           begin="0" end="4" step="1">
                                                    <%--href='window.location.href ='/user/center/adminConcern'"--%>
                                                    <%--<a href="${ctx}/search?tp=wz&kw=${kw}" target="_blank">${kw}</a>--%>
                                                    <a href="javascript:window.open(encodeURI('${ctx}/search?tp=wz&kw=${kw}'));void(0);">${kw}</a>
                                                </c:forEach>
                                            </c:if>
                                        </b>
                                    </h5>
                                </dd>
                            </c:forEach>
                        </dl>
                    </div>
                    <jsp:include page="../include/page.jsp"></jsp:include>
                </c:if>
            </div>

            <!-- 年鉴 -->
            <div class="searchbox02">
                <c:if test="${ctType=='nj'}">
                    <div class="favolist resultfavolist favoliststyle">
                        <dl>
                            <c:forEach items="${searchresult}" var="sub">
                                <dd>
                                    <span><a href="/book/book?bookcode=${sub.bookcode}" target="_blank"><img
                                            src="${ctx}${sub.cover}" alt=""></a></span>
                                    <h3><a href="/book/book?bookcode=${sub.bookcode}" target="_blank"><b
                                            style="color: #0558ce">${sub.title}</b></a></h3>
                                    <h4>出版单位：${sub.publishername}</h4>
                                    <h4>作者：${sub.author}</h4>
                                    <p>
                                            ${sub.abs}
                                            <%--${fn:substring(sub.abs, 0, 200)}......作用--%>
                                    </p>
                                </dd>
                            </c:forEach>
                        </dl>
                    </div>
                    <jsp:include page="../include/page.jsp"></jsp:include>
                </c:if>
            </div>
            <c:if test="${ctType=='ct'}">
                <c:if test="${entry=='人物'}">
                    <!-- 人物 -->
                    <div class="search-results-links">
                        <%--<a href="${ctx}/resultDataExcel/${type}/<c:forEach items='${searchresult}' var='result'>${result.bookcode},</c:forEach>/<c:forEach items='${searchresult}' var='result'>${result.zid},</c:forEach>">--%>
                            <%--<img src="${ctx}/static/client/img/result4.png" alt="">搜索结果下载</a>--%>
                        为您找到相关人物约<b>${trscount}</b> 个
                    </div>
                    <div class="searchbox03">
                        <div class="renwu renwustyle">
                            <dl>
                                <c:forEach items="${searchresult}" var="sub">
                                    <dd>
                                        <span><img src="${ctx}/static/client/img/userphoto_1.jpg"></span>
                                        <h3>${sub.title}</h3>
                                        <c:if test="${fn:length(sub.source)>10}">
                                            <p style="width: 259px;position:relative; right:20px;"
                                               title="${sub.source}">${fn:substring(sub.source,0,10)}...</p>
                                        </c:if>
                                        <c:if test="${fn:length(sub.source)<=10}">
                                            <p style="width: 259px;position:relative; right:20px;">${sub.source}</p>
                                        </c:if>
                                        <a onclick="relatedProduct('${sub.hc}')">相关内容</a></dd>
                                </c:forEach>
                            </dl>
                        </div>
                    </div>
                </c:if>
                <!-- 论文 -->
                <c:if test="${entry=='论文'}">
                    <div class="search-results-links">
                        <%--<a href="${ctx}/resultDataExcel/${type}/<c:forEach items='${searchresult}' var='result'>${result.bookcode},</c:forEach>/<c:forEach items='${searchresult}' var='result'>${result.zid},</c:forEach>">--%>
                            <%--<img src="${ctx}/static/client/img/result4.png" alt="">搜索结果下载</a>--%>
                        为您找到相关论文约<b>${trscount}</b> 个
                    </div>
                    <div class="searchbox04">
                        <div class="booklist">
                            <dl>
                                <c:forEach items="${searchresult}" var="sub">
                                    <dd>
                                        <i></i>
                                        <h3><a style="overflow: hidden;cursor:text;"> ${sub.title}</a></h3>
                                        <h4>
                                            作者：
                                            <c:if test="${empty sub.people}">
                                                <b>暂无记载</b>
                                            </c:if>
                                            <c:if test="${not empty sub.people}">
                                                <c:set value="${ fn:split(sub.people, ';') }" var="peo"/>
                                                <c:forEach items="${ peo }" var="s">
                                                    <b>${ s }</b>
                                                </c:forEach>
                                            </c:if>
                                        </h4>
                                        <div class="booklist-b">
                                            <c:if test="${empty sub.source}">
                                                暂无相关记载
                                            </c:if>
                                            <c:if test="${not empty sub.source}">
                                                ${sub.source}
                                            </c:if>
                                        </div>
                                    </dd>
                                </c:forEach>
                            </dl>
                        </div>
                    </div>
                </c:if>
                <!-- 课题 -->
                <c:if test="${entry=='课题'}">
                    <div class="search-results-links">
                        <%--<a href="${ctx}/resultDataExcel/${type}/<c:forEach items='${searchresult}' var='result'>${result.bookcode},</c:forEach>/<c:forEach items='${searchresult}' var='result'>${result.zid},</c:forEach>">--%>
                            <%--<img src="${ctx}/static/client/img/result4.png" alt="">搜索结果下载</a>--%>
                        为您找到相关课题约<b>${trscount}</b> 个
                    </div>
                    <div class="searchbox05">
                        <div class="booklist">
                            <dl>
                                <c:forEach items="${searchresult}" var="sub">
                                    <dd>
                                        <i></i>
                                        <h3><a style="overflow: hidden;cursor:text;">${sub.title}</a></h3>
                                        <c:if test="${sub.exmap['项目区分']=='立项'}">
                                            <table class="ketiinfo" width="100%" border="0">
                                                <tr>
                                                    <td>编号：${sub.exmap["编号"]}</td>
                                                    <td>负责人：${sub.exmap["负责人"]}</td>
                                                        <%--<td>立项资助：${sub.exmap["立项资助"]}</td>--%>
                                                    <c:if test="${fn:length(sub.exmap['立项资助'])>15}">
                                                        <td title="${sub.exmap["立项资助"]}">
                                                            立项资助:${fn:substring(sub.exmap['立项资助'],0,15)}...
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${fn:length(sub.exmap['立项资助'])<=15 }">
                                                        <td title="${sub.exmap["立项资助"]}">立项资助:${sub.exmap['立项资助']}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>资助金额：${sub.exmap["资助金额"]}</td>
                                                    <td>职称：${sub.exmap["职称"]}  </td>
                                                    <td>机构：${sub.exmap["机构"]}</td>
                                                </tr>
                                            </table>
                                            <div class="booklist-b"><b>立结项区分：立项课题</b></div>
                                        </c:if>
                                        <c:if test="${sub.exmap['项目区分']=='结项'}">
                                            <table class="ketiinfo" width="100%" border="0">
                                                <tr>
                                                    <td>编号：${sub.exmap["编号"]}</td>
                                                    <td>负责人：${sub.exmap["负责人"]}</td>
                                                        <%--<td>立项资助：${sub.exmap["立项资助"]}</td>--%>
                                                    <c:if test="${fn:length(sub.exmap['立项资助'])>15}">
                                                        <td title="${sub.exmap["立项资助"]}">
                                                            立项资助:${fn:substring(sub.exmap['立项资助'],0,15)}...
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${fn:length(sub.exmap['立项资助'])<=15}">
                                                        <td title="${sub.exmap["立项资助"]}">立项资助:${sub.exmap['立项资助']}</td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>评级：${sub.exmap["评级"]}</td>
                                                    <td>职称：${sub.exmap["职称"]}  </td>
                                                        <%--<td>成果名称：--%>
                                                        <%--<c:if test="${!empty sub.exmap['成果名称']}">--%>
                                                        <%--${sub.exmap["成果名称"]}--%>
                                                        <%--</c:if>--%>
                                                        <%--</td>--%>
                                                    <c:if test="${fn:length(sub.exmap['成果名称'])>15}">
                                                        <td title="${sub.exmap['成果名称']}">成果名称:
                                                            <c:if test="${!empty sub.exmap['成果名称']}">
                                                                ${fn:substring(sub.exmap['成果名称'],0,15)}...
                                                            </c:if>
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${fn:length(sub.exmap['成果名称'])<=15}">
                                                        <td title="${sub.exmap['成果名称']}">成果名称:
                                                            <c:if test="${!empty sub.exmap['成果名称']}">
                                                                ${sub.exmap['成果名称']}
                                                            </c:if>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td>成果形式：${sub.exmap["成果形式"]}  </td>
                                                    <td>立项时间：${sub.exmap["立项时间"]}</td>
                                                    <td>单位：${sub.exmap["单位"]}</td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3">备注：${sub.exmap["备注"]}  </td>
                                                </tr>
                                            </table>
                                            <div class="booklist-b"><b>立结项区分：结项课题</b></div>
                                        </c:if>
                                    </dd>
                                </c:forEach>

                            </dl>
                        </div>
                    </div>
                </c:if>
                <!-- 会议 -->
                <c:if test="${entry=='会议'}">
                    <div class="search-results-links">
                        <%--<a href="${ctx}/resultDataExcel/${type}/<c:forEach items='${searchresult}' var='result'>${result.bookcode},</c:forEach>/<c:forEach items='${searchresult}' var='result'>${result.zid},</c:forEach>">--%>
                            <%--<img src="${ctx}/static/client/img/result4.png" alt="">搜索结果下载</a>--%>
                        为您找到相关会议约<b>${trscount}</b> 个
                    </div>
                    <div class="searchbox06 activitylist activityliststyle">
                        <c:forEach items="${searchresult}" var="item">
                            <dd>
                                        <span>
                                            <c:set value="${ fn:split(item.exdate, '.') }" var="str1"/>
                                            <b>${str1[0]}</b>
                                            <em>${str1[1]}-${str1[2]}</em>
                                            <strong style="padding:7px;font-size:14px;">${item.exarea==""?"其他":item.exarea}</strong>
                                        </span>
                                <h3><a style="cursor:text;">${item.title}</a></h3>
                                <div class="activeinfo">${item.hc}</div>
                                <a class="activemore"><b><img src="${ctx}/static/client/img/upmore.jpg" alt=""></b><img
                                        src="${ctx}/static/client/img/lookmore.jpg" alt=""></a>
                            </dd>
                        </c:forEach>
                        </dl>
                    </div>
                </c:if>
                <!-- 图书 -->
                <c:if test="${entry=='图书'}">
                    <div class="search-results-links">
                        <%--<a href="${ctx}/resultDataExcel/${type}/<c:forEach items='${searchresult}' var='result'>${result.bookcode},</c:forEach>/<c:forEach items='${searchresult}' var='result'>${result.zid},</c:forEach>">--%>
                            <%--<img src="${ctx}/static/client/img/result4.png" alt="">搜索结果下载</a>--%>
                        为您找到相关图书约<b>${trscount}</b> 个
                    </div>
                    <div class="searchbox07">
                        <div class="booklist">
                            <dl>
                                <c:forEach items="${searchresult}" var="sub">
                                    <%--<dd>--%>
                                    <%--<span>${sub.source}</span>--%>
                                    <%--<span>${sub.people}</span>--%>
                                    <%--<a style="width: 370px;overflow: hidden;cursor:text; color: #959595">${sub.title} </a>--%>
                                    <%--</dd>--%>
                                    <dd>
                                        <i></i>
                                        <h3><a style="overflow: hidden;cursor:text;"> ${sub.title}</a></h3>
                                        <h4>
                                            作者：
                                            <c:if test="${empty sub.people}">
                                                <b>暂无记载</b>
                                            </c:if>
                                            <c:if test="${not empty sub.people}">
                                                <c:set value="${ fn:split(sub.people, ';') }" var="peo"/>
                                                <c:forEach items="${ peo }" var="s">
                                                    <b>${ s }</b>
                                                </c:forEach>
                                            </c:if>
                                        </h4>
                                        <div class="booklist-b">
                                            <c:if test="${empty sub.source}">
                                                暂无相关记载
                                            </c:if>
                                            <c:if test="${not empty sub.source}">
                                                ${sub.source}
                                            </c:if>
                                        </div>
                                    </dd>

                                </c:forEach>
                            </dl>
                        </div>
                    </div>
                </c:if>
                <!-- 大事记 -->
                <c:if test="${entry=='大事记'}">
                    <div class="search-results-links">
                        <%--<a href="${ctx}/resultDataExcel/${type}/<c:forEach items='${searchresult}' var='result'>${result.bookcode},</c:forEach>/<c:forEach items='${searchresult}' var='result'>${result.zid},</c:forEach>">--%>
                            <%--<img src="${ctx}/static/client/img/result4.png" alt="">搜索结果下载</a>--%>
                        为您找到相关大事记约<b>${trscount}</b> 个
                    </div>
                    <%--<div class="searchbox08">--%>
                    <%--<div class="news">--%>
                    <%--<dl>--%>
                    <%--<c:forEach items="${searchresult}" var="result">--%>
                    <%--<dd>--%>
                    <%--<h3><span>${result.exdate}</span><a--%>
                    <%--style="overflow: hidden;cursor:text;">${result.title}</a></h3>--%>
                    <%--${result.hc}--%>
                    <%--</dd>--%>
                    <%--</c:forEach>--%>
                    <%--</dl>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <div class="searchbox08">
                        <dl class="activitylist activityliststyle">
                            <c:forEach items="${searchresult}" var="item">
                                <dd>
                                    <span style="height:64px;">
                                        <c:set value="${ fn:split(item.exdate, '.') }" var="str1"/>
                                        <b>${str1[0]}</b>
                                        <em></em>
                                        <strong style="margin-top:8px;font-size:12px;">${str1[1]}-${str1[2]}</strong>
                                    </span>
                                    <h3><a style="cursor:text;">${item.title}</a></h3>
                                    <div class="activeinfo">${item.hc}</div>
                                    <a class="activemore"><b><img src="${ctx}/static/client/img/upmore.jpg"
                                                                  alt=""></b><img
                                            src="${ctx}/static/client/img/lookmore.jpg" alt=""></a>
                                </dd>
                            </c:forEach>
                        </dl>
                    </div>
                </c:if>
                <jsp:include page="../include/page.jsp"></jsp:include>
                <script>
                    var entry = $("#entry").val();

                    //相关作品div显示
                    function relatedProduct(conent) {
                        if (conent == null || conent == "") {
                            alertify.alert("[暂无作品信息记录]");
                        } else {
                            var newPP = conent.replace(/<p>/g, "<dd>").replace(/<\/p>/g, "</dd>");
                            $(".layer-renwu .renwudetail dl").html(newPP);
                            $(".layer-renwu .renwudetail dl dd:odd").css("backgroundColor", "#e6e6e6");
                            $(".layer-renwu").show();
                        }
                    }

                    $(".newclose, .btn-cancle").click(function () {
                        $(".layer-favedelete, .layer-renwu").hide();
                    });
                </script>
            </c:if>
        </div>


    </div>


    <!-- 右边 -->
    <c:if test="${not empty keyWord}">
        <div class="colum_right colum_right_style">

            <div class="right-box" id="baike" style="display:none;">
                <div class="right-hd"><h3>百科词条</h3></div>
                <div class="right-bd">
                    <div class="baike">
                        <h3 id="baikeName"></h3>
                        <p id="baikeValue"></p>
                        <p><a id="baikehref" href="https://baike.baidu.com/item/${keyWord}" target="_blank">百度百科 ></a></p>
                    </div>
                </div>
            </div>

            <div class="right-box">
                <div class="right-hd">
                    <a href="javascript:void(0);" onclick="downloadEcharts(1)"><img
                            src="${ctx}/static/client/img/graphs_icon2.png" alt=""></a>
                        <%--<a href="javascript:void(0)"><img src="${ctx}/static/client/img/graphs_icon1.png" alt="">--%>
                    </a><h3>热点</h3>
                </div>
                <jsp:include page="../include/hotResearch.jsp"></jsp:include>
            </div>

            <c:if test="${ctType !='nj'}">
                <div class="right-box">
                        <%--cttype--%>
                    <div class="right-hd">
                        <a href="javascript:void(0);" onclick="downloadEcharts(2)"><img
                                src="${ctx}/static/client/img/graphs_icon2.png" alt=""></a>
                            <%--<a href="javascript:void(0)"><img src="${ctx}/static/client/img/graphs_icon1.png" alt=""></a>--%>
                        <h3>相关人物</h3>
                            <%--${relatedPeopleData}--%>
                    </div>
                    <jsp:include page="../include/relatedPeople.jsp"></jsp:include>

                </div>
            </c:if>
            <div class="right-box">
                <div class="right-hd">
                    <a href="javascript:void(0);" onclick="downloadEcharts(3)"><img
                            src="${ctx}/static/client/img/graphs_icon2.png" alt=""></a>
                    <!--<a href="javascript:void(0)"><img src="${ctx}/static/client/img/graphs_icon1.png" alt=""></a>-->
                    <h3>相关机构</h3>
                </div>
                <jsp:include page="../include/relatedInstitution.jsp"></jsp:include>
            </div>

        </div>
    </c:if>
    <c:if test="${empty keyWord}">
        <div class="colum_right">

            <div class="right-box">
                <div class="right-hd"><h3>热门年鉴</h3></div>
                <div class="right-bd">
                    <dl class="njhot">
                        <c:forEach items="${booksByHot}" var="book" varStatus="i">
                            <dd>
                                <span><em>${i.index+1}</em><a href="${ctx}/book/book?bookcode=${book.bookcode}" target="_blank"><img src="${book.cover}"
                                                                                                                                     alt=""></a></span>
                                <a href="${ctx}/book/book?bookcode=${book.bookcode}" target="_blank">
                                    <div style="height: 90px;font-size: 18px;">${book.title}</div>
                                </a>
                                <p>点击量：<b>${book.hot}</b></p>
                            </dd>
                        </c:forEach>
                    </dl>
                </div>
            </div>

        </div>
    </c:if>

</div>


<input type="hidden" id="keyWord" value="${keyWord}">
<input type="hidden" id="type" value="${type}">
<input type="hidden" id="bookname" value="${bookname}">
<input type="hidden" id="pubdate" value="${pubdate}">
<input type="hidden" id="author" value="${author}">
<input type="hidden" id="organ" value="${organ}">
<input type="hidden" id="source" value="${source}">
<input type="hidden" id="ifadv" value="${ifadv}">
<input type="hidden" id="searchSql" value="${searchSql}">
<input type="hidden" id="cttp" value="${cttp}">
<input type="hidden" id="clickRe" value="${clickRe}">



<!-- 弹出拼接阅读 -->
<div class="layer_pj">
    <div class="layer_pj_box">
        <span class="pj_close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="pinjietit">
            <a href="javascript:void(0)"><img src="${ctx}/static/client/img/pinjie_icon1.png" alt=""></a>
            <a href="javascript:void(0)"><img src="${ctx}/static/client/img/pinjie_icon2.png" alt=""></a>
            <h3>拼接阅读</h3>选中多个检索结果，在同一PDF中拼接阅读（最多可拼4个）
        </div>
        <div class="pinjielist">
            <dl>
                <c:forEach begin="0" end="3">
                    <dd zid="" bookcode="">
                        <span><img src="${ctx}/static/client/img/pinjie_add.jpg" alt=""></span>
                    </dd>
                </c:forEach>
            </dl>
        </div>
    </div>
</div>
<!-- 人物相关作品弹出 -->
<div class="layer layer-renwu">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="newclose"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="layerinfo">
            <div class="renwudetail">
                <dl>

                </dl>
            </div>
        </div>
    </div>
</div>
<!-- 弹出目录 -->
<div class="layer layer-mulu" style="display: none;">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="close"><img alt="" src="${ctx}/static/client/img/close.png"></span>
        <div class="layerinfo">
            <dl class="mululist">
            </dl>
        </div>
    </div>
</div>
<%--遮罩层测试--%>
<%--<input id="btnshow" type="button" value="Show" onclick="showdiv();"/>--%>
<%--<div id="bg"></div>--%>
<%--<div id="show">请稍后...</div>--%>
<%--<input id="btnclose" type="button" value="Close" onclick="hidediv();"/>--%>
<!-- 弹出高级检索 -->
<script type="text/javascript" src="${ctx}/static/client/js/jquery.base64.js"></script>
<script>
    set_class(".nav .navbox a", "on", "");
    var type = $("#type").val();
    var count = '${PageCount}';

</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/searchresult.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/client/customer/js/search/searchresult.js"></script>

<script>
    $(function () {
        var typeRe = '${type}';
        var first = '${firstSearch}';
        if (first == 1) {
            $(".moreinfofont").slideDown();
            $(".moreclick i").show();
            $(".moreclick em").hide();
            //$(".search-results-links").children().remove();

        }
        if (typeRe == "wz") {
            $(".searchbox01").show();
            $(".searchbox01").addClass("active").siblings().removeClass("active");
            $(".searchbox02, .searchbox03, .searchbox04, .searchbox05, .searchbox06, .searchbox07, .searchbox08").hide();
        }
        if (typeRe == "nj") {
            $(".searchbox02").show();
            $(".searchbox02").addClass("active").siblings().removeClass("active");
            $(".searchbox01, .searchbox03, .searchbox04, .searchbox05, .searchbox06, .searchbox07, .searchbox08").hide();

        }
        if (typeRe == "rw") {
            $(".searchbox03").show();
            $(".searchbox03").addClass("active").siblings().removeClass("active");
            $(".searchbox02, .searchbox01, .searchbox04, .searchbox05, .searchbox06, .searchbox07, .searchbox08").hide();

        }
        if (typeRe == "lw") {
            $(".searchbox04").show();
            $(".searchbox04").addClass("active").siblings().removeClass("active");
            $(".searchbox02, .searchbox03, .searchbox01, .searchbox05, .searchbox06, .searchbox07, .searchbox08").hide();

        }
        if (typeRe == "kt") {
            $(".searchbox05").show();
            $(".searchbox05").addClass("active").siblings().removeClass("active");
            $(".searchbox02, .searchbox03, .searchbox04, .searchbox01, .searchbox06, .searchbox07, .searchbox08").hide();

        }
        if (typeRe == "hy") {
            $(".searchbox06").show();
            $(".searchbox06").addClass("active").siblings().removeClass("active");
            $(".searchbox02, .searchbox03, .searchbox04, .searchbox05, .searchbox01, .searchbox07, .searchbox08").hide();

        }
        if (typeRe == "ts") {
            $(".searchbox07").show();
            $(".searchbox07").addClass("active").siblings().removeClass("active");
            $(".searchbox02, .searchbox03, .searchbox04, .searchbox05, .searchbox06, .searchbox01, .searchbox08").hide();

        }
        if (typeRe == "dsj") {
            $(".searchbox08").show();
            $(".searchbox08").addClass("active").siblings().removeClass("active");
            $(".searchbox02, .searchbox03, .searchbox04, .searchbox05, .searchbox06, .searchbox07, .searchbox01").hide();

        }

    });
    $(function () {
        /*
           <a class="search_click02 active" id="wzTitle" onclick="searchType('wz')">标题</a>
           <a class="search_click01" id="wzTextContent"  onclick="searchType('wz','TextContent')">全文</a>
           <a class="search_click03" id="wzAuthor" onclick="searchType('wz','author')">作者</a>
           <a class="search_click04" id="wzKeyword" onclick="searchType('wz','Keyword')">关键词</a>
        * */
        $("#wzTextContent").click(function () {
            $("#wzTextContent").show();
            $("#wzTextContent").addClass("active").siblings().removeClass("active");
            //$("#wzAuthor, #wzKeyword").hide();
        });

        $("#wzTitle").click(function () {
            $("#wzTitle").show();
            $("#wzTitle").addClass("active").siblings().removeClass("active");
            //$("#wzAuthor, #wzKeyword").hide();
        });


        $("#wzAuthor").click(function () {
            $("#wzAuthor").show();
            $("#wzAuthor").addClass("active").siblings().removeClass("active");
           /// $("#wzTitle, #wzKeyword").hide();
        });

        $("#wzKeyword").click(function () {
            $("#wzKeyword").show();
            $("#wzKeyword").addClass("active").siblings().removeClass("active");
           // $("#wzTitle, #wzAuthor").hide();
        })
    });




    function mulushow(a, b, c) {
        $.ajax({
            url: "/article/getDirectory",
            asunc: false,
            data: {
                bookcode: a,
                fid: b
            },
            success: function (data) {
                if (data.length > 0) {
                    $(".layer-mulu").show();
                    var vl = "<dt>" + c + "</dt>";
                    for (var i in data) {
                        vl += "<dd><a href='/article/articleInfo?zid=" + data[i].zid + "&bookcode=" + data[i].bookcode + "'  >" + data[i].title + "</a></dd>"
                    }
                    $(".mululist").html(vl);
                } else {
                    alertify.alert("暂无数据")
                }
            }
        })
    }
</script>
<script>
</script>
<!-- 底部 -->
<%@ include file="../include/footer.jsp" %>
</body>
</html>
