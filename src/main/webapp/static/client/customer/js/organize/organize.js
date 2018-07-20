/**
 * Created by zly on 2017-6-5.
 */
$(function () {

    /*访问检索下载汇总*/
    $.ajax({
        url: appPath + "/organize/syqkall",
        data: {
            'ipStart': $("#ipStart").val(),
            'ipEnd': $("#ipEnd").val(),
            'orgId': $("#orgId").val()
        },
        type: "get",
        dataType: "json",
        success: function (data) {
            $(".li1 span:first").text(data.todayAccess);
            $(".li1 span:first").attr("title",data.todayAccess);
            $(".li2 span:first").text(data.hisAccess);
            $(".li2 span:first").attr("title",data.hisAccess);
            $(".li3 span:first").text(data.todaySearch);
            $(".li3 span:first").attr("title",data.todaySearch);
            $(".li4 span:first").text(data.hisSearch);
            $(".li4 span:first").attr("title",data.hisSearch);
            $(".li5 span:first").text(data.todayDownload);
            $(".li5 span:first").attr("title",data.todayDownload);
            $(".li6 span:first").text(data.hisDownload);
            $(".li6 span:first").attr("title",data.hisDownload);
        }
    });

    /*使用情况tab切换*/
    var syqkTabDom = 0;
    var syqkTimeDom = 0;
    $("#syqkTab span").click(function () {
        var i = $(this).index();
        syqkTabDom = $(this).index();

        $(this).addClass("on").siblings().removeClass("on");
        $("#syqkTabMain .canvas").eq(i).show().siblings().hide();
        var url = "/organize/syqk";

        if (i == 0) {
            var legend = ['电子书', '期刊', '标准'];
            if (syqkTimeDom == 0) {
                var xAxis = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
            } else if (syqkTimeDom == 1) {
                var xAxis = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'];
                var year = new Date().getFullYear();
                var month = new Date().getMonth() + 1;
                if (new Date(year, month, 0).getDate() == 31) {
                    xAxis.push('31');
                }
            } else if (syqkTimeDom == 2) {
                var xAxis = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
            }
            sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);
        } else if (i == 1) {
            var legend = ['检索量'];
            if (syqkTimeDom == 0) {
                var xAxis = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
            } else if (syqkTimeDom == 1) {
                var xAxis = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'];
                var year = new Date().getFullYear();
                var month = new Date().getMonth() + 1;
                if (new Date(year, month, 0).getDate() == 31) {
                    xAxis.push('31');
                }
            } else if (syqkTimeDom == 2) {
                var xAxis = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
            }
            sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);
        } else if (i == 2) {
            var legend = ['期刊', '标准'];
            if (syqkTimeDom == 0) {
                var xAxis = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
            } else if (syqkTimeDom == 1) {
                var xAxis = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'];
                var year = new Date().getFullYear();
                var month = new Date().getMonth() + 1;
                if (new Date(year, month, 0).getDate() == 31) {
                    xAxis.push('31');
                }
            } else if (syqkTimeDom == 2) {
                var xAxis = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
            }
            sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);
        }

    });
    /*使用情况时间按钮切换*/
    $("#syqk-time span").click(function () {
        $(this).addClass("on").siblings().removeClass("on");

        var i = $(this).index();
        syqkTimeDom = $(this).index();
        var url = "/organize/syqk";
        if (i == 0) {
            var xAxis = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
            if (syqkTabDom == 0) {
                var legend = ['电子书', '期刊', '标准'];
                sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);
            } else if (syqkTabDom == 1) {
                var legend = ['检索量'];
                sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);
            } else if (syqkTabDom == 2) {
                var legend = ['期刊', '标准'];
                sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);
            }

        } else if (i == 1) {
            var xAxis = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'];
            var year = new Date().getFullYear();
            var month = new Date().getMonth() + 1;
            if (new Date(year, month, 0).getDate() == 31) {
                xAxis.push('31');
            }
            if (syqkTabDom == 0) {
                var legend = ['电子书', '期刊', '标准'];
                sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);

            } else if (syqkTabDom == 1) {
                var legend = ['检索量'];
                sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);

            } else if (syqkTabDom == 2) {
                var legend = ['期刊', '标准'];
                sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);

            }

        } else if (i == 2) {
            var xAxis = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];

            if (syqkTabDom == 0) {
                var legend = ['电子书', '期刊', '标准'];
                sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);

            } else if (syqkTabDom == 1) {
                var legend = ['检索量'];
                sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);

            } else if (syqkTabDom == 2) {
                var legend = ['期刊', '标准'];
                sendSYQKRequest(url, syqkTabDom, syqkTimeDom, legend, xAxis);

            }
        }

    });

    /*热门搜索词标签云时间切换*/
    $("#rmssc-time span").click(function () {
        $(this).addClass("on").siblings().removeClass("on");

        var i = $(this).index();

        if (i == 0) {
            tagCloud(i);
        } else if (i == 1) {
            tagCloud(i);
        } else if (i == 2) {
            tagCloud(i);
        }

    });

    /*热门资源tab切换*/
    var rmzyTabDom = 0;
    var rmzyoptDom = 1;
    $("#rmzy-tabs-li span").click(function () {
        var i = $(this).index();
        rmzyTabDom = $(this).index();

        $(this).addClass("on").siblings().removeClass("on");

        if (i == 0) {
            hotResource(false, rmzyTabDom, rmzyoptDom);
        } else if (i == 1) {
            hotResource(false, rmzyTabDom, rmzyoptDom);
        }
    });
    /*热门资源类型按钮切换*/
    $("#rmzy-opt span").click(function () {
        $(this).addClass("on").siblings().removeClass("on");

        var i = $(this).index();
        rmzyoptDom = $(this).index() + 1;

        if (i == 0) {
            hotResource(false, rmzyTabDom, rmzyoptDom);
        } else if (i == 1) {
            hotResource(false, rmzyTabDom, rmzyoptDom);
        } else if (i == 2) {
            hotResource(false, rmzyTabDom, rmzyoptDom);
        }

    });
    /*弹出层热门资源切换*/
    var rmzyTabDomlayer = 0;
    var rmzyoptDomlayer = 1;
    $("#rm-layer span").click(function () {
        var i = $(this).index();
        rmzyTabDomlayer = $(this).index();

        $(this).addClass("on").siblings().removeClass("on");
        $('#hotPageIndex').val("1");
        if (i == 0) {
            hotResource(true, rmzyTabDomlayer, rmzyoptDomlayer);
        } else if (i == 1) {
            hotResource(true, rmzyTabDomlayer, rmzyoptDomlayer);
        }
    });
    $("#rm-layer-opt span").click(function () {

        $(this).addClass("on").siblings().removeClass("on");
        var i = $(this).index();
        rmzyoptDomlayer = $(this).index() + 1;
        $('#hotPageIndex').val("1");

        if (i == 0) {
            hotResource(true, rmzyTabDomlayer, rmzyoptDomlayer);
        } else if (i == 1) {
            hotResource(true, rmzyTabDomlayer, rmzyoptDomlayer);
        } else if (i == 2) {
            hotResource(true, rmzyTabDomlayer, rmzyoptDomlayer);
        }

    });

    //用户记录隔行背景色
    $("#yhjlData li:even,#jmenu-layer li:even").css("background", "#fff");

    //调用layer弹出层
    $(".jgyh-menu-li li").click(function () {
        var name = $(this).attr('name');
        var index = $(this).index();
        $("#name").html(name);
        $('#PageIndex').val("1");
        getMenuData(index);
        layer.open({
            type: 1,
            title: '',
            shade: 0,
            closeBtn: 0,
            area: ['680px', 'auto'], //宽高
            content: $('#menu-layer') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        });
    });
    $("#rmzy-more").click(function () {
        hotResource(true, 0, 1);
        layer.open({
            type: 1,
            shade: 0,
            title: '',
            closeBtn: 0,
            area: ['680px', 'auto'], //宽高
            content: $('#rmzy-layer') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        });
    });

    //关闭layer弹出框
    $(".jmenu-closed").click(function () {
        layer.closeAll();
    });

    /*初始化数据*/
    sendSYQKRequest("/organize/syqk", 0, 0, ['电子书', '期刊', '标准'], ['周一', '周二', '周三', '周四', '周五', '周六', '周日']);
    tagCloud(0);
    hotResource(false, 0, 1);
    getUserlog();

});


/*发送请求获取使用情况折线图数据*/
function sendSYQKRequest(url, tab, time, legend, xAxis) {
    $.ajax({
        url: appPath + url,
        type: 'get',
        data: {
            'tab': tab,
            'time': time,
            'ipStart': $("#ipStart").val(),
            'ipEnd': $("#ipEnd").val(),
            'orgId': $("#orgId").val()
        },
        dataType: 'json',
        success: function (data) {
            setSyqk(legend, xAxis, data);
        }
    })
}
/*构建折线图*/
function setSyqk(legend, xAxis, series) {

    var myChart = echarts.init(document.getElementById('syqkMain'));

    var option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: legend
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                magicType: {show: true, type: ['stack', 'tiled']},
                saveAsImage: {show: true}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: xAxis
        },
        yAxis: {
            type: 'value'
        },
        series: series
    };

    myChart.setOption(option);
}
/*访问检索下载汇总*/
function getMenuData(index) {
    $.ajax({
        url: appPath + "/organize/menu",
        type: 'post',
        data: {
            'type': index,
            'PageIndex': $('#PageIndex').val(),
            'PageSize': $('#PageSize').val(),
            'ipStart': $("#ipStart").val(),
            'ipEnd': $("#ipEnd").val(),
            'orgId': $("#orgId").val()
        },
        success: function (result) {
            $('#menuMaim').html(result);
        }
    });
}
function setPage(pageIndex) {
    var count = $('#PageCount').val();
    if (parseInt(pageIndex) < 1) {
        pageIndex = 1
    }
    if (parseInt(pageIndex) > parseInt(count)) {
        pageIndex = count
    }
    $('#PageIndex').val(pageIndex);
    getMenuData($('#type').val());
}
/*热门搜索词标签云*/
function tagCloud(time) {
    $.ajax({
        url: appPath + "/organize/tagcloud",
        type: "get",
        data: {
            "time": time,
            'ipStart': $("#ipStart").val(),
            'ipEnd': $("#ipEnd").val(),
            'orgId': $("#orgId").val()
        },
        dataType: "json",
        success: function (data) {
            $("#rmsscMain").html("");
            $("#rmsscMain").jQCloud(data, {
                removeOverflowing: true,
                width: 488,
                height: 274,
                shape: "rectangular",
            });
        }
    });
}
/*热门资源*/
function hotResource(flag, tab, type) {
    $.ajax({
        url: appPath + "/organize/hotresource",
        type: 'post',
        data: {
            'flag': flag,
            'tab': tab,
            'type': type,
            'PageIndex': $('#hotPageIndex').val(),
            'PageSize': $('#hotPageSize').val(),
            'ipStart': $("#ipStart").val(),
            'ipEnd': $("#ipEnd").val(),
            'orgId': $("#orgId").val()
        },
        success: function (result) {
            if (flag) {
                $('#rm-layer-main').html(result);
            } else {
                $('#rmzy-tabs-main').html(result);
            }
        }
    })
}
function hotpageJump() {
    var page = $("#hotpageNum").val();
    if (page != "" && /^[0-9]*$/.test(page)) {
        hotsetPage(page);
    } else {
        alert("请输入正确的页码!");
    }
}
function hotsetPage(pageIndex) {
    var count = $('#hotPageCount').val();
    if (parseInt(pageIndex) < 1) {
        pageIndex = 1
    }
    if (parseInt(pageIndex) > parseInt(count)) {
        pageIndex = count
    }
    $('#hotPageIndex').val(pageIndex);
    hotResource(true, $("#hottab").val(), $("#hotopt").val());
}
function openResource(id, type) {
    if (type == 1) {
        window.open(appPath + "/book/" + id);
    } else if (type == 2) {
        window.open(appPath + "/journal/" + id);
    } else if (type == 3) {
        window.open(appPath + "/standard/" + id);
    }
}
/*用户记录*/
function getUserlog() {
    $.ajax({
        url: appPath + "/organize/userlog",
        type: "post",
        data: {
            'PageIndex': $('#userPageIndex').val(),
            'PageSize': $('#userPageSize').val(),
            'ipStart': $("#ipStart").val(),
            'ipEnd': $("#ipEnd").val(),
            'orgId': $("#orgId").val()
        },
        success: function (data) {
            $("#yhjlMain").html(data);
        }
    });
}
function userpageJump() {
    var page = $("#userpageNum").val();
    if (page != "" && /^[0-9]*$/.test(page)) {
        usersetPage(page);
    } else {
        alert("请输入正确的页码!");
    }
}
function usersetPage(PageIndex) {
    var count = $('#userPageCount').val();
    if (parseInt(PageIndex) < 1) {
        PageIndex = 1
    }
    if (parseInt(PageIndex) > parseInt(count)) {
        PageIndex = count
    }
    $('#userPageIndex').val(PageIndex);
    getUserlog();
}
