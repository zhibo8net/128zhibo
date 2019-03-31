package website2018.service;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website2018.cache.CacheUtils;
import website2018.domain.Team;
import website2018.repository.TeamDao;
import website2018.utils.SysConstants;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/14.
 */
@Service
public class TeamCheckService {
    private static Logger logger = LoggerFactory.getLogger(TeamCheckService.class);
    @Autowired
    TeamDao teamDao;

    @Autowired
    BaoWeiService baoWeiService;

//    @Scheduled(cron = "0 0/10 * * * *")
//    public void refreshCache() {// 每10分钟刷新一次缓存
//
//        try{
//           List<Team> teamList= (List<Team>) teamDao.findAll();
//
//            SysConstants.cacheTeamList=teamList;
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
    public Team lVCheckTeam(String teamZh,String project){
        List<Team> teamList= CacheUtils.getListTeam();
        Map<String, String> sysParamMap = CacheUtils.getSysMap();
        String lvStr= sysParamMap.get("LIVE_LV_MATCH_TEAM")==null?"0.5": sysParamMap.get("LIVE_LV_MATCH_TEAM");
        float lv1=Float.parseFloat(lvStr);

        for(Team team:teamList){
            float lv=baoWeiService.checkNameAlike(teamZh+project,team.teamZh);
            if(lv>lv1){
                logger.info("相似度匹球队配成功{} ",team.teamZh );
                return team;
            }

           lv=baoWeiService.checkNameAlike(teamZh,team.teamZh);
            if(lv>lv1){
                logger.info("相似度匹球队配成功{} " ,team.teamZh);
             return team;
            }



            if(StringUtils.isNotEmpty(team.teamName1)){
                lv=baoWeiService.checkNameAlike(team.teamName1,teamZh);
                if(lv>lv1){
                    logger.info("相似度匹球队配成功 {}",team.teamZh);
                    return team;
                }

            }


            if(StringUtils.isNotEmpty(team.teamName2)){
                lv=baoWeiService.checkNameAlike(team.teamName2,teamZh);
                if(lv>lv1){
                    logger.info("相似度匹球队配成功 {}",team.teamZh);
                    return team;
                }


            }

            if(StringUtils.isNotEmpty(team.teamName3)){
                lv=baoWeiService.checkNameAlike(team.teamName3,teamZh);
                if(lv>lv1){
                    logger.info("相似度匹球队配成功{}",JSONObject.fromObject(team).toString());
                    return team;
                }

            }
        }
        return null;
    }

    public Team checkTeamNotSave(String teamZh,String project){
        try{
             Team t= lVCheckTeam(teamZh,project);

            if(t!=null){
                return t;
            }
            List<Team> tmListproject=teamDao.findByTeamZh(teamZh+project);
            if(tmListproject!=null&&tmListproject.size()>=1){
                return  tmListproject.get(0);
            }
            List<Team> tmList11=teamDao.findByTeamName1(teamZh+project);
            if(tmList11!=null&&tmList11.size()>=1){
                return  tmList11.get(0);
            }
            List<Team> tmList=teamDao.findByTeamZh(teamZh);
            if(tmList!=null&&tmList.size()>=1){
                return  tmList.get(0);
            }
            List<Team> tmList1=teamDao.findByTeamName1(teamZh);
            if(tmList1!=null&&tmList1.size()>=1){
                return  tmList1.get(0);
            }
            List<Team> tmList2=teamDao.findByTeamName2(teamZh);
            if(tmList2!=null&&tmList2.size()>=1){
                return  tmList2.get(0);
            }
            List<Team> tmList3=teamDao.findByTeamName3(teamZh);
            if(tmList3!=null&&tmList3.size()>=1){
                return  tmList3.get(0);
            }
        }catch (Exception e){
            logger.error("检查球队异常",e);
        }
        return null;

    }
    public Team saveTeam(Team team){
        return teamDao.save(team);
    }
    public Team checkTeamSaveTeam(String teamZh, String project,String image) {
        try {


            Team t=  checkTeamNotSave(teamZh, project);
            if(t!=null){
                return t;
            }

            Team tm = new Team();
            tm.addTime = new Date();
            tm.updateTime = new Date();
            tm.teamZh = teamZh;
            logger.info("保存球队{}", tm.teamZh);
            return teamDao.save(tm);

        } catch (Exception e) {
            logger.error("检查球队异常",e);
        }
        return null;
    }

    public Team checkTeamSaveTeam(String teamZh, String project) {
        try {


          Team t=  checkTeamNotSave(teamZh,project);
            if(t!=null){
                return t;
            }

            Team tm = new Team();
            tm.addTime = new Date();
            tm.updateTime = new Date();
            tm.teamZh = teamZh;
            logger.info("保存球队{}", tm.teamZh);
            return teamDao.save(tm);

        } catch (Exception e) {
            logger.error("检查球队异常",e);
        }
        return null;
    }
}

