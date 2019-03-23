package website2018.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.BeanMapper;

import com.google.common.collect.Lists;

import website2018.base.BaseEndPoint;
import website2018.domain.Ad;
import website2018.domain.Live;
import website2018.domain.LiveSource;
import website2018.domain.Match;
import website2018.domain.Video;
import website2018.dto.AdDTO;
import website2018.dto.DailyLivesDTO;
import website2018.dto.LiveDTO;
import website2018.dto.MatchDTO;
import website2018.dto.SignalDTO;

@Service
public class LiveService extends IndexService{

    public Live findById(Long id){
       return liveDao.findById(id);
    }
    public List<DailyLivesDTO> queryDailyLives(String project, String game) {
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
        do {
            DailyLivesDTO dailylives = new DailyLivesDTO();

            String dateStr = sdf.format(todayCal.getTime()) + " " + weeks[todayCal.get(Calendar.DAY_OF_WEEK) - 1];
            dailylives.dateStr = dateStr;

            List<Match> matches;
            if(game != null) {
                matches = matchDao.findByPlayDateStrAndProjectAndGame(dateStrForQueryFormat.format(todayCal.getTime()), project, game);
            }else {
                matches = matchDao.findByPlayDateStrAndProject(dateStrForQueryFormat.format(todayCal.getTime()), project);
            }
            
            for (Match m : matches) {
                MatchDTO mdto = BeanMapper.map(m, MatchDTO.class);
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
                            signalDTO.liveId=l.id;
                            signalDTO.playFlag=l.playFlag;
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
                    String[] teams = c.teams.split("|");
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

            result.add(dailylives);

            todayCal.add(Calendar.DATE, 1);
            index++;
        } while (index < 7);

        return result;
    }
    
    public List<Video> findVideos(String project, String game){
        return videoQueryer.findByProjectGameTypeCount(project, game, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
    }

    public List<Video> findLuxiangs(String project, String game){
        return videoQueryer.findByProjectGameTypeCount(project, game, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true);
    }
    
}
