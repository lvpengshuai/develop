<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src='https://cdn.bootcss.com/echarts/3.2.2/echarts.simple.js'></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/ECharts/echarts-wordcloud.js"></script>
</head>
<body>
    <div id="hotWords" class="graph" style="width: 939px;height:616px;"></div>
    <script type="text/javascript">
        $(function () {
            var keywords =  [
                {
                    name: '土地经济学',
                    value: 10000
                },
                {
                    name: '通货膨胀',
                    value: 6181
                },
                {
                    name: '增长',
                    value: 4386
                },
                {
                    name: '机械工程技术知识库',
                    value: 4055
                },
                {
                    name: '电工设备',
                    value: 2467
                },
                {
                    name: '电力系统',
                    value: 2244
                },
                {
                    name: 'Planet Fitness',
                    value: 1898
                },
                {
                    name: 'Pitch Perfect',
                    value: 1484
                },
                {
                    name: 'Express',
                    value: 1112
                },
                {
                    name: 'Home',
                    value: 965
                },
                {
                    name: 'Johnny Depp',
                    value: 847
                },
                {
                    name: 'Lena Dunham',
                    value: 582
                },
                {
                    name: 'Lewis Hamilton',
                    value: 555
                },
                {
                    name: 'KXAN',
                    value: 550
                },
                {
                    name: 'Mary Ellen Mark',
                    value: 462
                },
                {
                    name: 'Farrah Abraham',
                    value: 366
                },
                {
                    name: 'Rita Ora',
                    value: 360
                },
                {
                    name: 'Serena Williams',
                    value: 282
                },
                {
                    name: 'NCAA baseball tournament',
                    value: 273
                },
                {
                    name: 'Point Break',
                    value: 265
                }
            ];
            var myChart = echarts.init(document.getElementById('hotWords'));
            var option = {
                series: [ {
                    type: 'wordCloud',
                    gridSize: 2,
                    sizeRange: [15, 150],
                    rotationRange: [0, 0],
                    width: 900,
                    height: 600,
                    drawOutOfBound: true,
                    textStyle: {
                        normal: {
                            color: function () {
                                return 'rgb(' + [
                                        Math.round(Math.random() * 160),
                                        Math.round(Math.random() * 160),
                                        Math.round(Math.random() * 160)
                                    ].join(',') + ')';
                            }
                        },
                        emphasis: {
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    data:keywords
                } ]
            };
            myChart.setOption(option);
            window.onresize = myChart.resize;
        });
    </script>
</body>
</html>
