var EditBookManager = (function () {

    function init() {
        /*配置相同的值*/
        $.extend($.validator.messages, {
            required: '<span style="font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;">*必填</span>',
            equalTo: "<span  style='font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;'>两次输入的密码不一致</span>",
            minlength: "50",
        });
        /*验证*/
        // $.validator.addMethod("realname", function (value, element) {
        //     var realnameReg=/^[A-Za-z\u4e00-\u9fa5]+$/;
        //     return this.optional(element) || (realnameReg.test(value));
        // }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>请输入汉字或字母</span>");

    }


    var flag;
    $("#editBook").validate({
        submitHandler: function () {
            var url = appPath + "/admin/book/save";

            var options = {
                success: showResponse,
                url: url,
                data: {"flag": flag},
                dataType: "json",
                type: "post",
                clearForm: false,
                resetForm: false,
                timeout: 120000
            };
            $('#editBook').ajaxSubmit(options);
        }
    });
    function showResponse(result) {
        if (result.state == '0') {
            alert("修改成功!");
            window.location.href = appPath + "/admin/book";
        }
    }

    $("#save").click(function () {
        flag = "1";
        $("#editBook").submit();
    });
    $("#savepub").click(function () {
        flag = "0";
        $("#editBook").submit();
    });

    return {
        init: init
    }

})();

EditBookManager.init();