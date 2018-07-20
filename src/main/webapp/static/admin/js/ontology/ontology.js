////上传本体文件
var closeIntervalId;
$("#btn-import-ontology").click(function () {
    $('#ontology-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
});

$("#rdf-btn-upload").click(function () {
    var file = $("#rdf-file")
    if($.trim(file.val())==''){
        return false;
    }

    //隐藏上传框，出现上传进度条modal
    $('#ontology-modal').modal({backdrop: 'static', keyboard: false}).modal('hide');
    $('#progress_upload').modal({backdrop: 'static', keyboard: false}).modal('show');
    var url = appPath + "/admin/ontology/import";
    var options = {
        success: function (result) {
            $("#upload_information_show").hide()
            $("#ontology_upload_button").show()
            if (result.code == 10000) {
                $('#upload_information_complete_show').text("上传成功")
            } else {
                $('#upload_information_complete_show').attr("style","color:red")
                $('#upload_information_complete_show').text("上传失败，请检查文件格式是否正确")
            }
        },
        dataType: "json",
        url: url,
        type: "post",
        clearForm: true,
        resetForm: true,
        timeout: 60000
    };
    $("#rdf-form").ajaxSubmit(options);
    closeIntervalId = setInterval(getProgress, 1000);
    $("#progress_upload_info").attr("closeIntervalId", closeIntervalId);
});

function getProgress() {
    $("#ontology_upload_button").hide()
    var status = false;
    $.ajax({
        url: appPath + "/admin/progress",
        dataType: 'json',
        type: 'post',
        cache:false,
        success: function (result) {
            $("#progress_upload_info").text(result.status)
            $("#progress_upload_scroll").css("width", result.status);
            if (result.status == "100%") {
                clearInterval(closeIntervalId);
                $('#upload_information').modal({backdrop: 'static', keyboard: false}).modal('show');
                $("#progress_upload").modal({backdrop: 'static', keyboard: false}).modal('hide');
                $("#upload_information_show").text("文件已经上传至服务器,正在处理，请稍后...")
                status = true;

            }

        }
    })
    return status;
}
function refreshPage(){
    window.location.reload()
}