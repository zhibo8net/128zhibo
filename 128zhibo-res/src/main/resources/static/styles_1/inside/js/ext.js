/**
 * Created by realvik on 11/26/14.
 */
//ext


$(function(){
    $.getScript('//m.zhibo8.cc/static/js/signal.js?key='+(new Date()).valueOf(), function () {
        console.log("loaded")
    });


    $.getScript('//m.zhibo8.cc/static/js/ext.js', function () {
        console.log("loaded ext")
    });

});