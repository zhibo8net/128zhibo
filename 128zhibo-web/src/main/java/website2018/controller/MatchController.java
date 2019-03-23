package website2018.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.utils.mapper.BeanMapper;
import website2018.base.BaseEndPoint;
import website2018.domain.FriendLink;
import website2018.domain.Live;
import website2018.dto.LiveDTO;
import website2018.dto.MatchDTO;
import website2018.service.IndexService;
import website2018.service.LiveService;

import java.util.List;

/**
 * Created by Administrator on 2018/9/16.
 */
@Controller
public class MatchController extends BaseEndPoint {

    @Autowired
    LiveService liveService;
    @Autowired
    IndexService indexService;

    @RequestMapping(value = "/mwrap/{id}")
    public String mwrap(@PathVariable Long id, Model model) {
        try {
            MatchDTO matchDTO = indexService.findMatchDTO(id);
            if (matchDTO == null) {
                return "redirect:http://www.zhibo8.net/mindex.html";
            }
            List<FriendLink> friendLinks = liveService.findFriendLinks();
            model.addAttribute("friendLinks", friendLinks);
            model.addAttribute("matchDTO", matchDTO);

            return "mwrap";
        } catch (Exception e) {
            return "redirect:http://www.zhibo8.net/mindex.html";
        }

    }

    @RequestMapping(value = "/match_1/{id}")
    public String live(@PathVariable Long id, Model model) {
        try {
            MatchDTO matchDTO = indexService.findMatchDTO(id);
            if (matchDTO == null) {
                return "redirect:http://www.zhibo8.net/";
            }
            List<FriendLink> friendLinks = liveService.findFriendLinks();
            model.addAttribute("friendLinks", friendLinks);
            model.addAttribute("matchDTO", matchDTO);

            return "detail";
        } catch (Exception e) {
            return "redirect:http://www.zhibo8.net/";
        }

    }

    @RequestMapping(value = "/match_old_1/{id}")
    public String match_old_1(@PathVariable Long id, Model model) {
        try {
            MatchDTO matchDTO = indexService.findMatchDTO(id);
            if (matchDTO == null) {
                return "redirect:http://www.zhibo8.net/";
            }
            model.addAttribute("matchDTO", matchDTO);

            return "detail-old";
        } catch (Exception e) {
            return "redirect:http://www.zhibo8.net/";
        }
    }

    @RequestMapping(value = "/live_play_inner/{id}")
    public String live_play_inner(@PathVariable Long id, Model model) {
        try {
            Live live = liveService.findById(id);
            if (live == null || live.match == null) {
                return "redirect:http://www.zhibo8.net/";
            }
            LiveDTO liveDTO = BeanMapper.map(live, LiveDTO.class);
            liveDTO.link = live.link;
            model.addAttribute("liveDTO", liveDTO);

            MatchDTO matchDTO = indexService.findMatchDTO(live.match.id);
            if (matchDTO == null) {
                return "redirect:http://www.zhibo8.net/";
            }
            model.addAttribute("matchDTO", matchDTO);

            return "live-old";
        } catch (Exception e) {
            return "redirect:http://www.zhibo8.net/";
        }
    }
}
