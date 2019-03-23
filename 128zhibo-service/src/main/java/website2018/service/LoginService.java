package website2018.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.misc.IdGenerator;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import website2018.domain.Account;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.AccountDao;

//Spring Bean的标识.
@Service
public class LoginService implements UserDetailsService {

    // 注入配置值
    @Value("${app.loginTimeoutSecs:6000}")
    private int loginTimeoutSecs;

    // guava cache
    private Cache<String, Account> loginUsers;

    @PostConstruct
    public void init() {
        loginUsers = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(loginTimeoutSecs, TimeUnit.SECONDS)
                .build();
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        return accountDao.findByMobile(mobile);
    }

    public String login(String mobile, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(mobile, password);
        try {
            authenticationManager.authenticate(authToken);
        }catch(Exception e) {
            throw new ServiceException("用户名或密码错误", ErrorCode.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authToken);
        
        Account account = accountDao.findByMobile(mobile);
        String token = IdGenerator.uuid2();
        if(account.mobile.equals("admin")) {
            token = "s" + token;
        }else {
            token = "a" + token;
        }
        loginUsers.put(token, account);
        
        return token;
    }

    public Account getLoginUser(String token) {

        Account account = loginUsers.getIfPresent(token);

        return account;
        
    }

}
