<%--
  Created by IntelliJ IDEA.
  User: xuxuecheng
  Date: 2017/9/13
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<link rel="stylesheet" href="${ctx}/static/client/css/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/admin/js/ztree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/ztree/jquery.ztree.exedit-3.5.js"></script>
<div>
    <div class="zTreeDemoBackground left" style="width: 300px;min-height: 800px">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
</div>

<script>
    //接受json数据
    var zNodes = ${json};
    /**
     * ztree插件初始化js
     * Created by xuxuecheng on 2017/9/15.
     */
//初始化插件
    var setting = {
        view: {
            showLine: false, dblClickExpand: false,
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            beforeExpand: beforeExpand,
            onExpand: onExpand,
            onClick: onClick,
        }
    };


    var log, className = "dark";

    function beforeClick(treeId, treeNode) {
        className = (className === "dark" ? "" : "dark");
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var id = treeNode.id;
        if (treeNode.myAttr=="file"){
            $.ajax({
                url: '/getSwf',
                data: "id=" + id,
                success: function (data) {
                    var databoxleftHeight=$(".databoxleft").height()-140+"px";
                    var sss = encodeURI("/getIfreams?file=" + data.node_Url);
                    var zzz = encodeURI(data.id);
                    var str = "<iframe class='iframeClass' width='100%' height='" + databoxleftHeight + "' src='" + sss + "' excel='" + zzz + "'></iframe>";
                    $("#swfId").html(str);
                    //setSWF(data);
                }
            })
        }
        return true;
    }

    //实现节点单一打开
    function beforeExpand(treeId, treeNode) {
        var treeNodeP = treeNode.parentTId ? treeNode.getParentNode() : null;
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        for (var i = 0, l = !treeNodeP ? 0 : treeNodeP.children.length; i < l; i++) {
            if (treeNode !== treeNodeP.children[i]) {
                zTree.expandNode(treeNodeP.children[i], false, true, true, true);
            }
        }
    }

    //实现节点单一打开
    function onExpand(event, treeId, treeNode) {

    }

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode, null, null, null, true);
    }

    function expandNode(e) {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getNodeByTId(e);
        treeObj.expandNode(nodes, true, true, true, true);

    }

    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });
</script>
