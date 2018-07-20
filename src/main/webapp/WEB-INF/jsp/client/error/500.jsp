<%--
  User: zly
  Date: 2017-4-5
  Time: 14:08
--%>
<%@include file="../include/tags.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>500</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/common.js"></script>

<body class="searchbody">


<!--main-->
<div class="container">
    <div class="error-box">
        <div class="error-img"><img title="500" src="${pageContext.request.contextPath}/static/client/images/500-icon.png" /></div>
        <div class="error-info"><h2>很抱歉，服务器目前出现异常！</h2><p>我们正在努力修复中，请您稍后在试试...</p><a href="${pageContext.request.contextPath}/index">返回首页</a></div>
    </div>
</div>
<!--/main-->

<%--<%@ include file="../include/footer.jsp"%>--%>

</body>
</html>
