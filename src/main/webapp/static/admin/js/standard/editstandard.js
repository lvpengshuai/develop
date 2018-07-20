var EditStandardManager = (function () {

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
    $("#editStandard").validate({
        submitHandler: function () {
            var url = appPath + "/admin/standard/save";
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
            $('#editStandard').ajaxSubmit(options);
        }
    });
    function showResponse(result) {
        if (result.state == 0) {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("保存成功");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
        } else if (result.state == -1) {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("资源不存在");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
        }
    }

    $('#result-modal-btn-false').click(function () {
        window.location.href = appPath + "/admin/standard";
    });
    $("#save").click(function () {
        flag = "1";
        $("#editStandard").submit();
    });
    $("#savepub").click(function () {
        flag = "0";
        $("#editStandard").submit();
    });
    /* 发布日期，实施日期 */
    $("#pubDate,#executeDate").datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        language: 'zh-CN',
        todayHighlight: 1,
        startView: 2,
        forceParse: 1,
        minView: "month",
    });


    return {
        init: init
    }

})();

EditStandardManager.init();