<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/ECharts/echarts.min.js"></script>
</head>
<body>
    <div id="relatedInstitution" class="right-bd" style="width: 279px;height:269px;"></div>
    <input id="relatedInstitutionData" type="hidden" value="${relatedInstitutionData}">
    <script type="text/javascript">
        var chart3;
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var relatedInstitutionChart = echarts.init(document.getElementById('relatedInstitution'));
            var dataArr = $("#relatedInstitutionData").val().split(";");
            if(dataArr.length<=1){
                $("#relatedInstitution").parent().hide();
            }
            var numbers= dataArr.length>9?9:dataArr.length;
            var relatedInstitutionData = [
                {
                    name: '机构',
                    itemStyle: {
                        normal: {
                            color: "#6eda6a"
                        }
                    },
                    draggable: true,
                    link: 'www.baidu.com'
                },
            ];
            function getRandomColor(){
                var colors = ['#c1dff7', '#f6bdac', '#b4c5f1', "#b4c5f1", "#e6b6e6", '#a1dea2', '#f8b5d8', '#f6bdac', '#dc97d8'];
                return colors[parseInt(Math.random() * 9)];
            }
            for (var i = 0; i < numbers; i++) {
                relatedInstitutionData.push({
                    name: dataArr[i],
                    itemStyle: {
                        normal: {
                            color: getRandomColor()
                        }
                    },
                    draggable: true,
                });
            }
            console.log(relatedInstitutionData);
            var linksData=[];
            for(var i=1;i<=numbers;i++){
                linksData.push({
                    source: 0,
                    target: i
                })
            }
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: ''
                },
                tooltip: {},
                animationDurationUpdate: 1500,
                animationEasingUpdate: 'quinticInOut',
                label: {
                    normal: {
                        show: true,
                        textStyle: {
                            fontSize: 12
                        },
                    }
                },
                series: [

                    {
                        type: 'graph',
                        layout: 'force',
                        symbolSize: 30,
                        focusNodeAdjacency: true,
                        label: {
                            normal: {
                                show: true,
                                textStyle: {
                                    color: '#747474',
                                    fontSize: 15
                                },
                            }
                        },
                        force: {
                            repulsion: 450
                        },
                        data: relatedInstitutionData
                        ,
                        links: linksData
                        ,
                        lineStyle: {
                            normal: {
                                opacity: 0.9,
                                width: 1,
                                curveness: 0.3
                            }
                        },
                        nodeClick: 'link',
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            relatedInstitutionChart.setOption(option);
            relatedInstitutionChart.on('click', function (params) {
                window.open('${ctx}/search?kw=' + encodeURIComponent(params.name));
            });
            chart3=relatedInstitutionChart;
        });
    </script>
</body>
</html>