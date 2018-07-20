<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/ECharts/echarts.simple.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/ECharts/echarts-wordcloud.js"></script>
</head>
<body>
<div id="hotResearch" class="right-bd" style="width: 279px;height:269px;"></div>
<input id="hotResearchData" type="hidden" value="${hotResearchData}">
<script type="text/javascript">
    var dataArr=$("#hotResearchData").val().trim().split(";");
    if(dataArr.length<=1){
        $("#hotResearch").parent().hide();
    }
    var hotResearchData=[];
    for(var i=0; i<dataArr.length && i<15; i++){
        hotResearchData.push({
            name:dataArr[i],
            value:10000-600*i
        });
    }
    var chart1 = echarts.init(document.getElementById('hotResearch'));
    var option = {
        toolbox:{
            show:true,
            feature:{
                saveAsImage:{
                    show:true
                }
            }
        },
        series: [ {
            type: 'wordCloud',
            gridSize: 2,
            sizeRange: [12, 40],
            rotationRange: [0, 0],
            shape: 'pentagon',
            width: 279,
            height: 269,
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
            data: hotResearchData
        } ]
    };
    chart1.setOption(option);
    // 处理点击事件并且跳转到相应的百度搜索页面
    chart1.on('click', function (params) {
        window.open("/search?kw="+encodeURIComponent(params.name),"_blank");
    });
    window.onresize = chart1.resize;
    function down(src) {
        var $a = document.createElement('a');
        $a.setAttribute("href", src);
        $a.setAttribute("download", "");
        var evObj = document.createEvent('MouseEvents');
        evObj.initMouseEvent( 'click', true, true, window, 0, 0, 0, 0, 0, false, false, true, false, 0, null);
        $a.dispatchEvent(evObj);
    };
    function downloadEcharts(index) {
        var dataurl;
        if(index==1){
            dataurl = chart1.getDataURL();
        }else if(index==2){
            dataurl = chart2.getDataURL();
        }else{
            dataurl = chart3.getDataURL();
        }
        down(dataurl);
    }
</script>
</body>
</html>
