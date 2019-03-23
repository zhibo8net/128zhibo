package website2018.service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.BeanMapper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import website2018.base.BaseEndPoint;
import website2018.cache.CacheUtils;
import website2018.domain.*;
import website2018.dto.AdDTO;
import website2018.dto.DailyLivesDTO;
import website2018.dto.LiveDTO;
import website2018.dto.MatchDTO;
import website2018.dto.SignalDTO;
import website2018.repository.*;
import website2018.service.admin.NewsService;

@Service
public class IndexService {

    public static final String[] weeks = {"周日","周一","周二","周三","周四","周五","周六"};
    public static final List<String> gameList = Lists.newArrayList();
    {
        gameList.add("NBA");
        gameList.add("CBA");
        gameList.add("亚冠");
        gameList.add("欧冠");
        gameList.add("欧联杯");

        gameList.add("中超");
        gameList.add("英超");
        gameList.add("西甲");
        gameList.add("德甲");
        gameList.add("意甲");
    }

    // 缓存数据
    private Cache<String, List<DailyLivesDTO>> dailyLivesCache;

    @Autowired
    VideoQueryer videoQueryer;
    
    @Autowired
    MatchDao matchDao;

    @Autowired
    AdDao adDao;
    
    @Autowired
    EndedDao endedDao;
    
    @Autowired
    FriendLinkDao friendLinkDao;
    
    @Autowired
    ConfigDao configDao;
    
    @Autowired PageAdDao pageAdDao;

    @Autowired
    LiveSourceDao liveSourceDao;
    @Autowired
    NewsService newsService;
    @Autowired
    LiveDao liveDao;
    @PostConstruct
    public void init() {
        dailyLivesCache = CacheBuilder.newBuilder().maximumSize(10).expireAfterWrite(5, TimeUnit.MINUTES).build();
    }

    public List<DailyLivesDTO> dailyLives() {

        List<DailyLivesDTO> dailyLives = dailyLivesCache.getIfPresent("dailyLives");

        //暂时不使用缓存
        if ((dailyLives != null) && true) {

            return dailyLives;

        } else {
            dailyLives = queryDailyLives();

            dailyLivesCache.put("dailyLives", dailyLives);

            return dailyLives;
        }

    }

//    @Scheduled(cron = "0 0/5 * * * *")
//    @Transactional
    public void refreshCache() {// 每5分钟刷新一次缓存

        List<DailyLivesDTO> dailyLives = queryDailyLives();
        dailyLivesCache.put("dailyLives", dailyLives);
    }

    public MatchDTO findMatchDTO(Long id){
        LiveSource liveSource = liveSourceDao.findByActive(1);
        Pattern p = Pattern.compile(".*(" + liveSource.channels.replace(",", "|") + ").*");


        Match m=   matchDao.findById(id);
            MatchDTO mdto = BeanMapper.map(m, MatchDTO.class);
        if(m.guestTeam!=null){
            mdto.guestTeamName=m.guestTeam.teamZh;
            if(StringUtils.isNotEmpty(m.masterTeam.teamImgLink)){
                mdto.guestTeamLink=m.guestTeam.teamImgLink;
            }
        }
        if(m.masterTeam!=null){
            mdto.masterTeamName=m.masterTeam.teamZh;
            if(StringUtils.isNotEmpty(m.masterTeam.teamImgLink)){
                mdto.masterTeamLink=m.masterTeam.teamImgLink;
            }
            mdto.teamFlag="TRUE";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm");
        if(m.playDate!=null){
            mdto.playDateStr=sdf.format(m.playDate);
        }
            mdto.lives = Lists.newArrayList();

            for(Live l : m.lives) {
                if(l.name.contains("CCTV5")) {
                    mdto.emphasis = 1;
                }
                Matcher matcher = p.matcher(l.name);
                if(matcher.matches()) {
                    String tv = matcher.group(1);
                    if(tv.equals("QQ")) {
                        tv = "QQ直播";
                    }
                    LiveDTO liveDTO = null;
                    for(LiveDTO ld : mdto.lives) {
                        if(ld.name.equals(tv)) {
                            liveDTO = ld;
                        }
                    }
                    if(liveDTO == null) {
                        liveDTO = new LiveDTO();
                        liveDTO.name = tv;
                        mdto.lives.add(liveDTO);
                    }
                    //链接重复的，只添加一次
                    boolean existed = false;
                    for(SignalDTO s : liveDTO.signals) {
                        if(s.link.equals(l.link)) {
                            existed = true;
                        }
                    }
                    if(!existed) {
                        int signalsIndex = liveDTO.signals.size() + 1;
                        SignalDTO signalDTO = new SignalDTO();
                        signalDTO.name = l.name;
                        signalDTO.indexName = "信号" + signalsIndex;
                        signalDTO.link = l.link;
                        signalDTO.videoLink=l.videoLink;
                        signalDTO.liveId=l.id;
                        signalDTO.gameId=l.gameId;
                        signalDTO.playFlag=l.playFlag;
                        liveDTO.signals.add(signalDTO);
                    }
                }
            }


         return mdto;


    }
    public List<DailyLivesDTO> queryDailyLives() {
        LiveSource liveSource = liveSourceDao.findByActive(1);
        
        Pattern p = Pattern.compile(".*(" + liveSource.channels.replace(",", "|") + ").*");
        
        boolean onlyHaveLive = configDao.findByCkey("只显示有信号的场次").cvalue.equals("1");
        
        List<Ad> commonAd = adDao.findByType("通用");
        List<Ad> advancedAd = adDao.findByType("高级");

        List<AdDTO> commonDTOs = Lists.newArrayList();
        for (Ad ad : commonAd) {
            commonDTOs.add(BeanMapper.map(ad, AdDTO.class));
        }

        List<AdDTO> advancedDTOs = Lists.newArrayList();
        long currentTimeMills = new Date().getTime();
        for (Ad ad : advancedAd) {
            if (ad.endDate.getTime() > currentTimeMills) {
                advancedDTOs.add(BeanMapper.map(ad, AdDTO.class));
            }
        }

        List<DailyLivesDTO> result = Lists.newArrayList();

        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat dateStrForQueryFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar todayCal = Calendar.getInstance();

        int index = 0;
        Calendar twoHourBeforeNow = Calendar.getInstance();
        twoHourBeforeNow.add(Calendar.MINUTE, -150);
        long twoHourBeforeNowMills = twoHourBeforeNow.getTime().getTime();
        do {
            DailyLivesDTO dailylives = new DailyLivesDTO();

            String dateStr = sdf.format(todayCal.getTime()) + " " + weeks[todayCal.get(Calendar.DAY_OF_WEEK) - 1];
            dailylives.dateStr = dateStr;

            List<Match> matches = matchDao.findByPlayDateStrOrderByPlayDateAsc(dateStrForQueryFormat.format(todayCal.getTime()));
            matchLoop:
            for (Match m : matches) {
//                if(StringUtils.isBlank(m.name)) {
//                    continue matchLoop;
//                }
                //只有当前时间之前2小时之后开球的比赛才显示（例：现在9点，则7点后开球的才显示）
                if(m.playDate.getTime() > twoHourBeforeNowMills) {

                    MatchDTO mdto = BeanMapper.map(m, MatchDTO.class);

                    if(StringUtils.isNotEmpty(mdto.name)&&"1".equals(m.matchNewFlag)){
                        mdto.newsNoContentDTOList=  newsService.findByMatchName(mdto.name+"-"+mdto.id);
                    }

                    if(m.guestTeam!=null){
                        if(StringUtils.isNotEmpty(m.guestTeam.teamImgLink)&&StringUtils.isNotEmpty(m.masterTeam.teamImgLink)){
                            mdto.teamFlag="TRUE";
                            mdto.masterTeamName=m.masterTeam.teamZh;
                            mdto.masterTeamLink=m.masterTeam.teamImgLink;
                            mdto.guestTeamName=m.guestTeam.teamZh;
                            mdto.guestTeamLink=m.guestTeam.teamImgLink;
                        }
                    }
                    mdto.lives = Lists.newArrayList();
                    
                    for(Live l : m.lives) {
                        if(gameList.contains(m.game)) {
                            mdto.emphasis = 1;
                        }
                        Matcher matcher = p.matcher(l.name);
                        if(matcher.matches()) {
                            String tv = matcher.group(1);
                            if(tv.equals("QQ")) {
                                tv = "QQ直播";
                            }
                            LiveDTO liveDTO = null;
                            for(LiveDTO ld : mdto.lives) {
                                if(ld.name.equals(tv)) {
                                    liveDTO = ld;
                                }
                            }
                            if(liveDTO == null) {
                                liveDTO = new LiveDTO();
                                liveDTO.name = tv;
                                mdto.lives.add(liveDTO);
                            }
                            //链接重复的，只添加一次
                            boolean existed = false;
                            for(SignalDTO s : liveDTO.signals) {
                                if(s.link.equals(l.link)) {
                                    existed = true;
                                }
                            }
                            if(!existed) {
                                int signalsIndex = liveDTO.signals.size() + 1;
                                SignalDTO signalDTO = new SignalDTO();
                                signalDTO.name = l.name;
                                signalDTO.indexName = "信号" + signalsIndex;
                                signalDTO.link = l.link;
                                signalDTO.videoLink=l.videoLink;
                                signalDTO.liveId=l.id;
                                signalDTO.playFlag=l.playFlag;
                                signalDTO.gameId=l.gameId;
                                liveDTO.signals.add(signalDTO);
                            }
                        }
                    }

                    // 添加全局广告
                    for (AdDTO c : commonDTOs) {
                        mdto.ads.add(c);
                    }

                    // 添加高级广告
                    for (AdDTO c : advancedDTOs) {
                        boolean match = true;
                        String[] teams = c.teams.split("\\|");
                        for (String t : teams) {
                            if (!mdto.name.contains(t)) {
                                match = false;
                            }
                        }
                        if (match) {
                            mdto.ads.add(c);
                        }
                    }

                    // 根据广告，为比赛添加“重要”（整体变红）
                    for (AdDTO a : mdto.ads) {
                        if (a.important == 1) {
                            mdto.important = 1;
                        }
                    }

                    // 根据配置的「只显示有信号的场次」的值
                    if(onlyHaveLive) {
                        if(mdto.lives.size() > 0) {
                            dailylives.matches.add(mdto);
                        }
                    }else {
                        dailylives.matches.add(mdto);
                    }
                    
                }
            }

            result.add(dailylives);

            todayCal.add(Calendar.DATE, 1);
            index++;
        } while (index < 7);

        return result;
    }
    
    public List<Ended> findEndeds(String project){
        return endedDao.findTop20ByProjectOrderByIdDesc(project);
    }
    public List<Ended> findNewEndeds(Date addTime){
        List list=Lists.newArrayList();
        list.add("VS");
        List<Ended> list1=endedDao.findTop16ByProjectAndNameNotInOrderByAddTimeDesc("足球", list);
        List<Ended> list2=endedDao.findTop16ByProjectAndNameNotInOrderByAddTimeDesc("篮球",list);
        list1=  (list1==null?new ArrayList():list1);
        list2=  (list2==null?new ArrayList():list2);
        list1.addAll(list2);
        return list1;
    }
    public List<Video> findVideos(){

        List<Video> list = (List<Video>) CacheUtils.IndexCache.getIfPresent("ZHIBO8_INDEX_video_list");
        if(list==null||list.size()==0){
            list=videoQueryer.findByProjectGameTypeCount(null, null, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
            CacheUtils.IndexCache.put("ZHIBO8_INDEX_video_list",list);
        }
        return list;
    }

    public List<Video> findLuxiangs(String project){

        return videoQueryer.findByProjectGameTypeCount(project, null, "录像", 20, true);
    }
    public List<Video> findMyLuxiangs(String project){
        List<Video> list = (List<Video>) CacheUtils.IndexCache.getIfPresent("ZHIBO8_INDEX_video_list_by_project");
        if(list==null||list.size()==0){
            list= videoQueryer.findByProjectGameTypeCount(project, null, "录像", 20, true);
            CacheUtils.IndexCache.put("ZHIBO8_INDEX_video_list_by_project",list);
        }
        return list;

    }
    public List<FriendLink> findFriendLinks(){
        return (List<FriendLink>)friendLinkDao.findAll();
    }
    
    public List<String> days(){
        List<String> result = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat();
        for(int i = 0; i < 7; i++) {
            if(i == 0) {
                result.add("今天");
            }else if(i == 1) {
                result.add("明天");
            }else {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, i);
                int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;  
                if(week_index<0){  
                    week_index = 0;  
                }
                result.add(weeks[week_index]);
            }
        }
        return result;
    }
    
    public Map<String, PageAd> pageAds(){
        Map<String, PageAd> result = Maps.newHashMap();

        List<PageAd> list = (List<PageAd>) CacheUtils.IndexCache.getIfPresent("ZHIBO8_INDEX_page_ads");
        if(list==null||list.size()==0){
            list= (List<PageAd>)pageAdDao.findAll();
            CacheUtils.IndexCache.put("ZHIBO8_INDEX_page_ads",list);
        }
        for(PageAd pa : list) {
            result.put(pa.adKey, pa);
        }
        return result;
    }
}
