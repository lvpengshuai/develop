$(function () {
    getmeeting($("#bookyear").val(), 0, '会议词条', bookcode);
    $(".monthlist dd").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
    })

})


function getmeeting(year, month, type, bookcode) {
    $.ajax({
        url: appPath + "/book/meeting",
        data: {'year': year, 'month': month, 'type': type, 'bookcode': bookcode},
        success: function (data) {
            $("#meeting").html(data)
        }
    })
}

function getmettingAll(year, bookcode) {
    $("#monthss").children().siblings().removeClass("active");
    getmeeting(year, 0, '会议词条', bookcode);
}
