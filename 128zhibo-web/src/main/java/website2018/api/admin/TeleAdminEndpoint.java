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
import website2018.domain.Tele;
import website2018.dto.admin.TeleAdminDTO;
import website2018.service.admin.TeleService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class TeleAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(TeleAdminEndpoint.class);

    @Autowired
    private TeleService teleService;

    @RequestMapping(value = "/api/admin/teles", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<TeleAdminDTO> listAllTele() {

        assertAdmin();

        List<Tele> teles = teleService.findAll(buildSpecification(BaseService.base(), Tele.class));

        return BeanMapper.mapList(teles, Tele.class, TeleAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/teles/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<TeleAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<Tele> orders = teleService.findAll(buildSpecification(request, Tele.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<TeleAdminDTO> dtos = BeanMapper.mapList(orders, Tele.class, TeleAdminDTO.class);

        Page<TeleAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询电视表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/teles/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public TeleAdminDTO listOneTele(@PathVariable("id") Long id) {

        assertAdmin();

        Tele Tele = teleService.findOne(id);

        return BeanMapper.map(Tele, TeleAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/teles", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createTele(@RequestBody TeleAdminDTO teleAdminDTO) {

        assertAdmin();

        Tele tele = BeanMapper.map(teleAdminDTO, Tele.class);

        tele.addTime = new Date();
        teleService.create(tele);
        
        logService.log("添加电视", "/teleForm/" + tele.id);
    }

    @RequestMapping(value = "/api/admin/teles/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyTele(@RequestBody TeleAdminDTO teleAdminDTO) {

        assertAdmin();

        Tele tele = BeanMapper.map(teleAdminDTO, Tele.class);

        tele.addTime = new Date();
        teleService.modify(tele);

        logService.log("修改电视", "/teleForm/" + tele.id);
    }

    @RequestMapping(value = "/api/admin/teles/{id}", method = RequestMethod.DELETE)
    public void deleteTele(@PathVariable("id") Long id) {

        assertAdmin();

        teleService.delete(id);

        logService.log("删除电视", null);
    }

}
