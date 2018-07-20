<!--引用弹出层-->
$(function () {
    //头部筛选条件效果
    $(".sr_sort ul li").on('click', function () {
        if ($(this).hasClass("up")) {
            $(this).removeClass("up");
            $(this).addClass("down");
        } else {
            $(this).addClass("up");
            $(this).removeClass("down");
        }
    });
    $(".sr_sort ul li span").hover(function () {
        $(this).addClass("on");
    }, function () {
        $(this).removeClass("on");
    });
    $(".a-s-types .type-li a").click(function () {
        $(".sr_sort ul li").removeClass("up");
        $(".sr_sort ul li").addClass("down");
        var tpyevalue = $(this).text();
        $(".sort_bg").text(tpyevalue);
        return false;//阻止冒泡
    })
    //作品详情列表鼠标滑过样式
    $(".pro-list ul li").hover(function () {
        $(this).addClass("hover");
    }, function () {
        $(this).removeClass("hover");
    });
});
function getResource(type) {
    $.ajax({
        url: appPath + '/author/resource',
        type: 'post',
        data: {
            'type': type,
            'name': $('#author').val(),
            'PageIndex': $('#PageIndex').val(),
            'PageSize': $('#PageSize').val()
        },
        success: function (result) {
            $('.pro-list').html(result);
        }
    })
}

/*分页按钮*/
var pageIndex = $('#PageIndex').val();
function setPage(pageIndex) {
    var count = $('#PageCount').val();
    if (parseInt(pageIndex) < 1) {
        pageIndex = 1
    }
    if (parseInt(pageIndex) > parseInt(count)) {
        pageIndex = count
    }
    $('#PageIndex').val(pageIndex);
    getResource($('#type').val());
}

function resource(type) {
    $('#PageIndex').val("1");
    getResource(type);
}

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