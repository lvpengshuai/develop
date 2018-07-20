/**
 * Created by zhangpeng on 2017/4/11.
 */

var vm = new Vue({
    chartVm: {},
    el: "#content",
    data: {
        word: "硫酸",
        myChart: {},
        data: [],
        links: [],
        viewed: [],
        cache: {},
        list_cache: []
    },
    created: function () {

    },
    mounted: function () {
        this.$nextTick(function () {
            this.init();
        });
    },
    methods: {
        init: function () {
            //初始化echarts实例
            var searchWord = $("#knowledge_name").text().trim()

            this.myChart = echarts.init(document.getElementById('main'));

            this.loadGraph(searchWord, 1, "", true, 0);
            vm.chartVm = this.myChart;

            this.myChart.on('click', function (params) {
                var name = params.data.name;
                //获取数据
                var type = params.data.type;
                if (type == 1) {
                    vm.viewKnowledge(name);
                }

                var parent = params.data.parent;
                var id = params.dataIndex;

                if (vm.viewed.indexOf(name + "|" + type + parent + id) != -1) {
                    vm.refreshList(id);
                    return;
                }

                vm.viewed.push(name + "|" + type + parent + id);

                vm.loadGraph(name, type, parent, false, id);
            });
        },
        makeOption: function (nodes, links) {
            var series = [{
                type: 'graph',
                layout: 'force',
                symbolSize: function (value, params) {
                    var type = params.data.type;
                    if (type == 1) {
                        return 60;
                    }
                    return 40;
                },
                roam: true,
                symbol: "circle",
                data: nodes,
                links: links,
                draggable: false,
                hoverAnimation: true,
                focusNodeAdjacency: true,
                force: {
                    repulsion: 900,
                    edgeLength: [200, 500],
                    gravity: 0.1
                },
                label: {
                    normal: {
                        position: 'right',
                        show: true,
                        textStyle: {
                            fontSize: 14
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: function (params) {
                            var type = params.data.type;
                            if (type == 1) {
                                return '#62a8ea';
                            }
                            return '#f2a654';

                        }
                    }
                },
                lineStyle: {
                    normal: {
                        color: "#526069",
                        curveness: 0
                    }
                },
                edgeLabel: {
                    normal: {
                        show: false,
                        textStyle: {
                            color: "#263238"
                        }
                    }
                }
            }];
            var option = {
                animationDurationUpdate: 100,
                animationDuration: 1500,
                animationEasingUpdate: 'quinticInOut',
                series: series
            };
            return option;
        },
        loadGraph: function (name, type, parent, isRoot, id) {

            var params = {
                name: type == 0 ? parent : name,
                type: type == 0 ? name : "",
                root: isRoot ? false : true
            }

            vm.$http.post(appPath + "/atlas/word", params, {emulateJSON: true}).then(function (res) {
                var data = JSON.parse(res.bodyText);
                if (data.code != 10000) {
                    console.log("error:" + data.msg);
                    return;
                }
                var option = this.myChart.getOption();


                if (isRoot) {
                    //清空数据
                    this.data = [];
                    this.links = [];
                    this.cache = {};
                    this.viewed = [];

                    var target = {
                        id: id,
                        name: name,
                        type: type,
                        parent: parent
                    }
                    this.data.push(target);
                    vm.viewed.push(name + "|" + type + parent + id);
                }

                var graph = data.msg;

                var index = this.data.length;

                this.data = isRoot ? this.data.concat(graph.nodes) : option.series[0].data.concat(graph.nodes);

                //缓存显示数据
                if (type == 0) {
                    // this.cache[name] = graph.nodes;
                    this.$set(this.cache, id, graph.nodes);

                    console.log(this.cache)
                }

                //制作连接
                for (var i = index; i < this.data.length; i++) {
                    this.data[i].id = i;
                    var link = {
                        source: id,
                        target: i
                    }
                    this.links.push(link);
                }

                this.myChart.hideLoading();

                if (isRoot) {
                    this.myChart.setOption(this.makeOption(this.data, this.links));
                } else {
                    this.myChart.setOption({series: [{data: this.data, links: this.links}]});
                }

            });
        },
        refreshList: function (id) {
            this.list_cache = this.cache[id];
        },
        viewKnowledge: function (target) {
            window.open(appPath + "/knowledge/" + target);
        }

    },
});

/*全部加载*/
function afterLoad() {

    var option = vm.chartVm.getOption();
    var data = option.series[0].data

    for (var i = 0; i < data.length; i++) {
        console.log(data[i])
        var type = data[i].type;
        if (type == 1) {
            continue
        }
        var name = data[i].name;
        var parent = data[i].parent;
        var id = data[i].id;

        vm.loadGraph(name, type, parent, false, id);

    }


}
