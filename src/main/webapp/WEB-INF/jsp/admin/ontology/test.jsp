<%--
  Created by IntelliJ IDEA.
  User: zly
  Date: 2017-3-2
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>

    <title>本体资源库</title>
    <!-- Stylesheets -->
    <link rel="stylesheet" href="${ctx}/static/remark/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/css/bootstrap-extend.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/assets/css/site.min.css">
    <!-- Plugins -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/animsition/animsition.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/asscrollable/asScrollable.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/switchery/switchery.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/intro-js/introjs.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/slidepanel/slidePanel.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/jquery-mmenu/jquery-mmenu.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/flag-icon-css/flag-icon.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/waves/waves.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/bootstrap-sweetalert/sweet-alert.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/glyphicons/glyphicons.css">
    <!-- Fonts -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/weather-icons/weather-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/web-icons/web-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/open-iconic/open-iconic.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/brand-icons/brand-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/material-design/material-design.min.css">
    <!--[if lt IE 9]>
    <script src="${ctx}/static/admin/js/ontology/atlas.js"></script>
    <![endif]-->


    <script>
        Breakpoints();
        var appPath = '${ctx}';
    </script>
</head>
<body class="site-navbar-small dashboard site-menubar-unfold">
<div id="main" style="width: 850px; height: 500px"></div>
<div id="ss" ></div>
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">

    require.config({
        paths : {
            echarts : 'http://echarts.baidu.com/build/dist'
        }
    });
    require([ "echarts", "echarts/chart/force"], function(ec) {
        var myChart = ec.init(document.getElementById('main'), 'macarons');
        var option = {
            tooltip : {
                show : false
            },
            series : [ {
                type : 'force',
                name : "Force tree",
                itemStyle : {
                    normal : {
                        label : {show : true},
                        nodeStyle : {
                            brushType : 'both',
                            borderColor : 'rgba(255,215,0,0.4)',
                            borderWidth : 1
                        }
                    }
                },
                categories : [ {name : '学校'}, {name : '校区'}, {name : '学院'}, {name : '班级'} ],
                nodes :
                    [{
                        "flag": true,
                        "symbolSize": 40,
                        "name": "0",
                        "ignore": false,
                        "id": 0,
                        "label": "硫酸",
                        "category": 0
                    }, {
                        "flag": true,
                        "symbolSize": 30,
                        "name": "1",
                        "ignore": true,
                        "id": 1,
                        "label": "化学性质",
                        "category": 1
                    }, {
                        "flag": true,
                        "symbolSize": 20,
                        "name": "2",
                        "ignore": true,
                        "id": 2,
                        "label": "腈",
                        "category": 2
                    }, {
                        "flag": true,
                        "symbolSize": 20,
                        "name": "4",
                        "ignore": true,
                        "id": 4,
                        "label": "[",
                        "category": 2
                    }, {
                        "flag": true,
                        "symbolSize": 20,
                        "name": "6",
                        "ignore": true,
                        "id": 6,
                        "label": "己二腈",
                        "category": 2
                    }, {
                        "flag": true,
                        "symbolSize": 30,
                        "name": "7",
                        "ignore": true,
                        "id": 7,
                        "label": "制备方法",
                        "category": 1
                    }, {
                        "flag": true,
                        "symbolSize": 20,
                        "name": "8",
                        "ignore": true,
                        "id": 8,
                        "label": "氧化吸收",
                        "category": 2
                    }, {
                        "flag": true,
                        "symbolSize": 20,
                        "name": "10",
                        "ignore": true,
                        "id": 10,
                        "label": "常压法",
                        "category": 2
                    }],
                links :[{
                    "source": 2,
                    "target": 1
                }, {
                    "source": 4,
                    "target": 3
                }, {
                    "source": 6,
                    "target": 5
                }, {
                    "source": 8,
                    "target": 7
                }, {
                    "source": 10,
                    "target": 9
                }, {
                    "source": 1,
                    "target": 0
                }, {
                    "source": 3,
                    "target": 0
                }, {
                    "source": 5,
                    "target": 0
                }, {
                    "source": 7,
                    "target": 0
                }, {
                    "source": 9,
                    "target": 0
                }]
            } ]
        };
        myChart.setOption(option);
        var ecConfig = require('echarts/Config');
        function openOrFold(param) {
            var option = myChart.getOption();
            var nodesOption = option.series[0].nodes;
            var linksOption = option.series[0].links;
            var data = param.data;
            var linksNodes = [];

            var categoryLength = option.series[0].categories.length;

            if (data.category == (categoryLength - 1)) {
                alert(data.label);
            }

            if (data != null && data != undefined) {
                if (data.flag) {

                    for ( var m in linksOption) {

                        if (linksOption[m].target == data.id) {
                            linksNodes.push(linksOption[m].source);
                        }
                    }
                    if (linksNodes != null && linksNodes != undefined) {
                        for ( var p in linksNodes) {
                            nodesOption[linksNodes[p]].ignore = false;
                            nodesOption[linksNodes[p]].flag = true;
                        }
                    }
                    nodesOption[data.id].flag = false;
                    myChart.setOption(option);
                } else {

                    for ( var m in linksOption) {

                        if (linksOption[m].target == data.id) {
                            linksNodes.push(linksOption[m].source);
                        }
                        if (linksNodes != null && linksNodes != undefined) {
                            for ( var n in linksNodes) {
                                if (linksOption[m].target == linksNodes[n]) {
                                    linksNodes.push(linksOption[m].source);
                                }
                            }
                        }
                    }
                    if (linksNodes != null && linksNodes != undefined) {
                        for ( var p in linksNodes) {
                            nodesOption[linksNodes[p]].ignore = true;
                            nodesOption[linksNodes[p]].flag = true;
                        }
                    }
                    nodesOption[data.id].flag = true;
                    myChart.setOption(option);
                }
            }
        }
        myChart.on(ecConfig.EVENT.CLICK, openOrFold);
    });
</script>

</body>
</html>