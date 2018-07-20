$(function () {
    //学历选择框
    $("#xl").click(function () {
        if ($("#xl ul").hasClass("on")) {
            $("#xl ul").removeClass("on");
        } else {
            $("#xl ul").addClass("on");
        }
    });
    $("#xl ul li").bind("click", function () {
        var val = $(this).text();
        $("#xl span").html(val);
        $("#xlipt").val(val);
        $("#eduValue").text("")
    })

    //根据学历选择专业调用
    $("#choseZy").click(function () {
        majorDialg();
    });
    $("#choseMajor").click(function () {
        majorDialg();
    });

    //清空历史记录
    $(".rubbish").on("click", function () {
        removeHistory(0, 1);
    })
    function majorDialg() {
        var cxl = $("#xlipt").val();
        if (cxl == "") {
            $("#eduValue").text("请先选择学历");
        } else {
            $("#eduValue").text("");
            choseZy();
            layer.open({
                type: 1 //Page层类型
                , title: ''
                , area: ['738px']
                , shadeClose: true
                , shade: 0.4 //遮罩透明度
                , anim: -1 //0-6的动画形式，-1不开启
                , content: $('.layer-pub')
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    //确定按钮回调
                    var value1 = $("#lev1 li.on").attr("value");
                    var value2 = $("#lev2 li.on").attr("value");
                    var value3 = $("#lev3 li.on").attr("value");
                    $(".select-zy").val(value1 + "/" + value2 + "/" + value3);
                    //$(".select-add").removeClass("error").addClass("success");
                    $(".select-add").find(".valid_message").html("");
                    layer.closeAll();

                }
                , btn2: function (index, layero) {
                    layer.closeAll();//取消按钮
                }
            });
        }
    }
    //根据学历选择专业
    function choseZy() {
        $.ajax({
            url: appPath+"/static/client/json/major.json",
            success: function (data) {
                var cxl = $("#xlipt").val();
                var lev1 = $("#lev1");
                var lev2 = $("#lev2");
                var lev3 = $("#lev3");
                var xl = $("#xl");//获取选中的学历
                var temp_html = '';
                if (cxl == "本科以上") {
                    var indexValue = "major-" + (1);
                    var obj = data[0][indexValue];
                } else if (cxl == "本科以下") {
                    var indexValue = "major-" + (2);
                    var obj = data[1][indexValue];
                } else {
                    alert("请先选择您的学历");
                }
                var levOne = function () {
                    $.each(obj, function (i, name) {
                        if (i == 0) {
                            temp_html += "<li class='on' value='" + obj[i].name + "'>" + obj[i].name + "</li>";
                        } else {
                            temp_html += "<li value='" + obj[i].name + "'>" + obj[i].name + "</li>";
                        }
                        lev1.html(temp_html);
                        $("#lev1 li").click(function () {
                            var l = $(this).index();
                            if ($(this).hasClass("on")) {
                                $(this).addClass("on").siblings().removeClass("on");
                                levTwo(l);
                            } else {
                                $(this).addClass("on").siblings().removeClass("on");
                                levTwo(l);
                                levTree(l, 0);
                            }
                        });
                    });
                };
                var levTwo = function (l) {
                    temp_html = "";
                    $.each(obj[l].sub, function (i, name) {
                        if (i == 0) {
                            temp_html += "<li class='on' value='" + name.name + "'>" + name.name + "</li>";
                        } else {
                            temp_html += "<li value='" + name.name + "'>" + name.name + "</li>";
                        }
                    });
                    lev2.html(temp_html);
                    $("#lev2 li").click(function () {
                        $(this).addClass("on").siblings().removeClass("on");
                        var ll = $(this).index();
                        levTree(l, ll);
                    });
                }
                var levTree = function (l, ll) {
                    temp_html = "";
                    $.each(obj[l].sub[ll].sub, function (i, name) {
                        if (i == 0) {
                            temp_html += "<li class='on' value='" + name.name + "'>" + name.name + "</li>";
                        } else {
                            temp_html += "<li value='" + name.name + "'>" + name.name + "</li>";
                        }
                    });
                    lev3.html(temp_html);
                    $("#lev3 li").click(function () {
                        $(this).addClass("on").siblings().removeClass("on");
                    });
                }
                levOne();
                levTwo(0);
                levTree(0, 0);
            }
        });
    }
});