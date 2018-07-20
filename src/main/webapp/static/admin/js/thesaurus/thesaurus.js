/**
 * Created by zhangpeng on 2017/4/11.
 */

var vm = new Vue({
    el: "#content",
    data: {
        node: {
            text: "化工工艺"
        },
        treeWord: ""
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
            vm.getInfo(vm.node)
        },
        /**
         * 获取主题词关联
         * @param node
         */
        getInfo: function (node) {
            $("#clickWord").text(node.text)
            $("#molecular-formula-nodes").val("无")
            try {
                vm.getSameParentWord(node.text)
            } catch (e) {
            }
            $.ajax({
                url: '/admin/thesaurus/info',
                dataType: 'json',
                type: 'get',
                data: {'word': node.text},
                success: function (data) {
                    try {
                        //后期改bug优化把更改名称单独摘出来，因为重命名模块返回的东西不一样，会返回重命名失败的状态码
                        $("#molecular-formula-id").val(data.object.id)
                        $("#message").text("<span>" + JSON.stringify(data) + "</span>")
                        if (data.object.parent == "THINGS") {
                            $("#parent-node").val("根节点")
                        } else {
                            $("#parent-node").val(data.object.parent)
                        }
                        if (data.object.molecularFormula == "" || data.object.molecularFormula == undefined) {
                            $("#molecular-formula-nodes").text("无")
                        } else {
                            $("#molecular-formula-nodes").val(data.object.molecularFormula)
                        }
                        $("#renameWord-rename").val(data.object.word)
                        $("#renameWord").val(data.object.word)
                        $(".mainWord").val(data.object.word)
                        $("#thesaurus-nodes").val("没有数据")
                        $("#thesaurus-nodes").val(data.object.thesaurus)
                        $("#abbreviation-nodes").val("没有数据")
                        $("#abbreviation-nodes").val(data.object.abbreviation)
                        $("#foreignword-nodes").val("没有数据")
                        $("#foreignword-nodes").val(data.object.foreignword)
                        $("#aboutword-nodes").val("没有数据")
                        $("#aboutword-nodes").val(data.object.aboutword)
                        $("#aboutphrase-nodes").val("没有数据")
                        $("#aboutphrase-nodes").val(data.object.aboutphrase)
                        $("#similarphrase-nodes").val("没有数据")
                        $("#similarphrase-nodes").val(data.object.similarphrase)
                        $("#word-creator").text(data.object.creator)
                        $("#word-last-editor").text(data.object.editor)
                        $("#word-updateTime").text(data.object.updateDate)
                        formatChildes(data)
                        formatCoreWords(data)
                        $("#total-word").text(data.total)
                    } catch (e) {
                    }
                }

            })
            vm.getSameParentWord(node.text)
        },
        coreWordAdd: function () {
            $("#core-words-nodes-scroll").animate({scrollTop: $('#core-words-nodes-scroll')[0].scrollHeight}, 1000);
            var word = $("#clickWord").text()
            $("#core-words-nodes").append("<form action = '/admin/thesaurus/edit'method='GET'>" +
                "<input class='word-type' type='text' name='type' value='core_word'>&nbsp;" +
                "<input class='id' type='text'name='word' value=" + word + ">&nbsp;" +
                "<input class='id' type='text'name='id' value=''>&nbsp;" +
                "属性：<input class='core_word_attribute' type='text' name='attribute' value=''>&nbsp;" +
                "核心词：<input class='core_word' type='text' name='coreWord' value=''>&nbsp;" +
                "<input onclick='vm.editWord(this)' type='button' class='button' value='提交' /></form>")
        },
        editWord: function (thisForm) {
            var type = $(thisForm.form).attr("class")
            jQuery.ajax({
                url: '/admin/thesaurus/edit',
                data: $(thisForm.form).serialize(),
                type: "GET",
                beforeSend: function () {

                },
                success: function (data) {
                    if (type == "rename" || type == "parent" || type == "childes") {
                        var tree = jQuery.jstree.reference("#tree");
                        tree.refresh();
                        if (data.code == 20000) {
                            alert("更改失败，名称重复");
                        }
                        return;
                    }
                    if (data.message == 0) {
                        alert("请使用英文逗号分词");
                    }
                    var word = $("#clickWord").text()
                    vm.getInfo({text: word});
                }
            });
        },
        deleteWord: function (thisForm) {
            console.log(thisForm.form)
            jQuery.ajax({
                url: '/admin/thesaurus/delete',
                data: $(thisForm.form).serialize(),
                type: "POST",
                beforeSend: function () {

                },
                success: function () {
                    var word = $("#clickWord").text()
                    vm.getInfo({text: word});
                }
            });
        },
        addWord: function (thisForm) {
            $('#thesaurus-add-modal').modal({backdrop: 'static', keyboard: false}).modal('hide');
            jQuery.ajax({
                url: '/admin/thesaurus/add',
                data: $(thisForm.form).serialize(),
                type: "GET",
                beforeSend: function () {

                },
                success: function (data) {
                    var tree = jQuery.jstree.reference("#tree");
                    tree.refresh();
                    if (data.status == 0) {
                        alert("名称重复")
                    }
                }
            });
        },
        getSameParentWord: function (word) {
            $.ajax({
                url: '/admin/thesaurus/same',
                dataType: 'json',
                type: 'get',
                data: {'word': word},
                success: function (data) {
                    try {
                        var words = data.message;
                        $("#sameParentWord-nodes").val(words.toString().replace("[", "").replace("]", ""))
                    } catch (e) {
                    }
                }

            })
        },
        tabChange: function (text, id) {
            $("#" + id).text(text)
        },
        synCkm: function () {
            $('#sys_ckm_model').modal({backdrop: 'static', keyboard: false}).modal('hide');
            $('#syn-ckm-status').modal({backdrop: 'static', keyboard: false}).modal('show');
            var closeCkmProgress = setInterval(getCkmProgress, 2000);
            $("#progress_upload_info").attr("closeCkmProgress", closeCkmProgress);
            var url = encodeURI('/admin/thesaurus/ckm')
            $.ajax({
                url: url,// 跳转到 action
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    $("#ckm-result-info").text("任务成功执行完毕！")
                    $('#syn-ckm-status').modal({backdrop: 'static', keyboard: false}).modal('show');
                },
                error: function (e) {
                    console.log(e)
                    alert("异常！");
                }
            });

        },
        downloadThesaurus: function (state) {
            $('#export_thesaurus_model').modal({backdrop: 'static', keyboard: false}).modal('hide');
            if (state == 1) {
                var type = "xml";
                var form = $("<form>");//定义一个form表单
                form.attr("style", "display:none");
                form.attr("target", "");
                form.attr("method", "get");
                form.attr("action", "/admin/thesaurus/download?word=THINGS&state=1");
                var input1 = $("<input>");
                input1.attr("type", "hidden");
                input1.attr("name", "type");
                input1.attr("value", type);
                var input2 = $("<input>");
                input2.attr("type", "hidden");
                input2.attr("name", "word");
                input2.attr("value", "THINGS");
                $("body").append(form);//将表单放置在web中
                form.append(input1);
                form.append(input2)
                form.submit();
                //全部导出
                if (type != "excel") {
                    var closeCkmProgress = setInterval(getXmlProgress, 2000);
                    $("#progress_upload_info").attr("closeDownloadProgress", closeCkmProgress);
                }
            } else if (state == 3) {
                var type = "excel";
                var form = $("<form>");//定义一个form表单
                form.attr("style", "display:none");
                form.attr("target", "");
                form.attr("method", "get");
                form.attr("action", "/admin/thesaurus/download?word=THINGS&state=1");
                var input1 = $("<input>");
                input1.attr("type", "hidden");
                input1.attr("name", "type");
                input1.attr("value", type);
                var input2 = $("<input>");
                input2.attr("type", "hidden");
                input2.attr("name", "word");
                input2.attr("value", "THINGS");
                $("body").append(form);//将表单放置在web中
                form.append(input1);
                form.append(input2)
                form.submit();
                //导出选中
                if (type != "excel") {
                    var closeCkmProgress = setInterval(getXmlProgress, 2000);
                    $("#progress_upload_info").attr("closeDownloadProgress", closeCkmProgress);
                }
            } else {
                $('#export_thesaurus_status').modal({backdrop: 'static', keyboard: false}).modal('hide');
                var type = $("#expert1").text();
                var word = $(".mainWord").val()
                if (word == "null" || word == undefined) {
                    alert("您未选中节点");
                    return
                }
                var form = $("<form>");//定义一个form表单
                form.attr("style", "display:none");
                form.attr("target", "");
                form.attr("method", "get");
                form.attr("action", "/admin/thesaurus/download?word=THINGS&state=1");
                var input1 = $("<input>");
                input1.attr("type", "hidden");
                input1.attr("name", "type");
                input1.attr("value", type);
                var input2 = $("<input>");
                input1.attr("type", "hidden");
                input1.attr("name", "word");
                input1.attr("value", word);
                $("body").append(form);//将表单放置在web中
                form.append(input1);
                form.submit();
            }
        }
    }
});
//初始化tree实例
$('#tree').data('jstree', false).empty();
$('#tree').jstree({
    'core': {
        "check_callback": true,
        'data': {
            'dataType': 'json',
            'url': function (node) {
                var url = encodeURI(node.id === '#' ?
                    appPath + '/admin/thesaurus/tree' :
                    appPath + '/admin/thesaurus/tree?word=' + node.text)
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
    "plugins": ["contextmenu", "html_data", "themes", "json_data", "ui", "crrm", "cookies", "dnd", "search", "types",
    ],
    "contextmenu": {
        "items": contextMenu(),
        "select_node": true
    },
    "types": {
        "default": {
            "icon": "/static/admin/images/word-ico.png",

        },
    },

})
function contextMenu() {
    var temp = {
        "delete": {
            "label": "删除",
            "icon": " file",
            "separator_after": true,
            "action": function (data) {
                $("#treeLayout").layout('expand', 'south');
                var inst = $.jstree.reference(data.reference),
                    obj = inst.get_node(data.reference);
            }
        }
    }
    return null
}
/*jstree事件*/
var tree = $("#tree");
//选中获取信息
tree.bind('select_node.jstree', function (event, data) {
    vm.getInfo(data.node);
})

/*jstree事件结束*/


/*搜索开始*/
var to = false;
$('.search-tree-input').keydown(function () {
    if (to) {
        clearTimeout(to);
    }
    to = setTimeout(function () {
        var v = $('.search-tree-input').val();
        jQuery.ajax({
            url: '/admin/thesaurus/search',
            data: {"str": v},
            type: "GET",
            success: function (result) {
                $.extend(true, vm.node, result)
                if (result.length == undefined || result.length < 2) {
                    $("#search-notice").show()
                    return
                }
                $("#search-notice").hide()
                getNodes(result)

            }
        });

    }, 250);
});
$('.search-tree-input').hover(function () {
    var v = $('.search-tree-input').val();
    $("#search-notice").hide()

});
function getNodes(result) {
    var nodes = $('#tree').jstree(true)._model.data
    //直到全部加载完成，停止
    for (var key in nodes) {
        if (result.indexOf(nodes[key].text) != -1) {
            $('#tree').jstree(true).load_node(nodes[key].id, function (data) {
                result.splice(0, 1)
                console.log("迭代后结果")
                console.log(result)
                if (result.length < 2) {
                    $(function () {
                        var to = false;
                        if (to) {
                            clearTimeout(to);
                        }
                        to = setTimeout(function () {
                            $('#tree').jstree(true).search(result[0]);
                        }, 250);

                    });
                    return;
                }
                getNodes(result)
            })
        }
    }
}
/*搜索结束*/

/**
 * 删除
 * @param node
 */
$('#checkedTreeId').on('click', function (event, obj) {
    $("#selectedNodeName").empty()
    var node = $("#renameWord-rename").val()
    $("#selectedNodeName").append("<span>" + node + "</span>&nbsp;")
    $('#checkedTreeId-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
});
function deleteSelectedNodes(event, obj) {

    $('#checkedTreeId-modal').modal({backdrop: 'static', keyboard: false}).modal('hide');
    var node = $("#renameWord-rename").val()
    var nameArray = new Array();
    nameArray.push(node)

    //传入后台删除
    jQuery.ajax({
        url: '/admin/thesaurus/delete',
        data: {"type": "class_delete", "data": nameArray},
        type: "POST",
        beforeSend: function () {

        },
        success: function () {
            var tree = jQuery.jstree.reference("#tree");
            tree.refresh();
            vm.getInfo(vm.node)
        }
    });
}
/*批量删除结束*/

/**
 * 格式化下位词
 * @param data
 */
function formatChildes(data) {
    try {
        $("#child-nodes").empty()
        var childesArray = data.childes
        $("#child-nodes").val(childesArray)
    } catch (e) {
    }
}
/**
 * 格式化核心词
 * @param data
 */
function formatCoreWords(data) {
    try {
        $("#core-words-nodes").empty()
        var coreWordsMap = data.core_words
        var i = 0;
        for (var key in coreWordsMap) {
            i++;
            $("#core-words-nodes").append("<li></li><form action = '/admin/thesaurus/edit'method='GET'>" +
                "<span>" + i + "</span>" +
                "<input class='word-type' type='text' name='type' value='core_word'>&nbsp;" +
                "<input class='id' type='text'name='word' value=" + coreWordsMap[key].word + ">&nbsp;" +
                "<input class='id' type='text'name='id' value=" + coreWordsMap[key].id + ">&nbsp;" + "属性:" +
                "<input class='core_word_attribute' type='text' name='attribute' value=" + coreWordsMap[key].attribute + ">&nbsp;" +
                "核心词：<input class='core_word' type='text' name='coreWord' value=" + coreWordsMap[key].coreWord + ">&nbsp;" +
                "<input onclick='vm.deleteWord(this)' type='button' class='button' value='删除' />&nbsp;" +
                "<input onclick='vm.editWord(this)' type='button' class='button' value='更改' /></form></li><br>")

        }
    } catch (e) {
    }
}


/**
 * 更改层级关系
 */
$("#tree")
    .on('move_node.jstree', function (e, data) {
        var object = data.parent;
        var oldObject = data.old_parent;

        var parent = data.new_instance._model.data[object].text
        var oldParent = data.old_instance._model.data[oldObject].text
        if (oldParent == "" || oldParent == "null" || oldParent == undefined) {
            oldParent = "THINGS"
        }
        var id = data.new_instance._model.data[object].id

        $.ajax({
            url: '/admin/thesaurus/location/change',
            dataType: 'json',
            type: 'get',
            data: {'word': data.node.text, "parent": parent, "old_parent": oldParent},
            success: function (result) {
                console.log(result)
            }
        })
    })

/**
 * 导入合并
 */
$('#import').on('click', function (event, obj) {
    //传入后台删除
    jQuery.ajax({
        url: '/admin/thesaurus/generate',
        type: "GET",
        beforeSend: function () {
            layer.open({
                type: 1 //Page层类型
                ,
                area: ['543px', '293px']
                ,
                title: '提示'
                ,
                shade: 0.4 //遮罩透明度
                ,
                anim: -1 //0-6的动画形式，-1不开启
                ,
                content: '<div style="padding:50px; text-align:center;">导入进度： &nbsp;<span id="progress-num"></span><br><br><p id="progress-message" class="update-sucess">请稍后</p></div>'
            });

            closeIntervalId = setInterval(getProgress, 2000);
            $("#progress_upload_info").attr("closeIntervalId", closeIntervalId);
        },
        success: function (result) {
            if (result.code == 10000) {
                layer.closeAll();
                layer.open({
                    type: 1 //Page层类型
                    ,
                    area: ['543px', '293px']
                    ,
                    title: '提示'
                    ,
                    shade: 0.4 //遮罩透明度
                    ,
                    anim: -1 //0-6的动画形式，-1不开启
                    ,
                    content: '<div style="padding:50px; text-align:center;"><p><img src="/static/client/images/update-sucess.png"/></p><p class="update-sucess">任务执行成功！</p></div>'
                });
            } else {
                layer.closeAll();
                layer.open({
                    type: 1 //Page层类型
                    ,
                    area: ['543px', '293px']
                    ,
                    title: '提示'
                    ,
                    shade: 0.4 //遮罩透明度
                    ,
                    anim: -1 //0-6的动画形式，-1不开启
                    ,
                    content: '<div style="padding:50px; text-align:center;"><p class="update-sucess">' + result.message + '</p></div>'
                });
            }
        }
    });
});
/**
 * 增加主题词,弹出mosal框
 * @returns {boolean}
 */
function openAdd(event, obj) {

    //传入后台删除
    $('#thesaurus-add-modal').modal({backdrop: 'static', keyboard: false}).modal('show');

}
/**
 * 打开同步功能
 * @param event
 * @param obj
 */
function openSynCkm(event, obj) {

    //传入后台删除
    $('#sys_ckm_model').modal({backdrop: 'static', keyboard: false}).modal('show');

}
function openDownload() {
    $('#export_thesaurus_model').modal({backdrop: 'static', keyboard: false}).modal('show');
}
function getProgress() {

    var status = false;
    $.ajax({
        url: appPath + "/admin/thesaurus/generate/progress",
        dataType: 'json',
        type: 'GET',
        cache: false,
        success: function (result) {
            if (result.status != undefined && result.status == 1) {
                var closeIntervalId = $("#progress_upload_info").attr("closeIntervalId");
                window.clearInterval(closeIntervalId);
            }
            if (result.progress == "null" || result.progress == null) {
                $("#progress-num").text("0%")
            } else {
                $("#progress-num").text(result.progress)
            }
            $("#progress-message").text(result.message)
        }

    })
    return status;
}
function getCkmProgress() {
    $.ajax({
        url: appPath + "/admin/thesaurus/generate/progress",
        dataType: 'json',
        type: 'get',
        cache: false,
        success: function (result) {
            if (result.code == 20000) {
                var closeIntervalId = $("#progress_upload_info").attr("closeCkmProgress");
                window.clearInterval(closeIntervalId);
            }
            if (result.ckm_progress == null) {
                result.ckm_progress = ""
            }
            $("#ckm-result-info").empty();
            $("#ckm-result-info").append("<span>请稍后，任务进度:" + result.ckm_progress + "</span>");
            if (result.ckm_progress != null && result.ckm_progress.toString().indexOf("100") != -1) {
                $("#ckm-result-info").text("任务执行完毕！");
                var closeIntervalId = $("#progress_upload_info").attr("closeCkmProgress");
                window.clearInterval(closeIntervalId);
            }
        }

    })
}
function getXmlProgress() {
    var status = false;
    $.ajax({
        url: appPath + "/admin/thesaurus/generate/progress",
        dataType: 'json',
        type: 'GET',
        cache: false,
        success: function (result) {
            $('#export_thesaurus_status').modal({backdrop: 'static', keyboard: false}).modal('show');
            if (result.code == 20000) {
                $("#download-result-info").text("下载失败！");
            }
            if (result.status != undefined && result.status == 1) {
            }
            if (result.xml_progress == "null") {
                result.xml_progress = "0%"
            }
            $("#download-result-info").text("请稍后，已处理：" + result.xml_progress);
            if ($.trim(result.xml_progress) == $.trim("100%")) {
                var closeIntervalId = $("#progress_upload_info").attr("closeDownloadProgress");
                $('#export_thesaurus_status').modal({backdrop: 'static', keyboard: false}).modal('hide');
                window.clearInterval(closeIntervalId);
            }
        }

    })
    return status;
}