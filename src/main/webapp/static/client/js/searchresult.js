//检索词
var kw;

var st;//开始时间
var bookname;//年鉴名称
var pubdate;//发布时间
var author;//人物
var organ;//机构
var source;//作者
var tp;
var entrys;
var searchSql;

function searchAgin() {
    // location.href=appPath+"/search?kw="+kw;
    // alert(1)
}

function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
$(function () {
    kw = $("#keyWord").val();
    tp=$("#type").val();
    entrys=$("#entry").val();
    var asd = $("#cttype").val();
    if(tp=='nj'){
        $("#nj").addClass("active").siblings().removeClass("active");
    }
    if(tp=='ct'){
        $("#ct").addClass("active").siblings().removeClass("active");
        $(".pro_size strong").text(asd);
        // entryShow($("#cttype").val());
    }
    if(tp=='wz'){
        $("#wz").addClass("active").siblings().removeClass("active");
    }
    if(tp=='rw'){
        $("#rw").addClass("active").siblings().removeClass("active");
        // entryShow($("#cttype").val());
    }
    if(tp=='lw'){
        $("#lw").addClass("active").siblings().removeClass("active");
        // entryShow($("#cttp").val(),1);
    }
    if(tp=='kt'){
        $("#kt").addClass("active").siblings().removeClass("active");
        // entryShow($("#cttp").val());
    }
    if(tp=='hy'){
        $("#hy").addClass("active").siblings().removeClass("active");
        // entryShow($("#cttp").val());
    }
    if(tp=='ts'){
        $("#ts").addClass("active").siblings().removeClass("active");
        // entryShow($("#cttp").val());
    }
    if(tp=='dsj'){
        $("#dsj").addClass("active").siblings().removeClass("active");
        // entryShow($("#cttp").val());
    }

    if (tp=='wz'){
        $("#wzTextContent").addClass("active").siblings().removeClass("active");
    }

    if (GetQueryString("wzType")=='title'){
        $("#wzTitle").addClass("active").siblings().removeClass("active");
    }

    if (GetQueryString("wzType")=='author'){
        $("#wzAuthor").addClass("active").siblings().removeClass("active");
    }

    if (GetQueryString("wzType")=='Keyword'){
        $("#wzKeyword").addClass("active").siblings().removeClass("active");
    }

    //加载分类
    bookname= $("#bookname").val();
    pubdate= $("#pubdate").val();
    author= $("#author").val();
    organ= $("#organ").val();
    source=$("#source").val();
    searchSql= $("#searchSql").val();
    searchSql= $.base64.encode(encodeURI(searchSql));

});



//按检索类型翻页
function flipchart(type,p) {
    location.href=encodeURI(appPath+"/search?tp="+type+"&pi="+p+"&tp="+type+"&bn="+bookname+"&org="+organ+"&ath="+author+"&sre="+source+"&pubd="+pubdate+"&kw="+kw+"&ssq="+searchSql);
}
//排序
/**
 *
 * @param re 相关度
 * @param hot 热度
 * @param pubd  发布日期
 */
function softType(id,soft) {
    if(soft=="-1"){
        soft=1;
    }else {
        soft=-1;
    }
    bookname= $("#bookname").val();
    pubdate= $("#pubdate").val();
    author= $("#author").val();
    organ= $("#organ").val();
    source= $("#source").val();
    location.href = encodeURI(appPath + "/search?kw=" + kw + "&tp=" + type + "&" + id + "=" + soft + "&ssq=" + searchSql + "&bn=" + bookname + "&org=" + organ + "&ath=" + author + "&sre=" + source);
}
//分类查询
function searchGroup(id,val) {
    if(id=='bookname'){
        bookname=val;
    }
    if(id=='author'){
        author=val;
    }
    if(id=='pubdate'){
        pubdate=val;
    }
    if(id=='organ'){
        organ=val;
    }
    if (id == "source"){
        source = val;
    }
    location.href = encodeURI(appPath + "/search?kw=" + kw + "&tp=" + type + "&bn=" + bookname + "&org=" + organ + "&ath=" + author + "&pubd=" + pubdate + "&sre=" + source);
    showdiv();
}

// $(".moreinfo a").click(function(){
//     entryShow($(this).text());
//     $(this).addClass("active").siblings().removeClass("active");
// });
//检索类型
function searchType(type,entry,pi) {
    kw = $("#keyWord").val();
    var cttp=$("#cttp").val();
    if (typeof(entry) == "undefined"){
        entry='';
    }
    if(typeof(pi) == "undefined"){
        pi='';
    }
    if (entry == 'title' || entry == "author" || entry == "Keyword"){
        location.href=encodeURI(appPath+"/search?kw="+kw+"&tp="+type+"&wzType="+entry);
    }else {
        location.href = encodeURI(appPath + "/search?kw=" + kw + "&tp=" + type + "&cttp=" + cttp + "&swd=" + kw + "&entry=" + entry + "&pi=" + pi);
    }
    showdiv();
}





function entryShow(entry,pi) {
    entrys=entry;
    $("#ct").addClass("active").siblings().removeClass("active");
    $.ajax({
        url: appPath+"/searchentry",
        data: {
            swd:kw,
            entry:entry,
            pi:pi
        },
        success: function(data) {
            $("#ctcontent").html(data)
        }
    })
}


//词条筛选
$(".pro_size strong").click(function(){
    $(this).next("dl").slideToggle();
});
$(".pro_size dd").click(function(){
    $(this).parent("dl").slideToggle();
});
//搜索结果
$(".clickhide").click(function(){
    $("body").hide();
    //$(".showbox").hide();
});
$(".clickshow").click(function(){
    $("body").show();
    //$(".showbox").show();
});
$(".pro_size dd").click(function(){
    $(this).addClass("cur").siblings().removeClass("cur");
    $(this).parents("dl").siblings("strong").text($(this).text() );
});




function setPage(pageIndex){
    if(pageIndex<1){pageIndex=1}
    if(parseInt(pageIndex)>parseInt(count)){pageIndex=count}
    if(tp=='wz'||tp=='nj'){
        flipchart(type,pageIndex);
    }else {
        flipchart(type,pageIndex);
    }
}

