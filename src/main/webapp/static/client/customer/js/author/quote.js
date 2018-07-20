$(".module-title-cite").click(function () {
    var type = $(this).parent().parent().find("i").attr("class")
    $(".layer-pub-main").children().hide()
    $(".smallInput").show()
    if (type == "type-icon2") {
        //期刊
        $("#quote-journal").show()
        var journalName = $(this).parent().parent().find(".pro-info").find(".journal-name").text()
        var journalArticleName = $(this).parent().parent().find(".pro-info").find(".journal-article-name").text()
        var journalAuthor = $(this).parent().parent().find(".pro-info").find(".journal-author").text()
        var journalDate = $(this).parent().parent().find(".pro-info").find(".journal-year").text()
        var text = "[1]" + " " + journalAuthor + "." + journalArticleName + "[J]." + journalName + "," + journalDate
        $(".smallInput").html(text)
    }
    if (type == "type-icon1") {
        //书
        $("#quote-book").show()
        var bookName = $(this).parent().parent().find(".pro-info").find("h2").children().attr("title")
        var bookZone = $(this).parent().parent().find(".pro-info").find(".book-zone").text()
        var bookTheme = $(this).parent().parent().find(".pro-info").find(".book-theme").text().toString()
        var bookAuthor = $(this).parent().parent().find(".pro-info").find(".book-author").text()
        var bookPubDate = $(this).parent().parent().find(".pro-info").find(".book-pubDate").text().toString().substring(0, 5)
        {
            //解析出地址
            bookTheme = bookTheme.substring(bookTheme.indexOf(".—")+1,bookTheme.indexOf("："))
        }
        var text = "[1]" + " " + bookAuthor + "." + bookName + "[M]." +bookTheme+":"+ bookZone + "," + bookPubDate+"."
        $(".smallInput").html(text)
    }
    if (type == "type-icon3") {
        debugger;
        //标准
        $("#quote-standard").show()
        var standardCode = $(this).parent().parent().find(".pro-info").find(".standard-code").text()
        var standardAuthor = $(this).parent().parent().find(".pro-info").find(".standard-author").text()
        var standardYear = $(this).parent().parent().find(".pro-info").find(".standard-year").text()
        var standardName = $(this).parent().parent().find(".pro-info").find(".standard-name").text()
        var text = "[1]" + " " + standardCode + "," + standardName + "[S]." + "出版地" + "：" +"北京" +","+"出版者"+"："+"化工出版社"+ standardAuthor + "," + standardYear
        $(".smallInput").html(text)
    }
})
$("#book-quote").click(function () {
    var type = $(this).parent().parent().find("i").attr("class")
    $(".layer-pub-main").children().hide()
    $(".smallInput").show()

        //书
        $("#quote-book").show();
        var bookName = $("#name").val();
        var bookZone = $("#bookzone").val();
        var bookTheme = $(this).parent().parent().find(".pro-info").find(".book-theme").text().toString();
        var bookAuthor = $("#bookAuthor").val();
        var bookPubDate = $("#pubDate").val().toString().substring(0, 4);
        {
            //解析出地址
            bookTheme = bookTheme.substring(bookTheme.indexOf(".—")+1,bookTheme.indexOf("："));
        }
        var text = "[1]" + " " + bookAuthor + "." + bookName + "[M]." +bookTheme+":"+ bookZone + "," + bookPubDate+".";
        $(".smallInput").html(text);


})

function standardQuote() {
    $("#quote-standard").show()
    var standardCode = $("#standardId").val()
    var standardAuthor = ''
    var standardYear = $("#standardYear").val()
    var standardName = $("#standardName").val()
    var text = "[1]" + " " + standardCode + "," + standardName + "[S]." + "出版地" + "：" +"北京" +","+"出版者"+"："+"化工出版社" + standardAuthor + "," + standardYear
    $(".smallInput").html(text)
}
function journalQuote() {
    debugger;
    $("#quote-journal").show()
    var journalName = $("#journalName").val()
    var journalArticleName = $("#journalArticleName").val()
    var journalAuthor = $("#journalAuthor").val()
    var journalDate = $("#journalDate").val()
    var text = "[1]" + " " + journalAuthor + "." + journalArticleName + "[J]." + journalName + "," + journalDate
    $(".smallInput").html(text)
}