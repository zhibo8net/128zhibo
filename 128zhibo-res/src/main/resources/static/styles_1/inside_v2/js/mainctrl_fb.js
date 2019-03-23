 var clcik_2=0,click_3= 0,dinshi_time=10000,s_6=0,s_7=0,s_11=0,curr_num=0;
 /*信号弹出*/
    $('.match_mode_status h1').click(function(){
        $('#bottomNav').css('display','block');
        $('#overDiv').css('display','block');
    })
    $('.debug_switch').click(function(){
        $('#bottomNav').css('display','none');
        $('#overDiv').css('display','none');
    })
    $(function(){
		//返回不登陆
    	$(".head_rec a").click(function(){
    		$(this).parents(".login_box").css('display','none');
    		$('#overDiv').hide();
    	});
        var dia_content = '';
        /*视频信号*/
        var video_html = $('#video_notice').html();
        var   re =new RegExp("<a.*?<\/a>",'g');
        var re_resule;
        while((re_resule=re.exec(video_html))!=null){
            dia_content +='<li>'+re_resule+'</li>';
        }
        $('.debug_mesg').append(dia_content);
        if($('.debug_mesg li').length<=1){
            $('.match_mode_status h1').html('文字直播');
            $('.match_mode_status h1').unbind('click');
        }else{
            $('.match_mode_status h1').html('视频直播');
        }
        
        
        get_match_code('match_high_speed');
        /*切换*/
        $('.live_nav_items li a').click(function(){
            //设定定时刷新时间
            var sel_id=$(this).parent().attr('id');
            var start=sel_id.indexOf("_");
            var select_num  = $(this).parent().attr('id').substring(start+1);
            var select_num=parseInt(select_num);
            curr_num=select_num;
            $(this).parent().addClass("on");
            $(this).parent().siblings().removeClass("on")
            $('.main_wrap section').each(function(){
                $(this).css('display','none');
            })
            $('#section_'+select_num).css('display','block');
            /*首发接口*/
            if(select_num==2){
                if(clcik_2==0){
                    get_match_code('match_lineup');
//                                  console.log(game_over);
                    if(!game_over) {
                        setInterval(function () {
                            get_match_code('match_lineup');
                        }, dinshi_time);
                    }
                    clcik_2++;
                }
            }else if(select_num==3){
                if(click_3==0){
                	show_sqsj();
                    get_match_code('match_event');
                    get_match_code('match_team_statics');
                    if(!game_over){
                        setInterval(function(){
                            get_match_code('match_event');
                            get_match_code('match_team_statics');
                        },dinshi_time);
                    }
                    click_3++;
                }
            }else if(select_num==11&&s_11==0){
            	s_11++;
            	var url_cai='//data.zhibo8.cc/match/caipiao/'+game_date+'/'+saishi_id+".html";
            	var t=$("nav").offset().top;
            	$("#section_11").height($(window).height()-t-44);
            	$("#section_11 iframe").attr("src",url_cai).height($(window).height()-t-44);  
            }else if(select_num=='6' && typeof loadMall =='function'&&s_6==0){//商城
        		s_6++;
        		mall=new loadMall($('#section_'+select_num));
        		mall.init();
            }else if(select_num==7 && typeof DynamicIcon =='function' && s_7==0){
            	s_7++;
            	var d=new DynamicIcon($('#section_'+select_num));
            	d.init();
            }
            var l=$(this).parent().offset().left;
            var s=$(this).parent().parent().scrollLeft();
            $(this).parent().addClass("on").parent().animate({"scrollLeft":l+s-$(window).width()/4},300);
            $(this).parent().siblings().removeClass("on");
            
            $('.main_wrap section').each(function(){
	            $(this).css('display','none');
	        });
            $('#section_'+select_num).css('display','block');
//          if(select_num==8||select_num==9||select_num==10){
//          	$('#section_none').css('display','block');
//          }else{
//          	setDomainCookie("redirect",1,".zhibo8.cc");
//          }
            
            
        });
        
        //当存在战报、集锦、录像时 设置地址
    	var url='//m.zhibo8.cc/json/match/'+$("#saishi").html()+'.htm';
        $.getJSON(url,function(data){
        	var str='';
        	if(!data['gallery_url']){
        		$("#select_7").hide();
        	}
        	if(data['zhanbao_url']){
        		var zhanbao="/news/web"+data['zhanbao_url'];
        		str+='<li id="select_8"><a title="战报" href="'+zhanbao+'">战报</a></li>';
    		}
        	if(data['jijin_url']){
        		var jijin=data['jijin_url'];
        		str+='<li id="select_10"><a title="集锦" href="'+jijin+'">集锦</a></li>';
    		}
        	if(data['luxiang_url']){
        		var luxiang=data['luxiang_url'];
        		str+='<li id="select_10"><a title="录像" href="'+luxiang+'">录像</a></li>';
    		}
        	$(".live_nav ul").append(str);
        });
    });