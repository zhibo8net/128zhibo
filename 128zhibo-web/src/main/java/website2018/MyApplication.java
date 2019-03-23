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
        registrationBean.addUrlPatterns("/test1.html");
        registrationBean.addUrlPatterns("/test2.html");
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
        registrationBean.addUrlPatterns("/live_1.html");
        registrationBean.addUrlPatterns("/match_1/*");
        registrationBean.addUrlPatterns("/projectVideo_1.html");
        registrationBean.addUrlPatterns("/gameVideo_1.html");
        registrationBean.addUrlPatterns("/recording_1.html");
        registrationBean.addUrlPatterns("/news_1.html");
        registrationBean.addUrlPatterns("/news_1/*");
        registrationBean.addUrlPatterns("/image_1.html");
        registrationBean.addUrlPatterns("/projectImage_1.html");
        registrationBean.addUrlPatterns("/viewImage_1/*");
        registrationBean.addUrlPatterns("/video_1/*");
        registrationBean.addUrlPatterns("/nba_video.html");
        registrationBean.addUrlPatterns("/footer_video.html");
        registrationBean.addUrlPatterns("/news_detail/*");
        registrationBean.addUrlPatterns("/nba_news.html");
        registrationBean.addUrlPatterns("/footer_news.html");
        registrationBean.addUrlPatterns("/match_old_1/*");
        registrationBean.addUrlPatterns("/checkUser.html");
        registrationBean.addUrlPatterns("/checkUser");
        registrationBean.addUrlPatterns("/live_1");
        registrationBean.addUrlPatterns("/match_1/*");
        registrationBean.addUrlPatterns("/projectVideo_1");
        registrationBean.addUrlPatterns("/gameVideo_1");
        registrationBean.addUrlPatterns("/recording_1");
        registrationBean.addUrlPatterns("/news_1");
        registrationBean.addUrlPatterns("/news_1/*");
        registrationBean.addUrlPatterns("/image_1");
        registrationBean.addUrlPatterns("/projectImage_1");
        registrationBean.addUrlPatterns("/viewImage_1/*");
        registrationBean.addUrlPatterns("/video_1/*");
        registrationBean.addUrlPatterns("/nba_video");
        registrationBean.addUrlPatterns("/footer_video");
        registrationBean.addUrlPatterns("/news_detail/*");
        registrationBean.addUrlPatterns("/live_play_inner/*");
        registrationBean.addUrlPatterns("/nba_news");
        registrationBean.addUrlPatterns("/footer_news");
        registrationBean.addUrlPatterns("/prospect");
        registrationBean.addUrlPatterns("/prospect.html");
        registrationBean.addUrlPatterns("/download.html");
        registrationBean.addUrlPatterns("/userPage.html");
        registrationBean.addUrlPatterns("/mindex.html");
        registrationBean.addUrlPatterns("/mlogin.html");
        registrationBean.addUrlPatterns("/mdetail/*");
        registrationBean.addUrlPatterns("/mwrap/*");
        registrationBean.addUrlPatterns("/mnews.html");
        registrationBean.addUrlPatterns("/mvideo.html");
        registrationBean.addUrlPatterns("/jingcai.html");
        registrationBean.addUrlPatterns("/jingcainotice.html");


        registrationBean.addUrlPatterns("/test1.html");
        registrationBean.addUrlPatterns("/test2.html");
        return registrationBean;
    }

    public static void main(String[] args) throws Exception {
        BaseEndPoint.USE_CACHE = false;
        SpringApplication.run(MyApplication.class, args);
    }

}