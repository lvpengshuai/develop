<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <div id="relatedPeople" class="right-bd" style="width: 279px;height:269px;"></div>
    <input id="relatedPeopleData" type="hidden" value="${relatedPeopleData}">
    <script type="text/javascript">
        var chart2;
        $(function () {
            var relatedPeopleChart = echarts.init(document.getElementById('relatedPeople'));
            // 基于准备好的dom，初始化echarts实例
            var dataArr=$("#relatedPeopleData").val().split(";");
            if(dataArr.length<=1){
                $("#relatedPeople").parent().hide();
            }
            var numbers= dataArr.length>9?9:dataArr.length;
            var relatedPeopleData=[];
            function getRandomColor(){
                var colors = ['#f7b4d7', '#c1dff7', '#f6bdac', "#b4c5f1", "#b4c5f1", '#e6b6e6', '#a1dea2', '#f8b5d8', '#f6bdac'];
                return colors[parseInt(Math.random() * 9)];
            }
            for(var i=0;i<numbers;i++){
                relatedPeopleData.push({
                    name: dataArr[i],
                    itemStyle: {
                        normal: {
                            color: getRandomColor()
                        }
                    },
                    symbolSize: Math.floor(Math.random()*40)+40
                });
            }
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '',
                    link:'',
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
                        symbolSize: 50,
                        focusNodeAdjacency: true,
                        label: {
                            normal: {
                                show: true,
                                textStyle: {
                                    fontSize: 13
                                },
                            }
                        },
                        force: {
                            repulsion: 100
                        },
                        data: relatedPeopleData
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            relatedPeopleChart.setOption(option);
            chart2=relatedPeopleChart;
            chart2.on('click', function (params) {
                window.open("/search?kw="+encodeURIComponent(params.name),"_blank")
            });
        });
    </script>
