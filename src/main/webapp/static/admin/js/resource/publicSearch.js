$(function () {
    $(".search").css("width", "630px");
    $(".search input").css("width", "33%");
    $(".search input").css("display", "inline");
    $(".search input").attr("placeholder", "搜索标题");
    $(".search").prepend("<span style='width: 20px;display: inline-block'></span>");
    $(".search").prepend('<input class="form-control" type="text" placeholder="搜索年份" style="width: 30%;display: inline" id="yearEntry" oninput="yearEntry()" onpropertychange="yearEntry()" value="">');
    $(".search").prepend("<span style='width: 20px;display: inline-block'></span>");
    $(".search").prepend('<input class="form-control" type="text" placeholder="搜索年鉴" style="width: 30%;display: inline" id="nameEntry" oninput="nameEntry()" onpropertychange="nameEntry()" value="">');
})

function nameEntry() {
    $('#user_table').bootstrapTable('refresh');
}

function yearEntry() {
    $('#user_table').bootstrapTable('refresh');
}
