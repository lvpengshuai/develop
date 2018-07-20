<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mxp
  Date: 2017/9/14
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div id="organmain" class="organleft"></div>

    <div style="padding-right: 40px;">
        <div class="organfrequency">频次</div>
        <div class="organname">机构名称</div>


        <div style="height:500px;float: right;overflow-y: scroll">

            <div class="organright">
                <c:forEach items="${organmaps}" begin="0" end="25" var="organname">
                    <div style="text-align: left;padding-left: 15px">
                        <input onclick="organs()" type="checkbox" name="organname"
                               value="${organname.key}:${organname.value}"/>
                        <div class="organnamekey" title="${organname.key}">${organname.key}</div>
                    </div>
                </c:forEach>
            </div>
            <div class="organright1">
                <c:forEach items="${organmaps}" begin="0" end="25" var="organname">
                    <div><span>${organname.value}</span></div>
                </c:forEach>
            </div>
        </div>
    </div>
    <script>
        var myChart4 = echarts.init(document.getElementById('organmain'));

        obj = document.getElementsByName("organname");
        var temps = [];

        <c:forEach items="${organmaps}" begin="0" end="4" varStatus="i" var="organname">
        obj[${i.index}].checked = true;
        temps.push("${organname.key}:${organname.value}");
        </c:forEach>

        <%--人物初始化数据--%>
        <%--var tempss = [];--%>
        <%--personmapsobj = document.getElementsByName("name");--%>
        <%--<c:forEach items="${personmaps}" begin="0" end="4" varStatus="i" var="subs">--%>
        <%--personmapsobj[${i.index}].checked=true;--%>
        <%--tempss.push("${subs.key}");--%>
        <%--</c:forEach>--%>
        var tempss = [];
        //点击初始化
        $(function () {
            $("#character").click(function () {
                if (tempss != "") {
                    return;
                }
                // 人物初始化数据
                var personmapsobj = document.getElementsByName("name");
                <c:forEach items="${personmaps}" begin="0" end="2" varStatus="i" var="subs">
                personmapsobj[${i.index}].checked = true;
                tempss.push("${subs.key}");
                </c:forEach>


                /*人物初始化*/
                $.ajax({
                    url: "${ctx}/analy/statis?skw=" + tempss,
                    async: false,
                    dataType: 'json',
                    type: 'get',
                    success: function (data) {
                        var json = null;
                        // json.clear();
                        myChart1.clear();
                        var _date = [];
                        var _name = [];
                        var _value = [];
                        json = data;
                        for (var k in json) {
                            var jsonk = json[k]
                            _date.push(k)
                            for (var j in jsonk) {
                                _name.push(j)
                                _value.push(jsonk[j])
                            }
                        }

                        function setSeries() {
                            var series = [];
                            for (var i = 0; i < _value.length / _date.length; i++) {
                                var _itme = [];
                                _itme.splice(0, _itme.length);
                                for (var j = i; j < _name.length; j++) {
                                    if (_name[i] == _name[j]) {
                                        _itme.push(_value[j])
                                    }
                                }
                                series.push({
                                    name: _name[i],
                                    type: 'bar',
                                    barWidth: '60%',
                                    barMaxWidth: 30,//最大宽度
                                    stack: '总量',
                                    label: {
                                        normal: {
                                            show: true,
                                            position: 'insideRight'
                                        }
                                    },
                                    data: _itme
                                });


                            }

                            return series;
                        }

                        option2 = {
                            tooltip: {
                                trigger: 'axis',
                                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                }
                            },
                            legend: {
                                data: _name,
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            xAxis: {
                                type: 'value'
                            },
                            yAxis: {
                                type: 'category',
                                data: _date
                            },
                            series: setSeries()
                        };
                        myChart1.setOption(option2);

                    }


                })


            })
        })


        window.onload = function () {
            var organjson = {};

            //切割转换的数组
            for (var i = 0; i < temps.length; i++) {
                //定义临时变量数组
                var temp = [];
                temp = temps[i].split(":");
                organjson[[temp[0]]] = temp[1]
            }
//            for(var item in organjson){
//                alert(item)
//            }

            option4 = {
                itemStyle: {
                    normal: {
                        color: function (params) {
                            //首先定义一个数组
                            var colorList = getorgan().color;
                            // [
//                        '#C33531', '#EFE42A', '#64BD3D', '#EE9201', '#29AAE3',
//                        '#B74AE5', '#0AAF9F', '#E89589'
//                    ];
                            return colorList[params.dataIndex]
                        },
                        // color: '各异',
                        barBorderColor: '#FBFBFB',       // 柱条边线
                        barBorderRadius: 5,           // 柱条边线圆角，单位px，默认为0
                        barBorderWidth: 1,            // 柱条边线线宽，单位px，默认为1
                        shadowOffsetY: 5,
                        shadowColor: '#0AAF9F',
                        shadowBlur: 5,
                        label: {
                            show: 'right'
                            // position: 默认自适应，水平布局为'top'，垂直布局为'right'，可选为
                            //           'inside'|'left'|'right'|'top'|'bottom'
                            // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                        }
                    },
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: getorgan().datax,
                        nameRotate: 5,
                        // show : false
                        axisTick: {
                            show: false,
                            alignWithLabel: false
                        },
                        axisLine: {
                            show: false
                        },
                        label: {
                            show: false
                        },
                        nameTextStyle: {
                            fontWeight: 'bold',
                        },
                        axisLabel: {
                            interval: 0,
                            //rotate:180,
                            formatter: function (value) {
                                return value.split("").join("\n");
                            },
                            textStyle: {
                                nameRotate: 5,
                                color: '#000',
                                verticalAlign: 'center',
                                padding: [-200, 0, 0, 0],
                                lineHeight: 50,
                                fontSize: 16,
                            }
                        },
                        splitLine: {
                            show: false
                        },
                        z: 999,
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        splitLine: {
                            show: false
                        },
                    }
                ],
                series: [
                    {
                        // name: '机构统计图',
                        type: 'bar',
                        barWidth: '60%',
                        barMaxWidth: 90,//最大宽度
                        data: getorgan().series
                    }
                ]
            };


            function getorgan() {
                var series = [];
                var datax = [];
                var color = [];
                for (var item in organjson) {
                    datax.push(item);
                    var data = organjson[item];
                    series.push(data);
                    color.push("rgba(" + parseInt(parseInt(Math.random() * 255)) + "," + parseInt(parseInt(Math.random() * 255)) + ", " + parseInt(parseInt(Math.random() * 255)) + ",1)")
                }
                return {
                    series: series,
                    datax: datax,
                    color: color
                }
            }

            myChart4.setOption(option4);
        }

        function organs() {
            obj = document.getElementsByName("organname");
            check_val = [];
//            for (k in obj) {
//                alert(k);
//
//            }
            for (var i = 0 ; i < obj.length;i++){
                if (obj[i].checked) {
                    check_val.push(obj[i].value)
                }
            }

            var organjson = {};

            //var orglist = JSON.stringify(check_val);

            //切割转换的数组
            for (var i = 0; i < check_val.length; i++) {
                //定义临时变量数组
                var temp = [];
                temp = check_val[i].split(":");
                organjson[[temp[0]]] = temp[1]
            }
//            for(var item in organjson){
//                alert(item)
//            }

            option4 = {
                itemStyle: {
                    normal: {
                        color: function (params) {
                            //首先定义一个数组
                            var colorList = getorgan().color;
                            // [
//                        '#C33531', '#EFE42A', '#64BD3D', '#EE9201', '#29AAE3',
//                        '#B74AE5', '#0AAF9F', '#E89589'
//                    ];
                            return colorList[params.dataIndex]
                        },
                        // color: '各异',
                        barBorderColor: '#FBFBFB',       // 柱条边线
                        barBorderRadius: 5,           // 柱条边线圆角，单位px，默认为0
                        barBorderWidth: 1,            // 柱条边线线宽，单位px，默认为1
                        shadowOffsetY: 5,
                        shadowColor: '#0AAF9F',
                        shadowBlur: 5,
                        label: {
                            show: 'right'
                            // position: 默认自适应，水平布局为'top'，垂直布局为'right'，可选为
                            //           'inside'|'left'|'right'|'top'|'bottom'
                            // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                        }
                    },
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
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: getorgan().datax,
                        nameRotate: 5,
                        // show : false
                        axisTick: {
                            show: false,
                            alignWithLabel: false
                        },
                        axisLine: {
                            show: false
                        },
                        label: {
                            show: false
                        },
                        nameTextStyle: {
                            fontWeight: 'bold',
                        },
                        axisLabel: {
                            interval: 0,
                            //rotate:180,
                            formatter: function (value) {
                                return value.split("").join("\n");
                            },
                            textStyle: {
                                nameRotate: 5,
                                color: '#000',
                                verticalAlign: 'center',
                                padding: [-200, 0, 0, 0],
                                lineHeight: 50,
                                fontSize: 16,
                            }
                        },
                        splitLine: {
                            show: false
                        },
                        z: 999,
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        splitLine: {
                            show: false
                        },
                    }
                ],
                series: [
                    {
                        // name: '机构统计图',
                        type: 'bar',
                        barWidth: '60%',
                        barMaxWidth: 90,//最大宽度
                        data: getorgan().series
                    }
                ]
            };


            function getorgan() {
                var series = [];
                var datax = [];
                var color = [];
                for (var item in organjson) {
                    datax.push(item);
                    var data = organjson[item];
                    series.push(data);
                    color.push("rgba(" + parseInt(parseInt(Math.random() * 255)) + "," + parseInt(parseInt(Math.random() * 255)) + ", " + parseInt(parseInt(Math.random() * 255)) + ",1)")
                }
                return {
                    series: series,
                    datax: datax,
                    color: color
                }
            }

            myChart4.setOption(option4);

        }


    </script>
</div>
