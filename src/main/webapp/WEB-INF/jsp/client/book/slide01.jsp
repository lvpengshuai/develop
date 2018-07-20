<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>中国社会科学年鉴数据库</title>
<!-- 共用 js -->
<script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>

<!-- 共用css -->
<link href="${ctx}/static/client/css/guide.css" rel="stylesheet" type="text/css" />

<!-- 图片切换 插件 -->
<link href="${ctx}/static/client/css/flexslider.css" type="text/css" rel="stylesheet"  />
<script type="text/javascript" src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
<!-- 图片切换 插件 -->




</head>
<body style=" background:none">
	

        

        <!-- 弹出图片切换 -->
        <div class="iframe-slide">
            <div class="flexslider">
                <ul class="slides">
                    <c:forEach items="${bookFileByCodeAndTitle18}" var="bookFile" varStatus="i">
                            <li style="width: 858px;">
                                <a href="${ctx}${bookFile.fileurl}" download="${bookFile.fileurl.substring(32)}"><i class="savaicon"></i></a>
                                <div>
                                    <a href="${ctx}/article/articleInfo?zid=${bookFile.fid}&bookcode=${bookFile.bookcode}"
                                       target="_blank"  title="点击查看原文"><img src="${ctx}${bookFile.fileurl}"/></a>
                                </div>
                                <h4>${bookFile.title}</h4>
                                <p style="margin-top: 20px">${i.index+1}/${bookFileByCodeAndTitle18.size()}</p>
                            </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

    <script>
        $(function () {
            $(".iframe-slide .flexslider").flexslider({
                animation: "slide"
            }).flexslider(${indexNum});
        })
    </script>
			
</body>
</html>
