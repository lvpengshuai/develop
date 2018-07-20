
function check() {
    var valueI = $("#kw").val();
    if(valueI.replace(/(^\s+)|(\s+$)/g,'')==""){
        return false;
    }
}
function submitsearch() {
    var searchInput=$.trim($("#kw").val());
    if (searchInput!=""){
        $("#search").submit();
    }
}

function setButtonValue(info){
    $("#code").val(info);
}
//tab
$(function(){
    $("#kw").keydown(function (e) {
        if(e.which == 13) {
            var searchInput=$.trim($("#kw").val());
            if (searchInput!=""){
                $("#search").submit();
                return true;
            }else {
                return false;
            }
        }
    })
    //顶部搜索
    // $(".seach-chose dd").click(function(){
    //     // alert($("input[name='tp']").val()  );
    //     $(this).parents("dl").siblings("strong").text(  $(this).text() );
    //     $(this).parents("dl").toggle();
    //     $("#cttype").val($(this).text());
    //     // alert($("input[name='tp']").val()  );
    // });
    // $(".seach-chose strong").click(function(){
    //     $(this).next("dl").toggle();
    // });



    //会议
    $(".activity-year dd").click(function(){
        $(this).parents(".yearlist").siblings("h3").text(  $(this).text() );
        $(this).parents(".yearlist").toggle();
    });
    $(".activity-year h3").click(function(){
        $(this).next(".yearlist").toggle();
    });

    //词条分类选择
    $(".seach-cate dd").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
        $("#cttype").val($(this).text());
    });
    // //词条分类选择
    // $(".searchclick03").click(function(){
    //     $(".seach-cate").show();
    // });
    // $(".searchclick01, .searchclick02").click(function(){
    //     $(".seach-cate").hide();
    // });

});
