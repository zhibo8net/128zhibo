(function ($) {
    $.fn.flashSlider = function (options) {
        var options = $.extend({}, $.fn.flashSlider.defaults, options);
        this.each(function () {
            var obj = $(this);
            var curr = 1; //当前索引
            var $img = obj.find("a");
            var s = $img.length;
            var w = $img.eq(0).width();
            var h = $img.eq(0).height();
            var $flashelement = $("ul", obj);
            options.height == 0 ? obj.height(h) : obj.height(options.height);
            options.width == 0 ? obj.width(w) : obj.width(options.width);
            obj.css("overflow", "hidden");
            obj.css("position", "relative");
            $flashelement.css('width', s * w);
            if (!options.vertical) {
                $("li", obj).css('float', 'left')
            } else {
                $img.css('display', 'block')
            };
            if (options.controlsShow) {
                var navbtnhtml = '<div id="flashnvanum">';
                for (var i = 0; i < s; i++) {
                    navbtnhtml += '<span>' + (i + 1) + '</span>';
                }
                navbtnhtml += '</div>';
                obj.append(navbtnhtml);
                obj.find("#flashnvanum span").hover(function () {
                    var num = $(this).html();
                    flash(num, true);
                }, function () {
                    timeout = setTimeout(function () {
                        flash((curr / 1 + 1), false);
                    }, options.pause / 4);
                });
            };
            // obj.find("li").hover(function(){
            // 	flash($(this).index('li')+1,true);
            // },function(){
            // 	if($(this).index('li')+2>s){
            // 		flash(1,false);
            // 	}else{
            // 		flash($(this).index('li')+2,false);
            // 	}
            // });
            function setcurrnum(index) {
                obj.find("#flashnvanum span").eq(index).addClass('on').siblings().removeClass('on');
            }

            function flash(index, clicked) {
                $flashelement.stop();
                var next = index == s ? 1 : index + 1;
                curr = index - 1;
                setcurrnum((index - 1));
                if (!options.vertical) {
                    p = ((index - 1) * w * -1);
                    $flashelement.animate({
                            marginLeft: p
                        },
                        options.speed, options.easing
                    );
                } else {
                    p = ((index - 1) * h * -1);
                    $flashelement.animate({
                            marginTop: p
                        },
                        options.speed, options.easing
                    );
                };
                if (clicked) {
                    clearTimeout(timeout);
                };
                if (options.auto && !clicked) {
                    timeout = setTimeout(function () {
                        flash(next, false);
                    }, options.speed + options.pause);
                };
            }
            var timeout;
            //初始化
            setcurrnum(0);
            if (options.auto) {
                timeout = setTimeout(function () {
                    flash(2, false);
                }, options.pause);
            };
        });
    };
    //默认值
    $.fn.flashSlider.defaults = {
        controlsShow: true, //是否显示数字导航
        vertical: false, //纵向还是横向滑动
        speed: 800, //滑动速度
        auto: true, //是否自定滑动
        pause: 5000, //两次滑动暂停时间
        easing: "swing", //easing效果，默认swing，更多效果需要easing插件支持
        height: 0, //容器高度，不设置自动获取图片高度
        width: 0 //容器宽度，不设置自动获取图片宽度
    }
})(jQuery);