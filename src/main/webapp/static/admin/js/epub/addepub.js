function uploadFile(){
    var fileObj = document.getElementById("file_input").files[0]; // 获取文件对象
    var FileController = "entityServlet1"; // 接收上传文件的后台地址

    if(fileObj){
        // FormData 对象
        var form = new FormData();
        form.append("multipartFile", fileObj);// 文件对象

        // XMLHttpRequest 对象
        // var xhr = new XMLHttpRequest();
        // xhr.open("post", FileController, true);
        // xhr.onload = function () {
        //     alert(xhr.responseText);
        // };
        // xhr.send(form);

        $.ajax({
            url: appPath + "/admin/uploadResourceFile",
            type: 'post',
            data: {
                multipartFile: form,
            },
            success: function (data) {
                var code = data.status;
                if (code ==1) {
                    alert("修改失败");
                } else if (code == 0) {
                    alert("修改成功");
                    clearInput();
                }
            }

        });

    }else{
        alert("未选择文件");
    }
}
/*检查年鉴*/
$('#btn-check').click(function () {
    // alert(1);
    // window.location.href = appPath + '/admin/role/add';
    $("#check-area").append("正在检查需要解析文件，请稍后。。。。。。。。。。");
    // $("#btn-check").attr("disabled",true);
    $('#btn-check').attr("disabled","disabled");
    $.ajax({
        url: appPath + "/admin/source-check",
        type: 'post',
        // data: {
        //     multipartFile: form,
        // },
        success: function (data) {
            var code = data.status;
            if (code ==1) {
                $("#check-area").empty();
                $("#check-area").append("请检查上传资源是否上传！！！！！！！！");j
            } else {
                $("#check-area").empty();
                for(var i =0;i<code.length;i++){
                    if(i == 0){
                        value = code[i];
                        var name=value.split("file\\")[1];
                        $("#check-area").append("<div>"+name+"</div>");
                        $('#btn-resolve').removeAttr("disabled");
                        $('#btn-refresh').removeAttr("disabled");
                    }else {
                        value = code[i];
                        var name=value.split("file\\")[1];
                        $("#check-area").append("<div>"+name+"</div>");
                        $('#btn-resolve').removeAttr("disabled");
                        $('#btn-refresh').removeAttr("disabled");
                    }
                }

            }
        }

    });
});

/*解析年鉴*/
$('#btn-resolve').click(function () {
    // alert(1);
    // window.location.href = appPath + '/admin/role/add';
    $("#jiexi-area").empty();
    $('#btn-resolve').attr("disabled","disabled");
    $("#jiexi-area").append("解析中，请稍后，或点击刷新查看解析进度！！！！！！！！");
    $("#check-area").empty();
    $.ajax({
        url: appPath + "/admin/source-resolve",
        type: 'post',
        // data: {
        //     multipartFile: form,
        // },
        success: function (data) {
            var code = data.status;
            if (code ==0) {
                $("#jiexi-area").append("请检查上传资源是否上传！！！！！！！！");
            }if(code == 1){
                alert("上传完成！");
            }else if(code == 2){
                alert("上传失败！");
            }
        }

    });
});
/*刷新年鉴*/
$('#btn-refresh').click(function () {
    // alert(1);
    // window.location.href = appPath + '/admin/role/add';
    // $("#jiexi-area").append("解析中，请稍后，或点击刷新查看解析进度！！！！！！！！");
    $("#jiexi-area").empty();
    $.ajax({
        url: appPath + "/admin/source-refresh",
        type: 'post',
        // data: {
        //     multipartFile: form,
        // },
        success: function (data) {
            var code = data.log;
            if (code == 1) {
                $("#jiexi-area").append("请再次刷新！！！！");
            }else{
                for(var i =0;i<code.length;i++){
                        value = code[i];
                        $("#jiexi-area").append("<div>"+value+"</div>");
                }
            }
        }

    });
    $.ajax({
        url: appPath + "/admin/source-download",
        type: 'post',
        // data: {
        //     multipartFile: form,
        // },
        success: function (data) {
                var code = data.log;
                for(var i =0;i<code.length;i++){
                    value = code[i];
                    var name=value.split("forever\\")[1];
                    $("#download-log").empty();
                    $("#download-log").append("<option>"+name+"</option>");
                    $("#download-area").show();
                }
        }

    });
});
/*下载文件*/
$('#btn-download').click(function () {
    // alert(1);
    // window.location.href = appPath + '/admin/role/add';
    // $("#jiexi-area").empty();
    // $('#btn-resolve').attr("disabled","disabled");
    // $("#jiexi-area").append("解析中，请稍后，或点击刷新查看解析进度！！！！！！！！");
    // $("#check-area").empty();


    location.href="/admin/source-downloadLog";

    // $.ajax({
    //     url: appPath + "/admin/source-downloadLog",
    //     type: 'post',
    //     // data: {
    //     //     multipartFile: form,
    //     // },
    //     success: function (data) {
    //         var code = data.status;
    //         if (code ==0) {
    //             alert("文件下载失败！！");
    //         }if(code == 1){
    //             alert("文件已经下载到桌面！！！");
    //         }
    //     }

   // });
});
$(function(){
    $("#download-area").hide(); // 默认隐藏下拉框
    // $(":button").click(function() {
    //     $("select").toggle();  // 已经显示则隐藏，隐藏了则显示
    //     $(this).val($(this).val()=="显示"?"隐藏":"显示");
    // })
})