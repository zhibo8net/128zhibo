package website2018.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.utils.mapper.BeanMapper;
import website2018.base.BaseEndPoint;
import website2018.domain.Live;
import website2018.dto.LiveDTO;
import website2018.dto.MatchDTO;
import website2018.service.IndexService;
import website2018.service.LiveService;

import javax.servlet.http.HttpServletRequest;


@RestController
public class MatchResetController extends BaseEndPoint {

    @Autowired
    LiveService liveService;
    @Autowired
    IndexService indexService;

    @RequestMapping(value = "api/web/live/{id}")
    public LiveDTO getLive(@PathVariable Long id){

            Live live = liveService.findById(id);
            if (live == null || live.match == null) {
                return null;
            }
            LiveDTO liveDTO = BeanMapper.map(live, LiveDTO.class);
            liveDTO.link = live.link;
            return liveDTO;

    }
    @RequestMapping(value = "api/web/match/{id}")
    public MatchDTO getMatch(@PathVariable Long id,HttpServletRequest request) {


            MatchDTO matchDTO = indexService.findMatchDTO(id,request);
            if (matchDTO == null) {
                return null;
            }

            return matchDTO;

    }

}
