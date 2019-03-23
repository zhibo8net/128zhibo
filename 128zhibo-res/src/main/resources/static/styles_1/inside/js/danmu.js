var danmu = {
	flag: true,  //弹幕是否开启
	timer_play: 0,
	line_num: 2,   //行数
	labs: [],        //数据仓库
	line_next: [],   //没有数据的行
	last_id: [],     //每行最后上线的对象
	box_width: 0,  //银幕宽
	box_height: 60, //银幕高
	queue: [],     //弹幕队列
	id: 0,         //id序列号
	speed: 1,     //每次移动像素
	loading: 0,     //数据加载状态
	status: 'close',
	colors: ['#d93a49', '#7fb80e', '#585eaa', '#007947', '#8e453f', '#c1a173', '#145b7d', '#525f42', '#401c44', '#f47920', '#1d953f', '#f173ac'],
	cans: null,
	text_margin: 20, //弹幕间隔 px
	timestamp: 0,
	sleep: false,
	timer_xload: 0,
	orderId: 0, //这个比接口的orderid大1
	ratio:1,//比率
	reward_id:7777777,//打赏标记

	//添加
	put: function (textArr, line,order) {
		var top = 16*this.ratio * line + 7*this.ratio * (line - 1);
		this.id++;
		var ob = {
			'id': this.id,
			'left': this.box_width,
			'top': top,
			'textArr':textArr,
//			'text': text,
			'line': line,
			'width': 0
		};
		if(order instanceof Array) ob.no=order[0];
		this.queue.push(ob);

		//当前行最后一个的id
		this.last_id[line] = this.id;
	},

	//逐个
	play: function () {
		var t = (new Date()).getTime();

		if ((t - this.timestamp) > 800) {
			this.sleep = true;
		} else {
			if (this.sleep) {
				this.sleep = false;
				this.getcount('Wake up');
			}
		}

		this.timestamp = t;
		//

		this.load();

		//var arr  = this.queue;
		var arr = this.queue.slice(0); ////copy

		//var over = 1;
		var unset_ids = [];

		this.cans.clearRect(0, 0, this.box_width, this.box_height);
		this.cans.save();

		for (var i in arr) {
			i = parseInt(i);
			
			//画文字或者表情
			var drawImgOrFont=function(l){
				var textArr= this.queue[i].textArr;
				var len=0;
				for(var j=0;j<textArr.length;j++){
					var item=textArr[j];
					if(item['img']){
						var img=new Image();
						img.src=item['img'];
						
						this.cans.drawImage(img,this.queue[i].left+l+len+2,this.queue[i].top-17*this.ratio,25*this.ratio,25*this.ratio);
					}else{
						this.cans.fillText(item['text'],this.queue[i].left+l+len,this.queue[i].top);
					}
					len+=item['img']?27*this.ratio:this.cans.measureText(item['text']).width-2;
				}
			}
			
			var textArr= this.queue[i].textArr;
			var text_cn=(function(textArr){
				var str='';
				textArr.forEach(function(item,i){
					str+=item['text'];
				});
				return str;
			})(textArr);
			
			//计算长度
			if (this.queue[i].width == 0) {
				this.queue[i].width = Math.round(this.cans.measureText(text_cn).width);
			}
			
			if ((this.queue[i].left + this.queue[i].width+35*this.ratio) < 0) {
				//delete(this.queue[i]); //删除已飘过

				unset_ids.unshift(i);
			} else {
				this.queue[i].left -= this.speed;
				
				var c='#000',l=0;
				if(this.queue[i].no==this.reward_id){//打赏移动					
					var s_h=this.cans.measureText("赏").width;
					this.cans.fillStyle="#F96565";
					this.cans.fillRect(this.queue[i].left, this.queue[i].top-16*this.ratio,s_h+4*this.ratio,this.text_margin*this.ratio);
					this.cans.fillStyle="#FAD254";
					this.cans.fillText('赏', this.queue[i].left+2*this.ratio, this.queue[i].top);
					this.cans.strokeStyle="#F96565";
					this.cans.strokeRect(this.queue[i].left, this.queue[i].top-16*this.ratio,this.queue[i].width+s_h+8*this.ratio,this.text_margin*this.ratio);
					c="#F96565",l=s_h+6*this.ratio;
				}
				
				this.cans.fillStyle=c;
				drawImgOrFont.call(this,l);
//				this.cans.fillText(this.queue[i].text, this.queue[i].left+l, this.queue[i].top);
				if (this.last_id[this.queue[i].line] == this.queue[i].id) {
					if ((this.queue[i].left + this.queue[i].width + this.text_margin+l) < this.box_width) {

						this.last_id[this.queue[i].line] = 0; //清除
						this.line_next.push(this.queue[i].line); //添加下一条
					}
				}

				//over = 0;
			}
			
			


		/*
			
			//计算长度
			if (this.queue[i].width == 0) {
				this.queue[i].width = Math.round(this.cans.measureText(this.queue[i].text).width);
			}
			
			if ((this.queue[i].left + this.queue[i].width+35*this.ratio) < 0) {
				//delete(this.queue[i]); //删除已飘过

				unset_ids.unshift(i);
			} else {
				this.queue[i].left -= this.speed;
				
				var c='#000',l=0;
				if(this.queue[i].no==this.reward_id){//打赏移动					
					var s_h=this.cans.measureText("赏").width;
					this.cans.fillStyle="#F96565";
					this.cans.fillRect(this.queue[i].left, this.queue[i].top-16*this.ratio,s_h+4*this.ratio,this.text_margin*this.ratio);
					this.cans.fillStyle="#FAD254";
					this.cans.fillText('赏', this.queue[i].left+2*this.ratio, this.queue[i].top);
					this.cans.strokeStyle="#F96565";
					this.cans.strokeRect(this.queue[i].left, this.queue[i].top-16*this.ratio,this.queue[i].width+s_h+8*this.ratio,this.text_margin*this.ratio);
					c="#F96565",l=s_h+6*this.ratio;
				}
				
				this.cans.fillStyle=c;
				this.cans.fillText(this.queue[i].text, this.queue[i].left+l, this.queue[i].top);
				if (this.last_id[this.queue[i].line] == this.queue[i].id) {
					if ((this.queue[i].left + this.queue[i].width + this.text_margin+l) < this.box_width) {

						this.last_id[this.queue[i].line] = 0; //清除
						this.line_next.push(this.queue[i].line); //添加下一条
					}
				}

				//over = 0;
			}
			*/
		}
		this.cans.restore();

		//重建列队
		if (unset_ids.length > 0) {
			for (var id in unset_ids) {
				this.queue.splice(unset_ids[id], 1);
			}
		}

		/*if(this.line_next.length == this.line_num && over === 1 && this.queue.length > 0){
		 this.queue = [];
		 }*/

		this.timer_play = requestAnimationFrame(function () {
			danmu.play();
		});
	},

	load: function () {
		if (this.line_next.length > 0 && this.labs.length > 0) {
			for (var i in this.line_next) {

				if (this.labs.length > 0) {
					var xline = this.line_next.shift();
					var order=this.labs[0];
					//添加下一条
					this.put(this.labs.shift()[1], xline,order);
				}

			}
		}
	},

	rnd: function (min, max) {
		return min + Math.floor(Math.random() * (max + 1 - min));
	},

	geturl: function (id, per_page, type) {
		if (parseInt(per_page) == 0) {
			per_page = 10;
		}


		var dan_server = J_get('dan_server');

		if (isNull(dan_server)) {
			dan_server = 'dan';
		}


		var base = '//' + dan_server + '.zhibo8.cc/data/' + filename.replace(/-/g, '/');

		if (type == 'count') {
			base += '_count.htm';
		} else {
			base += '_' + Math.floor((id - 1) / per_page) + '.htm'; //id为+1后的orderid
		}

		base += '?rand=' + Math.random();

		return base;
	},

	setspeed: function () {
		var len = this.labs.length - 5;

		var g = Math.floor(len / 5);
		
		if (g < 1) {
			g = 1;
		}
		if (g > 3) {
			g = 3;
		}

		if (g < (this.speed-this.radio+1)) {
			this.speed--;
		} else if (g > this.speed) {
			this.speed++;
		}
	},

	xload: function (t) {
		if (this.sleep) {
			return;
		}

		this.timer_xload = setTimeout(function () {
			danmu.getcount();
		}, t);
	},

	getcount: function (x) {
		clearTimeout(this.timer_xload);

		if (!this.flag || this.sleep) {
			return;
		}

		if (typeof x !== 'string' && this.labs.length > 60) {
			this.xload(10000);
			return;
		}

		var ajax_url = this.geturl(0, 0, 'count');

		$.ajax({
			url: ajax_url,
			timeout: 5000,
			dataType: 'json',
			success: function (data) {
				var num = parseInt(data.num), per_page = parseInt(data.per_page);
				var re_start = false;

				if (danmu.labs.length > 1) {
					if (num > (danmu.labs[0][0] + 10)) {
						re_start = true;
					}
				}

				//切回激活页
				if (typeof x === 'string' && re_start) {
					danmu.orderId = 0;

					danmu.labs = [];
				}

				danmu.getdata(num, per_page);
				danmu.setspeed();

				danmu.xload(1000);
			},
			error: function () {
				danmu.xload(5000);
			}
		});
	},

	getdata: function (num, per_page) {
		if (num <= this.orderId || this.loading === 1) {
			return;
		}

		this.loading = 1;

		var url = '',
			start_id = 0,
			show_num = 5;  //显示最后几条

		if (this.orderId === 0) {
			start_id = num - show_num + 1;

			if (start_id < 1) {
				start_id = 1;
			}
		} else {
			start_id = this.orderId + 1;
		}

		url = this.geturl(start_id, per_page);

		$.ajax({
			url: url,
			timeout: 5000,
			dataType: 'json',
			success: function (data) {
				
				getEmoji(function (lists) {
					
					var reg=/\[([^\[\]]+)\]/g;
					
					var toTextArr=function(textarr,size){
						var result=[];
						for(var i=0;i<textarr.length;i++){
							if(i%2==1){
								var textimg='['+textarr[i]+']',src='';
								for(var x=0;x<lists.length;x++){
									var list=lists[x]['list'],flag=false;
									for(var y=0;y<list.length;y++){
										if(textimg==list[y]['name_cn']){
											if(size) list[y]['src']=list[y]['src'].replace("@3x","@"+size+"x");
											src=list[y]['src'];
											flag=true;
											break;
										}
									}
									if(flag) break;
								}
								
								result.push({
									text:textimg,
									img:src
								});
							}else if(textarr[i]){
								result.push({
									text:textarr[i]
								});
							}						
						}
						return result;
					}
				
				var oid = 0;

				for (var i in data) {
					var contentArr=toTextArr(data[i]['content'].split(reg),2);
					oid = parseInt(data[i]['order_id']) + 1;
					var rooms=[];
					rooms=sureroom();
					var flag =rooms.length == 0 ? true : (data[i]['room'] == cookie("room_id"));
					
					if (oid > danmu.orderId) {
						danmu.orderId = oid;

						if (flag) {
							danmu.labs.push([danmu.orderId,contentArr]);
//							danmu.labs.push([danmu.orderId, data[i]['content']]);
						}
					}
				}
				});

			},
			complete: function () {
				danmu.loading = 0;
			}
		});
	},

	init: function () {
		var playid = 'danmu2';

		this.box_width = document.body.clientWidth;

		for (var i = 1; i <= this.line_num; i++) {
			this.line_next.push(i);
		}

		var $canvas = document.getElementById(playid);

		

		this.cans = $canvas.getContext('2d');
		this.cans.textBaseline = 'top';

		
		var getPixelRatio = function(context) {  
		    var backingStore = context.backingStorePixelRatio ||  
		        context.webkitBackingStorePixelRatio ||  
		        context.mozBackingStorePixelRatio ||  
		        context.msBackingStorePixelRatio ||  
		        context.oBackingStorePixelRatio ||  
		        context.backingStorePixelRatio || 1;  
		  
		    return (window.devicePixelRatio || 1) / backingStore;  
		};
		var ratio =getPixelRatio(this.cans);
		this.ratio=ratio;
		this.speed=ratio;
		var box_width=this.box_width;
		var box_height=this.box_height;
		this.box_width=this.box_width*ratio;
		this.box_height=this.box_height*ratio;
		
		$canvas.width=this.box_width;
		$canvas.height=this.box_height;
		$canvas.style.width =box_width+"px";
		$canvas.style.height =box_height+"px";
		$canvas.style.marginTop="14px";
        this.cans.font =(16*ratio)+"px arial";
	},

	start: function (x) {
		if (this.status == 'open') {
			return;
		} else {
			this.status = 'open';
		}

		if ((typeof x) == 'number') {
			this.flag = true;
		}

		if (this.flag === false) {
			return;
		}

		$("#danmu2").show();

		if (this.box_width === 0) {
			this.init();
		}

		this.getcount(x);

		this.timestamp = (new Date()).getTime();
		this.play();

		dmsend.gray = false;
	},

	pause: function () {
		cancelAnimationFrame(this.timer_play);
		this.flag = false;
		dmsend.gray = true;
		this.status = 'close';
	}
};

var dmsend = avalon.define("dmsend", function (vm) {
	vm.note = '';
	vm.gray = !danmu.flag;

	//切换
	vm.danmu_switch = function () {
		danmu.flag = !danmu.flag;

		$(".dmspan").toggleClass('dmspan_gray');
		$(".dmbtn").toggleClass('dmbtn_gray');

		if (danmu.flag === false) {
			$("#danmu2").hide();
			danmu.pause();
		} else {
			danmu.start(1);
		}
	};

	if (danmu.flag === false) {
		vm.danmu_switch = function () {

		};
	}

	//发送
	vm.danmu_send = function () {
		if (danmu.flag === false) {
			return;
		}

		if (!bbs_username) {
			dmsend.note = '请先登录，然后再发表弹幕！';
			return;
		}

		if (model.is_sel_room === '') {
			dmsend.note = '请先选择房间，然后再发表弹幕！';
			return;
		}

		$text = $(".dmtext").val();
		$text = $text.substr(0, 255);

		if ($text == '') {
			dmsend.note = '请输入弹幕内容！';
			return;
		}

		if (danmu.flag === true && $text != '') {
			model.content = $text;
			model.postpinglun();

			$(".dmtext").val("");
			dmsend.note = '';
		}
	};

	vm.enterkey = function (e) {
		if (e.keyCode == 13) {
			dmsend.danmu_send();
		}
	};
});

$(function () {
	var room_id=cookie("room_id");
	if(room_id){
		danmu.start();
	}
});