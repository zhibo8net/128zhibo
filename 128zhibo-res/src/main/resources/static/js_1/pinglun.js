var advert_str_zuqiu = '娆у啝瓒崇悆娓告垙4鏈嶅紑鍚紒';//杩欓噷鏀剧疆骞垮憡鏂囧瓧 -zuqiu
var advert_url_zuqiu = '//ogzq2.wanjiashe.com/game.php?sid=4&pinglun';//杩欓噷鏀剧疆骞垮憡閾炬帴 -zuqiu
var advert_str_nba = '鐙绀煎寘锛佽寖鐗硅タ鏂版湇';//杩欓噷鏀剧疆骞垮憡鏂囧瓧 -nba
var advert_url_nba = '//ftx2.wanjiashe.com/game.php?sid=14&pinglun';//杩欓噷鏀剧疆骞垮憡閾炬帴 -nba

var advert_str = '';
var advert_url = '#';

var file = document.getElementById("pl_box").getAttribute("file");
if(file.indexOf('-nba-')>=0 || file.indexOf('-nbajijin-')>=0 || file.indexOf('-nbaluxiang-')>=0){
    advert_str = advert_str_nba;
    advert_url = advert_url_nba;
}else{
    advert_str = advert_str_zuqiu;
    advert_url = advert_url_zuqiu;
}

String.prototype.replaceAll = function(s1,s2) {
    return this.replace(new RegExp(s1,"gm"),s2);
}
var pl_path = file.replaceAll('-','/');


/**
 * Created by realvik on 15/1/15.
 */
//璁＄畻瀛楃涓查暱搴�
String.prototype.strLen = function() {
    var len = 0;
    for (var i = 0; i < this.length; i++) {
        if (this.charCodeAt(i) > 255 || this.charCodeAt(i) < 0) len += 2; else len ++;
    }
    return len;
}
//灏嗗瓧绗︿覆鎷嗘垚瀛楃锛屽苟瀛樺埌鏁扮粍涓�
String.prototype.strToChars = function(){
    var chars = new Array();
    for (var i = 0; i < this.length; i++){
        chars[i] = [this.substr(i, 1), this.isCHS(i)];
    }
    String.prototype.charsArray = chars;
    return chars;
}
//鍒ゆ柇鏌愪釜瀛楃鏄惁鏄眽瀛�
String.prototype.isCHS = function(i){
    if (this.charCodeAt(i) > 255 || this.charCodeAt(i) < 0)
        return true;
    else
        return false;
}
//鎴彇瀛楃涓诧紙浠巗tart瀛楄妭鍒癳nd瀛楄妭锛�
String.prototype.subCHString = function(start, end){
    var len = 0;
    var str = "";
    this.strToChars();
    for (var i = 0; i < this.length; i++) {
        if(this.charsArray[i][1])
            len += 2;
        else
            len++;
        if (end < len)
            return str;
        else if (start < len)
            str += this.charsArray[i][0];
    }
    return str;
}
//鎴彇瀛楃涓诧紙浠巗tart瀛楄妭鍒癳nd瀛楄妭锛�
String.prototype.mysubCHString = function(start, end){
    var len = 0;
    var str = "";
    this.strToChars();
    for (var i = 0; i < this.length; i++) {
        if(this.charsArray[i][1]){
            len += 2;
        } else{
            if(i==0)len++;
        }
        if (end < len)
            return str;
        else if (start < len)
            str += this.charsArray[i][0];
    }
    return str;
}
//鎴彇瀛楃涓诧紙浠巗tart瀛楄妭鎴彇length涓瓧鑺傦級
String.prototype.subCHStr = function(start, length){
    return this.subCHString(start, start + length);
}
//鎴彇瀛楃涓诧紙浠巗tart瀛楄妭鎴彇length涓瓧鑺傦級
String.prototype.mysubCHStr = function(start, length){
    return this.mysubCHString(start, start + length);
}











/*****************************************************************/
String.prototype.Trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.LTrim = function()
{
    return this.replace(/(^\s*)/g, "");
}
String.prototype.RTrim = function()
{
    return this.replace(/(\s*$)/g, "");
}


var vm = avalon.define({
    $id: "title_controller",
    info:'<a style="font-size:12px; line-height:26px;" href="#re">璇勮杞藉叆涓�</a>',
    advert:''
});

//鍖归厤鍥芥棗鎸備欢
var n_pendent=[{"name":"鑻辨牸鍏�","url":"https://duihui.qiumibao.com/zuqiu/yinggelan.png","reg":"馃嚞馃嚙"},
{"name":"淇勭綏鏂�","url":"https://duihui.qiumibao.com/zuqiu/eluosi.png","reg":"馃嚪馃嚭"},
{"name":"涓浗","url":"https://duihui.qiumibao.com/zuqiu/zhongguo.png","reg":"馃嚚馃嚦"},
{"name":"娌欑壒闃挎媺浼�","url":"https://duihui.qiumibao.com/zuqiu/shatealabo.png","reg":"馃嚫馃嚘"},
{"name":"鍩冨強","url":"https://duihui.qiumibao.com/zuqiu/aiji.png","reg":"馃嚜馃嚞"},
{"name":"涔屾媺鍦�","url":"https://duihui.qiumibao.com/zuqiu/wulagui.png","reg":"馃嚭馃嚲"},
{"name":"钁¤悇鐗�","url":"https://duihui.qiumibao.com/zuqiu/putaoya.png","reg":"馃嚨馃嚬"},
{"name":"瑗跨彮鐗�","url":"https://duihui.qiumibao.com/zuqiu/xibanya.png","reg":"馃嚜馃嚫"},
{"name":"鎽╂礇鍝�","url":"https://duihui.qiumibao.com/zuqiu/moluoge.png","reg":"馃嚥馃嚘"},
{"name":"浼婃湕","url":"https://duihui.qiumibao.com/zuqiu/yilang.png","reg":"馃嚠馃嚪"},
{"name":"娉曞浗","url":"https://duihui.qiumibao.com/zuqiu/faguo.png","reg":"馃嚝馃嚪"},
{"name":"婢冲ぇ鍒╀簹","url":"https://duihui.qiumibao.com/zuqiu/aodaliya.png","reg":"馃嚘馃嚭"},
{"name":"绉橀瞾","url":"https://duihui.qiumibao.com/zuqiu/bilu.png","reg":"馃嚨馃嚜"},
{"name":"涓归害","url":"https://duihui.qiumibao.com/zuqiu/danmai.png","reg":"馃嚛馃嚢"},
{"name":"闃挎牴寤�","url":"https://duihui.qiumibao.com/zuqiu/agenting.png","reg":"馃嚘馃嚪"},
{"name":"鍐板矝","url":"https://duihui.qiumibao.com/zuqiu/bingdao.png","reg":"馃嚠馃嚫"},
{"name":"鍏嬬綏鍦颁簹","url":"https://duihui.qiumibao.com/zuqiu/keluodiya.png","reg":"馃嚟馃嚪"},
{"name":"灏兼棩鍒╀簹","url":"https://duihui.qiumibao.com/zuqiu/niriliya.png","reg":"馃嚦馃嚞"},
{"name":"宸磋タ","url":"https://duihui.qiumibao.com/zuqiu/baxi.png","reg":"馃嚙馃嚪"},
{"name":"鐟炲＋","url":"https://duihui.qiumibao.com/zuqiu/ruishi.png","reg":"馃嚚馃嚟"},
{"name":"鍝ユ柉杈鹃粠鍔�","url":"https://duihui.qiumibao.com/zuqiu/gesidalijia.png","reg":"馃嚚馃嚪"},
{"name":"濉炲皵缁翠簹","url":"https://duihui.qiumibao.com/zuqiu/saierweiya.png","reg":"馃嚪馃嚫"},
{"name":"寰峰浗","url":"https://duihui.qiumibao.com/zuqiu/deguo.png","reg":"馃嚛馃嚜"},
{"name":"澧ㄨタ鍝�","url":"https://duihui.qiumibao.com/zuqiu/moxige.png","reg":"馃嚥馃嚱"},
{"name":"鐟炲吀","url":"https://duihui.qiumibao.com/zuqiu/ruidian.png","reg":"馃嚫馃嚜"},
{"name":"闊╁浗","url":"https://duihui.qiumibao.com/zuqiu/hanguo.png","reg":"馃嚢馃嚪"},
{"name":"姣斿埄鏃�","url":"https://duihui.qiumibao.com/zuqiu/bilishi.png","reg":"馃嚙馃嚜"},
{"name":"宸存嬁椹�","url":"https://duihui.qiumibao.com/zuqiu/banama.png","reg":"馃嚨馃嚘"},
{"name":"绐佸凹鏂�","url":"https://duihui.qiumibao.com/zuqiu/tunisi.png","reg":"馃嚬馃嚦"},
{"name":"娉㈠叞","url":"https://duihui.qiumibao.com/zuqiu/bolan.png","reg":"馃嚨馃嚤"},
{"name":"濉炲唴鍔犲皵","url":"https://duihui.qiumibao.com/zuqiu/saineijiaer.png","reg":"馃嚫馃嚦"},
{"name":"鍝ヤ鸡姣斾簹 ","url":"https://duihui.qiumibao.com/zuqiu/gelunbiya.png","reg":"馃嚚馃嚧"},
{"name":"鏃ユ湰","url":"https://duihui.qiumibao.com/zuqiu/riben.png","reg":"馃嚡馃嚨"},
{"name":"鑽峰叞 ","url":"https://duihui.qiumibao.com/zuqiu/helan.png","reg":"馃嚦馃嚤"},
{"name":"鎰忓ぇ鍒� ","url":"https://duihui.qiumibao.com/zuqiu/yidali.png","reg":"馃嚠馃嚬"}];
avalon.filters.user_pendent_hot=function(name){
//  name=name+'馃嚚馃嚪';
    for(var i=0;i<n_pendent.length;i++){
        if(name.indexOf(n_pendent[i]['reg'])>-1){
            var style='float:left;width:auto;max-width:105px;text-overflow:ellipsis; overflow:hidden; white-space:nowrap;padding-right:20px;background:url('+n_pendent[i]['url']+') no-repeat right center;background-size:16px;';
            return '<span style="'+style+'">'+name.replace(n_pendent[i]['reg'],'')+'</span>';
        }
    }
    return name;
}
avalon.filters.user_pendent=function(name){
//  name=name+'馃嚚馃嚪';
    for(var i=0;i<n_pendent.length;i++){
        if(name.indexOf(n_pendent[i]['reg'])>-1){
            return name.replace(n_pendent[i]['reg'],'<img  class="sm-pendent" style="margin-left:6px;width:16px;vertical-align: middle;" src="'+n_pendent[i]['url']+'" />');
        }
    }
    return name;
}
avalon.filters.del_pendent=function(name){
//  name=name+'馃嚚馃嚪';
    for(var i=0;i<n_pendent.length;i++){
        if(name.indexOf(n_pendent[i]['reg'])>-1){
            return name.replace(n_pendent[i]['reg'],'');
        }
    }
    return name;
}


var model = avalon.define({
    $id: "pl_app",
    name: "鐩存挱鍚ц瘎璁虹郴缁�",
    advert_str: advert_str,
    advert_url: advert_url,
    qqlogin_url: "//bbs.zhibo8.cc/connect.php?mod=login&op=init&referer="+document.location.href+"&statfrom=login_simple",
    file: document.getElementById("pl_box").getAttribute("file"),//$("#pl_box").attr("file"),
    userid: cookie("bbs_uid"),
    username: decodeURI(cookie("bbs_username")),
    rooms:sureroom(),
    is_sel_room:location.href.indexOf('showall') != -1 ? 0 : (sureroom().length==0?0:cookie('room_id')),//鎴块棿鍙�
    sel_room:function(room_id){//閫夋嫨鎴块棿
        if(model.is_sel_room==room_id)return;
        //鏈櫥褰曠敤鎴烽€夋嫨鎴块棿
        if(model.userid==''){
            model.is_sel_room=room_id;
            addCookie('room_id',room_id,3600*24*30);
            return ;
        }
        $.ajax({
            type:"get",
            url:"//pl.zhibo8.cc/room/index.php?room_id="+room_id,
            dataType:"jsonp",
            success:function(data){
                 if(data.status=="success"){
                    model.is_sel_room=room_id;
                    addCookie('room_id',room_id,3600*24*30);
                }else{
                    alert(data.info);
                }
            },
            error : function(data){
                alert('fail');
            }
        });
    },
    is_rookie:cookie("is_rookie"),//缁戝畾鎵嬫満鐢�
    replyname: '',
    isshoweditortip: true,//鏄惁鏄剧ず鍙戣〃璇存槑
    emoji:[],
    is_show_smiley: false, //鏄惁鏄剧ず琛ㄦ儏閫夋嫨鍣�
    show_smiley_box: function(){
        model.is_show_smiley = !model.is_show_smiley;
        if(model.emoji.length==0){
            getEmoji(function(lists){
                if(lists && lists.length>0){
//                  console.log(lists);
                    model.emoji=lists;
                }
            });
        }
    },
    mk_posIndex:0,
    selectCurImg:function(item,obj){
        //console.log(item['name_cn']);
        model.isshoweditortip=false;
        var posIndex=getTxt1CursorPosition(obj.target);
        if(posIndex){
            model.content=model.content.substring(0,posIndex)+item['name_cn']+model.content.substring(posIndex);
        }else if(posIndex==-1){
            model.content=model.content+item['name_cn'];
        }
    },
    enterCurImg:function(item,obj){
        $(obj.target).addClass("img-box");
    },
    leaveCurImg:function(item,obj){
        $(obj.target).removeClass("img-box");
    },
    hideeditortip: function(){
        model.isshoweditortip = false;
    },
    images:{'default':[],'coolmonkey':[],'grapeman':[]},
    bigimg_src:'',
    smiley_index:0,

    showbigimg: function(src){
        model.bigimg_src = '//bbs.zhibo8.cc/static/image/smiley/'+src;
    },

    change_smiley: function(index){
        model.smiley_index = index;
    },


    content:'',

    insert_to_text:function(imgno){
        model.content += imgno;
    },

    postinfo: '',

    parent_id: 0,

    replyname: '',

    reply: function(pid, username){
        model.parent_id = pid;
        model.replyname = username;
        //window.location = '#re';
        $('html,body').animate({scrollTop:$('#post_content').offset().top-50}, 300);
    },

    updown: function(cid, act){
        // alert($("."+act+"_btn[cid='"+cid+"']").html());return false;
        var url = "//pl.zhibo8.cc/code/"+act+".php?_platform2=pc&act=jsonp&cid="+cid+"&callback=?";
        jQuery.getJSON(url, function(data){
            if (data.status == -2) {
                alert('杩涜椤惰俯鎿嶄綔涔嬪墠锛岃鍏堢櫥褰�');
                model.show_login_box(true);
            } else {
                var num = 0;
                if(act == 'up'){
                    num = data.up;
                }else{
                    num = data.down;
                }
                if(num == 0){
                    alert("璇峰嬁閲嶅椤惰俯锛�");
                }else{
                    $("."+act+"_btn[cid='"+cid+"']").children('.'+act+'_cnt').html(num);
                }
            }
        });
    },

    box_x:0,
    box_y:0,
    input_username:'',
    input_password:'',
    isshowloginbox: false,
    /******************浜屾楠岃瘉***/
    checkCode:function(popup){
        var domain="//bbs.zhibo8.cc";
        $.ajax({
            url: domain+"/user/captcha?t=" + (new Date()).getTime()+"&callback=?", // 鍔犻殢鏈烘暟闃叉缂撳瓨
            type: "get",
            dataType: "json",
            success: function (data) {
                // 浣跨敤initGeetest鎺ュ彛
                // 鍙傛暟1锛氶厤缃弬鏁�
                // 鍙傛暟2锛氬洖璋冿紝鍥炶皟鐨勭涓€涓弬鏁伴獙璇佺爜瀵硅薄锛屼箣鍚庡彲浠ヤ娇鐢ㄥ畠鍋歛ppendTo涔嬬被鐨勪簨浠�

                initGeetest({
                    gt: data.gt,
                    challenge: data.challenge,
                    product: "popup", // 浜у搧褰㈠紡锛屽寘鎷細float锛宔mbed锛宲opup銆傛敞鎰忓彧瀵筆C鐗堥獙璇佺爜鏈夋晥
                    offline: !data.success // 琛ㄧず鐢ㄦ埛鍚庡彴妫€娴嬫瀬楠屾湇鍔″櫒鏄惁瀹曟満锛屼竴鑸笉闇€瑕佸叧娉�
                    // 鏇村閰嶇疆鍙傛暟璇峰弬瑙侊細//www.geetest.com/install/sections/idx-client-sdk.html#config
                }, popup);

            }
        });
    },
    handlerPopup:function (captchaObj) {//鏃ц鍧涚櫥褰�
        // 鎴愬姛鐨勫洖璋�
        captchaObj.onSuccess(function () {
            var validate = captchaObj.getValidate();
            $.ajax({
                //url: "//pl.zhibo8.cc/ajaxLogin.php?callback=?", // 杩涜浜屾楠岃瘉
                url:"//bbs.zhibo8.cc/user/login?callback=?",
                type: "post",
                dataType: "json",
                data: {
                    type: "pc",
                    username: $(".username_input").val(),
                    password: $(".password_input").val(),
                    geetest_challenge: validate.geetest_challenge,
                    geetest_validate: validate.geetest_validate,
                    geetest_seccode: validate.geetest_seccode
                },
                success: function (data) {
                    if(data.status=="1"){
                        if(data.act=='bind_phone'){
                            bindBox(data.title,model);
                        }else{
                            model.userid = data.userid;
                            model.username = model.input_username;

                        }
                        model.loging = false;
                        model.isshowloginbox = false;
                        model.input_password = '';
                    }else{
                        alert(data.mesg||'鐧诲綍澶辫触锛�');
                        model.loging = false;
                    }
                    $(".username_input").val("");
                    $(".password_input").val("");
                }
            });
        });

        $(".login_input").click(function () {
    //  submitClick.hadclick=function(){
            captchaObj.show();
    //  }
        });
        // 灏嗛獙璇佺爜鍔犲埌id涓篶aptcha鐨勫厓绱犻噷
        captchaObj.appendTo("#popup-captcha");
        // 鏇村鎺ュ彛鍙傝€冿細//www.geetest.com/install/sections/idx-client-sdk.html
    },

    /***************************浜屾楠岃瘉***/
    first:0,
    showloginBox: function (e) {
        //model.box_x = e.pageX-$("#main").offset().left;
        //model.box_y = e.pageY-$("#main").offset().top;
        model.box_x = e.pageX-240;
        model.box_y = e.pageY;
        model.isshowloginbox = true;
        if(model.first++==0){
            $(document.body).append('<style>.gt_holder.popup .gt_mask{z-index:11111;}</style><div id="popup-captcha"></div>');
            loadScript('//static.geetest.com/static/tools/gt.js',function(status){
                if(typeof initGeetest==='function'){
                    model.checkCode(model.handlerPopup);
                }

            });
        }
    },
    hideloginbox: function(){
        model.isshowloginbox = false;
    },

    loging: false,
    login:function(){
        if(typeof initGeetest==='function'){
            //$(".gt_holder").show();
//          $(".login_input").click();
        }else{
            model.loging = true;
//          var url = '//pl.zhibo8.cc/ajaxLogin.php';
            var url='//bbs.zhibo8.cc/user/login';
            url = url+"?username="+encodeURIComponent($(".username_input").val())+"&password="+encodeURIComponent($(".password_input").val())+"&callback=?";
            jQuery.getJSON(url, function(data){
                if(data.statu=="error"){
                    alert(data.value );
                    model.loging = false;
                }else{
                    console.log(data);return;
                    model.userid = data.userid;
                    model.username = model.input_username;
                    model.loging = false;
                    model.isshowloginbox = false;

                    model.input_password = '';

                }
            });
        }
    },

    keyprocess: function(e){
        var keyCode = e.keyCode;
        if(e.ctrlKey && keyCode == 13 || e.altKey && keyCode == 83) {
            //alert(keyCode+" "+event.ctrlKey);
            model.postpinglun();
        }
    },

    keyprocess2:function(e){
        var keyCode = e.keyCode;
        if(keyCode == 13) {
            model.login();
        }
    },


    logout: function(){
        var url = '//pl.zhibo8.cc/ajaxLogout.php?callback=?';
        jQuery.getJSON(url, function(data) {
            if(data.statu=='success'){
                model.userid = '';
                model.username = '';
//              model.is_sel_room='';
//              addCookie('room_id','');
            }
        });
    },

    tipshow:false,
    tipcn:'',
    postpinglun: function(){
        //alert("璇勮鏈嶅姟鍣ㄥ崌绾т腑锛岃绋嶅悗鍙戣〃璇勮.");return false;
        //alert(model.parent_id);return false;

        //$("#submit_btn").text("鍥炲涓�...");
        if(model.is_sel_room===''){
            alert("璇峰厛閫夋嫨鎴块棿锛屽啀鍙戣〃璇勮!");
            return;
        }
        if(checkpost()){
        }else{
            return false;
        }
//      alert(model.parent_id);return;
//         var url = "//pl.zhibo8.cc/ajaxPost.php?filename="+model.file+"&userid="+model.userid+"&username="+model.username+"&parentid="+model.parent_id+"&content="+encodeURIComponent(model.content.Trim())+"&callback=?";
        var url = "//pl.zhibo8.cc/ajaxPost.php?filename="+model.file+"&userid="+model.userid+"&username="+model.username+"&parentid="+model.parent_id+"&content="+encodeURIComponent($("#post_content").val().Trim())+"&callback=?";
        jQuery.getJSON(url, function(data){
            ispost = false;
            if(data.status=="error"){
                (data.value||data.info)&&alert(data.value || data.info || '鏈兘鎴愬姛鍙戣〃璇勮');
                $("#submit_btn").text("鍙戣〃 ctrl+enter");
                $("#post_content").css('background-color','#fff');
                ispost = false;
                if(data.act!='bind_phone'){
                    $("#post_content").val('');
                }
                //缁戝畾鎵嬫満
                if(data.value.indexOf('缁戝畾鎵嬫満')>=0){
                    $(".full_screen").show();
                }
            }else{
                $("#submit_btn").text("鍙戣〃 ctrl+enter");
                $("#post_content").css('background-color','#fff');
                ispost = false;
                //model.refresh();
                model.tipshow=true;model.tipcn=data.value;
                var content=$("#post_content").val();
                getEmoji(function(lists){
                    content=formatEmoji(content,lists);
                    model.list.unshift({appname:'web',appver:null,children:[],content:content.Trim(),createtime:idate(),device:null,down:'0',figureurl:null,filename:model.file,id:'0',ip:'0.0.0.1',level:'0',m_uid:null,parentid:'0',platform:null,report:'0',status:'normal',sysver:null,up:'0',updatetime:idate(),userid:model.userid,userinfo:null,username:model.username,room:data.room_id});
                });
                // model.list.unshift({appname:'web',appver:null,children:[],content:model.content.Trim(),createtime:idate(),device:null,down:'0',figureurl:null,filename:model.file,id:'0',ip:'0.0.0.1',level:'0',m_uid:null,parentid:'0',platform:null,report:'0',status:'normal',sysver:null,up:'0',updatetime:idate(),userid:model.userid,userinfo:null,username:model.username,room:data.room_id});
                setTimeout(function(){model.tipshow=false},3000)
                $("#post_content").val('');
                model.parent_id = 0;
                model.replyname = '';
                if(model.is_sel_room!==0){
                    model.is_sel_room=data.room_id;
                }
            }
            if(data.act=='bind_phone'){
                bindBox(data.title,model);
            }
        });
    },


    locationToRe: function(){
        window.location = '#re';
    },


    isshowimg:false,
    onrendered: function(){
        //$(function(){
        //    $("img.lazy").lazy({
        //        effect: "fadeIn",
        //        effectTime: 1000
        //    });
        //});
        bindBoxEvent()
    },

    showimg: function(){
        //alert("123"+this)
        //$("img.lazy").lazy({
        //    effect: "fadeIn",
        //    effectTime: 1000
        //});

        if(model.comment_id > 0){
            var $span = $('#pllist span[cid="' + model.comment_id + '"]');

            if($span.length){
                $('html,body').animate({scrollTop:$span.first().offset().top}, 1000);
            }

            model.comment_id = 0;
        }
    },


    all_num_info: 0,
    root_num:0,
    root_normal_num:0,
    pageno:0,
    addClass: function() {
        var level = 0
        var parent = this.parentNode
        do {
            if (parent.tagName == "DIV" && parent.className == "yinyong")   {
                level++
            }
            if (parent.tagName == "TABLE") {
                break
            }
        } while (parent = parent.parentNode)
        avalon(this).addClass(level%2===0?'dark':'light')
    },
    refresh: function(){
        $.getJSON("//cache.zhibo8.cc/json/"+pl_path+"_hot.htm?key="+Math.random(), function(data){
            getEmoji(function(lists){
                for(var i=0;i<data.length;i++){
                    data[i]['content']=formatEmoji(data[i]['content'],lists);
                }
                model.hotlist = data;
            });
//          model.hotlist = data;
        });
        model.list = [];
        loaddata(model.file, -1);

        model.prevlist = [];
        model.floor_num = 0;

        $('#pllist .prevtab').remove();
    },
    loadmore_info:'鐐瑰嚮鍔犺浇鏇村璇勮',
    loadmore: function(){
        if(model.isluxiang && model.firstload === 1){
            model.loadpinglun();

            return;
        }

        if(model.pageno>0){
            model.pageno--;
        } else{
            model.loadmore_info = '璇勮宸插叏閮ㄥ姞杞藉畬鎴�';
            return false;
        }
        loaddata(model.file, model.pageno);
        //model.isshowimg = true;
    },
    firstload:1,
    loadpinglun: function(){
        $.getJSON("//cache.zhibo8.cc/json/"+pl_path+"_hot.htm?key="+Math.random(), function(data){
            getEmoji(function(lists){
                for(var i=0;i<data.length;i++){
                    data[i]['content']=formatEmoji(data[i]['content'],lists);
                }
                model.hotlist = data;
            });
//          model.hotlist = data;
        });
        loaddata(model.file, -1);
        model.firstload = 0;
    },
    list: [
        //{'content':'璇勮杞藉叆涓€傘€傘€�'}
    ],
    hotlist: [
        //{'content':'璇勮杞藉叆涓€傘€傘€�'}
    ],
    showmore: false,
    iszhibo : location.href.indexOf('/zhibo/') > 0 ? true : false,
    //iszhibo:true,
    isluxiang : (location.href.indexOf('luxiang') > 0 && location.href.indexOf('floor=') == -1) ? true : false,
    pagemax : 0,
    pageprev : 0,
    floor_num : J_get('floor'),
    comment_id : J_get('comment_id'),
    loadprev : function(){
        if(model.pageprev < model.pagemax){
            $.getJSON("//cache.zhibo8.cc/json/"+pl_path+"_"+ (++model.pageprev) +".htm?key="+Math.random(), function(data,status){
                model.prev_count = Math.min((model.pageprev + 1) * 100, model.root_num);
                getEmoji(function(lists){
                    var new_data=contentFormat(data,lists);
                    model.prevlist = new_data;
                });

//              model.prevlist = data;
            });
        }
    },
    prevlist : [],
    floor_count : 0,
    prev_count : 0,
    showprev : function(){
        $('#pllist').prepend($('#pltpl').html());
    },
    //褰曞脊妗�
    loginView:false,
    login_isFirst:0,
    show_login_box:function(flag){
        model.loginView=flag;
        if(!flag){
            model.m_info='';
            model.tel='';
            model.code='';
            model.ck_status='';
            $(".username_input").val("");
            $(".password_input").val("");
        }else{
            var stop=$(window).scrollTop(),
                winH=$(window).height();
            var  pos=stop+winH/2+"px";
            $(".loginView").css({
                "position":"absolute",
                "top":pos
            })
        }
        if(!model.login_isFirst++){
        	//鑾峰彇楠岃瘉鐮�
			getCodeImg();
            loadScript('https://acstatic-dun.126.net/tool.min.js',function(){
                // 鍙渶鍒濆鍖栦竴娆�
                initWatchman({
                    productNumber: 'YD00000601637071',
                    onload: function (instance) {
                        var wm = instance;
                        wm & wm.getToken('3b4a04257ad54abeab71558df927bf7f', function(token) {
                            addCookie('token',token,3600*1*30);
                        });
                    }
                });
            });
        }
    },
    /*鎵嬫満鐧诲綍 +缁戝畾鎵嬫満寮€濮�*/
    tel:'',
    code:'',
    ck_status:'',
    btn_status:'鍙戦€侀獙璇佺爜',
    btn_able:true,
    m_info:'',
    mesg:{
    'err1':'璇疯緭鍏ユ墜鏈哄彿',    'err2':'璇疯緭鍏ラ獙璇佺爜',    'err3':'鎵嬫満鍙锋牸寮忔湁璇�',
    'err4':'璇峰厛娉ㄥ唽',  'err5':'杈撳叆鐨勯獙璇佺爜鏈夎锛岃閲嶆柊杈撳叆',    'info':'楠岃瘉鐮佸凡鍙戦€侊紝浜斿垎閽熷唴鏈夋晥','err6':'璇疯緭鍏ュ浘褰㈤獙璇佺爜'
    },
    getFocus:function(event){
        model.setBorder(event,true);
    },
    setBlur:function(tel){
        model.setBorder(event,false);
        if(typeof tel ==='string' && tel){
            model.ck_status=true;
//          var myreg = /^13[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}19{9}$/;
//          if(myreg.test(tel)){
//              model.ck_status=true;
//          }else{
//              model.ck_status=false;
//          }
        }
    },
    count_down:function(num){
        var mesg=model.mesg;
        num=num||60;
        model.btn_able=false;
//      model.m_info=mesg.info;
        (function f(){
            model.btn_status='宸插彂閫侀獙璇佺爜('+num+')s';
            num--;
            if(num>0){
                setTimeout(function(){
                    f();
                },1000);
            }else{
                model.btn_status='鍙戦€侀獙璇佺爜';
                model.m_info='';
                model.btn_able=true;
            }
        })();
    },
    getCode:function(tel){//鎵嬫満鐧诲綍鑾峰彇楠岃瘉鐮�
        var mesg=model.mesg;
        if(!tel){
            model.m_info=mesg.err1;return ;
        }else if(!model.ck_status){
            model.m_info=mesg.err3;return ;
        }else if(!model.verified_code){
			model.m_info=mesg.err6;return;
		}else{
            if(model.btn_able){
                var time=~~(new Date().getTime()/1000);
                $.getJSON('//pl.zhibo8.cc/get_check.php?type=sms_login&phone_no='+tel+'&time='+time+'&callback=?',function (data) {
                    if(data.status==='success'){
                        var chk=data.data.chk;
                        $.getJSON('//pl.zhibo8.cc/mobile/sms_login_vcode.php?phone_no='+tel+'&time='+time+'&chk='+chk+'&verified_code='+model.verified_code+'&vcode='+model.vcode+'&callback=?',function (data) {
                            model.m_info=data.info||data.info1;
                            if(data.status=='success'){
                                model.count_down(60);
                            }
                        });
                    } else {
                        model.m_info = data.info;
                    }
                });
            }
        }
    },
    setBorder:function(event,flag){//flag锛歵rue寰楀埌鐒︾偣锛宖alse澶卞幓鐒︾偣
        var $obj=$(event.target);
        if(flag){
            $obj.parents(".set_border").addClass("blue-box")
        }else{
            $obj.parents(".set_border").removeClass("blue-box");
        }
    },
    telLogin:function(tel,checkCode){//鎵嬫満鐧诲綍锛屽畬鎴愮櫥褰�
        var mesg=model.mesg;
        if(!tel){
            model.m_info=mesg.err1;return ;
        }else if(!model.ck_status){
            model.m_info=mesg.err3;return ;
        }else if(!checkCode){
            model.m_info=mesg.err2;return ;
//      }else if(!/^[0-9]{4}$/.test(checkCode)){
//          model.m_info=mesg.err5;return ;
        }else{
//          if(!model.btn_able){
//              return ;
//          }
            model.m_info=mesg.info;
            var time=~~(new Date().getTime()/1000);
            $.getJSON('//pl.zhibo8.cc/get_check.php?type=sms_login&phone_no='+tel+'&time='+time+'&vcode='+checkCode+'&callback=?',function (data) {
                if(data.status==='success'){
                    var chk=data.data.chk;
                    $.getJSON('//pl.zhibo8.cc/mobile/sms_login.php?phone_no='+tel+'&vcode='+checkCode+'&time='+time+'&chk='+chk+'&callback=?',function(data){
                        if(data.status=='success'){
                            location.reload();
                        }else{
                            model.m_info=data.info;
                        }
                    });
                }
            });
        }
    },
    telCheck:false,
    sendSuccess:false,
    getBindCode:function (tel) {//缁戝畾鎵嬫満鑾峰彇楠岃瘉鐮�
        var mesg=model.mesg;
        if(!tel){
            model.m_info=mesg.err1;return ;
        }else if(!model.ck_status){
            model.m_info=mesg.err3;return ;
        }else{
            if(model.btn_able){
                var time=~~(new Date().getTime()/1000);
                $.getJSON('//pl.zhibo8.cc/get_check.php?type=get_code&phone_no='+tel+'&time='+time+'&callback=?',function (data) {
                    if(data.status==='success'){
                        var chk=data.data.chk;
                        $.getJSON('//pl.zhibo8.cc/usercenter/bind_phone/request.php?phone_no='+tel+'&time='+time+'&chk='+chk+'&callback=?',function (data) {
                            if(data.status=='success'){
                                model.sendSuccess=true;
                                model.count_down();
                            }
                            model.m_info=data.info;

                        });
                    }
                });
            }
        }
    },
    processBind:function (tel,checkCode) {//瀹屾垚缁戝畾
        var mesg=model.mesg;
        if(!tel){
            model.m_info=mesg.err1;return ;
        }else if(!model.ck_status){
            model.m_info=mesg.err3;return ;
        }else if(!checkCode){
            model.m_info=mesg.err2;return ;
//      }else if(!/^[0-9]{4}$/.test(checkCode)){
//          model.m_info=mesg.err5;return ;
        }else{
            var time=~~(new Date().getTime()/1000);
            $.getJSON('//pl.zhibo8.cc/get_check.php?type=bind_phone&phone_no='+tel+'&time='+time+'&vcode='+checkCode+'&callback=?',function (data) {
                if(data.status==='success'){
                    var chk=data.data.chk;
                    $.getJSON('//pl.zhibo8.cc/usercenter/bind_phone/bind.php?phone_no='+tel+'&vcode='+checkCode+'&time='+time+'&chk='+chk+'&callback=?',function(data){
                        if(data.status=='success'){
                            location.reload();
                        }else{
                            model.m_info=data.info;
                        }
                    });
                }
            });
        }
    },
    closeBox:function (flag) {
        model.telCheck=flag;
        model.tel='';
        model.code='';
        model.telCheck=false;
        model.sendSuccess=false;
        model.m_info='';
        model.ck_status='';
    }
    /*鎵嬫満鐧诲綍+缁戝畾鎵嬫満  缁撴潫*/
   ,
	vcode:'',
	verified_code:'',
	update_code_img:function(){
		getCodeImg();
	}
})
function getCodeImg(){
	var $code_img=$("#code-img");
	$.get("//pl.zhibo8.cc/get_vcode.php",function (res) {
		if(res.status){
			model.vcode=encodeURIComponent(res.data.vcode);
			$code_img.html('<img src="'+res.data['path']+'" />');
		}else{
			$code_img.html('鐐瑰嚮鑾峰彇');
		}
	},"json");
}

/*鎵嬫満鐧诲綍鐨勬搷浣� */
function bindBoxEvent(){
    //鎵嬫満鐧诲綍鐐瑰嚮鍒囨崲寮规
    $("#logindiv .login-m").on("click",function(e){
        $("#nouserinfo .login-m .m-login-box").hide();
        $(this).children(".m-login-box").show();
        if(e.stopPropagation){
            e.stopPropagation();
        }
        hide_code_box();
    });
    $("#nouserinfo .login-m").on("click",function(e){
        $("#logindiv .login-m .m-login-box").hide();
        $(this).children(".m-login-box").show().find("input").on("keydown",function (event) {
            var keyCode=event.keyCode;
            if(keyCode==40||keyCode==38){
                return false;
            }
        });
        if(e.stopPropagation){
            e.stopPropagation();
        }
        hide_code_box();
    });
    $(window).click(function(e){
        hide_code_box();
    });
    //鎵弿鐧诲綍鎻愮ず妗�
    var is_sao=false,//鏄惁宸茶鎵�
        is_round=true,//鏄惁杞
        loginicon_status=0,//杞鐘舵€佸€�
        start_time=60*5;

    $(".loginView .login-sao").on("click",function (e) {
        if(e.stopPropagation){
            e.stopPropagation();
        }

        ct_code.call(this);
    });
    function hide_code_box(){
        if($(".loginView .login-sao .sao-code").is(":visible")){
            is_round=false;
        }
        $(".loginView .login-sao .sao-code").hide();

    }
    //浜岀淮鐮佸脊绐�
    function ct_code(){
        if($(this).find(".sao-code").length==0){
            var ran=Date.now();
            var str='<div class="sao-code">'+
                        '<div class="sao-close">鍏抽棴</div>'+
                        '<div class="sao-box">'+
                            '<div class="sao-flex">'+
                                '<div class="img-tip"></div>'+
                                '<div class="code-area">'+
                                    '<p class="code-tip">璇蜂娇鐢ㄧ洿鎾惂瀹㈡埛绔�<br/>鍙戠幇-鎵竴鎵壂鎻忎簩缁寸爜</p>'+
                                    '<div class="code-img"><img class="c-img" src="//pl.zhibo8.cc/usercenter/oauth/getqrcode.php?ran='+ran+'"/></div>'+
//                                  '<div class="code-pass code-bg">'+
//                                      '<div class="pass-refresh">'+
//                                          '<p>浜岀淮鐮佸け鏁�</p>'+
//                                          '<a href="javascript:;" class="refresh-code" ng-click="refresh_code()">鍒锋柊</a>'+
//                                      '</div>
//                                  '</div>'+
                                '</div>'+
                            '</div>'+
                        '</div>'+
                    '</div>';
            $(this).append(str);
            $(this).find(".refresh-code").on("click",function(){
                refresh_code();
            });
            $(this).find(".sao-close").on("click",function (e) {
                hide_code_box();
                if(e.stopPropagation){
                    e.stopPropagation();
                }
            });
            $(".sao-code").find(".code-img").addClass("refresh-mk").find(".c-img").hide().on("load",function () {
                $(this).off("load").show().parent().removeClass("refresh-mk");
                correspond(0);
            }).attr("src",'//pl.zhibo8.cc/usercenter/oauth/getqrcode.php?ran='+ran);
        }else{
            if($(this).find(".sao-code").is(":visible")){
                $(this).find(".sao-code").hide();
                is_round=false;
            }else{
                $(this).find(".sao-code").show();
                if(!is_round){
                    is_round=true;
                    correspond(loginicon_status);
                }
            }
        }
    }
    //鍒锋柊浜岀淮鐮�
    function refresh_code(){

    }
    //瀹氭椂涓庢湇鍔＄閫氫俊锛屽垽鏂壂鐮佺姸鎬�
    function correspond(loginicon){
        (function tt(loginicon){
            $.getJSON('//pl.zhibo8.cc/usercenter/oauth/qrcode_login.php?&loginicon='+loginicon+'&callback=?',function (data) {
                console.log(data);
                if(data.status=='408'){
                    loginicon_status=loginicon;
                    if(is_round){
                        setTimeout(function(){
                            tt(loginicon);
                        },1000);
                    }
                }else if(data.status=='201'){
                    is_round=true;//鍙互缁х画杞
                    loginicon_status=1;//纭畾褰撳墠鐘舵€�
                    if(start_time<60*5){
                        start_time=60*5;
                    }else{
                        start_down_time();
                    }
                    tt(1);
                }else if(data.status=='200'){//鐧诲綍鎴愬姛
                    location.reload();
                }else{
                    loginicon_status=0;
                    $(".sao-code").find(".code-img").addClass("refresh-mk").find(".c-img").hide().on("load",function () {
                        $(this).off("load").show().parent().removeClass("refresh-mk");
                        tt(0);
                    }).attr("src","//pl.zhibo8.cc/usercenter/oauth/getqrcode.php?ran="+Math.random());
                }
            });
        })(loginicon);
    }

    function start_down_time(){
        (function f(){
            setTimeout(function(){
                if(--start_time>0){
                    f();
                }else{
                    is_round=false;
                }
            },1000);
        })();
    }

}
window.login_qq=function(url){
    try{
        document.domain='zhibo8.cc';    //绗笁鏂硅法鍩�
    }catch(error){
        console.error(error);
    }
    addCookie('cur_url',location.href,3600*24*30);
    window.open(url,"qqlogin", "width=800,height=400,menubar=0,scrollbars=1,resizable=1,status=1,titlebar=0,toolbar=0,location=1");
}
window.login_wechat=function(url){
    try{
        document.domain='zhibo8.cc';    //绗笁鏂硅法鍩�
    }catch(error){
        console.error(error);
    }
    addCookie('cur_url',location.href,3600*24*30);
    window.open(url,"wechatlogin", "width=800,height=400,menubar=0,scrollbars=1,resizable=1,status=1,titlebar=0,toolbar=0,location=1");
}
window.login_weibo=function(url){
    try{
        document.domain='zhibo8.cc';    //绗笁鏂硅法鍩�
    }catch(error){
        console.error(error);
    }
    addCookie('cur_url',location.href,3600*24*30);
    window.open(url,"weibologin", "width=800,height=400,menubar=0,scrollbars=1,resizable=1,status=1,titlebar=0,toolbar=0,location=1");
}

//鏄剧ず鏄惁缁戝畾鎵嬫満
function bindBox(title,_model) {
    _model=_model||model;
    _model.show_login_box(false);
   var $box=$('<div class="tel_bind ">'+
            '<style>.body-bg{position:fixed;top:0;left:0;right:0;bottom:0;z-index:1110;background:rgba(0,0,0,.3);}'+
            '.model_view .close-btn{position:absolute;right:5px;top:5px;width:30px;height:30px;background:url("//bbsimg.zhibo8.cc/bbsimg/2017-09-30/20170930124728_1483.png") no-repeat center center;cursor:pointer;border-left:2px solid #EEEEEE;}'+
            '.model_view .close-btn:hover{background:url("//bbsimg.zhibo8.cc/bbsimg/2017-09-30/20170930124728_5328.png") no-repeat center center;}'+
            '.v_h_center{position:fixed;top:50%;left:50%;-webkit-transform: translate(-50%,-50%);-moz-transform: translate(-50%,-50%);-o-transform: translate(-50%,-50%);transform: translate(-50%,-50%);}'+
            '.tel_bind .model_view{width:360px;height:230px;z-index:1111;background:#fff;color:#575757;text-align: center;}'+
            '.tel_bind h1{text-align: center;font-size: 24px;padding:30px 0;margin:0;font-weight: normal;}'+
            '.tel_bind .txt_tip{width:80%;margin:0 auto;line-height: 30px;font-size:16px;}'+
            '.tel_bind .jump_bind{display:block;width:90%;height:40px;line-height: 40px;font-size:16px;color:#fff;margin:20px auto 10px;background:#2E9FFF;text-align:center;}'+
            '</style>'+
        '<div class="body-bg"></div>'+
        '<div class="model_view v_h_center">'+
            '<a class="close-btn" href="javascript:;"></a>'+
            '<h1>缁戝畾鎵嬫満鍙�</h1>'+
            '<div class="txt_tip">'+title+'</div>'+
            '<a href="javascript:;" class="jump_bind">鍘荤粦瀹氭墜鏈�</a>'+
        '</div>'+
    '</div>').appendTo(document.body);
    $(document.body).find(".close-btn").on("click",function () {
        $(this).parents(".tel_bind").hide();
    });
    $(document.body).find(".jump_bind").on("click",function () {
        model.telCheck=true;
    });
}
var isFirst=0;
function regBox(prev_login){
    if(isFirst++==0){
        loadScript('//news.zhibo8.cc/js/reg_box.js', function(status){
            if(status){
                model.show_login_box(false);
                regbox(prev_login,function(){
                    model.show_login_box(true);
                });
            }
        });
    }else{
        if(typeof regbox =='function'){
            model.show_login_box(false);
            regbox(prev_login,function(){
                model.show_login_box(true);
            });
        }
    }
}

function loadScript(url, callback){
   var script = document.createElement("script")
   script.type = "text/javascript";
   if (script.readyState){ //IE
      script.onreadystatechange = function(){
         if (script.readyState == "loaded" ||
            script.readyState == "complete"){
            script.onreadystatechange = null;
            callback(true);
         }
      };
   } else { //Others: Firefox, Safari, Chrome, and Opera
      script.onload = function(){
          callback(true);
      };
   }
   script.src = url;
   document.body.appendChild(script);
}





//鐐瑰嚮鍏朵粬鍖哄煙琛ㄦ儏搴撻殣钘�
$(function () {
    $(document).on("click",function (event) {
        if($(event.target).parents(".xie").length==0 && $(event.target).parents(".pl-emoji").length==0){
            model.is_show_smiley=false;
        }
    });
});
//鑾峰彇鍏夋爣浣嶇疆
function getTxt1CursorPosition(obj){
    var oTxt1=$(obj).parents(".input_l_rel").find("textarea")[0];
//  var oTxt1 = document.getElementById("txt1");//textfb
    var cursorPosition=-1;
    if(oTxt1.selectionStart){//闈濱E娴忚鍣�
        cursorPosition= oTxt1.selectionStart;
    }else{//IE

        if(document.selection){
            var range = document.selection.createRange();
            range.moveStart("character",-oTxt1.value.length);
            cursorPosition=range.text.length;
        }
    }
    return cursorPosition;
}

////
if(model.isluxiang){
     model.loadmore_info = '鐐瑰嚮鏄剧ず璇勮';
}else{
    $.getJSON("//cache.zhibo8.cc/json/"+pl_path+"_hot.htm?key="+Math.random(), function(data){

        getEmoji(function(lists){
            for(var i=0;i<data.length;i++){
                data[i]['content']=formatEmoji(data[i]['content'],lists);
            }
            model.hotlist = data;
        });
//      model.hotlist = data;
    });

    if(model.floor_num > 0){
        loaddata(model.file, -2, model.floor_num);
    }else{
        loaddata(model.file, -1);
    }

    model.firstload = 0;
}
////
function loaddata(file, pageno, floor_num){
    if(pageno==-1 || pageno == -2){
        $.getJSON("//cache.zhibo8.cc/json/"+pl_path+"_count.htm?key="+Math.random(), function(data){
            model.all_num_info = data.all_num;
            model.root_num = data.root_num;
            model.root_normal_num = data.root_normal_num;
            model.pageno = Math.ceil(data.root_num/100)-1;

            model.pagemax = model.pageno;
            model.floor_count = model.root_normal_num;

            if(pageno == -2){
                var pn = Math.ceil(floor_num / 100) - 1;
                model.pageprev = pn;

                if(pn < model.pageno){
                    model.pageno = pn;

                    model.floor_count = (model.pageno + 1) * 100;
                }
            }

            getdata(file,model.pageno);
        });
    }else{
        getdata(file,pageno);
    }
}

function getdata(file, pageno){
    $.getJSON("//cache.zhibo8.cc/json/"+pl_path+"_"+pageno+".htm?key="+Math.random(), function(data,status){
        if(data){
            //alert("ok");
//          console.log(data)
        }else{
            //alert("error");
            return;
        }
//      model.list=[];
//      for(var i=0;i<data.length;i++){
//          if(data[i].room!=model.is_sel_room){
////            model.list.pushArray([data[i]]);
//              data.splice(i,1);
//          }
//      }



        model.pageno = pageno;
        getEmoji(function(lists){

            var new_data=contentFormat(data,lists);
            model.list.pushArray(new_data);
            if(model.list.size()<30 && model.pageno>0){
                getdata(file,(model.pageno-1));
            }
        });
//      model.list.pushArray(data);
        if(model.list.size()>0){
            vm.info = '宸叉湁'+model.all_num_info+'鏉¤瘎璁�';
        }else{
            vm.info = '鏆傛棤璇勮锛岃刀蹇彂琛ㄨ瘎璁哄惂锛�';
        }

        if(model.pageno==0){
            model.loadmore_info = '璇勮宸插叏閮ㄥ姞杞藉畬鎴�';
        }else{
            model.loadmore_info = '鐐瑰嚮鍔犺浇鏇村璇勮';
        }

        vm.info = '<a style="font-size:12px; line-height:26px;" href="#pl_box">'+vm.info+'</a>';


    });
}


model.images =
{"default":[{"no":"#face01","img_src":"default\/1.gif"},{"no":"#face02","img_src":"default\/2.gif"},{"no":"#face03","img_src":"default\/3.gif"},{"no":"#face04","img_src":"default\/4.gif"},{"no":"#face05","img_src":"default\/5.gif"},{"no":"#face06","img_src":"default\/6.gif"},{"no":"#face07","img_src":"default\/7.gif"},{"no":"#face08","img_src":"default\/8.gif"},{"no":"#face09","img_src":"default\/9.gif"},{"no":"#face10","img_src":"default\/10.gif"},{"no":"#face11","img_src":"default\/11.gif"},{"no":"#face12","img_src":"default\/12.gif"},{"no":"#face13","img_src":"default\/13.gif"},{"no":"#face14","img_src":"default\/14.gif"},{"no":"#face15","img_src":"default\/15.gif"},{"no":"#face16","img_src":"default\/16.gif"},{"no":"#face17","img_src":"default\/17.gif"},{"no":"#face18","img_src":"default\/18.gif"},{"no":"#face19","img_src":"default\/19.gif"},{"no":"#face20","img_src":"default\/20.gif"},{"no":"#face21","img_src":"default\/21.gif"},{"no":"#face22","img_src":"default\/22.gif"},{"no":"#face23","img_src":"default\/23.gif"},{"no":"#face24","img_src":"default\/24.gif"},{"no":"#face25","img_src":"default\/25.gif"},{"no":"#face26","img_src":"default\/26.gif"},{"no":"#face27","img_src":"default\/27.gif"},{"no":"#face28","img_src":"default\/28.gif"},{"no":"#face29","img_src":"default\/29.gif"},{"no":"#face30","img_src":"default\/30.gif"},{"no":"#face31","img_src":"default\/31.gif"},{"no":"#face32","img_src":"default\/32.gif"},{"no":"#face33","img_src":"default\/33.gif"},{"no":"#face34","img_src":"default\/34.gif"},{"no":"#face35","img_src":"default\/35.gif"},{"no":"#face36","img_src":"default\/36.gif"},{"no":"#face37","img_src":"default\/37.gif"},{"no":"#face38","img_src":"default\/38.gif"},{"no":"#face39","img_src":"default\/39.gif"},{"no":"#face40","img_src":"default\/40.gif"},{"no":"#face41","img_src":"default\/41.gif"},{"no":"#face42","img_src":"default\/42.gif"},{"no":"#face43","img_src":"default\/43.gif"}],"coolmonkey":[{"no":"#face44","img_src":"coolmonkey\/01.gif"},{"no":"#face45","img_src":"coolmonkey\/02.gif"},{"no":"#face46","img_src":"coolmonkey\/03.gif"},{"no":"#face47","img_src":"coolmonkey\/04.gif"},{"no":"#face48","img_src":"coolmonkey\/05.gif"},{"no":"#face49","img_src":"coolmonkey\/06.gif"},{"no":"#face50","img_src":"coolmonkey\/07.gif"},{"no":"#face51","img_src":"coolmonkey\/08.gif"},{"no":"#face52","img_src":"coolmonkey\/09.gif"},{"no":"#face53","img_src":"coolmonkey\/10.gif"},{"no":"#face54","img_src":"coolmonkey\/11.gif"},{"no":"#face55","img_src":"coolmonkey\/12.gif"},{"no":"#face56","img_src":"coolmonkey\/13.gif"},{"no":"#face57","img_src":"coolmonkey\/14.gif"},{"no":"#face58","img_src":"coolmonkey\/15.gif"},{"no":"#face59","img_src":"coolmonkey\/16.gif"}],"grapeman":[{"no":"#face60","img_src":"grapeman\/01.gif"},{"no":"#face61","img_src":"grapeman\/02.gif"},{"no":"#face62","img_src":"grapeman\/03.gif"},{"no":"#face63","img_src":"grapeman\/04.gif"},{"no":"#face64","img_src":"grapeman\/05.gif"},{"no":"#face65","img_src":"grapeman\/06.gif"},{"no":"#face66","img_src":"grapeman\/07.gif"},{"no":"#face67","img_src":"grapeman\/08.gif"},{"no":"#face68","img_src":"grapeman\/09.gif"},{"no":"#face69","img_src":"grapeman\/10.gif"},{"no":"#face70","img_src":"grapeman\/11.gif"},{"no":"#face71","img_src":"grapeman\/12.gif"},{"no":"#face72","img_src":"grapeman\/13.gif"},{"no":"#face73","img_src":"grapeman\/14.gif"},{"no":"#face74","img_src":"grapeman\/15.gif"},{"no":"#face75","img_src":"grapeman\/16.gif"},{"no":"#face76","img_src":"grapeman\/17.gif"},{"no":"#face77","img_src":"grapeman\/18.gif"},{"no":"#face78","img_src":"grapeman\/19.gif"},{"no":"#face79","img_src":"grapeman\/20.gif"},{"no":"#face80","img_src":"grapeman\/21.gif"},{"no":"#face81","img_src":"grapeman\/22.gif"},{"no":"#face82","img_src":"grapeman\/23.gif"},{"no":"#face83","img_src":"grapeman\/24.gif"}]};


var ispost = false;
function checkpost(){
    if($("#post_content").val().length>500){
        alert("璇勮闀垮害涓嶈兘瓒呰繃500锛�");
        return false;
    } else if($("#post_content").val().Trim().length == 0){
        alert("璇勮涓嶈兘涓虹┖鍝︼紒");
        return false;
    }else if(model.is_sel_room===''){
        alert("璇峰厛閫夋嫨鎴块棿鍚庯紝鍦ㄥ彂琛ㄨ瘎璁�!");
        return false;
    }

    if(ispost){
        alert("璇勮鎻愪氦涓紝璇疯€愬績绛夊緟銆�");
        return false;
    }else{
        if(cookie("hassubmit")!="submited"){
            addCookie("hassubmit","submited",15);
            ispost = true;
            $("#submit_btn").text("姝ｅ湪鎻愪氦涓�...");
            $("#post_content").css('background-color','#dedede');
            return true;
        }else{
            alert("鎮ㄨ瘎璁虹殑澶绻佷簡锛佷紤鎭竴涓嬪啀鏉ワ紒");
            return false;
        }
    }
}



//鑾峰緱coolie 鐨勫€�
function cookie(name){
    var cookieArray=document.cookie.split("; "); //寰楀埌鍒嗗壊鐨刢ookie鍚嶅€煎
    var cookie=new Object();
    for (var i=0;i<cookieArray.length;i++){
        var arr=cookieArray[i].split("=");       //灏嗗悕鍜屽€煎垎寮€
        if(arr[0]==name)return unescape(arr[1]); //濡傛灉鏄寚瀹氱殑cookie锛屽垯杩斿洖瀹冪殑鍊�
    }
    return "";
}

function addCookie(objName,objValue,objSeconds){      //娣诲姞cookie
    var str = objName + "=" + escape(objValue);
    var domain = '.zhibo8.cc';
    if(objSeconds > 0){                               //涓�0鏃朵笉璁惧畾杩囨湡鏃堕棿锛屾祻瑙堝櫒鍏抽棴鏃禼ookie鑷姩娑堝け
        var date = new Date();
        var ms = objSeconds*1000;
        date.setTime(date.getTime() + ms);
        str += "; expires=" + date.toGMTString()+";domain=" + domain + ";path=/";
    }
    document.cookie = str;
}

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
/****** 鏃х増涓炬姤鍔熻兘 *******/
var forminfo = '<div id="forminfo" style="width:350px; height: 50px; background: #fff; border: solid 2px #CAD9EA; position: absolute; z-index: 999999; display: none; border-radius: 4px; -webkit-border-radius: 4px; -webkit-box-shadow: 0 0 5px #aaa; box-shadow: 0 0 5px #000; line-height: 50px; font-size: 16px; text-align: center; font-family: \'寰蒋闆呴粦\', \'Microsoft YaHei\', \'榛戜綋\', Arial;">鐞嗙敱锛�<input type="text" id="liyou" value=""/> <input id="report_submit_btn" type="button" value="鎻愪氦" /> <input id="report_close_btn" type="button" value="鍏抽棴" /><input id="report_commentid" type="hidden" value="0" /></div>';
$(function(){
    $("body").append(forminfo);

    if(sureroom().length!=0){
        var rooms=sureroom();
        //寮瑰箷淇℃伅鍏堥€夋嫨鎴块棿
        if(!cookie("room_id")){
            var danmuinfo='<div ms-controller="pl_app">'+
                    '<canvas ms-if="is_sel_room!=\'\'"id="danmu2"></canvas>'+
                    '<div ms-if="is_sel_room==\'\'" class="box_room">';
            for(i in rooms){
                danmuinfo+='<div class="room'+rooms[i].id+' dm_room'+rooms[i].id+'"><a href="javascript:void(0);" ms-click="sel_room('+rooms[i].id+')">'+rooms[i].name+'</a></div>';
            }
            danmuinfo+='</div>'+
                    '</div>';
            $("#dmbox").html(danmuinfo);
        }
    }
    //鐑棬璇勮鍒囨崲
    $(document).on("click",".hot_huifuT",function(){
        //$("#hotpp").toggle();

        $span=$(".hot_huifuT span");

        if($span.hasClass("down")){
            $span.removeClass("down");
            $span.addClass("up");
            $span.text('鐐瑰嚮鏀惰捣')
            model.showmore = true;
        }else{
            $span.removeClass("up");
            $span.addClass("down");
            $span.text('鐐瑰嚮灞曞紑');
            model.showmore = false;
        }
    });

    $(document).on("click",".report_btn",function(e){
    //$(".report_btn").on("click",function(e){
        var cid = $(this).attr("cid");
        $("#forminfo").show();
        $("#forminfo").css("left",(e.pageX-360)+"px");
        $("#forminfo").css("top",(e.pageY-60)+"px");
        $("#liyou").val("");
        $("#report_commentid").val(cid);
    });


    $("#report_submit_btn").on('click', function(event) {
        if($("#liyou").val().Trim()==""){
            alert("涓炬姤鐨勭悊鐢变笉鑳戒负绌�.");
            return false;
        }
        if(cookie("report_"+$("#report_commentid").val())!="reported"){
            addCookie("report_"+$("#report_commentid").val(),"reported",3600);
        }else{
            alert("鎮ㄤ妇鎶ョ殑澶绻佷簡锛佷紤鎭竴涓嬪啀鏉ワ紒");
            $("#forminfo").hide();
            return false;
        }
        var url = "//pl.zhibo8.cc/code/report_new.php?act=jsonp&cid="+$("#report_commentid").val()+"&content="+encodeURIComponent($("#liyou").val().Trim())+"&callback=?";
        jQuery.getJSON(url, function(data){
            if(data.report > 0){
                alert("涓炬姤宸叉垚鍔熸彁浜ゃ€�");
                $("#forminfo").hide();
            }
        });
    });

    $("#report_close_btn").on('click', function(event) {
        $("#forminfo").hide();
    });

});
//content鏈夋枃瀛楄〃鎯呮浛鎹负琛ㄦ儏鍥剧墖
function contentFormat(data,lists){
    for(var i=0;i<data.length;i++){
        data[i]['content']=formatEmoji(data[i]['content'],lists);
        if(data[i]['children'].length>0){
            (function f(data){
                for(var i=0;i<data.length;i++){
                    data[i]['content']=formatEmoji(data[i]['content'],lists);
                    if(data[i]['children'].length>0){
                        f(data[i]['children'])
                    }
                }
            })(data[i]['children']);
        }
    }
    return data;
}
//鍔犺浇琛ㄦ儏鍥剧墖闆嗗悎
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
                    lists=lists.concat(data);
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
//          var aryMethods = ['push', 'pop', 'shift', 'unshift', 'splice', 'sort', 'reverse','concat'];
//          var arrayAugmentations = [];
//          lists.__proto__ = arrayAugmentations;
//          aryMethods.forEach(function (method) {
//            var original = Array.prototype[method];
//
//            arrayAugmentations[method] = function () {
//              console.log('瑙﹀彂')
//              setTimeout(function(){cb(lists);},0);
//
//              return original.apply(this, arguments);
//            };
//          });

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

//琛ㄦ儏鏂囧瓧鏇挎崲涓哄浘鐗�
function formatEmoji(content,lists,size){
//  var reg=/\[(.+?)\]/g;
    var reg=/\[([^\[\]]+?)\]/g;
    var arr=[];
    while(arr=reg.exec(content)){
        for(var i=0;i<lists.length;i++){
            var list=lists[i]['list'],flag=false;
            for(var j=0;j<list.length;j++){
                if(arr[0]==list[j]['name_cn']){
                    //if(size) list[j]['src']=list[j]['src'].replace("@3x","@"+size+"x");
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

/*****************/
//images寤惰繜鍔犺浇

$(function(){
    var $winH = $(window).height();
    var $img = $("img.lazy");
    var $imgH = parseInt($img.height()/2);
    var $srcDef = "//tu.zhibo8.cc/uploads/qqimages/loading.gif";
    runing();
    $(window).scroll(function(){
        runing();
    })
    function runing(){
        var $img = $("img.lazy");
        $img.each(function(i){
            var $src = $img.eq(i).attr("data-src");
            var $scroTop = $img.eq(i).offset();
            if($scroTop.top + $imgH >= $(window).scrollTop() && $(window).scrollTop() + $winH >= $scroTop.top + $imgH)
            {
                if($img.eq(i).attr("src") == $srcDef){
                    //$img.eq(i).hide();
                    $img.eq(i).attr("src",function(){return $src});
                }
                //$img.eq(i).attr("src",function(){return $src}).fadeIn(3000);
                //$img.eq(i).attr("src",function(){return $src}).show();
            }
        })
    }
})

//鏃堕棿
function idate(){
    var today=new Date();
    var y=today.getFullYear();
    var m=checkTime(today.getMonth()+1);
    var d=checkTime(today.getDate());
    var h=today.getHours();
    var n=checkTime(today.getMinutes());
    var s=checkTime(today.getSeconds());

    return  y + '-' + m + '-' + d + ' ' + h + ':' + n + ':' +s;
}

function checkTime(i){
    if (i<10){i="0" + i;}

    return i;
}

/********缁戝畾鎵嬫満start***********/
var sec = 59;

$(function () {
    var timer,waitsec=59;

    //鑾峰彇楠岃瘉鐮�
    $(document).on("click","#getvcode",function(e){
        if(phoneChk() && sec === waitsec){
            //鍙戦€佽姹傞獙璇佺爜

            waitsec++; //闃叉閲嶅鐐瑰嚮

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
                        sendData(data); //鑾峰彇楠岃瘉鐮�
                    }else{
                        $(".msg").text('绯荤粺閿欒锛�').css("display",'block');
                    }
                },
                error:function(){

                }
            });

            timer = setInterval(function(){
                var $this = $("#getvcode");
                $this.text(sec + ' 绉�');

                if(sec <= 0){
                    clearInterval(timer);

                    waitsec--;
                    sec = waitsec;

                    $this.text('鑾峰彇楠岃瘉鐮�');
                    $this.css('background-color','#FA8B15');

                    $("#phone").attr("readonly",false);
                }else{
                    sec--;
                }
            },1000);
        }else{
            if(sec < waitsec){
                $(".msg").text('璇风◢鍚庡啀鑾峰彇锛�').css("display",'block');
            }
        }
    });

    //缁戝畾鎵嬫満
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
                        sendBind(data); //鑾峰彇楠岃瘉鐮�
                    }else{
                        $(".msg").text('绯荤粺閿欒锛�').css("display",'block');
                    }
                },
                error:function(){

                }
            });
        }else{
            $(".msg").text('璇锋纭緭鍏ラ獙璇佺爜锛�').css("display",'block');
        }
    });


    $(document).on("click",".show_bind_box",function(e){
        $(".full_screen").show();
    });

    $(document).on("click",".modal_box .cancle_btn",function(e){
        $(".full_screen").hide();
    });

});

//鏄惁鎵嬫満鍙�
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

//鍙戦€侀獙璇佺爜璇锋眰
function sendData(data){
    $.ajax({
        url:'//pl.zhibo8.cc/usercenter/bind_phone/request.php?isvoice=1&callback=?',
        timeout:3000,
        dataType:'jsonp',
        data:data,
        success:function(data){
            $(".msg").text(data.info).css("display",'block');

            if(data.info.indexOf('楠岃瘉鐮佸凡鍙戦€�') >= 0){
                $("#bind").css('background-color','#FA8B15');
            }else{
                sec = 3;
            }
        },
        error:function(){

        }
    });
}

//鍙戦€佺粦瀹氳姹�
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
                $(".msg").text($(".msg").text()+'\n'+'椤甸潰灏嗚嚜鍔ㄥ埛鏂�.');
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
/********缁戝畾鎵嬫満end*************/

//URL
function J_get(name, url){
    url  = url?url:self.window.document.location.href;
    var start   = url.indexOf(name + '=');
    if (start == -1) return '';
    var len = start + name.length + 1;
    var end = url.indexOf('&',len);
    if (end == -1) end = url.length;
    return url.substring(len,end);
}