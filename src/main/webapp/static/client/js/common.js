// JavaScript Document
//tab
$(function () {
    //首页图片切换
    if ($(".course-slide").length != 0) {
        $(".course-slide .flexslider").flexslider({
            animation: "slide"
        });
    }
    //首页弹出图片切换
    if ($(".layer-slide").length != 0) {
        var myFlexslider = $(".layer-slide .flexslider").flexslider({
            animation: "slide",                // 必备参数，自动滑动
        });
        $('.layershow').click(function () {
            myFlexslider.flexslider($(this).index());
        });
    }
    //图表荟萃图片切换
    if ($(".iframe-slide").length != 0) {
        var myFlexslider = $(".iframe-slide .flexslider").flexslider({
            animation: "slide"
        });
    }
    function tabs(tabTit, on, tabCon) {
        $(tabCon).each(function () {
            $(this).children().eq(0).show();
        });
        $(tabTit).each(function () {
            $(this).children().eq(0).addClass(on);
        });
        $(tabTit).children().click(function () {
            $(this).addClass(on).siblings().removeClass(on);
            var index = $(tabTit).children().index(this);
            $(tabCon).children().eq(index).show().siblings().hide();
        });
    }
    tabs(".tab-hd", "active", ".tab-bd");
    $(".close").on("click", function () {
        $(".layer").fadeOut();
    });
    //展开高级检索
    $(".click-highsearch").click(function () {
        $(".layer-highsearch").fadeIn();
    });
    //展开收藏
    $(".click-shoucang").click(function () {
        var iiiPP=$("#iiiPP").val();
        // if(username==null||username==""){
        //     alertify.alert("请先登陆,再收藏！");
        // }else {
            $.ajax({
                url: appPath + "/organize/authorization",
                type: "get",
                data: {
                    "name": "收藏",
                    dateTime: Math.random(),//随机函数------------------解决ie下兼容问题
                    "iiiPP":iiiPP,
                },
                dataType: "json",
                async: false,
                success:function (data) {
                    if (data.status == 0){
                        $(".layer-shoucang").fadeIn();
                    }
                    // else{
                    //     alertify.alert("请先登陆,再收藏！");
                    // }
                }
            })

        // }

    });
    //取消收藏
    $(".btn-cancle").on("click", function () {
        $(".layer").fadeOut();
    });
    //重命名
    $("body").delegate(".changename","click", function(){
        $(this).next(".favovhange").show();
    });
    $("body").delegate(".changeinfosave","click", function(){
        $(".favovhange").hide();
    });
    //删除
    $("body").delegate(".favedelete","click", function(){
        $(".layer").fadeOut();
        $(".layer-favedelete").show();
    });
    $("body").delegate(".newclose","click", function(){
        $(".layer-shoucangjia").hide();
    });
    //删除 拼接
    $("body").delegate(".splicedelete","click", function(){
        $(".layer").fadeOut();
        $(".layer-splicedelete").show();
    });
    //新建收藏夹
    $("body").delegate(".addfave","click", function(){
        $(".layer").fadeOut();
        $(".layer-shoucangjia").fadeIn();
    })


//首页底部弹出
    $(".layershow").click(function () {
        $(".indexlayer").delay(100).animate({height: '830px', opacity: '1'}, 500);
    });
    $(".indexclose").click(function () {
        $(".indexlayer").delay(100).animate({height: '0', opacity: '0'}, 500);
    });
    //目录对比阅读
    $(".fenxitit").click(function () {
        $(this).toggleClass("active");
        $(this).next(".fenxidl").slideToggle();
    });
    //拼接阅读
    $(".click_pj").click(function () {
        // if(username==''||username.length==0){
        //     return;
        // }
        $(".layer_pj").show();
    });
    $(".pj_close").click(function () {
        $(".layer_pj").hide();
    });

    //删除
    $(".favedelete").click(function(){
        $(".layer-favedelete").show();
    });

    $(".newclose, .btn-cancle").click(function(){
        $(".layer-favedelete, .layer-renwu").hide();
    });

    $(".yearbox em").click(function () {
        $(this).parent(".yearbox").toggleClass("yearboxon");
    });



//引用格式
    $(".wenxiangeshi em").click(function () {
        $(this).next("ul").toggle();
    });

//展开目录
    $(".infolist dd h3 a").click(function () {
        var classIndex = $(this).attr("index");
        $(".layer-mulu-" + classIndex).fadeIn();
    });



    //分析页面切换
    $(".click01").click(function(){
        $(".clickshow01").addClass("show");
        $(".clickshow02, .clickshow03, .clickshow04").removeClass("show");
    });
    $(".click02").click(function(){
        $(".clickshow02").addClass("show");
        $(".clickshow01, .clickshow03, .clickshow04").removeClass("show");
    });
    $(".click03").click(function(){
        $(".clickshow03").addClass("show");
        $(".clickshow02, .clickshow01, .clickshow04").removeClass("show");
    });
    $(".click04").click(function(){
        $(".clickshow04").addClass("show");
        $(".clickshow02, .clickshow01, .clickshow03").removeClass("show");
    });
    $(".tab-hd-center li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
    });


});
//1026会议
$(".activemore").click(function(){
    $(this).prev(".activeinfo").toggleClass("active");
    $(this).children("b").toggle();
});
// //人物相关作品
// $(".renwu dl dd a").click(function(){
//     $(".layer-renwu").show();
// });
$(".yearbox em").click(function () {
    $(this).parent(".yearbox").toggleClass("yearboxon");
});


$(".subbaner-prev em").click(function () {
    $(this).hide();
    $(this).next("i").show();
    $(".subbox03, .subbox01").hide();
    $(".subbox04, .subbox02").show();
});
$(".subbaner-prev i").click(function () {
    $(this).hide();
    $(this).prev("em").show();
    $(".subbox04, .subbox02").hide();
    $(".subbox03, .subbox01").show();
});




//图表荟萃列表切换
$(".graph01-click").click(function () {
    $(".graph01").show();
    $(".graph01-click").addClass("graphshow");
    $(".graph02-click").removeClass("graphshow");
    $(".graph02").hide();
});
$(".graph02-click").click(function () {
    $(".graph02").show();
    $(".graph02-click").addClass("graphshow");
    $(".graph01-click").removeClass("graphshow");
    $(".graph01").hide();
});

//登录
$(".logtext").focus(function () {
    $(this).parent("dd").addClass("ddshow");
});
$(".logtext").blur(function () {
    $(this).parent("dd").removeClass("ddshow");
});


//阅读页
$(".readlist li > a").click(function () {
    $(this).addClass("active");
    $(this).parent("li").siblings().children("a").removeClass("active");
    $(this).next("ul").slideDown();
    $(this).parent("li").siblings().children("ul").slideUp();
});

//数据中心
$(".datalist li > a").click(function () {
    $(this).addClass("active");
    $(this).parent("li").siblings().children("a").removeClass("active");
    $(this).next("ul").slideDown();
    $(this).parent("li").siblings().children("ul").slideUp();
});


//更多年鉴
$(".moreinfo em").click(function () {
    $(this).parent(".moreinfo").toggleClass("moreinfoactive");
});
$(".moreboxstyle .moreinfo:gt(2)").hide();
$(".morechose em").click(function () {
    $(".moreinfo:gt(2)").show();
});


//图表荟萃列表弹出
$(".graph-close").click(function () {
    $(".graphlayer").hide();
});
$(".graph-list1 dd a").click(function () {
    $(".graphlayer").show();
    // $("#graphshow").attr("src", "slide01.jsp");
});
//图表荟萃图片弹出
$(".graph-list2 dd a").click(function () {
    $(".graphlayer").show();
    // $("#graphshow").attr("src", "slide02.jsp");
});
