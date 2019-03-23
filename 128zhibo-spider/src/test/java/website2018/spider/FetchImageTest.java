package website2018.spider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import website2018.MyApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FetchImageTest {
    @Autowired
    EndedSpider spider;

    @Test
    public void ended() throws Exception {
        MyApplication.DONT_RUN_SCHEDULED = true;
        spider.fetchEnded();
    }

    @Test
    public void downloadFile() throws Exception {
        String str = spider.downloadFile("https://tu.zhibo8.cc/home/album/38828/9");
        System.out.println(str);
    }
}
