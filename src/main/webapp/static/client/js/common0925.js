// JavaScript Document

	//tab
	$(function(){

		//顶部搜索
		$(".seach-chose dd").click(function(){
			$(this).parents("dl").siblings("strong").text(  $(this).text() );
			$(this).parents("dl").toggle();
            $("#cttype").val($(this).text());
		});
		$(".seach-chose strong").click(function(){
			$(this).next("dl").toggle();
		});
		

		
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
		});
		
		//词条分类选择
		$(".searchclick03").click(function(){
			$(".seach-cate").show();
		});
		$(".searchclick01, .searchclick02").click(function(){
			$(".seach-cate").hide();
		});
		
		//会议列表

		
		//搜索结果展示更多
		$(".moreclick em").click(function(){
            var nowTime = new Date().getTime();
            var clickTime = $(this).attr("ctime");
            if( clickTime != 'undefined' && (nowTime - clickTime < 5000)){
                alertify.alert('操作过于频繁，稍后再试');
                return ;
            }else{
                $(this).attr("ctime",nowTime);
                $(".moreinfofont").slideDown();
                $(this).hide();
                $(".moreclick i").show();
            }

		});
		$(".moreclick i").click(function(){
            var nowTime = new Date().getTime();
            var clickTime = $(this).attr("ctime");
            if( clickTime != 'undefined' && (nowTime - clickTime < 5000)){
                alertify.alert('操作过于频繁，稍后再试');
                return ;
            }else{
                $(this).attr("ctime",nowTime);
                $(".moreinfofont").slideUp();
                $(this).hide();
                $(".moreclick em").show();
            }

		});
		
		
		//分析页面切换
		$(".click01").click(function(){
			$(".clickshow01").addClass("show");
			$(".clickshow02, .clickshow03, .clickshow04").removeClass("show");
		});
		$(".click02").click(function(){
			$(".clickshow02").addClass("show");
			$(".clickshow01, .clickshow03, .clickshow04").removeClass("show");
		});
		$(".click03").click(function(){
			$(".clickshow03").addClass("show");
			$(".clickshow02, .clickshow01, .clickshow04").removeClass("show");
		});
		$(".click04").click(function(){
			$(".clickshow04").addClass("show");
			$(".clickshow02, .clickshow01, .clickshow03").removeClass("show");
		});
		$(".tab-hd-center li").click(function(){
			$(this).addClass("active").siblings().removeClass("active");
		});


        //1102新年鉴列表
        $(".nianjianlist dd").each(function(){
            $(this).children(".nianjianother").children("p").children("a:gt(7)").hide();
        })

        $(".nianjianother em").click(function(){
            $(this).prev("p").children("a:gt(7)").show();
            $(this).hide();
            $(this).next("i").show();
        });
        $(".nianjianother i").click(function(){
            $(this).prev().prev("p").children("a:gt(7)").hide();
            $(this).hide();
            $(this).prev("em").show();
        });

        //1104新年鉴列表
        $(".nianjianlist dd").each(function(){
            $(this).children(".nianjianother").children("p").children("a:gt(7)").hide();
        })
        $(".nianjianother em").click(function(){
            $(this).parent("p").children("a:gt(7)").show();
            $(this).hide();
            $(this).next("i").show();
        });
        $(".nianjianother i").click(function(){
            $(this).parent("p").children("a:gt(7)").hide();
            $(this).hide();
            $(this).prev("em").show();
        });
   });
