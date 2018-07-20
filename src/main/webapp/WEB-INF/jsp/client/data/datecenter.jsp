<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>中国社会科学年鉴数据库</title>
    <!-- 共用 js -->
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>

    <!-- 公用搜索进入文件 -->
    <script type="text/javascript" src="${ctx}/static/client/customer/publicJS.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/customer/search.js"></script>
    <!-- 公用搜索进入文件 -->

    <!-- 共用css -->
    <link rel="stylesheet" href="${ctx}/static/client/css/style.css"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/static/client/flexPaper/css/flexpaper.css">
    <script type="text/javascript" src="${ctx}/static/client/flexPaper/js/flexpaper.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/flexPaper/js/flexpaper_handlers_debug.js"></script>
    <link rel="stylesheet" href="${ctx}/static/client/css/alertify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/alertify.js"></script>
</head>
<style>

    .ztree li button.switch {
        visibility: hidden;
        width: 1px;
    }

    .ztree li button.switch.roots_docu {
        visibility: visible;
        width: 16px;
    }

    .ztree li button.switch.center_docu {
        visibility: visible;
        width: 16px;
    }

    .ztree li button.switch.bottom_docu {
        visibility: visible;
        width: 16px;
    }

    .datalist li a {
        background-color: #8c0000;
        padding: 5px 20px;
    }

    .datalist li ul a {
        font-size: 15px;
    }

    .datalist li ul li a {
        width: 10px;
    }


</style>
<body>

<!--顶部-->
<%@include file="/WEB-INF/jsp/client/include/publicTop.jsp" %>

<!--中间-->
<div class="usercenter">

    <!-- 左侧 -->
    <div class="databoxleft">
        <h3>请选择指标</h3>
        <div class="datalist">

        </div>
    </div>

    <!-- 右侧 -->
    <div class="databoxright">
        <div class="search_box">
            <input name="" type="button" class="btn_search datasearch" value="搜索"/>
            <input placeholder="输入关键字" type="text" class="datasearch_value"/>
        </div>
        <div style="height: 120px;">
            <button class="dowload_excel" style="margin-top:95px;float: right;width: 90px;height: 30px;background: #0458ce;color: white">下载数据表</button>
        </div>
        <div class="databoxshow" id="swfId">
            <iframe width="100%" src="/getIfreams" style="min-height:760px;"></iframe>
            <%--<div id="documentViewer" class="flexpaper_viewer" style="width: 100%;height: 760px;"></div>--%>
            <%--flash阅读--%>
            <script type="text/javascript">
                $('#documentViewer').FlexPaperViewer(
                    {
                        config: {

                            SWFFile: '../static/client/flexPaper/FlexPaperViewer.swf',

                            Scale: 0.6,
                            ZoomTransition: 'easeOut',
                            ZoomTime: 0.5,
                            ZoomInterval: 0.2,
                            FitPageOnLoad: true,
                            FitWidthOnLoad: false,
                            FullScreenAsMaxWindow: false,
                            ProgressiveLoading: false,
                            MinZoomSize: 0.2,
                            MaxZoomSize: 5,
                            SearchMatchAll: false,

                            RenderingOrder: 'flash',

                            ViewModeToolsVisible: true,
                            ZoomToolsVisible: true,
                            NavToolsVisible: true,
                            CursorToolsVisible: true,
                            SearchToolsVisible: true,
                            WMode: 'window',
                            localeChain: 'zh_CN'
                        }
                    }
                );

                function setSWF(str) {
                    $FlexPaper('documentViewer').load({
                        SWFFile: str
                    })
                }
                $(document).ready(function(){
                    $(".list_tb").css({"background":"url(../../../../static/client/img/haed_li.png) repeat center center","color":" #878787","display":"block"});
                });
            </script>
        </div>
    </div>

</div>
<!-- 顶部搜索弹出 -->
<div class="layer layer-search">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="newclose"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="layerinfo">
            <div class="datadetail">
                <h5>您要找的是不是？</h5>
                <dl id="layer-search_dl">
                </dl>
            </div>
        </div>
    </div>
</div>

<!-- 底部 -->
<%@include file="/WEB-INF/jsp/client/include/publicFoot.jsp" %>
<!-- 弹出高级检索 -->
<script>
    // 导航栏选定方法
    set_class(".nav .navbox a", "on", "数据中心");
</script>


</body>
<script type="text/javascript">
    $(function () {
        //数据中心
        $(".datalist li > a").click(function () {
            $(this).toggleClass("active");
            $(this).parent("li").siblings().children("a").removeClass("active");
            $(this).next("ul").toggle();
            $(this).parent("li").siblings().children("ul").slideUp();
        });
        //数据中心搜索
        $(".newclose, .datadetail dd").click(function(){
            $(".layer-search").hide();
        });

        $(".datasearch").click(function () {
            var datasearch_value =$.trim($(".datasearch_value").val());
            if (datasearch_value!=""){
                console.log(datasearch_value);
                $(".layer-search").show();
                $.ajax({
                    url: "/getDataSearch",
                    type: "POST",
                    data: {
                        search: datasearch_value,
                    },
                    success: function (data) {
                        var jieguo = "";
                        for (var i in data) {
                            var j = "\"" + data[i].node_Url + "\"";
                            var k="\""+data[i].tid+"\"";
                            jieguo = jieguo + "<dd><a onclick='iframeUrl(" + j + "," + k + ")'>" + data[i].name + "</a></dd>"
                        }
                        if (jieguo!=""){
                            $("#layer-search_dl").html(jieguo);
                        }else {
                            $("#layer-search_dl").html("暂无数据");
                        }
                    }
                })
            }
        });

        $(".dowload_excel").click(function () {
            $.ajax({
                url: "${ctx}/organize/authorization",
                dataType: "json",
                data: {
                    'name': 'excel下载', dateTime: Math.random(),//随机函数------------------解决ie下兼容问题
                    iiiPP: "",
                },
                type: "get",
                success: function (data) {
                    if (data.status == 0) {
                        var sss = $(".iframeClass").attr("excel");
                        if (sss != undefined) {
                            window.location.href = "${ctx}/downloadExcel?id=" + sss;
                        } else {
                            alertify.alert("请选择图表");
                        }
                    } else if (data.status == 2) {
                        alertify.alert("权限不足");
                    }
                    else {
                        alertify.alert("登录后才能下载excel。");
                    }
                }
            })


        });
    });

    function iframeUrl(data, id) {
        expandNode(id);
        $(".layer-search").hide();
        var databoxleftHeight=$(".databoxleft").height()-140+"px";
        var sss=encodeURI("/getIfreams?file=" + data);
        var str = "<iframe width='100%' height='"+databoxleftHeight+"' src='"+sss+"'></iframe>";
        $("#swfId").html(str);
    }

    $(function () {
        showdetail();
    });

    function showdetail() {
        $.ajax({
            url: "/show",
            success: function (result) {
                $(".datalist").html(result);
            },
            error: function (result) {
                alert("失败");
            }
        });
    }
</script>


</html>
