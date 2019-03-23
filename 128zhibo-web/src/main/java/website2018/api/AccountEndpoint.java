package website2018.api;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.service.LoginService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class AccountEndpoint {

    public static final String JSON_UTF_8 = "application/json; charset=UTF-8";

    private static Logger logger = LoggerFactory.getLogger(AccountEndpoint.class);

    @Autowired
    private LoginService accountService;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = JSON_UTF_8)
    public Map<String, String> login(@RequestParam("mobile") String mobile, @RequestParam("password") String password) {

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new ServiceException("用户名或密码为空", ErrorCode.BAD_REQUEST);
        }

        String token = accountService.login(mobile, password);

        return Collections.singletonMap("token", token);
    }

}
