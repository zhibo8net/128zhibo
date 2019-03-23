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
import website2018.base.BaseService;
import website2018.domain.ImageBag;
import website2018.domain.Team;
import website2018.dto.admin.ImageAdminDTO;
import website2018.dto.admin.ImageBagAdminDTO;
import website2018.dto.admin.TeamAdminDTO;
import website2018.service.admin.ImageBagService;
import website2018.service.admin.TeamService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class TeamAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(TeamAdminEndpoint.class);



    @Autowired
    private TeamService teamService;


    @RequestMapping(value = "/api/admin/team/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<TeamAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<Team> orders = teamService.findAll(buildSpecification(request, Team.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<TeamAdminDTO> dtos = BeanMapper.mapList(orders, Team.class, TeamAdminDTO.class);

        Page<TeamAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询组图表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/team/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public TeamAdminDTO listOneImageBag(@PathVariable("id") Long id) {

        assertAdmin();

        Team team = teamService.findOne(id);

        TeamAdminDTO dto = BeanMapper.map(team, TeamAdminDTO.class);

        return dto;
    }

    @RequestMapping(value = "/api/admin/team", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createTeam(@RequestBody TeamAdminDTO teamAdminDTO) {

        assertAdmin();

        Team team = BeanMapper.map(teamAdminDTO, Team.class);

        team.addTime = new Date();
        team.updateTime=new Date();
        teamService.create(team);
        
        logService.log("添加球队", "/teamForm/" + team.id);
    }

    @RequestMapping(value = "/api/admin/team/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyTeam(@RequestBody TeamAdminDTO teamAdminDTO) {

        assertAdmin();

        Team team = BeanMapper.map(teamAdminDTO, Team.class);

        team.updateTime=new Date();
        teamService.modify(team);

        logService.log("修改球队", "/teamForm/" + team.id);
    }

    @RequestMapping(value = "/api/admin/team/{id}", method = RequestMethod.DELETE)
    public void deleteTeam(@PathVariable("id") Long id) {

        assertAdmin();

        teamService.delete(id);

        logService.log("删除球队", null);
    }

}
