//加载书信息--------------------------------------------------------------------
function getCatalogInfo(book) {
    var eachId = 0;
    var knowledgeId = $("#knowledge_name").text().trim();
    for (var i = 0; i < book.length; i++) {
        var catalog = book[i];
        var tmp = 2;
        //加载第一本书的信息
        $.ajax({
            url: 'book',
            dataType: 'json',
            type: 'get',
            data: {'knowledgeid': knowledgeId, 'propertyname': catalog, page: 1, size: 1},
            success: function (result) {

                //正则表达式过滤
                try {
                    var soContent = result[0].htmlcontent;
                    soContent = htmlRegex(soContent)
                    //正则完成
                    tmp++;
                    var numTmp = tmp - 1;
                    var zhaiyao = soContent;
                    /* if (zhaiyao.length > 200) {
                     zhaiyao = zhaiyao.substring(0, 200);
                     }*/
                    if(eachId==1){
                        //第一条默认展开样式
                        //setTimeout(openFirstCatalog(),1000)
                    }
                    var bookUrl = encodeURI("/knowledge/go?bookname="+ result[0].bookname + "&isbn=" + result[0].isbn)
                    $('#showCatalog').append("<div class='kn-scff' id=" + eachId + "><div class='kn-title'><span class='name layer-h-name'>"
                        + result[0].propertyname
                        + "</span><span class='kn-tp-error layer-error' num=" + "'" + numTmp + "'" + "posturl='http" + tmp + "'>纠错</span><span class='kn-open-dft openmore' op='fls'></span></div>" +
                        "<div  class='kn-scff-main'><div  class='kn-pub-summary' style='height:100px;overflow:hidden' ><p style='text-indent:2em;'>"
                        + soContent + "</p> </div><div class='kn-pub-content' style='display:none;'><p style='text-indent:2em;'>"
                        + soContent
                        + "</p><div class='kn-from'><span class='line'></span><span class='kn-from-name'>取自于<a href='"+bookUrl+  "' target='_blank'>"
                        + result[0].bookname
                        + "</a></span><a class='kn-from-more' onclick='more(\"" + result[0].propertyname + "\")' target='_blank'>显示更多&gt;&gt;</a></div></div></div></div>"
                    );
                    eachId++;
                    //锚点导航
                    $("#maodian").append("<a href='javascript:void(0);'>" + result[0].propertyname + "</a>")

                    //结束
                    //知识元页面模块内容展开关闭
                    $(".openmore").unbind('click').on("click", function () {
                        var op = $(this).attr("op");
                        if (op == "fls") {
                            $(this).parent().parent().find(".kn-pub-summary").fadeOut(0);
                            $(this).parent().parent().find(".kn-pub-content").fadeIn();
                            $(this).addClass("kn-open").removeClass("kn-open-dft");
                            $(this).attr('op', 'tre');

                        } else {
                            $(this).parent().parent().find(".kn-pub-content").fadeOut(0);
                            $(this).parent().parent().find(".kn-pub-summary").fadeIn();
                            $(this).addClass("kn-open-dft").removeClass("kn-open");
                            $(this).attr('op', 'fls');
                        }
                    });

                    //纠错
                    $(".layer-error").unbind('click').on("click", function () {
                        var index = $(this).attr("num");//获取元素的num属性  num为html中固定的数字 在纠错元素上
                        var posturl = $(this).attr("posturl");//post表单提交url 在纠错元素上定义值
                        var title = $(".layer-h-name").eq(index).text();
                        $("#layerError .layer-pub-title span").html(title);
                        layer.open({
                            type: 1 //Page层类型
                            , title: ''
                            , area: ['738px']
                            , shadeClose: true
                            , shade: 0.4 //遮罩透明度
                            , anim: -1 //0-6的动画形式，-1不开启
                            , content: $('#layerError')
                            , btn: ['确定', '取消']
                            , yes: function (index, layero) {
                                referError();
                                layer.closeAll();//取消按钮
                                console.log(posturl);
                                //确定按钮回调
                            }
                            , btn2: function (index, layero) {
                                layer.closeAll();//取消按钮
                            }
                        });
                    });
                    //锚点导航事件
                    //右侧快捷菜单锚点
                    var nav = $(".link-menu"); //得到导航对象
                    var win = $(window); //得到窗口对象
                    var sc = $(document);//得到document文档对象。
                    win.scroll(function () {
                        if (sc.scrollTop() >= 1446) {
                            nav.css({position: "fixed", top: "0px", "z-index": "300"});
                        } else {
                            nav.css({position: "relative"});
                        }
                    });
                    $(".link-menu a").unbind('click').on("click", (function () {
                        var index = $(this).index();
                        var offset = $("#" + index + "").offset();
                        $("html,body").animate({scrollTop: offset.top}, 350);
                    }));
                } catch (e) {
                    return
                }
            }
            //result结束
        })
        //结束
    }
}
function openFirstCatalog() {
    $("#0").children(".kn-scff-main").find(".kn-pub-summary").fadeOut(0);
    $("#0").children(".kn-scff-main").find(".kn-pub-content").fadeIn();
    $("#0").children(".kn-title").children(".openmore").attr("op","tre")
    $("#0").children(".kn-title").children(".openmore").addClass("kn-open").removeClass("kn-open-dft")

}
//加载书信息结束----------------------------------------------------------------

function more(currentCatalog) {
    var name = $("#knowledge_name").text().trim();

    window.open( encodeURI(appPath + "/knowledge/more?&query=" + currentCatalog + "&name=" + name));
}
//按照知识元的栏目分页加载书/右侧聚合书
function getBooks(page, catalog) {
    catalog = catalog.trim()
    var knowledgeid = $("#knowledgeId").text().trim();
    $("#" + catalog).addClass("cur");
    $("#loading").show()
    $("#catalog").text(catalog)
    $("#current").text(catalog);
    var size = $("#page_number").text();
    $("#bookContent").empty();
    $.ajax({
        url: appPath + '/knowledge/book',
        dataType: 'json',
        type: 'get',
        data: {'knowledgeid': knowledgeid, 'propertyname': catalog, page: page, size: size},
        success: function (result) {
            //缓冲样式
            $("#loading").hide()
            if (result.length == 0 || result == "[ ]" || result == undefined || result == null) {
                console.log(result)
                $("#loading").text("抱歉没有数据可以显示")
                $("#loading").show()
                $(".public_page").hide()
            }
            var i = 0;

            $(result).each(function () {
                var soContent = result[i].htmlcontent;
                //正则表达式过滤
                soContent = htmlRegex(soContent)
                //正则完成

                var content = soContent + '';
                var subcontent = content;
                if (content.length > 240) {
                    subcontent = content.trim().replace(/<.*?>/ig, "").substring(0, 240) + "...";
                }
                $("#bookContent").append("<dl> <dt><span>" +
                    result[i].bookname +
                    "</span></dt> <dd> " +
                    "<div class='kn-kz-summary' id='kn-kz-summary" + i + "'> " +
                    subcontent +
                    "</div>" +
                    " <div class='kn-kz-content' style='display:none;'>" +
                    content +
                    "</div> " +
                    "<div class='kn-kz-down kn-kz-more' op='fls' style='margin-top: 10px'>展开</div>" +
                    " </dd> </dl>")

                //知识扩展页面摘要展开收起
                i++;
            });
            //迭代输出结束
            $(".kn-kz-more").click(function () {
                var op = $(this).attr("op");
                if (op == "fls") {
                    $(this).parent().find(".kn-kz-summary").fadeOut(0);
                    $(this).parent().find(".kn-kz-content").fadeIn();
                    $(this).addClass("kn-kz-up").removeClass("kn-kz-down");
                    $(this).html("收起");
                    $(this).attr('op', 'tre');
                } else {
                    $(this).parent().find(".kn-kz-content").fadeOut(0);
                    $(this).parent().find(".kn-kz-summary").fadeIn();
                    $(this).addClass("kn-kz-down").removeClass("kn-kz-up");
                    $(this).html("展开");
                    $(this).attr('op', 'fls');

                }
            });
            $(".kn-kz-menu").height($("#bookContent").height());
        }

    });
}
//按照书的名字加载书
function getBook(page, catalog, bookName, pageNumber) {
    $("#bookName").text(bookName)
    var knowledgeid = $("#knowledgeId").attr("knowledgeId").trim();
    $("#bookContent").empty();
    var size = $("#page_number").text().trim();
    if (pageNumber != null || pageNumber != undefined) {
        size = pageNumber
    }
    $.ajax({
        url: appPath + '/knowledge/book',
        dataType: 'json',
        type: 'get',
        data: {'knowledgeid': knowledgeid, 'propertyname': catalog, "bookname": bookName, "page": page, "size": size},
        success: function (result) {
            //缓冲样式
            $("#loading").hide()
            if (result.length == 0 || result == "[ ]" || result == undefined || result == null) {
                console.log(result)
                $("#loading").text("抱歉没有数据可以显示")
                $("#loading").show()
                $(".public_page").hide()
            }
            var i = 0;
            $(result).each(function () {
                var content = result[i].htmlcontent;
                content = htmlRegex(content)
                var subcontent = content;
                if (content.length > 240) {
                    subcontent = content.trim().replace(/<.*?>/ig, "").substring(0, 240) + "...";
                }
                $("#bookContent").append("<dl> <dt><span>" +
                    result[i].bookname +
                    "</span></dt> <dd> " +
                    "<div class='kn-kz-summary'> " +
                    subcontent +
                    "</div>" +
                    " <div class='kn-kz-content' style='display:none;'>" +
                    content +
                    "</div> " +
                    "<div class='kn-kz-down kn-kz-more' op='fls' style='margin-top: 10px'>展开</div>" +
                    " </dd> </dl>")
                i++;
                //知识扩展页面摘要展开收起
            })
            //迭代输出结束
            $(".kn-kz-more").click(function () {
                var op = $(this).attr("op");
                if (op == "fls") {
                    $(this).parent().find(".kn-kz-summary").fadeOut(0);
                    $(this).parent().find(".kn-kz-content").fadeIn();
                    $(this).addClass("kn-kz-up").removeClass("kn-kz-down");
                    $(this).html("收起");
                    $(this).attr('op', 'tre');
                } else {
                    $(this).parent().find(".kn-kz-content").fadeOut(0);
                    $(this).parent().find(".kn-kz-summary").fadeIn();
                    $(this).addClass("kn-kz-down").removeClass("kn-kz-up");
                    $(this).html("展开");
                    $(this).attr('op', 'fls');

                }
            });
            $(".kn-kz-menu").height($("#bookContent").height());
        }
    });
    ///初始化分页
}

/*提交纠错*/
function referError() {
    debugger
    var knowledgeName = $("#knowledge_name").attr("title");
    var location = $("#layerError div").children("span").text();
    var message = $(".jc-text-area").children("textarea").val();
    $.ajax({
        url: appPath + '/knowledge/review',
        dataType: 'json',
        type: 'get',
        data: {'name': knowledgeName, 'location': location, "message": message},
        success: function (result) {
            layer.open({
                type: 1 //Page层类型
                ,
                area: ['543px', '293px']
                ,
                title: '纠错信息'
                ,
                shade: 0.4 //遮罩透明度
                ,
                anim: -1 //0-6的动画形式，-1不开启
                ,
                content: '<div style="padding:50px; text-align:center;"><p><img src="/static/client/images/update-sucess.png"/></p><p class="update-sucess">发送成功</p></div>'
            });
        }

    })
}

function htmlRegex(msg) {
    var rrr = /(?:<image[^>]*>)(.*?)(?:<\/image[^>]*>)/gi;
    var array = msg.match(rrr);
    if (array != null) {

        for (var q = 0; q < array.length; q++) {

            var replice1 = array[q]
            console.log(replice1)
            replice1 = replice1.replace("<images>", "")
            replice1 = replice1.replace("</images>", "")
            replice1 = replice1.replace("</image>", "")
            replice1 = replice1.replace("<image>", "")
            var imagePath = replice1.split("/")
            msg = msg.replace(replice1, "<img style='width:100%;height:100%' src='/data/" + imagePath[2] + "' />");

        }
    }
    return msg;
}

//分页代码-------------------施工中-----------------------------------------------------------------
$("#five").on("click", function (e) {
    $(".public_page span a").removeClass("page_now");
    $("#five").attr("class", "page_now")
    //获取书
    getBookController();
});
$("#four").on("click", function (e) {
    $(".public_page span a").removeClass("page_now");
    $("#four").attr("class", "page_now")
    getBookController();
});
$("#three").on("click", function (e) {
    $(".public_page span a").removeClass("page_now");
    $("#three").attr("class", "page_now")
    //获取书
    getBookController();
});
$("#two").on("click", function (e) {
    var two = parseInt($("#two").text())
    var pageSize = parseInt($("#pageSize").text())
    $(".public_page span a").removeClass("page_now");
    $("#two").attr("class", "page_now")
    getBookController();
});
$("#one").on("click", function (e) {
    $(".public_page span a").removeClass("page_now");
    $("#one").attr("class", "page_now")
    if (parseInt($("#pageSize").text()) > 5) {
        $("#lastPage").hide()
        $("#nextPage").show()
        $("#two").text(parseInt($("#one").text()) + 1)
        $("#three").text(parseInt($("#one").text()) + 2)
        $("#four").text(parseInt($("#one").text()) + 3)
        $("#five").text(parseInt($("#one").text()) + 4)
    } else {
        $("#nextPage").hide()
        $("#lastPage").hide()
    }
    //获取书
    getBookController();
});
$("#pageSize").on("click", function (e) {

    $("#nextPage").hide()
    $(".public_page span a").removeClass("page_now");
    $("#pageSize").attr("class", "page_now")
    // alert($("#five").text());
    e.stopPropagation();
    if (parseInt($("#pageSize").text()) > 5) {
        $("#one").text(1)
        $("#lastPage").show()
        $("#two").text(parseInt($("#pageSize").text()) - 4)
        $("#three").text(parseInt($("#pageSize").text()) - 3)
        $("#four").text(parseInt($("#pageSize").text()) - 2)
        $("#five").text(parseInt($("#pageSize").text()) - 1)
    }

    //获取书
    getBookController();
});

$("#forcePage").on("click", function (e) {
    //alert($("#pageSize").text());

    var pageSize = $("#pageSize").text();
    var currentPage = parseInt($(".page_now").text())
    var fivePageNum = parseInt($("#five").text())
    if (currentPage < 5) {
        $("#public_page").children("span").children("a").attr("class", "")
        if (currentPage == 1) {
            $("#two").attr("class", "page_now")
        }
        if (currentPage == 4) {
            $("#five").attr("class", "page_now")
        }
        if (currentPage == 3) {
            $("#four").attr("class", "page_now")
        }
        if (currentPage == 2) {
            $("#three").attr("class", "page_now")
        }
        getBookController()
        return
    } else {
    }
    if (fivePageNum == currentPage && (currentPage + 1) < pageSize) {
        $("#one").text(parseInt($("#one").text()) + 1)
        $("#two").text(parseInt($("#two").text()) + 1)
        $("#three").text(parseInt($("#three").text()) + 1)
        $("#four").text(parseInt($("#four").text()) + 1)
        $("#five").text(parseInt($("#five").text()) + 1)
        getBookController();
        return
    }
    if (currentPage + 5 >= pageSize) {
        getBookController();
        return
    }
    if (pageSize > 5 && parseInt($("#five").text()) < pageSize) {
        $("#one").text(parseInt($("#one").text()) + 1)
        $("#two").text(parseInt($("#two").text()) + 1)
        $("#three").text(parseInt($("#three").text()) + 1)
        $("#four").text(parseInt($("#four").text()) + 1)
        $("#five").text(parseInt($("#five").text()) + 1)
    }
    if (pageSize == parseInt($("#five").text()) + 1) {
        $("#nextPage").hide()
    } else {
        $("#nextPage").show()
    }
    //获取书
    getBookController();
});
$("#backPage").on("click", function (e) {
    //alert($("#pageSize").text());
    debugger
    var one = parseInt($("#one").text())
    var two = parseInt($("#two").text())
    var currentPage = parseInt($(".page_now").text())
    var pageSize = $("#pageSize").text();
    if ((two - 2) <= 1) {
        $("#lastPage").hide()
        $("#two").text(2)
        $("#three").text(3)
        $("#four").text(4)
        $("#five").text(5)
        getBookController();
        return
    }
    if (currentPage == pageSize) {
        $("#five").attr("class", "page_now")
        $("#pageSize").attr("class", "")
    }
    if (currentPage == pageSize - 1) {
        $("#four").attr("class", "page_now")
        $("#five").attr("class", "")
    }
    if (currentPage == pageSize - 2) {
        $("#three").attr("class", "page_now")
        $("#four").attr("class", "")
    }
    if (currentPage == pageSize - 3) {
        $("#two").attr("class", "page_now")
        $("#three").attr("class", "")
    }
    if (currentPage == two) {
        $("#nextPage").show()
        $("#two").attr("class", "page_now")
        $("#two").text($("#two").text() - 1)
        $("#three").text($("#three").text() - 1)
        $("#four").text($("#four").text() - 1)
        $("#five").text($("#five").text() - 1)

    }
    if (one <= 1) {
        getBookController();
        return
    }

    if (pageSize > 5 && one >= 1) {
        $("#one").text(parseInt($("#one").text()) - 1)
        $("#two").text(parseInt($("#two").text()) - 1)
        $("#three").text(parseInt($("#three").text()) - 1)
        $("#four").text(parseInt($("#four").text()) - 1)
        $("#five").text(parseInt($("#five").text()) - 1)
    }
    if (pageSize == parseInt($("#five").text()) + 1) {
        $("#nextPage").hide()
    } else {
        $("#nextPage").show()
    }
    //获取书
    getBookController();
});

function getBookController() {
    //使用html标签交换当前数据
    var currentPage = $(".page_now").text()
    var catalog = $("#catalog").text()
    var bookName = $("#bookName").text()
    if (bookName != "" && bookName != undefined && bookName != null) {
        getBook(currentPage, catalog, bookName)
        getPageSize(catalog, bookName);
        return
    }
    getBooks(currentPage, catalog)
    getPageSize(catalog);
}

//控制标签是否显示
function getPageSize(catalog, bookName) {
    debugger
    var knowledgeid = $("#knowledgeId").text().trim();
    $.ajax({
        url: appPath + '/knowledge/count',
        dataType: 'json',
        type: 'get',
        data: {'knowledgeid': knowledgeid, 'propertyname': catalog, "bookname": bookName},
        success: function (result) {
            var count = result.count
            var spilte = $("#page_number").text();
            var pageSize = Math.ceil(count / spilte)
            $("#pageSize").text(pageSize);
            if (pageSize <= 1) {
                $("#public_page").hide();
            }
            if (pageSize <= 5) {
                $("#five").hide();
                $("#nextPage").hide();
                $("#pageSize").hide();
                $("#lastPage").hide();
            }
            if (pageSize <= 4) {
                $("#five").hide();
            }
            if (pageSize <= 3) {
                $("#four").hide();
            }
            if (pageSize <= 2) {
                $("#three").hide();
            }
            if (pageSize < 2) {
                $("#two").hide();
            }
            formatPage();
        }
    });
}
function formatPage() {
    $("#public_page").show()
    var currentPage = parseInt($(".page_now").text())
    var five = parseInt($("#five").text())
    var four = parseInt($("#four").text())
    var three = parseInt($("#three").text())
    var two = parseInt($("#two").text())
    var one = parseInt($("#one").text())
    var maxPage = parseInt($("#pageSize").text())
    {
        $(".page_now").show()
        $("#five").show()
        $("#four").show()
        $("#three").show()
        $("#two").show()
        $("#one").show()
        $("#pageSize").show()
        $("#nextPage").show()
        $("#lastPage").show()
    }
    if (five + 2 >= maxPage) {
        $("#nextPage").hide()
    } else {
        $("#nextPage").show()
    }
    if (two - 2 >= one) {
        $("#lastPage").show()
    } else {
        $("#lastPage").hide()
        $("#one").text(1)
    }
    if (five > 5) {
        $("#lastPage").show()
    }
    if (two == 2) {
        $("#lastPage").hide()
    }
    if (maxPage <= 1) {
        $("#public_page").hide()
    }
    if (maxPage < 2) {
        $(".page_now").hide()
        $("#five").hide()
        $("#four").hide()
        $("#three").hide()
        $("#two").hide()
        $("#one").show()
        $("#pageSize").hide()
        $("#nextPage").show()
        $("#lastPage").show()
    }
    if (maxPage < 3) {
        $(".page_now").hide()
        $("#five").hide()
        $("#four").hide()
        $("#three").hide()
        $("#nextPage").hide()
        $("#lastPage").hide()
        $("#pageSize").hide()
    }
    if (maxPage < 4) {
        $("#pageSize").hide()
        $(".page_now").hide()
        $("#five").hide()
        $("#four").hide()
        $("#nextPage").hide()
        $("#lastPage").hide()
    }
    if (maxPage < 5) {
        $("#pageSize").hide()
        $(".page_now").hide()
        $("#five").hide()
        $("#one").show()
        $("#pageSize").hide()
        $("#nextPage").hide()
        $("#lastPage").hide()
    } else if (currentPage + 1 < maxPage) {
        $("#nextPage").show()
    }
    if (five + 1 == maxPage) {
        $("#nextPage").hide()
    }
    $(".page_now").show()
}
function getBookControllerClick(catalog, bookName) {
    //使用html标签交换当前数据
    var currentPage = $(".page_now").text()
    debugger
    if (bookName != "" && bookName != undefined && bookName != null) {
        getBook(1, catalog, bookName)
        getPageSize(catalog, bookName);
        formatPage();
        return
    }
    getBooks(1, catalog)
    getPageSize(catalog);
    formatPage();
}

//---------------------------------------------------------结束--------------------------------
