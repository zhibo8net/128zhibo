package website2018.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.google.common.collect.Lists;

import website2018.domain.Account;
import website2018.dto.Token;
import website2018.service.LoginService;

public class RestFilter extends GenericFilterBean {

    private LoginService accountService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();
    public static ThreadLocal<Account> accountHolder = new ThreadLocal<Account>();

    public RestFilter(LoginService accountService) {
        this.accountService = accountService;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = getAsHttpRequest(request);

        String token = extractAuthTokenFromRequest(httpRequest);

        if (validateTokenKey(token)) {
            Account account = accountService.getLoginUser(token);
            if(account != null) {
                RestFilter.requestHolder.set(httpRequest);
                RestFilter.accountHolder.set(account);
                List<String> allowedIPs = Lists.newArrayList();
                if (isAllowIP(allowedIPs, request.getRemoteAddr())) {
                    if (true) {
                        if (true) {
                            UserDetails userDetails = account;
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
                            log.info("Unable to authenticate the token: " + token
                                    + ". Incorrect secret or token is expired");
                        }
                    }
                } else {
                    log.info("Unable to authenticate the token: " + token + ". IP - " + request.getRemoteAddr()
                            + " is not allowed");
                }
            }else {
                log.info("Unable to authenticate the token: " + token + ". IP - " + request.getRemoteAddr()
                + " is not allowed");
            }
        } else {
            log.info("Unable to authenticate the token: " + token + ". Key is broken");
        }

        chain.doFilter(request, response);
    }

    private void updateLastLogin(final Token token) {

    }

    private boolean isAllowIP(List<String> allowedIps, String remoteAddr) {
        for (String allowedIp : allowedIps) {
            if (validateIP(allowedIp, remoteAddr)) {
                return true;
            }
        }
        return true;
    }

    private boolean validateIP(String allowedIp, String remoteAddr) {
        return true;
    }

    private boolean validateTokenKey(String token) {
        return token != null;
    }

    private HttpServletRequest getAsHttpRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }

        return (HttpServletRequest) request;
    }

    private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
        /* Get token from header */
        String authToken = httpRequest.getHeader("X-Auth-Token");

        /* If token not found get it from request parameter */
        if (authToken == null) {
            authToken = httpRequest.getParameter("token");
        }

        return authToken;
    }

    @Override
    public void destroy() {
        
    }

}