package website2018.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springside.modules.utils.mapper.BeanMapper;

import com.google.common.collect.Lists;

import website2018.base.BaseEndPoint;
import website2018.domain.*;
import website2018.dto.DailyLivesDTO;
import website2018.dto.EndedDTO;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.MatchDao;
import website2018.service.IndexService;
import website2018.service.LiveService;

@Controller
public class LiveController extends BaseEndPoint {

    @Autowired
    LiveService liveService;
    @Autowired
    IndexService indexService;

    @Autowired
    MatchDao matchDao;

    @RequestMapping(value = "/live_1/{id}")
    public String live(@PathVariable Long id, Model model) {
       Live live=liveService.findById(id);
        if(live==null){
            return "redirect:http://www.zhibo8.net/";
        }
        model.addAttribute("pageAds", indexService.pageAds());
        if(StringUtils.isNotEmpty(live.videoLink)){
            if("PPTV".equals(live.name)){
                model.addAttribute("liveAddress","PPTV");
            }
            if("俄罗斯体育频道".equals(live.name)){
                model.addAttribute("liveAddress","ELS");
                model.addAttribute("BridgeMovieObjectId","BridgeMovieObject"+live.gameId);
               String s="ZonePlayGameId="+live.gameId+"&scaleMode=scaleAll&userID=0&videoID="+live.gameId+"&matchName=直播吧&startImmediately=true&gameId="+live.gameId+"&lng=cn&sport=0&ref=36";
                model.addAttribute("BridgeMovieObjectData",s);
                  }
        }else{
            model.addAttribute("liveAddress","false");
        }
        model.addAttribute("live",live);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(live.match.playDate==null?new Date():live.match.playDate);
        model.addAttribute("matchTime",dateString);
        List<FriendLink> friendLinks = liveService.findFriendLinks();
        model.addAttribute("friendLinks", friendLinks);

        return "detail-old";
    }
    @RequestMapping(value = "/zuqiubf")
    public String zuqiubf( Model model) {
        model.addAttribute("menu","bf");
        return "zuqiubf";
    }
    @RequestMapping(value = "/live_1", method = RequestMethod.GET)
    public String live(String project, String game, Model model) {
        
        if(! (projectWhiteList.contains(project))) {
            throw new ServiceException("请正常使用网站", ErrorCode.BAD_REQUEST);
        }

        model.addAttribute("project", project);
        model.addAttribute("projectEnglish", project.equals("足球") ? "football" : "basketball");
        if(game != null) {
            model.addAttribute("game", game);
            model.addAttribute("pageTitle", game+"直播");
        }else {
            model.addAttribute("game", project);
            model.addAttribute("pageTitle", project+"直播");
        }
        
        List<Ended> projectEndedEntitys = liveService.findEndeds(project);
        List<EndedDTO> projectEndeds = Lists.newArrayList();
        List<EndedDTO> projectEndeds2 = Lists.newArrayList();
        for(Ended e : projectEndedEntitys) {
            EndedDTO ed = BeanMapper.map(e, EndedDTO.class);
            for(Video v : e.videos) {
                if(v.type.equals("集锦")) {
                    ed.hasJijin = true;
                }else if(v.type.equals("录像")) {
                    ed.hasLuxiang = true;
                }
            }
            if(ed.hasJijin || ed.hasLuxiang) {
                if(ed.name.length() < 13 && ed.name.length() > 3) {
                    projectEndeds.add(ed);
                }else if(ed.name.length() > 13){
                    projectEndeds2.add(ed);
                }
            }
            if(projectEndeds.size() == 8) {
                break;
            }
        }
        model.addAttribute("projectEndeds", projectEndeds);
        model.addAttribute("projectEndeds2", projectEndeds2);

        List<DailyLivesDTO> dailyLives = liveService.queryDailyLives(project, game);
        model.addAttribute("dailyLives", dailyLives);
        
        List<Video> videos = liveService.findVideos(project, game);
        model.addAttribute("videos", videos);

        List<Video> projectLuxiangs = liveService.findLuxiangs(project, game);
        model.addAttribute("luxiangs", projectLuxiangs);

        List<FriendLink> friendLinks = liveService.findFriendLinks();
        model.addAttribute("friendLinks", friendLinks);
        
        List<String> days = liveService.days();
        model.addAttribute("days", days);

        model.addAttribute("menu", (project.equals("足球") ? "football" : "basketball")+"Live");
        return "live";
    }

}
