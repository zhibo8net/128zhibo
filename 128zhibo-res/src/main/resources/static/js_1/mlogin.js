var allObj = {};
var nowType = 1;
        $(function () {
            // 只需初始化一次
            // initWatchman({
            //     productNumber: 'YD00000601637071',
            //     onload: function (instance) {
            //         var wm = instance;
            //         wm & wm.getToken('3b4a04257ad54abeab71558df927bf7f', function (token) {
            //             addCookie('token', token, 3600 * 24 * 30);
            //         });
            //     }
            // });
            setTimeout(function () {
                var winH = $(window).height();
                winH = winH < 400 ? 400 : winH;
                $(".c-box").height(winH - $(".b-o-login").height());
                $(".b-o-login").css("position", "relative");
            }, 100);
            var myreg = /^13[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$/;
            var $btn_code = $(".btn-get-code"),
                $tel = $(".tel"),
                $btn_login = $(".btn-login"),
                $code = $(".code"),
                $register = $('.register');
            var check_status = false, //检查手机合法性
                btn_able = true, //是否处于可点击状态
                is_register = false;
            //检查输入的手机号
            $tel.on("input", function () {
                var tel = $(this).val().trim();
                if (!tel) return;
                if (tel) {
                    check_status = true;
                } else {
                    check_status = false;
                }
                if ($code.val() && check_status) {
                    $btn_login.addClass("able-login");
                } else {
                    $btn_login.removeClass("able-login");
                }

            });
            $code.on("input", function () {
                var code = $(this).val();
                if (code && check_status) {
                    $btn_login.addClass("able-login");
                } else {
                    $btn_login.removeClass("able-login");
                }
            });
            $register.on('click', function () {
                if (!is_register) {
                    $('.pageName').html('注册');
                    $register.html('登录');
                    nowType = 1;
                } else {
                    $('.pageName').html('登录');
                    $register.html('注册');
                    nowType = 0;
                }
                is_register = !is_register;
            })

            var $code_img = $("#code-img"),
                vcode = '';

            //点击登录
            $btn_login.on("click", function () {
                if (!$(this).hasClass("able-login")) return;
                var tel = $tel.val(),
                    password = $code.val();
                if (check(tel, password)) {
                    if (!nowType) {
                        login('/api/user/register')
                    } else {
                        login('/api/user/login')
                    }
                }
            });

            function check(tel, code) { //检查
                if (!tel) {
                    alertBox("请输入手机号");
                    return false;
                } else if (!code) {
                    alertBox("请输入密码");
                    return false;
                }else if (!(/^1[34578]\d{9}$/.test(tel))) {
                    alertBox("手机号码有误，请重填");
                    return false;
                } else {
                    //						$scope.info='';
                    return true;
                }
            }
            function login(url) {
                var phone = $tel.val(),
                    password = $code.val();
                var data = {
                    userName: phone,
                    password: hex_md5(password)
                }
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: JSON.stringify(data),
                    contentType: 'application/json;charset=utf-8',
                    dataType: "json",
                    success: function (resp) {
                        if (resp.code == '0000') {
                            if (!nowType) {
                                alert('注册成功')
                            } else {
                                alert('登录成功')
                            }
                            history.go(-1);
                        } else {
                            alert(resp.message)
                        }
                    },
                    fail: function (err) {
                        alert(err)
                    }
                })
            }
            //弹窗提示
            function alertBox(mesg) {
                var str = '<div class="alert-box" style="display:none;">' +
                    '<div class="a-btn alert-bg"></div>' +
                    '<div class="alert-ent">' +
                    '<h2 class="alert-mesg">' + mesg + '</h2>' +
                    '<a href="javascript:;" class="a-btn alert-btn">确定</a>' +
                    '</div>' +
                    '</div>';
                $(document.body).append(str)
                    .find(".alert-box").fadeIn(200)
                    .find(".a-btn").on("click", function () {
                        $(this).parents(".alert-box").fadeOut(200, function () {
                            $(this).remove();
                        });
                    });
            }

        });