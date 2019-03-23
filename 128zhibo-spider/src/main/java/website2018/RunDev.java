package website2018;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.AbstractEnvironment;

public class RunDev {
    
    public static void main(String[] args) throws Exception {
        MyApplication.DONT_RUN_SCHEDULED = true;
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");
        SpringApplication.run(MyApplication.class, args);
    }

}