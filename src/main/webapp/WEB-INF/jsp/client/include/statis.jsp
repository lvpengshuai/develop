<%--
  Created by IntelliJ IDEA.
  User: mxp
  Date: 2017/9/5
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <div id="personmain" style="width: 800px;height:500px;float: left"></div>

    <div style="padding-right: 40px">
        <div class="statisfrequency">频次</div>
        <div class="statisname">人物</div>

        <div style="height:500px;float: right;overflow-y: scroll;">

            <div class="statisright">
                <c:forEach items="${personmaps}" begin="0" end="25" var="subs">
                    <div style="text-align: left;padding-left: 15px">
                        <input onclick="re()" type="checkbox" name="name" value="${subs.key}">
                        <div class="subkey">${subs.key}</div>
                    </div>
                </c:forEach>
            </div>
            <div class="statisright1">
                <c:forEach items="${personmaps}" begin="0" end="25" var="subs">
                    <div><span>${subs.value}</span></div>
                </c:forEach>
            </div>

        </div>
    </div>

    <script>


        var myChart1 = echarts.init(document.getElementById('personmain'));




        

        function re() {
            obj = document.getElementsByName("name");
            check_val = [];
            for (k in obj) {
                if (obj[k].checked)
                    check_val.push(obj[k].value);
            }
            $.ajax({
                url: "${ctx}/analy/statis?skw=" + check_val,
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
        }


    </script>
</div>