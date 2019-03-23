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
import website2018.domain.PageAd;
import website2018.dto.admin.PageAdAdminDTO;
import website2018.service.admin.PageAdService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class PageAdAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(PageAdAdminEndpoint.class);

    @Autowired
    private PageAdService pageAdService;

    @RequestMapping(value = "/api/admin/pageAds", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<PageAdAdminDTO> listAllPageAd() {

        assertAdmin();

        List<PageAd> pageAds = pageAdService.findAll(buildSpecification(BaseService.base(), PageAd.class));

        return BeanMapper.mapList(pageAds, PageAd.class, PageAdAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/pageAds/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<PageAdAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<PageAd> orders = pageAdService.findAll(buildSpecification(request, PageAd.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "asc")));

        List<PageAdAdminDTO> dtos = BeanMapper.mapList(orders, PageAd.class, PageAdAdminDTO.class);

        Page<PageAdAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询页面广告表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/pageAds/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public PageAdAdminDTO listOnePageAd(@PathVariable("id") Long id) {

        assertAdmin();

        PageAd pageAd = pageAdService.findOne(id);

        return BeanMapper.map(pageAd, PageAdAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/pageAds", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createPageAd(@RequestBody PageAdAdminDTO pageAdAdminDTO) {

        assertAdmin();

        PageAd pageAd = BeanMapper.map(pageAdAdminDTO, PageAd.class);

        pageAd.addTime = new Date();
        pageAdService.create(pageAd);
        
        logService.log("添加页面广告", "/pageAdForm/" + pageAd.id);
    }

    @RequestMapping(value = "/api/admin/pageAds/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyPageAd(@RequestBody PageAdAdminDTO pageAdAdminDTO) {

        assertAdmin();

        PageAd pageAd = BeanMapper.map(pageAdAdminDTO, PageAd.class);

        pageAd.addTime = new Date();
        pageAdService.modify(pageAd);

        logService.log("修改页面广告", "/pageAdForm/" + pageAd.id);
    }

    @RequestMapping(value = "/api/admin/pageAds/{id}", method = RequestMethod.DELETE)
    public void deletePageAd(@PathVariable("id") Long id) {

        assertAdmin();

        pageAdService.delete(id);

        logService.log("删除页面广告", null);
    }

}
