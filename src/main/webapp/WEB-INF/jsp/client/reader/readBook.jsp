<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>${book.bookName } - 在线阅读</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Cache-Control" content="no-siteapp" />

	<link rel="stylesheet" 	href="${ctxStatic }/readBook/assets/css/amazeui.min.css" />
	<link rel="stylesheet" 	href="${ctxStatic }/readBook/assets/css/app.css">

	<!--[if (gte IE 10)|!(IE)]><!-->
	<!--<![endif]-->
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="${ctxStatic }/readBook/assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<!--[if lte IE 8 ]>
	<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://cdn.bootcss.com/modernizr/2.8.3/modernizr.min.js"></script>
	<script src="http://cdn.amazeui.org/amazeui/2.4.2/js/amazeui.ie8polyfill.js"></script>
	<![endif]-->
	<script src="${ctxStatic }/readBook/jquery.cookies.2.2.0.min.js"></script>
	<script src="${ctxStatic }/readBook/layer/layer.js"></script>
</head>
<body class="rb_st_yl">
<!-- <div id="read_help" class="read_help"></div> -->
<div class="reads ms-cf"  style="padding: 5px; margin-top: 10px; background: #BBB; opacity: 0.7;">
	<ul style="margin: 0;">
		<li><a href="${ctx }/book/${book.bookId }.html" ><img	src="${ctxStatic }/readBook/assets/i/menu_back.png" /></a></li>
		<li style="text-align:center;height:38px;line-height:38px"><span>${book.bookName }</span></li>
		<li><a href="javascript:void(0);" onclick="showchapter();"><img src="${ctxStatic }/readBook/assets/i/menu_book.png" /></a></li>
	</ul>
</div>
<div style="padding: 10px">
	<article class="am-article" id="book_content"></article>
</div>
<div class="reads ms-cf"  style="padding: 5px; margin-top: 10px; background: #BBB; opacity: 0.7;">
	<ul style="margin: 0;">
		<li><a href="javascript:void(0);" onclick="getprev();"><img src="${ctxStatic }/readBook/assets/i/prev.png" /></a></li>

		<li><a class="jrsc" href="javascript:void(0);"><span id="span_page">0</span>/<span id="span_page_count">0</span></a></li>
		<li><a href="javascript:void(0);" onclick="getnext();"><img	src="${ctxStatic }/readBook/assets/i/next.png" /></a></li>
	</ul>
</div>
<div id="showfnote" class="showfnote"></div>
<div id="fnote_content" style="display: none;"></div>


<div id="book_chapter" class="book_chapter"></div>
<div class="chapter_menu"></div>

<script type="text/javascript">

    var doctitle = {"text":"${book.bookName}"};
    var docauthor = {"text":"${book.bookAuthors}"};
    var navmap = {"navPoint" : [
        <c:forEach items="${list}" var="item" varStatus="index">
        {"attributes" : {"id" : "${item.id}","playOrder" : "${empty item.playOrder ? '0' : item.playOrder }"},
            "navLabel" : {"text" : "${empty item.text ? '封面' : item.text }"},
            "content" : {"attributes" : {"src" : "${item.src}"}}
        }<c:if test="${not index.last}">,</c:if>
        </c:forEach>
    ]
    };
    document.title = doctitle.text;

    var g_page = 0;
    var g_page_count = ${fn:length(list)};
    var g_chapter_isshow = 0;

    function showchapter() {
        g_chapter_isshow = 1;

        $('#set_menu').hide();
        $('#book_chapter').show();
        $('#book_chapter').animate({
            left : 0
        }, "slow");
    }

    function hidechapter() {
        g_chapter_isshow = 0;
        $('#book_chapter').animate({
            left : '-90%'
        }, "slow");
        $('#book_chapter').hide();
    }

    function getnext() {
        if (g_page < (g_page_count - 1)) {
            g_page++;
            loadpage(g_page);
        } else {
            return;
        }
    }

    function getprev() {
        if (g_page > 0) {
            g_page--;
            loadpage(g_page);
        } else {
            return;
        }
    }

    function loadpage(page) {
        var index = layer.load();
        var src = navmap.navPoint[page].content.attributes.src;
        $("#book_content").load("${ctx}/book/reader/readChapter", {
            hrefsrc : src,
            bookUrl:'${bookUrl}'
        }, function(data) {
            layer.close(index);
            g_page = page;
            $("#curr_title").text(navmap.navPoint[page].navLabel.text);
            $("#span_page").text(g_page);
            $("#book_chapter li").removeClass("active");
            $("#book_chapter li#ch_li_" + g_page).addClass("active");
            //$("#doc-dropdown-js").dropdown('close');

            $('#bot_menu').hide();
            $('#set_menu').hide();
            hidechapter();
            document.body.scrollTop = 0;

            $.cookie('product_${book.bookId}', g_page, {
                expires : 3650
            });
        });
    }

    function setreadstyle() {
        $('#set_menu').toggle();
    }

    $(function() {

		/* $("#read_help").click(function() {
		 $("#read_help").hide();
		 }); */
        $("#span_page_count").text(g_page_count - 1);
        if(g_page==0){
            loadpage(g_page);
        }

        $.each(navmap.navPoint,function(i,key){
            var label = key.navLabel.text;
            var item = "<a href='javascript:void(0);' onclick='loadpage("
                + i
                + ");'><li id='ch_li_"+i+"'>"
                + label
                + "</li></a>";
            $("#book_chapter").append(item);
        });

		/* for ( var key in navmap.navPoint) {
		 var label = navmap.navPoint[key].navLabel.text;
		 var item = "<a href='javascript:void(0);' onclick='loadpage("
		 + key
		 + ");'><li id='ch_li_"+key+"'>"
		 + label
		 + "</li></a>";
		 $("#book_chapter").append(item);
		 } */

        $('#book_content').click(function(e) {
            e = e || event;
            var t = e.target || e.srcElement
            var _t = t.parentNode.tagName.toLowerCase();

            var evt = window.event || arguments.callee.caller.arguments[0];

            if (evt.clientX > window.innerWidth / 3 * 1
                && evt.clientX < window.innerWidth / 3 * 2
                && _t != 'a') {
                $('#top_menu').toggle();
                $('#bot_menu').toggle();
                $('#set_menu').hide();
            }
            if (evt.clientX < window.innerWidth / 3 * 1
                && _t != 'a') {
                document.body.scrollTop = document.body.scrollTop
                    - document.documentElement.clientHeight
                    - 100;
            }
            if (evt.clientX > window.innerWidth / 3 * 2
                && _t != 'a') {
                document.body.scrollTop = document.body.scrollTop
                    + document.documentElement.clientHeight
                    - 100;
            }
        });

        $('#btn_li_pre').click(function(e) {
            getprev();
        });

        $('#btn_li_next').click(function(e) {
            getnext();
        });

        $('#btn_li_showtopmenu').click(function(e) {
            $('#set_menu').toggle();
        });

        //显示/隐藏目录
        $("#btn_tag_menu").on('click', function() {
            if (0 == g_chapter_isshow)
                showchapter();
            else {
                hidechapter();
            }
        });
        //设置阅读背景
        $(".set_menu li span").on("click",function() {
            var readstyle = $(this).attr('class');
            $(this).parent().siblings().find("strong")
                .removeClass('box_slt');
            $(this).find("strong").attr('class', 'box_slt');
            $("body").attr("class", readstyle);
        });
        //增加字体
        $("#jiazi").on("click", function() {
            var thisEle = $("#book_content").css("font-size");
            var textFontSize = parseFloat(thisEle, 10);
            //javascript自带方法
            var unit = thisEle.slice(-2); //获取单位
            textFontSize += 2;
            //设置para的字体大小
            $("#book_content").css("font-size", textFontSize + unit);
        });
        //减小字体
        $("#jianzi").on("click", function() {
            var thisEle = $("#book_content").css("font-size");
            var textFontSize = parseFloat(thisEle, 10);
            //javascript自带方法
            var unit = thisEle.slice(-2); //获取单位
            textFontSize -= 2;
            //设置para的字体大小
            $("#book_content").css("font-size", textFontSize + unit);
        });
        //白天阅读背景
        $("#baitian").on("click", function() {
            $(".set_menu li").find("strong").removeClass('box_slt');
            $("body").attr("class", "rb_st_h");
        });
        //黑夜阅读背景
        $("#heiye").on("click", function() {
            $(".set_menu li").find("strong").removeClass('box_slt');
            $("body").attr("class", "rb_st_z");
        });

        //读取本地相关信息
        if (typeof ($.cookie('product_${book.bookId}')) != 'undefined') {
            g_page = parseInt($.cookie('product_${book.bookId}'));
            if(g_page>0){
                loadpage(g_page);
            }
        }

		/* if (typeof ($.cookie('read_help')) == 'undefined') {
		 $('#read_help').show();
		 $.cookie('read_help', "yes", {
		 expires : 3650
		 });
		 } */
    });
</script>
</body>
</html>
