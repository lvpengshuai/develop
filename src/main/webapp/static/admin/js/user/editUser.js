var addUserManager = (function () {

    function init() {
        /*配置相同的值*/
        $.extend($.validator.messages, {
            required: '<span style="font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;">*必填</span>',
            equalTo: "<span  style='font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;'>两次输入的密码不一致</span>",
            minlength: "50",
        });
        /*验证账户*/
        $.validator.addMethod("username", function (value, element) {
            var usernameReg = /^[A-Za-z0-9]{4,12}$/;
            return this.optional(element) || (usernameReg.test(value));
        }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>账户长度为4-12个字符</span>");
        /*验证密码*/
        $.validator.addMethod("pwd", function (value, element) {
            var passwordReg = /^[a-zA-Z0-9]{6,20}$/;
            return this.optional(element) || (passwordReg.test(value));
        }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>密码长度6-20个字符或数字</span>");
        /*验证原始密码*/
        $.validator.addMethod("oldpassword", function (value, element) {
            var passwordReg = /^[a-zA-Z0-9]{6,20}$/;
            return this.optional(element) || (passwordReg.test(value));
        }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>密码长度6-20个字符或数字</span>");

        /*验证真实姓名*/
        $.validator.addMethod("realname", function (value, element) {
            var realnameReg = /^[A-Za-z\u4e00-\u9fa5]+$/;
            return this.optional(element) || (realnameReg.test(value));
        }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>请输入汉字或字母</span>");
        /*验证邮箱*/
        $.validator.addMethod("email", function (value, element) {
            var emailReg = /\w+((-w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+/;
            return this.optional(element) || (emailReg.test(value));
        }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>请输入正确的邮箱</span>");
        /*验证电话*/
        $.validator.addMethod("telephone", function (value, element) {
            var telephoneReg = /(^1[3|4|5|7|8]\d{9}$)|(^\d{3,4}-\d{7,8}$)|(^\d{7,8}$)|(^\d{3,4}-\d{7,8}-\d{1,4}$)|(^\d{7,8}-\d{1,4}$)/;
            return this.optional(element) || (telephoneReg.test(value));
        }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>请输入正确的手机号码</span>");

    }

    $("#addUser").validate({
        submitHandler: function () {
            var url = appPath + "/admin/admin-edit/update";
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
        }
    });
    function showResponse(result) {
        if (result.state == '0') {
            window.location.href = appPath + "/admin/admin-user";
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