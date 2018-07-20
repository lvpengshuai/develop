<%--
  Created by IntelliJ IDEA.
  User: 门喜朋
  Date: 2017/12/27
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>中国社会科学年鉴数据库</title>
</head>
<body>
<iframe src="${ctx}/getIfream?file=${pdfname}&page=${startPage}" style="border: none;margin: -2px;padding: 0;" width="100%" height="98.5%"></iframe>

</body>
</html>
