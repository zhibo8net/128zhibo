
var winH=$(window).height();
var loaded=true;

function curpos($p,winH){//当前位置是否在可视区域
	var top=$p.offset().top;
	var scrollH=$(window).scrollTop();
	if(scrollH<top&&scrollH+winH>top){
		return true;
	}else{
		return false;
	}
}

function send_nba_val(winH){//nba赋值
// 	$("[type='nba'] .pinglun").each(function(i,item){
// 		var x=i;var flag=$(item).attr("flag");
// 		if(curpos($(item),winH)&&!flag){
// 			$(item).attr("flag",true);
// 			var val=$(this).attr("val");
// 			val=val.replace(/-/g,"/");
// 			var $this=$(this);
// 			var url="//cache.zhibo8.cc/json/"+val+"_count.htm";
// 			$.getJSON(url,function(data){
// 				$this.text(data.all_num);
// //				$(".nba_p .pinglun").eq(x).text(data.all_num);
// 			});
// 		}
// 	});
}

function send_zuqiu_val(winH){//足球赋值
	// $("[type='zuqiu'] .pinglun").each(function(i,item){
	// 	var x=i;var flag=$(item).attr("flag");
	// 	if(curpos($(item),winH)&&!flag){
	// 		$(item).attr("flag",true);
	// 		var val=$(this).attr("val");
	// 		val=val.replace(/-/g,"/");
	// 		var $this=$(this);
	// 		var url="//cache.zhibo8.cc/json/"+val+"_count.htm";
	// 		$.getJSON(url,function(data){
	// 			$this.text(data.all_num);
	// 		});
	// 	}
	// });
}

function init(winH){
//	if($(".basketball_p").css("display")!="none"){
		send_nba_val(winH)
//	}
//	if($(".football_p").css("display")!="none"){
		send_zuqiu_val(winH)
//	}
}


$(function(){

//	$(".global").prepend(addAdv("https://s.click.taobao.com/HJM3UOx","//tu.qiumibao.com/img/1111_480_60.jpg"));
//	setTimeout(function(){top=$(".nav").offset().top},100);
    var default_type = getCookie("stype");
    if(!default_type){

		modelbox(true);
    }else{
    	if(default_type=='all'||default_type=='other'||default_type=='zhongyao'){
			shaixuanlist(default_type);
    	}else{
    		shaixuanlist(default_type);
    	}

	    switch(default_type){
//      	case 'zhongyao': $(".global_sel").text("重要");break;
        	case 'football': $(".global_sel").text("足球");break;
        	case 'basketball': $(".global_sel").text("篮球");break;
//      	case 'other': $(".global_sel").text("其他");break;
        	default: $(".global_sel").text("全部");
        }
	}



//  init(winH);
	/***************固定导航条*/
	var top=$(".nav").offset().top;

	$(window).on("scroll",function(){
		var H=$(this).scrollTop();
		if(top-H<=0){
			$(".nav").css({"position":"fixed","top":0,"z-index":999});
		}else{
			$(".nav").css({"position":"static"});
		}
		var ctype=getCookie("stype");
		if(ctype=="basketball"){
			send_nba_val(winH);
		}else if(ctype=="football"){
			send_zuqiu_val(winH);
		}else{
			send_nba_val(winH);
			send_zuqiu_val(winH);
		}
// 		if($(document).height()-$(this).scrollTop()-$(this).height()<50){
// //  		$("#"+type+"_new .panel ul").append("<li class='loading'>加载中...</li>");
// 			if(loaded){
// 				loaded=false;
// 				var t=$("#"+type+"_new .panel ul").find(".loading").attr("date");
// 	    		if(t){
// 	    			if(type=="news"){
// 	    				loadNews(new Date(parseInt(t)),type);
// 	    			}else{
// 	    				loadMore(new Date(parseInt(t)),type);
// 	    			}
// 	    		}else{
// 	    			if(type=="news"){
// 	    				loadNews(new Date(),type);
// 	    			}else{
// 	    				loadMore(new Date(),type);;
// 	    			}
// 	    		}
// 			}

//     	}
	})
	/***************固定导航条*/
    var gotop_btn_elem = $(".gotop");
    gotop_btn_elem.on('click',  function(e){//回到顶部
        window.scroll(0, 0);
    })

    $(".global_sel").on("click",function(){
    	modelbox(true);
    	$(".box_bg").on("click",function(){
    		modelbox(false);
    	})
    });

    $(".global").on("click",".box ul a",function(){
    	var stype=$(this).attr("value");
    	setDomainCookie("stype",stype,".zhibo8.cc");
        shaixuanlist(stype);
    	modelbox(false);
    });


})
	//添加顶部广告
//	function addAdv(url,imgSource){
//		var str='<style>.adv11{width:100%;height:auto;}.adv11 img{width:100%;vertical-align:middle;}</style>'+
//				'<div class="adv11"><a href="'+url+'"><img src="'+imgSource+'"/></a></div>';
//		return str;
//	}

function modelbox(flag){//选择赛事框弹出、隐藏
	if(flag){
		var html='';
    	html+='<div class="box"><div class="box_bg"></div>';
    	html+='<div class="box_box"><div class="box_ent"><div class="box_center"><h2 class="box_t">选择你的属性</h2><ul><li><a href="javascript:;" value="basketball">只看篮球</a></li><li><a href="javascript:;" value="football">只看足球</a></li><li><a href="javascript:;" value="all">全部</a></li></ul></div></div></div></div>';
    	$(".global").append(html);
	}else{
		$(".global").find(".box").remove();
	}
}
function shaixuanlist(ctype){//筛选所有列表
	if(ctype=='football'){
		$(".panel li").each(function(i,item){
			$(this).hide();
			if($(this).attr("type")=='zuqiu'){
				$(this).show();
			}
		});

		send_zuqiu_val(winH);
	}else if(ctype=='basketball'){
		$(".panel li").each(function(i,item){
			$(this).hide();
			if($(this).attr("type")=='nba'){
				$(this).show();
			}
		});
		send_nba_val(winH);
	}else{
		var num=0;
		$(".panel li").each(function(i,item){
			$(this).show();
			num++;
			if($(this).parents(".showtime").length>0&&num>50){
				$(this).hide();
			}
		});
		send_nba_val(winH);
		send_zuqiu_val(winH);
	}

    switch(ctype){
//		case 'zhongyao': $(".global_sel").text("重要");break;
		case 'football': $(".global_sel").text("足球");break;
		case 'basketball': $(".global_sel").text("篮球");break;
//		case 'other': $(".global_sel").text("其他");break;
		default: $(".global_sel").text("全部");
	}
}
function timepro(num){//判断是否大于10
	return num<10?('0'+num):num;
}
//加载新闻
function loadNews(time,type){//获取新闻内容
	var curday=time.getFullYear()+'/'+timepro(time.getMonth()+1)+"/"+timepro(time.getDate());
	var url="//m.zhibo8.cc/json/news/gather/"+curday+"_news.htm";
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		success:function(data){
			var all_ball=data;
			var str="";
			for(var i=0;i<all_ball.length;i++){
				if(all_ball[i]['model']=='web'){
					continue;
				}
				if(all_ball[i]['type'].indexOf("nba")>-1){
					all_ball[i]['type2']='nba';
				}else if(all_ball[i]['type'].indexOf("zuqiu")>-1){
					all_ball[i]['type2']='zuqiu';
				}else{
					if(all_ball[i]['label'].indexOf('篮球')>-1){
						all_ball[i]['type2']='nba';
					}else if(all_ball[i]['label'].indexOf('足球')>-1){
						all_ball[i]['type2']='zuqiu';
					}else{
						continue;
					}
				}
				var t1=new Date().getTime()-new Date(all_ball[i]['createtime']).getTime();
				var create_time=all_ball[i]['createtime'].substring(all_ball[i]['createtime'].indexOf("-")+1,all_ball[i]['createtime'].lastIndexOf(":"));
				if(t1>0&&t1/(60*1000)<60){
					create_time=Math.floor(t1/(60*1000))+'分钟';
				}
				str+='<li class="lite" type="'+all_ball[i]['type2']+'">'+
					'<div class="icon_video">'
				var filetype="";

				str+='<a href="//m.zhibo8.cc/news/web'+all_ball[i]['url']+'">'+
				'<img src="'+all_ball[i]['thumbnail']+'">';
				str+='</a>'+
				'</div>'+
				'<h2><a href="//m.zhibo8.cc/news/web'+all_ball[i]['url']+'">'+all_ball[i]['title']+'</a>'+
				'</h2>'+
				'<p class="lite_bot"><span class="pass_time">'+create_time+'</span><span class="pinglun" val="'+all_ball[i]['pinglun']+'">0</span></p>';
			}
			var t=time.getTime()-24*60*60*1000;
			str+='</li><li class="loading" date="'+t+'"></li>';
			$(".loading").after(str);
			$(".loading").eq(0).remove();
			var ctype=getCookie("stype");
			shaixuanlist(ctype);loaded=true;
		}
	})
}
//加载更多
function loadMore(time,type){
	var curday=time.getFullYear()+'-'+timepro(time.getMonth()+1)+"-"+timepro(time.getDate());
	var url="//m.zhibo8.cc/json/news/nba/"+curday+".json";
	$.ajax({
		url:"//m.zhibo8.cc/json/"+type+"/nba/"+curday+".json",
		type:"get",
		dataType:"json",
		success:function(data){
			var nba=data.video_arr;
			$.ajax({
				url:"//m.zhibo8.cc/json/"+type+"/zuqiu/"+curday+".json",
				type:"get",
				dataType:"json",
				success:function(data){
					var zuqiu=data.video_arr;
					var str="";
					var all_ball=[];
					all_ball=all_ball.concat(nba,zuqiu);
					var sort_new=function(obj1,obj2){
						return new Date(obj1['createtime']).getTime()>new Date(obj2['createtime']).getTime()?-1:1;
					}
					all_ball.sort(sort_new);
					for(var i=0;i<all_ball.length;i++){
						if(all_ball[i]['type'].indexOf("nba")>-1){
							all_ball[i]['type2']='nba';
						}else{
							all_ball[i]['type2']='zuqiu';
						}
						var t1=new Date().getTime()-new Date(all_ball[i]['createtime']).getTime();
						var create_time=all_ball[i]['createtime'].substring(all_ball[i]['createtime'].indexOf("-")+1,all_ball[i]['createtime'].lastIndexOf(":"));
						if(t1>0&&t1/(60*1000)<60){
							create_time=Math.floor(t1/(60*1000))+'分钟';
						}
						str+='<li class="lite" type="'+all_ball[i]['type2']+'">'+
							'<div class="icon_video">'
						var filetype="";
						if(type=='video'){
							str+='<a href="//m.zhibo8.cc'+all_ball[i]['url']+'">'+
							'<img class="player" src="//static4style.qiumibao.com/m_images/video_play.png" alt=""/><img src="//tu.qiumibao.com/v/thumb/'+all_ball[i]['type2']+'/'+all_ball[i]['createtime'].substring(0,4)+'/'+all_ball[i]['filename'].replace(/-/g,"/")+'.jpg" />'+
							'</a>'+
							'</div>'+
							'<h2><a href="//m.zhibo8.cc'+all_ball[i]['url']+'">'+all_ball[i]['title']+'</a>'+
							'</h2>'+
							'<p class="lite_bot"><span class="pass_time">'+create_time+'</span><span class="pinglun" val="2016-'+all_ball[i]['type']+'-'+all_ball[i]['filename']+'">0</span></p>';

						}else{
							str+='<a href="//m.zhibo8.cc/news/web'+all_ball[i]['url']+'">'+
							'<img src="'+all_ball[i]['thumbnail']+'">';
							str+='</a>'+
							'</div>'+
							'<h2><a href="//m.zhibo8.cc/news/web'+all_ball[i]['url']+'">'+all_ball[i]['title']+'</a>'+
							'</h2>'+
							'<p class="lite_bot"><span class="pass_time">'+create_time+'</span><span class="pinglun" val="'+all_ball[i]['way']+'">0</span></p>';

						}
					}
					var t=time.getTime()-24*60*60*1000;
					str+='</li><li class="loading" date="'+t+'"></li>';
					$(".loading").after(str);
					$(".loading").eq(0).remove();
					var ctype=getCookie("stype");
					shaixuanlist(ctype);loaded=true;
				}
			})
		}
	});
}
//写cookies
function setCookie(name,value)
{
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/";
}

function setDomainCookie(name,value,domain)
{
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";domain=" + domain + ";path=/";
}

//读取cookies
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

    if(arr=document.cookie.match(reg))

        return (arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString()+";path=/";
}

// 操作系统判断
var systemOS = function(){
    var ua = navigator.userAgent.toLowerCase();
    if (ua.indexOf('ucweb') > 0 || ua.indexOf('ucbrowser') > 0) {
        return "uc";
    }else if (ua.indexOf('iphone') > 0) {
        return "iphone";
    }else if (ua.indexOf('android') > 0) {
        return "android";
    }
};