// 加载 收藏夹
function collectFolder() {

    var iiiPP = $("#iiiPP").val();
    $.ajax({
        url: appPath + "/organize/authorization",
        type: "get",
        data: {
            "name": "收藏",
            dateTime: Math.random(),//随机函数------------------解决ie下兼容问题
            iiiPP: iiiPP,
        },
        dataType: "json",
        async: false,
        success: function (data) {
            if (data.status == 0) {
                var na = username;
                if (na == null || na == "") {
                    return;
                }
                $.ajax({
                    url: appPath + "/user/center/collectFolder",
                    type: 'post',
                    data: {
                        username: username,
                    },
                    success: function (data) {
                        var json = JSON.stringify(data);
                        var data2 = eval(json);
                        var dad = "<h5>请选择一个收藏夹<a href='##' class='addfave'>新建收藏夹</a></h5>"
                            + "<ul id='test'>";
                        var dbd = "";
                        var ddd = "</ul>";

                        for (var i in data2) {
                            var da = "<li>"
                                + "<em style='cursor:pointer' class='folName'id='" + data2[i].foldername + "'>" + data2[i].foldername.substring(0, 17) + "</em>"
                                + "<a href='##' class='favedelete' onclick=" + "folderD('" + data2[i].foldername + "')" + "><img src='/static/client/img/favo_icon3.png'>删除</a>"
                                + "<a href='##' class='changename'><img src='/static/client/img/favo_icon2.png'>重命名</a>"
                                + "<div class='favovhange'>"
                                + "<input  index='" + i + "' type='text'  class='changeinfo' value='" + data2[i].foldername + "' >"
                                + "<input  onclick='folderReName(\"" + data2[i].foldername + "\"," + i + ")' type='button' value='保存' class='changeinfosave'>"
                                + "</div>"
                                + "</li>";
                            dbd = dbd + da;
                        }
                        var div = dad + dbd + ddd;
                        $("#favoFolder").html(div);
                    }
                });
            } else if (data.status == 2) {
                alertify.alert("权限不足");
            }
            if (data.status == 3) {
                alertify.confirm("请先登陆,再进行拼接！！", function (e) {
                    if (e) {
                        window.location.href = encodeURI("/login?SKT=" + window.location.pathname + window.location.search);
                    }
                });
            }
        }
    })

}

//删除时 需要用的收藏夹名字
function folderD(name) {
    folderName = name;
}

//删除收藏文件夹
function folderDelete() {
    $.ajax({
        url: appPath + "/user/center/collectFolderDelete",
        type: 'post',
        data: {
            folderName: folderName,
            username: username,
        },
        success: function (data) {
            var code = data.code;
            if (code == 0) {
                alertify.alert("删除成功");
                collectFolder();
                folderName = "";
                $(".layer").fadeOut();
                $(".layer-shoucang").fadeIn();

            } else {
                folderName = "";
                alertify.alert("删除失败");
            }
        }

    });
}

//收藏夹重命名
function folderReName(oldFolder, i) {
    var newFolder = $("input[index='" + i + "']").val();
    var pattern = /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
    if (!pattern.test(newFolder)) {
        alertify.alert("包含非法字符");
        return;
    }
    if (newFolder == oldFolder) {
        return;
    }
    $.ajax({
        url: appPath + "/user/center/collectFolderReName",
        type: 'post',
        data: {
            folderName: newFolder,
            oldFolderName: oldFolder,
            username: username,
        },
        success: function (data) {
            var code = data.code;
            if (code == 0) {
                alertify.alert("修改成功");
                folderName = "";
                collectFolder();
            } else if (code == 2) {
                folderName = "";
                alertify.alert("该名称已经存在！！！");
            } else {
                folderName = "";
                alertify.alert("修改失败");
            }
        }

    });
}

// 收藏夹添加
function insertFolder() {
    var fName = document.getElementById("addfavetetx").value;
    var pattern = /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
    if (!pattern.test(fName)) {
        alertify.alert("包含非法字符");
        return;
    }
    if (fName == null || fName == "") {
        alertify.alert("请输入收藏夹名称！！！");
        return;
    }
    $.ajax({
        url: appPath + "/user/center/insertFolder",
        type: 'post',
        data: {
            folderName: fName,
            userName: username,
        },
        success: function (data) {
            var code = data.state;
            if (code == 0) {
                alertify.alert("添加成功");
                collectFolder();
                folderName = "";
                $(".layer").fadeOut();
                $(".layer-shoucang").fadeIn();
            } else if (code == 2) {
                folderName = "";
                alertify.alert("该名称已经存在！！！");
            } else {
                folderName = "";
                alertify.alert("添加失败");
            }
        }

    });

}

//添加收藏
function addCollect() {
    var bookcode = $("#bookcode").val();
    // alert(bookcode);
    // var tid(id)  title(标题)  subtitle（副标题）  source（来源）  abs（摘要）  username（用户名）   foldername（文件夹名字）
    var tid = document.getElementById("zid").value;
    var title = document.getElementById("titleA").textContent;
    var subtitle = null;
    var source = document.getElementById("sourceA").value;
    var abs = document.getElementById("absA") == null ? "" : document.getElementById("absA").value;
    if (foName == null) {
        alertify.alert("请选择收藏夹");
        return;
    }
    $.ajax({
        url: appPath + "/user/center/addCollect",
        type: 'post',
        data: {
            tid: tid,
            title: title,
            subtitle: subtitle,
            source: source,
            abs: abs,
            username: username,
            foldername: foName,
            bookcode: bookcode,
        },
        success: function (data) {
            var code = data.state;
            if (code == 0) {
                alertify.alert("添加成功");
                collectFolder();
            } else if (code == 1) {
                alertify.alert("添加失败");
            } else if (code == 2) {
                alertify.alert("该收藏已添加");
            }
        }

    });


}

// 添加关注
$(function () {
    $(".keywordIds").on({
        click: function () {
            console.log("关注");
            // if (username == "") {
            //     alertify.alert("请先登陆再关注！！！！")
            //     return;
            // }
            var iiiPP = $("#iiiPP").val();
            var concern = $(this).attr("value");
            // 添加我得关注
            $.ajax({
                url: appPath + "/organize/authorization",
                type: "get",
                data: {
                    "name": "关注",
                    dateTime: Math.random(),//随机函数------------------解决ie下兼容问题
                    iiiPP: iiiPP,
                },
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data.status == 0) {
                        $.ajax({
                            url: "/user/center/insertConcern",
                            type: 'post',
                            data: {
                                concern: concern,
                                userName: username,
                            },
                            success: function (data) {
                                var code = data.state;
                                if (code == 0) {
                                    alertify.alert("关注成功");
                                } else if (code == 1) {
                                    alertify.alert("关注失败");
                                } else if (code == 30) {
                                    alertify.alert("最大关注词数为30，已超过30！！！！");
                                } else {
                                    alertify.alert("关注失败，请联系管理员");
                                }
                            }

                        });
                    } else if (data.status == 2) {
                        alertify.alert("权限不足");
                    } else if (data.status == 3) {
                        alertify.confirm("请先登陆,再进行关注！！", function (e) {
                            if (e) {
                                window.location.href = encodeURI("/login?SKT=" + window.location.pathname + window.location.search);
                            }
                        });
                    }
                }

            })

        }
    });
    $(".bd-a").hover(function () {
        $(this).addClass("bd-a-h");
    }, function () {
        $(this).removeClass("bd-a-h");
    });
});