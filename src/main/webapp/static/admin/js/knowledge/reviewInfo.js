function showReviewInfo(id, name) {
    $('#knowledge_info').modal({backdrop: 'static', keyboard: false}).modal('show');
    $('#knowledge_review_info').attr("name", name);
    $('#knowledge_review_info').attr("remark", id);
    $.ajax({
        url: appPath + '/admin/knowledge/review/info',
        dataType: 'json',
        type: 'get',
        data: {'id': id},
        success: function (result) {
            if (result.code == 10000) {
                $('#review-k-go').empty();
                $('#review-m-go').empty();
                $('#review-info-user-tickling').empty();
                $('#review-k-go').append(" <p class='review-text'>" + result.msg.knowledgeName + "</p>");
                $('#review-m-go').append(" <p class='review-text'>" + result.msg.lcoation + "</p>");
                $('#review-info-user-tickling').append("<p class='review-text' style=' word-wrap:break-word; word-break:normal;'>" + result.msg.text + "</p>");
            }
        }
    })
}
$("#review-k-go,#review-m-go").on("click", function () {
    var name = $('#knowledge_review_info').attr("name").toString()
    window.open(appPath + '/knowledge/' + name);
})

$("#review-info-examine").on("click", function () {
    var id = $('#knowledge_review_info').attr("remark");
    var ids = new Array();
    ids.push(id);
    $('#knowledge_info').modal({backdrop: 'static', keyboard: false}).modal('hide');
    $.ajax({
        url: appPath + '/admin/knowledge/review/examine',
        dataType: 'json',
        type: 'get',
        data: {"id": ids, "state": 1},
        success: function (result) {
            console.log("执行成功" + result);
            $('#book_table').bootstrapTable('refresh');
            // window.location.reload();//刷新当前页面.
        }
    })
})