$(function () {

    // 配置表单验证基本验证
    $.extend($.validator.messages, {
        required: '<span class="valid_message" style="font-size: 12px;">必填</span>',
        equalTo: '<span class="valid_message" style="font-size: 12px;">两次输入的密码不一致</span>',
        minlength: "50",
    });

    /*验证账户名称（支持数字、字母、下划线，下划线不能开头的组合4-20个字符）*/
    $.validator.addMethod("userName", function (value, element) {
        var roleNameReg = /^(?![0-9_])(?!.*?_$)[a-zA-Z0-9_]{4,20}$/;
        $("#userNameValue").html('');
        return this.optional(element) || (roleNameReg.test(value));
    }, "<span class='valid_message'>支持字母数字下划线,下划线不能开头和结尾，数字不能开头，4-20字符</span>");

    /* 验证密码 */
    $.validator.addMethod("pwd", function (value, element) {
        var roleNameReg =  /^[a-zA-Z0-9_.\[\]]{6,20}$/;
        //$("#realNameTip").html('');
        return this.optional(element) || (roleNameReg.test(value));
    }, "<span class='valid_message'>数字、字母、下划线（6-20个字符）</span>");

    /* 验证邮箱 */
    $.validator.addMethod("email", function (value, element) {
        var roleNameReg =  /(^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$)/;
        //$("#realNameTip").html('');
        return this.optional(element) || (roleNameReg.test(value));
    }, "<span class='valid_message'>请输入正确的邮箱格式</span>");

    /* 验证手机号 */
    $.validator.addMethod("telephone", function (value, element) {
        var roleNameReg =  /^0?(13|14|15|17|18)[0-9]{9}$/;
        //$("#realNameTip").html('');
        return this.optional(element) || (roleNameReg.test(value));
    }, "<span class='valid_message'>请输入正确的手机号</span>");

    /* 验证所在单位 */
    $.validator.addMethod("organization", function (value, element) {
        var roleNameReg =  /^[\u4e00-\u9fa5A-Za-z0-9()（）.]{0,50}$/
        //$("#realNameTip").html('');
        return this.optional(element) || (roleNameReg.test(value));
    }, "<span class='valid_message'>数字、字母（0-50个字符）</span>");

    $("#updateInfo").validate({
        showErrors : function(errorMap, errorList) {
            // 遍历错误列表
            for(var obj in errorMap) {
                // 自定义错误提示效果
                $('.' + obj).text('');
            }
            // 此处注意，一定要调用默认方法，这样保证提示消息的默认效果
            this.defaultShowErrors();
        },
        submitHandler: function () {
            var url = appPath + "/register";
            var options = {
                success: showResponse,
                url: url,
                dataType: "json",
                type: "post",
                clearForm: false,
                resetForm: false,
                timeout: 120000
            };
            $("#updateInfo").ajaxSubmit(options);
        }
});

    $(document).keydown(function () {
        if(event.keyCode==13) {
            $(".username").text("");
            $("#code").text("");
            // 判断协议是否选中
            if($("#tmp").is(":checked")){
                $("#bokValue").text("");
                $("#bokValue").css({"background-image":"","background-repeat":"no-repeat","background-position":"left center"});
                $("#updateInfo").submit();
            }else {
                $("#bokValue").text("请选中协议");
                $("#bokValue").css({"background-image":"url(../../static/client/images/message-error.png)","background-repeat":"no-repeat","background-position":"left center"});
            }
        }
    });


    //alert("提交成功");
    function showResponse(result) {
        if(result.code == 0){
            $(".username").text(result.msg);
            return false;
        }else if(result.code == -1){
            $(".code").text(result.msg);
            return false;
        }else if(result.code == 1){
            //window.location.href = appPath+"/login";
            layer.open({
             type: 1 //Page层类型
             ,
                closeBtn : 0,
             area: ['400px', '230px']
             ,
             title: '提示'
             ,
             shade: 0.4 //遮罩透明度
             ,
             anim: -1 //0-6的动画形式，-1不开启
             ,
             content: '<a href="'+appPath+'/login"><div style="padding:40px; text-align:center;"><p style="margin-bottom: 20px"><img src="' + appPath + 'static/client/images/update-sucess.png"/></p><p class="update-sucess" style="font-size: 12px">前往登录</p></div></a>'
             });
        }else if(result.code == -2){
            $("#email-tip").text(result.msg);
        }

    }
    $("#regUser").click(function () {
        window.location.href = appPath+"/login";
    });

    /* 注册用户 */
    $("#submitPinfo").click(function () {
        // 判断协议是否选中
        if($("#tmp").is(":checked")){
            $("#bokValue").text("");
            $("#bokValue").css({"background-image":"","background-repeat":"no-repeat","background-position":"left center"});
            $("#updateInfo").submit();
        }else {
            $("#bokValue").text("请选中协议");
            $("#bokValue").css({"background-image":"url(../../static/client/images/message-error.png)","background-repeat":"no-repeat","background-position":"left center"});
        }

    });

    /* 验证码获得焦点 */
    $("#codeCheck").focus(function () {
        $(".code").text("");
    });
    /* 用户名获得焦点 */
    $("#user_name").focus(function () {
        $(".username").text("");
    });
    /* 邮箱获得焦点 */
    $(".email").focus(function () {
        $("#email-tip").text("");
    });
    /* 判断是否选中协议 */
    $("#tmp").click(function () {
        if($("#tmp").is(":checked")){
            $("#bokValue").text("");
            $("#bokValue").css({"background-image":"","background-repeat":"no-repeat","background-position":"left center"});
            //$("#updateInfo").submit();
        }else {
            $("#bokValue").text("请选中协议");
            $("#bokValue").css({"background-image":"url(../../static/client/images/message-error.png)","background-repeat":"no-repeat","background-position":"left center"});
        }
    });
});






