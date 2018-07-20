var resetPassWordManager = (function () {

    function init() {
        /*配置相同的值*/
        $.extend($.validator.messages, {
            required: '<span style="font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;">*必填</span>',
            equalTo: "<span  style='font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;'>两次输入的密码不一致</span>",
            minlength: "50",
        });
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

    }

    /*重置密码开始*/
    $("#addUser").validate({
        submitHandler: function () {
            var url = appPath + "/admin/admin-user/resetpassword";
            var options = {
                success: showResponse,
                url: url,
                dataType: "json",
                type: "post",
                clearForm: true,
                resetForm: true,
                timeout: 120000
            };
            $('#addUser').ajaxSubmit(options);
        }
    });
    function showResponse(result) {
        if (result.code == '0') {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("密码重置成功！");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
        }else if(result.code == "-1"){
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("您输入的旧密码与原始密码不相同！");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
        }else{
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("密码重置失败！");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
        }
    }

    $("#resetpassword_modal_btn_true").click(function () {
        $("#addUser").submit();
    });
    /*重置密码结束*/

    return {
        init: init
    }

})();

resetPassWordManager.init();