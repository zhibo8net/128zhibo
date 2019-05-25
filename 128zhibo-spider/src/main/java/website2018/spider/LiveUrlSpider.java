package website2018.spider;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import website2018.MyApplication;
import website2018.base.BaseSpider;
import website2018.domain.*;
import website2018.repository.*;
import website2018.service.BaoWeiService;
import website2018.service.KeyUrlService;
import website2018.service.TeamCheckService;
import website2018.utils.SpringContextHolder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class LiveUrlSpider extends BaseSpider {
    private static Logger logger = LoggerFactory.getLogger(LiveUrlSpider.class);

    @Autowired
    KeyUrlService keyUrlService;
    @Autowired
    MatchDao matchDao;

    @Scheduled(cron = "0 0/3 * * * *")
    @Transactional
    public void runSchedule() throws Exception {
            // 启动新线程来抓取
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 在新线程中打开Session
                        EntityManagerFactory entityManagerFactory = SpringContextHolder.getApplicationContext().getBean(EntityManagerFactory.class);
                        EntityManager entityManager = entityManagerFactory.createEntityManager();
                        EntityManagerHolder entityManagerHolder = new EntityManagerHolder(entityManager);
                        TransactionSynchronizationManager.bindResource(entityManagerFactory, entityManagerHolder);

                        fetchLiveUrl();

                        // 关闭新线程的Session
                        TransactionSynchronizationManager.unbindResource(entityManagerFactory);
                        EntityManagerFactoryUtils.closeEntityManager(entityManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e.getLocalizedMessage());
                    }
                }
            }, "LiveSpiderUrl - " + System.currentTimeMillis());
            t.start();


        }

    public void fetchLiveUrl(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -240);
        Date d=calendar.getTime();
        calendar.add(Calendar.MINUTE, 300);
        Date d1=calendar.getTime();
        List<Match> matchList=matchDao.findByPlayDateGreaterThanAndPlayDateLessThan(d,d1);
        for(Match match:matchList){
            try {


                boolean flag = false;
                List<Live> liveList = match.lives;
                for (Live live : liveList) {
                    String liveUrl = keyUrlService.getKeyUrl(live.link);
                    if (!StringUtils.equals(liveUrl, live.link)) {
                        live.link=liveUrl;
                        flag = true;
                    }
                }

                if (flag) {
                    matchDao.save(match);
                }
            }catch (Exception e){
                logger.error("定时加密url错误",e);
            }
        }
    }
}
