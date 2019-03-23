
function J_get(name, url)
{
    url  = url?url:self.window.document.location.href;
    var start	= url.indexOf(name + '=');
    if (start == -1) return '';
    var len = start + name.length + 1;
    var end = url.indexOf('&',len);
    if (end == -1) end = url.length;
    return url.substring(len,end);
}

//监听拖动区域高度变化，重新获取高度
var old_p_length=0;
function li_p_length(){
    var p_length = $(".j_scroller li p").length;
    if(p_length!=old_p_length&&$('#select_3').hasClass('on')&&$('.contTab span').eq(0).hasClass('cur')){
        bb();
        old_p_length = p_length;
    }
}

var is_dong=false;
// 统计滑动
function bb() {
    if(!is_dong){
        is_dong = true;
        if ($(".j_wrapper").length > 0) {
            var c = $(".j_wrapper").length;
            $(".j_wrapper").each(function(n,item) {
                var i = $(this),
                    f = i.find(".j_scroller"),
                    k = f.find("li"),
                    h = k.length * k.width(),
                    g = k.height(),
                    e = i.parent().find(".btn_l"),
                    j = i.parent().find(".btn_r");
                if (k.length > 4) {
//                  i.attr("id", "wrapper" + new Date().getTime());
                    k.css("width", k.width());
//                  f.css("width", h).css("height", g);
					f.css("width", h);
//                  i.css("height", g);
                    e.on("click",function(){
                    	var scrollSize=k.width();
						var currLeft=i.scrollLeft();
						currLeft=currLeft-currLeft%scrollSize-scrollSize;
						i.animate({scrollLeft:currLeft+"px"},"slow");
                    });
                    j.on("click",function(){
                    	var scrollSize=k.width();
						var currLeft=i.scrollLeft();
						currLeft=currLeft-currLeft%scrollSize+scrollSize;
						i.animate({scrollLeft:currLeft+"px"},"slow");
                    });
//                  var d = new iScroll(i.attr("id"), {
//                      hScroll: true,
//                      vScroll: true,
//                      hScrollbar: false,
//                      bounce: true,
//                      momentnum: true,
//                      lockDirection: false,
//                      onScrollMove: function() {
//                      },
//                      onScrollEnd: function() {
//                      }
//                  });
//                  e.on("click tap",
//                      function() {
//                          if (!e.hasClass("end")) {
//                              d.scrollTo( - parseInt(k.width()), 0, 100, true)
//                          }
//                      });
//                  j.on("click tap",
//                      function() {
//                          if (!j.hasClass("end")) {
//                              d.scrollTo(parseInt(k.width()), 0, 100, true)
//                          }
//                      })
                }
            })
            is_dong = false;
        }
    }

}

//统计切换
function a() {
    $(".j_teamTabs span").on("click tap",
        function() {
            var d = $(this),
                c = d.index(),
                e = d.parent().find("span"),
                f = d.parent().next().find(".j_teamLineup");
            if (!d.hasClass("cur")) {
                e.removeClass("cur");
                f.addClass("hide");
                e.eq(c).addClass("cur");
                f.eq(c).removeClass("hide");
            }
           // bb();
        })
}

//判断输入字符串是否为空或者全部都是空格
function isNull( str ){
    if ( str == "" ) return true;
    var regu = "^[ ]+$";
    var re = new RegExp(regu);
    return re.test(str);
}

var json_data;
//球员当场统计
var statistics  = avalon.define("statistics", function(vm){
    vm.statistics  = {};
});
//球队当场比赛各项数据
var stats_team = avalon.define("stats_team", function(vm){
    vm.stats_team = {};
});
//各项最高
var score_rank = avalon.define("score_rank", function(vm){
    vm.score_rank = {};
});
//比赛状态
var score_period = avalon.define("score_period", function(vm){
    vm.score_period = {};
});
//球队比分
var bifen = avalon.define("bifen", function(vm){
    vm.bifen = {};
});
//支持球队
function get_support_data(saishi_id){
    $.ajax({
        type: "getjson",
        dataType: "json",
        async: true,
        url:  "//up.qiumibao.com/count/getResult.php?jsoncallback=?",
        data:{filenames:'match_'+saishi_id+'_host,match_'+saishi_id+'_visit'},
        success: function(data){
            if(!data[1]['count']){
                $('.guest_good span').html(0);
            }else{
                $('.guest_good span').html(data[1]['count']);
            }
            if(!data[0]['count']){
                $('.host_good span').html(0);
            }else{
                $('.host_good span').html(data[0]['count']);
            }
            //支持比例
            if(data[0]['count']&&data[1]['count']){
                var width_scale = parseInt(data[1]['count'])/parseInt(parseInt(data[1]['count'])+parseInt(data[0]['count']))*100;
                $('.line_width').css('width',(100-width_scale)+'%');
            }
        }
    })
}

function support_data(id,type){
    $.ajax({
        type: "getjson",
        dataType: "json",
        async: true,
        url:  "//up.qiumibao.com/count/index.php?callback=?",
        data:{filename:id},
        success: function(data){
            if(type=='guest'){
                if(!data.num){data.num=0}
                $('.guest_good span').html(data.num);
                //支持比例
                var width_scale = parseInt($('.host_good span').html())/parseInt(parseInt(data.num)+parseInt($('.host_good span').html()))*100;
                $('.line_width').css('width',width_scale+'%');
            }else if(type=='host'){
                if(!data.num){data.num=0}
                $('.host_good span').html(data.num);
                //支持比例
                var width_scale = parseInt($('.host_good span').html())/parseInt(parseInt(data.num)+parseInt($('.guest_good span').html()))*100;
                $('.line_width').css('width',width_scale+'%');
            }
        }
    })
}

var game_over=false,game_id=0,saishi_interval,player_now_code=0,stats_team_now_code=0, roster_oncourt_now_code=0,score_rank_now_code=0,score_period_now_code=0,bifen_now_code=0;

function saishi(){
    $.ajax({
        type: "get",
        dataType: "json",
        async: true,
        url:"//dc4pc.qiumibao.com/dc/matchs/sina/"+game_date+".htm?get="+Math.random(),
        success: function(data){
            for(i in data){
                if((data[i]['team_a']==guest&&data[i]['team_b']==host)||(data[i]['team_a']==host&&data[i]['team_b']==guest)){
                    game_id=data[i]['id'];
                }
            }
            if(game_id>0){
                get_match_code('score_period');
                get_match_code('bifen');
                get_match_code('player');
            }
        }
    })
}

load_json();
//载入预加载数据
function load_json(){
    var advance = {"player":{"id":"0","code":0,"data":{"host":{"team_id":0,"team_name_cn":"主队","on":[{"player_id":null,"player_name_cn":null,"player_logo":null,"position":null,"minutes":null,"field":null,"three":null,"free":null,"off":null,"def":null,"off+def":null,"ass":null,"ste":null,"blo":null,"turn":null,"fouls":null,"points":null}],"off":[{"player_id":null,"player_name_cn":null,"player_logo":null,"position":null}],"total":{"minutes":null,"field_made":null,"field_att":null,"three_made":null,"three_att":null,"free_made":null,"free_att":null,"off":null,"def":null,"off+def":null,"ass":null,"ste":null,"blo":null,"turn":null,"fouls":null,"points":null,"field":null,"three":null,"free":null}},"guest":{"team_id":0,"team_name_cn":"客队","on":[{"player_id":null,"player_name_cn":null,"player_logo":null,"position":null,"minutes":null,"field":null,"three":null,"free":null,"off":null,"def":null,"off+def":null,"ass":null,"ste":null,"blo":null,"turn":null,"fouls":null,"points":null}],"off":[{"player_id":null,"player_name_cn":null,"player_logo":null,"position":null}],"total":{"minutes":null,"field_made":null,"field_att":null,"three_made":null,"three_att":null,"free_made":null,"free_att":null,"off":null,"def":null,"off+def":null,"ass":null,"ste":null,"blo":null,"turn":null,"fouls":null,"points":null,"field":null,"three":null,"free":null}}}},"stats_team":{"id":0,"code":0,"data":{"guest":{"team_info":{"team_id":null,"team_name":"客场"},"score_board":{"name":"得分/篮板","points":null},"field_rate":{"name":"投篮命中率","points":null},"three_rate":{"name":"三分命中率","points":null},"free_rate":{"name":"罚球命中率","points":null},"fp_points":{"name":"快攻/内线得分","points":null},"fouls":{"name":"技术/恶意犯规","points":null},"diq_ejt":{"name":"六犯/被逐出场","points":null},"biggest":{"name":"最大领先分数","points":null}},"host":{"team_info":{"team_id":null,"team_name":"主场"},"score_board":{"name":"得分/篮板","points":null},"field_rate":{"name":"投篮命中率","points":null},"three_rate":{"name":"三分命中率","points":null},"free_rate":{"name":"罚球命中率","points":null},"fp_points":{"name":"快攻/内线得分","points":null},"fouls":{"name":"技术/恶意犯规","points":null},"diq_ejt":{"name":"六犯/被逐出场","points":null},"biggest":{"name":"最大领先分数","points":null}}}},"roster_oncourt":{"id":"0","code":0,"data":{"host":{"0":{"player_id":0,"points":0,"fouls":0,"player_name":0,"if_pic":0,"period":0,"game_clock":0,"event":0,"player_logo":0}},"guest":{"0":{"player_id":0,"points":0,"fouls":0,"player_name":0,"if_pic":0,"period":0,"game_clock":0,"event":0,"player_logo":0}}}},"score_rank":{"id":"0","code":0,"data":{"host":{"team_info":{"team_id":0,"team_name":"主场"},"points":{"player_id":null,"name":"得分","points":null,"player_name":null},"off+def":{"player_id":null,"name":"篮板","points":null,"player_name":null},"ass":{"player_id":null,"name":"助攻","points":null,"player_name":null},"ste":{"player_id":null,"name":"抢断","points":null,"player_name":null},"blo":{"player_id":null,"name":"盖帽","points":null,"player_name":null},"minutes":{"player_id":null,"name":"时间","points":null,"player_name":null},"turn":{"player_id":null,"name":"失误","points":null,"player_name":null},"per_fouls":{"player_id":null,"name":"犯规","points":null,"player_name":null}},"guest":{"team_info":{"team_id":null,"team_name":"客场"},"points":{"player_id":null,"name":"得分","points":null,"player_name":null},"off+def":{"player_id":null,"name":"篮板","points":null,"player_name":null},"ass":{"player_id":null,"name":"助攻","points":null,"player_name":null},"ste":{"player_id":null,"name":"抢断","points":null,"player_name":null},"blo":{"player_id":null,"name":"盖帽","points":null,"player_name":null},"minutes":{"player_id":null,"name":"时间","points":null,"player_name":null},"turn":{"player_id":null,"name":"失误","points":null,"player_name":null},"per_fouls":{"player_id":null,"name":"犯规","points":null,"player_name":null}}}},"score_period":{"id":"0","code":0,"data":{"remain":{"livecast_id":"0","live":"0","date":0,"time":0,"status":"比赛未开始","period":0},"host":{"team_id":0,"team_conference":0,"team_rank":0,"full_timeouts":"0","short_timeouts":"0","team_fouls":0,"wins":0,"losses":0,"scores":["0","0","0","0"],"team_name":"主队"},"guest":{"team_id":0,"team_conference":0,"team_rank":0,"full_timeouts":"0","short_timeouts":"0","team_fouls":0,"wins":0,"losses":0,"scores":["0","0","0","0"],"team_name":"客队"}}},"score_team":{"id":"0","code":0,"data":{"guest":{"team_id":0,"color":0,"area":0,"score":0,"full_timeouts":"0","short_timeouts":"0","team_fouls":0,"fast_points":0,"points_paint":0,"off_turnovers":0,"biggest":0,"tec_fouls":0,"flag":0,"disqualifications":0,"ejections":0,"team_name":0},"host":{"team_id":0,"color":0,"area":0,"score":0,"full_timeouts":"0","short_timeouts":"0","team_fouls":0,"fast_points":0,"points_paint":0,"off_turnovers":0,"biggest":0,"tec_fouls":0,"flag":0,"disqualifications":0,"ejections":0,"team_name":0}}},"bifen":{"id":"0","code":0,"data":{"Team1Id":0,"Team2Id":0,"Score1":"0","Score2":"0","date":0,"time":0,"quarter":"0","minutes":0,"seconds":0,"status":0,"period":0,"period_cn":"数据加载中","status_cn":0,"Team1":"客队","Team2":"主队","minutes_ex":"0","seconds_ex":0,"team1_scores":["0","0","0","0","0","0","0","0","0","0","0","0","0","0"],"team2_scores":["0","0","0","0","0","0","0","0","0","0","0","0","0","0"]}}};
    //球员当场统计
    statistics.statistics = advance.player.data;

    //球队当场比赛各项数据
    stats_team.stats_team = advance.stats_team.data;

    //各项最高
    score_rank.score_rank = advance.score_rank.data;

    //比赛状态
    score_period.score_period = advance.score_period.data;

    //球队比分
    bifen.bifen = advance.bifen.data;
    setTimeout(function(){
        $('.bifen_status').html(advance.bifen.data.period_cn);
    },100)

    $(function(){
        $('.score_period').css('visibility','visible');
        $('.bifen').css('visibility','visible');
        $('.score_rank').css('visibility','visible');
        $('.stats_team').css('visibility','visible');
        $('.statistics').css('visibility','visible');
    })
   // })
}

var player_interval=0,stats_team_interval=0,roster_oncourt_interval= 0,score_rank_interval=0,score_period_interval=0,bifen_interval=0;
function set_interval(ajax_time){
    player_interval = setInterval("get_match_code('player')",ajax_time);
    stats_team_interval = setInterval("get_match_code('stats_team')",ajax_time);
    roster_oncourt_interval = setInterval("get_match_code('roster_oncourt')",ajax_time);
    score_rank_interval = setInterval("get_match_code('score_rank')",ajax_time);
}
function set_header_interval(ajax_time){
    score_period_interval = setInterval("get_match_code('score_period')",ajax_time);
    bifen_interval = setInterval("get_match_code('bifen')",ajax_time);
}

set_header_interval(10000);
//set_interval(10000);
function clear_interval(){
    clearInterval(player_interval);
    clearInterval(stats_team_interval);
    clearInterval(roster_oncourt_interval);
    clearInterval(score_rank_interval);
}

function clear_header_interval(){
    /*顶部数据*/
    clearInterval(score_period_interval);
    clearInterval(bifen_interval);
}

//球员当场统计
function get_match_code(type){
	game_id=saishi_id;
    if(game_id>0){
        clearInterval(saishi_interval);
        $.ajax({
            type: "get",
            dataType: "json",
            async: true,
            url:"//dc4pc.qiumibao.com/dc/matchs/sina/"+game_date+"/"+type+"_"+game_id+"_code.htm?get="+Math.random(),
            success: function(data){
                if(type=='player'&&player_now_code != data){
                    get_match_data(type);
                }
                if(type=='stats_team'&&stats_team_now_code != data){
                    get_match_data(type);
                }
                if(type=='roster_oncourt'&&roster_oncourt_now_code != data){
                    get_match_data(type);
                }
                if(type=='score_rank'&&score_rank_now_code != data){
                    get_match_data(type);
                }
                if(type=='score_period'&&score_period_now_code !=data ){
                    get_match_data(type);
                }
                if(type=='bifen'&&bifen_now_code != data){
                    get_match_data(type);
                }
            }
        })
    }
}
function get_match_data(type){
    $.ajax({
        type: "get",
        dataType: "json",
        async: true,
        url:"//dc4pc.qiumibao.com/dc/matchs/sina/"+game_date+"/"+type+"_"+game_id+".htm?get="+Math.random(),
        success: function(data){
        	
            if(data.id>=0&&data.data!=null&&!$.isEmptyObject(data.data)) {
                //球员当场统计
                if(type=='player'){
                    player_data = data;
                    player_now_code = player_data.code;
                    statistics.statistics = player_data.data;
                }
                //球队当场比赛各项数据
                if(type=='stats_team'){
                    stats_team_data = data;
                    stats_team_now_code = stats_team_data.code;
                    stats_team.stats_team = stats_team_data.data;
                }

                //场上球员得分犯规数
                if(type=='roster_oncourt'){

                    roster_data = data;
                    roster_oncourt_now_code = roster_data.code;
                    roster_oncourt_data = roster_data.data;

                    /*显示场上球员*/
                    if(!game_over){
                        setTimeout(function(){
                            var team_l = $('.team_l span');
                            team_l.removeClass('on_match');
                            for(var i in roster_oncourt_data){
                                for(var j in roster_oncourt_data[i]){
                                    team_l.each(function(index){
                                        if(parseInt(team_l.eq(index).attr('player_id'))==parseInt(roster_oncourt_data[i][j]['player_id'])){
                                            team_l.eq(index).addClass('on_match');
                                            //console.log(roster_oncourt_data[i][j]['player_name']);
                                        }
                                    });
                                }
                            }
                        },100);
                    }
                }
                //各项最高
                if(type=='score_rank'){
                    score_rank_data = data;
                    score_rank_now_code = score_rank_data.code;
                    score_rank.score_rank = score_rank_data.data;
                }
                //比赛状态
                if(type=='score_period'){
                    score_period_data = data;
                    score_period_now_code = score_period_data.code;
                    score_period.score_period = score_period_data.data;
                    host_id = score_period_data.data.host.team_id;
                    guest_id = score_period_data.data.guest.team_id;
                }
                //球队比分
                if(type=='bifen'){
//              	console.log(data);
                    bifen_data = data;
                    bifen_now_code = bifen_data.code;
                    bifen.bifen = bifen_data.data;
                    $(function(){ $('.bifen_status').html(bifen.bifen.period_cn);})
                    if(bifen_data.data.period==-1){
                        game_over = true;
                        setTimeout(function(){$('.team_l span').removeClass('on_match');},200);
                        setTimeout(clear_header_interval,3000);
                        setTimeout(clear_interval,3000);
                    }
                    if(parseInt(bifen_data.data.team1_scores[4])>0){
                        a();
                    }
                }
            }
        }
    })
}