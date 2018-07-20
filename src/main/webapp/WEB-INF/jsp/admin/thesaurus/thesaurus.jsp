<%--
  User: pz
  Date: 2017-3-2
  Time: 13:51
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>

    <title>词库编辑系统</title>

    <!-- Stylesheets -->
    <link rel="stylesheet" href="${ctx}/static/remark/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/css/bootstrap-extend.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/assets/css/site.min.css">
    <!-- Plugins -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/animsition/animsition.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/asscrollable/asScrollable.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/switchery/switchery.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/intro-js/introjs.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/slidepanel/slidePanel.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/jquery-mmenu/jquery-mmenu.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/flag-icon-css/flag-icon.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/waves/waves.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/bootstrap-sweetalert/sweet-alert.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/glyphicons/glyphicons.css">
    <!-- Fonts -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/weather-icons/weather-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/web-icons/web-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/open-iconic/open-iconic.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/brand-icons/brand-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/material-design/material-design.min.css">
    <!--[if lt IE 9]>
    <script src="${ctx}/static/remark/vendor/html5shiv/html5shiv.min.js"></script>
    <![endif]-->
    <!--[if lt IE 10]>
    <script src="${ctx}/static/remark/vendor/media-match/media.match.min.js"></script>
    <script src="${ctx}/static/remark/vendor/respond/respond.min.js"></script>
    <![endif]-->
    <!-- Scripts -->
    <script type="text/javascript"src="${pageContext.request.contextPath}/static/client/js/jquery-1.11.3.min.js"></script>
    <script src="${ctx}/static/remark/global/vendor/modernizr/modernizr.min.js"></script>
    <script src="${ctx}/static/remark/global/vendor/breakpoints/breakpoints.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/vue/vue.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/vue/vue-resource.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/echarts3/echarts.min.js"></script>

    <script>
        Breakpoints();
        var appPath = '${ctx}';
    </script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css"/>
    <link href="${pageContext.request.contextPath}/static/admin/css/thesaurus/thesaurus.css" rel="stylesheet"/>
</head>
<body class="site-navbar-small dashboard site-menubar-unfold">
<!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->
<%-- 顶部右侧菜单导航开始 --%>
<%@include file="/WEB-INF/jsp/admin/include/nav.jsp" %>
<%-- 顶部右侧菜单导航结束 --%>


    <div class="page-content container-fluid padding-top-5">
        <%--主要内容--%>
        <div id="main">
            <!--主菜单-->
                    <button type="button" class="btn btn-success btn-icon waves-effect waves-light"
                            id="import">同步词库
                    </button>
            <button onclick="openAdd()" class="btn btn-success btn-icon waves-effect waves-light" type="button">新建主题词</button>
            <button onclick="openSynCkm()" class="btn btn-success btn-icon waves-effect waves-light" type="button">同步到ckm</button>
            <span class="page-list2">
                                <span style="" class="btn-group dropdown">
                                    <button onclick="" class="btn btn-default  btn-outline dropdown-toggle" type="button"
                                            data-toggle="dropdown">
                                        <span class="page-size2" name="pageSize" id="expert1" >导出全部</span>
                                        <span class="caret"/>
                                    </button>
                                    <ul class="dropdown-menu" id="knowledge-dropdown-menu" role="menu">
                                        <li class="active">
                                            <a onclick="openDownload('xml')" href="javascript:void(0)" value="1">TrsServer xml</a>
                                        </li>
                                         <li>
                                            <a onclick="vm.downloadThesaurus(3)" href="javascript:void(0)" value="0">excel</a>
                                        </li>

                                    </ul>
                                </span>
                            </span>
            <button onclick="vm.downloadThesaurus(2)" class="btn btn-success btn-icon waves-effect waves-light" type="button">导出选中节点EXCEL</button>
            <%--jsTree开始--%>
            <!--/栏目名称-->
            <div style="padding: 5px 0px 0px 15px;">
                <input type="text" placeholder="请输入您要查找知识元名称" v-model="treeWord"
                       class="search-tree-input"><span id="search-notice" style="display:none;color: red">无数据显示</span>
            </div>
            <div class="tree-container-wrap">
                <div class="tree-container" id="tree"></div>
                <div class="total-word">共<span id="total-word"></span>个主题词</div>
            </div>

            <%--jstree结束--%>

            <%--编辑记录--%>


            <div class="main-info">

            <%--核心词关联信息--%>
            <div class="thesaurus-info" id="info-edit">
                <div class="thesaurus-history">

                    创建人：<span id="word-creator">admin</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    最后修改人员：<span id="word-last-editor">admin</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    最后修改时间: <span id="word-updateTime">2017-03-1</span>
                </div>
                <%--重命名--%>
                <div style="" class="rename" id="rename">
                    <form action="" method="post" class="rename">
                        <div style="display: inline">
                            <label><h1>主题词 </h1></label>
                            <br>
                            <label class="spanLabel">名&nbsp;称 :</label>
                            <input class="word-type" type="text" name="type" value="rename">
                            <input style="display:inline;width: 500px" class="form-control" id="renameWord-rename" type="text" name="rename"/>
                            <input id="renameWord" style="display: none" type="text" name="word"/>
                        </div>
                        <br>
                        <br>
                        <label style="margin-left: 40%">
                            <button style="display:inline;" onclick="vm.editWord(this)" type="button" class="btn btn-success btn-icon waves-effect waves-light"
                                    >保存
                            </button>
                            <button style="display:inline;" id="checkedTreeId" type="button" class="btn btn-danger"
                            >删除
                            </button>

                        </label>
                    </form>
                </div>
                    <h1>主题关系</h1>
                <%--上位词--%>
                <div class="parent" id="parent">
                    <div style="display: inline">
                    <form action="" method="post" class="parent">
                            <label class="spanLabel">&nbsp;上位词 :</label>
                      
                            <input class="word-type" type="text" name="type" value="parent">
                            <input style="display: none;" id="parentWord" class="mainWord" type="text" name="word" value="">
                            <input readonly="readonly" style="display:inline;width: 500px" class="form-control" id="parent-node" type="text" name="parent"/>
                    </form>
                    </div>

                </div>
                    <br>
                <%--下位词--%>
                <div class="childes" id="childes">
                    <div style="display:inline;">
                    <form action="" method="post" class="childes">
                        <label class="spanLabel">&nbsp;下位词 :</label>
                    <input class="word-type" type="text" name="type" value="child">
                    <input id="childesWord" class="mainWord" type="text" name="word" value="">
                    <input readonly="readonly"  style="display:inline;width: 500px" class = "form-control" id="child-nodes" name="childes">
                    </form>
                    </div>
                    <br>
                </div>
                    <br>
                    <%--//////////////////////////////////////////////////////////////////////////////////////////--%>
                    <form action="" method="post" class="thesaurus">
                    <%--同义词--%>
                    <div class="thesaurus" id="thesaurus-div">
                        <div style="display: inline;">

                            <label class="spanLabel">&nbsp;同义词 :</label>
                            <input class="word-type" type="text" name="type" value="zhuTi">
                            <input id="thesaurus-id" class="mainWord" type="text" name="word" value="">
                            <input  placeholder="输入多个词语时请用','做分隔符"  style="display:inline;width: 500px" class = "form-control" id="thesaurus-nodes" name="thesaurus">
                        </div>
                    </div>
                    <br>
                    <%--缩略词--%>
                    <div class="abbreviation" id="abbreviation-div">
                        <div style="display: inline;">

                            <label class="spanLabel">&nbsp;缩略词 :</label>

                            <input  placeholder="输入多个词语时请用','做分隔符" style="display: inline;width: 500px" class = "form-control" id="abbreviation-nodes" name="abbreviation">


                        </div>
                    </div>
                    <br>
                    <%--外文词--%>
                    <div class="foreignword" id="foreignword-div">
                        <div style="display: inline">

                            <label class="spanLabel">&nbsp;外文词 :</label>
                            <input  placeholder="输入多个词语时请用','做分隔符" style="display:inline;width: 500px" class = "form-control" id="foreignword-nodes" name="foreignword">


                        </div>
                    </div>
                    <br>
                    <%--相关词--%>
                    <div class="aboutword" id="aboutword-div">
                        <div style="display: inline;">
                            <label class="spanLabel">&nbsp;相关词 :</label>
                            <input  placeholder="输入多个词语时请用','做分隔符" style="display:inline;width: 500px" class = "form-control" id="aboutword-nodes" name="aboutword">

                        </div>
                    </div>
                    <br>
                    <%--分子式--%>
                    <div class="molecular-formula" id="molecular-formula">
                        <div style="display: inline;">

                                <label class="spanLabel">&nbsp;分子式 :</label>
                                <input style="display:inline;width: 500px" class="form-control" cols="50" rows="2" id="molecular-formula-nodes" name="molecularFormula">
                            <br>
                            <br>
                                <button style="margin-left:40%;display: inline;" onclick="vm.editWord(this)" type="button" class="btn btn-success btn-icon waves-effect waves-light"
                                >保存
                                </button>
                        </div>
                    </div>
                    </form>
                    <%--//////////////////////////////////////////////////////////////////////////////////////////--%>
                    <br>
                    <h1>短语关系</h1>
                    <%--相关短语--%> <form action="" method="post" class="aboutphrase">
                    <div class="aboutphrase" id="aboutphrase-div">
                        <div style="display:inline">
                            <label class="spanLabel">相关短语 :</label>
                            <input class="word-type" type="text" name="type" value="phrase">
                            <input id="aboutphrase-id" class="mainWord" type="text" name="word" value="">
                            <input placeholder="输入多个短语时请用','做分隔符" style="display:inline;width: 500px" class = "form-control" id="aboutphrase-nodes" name="aboutphrase">

                        </div>
                    </div>
                    <br>
                    <%--简单短语--%>
                    <div class="similarphrase" id="similarphrase-div">
                        <div style="display: inline">

                            <label class="spanLabel">简单短语 :</label>
                            <input placeholder="输入多个短语时请用','做分隔符" style="display:inline;width: 500px" class = "form-control" id="similarphrase-nodes" name="similarphrase">
                            <br>
                            <br>
                            <button style="margin-left:40%;display: inline" onclick="vm.editWord(this)" type="button" class="btn btn-success btn-icon waves-effect waves-light"
                            >保存
                            </button>
                        </div>
                    </div>
                </form>
                    <br>
                    <h1>本体关系</h1>
                    <%--同位词--%>
                    <div class="sameParentWord" id="sameParentWord-div">
                        <div style="display: inline">
                            <form action="" method="post" class="sameParentWord-form">
                                <label class="spanLabel">&nbsp;同位词 :</label>
                                <input style="display:inline;width: 500px" class = "form-control" id="sameParentWord-nodes" name="sameParentWord">
                            </form>
                        </div>
                    </div>
                    <br>
                <%--核心词--%>
                <div class="core-words" id="core-words">
                        <label class="spanLabel">核心词 :</label>
                    <button class="btn btn-success btn-icon waves-effect waves-light" onclick="vm.coreWordAdd()" id="core-words-add" type="button" class="button">增加</button>
                    <div class="show-head">
                        <span style="float: left;">序号</span>
                        <br>
                    </div>
                    <div id="core-words-nodes-scroll">
                        <ui id="core-words-nodes"></ui>
                    </div>

                </div>

            </div>
            <%--核心词关联信息结束--%>
            </div>

            <%--提示弹出层--%>
            <div class="layui-layer-shade" id="layui-layer-shade2" times="2"
                 style="display:none;z-index:19891015; background-color:#000; opacity:0.4; filter:alpha(opacity=40);"></div>

            <div class="layui-layer layui-layer-page " id="layui-layer2" type="page" times="2" showtime="0" contype="string"
                 style="display:none;z-index: 19891016; width: 543px; height: 293px; top: 240.5px; left: 281.5px;">
                <div class="layui-layer-title" style="cursor: move;">提示</div>
                <div id="" class="layui-layer-content" style="height: 250px;">
                    <div style="padding:50px; text-align:center;"><p><img src="/static/client/images/update-sucess.png"></p>
                        <p class="update-sucess">同步完成</p></div>
                </div>
                <span class="layui-layer-setwin"><a class="layui-layer-ico layui-layer-close layui-layer-close1"
                                                    href="javascript:;"></a></span><span class="layui-layer-resize"></span></div>
            <%--提示弹出层--%>

            <%--隐藏存值--%>
            <textarea style="display: none" id="clickWord"></textarea>
            <textarea style="display: none" id="closeIntervalId"></textarea>
        </div>
        <%--主要内容结束--%>

    </div>

    <!-- 主界面结束 -->
    <!-- 脚部开始 -->
    <%@include file="/WEB-INF/jsp/admin/include/footer.jsp" %>
    <%-- 脚部结束 --%>
<!-- 通用模态框开始 -->
<%@include file="/WEB-INF/jsp/admin/include/modal.jsp" %>
<%-- 通用模态框结束 --%>
<%@include file="../include/scrpit.jsp" %>
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script src="${ctx}/static/remark/global/js/components/material.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/vue/vue.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/vue/vue-resource.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/jstree/jstree.min.js"></script>

<script>
    (function (document, window, $) {
        'use strict';
        var Site = window.Site;
        $(document).ready(function () {
            Site.run();
        });
    })(document, window, jQuery);
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/thesaurus/thesaurus.js"></script>
</body>
</html>