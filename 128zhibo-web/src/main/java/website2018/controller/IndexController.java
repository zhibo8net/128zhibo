package website2018.controller;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import website2018.base.BaseEndPoint;
import website2018.repository.MatchDao;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class IndexController extends BaseEndPoint {


    @Autowired
    MatchDao matchDao;
    @RequestMapping(value = "/")
    public String indexDefault( Model model) {
        return "forward:index.html";
    }

    @RequestMapping(value = "/live/{id}", method = RequestMethod.GET)
    public String liveIndex( @PathVariable Long id, Model model) {
        return "redirect:"+localhost+"/#/live?matchID="+id;
    }
    @RequestMapping(value = "/mlive/{id}", method = RequestMethod.GET)
    public String liveNobileIndex( @PathVariable Long id, Model model) {
        return "redirect:"+localhost+"/#/live?matchID="+id;
    }
    @RequestMapping(value = "/imagefile/**", method = RequestMethod.GET)
    @ResponseBody
    public byte[] imagefile(HttpServletRequest request) throws IOException {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        path = StringUtils.substringAfter(path, "/imagefile/");
        String diskPath = uploadPath + path;
        byte[] result = FileUtils.readFileToByteArray(new File(diskPath));
        return result;
    }

}
