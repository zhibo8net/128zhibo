package website2018;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import website2018.base.BaseEndPoint;
import website2018.filter.RestFilter;
import website2018.filter.UserRestFilter;
import website2018.filter.WebFilter;
import website2018.service.LoginService;
import website2018.service.user.UserLoginService;

@SpringBootApplication
@EnableScheduling
public class MyApplication {
    
    public static boolean DONT_RUN_SCHEDULED = false;
    public static boolean TEST_LIVE_SPIDER = false;

    @Autowired LoginService accountService;

    @Autowired
    UserLoginService userLoginService;
    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }
    
    @Bean
    public FilterRegistrationBean restFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RestFilter(accountService));
        registrationBean.addUrlPatterns("/api/web/*");
        registrationBean.addUrlPatterns("/api/admin/*");
        registrationBean.addUrlPatterns("/admin_1/php/upload_json.php");
        registrationBean.addUrlPatterns("/api/matchRank/*");
        registrationBean.addUrlPatterns("/api/matchSupport/*");
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean userRestFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new UserRestFilter(userLoginService));
        registrationBean.addUrlPatterns("/api/user/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
   @Bean
    public FilterRegistrationBean webFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new WebFilter());
        registrationBean.addUrlPatterns("/");

        return registrationBean;
    }

    public static void main(String[] args) throws Exception {
        BaseEndPoint.USE_CACHE = false;
        SpringApplication.run(MyApplication.class, args);
    }

}