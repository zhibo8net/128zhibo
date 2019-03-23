package website2018.api.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.web.MediaTypes;

import website2018.base.BaseEndPoint;
import website2018.base.BaseService;
import website2018.domain.Log;
import website2018.dto.admin.LogAdminDTO;
import website2018.service.admin.LogService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class LogAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(LogAdminEndpoint.class);

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/api/admin/logs", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<LogAdminDTO> listAllLog() {

        assertAdmin();

        List<Log> logs = logService.findAll(buildSpecification(BaseService.base(), Log.class));

        return BeanMapper.mapList(logs, Log.class, LogAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/logs/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<LogAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) throws Exception{

        assertAdmin();

        Page<Log> orders = logService.findAll(buildSpecification(request, Log.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<LogAdminDTO> dtos = BeanMapper.mapList(orders, Log.class, LogAdminDTO.class);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(LogAdminDTO dto : dtos) {
            dto.addTimeStr = sdf.format(dto.addTime);
        }

        Page<LogAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/logs/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public LogAdminDTO listOneLog(@PathVariable("id") Long id) {

        assertAdmin();

        Log Log = logService.findOne(id);

        return BeanMapper.map(Log, LogAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/logs", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createLog(@RequestBody LogAdminDTO logAdminDTO) {

        assertAdmin();

        logAdminDTO.addTime = new Date();
        Log Log = BeanMapper.map(logAdminDTO, Log.class);

        logService.create(Log);
    }

    @RequestMapping(value = "/api/admin/logs/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyLog(@RequestBody LogAdminDTO logAdminDTO) {

        assertAdmin();

        Log Log = BeanMapper.map(logAdminDTO, Log.class);

        logAdminDTO.addTime = new Date();
        logService.modify(Log);

    }

    @RequestMapping(value = "/api/admin/logs/{id}", method = RequestMethod.DELETE)
    public void deleteLog(@PathVariable("id") Long id) {

        assertAdmin();

        logService.delete(id);

    }

}
