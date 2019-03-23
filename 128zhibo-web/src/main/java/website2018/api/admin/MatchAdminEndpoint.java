package website2018.api.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.web.MediaTypes;

import website2018.base.BaseEndPoint;
import website2018.base.BaseService;
import website2018.domain.Match;
import website2018.dto.admin.MatchAdminDTO;
import website2018.dto.admin.MatchAdminDTOForList;
import website2018.service.admin.MatchService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class MatchAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(MatchAdminEndpoint.class);

    @Autowired
    private MatchService matchService;

    @RequestMapping(value = "/api/admin/matchs", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<MatchAdminDTO> listAllMatch() {

        assertAdmin();

        List<Match> matchs = matchService.findAll(buildSpecification(BaseService.base(), Match.class));

        return BeanMapper.mapList(matchs, Match.class, MatchAdminDTO.class);
    }
    @RequestMapping(value = "/api/admin/matchList", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public String listRelMatch() {

        List<Match> matchs = matchService.findMatchRelList();
        StringBuffer sb=new StringBuffer();

        for(Match match:matchs){
            if(StringUtils.isNotEmpty(match.name)){
                sb.append(match.name).append("-").append(match.id).append("|");
            }

        }
        return sb.toString();
    }
    @RequestMapping(value = "/api/admin/matchs/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<MatchAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<Match> orders = matchService.findAll(buildSpecification(request, Match.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<MatchAdminDTOForList> dtos = BeanMapper.mapList(orders, Match.class, MatchAdminDTOForList.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        for(MatchAdminDTOForList d : dtos) {
            if(d.unlockTime != null) {
                d.unlockDateStr = dateFormat.format(d.unlockTime);
                d.unlockTimeStr = timeFormat.format(d.unlockTime);
            }
        }

        Page<MatchAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询比赛表", null);

        return dtoPage;
    }
    @RequestMapping(value = "/api/admin/matchListByProject", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public String matchListByProject(@RequestParam String project) {

        List<Match> matchs = matchService.matchListByProject(project);
        StringBuffer sb=new StringBuffer();

        for(Match match:matchs){
            if(StringUtils.isNotEmpty(match.name)){
                sb.append(match.name).append("-").append(match.id).append("|");
            }

        }
        return sb.toString();
    }
    @RequestMapping(value = "/api/admin/matchs/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public MatchAdminDTO listOneMatch(@PathVariable("id") Long id) {

        assertAdmin();

        Match Match = matchService.findOne(id);

        MatchAdminDTO result = BeanMapper.map(Match, MatchAdminDTO.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        if(result.unlockTime != null) {
            result.unlockDateStr = dateFormat.format(result.unlockTime);
            result.unlockTimeStr = timeFormat.format(result.unlockTime);
        }
        
        return result;
    }

    @RequestMapping(value = "/api/admin/matchs", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createMatch(@RequestBody MatchAdminDTO matchAdminDTO) throws Exception{

        assertAdmin();

        Match match = BeanMapper.map(matchAdminDTO, Match.class);
        
        if(StringUtils.isNotEmpty(matchAdminDTO.unlockDateStr) && StringUtils.isNotEmpty(matchAdminDTO.unlockTimeStr)) {
            Date unlockTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(matchAdminDTO.unlockDateStr + " " + matchAdminDTO.unlockTimeStr);
            match.unlockTime = unlockTime;
        }

        match.addTime = new Date();
        matchService.create(match);
        
        logService.log("添加比赛", "/matchForm/" + match.id);
    }

    @RequestMapping(value = "/api/admin/matchs/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyMatch(@RequestBody MatchAdminDTO matchAdminDTO) throws Exception{

        assertAdmin();

        Match match = BeanMapper.map(matchAdminDTO, Match.class);

        if(StringUtils.isNotEmpty(matchAdminDTO.unlockDateStr) && StringUtils.isNotEmpty(matchAdminDTO.unlockTimeStr)) {
            Date unlockTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(matchAdminDTO.unlockDateStr + " " + matchAdminDTO.unlockTimeStr);
            match.unlockTime = unlockTime;
        }

        match.addTime = new Date();
        matchService.modify(match);

        logService.log("修改比赛", "/matchForm/" + match.id);
    }

    @RequestMapping(value = "/api/admin/matchs/{id}", method = RequestMethod.DELETE)
    public void deleteMatch(@PathVariable("id") Long id) {

        assertAdmin();

        matchService.delete(id);

        logService.log("删除比赛", null);
    }

}
