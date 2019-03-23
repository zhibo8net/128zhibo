$(document).ready(function(){
	var html="";
	html+="	<style>";
	html+="		.mfp-bg{top: 0;left: 0;width: 100%;height: 100%; z-index: 1042; overflow: hidden;position: absolute; background: #0b0b0b;opacity: 0.8;filter: alpha(opacity=80);}";
	html+="		.mfp-wrap{top: 0;left: 0;width: 100%;height: 100%; z-index: 1043;position: absolute; outline: none !important; -webkit-backface-visibility: hidden;}";
	html+="		.mfp-container{text-align: center;position: absolute;width: 100%;height: 100%;left: 0;top: 0;padding: 0 8px;-webkit-box-sizing: border-box;-moz-box-sizing: border-box; box-sizing: border-box;}";
	html+="		.mfp-container:before{content: '';display: inline-block;height: 100%;vertical-align: middle;}";
	html+="		.mfp-content{position: relative;display: inline-block;vertical-align: middle;margin: 0 auto;text-align: left;z-index: 1045;}";
	html+="		.mfp-close{display: block;top: 0; line-height: 44px; position: absolute; background: transparent; border: none; height: 44px; font-size: 28px; font-family: Arial, Baskerville, monospace; opacity: 0.65;color: white;right: -6px;text-align: right;padding-right: 6px;width: 100%;}";
	html+="		img.mfp-img{width: auto;max-width: 100%;height: auto;display: block;line-height: 0;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box;padding: 40px 0 40px;margin: 0 auto;}";
	html+="		.mfp-title{position:absolute;margin-top:-36px;text-align: left;line-height: 18px;color: #f3f3f3;word-wrap: break-word;padding-right: 36px;}";
	html+="		.mfp-counter{position: absolute;bottom:20px;right: 0;color: #cccccc;font-size: 12px;line-height: 18px;}";
	html+="		.mfp-arrow{position: absolute;opacity: 0.65;margin: 0;top: 50%;margin-top: -55px;padding: 0;width: 90px;height: 110px;-webkit-tap-highlight-color: rgba(0, 0, 0, 0);} ";
	html+="		button.mfp-arrow{overflow: visible;cursor: pointer;background: transparent;border: 0; -webkit-appearance: none; display: block;padding: 0;z-index: 1046;-webkit-box-shadow: none;box-shadow: none;}";
	html+="		.mfp-arrow-left{left:0;}";
	html+="		.mfp-arrow-right{right:0;}";
	html+="		.mfp-arrow:after{content: '';display: block;width: 0;height: 0; position: absolute;left: 0;top: 0; margin-top: 35px;margin-left: 20px;border: medium inset transparent;}";
	html+="		.mfp-arrow-left:after, .mfp-arrow-left{border-right: 17px solid white;/*margin-left: 31px;*/}";
	html+="		.mfp-arrow:after, .mfp-arrow .mfp-a{border-top-width: 13px;border-bottom-width: 13px;top: 8px;}";
	html+="		.mfp-arrow:hover{opacity:1;}";
	html+="		.mfp-arrow-right:after, .mfp-arrow-right{border-left: 17px solid white;margin-left: 50px}";
	html+="	</style>";
	$("body").append(html);
	$(document).on('click','.width_img a',function(){
		var all=$(".width_img").length;
		var curr=$(".width_img").index($(this).parents(".width_img"))+1;
		var counter=all==1?'':curr+" of "+all;
		var gifStr='<div class="mfp-bg" style="height:'+$(document).height()+'px;"></div>'+
					'<div class="mfp-wrap"style="top:'+$(window).scrollTop()+'px;height:'+$(window).height()+'px;">'+
						'<div class="mfp-container">'+
							'<div class="mfp-content">'+
									'<button type="button" class="mfp-close">×</button>'+
									'<img id="gif_img" class="mfp-img" src="'+$(this).attr("href")+'"/>'+
									'<div class="mfp-title">'+$(this).attr("title")+'</div>'+
									'<div class="mfp-counter">'+counter+'</div>'+
								'</div>';
			if(all>1){
				gifStr+=		'<button title="Previous" type="button" class="mfp-arrow mfp-arrow-left mfp-prevent-close"></button>'+
								'<button title="Next" type="button" class="mfp-arrow mfp-arrow-right mfp-prevent-close"></button>';
			}
			gifStr+=	'</div>'+
					'</div>';
		$("body").append(gifStr);
		//阻止链接跳转
		return false;
	});
	$(document).on('click',".mfp-close,.mfp-wrap,.mfp-bg",function(e){
		e=e||window.event;
		var btn=e.target.title||e.target.tagName;
		var switchOpen=function(btn){
			if($(".mfp-counter").text()=='') return;
			var arr=$(".mfp-counter").text().split("of");
			var curr=arr[0].trim();
			var all=arr[1].trim();
			if(btn=='Previous'){
				curr=curr==1?all:curr-1;
			}else if(btn=='Next'||btn=='IMG'){
				curr=curr==all?1:parseInt(curr)+1;
			}
			$(".mfp-counter").text(curr+" of "+all);
			$(".mfp-img").attr("src",$(".width_img").eq(curr-1).find("a").attr('href'));
			$(".mfp-title").text($(".width_img").eq(curr-1).find("a").attr('title'));
		};
		if(btn=='Previous'||btn=='Next' || btn=='IMG'){
			switchOpen(btn);
			return false;
		}
		if(e.target.className=='mfp-img') return false;
		$(".mfp-wrap").remove();
		$(".mfp-bg").remove();
	});
});