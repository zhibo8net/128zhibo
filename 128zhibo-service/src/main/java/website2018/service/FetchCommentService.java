package website2018.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website2018.cache.CacheUtils;
import website2018.domain.User;
import website2018.repository.UserDao;

import java.util.List;


/**
 * Created by Administrator on 2018/10/14.
 */
@Service
public class FetchCommentService {

    @Autowired
    UserDao userDao;

    public User getUserByUserName(String userName){

        List<User> userList=(List<User>)CacheUtils.fecthUserCache.getIfPresent("ZHIBO_FETCH_USER");
        if(userList==null||userList.size()==0){
            userList=    userDao.findByUserNameLessThan("11200000000");
            CacheUtils.fecthUserCache.put("ZHIBO_FETCH_USER",userList);
        }

        for(User user:userList){
            if(userName.equals(user.userName)){
                return user;
            }
        }
        return null;
    }


    public User getUserRandom(){

        List<User> userList=(List<User>)CacheUtils.fecthUserCache.getIfPresent("ZHIBO_FETCH_USER");
        if(userList==null||userList.size()==0){
            userList=    userDao.findByUserNameLessThan("11200000000");
            CacheUtils.fecthUserCache.put("ZHIBO_FETCH_USER",userList);
        }

        int num = (int) (Math.random()*userList.size());
        if(num>=userList.size()){
            return null;
        }
        return userList.get(num);
    }
}

