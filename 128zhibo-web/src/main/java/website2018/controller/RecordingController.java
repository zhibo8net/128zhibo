package website2018.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Maps;

import website2018.base.BaseEndPoint;
import website2018.domain.Video;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.EndedDao;
import website2018.repository.VideoDao;

@Controller
public class RecordingController extends BaseEndPoint {

    @Autowired EndedDao endedDao;
    @Autowired VideoDao videoDao;
    
    @RequestMapping(value = "/recording_1", method = RequestMethod.GET)
    public String recording(@RequestParam(defaultValue="") String project,@RequestParam(defaultValue="") String game, @RequestParam(defaultValue="0") Integer pageNumber, Model model) {

        boolean all = project.equals("") && game.equals("");
        
        if(! (all || projectWhiteList.contains(project))) {
            throw new ServiceException("请正常使用网站", ErrorCode.BAD_REQUEST);
        }
        if(! (all || gameWhiteList.contains(game))) {
            throw new ServiceException("请正常使用网站", ErrorCode.BAD_REQUEST);
        }
        model.addAttribute("pageTitle", project);
        model.addAttribute("project", project);
        model.addAttribute("projectEnglish", project.equals("足球") ? "football" : "basketball");
        model.addAttribute("game", game);
        
        Map<String, Object> requestMap = Maps.newHashMap();
        if(!all) {
            requestMap.put("project", project);
            requestMap.put("game", game);
            model.addAttribute("pageTitle", game+"录像");
        }
        requestMap.put("type", "录像");
        Page page = videoDao.findAll(buildSpecification(requestMap, Video.class), new PageRequest(pageNumber, 12, getSort("id", "DIRE")));
        model.addAttribute("page", page);
        String search = BaseEndPoint.encodeParameterStringWithPrefix(requestMap, "");
        model.addAttribute("pageUrl", "?" + search);

        List<Video> videos;
        if(all) {
            videos = videoQueryer.findByProjectGameTypeCount(null, null, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
        }else {
            videos = videoQueryer.findByProjectGameTypeCount(project, game, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
        }
        model.addAttribute("videos", videos);

        List<Video> luxiangs;
        if(all) {
            luxiangs = videoQueryer.findByProjectGameTypeCount(null, null, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true);
        }else {
            luxiangs = videoQueryer.findByProjectGameTypeCount(project, game, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true);
        }
        model.addAttribute("luxiangs", luxiangs);

        model.addAttribute("menu", "record");
        return "recording";
    }
    
}
