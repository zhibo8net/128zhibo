package website2018.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import website2018.cache.CacheUtils;
import website2018.utils.DateUtils;
import website2018.utils.MD5Util;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/18.
 */
@Service
public class KeyUrlService {

    private static Logger logger = LoggerFactory.getLogger(KeyUrlService.class);


    public String getKeyUrl(String url){
        Map<String, String> sysParamMap = CacheUtils.getSysMap();
        String KEY=sysParamMap.get("LIVE_ZHIBO_KEY")==null?"":sysParamMap.get("LIVE_ZHIBO_KEY");

        if(StringUtils.isEmpty(KEY)){
            logger.warn("播放鉴权为空");
            return url;
        }
        if(StringUtils.isEmpty(url)){
            logger.warn("播放url为空");
            return url;
        }

        String CONTAIN_URL=sysParamMap.get("LIVE_ZHIBO_CONTAIN_URL")==null?"":sysParamMap.get("LIVE_ZHIBO_CONTAIN_URL");

        if(StringUtils.isEmpty(CONTAIN_URL)){
            logger.warn("为配置过滤URL");
            return url;
        }

        List<String> containList= Lists.newArrayList(CONTAIN_URL.split("\\|"));
        boolean flag=false;
        for(String str:containList){
            if(url.indexOf(str)>=0){
                flag=true;
                break;
            }
        }
        if(!flag){
            logger.info("为滤URL不包含当前url,过滤url={},播放url={}", JSON.toJSONString(containList), url);
            return url;
        }

        try{
            if(url.indexOf("?")>0) {
                String ld =url.substring(0,url.indexOf("?"));
                String[] ub=url.split("\\/");
                String u=ub[ub.length-1].split("\\.")[0];

                String txTime= DateUtils.to16Hex(new Date());
                String   txSecret= MD5Util.MD5(KEY + u + txTime);
                StringBuffer sb=new StringBuffer();
                sb.append(ld);
                sb.append("?txTime=").append(txTime).append("&txSecret=").append(txSecret);
                return sb.toString();
            }
        }catch (Exception e){
            logger.error("播放加密错误{}",e);
        }

            return url;
    }

}
