<%--
  Created by IntelliJ IDEA.
  User: epro
  Date: 2017/8/23
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.trs.core.util.IPUtil" %>
<input  type="hidden" id="iiiPP" value="<%= IPUtil.getServerIp() %>">

<!-- 0925新增js -->
<script type="text/javascript" src="${ctx}/static/client/js/common0925.js"></script>
<link rel="stylesheet" href="${ctx}/static/client/css/style0925.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/client/customer/search.js"></script>
<!-- 新顶部 -->
<div class="top" >
    <div class="wrapin">
        <div class="tophome"><a href="${ctx}/index"><img src="${ctx}/static/client/img/home_top.png"></a>学术殿堂之重器&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学术历史之奠基</div>
        <ul class="top-list" id="top-list">
                <%--<c:if test="${userName == null}">--%>
                    <%--<span style="color: #6a6a6a;font-weight: 600;">欢迎来到社会科学年鉴数据库</span>--%>
                    <%--<a href="javascript:void(0)" onclick=" window.location.href = encodeURI('${ctx}/login?SKT='+window.location.pathname+window.location.search+'')" class="logout"> [请登录]</a>--%>
                <%--</c:if>--%>
                <%--<c:if test="${userName != null}">--%>
                    <%--<li>--%>
                            <%--<a>${userName}</a>--%>
                            <%--<a href="javascript:void(0)" onclick="window.location.href=encodeURI('${ctx}/logout?SKT='+window.location.pathname+window.location.search+'')" class="logout">[退出]</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                            <%--<a href="${ctx}/user/center">个人中心</a>--%>
                    <%--</li>--%>
                <%--</c:if>--%>
        </ul>
    </div>
</div>
<div class="head" >
    <div class="wrapin logo_box">
        <div class="logo fl hand"><a href="${ctx}/index"><img src="${ctx}/static/client/img/logo.png"></a></div>
        <form action="${pageContext.request.contextPath}/search" id="search" method="get">
            <ul class="head-list">
                <%--<li><a href="#">词条</a></li>--%>
                <li><a href="${ctx}/book/booksList" class="list_nj" target="_blank">年鉴</a></li>
                <li><a href="${ctx}/dataCenter" class="list_tb" target="_blank">图表</a></li>
            </ul>
            <div class="search_box">
                <div class="seach-chose">
                    <strong>
                        <c:choose>
                            <c:when test="${type=='nj'}">年鉴</c:when>
                            <c:when test="${type=='wz'}">章节</c:when>
                            <c:when test="${type=='rw'}">人物</c:when>
                            <c:when test="${type=='lw'}">论文</c:when>
                            <c:when test="${type=='kt'}">课题</c:when>
                            <c:when test="${type=='hy'}">会议</c:when>
                            <c:when test="${type=='ts'}">图书</c:when>
                            <c:when test="${type=='dsj'}">大事记</c:when>
                            <c:otherwise>年鉴</c:otherwise>
                        </c:choose>
                    </strong>
                    <dl>
                        <dd onclick="setButtonValue('nj')" >年鉴</dd>
                        <dd onclick="setButtonValue('wz')" >章节</dd>
                        <dd onclick="setButtonValue('rw')" >人物</dd>
                        <dd onclick="setButtonValue('lw')" >论文</dd>
                        <dd onclick="setButtonValue('kt')" >课题</dd>
                        <dd onclick="setButtonValue('hy')" >会议</dd>
                        <dd onclick="setButtonValue('ts')" >图书</dd>
                        <dd onclick="setButtonValue('dsj')" >大事记</dd>
                    </dl>
                </div>
                <input name="" type="button" class="btn_search"  onclick="submitsearch()" />
                <input placeholder="请输入关键字" name="kw" type="text" value="${keyWord}" style="width: 378px" id="kw"/>
                <input id="code" name="tp" type="hidden" value="${type==''?'wz':type}">
                <input id="cttype" name="cttype" type="hidden" value="${cttype}">
            </div>
        </form>
        <p class="advanced_search"><a class="click-highsearch">高级搜索</a></p>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $.ajax({
            url:  "/organize/loginSign",
            type: 'post',
            success: function (data) {
                var code = data.loginSign;
                var a=null;
                var aa=null;
                var b=null;
                // 机构ip内 未登录
                if(code == 1){
                     a= "<span style='color: #6a6a6a;font-weight: 600;'>"+data.org+"，欢迎您！</span>";
                     aa="<a class='logout' onclick='loginURL()'>【登录】</a><a class='logout' onclick='RegisterURL()'>【注册】</a> ";
                     b=a+aa;
                    $("#top-list").html(b);
                }if (code == 2){
                     a= "<span style='color: #6a6a6a;font-weight: 600;'>欢迎来到社会科学年鉴数据库</span>";
                     aa="<a class='logout' onclick='loginURL()'>【登录】</a><a class='logout' onclick='RegisterURL()'>【注册】</a> ";
                     b=a+aa;
                    $("#top-list").html(b);
                }if(code == 3){
                    a= "<li>"+data.org+"，${userName}，欢迎您！<a class='logout' onclick='loginoutURL()')"+" >【退出】</a></li>";
                    aa="<li><a href='${ctx}/user/center'>个人中心</a></li>";
                    b=a+aa;
                    $("#top-list").html(b);
                }if(code == 4){
                    a= "<li>${userName}，欢迎您！<a class='logout' onclick='loginoutURL()')"+" >【退出】</a></li>";
                    aa="<li><a href='${ctx}/user/center'>个人中心</a></li>";
                    b=a+aa;
                    $("#top-list").html(b);
                }
            }

        });


    });

    var idData = document.getElementsByName('titleID');
    function loginURL() {
        //获取zid
        var id = document.getElementsByName('titleID');
        //zid值
        if (id.length > 0) {
            var value = [];
            var zid = "";
            var bookcode = "";
            // title值
            var title = "";
            for (var i = 0; i < id.length; i++) {
                if (id[i].checked) {
                    value.push(id[i].value);
                    var vl = id[i].value.split("|");
                    title = title + vl[1] + "+";
                    zid = zid + vl[0] + ";";
                    bookcode = bookcode + vl[2] + ";";
                }
            }
            zid = zid.substring(0, zid.length - 1);
            title = title.substring(0, title.length - 1);
            bookcode = bookcode.substring(0, bookcode.length - 1);
            if (zid == "" || bookcode == "") {
                window.location.href = encodeURI("/login?SKT=" + window.location.pathname + window.location.search);
            } else {
                window.open(encodeURI("/login?SKT=/article/articleInfo?zid=" + zid + "&bookcode=" + bookcode), "_blank");
            }
        } else {
            window.location.href = encodeURI("/login?SKT=" + window.location.pathname + window.location.search);
        }

    }
    function RegisterURL() {
        window.location.href = encodeURI("/onlineRegister");
    }
    function loginoutURL() {
        window.location.href= encodeURI('${ctx}/logout?SKT='+window.location.pathname+window.location.search);
    }
</script>
<!-- 弹出高级检索 -->
<%@include file="/WEB-INF/jsp/client/include/advancesearch.jsp" %>
