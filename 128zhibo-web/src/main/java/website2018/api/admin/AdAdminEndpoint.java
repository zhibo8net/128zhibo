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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.web.MediaTypes;

import website2018.base.BaseEndPoint;
import website2018.base.BaseService;
import website2018.domain.Account;
import website2018.domain.Ad;
import website2018.dto.admin.AdAdminDTO;
import website2018.service.admin.AdService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class AdAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(AdAdminEndpoint.class);

    @Autowired
    private AdService adService;

    @RequestMapping(value = "/api/admin/ads", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<AdAdminDTO> listAllAd() {

        assertAdmin();

        List<Ad> ads = adService.findAll(buildSpecification(BaseService.base(), Ad.class));

        return BeanMapper.mapList(ads, Ad.class, AdAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/ads/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<AdAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<Ad> orders = adService.findAll(buildSpecification(request, Ad.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<AdAdminDTO> dtos = BeanMapper.mapList(orders, Ad.class, AdAdminDTO.class);

        Page<AdAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询比赛广告表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/ads/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public AdAdminDTO listOneAd(@PathVariable("id") Long id) {

        assertAdmin();
        
        Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(account.name);

        Ad Ad = adService.findOne(id);

        AdAdminDTO dto = BeanMapper.map(Ad, AdAdminDTO.class);
        if(dto.endDate != null) {
            dto.endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(dto.endDate);
            dto.endTimeStr = new SimpleDateFormat("HH:mm").format(dto.endDate);
        }
        
        return dto;
    }

    @RequestMapping(value = "/api/admin/ads", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createAd(@RequestBody AdAdminDTO adAdminDTO) throws Exception {

        assertAdmin();

        Ad ad = BeanMapper.map(adAdminDTO, Ad.class);
        if(StringUtils.isNotBlank(adAdminDTO.endDateStr) && StringUtils.isNotBlank(adAdminDTO.endTimeStr)) {
            Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(adAdminDTO.endDateStr + " " + adAdminDTO.endTimeStr);
            ad.endDate = endTime;
        }

        ad.addTime = new Date();
        adService.create(ad);
        
        logService.log("添加比赛广告", "/adForm/" + ad.id);
    }

    @RequestMapping(value = "/api/admin/ads/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyAd(@RequestBody AdAdminDTO adAdminDTO) throws Exception {

        assertAdmin();

        Ad ad = BeanMapper.map(adAdminDTO, Ad.class);
        if(StringUtils.isNotBlank(adAdminDTO.endDateStr) && StringUtils.isNotBlank(adAdminDTO.endTimeStr)) {
            Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(adAdminDTO.endDateStr + " " + adAdminDTO.endTimeStr);
            ad.endDate = endTime;
        }

        ad.addTime = new Date();
        adService.modify(ad);

        logService.log("修改比赛广告", "/adForm/" + ad.id);
    }

    @RequestMapping(value = "/api/admin/ads/{id}", method = RequestMethod.DELETE)
    public void deleteAd(@PathVariable("id") Long id) {

        assertAdmin();

        adService.delete(id);

        logService.log("删除比赛广告", null);
    }

}
