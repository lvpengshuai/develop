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
	<link rel="stylesheet" href="${ctx}/static/client/css/alertify.css" />
	<!-- banner切换 -->
	<link rel="stylesheet" href="${ctx}/static/client/css/normalize.css" />
	<script src="${ctx}/static/client/js/jquery.flexslider-min.js"></script>
	<script src="${ctx}/static/client/js/demo.js"></script>
	<link rel="stylesheet" href="${ctx}/static/client/css/alertify.css"/>
	<script type="text/javascript" src="${ctx}/static/client/js/alertify.js"></script>

</head>
<body>
		<!--顶部-->
		<%@include file="/WEB-INF/jsp/client/include/publicTop.jsp" %>
		<!--导航-->
		<%@include file="/WEB-INF/jsp/client/include/publicNavigation.jsp" %>
  	  	<!-- banner -->
		<%@include file="/WEB-INF/jsp/client/include/publicBanner.jsp" %>
		<!--中间-->
		<div class="usercenter">
        
        	<div class="statue">
                当前位置：<a href="/index">首页</a> &gt;<span>找回密码</span>
            </div>
        
        	<div class="passwordbox">
				
                <h2><img src="${ctx}/static/client/img/pwdtext.jpg" /></h2>
            	<dl class="base_form">
                    <dd><span>Email：</span><input class="logtext" id="logtext" value="" type="text" placeholder=""></dd>
                    <%--<dd><span>姓名：</span><input class="logtext" id="logtext1" value="" type="text" placeholder=""></dd>--%>
                    <dd><span>&nbsp;</span><input class="user_pwd_sub" id="submit" value="提交申请" type="submit"></dd>
                    <dd>请留下您的基本信息并提交申请，管理员将尽快为您重置密码</dd>
                </dl>
                    
            </div>

        
		</div>
		<!-- 底部 -->
		<%@include file="/WEB-INF/jsp/client/include/publicFoot.jsp" %>
		<!-- 弹出高级检索 -->
		<script>
            // 导航栏选定方法
            set_class(".nav .navbox a","on","行业类年鉴");
            /*提交申请*/
            $("#submit").click(function () {
                var logtext = $("#logtext").val();
                var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                var logtext1 = $("#logtext1").val();
                if (logtext == null || logtext == ""){
                    alertify.alert("请填写邮箱");
                    return;
                }
                if (logtext != null && logtext !=""){
                    var zz = reg.test(logtext);
                    if (zz){
                        $.ajax({
                            url:"/repassword",
                            type:"get",
                            data:{
                                usermail:logtext,
                            },
                            dataType:"json",
                            async:false,
                            success:function (data) {
                                if (data.status == 0){
                                    alertify.alert("邮箱已发送成功请您注意查收！");
                                }else{
                                    alertify.alert(data.msg);
                                }
                            }
                        })
					}else {
                        alertify.alert("邮箱格式有误");
					}
				}

            })
		</script>
</body>
</html>
