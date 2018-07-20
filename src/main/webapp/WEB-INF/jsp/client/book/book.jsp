<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${book.title}</title>
    <!-- 共用 js -->
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>
    <%--<script type="text/javascript" src="${ctx}/static/client/web/viewer.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/static/client/js/common0925.js"></script>--%>

    <!-- 公用搜索进入文件 -->
    <script type="text/javascript" src="${ctx}/static/client/customer/publicJS.js"></script>
    <!-- 本页面js -->
    <script type="text/javascript" src="${ctx}/static/client/customer/js/book/book.js"></script>
    <!-- 共用css -->
    <link rel="stylesheet" href="${ctx}/static/client/css/style.css"/>
    <link rel="stylesheet" href="${ctx}/static/client/css/style0925.css"/>

    <!-- banner切换 -->
    <link rel="stylesheet" href="${ctx}/static/client/css/normalize.css"/>
    <script src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
    <script src="${ctx}/static/client/js/meeting.js"></script>

    <%-- alert框 --%>
    <link rel="stylesheet" href="${ctx}/static/client/css/alertify.css" />
    <script type="text/javascript" src="${ctx}/static/client/js/alertify.js"></script>
    <script>
        var appPath = '${ctx}';
        var username = '${userName}';
        var ONLINESTATUS='${ONLINESTATUS}';
    </script>
    <%--<link rel="stylesheet" href="${ctx}/static/remark/css/bootstrap.min.css">--%>
    <!-- 大事记 -->
    <link href="${ctx}/static/client/js/timeslide/timeslide.css" type="text/css" rel="stylesheet" />
    <style>
        .row {
            margin-right: -15px;
        }

        .row:before, .row:after {
            display: table;
            content: " ";
        }

        .row:after {
            clear: both;
        }

        .col-lg-5, .col-lg-3, .col-lg-4, .col-lg-2, .col-lg-6 {
            position: relative;
            min-height: 1px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        @media (min-width: 1200px) {
            .col-lg-5, .col-lg-3, .col-lg-4, .col-lg-2, .col-lg-6 {
                float: left;
            }

            .col-lg-5 {
                width: 41.66666667%;
            }

            .col-lg-3 {
                width: 25%;
            }

            .col-lg-4 {
                width: 33.33333333%;
            }

            .col-lg-2 {
                width: 16.66666667%;
            }

            .col-lg-6 {
                width: 50%;
            }
        }
    </style>
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
            当前位置：<a href="${ctx}/index">首页</a> &gt;&gt;<a href="${ctx}/book/booksList">年鉴</a> &gt;&gt;<span>${book.title}</span>
        </div>

        <!-- 详细 -->
        <div class="pro_des">

            <div class="pro_img_img">
                <em></em>
                <img src="${book.cover}"/>
            </div>

            <div class="shuzi_right">

                <div class="shuzi_right_top">
                    <h5 class="tit">${book.title}<p>${book.title_en}</p></h5>
                    <div class="favarite" style="display: none;">
                        <a onclick="collectFolder()" class="click-shoucang" title="收藏"><img
                                src="${ctx}/static/client/img/icons_star.jpg" alt=""/></a>
                        <a href="##" class="click-wenxian" title="引用文献"><img
                                src="${ctx}/static/client/img/icons_link.jpg" alt=""/></a>
                    </div>
                </div>
                <div style="width: 710px;height: 140px;">
                    <div class="row" style="margin-top: -16px;">
                        <c:if test="${not empty book.author}">
                            <div style="height: 30px;line-height: 30px;width: 90%;">${book.author}&nbsp;&nbsp;编
                            </div>
                        </c:if>
                        <c:if test="${not empty book.charCount}">
                            <div style="height: 30px;line-height: 30px;width: 40%;display: inline-block">字数(千字)：${book.charCount}</div>
                        </c:if>
                        <c:if test="${not empty book.pubdate}">
                            <div style="height: 30px;line-height: 30px;width: 40%;display: inline-block">出版日期：${book.pubdate}</div>
                        </c:if>
                        <c:if test="${not empty book.publisherName}">
                            <div style="height: 30px;line-height: 30px;width:40%;display: inline-block">
                                出版单位：${book.publisherName}</div>
                        </c:if>
                        <c:if test="${not empty book.classification}">
                            <div style="height: 30px;line-height: 30px;width: 40%;display: inline-block">
                                中图分类号：${book.classification}</div>
                        </c:if>
                        <c:if test="${not empty book.isbn}">
                            <div style="height: 30px;line-height: 30px;width: 40%;display: inline-block">ISBN：${book.isbn}</div>
                        </c:if>
                    </div>
                    <div style="margin-top: 5px;" class="other">
                        同年鉴其他卷次：
                        <c:if test="${empty bookList}">暂无数据</c:if>
                        <c:forEach items="${bookList}" var="_book">
                            <a href="${ctx}/book/book?bookcode=${_book.bookcode}" target="_blank">${_book.bookYear}</a>&nbsp;
                        </c:forEach>
                        >>
                    </div>
                </div>

                <div class="add_buy">
                    <%--<div class="yearbox">--%>
                        <%--<em><img src="${ctx}/static/client/img/more.png" alt=""></em>--%>
                        <%--<b></b>--%>
                        <%--同年鉴其他卷次：--%>
                        <%--<c:if test="${empty bookList}">暂无数据</c:if>--%>
                        <%--<c:forEach items="${bookList}" var="_book">--%>
                            <%--<a href="${ctx}/book/book?bookcode=${_book.bookcode}" target="_blank">${_book.bookYear}</a>--%>
                        <%--</c:forEach>--%>
                    <%--</div>--%>
                    <a  onclick="readOnline()" class="color2_1 nianjuan" target="_blank">在线阅读</a>
                    <a href="/book/analysis?bookcode=${book.bookcode}" class="color2_1 nianjuan" style="margin-left: 40px;" target="_blank">同类对比</a>
                </div>

            </div>
        </div>

        <!-- 切换 -->
        <div class="tab package">
            <div id="contentE" class="area">
                <div class="cons">
                    <div class="con tab-hd" style="left: 0px;"><!--调整第一个显示位置请修改"left:0px;的值" -->
                        <c:if test="${bookDetailsListfinal.size()>0}">
                            <div class="left wdA">目录浏览</div>
                        </c:if>
                        <c:if test="${bookDetailsAuthorNum>0}">
                            <div class="left wdA">综述\概况</div>
                        </c:if>
                        <c:if test="${hotArticles.size()>0}">
                            <div class="left wdA">热门文献</div>
                        </c:if>
                        <c:if test="${bookDetailMapNum>0}">
                            <div class="left wdA">会议活动</div>
                        </c:if>
                        <c:if test="${htmlContentNum>0}">
                            <div class="left wdA">大事记</div>
                        </c:if>
                        <c:if test="${bookFileNum>0}">
                            <div class="left wdA">图表荟萃</div>
                        </c:if>
                        <c:if test="${entrysCount>0}">
                            <div class="left wdA">图书</div>
                        </c:if>
                        <c:if test="${entrysCount1>0}">
                            <div class="left wdA">人物</div>
                        </c:if>
                        <c:if test="${bookDetailsMechanismNum>0}">
                            <div class="left wdA" id="collectList9title">机构</div>
                        </c:if>
                        <c:if test="${bookDetailTopicNum>0}">
                            <div class="left wdA">课题</div>
                        </c:if>
                        <c:if test="${bookDetailPaperCount>0}">
                            <div class="left wdA">最佳论文</div>
                        </c:if>
                    </div>
                </div>
                <div class="btns" id="bbtns" style="display: none;">
                    <a href="javascript:void(0)" class="up"></a>
                    <a href="javascript:void(0)" class="down"></a>
                </div>
            </div>
            <script type="text/javascript">
                jQuery(function (jq) {
                    var rr = jq('#contentE');
                    var conr = rr.find('div.con'),
                            conr0 = conr[0],
                            btnWr = rr.find('> div.btns'),
                            btnPr = btnWr.find('a.up'),
                            btnNr = btnWr.find('a.down');

                    var lisr = conr.find('.left');

                    conr.find(".bord").hover(function () {
                        jq(this).addClass("bgBord");
                    }, function () {
                        jq(this).removeClass("bgBord");
                    });

                    var pnumr = 8, numr = lisr.length;
                    if (numr <= pnumr) return;

                    var owr = lisr[1].offsetLeft - lisr[0].offsetLeft,
                            idxArear = [0, numr - pnumr],
                            idxr = 0;

                    function updateNum(n) {
                        if (n > idxArear[1] || n < idxArear[0]) {
                            return;
                        }

                        btnPr[((n == 0) ? 'add' : 'remove') + 'Class']('uN');
                        btnNr[((n == idxArear[1]) ? 'add' : 'remove') + 'Class']('dN');

                        idxr = n;
                        conr.stop().animate({left: (-n * owr)}, 300);
                    }

                    btnPr.click(function () {
                        updateNum(idxr - 1);
                        return false;
                    });
                    btnNr.click(function () {
                        updateNum(idxr + 1);
                        return false;
                    });
                });
            </script>
            <ul class="tab-bd">
                <c:if test="${bookDetailsListfinal.size()>0}">
                    <li style="">
                        <!-- 目录浏览 -->
                        <div class="infolist">
                            <dl>
                                <c:if test="${bookDetailsListfinal.size()<=2}">
                                <c:forEach items="${bookDetailsListfinal}" var="firstCatalog" varStatus="status">
                                    <dd style="display: inline-block;vertical-align: top; height: 100%;">
                                        <h3><p class="ml-title" title="${firstCatalog[0].title}">${firstCatalog[0].title}</p></h3>
                                        <c:forEach items="${firstCatalog[1]}" var="bookDetails2">
                                            <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;"><a href="${ctx}/article/articleInfo?zid=${bookDetails2.zid}&bookcode=${bookDetails2.bookcode}"
                                               target="_blank">${bookDetails2.title}</a></p>
                                        </c:forEach>
                                 </dd>
                                </c:forEach>
                            </c:if>
                    <c:if test="${bookDetailsListfinal.size()>2}">
                                <c:forEach items="${bookDetailsListfinal}" var="firstCatalog" varStatus="status">
                                    <dd>
                                        <h3><a href="javascript:void(0);"
                                               index="${status.count}">展开目录</a><p class="ml-title" title="${firstCatalog[0].title}">${firstCatalog[0].title}</p></h3>
                                        <c:forEach items="${firstCatalog[1]}" var="bookDetails2" begin="0" end="2">
                                            <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;"><a href="${ctx}/article/articleInfo?zid=${bookDetails2.zid}&bookcode=${book.bookcode}"
                                                  target="_blank" title="${bookDetails2.title}">${bookDetails2.title}</a></p>
                                        </c:forEach>
                                        <c:if test="${firstCatalog[1].size()==1}">
                                            <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                                <a href="" target="_blank" title="">&nbsp;</a></p>
                                            <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                                <a href="" target="_blank" title="">&nbsp;</a></p>
                                        </c:if>
                                        <c:if test="${firstCatalog[1].size()==2}">
                                            <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                                <a href="" target="_blank" title="">&nbsp;</a></p>
                                        </c:if>
                                        <c:if test="${firstCatalog[1].size()==0}">
                                            <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                                <a href="" target="_blank" title="">&nbsp;</a></p>

                                            <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                                <a href="" target="_blank" title="">&nbsp;</a></p>

                                            <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                                <a href="" target="_blank" title="">&nbsp;</a></p>
                                        </c:if>
                                    </dd>
                                </c:forEach>
                        </c:if>
                            </dl>
                        </div>
                    </li>
                </c:if>
                <c:if test="${bookDetailsAuthorNum>0}">
                    <li style="">
                        <!-- 综述\概况 -->
                        <div class="wenxian zsabout" id="collectList3">

                                <%--<h3><b>2014</b>研究综述</h3>--%>
                                <%--<div class="zhongshu-item1"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item2"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item3"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item4"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item5"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item6"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item7"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item8"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item9"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item10"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item11"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                                <%--<div class="zhongshu-item12"><a href="#">节日研究综述<p>浏览量 : 2341</p></a></div>--%>
                        </div>
                    </li>
                </c:if>
                <c:if test="${hotArticles.size()>0}">
                    <li>
                        <!-- 热门文献 -->
                        <div class="wenxian">
                            <dl>
                                <dt>— 热门文献TOP10 —</dt>
                                <c:forEach items="${hotArticles}" var="article" varStatus="status" begin="0" end="9">
                                    <dd style="height: 49px">
                                        <em>${status.count}</em>
                                        <span style="width: 80px">浏览：${article.readCount}</span>
                                        <span style="width:170px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">来源：${hotArticlesFirstCatalogs[status.index]}</span>
                                        <span style="width:50px;height:48px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${hotArticlesAuthors[status.index]}</span>
                                        <span style="width:265px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                            <a href="${ctx}/article/articleInfo?zid=${article.zid}&bookcode=${book.bookcode}" target="_blank" title="${article.title}">${article.title}</a>
                                        </span>

                                    </dd>
                                </c:forEach>
                            </dl>
                        </div>
                    </li>
                </c:if>
                <c:if test="${bookDetailMapNum>0}">
                    <li style="">
                        <!-- 会议活动 -->
                        <div class="activity">
                            <div class="activity-year">
                                <input type="hidden" value="${book.bookYear}" id="bookyear">
                                <h3><a style="color:#ffffff;" href="javascript:void(0);" onclick="getmettingAll('${book.bookYear}','${book.bookcode}')">${book.bookYear-1}</a></h3>
                                <dl class="monthlist" id="monthss">
                                    <c:forEach begin="1" end="12" varStatus="i">
                                        <dd style="cursor:pointer ;" onclick="getmeeting('${book.bookYear}','${i.index}','会议词条','${book.bookcode}')">${i.index}月</dd>
                                    </c:forEach>
                                </dl>
                            </div>
                            <dl id="meeting" class="activitylist activityliststyle"></dl>
                        </div>
                    </li>
                </c:if>

                <c:if test="${htmlContentNum>0}">
                    <li style="">
                        <!-- 大事记 -->
                        <div class="history">
                            <c:forEach items="${listDsj}" var="dsj" varStatus="status">
                                <div class="history-date">
                                    <dl>
                                        <c:if test="${status.first}">
                                            <h2 class="first">
                                                <a href="#nogo">${dsj.month}月</a>
                                            </h2>
                                        </c:if>
                                        <c:if test="${!status.first}">
                                            <h2 class="date02">
                                                <a href="#nogo">${dsj.month}月</a>
                                            </h2>
                                        </c:if>
                                        <c:if test="${empty dsj.event}">
                                            <dd>
                                                <h3>&nbsp;<span>&nbsp;</span></h3>
                                                <div class="timeslidebox">
                                                    <p>本月无大事记</p>
                                                </div>
                                            </dd>
                                        </c:if>
                                        <c:forEach items="${dsj.event}" var="event" varStatus="sta">
                                            <dd>
                                                <h3>${fn:substring(event.exdata,5,10)}<span>${fn:substring(event.exdata,0,4)}</span></h3>
                                                <div class="timeslidebox">
                                                    <c:if test="${event.title != '大事记'}">
                                                        <h4>${event.title}</h4>
                                                        <a href="javascript:void(0);"><b>收起</b><em>查看更多</em></a>
                                                        <p>${event.htmlContent}</p>
                                                    </c:if>
                                                    <c:if test="${event.title == '大事记'}">
                                                        <%--<h4>${event.title}</h4>--%>
                                                        <a href="javascript:void(0);" style="top: 6px;"><b>收起</b><em>查看更多</em></a>
                                                        <p>${event.htmlContent}</p>
                                                    </c:if>

                                                    <%--<h4>--%>
                                                        <%--<c:if test="${event.title != '大事记'}">--%>
                                                            <%--${event.title}--%>
                                                        <%--</c:if>--%>
                                                    <%--</h4>--%>

                                                </div>
                                            </dd>
                                        </c:forEach>
                                    </dl>
                                </div>
                            </c:forEach>
                        </div>

                    </li>
                </c:if>
                <c:if test="${bookFileNum>0}">
                    <li style="">
                        <!-- 图表荟萃 -->
                        <div class="graph" style="width: 900px">
                            <div class="graph-t">
                                <div class="graph-search">
                                    <input class="graph-search-text" placeholder="输入关键字" type="text" id="inputId">
                                    <input class="graph-search-bt" value="搜索" type="button" onclick="graph_input()">
                                </div>
                                共找到图片<b>${bookFileNum}</b>张
                                <a class="graph01-click graphshow"><em id="em1"><img
                                        src="${ctx}/static/client/img/graph_icon1.png" alt=""></em></a>
                                <a class="graph02-click"><em id="em2"><img
                                        src="${ctx}/static/client/img/graph_icon4.png" alt=""></em></a>
                            </div>
                            <div class="graph-l" style="width:50px;opacity: 0;">
                            </div>
                            <!-- 列表形式 -->
                            <div class="graph-r graph01" id="collectList">

                                <dl class="graph-list1">
                                </dl>
                                <script>
                                    collectShow("${book.bookcode}", 1, 18);

                                </script>
                            </div>
                            <!-- 缩略图形式 -->
                            <div class="graph-r graph02" id="collectList2">
                                <dl class="graph-list2">
                                </dl>

                                <script>
                                    collectShow2("${book.bookcode}", 1, 18);</script>
                            </div>
                        </div>
                    </li>
                </c:if>
                <c:if test="${entrysCount>0}">
                    <li>
                        <!-- 图书 -->
                        <div class="searchbox07">
                            <div class="booklist" id="entryList4" >
                            </div>
                        </div>
                    </li>
                </c:if>
                <c:if test="${entrysCount1>0}">
                    <li>
                        <!-- 人物 -->
                        <div id="entryList5" class="renwu">
                        </div>

                    </li>
                </c:if>
                <c:if test="${bookDetailsMechanismNum>0}">
                    <li>
                        <!-- 机构 -->
                        <div class="mechanismbox">
                            <div class="mechanismbox-left">

                                <div class="mechanism-menu">
                                    <dl>
                                        <c:if test="${fn:length(bookDetailsMechanism) >1}">
                                            <c:forEach items="${bookDetailsMechanism}" var="bookDetailsMechanism" varStatus="s"  begin="0" step="1">
                                                <c:if test="${s.first}">
                                                    <dd class="active"  onclick="collectShow9('${book.bookcode}',1,10,'${bookDetailsMechanism.zid}','${bookDetailsMechanism.source}','${bookDetailsMechanism.title}');">${bookDetailsMechanism.title}</dd>
                                                </c:if>
                                                <c:if test="${!s.first}">
                                                    <dd onclick="collectShow9('${book.bookcode}',1,10,'${bookDetailsMechanism.zid}','${bookDetailsMechanism.source}','${bookDetailsMechanism.title}');">${bookDetailsMechanism.title}</dd>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </dl>
                                    <script>
                                        $(function(){
                                            $(".mechanism-menu dl dd").click(function(){
                                                $(".mechanism-menu dl dd").removeClass();
                                                $(this).attr("class","active");
                                            });

                                        })
                                    </script>
                                </div>
                            </div>
                            <div class="mechanismbox-right" id="collectList9">
                                <%--<script>--%>
                                    <%--$(function(){--%>
                                        <%--$("body").delegate(".activemore","click", function(){--%>
                                            <%--alert("jigou ");--%>
                                            <%--$(this).prev(".activeinfo").toggleClass("active");--%>
                                            <%--$(this).children("b").toggle();--%>
<%--//                                            $(this).children("b").attr("style","display: none;");--%>
                                        <%--});--%>
                                    <%--})--%>
                                <%--</script>--%>
                                <c:if test="${fn:length(bookDetailsMechanism) ==1}">
                                    <script>
                                        $(function(){
                                        $(".mechanismbox-left").attr("style","display: none;");
                                        $("#collectList9").removeClass();

                                        })
                                    </script>
                                </c:if>
                            </div>
                        </div>
                    </li>
                </c:if>
                <c:if test="${bookDetailTopicNum>0}">
                    <li>
                        <!-- 课题 -->
                        <div class="keti">
                            <div class="keti-left">
                                <a class="on" onclick="collectShow8('${book.bookcode}',1,5,'立项');">立项清单</a>
                                <a onclick="collectShow8('${book.bookcode}',1,4,'结项');">结项清单</a>
                            </div>
                            <div class="booklist" id="collectList8">
                            </div>
                        </div>
                    </li>
                </c:if>
                <c:if test="${bookDetailPaperCount>0}">
                    <li>
                        <!-- 最佳论文 -->
                        <div class="searchbox04">
                            <div class="booklist" id="collectList7">
                            </div>
                        </div>
                    </li>
                </c:if>
            </ul>
            <!-- 图表荟萃列表缩略图弹窗 -->
            <div class="graphlayer">
                <div class="graphlayerbg"></div>
                <span class="graph-close"><img alt="" src="${ctx}/static/client/img/indexclose.png"></span>
                <div class="graphinfo">
                    <iframe id="graphshow" frameborder="no" allowTransparency="true" noresize="noresize" border="0"
                            framespacing="0" target="" name="myFrameName" src=""
                            style="background-color:transparent"></iframe>
                </div>
            </div>
        </div>


    </div>


    <!-- 右边 -->
    <div class="colum_right">

        <div class="right-box">
            <div class="right-hd">
                <a href="javascript:void(0);" onclick="downloadEcharts(1)"><img
                        src="${ctx}/static/client/img/graphs_icon2.png" alt=""></a>
                <%--<a href="#"><img src="${ctx}/static/client/img/graphs_icon1.png" alt=""></a>--%>
                <h3>热点</h3>
            </div>
            <jsp:include page="../include/hotResearch.jsp"></jsp:include>
        </div>

        <div class="right-box">
            <div class="right-hd">
                <a href="javascript:void(0);" onclick="downloadEcharts(2)"><img
                        src="${ctx}/static/client/img/graphs_icon2.png" alt=""></a>
                <%--<a href="#"><img src="${ctx}/static/client/img/graphs_icon1.png" alt=""></a>--%>
                <h3>相关人物</h3>
            </div>
            <jsp:include page="../include/relatedPeople.jsp"></jsp:include>
        </div>

        <div class="right-box">
            <div class="right-hd">
                <a href="javascript:void(0);" onclick="downloadEcharts(3)"><img
                        src="${ctx}/static/client/img/graphs_icon2.png" alt=""></a>
                <%--<a href="#"><img src="${ctx}/static/client/img/graphs_icon1.png" alt=""></a>--%>
                <h3>相关机构</h3>
            </div>
            <jsp:include page="../include/relatedInstitution.jsp"></jsp:include>
        </div>

    </div>

</div>

<!-- 弹出目录 -->
<c:forEach items="${bookDetailsListfinal}" var="firstCatalog" varStatus="status">
    <div class="layer layer-mulu-${status.count}">
        <div class="layerbg"></div>
        <div class="layerbox">
            <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
            <div class="layerinfo">
                <dl class="mululist">
                    <dt>${firstCatalog[0].title}</dt>
                    <c:forEach items="${firstCatalog[1]}" var="bookDetails2">
                        <dd><a href="${ctx}/article/articleInfo?zid=${bookDetails2.zid}&bookcode=${bookDetails2.bookcode}"
                               target="_blank">${bookDetails2.title}</a></dd>
                    </c:forEach>
                </dl>
            </div>
        </div>
    </div>
</c:forEach>
<!-- 弹出引用文献 -->
<div class="layer layer-wenxian">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="layerinfo">
            <div class="wenxiangeshi">
                <em>引用格式</em>
                <ul>
                    <li>格式1</li>
                    <li>格式2</li>
                    <li>格式3</li>
                </ul>
            </div>
            <dl class="mululist mululiststyle">
                <dt><a href="#"><img src="${ctx}/static/client/img/wenxian_icon3.png" alt="清空">清空</a><a href="#"><img
                        src="${ctx}/static/client/img/wenxian_icon2.png" alt="导出">导出</a><a href="#"><img
                        src="${ctx}/static/client/img/wenxian_icon1.png" alt="复制">复制</a></dt>
                <dd><i><img src="${ctx}/static/client/img/wenxian_icon4.png" alt="删除"></i><a href="#">（1）朝戈金编.《中国民俗学年鉴（2015）》.中国社会科学出版社</a>
                </dd>
                <dd><i><img src="${ctx}/static/client/img/wenxian_icon4.png" alt="删除"></i><a href="#">（2）朝戈金编.《中国民俗学年鉴（2015）》.中国社会科学出版社</a>
                </dd>
                <dd><i><img src="${ctx}/static/client/img/wenxian_icon4.png" alt="删除"></i><a href="#">（3）朝戈金编.《中国民俗学年鉴（2015）》.中国社会科学出版社</a>
                </dd>
                <dd><i><img src="${ctx}/static/client/img/wenxian_icon4.png" alt="删除"></i><a href="#">（1）朝戈金编.《中国民俗学年鉴（2015）》.中国社会科学出版社</a>
                </dd>
                <dd><i><img src="${ctx}/static/client/img/wenxian_icon4.png" alt="删除"></i><a href="#">（2）朝戈金编.《中国民俗学年鉴（2015）》.中国社会科学出版社</a>
                </dd>
                <dd><i><img src="${ctx}/static/client/img/wenxian_icon4.png" alt="删除"></i><a href="#">（3）朝戈金编.《中国民俗学年鉴（2015）》.中国社会科学出版社</a>
                </dd>
            </dl>
        </div>
    </div>
</div>
<!-- 弹出收藏 -->
<div class="layer layer-shoucang">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="layerinfo">
            <div class="favolistR folder" id="favoFolder">
                <%--<h5>请选择一个收藏夹<a href="#">新建收藏夹</a></h5>--%>
                <%--<ul>--%>
                <%--<li>--%>
                <%--<em>经济民俗学</em>--%>
                <%--<a href="#"><img src="${ctx}/static/client/img/folder_icon3.png">删除</a>--%>
                <%--<a href="#"><img src="${ctx}/static/client/img/folder_icon2.png">重命名</a>--%>
                <%--</li>--%>

                <%--</ul>--%>
            </div>
            <div class="btns"><a href="#">确定</a><a href="#" class="btn-cancle">取消</a></div>
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
                    <dd>（1）余淼杰、梁中华（2014）：“贸易自由化与中国劳动收入份额——基于制造业数据的实证分析”，《管理世界》，第7期。</dd>
                </dl>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/client/js/jquery.base64.js"></script>
<!-- 弹出高级检索 -->
<script>



    //在原书中阅读
    function readOnline() {
        var iiiPP=$("#iiiPP").val();
//        if (username == null){
//            alertify.alert("请登录！");
//        }else {
        var yd =  encodeURI("原文阅读");
        $.ajax({
            url:"${ctx}/organize/authorization",
            dataType:"json",
            data:{
                'name':"原文阅读",
                dateTime: Math.random(),//随机函数------------------
                iiiPP:iiiPP,
            },
            type:"get",
            async:false,
            success:function(data){
                if (data.status == 0){
                    <%--if(ONLINESTATUS == "ON"){--%>
                        <%--var open = window.open();--%>
                        <%--open.location = '${ctx}/book/readpdf?bid=${book.id}&page=1';--%>
                    <%--}else {--%>
<%--//                        alert(1);--%>
                        <%--window.open('${ctx}/static/client/web/viewer.html?page=1&file='+encodeURIComponent('${ctx}/book/readpdfOFF?bid='+${book.id}),'PDF');--%>
                        <%--&lt;%&ndash;window.open('${ctx}/static/client/web/viewer.html?page=${startPage}&file='+encodeURIComponent('${ctx}/book/readpdfOFF?bid='+${book.id}),'PDF');&ndash;%&gt;--%>
                    <%--}--%>
                    window.open('${ctx}/static/client/web/viewer.html?page=1&file='+encodeURIComponent('${ctx}/book/readpdfOFF?bid='+${book.id}),'PDF');
                }else {
                    alertify.alert("权限不足");
                }
            }
        })
//        }
    }

    collectShow("${book.bookcode}", 1, 18);
    collectShow2("${book.bookcode}", 1, 6);
    collectShow3("${book.bookcode}", 1, 7);
    collectShow4("${book.bookcode}", 1, 7);
    collectShow5("${book.bookcode}", 1, 7);
    collectShow7("${book.bookcode}", 1, 9);
    collectShow8("${book.bookcode}", 1, 5, "立项");
    collectShow9("${book.bookcode}", 1, 10, "${bookDetailsMechanism[0].zid}",'${bookDetailsMechanism[0].source}','${bookDetailsMechanism[0].title}');
    set_class(".nav .navbox a", "on", "");

    function graph_input() {
        collectShow("${book.bookcode}", 1, 18);
        collectShow2("${book.bookcode}", 1, 6);
    }
    var bookcode = '${book.bookcode}';
    var bbtns="${bbtns}";
    if(bbtns>9){
        $("#bbtns").attr("style","display:block");
    }
</script>
<script>
    $(function(){
        console.log($("#code").val("nj"));
        $("body").delegate(".activemore","click", function(){
            $(this).prev(".activeinfo").toggleClass("active");
            $(this).children("b").toggle();
        });
    })
</script>
<script src="${ctx}/static/client/customer/js/hovertreescroll.js"></script>

<!-- 底部 -->
<%@ include file="../include/footer.jsp" %>
</body>
</html>
