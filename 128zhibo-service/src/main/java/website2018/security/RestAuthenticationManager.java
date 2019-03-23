package website2018.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.text.EncodeUtil;
import org.springside.modules.utils.text.HashUtil;

import website2018.domain.Account;
import website2018.repository.AccountDao;

@Component
public class RestAuthenticationManager implements AuthenticationProvider{

    @Autowired AccountDao accountDao;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        String principal = (String)token.getPrincipal();
        String credentials = (String)token.getCredentials();
        String hashPassword = hashPassword(credentials);
        
        Account account = accountDao.findByMobile(principal);
        if((account != null) && (account.status == 1)) {
            String hashPasswordFromDb = account.password;
            if(hashPassword.equals(hashPasswordFromDb)) {
                return authentication;
            }
        }
        
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean result = authentication.equals(UsernamePasswordAuthenticationToken.class);
        return result;
    }

    private String hashPassword(String password) {
        return EncodeUtil.encodeBase64(HashUtil.sha1(password));
    }

}
