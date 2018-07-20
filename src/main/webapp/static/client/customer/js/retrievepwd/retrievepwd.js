/**
 * Created by zly on 2017-5-18.
 */
$(function () {
    // 配置表单验证基本验证
    $.extend($.validator.messages, {
        required: '<span class="valid_message" style="font-size: 12px;">必填</span>',
        equalTo: '<span class="valid_message" style="font-size: 12px;">两次输入的密码不一致</span>',
        minlength: "50",
    });

    /* 验证邮箱 */
    $.validator.addMethod("email", function (value, element) {
        var roleNameReg =  /(^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$)/;
        //$("#realNameTip").html('');
        return this.optional(element) || (roleNameReg.test(value));
    }, "<span class='valid_message'>请输入正确的邮箱格式</span>");

    $("#retrievepwd_from").validate({
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
            var url = appPath + "/retrievepwd/sendemail";
            var options = {
                success: function (result) {

                    if(result.status == '0'){
                        debugger
                        window.location.href = appPath +"/retrievepwd/confirmemail?email="+result.email;
                    }else if(result.status == '1') {
                        /*$(".form_control:first").removeClass("success");
                        $(".form_control:first").addClass("error");*/
                        $("#email-tip").text(result.msg)
                    }else if(result.status == '2') {
                        /*$(".form_control:last").removeClass("success");
                        $(".form_control:last").addClass("error");*/
                        $("#code-tip").text(result.msg)
                    }
                },
                url: url,
                dataType: "json",
                type: "post",
                clearForm: false,
                resetForm: false,
                timeout: 120000
            };
            $("#retrievepwd_from").ajaxSubmit(options);
        }
    });

    /* 获得焦点 */
    $("#email-show").focus(function () {
        //$(".form_control:last").addClass("success");
        $("#email-tip").text("");
    });
    $("#checkcode").focus(function () {
        //$(".form_control:last").addClass("success");
        $("#code-tip").text("");
    });

    $("#ret_btn").click(function () {
        $("#retrievepwd_from").submit();
    });

    $(document).keydown(function () {
        if(event.keyCode==13) {
            $("#retrievepwd_from").submit();
        }
    });





});
