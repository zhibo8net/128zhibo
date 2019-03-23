package website2018.service.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.BeanMapper;
import website2018.cache.CacheUtils;
import website2018.domain.Ended;
import website2018.domain.User;
import website2018.dto.user.ReturnResponse;
import website2018.dto.user.UserDTO;
import website2018.repository.UserDao;
import website2018.utils.MD5Util;
import website2018.utils.MobileUtils;
import website2018.utils.SysConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/9/24.
 */
@Service
public class UserLoginService {


    @Autowired
    UserDao userDao;

    public User findByUserName(String userName){
     User user=   userDao.findByUserName(userName);
        return user;
    }

    public ReturnResponse checkUserLogin(HttpServletRequest request,UserDTO userDTO){
        ReturnResponse response=new ReturnResponse();

        if(userDTO==null|| StringUtils.isEmpty(userDTO.userName)||StringUtils.isEmpty(userDTO.password)){
            response.code="0002";
            response.message="请求参数错误";
            return response;
        }
        if(!MobileUtils.isMobileNO(userDTO.userName)){
            response.code="0009";
            response.message="请输入正确的手机号";
            return response;
        }
        User user=   userDao.findByUserName(userDTO.userName);
        if(user==null){
            User  u = BeanMapper.map(userDTO, User.class);
            u.userNickName=MobileUtils.hiddenMobile(userDTO.userName);
            u.mobile=userDTO.userName;
            u.password=MD5Util.MD5(userDTO.password);
            u.userLink="/image_1/defalutUser.jpg";
            Date d=new Date();
            u.addTime=d;
            u.updateTime=d;
            userDao.save(u);
        }else{
            String password= MD5Util.MD5(userDTO.password);
            if(!password.equals(user.password)){
                response.code="0003";
                response.message="用户名或者密码错误";
                return response;
            }
        }
        request.getSession().setAttribute(SysConstants.USER_LOGIN_FLAG,userDTO);
        response.code="0000";
        response.message="登录成功";
        return response;
    }

    public ReturnResponse doUserRegister(HttpServletRequest request,UserDTO userDTO){
        ReturnResponse response=new ReturnResponse();

        if(userDTO==null|| StringUtils.isEmpty(userDTO.userName)||StringUtils.isEmpty(userDTO.password)){
            response.code="0002";
            response.message="请求参数错误";
            return response;
        }
        if(!MobileUtils.isMobileNO(userDTO.userName)){
            response.code="0009";
            response.message="请输入正确的手机号";
            return response;
        }
        if(userDTO.password.equals(userDTO.rePassword)){
            response.code="0005";
            response.message="2次输入密码不一致";
            return response;
        }
        User user=   userDao.findByUserName(userDTO.userName);
        if(user==null){
            User  u = BeanMapper.map(userDTO, User.class);
            u.password=MD5Util.MD5(userDTO.password);
            u.userNickName=MobileUtils.hiddenMobile(userDTO.userName);
            u.mobile=userDTO.userName;
            u.userLink="/image_1/defalutUser.jpg";
            Date d=new Date();
            u.addTime=d;
            u.updateTime=d;
            userDao.save(u);
        }else{
            response.code="0004";
            response.message="用户名已存在";
            return response;
        }
        request.getSession().setAttribute(SysConstants.USER_LOGIN_FLAG,userDTO);
        response.code="0000";
        response.message="注册成功";
        return response;
    }

    public User getUser(UserDTO userDTO){
        User user =(User) CacheUtils.UserCache.getIfPresent("ZHIBO8_INDEX_USER_"+userDTO.id);
        if(user==null){
            user=userDao.findByUserName(userDTO.userName);
            CacheUtils.UserCache.put("ZHIBO8_INDEX_USER_"+userDTO.id,user);
        }

       return user;

    }
    public void updateUser(UserDTO userDTO){
        User user=   userDao.findByUserName(userDTO.userName);
        if(user==null){
            return ;
        }
        user.userLink=userDTO.userLink;
        userDao.save(user);
    }
    public ReturnResponse doUpdateUser(UserDTO loginUserDTO,UserDTO userDTO) {
        ReturnResponse response = new ReturnResponse();
        User user=   userDao.findByUserName(loginUserDTO.userName);
        if(user==null){
            response.code="0002";
            response.message="用户不存在";
            return response;
        }
        user.userNickName=userDTO.userNickName;
        user.mobile=userDTO.mobile;
        user.userEmail=userDTO.userEmail;
        user.updateTime=new Date();

        userDao.save(user);
        response.code="0000";
        response.message="修改成功";
        return response;
    }
    }
