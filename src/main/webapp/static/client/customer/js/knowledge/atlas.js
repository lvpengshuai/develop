/**
 * Created by zhangpeng on 2017/4/11.
 */

var vm = new Vue({
    el: "#content",
    data: {
        word: "氧化",
        treeWord: "",
        myChart: {},
        myChartCopyOption: {},
        viewedCopy: [],
        dataCopy: [],
        dataCopy2: [],
        linksCopy: [],
        linksCopy2: [],
        cacheCopy: [],
        listCopy: [],
        data: [],
        links: [],
        viewed: [],
        cache: {},
        list: [],
        nameList: [],
        nameListCopy: [],
        deletedNameList: [],
        path: [],
        pathArray: [],
        styleObject: {},
        /*判断恢复点击前的图*/
        isFirst: 1,
        isSecond: 0,

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
            this.initGraph();
            this.initTree();
        },
        initGraph: function () {
            //初始化echarts实例
            vm.myChart = echarts.init(document.getElementById('graph'));
            this.loadGraph(this.word, 1, "", true, 0);
            vm.myChart.on('click', function (params) {
                vm.chartOnclickMethod(params)
            });
        },
        initTree: function () {
            //初始化tree实例
            $('#tree').data('jstree', false).empty();
            $('#tree').jstree({
                'core': {
                    'data': {
                        'dataType': 'json',
                        'url': function (node) {
                             var url = encodeURI(node.id == '#' ?
                                 appPath + '/atlas/nodes' :
                                 appPath + '/atlas/nodes?name=' + node.text)
                                return url
                        },
                        'data': function (node) {
                            return {"text": node.text};
                        }
                    },
                    "themes": {
                        dots: false  //是否显示虚线点
                    }
                },
                "plugins": ["types", "themes", "search"],
                "types": {
                    "default": {
                        "icon": "static/client/images/tree_icon.png",
                        "line": false
                    }
                },

            }).bind('select_node.jstree', function (event, data) {
                vm.loadGraph(data.node.text, 1, "", true, 0);
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
                    repulsion: 2000,
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
            /*包含缩屏和切换背景的工具*/
            var option_tool = {
                toolbox: {
                    bottom: 30,
                    show: true,
                    feature: {
                        myTool1: {
                            show: true,
                            title: '缩屏',
                            icon: 'image:///static/client/images/fullscreen-s.png',
                            onclick: function (myChart) {
                                if ($.support.fullscreen) {
                                    vm.myChart.on('click', function (params) {
                                        vm.chartOnclickMethod(params)
                                    });
                                    $('#full').fullScreen({
                                        'background': '#344'
                                    });
                                } else {
                                    alert("您所使用的浏览器不支持全屏！")
                                }
                            }

                        },
                        myTool_background: {
                            bottom: 20,
                            show: true,
                            title: '切换背景色',
                            icon: 'image:///static/client/images/kaideng.png',
                            onclick: function (myChart) {
                                var option = myChart.getOption();
                                console.log(option.toolbox[0].feature.myTool_background.icon)
                                console.log(option)
                                if ($('#full').css("background-color") == "rgba(0, 0, 0, 0)") {
                                    $('#full').css("background-color", "#ffffff");
                                    option.toolbox[0].feature.myTool_background.icon =
                                        "image:///static/client/images/guandeng.png"
                                    myChart.setOption(option)
                                    return
                                }
                                if ($('#full').css("background-color") == "rgb(0, 0, 0)") {
                                    $('#full').css("background-color", "#ffffff");
                                    option.toolbox[0].feature.myTool_background.icon =
                                        "image:///static/client/images/guandeng.png"
                                    myChart.setOption(option)
                                    return
                                }
                                if ($('#full').css("background-color") == "rgb(255, 255, 255)") {
                                    option.toolbox[0].feature.myTool_background.icon =
                                        "image:///static/client/images/kaideng.png"
                                    $('#full').css("background-color", "#000000");
                                    myChart.setOption(option)
                                }
                            }
                        },
                        restore: {show: true},
                    }
                },
                animationDurationUpdate: 100,
                animationDuration: 1500,
                animationEasingUpdate: 'quinticInOut',
            };
            var option = {
                toolbox: {
                    bottom: 20,
                    show: true,
                    feature: {
                        myTool1: {
                            show: true,
                            title: '全屏',
                            icon: 'image:///static/client/images/fullscreen.png',
                            onclick: function (myChart) {
                                vm.clickFullScreenMethod(myChart,option_tool,option)
                            }
                        },
                        restore: {show: true},
                        myTool_background: {
                            bottom: 20,
                            show: false,
                            title: '切换背景色',
                            icon: 'image:///static/client/images/kaideng.png',
                            onclick: function (myChart) {
                                var option = myChart.getOption();
                                if ($('#full').css("background-color") == "rgba(0, 0, 0, 0)") {
                                    $('#full').css("background-color", "#ffffff");
                                    option.toolbox[0].feature.myTool_background.icon =
                                        "image:///static/client/images/guandeng.png"
                                    myChart.setOption(option)
                                    return
                                }
                                if ($('#full').css("background-color") == "rgb(0, 0, 0)") {
                                    $('#full').css("background-color", "#ffffff");
                                    option.toolbox[0].feature.myTool_background.icon =
                                        "image:///static/client/images/guandeng.png"
                                    myChart.setOption(option)
                                    return
                                }
                                if ($('#full').css("background-color") == "rgb(255, 255, 255)") {
                                    option.toolbox[0].feature.myTool_background.icon =
                                        "image:///static/client/images/kaideng.png"
                                    $('#full').css("background-color", "#000000");
                                    myChart.setOption(option)
                                }
                            }
                        },
                    }
                },
                animationDurationUpdate: 100,
                animationDuration: 1500,
                animationEasingUpdate: 'quinticInOut',
                series: series
            };
            return option;
        },
        search: function () {
            this.loadGraph(this.word, 1, "", true, 0);
        },
        searchTree: function () {
            $('#tree').jstree(true).search(this.treeWord);
        },
        loadGraph: function (name, type, parent, isRoot, id) {


            $("#attribute-name").text("" + name)
            vm.deletedNameList = new Array();
            var params = {
                name: type == 0 ? parent : name,
                type: type == 0 ? name : "",
                root: isRoot ? false : true
            };


            vm.$http.post(appPath + "/atlas/word", params, {emulateJSON: true}).then(function (res) {
                var data = JSON.parse(res.bodyText);
                if (data.code != 10000) {
                    return;
                }
                var option = vm.myChart.getOption();


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
                        parent: parent,
                        symbolSize: 100,
                        label: {
                            normal: {
                                textStyle: {
                                    color: "#1e75e6",
                                    fontSize: 18,
                                    fontWeight: 'bolder'
                                }
                            }
                        },
                    }
                    this.data.push(target);
                    vm.viewed.push(name + "|" + type + parent + id);

                }
                if (this.isSecond != 1) {
                    if (id == 0) {
                        this.isSecond = 1;
                        vm.nameList.push({name: name, id: id, type: type, parent: parent});
                        this.styleObject = {
                            'font-style': 'italic',
                            'margin-right': '10px',
                        }
                    } else {
                        this.styleObject = {
                            'font-style': 'italic',
                            'margin-right': '10px',
                            // 'color':'#b2b2b2'
                        }
                    }
                }

                var graph = data.msg;

                var index = this.data.length;
                /*##################################################################################################################*/
//去重,再次加载可以自动过滤重复数据，避免出错,
                var deleteName;
                for (var i = 0; i < this.links.length; i++) {
                    if (this.links[i].source == id) {
                        var deleteId = this.links[i].target
                        deleteName = this.data[deleteId].name;
                        for (var q = 0; q < graph.nodes.length; q++) {
                            if (graph.nodes[q].name == deleteName) {
                                graph.nodes.splice(q, 1)
                            }
                        }
                    }
                }
                /*#####################################################################################################################*/

                this.data = isRoot ? this.data.concat(graph.nodes) : option.series[0].data.concat(graph.nodes);

                //缓存显示数据
                if (type == 0) {
                    this.cache[name] = graph.nodes;
                    this.$set(this.cache, id, graph.nodes);
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

                vm.myChart.hideLoading();

                if (isRoot) {
                    vm.myChart.setOption(this.makeOption(this.data, this.links));
                } else {
                    vm.myChart.setOption({series: [{data: this.data, links: this.links}]});
                }
                vm.refreshList(id);

            });
        },
        loadHistoryGraph: function (name, type, parent, isRoot, id, thisElement) {
            //使用备份数据
            //从原始数据开始计算

            if (id != 0) {
                try {
                    this.deletedNameList.push(thisElement)
                    $(thisElement).attr("style", "font-style: italic; margin-right: 10px;color:#9b9b9b")
                } catch (e) {
                }
            } else {
                for (var p = 0; p < this.deletedNameList.length; p++) {
                    $(this.deletedNameList[p]).attr("style", "font-style: italic; margin-right: 10px;")
                }
            }

            var newLinks = new Array();
            var newData = new Array();
            /*删除的id列表*/
            var deleteIds = new Array();
            /*当前图的内容*/
            var seris_data = $.extend(true, [], this.dataCopy)
            var series_links = $.extend(true, [], this.linksCopy)
            if(series_links=={}||series_links=={ }||series_links == undefined){
                setTimeout(console.log(seris_data),100)
                return
            }

            /*禁止删除第一个*/
            if (id == 0) {
                this.data = this.dataCopy
                this.links = this.linksCopy
                var option = vm.makeOption($.extend(true, [], this.dataCopy),$.extend(true, [], this.linksCopy))
                 vm.myChart.setOption(option)
                this.isFirst = 1;
                this.nameList = [];
                this.nameList = this.nameListCopy
                setTimeout("vm.formatNameList()", 700)
                //恢复原始数据，继续记录图谱数据
                return
            } else {
                this.isFirst = 0;
            }

            /*################################################################################*/
            /**
             */
            /*获取路径*/
            var target = id;
            var pathArray = new Array();
            pathArray.push(id)
            while (true) {
                if (target == 0) {
                    break;
                }
                for (var i = 0; i < series_links.length; i++) {
                    if (series_links[i].target == target) {
                        target = series_links[i].source
                        pathArray.push(target)
                        break;
                    }
                }
            }

            if (pathArray.length < 2) {
                return
            }
            /**删除所有节点方法
             * 1、判断删除所有节点逻辑，遍历节点结合，保存sources与路径id相同的节点
             * 2、加载新图
             */
            /**删除所有节点连接的方法
             * 1、获取所有新节点的连接
             *遍历所有连接，如果不属于新节点的连接，删除
             */
            /*获取最后一个节点的所有子节点，及连接*/
            for (var i = 0; i < series_links.length; i++) {
                if (id == series_links[i].source) {
                    deleteIds.push(series_links[i].target)
                    newLinks.push(series_links[i])
                }
            }

            /*合并两个数组最后所有的点和连接点*/
            for (var i = 0; i < pathArray.length; i++) {
                if (deleteIds.indexOf(pathArray[i]) == -1)
                    deleteIds.push(pathArray[i])
            }

            //TODO ，出现bug重点验证的地方，可能会过滤不完全导致剩余孤立节点，最后导致bug
            /*过滤路径节点的连接，可能会有重复的，最后导致bug*/
            for (var i = 0; i < series_links.length; i++) {
                if (pathArray.indexOf(series_links[i].source) != -1 && pathArray.indexOf(series_links[i].target) != -1) {
                    newLinks.push(series_links[i])
                }
            }

            /*过滤节点*/
            for (var i = 0; i < seris_data.length; i++) {
                if (deleteIds.indexOf(seris_data[i].id) != -1) {
                    newData.push(seris_data[i])
                }
            }
            /*################################################################################*/
            /*重排序连接id*/
            var copyLinks = newLinks.concat()
            for (var i = 0; i < newData.length; i++) {
                for (var q = 0; q < newLinks.length; q++) {
                    if (newLinks[q].target == newData[i].id) {
                        copyLinks[q].target = i;
                    }
                    if (newLinks[q].source == newData[i].id) {
                        copyLinks[q].source = i;
                    }
                }
            }
            /*重排序节点id*/
            for (var i = 0; i < newData.length; i++) {
                if (newData[i].id == id) {
                    id = i;
                }
                newData[i].id = i;
            }
            /*##########################################################################################*/
            var option1 = vm.makeOption(newData,copyLinks)
            vm.myChart.setOption(option1)
            return id;
        },
        loadHistoryGraph2: function (name, type, parent, isRoot, id, thisElement) {
            //使用备份数据
            //从原始数据开始计算

            if (id != 0) {
                try {
                    this.deletedNameList.push(thisElement)
                    $(thisElement).attr("style", "font-style: italic; margin-right: 10px;color:#9b9b9b")
                } catch (e) {
                    console.log(e)
                }
            } else {
                for (var p = 0; p < this.deletedNameList.length; p++) {
                    $(this.deletedNameList[p]).attr("style", "font-style: italic; margin-right: 10px;")
                }
            }

            var newLinks = new Array();
            var newData = new Array();
            /*删除的id列表*/
            var deleteIds = new Array();
            /*当前图的内容*/
            var seris_data = $.extend(true, [], this.dataCopy2)
            var series_links = $.extend(true, [], this.linksCopy2)


            /*禁止删除第一个*/
            if (id == 0) {
                var option = vm.myChart.getOption()
                option.series[0].data = $.extend(true, [], this.dataCopy2)
                option.series[0].links = $.extend(true, [], this.linksCopy2)
                vm.myChart.setOption(option)
                this.isFirst = 1;
                //恢复原始数据，继续记录图谱数据
                return
            } else {
                this.isFirst = 0;
            }

            /*################################################################################*/
            /**
             */
            /*获取路径*/
            var target = id;
            var pathArray = new Array();
            pathArray.push(id)
            while (true) {
                if (target == 0) {
                    break;
                }
                for (var i = 0; i < series_links.length; i++) {
                    if (series_links[i].target == target) {
                        target = series_links[i].source
                        pathArray.push(target)
                        break;
                    }
                }
            }

            if (pathArray.length < 2) {
                return
            }
            /**删除所有节点方法
             * 1、判断删除所有节点逻辑，遍历节点结合，保存sources与路径id相同的节点
             * 2、加载新图
             */
            /**删除所有节点连接的方法
             * 1、获取所有新节点的连接
             *遍历所有连接，如果不属于新节点的连接，删除
             */
            /*获取最后一个节点的所有子节点，及连接*/
            for (var i = 0; i < series_links.length; i++) {
                if (id == series_links[i].source) {
                    deleteIds.push(series_links[i].target)
                    newLinks.push(series_links[i])
                }
            }


            /*合并两个数组最后所有的点和连接点*/
            for (var i = 0; i < pathArray.length; i++) {
                if (deleteIds.indexOf(pathArray[i]) == -1)
                    deleteIds.push(pathArray[i])
            }

            //TODO ，出现bug重点验证的地方，可能会过滤不完全导致剩余孤立节点，最后导致bug
            /*过滤路径节点的连接，可能会有重复的，最后导致bug*/
            for (var i = 0; i < series_links.length; i++) {
                if (pathArray.indexOf(series_links[i].source) != -1 && pathArray.indexOf(series_links[i].target) != -1) {
                    newLinks.push(series_links[i])
                }
            }

            /*过滤节点*/
            for (var i = 0; i < seris_data.length; i++) {
                if (deleteIds.indexOf(seris_data[i].id) != -1) {
                    newData.push(seris_data[i])
                }
            }
            /*################################################################################*/
            /*重排序连接id*/
            var copyLinks = newLinks.concat()
            for (var i = 0; i < newData.length; i++) {
                for (var q = 0; q < newLinks.length; q++) {
                    if (newLinks[q].target == newData[i].id) {
                        copyLinks[q].target = i;
                    }
                    if (newLinks[q].source == newData[i].id) {
                        copyLinks[q].source = i;
                    }
                }
            }
            /*重排序节点id*/
            for (var i = 0; i < newData.length; i++) {
                if (newData[i].id == id) {
                    id = i;
                }
                newData[i].id = i;
            }
            /*##########################################################################################*/
            var option1 = vm.makeOption(newData,copyLinks)
            vm.myChart.setOption(option1)
            return id;
        },
        formatNameList: function () {
            if (this.isFirst == 1) {
                /*当前图的内容*/
                var elements = $(".name-list")
                /*获取点击的id列表*/
                for (var q = 0; q < elements.length; q++) {
                    var nodeName = $(elements[q]).children("a").text()
                    var id = $(elements[q]).children("span").text()
                    var type = $(elements[q]).children("p").text()
                    var parent = $(elements[q]).children("h2").text()

                    if (type == 0) {
                        $(elements[q]).children("a").attr("class", "nameList1")
                    }
                    $(elements[q]).children("a").attr("onclick", "vm.loadHistoryGraph(" + "'" + nodeName + "'" + ", " + type + "," + "'" + parent + "'" + ", true, " + id + ",this)")
                    $(elements[q]).children("a").attr("id", nodeName)
                    $(elements[q]).children("a").attr("historyId", id)
                }
            } else {
                /*当前图的内容*/
                var elements = $(".name-list")
                /*获取点击的id列表*/
                for (var q = 0; q < elements.length; q++) {
                    var nodeName = $(elements[q]).children("a").text()
                    var id = $(elements[q]).children("span").text()
                    var type = $(elements[q]).children("p").text()
                    var parent = $(elements[q]).children("h2").text()

                    if (id == 0) {
                        continue
                    }
                    if (type == 0) {
                        $(elements[q]).children("a").attr("class", "nameList1")
                    }
                    $(elements[q]).children("a").attr("onclick", "vm.loadHistoryGraph2(" + "'" + nodeName + "'" + ", " + type + "," + "'" + parent + "'" + ", true, " + id + ",this)")
                    $(elements[q]).children("a").attr("id", nodeName)
                    $(elements[q]).children("a").attr("historyId", id)
                }

            }

        },
        showNameList: function (id) {


            /*当前图的内容*/
            var option = vm.myChart.getOption()
            var seris_data = option.series[0].data
            var series_links = option.series[0].links

            /*获取点击的id列表*/


            /*################################################################################*/
            /**
             * 1、通过获取的路径删除其他节点所有点跟所有连接
             * 2、nameList根据路径节点重新加载
             * 3、旧nameList中的置灰色
             */
            var target = id;
            var pathArray = new Array();
            pathArray.push(id)
            while (true) {
                if (target == 0) {
                    break;
                }
                for (var i = 0; i < series_links.length; i++) {
                    if (series_links[i].target == target) {
                        target = series_links[i].source
                        pathArray.push(target)
                        break;
                    }
                }
            }
            this.nameList = [];
            for (var i = 0; i < seris_data.length; i++) {
                if (pathArray.indexOf(seris_data[i].id) != -1) {
                    var name = seris_data[i].name
                    var id = seris_data[i].id
                    var type = seris_data[i].type
                    var parent = seris_data[i].parent
                    /*存入nameList中的对象*/
                    var index = vm.nameList.indexOf({name: name, id: id, type: type, parent: parent});
                    if (index == -1) {
                        vm.nameList.push({name: name, id: id, type: type, parent: parent});
                        this.styleObject = {
                            'font-style': 'italic',
                            'margin-right': '10px',
                        }
                    } else {
                        this.styleObject = {
                            'font-style': 'italic',
                            'margin-right': '10px',
                            // 'color':'#b2b2b2'
                        }
                    }
                }
            }
            this.pathArray = pathArray
            /*################################################################################*/
            return true;
        },
        refreshList: function (id) {
            this.list = this.cache[id];
        },
        chartOnclickMethod: function (params) {
            //获取数据
            var type = params.data.type;
            var name = params.data.name;
            var parent = params.data.parent;
            var id = params.data.id;
            if (name == undefined && type == undefined) {
                return
            }
            /**
             * 如果是路径数据，恢复元数据。反之继续加载
             */
            if (this.isFirst == 1) {

                if (vm.viewed.indexOf(name + "|" + type + parent + id) != -1) {
                    vm.refreshList(id);
                    return;
                }
                vm.viewed.push(name + "|" + type + parent + id);
                vm.loadGraph(name, type, parent, false, id);
                /*复制数据*/
                console.log("this.dataCopy")
                console.log(this.dataCopy)
                /*复制数据结束*/
            } else {

                /*图上上l路径数据，从原始数据开始计算*/
                var option = vm.myChart.getOption()
                this.data = option.series[0].data
                this.links = option.series[0].links
                vm.myChart.setOption(option)
                setTimeout(vm.loadGraph(name, type, parent, false, id), 100);
                setTimeout(vm.refreshList(id), 300)

                //结束
            }


                if(vm.showNameList(id)){
                    setTimeout("vm.formatNameList()", 200)
                }else {
                    setTimeout("vm.formatNameList()", 300)
                }
                setTimeout("  vm.copyMyChart()", 300)

                 vm.refreshList(id);

        },
        copyMyChart: function () {
            /*复制数据*/
            var option = vm.myChart.getOption();
            if (this.isFirst != 1) {
                $.extend(true, this.linksCopy2, option.series[0].links)
                $.extend(true, this.dataCopy2, option.series[0].data)
                return
            }
            vm.myChartCopyOption = []
            this.viewedCopy = []
            this.dataCopy = []
            this.linksCopy = []
           /* vm.myChartCopyOption = $.extend(true, vm.myChartCopyOption, option)
            $.extend(true, this.viewedCopy, this.viewed)
            $.extend(true, this.dataCopy, option.series[0].data)
            $.extend(true, this.linksCopy, option.series[0].links)
            $.extend(true, this.listCopy, this.list)
            $.extend(true, this.nameListCopy, this.nameList)*/
            vm.myChartCopyOption = $.extend(true, {}, option)
            this.viewedCopy = $.extend(true, [], this.viewed)
            this.dataCopy = $.extend(true, [], option.series[0].data)
            this.linksCopy = $.extend(true, [], option.series[0].links)
            this.nameListCopy = $.extend(true,[], this.nameList)

        },
        clickFullScreenMethod:function(myChart,option_tool,option){
            var width = $(window).width()-50; //浏览器当前窗口可视区域宽度
            var height = $(window).height();
            if ($.support.fullscreen) {
                $("#click-path").css("position", "absolute")
                $("#click-path").css("background-color", "#c2e8f2")
                var newOption = jQuery.extend(true, {}, myChart.getOption());
                newOption.toolbox[0].feature = option_tool.toolbox.feature
                $("#graph").attr("style", "width: "+width+"px;height:"+ height+"px");
                vm.myChart = echarts.init(document.getElementById('graph'));
                vm.myChart.setOption(newOption);
                vm.myChart.on('click', function (params) {
                    vm.chartOnclickMethod(params)
                });
                $('#full').fullScreen({
                    'background': '#000000',
                    'callback': function (isFullScreen) {
                        var oldOption = {};
                        $.extend(true, oldOption, vm.myChart.getOption());
                        if (!isFullScreen) {
                            $("#click-path").css("position", "")
                            $("#click-path").css("background-color", "")
                            $('#full').css("background-color", "#ffffff");
                            $("#graph").attr("style", "width: 960px;height: 500px;");
                            vm.myChart = echarts.init(document.getElementById('graph'));
                            oldOption.toolbox[0].feature = option.toolbox.feature
                            vm.myChart.setOption(oldOption);
                            vm.myChart.on('click', function (params) {
                                vm.chartOnclickMethod(params)
                            });
                        }
                    }
                });
            } else {
                alert("您所使用的浏览器不支持全屏！")
            }
        }
    }
});
