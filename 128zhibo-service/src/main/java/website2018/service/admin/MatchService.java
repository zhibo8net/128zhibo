package website2018.service.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import website2018.domain.Ad;
import website2018.domain.Live;
import website2018.domain.Match;
import website2018.domain.Team;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.AdDao;
import website2018.repository.LiveDao;
import website2018.repository.MatchDao;
import website2018.service.KeyUrlService;
import website2018.service.TeamCheckService;
import website2018.utils.DateUtils;

@Service
public class MatchService {

    @Autowired
    MatchDao matchDao;
    
    @Autowired
    LiveDao liveDao;
    
    @Autowired
    AdDao adDao;
    @Autowired
    TeamCheckService teamService;
    @Autowired
    KeyUrlService keyUrlService;
    @Transactional(readOnly = true)
    public List<Match> findAll(Specification spec) {
        return matchDao.findAll(spec);
    }
    @Transactional(readOnly = true)
    public List<Match> findMatchRelList() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return matchDao.findTop100ByPlayDateGreaterThanOrderByPlayDateAsc(new Date());
    }

    @Transactional(readOnly = true)
    public List<Match> matchListByProject(String project) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return matchDao.findTop10000ByPlayDateGreaterThanAndProjectOrderByPlayDateAsc(new Date(), project);
    }

    @Transactional(readOnly = true)
    public Page<Match> findAll(Pageable pageable) {
        return matchDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Match> findAll(Specification<Match> specification, Pageable pageable) {
        return matchDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Match findOne(Long id) {
        return matchDao.findOne(id);
    }

    @Transactional
    public void create(Match match) {
        for(Live live:match.lives){
            live.link=keyUrlService.getKeyUrl(live.link);
            live.match=match;
        }
        if(match.guestTeam==null&&match.masterTeam==null){
          String matchName[]=  match.name.split("VS");
            if(matchName.length>=2){
                Team team1=teamService.checkTeamSaveTeam(matchName[0], match.project);
                match.masterTeam=team1;
                Team team2=teamService.checkTeamSaveTeam(matchName[1], match.project);
                match.guestTeam=team2;
            }else{
                String matchname[]=  match.name.split("vs");
                if(matchname.length>=2){
                    Team team1=teamService.checkTeamSaveTeam(matchname[0], match.project);
                    match.masterTeam=team1;
                    Team team2=teamService.checkTeamSaveTeam(matchname[1], match.project);
                    match.guestTeam=team2;
                }
            }
        }
        matchDao.save(match);
    }

    @Transactional
    public void modify(Match match) {

        Match orginalMatch = matchDao.findOne(match.id);

        if (orginalMatch == null) {
            throw new ServiceException("比赛不存在", ErrorCode.BAD_REQUEST);
        }
        orginalMatch.playDateStr=match.playDateStr;
        orginalMatch.playDate= DateUtils.getDate((match.playDateStr+" "+match.playTime),"yyyy-MM-dd HH:mm");
        orginalMatch.playTime=match.playTime;
        orginalMatch.sinaLiveUrl=match.sinaLiveUrl;
        orginalMatch.sinaShujuUrl=match.sinaShujuUrl;
        orginalMatch.matchStreamUrl=match.matchStreamUrl;
        orginalMatch.name = match.name;
        orginalMatch.locked = match.locked;
        orginalMatch.unlockTime = match.unlockTime;
        orginalMatch.project = match.project;
        orginalMatch.game = match.game;
        orginalMatch.emphasis = match.emphasis;

        // 删掉原有的直播数据
        for (Live live : orginalMatch.lives) {
            liveDao.delete(live.id);
        }
        orginalMatch.lives.clear();
        // 添加新的直播数据
        for (Live live : match.lives) {
            live.link=keyUrlService.getKeyUrl(live.link);
            orginalMatch.lives.add(live);
            live.match = orginalMatch;
        }

        // 删掉原有的广告数据
        for (Ad ad : orginalMatch.ads) {
            adDao.delete(ad.id);
        }
        orginalMatch.ads.clear();
        // 添加新的广告数据
        for (Ad ad : match.ads) {
            orginalMatch.ads.add(ad);
            ad.match = orginalMatch;
        }
        
        matchDao.save(orginalMatch);
    }

    @Transactional
    public void delete(Long id) {
        Match match = matchDao.findOne(id);

        if (match == null) {
            throw new ServiceException("比赛不存在", ErrorCode.BAD_REQUEST);
        }

        matchDao.delete(id);
    }

}
