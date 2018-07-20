<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>中国社会科学年鉴数据库</title>
<!-- 共用 js -->
<script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>
<style type="text/css">
    .no-1{overflow:yes;overflow-y :auto;height: 500px;width: 80%;border: dashed 1px #ccc;padding: 20px;background-color: #cccccc;}
    body{padding: 20px;}
</style>
<script>
    var isdo = "0";
    var path = "";
    $(function () {
        $.ajax({url: "/view/logshow?path=1",type: 'get',dataType: "json",success: function (data) {$("#ip").text(data["ip"]);}});
    });
    function settimeoutfun() {
        $.ajax({
            url: "/view/logshow?path=" + path,
            type: 'get',
            dataType: "json",
            success: function (data) {
                setcolor();
                var model = eval(data["list"]);
                var logc = "";
                for (var i in model) {
                    logc += model[i] + "<br>";
                }
                if(logc == "输入路径不正确<br>"){
                    isdo = "0";
                }
                $("#content").html(logc);
                var div = document.getElementById('content');
                div.scrollTop = div.scrollHeight;
                $("#ip").text(data["ip"]);
            }

        });
        if (isdo == "1") {
            setTimeout("settimeoutfun()", 1000);
        }
    }
    function start() {
        path = $("#in").val();
        path = path.replace(/\//g, "%2F");
        path = path.replace(/\\/g, "%2F");
        if (path == "") {
            $("#content").html("没有输入路径");
            setcolor();
            return;
        }

        if (isdo == "1") {
            return false;
        } else {
           isdo = "1";
        }
        settimeoutfun();
        setcolor();
    }
    function stop() {
        isdo = "0";
        setcolor();
    }
    function setcolor(){
        if(isdo == "0"){
            $("#content").css("background-color","#cccccc");
            $("#state").text("已停止.");
        }else{
            $("#content").css("background-color","#00FF00");
            $("#state").text("LOG刷新中...");
        }
    }
</script>
</head>
<body>
访问IP:<span id="ip"></span><br>
<input id="in" style="width: 528px;">
<input type="button" onclick="start()" value="开始"><input type="button" onclick="stop()" value="停止" value="/usr/local/trs/tomcat/apache-tomcat-8.0.47/logs/catalina.out">
<span id="state"></span>
<div id="content" class="no-1"></div>
</body>
</html>
