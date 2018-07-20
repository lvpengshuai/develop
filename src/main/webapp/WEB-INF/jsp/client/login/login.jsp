<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<%@ page import="com.trs.core.util.IPUtil" %>
<input  type="hidden" id="iiiPP" value="<%= IPUtil.getServerIp() %>">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>中国社会科学年鉴数据库</title>
<!-- 共用 js -->
<script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>
<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/client/customer/publicJS.js"></script>
<script src="${ctx}/static/client/customer/login.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/plugin/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/customer/css/login/custominput.js"></script>
    <link rel="stylesheet" href="${ctx}/static/client/css/alertify.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/alertify.js"></script>
    <script>
        var appPath = "${pageContext.request.contextPath}";
        var  url="${urlG}";
    </script>

    <style>
        .regirect{
            color: blue;
        }
        .regirect:hover {
            color: red;
        }
        .wrapin{
            top: 30px;
        }
    </style>

<!-- 共用css -->
<link rel="stylesheet" href="${ctx}/static/client/css/style.css" />
</head>
<body>
<%--登录注册模态--%>
<%@include file="/WEB-INF/jsp/client/include/modal.jsp"%>
	<div class="loginbg">
            
        <div class="login_box" style="margin-top: 50px;height: 540px;">
            <dl class="log">
                <form id="subForm" method="post">
                    <dd>
                        <label for="userName" >
                            <em><img src="${ctx}/static/client/img/login_1.png" alt="" /></em>
                        </label>
                        <input id="username" class="logtext" type="text"  name="userName" value="" placeholder="请输入用户名" required/>
                        <span id="userNameValue" class="valid_message" style="font-size: 12px;">${info}</span>
                    </dd>
                    <dd>
                        <label for="password">
                            <em><img src="${ctx}/static/client/img/login_2.png" alt="" /></em>
                        </label>
                        <input id="password" class="logtext"  type="password" for="password"  value="" placeholder="密码"  />
                    </dd>

                    <input type="hidden" value="${path}" id="path">

                    <dd class="dd03"><em><img src="${ctx}/static/client/img/login_3.png" alt="" /></em>
                        <input type="text" name="code" id="checkCode"  class="logtext logyzm2" type="text" value="" placeholder="验证码"  />
                        <span id="code" class="valid_message" style="font-size: 12px;"></span>
                    </dd>
                    <dd class="dd04">
                        <img src="${pageContext.request.contextPath}/imagecode" id="imageCode" class="yzm_img" onclick="loginManager.reloadcode()" class="yzm_img">
                    </dd>
                    <dd class="dd01">
                        <c:if test="${online == 'ON'}">
                        <a href="${ctx}/view/repassword" target="_blank" class="fr">忘记密码？</a>
                        </c:if>
                        <label><input id="flag" name="automaticLogon" type="checkbox" value="1">下次自动登录</label>
                    </dd>
                    <dd class="dd05">
                        <input class="logsub" type="button" id="submit" value="登录" onclick="loginSUB()" />

                    </dd>
                    <c:if test="${online == 'ON'}">
                        <a href="${ctx}/onlineRegister" class="regirect" style="float: right;">立即注册>></a>
                    </c:if>
                </form>
            </dl>
          </div>
          <!-- 底部 -->
        <%@include file="/WEB-INF/jsp/client/include/publicFoot.jsp" %>
      </div>
    <script src="${pageContext.request.contextPath}/static/admin/js/jquery/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/remark/js/custom/jquery.form.js"></script>
</body>
<script type="text/javascript">
    function isRightCode(){
        var code =document.getElementById("checkCode").value;
        code = "c=" + code;
        <%--$.ajax({--%>
            <%--type:"POST",--%>
            <%--url:"${ctx}/validateCode",--%>
            <%--data:code,--%>
            <%--success:function(data){--%>
                <%--// 返回结果  1是验证码正确 ， 0是验证码错误--%>
<%--//                    alert(data);--%>
                <%--if(data==1){// 成功--%>
                    <%--imgRN=1;--%>
                <%--}else{--%>
                    <%--imgRN=0;--%>
                <%--}--%>
            <%--}--%>
        <%--});--%>
    }
    $(function () {
        $("#flag").click(function () {
            if ($(this).is(":checked")) {
                $(this).val(0);
            } else {
                $(this).val(1);
            }
        })
    })
</script>

</html>
