<%--
  User: zly
  Date: 2017-3-30
  Time: 15:22
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
    <script src="${pageContext.request.contextPath}/static/admin/js/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/custominput.js"></script>
    <script src="${pageContext.request.contextPath}/static/remark/js/custom/jquery.form.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/login.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/resetpwd/resetpwd.js"></script>
    <script>
        var appPath = '${pageContext.request.contextPath}';
    </script>
</head>

<body class="findpwd-body">

<%@ include file="../include/header.jsp"%>

<!--main-->
<div  class="retrieve_main">
    <p  class="retrieve_tit">找回密码</p>
    <img src="${pageContext.request.contextPath}/static/client/images/321_retrieve_bg03.png" alt="" />
    <ul class="retrieve_steps">
        <li>第一步，填写邮箱地址</li>
        <li>第二步，邮箱验证</li>
        <li>第三步，设置新密码</li>
    </ul>
    <div class="clear"></div>
    <div class="retrieve_from find-pwd-step3">
        <form id="resetpwd" method="post">
            <input type="hidden" name="email" value="${email}"/>
            <%--<input type="hidden" name="checkCode" value="${checkCode}"/>--%>
            <input type="hidden" name="userName" value="${requestScope.userName}"/>
            <div class="form_control" style="width: 700px;margin-bottom: 20px">
                <span class="retrieve_title">账号：</span>
                <input type="text" name="username" style="border: 1px solid #cecece;" id=""  class="ret_input required" value="${userName}" placeholder="" readonly/>
            </div>
            <div class="form_control" style="width: 700px">
                <span class="retrieve_title">新密码：</span>
                <input type="password" name="password" style="border: 1px solid #cecece;" id="password"  class="ret_input required pwd" placeholder="请输入新的密码" />
            </div>
            <div class="ret_ty form_control" style="width: 700px">
                <span class="retrieve_title">确认密码：</span>
                <input type="password" id="password_again" style="border: 1px solid #cecece;" equalTo="#password" name="password_again" class="ret_input required" placeholder="请再次输入您新的密码"/>
            </div>
            <div class="ret_btn" id="ret_btn">提交</div>
        </form>
    </div>
</div>
<!--/main-->

<%@ include file="../include/footer.jsp"%>
<%@include file="../include/wangmai.jsp"%>
</body>
</html>
