package website2018.api.admin;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.web.MediaTypes;

import website2018.base.BaseEndPoint;
import website2018.base.BaseService;
import website2018.domain.News;
import website2018.dto.admin.NewsAdminDTO;
import website2018.service.admin.NewsService;
import website2018.utils.DateUtils;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class NewsAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(NewsAdminEndpoint.class);

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/api/admin/newss", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<NewsAdminDTO> listAllNews() {

        assertAdmin();

        List<News> newss = newsService.findAll(buildSpecification(BaseService.base(), News.class));

        return BeanMapper.mapList(newss, News.class, NewsAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/newss/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<NewsAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<News> orders = newsService.findAll(buildSpecification(request, News.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<NewsAdminDTO> dtos = BeanMapper.mapList(orders, News.class, NewsAdminDTO.class);

        Page<NewsAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());
        for(NewsAdminDTO dto : dtoPage.getContent()) {
            if(StringUtils.isNotBlank(dto.image)) {
                dto.image = this.webImageBase + dto.image;
            }
            Date d=dto.updateTime==null?dto.addTime:dto.updateTime;
            dto.addTimeStr= DateUtils.getDateStr(d,"yyyy-MM-dd HH:mm:ss");
        }

        logService.log("查询新闻表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/newss/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public NewsAdminDTO listOneNews(@PathVariable("id") Long id) {

        assertAdmin();

        News news = newsService.findOne(id);

        NewsAdminDTO dto = BeanMapper.map(news, NewsAdminDTO.class);
        
        if(StringUtils.isNotBlank(dto.image)) {
            dto.image = this.webImageBase + dto.image;
        }
        
        return dto;
    }

    @RequestMapping(value = "/api/admin/newss", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createNews(@RequestBody NewsAdminDTO newsAdminDTO) {

        assertAdmin();

        News news = BeanMapper.map(newsAdminDTO, News.class);
        news.updateTime = new Date();
        news.addTime = new Date();
        if(news.image != null) {
            news.image = news.image.replace(this.webImageBase, "");
        }else{
            news.image ="";
        }


        newsService.create(news);
        
        logService.log("添加新闻", "/newsForm/" + news.id);
    }

    @RequestMapping(value = "/api/admin/newss/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyNews(@RequestBody NewsAdminDTO newsAdminDTO) {

        assertAdmin();

        News news = BeanMapper.map(newsAdminDTO, News.class);

        news.addTime = new Date();
        if(news.image != null) {
            news.image = news.image.replace(this.webImageBase, "");
        }else{
            news.image ="";
        }
        news.updateTime = new Date();

        newsService.modify(news);

        logService.log("修改新闻", "/newsForm/" + news.id);
    }

    @RequestMapping(value = "/api/admin/newss/{id}", method = RequestMethod.DELETE)
    public void deleteNews(@PathVariable("id") Long id) {

        assertAdmin();

        newsService.delete(id);

        logService.log("删除新闻", null);
    }

}
