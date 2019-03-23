
//当前时间时间
function now_time(){
    var today=new Date();
    var y=today.getFullYear();
    var m=checkTime(today.getMonth()+1);
    var d=checkTime(today.getDate());
    var h=checkTime(today.getHours());
    var n=checkTime(today.getMinutes());
    var s=checkTime(today.getSeconds());

    return  y + '-' + m + '-' + d + ' ' + h + ':' + n + ':' +s;
}
function checkTime(i){
	if (i<10){i="0" + i;}

	return i;
}
//获取竞猜数据
function guessData(backFunc){
//	filename='2016-nba-0118leitingvsrehuo';
	$.ajax({
        url:'//guess.zhibo8.cc/guess/?file='+filename+'&format=json',
        dataType:'jsonp',
        timeout:3000,
        success:function(data){
//      	console.log(data);
        	return backFunc(data.guess);
//          for(var i in data.guess){
//              jc_ids+=','+data.guess[i].id;
//          }
//
//          guess.array=data.guess.concat(jc_lg); //文字直播里的竞猜也加入
        },
        error:function(){
            guess.array=[];
            $(".jc li .jcli_tit:first").html('<a href="javascript:;" onclick="$(this).text(\'加载中...\');load_guess(0);">点击重新加载</a>');
        }
    });
}
//文字列表竞猜投注中转
function touzhu(str){
    str+='';

    if(str.indexOf('|')==-1){return;}

    var arr=str.split('|');

    if(arr.length==4){
        guess.show(arr[0],arr[1],arr[2],arr[3]);
    }
}
$(function(){
	var firstClick=0;
	$("#select_5").on("click",function(){
		if(firstClick==0){
			guessData(function(data){
				guess.array=data;
				firstClick=1;
			});
		}
	});
	//跳出键盘时候，改变投注框的显示位置
	var initHeight= $(window).height();
	$(window).resize(function(){
		var afterHieght=$(this).height();
		if(parseInt(initHeight)-parseInt(afterHieght)>200){
			$("#touzhu_box").css("bottom","-120px");
		}else{
			$("#touzhu_box").css("bottom","50px");
		}
	});
});
//竞猜
var guess = avalon.define("guess",function (vm){
    vm.array=[];
    //vm.array=JSON.parse('[{"id":"6761","filename":"2015-zuqiu-0804shanghaishanggangvsmadelijingji","type":"","title":"\u3010\u8db3\u7403\u53cb\u8c0a\u8d5b \u4e0a\u6d77\u4e0a\u6e2f\u8fdb\u7403\u3011\u672c\u573a\u6bd4\u8d5b\uff0c\u4e0a\u6d77\u4e0a\u6e2f\u80fd\u5426\u53d6\u5f97\u8fdb\u7403\uff1f\uff0808\u670804\u65e5 19:00\uff09 ","description":"","mingold":"1","status":"normal","terminaltime":"2015-08-01 19:00:00","creater":"\u94dc\u724c\u7ecf\u7eaa\u4eba","updater":"","createtime":"2015-07-26 09:38:12","updatetime":"0000-00-00 00:00:00","items":[{"id":"13877","guess_id":"6761","optName":"\u80fd","optNum":"499","odds":"1.9","status":"0","createtime":"2015-07-26 09:38:15","updatetime":"0000-00-00 00:00:00","creater":"\u94dc\u724c\u7ecf\u7eaa\u4eba","updater":"","gold":"340129"},{"id":"13878","guess_id":"6761","optName":"\u4e0d\u80fd","optNum":"100","odds":"1.9","status":"0","createtime":"2015-07-26 09:38:17","updatetime":"0000-00-00 00:00:00","creater":"\u94dc\u724c\u7ecf\u7eaa\u4eba","updater":"","gold":"49380"}],"verifier":"","maxgold":"2000","answer_id":"0","g_order":"0","num":"599","gold":"389509","open_status":null}]');

    vm.arrtype=0;
    //user.jc_task.userinfo.gold=0;

    vm.show=function(guess_id,answer_id,odds,maxgold){
        user.order= {"guess_id":guess_id,"answer_id":answer_id,"odds":odds,"maxgold":maxgold}; 
        //$('#bottomNav').show();
        $('#overDiv').show();

        //弹出框初始化
        $('.alert').html('');
        $('.alert').removeClass('alert-success');
        $('.alert').removeClass('alert-danger');

        if(!cookie('bbs_username')){
        	$(".login_box").css("display","block");
        	$(document).scrollTop(0);
        	$('.login_box').bind("touchmove",function(e){    
           		e.preventDefault();    
        	});
        	//location.href="login.html";
//
//          $('.alert').html('请登陆后进行操作');
//          $('.alert').removeClass('alert-success');
//          $('.alert').addClass('alert-danger');
//          $('.earn_gold').html(0);
//          $('.modal-header b').html(0);
        }else{
        	$('#touzhu_box').show();
            //用户金币数量
            if(user.jc_task.userinfo.gold == 0){
                $.getJSON('//guess.zhibo8.cc/guess/ajax/encourageInfo.php?onlygold=1&callback=?',function(data){
                    user.jc_task.userinfo.gold = data.userinfo.gold;
                });
            }
        }
    };
});
//用户信息
var bbs_username = cookie('bbs_username');
var host_url = '//guess.zhibo8.cc';

var user = avalon.define("user",function(vm){
    vm.jc_task = {};
    vm.order = {};

    vm.jc_task.userinfo= {"gold":0};
    vm.order= {"guess_id":0,"answer_id":0,"odds":0,"maxgold":0};
	vm.hideBox=function(){
		$('body').bind("touchmove",function(e){    
            
        }); 
    	$('#touzhu_box').hide();
        $('#overDiv').hide();
    };
    vm.income = function(){
        var num =Math.floor($(this).val());
        var user_gold = Math.floor($('.modal-header b').html());
        if(!bbs_username){
            $('.alert').html('请登陆后进行操作');
            $('.alert').removeClass('alert-success');
            $('.alert').addClass('alert-danger');
            $('.earn_gold').html(0);
            return false;
        }
        if(num<=0){
            $('.alert').html('投注金币要大于0');
            $('.alert').removeClass('alert-success');
            $('.alert').addClass('alert-danger');
            $('.earn_gold').html(0);
            return false;
        }else{
            $('.alert').html('');
            $('.alert').removeClass('alert-danger');
            $('.alert').removeClass('alert-success');
        }
        if(num>user_gold){
            $('.alert').html('您的金币不够');
            $('.alert').removeClass('alert-success');
            $('.alert').addClass('alert-danger');
            $('.earn_gold').html(0);
            return false;
        }else{
            $('.alert').html('');
            $('.alert').removeClass('alert-danger');
            $('.alert').removeClass('alert-success');
        }

        var odds =  $("input[name='odds']").val();
        var result = Math.floor(num*odds);
        $('.earn_gold').html(result);
    };

    vm.tz_submit = function(){
        var num =Math.floor($("input[name='number']").val());
        var user_gold = Math.floor($('.modal-header b').html());
        if(!bbs_username){
            $('.alert').html('请登陆后进行操作');
            $('.alert').removeClass('alert-success');
            $('.alert').addClass('alert-danger');
            $('.earn_gold').html(0);
            return false;
        }

        if(num<=0){
            $('.alert').html('投注金币要大于0');
            $('.alert').removeClass('alert-success');
            $('.alert').addClass('alert-danger');
            $('.earn_gold').html(0);
            return false;
        }else{
            $('.alert').html('');
            $('.alert').removeClass('alert-danger');
            $('.alert').removeClass('alert-success');
        }

        if(num>user_gold){
            $('.alert').html('投注金币超过限制!');
            $('.alert').removeClass('alert-success');
            $('.alert').addClass('alert-danger');
            $('.earn_gold').html(0);
            return false;
        }else{
            $('.alert').html('');
            $('.alert').removeClass('alert-danger');
        }

        var answer_id = $("input[name='answer_id']").val();
        var guess_id = $("input[name='guess_id']").val();

        $.getJSON(host_url+"/guess/ajax/guessOrderSave.php?answer_id="+answer_id+"&guess_id="+guess_id+"&credit="+num+'&callback=?',function(data){
            if(data.code==0){
                var result_data = data.data;
                //var guess_id = $("input[name='guess_id']").val();

                user.jc_task.userinfo.gold = data.data.user_gold;

                $('.alert').html('投注成功');
                $('.alert').addClass('alert-success');

                //更新投注信息
                $('#g_'+guess_id+' .jcli_rp').each(function(i){
                    var percent = Math.floor(100*(result_data.items[i].optNum/result_data.num));

                    $(this).find(".optNum").html(result_data.items[i].optNum);
                    $(this).find(".gold").html(result_data.items[i].gold);
                    $(this).find(".jcli_rp_jd_wz").html(percent+"%");
                    $(this).find(".jcli_rp_jd_k").css("width",parseInt(percent)+"%");
                });

                setTimeout(function(){
                    $('#touzhu_box').hide();
                    $('#overDiv').hide();
                },500);
            }else{
                $('.alert').html(data.info);
                $('.alert').addClass('alert-danger');
            }
        });
    };
});

