    var li_p_interval_id = 0,
        qie_huan = 0,
        is_click = true,
        s_6 = 0,
        s_7 = 0,
        s_11 = 0,
        mall = null,
        curr_num = 0;
    $(function () {
        /*信号弹出*/
        $('.match_mode_status h1').click(function () {
            $('#bottomNav').css('display', 'block');
            $('#overDiv').css('display', 'block');
        })
        $('.debug_switch').click(function () {
            $('#bottomNav').css('display', 'none');
            $('#overDiv').css('display', 'none');
        })
        setTimeout(new_bifen_data, 500);
        bifen_settime = setInterval(new_bifen_data, 5000);
        //返回不登陆
        $(".head_rec a").click(function () {
            $(this).parents(".login_box").css('display', 'none');
            $('#overDiv').hide();
        });
        /*视频信号*/
        var dia_content = '';
        var video_html = $('#video_notice').html();
        var re = new RegExp("<a.*?<\/a>", 'g');
        var re_resule;
        while ((re_resule = re.exec(video_html)) != null) {
            dia_content += '<li>' + re_resule + '</li>';
        }
        $('.debug_mesg').append(dia_content);
        if ($('.debug_mesg li').length <= 1) {
            $('.match_mode_status h1').html('文字直播');
            $('.match_mode_status h1').unbind('click');
        } else {
            $('.match_mode_status h1').html('视频直播');
        }
        $('.live_nav_items li a').click(function () {
            if (is_click) {
                //设定定时刷新时间
                var sel_id = $(this).parent().attr('id');
                var start = sel_id.indexOf("_");
                var select_num = $(this).parent().attr('id').substring(start + 1);
                var select_num = parseInt(select_num);
                curr_num = select_num;
                if (select_num == 3) {
                    $(".j_teamTabs span").removeClass("cur");
                    $(".j_teamTabs span").eq(0).addClass("cur");
                    $(".j_teamLineup").addClass("hide");
                    $(".j_teamLineup").eq(0).removeClass("hide");
                    if (!game_over && qie_huan == 0) {
                        set_interval(30000);
                    }
                    if (qie_huan == 0) {
                        is_click = false;
                        get_match_code('player');
                        get_match_code('stats_team');
                        get_match_code('roster_oncourt');
                        get_match_code('score_rank');
                        get_match_code('score_period');
                        get_match_code('bifen');
                        show_sqsj();
                        setTimeout(function () {
                            is_click = true;
                        }, 10)
                    }
                    setTimeout(function () {
                        if (qie_huan == 0) {
                            a();
                            bb();
                            qie_huan++;
                        }
                    }, 10)
                    clearInterval(li_p_interval_id);
                    li_p_interval_id = setInterval(li_p_length, 3000);
                } else if (select_num == 11 && s_11 == 0) { //赔率
                    s_11++;
                    var url_cai = '//data.zhibo8.cc/match/caipiao/' + game_date + '/' + saishi_id + ".html";
                    var t = $("nav").offset().top;
                    $("#section_11").height($(window).height() - t - 44);
                    $("#section_11 iframe").attr("src", url_cai).height($(window).height() - t - 44);
                } else if (select_num == '6' && typeof loadMall == 'function' && s_6 == 0) { //商城
                    s_6++;
                    mall = new loadMall($('#section_' + select_num));
                    mall.init();
                } else if (select_num == 7 && typeof DynamicIcon == 'function' && s_7 == 0) {
                    s_7++;
                    var d = new DynamicIcon($('#section_' + select_num));
                    d.init();
                }
                var l = $(this).parent().offset().left;
                var s = $(this).parent().parent().scrollLeft();
                $(this).parent().addClass("on").parent().animate({
                    "scrollLeft": l + s - $(window).width() / 4
                }, 300);
                $(this).parent().siblings().removeClass("on");
                $('.main_wrap section').each(function () {
                    $(this).css('display', 'none');
                })
                $('#section_' + select_num).css('display', 'block');
                //              if(select_num==8||select_num==9||select_num==10){
                //              	$('#section_none').css('display','block');
                //              }else{
                //              	setDomainCookie("redirect",1,".zhibo8.cc");
                //              }
            }
        });
        //当存在战报、集锦、录像时 设置地址
        var url = '//m.zhibo8.cc/json/match/' + $("#saishi").html() + '.htm';
        $.getJSON(url, function (data) {
            var str = '';
            if (data['zhanbao_url']) {
                var zhanbao = "/news/web" + data['zhanbao_url'];
                str += '<li id="select_8"><a title="战报" href="' + zhanbao + '">战报</a></li>';
            }
            if (data['jijin_url']) {
                var jijin = data['jijin_url'];
                str += '<li id="select_10"><a title="集锦" href="' + jijin + '">集锦</a></li>';
            }
            if (data['luxiang_url']) {
                var luxiang = data['luxiang_url'];
                str += '<li id="select_10"><a title="录像" href="' + luxiang + '">录像</a></li>';
            }
            $(".live_nav ul").append(str);
        });
    })