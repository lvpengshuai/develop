var LoginManager = (function () {
    function init() {

        // 配置相同的值
        $.extend($.validator.messages, {
            required: '<span style="font-size: 12px;color:red;">*必填</span>',
            minlength:"50",
        });

        // 验证账号
        $.validator.addMethod("username", function (value, element) {
            $("#checkValue").text("");
            var roleNameReg=/^[A-Za-z0-9]{5,15}$/;
            return this.optional(element) || (roleNameReg.test(value));
        }, "<span  style='font-size: 12px;color:red;'>*格式非法</span>");

        // 验证密码
        $.validator.addMethod("pwd", function (value, element) {
            var remarkReg=/^(\w){6,20}$/;
            return this.optional(element) || (remarkReg.test(value));
        }, "<span  style='font-size: 12px;color:red;'>*格式非法</span>");
    }

    function showResponse(result){
        if(result.code == 0){
            window.location.href = appPath+'/admin/home';
        }else if(result.code == 1){
            $("#checkValue").text("*用户名或者密码错误");
        }else if(result.code==2) {
            $("#checkValue").text("*用户被禁用，请联系管理员");
        }
    }
    $("#login").validate({
        submitHandler: function () {
            var url = appPath+"/admin/login";
            var options = {
                // beforeSubmit: checkForm,
                success: showResponse,
                url: url,
                type: "post",
                dataType: "json",
                clearForm: false,
                resetForm: false,
                timeout: 120000
            };
            $('#login').ajaxSubmit(options);
        }
    });

    $('#submit').click(function () {
        $("#checkValue").text("");
        $("#login").submit();

    });

    $(document).keydown(function () {
        if(event.keyCode==13) {
            $("#checkValue").text("");
            $("#login").submit();
        }
    });

    return {
        init: init,
    }
})();

LoginManager.init();