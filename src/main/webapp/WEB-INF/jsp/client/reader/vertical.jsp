<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>${book.bookName } - 在线阅读</title>
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/jquery.cookie.js"></script>
    <script type="text/javascript"  src="${ctx}/static/client/js/jquery.fullscreen-min.js"></script>
    <link href="${ctx}/static/client/css/global.css?v=1.0" rel="stylesheet" type="text/css" />
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico" />
    <link href="${ctx}/static/client/css/book.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/client/reader/css/normalize.css">
    <link rel="stylesheet" href="${ctx}/static/client/reader/css/main.css">
    <link rel="stylesheet" href="${ctx}/static/client/reader/css/popup.css">

    <c:set var="readPath" value="${book.verId }/complete"></c:set>

    <script>
        var rangy_hilighter_string='';
        var rangy_user_id = "${userId}";
        var rangy_book_id = "${book.bookId}";
        var rangy_chapterUrl = "";
        var book_ready_complete = false;



//        document.domain = "dzzgsw.com";
        var reader;

        document.onreadystatechange = function() {
            if (document.readyState == "complete") {
                EPUBJS.filePath = "${ctx}/static/client/reader/js/libs/";
                EPUBJS.cssPath = "${ctx}/static/client//reader/css/";
                <%--EPUBJS.readDataPath = "${ctx}/static/client/book/webreadata/get";--%>
                EPUBJS.isReadData = true;
                // fileStorage.filePath = EPUBJS.filePath;
                reader = ePubReader("${ctx}/cssp_resource/epub/${book.bookCode}/${book.platform}/${readPath}/");
            }
        };


    </script>
    <!-- Render -->
    <script src="${ctx}/static/client/reader/epub.js?v=0.3"></script>
    <!-- Hooks -->
    <script src="${ctx}/static/client/reader/hooks.js"></script>
    <!-- Reader -->
    <script src="${ctx}/static/client/reader/reader.js?v=0.3"></script>
    <!-- File Storage -->
    <!-- <script src="js/libs/fileStorage.min.js"></script> -->
    <!-- Full Screen -->
    <script src="${ctx}/static/client/reader/libs/screenfull.min.js"></script>
    <script type="text/javascript">
        var rangy_hilighter_string='';
        var rangy_user_id = "";
        var rangy_book_id = "679e392b35ea4420b29a638feb82aad3";
        var rangy_chapterUrl = "";
        var book_ready_complete = false;

        //var server_url = http://file.dzzgsw.com/9;

        var bookId = "${book.bookId}";
        var starLevel = -1;
        var onceLoad= true;
        var src="${book.src}";
        function gotoPage1() {
            if (reader.book.totalPages != null && onceLoad) {
                onceLoad=false;
                reader.book.goto(src);
            }
        }

        $(document).ready(function() {
            window.setInterval("gotoPage1()",100);

            //var iframeWindow = self.frames[0];
            //var inputText = iframeWindow.document.getElementById("div_p");
            //在线阅读字号
            $(document).ready(function(){
                $(".readBoxright dd").hover(function(){
                    $(this).children("ul").toggle();
                })
            })
            $("#readMode").click(function() {
                $.cookie('readMode', 'hor');
                location.reload();
            });
            $("#fullScreenBtn").click(function() {
                $(document).toggleFullScreen();
            });
            $("#catalogBtn").click(function() {
                if (reader) {
                    reader.TocController && reader.TocController.show();
                }
            });
            //书签
//            $("#bookMarkBtn").click(function() {
//                if (reader) {
//                    reader.BookmarksController && reader.BookmarksController.show();
//                }
//            });
            $(".read_delete").click(function(){
                if (reader) {
                    $("#buyDiv").hide();
                    reader.TocController && reader.TocController.hide();
                    reader.BookmarksController && reader.BookmarksController.hide();
                    reader.CommentController && reader.CommentController.hide();
                }
            });
            $("#fontSizeSet li").click(function() {
                $("#viewer > iframe").attr("name","ifName");
                $("#viewer > iframe").attr("id","ifName");
                var framedoc = $(document.getElementById('ifName').contentWindow.document);
                switch($(this).index()) {
                    case 0:
                        if (reader.settings.styles.fontSize != "70%") {
                            if (!reader.fontSizeMap["70%"]) {
                                reader.book.setStyle("fontSize", "70%");
                                reader.book.calcBookPages();
                                //$("#viewer > iframe").attr("name","ifName");
                                //$(window.frames["ifName"].document).find(".content ").css({"line-height":"3em"});
                                framedoc.find(".content ").css({"line-height":"3em"});
                            } else {
                                restorePage("70%");
                            }
                        }
                        break;
                    case 1:
                        if (reader.settings.styles.fontSize != "100%") {
                            if (!reader.fontSizeMap["100%"]) {
                                reader.book.setStyle("fontSize", "100%");
                                reader.book.calcBookPages();
                                //$("#viewer > iframe").attr("name","ifName");
                                //$(window.frames["ifName"].document).find(".content ").css({"line-height":"3em"});
                                framedoc.find(".content ").css({"line-height":"3em"});
                            } else {
                                restorePage("100%");
                            }
                        }
                        break;
                    case 2:
                        if (reader.settings.styles.fontSize != "130%") {
                            if (!reader.fontSizeMap["130%"]) {
                                reader.book.setStyle("fontSize", "130%");
                                reader.book.setStyle("lineHeight", "400%");
                                reader.book.calcBookPages();
                                //$("#viewer > iframe").attr("name","ifName");
                                //$(window.frames["ifName"].document).find(".content ").css({"line-height":"3em"});
                                framedoc.find(".content ").css({"line-height":"3em"});
                            } else {
                                restorePage("130%");
                            }
                        }
                        break;
                    default:
                }

                function restorePage(fontSize) {
                    reader.book.setStyle("fontSize", fontSize);
                    for (var i = 0; i < reader.book.chapters.length; i++) {
                        reader.book.chapters[i].pages = reader.fontSizeMap[fontSize].chaptersPage[i];
                    }
                    reader.book.totalPages = reader.fontSizeMap[fontSize].totalPages;
                    reader.ControlsController.showPage();
                }
            });

            $("#commentStars a").mouseover(function() {
                var index = $(this).index();
                $("#commentStars i").each(function() {
                    if ($(this).parent().index() <= index) {
                        $(this).removeClass("star_empty").addClass("star");
                    } else {
                        $(this).removeClass("star").addClass("star_empty");
                    }
                });
            }).mouseout(function() {
                $("#commentStars i").each(function() {
                    if ($(this).parent().index() <= starLevel) {
                        $(this).removeClass("star_empty").addClass("star");
                    } else {
                        $(this).removeClass("star").addClass("star_empty");
                    }
                });
            }).click(function() {
                starLevel = $(this).index();
            }).focus(function() {
                $(this).blur();
            });



            $("#setPage").click(function() {
                $("#gotoBar").toggle();
            });
            $("#pageNum").keypress(function(event) {
                var value = event.keyCode || event.which;
                var str = String.fromCharCode(value);
                if (value == 13) {
                    gotoPage();
                } else if (!(/^[0-9]$/.test(str) || value == 8)) {
                    event.preventDefault();
                }
            });
            $("#gotoSubmit").click(gotoPage);

//            getComments();

            $(window).resize(resizeHandler);
            resizeHandler();
        });

        function gotoPage() {
            var val = parseInt($("#pageNum").val());
            if (isNaN(val) || val < 0 || val > reader.book.totalPages) {
                $("#pageNum").val("");
            } else {
                reader.book.gotoPage(Math.ceil(val));
            }
        }

        function resizeHandler() {
            $("#div3").height($(document.body).height() - ${book.owner ? 360 : 100});
        }



    </script>
    <style type="text/css">


        #read {background: #EDE8D5}
        .readBoxCenter {background: #FAF7ED}
        .readBoxright {border-top: 0px solid #e0dcca}
        .readBoxright dd {background: #e0dcca;border: 1px solid #e1ddca}



        .readBookPage3 {
            position: absolute;
            left: 25px;
            bottom:20px;
            width: 110px;
        }
        html,body {
            height: 98%;
            margin: 0;
            padding: 0;
        }
        .readBoxleft > dl,dd {
            margin: 0;
            /*height: 36px;*/
        }
        .tp-c img{
            max-height:608px !important;
            width:400px !important;
        }
        .icon-bookmark-empty {
            display: inline-block;
            width: 22px;
            height: 22px;
            background-image: url("${ctx}/static/client/img/book_ico_sq1.png");
            cursor: pointer;
        }
        .icon-bookmark {
            display: inline-block;
            width: 22px;
            height: 22px;
            background-image: url("${ctx}/static/client/img/book_ico_sq2.png");
            cursor: pointer;
        }
    </style>
</head>
<body id="read" style="position: inherit;">
<noscript>请使用支持脚本的浏览器！</noscript>
<!-- 当前页 -->
<div class="readBookPage3" style="width: auto;">
    <div class="readBookPageone" id="gotoBar" style="display: none;width: 105px;">
        <a href="javascript:void(0)">
            <img src="${ctx}/static/client/img/read_page_next.jpg" id="gotoSubmit"/>
        </a>
        当前页<input id="pageNum" style="width: 30px; background-color: #464646; height: 18px; line-height: 18px;border: none;color: white;text-align: center;margin-left: 10px;"/>
    </div>
    <div class="readBookPagetwo">
        <span id="progressTitle">1 / 1 100%</span>
        <a href="javascript:void(0)"><img src="${ctx}/static/client/img/read_page_pre.jpg" id="setPage"/></a>
    </div>
</div>

<!-- 右侧垂直阅读进度 -->
<div class="read_book_jd2"><div style="background:#000; height:100%" id="progressBar"></div></div>

<!-- 顶部 -->
<div id="readBox" style="height:100%">
    <!-- 右侧导航 -->
    <div class="readBoxright">
        <dl style="margin: 0;">
            <dd><a href="javascript:void(0)" id="fullScreenBtn" title="全屏"><img src="${ctx}/static/client/img/read_ico_2.png" alt="全屏" />
                <span>全屏</span>
            </a></dd>


            <dd><a href="javascript:void(0)" id="readMode" title="双页"><img src="${ctx}/static/client/img/read_ico_3.png" alt="双页" /><span>双页</span></a></dd>


            <dd><a href="javascript:void(0)" title="字体大小"><img src="${ctx}/static/client/img/read_ico_4.png" alt="字体大小" /><span>字体</span></a>
                <ul style="left:38px;top:-13px;" id="fontSizeSet">
                    <li><a href="javascript:void(0)">小</a></li>
                    <li><a href="javascript:void(0)">中</a></li>
                    <li><a href="javascript:void(0)">大</a></li>
                </ul>
            </dd>

            <dd><a href="javascript:void(0)" id="catalogBtn" title="目录"><img src="${ctx}/static/client/img/read_ico_5.png" alt="目录" /><span>目录</span></a></dd>
            <%--<dd><a href="javascript:void(0)" id="bookMarkBtn" title="书签"><img src="${ctx}/static/client/img/read_ico_6.png" alt="书签" /><span>书签</span></a></dd>--%>
            <dd ><a href="javascript:void(0)" id="prevPage" title="上一页"><img src="${ctx}/static/client/img/read_ico_12.png" alt="上一页" /><span>后退</span></a></dd>
            <dd ><a href="javascript:void(0)" id="nextPage" title="下一页"><img src="${ctx}/static/client/img/read_ico_13.png" alt="下一页" /><span>前进</span></a></dd>

        </dl>
    </div>
    <!-- 中间内容 -->
    <div class="readBoxCenter" id="contentDiv" style="height:100%">
        <!-- 书名 -->
        <div class="read_book_tit"><span style="float:left;" id="chapterTitle"></span><span id="currentPage"></span></div>
        <div class="read_book_sc">
            <span id="bookmark" class="icon-bookmark-empty"></span>
        </div>
        <!-- 内容 -->
        <div class="read_book_info" style="height:100%">
            <div style="height:100%">
                <div style="background: transparent;width: 100%; height: 100%; right:0; overflow: hidden;">
                    <div id="viewer"></div>
                    <div id="loader"><img src="${ctx}/static/client/img/loader.gif"></div>
                </div>
                <div class="overlay"></div>
            </div>
        </div>
    </div>
    <div class="readBoxCenter" id="catalogDiv" style="display: none;height:100%;">
        <!-- 删除 -->
        <div class="read_delete"><a href="javascript:void(0)"><img src="${ctx}/static/client/img/read_delete.jpg" alt="关闭" /></a></div>
        <div class="read_book_tit"><em>目录</em></div>
        <div id="div1" style="overflow:hidden; height:82%; width:680px; ">
            <!-- 书名 -->
            <div class="read_book_td">${book.bookName }</div>
            <dl class="read_book_ml" id="tocViewList">
                <!-- 目录 -->
            </dl>
        </div>
    </div>
    <div class="readBoxCenter" id="bookmarkDiv" style="display: none;height:100%;">
        <div class="read_delete"><a href="javascript:void(0)"><img src="${ctx}/static/client/img/read_delete.jpg" alt="关闭" /></a></div>
        <div class="read_book_tit"><em>我的书签</em></div>
        <div id="div2" style="overflow:hidden; height:82%; width:680px; ">
            <!-- 书名 -->
            <div class="read_book_td">${book.bookName }</div>
            <!-- 书签列表 -->
            <%--<dl class="read_book_mark" id="bookmarkList">--%>
            </dl>
        </div>
    </div>
</div>
<div class="read_lishi" id="rateTipDiv" style="display: none;">
    <div class="read_layer"></div>
    <div class="read_layer_box">
        <p id="rateTips"></p>
        <input id="resetPage" type="button" class="read_book_btn1" value="跳转" />
        <input id="cancelResetPage" type="button" class="read_book_btn2" value="取消" />
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/client/js/jsScroll.js"></script>
</body>
</html>
