
$(function(){
	$(window).on("scroll",function(){
		if($(document).height()-$(this).height()-$(this).scrollTop()<100){
    		if(curr_num==6&&mall.next_id!=''&&mall.isload){
    			mall.isload=false;
    			mall.loadData(mall.next_id);
    		}
    	}
	});
});
//商城
function loadMall($obj){
	this.saishi_id=saishi_id||$("#saishi").html();//赛事id
	this.home=host||$("#host").html();//主队名
	this.visit=visit=guest||$("#guest").html();//客队名
	this.next_id='';//
	this.obj=$obj;//添加到的节点
	this.url_daogou='//daogou.zhibo8.cc/api/match/saishi';
	this.isload=true;
	
	this.loadData=function(next_id){//数据加载
		var _this=this;
		$.ajax({
			type:"post",
			url:this.url_daogou+'/'+next_id,
			data:{saishi_id:this.saishi_id,home:this.home,visit:this.visit},
			async:true,
			success:function(data){
//				console.log(data);
				var data=data.data;
				if(!$.isEmptyObject(data)){
					//专题
					var topic=data.topic,
						more=data.more;
					if(topic&&_this.next_id==''){
						_this.createTopic(topic,more);
					}
					
					//商品
					var products=data.products;
					if(products){
						_this.createProducts(products);
					}
					
					if(data.next_id!=''){
						_this.next_id=data.next_id;
						_this.isload=true;
					}else{
						_this.next_id=data.next_id;
						$(_this.obj).append('<div style="text-align:center;color:#c5c5c5;font-size:14px;">已加载完毕</div>');
					}
				}
			},
		});
	};
	this.createTopic=function(topic,more){//专题
		var str_topic='';
		str_topic+='<div class="topic">';
		if(topic.list.length>0&&more){
			str_topic+='<div class="t-more"><a href="'+more['url']+'"><img src="'+more['image']+'"/></a></div>'
		}
		for(var i=0;i<topic.list.length;i++){
			var item=topic.list[i];
			str_topic+='<div class="t-list"><a href="//daogou.zhibo8.cc/subject/item/'+item['id']+'"><img src="'+item['image']+'"/></a></div>';
		}
		str_topic+='</div>';
		
		this.obj.html(str_topic);
	};
	this.createProducts=function(products){//商品
		var str_produ='';
		str_produ+='<ul class="sje">';
		for(var i=0;i<products.list.length;i++){
			var item=products.list[i];
			str_produ+='<li class="list"><a href="'+(item['type']=='tuan'?item['ab_url']:item['ab_url'])+'">'+
				   	'<img class="fl sje-img" src="'+item['pict_url']+'" />'+
				   	'<div class="sje-w">'+
					'<h2 class="sje-h2">'+item['title']+'</h2>'+
					'<p class="sje-w-m"><span class="fl"><span class="m-price">'+item['m_price']+'</span></span> <span class="fr"><img class="source" src="'+item['mall_icon']+'"</span></p>'+//<span class="old-price">'+item['old_price']+'</span>
					'<div class="sje-w-b"><div class="fl"></div><span class="fr">'+item['sale_num']+'</span></div>'+//'+this.createCoupon(item['coupon_con'],item['type'])+'
				'</div>'+
			'</a></li>';
		}
		str_produ+='</ul>';
		this.obj.append(str_produ);
	};
	this.createCoupon=function(num,type){//优惠券
		if(type=='tuan'){
			var html='';
			html+='<div class="coupon">';
			html+=	'<div class="coupon-logo">券</div>';
			html+=	'<div class="coupon-word">'+num+'</div>';
			html+='</div>';
			return html;
		}
		return '';
	};
	this.init=function(){
		this.loadData('');
	};
}
//动图
function DynamicIcon(obj){
	this.game_date=game_date||$("#game_time").html();
	this.saishi_id=saishi_id||$("#saishi").html();
	this.obj=obj;
	this.loadData=function(game_date,saishi_id){
//		game_date='2016-10-18';saishi_id='86404';//test
		var that=this;
		var url='//m.zhibo8.cc/json/gallery/'+game_date+'/'+saishi_id+'.htm';
		$.ajax({
			url:url,
			type:"get",
			async:true,
			dataType:"json",
			success:function(data){
				if(data.status=='1'){
					var data=data.data;
					var list=data.list,str='';
					if((list instanceof Array) && list.length>0){
						str+='<ul class="d-content">';
						for(var i=0;i<list.length;i++){
							var item=list[i];
							str+=this.createLi(item);
						}
						str+='</div>';
						this.obj.html(str);
						this.bindClick(this.obj);
					}else{
						this.obj.html("暂无动图");
					}
				}
			}.bind(this),
			error:function(data){
				this.obj.html("暂无动图");
			}.bind(this)
		});
//		$.getJSON(url,function(data){
//			if(data.status=='1'){
//				var data=data.data;
//				var list=data.list,str='';
//				if(list.length>0){
//					str+='<ul class="d-content">';
//					for(var i=0;i<list.length;i++){
//						var item=list[i];
//						str+=this.createLi(item);
//					}
//					str+='</div>';
//					this.obj.html(str);
//					this.bindClick(this.obj);
//				}
//			}
//		}.bind(this));
	};
	this.createLi=function(item){
		var str='';
		str+='<li class="d-list">'+
    			'<div class="d-time"><span class="d-fl d-mk"></span><span class="d-fl">'+item['period_cn']+'</span></div>'+
    			'<div class="d-ent">'+
    				'<h2 class="d-tlt">'+item['title']+'</h2>'+
    				'<a href="javascript:;" class="d-icon" data-href="'+item['url']+'"><img src="'+item['thumbnail_url']+'"/></a>'+
    			'</div>'+
    		'</li>';
		return str;
	};
	this.bindClick=function($obj){
		if($obj.length==0) return false;
		str='',_this=this;
		str+='<style>.d-box{}';
		str+='		 .d-box .d-bg{position:fixed;top:0;right:0;left:0;bottom:-60px;background-color:rgba(0,0,0,1);z-index:1111;}';
		str+='		 .d-box .d-content{position:fixed;top:50%;left:10px;right:10px;transform:translateY(-50%);-webkit-transform:translateY(-50%);text-align:center;z-index:1112;}';
		str+='		 .d-box .d-content .d-img{max-width:100%;}';
		str+='</style>';
		$(document.body).append(str);
		$obj.find("a").on("click tap",function(){
			var url=$(this).attr("data-href");
			if(!document.querySelector(".d-box")){	
				_this.createBg(url);
			}
			document.getElementById("d-bg").onclick=function(){
				$("body .d-box").remove();
			};
			document.getElementById("d-bg").addEventListener("touchmove",function(event){
				return false;
			},false);
		});
	};
	this.createBg=function(url){
		str='';
		str+='<div class="d-box">';
		str+='	<div class="d-bg" id="d-bg"></div>';
		str+='	<div class="d-content">';
		str+='		<img class="d-img" src="'+url+'"/>';
		str+='	</div>';
		str+='</div>';
		$(document.body).append(str);	
	}
	this.init=function(){
		this.loadData(this.game_date,this.saishi_id);
	};
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


