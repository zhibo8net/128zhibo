package website2018.spider;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

/**
 * Created by Administrator on 2019/5/19.
 */
public class TestUrl {

    public static  void main(String[] args)throws Exception{

        String url="http://liveplay.oadql.cn/live/stream1233.u3mb?xx=123";
      String[] b=url.split("\\/");
        System.out.println(JSON.toJSONString(Lists.newArrayList(b)));

        String u=b[b.length-1].split("\\.")[0];
        System.out.println(u);

        String ld =url.substring(0,url.indexOf("?"));
        System.out.println(ld);
    }
}
