package website2018.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import website2018.domain.SysParam;
import website2018.repository.*;
import website2018.utils.SysConstants;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/14.
 */
@Service
public class SysParamService {

    @Autowired
    SysParamDao sysParamDao;


//    @Scheduled(cron = "0 0/10 * * * *")
//    public void refreshCache() {// 每10分钟刷新一次缓存
//
//        try{
//            Map<String, String> sysParamMap = SysConstants.sysParamMap;
//            sysParamMap.clear();
//            List<SysParam> sysParamList= (List<SysParam>) sysParamDao.findAll();
//            for(SysParam sysParam:sysParamList){
//                sysParamMap.put(sysParam.sysKey,sysParam.sysValue);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
}

