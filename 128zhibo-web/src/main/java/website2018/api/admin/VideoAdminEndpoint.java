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
import website2018.domain.Video;
import website2018.dto.admin.VideoAdminDTO;
import website2018.service.admin.VideoService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class VideoAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(VideoAdminEndpoint.class);

    @Autowired
    private VideoService videoService;

    @RequestMapping(value = "/api/admin/videos", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<VideoAdminDTO> listAllVideo() {

        assertAdmin();

        List<Video> videos = videoService.findAll(buildSpecification(BaseService.base(), Video.class));

        return BeanMapper.mapList(videos, Video.class, VideoAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/videos/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<VideoAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertAdmin();

        Page<Video> orders = videoService.findAll(buildSpecification(request, Video.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<VideoAdminDTO> dtos = BeanMapper.mapList(orders, Video.class, VideoAdminDTO.class);

        Page<VideoAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());
        for(VideoAdminDTO dto : dtoPage.getContent()) {
            if(StringUtils.isNotBlank(dto.image)) {
                dto.image = this.webImageBase + dto.image;
            }
        }

        logService.log("查询视频表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/videos/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public VideoAdminDTO listOneVideo(@PathVariable("id") Long id) {

        assertAdmin();

        Video video = videoService.findOne(id);

        VideoAdminDTO dto = BeanMapper.map(video, VideoAdminDTO.class);
        
        if(StringUtils.isNotBlank(dto.image)) {
            dto.image = this.webImageBase + dto.image;
        }
        
        return dto;
    }

    @RequestMapping(value = "/api/admin/videos", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createVideo(@RequestBody VideoAdminDTO videoAdminDTO) {

        assertAdmin();

        Video video = BeanMapper.map(videoAdminDTO, Video.class);

        video.addTime = new Date();
        if(video.image != null) {
            video.image = video.image.replace(this.webImageBase, "");
        }
        videoService.create(video);
        
        logService.log("添加视频", "/videoForm/" + video.id);
    }

    @RequestMapping(value = "/api/admin/videos/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyVideo(@RequestBody VideoAdminDTO videoAdminDTO) {

        assertAdmin();

        Video video = BeanMapper.map(videoAdminDTO, Video.class);

        video.addTime = new Date();
        if(video.image != null) {
            video.image = video.image.replace(this.webImageBase, "");
        }
        videoService.modify(video);

        logService.log("修改视频", "/videoForm/" + video.id);
    }

    @RequestMapping(value = "/api/admin/videos/{id}", method = RequestMethod.DELETE)
    public void deleteVideo(@PathVariable("id") Long id) {

        assertAdmin();

        videoService.delete(id);

        logService.log("删除视频", null);
    }

}
