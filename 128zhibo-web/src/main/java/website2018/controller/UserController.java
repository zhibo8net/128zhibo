package website2018.controller;


import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.web.MediaTypes;
import website2018.base.BaseEndPoint;
import website2018.domain.User;
import website2018.dto.user.ReturnResponse;
import website2018.dto.user.UserDTO;
import website2018.service.user.UserLoginService;
import website2018.utils.MobileUtils;
import website2018.utils.SysConstants;
import website2018.utils.UploadUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@RestController
public class UserController extends BaseEndPoint {
    
    @Autowired
    UserLoginService userLoginService;
    
    @RequestMapping(value = "/api/user/login", produces = MediaTypes.JSON_UTF_8)
    public ReturnResponse userLogin(@RequestBody(required=false) UserDTO userDTO,HttpServletRequest request) {



        return userLoginService.checkUserLogin(request,userDTO);
    }
    @RequestMapping(value = "/api/user/register", produces = MediaTypes.JSON_UTF_8)
    public ReturnResponse userRegister(@RequestBody(required=false) UserDTO userDTO,HttpServletRequest request) {


        return userLoginService.doUserRegister(request, userDTO);
    }

    @RequestMapping(value = "/checkUser",produces = MediaTypes.JSON_UTF_8)
    public ReturnResponse checkUser(HttpServletRequest request) {
        UserDTO userDTO= (UserDTO) request.getSession().getAttribute(SysConstants.USER_LOGIN_FLAG);
        ReturnResponse response=new ReturnResponse();

        if(userDTO==null){
            response.code="0001";
            response.message="用户未登录";
            return response;
        }
        User user=userLoginService.getUser(userDTO);
        if(user==null){
            response.code="0002";
            response.message="用户不存在";
            return response;
        }
        response.code="0000";
        response.message="用户已登录";
         userDTO = BeanMapper.map(user, UserDTO.class);
        userDTO.password="";
        userDTO.rePassword="";
        userDTO.userLink=user.userLink;

        response.data=userDTO;
        return response;
    }


    @RequestMapping(value = "/api/user/updateUser",produces = MediaTypes.JSON_UTF_8)
    public ReturnResponse updateUser(HttpServletRequest request,@RequestBody(required=false) UserDTO userQaramDTO) {
        UserDTO userDTO= (UserDTO) request.getSession().getAttribute(SysConstants.USER_LOGIN_FLAG);
        ReturnResponse response=new ReturnResponse();

        if(userDTO==null){
            response.code="0001";
            response.message="用户未登录";
            return response;
        }

        return userLoginService.doUpdateUser(userDTO,userQaramDTO);
    }

    //返回例如http://127.0.0.1/image/2018/2/2/aakjfkasjfk.jpeg
    @RequestMapping(value = "/api/user/file")
    public ReturnResponse upload(@RequestParam("image") MultipartFile file,HttpServletRequest request) throws Exception {

        UserDTO userDTO= (UserDTO) request.getSession().getAttribute(SysConstants.USER_LOGIN_FLAG);
        ReturnResponse response=new ReturnResponse();

        if(userDTO==null){
            response.code="0001";
            response.message="用户未登录";
            return response;
        }
        String ext = org.apache.commons.lang3.StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

        String fileName = UUID.randomUUID().toString() + "." + ext;

        String nameWithTimePrefix = UploadUtils.nameWithTimePrefix(fileName);

        String fullPath = uploadPath + nameWithTimePrefix;

        File diskFile = new File(fullPath);

        Files.createParentDirs(diskFile);

        file.transferTo(diskFile);

        userDTO.userLink= webImageBase + nameWithTimePrefix;
        response.code="0000";
        response.message="修改成功";
        response.data=userDTO;
        userLoginService.updateUser(userDTO);
        return response;
    }
    }
