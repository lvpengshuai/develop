$(function(){
    //化工知识体系二级菜单
    $(".system-menu-title").hover(function(){
        $(this).addClass("hover");
        $(".lev1").show();
        $(".lev1 a").hover(function(){
            var index=$(this).index();
            $(".lev2").show();
            $(this).addClass("on").siblings().removeClass("on");
            $(".lev2 ul").eq(index).show().siblings().hide();
            $(".lev2 ul").eq(index).hover(function(){},function(){$(this).hide();});
        });
    });
    $(".system-media-pic").hover(function(){

    },function(){
        $(".lev1,.lev2").hide();
        $(".system-menu-title").removeClass("hover");
    });
    //知识资源tab切换效果
	/*$(".source-types ul li").hover(function(){
	 var index=$(this).index();
	 $(this).addClass("hover").siblings().removeClass("hover");
	 $(".source-tabs .s-tab-box").eq(index).fadeIn(500).siblings().fadeOut();
	 });*/
    jQuery("#scroll").jCarouselLite({
        auto:false,
        speed:300,
        visible:1,
        vertical:true,
        stop:$("#scroll"),
        btnGo: $(".source-types ul li"),
        btnGoOver:true
    });
	/*//footer在底部
	 var wh=$(window).height();
	 var bh=$(document.body).height();
	 if(bh<wh){
	 $(".footer").css({"position":"absolute","bottom":"0","left":"0"});
	 }else{

	 }*/
});