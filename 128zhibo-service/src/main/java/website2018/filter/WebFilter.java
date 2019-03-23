package website2018.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import website2018.base.BaseEndPoint;
import website2018.cache.CacheUtils;
import website2018.domain.FriendLink;
import website2018.domain.Tele;
import website2018.repository.FriendLinkDao;
import website2018.repository.TeleDao;

public class WebFilter extends GenericFilterBean {

    String webImageBase = "";
    
//    TeleDao teleDao;
//    FriendLinkDao friendLinkDao;
    
    @Override
    protected void initFilterBean() throws ServletException {
        
        super.initFilterBean();
        
//        BaseEndPoint baseEndPoint = (BaseEndPoint)WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean("baseEndPoint");
//        webImageBase = baseEndPoint.webImageBase;
//
//        teleDao = (TeleDao)WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean("teleDao");
//        friendLinkDao = (FriendLinkDao)WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean("friendLinkDao");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        List<Tele> teles = CacheUtils.getListTele();
        request.setAttribute("teles", teles);

        List<FriendLink> friendLinks = CacheUtils.getListFriendLink();
        request.setAttribute("friendLinks", friendLinks);

        webImageBase=CacheUtils.getWebImageBase();
        request.setAttribute("webImageBase", webImageBase);
        
        filterChain.doFilter(request, response);
    }

}