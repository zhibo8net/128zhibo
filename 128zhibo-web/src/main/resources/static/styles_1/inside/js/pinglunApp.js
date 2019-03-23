/**
 * Created by zhibo8 on 14-9-9.
 */

console.log("new pl .");

var listpl;

var app = angular.module("pinglunApp", ['infinite-scroll','realvik-scroll','div-scroll','ngCookies']);
app.factory('instance', function(){

    return {};

});

app.controller("parentCtrl",['$scope','$rootScope',function($scope,$rootScope){
    $scope.$on("showloginbox", function (event) {
        $scope.$broadcast("doshowloginbox");
    });

    $scope.$on("showuserbox", function (event) {
        $scope.$broadcast("doshowuserbox");
    });

    $scope.$on("login", function (event) {
        $scope.$broadcast("dologin");
    });

    $scope.locationToUserInfo = function(){
        window.location = '#user_info_wrap';
    }

    $rootScope.commentInfo = '评论载入中';//'已有108条评论';

    $scope.advertInfo = '真实中超足球游戏';

    $scope.advertUrl = '//cnrdn.com/5xd9';

}]);

app.controller("infoCtrl", ['$scope','$rootScope','$http','$sce','$interval','$cookies','$window','instance', function($scope,$rootScope,$http,$sce,$interval,$cookies,$window,instance) {
    $scope.userid = instance.userid;

    $scope.$on("doshowloginbox", function (e) {
        $scope.isshowloginbox = instance.isshowloginbox;
        $scope.box_x = 0;//instance.box_x  ;
        $scope.box_y = instance.box_y  ;
    });

    $scope.$on("doshowuserbox", function (e) {
        $scope.isshowuserbox = instance.isshowuserbox;
        $scope.box_x = 0;//instance.box_x  ;
        $scope.box_y = instance.box_y  ;

        $scope.current = 'hot';

        var url = "//pl.zhibo8.cc/usercenter/message.php?platform=bbs&usercode="+instance.userid+"&callback=JSON_CALLBACK&random="+Math.random();//1154542
        //var url = "//pl.zhibo8.cc/usercenter/message.php?platform=bbs&usercode=1154542&callback=JSON_CALLBACK";
        $http.jsonp(url).success(function(data) {
            $scope.myHotPinglunList = data.hot_list;
            $scope.myRepliedList = data.messages;
            $scope.hotLength = data.hot_list.length;
            $scope.repliedLength = data.messages.length;
            $scope.myHotPinglunList = $scope.addUrl($scope.myHotPinglunList,'hot');
            $scope.myRepliedList = $scope.addUrl($scope.myRepliedList,'replied');
            /*
             for(i=0;i<$scope.hotLength;i++){
             var filename = $scope.myHotPinglunList[i]['filename'];
             var arr = filename.split('-');
             if(filename.indexOf('-news-')>-1){
             var date = arr[0].replace(new RegExp('_',"gm"),'-');
             $scope.myHotPinglunList[i]['url'] = '//news.zhibo8.cc/'+arr[2]+'/'+date+'/'+arr[3]+'.htm';
             }else if(filename.indexOf('-zuqiu-')>-1 || filename.indexOf('-nba-')>-1){
             $scope.myHotPinglunList[i]['url'] = '//www.zhibo8.cc/zhibo/'+arr[1]+'/'+arr[0]+'/'+arr[2]+'.htm';
             }else if(filename.indexOf('-zuqiujijin-')>-1 || filename.indexOf('-nbajijin-')>-1 || filename.indexOf('-zuqiuluxiang-')>-1 || filename.indexOf('-nbaluxiang-')>-1){
             arr[1] = arr[1].replace('jijin','');
             arr[1] = arr[1].replace('luxiang','');
             if(arr.length==4){
             $scope.myHotPinglunList[i]['url'] = '//www.zhibo8.cc/'+arr[1]+'/'+arr[0]+'/'+arr[2]+'-'+arr[3]+'.htm';
             }else{
             $scope.myHotPinglunList[i]['url'] = '//www.zhibo8.cc/'+arr[1]+'/'+arr[0]+'/'+arr[2]+'-'+arr[3]+'-'+arr[4]+'.htm';
             }
             }else if(filename.indexOf('album_')){
             arr = filename.split('_');
             $scope.myHotPinglunList[i]['url'] = '//tu.zhibo8.cc/home/album/'+arr[2]+'.html';
             }
             }
             */
        });
    });

    $scope.addUrl = function(list,type){
        for(i=0;i<list.length;i++){
            var filename = '';
            if(type=='hot'){
                var filename = list[i]['filename'];
            }else{
                var filename = list[i]['content']['c_filename'];
            }
            var arr = filename.split('-');
            if(filename.indexOf('-news-')>-1){
                var date = arr[0].replace(new RegExp('_',"gm"),'-');
                list[i]['url'] = '//news.zhibo8.cc/'+arr[2]+'/'+date+'/'+arr[3]+'.htm?random='+Math.random();;
            }else if(filename.indexOf('-zuqiu-')>-1 || filename.indexOf('-nba-')>-1){
                list[i]['url'] = '//www.zhibo8.cc/zhibo/'+arr[1]+'/'+arr[0]+'/'+arr[2]+'.htm?random='+Math.random();;
            }else if(filename.indexOf('-zuqiujijin-')>-1 || filename.indexOf('-nbajijin-')>-1 || filename.indexOf('-zuqiuluxiang-')>-1 || filename.indexOf('-nbaluxiang-')>-1){
                arr[1] = arr[1].replace('jijin','');
                arr[1] = arr[1].replace('luxiang','');
                if(arr.length==4){
                    list[i]['url'] = '//www.zhibo8.cc/'+arr[1]+'/'+arr[0]+'/'+arr[2]+'-'+arr[3]+'.htm?random='+Math.random();
                }else{
                    list[i]['url'] = '//www.zhibo8.cc/'+arr[1]+'/'+arr[0]+'/'+arr[2]+'-'+arr[3]+'-'+arr[4]+'.htm?random='+Math.random();
                }
            }else if(filename.indexOf('album_')){
                arr = filename.split('_');
                list[i]['url'] = '//tu.zhibo8.cc/home/album/'+arr[2]+'.html?random='+Math.random();
            }
        }
        return list;
    }

    $scope.turnTo = function(type){
        $scope.current = type;
    }



    $scope.login = function(){
        if( (!angular.isDefined($scope.username)) || (!angular.isDefined($scope.password))){
            alert("用户名和密码不能为空！");
            return;
        }
        $scope.loging = true;
        var url = '//pl.zhibo8.cc/ajaxLogin.php';
        url = url+"?username="+$scope.username+"&password="+$scope.password+'&callback=JSON_CALLBACK';
        $http.jsonp(url).success(function(data) {
            if(data.statu == 'success'){
                instance.userid = data.userid;
                instance.username = data.username;
                $rootScope.userid = data.userid;
                $rootScope.username = data.username;
                $scope.isshowloginbox = false;
                $scope.$emit("login");
            }else{
                alert(data.value);
            }
            $scope.loging = false;
        });

    }

    $scope.keyprocess2 = function(e){
        var keyCode = e.keyCode;
        if(keyCode == 13) {
            $scope.login();
        }
    }


    $scope.hideloginbox = function(e){
        $scope.isshowloginbox = false;
    }

    $scope.hideuserbox = function(e){
        $scope.isshowuserbox = false;
    }

}]);

app.controller("myCtrl", ['$scope','$rootScope','$http','$sce','$interval','$location','$anchorScroll','$cookies','$window','instance', function($scope,$rootScope,$http,$sce,$interval,$location,$anchorScroll,$cookies,$window,instance) {
    $scope.qqlogin_url = 'https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101169587&state=test&scope=get_user_info,add_share,get_info,add_t&redirect_uri=http%3A%2F%2Fpl.zhibo8.cc%2Fusercenter%2Foauth%2Findex.php%3FredirectURL%3D'+window.location.href;
    $scope.isshoweditortip = true;
    $scope.is_rookie = $cookies.is_rookie; //1111
    $scope.username = "";
    $scope.enable_show_load_btn = true;
    if(cookie("bbs_username")){
        $scope.userid   = $cookies.bbs_uid; //cookie("bbs_uid");
        $scope.username = decodeURI($cookies.bbs_username);//decodeURI(cookie("bbs_username"));
        instance.userid = $scope.userid;
        instance.username = $scope.username;
        $rootScope.userid = $scope.userid;
        $rootScope.username = $scope.username;
    }

    $scope.pageno = 0;

    $scope.images =
    {"default":[{"no":"#face01","img_src":"default\/1.gif"},{"no":"#face02","img_src":"default\/2.gif"},{"no":"#face03","img_src":"default\/3.gif"},{"no":"#face04","img_src":"default\/4.gif"},{"no":"#face05","img_src":"default\/5.gif"},{"no":"#face06","img_src":"default\/6.gif"},{"no":"#face07","img_src":"default\/7.gif"},{"no":"#face08","img_src":"default\/8.gif"},{"no":"#face09","img_src":"default\/9.gif"},{"no":"#face10","img_src":"default\/10.gif"},{"no":"#face11","img_src":"default\/11.gif"},{"no":"#face12","img_src":"default\/12.gif"},{"no":"#face13","img_src":"default\/13.gif"},{"no":"#face14","img_src":"default\/14.gif"},{"no":"#face15","img_src":"default\/15.gif"},{"no":"#face16","img_src":"default\/16.gif"},{"no":"#face17","img_src":"default\/17.gif"},{"no":"#face18","img_src":"default\/18.gif"},{"no":"#face19","img_src":"default\/19.gif"},{"no":"#face20","img_src":"default\/20.gif"},{"no":"#face21","img_src":"default\/21.gif"},{"no":"#face22","img_src":"default\/22.gif"},{"no":"#face23","img_src":"default\/23.gif"},{"no":"#face24","img_src":"default\/24.gif"},{"no":"#face25","img_src":"default\/25.gif"},{"no":"#face26","img_src":"default\/26.gif"},{"no":"#face27","img_src":"default\/27.gif"},{"no":"#face28","img_src":"default\/28.gif"},{"no":"#face29","img_src":"default\/29.gif"},{"no":"#face30","img_src":"default\/30.gif"},{"no":"#face31","img_src":"default\/31.gif"},{"no":"#face32","img_src":"default\/32.gif"},{"no":"#face33","img_src":"default\/33.gif"},{"no":"#face34","img_src":"default\/34.gif"},{"no":"#face35","img_src":"default\/35.gif"},{"no":"#face36","img_src":"default\/36.gif"},{"no":"#face37","img_src":"default\/37.gif"},{"no":"#face38","img_src":"default\/38.gif"},{"no":"#face39","img_src":"default\/39.gif"},{"no":"#face40","img_src":"default\/40.gif"},{"no":"#face41","img_src":"default\/41.gif"},{"no":"#face42","img_src":"default\/42.gif"},{"no":"#face43","img_src":"default\/43.gif"}],"coolmonkey":[{"no":"#face44","img_src":"coolmonkey\/01.gif"},{"no":"#face45","img_src":"coolmonkey\/02.gif"},{"no":"#face46","img_src":"coolmonkey\/03.gif"},{"no":"#face47","img_src":"coolmonkey\/04.gif"},{"no":"#face48","img_src":"coolmonkey\/05.gif"},{"no":"#face49","img_src":"coolmonkey\/06.gif"},{"no":"#face50","img_src":"coolmonkey\/07.gif"},{"no":"#face51","img_src":"coolmonkey\/08.gif"},{"no":"#face52","img_src":"coolmonkey\/09.gif"},{"no":"#face53","img_src":"coolmonkey\/10.gif"},{"no":"#face54","img_src":"coolmonkey\/11.gif"},{"no":"#face55","img_src":"coolmonkey\/12.gif"},{"no":"#face56","img_src":"coolmonkey\/13.gif"},{"no":"#face57","img_src":"coolmonkey\/14.gif"},{"no":"#face58","img_src":"coolmonkey\/15.gif"},{"no":"#face59","img_src":"coolmonkey\/16.gif"}],"grapeman":[{"no":"#face60","img_src":"grapeman\/01.gif"},{"no":"#face61","img_src":"grapeman\/02.gif"},{"no":"#face62","img_src":"grapeman\/03.gif"},{"no":"#face63","img_src":"grapeman\/04.gif"},{"no":"#face64","img_src":"grapeman\/05.gif"},{"no":"#face65","img_src":"grapeman\/06.gif"},{"no":"#face66","img_src":"grapeman\/07.gif"},{"no":"#face67","img_src":"grapeman\/08.gif"},{"no":"#face68","img_src":"grapeman\/09.gif"},{"no":"#face69","img_src":"grapeman\/10.gif"},{"no":"#face70","img_src":"grapeman\/11.gif"},{"no":"#face71","img_src":"grapeman\/12.gif"},{"no":"#face72","img_src":"grapeman\/13.gif"},{"no":"#face73","img_src":"grapeman\/14.gif"},{"no":"#face74","img_src":"grapeman\/15.gif"},{"no":"#face75","img_src":"grapeman\/16.gif"},{"no":"#face76","img_src":"grapeman\/17.gif"},{"no":"#face77","img_src":"grapeman\/18.gif"},{"no":"#face78","img_src":"grapeman\/19.gif"},{"no":"#face79","img_src":"grapeman\/20.gif"},{"no":"#face80","img_src":"grapeman\/21.gif"},{"no":"#face81","img_src":"grapeman\/22.gif"},{"no":"#face82","img_src":"grapeman\/23.gif"},{"no":"#face83","img_src":"grapeman\/24.gif"}]};
    /*
     var timer = $interval(function() {
     $("img.lazy").lazy({
     effect: "fadeIn",
     effectTime: 1000
     });
     },200);
     */

    var stop;
    $scope.fight = function() {
        // Don't start a new fight if we are already fighting
        if ( angular.isDefined(stop) ) return;

        stop = $interval(function() {
            if("undefined" == typeof(listpl)){
                $scope.status = '评论数据载入中';
            } else {
                $scope.status = '评论数据加载完成';

                $scope.stopFight();

                $scope.scroll_disabled = false;

                $scope.pageno = 0;



                for(var i=listpl.length-1;i>-1;i--){
                    if(listpl[i].parentid!='0')continue;
                    if(listpl[i].device!=null && listpl[i].device!='null' && listpl[i].device!=''){
                        if(listpl[i].device.indexOf("android")>-1){
                            listpl[i].device = '来自新版直播吧安卓客户端';
                        }else if(listpl[i].device.indexOf("ios")>-1){
                            listpl[i].device = '来自新版直播吧苹果客户端';
                        }else if(listpl[i].device.indexOf("wp-")>-1){
                            listpl[i].device = '来自新版直播吧WindowsPhone客户端';
                        }else if(listpl[i].device.indexOf("mqq")>-1){
                            listpl[i].device = '通过QQ浏览器 发自m.zhibo8.cc';
                        }else if(listpl[i].device.indexOf("uc")>-1){
                            listpl[i].device = '通过UC浏览器 发自m.zhibo8.cc';
                        }
                    }else{
                        listpl[i].device = '';
                    }
                    listpl[i].children = getchild_data(listpl[i].id,listpl,0);
                }


                $scope.list = listpl;

                $scope.list = $.grep( $scope.list, function(p){
                    return p.parentid == 0;
                });

                $scope.loadlist = $scope.list.slice(0,100);

                $scope.hotlist = listpl.sort(sortByKey);

                $scope.hotlist = $.grep( $scope.hotlist, function(p){
                    return (p.up- p.down) >= 10;
                });

                $scope.all_num = listpl.length;

                $scope.root_num = $scope.list.length;

                if($scope.all_num==0){
                    $rootScope.commentInfo = '暂无评论';
                    $scope.all_num_info = '暂无评论，赶快发表评论吧！';
                }else{
                    $rootScope.commentInfo = '已有'+$scope.all_num+'条评论';
                    $scope.all_num_info = '已有'+$scope.all_num+'条评论。';
                }

            }
        }, 100);
    }

    $scope.stopFight = function() {
        if (angular.isDefined(stop)) {
            $interval.cancel(stop);
            stop = undefined;
        }
    };

    $scope.fight();

    $scope.scroll_disabled = false;

    $scope.showloading = false;

    $scope.root_normal_num= 0;
    $scope.refresh = function(){
        $.ajaxSettings.async = true;
        var file = $scope.filename;
        var file_arr = file.split("-");
        var json_path = '';
        for(var fi = 0; fi<file_arr.length;fi++){
            json_path += '/' + file_arr[fi];
        }

        var count_path = "//cache.zhibo8.cc/json"+json_path+"_count.htm?random="+Math.random();
        $.getJSON(count_path, function(data){
            $scope.all_num = data.all_num;
            $scope.root_num = data.root_num;
            $scope.root_normal_num = data.root_normal_num;
            if($scope.all_num==0){
                $rootScope.commentInfo = '暂无评论';
                $scope.all_num_info = '暂无评论，赶快发表评论吧！';
            }else{
                $('.pinglun_num').html($scope.all_num);
                $rootScope.commentInfo = '已有'+$scope.all_num+'条评论';
                $scope.all_num_info = '已有'+$scope.all_num+'条评论。';
            }

            $scope.pageno = Math.ceil(data.root_num/100)-1;
            if($scope.all_num>0){
                var hot_path = "//cache.zhibo8.cc/json"+json_path+"_hot.htm?random="+Math.random();
                $.getJSON(hot_path, function(data){
                	getEmoji(function (lists) {
                    	var new_data=contentFormat(data,lists);
                    	$scope.hotlist = new_data;
                    });
//                  $scope.hotlist = data;
                });

                var list_path = "//cache.zhibo8.cc/json"+json_path+"_"+$scope.pageno+".htm?random="+Math.random();
                $.getJSON(list_path, function(data){
                    console.log("refresh.....");
                    getEmoji(function (lists) {
                    	var new_data=contentFormat(data,lists);
                    	$scope.loadlist = new_data;
                    });
//                  $scope.loadlist = data;
                });
            }
        });

        //listpl = undefined;
        //var file = $scope.filename;
        //var check_url = '//pl.zhibo8.cc/archive/check.php?file='+file;
        //var file_arr = file.split("-");
        //var json_path = '';
        //for(var fi = 0; fi<file_arr.length;fi++){
        // json_path += '/' + file_arr[fi];
        //}
        //json_path = '//pl.zhibo8.cc/json'+json_path+'.htm';
        //
        //$.getScript(json_path, function(){});
        //$.getScript(check_url, function(){});
        //$scope.fight();
    }
    //评论数变化，却换刷新评论
/*    var now_count= 0;
    $('#select_4').click(function(){
        if(parseInt(now_count)!=parseInt($('.pinglun_num').html())){
            $scope.refresh();
            now_count = $('.pinglun_num').html();
        }
    })*/
    var qiehuan=0;
    $('#select_4').click(function(){
        if(qiehuan==0){
            $scope.refresh();
            qiehuan++
        }
    })

    /*评论数*/
    $scope.get_count_num = function(){
        var file = $scope.filename;
        var file_arr = file.split("-");
        var json_path = '';
        for(var fi = 0; fi<file_arr.length;fi++){
            json_path += '/' + file_arr[fi];
        }
        var count_path = "//cache.zhibo8.cc/json"+json_path+"_count.htm?random="+Math.random();
        $.ajax({
            type: "get",
            dataType: "json",
            async: true,
            url:count_path,
            success: function(data){
                $('.pinglun_num').html(data.all_num);
            }
        })
    }
    $(function(){
        $scope.get_count_num();

    })

    $scope.postpinglun = function(){
    	//选择房间才能评论
    	if($scope.rooms.length>0&&$scope.is_sel_room==''){
    		alert("请先选择房间，再发表评论！");
    		return;
    	}
        if($scope.isdoingpost == true){
            $scope.postinfo = '评论提交中...请耐心等待';
            return;
        }else{
            $scope.isdoingpost = true;
            $scope.postinfo = '评论提交中...';
        }


         var url = "//pl.zhibo8.cc/ajaxPost.php?filename="+$scope.filename+"&userid="+$scope.userid+"&username="+$scope.username+"&parentid="+$scope.parentid+"&content="+encodeURIComponent($scope.pl_content)+"&callback=JSON_CALLBACK&random="+Math.random();
        $http.jsonp(url).success(function(data) {
            $scope.isdoingpost = false;
            if(data.status=="error"){
                data.value&&alert(data.value );
                $scope.postinfo = '';
				
				//绑定手机 //1111
                if(data.value.indexOf('绑定手机')>=0){
                    $(".full_screen").show();
                }
            }else{
                var now = new Date();
                var year = now.getFullYear();       //年
                var month = now.getMonth() + 1;     //月
                var day = now.getDate();            //日
                var hh = now.getHours();            //时
                var mm = now.getMinutes();          //分
                var ss = now.getSeconds();           //秒
                var clock = year + "-";
                if(month < 10)
                    clock += "0";
                clock += month + "-";
                if(day < 10)
                    clock += "0";
                clock += day + " ";
                if(hh < 10)
                    clock += "0";
                clock += hh + ":";
                if (mm < 10) clock += '0';
                clock += mm + ":";
                if (ss < 10) clock += '0';
                clock += ss;
				if(!($scope.loadlist instanceof Array)){
					$scope.loadlist=[];
				}
                //var now_time = year+'-'+month+'-'+date+' '+hour+':'+minutes+':'+seconds;
                $scope.loadlist.unshift({
                    "id": "8098845",
                    "filename": $scope.filename,
                    "userid": $scope.userid,
                    "m_uid": $scope.userid,
                    "content": $scope.pl_content,
                    "createtime": clock,
                    "updatetime": clock,
                    "parentid": "0",
                    "username": $scope.username,
                    "status": "normal",
                    "up": "0",
                    "down": "0",
                    "report": "0",
                    "device": "",
                    "ip": "",
                    "userinfo": "",
                    "sysver": "4.4.2",
                    "platform": null,
                    "appname": "",
                    "appver": "4.3.1",
                    "figureurl": "",
                    "level": "0",
                    "children": [ ],
                    "room":data.room_id
                });
                $scope.postinfo = '';
                $scope.pl_content = '';
                $scope.parentid = 0;
                $scope.replyname = '';
                if($scope.rooms.length>0){
                	$scope.is_sel_room=data.room_id;
                	$scope.roomname=$scope.rooms[data.room_id-1].name;
                }
                //$scope.refresh();
            }
            $cookies.posttime = new Date().getTime();
            if(data.act=='bind_phone'){
            	
				bindBox (data.title);
			}
        });
    }

    $scope.reply = function(pid, replyname){
        $scope.parentid  = pid;
        $scope.replyname = replyname;
        //$location.hash('main');
        //$anchorScroll();
        window.location = '#re';
    }

    $scope.locationToRe = function(){
        window.location = '#re';
    }

    $scope.updown = function(id, act){
        var url = "//pl.zhibo8.cc/code/"+act+".php?_platform2=wap&act=jsonp&cid="+id+"&callback=JSON_CALLBACK&random="+Math.random();
        $http.jsonp(url).success(function(data) {
            var num = 0;
            if(act == 'up'){
                num = data.up;
            }else{
                num = data.down;
            }
            if(num == 0){
                alert("请勿重复顶踩！");
            }else{
                $("."+act+"_btn[cid='"+data.cid+"']").children('.'+act+'_cnt').html(num);
            }
        });
    }

    $scope.logout = function(){
        var url = '//pl.zhibo8.cc/ajaxLogout.php?callback=JSON_CALLBACK&random='+Math.random();
        $http.jsonp(url).success(function(data) {
            if(data.statu=='success'){
                $scope.userid = '';
                $scope.username = '';
                $rootScope.userid = '';
                $rootScope.username = '';
            }
            //alert(decodeURI($cookies.bbs_username));
        });
    }

    $scope.hideeditortip = function(){
        $scope.isshoweditortip = false;
        $("#post_content").focus();
    }
	
	//1111
	$scope.showphone = function(){
        $(".full_screen").show();
    }

    $scope.loadMore = function() {
        var file = $scope.filename;
        var file_arr = file.split("-");
        var json_path = '';
        for(var fi = 0; fi<file_arr.length;fi++){
            json_path += '/' + file_arr[fi];
        }

        console.log("pageno:"+$scope.pageno);
        if($scope.pageno<=0){
            $scope.scroll_disabled = true;
            $scope.enable_show_load_btn = false;
            return;
        }

        var list_path = "//cache.zhibo8.cc/json"+json_path+"_"+($scope.pageno-1)+".htm?random="+Math.random();
        $.getJSON(list_path, function(data){
            console.log("server_data:"+$scope.pageno);
            if(data){
                //console.log("real_data:"+$scope.pageno);
                //console.log($scope.loadlist);
                getEmoji(function (lists) {
                	var new_data=contentFormat(data,lists);
                	$scope.loadlist = $scope.loadlist.concat(new_data);
                	$scope.pageno--;
                });
//              $scope.loadlist = $scope.loadlist.concat(data);
                //$scope.loadlist = data;
                
            }
        });
        //var last = $scope.loadlist.length;
        //if(last<$scope.list.length){
        //    $scope.pageno ++;
        //    $scope.loadlist = $scope.list.slice(0,last+10);
        //}else{
        //    $scope.scroll_disabled = true;
        //}
    };

    $scope.realvik = function(){
		console.log('test')
    }


    $scope.deliberatelyTrustDangerousSnippet = function(html) {
        var content_html = html;
        //content_html = content_html.replace(new RegExp(' src=',"gm"),' src="//tu.zhibo8.cc/uploads/qqimages/loading.gif" datasrc=');
        return $sce.trustAsHtml(content_html);
    };
    /*
     $scope.echoChildren = function(children){
     return $sce.trustAsHtml( getchild(children,0) );
     }

     $scope.echoChildrenOld = function(id){
     return $sce.trustAsHtml( getchild_old(id, listpl, 0) );
     }
     */
    $scope.testEcho = function(str){
        alert(str)
    }

    $scope.showloginbox = function(e){
        instance.isshowloginbox = true;
        instance.box_x = e.pageX ;
        instance.box_y = e.pageY ;
        $scope.$emit("showloginbox");
    }

    $scope.showuserbox = function(e){
        instance.isshowuserbox = true;
        instance.box_x = e.pageX ;
        instance.box_y = e.pageY ;
        $scope.$emit("showuserbox");
    }

    $scope.$on("dologin", function (e) {
        $scope.isshowloginbox = instance.isshowloginbox;
        $scope.userid = instance.userid  ;
        $scope.username = instance.username  ;
    });


    $scope.keyprocess = function(e){
        var keyCode = e.keyCode;
        if(e.ctrlKey && keyCode == 13 || e.altKey && keyCode == 83) {
            //alert(keyCode+" "+event.ctrlKey);
            $scope.postpinglun();
        }
    }

    $scope.bigimg_src = '//tu.zhibo8.cc/uploads/qqimages/loading.gif';
    $scope.showbigimg = function(src){
        $scope.bigimg_src = '//bbs.zhibo8.cc/static/image/smiley/'+src;
    }

    $scope.pl_content = '';
    $scope.insert_to_text = function(no){
        $scope.pl_content += no;
        $scope.is_show_smiley = false;
    }

    $scope.smiley_index = 2;
    $scope.change_smiley = function(index){
        $scope.smiley_index = index;
    }

    $scope.is_show_smiley = false;
    $scope.show_smiley_box = function(is_show){
        $scope.is_show_smiley = is_show;
    }
	/*
	 *分房
	 */
	
	
	$scope.roomname="最新房间";
//	$scope.rooms=[{id:1,name:"球迷房间"},{id:2,name:"彩民房间"}];//selectroom();
	$scope.rooms=[];
	$scope.$on("domLoad",function(e){
		if($scope.is_sel_room==''){
			$scope.is_bg=true;
		}
		$scope.rooms=sureroom();
		if($scope.rooms.length>0&&$scope.is_sel_room!=''){
			$scope.roomname=$scope.rooms[$scope.is_sel_room-1].name;
			$("#section_4").css("paddingTop","35px");
		}
	});
	$scope.is_sel_room=cookie("room_id");
	
	$scope.is_bg=false;
	$scope.cancelRoom=function(){
		if($scope.is_sel_room=="") return;
		$scope.is_bg=!$scope.is_bg;
	};
	
	$scope.selroom=function(room_id){
		if(!room_id){
			$("ul#rooms_nav li .gou").removeClass("active").eq($scope.is_sel_room-1).addClass("active");
			$scope.is_bg=!$scope.is_bg;
			return;
		}
		if(room_id==$scope.is_sel_room){
			$scope.is_bg=!$scope.is_bg;
			return;
		}
		if(!cookie("bbs_username")){
			if(!$scope.is_sel_room&&typeof danmu !='undefined'){
				danmu.start();
			}
			$scope.changeAction(room_id);
			return;
		}
		var url_selroom="//pl.zhibo8.cc/room/index.php?room_id="+room_id;
		$.ajax({
			url:url_selroom,
			dataType:"jsonp",
			success:function(data){
				if(data.status="success"){
					if(!$scope.is_sel_room&&typeof danmu !='undefined'){
						danmu.start();
					}
					$scope.changeAction(room_id);
				}else{
					alert(data.info);
					$scope.is_bg=!$scope.is_bg;
				}
			}
		});
		
	};
	//切换房间要改变的状态
	$scope.changeAction=function(room_id){
		$scope.is_sel_room=room_id;
		$scope.roomname=$scope.rooms[room_id-1].name;
		addCookie("room_id",room_id,3600*24*30);
		$scope.is_bg=!$scope.is_bg;
		$(".box_room").hide();
		$("#danmu2").show();
		$("#section_4").css("paddingTop","35px");
		$("ul#rooms_nav li .gou").removeClass("active").eq(room_id-1).addClass("active");
	}
	
	/****************内页弹幕 */
	//判断是否选择房间
	if(sureroom().length!=0){
		var rooms=sureroom();
		//弹幕信息先选择房间
		if(!cookie("room_id")){
			$("#danmu2").hide();
			$(".dm_tan").hide();
		}
	}
	$(".dm_tan").after('<div class="input_area" style="display:none;">'+
	'<div class="input_top"> '+ 	
	'<div class="close_send"></div>	</div>	<div class="input_info"><textarea type="text"></textarea></div>'+
			'<div class="send_info"><a href="javascript:;">发送</a></div>'+
		'</div>');
	$(".dm_close").on("click",function(e){
		$(".dm_box").hide();
		$(".dm_tan").show();
		if ($scope.is_sel_room!='') {
			danmu.pause();
		}
		
		e.stopPropagation();
	});
	$(".dm_tan").on("click",function(){
		$(".dm_box").show();
		$(".dm_tan").hide();
		if ($scope.is_sel_room!='') {
			danmu.start(1);
		}
	})
	$(".box_room .room").on("click",function(){
		var room_id=$(this).index()+1;
		$scope.roomname=room_id==1?'球迷房间':'彩民房间';
		if(!cookie("bbs_username")){
			addCookie("room_id",room_id,3600*24*30);
			$scope.is_sel_room=room_id;$scope.is_bg=false;
			$(".box_room").hide();
			$("#danmu2").show();
			if (typeof danmu != 'undefined') {
				danmu.start();
			}
			return;
		}
		var url_selroom="//pl.zhibo8.cc/room/index.php?room_id="+room_id;
		$.ajax({
			url:url_selroom,
			dataType:"jsonp",
			success:function(data){
				if(data.status="success"){
					if(!$scope.is_sel_room&&typeof danmu !='undefined'){
						danmu.start();
					}
					addCookie("room_id",room_id,3600*24*30);
					$scope.is_sel_room=room_id;$scope.is_bg=false;
					$(".box_room").hide();
					$("#danmu2").show();
				}else{
					alert(data.info);
					$scope.is_bg=!$scope.is_bg;
				}
			}
		});
	});
	$(".dm_pinglun img").on("click",function(e){
		if ($scope.rooms.length>0&&$scope.is_sel_room=='') {
			alert('请先选择房间，再发表评论！');
			return;
		}
		$(".input_area").show();
		$(".input_area .input_info textarea")[0].focus();
		if($(window).scrollTop()==0){
			$(window).scrollTop(100);
			$(window).scrollTop(0);
		}
		$(window).scrollTop(0);
		$("body,html").css({"overflow":"hidden"});
	});
	$(".input_area .input_info textarea").on("focus",function(){
		$(window).scrollTop(0);	
	});
	$(".input_area .send_info").on("click",function(){
		var val=$(".input_area .input_info textarea").val();
		$(".input_area .input_info textarea").val("");
		$scope.pl_content=val;
		$(".input_area").hide();
		$("body,html").css({"overflow":""});
		$scope.postpinglun();
		
	})
	$(".input_area .close_send").on("click",function(){
		$(".input_area").hide();
		$("body,html").css({"overflow":""});
	})
	$(".input_area .input_info input").on("click",function(e){
		e.stopPropagation();
	});
	
	/******************内页弹幕 */
	
	$scope.filterRoom=function(item){
		if($scope.rooms.length==0) return true;
		return item.room==$scope.is_sel_room;
	};
}]);
function bindBox (title) {
	$('<div class="model-box">'+
		'<style>.body-bg{position:fixed;top:0;left:0;right:0;bottom:0;z-index:1000;background:rgba(0,0,0,.3);}'+
			'.model-view{position:fixed;top:50%;left:50%;z-index:1001;background:#fff;width:80%;max-width:400px;font-size:16px;text-align:center;-webkit-border-radius:8px;border-radius:8px;-webkit-transform:translate(-50%,-50%);transform:translate(-50%,-50%);}'+
			'.model-view h1{padding:25px 25px 18px;font-weight:normal;color:#000;font-size:18px;}'+
			'.model-view .txt-tip{margin:8px 0 16px;padding:0 15px;font-size:14px;line-height:34px;}'+
			'.model-view a{float:left;width:50%;border-box:box-sizing;-webkit-border-box:box-sizing;height:40px;line-height:40px;}'+
			'.model-view .cancel{color:#ababab;border-radius:0 0 0 8px;-webkit-border-radius:0 0 0 8px;border-right:1px solid #f2f2f2;-webkit-box-sizing: border-box;box-sizing: border-box;} .model-view .to-bind{color:#77b7f9;border-radius:0 0 8px 0;}'+
		'</style>'+
		'<div class="body-bg"></div>'+
		'<div class="model-view"><h1>绑定手机号</h1><div class="txt-tip">'+title+'</div>'+
		'<div class="b-clear" style="border-top:1px solid #f2f2f2;overflow:hidden;"><a href="javascript:;" class="cancel">取消</a><a href="javascript:;" class="to-bind">去绑定手机</a></div></div>'+
	'</div>').appendTo(document.body)
	.find(".cancel").on("click",function () {
		$(this).parents(".model-box").remove();
	}).next(".to-bind").on("click",function () {
		location.href="http://bbs.zhibo8.cc/mobile/bind_tel.html";
	});
}

app.directive("roomsData",function($timeout){
	return{
		restrict:"AE",
		link:function(scope,ele,attr){
			$timeout(function(){
				scope.$emit("domLoad");
			});
		}
	};
});
function sureroom(){
	var $liset=$("#rooms_info li");
	var rooms=[];
	if($liset.length>0){
		$liset.each(function(i,item){
			rooms.push({id:$(item).attr("rid"),name:$(item).text()});
		});
	}
	console.log($liset);
	return rooms;
}


//content有文字表情替换为表情图片
function contentFormat(data,lists){
	for(var i=0;i<data.length;i++){
		data[i]['content']=formatEmoji(data[i]['content'],lists,2);
		if(data[i]['children'] && data[i]['children'].length>0){
			(function f(data){
				for(var i=0;i<data.length;i++){
					data[i]['content']=formatEmoji(data[i]['content'],lists,2);
					if(data[i]['children'].length>0){
						f(data[i]['children'])
					}
				}
			})(data[i]['children']);
		}
	}
	return data;
}
//加载表情图片集合
var getEmoji=(function($){
	var lists=[],flag=false;
	$.ajax({
		type:"get",
		url:"//m.zhibo8.cc/json/emoji/index.php",
		async:true,
		dataType:"json",
		success:function(data){
			try{
				if(data instanceof Array){
					lists=data;
				}else{
					flag=true;
				}
			}catch(e){
				flag=true;throw Error(e);
			}
		},
		error:function(){
			flag=true;
		}
	});
	
	return function(cb){
		if(lists.length==0){
			(function r(){
				setTimeout(function(){
					if(lists.length==0){
						if(flag) cb(lists);
						r();
					}else{
						cb(lists);
					}
				},10);
			})();
		}else {
			cb(lists);
		}
	}
})($);


//表情文字替换为图片
function formatEmoji(content,lists,size){
//	var reg=/\[(.+?)\]/g;
	var reg=/\[([^\[\]]+?)\]/g;
	var arr=[];
	while(arr=reg.exec(content)){
		for(var i=0;i<lists.length;i++){
			var list=lists[i]['list'],flag=false;
			for(var j=0;j<list.length;j++){
				if(arr[0]==list[j]['name_cn']){
					if(size) list[j]['src']=list[j]['src'].replace("@3x","@"+size+"x");
					var img='<img src="'+list[j]['src']+'"/>';
					content=content.replace(arr[0],img);
					flag=true;
					break;
				}
			}
			if(flag) break;
		}
	}
	return content;
}

function getchild_data(id, list, loop){
    var arr = new Array;
    for(var i=0;i<list.length;i++){
        if(0==list[i].parentid){
            continue;
        }
        if(id==list[i].parentid){
            if(loop%2 ==0)list[i].cclass = "";
            else list[i].cclass = "light";
            list[i].children = getchild_data(list[i].id, list, loop+1);
            arr.push(list[i]);
        }
    }
    return arr;
}

/*
 function getchild_old(id, list, loop){
 var html = "";
 if("undefined" == typeof(listpl))return html;
 for(var i=0;i<list.length;i++){
 if(id==list[i].parentid){
 if(loop%2 ==0)cclass = "";
 else cclass = "light";
 html += '<div class="yinyong '+cclass+'"><div class="info"><div class="left"><a href="//bbs.zhibo8.cc/?'+list[i].userid+'" target="_blank" class="user_name">'+list[i].username+'</a> 发表于'+list[i].createtime+' <a class="reply" cid="'+list[i].id+'" href="#re">回复</a></div><div class="right"><!-- '+list[i].act+' --> <span style="cursor:pointer; color:#4E84AE;" class="report_btn" cid="'+list[i].id+'" >举报</span></div></div><p class="word">'+list[i].content+'</p>'+getchild_old(list[i].id,list,loop+1)+'</div>';
 }
 }
 return html;
 }


 function getchild(list, loop){
 var html = "";
 for(var i=0;i<list.length;i++){
 if(loop%2 ==0)cclass = "";
 else cclass = "light";
 html += '<div class="yinyong '+cclass+'"><div class="info"><div class="left"><a href="//bbs.zhibo8.cc/?'+list[i].userid+'" target="_blank" class="user_name">'+list[i].username+'</a> 发表于'+list[i].createtime+' <a class="reply" cid="'+list[i].id+'"  href="#re">回复</a></div><div class="right"><!-- '+list[i].act+' --> <span style="cursor:pointer; color:#4E84AE;" class="report_btn" cid="'+list[i].id+'" onclick="report('+list[i].id+')" >举报</span></div></div><p class="word">'+list[i].content+'</p>'+getchild(list[i].children,loop+1)+'</div>';
 }
 return html;
 }
 */
function sortByKey(a,b){
    return  (parseInt(b.up)-parseInt(b.down)) - (parseInt(a.up)-parseInt(a.down));
}
/*
 function sortByCreatetime(a,b){
 return  parseInt((new Date(Date.parse(b.createtime.replace(/-/g,"/")))).getTime()) - parseInt((new Date(Date.parse(a.createtime.replace(/-/g,"/")))).getTime());
 }
 */

function keyprocess(e){
    e = e ? e : event;
    var keyCode = e.which ? e.which : e.keyCode;
    if(e.ctrlKey && keyCode == 13 || e.altKey && keyCode == 83) {
        $("#submit_btn").click();
    }
}

function report(id){
    alert("report:"+id)
}

//判断选择房间
function sureroom(){
	var liset=$("#rooms_info li");
	var rooms=[];
	if(liset.length!=0){
		liset.each(function(i,item){
			rooms.push({"id":$(item).attr("rid"),"name":$(item).text()});
		});
	}
	return rooms;
}
/****** 旧版举报功能 *******/
var forminfo = '<div id="forminfo" style="width:300px; height: 50px; background: #fff; border: solid 2px #CAD9EA; position: absolute; z-index: 999999; display: none; border-radius: 4px; -webkit-border-radius: 4px; -webkit-box-shadow: 0 0 5px #aaa; box-shadow: 0 0 5px #000; line-height: 50px; font-size: 16px; text-align: center; font-family: \'微软雅黑\', \'Microsoft YaHei\', \'黑体\', Arial;">理由：<input type="text" id="liyou" value=""/> <input id="report_submit_btn" type="button" value="提交" /> <input id="report_close_btn" type="button" value="关闭" /><input id="report_commentid" type="hidden" value="0" /></div>';
$(function(){
	
    $("body").append(forminfo);
    $(".report_btn").live("click",function(e){
        var cid = $(this).attr("cid");
        $("#forminfo").show();
        $("#forminfo").css("left","0");
        $("#forminfo").css("top",(e.pageY+10)+"px");
        $("#liyou").val("");
        $("#report_commentid").val(cid);
    });

    $("#report_submit_btn").live('click', function(event) {
        if($("#liyou").val().Trim()==""){
            alert("举报的理由不能为空.");
            return false;
        }
        if(cookie("report_"+$("#report_commentid").val())!="reported"){
            addCookie("report_"+$("#report_commentid").val(),"reported",3600);
        }else{
            alert("您举报的太频繁了！休息一下再来！");
            $("#forminfo").hide();
            return false;
        }
        var url = "//pl.zhibo8.cc/code/report_new.php?act=jsonp&cid="+$("#report_commentid").val()+"&content="+encodeURIComponent($("#liyou").val().Trim())+"&callback=?&random="+Math.random();
        jQuery.getJSON(url, function(data){
            if(data.report > 0){
                alert("举报已成功提交。");
                $("#forminfo").hide();
            }
        });
    });

    $("#report_close_btn").live('click', function(event) {
        $("#forminfo").hide();
    });
});

//微信分享
$(function(){
	//判断是在微信打开
	var ua = navigator.userAgent.toLowerCase();
	if(ua.indexOf("micromessenger")>-1 && window.wx){
		$.getScript('//m.zhibo8.cc/static/js/wxshare.js',function(){
			$.getJSON('//pl.zhibo8.cc/weixin/getSign.php?url=' +encodeURIComponent(location.href) + "&callback=?", function(res) {
				if(res.status == "success") {
					if(typeof Share == 'function'){
						var share = new Share().config(res.data);
					}
				}
			});
		});
	}
});


String.prototype.Trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

function cookie(name){
    var cookieArray=document.cookie.split("; "); //得到分割的cookie名值对
    var cookie=new Object();
    for (var i=0;i<cookieArray.length;i++){
        var arr=cookieArray[i].split("=");       //将名和值分开
        if(arr[0]==name)return unescape(arr[1]); //如果是指定的cookie，则返回它的值
    }
    return "";
}

function addCookie(objName,objValue,objSeconds){      //添加cookie
    var str = objName + "=" + escape(objValue);
    var domain=".zhibo8.cc";
//  var domain='.qiumibao.com';
    if(objSeconds > 0){                               //为0时不设定过期时间，浏览器关闭时cookie自动消失
        var date = new Date();
        var ms = objSeconds*1000;
        date.setTime(date.getTime() + ms);
        str += "; expires=" + date.toGMTString()+";domain="+domain+";path=/";
    }
    document.cookie = str;
}

//1111
/********绑定手机start***********/
var sec = 59;

$(function () {
    var timer,waitsec=59;

    //获取验证码
	$(document).on("click","#getvcode",function(e){
        if(phoneChk() && sec === waitsec){
            //发送请求验证码

            waitsec++; //防止重复点击

            $(this).css('background-color','#ccc');
            $("#phone").attr("readonly",true)

            //$("#bind").css('background-color','#FA8B15');

            phone_no = $("#phone").val();

            //get sign code
            $.ajax({
                url:'//pl.zhibo8.cc/usercenter/apply/sign.php?callback=?',
                timeout:3000,
                dataType:'jsonp',
                data:{"phone_no":phone_no},
                success:function(data){
                    if(data.status === 'success'){
                        sendData(data); //获取验证码
                    }else{
                        $(".msg").text('系统错误！').css("display",'block');
                    }
                },
                error:function(){

                }
            });

            timer = setInterval(function(){
                var $this = $("#getvcode");
                $this.text(sec + ' 秒');

                if(sec <= 0){
                    clearInterval(timer);

                    waitsec--;
                    sec = waitsec;

                    $this.text('获取验证码');
                    $this.css('background-color','#FA8B15');

                    $("#phone").attr("readonly",false);
                }else{
                    sec--;
                }
            },1000);
        }else{
            if(sec < waitsec){
                $(".msg").text('请稍后再获取！').css("display",'block');
            }
        }
    });

    //绑定手机
    $(document).on("click","#bind",function(e){
        $vcode = $("#vcode").val();

        if(phoneChk() && $vcode.length === 4){
            //get sign code
            $.ajax({
                url:'//pl.zhibo8.cc/usercenter/apply/sign.php?callback=?',
                timeout:3000,
                dataType:'jsonp',
                data:{"vcode":$vcode},
                success:function(data){
                    if(data.status === 'success'){
                        sendBind(data); //获取验证码
                    }else{
                        $(".msg").text('系统错误！').css("display",'block');
                    }
                },
                error:function(){

                }
            });
        }else{
            $(".msg").text('请正确输入验证码！').css("display",'block');
        }
    });
	
	$(document).on("click",".modal_box .cancle_btn",function(e){
		$(".full_screen").hide();
	});
	
});

//是否手机号
function phoneChk(){
    phone = $("#phone").val();

    console.log(phone);

    var myreg = /^13[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$/;

    if(!myreg.test(phone)){
        $(".msg").css("display",'block');

        return false;
    }else{
        $(".msg").css("display",'none');

        return true;
    }
}

//发送验证码请求
function sendData(data){
    $.ajax({
        url:'//pl.zhibo8.cc/usercenter/bind_phone/request.php?isvoice=1&callback=?',
        timeout:3000,
        dataType:'jsonp',
        data:data,
        success:function(data){
            $(".msg").text(data.info).css("display",'block');

            if(data.info.indexOf('验证码已发送') >= 0){
                $("#bind").css('background-color','#FA8B15');
            }else{
                sec = 3;
            }
        },
        error:function(){

        }
    });
}

//发送绑定请求
function sendBind(data){
    $.ajax({
        url:'//pl.zhibo8.cc/usercenter/bind_phone/bind.php?callback=?',
        timeout:3000,
        dataType:'jsonp',
        data:data,
        success:function(data){
            $(".msg").text(data.info).css("display",'block');

            if(data.status == 'success'){
                is_v = 1;
                $(".msg").text($(".msg").text()+'\n'+'页面将自动刷新.');
                $("#varea").hide();
                setTimeout('closeModalBox()',3000);
            }
        },
        error:function(){

        }
    });
}

function closeModalBox(){
    $(".full_screen").hide();
    window.location.reload();
}
/********绑定手机end*************/