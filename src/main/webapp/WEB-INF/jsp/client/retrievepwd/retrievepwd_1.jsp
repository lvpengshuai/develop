<%--
  Created by IntelliJ IDEA.
  User: zly
  Date: 2017-3-30
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../include/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>找回密码</title>
    <link href="${pageContext.request.contextPath}/static/client/css/base.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/client/customer/css/retrievepwd/style321.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/client/css/login.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/jquery/jquery-validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/layer/layer.js"></script>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/jquery-validate.js"></script>--%>
    <script src="${pageContext.request.contextPath}/static/admin/js/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/remark/js/custom/jquery.form.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/custominput.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/login.js"></script>
    <script>
        var appPath = '${pageContext.request.contextPath}';
        function reloadcode(){
            $('#imageCode_3').attr('src',appPath+'/imagecode?createTypeFlag='+Math.floor(Math.random()*100));
        }
    </script>
    <style>
        .form_control.success .valid_message{ background:none}
    </style>
</head>

<body class="findpwd-body">

<%--<%@ include file="head.jsp"%>--%>
<%@ include file="../include/header.jsp"%>

<!--main-->
<div  class="retrieve_main">
    <p  class="retrieve_tit">找回密码</p>
    <img src="${pageContext.request.contextPath}/static/client/images/321_retrieve_bg01.png" alt="" />
    <ul class="retrieve_steps">
        <li>第一步，填写邮箱地址</li>
        <li>第二步，邮箱验证</li>
        <li>第三步，设置新密码</li>
    </ul>
    <div class="clear"></div>
    <div class="retrieve_from find-pwd-first">
        <form method="post" id="retrievepwd_from">
            <div class="form_control">
                <span class="retrieve_title">电子邮箱：</span>
                <input type="text" name="email" style="border: 1px solid #cecece;" id="email-show"  class="ret_input required email" placeholder="请输入您注册时所填写的邮箱地址"/>
                <span id="email-tip" class="valid_message" style="font-size: 12px;"></span>
            </div>
            <div class="ret_ty form_control">
                <span class="retrieve_title">验证码：</span>
                <input type="text" name="checkCode" style="border: 1px solid #cecece;" id="checkcode" class="ret_input input_yzm required"  placeholder="输入验证码"/>
                <img src="${pageContext.request.contextPath}/imagecode" id="imageCode_3" onclick="reloadcode()" class="yzm_img">
                <p class="yzm_font" onclick="reloadcode()">看不清楚换一张</p>
                <span id="code-tip" class="valid_message" style="font-size: 12px;"></span>
            </div>
            <div class="clear"></div>
            <div class="ret_btn" id="ret_btn">下一步</div>
        </form>

    </div>
</div>
<!--/main-->

<%@ include file="../include/footer.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/customer/js/retrievepwd/retrievepwd.js"></script>
<%@include file="../include/wangmai.jsp"%>
</body>
</html>
