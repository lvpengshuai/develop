<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--<%@ include file="/WEB-INF/views/include/taglib.jsp"%>--%>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>${book.bookName } - 在线阅读</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<meta name="apple-mobile-web-app-capable" content="yes">
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/jquery.cookie.js"></script>
    <script type="text/javascript"  src="${ctx}/static/client/js/jquery.fullscreen-min.js"></script>
    <link href="${ctx}/static/client/css/book.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/client/css/reader.css" rel="stylesheet" type="text/css" />
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


//var server_url = ${resourceUri}${book.platform};

function doUpdateHilighter(){
	$.ajax({
		type : "POST",
		url : "${ctx}/book/webreadata/update",
		data:{
			userId : rangy_user_id,
			bookId : rangy_book_id,
			chapterUrl : rangy_chapterUrl,
			hilighterString : rangy_hilighter_string
		}
	});
}

function doUpdateNote(txt_note,txt_select){
	$.ajax({
		type : "POST",
		url : "${ctx}/book/webreadata/update",
		data:{
			userId : rangy_user_id,
			bookId : rangy_book_id,
			chapterUrl : rangy_chapterUrl,
			noteText : txt_note,
			selectText : txt_select,
			hilighterString : rangy_hilighter_string
		}
	});
}

//document.domain = "http://localhost:8080";
var reader;

document.onreadystatechange = function() {
	if (document.readyState == "complete") {
		EPUBJS.filePath = "${ctx}/static/client/reader/js/libs/";
		EPUBJS.cssPath = "${ctx}/static/client//reader/css/";
		<%--EPUBJS.readDataPath = "${ctx}/static/client//book/webreadata/get";--%>
		EPUBJS.isReadData = true;
		// fileStorage.filePath = EPUBJS.filePath;
        reader = ePubReader("${ctx}/cssp_resource/epub/${book.bookCode}/${book.platform}/${readPath}/");
	}
};
</script>

<!-- Render -->
<script src="${ctx}/static/client/reader/epub.js"></script>
<!-- Hooks -->
<script src="${ctx}/static/client/reader/hooks.js"></script>
<!-- Reader -->
<script src="${ctx}/static/client/reader/reader.js"></script>
<!-- File Storage -->
<!-- <script src="js/libs/fileStorage.min.js"></script> -->
<!-- Full Screen -->
<script src="${ctx}/static/client/reader/libs/screenfull.min.js"></script>
<script type="text/javascript">

var bookId = "${book.bookId}";
var starLevel = -1;
var onceLoad= true;
var src="${book.src}";
// 获取目录下的内容 查找需要跳转得目录
function gotoPage1() {
    if (reader.book.totalPages != null && onceLoad) {
        onceLoad=false;
        reader.book.goto(src);
    }
}
$(document).ready(function() {
    window.setInterval("gotoPage1()",100);

    //在线阅读字号
    $(document).ready(function(){
        $(".readBoxright dd").hover(function(){
            $(this).children("ul").toggle();                         
        })                           
    })
    $("#readMode").click(function() {
        $.cookie('readMode', 'ver');
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
    $("#bookMarkBtn").click(function() {
        if (reader) {
            alert( reader.BookmarksController);
            reader.BookmarksController && reader.BookmarksController.show();
        }
    });
    $("#commentBtn").click(function() {
        reader.CommentController && reader.CommentController.show();
    });
    $(".read_delete").click(function(){
        if (reader) {
        	$("#buyDiv").hide();
            reader.TocController && reader.TocController.hide();
            reader.BookmarksController && reader.BookmarksController.hide();
            reader.CommentController && reader.CommentController.hide();
        }
    });
    $("#fontSizeSet li").click(function() {
        switch($(this).index()) {
        case 0:
            if (reader.settings.styles.fontSize != "70%") {
                if (!reader.fontSizeMap["70%"]) {
                    reader.book.setStyle("fontSize", "70%");
                    reader.book.calcBookPages();
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
                } else {
                    restorePage("100%");
                }
            }
            break;
        case 2:
            if (reader.settings.styles.fontSize != "130%") {
                if (!reader.fontSizeMap["130%"]) {
                    reader.book.setStyle("fontSize", "130%");
                    reader.book.calcBookPages();
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
    
    $("#contentDiv").mouseover(function() {
    	$("#prevPage").parent().show();
    	$("#nextPage").parent().show();
    }).mouseout(function() {
    	$("#prevPage").parent().hide();
    	$("#nextPage").parent().hide();
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

    $(window).resize(resizeHandler);
    resizeHandler();
});

function gotoPage() {
	var val = parseInt($("#pageNum").val());
	if (isNaN(val) || val < 0 || val > reader.book.totalPages * 2) {
		$("#pageNum").val("");
	} else {
		reader.book.gotoPage(Math.floor(val/2));
	}
}

function resizeHandler() {
    $("#div3").height($(document.body).height() - ${book.owner ? 380 : 160});
}
</script>
<style type="text/css">
<!--

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


body{
 margin:0px;
 padding:10px 0 0 0; 
 background-color: #ffffff;
 background:url(${ctx}/static/client/img/read_bg.jpg) repeat left top;
}
html, body {
 height:100%;
 overflow: hidden;
}
html>body{ 
 width: auto;
 height: auto;
 position: absolute;
 top: 0px;
 left: 0px;
 right: 0px;
 bottom: 0px;
}
#mainDiv {
 width: 100%;
 height: 100%;
 padding:56px 0px
}
#centerDiv{
 width: 100%;
 height: 100%;
}
#topDiv{
 width:100%;
 height:56px;
 position:absolute;
 top:0px;
}
#bottomDiv{
 width:100%;
 height:56px;
 position:absolute;
 bottom:0px;
 bottom:10px; 
}
.readBoxCenterBox_top{ float:left; width:100%}
.readBoxCenterBox_top_l{ float:left; width:50%}
.readBoxCenterBox_top_r{ float:right; width:50%; background:#f0e9d9}
#readBoxTwo .read_book_sc{ width:87%} 
.readBoxCenterBoxContent{ position:relative}
.readBoxCenterBox{
 margin: 0 130px 20px 130px;
 height: 88%;
}
.readBoxleft dl,dd {
 margin: 0;
 /*padding: 0;*/
}
.icon-bookmark-empty {
    display: inline-block;
	width: 22px;
	height: 22px;
    background-image: url("${ctx}/static/client/img/book_ico_sq1.png");
    cursor: pointer;
}
.icon-bookmark-empty1 {
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
.read_book_mark dd h5{
  text-align: left;
}
-->
</style>
</head>
<body>
<div id="mainDiv">
    <div id="topDiv">
         <div id="readBoxThere">
             <!-- 右侧导航 -->
             <div class="readBoxright" style="width: auto; right: 143px;">
                 <dl>
                     <dd><a href="javascript:void(0)" id="fullScreenBtn" title="全屏"><img src="${ctx}/static/client/img/read_ico_2.png" alt="全屏" /></a></dd>
                    <dd><span style="height: 30px;line-height: 30px;">全屏</span></dd>
           
                     <c:if test="${empty(onlyReadMode) }">
                     <dd><a href="javascript:void(0)" id="readMode" title="单页"><img src="${ctx}/static/client/img/read_ico_10.png" alt="单页"/></a></dd>
					 <dd><span style="height: 30px;line-height: 30px;">单页</span></dd>
           
                     </c:if>
                     <dd><a href="javascript:void(0)" title="字体大小"><img src="${ctx}/static/client/img/read_ico_4.png" alt="字体大小" /></a>
                         <ul style="left: -39px; top: 25px;" id="fontSizeSet">
                             <li><a href="javascript:void(0)">小</a></li>
                             <li><a href="javascript:void(0)">中</a></li>
                             <li><a href="javascript:void(0)">大</a></li>
                         </ul>
                     </dd>
                      <dd><span style="height: 30px;line-height: 30px;">字体</span></dd>
           
           
                     <dd><a href="javascript:void(0)" id="catalogBtn" title="目录"><img src="${ctx}/static/client/img/read_ico_5.png" alt="目录" /></a></dd>
                      <dd><span style="height: 30px;line-height: 30px;">目录</span></dd>
            
            
                     <%--<dd><a href="javascript:void(0)" id="bookMarkBtn" title="书签"><img src="${ctx}/static/client/img/read_ico_6.png" alt="书签" /></a></dd>--%>
                     <%--<dd><span style="height: 30px;line-height: 30px;">书签</span></dd>--%>
            
                     
                     <%--<dd><a href="javascript:void(0)" id="commentBtn" title="评论"><img src="${ctx}/static/client/img/read_ico_7.png" alt="评论" /></a></dd>--%>
                     <%--<dd><span style="height: 30px;line-height: 30px;">评论</span></dd>--%>
            
                </dl>
            </div>
        </div>    
    </div>
 
    <div id="centerDiv" style="padding-bottom: 20px;">
         <div id="readBoxTwo" style="height: 100%">
             <!-- 中间内容 -->
             <div class="readBoxCenterBox"  id="contentDiv" style="min-width: 850px; width: 80%">
                 <!-- 中间顶部 -->
                 <div class="readBoxCenterBox_top">
                     <!-- 书名 -->
                     <div class="readBoxCenterBox_top_l">
                         <div class="read_book_tit"><span style="float:left;" class="chapterTitle"></span><span id="currentPage1"></span></div>
                         <div class="read_book_sc">
                             <span id="bookmark" class="icon-bookmark-empty1"></span>
                         </div>
                     </div>
                     <!-- 书名 -->
                     <div class="readBoxCenterBox_top_r">
                         <div class="read_book_tit"><span style="float:left;" class="chapterTitle"></span><span id="currentPage2"></span></div>
                         <div class="read_book_sc">
                             <span id="bookmark2" class="icon-bookmark-empty"></span>
                         </div>
                     </div>
                 </div>
    
                 <!-- 中间内容 -->
                 <div class="readBoxCenterBoxContent" style="background: transparent;width: 100%; height: 95%;overflow: hidden;">
                     <div class="readBoxCenter" id="readbox_left"></div><!-- 背景 -->
                     <div class="readBoxCenter" id="readbox_right"></div><!-- 背景 -->
                     <div id="viewer" style="position: absolute;top: 0; left: 1%; right: 1%"></div>
			         <div id="loader"><img src="${ctx}/static/client/img/loader.gif"></div>
                 </div>
                 
                 <!-- 上一页下一页 -->
                 <div class="read_book_pre" style="display: none;"><a href="javascript:void(0)" id="prevPage" title="上一页"></a></div>
                 <div class="read_book_next" style="display: none;"><a href="javascript:void(0)" id="nextPage" title="下一页"></a></div>
            </div>
            <div class="readBoxCenterBox" id="catalogDiv" style="display: none;height:100%; min-width: 850px;">
		    	<!-- 删除 -->
		        <div class="read_delete"><a href="javascript:void(0)"><img src="${ctx}/static/client/img/read_delete.jpg" alt="关闭" /></a></div>
		        <div class="read_book_tit"><em>目录</em></div>
		        <div id="div1" style="overflow:hidden; height:70%; width:95%; ">
		            <!-- 书名 -->
		        	<div class="read_book_td">${book.bookName }</div>
		            <dl class="read_book_ml" id="tocViewList" style="width: 100%;">
		            	<!-- 目录 -->
		            </dl>
		        </div>
		    </div>
		    <div class="readBoxCenterBox" id="bookmarkDiv" style="display: none;height:100%; min-width: 850px;">
		        <div class="read_delete"><a href="javascript:void(0)"><img src="${ctx}/static/client/img/read_delete.jpg" alt="关闭" /></a></div>
		        <div class="read_book_tit"><em>我的书签</em></div>
		        <div id="div2" style="overflow:hidden; height:70%; width:95%; ">
		            <!-- 书名 -->
		            <div class="read_book_td">${book.bookName }</div>
		            <!-- 书签列表 -->
		            <%--<dl class="read_book_mark" id="bookmarkList" style="width: 100%;">--%>
		            </dl>
		        </div>
		    </div>
			<!-- 中间部分结束 -->
        </div>
    </div>

    <!-- 底部 -->
    <div id="bottomDiv">
        <!-- 当前页 -->
        <div class="readBookPage2" style="left: 5px;width: auto;">
            <div class="readBookPageone"  id="gotoBar" style="display: none;width: 105px;">
                <a href="javascript:void(0)">
                    <img src="${ctx}/static/client/img/read_page_next.jpg" id="gotoSubmit"/>
                </a>
                                                        当前页<input id="pageNum" style="width: 30px; background-color: #464646; height: 18px; line-height: 18px;border: none;color: white;text-align: center;margin-left: 10px;"/>
            </div>
            <div class="readBookPagetwo">
                <span id="progressTitle2">1 / 1 100%</span>
                <a href="javascript:void(0)" id="setPage"><img src="${ctx}/static/client/img/read_page_pre.jpg"/></a>
            </div>
        </div>
    </div>
    <div class="read_book_jd2"><div style=" background:#000; width:100%; height:100%" id="progressBar"></div></div>
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
