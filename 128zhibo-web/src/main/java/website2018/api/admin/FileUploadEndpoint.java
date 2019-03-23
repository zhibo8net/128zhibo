package website2018.api.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;

import website2018.base.BaseEndPoint;
import website2018.utils.UploadUtils;

@Controller
public class FileUploadEndpoint extends BaseEndPoint {

    //返回例如http://127.0.0.1/image/2018/2/2/aakjfkasjfk.jpeg
    @RequestMapping(value = "/api/admin/file", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("image") MultipartFile file) throws Exception {
        assertAdmin();

        String ext = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

        String fileName = UUID.randomUUID().toString() + "." + ext;

        String nameWithTimePrefix = UploadUtils.nameWithTimePrefix(fileName);
        
        String fullPath = uploadPath + nameWithTimePrefix;
        
        File diskFile = new File(fullPath);
        
        Files.createParentDirs(diskFile);

        file.transferTo(diskFile);

        return webImageBase + nameWithTimePrefix;
    }

    @RequestMapping(value = "/admin_1/php/upload_json", method = RequestMethod.POST)
    @ResponseBody
    public ReturnImg upload_json(@RequestParam("imgFile") MultipartFile file) throws Exception {
      List<String> list= new ArrayList<>();
        list.add("png");
        list.add("jpeg");
        list.add("bmp");
        list.add("jpg");
        list.add("gif");
        list.add("png");
        ReturnImg returnImg=new ReturnImg();

        String ext = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        if(!list.contains(ext)){

            returnImg.message="图片格式不正确";
            returnImg.error=1;
            return returnImg;
        }

        String fileName = UUID.randomUUID().toString() + "." + ext;

        String nameWithTimePrefix = UploadUtils.nameWithTimePrefix(fileName);

        String fullPath = uploadPath + nameWithTimePrefix;

        File diskFile = new File(fullPath);

        Files.createParentDirs(diskFile);

        file.transferTo(diskFile);

        returnImg.url=(webImageBase + nameWithTimePrefix);
        returnImg.message="上传成功";
        returnImg.error=0;
        return returnImg;
    }

    class ReturnImg{
        public String url;

        public String message;

        public int error;
    }
}
