<%--
  Created by IntelliJ IDEA.
  User: epro
  Date: 2017/9/10
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="width:1050px;height: 600px;padding: 0px;left: -140px;" id="hotPerson">
</div>
<script>
    $(".fenxilist #yearrmrw dd").click(function () {
        var info = $(this).attr("value");
        $(this).addClass("on").siblings().removeClass("on");
        showHotPerson(info);
    });

    function showHotPerson(info) {
        $.ajax({
            url : "${ctx}/book/analysisHotword?bookAbbreviate=${bookAbbreviate}&bookcode="+ info +"&type=personname",
            type : "get",
            dataType : "json",
            success : function(data) {
                var JosnList1 = [];
                var models = eval(data["msg"]);
                for(var i in models){
                    JosnList1.push(models[i]);
                }
                var seriesName = "人物姓名";
                var hotWord = echarts.init(document.getElementById('hotPerson'));
                option = {
                    tooltip: {
                        formatter: function (params,ticket) {
                            return "<div style='text-align: center; min-width: 100px;'>"
                                    + params.seriesName
                                    + "<br>"
                                    + params.name
                                    + "<hr>"
                                    + "频数"
                                    + "<br>"
                                    + JosnList1[params.dataIndex].count
                                    + "</div>";
                        },
                        show: true,
                        backgroundColor: '#3878D9',
                    },
                    toolbox: {
                        title: '保存为图片',
                        top: '5%',
                        right: '10%',
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
                    series: [{
                        name: seriesName,
                        type: 'wordCloud',
                        sizeRange: [40, 99],
                        rotationRange: [0, 0],
                        shape: 'triangle',
                        textPadding: 0,
                        gridSize: 15,
                        drawOutOfBound: false,
                        autoSize: {
                            enable: false,
                            minSize: 6
                        },
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
                        data: [{
                            name: "社会",
                            value: 999
                        }, {
                            name: "经济",
                            value: 999
                        }]
                    }]
                };

                option.series[0].data = JosnList1;
                hotWord.on('click', function (params) {
                    window.open('${ctx}/search?kw=' + encodeURIComponent(params.name));
                    hotWord.on.clear();
                });

                // 为echarts对象加载数据
                hotWord.setOption(option);
                hotWord.on('mouseover', function (params) {
                });
            },

            error: function (XMLHttpRequest, textStatus, errorThrown) {
//                alert(XMLHttpRequest.status);
//                alert(XMLHttpRequest.readyState);
//                alert(textStatus);
            }
        });
    }

    function eConsole(param) {
        if (typeof param.seriesIndex == 'undefined') {
            return;
        }
        if (param.type == 'click') {
            alert(param.name);
        }
    }
</script>
