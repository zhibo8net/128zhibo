$(function () {
    // $("#siderbar .advert:last").css('height', '1040px');

    //$("#siderbar .advert:last iframe:first").css('height','120px');
    $("#siderbar .advert:last iframe:eq(1)").css('margin-top', '0');

    $("#siderbar .advert:last iframe:first").remove();

    $(window).scroll(function () {

        var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;



        var offset = $("#siderbar .advert:first").offset();



        if (scrollTop > (offset.top + 250)) {
            $("#siderbar .advert:last").css({
                'position': 'fixed',
                'top': '0px',
                'left': offset.left + 'px',
                'margin-top': '5px',
                'z-index': '999'
            });
            //$("#siderbar .advert:last iframe:eq(1)").css({'position':'fixed','top':'130px','left':offset.left+'px','margin-top':'10px','z-index':'999'});
            //
            //$("#siderbar .advert:last iframe:first").css({'position':'fixed','top':'0px','left':offset.left+'px','margin-top':'10px','z-index':'999'});
            //
            //$("#siderbar .advert:last iframe:eq(2)").css({'position':'fixed','top':'390px','left':offset.left+'px','margin-top':'10px','z-index':'999'});



        } else {
            $("#siderbar .advert:last").css({
                'position': 'relative',
                'top': '',
                'left': '',
                'margin-top': '0',
                'z-index': '0'
            });
            //$("#siderbar .advert:last iframe:eq(1)").css({'position':'relative','top':'','left':'','margin-top':'10px','z-index':'0'});
            //
            //$("#siderbar .advert:last iframe:first").css({'position':'relative','top':'','left':'','margin-top':'0','z-index':'0'});
            //
            //$("#siderbar .advert:last iframe:eq(2)").css({'position':'relative','top':'','left':'','margin-top':'10px','z-index':'0'});

        }

    });
})