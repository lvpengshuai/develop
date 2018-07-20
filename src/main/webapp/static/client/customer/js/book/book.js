// 加载 收藏夹
function collectFolder() {
    $.ajax({
        url: appPath + "/user/center/collectFolder",
        type: 'post',
        data: {},
        success: function (data) {
            var json = JSON.stringify(data);
            var data2 = eval(json);
            var dad = " <h5>请选择一个收藏夹<a href='#'>新建收藏夹</a></h5>"
                + "<ul>";

            var dbd = "";
            var ddd = "</ul>";

            for (var i in data2) {
                var da = "<li>"
                    + "<em>" + data2[i].foldername + "</em>"
                    // +"<a  style='padding-left:0px;float: left;' onclick="+"collectShow('"+data2[i].foldername+"')"+">"+data2[i].foldername.substring(0,7)+"</a>"
                    + "<a  onclick=" + "folderDelete('" + data2[i].foldername + "')" + "><img src='/static/client/img/folder_icon3.png'>删除</a>"
                    + "<a  onclick=" + "folderReName('" + data2[i].foldername + "')" + "><img src='/static/client/img/folder_icon2.png'>重命名</a>"
                    + "</li>";
                dbd = dbd + da;
            }
            var div = dad + dbd + ddd;
            $("#favoFolder").html(div);
        }

    });

}

//删除收藏文件夹
function folderDelete(folderName) {
    $.ajax({
        url: appPath + "/user/center/collectFolderDelete",
        type: 'post',
        data: {
            folderName: folderName,
        },
        success: function (data) {
            var code = data.code;
            if (code == 0) {
                alert("删除成功");
                collectFolder();
            } else {
                alert("删除失败");
            }
        }

    });


}

//收藏夹重命名
function folderReName(folderName) {
    // $.ajax({
    //     url: appPath +"/user/center/collectFolderReName",
    //     type: 'post',
    //     data: {
    //         folderName:folderName,
    //     },
    //     success: function (data) {
    //         var code = data.code;
    //         if (code == 0) {
    //             alert("删除成功");
    //             collectFolder();
    //         } else {
    //             alert("删除失败");
    //         }
    //     }
    //
    // });
}

//添加收藏
function addCollect() {
    // tid(id)  title(标题)  subtitle（副标题）  source（来源）  abs（摘要）  username（用户名）   foldername（文件夹名字）
    // $.ajax({
    //     url: appPath +"/user/center/addCollect",
    //     type: 'post',
    //     data: {
    //         tid:tid,
    //         title:title,
    //         subtitle:subtitle,
    //         source:source,
    //         abs:abs,
    //         username:username,
    //         foldername:foldername,
    //     },
    //     success: function (data) {
    //         var code = data.code;
    //         if (code == 0) {
    //             alert("添加成功");
    //             collectFolder();
    //         } else {
    //             alert("添加失败");
    //         }
    //     }
    //
    // });

}

// 图表荟萃
function setgraphshow1() {
    $(".graphlayer").show();
}

var PageCount = null;
var PageIndex = null;
var bookcodes = null;
var currPages = null;

function collectShow(bookcode, PageIndex, currPage) {
    bookcodes = bookcode;
    currPages = currPage;
    $.ajax({
        url: appPath + "/book/getBookFile18",
        type: 'post',
        data: {
            bookcode: bookcode,
            PageIndex: PageIndex,
            currPage: currPage,
            likeString: $("#inputId").val()
        },
        success: function (data) {
            var json = JSON.stringify(data[0] != "" ? data[0] : 0);

            PageCount = Number(data[1]);
            PageIndex = Number(data[2]);
            var data2 = eval(json);
            var dad = "<dl class='graph-list1'>";
            var dbd = "";
            var dcd = " </dl>";

            var pageOne = "<div class='user_pre_next'>";
            var pageTree = "</div>";
            for (var i in data2) {
                //var absss=cutstr(data2[i].abs,205);
                var titleNew = data2[i].title.replace("<font style='color:red'>", "");
                titleNew = titleNew.replace("</font>", "");
                var da = "<dd style='overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'>" +
                    "<a href='/book/getBookFileImage18?bookcode=" + bookcode + "&pageIndex=" + PageIndex + "&likeString=" + $("#inputId").val() + "&indexNum=" + i + "' target='myFrameName' onclick='setgraphshow1()' title=''>" + data2[i].title + "</a>" +
                    "</dd>";
                dbd = dbd + da;
            }
            var pgj = PageIndex - 1;
            var pgjj = PageIndex + 1;
            var pcj = PageCount - 1;
            var pcjj = PageCount + 1;

            var pa = "<div class='pre_next' onselectstart='return false'><span><a onclick='setPage(" + pgj + ")' class='pre'>&lt; 上一页</a></span>";
            var q = "";
            var w = "";
            var e = "";
            var r = "";
            var qq = "";
            var ww = "";
            var ee = "";
            var rr = "";
            var qqq = "";
            var www = "";
            var eee = "";
            var aaa = "";
            var sss = "";
            if (PageCount > 1 && PageCount <= 5) {
                for (var i = 1; i < pcjj; i++) {
                    if (i == PageIndex) {
                        q += "<a id='a_" + PageIndex + "' onclick='return false;' href='javascript:void(0);' class='cur'>" + PageIndex + "</a>";
                    } else {
                        q += "<a id='a_" + i + "' onclick='setPage(" + i + ");return false;' href='javascript:void(0);' >" + i + "</a>";
                    }
                }
            }
            if (PageCount > 5) {
                if (PageIndex < 4) {
                    for (var i = 1; i < pgj; i++) {
                        w += "<span><a id='a_" + i + "' onclick='setPage(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    e = "<span><a id='a_" + PageIndex + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex < 100) {
                        r = "class='cur'";
                    }
                    qq = ">" + PageIndex + "</a></span>";
                } else {
                    ww = "<span><a id='a_1' onclick='setPage(1);return false;' href='javascript:void(0);'>1</a></span>"
                        + "<span><a  onclick='return false;' style='cursor: default;'>...</a></span>";
                    for (var i = pgj; i < PageIndex; i++) {
                        ee += "<span><a id='a_" + i + "' onclick='setPage(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    rr = "<span><a id='a_" + PageIndex + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex < 100) {
                        qqq = "class='cur'";
                    }
                    if (PageIndex >= 100) {
                        qqq = "class='cur'";
                    }
                    www = ">" + PageIndex + "</a></span>";
                }
                var pcj2 = PageCount - 2;
                var pcjj2 = PageCount + 2;
                if (PageIndex >= pcj2 || pcj2 <= 0) {
                    for (var i = pgjj; i < pcjj; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                } else {
                    for (var i = pgjj; i < 5; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    aaa = "<span><a href='javascript:void(0);' onclick='return false;' style='cursor: default;'>...</a></span>"
                        + "<span><a id='a_" + PageCount + "' onclick='setPage(" + PageCount + ");return false;'href='javascript:void(0);'>" + PageCount + "</a></span>";
                }

            }
            if (PageCount == 1) {
                sss = "<span><a id='a_1' onclick='return false;' href='javascript:void(0);' class='cur'>1</a></span>";
            }
            var zzz = "<a class='next' onclick='setPage(" + pgjj + ")'>下一页 &gt;</a>"
                + "<span>共" + PageCount + "页</span>"
                + "<span>到第</span>"
                + "<input id='pageNum' name='' class='pre_txt' type='text'>"
                + "<span>页</span>"
                + " <input name='' class='pre_sub' value='确定' onclick='pageJump1()' type='button'>"
                + "<input type='hidden' id='pageCount' value='" + PageCount + "'/>"
                + " </div>";
            // var eeee="<div class='graphlayerbg'></div><span class='graph-close'><img src='/static/client/img/indexclose.png'></span><div class='graphinfo'><iframe id='graphshow' frameborder='no' allowTransparency='true' noresize='noresize' border='0' framespacing='0' target='' name='myFrameName'  style='background-color:transparent' ></iframe></div>"

            if (data2.length != 0 && data2 != 0) {
                var ddd = dad + dbd + dcd + pageOne + pa + q + w + e + r + qq + ww + ee + rr + qqq + www + eee + aaa + sss + zzz + pageTree;
                $("#collectList").html(ddd);
                $(".graph-t b").text(data[3])
            } else if (data2 == 0) {
                $(".graph-t b").text(0)
                $("#collectList").empty();
                $("#collectList").html("<span >暂无数据</span>");
            }
        }

    });

}

function pageJump1() {
    var page = $("#pageNum").val();
    var pageCount = $("#pageCount").val();
    if (page != "" && /^[0-9]*$/.test(page)) {
        if (parseInt(pageCount) < parseInt(page)) {
            page = $("#pageCount").val();
        }
        setPage(page);
    } else {
        alertify.alert("请输入正确的页码!");
    }
}

// 下一页执行方法
function setPage(page) {
    if (page <= 0) {
        page = 1;
    } else if (page >= PageCount) {
        page = PageCount;
    }
    collectShow(bookcodes, page, currPages);
};


var PageCount2 = null;
var PageIndex2 = null;
var bookcodes2 = null;
var currPages2 = null;

function collectShow2(bookcode, PageIndex, currPage) {
    bookcodes2 = bookcode;
    currPages2 = currPage;
    $.ajax({
        url: appPath + "/book/getBookFile6",
        type: 'post',
        data: {
            bookcode: bookcode,
            PageIndex: PageIndex,
            currPage: currPage,
            likeString: $("#inputId").val()

        },
        success: function (data) {
            var json = JSON.stringify(data[0]);

            PageCount2 = Number(data[1]);
            PageIndex2 = Number(data[2]);
            var data2 = eval(json);
            var dad = "<dl class='graph-list2'>";
            var dbd = "";
            var dcd = " </dl>";

            var pageOne = "<div class='user_pre_next'>";
            var pageTree = "</div>";
            for (var i in data2) {
                //var absss=cutstr(data2[i].abs,205);
                var titleNew = data2[i].title.replace("<font style='color:red'>", "");
                titleNew = titleNew.replace("</font>", "");
                var da = "<dd><a href='/book/getBookFileImage6?bookcode=" + bookcode + "&pageIndex=" + PageIndex + "&likeString=" + $("#inputId").val() + "&indexNum=" + i + "' target='myFrameName' onclick='setgraphshow1()'><img src='" + data2[i].fileurl + "' alt='" + titleNew + "'/><p title='" + titleNew + "'>" + data2[i].title + "</p></a></dd>";
                dbd = dbd + da;
            }
            var pgj = PageIndex2 - 1;
            var pgjj = PageIndex2 + 1;
            var pcj = PageCount2 - 1;
            var pcjj = PageCount2 + 1;

            var pa = "<div class='pre_next' onselectstart='return false'><span><a onclick='setPage2(" + pgj + ")' class='pre'>&lt; 上一页</a></span>";
            var q = "";
            var w = "";
            var e = "";
            var r = "";
            var qq = "";
            var ww = "";
            var ee = "";
            var rr = "";
            var qqq = "";
            var www = "";
            var eee = "";
            var aaa = "";
            var sss = "";
            if (PageCount2 > 1 && PageCount2 <= 5) {
                for (var i = 1; i < pcjj; i++) {
                    if (i == PageIndex2) {
                        q += "<a id='a_" + PageIndex2 + "' onclick='return false;' href='javascript:void(0);' class='cur'>" + PageIndex2 + "</a>";
                    } else {
                        q += "<a id='a_" + i + "' onclick='setPage2(" + i + ");return false;' href='javascript:void(0);' >" + i + "</a>";
                    }
                }
            }
            if (PageCount2 > 5) {
                if (PageIndex2 < 4) {
                    for (var i = 1; i < pgj; i++) {
                        w += "<span><a id='a_" + i + "' onclick='setPage2(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    e = "<span><a id='a_" + PageIndex2 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex2 < 100) {
                        r = "class='cur'";
                    }
                    qq = ">" + PageIndex2 + "</a></span>";
                } else {
                    ww = "<span><a id='a_1' onclick='setPage2(1);return false;' href='javascript:void(0);'>1</a></span>"
                        + "<span><a  onclick='return false;' style='cursor: default;'>...</a></span>";
                    for (var i = pgj; i < PageIndex2; i++) {
                        ee += "<span><a id='a_" + i + "' onclick='setPage2(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    rr = "<span><a id='a_" + PageIndex2 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex2 < 100) {
                        qqq = "class='cur'";
                    }
                    if (PageIndex2 >= 100) {
                        qqq = "class='cur'";
                    }
                    www = ">" + PageIndex2 + "</a></span>";
                }
                var pcj2 = PageCount2 - 2;
                var pcjj2 = PageCount2 + 2;
                if (PageIndex2 >= pcj2 || pcj2 <= 0) {
                    for (var i = pgjj; i < pcjj; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage2(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                } else {
                    for (var i = pgjj; i < 5; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage2(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    aaa = "<span><a href='javascript:void(0);' onclick='return false;' style='cursor: default;'>...</a></span>"
                        + "<span><a id='a_" + PageCount2 + "' onclick='setPage2(" + PageCount2 + ");return false;'href='javascript:void(0);'>" + PageCount2 + "</a></span>";
                }

            }
            if (PageCount2 == 1) {
                sss = "<span><a id='a_1' onclick='return false;' href='javascript:void(0);' class='cur'>1</a></span>";
            }
            var zzz = "<a class='next' onclick='setPage2(" + pgjj + ")'>下一页 &gt;</a>"
                + "<span>共" + PageCount2 + "页</span>"
                + "<span>到第</span>"
                + "<input id='pageNum2' name='' class='pre_txt' type='text'>"
                + "<span>页</span>"
                + " <input name='' class='pre_sub' value='确定' onclick='pageJump2()' type='button'>"
                + "<input type='hidden' id='pageCount2' value='" + PageCount2 + "'/>"
                + " </div>";
            if (data2.length != 0) {
                var ddd = dad + dbd + dcd + pageOne + pa + q + w + e + r + qq + ww + ee + rr + qqq + www + eee + aaa + sss + zzz + pageTree;
                $("#collectList2").html(ddd);
            } else {
                $("#collectList2").empty();
                // alert("暂无收藏！");
                $("#collectList2").html("<span >暂无数据</span>");
            }
        }

    });

}

function pageJump2() {
    var page = $("#pageNum2").val();
    var pageCount = $("#pageCount2").val();
    if (page != "" && /^[0-9]*$/.test(page)) {
        if (parseInt(pageCount) < parseInt(page)) {
            page = $("#pageCount2").val();
        }
        setPage2(page);
    } else {
        alertify.alert("请输入正确的页码!");
    }
}

// 下一页执行方法
function setPage2(page) {
    if (page <= 0) {
        page = 1;
    } else if (page >= PageCount2) {
        page = PageCount2;
    }
    collectShow2(bookcodes, page, currPages2);
};
//结束

//综述\概况
var PageCount3 = null;
var PageIndex3 = null;
var bookcodes3 = null;
var currPages3 = null;

function collectShow3(bookcode, PageIndex, currPage) {
    bookcodes3 = bookcode;
    currPages3 = currPage;
    $.ajax({
        url: appPath + "/book/getDetailsAuthor",
        type: 'post',
        data: {
            bookcode: bookcode,
            PageIndex: PageIndex,
            currPage: currPage

        },
        success: function (data) {
            var json = JSON.stringify(data[0]);
            PageCount3 = Number(data[1]);
            PageIndex3 = Number(data[2]);
            var data2 = eval(json);
            var dad = "<dl>";
            var dbd = "";
            var dcd = " </dl>";

            var pageOne = "<div class='user_pre_next'>";
            var pageTree = "</div>";
            var iii=0;
            for (var i in data2) {
                //var absss=cutstr(data2[i].abs,205);
                var da = "<dd><span title='" + data2[i].readCount + "'>点击量：" + data2[i].readCount + "</span><a target='_blank' href='/article/articleInfo?zid=" + data2[i].zid + "&bookcode=" + data2[i].bookcode + "' title='" + data2[i].title + "'>" + data2[i].title + "</a></dd>";
                dbd = dbd + da;
                iii=i;
            }
            var pgj = PageIndex3 - 1;
            var pgjj = PageIndex3 + 1;
            var pcj = PageCount3 - 1;
            var pcjj = PageCount3 + 1;

            var pa = "<div class='pre_next' onselectstart='return false'><span><a onclick='setPage3(" + pgj + ")' class='pre'>&lt; 上一页</a></span>";
            var q = "";
            var w = "";
            var e = "";
            var r = "";
            var qq = "";
            var ww = "";
            var ee = "";
            var rr = "";
            var qqq = "";
            var www = "";
            var eee = "";
            var aaa = "";
            var sss = "";
            if (PageCount3 > 1 && PageCount3 <= 5) {
                for (var i = 1; i < pcjj; i++) {
                    if (i == PageIndex3) {
                        q += "<a id='a_" + PageIndex3 + "' onclick='return false;' href='javascript:void(0);' class='cur'>" + PageIndex3 + "</a>";
                    } else {
                        q += "<a id='a_" + i + "' onclick='setPage3(" + i + ");return false;' href='javascript:void(0);' >" + i + "</a>";
                    }
                }
            }
            if (PageCount3 > 5) {
                if (PageIndex3 < 4) {
                    for (var i = 1; i < pgj; i++) {
                        w += "<span><a id='a_" + i + "' onclick='setPage3(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    e = "<span><a id='a_" + PageIndex3 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex3 < 100) {
                        r = "class='cur'";
                    }
                    qq = ">" + PageIndex3 + "</a></span>";
                } else {
                    ww = "<span><a id='a_1' onclick='setPage3(1);return false;' href='javascript:void(0);'>1</a></span>"
                        + "<span><a  onclick='return false;' style='cursor: default;'>...</a></span>";
                    for (var i = pgj; i < PageIndex3; i++) {
                        ee += "<span><a id='a_" + i + "' onclick='setPage3(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    rr = "<span><a id='a_" + PageIndex3 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex3 < 100) {
                        qqq = "class='cur'";
                    }
                    if (PageIndex3 >= 100) {
                        qqq = "class='cur'";
                    }
                    www = ">" + PageIndex3 + "</a></span>";
                }
                var pcj2 = PageCount3 - 2;
                var pcjj2 = PageCount3 + 2;
                if (PageIndex3 >= pcj2 || pcj2 <= 0) {
                    for (var i = pgjj; i < pcjj; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage3(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                } else {
                    for (var i = pgjj; i < 5; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage3(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    aaa = "<span><a href='javascript:void(0);' onclick='return false;' style='cursor: default;'>...</a></span>"
                        + "<span><a id='a_" + PageCount3 + "' onclick='setPage3(" + PageCount3 + ");return false;'href='javascript:void(0);'>" + PageCount3 + "</a></span>";
                }

            }
            if (PageCount3 == 1) {
                sss = "<span><a id='a_1' onclick='return false;' href='javascript:void(0);' class='cur'>1</a></span>";
            }
            var zzz = "<a class='next' onclick='setPage3(" + pgjj + ")'>下一页 &gt;</a>"
                + "<span>共" + PageCount3 + "页</span>"
                + "<span>到第</span>"
                + "<input id='pageNum3' name='' class='pre_txt' type='text'>"
                + "<span>页</span>"
                + " <input name='' class='pre_sub' value='确定' onclick='pageJump3()' type='button'>"
                + "<input type='hidden' id='pageCount3' value='" + PageCount3 + "'/>"
                + " </div>";
            var ddd="";
            if (data2.length != 0) {
                if(i<10){
                    ddd = dad + dbd + dcd;
                }else{
                    ddd = dad + dbd + dcd + pageOne + pa + q + w + e + r + qq + ww + ee + rr + qqq + www + eee + aaa + sss + zzz + pageTree;
                }
                $("#collectList3").html(ddd);
            } else {
                $("#collectList3").empty();
                // alert("暂无收藏！");
                $("#collectList3").html("<span >暂无数据</span>");
            }
        }

    });

}

function pageJump3() {
    var page = $("#pageNum3").val();
    var pageCount = $("#pageCount3").val();
    if (page != "" && /^[0-9]*$/.test(page)) {
        if (parseInt(pageCount) < parseInt(page)) {
            page = $("#pageCount3").val();
        }
        setPage3(page);
    } else {
        alertify.alert("请输入正确的页码!");
    }
}

// 下一页执行方法
function setPage3(page) {
    if (page <= 0) {
        page = 1;
    } else if (page >= PageCount3) {
        page = PageCount3;
    }
    collectShow3(bookcodes, page, currPages3);
};

//图书
var PageCount4 = null;
var PageIndex4 = null;
var bookcodes4 = null;
var currPages4 = null;

// 加载图书词条
function collectShow4(bookcode, pageIndex, currPage) {
    bookcodes4 = bookcode;
    currPages4 = currPage;
    $.ajax({
        url: appPath + "/book/bookEntry",
        type: 'post',
        data: {
            bookcode: bookcode,
            pageIndex: pageIndex,
            currPage: currPage
        },
        success: function (data) {
            var json = JSON.stringify(data[0]);
            PageCount4 = Number(data[1]);
            PageIndex4 = Number(data[2]);
            var data2 = eval(json);
            var dad = "<dl>";
            var dbd = "";
            var dcd = " </dl>";

            var pageOne = "<div class='user_pre_next'>";
            var pageTree = "</div>";
            var poe="";
            var sou="";
            for (var i in data2) {
                if(data2[i].people==""){
                    poe="<暂无作者信息>";
                }else{
                    poe=data2[i].people;
                }
                if(data2[i].source==""){
                    sou="<暂无来源信息>";
                }else{
                    sou =data2[i].source;
                }
                var da = '<dd>' +'<i></i>'+
                    // '<span  title="'+data2[i].source+'">' + data2[i].source + '</span>' +
                    // '<span style="display:inline-block;width:120px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" title="'+data2[i].people+'">' + data2[i].people + '</span>' +
                    // '<a style="cursor:default;display:inline-block;width:490px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" title="'+data2[i].title+'">' + data2[i].title + '</a></dd>';
                    '<h3><a style="cursor:text;">'+data2[i].title+'</a></h3>'+
                    '<h4>作者：'+poe+'</h4>'+
                    '<div class="booklist-b">'+sou+'</div></dd>';

                dbd = dbd + da;
            }
            var pgj = PageIndex4 - 1;
            var pgjj = PageIndex4 + 1;
            var pcj = PageCount4 - 1;
            var pcjj = PageCount4 + 1;

            var pa = "<div class='pre_next'  onselectstart='return false'><span><a onclick='setPage4(" + pgj + ")' class='pre'>&lt; 上一页</a></span>";
            var q = "";
            var w = "";
            var e = "";
            var r = "";
            var qq = "";
            var ww = "";
            var ee = "";
            var rr = "";
            var qqq = "";
            var www = "";
            var eee = "";
            var aaa = "";
            var sss = "";
            if (PageCount4 > 1 && PageCount4 <= 5) {
                for (var i = 1; i < pcjj; i++) {
                    if (i == PageIndex4) {
                        q += "<a id='a_" + PageIndex4 + "' onclick='return false;' href='javascript:void(0);' class='cur'>" + PageIndex4 + "</a>";
                    } else {
                        q += "<a id='a_" + i + "' onclick='setPage4(" + i + ");return false;' href='javascript:void(0);' >" + i + "</a>";
                    }
                }
            }
            if (PageCount4 > 5) {
                if (PageIndex4 < 4) {
                    for (var i = 1; i < pgj; i++) {
                        w += "<span><a id='a_" + i + "' onclick='setPage4(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    e = "<span><a id='a_" + PageIndex4 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex4 < 100) {
                        r = "class='cur'";
                    }
                    qq = ">" + PageIndex4 + "</a></span>";
                } else {
                    ww = "<span><a id='a_1' onclick='setPage4(1);return false;' href='javascript:void(0);'>1</a></span>"
                        + "<span><a onclick='return false;' style='cursor: default;'>...</a></span>";
                    for (var i = pgj; i < PageIndex4; i++) {
                        ee += "<span><a id='a_" + i + "' onclick='setPage4(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    rr = "<span><a id='a_" + PageIndex4 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex4 < 100) {
                        qqq = "class='cur'";
                    }
                    if (PageIndex4 >= 100) {
                        qqq = "class='cur'";
                    }
                    www = ">" + PageIndex4 + "</a></span>";
                }
                var pcj2 = PageCount4 - 2;
                var pcjj2 = PageCount4 + 2;
                if (PageIndex4 >= pcj2 || pcj2 <= 0) {
                    for (var i = pgjj; i < pcjj; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage4(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                } else {
                    for (var i = pgjj; i < 5; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage4(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    aaa = "<span><a href='javascript:void(0);' onclick='return false;' style='cursor: default;'>...</a></span>"
                        + "<span><a id='a_" + PageCount4 + "' onclick='setPage4(" + PageCount4 + ");return false;'href='javascript:void(0);'>" + PageCount4 + "</a></span>";
                }

            }
            if (PageCount4 == 1) {
                sss = "<span><a id='a_1' onclick='return false;' href='javascript:void(0);' class='cur'>1</a></span>";
            }
            var zzz = "<a class='next' onclick='setPage4(" + pgjj + ")'>下一页 &gt;</a>"
                + "<span>共" + PageCount4 + "页</span>"
                + "<span>到第</span>"
                + "<input id='pageNum4' name='' class='pre_txt' type='text'>"
                + "<span>页</span>"
                + " <input name='' class='pre_sub' value='确定' onclick='pageJump4()' type='button'>"
                + "<input type='hidden' id='pageCount4' value='" + PageCount4 + "'/>"
                + " </div>";
            if (data2.length != 0) {
                var ddd = dad + dbd + dcd + pageOne + pa + q + w + e + r + qq + ww + ee + rr + qqq + www + eee + aaa + sss + zzz + pageTree;
                $("#entryList4").html(ddd);
            } else {
                $("#entryList4").empty();
                // alert("暂无收藏！");
                $("#entryList4").html("<span >暂无数据</span>");
            }
        }
    });
}

function pageJump4() {
    var page = $("#pageNum4").val();
    var pageCount = $("#pageCount4").val();
    if (page != "" && /^[0-9]*$/.test(page)) {
        if (parseInt(pageCount) < parseInt(page)) {
            page = $("#pageCount4").val();
        }
        setPage4(page);
    } else {
        alertify.alert("请输入正确的页码!");
    }
}

// 下一页执行方法
function setPage4(page) {
    if (page <= 0) {
        page = 1;
    } else if (page >= PageCount4) {
        page = PageCount4;
    }
    collectShow4(bookcodes4, page, currPages4);
};

//人物词条
var PageCount5 = null;
var PageIndex5 = null;
var bookcodes5 = null;
var currPages5 = null;

// 加载人物词条
function collectShow5(bookcode, pageIndex, currPage) {
    // foldername 收藏夹名称   PageIndex 当前页数   currPage 一次展示几条
    bookcodes5 = bookcode;
    currPages5 = currPage;
    $.ajax({
        url: appPath + "/book/peopleEntry",
        type: 'post',
        data: {
            bookcode: bookcode,
            pageIndex: pageIndex,
            currPage: currPage
        },
        success: function (data) {
            var json = JSON.stringify(data[0]);
            PageCount5 = Number(data[1]);
            PageIndex5 = Number(data[2]);
            var data2 = eval(json);
            var dad = "<dl style='height: 490px'>";
            var dbd = "";
            var dcd = " </dl>";
            var pageOne = "<div class='user_pre_next'>";
            var pageTree = "</div>";
            for (var i in data2) {
                var str = "\"" + data2[i].htmlContent + "\"";
                // var da = "<dd><span><img src='/static/client/img/userphotos.png' /></span>" +
                //     "<h3>" + data2[i].title + "</h3>" +
                //     "<p>" + data2[i].source + "</p>" +
                var rwsource = "";
                if ((data2[i].source).length > 32){
                    rwsource = data2[i].source.substr(0,31) + "...";
                }else{
                    rwsource = data2[i].source;
                }
                var da = "<dd><h3 title='"+data2[i].title+"'>" + data2[i].title.substring(0,6) + "</h3><p style='min-height:40px;' title='" + data2[i].source + "'>" + rwsource + "</p>"
                    + "<a onclick='relatedProduct(" + str + ")'>相关内容</a></dd>";
                dbd = dbd + da;
            }
            var pgj = PageIndex5 - 1;
            var pgjj = PageIndex5 + 1;
            var pcj = PageCount5 - 1;
            var pcjj = PageCount5 + 1;

            var pa = "<div class='pre_next' onselectstart='return false'><span><a onclick='setPage5(" + pgj + ")' class='pre'>&lt; 上一页</a></span>";
            var q = "";
            var w = "";
            var e = "";
            var r = "";
            var qq = "";
            var ww = "";
            var ee = "";
            var rr = "";
            var qqq = "";
            var www = "";
            var eee = "";
            var aaa = "";
            var sss = "";
            if (PageCount5 > 1 && PageCount5 <= 5) {
                for (var i = 1; i < pcjj; i++) {
                    if (i == PageIndex5) {
                        q += "<a id='a_" + PageIndex5 + "' onclick='return false;' href='javascript:void(0);' class='cur'>" + PageIndex5 + "</a>";
                    } else {
                        q += "<a id='a_" + i + "' onclick='setPage5(" + i + ");return false;' href='javascript:void(0);' >" + i + "</a>";
                    }
                }
            }
            if (PageCount5 > 5) {
                if (PageIndex5 < 4) {
                    for (var i = 1; i < pgj; i++) {
                        w += "<span><a id='a_" + i + "' onclick='setPage5(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    e = "<span><a id='a_" + PageIndex5 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex5 < 100) {
                        r = "class='cur'";
                    }
                    qq = ">" + PageIndex5 + "</a></span>";
                } else {
                    ww = "<span><a id='a_1' onclick='setPage5(1);return false;' href='javascript:void(0);'>1</a></span>"
                        + "<span><a  onclick='return false;' style='cursor: default;'>...</a></span>";
                    for (var i = pgj; i < PageIndex5; i++) {
                        ee += "<span><a id='a_" + i + "' onclick='setPage5(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    rr = "<span><a id='a_" + PageIndex5 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex5 < 100) {
                        qqq = "class='cur'";
                    }
                    if (PageIndex5 >= 100) {
                        qqq = "class='cur'";
                    }
                    www = ">" + PageIndex5 + "</a></span>";
                }
                var pcj2 = PageCount5 - 2;
                var pcjj2 = PageCount5 + 2;
                if (PageIndex5 >= pcj2 || pcj2 <= 0) {
                    for (var i = pgjj; i < pcjj; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage5(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                } else {
                    for (var i = pgjj; i < 5; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage5(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    aaa = "<span><a href='javascript:void(0);' onclick='return false;' style='cursor: default;'>...</a></span>"
                        + "<span><a id='a_" + PageCount5 + "' onclick='setPage5(" + PageCount5 + ");return false;'href='javascript:void(0);'>" + PageCount5 + "</a></span>";
                }

            }
            if (PageCount5 == 1) {
                sss = "<span><a id='a_1' onclick='return false;' href='javascript:void(0);' class='cur'>1</a></span>";
            }
            var zzz = "<a class='next' onclick='setPage5(" + pgjj + ")'>下一页 &gt;</a>"
                + "<span>共" + PageCount5 + "页</span>"
                + "<span>到第</span>"
                + "<input id='pageNum5' name='' class='pre_txt' type='text'>"
                + "<span>页</span>"
                + " <input name='' class='pre_sub' value='确定' onclick='pageJump5()' type='button'>"
                + "<input type='hidden' id='pageCount5' value='" + PageCount5 + "'/>"
                + " </div>";
            if (data2.length != 0) {
                var ddd = dad + dbd + dcd + pageOne + pa + q + w + e + r + qq + ww + ee + rr + qqq + www + eee + aaa + sss + zzz + pageTree;
                $("#entryList5").html(ddd);
            } else {
                $("#entryList5").empty();
                // alert("暂无收藏！");
                $("#entryList5").html("<span >暂无数据</span>");
            }
        }
    });
}

function pageJump5() {
    var page = $("#pageNum5").val();
    var pageCount = $("#pageCount5").val();
    if (page != "" && /^[0-9]*$/.test(page)) {
        if (parseInt(pageCount) < parseInt(page)) {
            page = $("#pageCount5").val();
        }
        setPage5(page);
    } else {
        alertify.alert("请输入正确的页码!");
    }
}

// 下一页执行方法
function setPage5(page) {
    if (page <= 0) {
        page = 1;
    } else if (page >= PageCount5) {
        page = PageCount5;
    }
    collectShow5(bookcodes5, page, currPages5);
};

//相关作品div显示
function relatedProduct(conent) {

    if (conent == null || conent == "") {
        alertify.alert("[暂无作品信息记录]");
    } else {
        var newPP = conent.replace(/<p>/g, "<dd>").replace(/<\/p>/g, "</dd>");
        $(".layer-renwu .renwudetail dl").html(newPP);
        $(".layer-renwu .renwudetail dl dd:odd").css("backgroundColor", "#e6e6e6");
        $(".layer-renwu").show();
    }
}

//最佳论文
var PageCount7 = null;
var PageIndex7 = null;
var bookcodes7 = null;
var currPages7 = null;

// 加载我得收藏
function collectShow7(bookcode, PageIndex, currPage) {
    // foldername 收藏夹名称   PageIndex 当前页数   currPage 一次展示几条
    bookcodes7 = bookcode;
    currPages7 = currPage;
    $.ajax({
        url: appPath + "/book/getBookDetailPaper",
        type: 'post',
        data: {
            bookcode: bookcode,
            PageIndex: PageIndex,
            currPage: currPage

        },
        success: function (data) {
            var json = JSON.stringify(data[0]);

            PageCount7 = Number(data[1]);
            PageIndex7 = Number(data[2]);
            var data2 = eval(json);
            var dad = "<dl >";
            var dbd = "";
            var dcd = " </dl>";

            var pageOne = "<div class='user_pre_next'>";
            var pageTree = "</div>";
            var poe="";
            var sou="";
            for (var i in data2) {
                if(data2[i].people==""){
                    poe="<暂无作者>";
                }else{
                    poe=data2[i].people;
                }
                if(data2[i].source==""){
                    sou="<暂无来源信息>";
                }else{
                    sou =data2[i].source;
                }
                var strs= new Array(); //定义一数组
                //strs=data2[i].people.split(";"); //字符分割
                // for (i=0;i<strs.length ;i++ )
                // {
                //     var peop="<b>"+strs[i]+"</b>"; //分割后的字符输出
                //     poe=poe+peop;
                // }
                var da = "<dd ><i></i>"
                    //+"<a style='cursor:default;display: inline-block;width: 302px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'>" + data2[i].title + "</a>"
                    // + "<span style='width: 402px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;' title=" + data2[i].title + ">" + data2[i].title + "</span>"
                    // + "<span style='width: 118px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;' title=" + data2[i].people + ">" + data2[i].people + "</span>"
                    // + "<span style='width: 260px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;' title=" + data2[i].source + ">" + data2[i].source + "</span>"
                    + '<h3><a style="cursor:text;">'+data2[i].title+'</a></h3>'
                    +'<h4>作者：'+poe+'</h4>'
                    +'<div class="booklist-b">'+sou+'</div>'
                    + "</dd>";
                dbd = dbd + da;
            }
            var pgj = PageIndex7 - 1;
            var pgjj = PageIndex7 + 1;
            var pcj = PageCount7 - 1;
            var pcjj = PageCount7 + 1;

            var pa = "<div class='pre_next' onselectstart='return false'><span><a onclick='setPage7(" + pgj + ")' class='pre'>&lt; 上一页</a></span>";
            var q = "";
            var w = "";
            var e = "";
            var r = "";
            var qq = "";
            var ww = "";
            var ee = "";
            var rr = "";
            var qqq = "";
            var www = "";
            var eee = "";
            var aaa = "";
            var sss = "";
            if (PageCount7 > 1 && PageCount7 <= 5) {
                for (var i = 1; i < pcjj; i++) {
                    if (i == PageIndex7) {
                        q += "<a id='a_" + PageIndex7 + "' onclick='return false;' href='javascript:void(0);' class='cur'>" + PageIndex7 + "</a>";
                    } else {
                        q += "<a id='a_" + i + "' onclick='setPage7(" + i + ");return false;' href='javascript:void(0);' >" + i + "</a>";
                    }
                }
            }
            if (PageCount7 > 5) {
                if (PageIndex7 < 4) {
                    for (var i = 1; i < pgj; i++) {
                        w += "<span><a id='a_" + i + "' onclick='setPage7(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    e = "<span><a id='a_" + PageIndex7 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex7 < 100) {
                        r = "class='cur'";
                    }
                    qq = ">" + PageIndex7 + "</a></span>";
                } else {
                    ww = "<span><a id='a_1' onclick='setPage7(1);return false;' href='javascript:void(0);'>1</a></span>"
                        + "<span><a onclick='return false;' style='cursor: default;'>...</a></span>";
                    for (var i = pgj; i < PageIndex7; i++) {
                        ee += "<span><a id='a_" + i + "' onclick='setPage7(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    rr = "<span><a id='a_" + PageIndex7 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex7 < 100) {
                        qqq = "class='cur'";
                    }
                    if (PageIndex7 >= 100) {
                        qqq = "class='cur'";
                    }
                    www = ">" + PageIndex7 + "</a></span>";
                }
                var pcj2 = PageCount7 - 2;
                var pcjj2 = PageCount7 + 2;
                if (PageIndex7 >= pcj2 || pcj2 <= 0) {
                    for (var i = pgjj; i < pcjj; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage7(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                } else {
                    for (var i = pgjj; i < 5; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage7(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    aaa = "<span><a href='javascript:void(0);' onclick='return false;' style='cursor: default;'>...</a></span>"
                        + "<span><a id='a_" + PageCount7 + "' onclick='setPage7(" + PageCount7 + ");return false;'href='javascript:void(0);'>" + PageCount7 + "</a></span>";
                }

            }
            if (PageCount7 == 1) {
                sss = "<span><a id='a_1' onclick='return false;' href='javascript:void(0);' class='cur'>1</a></span>";
            }
            var zzz = "<a class='next' onclick='setPage7(" + pgjj + ")'>下一页 &gt;</a>"
                + "<span>共" + PageCount7 + "页</span>"
                + "<span>到第</span>"
                + "<input id='pageNum7' name='' class='pre_txt' type='text'>"
                + "<span>页</span>"
                + " <input name='' class='pre_sub' value='确定' onclick='pageJump7()' type='button'>"
                + "<input type='hidden' id='pageCount7' value='" + PageCount7 + "'/>"
                + " </div>";
            if (data2.length != 0) {
                var ddd = dad + dbd + dcd + pageOne + pa + q + w + e + r + qq + ww + ee + rr + qqq + www + eee + aaa + sss + zzz + pageTree;
                $("#collectList7").html(ddd);
            } else {
                $("#collectList7").empty();
                // alert("暂无收藏！");
                $("#collectList7").html("<span >暂无数据</span>");
            }
        }

    });

}

function pageJump7() {
    var page = $("#pageNum7").val();
    var pageCount = $("#pageCount7").val();
    if (page != "" && /^[0-9]*$/.test(page)) {
        if (parseInt(pageCount) < parseInt(page)) {
            page = $("#pageCount7").val();
        }
        setPage7(page);
    } else {
        alertify.alert("请输入正确的页码!");
    }
}

// 下一页执行方法
function setPage7(page) {
    if (page <= 0) {
        page = 1;
    } else if (page >= PageCount7) {
        page = PageCount7;
    }
    collectShow7(bookcodes7, page, currPages7);
};
//结束


//课题
var PageCount8 = null;
var PageIndex8 = null;
var bookcodes8 = null;
var currPages8 = null;
var source8 = "立项";

// 加载我的收藏
function collectShow8(bookcode, PageIndex, currPage, soruce) {
    // foldername 收藏夹名称   PageIndex 当前页数   currPage 一次展示几条
    bookcodes8 = bookcode;
    currPages8 = currPage;
    $.ajax({
        url: appPath + "/book/getBookDetailTopic",
        type: 'post',
        data: {
            bookcode: bookcode,
            PageIndex: PageIndex,
            currPage: currPage,
            source: soruce

        },
        success: function (data) {
            var json = JSON.stringify(data[0]);
            PageCount8 = Number(data[1]);
            PageIndex8 = Number(data[2]);
            var exdatas = data[3];
            var type = data[4];
            var data2 = eval(json);
            var dad = "<dl>";
            var dbd = "";
            var dcd = " </dl>";

            var pageOne = "<div class='user_pre_next'>";
            var pageTree = "</div>";
            for (var i in data2) {
                var exdata = "";
                var exdataNew = exdatas[i];
                var  tr="<tr>";
                var  tr2="</tr>"
                for (var j in exdataNew) {
                    // if ((j + 1) % 3 == 0) {
                    //     exdata += "<div class='col-lg-6'>" + exdataNew[j] + "</div>"
                    // } else if (j == exdataNew.length - 1) {
                    //     exdata += "<div class=''>" + exdataNew[j] + "</div>"
                    // } else {
                    //     exdata += "<div class='col-lg-3'>" + exdataNew[j] + "</div>"
                    // }
                    if(j==0||j==3||j ==6||j ==9){
                        if(exdataNew[j].length>15){
                            exdata += tr +"<td title='"+exdataNew[j]+"'>" + exdataNew[j].substring(0,15) + "...</td>"
                        }else{
                            exdata += tr +"<td >" + exdataNew[j]+ "</td>"
                        }

                    }else if(j==1||j==4||j ==7){
                        if(exdataNew[j].length>15){
                            exdata += "<td title='"+exdataNew[j]+"'>" + exdataNew[j].substring(0,15) + "...</td>"
                        }else {
                            exdata += "<td>" + exdataNew[j] + "</td>"
                        }

                    }else if(j==2||j==5||j ==8){
                        if(exdataNew[j].length>15){
                            exdata += "<td title='"+exdataNew[j]+"'>" + exdataNew[j].substring(0,15) + "...</td>"+tr2
                        }else{
                            exdata += "<td>" + exdataNew[j] + "</td>"   + tr2
                        }

                    }
                }
                // $.each(exdatas,function (i,n){
                //     exdata+="<div class='col-lg-3'>"+n+"</div>"
                // })
                var da = "<dd style='background-color: white'><i></i><h3><a style=' cursor:text;'>" + data2[i].title + "</a></h3><table class='ketiinfo' width='100%' border='0'>" + exdata + "</table><div class='booklist-b'><b>立结项区分："+type+"</b></div></dd>";
                dbd = dbd + da;
            }
            var pgj = PageIndex8 - 1;
            var pgjj = PageIndex8 + 1;
            var pcj = PageCount8 - 1;
            var pcjj = PageCount8 + 1;

            var pa = "<div class='pre_next' onselectstart='return false'><span><a onclick='setPage8(" + pgj + ")' class='pre'>&lt; 上一页</a></span>";
            var q = "";
            var w = "";
            var e = "";
            var r = "";
            var qq = "";
            var ww = "";
            var ee = "";
            var rr = "";
            var qqq = "";
            var www = "";
            var eee = "";
            var aaa = "";
            var sss = "";
            if (PageCount8 > 1 && PageCount8 <= 5) {
                for (var i = 1; i < pcjj; i++) {
                    if (i == PageIndex8) {
                        q += "<a id='a_" + PageIndex8 + "' onclick='return false;' href='javascript:void(0);' class='cur'>" + PageIndex8 + "</a>";
                    } else {
                        q += "<a id='a_" + i + "' onclick='setPage8(" + i + ");return false;' href='javascript:void(0);' >" + i + "</a>";
                    }
                }
            }
            if (PageCount8 > 5) {
                if (PageIndex8 < 4) {
                    for (var i = 1; i < pgj; i++) {
                        w += "<span><a id='a_" + i + "' onclick='setPage8(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    e = "<span><a id='a_" + PageIndex8 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex8 < 100) {
                        r = "class='cur'";
                    }
                    qq = ">" + PageIndex8 + "</a></span>";
                } else {
                    ww = "<span><a id='a_1' onclick='setPage8(1);return false;' href='javascript:void(0);'>1</a></span>"
                        + "<span><a onclick='return false;' style='cursor: default;'>...</a></span>";
                    for (var i = pgj; i < PageIndex8; i++) {
                        ee += "<span><a id='a_" + i + "' onclick='setPage8(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    rr = "<span><a id='a_" + PageIndex8 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex8 < 100) {
                        qqq = "class='cur'";
                    }
                    if (PageIndex8 >= 100) {
                        qqq = "class='cur'";
                    }
                    www = ">" + PageIndex8 + "</a></span>";
                }
                var pcj2 = PageCount8 - 2;
                var pcjj2 = PageCount8 + 2;
                if (PageIndex8 >= pcj2 || pcj2 <= 0) {
                    for (var i = pgjj; i < pcjj; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage8(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                } else {
                    for (var i = pgjj; i < 5; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage8(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    aaa = "<span><a href='javascript:void(0);' onclick='return false;' style='cursor: default;'>...</a></span>"
                        + "<span><a id='a_" + PageCount8 + "' onclick='setPage8(" + PageCount8 + ");return false;'href='javascript:void(0);'>" + PageCount8 + "</a></span>";
                }

            }
            if (PageCount8 == 1) {
                sss = "<span><a id='a_1' onclick='return false;' href='javascript:void(0);' class='cur'>1</a></span>";
            }
            var zzz = "<a class='next' onclick='setPage8(" + pgjj + ")'>下一页 &gt;</a>"
                + "<span>共" + PageCount8 + "页</span>"
                + "<span>到第</span>"
                + "<input id='pageNum8' name='' class='pre_txt' type='text'>"
                + "<span>页</span>"
                + " <input name='' class='pre_sub' value='确定' onclick='pageJump8()' type='button'>"
                + "<input type='hidden' id='PageCount8' value='" + PageCount8 + "'/>"
                + " </div>";
            if (data2.length != 0) {
                var ddd = dad + dbd + dcd + pageOne + pa + q + w + e + r + qq + ww + ee + rr + qqq + www + eee + aaa + sss + zzz + pageTree;
                $("#collectList8").html(ddd);
            } else {
                $("#collectList8").empty();
                // alert("暂无收藏！");
                $("#collectList8").html("<span >暂无数据</span>");
            }
        }

    });

}

function pageJump8() {
    var page = $("#pageNum8").val();
    var pageCount = $("#PageCount8").val();
    if (page != "" && /^[0-9]*$/.test(page)) {
        if (parseInt(pageCount) < parseInt(page)) {
            page = $("#PageCount8").val();
        }
        setPage8(page);
    } else {
        alertify.alert("请输入正确的页码!");
    }
}

// 下一页执行方法
function setPage8(page) {
    if (page <= 0) {
        page = 1;
    } else if (page >= PageCount8) {
        page = PageCount8;
    }
    collectShow8(bookcodes8, page, currPages8, source8);
};

$(function () {
    var type = true;
    $(".keti-left a").click(function () {
        $(this).addClass("on");
        $(this).siblings("a").removeClass("on");
        if (type) {
            source8 = "结项";
            type = false;
        } else {
            source8 = "立项";
            type = true;
        }
    });
})


//机构
var PageCount9 = null;
var PageIndex9 = null;
var bookcodes9 = null;
var currPages9 = null;
var source9 = "";
var typedd="";
var titleType="";
function collectShow9(bookcode, PageIndex, currPage, soruce,sourceType,title) {
    if(sourceType==3){
        currPage=15;
    }else{
        currPage=10;
    }
    bookcodes9 = bookcode;
    currPages9 = currPage;
    source9 = soruce;
    titleType=title;
    $.ajax({
        url: appPath + "/book/getBookDetailsMechanism",
        type: 'post',
        data: {
            bookcode: bookcode,
            PageIndex: PageIndex,
            currPage: currPage,
            source: soruce,
            sourceType:sourceType,
            titleType:titleType
        },
        success: function (data) {
            var json = JSON.stringify(data[0]);
            PageCount9 = Number(data[1]);
            PageIndex9 = Number(data[2]);
            typedd=data[3];
            var data2 = eval(json);
            var dad = "<dl class='mechanism mechanismstyle01'>";
            var dbd = "";
            var dcd = " </dl>";
            var da="";
            var type1="";
            var pageOne = "<div class='user_pre_next'>";
            var pageTree = "</div>";
            for (var i in data2) {

                if(typedd==3){
                da= "<dd>"+
                    "<h3 style='overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'><a  style='cursor:text' title='"+data2[i].replace("<p>","")+"'>"+data2[i].replace("<p>","")+" </a></h3>"+
                    "</dd>";
                }else {
                    if(data2[i].abs==null||data2[i].abs==""){
                        da= "<dd>"+
                            "<h3 style='overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'><a  style='cursor:text' title="+"\""+data2[i].htmlContent.replace("<p>","").replace("</p>","")+"\""+">"+data2[i].htmlContent.replace("<p>","").replace("</p>","")+"</a></h3>"+
                            "<div class='activeinfo' style='display: none;'>"+data2[i].abs+"</div>"+
                            "<a  class='activemore' style='display: none;'><b><img src='../static/client/img/upmore.png' alt=''></b><img src='../static/client/img/lookmore.png' alt=''></a>"+
                            "</dd>";

                    }else{
                        da= "<dd>"+
                            "<h3 style='overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'><a style='cursor:text' title="+"\""+data2[i].htmlContent.replace("<p>","").replace("</p>","")+"\""+">"+data2[i].htmlContent.replace("<p>","").replace("</p>","")+"</a></h3>"+
                            "<div class='activeinfo'>"+data2[i].abs+"</div>"+
                            "<a  class='activemore'><b><img src='../static/client/img/upmore.png' alt=''></b><img src='../static/client/img/lookmore.png' alt=''></a>"+
                            "</dd>";
                    }

                }
                dbd = dbd + da;
            }
            var pgj = PageIndex9 - 1;
            var pgjj = PageIndex9 + 1;
            var pcj = PageCount9 - 1;
            var pcjj = PageCount9 + 1;

            var pa = "<div class='pre_next' onselectstart='return false'><span><a onclick='setPage9(" + pgj + ")' class='pre'>&lt; 上一页</a></span>";
            var q = "";
            var w = "";
            var e = "";
            var r = "";
            var qq = "";
            var ww = "";
            var ee = "";
            var rr = "";
            var qqq = "";
            var www = "";
            var eee = "";
            var aaa = "";
            var sss = "";
            if (PageCount9 > 1 && PageCount9 <= 5) {
                for (var i = 1; i < pcjj; i++) {
                    if (i == PageIndex9) {
                        q += "<a id='a_" + PageIndex9 + "' onclick='return false;' href='javascript:void(0);' class='cur'>" + PageIndex9 + "</a>";
                    } else {
                        q += "<a id='a_" + i + "' onclick='setPage9(" + i + ");return false;' href='javascript:void(0);' >" + i + "</a>";
                    }
                }
            }
            if (PageCount9 > 5) {
                if (PageIndex9 < 4) {
                    for (var i = 1; i < pgj; i++) {
                        w += "<span><a id='a_" + i + "' onclick='setPage9(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    e = "<span><a id='a_" + PageIndex9 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex9 < 100) {
                        r = "class='cur'";
                    }
                    qq = ">" + PageIndex9 + "</a></span>";
                } else {
                    ww = "<span><a id='a_1' onclick='setPage9(1);return false;' href='javascript:void(0);'>1</a></span>"
                        + "<span><a onclick='return false;' style='cursor: default;'>...</a></span>";
                    for (var i = pgj; i < PageIndex9; i++) {
                        ee += "<span><a id='a_" + i + "' onclick='setPage9(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    rr = "<span><a id='a_" + PageIndex9 + "' onclick='return false;' href='javascript:void(0);'";
                    if (PageIndex9 < 100) {
                        qqq = "class='cur'";
                    }
                    if (PageIndex9 >= 100) {
                        qqq = "class='cur'";
                    }
                    www = ">" + PageIndex9 + "</a></span>";
                }
                var pcj2 = PageCount9 - 2;
                var pcjj2 = PageCount9 + 2;
                if (PageIndex9 >= pcj2 || pcj2 <= 0) {
                    for (var i = pgjj; i < pcjj; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage9(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                } else {
                    for (var i = pgjj; i < 5; i++) {
                        eee += "<span><a id='a_" + i + "' onclick='setPage9(" + i + ");return false;' href='javascript:void(0);'>" + i + "</a></span>";
                    }
                    aaa = "<span><a href='javascript:void(0);' onclick='return false;' style='cursor: default;'>...</a></span>"
                        + "<span><a id='a_" + PageCount9 + "' onclick='setPage9(" + PageCount9 + ");return false;'href='javascript:void(0);'>" + PageCount9 + "</a></span>";
                }

            }
            if (PageCount9 == 1) {
                sss = "<span><a id='a_1' onclick='return false;' href='javascript:void(0);' class='cur'>1</a></span>";
            }
            var zzz = "<a class='next' onclick='setPage9(" + pgjj + ")'>下一页 &gt;</a>"
                + "<span>共" + PageCount9 + "页</span>"
                + "<span>到第</span>"
                + "<input id='pageNum9' name='' class='pre_txt' type='text'>"
                + "<span>页</span>"
                + " <input name='' class='pre_sub' value='确定' onclick='pageJump9()' type='button'>"
                + "<input type='hidden' id='PageCount9' value='" + PageCount9 + "'/>"
                + " </div>";
            if (data2.length != 0) {
                var ddd = dad + dbd + dcd + pageOne + pa + q + w + e + r + qq + ww + ee + rr + qqq + www + eee + aaa + sss + zzz + pageTree;

                $("#collectList9").html(ddd);
            } else {
                $("#collectList9").empty();
                // alert("暂无收藏！");
                $("#collectList9").html("<span >暂无数据</span>");
            }
        }

    });

}

function pageJump9() {
    var page = $("#pageNum9").val();
    var pageCount = $("#PageCount9").val();
    if (page != "" && /^[0-9]*$/.test(page)) {
        if (parseInt(pageCount) < parseInt(page)) {
            page = $("#PageCount9").val();
        }
        setPage9(page);
    } else {
        alertify.alert("请输入正确的页码!");
    }
}

// 下一页执行方法
function setPage9(page) {

    if (page <= 0) {
        page = 1;
    } else if (page >= PageCount9) {
        page = PageCount9;
    }
    collectShow9(bookcodes9, page, currPages9, source9,typedd,titleType);
};


$(function () {
    $(".lunwen-left a").eq(0).addClass("on");
    $(".lunwen-left a").click(function () {
        $(this).addClass("on");
        $(this).siblings("a").removeClass("on");
    });

    //大事记
    $(".timeslidebox a").click(function(){
        $(this).next("p").toggleClass("active");
        $(this).children("b").toggle();
    });
    $(".history-date:gt(1) dd").hide();
    $(".history-date h2").click(function(){
        $(this).toggleClass("active");
        $(this).parent("dl").children("dd").slideToggle();
    });
    $(".history-date h2:lt(2)").addClass("active");
})
//结束
