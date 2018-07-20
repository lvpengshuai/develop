<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<%
    String serviceRoot=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    request.setAttribute("path",serviceRoot);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>中国社会科学年鉴数据库</title>
    <script type="text/javascript" >
        function changeTab(info){
            if(info == "mygz"){
                $("#mygzid").attr("class", "cur");
                $("#myscid").attr("class", "");
                $("#mypjid").attr("class", "");
                $("#mymmid").attr("class", "");

                $("#mygz").show();
                $("#mysc").hide();
                $("#mypj").hide();
                $("#mymm").hide();
            }else if(info == "mysc"){
                $("#mygzid").attr("class", "");
                $("#myscid").attr("class", "cur");
                $("#mypjid").attr("class", "");
                $("#mymmid").attr("class", "");

                $("#mygz").hide();
                $("#mysc").show();
                $("#mypj").hide();
                $("#mymm").hide();
            }else if(info == "mypj"){
                $("#mygzid").attr("class", "");
                $("#myscid").attr("class", "");
                $("#mypjid").attr("class", "cur");
                $("#mymmid").attr("class", "");

                $("#mygz").hide();
                $("#mysc").hide();
                $("#mypj").show();
                $("#mymm").hide();
            }else if(info == "mymm"){
                $("#mygzid").attr("class", "");
                $("#myscid").attr("class", "");
                $("#mypjid").attr("class", "");
                $("#mymmid").attr("class", "cur");

                $("#mygz").hide();
                $("#mysc").hide();
                $("#mypj").hide();
                $("#mymm").show();
            }
        }
    </script>
    <!-- 共用 js -->
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/customer/usercenter.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/customer/publicJS.js"></script>
    <link rel="stylesheet" href="${ctx}/static/client/css/page.css" />
    <!-- 共用css -->
    <link rel="stylesheet" href="${ctx}/static/client/css/style.css" />
    <link rel="stylesheet" href="${ctx}/static/client/css/style0925.css" />
    <link rel="stylesheet" href="${ctx}/static/client/css/alertify.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/alertify.js"></script>

    <!-- banner切换 -->
    <link rel="stylesheet" href="${ctx}/static/client/css/normalize.css" />
    <script src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
    <script>
        var appPath = '${ctx}';
        var username='${userName}';
        var folderName="";
        var spliceDeleteId="";
        var showSize=3;
        var showSizeSplice=6;
    </script>
    <style>
        .selected{font-weight:bold;  color:#ca0000;}
    </style>
</head>

<body onload='init()'>
<%--onload='init()'--%>
<!--顶部-->
<%@include file="/WEB-INF/jsp/client/include/publicTop.jsp" %>
<!--中间-->
<div class="usercenter">

    <div class="statue">
        当前位置：<a href="/index">首页</a> &gt;<span>个人中心</span>
    </div>

    <div class="wrapbox">
        <!-- 左侧 -->
        <div class="userleft">

            <div class="userphotos">
                <span><img src="${ctx}/static/client/img/userphotos.png"></span>
                <p>${organization}</p>
                <p><b>${userName}</b></p>
            </div>

            <!-- 目录 -->
            <div class="user_cate">
                <ul>
                    <li><a id="mygzid" class="cur" href="javaScript:changeTab('mygz')"><em class="em01"></em>我的关注</a></li>
                    <li><a id="myscid" href="javaScript:changeTab('mysc')"><em class="em02"></em>我的收藏</a></li>
                    <li><a id="mypjid" href="javaScript:changeTab('mypj')"><em class="em03"></em>我的拼接</a></li>
                    <li><a id="mymmid" href="javaScript:changeTab('mymm')"><em class="em04"></em>修改密码</a></li>
                </ul>
            </div>

        </div>
        <!-- 我的关注 -->
        <div id="mygz" style='display:block;'><!-- start -->
            <!-- 右侧 -->
            <div class="userright" id="myID">


                <!-- 下面列表 -->
                <div class="userright-b">

                    <!-- 我的关注 -->
                    <div class="carebox" >

                    </div>

                </div>

            </div>
        </div><!-- end -->
        <!-- 我的收藏 -->
        <div id="mysc" style='display:none;'><!-- start -->
            <!-- 中间 -->
            <div class="userright userrightstyle">
                <!-- 收藏列表 -->
                <div class="favolist"  id="collectList">
                    <%--<div class="user_pre_next" id="pageNext">--%>
                    <%--</div>--%>
                </div>

            </div>
            <!-- 添加收藏 -->
            <div class="favolistR" id="folder">
            </div>
        </div><!-- end -->
        <!-- 我的拼接 -->
        <div id="mypj"  style='display:none;'><!-- start -->
            <!-- 右侧 -->
            <div class="userright">

                <!-- 顶部统计 -->
                <div id="mypjt" class="userright-t">
                    <dl>
                        <dt><img src="${ctx}/static/client/img/usericon3.png">已关注<b>15</b>个关键词</dt>
                        <dd><img src="${ctx}/static/client/img/usericon3.png">本周新增关注<b>5</b>个</dd>
                        <dd><img src="${ctx}/static/client/img/usericon2.png">新增收藏<b>15</b>条</dd>
                        <dd><img src="${ctx}/static/client/img/usericon1.png">新增拼接阅读<b>5</b>条</dd>
                    </dl>
                </div>

                <!-- 下面列表 -->
                <div class="userright-b" >
                    <!-- 我的拼接 -->
                    <div class="pinjiebox" id="splice">
                    </div>
                </div>

            </div>
        </div><!-- end -->
        <!-- 修改密码 -->
        <div id="mymm"  style='display:none;'><!-- start -->
            <!-- 右侧 -->
            <div class="userright">

                <!-- 顶部统计 -->
                <div id="mymmt" class="userright-t">
                    <dl>
                        <dt><img src="${ctx}/static/client/img/usericon3.png">已关注<b>15</b>个关键词</dt>
                        <dd><img src="${ctx}/static/client/img/usericon3.png">本周新增关注<b>5</b>个</dd>
                        <dd><img src="${ctx}/static/client/img/usericon2.png">新增收藏<b>15</b>条</dd>
                        <dd><img src="${ctx}/static/client/img/usericon1.png">新增拼接阅读<b>5</b>条</dd>
                    </dl>
                </div>

                <!-- 下面列表 -->
                <div class="userright-b">
                    <!-- 修改密码 -->
                    <dl class="base_form">
                        <form id="addUser" <%--action="${ctx}/admin/admin-user"--%> method="post">
                            <input type="hidden" name="id" value="${user.id}">
                            <dd><span>当前密码：</span>
                                <input class="logtext" value=""  type="password" id="oldpassword" name="oldpassword" required=""
                                       aria-required="true" placeholder="请输入当前密码"/>
                            </dd>
                            <dd><span>新密码：</span>
                                <input class="logtext" placeholder="请填写密码" id="password" type="password"
                                       name="password"  type="password" placeholder="请输入新密码" required/>
                            </dd>
                            <dd><span>确认新密码：</span>
                                <input class="logtext" value="" type="password" placeholder="请确认新密码"  id="password_again"
                                       name="password_again" equalTo="#password" required/>
                            </dd>
                            <dd><span>&nbsp;</span>
                                <input class="user_pwd_sub" value="确认修改" id="resetpassword_modal_btn_true" type="button" onclick='subfuction("${userName}")' >
                                <input class="user_pwd_ret" value="重置" type="button" onclick='clearInput()'>
                            </dd>
                        </form>
                    </dl>
                </div>

            </div>
        </div><!-- end -->
    </div>
</div>
<!-- 新建文件夹 -->
<div class="layer layer-shoucangjia">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="layerinfo">
            <div class="favolistR folder">
                <h5>请输入收藏夹名称</h5>
                <ul>
                    <li>
                        <input placeholder="请输入收藏夹名称" type="text" class="addfavetetx" id="addfavetetx">
                    </li>
                </ul>
                <div class="btns"><a href="#" onclick="insertFolder()">确定</a><a href="#" class="btn-cancle">取消</a></div>
            </div>
        </div>
    </div>
</div>

<!-- 删除确定取消 收藏夹-->
<div class="layer layer-favedelete">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="layerinfo">
            <div class="deletetips">您确定要删除吗？</div>
            <div class="btns"><a href="#" onclick="folderDelete()">确定</a><a href="#" class="btn-cancle">取消</a></div>
        </div>
    </div>
</div>

<!-- 删除确定取消 我得拼接 -->
<div class="layer layer-splicedelete">
    <div class="layerbg"></div>
    <div class="layerbox">
        <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
        <div class="layerinfo">
            <div class="deletetips">您确定要删除吗？</div>
            <div class="btns"><a href="#" onclick="deleteSplice()">确定</a><a href="#" class="btn-cancle">取消</a></div>
        </div>
    </div>
</div>
<!-- 底部 -->
<%@include file="/WEB-INF/jsp/client/include/publicFoot.jsp" %>
<script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>
<script type="text/javascript">
    function init(){
        //初始化 读取关注词
        concernShow();
        //初始化 读取我得收藏
        collectShow("","1",showSize);
        //加载收藏文件夹
        collectFolder();
        //初始化 修改密码和拼接
        concernShowPassword();
        //初始化 读取我得拼接
        spliceShow("1",showSizeSplice);

    }
    // 导航栏选定方法
    set_class(".nav .navbox a","on","");
    $("body").delegate(".folName","click", function(){
        $(this).parent().siblings().children().removeClass("selected");   // 删除其他兄弟元素的样式
        $(this).addClass('selected');                            // 添加当前元素的样式
    });
</script>
</body>
</html>
