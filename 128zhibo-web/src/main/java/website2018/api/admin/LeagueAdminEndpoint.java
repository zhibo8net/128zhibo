package website2018.api.admin;

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
import website2018.domain.League;
import website2018.dto.admin.LeagueAdminDTO;
import website2018.service.admin.LeagueService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class LeagueAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(LeagueAdminEndpoint.class);



    @Autowired
    private LeagueService leagueService;


    @RequestMapping(value = "/api/admin/league/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<LeagueAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<League> orders = leagueService.findAll(buildSpecification(request, League.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<LeagueAdminDTO> dtos = BeanMapper.mapList(orders, League.class, LeagueAdminDTO.class);

        Page<LeagueAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询组图表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/league/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public LeagueAdminDTO listOneImageBag(@PathVariable("id") Long id) {

        assertAdmin();

        League league = leagueService.findOne(id);

        LeagueAdminDTO dto = BeanMapper.map(league, LeagueAdminDTO.class);

        return dto;
    }

    @RequestMapping(value = "/api/admin/league", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createLeague(@RequestBody LeagueAdminDTO leagueAdminDTO) {

        assertAdmin();

        League league = BeanMapper.map(leagueAdminDTO, League.class);

        league.addTime = new Date();
        league.updateTime=new Date();
        leagueService.create(league);
        
        logService.log("添加联赛", "/teamForm/" + league.id);
    }

    @RequestMapping(value = "/api/admin/league/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyLeague(@RequestBody LeagueAdminDTO leagueAdminDTO) {

        assertAdmin();

        League league = BeanMapper.map(leagueAdminDTO, League.class);

        league.updateTime=new Date();
        leagueService.modify(league);

        logService.log("修改联赛", "/teamForm/" + league.id);
    }

    @RequestMapping(value = "/api/admin/league/{id}", method = RequestMethod.DELETE)
    public void deleteTeam(@PathVariable("id") Long id) {

        assertAdmin();

        leagueService.delete(id);

        logService.log("删除联赛", null);
    }

}
