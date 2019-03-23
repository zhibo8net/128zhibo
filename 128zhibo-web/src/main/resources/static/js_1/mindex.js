(function(window,$,undefined){
    $('.saishi li.lite').each(function () {
        if($(this).find('.masterDefaultImg').length > 0 && $(this).find('.masterTeamName b').html() == ''){
            $(this).find('.masterDefaultImg').show();
        }
        if($(this).find('.guestDefaultImg').length > 0 && $(this).find('.guestTeamName b').html() == ''){
            $(this).find('.guestDefaultImg').show();
        }
    });
    /*------比分数据的分区*/
    //比分
    var list_code;
    function getBifenScore(){
        $.get("//bifen4m.qiumibao.com/json/list.htm",function(data,status){
            //获取已开始的比赛
            var sdate_arr=[];//用于存放已开始比赛的id
            sdate_arr=gameHadStart(sdate_arr);
            var updList=data;
            list_code=updList.code;
            //console.log(updList);
            var toList=updList.list;
            for(var j=0;j<sdate_arr.length;j++){
                //flag用于判断已开始的赛事是否有在list里
                var flag=true;
                for(var i=0;i<toList.length;i++){
                    var id=toList[i].id;
                    if(sdate_arr[j].id==id){
                        flag=false;
                        if(toList[i].home_score!=""&&toList[i].visit_score!=""){

                            if(isNotCbaBasketball($("#"+id).parent('a').attr('title'),toList[i].type)){
                                var score=toList[i].visit_score+" - "+toList[i].home_score;
                            }else{
                                var score=toList[i].home_score+" - "+toList[i].visit_score;
                            }

                            $("#"+id).find(".s_name").html(score);
                        }
                        if(toList[i].period_cn!=""){
                            var time_status=toList[i].period_cn.replace("\n","<br />");
                            $("#"+id).find(".remind").html(time_status);
                        }else{
                            $("#"+id).find(".remind").html("进行中");
                        }
                    }
                }
                if(flag){
                    //当比分接口中取不到得分，到单场比赛中取数
                    getSingleScore(sdate_arr[j]);
                }
            }
        },"json");
    }
    //获取单场比分
    function getSingleScore(currentList){
        var today=currentList.sdate;
        var id=currentList.id;
        $.ajax({
            url:"//bifen4m.qiumibao.com/json/"+today+"/"+id+".htm",
            success:function(data,status){
                if(status=="error"){return ;}
                var item=data;
                if(item.home_score!=""&&item.visit_score!=""){
                    var score=item.home_score+" - "+item.visit_score;
                    $("#"+id).find(".s_name").html(score);
                }
                if(item.period_cn!=""){
                    var time_status=item.period_cn.replace("\n","<br />");
                    $("#"+id).find(".remind").html(time_status);
                }else{
                    $("#"+id).find(".remind").html("进行中");
                }
            },
            error:function(data,status){
                console.log(status);
            },
            dataType:"json"});
        //		$.get("http://bifen.qiumibao.com/json/"+today+"/"+id+".htm",function(data,status){
        //			if(status=="error"){return ;}
        //			var item=data;
        //			if(item.home_team==''&&item.home_score!=""){
        //					var score=item.home_score+" - "+item.visit_score;
        //					$("#"+id).find(".s_name").html(score);
        //					var bifen=item.period_cn.replace("\n","<br />");
        //					$("#"+id).find(".remind").html(bifen);
        //				}
        //		},"json");
    }
    //获取比赛开始的id
    function gameHadStart(start_arr){
        $(".saishi table").each(function(i,item){
            var bisaiTime=$(item).find(".hideTime").text();
            bisaiTime=bisaiTime.replace(/-/g,"/");
            if(new Date(bisaiTime) < new Date()){
                var id=$(item).attr("id");
                if(id==undefined){return true;}
                var sdate=bisaiTime.slice(0,bisaiTime.indexOf(" "));
                start_arr.push({id:id,sdate:sdate});
            }else{
                return false;
            }
        });
        return start_arr;
    }

    //判断是否是非cba篮球赛
    function isNotCbaBasketball(title,type) {
        if((type == 'basketball' || type == 'nba') && title.indexOf('CBA') < 0 && title.indexOf('cba') < 0){
            return true;
        }else{
            return false;
        }
    }
    /*-------------------------------------------*/

    /*-------------闹钟提醒函数--------------------*/
    //是否执行时间提醒
    var timer;
    function startIsRemind(obj){
        var txt=obj.next().text();
        var startTime=new Date(txt);
        timer=setInterval(function(){
            var currentTime=new Date();
            var diffTime=startTime.getTime()-currentTime.getTime();
            //alert(diffTime);
            if(diffTime<5*60*1000){
                var $tb=obj.parents("table").find("td");
                alert($tb.eq(1).find("b").text()+"和"+$tb.eq(3).find("b").text()+"的比赛即将开赛");
                clearInterval(timer);
            }
        },3000);
    }
    /*-------------------------------------*/
    /*----------赛事选择-------------*/
    function shaixuanSaishi(stype){
        if(stype=='zhongyao'){
            $('.saishi li').each(function(index){
                $(this).hide();
                // if($(this).html().indexOf('<b>')>=0){
                if($(this).hasClass('emphasis')){
                    $(this).show();
                }
            })
            $('.headline li').each(function(index){
                $(this).show();
            })
        }else if(stype=='all'){
            $('.module li').each(function(index){
                $(this).show();
            })

        }else{
            $('.module li').each(function(index){
                $(this).hide();
                if($(this).attr("type")==stype){
                    $(this).show();
                }else{
                    var title=$(this).attr("label");
                    if(typeof title !='string') return ;
                    var type=stype=='football'?'足球':(stype=='basketball'?'篮球':undefined);
                    if(title==type||title.indexOf(','+type+',')>-1||title.indexOf(','+type)==title.length-3&&title.length>5||title.indexOf(type+',')==0){
                        $(this).show();
                    }
                }


            });
            $(".headline li").each(function(){
                if(stype=='other'){
                    $(this).show();
                }
            });

        }
        //      var line=0;
        //      $('.headline li').each(function(index,item){
        //      	$(this).hide();
        //      	var type=$(this).attr("type");
        //      	var label=$(this).attr("label");
        //      	if(!type) return false;
        //      	if(type!='football'&&type!='basketball'){
        //      		if(existIndex(label,1,'足球')||existIndex(label,2,'足球')||existIndex(label,3,'足球')||existIndex(label,4,'足球')){
        //      			type='football';
        //      		}else if(existIndex(label,1,'篮球')||existIndex(label,2,'篮球')||existIndex(label,3,'篮球')||existIndex(label,4,'篮球')){
        //      			type='basketball';
        //      		}
        //      	}
        //      	if(line<4&&(stype==type||stype=='all'||stype=='zhongyao')){
        //      		line++;
        //      		$(this).show();
        //      	}
        //      })
        if(!$('.headline li:last').attr("label")){
            $('.headline li:last').show();
        }

        switch(stype){
            case 'zhongyao': $(".global_sel").text("重要");break;
            case 'football': $(".global_sel").text("足球");break;
            case 'basketball': $(".global_sel").text("篮球");break;
            case 'other': $(".global_sel").text("其他");break;
            default: $(".global_sel").text("全部");
        }
    }
    function existIndex(str,n,type){
        switch (n){
            case 1:if(str==type) return true;
            case 2:if(str.indexOf(','+type+',')>-1) return true;
            case 3:if(str.indexOf(','+type)==str.length-3&&str.length>5) return true;
            case 4:if(str.indexOf(type+',')==0) return true;
            default: false;
        }
    }
    /*---------------------------------------------*/
    /*-----cookie的操作------------------*/
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
    /*--------------------------------------*/
    /*-------------------*/
    function closeclick_phone()
    {
        document.getElementById('addDesktop_iphone').style.display='none';
        cookiesave('closeclick_iphone','closeclick_iphone','','','');
    }
    function cookiesave(n, v, mins, dn, path){
        if(n)
        {
            if(!mins) mins = 7 * 24 * 60;
            if(!path) path = "/";
            var date = new Date();
            date.setTime(date.getTime() + (mins * 60 * 1000));
            var expires = "; expires=" + date.toGMTString();
            if(dn) dn = "domain=" + dn + "; ";
            document.cookie = n + "=" + v + expires + "; " + dn + "path=" + path;
        }
    }
    function cookieget(n){
        var name = n + "=";
        var ca = document.cookie.split(';');
        for(var i=0;i<ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1,c.length);
            if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
        }
        return "";
    }
    function clickclose(){
        var is_IOS7=Boolean(navigator.userAgent.match(/OS [7-9]_\d[_\d]* like Mac OS X/i));
        if(!OST.isIPHONE){
            document.getElementById('addDesktop_iphone').style.display='none';
        }
        else{
            if(cookieget('closeclick_iphone')=='closeclick_iphone'){
                document.getElementById('addDesktop_iphone').style.display='none';
            }else{
                if(is_IOS7){
                    document.getElementById( "ico_adddesktop_ios7_phone" ).className = "ico_adddesktop_ios7";
                }
                document.getElementById('addDesktop_iphone').style.display='block';
            }
        }
    }
    //      window.onload=clickclose;

    window.pv_abtest = '3';//土豆统计phone
    /*----------------------------------------------------*/
    /*--------------点击返回顶部，系统的判断------------*/
    function systemOS(){
        var ua = navigator.userAgent.toLowerCase();
        if (ua.indexOf('ucweb') > 0 || ua.indexOf('ucbrowser') > 0) {
            return "uc";
        }else if (ua.indexOf('iphone') > 0) {
            return "iphone";
        }else if (ua.indexOf('android') > 0) {
            return "android";
        }
    }
    function modelbox(flag){
        if(flag){
            var html='';
            html+='<div class="box"><div class="box_bg"></div>';
            html+='<div class="box_box"><div class="box_ent"><div class="box_center"><h2 class="box_t">选择你的属性</h2><ul><li><a href="javascript:;" value="basketball">只看篮球</a></li><li><a href="javascript:;" value="football">只看足球</a></li><li><a href="javascript:;" value="zhongyao">重要</a></li></ul></div></div></div></div>';
            $(".global").append(html);
        }else{
            $(".global").find(".box").remove();
        }
    }
    //添加顶部广告
    //		function addAdv(url,imgSource){
    //			var str='<style>.adv11{width:100%;height:auto;}.adv11 img{width:100%;vertical-align:middle;}</style>'+
    //					'<div class="adv11"><a href="'+url+'"><img src="'+imgSource+'"/></a></div>';
    //			return str;
    //		}
    /*--------------点击返回顶部，系统的判断------------*/
    $(function(){
        //		$(".global").prepend(addAdv("https://s.click.taobao.com/HJM3UOx","http://tu.qiumibao.com/img/1111_480_60.jpg"));
        //		setTimeout(function(){top=$(".nav").offset().top},100);
        /*-------终端类型的判断-----------------*/
        OST = {};
        var osType = {
            isWin:'Win',
            isMac:'Mac',
            isSafari:'Safari',
            isChrome:'Chrome',
            isIPAD: 'iPad',
            isIPHONE: 'iPhone',
            isIPOD: 'iPod',
            isLEPAD: 'lepad_hls',
            isMIUI: 'MI-ONE',
            isAndroid:'Android',
            isAndroid4: 'Android 4.',
            isAndroid41: 'Android 4.1',
            isSonyDTV: "SonyDTV",
            isBlackBerry:"BlackBerry",
            isMQQBrowser:'MQQBrowser',
            isMobile:'Mobile'
        };
        for(var os in osType){
            if(navigator.userAgent.indexOf(osType[os]) !== -1){
                OST[os] = true;
            }else{
                OST[os] = false;
            }
        }
        OST.isIos = ((OST.isIPAD || OST.isIPHONE || OST.isIPOD) || OST.isMac );
        OST.isPhone = (OST.isIPHONE || OST.isIPOD || (OST.isAndroid&&OST.isMobile));
        OST.isPad = (OST.isIPAD || (OST.isAndroid && !OST.isMobile));
        /*-------终端类型的判断-----------------*/
        /*----------定时更新数据-------------*/
        // getBifenScore();
        // setInterval(function(){
        //     $.get("//bifen4m.qiumibao.com/json/list_code.htm",function(data){
        //         if(list_code!=data.code){
        //             getBifenScore();
        //         }
        //     },"json");
        // },10000);
        /*----------定时更新数据-------------*/
        /*-----闹钟提醒、取消-------*/
        //$("div.remind").click(function(e){
        //	var id=$(this).find("img").attr("id");
        //			if(id=="clock1"){
        //				$(this).html('<img src="images/clock2.png" id="clock2"/>');
        //				//开启提醒
        //				startIsRemind($(this).parents("td"),true);
        //				return false;
        //			}else if(id=="clock2"){
        //				$(this).html('<img src="images/clock1.png" id="clock1"/>');
        //				//取消提醒
        //				clearInterval(timer);
        //				return false;
        //			}
        //});
        /*-----闹钟提醒、取消-------*/
        /*-----------------重要、篮球、足球、其他、全部的选择--------------------*/

        $(".shaixuan span").click(function(){
            var stype = $(this).attr("value");
            setDomainCookie("stype",stype,".zhibo8.net");
            $(".shaixuan span").removeClass("current");
            $(this).addClass("current");
            shaixuanSaishi(stype);
        });

        var default_stype = getCookie("stype");
        if(default_stype==null){
            modelbox(true);
        }else{
            if(default_stype=='zuqiu')default_stype='football';
            if(default_stype=='nba')default_stype='basketball';
            $(".shaixuan span").removeClass("current");
            $(".shaixuan span").each(function(index){
                if($(this).attr("value")==default_stype){
                    $(this).addClass("current");
                }
            });
            shaixuanSaishi(default_stype);
        }
        //初始时选择赛事
        $(".global").on("click",".box ul a",function(){
            var c_stype=$(this).attr("value");
            setDomainCookie("stype",c_stype,".zhibo8.net");
            shaixuanSaishi(c_stype);
            if(c_stype==="all"){
                c_stype="zhongyao";
            }
            $(".shaixuan span").removeClass("current");
            $(".shaixuan span").each(function(i,item){
                var stype = $(item).attr("value");
                if(stype==c_stype){
                    $(item).addClass("current");
                }
            });
            modelbox(false);
        });

        //全局选择
        $(".global_sel").on("click",function(){
            modelbox(true);
            $(".box_bg").on("click",function(){
                modelbox(false);
            });
        })
        var $nav=$(".nav");
        var top=$nav.offset().top;
        var navH=$nav.height();
        if(OST.isIos){
            $nav.addClass("nav_sticky");
        }else{
            $nav.after('<div class="empty"></div>').next(".empty").height(navH).hide();
        }
        $(window).on("scroll",function(){
            var scrollH=$(this).scrollTop();
            if(top-scrollH<=0){
                $nav.addClass("nav_fixed").next(".empty").show();
                $(document.body).hasClass("down_fixed");
            }else{
                $nav.removeClass("nav_fixed").next(".empty").hide();
                $nav.hasClass("down__nav_fixed");
            }
        });
        /*-----------------重要、篮球、足球、其他、全部的选择--------------------*/
        /*-----android,uc的判断--------*/
        if(systemOS() != 'uc'){

            var adv=getCookie("m_adv");
            if(!adv){
                $(document.body).addClass("down_fixed");
                //	        	$("body").css("margin-top",'40px');
                $("#m_a_d_v").show();
            }
            $("#m_close").click(function(){
                $("#m_a_d_v").hide();
                setCookie("m_adv",1);
                $(document.body).removeClass("down_fixed");
                return false;
            });
            $("#m_adv > a").click(function(){

                window.location = 'https://www.zhibo8.net/download.html';//'<?php echo $apk_url; ?>';
            });

            $(".advert_android").show();
        }
        if(systemOS() == 'uc'){
            $(".advert").hide();
        }

        /*-----android,uc的判断--------*/
        /*--------------点击返回顶部，系统的判断------------*/
        var gotop_btn_elem = $(".gotop");
        gotop_btn_elem.on('click',  function(e){
            window.scroll(0, 0);
        });
        /*--------------点击返回顶部，系统的判断------------*/
    });
})(window,jQuery);