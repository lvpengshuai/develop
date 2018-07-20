<%--
  Created by IntelliJ IDEA.
  User: mxp
  Date: 2017/9/1
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 1000px;height:500px">
</div>


<script>
    //获取年鉴json值
    var jsonObj=${bnamemaps};
    // 基于准备好的dom，初始化echarts实例
    //var echarts = require('echarts');
    var myChart = echarts.init(document.getElementById('main'));


    var data = genData();

    option = {
//        title : {
//            text: '年鉴分布统计',
//            subtext: '饼状图',
//            x:'center'
//        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        toolbox: {
            title: '保存为图片',
            top: '5%',
            left: '5%',
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
        legend: {
            type: 'scroll',
            orient: 'vertical',
            right: 0,
            top: 20,
            bottom: 5,
            backgroundColor: '#fff',
            padding:[20,70,20,20],
            lineHeight:30,
            borderWidth: 1,
            borderColor: 'red',
            data: data.legendData
        },
        series : [
            {
                name: '年鉴分布',
                type: 'pie',
                radius : '80%',
                center: ['40%', '55%'],
                data: data.seriesData,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 255, 0.5)'
                    }
                }
            }
        ]
    };
    //将值填入饼状图
    function genData() {
        var legendData = [];
        var seriesData = [];
        for(var item in jsonObj){
            var jValue=jsonObj[item];
            name=item;
            legendData.push(name);
            seriesData.push({
                name: name,
                value:jValue
            });
        }
//        for (var i = 0; i < 20; i++) {
//            name = "中国图书年鉴" + i;
//            //alert(name)
//            legendData.push(name);
//            seriesData.push({
//                name: name,
//                value: Math.round(Math.random() * 100000)
//                // value: 1
//            });
//        }
        return {
            legendData: legendData,
            seriesData: seriesData
        };

    }
    myChart.setOption(option);
</script>

</div>
