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
import website2018.domain.FriendLink;
import website2018.dto.admin.FriendLinkAdminDTO;
import website2018.service.admin.FriendLinkService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class FriendLinkAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(FriendLinkAdminEndpoint.class);

    @Autowired
    private FriendLinkService friendLinkService;

    @RequestMapping(value = "/api/admin/friendLinks", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<FriendLinkAdminDTO> listAllFriendLink() {

        assertAdmin();

        List<FriendLink> friendLinks = friendLinkService.findAll(buildSpecification(BaseService.base(), FriendLink.class));

        return BeanMapper.mapList(friendLinks, FriendLink.class, FriendLinkAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/friendLinks/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<FriendLinkAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<FriendLink> orders = friendLinkService.findAll(buildSpecification(request, FriendLink.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<FriendLinkAdminDTO> dtos = BeanMapper.mapList(orders, FriendLink.class, FriendLinkAdminDTO.class);

        Page<FriendLinkAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询友情链接表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/friendLinks/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public FriendLinkAdminDTO listOneFriendLink(@PathVariable("id") Long id) {

        assertAdmin();

        FriendLink FriendLink = friendLinkService.findOne(id);

        return BeanMapper.map(FriendLink, FriendLinkAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/friendLinks", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createFriendLink(@RequestBody FriendLinkAdminDTO friendLinkAdminDTO) {

        assertAdmin();

        FriendLink friendLink = BeanMapper.map(friendLinkAdminDTO, FriendLink.class);

        friendLink.addTime = new Date();
        friendLinkService.create(friendLink);
        
        logService.log("添加友情链接", "/friendLinkForm/" + friendLink.id);
    }

    @RequestMapping(value = "/api/admin/friendLinks/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyFriendLink(@RequestBody FriendLinkAdminDTO friendLinkAdminDTO) {

        assertAdmin();

        FriendLink friendLink = BeanMapper.map(friendLinkAdminDTO, FriendLink.class);

        friendLink.addTime = new Date();
        friendLinkService.modify(friendLink);

        logService.log("修改友情链接", "/friendLinkForm/" + friendLink.id);
    }

    @RequestMapping(value = "/api/admin/friendLinks/{id}", method = RequestMethod.DELETE)
    public void deleteFriendLink(@PathVariable("id") Long id) {

        assertAdmin();

        friendLinkService.delete(id);

        logService.log("删除友情链接", null);
    }

}
