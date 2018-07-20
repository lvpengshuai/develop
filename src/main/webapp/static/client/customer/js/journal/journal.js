/**
 * Created by zly on 2017-5-26.
 */
$(function () {
    $("#showAll1").click(function () {
        var op = $(this).attr("op");
        if (op == "fls") {
            $("#ckwxless").fadeOut(0);
            $("#ckwxall").fadeIn();
            $(this).html("点击收起");
            $(this).attr('op', 'tre');
        } else {
            $("#ckwxall").fadeOut(0);
            $("#ckwxless").fadeIn();
            $(this).html("显示全部");
            $(this).attr('op', 'fls');
        }
    });

});
function pdfRead(path) {
    var src = "";
    var op = $("#btn-pdf").attr("op");
    if (op == 'read') {
        $.ajax({
            url: appPath + "/readpdf/journal",
            type: "get",
            dataType: "json",
            success: function (result) {
                if (result.code == "1") {
                    layer.open({
                        type: 1 //Page层类型
                        ,
                        area: ['400px', '210px']
                        ,
                        title: '提示'
                        ,
                        shade: 0.4 //遮罩透明度
                        ,
                        anim: -1 //0-6的动画形式，-1不开启
                        ,
                        content: '<div style="padding:40px; text-align:center;"><p style="margin-bottom: 20px"><img src="' + appPath + '/static/client/images/update-error.png"/></p><p class="update-sucess">您还没有登录，请先<a href="javascript:void(0);" style="color: #faa81e" onclick="tipLogin()">登录</a>。</p></div>'
                    });
                } else if (result.code == "2") {
                    layer.open({
                        type: 1 //Page层类型
                        ,
                        area: ['400px', '210px']
                        ,
                        title: '提示'
                        ,
                        shade: 0.4 //遮罩透明度
                        ,
                        anim: -1 //0-6的动画形式，-1不开启
                        ,
                        content: '<div style="padding:40px; text-align:center;"><p style="margin-bottom: 20px"><img src="' + appPath + '/static/client/images/update-error.png"/></p><p class="update-close">' + result.msg + '</p></div>'
                    });
                } else if (result.code == '0') {
                    src = appPath + '/read' + path;
                    document.getElementById("pdfifrm").src = src;
                    $("#pdf-pannel").fadeIn();
                    $("#btn-pdf").html("关闭阅读");
                    $("#btn-pdf").attr('op', 'noread');
                }
            }
        });
    } else {
        src = "";
        $("#pdf-pannel").fadeOut(0);
        $("#btn-pdf").html("阅读全文");
        $("#btn-pdf").attr('op', 'read');
        document.getElementById("pdfifrm").src = src;
    }
}

function downloadpdf(path,id) {
    $.ajax({
        url: appPath + "/download/journal",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == "1") {
                layer.open({
                    type: 1 //Page层类型
                    ,
                    area: ['400px', '210px']
                    ,
                    title: '提示'
                    ,
                    shade: 0.4 //遮罩透明度
                    ,
                    anim: -1 //0-6的动画形式，-1不开启
                    ,
                    content: '<div style="padding:40px; text-align:center;"><p style="margin-bottom: 20px"><img src="' + appPath + '/static/client/images/update-error.png"/></p><p class="update-sucess">您还没有登录，请先<a href="javascript:void(0);" style="color: #faa81e" onclick="tipLogin()">登录</a>。</p></div>'
                });
            } else if (result.code == "2") {
                layer.open({
                    type: 1 //Page层类型
                    ,
                    area: ['400px', '210px']
                    ,
                    title: '提示'
                    ,
                    shade: 0.4 //遮罩透明度
                    ,
                    anim: -1 //0-6的动画形式，-1不开启
                    ,
                    content: '<div style="padding:40px; text-align:center;"><p style="margin-bottom: 20px"><img src="' + appPath + '/static/client/images/update-error.png"/></p><p class="update-close">' + result.msg + '</p></div>'
                });
            } else if (result.code == '0') {
                var src = appPath + '/downloadpdf' + path+"?id="+id;
                window.location.href = src;
            }
        }
    });
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