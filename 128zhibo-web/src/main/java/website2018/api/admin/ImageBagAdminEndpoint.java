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
import website2018.domain.ImageBag;
import website2018.dto.admin.ImageAdminDTO;
import website2018.dto.admin.ImageBagAdminDTO;
import website2018.service.admin.ImageBagService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class ImageBagAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(ImageBagAdminEndpoint.class);

    @Autowired
    private ImageBagService imageBagService;

    @RequestMapping(value = "/api/admin/imageBags", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<ImageBagAdminDTO> listAllImageBag() {

        assertAdmin();

        List<ImageBag> imageBags = imageBagService.findAll(buildSpecification(BaseService.base(), ImageBag.class));

        return BeanMapper.mapList(imageBags, ImageBag.class, ImageBagAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/imageBags/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<ImageBagAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<ImageBag> orders = imageBagService.findAll(buildSpecification(request, ImageBag.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<ImageBagAdminDTO> dtos = BeanMapper.mapList(orders, ImageBag.class, ImageBagAdminDTO.class);

        Page<ImageBagAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询组图表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/imageBags/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ImageBagAdminDTO listOneImageBag(@PathVariable("id") Long id) {

        assertAdmin();

        ImageBag ImageBag = imageBagService.findOne(id);

        ImageBagAdminDTO dto = BeanMapper.map(ImageBag, ImageBagAdminDTO.class);
        for(ImageAdminDTO i : dto.images) {
            i.name = webImageBase + i.name;
        }
        
        return dto;
    }

    @RequestMapping(value = "/api/admin/imageBags", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createImageBag(@RequestBody ImageBagAdminDTO imageBagAdminDTO) {

        assertAdmin();

        ImageBag imageBag = BeanMapper.map(imageBagAdminDTO, ImageBag.class);

        imageBag.addTime = new Date();
        imageBagService.create(imageBag);
        
        logService.log("添加组图", "/imageBagForm/" + imageBag.id);
    }

    @RequestMapping(value = "/api/admin/imageBags/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyImageBag(@RequestBody ImageBagAdminDTO imageBagAdminDTO) {

        assertAdmin();

        ImageBag imageBag = BeanMapper.map(imageBagAdminDTO, ImageBag.class);

        imageBag.addTime = new Date();
        imageBagService.modify(imageBag);

        logService.log("修改组图", "/imageBagForm/" + imageBag.id);
    }

    @RequestMapping(value = "/api/admin/imageBags/{id}", method = RequestMethod.DELETE)
    public void deleteImageBag(@PathVariable("id") Long id) {

        assertAdmin();

        imageBagService.delete(id);

        logService.log("删除组图", null);
    }

}
