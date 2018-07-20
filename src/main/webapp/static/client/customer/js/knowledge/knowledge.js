// JavaScript Document
/**
 * 知识元扩展页面
 */
//知识元扩展页面结束
$(function () {
    getCatalogInfo(name);

    //左侧 知识扩展显示隐藏
    $(".xiala").click(function () {
        $(".zskz ul").eq(1).toggle();
        $(".zskz ul").eq(0).toggle();
    });
    //鼠标滑过图片效果
    $(".knowledge .sku-position").hover(function () {
        $(this).addClass("hover");
    }, function () {
        $(this).removeClass("hover");
    });
    //知识属性信息右侧缩略图点击效果
    $(".knowledge .hover-bg").click(function () {
        var bigpic = $("#source").attr("bigpic");
        $("#layerPic").find("img").attr("src", bigpic);
        layer.open({
            type: 1 //Page层类型
            , title: ''
            , area: ['738px']
            , shadeClose: true
            , shade: 0.4 //遮罩透明度
            , anim: -1 //0-6的动画形式，-1不开启
            , content: $('#layerPic')
        });
    });


    //知识扩展页面分页每页显示多少条
    $(".page-num b").on("click", function (e) {

        if ($(".page-num span").is(":hidden")) {
            $(".page-num span").show();
        } else {
            $(".page-num span").hide();
        }
        $(".page-num span a").click(function () {
            var pagenum = $(this).text();
            $(".page-num b").html(pagenum);
            var catalog = $("#current").text()
            var bookName = $("#bookName").text()
            var id = $("body").attr("id")

            try{
            $("#one").text(1)
            $("#two").text(parseInt(2))
            $("#three").text(parseInt(3))
            $("#four").text(parseInt(4))
            $("#five").text(parseInt(5))
            }catch (e){}
            if (bookName != null && bookName != undefined) {
                getBook(1, catalog, bookName,pagenum);
                getPageSize(catalog,bookName);
                formatPage()
                return
            }
            getBooks(1, catalog,pagenum);
            getPageSize(catalog);
            formatPage()
            e.stopPropagation();
        });
        $(document).one("click", function () {
            $(".page-num span").hide();
        });
        e.stopPropagation();
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
                console.log("sss" + posturl);
                referError();
                layer.closeAll();//取消按钮
                //确定按钮回调
            }
            , btn2: function (index, layero) {
                layer.closeAll();//取消按钮
            }
        });
    });

    //知识扩展页面菜单展开收起———————————————施工中—————————————————————》

    $(".kn-kz-menu dl dt a").click(function () {
        if ($(this).parent("dt").parent("dl").hasClass("cur")) {
            //$(".kn-kz-menu dl dd").removeClass("cur");
            $(this).parent("dt").parent("dl").removeClass("cur");
            $(this).parent("dt").children("img").attr("src", appPath + "/static/client/images/tmp2.png");
        } else {
            $(this).parent("dt").parent("dl").addClass("cur");
            $(this).parent("dt").children("img").attr("src", appPath + "/static/client/images/tmp.png");
            var catalog = $(this).parent("dt").parent("dl").attr("id");
            $(".kn-kz-menu dl dd").removeClass("cur");
            $("#bookName").empty();
            getBookControllerClick(catalog)
        }
    });
    $(".kn-kz-menu dl dt img").click(function () {
        if ($(this).parent("dt").parent("dl").hasClass("cur")) {
            $(this).parent("dt").parent("dl").removeClass("cur");
            $(this).attr("src", appPath + "/static/client/images/tmp2.png");
        } else {
            $(this).parent("dt").parent("dl").addClass("cur");
            $(this).attr("src", appPath + "/static/client/images/tmp.png");
        }
    });
    /* 点击子菜单变蓝 */
    $(".kn-kz-menu dl dd").click(function () {
        $(".kn-kz-menu dl dd").removeClass("cur");
        $(this).addClass("cur");
    });


    //知识扩展页面摘要展开收起
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


});
//加载结束

function tipLogin() {
    $(".userName-user").val("");
    $(".password-pwd").val("");
    $(".checkCode").val("");
    $("#username-error span").text("");
    $("#password-error span").text("");
    $("#code-error span").text("");
    layer.open({
        type: 1 //Page层类型
        , area: ['540px']
        , title: ''
        , shadeClose: true
        , shade: 0.4 //遮罩透明度
        , anim: -1 //0-6的动画形式，-1不开启
        , content: $('.layer-box')
    });
}