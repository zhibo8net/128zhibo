window.switchTo = function(tag){
	if(tag=='all'){
		$(".schedule li").show();
	}else{
		$(".schedule li").hide();
		$(".schedule li."+tag).show();
	}

	$(".schedule-tabs li").show();
	$(".schedule-tabs li").removeClass("active");

	$(".schedule-tabs .tab-"+tag).addClass("active");
}

$(function(){
	//日期定位
	$(".scroller li").click(function(e){
		e.preventDefault();
		var index = $(this).index();
		var pos = $(".day").not(".hide").eq(index).offset().top-43*(index);
		$(".scroller li a").removeClass("active");
		$(this).find("a").addClass("active");
		$(".scroller").offset({ top: pos});
		$("body,html").animate({ scrollTop: pos}, "slow");
	});

	//比赛映射日期
	$(window).scroll(function(){
		var matchTop = $("#js-schedule").offset().top;
		var top = document.body.scrollTop;
		if (top == 0)
		{
			top = document.documentElement.scrollTop;
		}
		if (top > matchTop)
		{
			$(".scroller").css("top", top - matchTop);
		}
		else
		{
			$(".scroller").css("top", 0);
		}
		for (var i = 0; i < $(".day").length; i++)
		{
			var more = $(".day").eq(i).offset().top - top;
			if (more >= 0 && more < window.screen.height)
			{
				$(".scroller li a").removeClass("active");
				$(".scroller li").eq(i).find("a").addClass("active");
				break;
			}
		}
	});

	//回到顶部
	$(".up").click(function(){
		$("body,html").animate({ scrollTop: $(".day").eq(0).offset().top}, "slow");
	});


	//点击“加载更多”
	$("#loadMore a").click(function(){
		$(".schedule").removeClass("max-height-600");
		$("#loadMore").hide();
		$("#scrollToTop").show();
	});

	//点击“返回顶部”
	$("#scrollToTop a").click(function(){
		$("body,html").animate({ scrollTop: 0}, "slow");
	});

});
