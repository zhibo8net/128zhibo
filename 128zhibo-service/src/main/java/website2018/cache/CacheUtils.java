package website2018.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import website2018.base.BaseEndPoint;
import website2018.domain.*;
import website2018.dto.DailyLivesDTO;
import website2018.repository.*;
import website2018.utils.SpringContextHolder;
import website2018.utils.SysConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/12/15.
 */
public class CacheUtils {


    // 缓存数据
   public static Cache<String,Object> baseFooterCache= CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES).build();

    public static Cache<String,Object> IndexCache= CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(5, TimeUnit.MINUTES).build();

    public static Cache<String,Object> UserCache= CacheBuilder.newBuilder().maximumSize(3000).expireAfterWrite(5, TimeUnit.MINUTES).build();

    public static Cache<String,Object> baseCache= CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES).build();

    public static Cache<String,Object> fecthUserCache= CacheBuilder.newBuilder().maximumSize(3000).expireAfterWrite(15, TimeUnit.MINUTES).build();


    public static Cache<String,Object> fecthCacheComment= CacheBuilder.newBuilder().maximumSize(100000).expireAfterWrite(10, TimeUnit.HOURS).build();


    public static List<Tele> getListTele(){

        List<Tele> listTele = (List<Tele>) baseFooterCache.getIfPresent("ZHIBO8_TELE_LIST");

        if(listTele==null||listTele.size()==0){
            TeleDao  teleDao=  (TeleDao) SpringContextHolder.getApplicationContext().getBean("teleDao");
            listTele= (List<Tele>)teleDao.findAll();
            baseFooterCache.put("ZHIBO8_TELE_LIST",listTele);
        }
        return listTele;

    }

    public static List<FriendLink> getListFriendLink(){

        List<FriendLink> listFriendLink = (List<FriendLink>) baseFooterCache.getIfPresent("ZHIBO8_FriendLink_LIST");

        if(listFriendLink==null||listFriendLink.size()==0){
            FriendLinkDao    friendLinkDao=  (FriendLinkDao) SpringContextHolder.getApplicationContext().getBean("friendLinkDao");
            listFriendLink= (List<FriendLink>)friendLinkDao.findAll();
            baseFooterCache.put("ZHIBO8_FriendLink_LIST",listFriendLink);
        }
        return listFriendLink;

    }

    public static String getWebImageBase(){

        String webImageBase=(String) baseFooterCache.getIfPresent("ZHIBO8_webImageBase");
        if(StringUtils.isEmpty(webImageBase)){
            BaseEndPoint baseEndPoint=  (BaseEndPoint) SpringContextHolder.getApplicationContext().getBean("baseEndPoint");

            if(baseEndPoint!=null){
                webImageBase=baseEndPoint.webImageBase;
                baseFooterCache.put("ZHIBO8_webImageBase",webImageBase);
            }

        }
        return webImageBase;
    }


    public static List<String> getSensitiveList(){

        List<String>  sensitiveList=( List<String>) baseFooterCache.getIfPresent("ZHIBO8_sensitiveList");
        if(sensitiveList==null||sensitiveList.size()==0){
            SensitiveDao sensitiveDao=  (SensitiveDao) SpringContextHolder.getApplicationContext().getBean("sensitiveDao");
            sensitiveList=new ArrayList<>();
            if(sensitiveDao!=null){
             List<Sensitive>   list= (List<Sensitive>) sensitiveDao.findAll();
               for(Sensitive sensitive:list){
                   if(StringUtils.isNotEmpty(sensitive.content)){
                       String[] str=sensitive.content.split("\\|");
                       for(String s:str){
                           if(!sensitiveList.contains(s)){
                               sensitiveList.add(s);
                           }
                       }
                   }
               }
                baseFooterCache.put("ZHIBO8_sensitiveList",sensitiveList);
            }

        }
        return sensitiveList;
    }


    public static List<Team> getListTeam(){

        List<Team> listTeam = (List<Team>) baseFooterCache.getIfPresent("ZHIBO8_listTeam_LIST");

        if(listTeam==null||listTeam.size()==0){
            TeamDao    teamDao=  (TeamDao) SpringContextHolder.getApplicationContext().getBean("teamDao");
            listTeam= (List<Team>)teamDao.findAll();
            baseFooterCache.put("ZHIBO8_listTeam_LIST",listTeam);
        }
        return listTeam;

    }


    public static Map<String,String> getSysMap(){

        Map<String,String> sysMap =(Map<String,String>) baseFooterCache.getIfPresent("ZHIBO8_sys_map");

        if(sysMap==null||sysMap.isEmpty()){
            SysParamDao sysParamDao=  (SysParamDao) SpringContextHolder.getApplicationContext().getBean("sysParamDao");
            List<SysParam> sysParamList= (List<SysParam>) sysParamDao.findAll();
            sysMap =new HashMap<String,String>();
            for(SysParam sysParam:sysParamList){
                sysMap.put(sysParam.sysKey,sysParam.sysValue);
            }

            baseFooterCache.put("ZHIBO8_sys_map",sysMap);
        }
        return sysMap;

    }
}
