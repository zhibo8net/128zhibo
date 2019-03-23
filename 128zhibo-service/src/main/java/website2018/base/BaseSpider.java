package website2018.base;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;

import website2018.utils.UploadUtils;

@Component
public class BaseSpider {

    // 已爬取过的url们
    protected Cache<String, String> fetched;

    protected Logger logger = LoggerFactory.getLogger(BaseSpider.class);

    // 官网文档：HttpClient是线程安全的
    CloseableHttpClient httpClient;

    @PostConstruct
    public void init() {

        // httpClient的初始化
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36";
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5 * 1000).setConnectTimeout(5 * 1000)
                .build();
        httpClient = HttpClientBuilder.create().setUserAgent(userAgent).setMaxConnTotal(500).setMaxConnPerRoute(200)
                .setDefaultRequestConfig(requestConfig).build();

        // 已爬取过的url们
        fetched = CacheBuilder.newBuilder().maximumSize(10000).expireAfterAccess(7, TimeUnit.DAYS).build();

        // 特定爬虫的初始化方法
        initForOneSpider();
    }

    protected void initForOneSpider() {

    }


    @PreDestroy
    public void closeClient() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (Exception e) {
                // ...
            } finally {
                logger.warn("httpClient已关闭");
            }
        }
    }

    public Document readDocFrom(String url) {
        try {
            String html = readFromUrl(url);
            if(html == null) {
                return null;
            }
            Document doc = Jsoup.parse(html);
            return doc;
        }catch(Exception e) {
            return null;
        }
    }
    
    public Document readDocFromByJsoup(String url) {
        try {
            return Jsoup.connect(url).get();
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String readDocFromByJsoupReqJson(String url) {
        try {
            Connection.Response res = Jsoup.connect(url).header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(10000).ignoreContentType(true).execute();

            return res.body();
        }catch(Exception e) {
           // e.printStackTrace();
            return null;
        }
    }
    public String readFromUrl(String url) throws Exception {
        return this.readFromUrl(url, "UTF-8");
    }

    public String readFromUrl(String url, String encoding) throws Exception {

        CloseableHttpResponse remoteResponse = null;

        InputStream entityInputStream = null;

        try {

            HttpGet httpGet = new HttpGet(url);

            remoteResponse = httpClient.execute(httpGet);

            HttpEntity entity = remoteResponse.getEntity();

            entityInputStream = entity.getContent();

            String str = IOUtils.toString(entityInputStream, encoding);

            return str;
        } catch (Exception e) {
            logger.error("抓取网页发生错误：" + url + "。错误信息：" + e.getMessage());
            return null;
        } finally {
            if (entityInputStream != null) {
                try {
                    entityInputStream.close();
                } catch (Exception e) {
                    // ...
                }
            }
            if (remoteResponse != null) {
                try {
                    remoteResponse.close();
                } catch (Exception e) {
                    // ...
                }
            }
        }

    }

    public List<String> texts(Node node) {
        List<String> result = Lists.newArrayList();
        for (Node c : node.childNodes()) {
            if (c instanceof TextNode) {
                String trim = ((TextNode) c).text().trim();
                if (trim.length() != 0) {
                    result.add(trim);
                }
            }
        }
        return result;
    }

    @Value("${upload.uploadPath}")
   public String baseDir;

    //返回例如2018/2/2/aakjfkasjfk.jpeg
    public String downloadFile(String url) {
        try {
            
            String urlWithoutParam = url.indexOf("?") < 0 ? url : url.substring(0, url.indexOf("?"));
            String ext = urlWithoutParam.indexOf(".") < 0 ? ".ext"
                    : urlWithoutParam.substring(urlWithoutParam.lastIndexOf("."));
            String filename ="";
            if(url.indexOf("redirect")>=0){

                filename = UUID.randomUUID().toString()+".gif";
            }else{
                filename = UUID.randomUUID().toString() + ext;
            }

            String nameWithTimePrefix = UploadUtils.nameWithTimePrefix(filename);
            String fullPath = baseDir + nameWithTimePrefix;
            FileUtils.copyURLToFile(new URL(url), new File(fullPath));


            System.out.println("保存图片到：" + fullPath);
            return nameWithTimePrefix;
        } catch (Exception e) {
            logger.error("未能获取图片：" + url);
            return "";
        }

    }

    public String game(String str) {
        String pattern = "(英超|意甲|西甲|法甲|德甲|中超|欧冠|NBA|CBA)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        if (m.find()) {
            String g = m.group();
            return g;
        } else {
            return "";
        }
    }

    public String type(String str) {
        String pattern = "(录像|集锦|片段)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        if (m.find()) {
            String g = m.group();
            return g;
        } else {
            return "";
        }
    }
}
