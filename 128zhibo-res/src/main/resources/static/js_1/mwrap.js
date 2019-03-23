$(function () {
    var clcik_2 = 0,
        click_3 = 0,
        dinshi_time = 10000,
        s_6 = 0,
        s_7 = 0,
        s_11 = 0,
        curr_num = 0;
    var game_over = false,
        game_id = 0,
        saishi_interval, lineup_now_code = 0,
        statics_now_code = 0,
        event_now_code = 0,
        high_speed_code = 0,
        team_a_id = 0,
        team_b_id = 0;

    function locationToRe(param) {
        $('.textfb,.post_content,#post_content').focus();
    }
    //支持比例
    function changeLine(type, num) {
        if (type == 'guest') {
            if (!num) {
                num = 0
            }
            $('.guest_good span').html(num);
            //支持比例
            var width_scale = parseInt($('.host_good span').html()) / parseInt(parseInt(num) +
                parseInt($('.host_good span').html())) * 100;
            $('.line_width').css('width', width_scale + '%');
        } else if (type == 'host') {
            if (!num) {
                num = 0
            }
            $('.host_good span').html(num);
            //支持比例
            var width_scale = parseInt($('.host_good span').html()) / parseInt(parseInt(num) +
                parseInt($('.guest_good span').html())) * 100;
            $('.line_width').css('width', width_scale + '%');
        }
    }
    $('.host_good a').click(function () {
        var num = $('.host_good span').html();
        ++num;
        $('.host_good span').html(num);
        changeLine('host', num)
    });
    $('.guest_good a').click(function () {
        var num = $('.guest_good span').html();
        ++num;
        $('.guest_good span').html(num);
        changeLine('guest', num)
    })


    $('.live_nav_items li a').click(function () {
        //设定定时刷新时间
        var sel_id = $(this).parent().attr('id');
        var start = sel_id.indexOf("_");
        var select_num = $(this).parent().attr('id').substring(start + 1);
        var select_num = parseInt(select_num);
        curr_num = select_num;
        $(this).parent().addClass("on");
        $(this).parent().siblings().removeClass("on")
        $('.main_wrap section').each(function () {
            $(this).css('display', 'none');
        })
        $('#section_' + select_num).css('display', 'block');
        /*首发接口*/
        if (select_num == 2) {
            if (clcik_2 == 0) {
                // get_match_code('match_lineup');
                //                                  console.log(game_over);
                if (!game_over) {
                    setInterval(function () {
                        // get_match_code('match_lineup');
                    }, dinshi_time);
                }
                clcik_2++;
            }
        } else if (select_num == 3) {
            if (click_3 == 0) {
                // show_sqsj();
                // get_match_code('match_event');
                // get_match_code('match_team_statics');
                if (!game_over) {
                    setInterval(function () {
                        // get_match_code('match_event');
                        // get_match_code('match_team_statics');
                    }, dinshi_time);
                }
                click_3++;
            }
        } else if (select_num == 11 && s_11 == 0) {
            s_11++;
            // var url_cai = '//data.zhibo8.cc/match/caipiao/' + game_date + '/' + saishi_id +
            //     ".html";
            // var t = $("nav").offset().top;
            // $("#section_11").height($(window).height() - t - 44);
            // $("#section_11 iframe").attr("src", url_cai).height($(window).height() - t - 44);
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
        });
        $('#section_' + select_num).css('display', 'block');
        //          if(select_num==8||select_num==9||select_num==10){
        //          	$('#section_none').css('display','block');
        //          }else{
        //          	setDomainCookie("redirect",1,".zhibo8.cc");
        //          }


    });
})