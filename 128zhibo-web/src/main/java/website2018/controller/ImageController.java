package website2018.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.google.common.collect.Maps;

import website2018.base.BaseEndPoint;
import website2018.domain.Image;
import website2018.domain.ImageBag;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.ImageBagDao;

@Controller
public class ImageController extends BaseEndPoint {
    
    @Autowired ImageBagDao imageBagDao;
    @RequestMapping(value = "/download")
    public String download(Model model) {
        return "download";
    }
    @RequestMapping(value = "/image_1", method = RequestMethod.GET)
    public String image(Model model) {

        List<ImageBag> footballImages = imageBagDao.findTop8ByProjectOrderByIdDesc("足球");
        model.addAttribute("footballImages", footballImages);

        List<ImageBag> basketballImages = imageBagDao.findTop8ByProjectOrderByIdDesc("篮球");
        model.addAttribute("basketballImages", basketballImages);

        model.addAttribute("videos", videoQueryer.findByProjectGameTypeCount(null, null, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT));
        model.addAttribute("luxiangs", videoQueryer.findByProjectGameTypeCount(null, null, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true));

        model.addAttribute("menu", "image");
        return "imageIndex";
    }

    @RequestMapping(value = "/projectImage_1", method = RequestMethod.GET)
    public String projectImage(String project,@RequestParam(defaultValue="0") Integer pageNumber, Model model) {

        if(! (projectWhiteList.contains(project))) {
            return "redirect:http://www.zhibo8.net/";
        }

        model.addAttribute("project", project);
        model.addAttribute("projectEnglish", project.equals("足球") ? "football" : "basketball");
        
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("project", project);
        Page page = imageBagDao.findAll(buildSpecification(requestMap, ImageBag.class), new PageRequest(pageNumber, 12, getSort("id", "DIRE")));
        model.addAttribute("page", page);
        String search = BaseEndPoint.encodeParameterStringWithPrefix(requestMap, "");
        model.addAttribute("pageUrl", "?" + search);

        model.addAttribute("videos", videoQueryer.findByProjectGameTypeCount(project, null, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT));
        model.addAttribute("luxiangs", videoQueryer.findByProjectGameTypeCount(project, null, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true));

        model.addAttribute("menu", "image");
        return "imageList";
    }

    @RequestMapping(value = "/viewImage_1/{bagId}/{index}", method = RequestMethod.GET)
    public String viewImage(@PathVariable Long bagId, @PathVariable Integer index, Model model) {

        ImageBag imageBag = imageBagDao.findOne(bagId);
        if(imageBag == null) {
            return "redirect:http://www.zhibo8.net/";
        }
        imageBag.readCount=imageBag.readCount==null?1:imageBag.readCount+1;
        imageBagDao.save(imageBag);

        Image image = imageBag.images.get(index - 1);//1则取0
        model.addAttribute("imageBag", imageBag);
        model.addAttribute("image", image);
        model.addAttribute("index", index);
        model.addAttribute("project", imageBag.project);

        model.addAttribute("videos", videoQueryer.findByProjectGameTypeCount(imageBag.project, null, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT));
        model.addAttribute("luxiangs", videoQueryer.findByProjectGameTypeCount(imageBag.project, null, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true));

        model.addAttribute("menu", "image");
        return "imageInner";
    }

    @RequestMapping(value = "/imagefile/**", method = RequestMethod.GET)
    @ResponseBody
    public byte[] imagefile(HttpServletRequest request) throws IOException{
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        path = StringUtils.substringAfter(path, "/imagefile/");
        String diskPath = uploadPath + path;
        byte[] result = FileUtils.readFileToByteArray(new File(diskPath));
        return result;
    }

}
