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
import website2018.domain.Sensitive;
import website2018.dto.admin.SensitiveAdminDTO;
import website2018.service.admin.SensitiveAdminService;
import website2018.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class SensitiveAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(SensitiveAdminEndpoint.class);



    @Autowired
    private SensitiveAdminService sensitiveAdminService;


    @RequestMapping(value = "/api/admin/sensitive/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<SensitiveAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<Sensitive> orders = sensitiveAdminService.findAll(buildSpecification(request, Sensitive.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<SensitiveAdminDTO> dtos = BeanMapper.mapList(orders, Sensitive.class, SensitiveAdminDTO.class);
        for(SensitiveAdminDTO sensitiveAdminDTO:dtos){
            sensitiveAdminDTO.optTime= DateUtils.getDateStr(sensitiveAdminDTO.updateTime,"yyyy-MM-dd HH:mm:ss");
        }
        Page<SensitiveAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("敏感字符分页查询", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/sensitive/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public SensitiveAdminDTO listOneSensitive(@PathVariable("id") Long id) {

        assertAdmin();

        Sensitive sensitive = sensitiveAdminService.findOne(id);

        SensitiveAdminDTO dto = BeanMapper.map(sensitive, SensitiveAdminDTO.class);

        return dto;
    }

    @RequestMapping(value = "/api/admin/sensitive", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createSensitive(@RequestBody SensitiveAdminDTO sensitiveAdminDTO) {

        assertAdmin();

        Sensitive sensitive = BeanMapper.map(sensitiveAdminDTO, Sensitive.class);

        sensitive.addTime = new Date();
        sensitive.updateTime=new Date();
        sensitiveAdminService.create(sensitive);
        
        logService.log("添加敏感词", "/sensitiveForm/" + sensitive.id);
    }

    @RequestMapping(value = "/api/admin/sensitive/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifySensitive(@RequestBody SensitiveAdminDTO sensitiveAdminDTO) {

        assertAdmin();

        Sensitive sensitive=BeanMapper.map(sensitiveAdminDTO, Sensitive.class);

        sensitive.updateTime=new Date();
        sensitiveAdminService.modify(sensitive);

        logService.log("修改敏感词", "/teamForm/" + sensitiveAdminDTO.id);
    }

    @RequestMapping(value = "/api/admin/sensitive/{id}", method = RequestMethod.DELETE)
    public void deleteSensitive(@PathVariable("id") Long id) {

        assertAdmin();

        sensitiveAdminService.delete(id);

        logService.log("删除敏感词", null);
    }

}
