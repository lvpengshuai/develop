/**
 * ztree插件初始化js
 * Created by xuxuecheng on 2017/9/15.
 */
//初始化插件
var setting = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false,
        showLine: false
    },
    edit: {
        enable: true,
        editNameSelectAll: true,
        showRemoveBtn: showRemoveBtn,
        showRenameBtn: showRenameBtn
    },
    data: {
        keep: {
            parent: false,
            leaf: true
        },
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeEditName: beforeEditName,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        beforeClick: beforeClick,
        onRemove: onRemove,
        onRename: onRename,
        onClick: onClick,
    }
};


var log, className = "dark";
/*function addUploadUrl() {
        var str2  ="";
        str2 = "<label class='sr-only' for='inputfile'>文件输入</label><input type='file' id='inputfile' name='files'>";
        $(".col-md-right div form input:last").after(str2);
        push();
}*/
function beforeClick(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    showLog("[ " + getTime() + " beforeClick ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    $.ajax({
        url: "/node/urlList",
        type: "get",
        data: {
            nodeId: treeNode.id
        },
        success: function (data) {
            var str = "";
            str += "<tr><td>节点ID</td>&emsp;&emsp;<td style='text-align: center'>节点资源</td></tr>"
            for (var i = 0; i < data.length; i++) {
                str += "<tr><td>" + (i + 1) + "</td><td style='text-align: center'>" + data[i].node_Url + "</td></tr>";
            }
            $(".col-md-right div table").html(str);
            $("#nodeId").val(treeNode.id);

        },
        error: function (data) {
            alert("失败");
        }
    });
}
function onClick(treeId, treeNode) {
    showLog("[ " + getTime() + " onClick ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    return true;
}
//修改名字之前操作
function beforeEditName(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    return true;
}
//删除节点之前操作，异步查看数据库是否存在子节点
function beforeRemove(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    var flag = false;
    $.ajax({
        url: "/admin/queryNode",
        data: {fid: treeNode.id},
        success: function (data) {
            if (data.code == 1) {
                flag = confirm("节点[ " + treeNode.name + " ]下还有子节点,确定要删除吗？");
                alert("查询数据库是否存在子节点");
            } else {
                flag = confirm("确定要删除[ " + treeNode.name + " ]节点吗？");
            }
            if (flag == true) {
                $.ajax({
                    url: "/admin/delNode",
                    type: "get",
                    data: {id: treeNode.id},
                    success: function (data) {
                        alert("删除成功");
                        window.location.href = '/admin/node';
                    },
                    error: function () {
                        alert("删除失败");
                    }
                });
            } else {
                flag = false;
            }
        },
        error: function () {
            alert("error");
        }
    });
    return flag;
}
function onRemove(e, treeId, treeNode, flag) {
    showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}
//修改名称时判断名称是否为空并异步修改
function beforeRename(treeId, treeNode, newName) {
    className = (className === "dark" ? "" : "dark");
    showLog("[ " + getTime() + " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    if (newName.length == 0) {
        alert("节点名称不能为空.");
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        setTimeout(function () {
            zTree.editName(treeNode)
        }, 10);
        return false;
    }
    $.ajax({
        url: "/admin/updateNode",
        type: "get",
        data: {
            id: treeNode.id,
            name: newName
        },
        success: function (data) {
            alert("修改成功");
            window.location.href = '/admin/node';
        },
        error: function () {
            alert("修改失败");
        }
    });
    return true;
}
//修改成功后日志
function onRename(e, treeId, treeNode) {
    showLog("[ " + getTime() + " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}
//是否展示删除图标
function showRemoveBtn(treeId, treeNode) {
    return true;
}
//是否展示修改图标
function showRenameBtn(treeId, treeNode) {
    return true;
}
//展示日志
function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='" + className + "'>" + str + "</li>");
    if (log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}
function getTime() {
    var now = new Date(),
        h = now.getHours(),
        m = now.getMinutes(),
        s = now.getSeconds(),
        ms = now.getMilliseconds();
    return (h + ":" + m + ":" + s + " " + ms);
}

//增加节点
var newCount = 1;
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.id
        + "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_" + treeNode.id);
    if (btn) btn.bind("click", function () {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        alert()
        zTree.addNodes(treeNode, {id: (100 + newCount), pId: treeNode.id, name: "new node"});
        $.ajax({
            url: "/admin/getNodeById",
            data: {id: treeNode.id},
            success: function (data) {
                if (data.code == 1) {
                    alert("叶子节点被锁定，无法增加子节点");
                } else {
                    zTree.addNodes(treeNode, {id: (100 + newCount), pId: treeNode.id, name: "new node"});
                    $.ajax({
                        url: "/admin/addNode",
                        data: {
                            fid: treeNode.id,
                            name: "new node"
                        },
                        success: function (data) {
                            if (data.code == 1) {
                                alert("增加成功");
                                //window.location.href = '/admin/node';
                            } else {
                                alert("增加失败");
                            }

                        },
                        error: function () {
                            alert("增加错误");
                        }
                    });
                }
            }
        });
        return true;
    });
};
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.id).unbind().remove();
};
function selectAll() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}

$(document).ready(function () {
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    $("#selectAll").bind("click", selectAll);
});
