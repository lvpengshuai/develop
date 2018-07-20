<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>${book.title}</title>
    <link rel="stylesheet" href="${ctx}/static/client/css/normalize.css"/>
    <link rel="stylesheet" href="${ctx}/static/client/css/style.css"/>
    <link rel="stylesheet" href="${ctx}/static/client/css/bookdetail.css">
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/customer/js/book/bookdetail.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/customer/publicJS.js"></script>
    <script src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
    <script src="${ctx}/static/client/js/demo.js"></script>
    <link rel="stylesheet" href="${ctx}/static/client/css/alertify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/alertify.js"></script>
    <script src="${ctx}/static/client/js/layer/layer.js"></script>

    <!-- 滚动条 -->
    <script src="${ctx}/static/client/js/scrollbar/jquery.mousewheel.js"></script>
    <script src="${ctx}/static/client/js/scrollbar/perfect-scrollbar.js"></script>
    <link href="${ctx}/static/client/js/scrollbar/perfect-scrollbar.css" rel="stylesheet">
    <%--导出应用文献--%>
    <script src="${ctx}/static/client/js/FileSaver.js"></script>
    <script src="${ctx}/static/client/js/jquery.wordexport.js"></script>

    <script src="${ctx}/static/client/js/jquery.base64.js"></script>

    <script type="text/javascript">
        var appPath = '${ctx}';
        var username = '${userName}';
        var folderName = "";
        var foName = null;
        var epub = "${book.epub}";
        var ONLINESTATUS="${ONLINESTATUS}";

        function fwdIdUrl(info) {
            window.open("${ctx}/article/articleInfo?zid=" + info.id);

        }


    </script>
    <style type="text/css">
        /*如显示目录，正文内容浮动*/
        <c:if test="${muluFlag!=1}">
        .readboxtit, .readboxtit3, .readboxinfo, .readboxautor {
            padding-left: 170px;
        }

        </c:if>
    </style>
</head>
<body>
<!--顶部-->
<%@include file="/WEB-INF/jsp/client/include/publicTop.jsp" %>
<!--导航-->
<%@include file="/WEB-INF/jsp/client/include/publicNavigation.jsp" %>
<!-- banner -->

<div class="wrapin">
    <!-- 左边 -->
    <div class="colum_left sub_left">

        <!-- 当前位置 -->
        <div class="statue">
            当前位置：<a href="${pageContext.request.contextPath}/index">首页</a> &gt;&gt;<a href="/book/book?bookcode=${book.bookcode}">${book.title}</a> &gt;&gt;<span>文献详情</span>
        </div>

        <div class="tiaomu-info">
            <div class="tiaomu-info-t">
                <input type="hidden" class="zid" value="${bookDetails.zid}" id="zid">
                <h3 id="titleA">${title}</h3>
                <div class="favarite">
                    <c:if test="${muluFlag==1}">
                        <a class="click-shoucang" title="收藏" onclick="collectFolder()"><img
                                src="${ctx}/static/client/img/icons_star.jpg" alt=""></a>
                        <a class="click-wenxian" title="引用文献"><img src="${ctx}/static/client/img/icons_link.jpg" alt=""></a>
                    </c:if>
                </div>
            </div>
            <div class="tiaomu-info-c">
                <b>来源：
                    <a target="_blank" class="bd-a" style="color: #0458ce" href="/book/book?bookcode=${bookcode}">
                        <input  id="sourceA" name="cttype" type="hidden" value="${zipTitle1}">
                        ${zipTitle1}
                    </a>>>
                    <a class="bd-a muluShow" style="color: #0458ce;">${writings}</a></b>
                <c:if test="${!empty bookAuthorList}">
                <span>
                    作者：
                            <c:forEach items="${bookAuthorList}" varStatus="varStatus" var="bookAuthor">
                                <a class="bd-a" href="${ctx}/search?kw=${bookAuthor.personname}" target="_blank">${bookAuthor.personname}</a>
                            </c:forEach>
                </span>
                </c:if>
                <c:if test="${empty bookAuthorList}">
                <span>
                    作者：《暂无作者信息》
                </span>
                </c:if>
                <c:if test="${!empty readCount}">
                    <span>浏览次数：${readCount}</span>
                </c:if>
            </div>
            <c:if test="${!empty abs}">
            <div class="tiaomu-info-d" style="padding:20px 0px;" >
                <c:if test="${! empty abs}">
                    <input id="absA" name="cttype" type="hidden" value="${abs}">
                </c:if>
                <c:if test="${empty abs}">
                    <input id="absA" name="cttype" type="hidden" value="">
                </c:if>

                    摘要：  ${abs}
            </div>
            </c:if>
            <c:if test="${!empty personblurb}">
                <div class="tiaomu-info-d2" id="personblurb">作者简介：  ${personblurb}</div>
            </c:if>
            <div class="tiaomu-info-k" style="font-size: 16px;">
                <c:if test="${ !empty wordList}">
                    关键词：
                    <div class="guanjianci">
                    <c:forEach items="${wordList}" var="keyword" varStatus="varStatus">
                        <a class="active keywordIds" value="${keyword}">${keyword}&nbsp;&nbsp;
                            <b>+</b>
                        </a>
                    </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>


        <!-- 切换 -->
        <div class="tab package" style="    margin-top: -22px;">
            <ul class="tab-hd tab-hd-style">
                <li class="active">在线阅读</li>
                <li>原书中阅读</li>
            </ul>
            <ul class="tab-bd tab-bd-new" style="padding:50px 80px;">
                <li style="display: list-item;">
                    <div class="readbox">
                        <%--判断是否显示目录列表--%>
                        <c:if test="${muluFlag!=1}">
                            <div class="mulu">
                                <h3>目录</h3>
                                <dl class="ps-container">
                                    <c:forEach items="${titleList}" varStatus="status" var="bookd">
                                        <dd><a href="/article/articleInfo?zid=${bookd.zid}&bookcode=${bookd.bookcode}&titleIndex=${status.index}"
                                                <c:if test="${status.index==titleIndex}"> class="active"</c:if>
                                        >${bookd.title }</a>
                                        </dd>
                                    </c:forEach>
                                    <div class="ps-scrollbar-x-rail"
                                         style="width: 170px; display: none; left: 0px; bottom: 3px;">
                                        <div class="ps-scrollbar-x" style="left: 0px; width: 0px;"></div>
                                    </div>
                                    <div class="ps-scrollbar-y-rail"
                                         style="top: 0px; height: 793px; display: inherit; right: 3px;">
                                        <div class="ps-scrollbar-y" style="top: 0px; height: 629px;"></div>
                                    </div>
                                </dl>
                            </div>
                        </c:if>
                        <div class="readboxtit">${title}</div>
                        <c:if test="${!empty bookAuthorList}">
                            <div class="readboxtit3">作者：
                                <c:forEach items="${bookAuthorList}" varStatus="varStatus" var="bookAuthor">
                                    ${bookAuthor.personname}
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="readboxinfo">
                            <div id="p1">
                                <div class="laypho">${ContextInfo['HtmlContent']}</div>
                                <c:if test="${!empty ContextInfo['endContextInfo']}">
                                    <div class="showword" id="p11"><a href="javascript:void(0)">+&nbsp;显示更多信息</a></div>
                                </c:if>
                            </div>
                            <div id="p2" style="display: none">
                                <div class="laypho">${ContextInfo['endContextInfo']}</div>
                                <div class="showword"><a href="javascript:void(0)" id="p22"><b style="font-weight:700;">-</b>&nbsp;收起正文全文</a></div>
                            </div>
                        </div>
                        <div class="readboxautor">
                            <p>
                                <%--  <c:forEach items="${bookAuthorList}" varStatus="varStatus" var="bookAuthor">
                                      ${bookAuthor.personblurb}</br>
                                  </c:forEach>--%>
                            </p>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="readhistory">
                        <span> <a href="/book/book?bookcode=${book.bookcode}"><img src="${book.cover}" alt=""></a>${book.title}</span>
                        <p style="margin:10px 0 0px;">出版社:${book.publisherName}</p>
                        <p style="margin:10px 0 0px;">出版日期:${book.pubdate}</p>
                        <p style="margin:10px 0 5px;">章节:<b>《${zipTitle1}》&nbsp;\&nbsp;&nbsp;${writings}</b></p>
                        <%--<a href="javaScript:alert('开发中...');" target="_blank">在原书中阅读</a>--%>
                        <a onclick="readSourceBooks()" target="_blank" style="padding:5px 5px;">在原书中阅读</a>
                        <%--<a onclick="readSourceBook()" target="_blank" style="padding:5px 5px;">在原书中阅读</a>--%>
                        <%--<a onclick="epubAjax(${title},${book.epub})" style="padding:5px 5px;" >在原书中阅读</a>--%>
                    </div>
                </li>
            </ul>
        </div>

        <div class="height60"></div>

        <div class="tab package">
            <ul class="tab-hd" style=" background:none ">
                <li class="active" style=" cursor:default ;">相似文献</li>
            </ul>
            <ul class="tab-bd">
                <li style="display: list-item;">
                    <div class="likelist">
                        <dl>
                            <c:forEach items="${relatedArticles}" var="relatedArticle">
                                <dd>
                                    <h3>
                                        <a href="/article/articleInfo?zid=${relatedArticle.zid}&bookcode=${relatedArticle.bookcode}" target="_blank">${relatedArticle.title}</a>
                                    </h3>
                                    <h4>来源：
                                        <c:forEach items="${relatedArticle.listZT}" var="item" varStatus="status">
                                            ${item.titleSource}
                                            <c:if test="${!status.last}">\</c:if>
                                        </c:forEach>
                                    </h4>
                                    <p>
                                            ${relatedArticle.content}
                                    </p>
                                </dd>
                            </c:forEach>
                        </dl>
                    </div>
                </li>
            </ul>
        </div>
    </div>


    <!-- 右边 -->
    <div class="sub_right">

        <div class="sub_right_box">
            <div class="sub_right_hd"><h3>${book.bookname}</h3></div>
            <div class="sub_right_bd">
                <a href="/book/book?bookcode=${book.bookcode}"><img src="${book.cover}" alt=""></a>
            </div>
        </div>


    </div>

</div>

<div id="pagecontent" style="display: none"></div>
<!-- 新建文件夹 -->
<div class="layer layer-shoucangjia">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="layerinfo">
            <div class="favolistR folder">
                <h5>请输入收藏夹名称</h5>
                <ul>
                    <li>
                        <input placeholder="请输入收藏夹名称" type="text" class="addfavetetx" id="addfavetetx">
                    </li>
                </ul>
                <div class="btns"><a onclick="insertFolder()">确定</a><a class="btn-cancle">取消</a></div>
            </div>
        </div>
    </div>
</div>

<!-- 删除确定取消 -->
<div class="layer layer-favedelete">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="layerinfo">
            <div class="deletetips">您确定要删除吗？</div>
            <div class="btns"><a onclick="folderDelete()">确定</a><a class="btn-cancle">取消</a></div>
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
            </div>
            <div class="btns"><a onclick="addCollect()">确定</a><a class="btn-cancle">取消</a></div>
            <input type="hidden" id="bookcode" value="${book.bookcode}">
        </div>
    </div>
</div>
<!-- 弹出引用文献 -->
<c:if test="${!empty footImport}">
    <div class="layer layer-wenxian">
        <div class="layerbg"></div>
        <div class="layerbox">
            <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
            <div class="layerinfo">
                <div class="wenxiangeshi">
                    <em>引用格式</em>
                    <ul>
                        <li>引用格式</li>
                        <li>word</li>
                        <li>txt</li>
                    </ul>
                </div>
                <dl class="mululist mululiststyle">
                    <dt><a id="clearAll"><img src="${ctx}/static/client/img/wenxian_icon3.png" alt="清空">清空</a><a class="daochu"><img src="${ctx}/static/client/img/wenxian_icon2.png" alt="导出">导出</a><a
                            id="copy"><img
                            src="${ctx}/static/client/img/wenxian_icon1.png" alt="复制">复制</a></dt>
                    <c:forEach items="${footImport}" var="footImport">
                        <dd><i><img src="${ctx}/static/client/img/wenxian_icon4.png" alt="删除" class="clear" style="cursor: pointer"></i><a>${footImport}</a></dd>
                    </c:forEach>
                </dl>
            </div>
        </div>
    </div>
</c:if>
<!-- 弹出目录 -->
<div class="layer layer-mulu" style="display: none;">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="close"><img alt="" src="${ctx}/static/client/img/close.png"></span>
        <div class="layerinfo">
            <dl class="mululist mululistml">
            </dl>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jsp/client/include/publicFoot.jsp" %>
<script type="text/javascript">
    $(function () {
        //展开引用文献
        $(".click-wenxian").click(function () {

            var a =${footImport.size()};
            if (a == 0) {
                alertify.alert("这篇文献没有引用的内容.");
            } else {
                $(".layer-wenxian").fadeIn();
            }
        });

        $(".muluShow").click(function () {
            $(".layer-mulu").show();
            $.ajax({
                url: "/article/getDirectory",
                asunc: false,
                data: {
                    bookcode: "${bookDetails.bookcode}",
                    fid: "${bookDetails.fid}"
                },
                success: function (data) {
                    if (data.length > 0) {
                        var vl = "<dt>${writings}</dt>";
                        for (var i in data) {
                            vl += "<dd><a href='/article/articleInfo?zid=" + data[i].zid + "&bookcode=" + data[i].bookcode + "'>" + data[i].title + "</a></dd>"
                        }
                        $(".mululistml").html(vl);
                    } else {
                        alertify.alert("暂无数据")
                    }
                }
            })
        });
        $(".likelist dl dd p").find("font").remove();
        //删除全部引用文献
        $("#clearAll").click(function () {
            $(".mululiststyle dd").remove();
        });
        //删除单条引用文献
        $(".clear").click(function () {
            $(this).parents("dd").remove();
        });
        //隐藏引用格式
        $(".wenxiangeshi ul li").click(function () {
            $(".wenxiangeshi em").text($(this).text());
            $(".wenxiangeshi ul").toggle();
        });
        //复制引用文献
        $("#copy").click(function () {
            var copy = "";
            var i = $(".mululiststyle dd").size();
            for (var j = 0; j < i; j++) {
                var k = $(".mululiststyle dd a").eq(j).text();
                copy = copy + k + "\n";
            }
            copyTextToClipboard(copy);
        });
        //导出应用文献
        $(".daochu").click(function () {
            var ii = $(".wenxiangeshi em").text();

            if (ii == "word") {
                var copy = "";
                var i = $(".mululiststyle dd").size();
                for (var j = 0; j < i; j++) {
                    var k = $(".mululiststyle dd a").eq(j).text();
                    copy = copy + "<div>" + k + "</div>";
                }
                $("#pagecontent").html(copy);
                $("#pagecontent").wordExport("引用文献");
            } else if (ii == "txt") {
                var copy = "";
                var i = $(".mululiststyle dd").size();
                for (var j = 0; j < i; j++) {
                    var k = $(".mululiststyle dd a").eq(j).text();
                    copy = copy + k + "\n";
                }
                var blob = new Blob([copy], {type: "text/plain;charset=utf-8"});
                saveAs(blob, "引用文献.txt");

            } else {
                alert("请选择引用格式!!!");
            }
        })
    });

    //复制到剪切板
    function copyTextToClipboard(text) {
        var textArea = document.createElement("textarea");
        textArea.style.background = 'transparent';
        textArea.value = text;
        document.body.appendChild(textArea);
        textArea.select();
        try {
            var successful = document.execCommand('copy');
            var msg = successful ? 'successful' : 'unsuccessful';
        } catch (err) {
            console.log('Oops, unable to copy');
        }
        document.body.removeChild(textArea);
    }


    layer.ready(function () {
        layer.photos({
            photos: '.laypho'
        });
    });
    $(function () {
        $("#p22").click(function () {
            document.getElementById("p2").style.display = "none";
            document.getElementById("p11").style.display = "block";
        });
        $("#p11").click(function () {
            var iiiPP=$("#iiiPP").val();
            $.ajax({
                url: "${ctx}/organize/authorization",
                dataType: "json",
                data: {
                    'name': '在线阅读', dateTime: Math.random(),//随机函数------------------解决ie下兼容问题
                    iiiPP:iiiPP,
                },
                type: "get",
                success: function (data) {
                    if (data.status == 0) {
                        document.getElementById("p2").style.display = "block";
                        document.getElementById("p11").style.display = "none";
                    } else if (data.status == 2) {
                        alertify.alert("权限不足");
                    }
                    else {
                        alertify.alert("登录后才能读取更多文献信息.");
                    }
                }
            })


        });
    });

    //在原书中阅读
    <%--function readSourceBook() {--%>

    <%--$.ajax({--%>
    <%--url:"${ctx}/organize/authorization",--%>
    <%--dataType:"json",--%>
    <%--data:{'name':'原文阅读'},--%>
    <%--type:"get",--%>
    <%--success:function(data){--%>
    <%--if (data.status == 0){--%>
    <%--location.href="${ctx}/book/reader_test?bookId=${book.epub}&title=${epubZid}"--%>
    <%--}else if(data.status == 2){--%>
    <%--alertify.alert("登录后才能读取原书信息.");--%>
    <%--}--%>
    <%--else {--%>
    <%--alertify.alert("权限不足");--%>
    <%--}--%>
    <%--}--%>
    <%--})--%>


    <%--}--%>
    //新版pdfjs的原书阅读
    function readSourceBooks() {
        var names = encodeURI("原文阅读");
        var iiiPP=$("#iiiPP").val();
        $.ajax({
            url: "${ctx}/organize/authorization",
            type: "get",
            data: {
                name: "原文阅读",
                dateTime: Math.random(),//随机函数------------------解决ie下兼容问题
                iiiPP:iiiPP,
            },
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.status == 0) {
                    window.open('${ctx}/static/client/web/viewer.html?page=${startPage}&file='+encodeURIComponent('${ctx}/book/readpdfOFF?bid='+${book.id}),'PDF');
                    <%--if(ONLINESTATUS == "OFF"){--%>
                        <%--&lt;%&ndash;window.open('${ctx}/static/client/web/viewer.html?page='+${startPage}+'&file='+encodeURIComponent('${ctx}/book/readpdfOFF?bid='+${book.id}+'&page='+1),'PDF');&ndash;%&gt;--%>
                        <%--window.open('${ctx}/static/client/web/viewer.html?page=${startPage}&file='+encodeURIComponent('${ctx}/book/readpdfOFF?bid='+${book.id}),'PDF');--%>
                    <%--}else {--%>
                        <%--var open = window.open();--%>
                        <%--open.location = '${ctx}/book/readpdf?bid=${book.id}&page=${startPage}';--%>
                    <%--}--%>

                } else if (data.status == 2) {
                    alertify.alert("权限不足");
                }
                else {
                    alertify.alert("登录后才能读取原书信息.");
                }
            }
        })
    }

    //点击文件夹触发方法
    function nameFolder(fName) {
        foName = fName;
    }

    $("body").delegate(".folName", "click", function () {
        foName = this.id;
        $(this).parent().siblings().children().removeClass("selected");   // 删除其他兄弟元素的样式
        $(this).addClass('selected');                            // 添加当前元素的样式
    });
    // 导航栏选定方法
    set_class(".nav .navbox a", "on", "");
</script>
</body>
</html>
