package website2018.base;

import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.filter.RestFilter;
import website2018.service.LoginService;
import website2018.service.VideoQueryer;
import website2018.service.admin.LogService;
import website2018.utils.DynamicSpecifications;
import website2018.utils.SearchFilter;

@Component
public class BaseEndPoint {

    //是否使用页面缓存的开关
    public static boolean USE_CACHE = false;

    Logger logger = LoggerFactory.getLogger(BaseEndPoint.class);

    // 页面缓存
    private Cache<String, String> cachedPages;

    @Autowired
    ServletContext servletContext;

    @Autowired
    LoginService loginService;
    
    public static final int RIGHT_VIDEO_COUNT = 20;
    public static final int RIGHT_LUXIANG_COUNT = 20;
    
    @Autowired
    protected VideoQueryer videoQueryer;
    
    @Autowired
    protected LogService logService;

    @Value("${upload.webImageBase}")
    public String webImageBase;
    
    @Value("${upload.uploadPath}")
    public String uploadPath;

    @Value("${preoject.localhost}")
    public String localhost;

    public List<String> projectWhiteList = Lists.newArrayList("篮球", "足球");
    
    public List<String> gameWhiteList = Lists.newArrayList("英超|意甲|西甲|法甲|德甲|中超|欧冠|世界杯|NBA|CBA|其他".split("\\|"));
    
    @PostConstruct
    public void init() {
        // 页面缓存：缓存时间5分钟
        cachedPages = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(60 * 5, TimeUnit.SECONDS).build();
    }

    protected void assertAdmin() {
        if (RestFilter.accountHolder.get() == null) {
            throw new ServiceException("没有管理员权限", ErrorCode.FORBIDDEN);
        }
    }

    protected void assertSuper() {
        if (RestFilter.accountHolder.get() == null) {
            throw new ServiceException("没有超级管理员权限", ErrorCode.FORBIDDEN);
        }else if(! RestFilter.accountHolder.get().mobile.equals("admin")) {
            throw new ServiceException("没有超级管理员权限", ErrorCode.FORBIDDEN);
        }
    }
    
    protected Sort getSort(String sortType, String sortDire){
        Direction dire;
        if("asc".equalsIgnoreCase(sortDire)){
            dire = Direction.ASC;
        }else{
            dire = Direction.DESC;
        }
        
        if(sortType.equals("id")){
            return new Sort(dire, "id");
        }else{
            return new Sort(dire, sortType);
        }
    }
    
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Validate.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while ((paramNames != null) && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if ((values == null) || (values.length == 0)) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    if(unprefixed.endsWith("Time")) {
                        try {
                            if(StringUtils.isNotBlank(values[0])) {
                                params.put(unprefixed, new SimpleDateFormat("yyyy-MM-dd").parse(values[0]));
                            }
                        }catch(java.text.ParseException pe) {
                            System.out.println("查询时间格式错误。");
                        }
                    }else {
                        params.put(unprefixed, values[0]);
                    }
                }
            }
        }
        return params;
    }

    public <E> Specification<E> buildSpecification(Map<String, Object> searchParams, Class<E> entityClass) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<E> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClass);
        return spec;
    }

    public <E> Specification<E> buildSpecification(ServletRequest request, Class<E> entityClass) {
        return buildSpecification(getParametersStartingWith(request, "search_"), entityClass);
    }

    /**
     * 组合Parameters生成Query String的Parameter部分, 并在paramter name上加上prefix.
     * 
     * @see #getParametersStartingWith
     */
    public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
        if ((params == null) || (params.size() == 0)) {
            return "";
        }

        if (prefix == null) {
            prefix = "";
        }

        StringBuilder queryStringBuilder = new StringBuilder();
        Iterator<Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
            if (it.hasNext()) {
                queryStringBuilder.append('&');
            }
        }
        return queryStringBuilder.toString();
    }

    // 尝试从页面缓存中获取
    // 调用方法：controller方法中执行业务逻辑，将数据放于request内，然后调用此方法
    protected String page(HttpServletRequest request, HttpServletResponse response, String name) {

        String html = cachedPages.getIfPresent(name);

        if (html == null) {

            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setPrefix("templates/");
            resolver.setSuffix(".html");
            resolver.setCharacterEncoding("UTF-8");

            TemplateEngine templateEngine = new TemplateEngine();
            // 让layout语法生效
            templateEngine.addDialect(new LayoutDialect());
            templateEngine.setTemplateResolver(resolver);

            final IContext ctx = new WebContext(request, response, servletContext);

            html = templateEngine.process(name, ctx);

            if (USE_CACHE) {
                cachedPages.put(name, html);
            }

            logger.debug("生成" + name);

        } else {

            logger.debug("从页面缓存中获得" + name);

        }

        return html;
    }
}
