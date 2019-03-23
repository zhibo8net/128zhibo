package website2018.service.comment;


import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.BeanMapper;
import website2018.cache.CacheUtils;
import website2018.domain.Comment;
import website2018.dto.CommentDTO;
import website2018.dto.user.ReturnResponse;
import website2018.dto.user.UserDTO;
import website2018.repository.CommentDao;
import website2018.repository.UserDao;
import website2018.utils.MobileUtils;
import website2018.utils.StrUtils;
import website2018.utils.SysConstants;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2018/10/14.
 */
@Service
public class CommentService {

    private static Logger logger = LoggerFactory.getLogger(CommentService.class);
    @Autowired
    CommentDao commentDao;
    @Autowired
    UserDao userDao;

   public  ReturnResponse addComment(CommentDTO commentDTO,HttpServletRequest request){
        ReturnResponse returnResponse=new ReturnResponse();
        try {
            if(commentDTO==null){
                returnResponse.code="0001";
                returnResponse.message="请求参数不能为空";
                return   returnResponse;
            }
            if(commentDTO.comment==null){
                returnResponse.code="0002";
                returnResponse.message="评论内容不能为空";
                return   returnResponse;
            }
            if(commentDTO.comment.length()>250){
                returnResponse.code="0003";
                returnResponse.message="评论内容不能超过255个字符";
                return   returnResponse;
            }
            if(commentDTO.relId==null){
                returnResponse.code="0001";
                returnResponse.message="请求管理信息不能为空";
                return   returnResponse;
            }
            UserDTO userDTO= (UserDTO) request.getSession().getAttribute(SysConstants.USER_LOGIN_FLAG);

            if(userDTO==null){
                returnResponse.code="0002";
                returnResponse.message="用户未登录";
                return   returnResponse;
            }
            Comment comment=BeanMapper.map(commentDTO, Comment.class);
           comment.user=userDao.findByUserName(userDTO.userName);

            comment.userType= comment.userType==null?1:comment.userType;
            comment.type= comment.type==null?1:comment.type;
            Date d=new Date();
            comment.addTime=d;
            comment.updateTime=d;
            comment.comment= StrUtils.stringFilter(comment.comment);
            comment.comment= StrUtils.stringFilterScriptChar( comment.comment);

            List<String> sensitiveList= CacheUtils.getSensitiveList();
            for(String str:sensitiveList){
                comment.comment=comment.comment.replaceAll(str,"*");
            }
            commentDao.save(comment);

            returnResponse.code="0000";
            returnResponse.message="添加评论成功";

        }catch (Exception e){
            returnResponse.code="0007";
            returnResponse.message="系统异常稍后再试";
        }

      return   returnResponse;
    }

    public List<CommentDTO> findCommentDTOByRelId(Integer relId, Integer type,Integer userType,Integer pageNumber) {
        List<CommentDTO> commentDTOList = Lists.newArrayList();
        List<Comment> commentList=null;
        if(pageNumber==null||pageNumber==0){
            if(userType==null){
                commentList=commentDao.findByRelIdAndTypeOrderByAddTimeDesc(relId, type);
            }else{
                commentList=commentDao.findByRelIdAndTypeAndUserTypeOrderByAddTimeDesc(relId, type, userType);
            }

        }else{
            if(userType==null){
                commentList=commentDao.findByRelIdAndTypeOrderByAddTimeDesc(relId, type, new PageRequest(pageNumber,10) );
            }else{
                commentList=commentDao.findByRelIdAndTypeAndUserTypeOrderByAddTimeDesc(relId, type,userType);
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


        try {
            for(Comment comment:commentList){
                CommentDTO commentDTO = BeanMapper.map(comment, CommentDTO.class);
                commentDTO.addTimeStr=sdf.format(comment.addTime==null?new Date():comment.addTime);
                commentDTO.updateTimeStr=sdf.format(comment.updateTime == null ? new Date() : comment.updateTime);
                commentDTO.userNickName=comment.user == null ? "" : comment.user.userNickName;
                commentDTO.userName= StringUtils.isNotBlank(commentDTO.userNickName)?commentDTO.userNickName: MobileUtils.hiddenMobile(comment.user == null ? "" : comment.user.userName);
                commentDTO.userLink=comment.user == null ? "" : comment.user.userLink;
                commentDTO.pageNumber=pageNumber;
                commentDTOList.add(commentDTO);
            }

        } catch (Exception e) {
            logger.error("查询评论异常", e);
        }
        return commentDTOList;
    }
}

