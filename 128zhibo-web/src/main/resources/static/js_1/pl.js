$(function () {
    var userChooseType = '1';

    $("#editortip").click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        $('.textfb,.post_content,#post_content').focus();
        $(this).remove();
    });

    checkLogin();
    getAllList();

    function checkLogin() {
        $.ajax({
            url: '/checkUser',
            type: 'POST',
            // data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
            dataType: "json",
            success: function (resp) {
                if (resp.code == '0000') {
                    $('.loginBox').fadeOut(); // 隐藏登录弹窗
                    $("#nouserinfo").hide(); // 隐藏登录注册框
                    $('.yhm,#logindiv').hide(); // 图文直播页用户信息
                    $('#userinfo').show();
                    $('#userinfo .ng-binding').html(resp.data.userName);
                }
            },
            fail: function (err) {
                // alert(err)
            }
        });
    }

    function getAllList() {
        var data = {
            type: '1',
            userType: userChooseType,
            relId: $('.getId').html() || ''
        };
        if (!data.relId) {
            alert('系统异常，请稍后重试')
            return false
        }
        $.ajax({
            url: '/api/user/getCommentAllList',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
            dataType: "json",
            success: function (response) {
                var str = '';
                if (response.length == 0) {
                    $('#pllist').html('<p class="no_data_3" style="text-align: center;">暂无评论</p>');
                    $('.nowplnum,.loadMore').hide();
                    return
                }
                $('.num').html(response.length);
                response.map(function (arr, i) {
                    if (arr) {
                        str +=
                            '<table ng-repeat="p in loadlist|filter:filterRoom" cellspacing="0" cellpadding="0" border="0" style="width: 100%;" class="ng-scope"><tbody><tr><td class="commentTextList" valign="top"><div class="comment"><div class="info"><div><a href="javascript:;" target="_blank" class="user_name ng-binding">' +
                            arr.userName +
                            '</a><div class="right ng-binding"><span style="cursor:pointer; color:#4E84AE;" class="up_btn" cid="135201451" uid="0">顶(<font class="up_cnt ng-binding">0</font>)</span>&nbsp;&nbsp;<span style="cursor:pointer; color:#4E84AE;" class="down_btn" cid="135201451" uid="0">踩(<font class="down_cnt ng-binding">0</font>)</span>&nbsp;&nbsp;<a class="reply" cid="135201451" href="javascript:void(0);" ng-click="reply(p.id, p.username)">回复</a>&nbsp;&nbsp;<span style="cursor:pointer; color:#4E84AE;" class="report_btn" cid="135201451" uid="0">举报</span> ' +
                            (response.length - i) +
                            '楼</div></div></div><p class="word ng-binding" ng-bind-html="deliberatelyTrustDangerousSnippet(p.content)" div-scroll="realvik()" isloaded="true">' +
                            arr.comment +
                            '</p><a class="dev_info ng-binding" href="javascript:;" target="_blank">' +
                            arr.addTimeStr +
                            '</a></div></td></tr></tbody></table>'
                    }
                });
                $('#pllist').html(str);
                $('.nowplnum,.loadMore').show();
            },
            fail: function (err) {
                $('#pllist').html('<p class="no_data_3">暂无评论</p>');
                $('.nowplnum,.loadMore').hide();
            }
        });
    };

    $('.reloadData').click(function () {
        getAllList();
    })

    $('#submit_btn').click(function () {
        var data = {
            type: '1',
            userType: userChooseType,
            relId: $('.getId').html() || '',
            comment: $('#post_content').val() || ''
        };
        if (!data.relId) {
            alert('系统异常，请稍后重试')
            return false
        } else if (!data.comment) {
            alert('请输入评论信息')
            return false
        }
        $.ajax({
            url: '/api/user/addComment',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
            dataType: "json",
            success: function (resp) {
                if (resp.code == '0000') {
                    console.log(resp)
                    $('#post_content').val('');
                    getAllList();
                }
            },
            fail: function (err) {
                // alert(err)
            }
        });
    });
})