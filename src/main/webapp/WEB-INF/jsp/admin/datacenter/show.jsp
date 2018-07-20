<%--
  Created by IntelliJ IDEA.
  User: xuxuecheng
  Date: 2017/9/13
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<link rel="stylesheet" href="${ctx}/static/admin/css/ztree/zTreeStyle.css" type="text/css">

<script type="text/javascript" src="${ctx}/static/admin/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/ztree/jquery.ztree.exedit-3.5.js"></script>
<%--<script type="text/javascript" src="${ctx}/static/admin/js/ztree/ztree.js"></script>--%>

<%--上传插件--%>
<link href="${ctx}/static/admin/css/ztree/fileinput.min.css" media="all" rel="stylesheet" type="text/css"/>
<link href="${ctx}/static/admin/css/ztree/themes/explorer/theme.min.css" media="all" rel="stylesheet" type="text/css"/>
<script src="${ctx}/static/admin/js/ztree/plugins/sortable.min.js" type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ztree/fileinput.min.js" type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ztree/locales/zh.js" type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ztree/themes/explorer/theme.min.js" type="text/javascript"></script>


<div>
    <div class="zTreeDemoBackground left col-md-3" style="float: left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
    <div class="col-md-right col-md-6">
        <form enctype="multipart/form-data" action="${ctx}/node/upload" method="post">
            <input type="hidden" value="" id="nodeId" name="nodeId">
            <input type="hidden" value="" id="tid" name="tid">
            <label for="input-43">Select File</label>
            <div class="file-loading">
                <input id="input-43" name="input43[]" type="file" multiple>
            </div>
            <div id="errorBlock" class="help-block"></div>
        </form>
        <table class="table table-hover table-striped" id="table">
        </table>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
    <div class="modal-dialog" role="document" style="margin-top: 300px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">提示</h4>
            </div>
            <div class="modal-body">
                <img src="${ctx}/static/admin/images/loging.gif">文件上传中...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<script>
    //接受json数据
    var zNodes = ${json};
    var nodeIdNew;
    var tid;
    var nodeFileId = 1;
    $(function () {
        $("#input-43").fileinput({
            showPreview: false,
            maxFileCount: 10,
            language: 'zh',
            allowedFileExtensions: ["xlsx", "pdf", "xls"],
//            uploadUrl: "/node/upload",
//            uploadAsync: true,
            elErrorContainer: "#errorBlock",
        });
        $(".fileinput-upload-button").click(function () {
            $('#myModal').modal('show')
        })
    })

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
            showLine: false,
            dblClickExpand: dblClickExpand,
        },
        edit: {
            enable: true,
            editNameSelectAll: true,
            showRemoveBtn: showRemoveBtn,
            showRenameBtn: showRenameBtn
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeDrag: beforeDrag,
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

    function beforeDrag(treeId, treeNodes) {
        return false;
    }

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
    }

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
                str += "<tr><td>节点ID</td>&emsp;&emsp;<td style='text-align: center'>节点资源</td><td>删除</td></tr>"
                for (var i = 0; i < data.length; i++) {
                    str += "<tr><td>" + (i + 1) + "</td><td style='text-align: center'>" + data[i].name + "</td><td><button class='btn btn-danger' onclick='delNodeUrl(\"" + data[i].id + "\")'>删除</button></td></tr>";
                }
                $(".col-md-right table").html(str);
                nodeIdNew = $("#nodeId").val(treeNode.id);
                tid = $("#tid").val(treeNode.parentTId);
            },
            error: function (data) {
                alert("失败");
            }
        });
    }

    //修改名字之前操作
    function beforeEditName(treeId, treeNode) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.selectNode(treeNode);
        setTimeout(function () {
            setTimeout(function () {
                zTree.editName(treeNode);
            }, 0);
        }, 0);
        return false;
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
            async: false,
            success: function (data) {
                if (data.code == 1) {
                    flag = confirm("节点[ " + treeNode.name + " ]下还有子节点,确定要删除吗？");
                } else {
                    flag = confirm("确定要删除[ " + treeNode.name + " ]节点吗？");
                }
                if (flag == true) {
                    $.ajax({
                        url: "/admin/delNode",
                        type: "get",
                        data: {id: treeNode.id},
                        async: false,
                        success: function (data) {
                            //window.location.href = '/admin/node';
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

    function onRemove(e, treeId, treeNode) {
        showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    }

    //修改名称时判断名称是否为空并异步修改
    function beforeRename(treeId, treeNode, newName, isCancel) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        className = (className === "dark" ? "" : "dark");
        showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime() + " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>" : ""));
        if (newName.length == 0) {
            setTimeout(function () {
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.cancelEditName();
                alert("节点名称不能为空.");
            }, 0);
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
            },
            error: function () {
                alert("修改失败");
            }
        });
        return true;
    }

    //修改成功后日志
    function onRename(e, treeId, treeNode, isCancel) {
        showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime() + " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>" : ""));
    }

    //是否展示删除图标
    function showRemoveBtn(treeId, treeNode) {
        if (treeNode.id==1){
            return false;
        }
        return true;
    }

    //是否展示修改图标
    function showRenameBtn(treeId, treeNode) {
        if (treeNode.id==1){
            return false;
        }
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
    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) btn.bind("click", function () {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            $.ajax({
                url: "/admin/addNode",
                data: {
                    fid: treeNode.id,
                    name: "new node" + nodeFileId,
                },
                success: function (data) {
                    if (data.code == 1) {
                        treeNode = zTree.addNodes(treeNode, {id: data.id, pId: treeNode.id, name: "new node" + nodeFileId});
                        zTree.editName(treeNode[0]);
                    } else {
                        alert("增加失败");
                    }
                    nodeFileId++;
                },
                error: function () {
                    alert("增加错误");
                }
            });
            return false;
        });
    };

    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
    };

    function selectAll() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
    }

    function expandNode(e, a) {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getNodeByTId(e);
        treeObj.expandNode(nodes, true, null, null);

        $.ajax({
            url: "/node/urlList",
            type: "get",
            data: {
                nodeId: a == "" ? null : a
            },
            success: function (data) {
                var str = "";
                str += "<tr><td>节点ID</td>&emsp;&emsp;<td style='text-align: center'>节点资源</td><td>删除</td></tr>";
                for (var i = 0; i < data.length; i++) {
                    str += "<tr><td>" + (i + 1) + "</td><td style='text-align: center'>" + data[i].name + "</td><td><button class='btn btn-danger' onclick='delNodeUrl(\"" + data[i].id + "\")'>删除</button></td></tr>";
                }
                $(".col-md-right table").html(str);
                nodeIdNew = $("#nodeId").val("${nodeId}");
                tid = $("#tid").val("${tid}");
            },
            error: function (data) {
                alert("失败");
            }
        });

    }

    function dblClickExpand(treeId, treeNode) {
        return treeNode.level > 0;
    }

    function delNodeUrl(id) {
        $.ajax({
            url: "${ctx}/node/delNodeUrl",
            data: {
                id: id,
            },
            success: function (data) {
                console.log(data)
                expandNode(data[0], data[1]);
            }
        })
    }

    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        expandNode("${tid}", "${nodeId}");
        $("#selectAll").bind("click", selectAll);
    });
</script>
<style type="text/css">
    /*.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}*/
    /*.ztree li ul.level0 {padding:0; background:none;}*/
</style>
