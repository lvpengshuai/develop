<%--
  User: zly
  Date: 2017-3-30
  Time: 15:22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../include/tags.jsp" %>
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
    <style>
        .ret_btn1{width:320px;height:48px;  margin:30px auto; color:#fff; font-size:20px; line-height:48px; text-align:center; background-color: #cdd1d6; border-radius:5px; cursor: default; margin-left:90px;}

    </style>
</head>

<body class="findpwd-body">

<%--<%@ include file="head.jsp"%>--%>
<%@ include file="../include/header.jsp"%>


<!--main-->
<div  class="retrieve_main">
    <p  class="retrieve_tit">找回密码</p>
    <img src="${pageContext.request.contextPath}/static/client/images/321_retrieve_bg02.png" alt="" />
    <ul class="retrieve_steps">
        <li>第一步，填写邮箱地址</li>
        <li>第二步，邮箱验证</li>
        <li>第三步，设置新密码</li>
    </ul>
    <div class="clear"></div>
    <div class="retrieve_from">
        <div class="find-pwd-step2-box">
            <p class="tips1">设置新密码的链接已经发送到<span>${fn:substring(fn:split(email,'@')[0],0,3)}******@${fn:split(email,'@')[1]}</span></p>
            <p class="tips2">请您在30分钟之内登录邮箱，点击邮箱内链接设置新的密码</p>
            <a class="ret_btn" id="sendE" href="javascript:void(0);">再次发送邮件</a>
            <div class="ret_btn1" id="resendE" style="display: none;" href="javascript:void(0);"><span id="time"></span>S后再次发送邮件</div>
        </div>
    </div>
</div>
<!--/main-->

<%@ include file="../include/footer.jsp"%>
<input type="hidden" id="email" value="${email}"/>
<script>
    var url = appPath + "/retrievepwd/sendemail";

    $(function () {
        if (window.name == '-1' || window.name == "") {
            $("#resendE").attr("style", "display:none");
            $("#sendE").attr("style", "display:block");
        }else {

            timeInterval();
        }
    });

    function timeInterval() {
        var maxtime = getMaxtime();
        $("#time").text(maxtime);
        var time = setInterval(function () {
            $("#time").text(maxtime);
            if (maxtime >= 0) {
                $("#time").text(maxtime);
                maxtime--;
                window.name = maxtime;
            } else {
                $("#resendE").attr("style", "display:none");
                $("#sendE").attr("style", "display:block");
                clearInterval(time);
            }
        }, 1000);
    }

    function getMaxtime() {
        var maxtime;
        if (window.name == '-1' || window.name == "") {
            maxtime = 1 * 60;
        } else {
            maxtime = window.name;
            $("#sendE").attr("style", "display:none");
            $("#resendE").attr("style", "display:block");
        }
        return function () {
            return maxtime;
        }();
    }
    $("#sendE").click(function () {
        $("#sendE").attr("style", "display:none");
        $("#resendE").attr("style", "display:block");

        timeInterval();

        $.ajax({
            url: url,
            type: "post",
            data: {"email": $("#email").val(), "checkCode": "resend"},
            success: function (data) {

            }
        });
    });

</script>
<%@include file="../include/wangmai.jsp" %>
</body>
</html>
