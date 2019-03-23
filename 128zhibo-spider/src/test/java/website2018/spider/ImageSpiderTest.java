package website2018.spider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import website2018.MyApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class ImageSpiderTest {
    
    @Autowired
    ImageSpider spider;

    @Test
    public void fetchImage() throws Exception {
        MyApplication.DONT_RUN_SCHEDULED = true;
        
        spider.fetchImage();
    }
    
}
