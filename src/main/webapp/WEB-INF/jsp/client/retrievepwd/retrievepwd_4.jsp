<%--
  Created by IntelliJ IDEA.
  User: zly
  Date: 2017-3-30
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>找回密码</title>
    <link href="${pageContext.request.contextPath}/static/client/css/base.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/client/customer/css/retrievepwd/style321.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/client/css/login.css" rel="stylesheet" />

    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/jquery-validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/custominput.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/login.js"></script>
</head>

<body class="findpwd-body">

<%@ include file="../include/header.jsp"%>

<!--main-->
<div  class="retrieve_main">
    <div class="retrieve_from">
        <div class="find-pwd-step4">
            <div class="tips1">恭喜 !  您已经成功设置新账户密码</div>
            <div class="tips2">新密码设置成功，将于<span id="time"></span>秒钟后自动跳转登录化工知识库，如未跳转请点击“完成”按钮</div>
            <a class="ret_btn" href="#" target="_blank">完成</a>
        </div>
    </div>
</div>
<!--/main-->

<%@ include file="../include/footer.jsp"%>
<script>

    var appPath = "${pageContext.request.contextPath}";
    var time = 3;

    $("#time").text(time);
    var interval = setInterval(function () {
        time--;
        $("#time").text(time);
        if (time == 0) {
            window.location.href = appPath + "/login";
            clearInterval(interval);
        }
    }, 1000);

</script>
<%@include file="../include/wangmai.jsp"%>
</body>
</html>
