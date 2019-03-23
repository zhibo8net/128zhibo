package website2018.spider;

import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveSpiderTest {
    @Autowired
    LiveSpider spider;

    @Test
    public void fetchLive() throws Exception {
        spider.testFetch();
    }
    
    public static void main(String[] args) throws Exception {
        Jsoup.connect("https://m.zhibo8.cc").get();
    }
    
}
