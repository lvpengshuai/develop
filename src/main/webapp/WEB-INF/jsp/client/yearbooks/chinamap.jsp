<%--
  Created by IntelliJ IDEA.
  User: xubo
  Date: 2017/9/4
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=EDGE">
    <%--<script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>--%>
    <%--<script src="${ctx}/static/client/js/ECharts/echarts.min.js"></script>--%>
    <script src="${ctx}/static/client/js/ECharts/china.js"></script>
    <style>
        #china-map {width:750px; height: 800px;}</style>
</head>
<body>

<div id="china-map" style="position: relative; overflow: hidden; width: 850px; height: 600px; padding: 0px; margin: 0px; border-width: 0px; cursor: default;"></div>

<script>
    function getdata(showvalue) {
        var jValue;
        var showDataJson = $.parseJSON('${showdata}');
        $.each( showDataJson, function(index, content){
            for(var item in content){
                if(item==showvalue){
                    jValue =content[item];
                }
            }
        })
        return jValue;
    }
    var myChart = echarts.init(document.getElementById('china-map'));
    function getData() {
        return $.parseJSON('${valdata}');
    }
    option = {
        tooltip: {
            formatter:'${b}',
            position: function (pos, params, dom, rect, size) {
                // 鼠标在左侧时 tooltip 显示到右侧，鼠标在右侧时 tooltip 显示到左侧。
                if(pos[0]<size.viewSize[0] / 2){
                    return 'right';
                }else {
                    return 'left';
                }
            },
            trigger: 'item',
            backgroundColor: '#0079e9',
            borderWidth: 1,
            borderColor: '#ccc',
            padding: 10,
            width:200,
            height:80,
            alwaysShowContent:false,
            textStyle: {
                color: '#000'
            },
            extraCssText: 'width: 200px',
            formatter: function (params) {
                var aa = params.data;
                var b =  getdata(params.name);
                return b;
            },
        },
        visualMap: {
            show: false,
            min: 0,
            max: 2500,
            left: 'left',
            top: 'bottom',
            calculable: true
        },
        toolbox: {
            title: '保存为图片',
            top: '5%',
            right: '5.3%',
            showTitle: true,
            itemSize: 18,
            feature: {
                saveAsImage: {
                    show: true,
                    showTitle: true,
                    title: '保存为图片',
                    icon: 'image://' + '${ctx}/static/client/img/icon_2.png',
                    type: 'png',
                    lang: '点击本地保存'
                }
            }
        },
        series: [
            {
                type: 'map',
                mapType: 'china',
                roam: false,
                label: {
                    normal: {
                        show: true

                    },
                },
                data: function(){
                    return getData();
                }()
            },
        ],
    };

    myChart.setOption(option);
    myChart.on('mouseover', function (params) {
        var dataIndex = params.dataIndex;
//        console.log(dataIndex);
//        var a=randomData();
    });
</script>

</body>
</html>
