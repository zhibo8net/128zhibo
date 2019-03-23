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
import website2018.Enum.ProblemFlag;
import website2018.Enum.ProblemType;
import website2018.base.BaseEndPoint;
import website2018.base.BaseService;
import website2018.domain.Issue;
import website2018.domain.ProblemDb;
import website2018.dto.admin.IssueAdminDTO;
import website2018.dto.admin.ProblemAdminDb;
import website2018.service.admin.IssueAdminService;
import website2018.service.admin.ProblemDbAdminService;
import website2018.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class ProblemDbAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(ProblemDbAdminEndpoint.class);

    @Autowired
    private ProblemDbAdminService problemDbAdminService;



    @RequestMapping(value = "/api/admin/problemDb/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<ProblemAdminDb> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<ProblemDb> orders = problemDbAdminService.findAll(buildSpecification(request, ProblemDb.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<ProblemAdminDb> dtos = BeanMapper.mapList(orders, ProblemDb.class, ProblemAdminDb.class);
        for(ProblemAdminDb problemAdminDb:dtos){


            if(problemAdminDb.updateTime!=null){
                problemAdminDb.updateTimeStr= DateUtils.getDefaultDateStr(problemAdminDb.updateTime);
            }

            if(ProblemType.getEnum(problemAdminDb.problemType)!=null){
                problemAdminDb.problemTypeStr=ProblemType.getEnum(problemAdminDb.problemType).getDesc();
            }

        }

        Page<ProblemAdminDb> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询题库", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/problemDb/{id}", method = RequestMethod.DELETE)
    public void deleteProblemDb(@PathVariable("id") Long id) {

        assertAdmin();

        problemDbAdminService.delete(id);

        logService.log("删除竞猜题库", null);
    }
    @RequestMapping(value = "/api/admin/problemDb/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ProblemAdminDb listOneNews(@PathVariable("id") Long id) {

        assertAdmin();

        ProblemDb problemDb = problemDbAdminService.findOne(id);

        ProblemAdminDb dto = BeanMapper.map(problemDb, ProblemAdminDb.class);



        return dto;
    }
    @RequestMapping(value = "/api/admin/problemDb", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createNews(@RequestBody ProblemAdminDb problemAdminDb) {

        assertAdmin();

        ProblemDb problemDb = BeanMapper.map(problemAdminDb, ProblemDb.class);



        problemDbAdminService.create(problemDb);

        logService.log("添加竞猜题库", "/problemForm/" + problemDb.id);
    }

    @RequestMapping(value = "/api/admin/problemDb/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyMatch(@RequestBody ProblemAdminDb problemAdminDb) throws Exception{

        assertAdmin();

        ProblemDb problemDb = BeanMapper.map(problemAdminDb, ProblemDb.class);



        problemDbAdminService.modify(problemDb);
        logService.log("修改题库", "/problemForm/" + problemDb.id);
    }

    @RequestMapping(value = "/api/admin/problemAdminDbs", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<ProblemAdminDb> listAllProblemAdminDb() throws Exception{

        assertAdmin();

        List<ProblemDb> problemDbs = problemDbAdminService.findByProblemFlag(ProblemFlag.DEFALUT.getCode());

        return BeanMapper.mapList(problemDbs, ProblemDb.class, ProblemAdminDb.class);
    }
}
