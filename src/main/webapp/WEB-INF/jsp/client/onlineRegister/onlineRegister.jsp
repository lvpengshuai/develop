<%--
  Created by IntelliJ IDEA.
  User: mxp
  Date: 2017/9/28
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<html>
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>
    <title>中国社会科学年鉴数据库</title>


    <!-- 共用 js -->
    <script type="text/javascript" src="${ctx}/static/client/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/client/js/common.js"></script>



    <!-- 0925新增js -->
    <script type="text/javascript" src="${ctx}/static/client/js/common0925.js"></script>

    <!-- 共用css -->
    <link rel="stylesheet" href="${ctx}/static/client/css/style.css" />

    <!-- 新增css0925 -->
    <link rel="stylesheet" href="${ctx}/static/client/css/style0925.css" />

    <link rel="stylesheet" href="${ctx}/static/client/css/alertify.css" />
    <!-- banner切换 -->
    <link rel="stylesheet" href="${ctx}/static/client/css/normalize.css"/>
    <script src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
    <script src="${ctx}/static/client/js/bookyear.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/client/js/alertify.js"></script>

</head>
<body>

<!--顶部-->
<%@include file="/WEB-INF/jsp/client/include/publicTop.jsp" %>
<!--导航-->
<%@include file="/WEB-INF/jsp/client/include/publicNavigation.jsp" %>
<%--登录注册模态--%>
<%@include file="/WEB-INF/jsp/client/include/modal.jsp"%>
<!--中间-->
<div class="usercenter">

    <div class="statue">
        当前位置：<a href="/index">首页</a> &gt;<span>注册</span>
    </div>

    <!-- 注册 -->
    <form id="addUser" method="post">
    <div class="reg_box">
        <h3>注册</h3>
        <dl>
            <dd>
                <div class="texttit"><b>*</b>用户名</div>
                <div class="textinfo">
                    <input class="regtext username"  placeholder="用户名" value="" type="text"  id="username" name="username" >
                    <%--<img src="img/ok.png" alt="正确"></i>--%>
                    <em class="tips">只能由英文、数字、4-20字符组成</em>
                </div>
            </dd>
            <dd>
                <div class="texttit"><b></b>密码</div>
                <div class="textinfo">
                    <input class="regtext pwd" placeholder="" value="" type="password"  id="pwd" name="pwd"  >
                    <%--<i><img src="img/fault.png" alt="错误"></i>--%>
                    <em class="tips">至少6位字符</em>
                </div>
            </dd>
            <dd>
                <div class="texttit"><b></b>确认密码</div>
                <div class="textinfo">
                    <input class="regtext qpassword" placeholder="" value="" id="qpassword" name="qpassword"  type="password">
                    <%--<i><img src="img/fault.png" alt="错误"></i>--%>
                    <em class="tips">再次输入密码</em>
                </div>
            </dd>
            <dd>
                <div class="texttit"><b></b>真实姓名</div>
                <div class="textinfo">
                    <input class="regtext realname" placeholder="请输入姓名" value="" id="realname" name="realname" type="text">
                    <%--<i><img src="img/ok.png" alt="正确"></i>--%>
                    <em class="tips">最多20位字符</em>
                </div>
            </dd>
            <dd>
                <div class="texttit"><b></b>性别</div>
                <div class="textinfo" >
                    <label><input type="radio" name="gender" value="1" checked="checked"  id="RadioGroup2_0">男</label>
                    <label><input type="radio" name="gender" value="0" id="RadioGroup2_1">女</label>
                </div>
            </dd>
            <dd>
                <div class="texttit"><b></b>邮箱</div>
                <div class="textinfo">
                    <input class="regtext email" id="email" name="email" placeholder="" value="" type="text">
                </div>
            </dd>
            <dd>
                <div class="texttit"><b></b>电话</div>
                <div class="textinfo">
                    <input class="regtext telephone" id="telephone" name="telephone" placeholder="" value="" type="text">
                </div>
            </dd>
            <dd>
                <div class="texttit"><b></b>地址</div>
                <div class="textinfo">
                    <input class="regtext" placeholder="" value="" type="text">
                </div>
            </dd>
            <dd>
                <div class="texttit"><b></b>教育程度</div>
                <div class="textinfo">
                    <select size="1" name="education" id="education">
                        <option>高中以下</option>
                        <option>高中</option>
                        <option>大学专科</option>
                        <option>大学本科</option>
                        <option>硕士</option>
                        <option>博士</option>
                        <option>博士以上</option>
                    </select>
                </div>
            </dd>
            <dd>
                <div class="texttit"><b></b>职业</div>
                <div class="textinfo">
                    <%--<select size="1"  name="major" id="major">--%>
                        <%--<option>编辑</option>--%>
                    <%--</select>--%>
                        <input class="regtext" placeholder="" value="" type="text" name="major" id="major">
                </div>
            </dd>
            <dd style="display: none">
                <div class="texttit">&nbsp;</div>
                <div class="textinfo">
                    <input value="" id="box" name="box"  class="box" type="checkbox" checked="checked"> 同意中国社科年鉴数据库注册条款
                </div>
            </dd>
            <dd>
                <div class="texttit">&nbsp;</div>
                <div class="textinfo">
                    <input class="regsub" value="立即注册" type="submit">
                </div>
            </dd>
            <dd>
                <div class="texttit">&nbsp;</div>
                <div class="textinfo">
                    已有账号？
                    <a href="/login">立即登录>></a>
                </div>
            </dd>
        </dl>

    </div>

    </form>


</div>


<script src="${ctx}/static/admin/js/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/static/remark/js/custom/jquery.form.js"></script>
<script type="text/javascript">

    var addUserManager = (function () {

        function init() {

            /*验证账户*/
            $.validator.addMethod("username", function (value, element) {
                var usernameReg = /^[A-Za-z0-9]{4,12}$/;
                var ok =  this.optional(element) || (usernameReg.test(value));
                if (ok){
                    $("#cw").remove();
                  $("#username").after("<img id='cw' src='../../../../static/client/img/ok.png' style='margin-top: 5px' alt='正确'>");
                }else {
                    $("#cw").remove();
                }
                if(value ==''){
                    $("#cw").remove();
                    ok =false;
                };
                return ok;
            }, "<img src='../../../../static/client/img/fault.png'>");
            /*验证密码*/
            $.validator.addMethod("pwd", function (value, element) {
                var passwordReg = /^[a-zA-Z0-9]{6,20}$/;
                var ok = this.optional(element) || (passwordReg.test(value));
                if (ok){
                    $("#pcw").remove();
                    $("#pwd").after("<img id='pcw' src='../../../../static/client/img/ok.png' style='margin-top: 5px' alt='正确'>");
                }else {
                    $("#pcw").remove();
                }
                if(value ==''){
                    $("#pcw").remove();
                    ok = false;
                };
                return ok;
            }, "<img src='../../../../static/client/img/fault.png'>");


            /*验证密码*/
            $.validator.addMethod("qpassword", function (value, element) {
                var qpasswordReg = /^[a-zA-Z0-9]{6,20}$/;
                var ok = this.optional(element) || (qpasswordReg.test(value));
                var pwd = $("#pwd").val();
                if (ok){
                    if(value != pwd){
                        $("#qpcw").remove();
                        ok = false;
                    }else {
                        ok =true;
                        $("#qpcw").remove();
                        $("#qpassword").after("<img id='qpcw' src='../../../../static/client/img/ok.png' style='margin-top: 5px' alt='正确'>");
                    }
                }else {
                    $("#qpcw").remove();
                    ok = false
                }if(value == null || value==""){
                    $("#qpcw").remove();
                    ok = false
                }
                return ok;
            }, "<img src='../../../../static/client/img/fault.png'>");




            /*验证真实姓名*/
            $.validator.addMethod("realname", function (value, element) {
                var realnameReg = /^[A-Za-z\u4e00-\u9fa5]+$/;
                var ok = this.optional(element) || (realnameReg.test(value));

                if (ok){
                    $("#namecw").remove();
                    $("#realname").after("<img id='namecw' src='../../../../static/client/img/ok.png' style='margin-top: 5px' alt='正确'>");
                }else {
                    $("#namecw").remove();
                }
                if(value =="" || value == null){
                    $("#namecw").remove();
                    ok = false;
                };
                return ok;

            }, "<img src='../../../../static/client/img/fault.png'>");





            /*验证邮箱*/
            $.validator.addMethod("email", function (value, element) {
                var emailReg = /\w+((-w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+/;
                var ok = this.optional(element) || (emailReg.test(value));
                if (ok){
                    $("#emailcw").remove();
                    $("#email").after("<img id='emailcw' src='../../../../static/client/img/ok.png' style='margin-top: 5px' alt='正确'>");
                }else {
                    $("#emailcw").remove();
                }
                if(value =="" || value == null){
                    $("#emailcw").remove();
                    ok = false;
                };
                return ok;
            }, "<img src='../../../../static/client/img/fault.png'>");


            /*验证电话*/
            $.validator.addMethod("telephone", function (value, element) {
                var telephoneReg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
                var ok = this.optional(element) || (telephoneReg.test(value));
                if (ok){
                    $("#telcw").remove();
                    $("#telephone").after("<img id='telcw' src='../../../../static/client/img/ok.png' style='margin-top: 5px' alt='正确'>");
                }else {
                    $("#telcw").remove();
                }
                if(value =="" || value == null){
                    $("#telcw").remove();
                    ok = false;
                };
                return ok;
            }, "<img src='../../../../static/client/img/fault.png'>");

            $.validator.addMethod("box", function (value, element) {
                var ok =  (document.getElementById("box").checked);
                if (ok){
                }else {
                    alertify.alert("请勾选同意条框");
                }
                return ok;
            },"");
        }
            $("#addUser").validate({
                submitHandler: function () {
                    var url = "/onlineRegister";
                    var options = {
                        success: showResponse,
                        url: url,
                        dataType: "json",
                        type: "post",
                        clearForm: false,
                        resetForm: false,
                        timeout: 120000
                    };
                    $('#addUser').ajaxSubmit(options);
                    popupDiv('pop-div');
                }
            });

        function showResponse(result) {
            if (result.state == '0') {
                alertify.confirm("注册成功!",function (e) {
                    if(e){
                        window.location.href = "/login";
                    }else{
                        window.location.href = "/login";
                    }
                });
            } else if (result.state == '1') {
                hideDiv('pop-div');
                alertify.alert("'"+result.msg+"'");
//                $("#username").focus();
//                $("#nameTip").html("<em>* " + result.msg + "</em>");
            }
        }

        $("#save").click(function () {
            $("#addUser").submit();
        });

        return {
            init: init
        }

    })();

    addUserManager.init();

</script>
</body>
</html>
