<%--
  Created by IntelliJ IDEA.
  User: 门喜朋
  Date: 2017/11/21
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>中国社会科学年鉴数据库</title>
</head>
<script type="text/javascript">
    var t = 5;
    function countDown(){
        var time = document.getElementById("time");
        t--;
        time.innerHTML=t;
        if (t<=0) {
            location.href="/index";
            clearInterval(inter);
        };
    }
    var inter = setInterval("countDown()",1000);
    //window.onload=countDown;
</script>

</head>
<body onload="countDown()">

禁止访问，<span id="time">5</span>秒后跳转到首页
<!--也可以在这里写javascript代码，这样就不必在body标签中写onload="countDown()"。-->
</body>
</html>
