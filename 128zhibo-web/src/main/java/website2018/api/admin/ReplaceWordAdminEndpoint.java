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
import website2018.domain.ReplaceWord;
import website2018.dto.admin.ReplaceWordAdminDTO;
import website2018.service.admin.ReplaceWordService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class ReplaceWordAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(ReplaceWordAdminEndpoint.class);

    @Autowired
    private ReplaceWordService replaceWordService;

    @RequestMapping(value = "/api/admin/replaceWords", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<ReplaceWordAdminDTO> listAllReplaceWord() {

        assertAdmin();

        List<ReplaceWord> replaceWords = replaceWordService.findAll(buildSpecification(BaseService.base(), ReplaceWord.class));

        return BeanMapper.mapList(replaceWords, ReplaceWord.class, ReplaceWordAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/replaceWords/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<ReplaceWordAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<ReplaceWord> orders = replaceWordService.findAll(buildSpecification(request, ReplaceWord.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<ReplaceWordAdminDTO> dtos = BeanMapper.mapList(orders, ReplaceWord.class, ReplaceWordAdminDTO.class);

        Page<ReplaceWordAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询替换词表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/replaceWords/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ReplaceWordAdminDTO listOneReplaceWord(@PathVariable("id") Long id) {

        assertAdmin();

        ReplaceWord ReplaceWord = replaceWordService.findOne(id);

        return BeanMapper.map(ReplaceWord, ReplaceWordAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/replaceWords", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createReplaceWord(@RequestBody ReplaceWordAdminDTO replaceWordAdminDTO) {

        assertAdmin();

        ReplaceWord replaceWord = BeanMapper.map(replaceWordAdminDTO, ReplaceWord.class);

        replaceWord.addTime = new Date();
        replaceWordService.create(replaceWord);
        
        logService.log("添加替换词", "/replaceWordForm/" + replaceWord.id);
    }

    @RequestMapping(value = "/api/admin/replaceWords/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyReplaceWord(@RequestBody ReplaceWordAdminDTO replaceWordAdminDTO) {

        assertAdmin();

        ReplaceWord replaceWord = BeanMapper.map(replaceWordAdminDTO, ReplaceWord.class);

        replaceWord.addTime = new Date();
        replaceWordService.modify(replaceWord);

        logService.log("修改替换词", "/replaceWordForm/" + replaceWord.id);
    }

    @RequestMapping(value = "/api/admin/replaceWords/{id}", method = RequestMethod.DELETE)
    public void deleteReplaceWord(@PathVariable("id") Long id) {

        assertAdmin();

        replaceWordService.delete(id);

        logService.log("删除替换词", null);
    }

}
