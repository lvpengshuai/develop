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
    <title>404</title>
    <link href="${pageContext.request.contextPath}/static/client/css/base.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/client/css/error.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/client/plugin/layer/layer.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/client/css/login.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/jquery-validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/custominput.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/login.js"></script>
</head>

<body class="searchbody">

<!--main-->
<div class="container">
    <div class="error-box">
        <div class="error-img"><img title="404" src="${pageContext.request.contextPath}/static/client/images/404-icon.png" /></div>
        <div class="error-info"><h2>很抱歉，您访问的页面已丢失......</h2><p>您可以先去别的页面看看</p><a href="${pageContext.request.contextPath}/index">返回首页</a></div>
    </div>
</div>

<%@ include file="../include/footer.jsp"%>

</body>
</html>
