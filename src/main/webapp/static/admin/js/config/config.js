/**
 * Created by zly on 2017-6-15.
 */
$(function () {

    $("#save").click(function () {
        $.ajax({
            url: appPath + "/admin/setting",
            type: "post",
            data: {
                "book": $("#book").val(),
                "journal": $("#journal").val(),
                "standard": $("#standard").val()
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 1) {
                    alert("修改成功");
                    window.location.reload();
                } else {
                    alert("修改失败");
                }
            }
        });

    });
});