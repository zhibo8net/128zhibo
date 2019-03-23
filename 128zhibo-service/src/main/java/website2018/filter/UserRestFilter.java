package website2018.filter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.filter.GenericFilterBean;
import website2018.dto.user.ReturnResponse;
import website2018.dto.user.UserDTO;
import website2018.service.user.UserLoginService;
import website2018.utils.SysConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class UserRestFilter extends GenericFilterBean {

    private UserLoginService userLoginService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public UserRestFilter(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }
    public static List<String> backList= Lists.newArrayList();
    static {
        backList.add("/api/user/login");
        backList.add("/api/user/register");
        backList.add("/api/user/getCommentAllList");
        backList.add("/api/user/getCommentPageList");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = getAsHttpRequest(request);


            UserDTO userDTO= (UserDTO) httpRequest.getSession().getAttribute(SysConstants.USER_LOGIN_FLAG);
        if(isBackList(httpRequest)){
            chain.doFilter(request, response);
        }else{
            if(userDTO==null){
                ReturnResponse errorResponse = new ReturnResponse("0001","登陆以失效/用户未登录");
                writeJson(response,errorResponse);

            }else{
                chain.doFilter(request, response);
            }
        }

    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private boolean isBackList(HttpServletRequest request){
        String uri=request.getRequestURI();
       if( backList.contains(uri)){
           return true;
       }
        return false;
    }
    private boolean isAllowIP(List<String> allowedIps, String remoteAddr) {
        for (String allowedIp : allowedIps) {
            if (validateIP(allowedIp, remoteAddr)) {
                return true;
            }
        }
        return true;
    }

    private void writeJson( ServletResponse response, ReturnResponse errorResponse) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        String userJson = convertObjectToJson(errorResponse);
        OutputStream out = response.getOutputStream();
        out.write(userJson.getBytes("UTF-8"));
        out.flush();
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