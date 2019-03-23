package website2018.api.admin;

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
import website2018.domain.Config;
import website2018.dto.admin.ConfigAdminDTO;
import website2018.service.admin.ConfigService;

@RestController
public class ConfigAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(ConfigAdminEndpoint.class);

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/api/admin/configs", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<ConfigAdminDTO> listAllConfig() {

        assertAdmin();

        List<Config> configs = configService.findAll(buildSpecification(BaseService.base(), Config.class));

        return BeanMapper.mapList(configs, Config.class, ConfigAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/configs/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<ConfigAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<Config> orders = configService.findAll(buildSpecification(request, Config.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<ConfigAdminDTO> dtos = BeanMapper.mapList(orders, Config.class, ConfigAdminDTO.class);

        Page<ConfigAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询配置表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/configs/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ConfigAdminDTO listOneConfig(@PathVariable("id") Long id) {

        assertAdmin();

        Config Config = configService.findOne(id);

        return BeanMapper.map(Config, ConfigAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/configs", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createConfig(@RequestBody ConfigAdminDTO configAdminDTO) {

        assertAdmin();

        Config config = BeanMapper.map(configAdminDTO, Config.class);

        config.addTime = new Date();
        configService.create(config);
        
        logService.log("添加配置", "/configForm/" + config.id);
    }

    @RequestMapping(value = "/api/admin/configs/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyConfig(@RequestBody ConfigAdminDTO configAdminDTO) {

        assertAdmin();

        Config config = BeanMapper.map(configAdminDTO, Config.class);

        config.addTime = new Date();
        configService.modify(config);

        logService.log("修改配置", "/configForm/" + config.id);
    }

    @RequestMapping(value = "/api/admin/configs/{id}", method = RequestMethod.DELETE)
    public void deleteConfig(@PathVariable("id") Long id) {

        assertAdmin();

        configService.delete(id);

        logService.log("删除配置", null);
    }

}
