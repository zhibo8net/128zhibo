package website2018.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.springside.modules.utils.mapper.BeanMapper;
import website2018.base.BaseEndPoint;
import website2018.domain.Ended;
import website2018.domain.Video;
import website2018.dto.MatchDTO;
import website2018.dto.ProjectVideo;
import website2018.dto.match.BasketMatchRankDTO;
import website2018.dto.match.BasketMatchTelRankDTO;
import website2018.dto.match.FootBallJSBRankDTO;
import website2018.dto.match.FootBallSSBRankDTO;
import website2018.dto.video.VideoDTO;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.EndedDao;
import website2018.repository.VideoDao;
import website2018.service.MatchRankService;

@Controller
public class VideoController extends BaseEndPoint {
    
    @Autowired EndedDao endedDao;
    @Autowired VideoDao videoDao;
    @Autowired
    MatchRankService matchRankService;

    @RequestMapping(value = "/video_1/{id}", method = RequestMethod.GET)
    public String ended(@PathVariable Long id, Model model) {

        Ended entity = endedDao.findOne(id);
        if(entity==null){
            return "redirect:http://www.zhibo8.net/";
        }
        model.addAttribute("entity", entity);

        model.addAttribute("project", entity.project);
        model.addAttribute("pageTitle", entity.name);
        List<Video> videos = videoQueryer.findByProjectGameTypeCount(entity.project, null, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
        model.addAttribute("videos", videos);

        List<Video> luxiangs = videoQueryer.findByProjectGameTypeCount(entity.project, null, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true);
        model.addAttribute("luxiangs", luxiangs);
        
        model.addAttribute("menu", (entity.project.equals("足球") ? "football" : "basketball")+"Video");
        return "ended";
    }

    @RequestMapping(value = "/projectVideo_1", method = RequestMethod.GET)
    public String projectVideo(String project, Model model) {

        if(! (projectWhiteList.contains(project))) {
            throw new ServiceException("请正常使用网站", ErrorCode.BAD_REQUEST);
        }
        model.addAttribute("project", project);
        model.addAttribute("pageTitle", project+"集锦");
        model.addAttribute("projectEnglish", project.equals("足球") ? "football" : "basketball");
        List<String> games = Lists.newArrayList();
        if(project.equals("篮球")) {
            games = Lists.newArrayList("NBA|CBA|热门".split("\\|"));
        }else {
            games = Lists.newArrayList("英超|意甲|西甲|法甲|德甲|中超|欧冠|热门".split("\\|"));
        }
        
        List<ProjectVideo> projectVideos = Lists.newArrayList();
        for(String g : games) {
            ProjectVideo pv = new ProjectVideo();
            pv.name = g;
            List<Video> videos = videoQueryer.findByProjectGameTypeCount(project, g, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
            for(int i = 0; (i < videos.size()) && (i < 4); i++) {
                pv.videos.add(videos.get(i));
            }
            projectVideos.add(pv);
        }
        model.addAttribute("projectVideos", projectVideos);

        List<Video> videos = videoQueryer.findByProjectGameTypeCount(project, null, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
        model.addAttribute("videos", videos);

        List<Video> luxiangs = videoQueryer.findByProjectGameTypeCount(project, null, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true);
        model.addAttribute("luxiangs", luxiangs);

        model.addAttribute("menu", (project.equals("足球") ? "football" : "basketball")+"Video");
        return "projectVideo";
    }

    @RequestMapping(value = "/mvideo")
    public String mvideo( Model model) throws Exception {
        List<Video> videos = videoQueryer.findByProjectGameTypeCount(null, null, "视频", 100);
        List<VideoDTO> videoDTOList = Lists.newArrayList();

        for (Video video : videos) {
            VideoDTO videoDTO = BeanMapper.map(video, VideoDTO.class);

            if (video.name.length() > 25) {
                videoDTO.name = video.name.substring(0, 25);
            }
            videoDTOList.add(videoDTO);

        }
        model.addAttribute("mvideoList", videoDTOList);

        return "mvideo";
    }
        @RequestMapping(value = "/footer_video", method = RequestMethod.GET)
    public String footerVideo( Model model) throws Exception {
        List<Video> videos = videoQueryer.findByProjectGameTypeCount("足球", null, "视频", 13);
        List<VideoDTO> videoDTOList=Lists.newArrayList();
        int i=0;
        for(Video video:videos){
            VideoDTO videoDTO=BeanMapper.map(video, VideoDTO.class);
            if(i==0){
                videoDTO.isFisrt="TRUE";
            }
            if(video.name.length()>20){
                videoDTO.name=video.name.substring(0,20);
            }
            videoDTOList.add(videoDTO);
            i++;
        }
        model.addAttribute("videos", videoDTOList);

        //最新视频
        List<Video> newVideos = videoQueryer.findByProjectGameTypeCount("足球",null, "视频", 20);
        model.addAttribute("newVideos", newVideos);
        List<Video> hotVideos = videoQueryer.findByProjectGameTypeCount("足球", "热门", "视频", 10);
        model.addAttribute("hotVideos", hotVideos);
        //英超|意甲|西甲|法甲|德甲|中超|欧冠
        List<Video> ycVideos = videoQueryer.findByProjectGameTypeCount("足球", "英超", "视频", 20);
        model.addAttribute("ycVideos", ycVideos);

        List<Video> yjVideos = videoQueryer.findByProjectGameTypeCount("足球", "意甲", "视频", 20);
        model.addAttribute("yjVideos", yjVideos);

        List<Video> xjVideos = videoQueryer.findByProjectGameTypeCount("足球", "西甲", "视频", 20);
        model.addAttribute("xjVideos", xjVideos);

        List<Video> fjVideos = videoQueryer.findByProjectGameTypeCount("足球", "法甲", "视频", 20);
        model.addAttribute("fjVideos", fjVideos);

        List<Video> djVideos = videoQueryer.findByProjectGameTypeCount("足球", "德甲", "视频", 20);
        model.addAttribute("djVideos", djVideos);

        List<Video> zcVideos = videoQueryer.findByProjectGameTypeCount("足球", "中超", "视频", 20);
        model.addAttribute("zcVideos", zcVideos);

        List<Video> ogVideos = videoQueryer.findByProjectGameTypeCount("足球", "欧冠", "视频", 20);
        model.addAttribute("ogVideos", ogVideos);


        FootBallJSBRankDTO footBallJSBRankDTO= matchRankService.queryFootBallJSBRankDTO();
        if(footBallJSBRankDTO!=null){
            model.addAttribute("zcFootballJsbList", footBallJSBRankDTO.zcFootballJsbList);
            model.addAttribute("ycFootballJsbList", footBallJSBRankDTO.ycFootballJsbList);
            model.addAttribute("xjFootballJsbList", footBallJSBRankDTO.xjFootballJsbList);

            model.addAttribute("yjFootballJsbList", footBallJSBRankDTO.yjFootballJsbList);
            model.addAttribute("djFootballJsbList", footBallJSBRankDTO.djFootballJsbList);
            model.addAttribute("fjFootballJsbList", footBallJSBRankDTO.fjFootballJsbList);
        }
        FootBallSSBRankDTO footBallSSBRankDTO= matchRankService.queryFootBallSSBRankDTO();
        if(footBallSSBRankDTO!=null){
            model.addAttribute("zcFootballSsbList", footBallSSBRankDTO.zcFootballSsbList);
            model.addAttribute("ycFootballSsbList", footBallSSBRankDTO.ycFootballSsbList);
            model.addAttribute("xjFootballSsbList", footBallSSBRankDTO.xjFootballSsbList);

            model.addAttribute("yjFootballSsbList", footBallSSBRankDTO.yjFootballSsbList);
            model.addAttribute("djFootballSsbList", footBallSSBRankDTO.djFootballSsbList);
            model.addAttribute("fjFootballSsbList", footBallSSBRankDTO.fjFootballSsbList);

            model.addAttribute("ogFootballSsbList", footBallSSBRankDTO.ogFootballSsbList);
            model.addAttribute("ygFootballSsbList", footBallSSBRankDTO.ygFootballSsbList);
        }
        return "footer_video";
    }
        @RequestMapping(value = "/nba_video", method = RequestMethod.GET)
    public String nbaVideo( Model model) throws Exception {

        List<Video> videos = videoQueryer.findByProjectGameTypeCount("篮球", "NBA", "视频", 13);
        List<VideoDTO> videoDTOList=Lists.newArrayList();
        int i=0;
        for(Video video:videos){
            VideoDTO videoDTO=BeanMapper.map(video, VideoDTO.class);
            if(i==0){
                videoDTO.isFisrt="TRUE";

            }
            if(video.name.length()>20){
                videoDTO.name=video.name.substring(0,20);
            }
            videoDTOList.add(videoDTO);
            i++;
        }
        model.addAttribute("videos", videoDTOList);

        List<Video> newVideos = videoQueryer.findByProjectGameTypeCount("篮球",null, "视频", 20);
        model.addAttribute("newVideos", newVideos);

        List<Video> hotVideos = videoQueryer.findByProjectGameTypeCount("篮球", "热门", "视频", 10);
        model.addAttribute("hotVideos", hotVideos);

        List<Video> cbaNewVideos = videoQueryer.findByProjectGameTypeCount("篮球", "CBA", "视频", 10);
        model.addAttribute("cbaNewVideos", cbaNewVideos);

        List<Video> cbaHotVideos = videoQueryer.findByProjectGameTypeCount("篮球", "CBA", "录像", 10);
        model.addAttribute("cbaHotVideos", cbaHotVideos);

        BasketMatchRankDTO basketMatchRankDTO= matchRankService.queryBasketballRankDTO();
        if(basketMatchRankDTO!=null){
            model.addAttribute("NBAWestMatchRankList", basketMatchRankDTO.NBAWestMatchRankList);
            model.addAttribute("NBAEastMatchRankList", basketMatchRankDTO.NBAEastMatchRankList);
            model.addAttribute("CBAMatchRankList", basketMatchRankDTO.CBAMatchRankList);
        }

        BasketMatchTelRankDTO basketMatchTelRankDTO= matchRankService.queryBasketMatchTelRankDTO();
        if(basketMatchTelRankDTO!=null){
            model.addAttribute("DSMatchTelRankList", basketMatchTelRankDTO.DSMatchTelRankList);
            model.addAttribute("LBMatchTelRankList", basketMatchTelRankDTO.LBMatchTelRankList);
            model.addAttribute("ZGMatchTelRankList", basketMatchTelRankDTO.ZGMatchTelRankList);
            model.addAttribute("QDMatchTelRankList", basketMatchTelRankDTO.QDMatchTelRankList);
            model.addAttribute("GMMatchTelRankList", basketMatchTelRankDTO.GMMatchTelRankList);
        }
        return "nba_video";
    }
    @RequestMapping(value = "/gameVideo_1", method = RequestMethod.GET)
    public String gameVideo(String project, String game, @RequestParam(defaultValue="0") Integer pageNumber, Model model) {

        if(! projectWhiteList.contains(project)) {
            throw new ServiceException("请正常使用网站", ErrorCode.BAD_REQUEST);
        }
        if(! gameWhiteList.contains(game)) {
            throw new ServiceException("请正常使用网站", ErrorCode.BAD_REQUEST);
        }
        model.addAttribute("project", project);
        model.addAttribute("game", game);
        model.addAttribute("projectEnglish", project.equals("足球") ? "football" : "basketball");
        model.addAttribute("pageTitle", game+"集锦");
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("project", project);
        requestMap.put("game", game);
        requestMap.put("type", "视频");
        Page page = videoDao.findAll(buildSpecification(requestMap, Video.class), new PageRequest(pageNumber, 12, getSort("id", "DIRE")));
        model.addAttribute("page", page);
        String search = BaseEndPoint.encodeParameterStringWithPrefix(requestMap, "");
        model.addAttribute("pageUrl", "?" + search);

        List<Video> videos = videoQueryer.findByProjectGameTypeCount(project, game, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
        model.addAttribute("videos", videos);

        List<Video> luxiangs = videoQueryer.findByProjectGameTypeCount(project, game, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true);
        model.addAttribute("luxiangs", luxiangs);

        model.addAttribute("menu", (project.equals("足球") ? "football" : "basketball")+"Video");
        return "gameVideo";
    }
    
}
