$(document).ready(function() {
	$(".js-image").mousemove(function(e) {
		var positionX = e.offsetX || 0;
		if (positionX <= $(this).width() / 2) {
			this.style.cursor = 'url("/image/icon/arrow-back.png"),auto';
			$(this).attr('title', '点击查看上一张');
			$(this).parent().attr('href', $("#js-prev").attr('href') || "javascript:void(0);");
		} else {
			this.style.cursor = 'url("/image/icon/arrow-forward.png"),auto';
			$(this).attr('title', '点击查看下一张');
			$(this).parent().attr('href', $("#js-next").attr('href') || "javascript:void(0);");
		}
	});
});