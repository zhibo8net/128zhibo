function J_get(name, url) {
    url = url ? url : self.window.document.location.href;
    var start = url.indexOf(name + '=');
    if (start == -1) return '';
    var len = start + name.length + 1;
    var end = url.indexOf('&', len);
    if (end == -1) end = url.length;
    return url.substring(len, end);
}


//判断输入字符串是否为空或者全部都是空格
function isNull(str) {
    if (str == "") return true;
    var regu = "^[ ]+$";
    var re = new RegExp(regu);
    return re.test(str);
}
var game_date = $("#game_time").html();
var saishi_id = $("#saishi").html();

//首发
var lineup = (function () {
    var vm = {}
    vm.lineup = {};
    vm.team_name = {};
    vm.coach = {};
    vm.zhenxing = {};
    vm.posX = {
        "GK": 0,
        "D1": 1,
        "D2": 2,
        "DM": 3,
        "M": 4,
        "AM": 5,
        "A": 6
    };
    vm.posY = {
        "R": 0,
        "CR": 1,
        "C": 2,
        "CL": 3,
        "L": 4
    };
    vm.winW = parseInt($(window).width()) >= 620 ? 620 : parseInt($(window).width());
    vm.winH = parseInt($(window).width()) >= 620 ? 1140 : parseInt($(window).width() * 1140 / 620);
    return vm
})();

//实时统计
var statics = (function () {
    var vm = {};
    vm.statics = {};
})();
//足球比赛时间
var events = (function () {
    var vm = {};
    vm.events = {};
})();
//比赛信息
var speed = (function () {
    var vm = {};
    vm.speed = {};
})();


//支持球队

function get_support_data(saishi_id) {
    $.ajax({
        type: "getjson",
        dataType: "json",
        async: true,
        url: "//up.qiumibao.com/count/getResult.php?jsoncallback=?",
        data: {
            filenames: 'match_' + saishi_id + '_host,match_' + saishi_id + '_visit'
        },
        success: function (data) {
            if (!data[1]['count']) {
                $('.guest_good span').html(0);
            } else {
                $('.guest_good span').html(data[1]['count']);
            }
            if (!data[0]['count']) {
                $('.host_good span').html(0);
            } else {
                $('.host_good span').html(data[0]['count']);
            }
            //支持比例
            if (data[0]['count'] && data[1]['count']) {
                var width_scale = parseInt(data[1]['count']) / parseInt(parseInt(data[1]['count']) + parseInt(data[0]['count'])) * 100;
                $('.line_width').css('width', (100 - width_scale) + '%');
            }
        }
    })
}

function support_data(id, type) {
    $.ajax({
        type: "getjson",
        dataType: "json",
        async: true,
        url: "//up.qiumibao.com/count/index.php?callback=?",
        data: {
            filename: id
        },
        success: function (data) {
            if (type == 'guest') {
                if (!data.num) {
                    data.num = 0
                }
                $('.guest_good span').html(data.num);
                //支持比例
                var width_scale = parseInt($('.host_good span').html()) / parseInt(parseInt(data.num) + parseInt($('.host_good span').html())) * 100;
                $('.line_width').css('width', width_scale + '%');
            } else if (type == 'host') {
                if (!data.num) {
                    data.num = 0
                }
                $('.host_good span').html(data.num);
                //支持比例
                var width_scale = parseInt($('.host_good span').html()) / parseInt(parseInt(data.num) + parseInt($('.guest_good span').html())) * 100;
                $('.line_width').css('width', width_scale + '%');
            }
        }
    })
}
var game_over = false,
    game_id = 0,
    saishi_interval, lineup_now_code = 0,
    statics_now_code = 0,
    event_now_code = 0,
    high_speed_code = 0,
    team_a_id = 0,
    team_b_id = 0;

$(function () {
    game_id = $("#saishi").html();
})
var dc_url = '//dc4pc.qiumibao.com';

function saishi() {
    $.ajax({
        type: "get",
        dataType: "json",
        async: true,
        url: dc_url + "/dc/matchs/data/" + game_date + ".htm?get=" + Math.random(),
        success: function (data) {
            for (var i in data) {
                var patt1 = new RegExp(data[i]['team_a']);
                var patt2 = new RegExp(data[i]['team_b']);
                if ((patt1.test(guest) && patt2.test(host)) || (patt1.test(host) && patt2.test(guest))) {
                    game_id = data[i]['id']
                }
            }
        }
    }).done(function () {
        get_match_code('match_high_speed');
    })
}


//球员当场统计
function get_match_code(type) {
    if (game_id > 0) {
        clearInterval(saishi_interval);
        $.ajax({
            type: "get",
            dataType: "json",
            async: true,
            url: dc_url + "/dc/matchs/data/" + game_date + "/" + type + "_" + game_id + "_code.htm?get=" + Math.random(),
            success: function (data) {
                if (type == 'match_event' && event_now_code != parseInt(data)) {
                    get_match_data('match_event');
                } else if (type == 'match_lineup' && lineup_now_code != parseInt(data)) {
                    //console.log('lineup_now_code'+lineup_now_code);
                    get_match_data('match_lineup');
                } else if (type == 'match_team_statics' && statics_now_code != parseInt(data)) {
                    get_match_data('match_team_statics');
                } else if (type == 'match_high_speed' && high_speed_code != parseInt(data)) {
                    get_match_data('match_high_speed');
                }
            }
        })
    }
}

function get_match_data(type) {
    $.ajax({
        type: "get",
        dataType: "json",
        async: true,
        url: dc_url + "/dc/matchs/data/" + game_date + "/" + type + "_" + game_id + ".htm?get=" + Math.random(),
        success: function (data) {
            if (type == 'match_event') {
                var event_data = data;
                event_now_code = parseInt(event_data.code);
                if (event_data.data.length > 0 && event_data.data != null && !$.isEmptyObject(data.data)) {
                    $('.no_data_3').hide();
                    for (var i in event_data.data) {
                        //console.log(team_a_id);
                        if (event_data.data[i].sl_team_id == team_a_id) {
                            event_data.data[i].is_host = 1;
                        } else {
                            event_data.data[i].is_host = 0;
                        }
                    }

                }
                events.events = event_data.data;
                //                console.log(data);
            } else if (type == 'match_lineup') {
                //              console.log(data);
                lineup_now_code = parseInt(data.code);
                var json_data = data;
                if (parseInt(data.code) > 0 && data.data != null && !$.isEmptyObject(data.data)) {
                    var lineup_data = data,
                        param = new Object(),
                        param2 = new Object(),
                        param3 = new Object();

                    for (var i in lineup_data.data) {
                        if (i == team_a_id) {
                            param[0] = lineup_data.data[i];
                            param2[0] = lineup_data.coach[i];
                            param3[0] = lineup_data[i];
                        }
                        if (i == team_b_id) {
                            param[1] = lineup_data.data[i];
                            param2[1] = lineup_data.coach[i];
                            param3[1] = lineup_data[i];
                        }
                    }
                    lineup.lineup = param;
                    lineup.coach = param2;
                    lineup.zhenxing = param3;
                    //                  lineup.lineup=lineup_data.data;
                    $('.no_data_2').hide();
                }
            } else if (type == 'match_team_statics') {
                //                console.log(data);
                statics_now_code = parseInt(data.code);
                if (parseInt(data.code) > 0 && data.data != null && !$.isEmptyObject(data.data)) {
                    var statics_data = data;
                    for (var i in statics_data.data) {
                        if (i == team_a_id) {
                            statics_data.data[0] = statics_data.data[i];
                        }
                        if (i == team_b_id) {
                            statics_data.data[1] = statics_data.data[i];
                        }
                    }
                    //                    console.log(statics_data);
                    statics.statics = statics_data.data;
                    $('.no_data_3').hide();
                }
            } else if (type == 'match_high_speed') {
                //                console.log(data);
                var speed_data = data;
                high_speed_code = parseInt(speed_data.code);
                speed.speed = speed_data.data;
                speed.speed.home_logo = '';
                speed.speed.away_logo = '';
                team_a_id = speed_data.data.Team1Id;
                team_b_id = speed_data.data.Team2Id;
                if (speed_data.data.period_cn == "完赛") {
                    game_over = true;
                }
            }
        }
    })
}