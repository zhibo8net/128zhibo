/**
 * Created by Administrator on 2015/5/7.
 */


var host=0,guest=0,total_score_h=0,total_score_v=0,game_date=0,saishi_id=0,bifen_settime=null;
$(function(){
    /*数据展示*/
    host = $('#host').html();
    guest = $('#guest').html();
    total_score_h=$('#total_score_h').html();
    total_score_v=$('#total_score_v').html();
    game_date = $('#date_time').html();
    saishi_id = $('#saishi').html();
    filename=$("#filename").html();
    var top_title = $('#title').html();
    $('#top_title').html(top_title);
    if(total_score_v!==''){
    	$('.team_guest').html(decodeURI(guest)+"("+total_score_v+")");
    	$('.vt_name').html(decodeURI(guest)+"("+total_score_v+")");
    }else{
    	$('.team_guest').html(decodeURI(guest));
    	$('.vt_name').html(decodeURI(guest));
    }
    if(total_score_h!==''){
    	$('.team_host').html(decodeURI(host)+"("+total_score_h+")");
    	$('.ht_name').html(decodeURI(host)+"("+total_score_h+")");
    }else{
    	$('.team_host').html(decodeURI(host));
    	$('.ht_name').html(decodeURI(host));
    }
    game_time = $('#game_time').html();
    
    /*球队支持*/
    get_support_data(saishi_id);
    $('.guest_good a').click(function () {
    	//禁止重复点赞
    	var clickRight='match_'+saishi_id+'_visit';
    	if(!cookie(clickRight)){
    		addCookie(clickRight,saishi_id,3600*24*30);
    	}else{
    		alert("请勿重复点赞");
    		return;
    	}
        support_data('match_'+saishi_id+'_visit','guest');
    })
    $('.host_good a').click(function () {
    	//禁止重复点击
    	var clickLeft='match_'+saishi_id+'_host';
    	if(!cookie(clickLeft)){
    		addCookie(clickLeft,saishi_id,3600*24*30);
    	}else{
    		alert("请勿重复点赞");
    		return;
    	}
        support_data('match_'+saishi_id+'_host','host');
    });
	
	//adv1111
//	$(".main_wrap").prepend(addAdv("https://s.click.taobao.com/HJM3UOx","http://tu.qiumibao.com/img/1111_480_60.jpg"));
		
    
    /*赛前数据*/
// show_sqsj();
    /*定时刷新*/
//  max_sid_ajax();
    (function f(){
    	max_sid_ajax(function(){
    		setTimeout(f,1000);
    	});
    })();
    
//  setInterval(max_sid_ajax,1000);
    
    if($('.match_hometeam img').attr('src')=='//duihui.oss-cn-hangzhou.aliyuncs.com/nba%2F.png'){
        $('.match_hometeam img').attr('src','//duihui.oss-cn-hangzhou.aliyuncs.com/nba%2Fdefault_h.png');
    }
    if($('.match_guestteam img').attr('src')=='//duihui.oss-cn-hangzhou.aliyuncs.com/nba%2F.png'){
        $('.match_guestteam img').attr('src','//duihui.oss-cn-hangzhou.aliyuncs.com/nba%2Fdefault_v.png');
    }
})

	//添加顶部广告
//	function addAdv(url,imgSource){
//		var str='<style>.adv11{width:100%;height:auto;text-align:center;}.adv11 img{width:100%;vertical-align:middle;}</style>'+
//				'<div class="adv11"><a href="'+url+'"><img src="'+imgSource+'"/></a></div>';
//		return str;
//	}

jQuery.support.cors = true;

//文字ajax
var interact = avalon.define("interact", function(vm){
    vm.interact = [];
    // vm.host_score = 0;
    // vm.visit_score = 0;
});
var live_sid=0,load_id= 0,load_status=false,is_first= 1,now_sid= 0,is_ajax_new_mes=true,yichang=0;
var dingshi_url='//dingshi4m.qiumibao.com',min_sid=0;

var bifen_url="//bifen4m.qiumibao.com";
function new_bifen_data(){
	$.ajax({
		type: "get",
        dataType: "json",
        async: true,
        url:bifen_url+"/json/"+game_date+"/"+saishi_id+".htm?random="+Math.random(),
        success:function(data){
        	if(data!=null&&!$.isEmptyObject(data)){
//      		console.log(data);
        		if(data['big_score_2']!=total_score_v&&data['big_score_2']!==''){
        			$('.team_guest').html(decodeURI(guest)+"("+data['big_score_2']+")");
        			$('.vt_name').html(decodeURI(guest)+"("+data['big_score_2']+")");
        			total_score_v=data['big_score_2'];
        		}
        		if(data['big_score_1']!=total_score_h&&data['big_score_1']!==''){
        			$('.team_host').html(decodeURI(host)+"("+data['big_score_1']+")");
			   		$('.ht_name').html(decodeURI(host)+"("+data['big_score_1']+")");
			   		total_score_h=data['big_score_1'];
        		}
        		if(data.period_cn) $(".bifen_status").text(data.period_cn);
        		if(data['home_score']) $('.host_score').html(data['home_score']);
	            if(data['visit_score']) $('.visit_score').html(data['visit_score']);
			    if(data['full_timeouts_1']!==""&&data['full_timeouts_1']!=="undefined"){
//	        		$('.host_score').html(data['home_score']);
//	                $('.visit_score').html(data['visit_score']);
//	                $(".bifen_status").text(data.period_cn);
	                var host_d='本节犯规：'+data['team_fouls_1'];
	                var guest_d='本节犯规：'+data['team_fouls_2'];
	                var cd_val=(parseInt(data['full_timeouts_1'])+parseInt(data['short_timeouts_1']))+' 暂停  '+(parseInt(data['full_timeouts_2'])+parseInt(data['short_timeouts_2']));
	                
	                if($(".match_mode .team_detail").length>0){
	                	$(".match_mode .host_d").text(host_d);
	                	$(".match_mode .geust_d").text(guest_d);
	             
	                	$(".match_mode .cd_val").text(cd_val);
	                }else{
                		var str="<div class='team_detail'><span class='host_d'>"+host_d+"</span><span class='cd_val'>"+cd_val+"</span><span class='guest_d'>"+guest_d+"</span></div>";
                		$(".match_mode").append(str);
	                }
	            }
                if(data['period_cn']=='完赛'){
                	clearInterval(bifen_settime);
                }
        	}
        }
	})
}

//get max sid
var content = '';
function max_sid_ajax(fn){
//	saishi_id="63902";//测试效果
    $.ajax({
        type: "get",
        dataType: "json",
        async: true,
        url:  dingshi_url+"/livetext/data/cache/max_sid/"+saishi_id+"/0.htm?get="+Math.random(),
        success: function(data){
            //网络异常提示
            if(yichang>15){
                if($('.debug_mesg li').length<=1){
                    $('.match_mode_status h1').html('文字直播');
                    $('.match_mode_status h1').unbind('click');
                }else{
                    $('.match_mode_status h1').html('视频直播');
                }
                $('.match_mode_status h1').css('background','#74B5F7');
            }
            yichang=0
            var data = parseInt(data);
            if(is_first!=1){
                //max_sid与现在载入sid号相差太多清空现有数据重新载入
                is_ajax_new_mes = true;
                if(data>now_sid+30){
                    is_first=1;
                    now_sid=0;
                    load_id=0;
                    $(".card_wrap").empty();
                }else if(now_sid>=data){
                    is_ajax_new_mes = false;
                }
            }
            if(is_first==1){
                var yushu = data%10;
                var zhengshu = Math.floor(data/10);
                if(yushu==0){
                    load_id = zhengshu*10;
                }else{
                    load_id = zhengshu*10+10;
                }
                if(data%2){
                    live_sid = data+1;
                }else{
                    live_sid = data;
                }
            }
            fn();
        },
        error:function (){
            yichang++;
            if(yichang>15){
//              $('.match_mode_status h1').html('暂无数据');
//              $('.match_mode_status h1').css('background','#FFBB00');    
            }
            if(yichang==1){
            	$('.bifen_status').html(game_time.substring(game_time.indexOf("-")+1));
	        	$("#loading").html("暂无直播数据");
	        	getUpdate_count();
            }
            fn();
        }
    }).done(function () {
        if(is_ajax_new_mes){
            new_mesaage_ajax();
        }
    });
}

var bifen_code='',bisai_status='start';
//无文字直播采集球迷数据
function getUpdate_count(){
	//比分
	if($('#j_recommend_list > div').length===0){
		$.getJSON('//bifen4pc.qiumibao.com/json/' + game_date + '/' + saishi_id + '_code.htm?' + Math.random(),function(d){
			if(d.code != bifen_code){
				bifen_code = d.code;

				$.getJSON('//bifen4pc.qiumibao.com/json/' + game_date + '/' + saishi_id + '.htm?' + Math.random(),function(data){
					if(!isNull(data)&!$.isEmptyObject(data)){console.log(data)
						$(".host_score").text(data.home_score);

						$(".visit_score").text(data.visit_score);
	
						$(".bifen_status").text(data.period_cn);
						if(data.period_cn!='完赛'){
							setTimeout(function(){
								getUpdate_count();
							},5000);
						}
					}
				});
			}else{
				setTimeout(function(){
					getUpdate_count();
				},5000);
			}
		});
	}
}

var dc_url='//dc4pc.qiumibao.com';

var isNba = $('title').html().indexOf('NBA') > -1 ? true : false;

//get new message
function new_mesaage_ajax(){
    $.ajax({
        type: "get",
        dataType: "json",
        async: true,
        url:  dingshi_url+"/livetext/data/cache/livetext/"+saishi_id+"/0/lit_page_2/"+live_sid+".htm?get="+Math.random(),
        success: function(data){
            if(data!=null&&!$.isEmptyObject(data)){
//            	console.log("------new_message_ajax------");
//            	console.log(data);
                //正在加载
                var data_len = data.length;
//				console.log(data);
				
				
                
                var game_s_t = js_strto_time(game_time);
                var now_s_t = Date.parse(new Date())/1000;
                //console.log(now_s_t);
                if(now_s_t>game_s_t){
            
                    if(parseInt(data[data_len-1]['home_score'])>0||parseInt(data[data_len-1]['visit_score'])>0){
                        $('.host_score').html(data[data_len-1]['home_score']);
                        $('.visit_score').html(data[data_len-1]['visit_score']);
                    }else{
                        $.getJSON(dc_url+"/dc/matchs/sina/"+game_date+"/bifen_"+game_id+".htm?get="+Math.random(),function(data){
                            //console.log(data.data.Score1);
                           
                            if(parseInt(data.data.Score1)>0||parseInt(data.data.Score1)>0){
                                $('.host_score').html(data.data.Score2);
                                $('.visit_score').html(data.data.Score1);
                            }else{
                                var this_url = window.location.href;
                                var patt = new RegExp("2015\/(.*?).htm","g");
                                var parren = patt.exec(this_url);
                                var url_c = parren[1];
                                $.getJSON("//m.zhibo8.cc/json/bifen/zhibo/nba/2015/"+url_c+".htm?"+Math.random(),function(data){
                                    $('.host_score').html(data.bifen_b);
                                    $('.visit_score').html(data.bifen_a);
                                })
                            }
                        })
                    }
                }else{
                	$('.bifen_status').html(game_time.substring(game_time.indexOf("-")+1));
                }

                $('.host_score').html(data[data_len-1]['home_score']);
                $('.visit_score').html(data[data_len-1]['visit_score']);
                for (var i in data) {
                	if(filename.indexOf("nba")==-1)
                	$('.bifen_status').html(data[i].pid_text);
                    if (!isNull(data[i]['live_text']) && data[i]['img_url'].length != 0 && data[i]['guess_id'] == 0 && parseInt(data[i]['live_sid'])>now_sid) {
                    	var width_img='width_img';
                    	if(data[i]['weibo_url']){data[i]['img_url']=data[i]['weibo_url'].replace('http://','//');width_img='';}

                        if(isNba){
                            width_img = '';
                        }

                        $(".card_wrap").prepend('<div class="card_mode '+width_img+'"><ul class="feed_items"><li class="feed_items_list"><div class="feed_mate"> <strong style="color:' + data[i]['text_color'] + '">' + data[i]['user_chn'] + ':</strong> <p style="color:' + data[i]['text_color'] + '">' + data[i]['live_time'].substr(10, 6) + '</p></div><div class="feed_txt" style="color:' + data[i]['text_color'] + '"><span class="dengxaing-keleyi-com"><a title="' + data[i]['live_text'] + '" href="' + data[i]['img_url'].replace('&_abc=.gif', '') + '" target="_blank">' + data[i]['live_text'] + '</a></span></div></li></ul></div>')
//                      $('.dengxaing-ke' + 'leyi-com').magnificPopup({ //data['img_url'] = data['img_url'].replace('&_abc=.gif', '');
//                          delegate: 'a',
//                          type: 'image',
//                          tLoading: 'Loading image #%curr%...',
//                          mainClass: 'mfp-img-mobile',
//                          gallery: {
//                              enabled: true,
//                              navigateByImgClick: true,
//                              preload: [0,1] // Will preload 0 - before current, and 1 after the current image
//                          },
//                          image: {
//                              tError: '<a href="%url%">The image #%curr%</a> could not be loaded.',
//                              titleSrc: function(item) {
//                                  return item.el.attr('title');
//                              }
//                          }
//                      });
                    } else if (!isNull(data[i]['live_text']) && data[i]['guess_id'] == 0 && isNull(data[i]['text_url'])&& parseInt(data[i]['live_sid'])>now_sid) {
                        if(data[i]['score_status']==0){
                            $(".card_wrap").prepend('<div class="card_mode "><ul class="feed_items"><li class="feed_items_list"><div class="feed_mate"> <strong style="color:' + data[i]['text_color'] + '">' + data[i]['user_chn'] + ':</strong> <p style="color:' + data[i]['text_color'] + '">' + data[i]['live_time'].substr(10, 6) + '</p></div><div class="feed_txt" style="color:' + data[i]['text_color'] + '"><span>' + data[i]['live_text'] + '</span></div></li></ul></div>')
                        }else{
                            $(".card_wrap").prepend('<div class="card_mode "><ul class="feed_items"><li class="feed_items_list"><div class="feed_mate"> <strong style="color:' + data[i]['text_color'] + '">' + data[i]['user_chn'] + ':</strong> <p style="color:' + data[i]['text_color'] + '">' + data[i]['live_time'].substr(10, 6) + '</p></div><div class="feed_txt" style="color:' + data[i]['text_color'] + '"><span>' + data[i]['live_text'] + '['+data[i]['home_score']+'-'+data[i]['visit_score']+']</span></div></li></ul></div>')
                        }
                    } else if(!isNull(data[i]['live_text']) && data[i]['guess_id'] == 0 && !isNull(data[i]['text_url'])&& parseInt(data[i]['live_sid'])>now_sid){
                        $(".card_wrap").prepend('<div class="card_mode "><ul class="feed_items"><li class="feed_items_list"><div class="feed_mate"> <strong style="color:' + data[i]['text_color'] + '">' + data[i]['user_chn'] + ':</strong> <p style="color:' + data[i]['text_color'] + '">' + data[i]['live_time'].substr(10, 6) + '</p></div><div class="feed_txt" style="color:' + data[i]['text_color'] + '"><a  target="_blank" href="'+data[i]['text_url']+'">' + data[i]['live_text'] + '</a></div></li></ul></div>')
                    }else if(data[i]['guess_id']!=0&&parseInt(data[i]['live_sid'])>now_sid){
                    	liveguess.array=[];
                    	var temparr=JSON.parse(data[i]["guess_data"]);
                    	temparr.username=data[i]["user_chn"];
                    	temparr.nowtime=data[i]["live_time"].substr(10,6);
                    	temparr.textcolor=data[i]["text_color"];
                    	liveguess.array.push(temparr);
                    	//console.log(temparr);
                    	$(".card_wrap").append($("#liveguess").html());
                    }
                }
                if(parseInt(data_len)==2&&parseInt(data[data_len-1]['live_sid'])>now_sid){
                    live_sid = parseInt(live_sid)+2;
                }
                if(parseInt(data[data_len-1]['live_sid'])>now_sid){
                    now_sid = parseInt(data[data_len-1]['live_sid']);
                    min_sid = parseInt(data[0]['live_sid']);
                }
            }
            if(is_first==1){
                is_first++;
                old_message_ajax();
            }
            if(load_id<=1){
                $('#loading').html('数据加载完毕!');
                $('#loading').show();
            }
        }
    })
}

function old_message_ajax() {
    if (load_id > 1) {
        $.ajax({
            type: "get",
            dataType: "json",
            async: true,
            url:  dingshi_url+"/livetext/data/cache/livetext/"+saishi_id+"/0/page_10/"+load_id+".htm?get="+Math.random(),
            success: function (data) {
//            	console.log("---old_message_ajax----");
//            	console.log(data);
                load_id = data[0]['live_sid']-1;
                data = data.reverse();
                if (data != null && !$.isEmptyObject(data)) {
                    for (var i in data) {
                    	//console.log(data[i]["live_sid"]+","+now_sid+","+min_sid);
                        if (!isNull(data[i]['live_text']) && data[i]['img_url'].length != 0 && data[i]['guess_id'] == 0&&min_sid>parseInt(data[i]['live_sid'])) {
                        	var width_img='width_img';
                        	if(data[i]['weibo_url']){data[i]['img_url']=data[i]['weibo_url'].replace('http://','//');width_img='';};

                            if(isNba){
                                width_img = '';
                            }

                            $(".card_wrap").append('<div class="card_mode '+width_img+'"><ul class="feed_items"><li class="feed_items_list"><div class="feed_mate"> <strong style="color:' + data[i]['text_color'] + '">' + data[i]['user_chn'] + ':</strong> <p style="color:' + data[i]['text_color'] + '">' + data[i]['live_time'].substr(10, 6) + '</p></div><div class="feed_txt" style="color:' + data[i]['text_color'] + '"><span class="dengxaing-keleyi-com"><a title="' + data[i]['live_text'] + '" href="' + data[i]['img_url'].replace('&_abc=.gif', '') + '" target="_blank">' + data[i]['live_text'] + '</a></span></div></li></ul></div>')
//                          $('.dengxaing-ke' + 'leyi-com').magnificPopup({
//                              delegate: 'a',
//                              type: 'image',
//                              tLoading: 'Loading image #%curr%...',
//                              mainClass: 'mfp-img-mobile',
//                              gallery: {
//                                  enabled: true,
//                                  navigateByImgClick: true,
//                                  preload: [0,1] // Will preload 0 - before current, and 1 after the current image
//                              },
//                              image: {
//                                  tError: '<a href="%url%">The image #%curr%</a> could not be loaded.',
//                                  titleSrc: function(item) {
//                                      return item.el.attr('title');
//                                  }
//                              }
//                          });
                        } else if (!isNull(data[i]['live_text']) && data[i]['guess_id'] == 0 && isNull(data[i]['text_url'])&&min_sid>parseInt(data[i]['live_sid'])) {
                            if(data[i]['score_status']==0){
                                $(".card_wrap").append('<div class="card_mode "><ul class="feed_items"><li class="feed_items_list"><div class="feed_mate"> <strong style="color:' + data[i]['text_color'] + '">' + data[i]['user_chn'] + ':</strong> <p style="color:' + data[i]['text_color'] + '">' + data[i]['live_time'].substr(10, 6) + '</p></div><div class="feed_txt" style="color:' + data[i]['text_color'] + '"><span>' + data[i]['live_text'] + '</span></div></li></ul></div>')
                            }else{
                                $(".card_wrap").append('<div class="card_mode "><ul class="feed_items"><li class="feed_items_list"><div class="feed_mate"> <strong style="color:' + data[i]['text_color'] + '">' + data[i]['user_chn'] + ':</strong> <p style="color:' + data[i]['text_color'] + '">' + data[i]['live_time'].substr(10, 6) + '</p></div><div class="feed_txt" style="color:' + data[i]['text_color'] + '"><span>' + data[i]['live_text'] + '['+data[i]['home_score']+'-'+data[i]['visit_score']+']</span></div></li></ul></div>')
                            }
                        } else if (!isNull(data[i]['live_text']) && data[i]['guess_id'] == 0 && !isNull(data[i]['text_url'])&&min_sid>parseInt(data[i]['live_sid'])) {
                            $(".card_wrap").append('<div class="card_mode "><ul class="feed_items"><li class="feed_items_list"><div class="feed_mate"> <strong style="color:' + data[i]['text_color'] + '">' + data[i]['user_chn'] + ':</strong> <p style="color:' + data[i]['text_color'] + '">' + data[i]['live_time'].substr(10, 6) + '</p></div><div class="feed_txt" style="color:' + data[i]['text_color'] + '"><a  target="_blank" href="' + data[i]['text_url'] + '">' + data[i]['live_text'] + '</a></div></li></ul></div>')
                        }else if(data[i]['guess_id']!=0&&min_sid>parseInt(data[i]['live_sid'])){
                        	liveguess.array=[];
                        	var temparr=JSON.parse(data[i]["guess_data"]);
                        	temparr.username=data[i]["user_chn"];
                        	temparr.nowtime=data[i]["live_time"].substr(10,6);
                        	temparr.textcolor=data[i]["text_color"];
                        	liveguess.array.push(temparr);
                        	//console.log(temparr);
                        	$(".card_wrap").append($("#liveguess").html());
                        }
                    }
                    load_status = true;
                }
                if(is_first==2){
                    max_id = data[0]['live_sid'];
                    is_first++;
                    old_message_ajax();
                }
                if(load_id<=1){
                    $('#loading').html('数据加载完毕!');
                    $('#loading').show();
                }
            },
            error:function(){
                setTimeout(function(){
                    load_status = true;
                },5000)
            }
        })
        //下拉加载更多
    }
}
var liveguess=avalon.define("liveguess",function(vm){
	vm.array=[];
})
$(window).scroll(function(){
    // 当滚动到最底部以上100像素时， 加载新内容
    if ($(document).height() - $(this).scrollTop() - $(this).height()<200&&load_status==true&&load_id>1&&$('#select_1').hasClass('on')){
        $('#loading').show();
        load_status = false;
        old_message_ajax();
    }
});

//时间做处理
function js_strto_time(str_time){
    var new_str = str_time.replace(/:/g,'-');
    new_str = new_str.replace(/ /g,'-');
    var arr = new_str.split("-");
    var datum = new Date(Date.UTC(arr[0],arr[1]-1,arr[2],arr[3]-8,arr[4]));
    return strtotime = datum.getTime()/1000;
}


//历史数据
var dataalalysis = avalon.define("dataalalysis",function (vm){
    vm.jf={};
    vm.zj0={};
    vm.zj1={};
    vm.zj2={};
    vm.zfg={};
    vm.kfg={};
    vm.zdjfb={};
    vm.kdjfb={};
    vm.zjfb={};
    vm.game_type='';
    vm.ht_name=$('#host').html();;
    vm.vt_name=$('#guest').html();;
});
saishi_id=$('#saishi').html();
//saishi_id=document.getElementById('saishi').innerHTML;
//赛前数据
function show_sqsj(){
	var t=new Date();
	var nowTime=t.getFullYear()+"-"+checkTime(t.getMonth()+1)+"-"+checkTime(t.getDate());
	//获取比赛的类型
	if(game_date==nowTime){
		$.getJSON('//dc4pc.qiumibao.com/dc/matchs/sina/'+$('#date_time').html()+'.htm',function(data){
			for(i in data){
		        if((data[i]['team_a']==guest&&data[i]['team_b']==host)||(data[i]['team_a']==host&&data[i]['team_b']==guest)){
		            dataalalysis.game_type=data[i]['data_from'];
		        }
		    }
		});	
	}else{
	//获取比赛的类型
		$.getJSON('//m.zhibo8.cc/json/zhibo/saishi.htm',function(data){
//			console.log(data[0].formatDate);
			for(var i=0;i<data.length;i++){
				if(data[i].formatDate==game_date){
					for(var j=0;j<data[i].list.length;j++){
						if(data[i].list[j].id==saishi_id&&data[i].list[j].label.toLowerCase().indexOf("nba")!=-1){
							dataalalysis.game_type='nba';
							break;
						}
					}
					break;
				}
			}
		});
	}
	//交锋历史
	$.getJSON('//data.zhibo8.cc/json/'+saishi_id+'_his.html',function(data){
		dataalalysis.jf = data[0]==undefined?[]:data[0];
//		console.log(dataalalysis.jf);
	    setTimeout(function(){
	        $('.js_data').each(function(i,v){
	            if(i%2){
	                $(this).addClass('jf_tr_dark');
	            }
	        });
	    },1000);
	});
	
	//最近战绩
	$.getJSON('//data.zhibo8.cc/json/'+saishi_id+'_log.html',function(data){
//		console.log(data[2]);
	    dataalalysis.zj0 = data[0]==undefined?[]:data[0];
	    dataalalysis.zj1= data[1]==undefined?[]:data[1];
	    dataalalysis.zj2= data[2]==undefined?[]:data[2];
//	    console.log(dataalalysis.zj2);
	    setTimeout(function(){
	        $('.zj_h_tr').each(function(i,v){
	            if(i%2){
	                $(this).addClass('jf_tr_dark');
	            }
	        });
	    },1000);
	    setTimeout(function(){
	        $('.zj_v_tr').each(function(i,v){
	            if(i%2){
	                $(this).addClass('jf_tr_dark');
	            }
	        });
	    },1000);
	
	});
	
	//主队未来赛事安排
	$.getJSON('//data.zhibo8.cc/json/'+saishi_id+'_zfg.html',function(data){
	    dataalalysis.zfg = data==undefined?[]:data;
	    setTimeout(function(){
	        $('.zfg').each(function(i,v){
	            if(i%2){
	                $(this).addClass('wl_tr_dark');
	            }
	        });
	    },1000);
	});
	//客队未来赛事安排
	$.getJSON('//data.zhibo8.cc/json/'+saishi_id+'_kfg.html',function(data){
	    dataalalysis.kfg = data==undefined?[]:data;
	    setTimeout(function(){
	        $('.kfg').each(function(i,v){
	            if(i%2){
	                $(this).addClass('wl_tr_dark');
	            }
	        });
	    },1000);
	});
//	var filename=$("#filename").html();
	var zd_type,kd_type;
	if(filename.indexOf('nba')!=-1){
		zd_type='zdphb';kd_type='kdphb';
	}else if(filename.indexOf('zuqiu')!=-1){
		zd_type='zdjfb';kd_type='kdjfb';
		//总积分 （足球）
		$.getJSON('//data.zhibo8.cc/json/'+saishi_id+'_zjfb.html',function(data){
			dataalalysis.zjfb=data==undefined?[]:data;
		});
	}
	
	//nba、cba主队积分排行榜
	$.getJSON('//data.zhibo8.cc/json/'+saishi_id+'_'+zd_type+'.html',function(data){
		dataalalysis.zdjfb=data==undefined?[]:data;
	});
	//nba 、cba客队积分排行榜
	$.getJSON('//data.zhibo8.cc/json/'+saishi_id+'_'+kd_type+'.html',function(data){
		dataalalysis.kdjfb=data==undefined?[]:data;
	});
}

$(function(){
    $(".debug_mesg").on('mouseup', 'a', function(){
        $.ajax({
            url: "//ping.qiumibao.com/ping/index.php?is_wap=1&url="+encodeURIComponent($(this).attr("href"))+'&match_id='+$('#saishi').text()+"&t=" + (new Date()).getTime()+"&callback=?",
            type: "get",
            dataType: "json",
            success: function (data) {

            }
        });
    });
});