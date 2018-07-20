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

	<!-- 公用搜索进入文件 -->
	<script type="text/javascript" src="${ctx}/static/client/customer/publicJS.js"></script>
	<script type="text/javascript" src="${ctx}/static/client/customer/search.js"></script>
	<!-- 公用搜索进入文件 -->

	<!-- 共用css -->
	<link rel="stylesheet" href="${ctx}/static/client/css/style.css" />

	<!-- banner切换 -->
	<link rel="stylesheet" href="${ctx}/static/client/css/normalize.css" />
	<script src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
	<script src="${ctx}/static/client/js/demo.js"></script>
	<!--echarts js-->、
	<script src="${ctx}/static/client/js/ECharts/echarts.min.js"></script>
	<%--机构统计图css--%>
	<link rel="stylesheet" href="${ctx}/static/client/css/organ.css">
	<%--人物统计图css--%>
	<link rel="stylesheet" href="${ctx}/static/client/css/statis.css">

</head>
<body>

	<!--顶部-->
	<%@include file="/WEB-INF/jsp/client/include/publicTop.jsp" %>
	<!--导航-->
	<%@include file="/WEB-INF/jsp/client/include/publicNavigation.jsp" %>
	<!-- banner -->
	<%@include file="/WEB-INF/jsp/client/include/publicBanner.jsp" %>
	<!--精品书籍推荐-->
	<div class="wrapin">
        	<div class="resultfx">
            
            	<!-- 当前位置 -->
            	<div class="statue">
                    当前位置：<a href="${ctx}/index">首页</a> &gt;&gt;<span>结果分析</span>
                </div>
				
				<!-- 找到结果 -->
            	<div class="resultnum">
                    为您找到相关条目约<b>${trscount}</b>个<a href="javascript:history.back();">返回检索结果</a>
                </div>
				
				             

                
                <!-- 切换 -->
                <div class="tab package packagestyle"> 
                	<ul class="tab-hd"><li class="active">年鉴分布</li><li>时间分布</li><li id="character">人物</li><li>机构</li></ul>
                    <ul class="tab-bd">
                        <li style="display: list-item;">
                        	<!-- 年鉴分布 -->
						   <div class="resultdatabox">
							   <jsp:include page="../include/annual.jsp"></jsp:include>
						   </div>
                        </li>
                        <li>
                        	<!-- 时间分布 -->
                            <div  class="resultdatabox">
								<jsp:include page="../include/within.jsp"></jsp:include>
						    </div>
                        </li>
						<li>
                        	<!-- 人物 -->
                            <div >
								<jsp:include page="../include/statis.jsp"></jsp:include>
						    </div>
                        </li>
						<li>
                        	<!-- 机构 -->
                            <div class="resultdatabox">
								<jsp:include page="../include/organ.jsp"></jsp:include>
								<%--<img src="${ctx}/static/client/img/graph5.png" alt="">--%>
						    </div>
                        </li>
                    </ul>
            	</div>    
           </div>
	</div>
	<!-- 底部 -->
	<%@include file="/WEB-INF/jsp/client/include/publicFoot.jsp" %>
	<!-- 弹出目录 -->
	<div class="layer">
     	<div class="layerbg"></div>
        <div class="layerbox">
         	<span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
            <div class="layerinfo">
            	<dl class="mululist">
            		<dt>第一篇 特辑</dt>
                    <dd><a href="#">在联合国教科文组织总部的演讲</a></dd>
                    <dd><a href="#">推动人类文明交流互鉴，促世界和平谱写新篇章</a></dd>
                    <dd><a href="#">中国民俗学会进入联合国教科文组织非遗申报“审查机构”</a></dd>
                    <dd><a href="#">朝戈金当选国际哲学与人文科学理事会主席</a></dd>
                    <dd><a href="#">我们站在文艺发展的历史新起点上</a></dd>
                    <dd><a href="#">民间文艺永恒价值的诘问——学习习近平总书记在文艺工作座谈会上的讲话</a></dd>
                    <dd><a href="#">推动人类文明交流互鉴，促世界和平谱写新篇章</a></dd>
                    <dd><a href="#">中国民俗学会进入联合国教科文组织非遗申报“审查机构”</a></dd>
                    <dd><a href="#">朝戈金当选国际哲学与人文科学理事会主席</a></dd>
                    <dd><a href="#">我们站在文艺发展的历史新起点上</a></dd>
                    <dd><a href="#">民间文艺永恒价值的诘问——学习习近平总书记在文艺工作座谈会上的讲话</a></dd>
            	</dl>
            </div>
        </div>
    </div>
	<!-- 弹出高级检索 -->
	<script>
        // 导航栏选定方法
        set_class(".nav .navbox a","on","行业类年鉴");
        <%--$(function(){--%>
            <%--$("#character").click(function () {--%>
                <%--// 人物初始化数据--%>
                <%--var personmapsobj = document.getElementsByName("name");--%>
                <%--<c:forEach items="${personmaps}" begin="0" end="4" varStatus="i" var="subs">--%>
                <%--personmapsobj[${i.index}].checked=true;--%>
                <%--alert("6666"+'${subs.key}');--%>
                <%--tempss.push("${subs.key}");--%>
                <%--</c:forEach>--%>
            <%--})--%>
       <%--})--%>

        
	</script>
	</body>
</html>
