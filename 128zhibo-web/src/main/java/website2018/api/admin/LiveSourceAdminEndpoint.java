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
import website2018.domain.LiveSource;
import website2018.dto.admin.LiveSourceAdminDTO;
import website2018.service.admin.LiveSourceService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class LiveSourceAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(LiveSourceAdminEndpoint.class);

    @Autowired
    private LiveSourceService liveSourceService;

    @RequestMapping(value = "/api/admin/liveSources", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<LiveSourceAdminDTO> listAllLiveSource() {

        assertAdmin();

        List<LiveSource> liveSources = liveSourceService.findAll(buildSpecification(BaseService.base(), LiveSource.class));

        return BeanMapper.mapList(liveSources, LiveSource.class, LiveSourceAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/liveSources/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<LiveSourceAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<LiveSource> orders = liveSourceService.findAll(buildSpecification(request, LiveSource.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<LiveSourceAdminDTO> dtos = BeanMapper.mapList(orders, LiveSource.class, LiveSourceAdminDTO.class);

        Page<LiveSourceAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询直播源表", null);
        
        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/liveSources/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public LiveSourceAdminDTO listOneLiveSource(@PathVariable("id") Long id) {

        assertAdmin();

        LiveSource LiveSource = liveSourceService.findOne(id);

        return BeanMapper.map(LiveSource, LiveSourceAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/liveSources", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createLiveSource(@RequestBody LiveSourceAdminDTO LiveSourceAdminDTO) {

        assertAdmin();

        LiveSource liveSource = BeanMapper.map(LiveSourceAdminDTO, LiveSource.class);

        liveSource.addTime = new Date();
        liveSourceService.create(liveSource);

        logService.log("添加直播源", "/liveSourceForm/" + liveSource.id);
    }

    @RequestMapping(value = "/api/admin/liveSources/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyLiveSource(@RequestBody LiveSourceAdminDTO LiveSourceAdminDTO) {

        assertAdmin();

        LiveSource liveSource = BeanMapper.map(LiveSourceAdminDTO, LiveSource.class);

        liveSource.addTime = new Date();
        liveSourceService.modify(liveSource);

        logService.log("修改直播源", "/liveSourceForm/" + liveSource.id);
    }

    @RequestMapping(value = "/api/admin/liveSources/{id}", method = RequestMethod.DELETE)
    public void deleteLiveSource(@PathVariable("id") Long id) {

        assertAdmin();

        liveSourceService.delete(id);

        logService.log("删除直播源", null);
    }

    /**
     * 切换活动直播源
     * @param id 要切换为活动直播源的直播源的ID
     * @param request
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/api/admin/liveSources/{id}/active", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public Page<LiveSourceAdminDTO> active(@PathVariable("id") Long id, HttpServletRequest request, Pageable pageable) {

        assertAdmin();
        
        liveSourceService.active(id);

        Page<LiveSource> orders = liveSourceService.findAll(buildSpecification(request, LiveSource.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<LiveSourceAdminDTO> dtos = BeanMapper.mapList(orders, LiveSource.class, LiveSourceAdminDTO.class);

        Page<LiveSourceAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("切换活动直播源", null);
        
        return dtoPage;
    }

}
