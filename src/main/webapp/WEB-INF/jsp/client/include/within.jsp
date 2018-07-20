<%--
  Created by IntelliJ IDEA.
  User: mxp
  Date: 2017/9/4
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <div id="withinmain" style="width: 1200px;height: 500px;"></div>
    <script>
        var myChart = echarts.init(document.getElementById('withinmain'));

        var yearjson =${yearmaps};

        var data = getData();
        // var colors = ['#5793f3', '#d14a61', '#675bba'];

        option = {
            title: {
                //text: '折线图堆叠'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['条目数'],
                x: 'left',
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                title: '保存为图片',
                top: '0%',
                right: '3%',
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
            xAxis: {
                width: 20,
                type: 'category',
                boundaryGap: true,
                // data: ['2016年','2014年','2013年','2012年','2011年','2010年','2009年','2008年','2007年','2006年'],
                data: data.legendData,
                splitLine: {
                    show: false
                }
            },
            yAxis: {
                type: 'value',
                splitLine: {
                    show: false
                }
            },
            series: [
                {
                    name: '条目数',
                    symbol: 'circle',
                    symbolSize: 22,
                    type: 'line',
                    stack: '总量',
                    data: data.seriesData
                }
            ]
        };

        function getData() {

            var seriesData = [];
            var legendData = [];

            for(var item in yearjson){

             var jValue=yearjson[item];
                seriesData.push(jValue);
                legendData.push(item-1);

        }

//            for (var i = 0; i < 10; i++) {
//
//                seriesData.push(Math.round(Math.random() * 1000));
//                legendData.push(i);
//
//            }

            return {
                seriesData: seriesData,
                legendData: legendData
            };


        }
        myChart.setOption(option)
    </script>
</div>