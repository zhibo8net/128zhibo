package website2018.spider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import website2018.MyApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoSpiderTest {
    @Autowired
    VideoSpider spider;

    @Test
    public void fetchVideo() throws Exception {
        MyApplication.DONT_RUN_SCHEDULED = true;
        spider.fetchVideo();
    }

    @Test
    public void test() throws Exception {
        MyApplication.DONT_RUN_SCHEDULED = true;
        spider.test();
    }
}
