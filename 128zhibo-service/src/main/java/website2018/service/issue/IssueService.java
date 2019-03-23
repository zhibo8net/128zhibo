package website2018.service.issue;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.BeanMapper;
import website2018.Enum.IssueCheck;
import website2018.Enum.IssueStatus;
import website2018.Enum.IssueUserStatus;
import website2018.domain.*;
import website2018.dto.CommentDTO;
import website2018.dto.user.ReturnResponse;
import website2018.dto.user.UserDTO;
import website2018.dto.web.*;
import website2018.repository.IssueDao;
import website2018.repository.IssueUserDao;
import website2018.repository.UserDao;
import website2018.utils.DateUtils;
import website2018.utils.MobileUtils;
import website2018.utils.SysConstants;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2019/3/2.
 */
@Service
public class IssueService {

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private IssueUserDao issueUserDao;

    @Autowired
    private UserDao userDao;
    //查询竞猜公告
    public IssueWebDTO queryLastJingcainotice(){
        Issue issue = issueDao.findTop1ByStatusOrderByIdDesc(IssueStatus.PIE_AWARD.getCode());
        if (issue == null) {
            return null;
        }
        issue.issueQuestionList.clear();
        List<IssueUser> temp=issue.issueUserList;
        List<IssueUser> issueUserList=Lists.newArrayList();
        for(IssueUser issueUser:temp){
            if(StringUtils.equals(issueUser.status,IssueUserStatus.AWARD.getCode())){
                issueUser.user=null;
                issueUser.userMobile= MobileUtils.hiddenMobile(issueUser.userMobile);
                issueUserList.add(issueUser);
            }
        }

        Collections.sort(issueUserList, new Comparator<IssueUser>() {
            public int compare(IssueUser arg0, IssueUser arg1) {
                BigDecimal data1 = new BigDecimal(arg0.answerRate);
                BigDecimal data2 = new BigDecimal(arg1.answerRate);

                return data2.compareTo(data1);
            }
        });
        issue.issueUserList=issueUserList;
        IssueWebDTO issueWebDTO = BeanMapper.map(issue, IssueWebDTO.class);


        return issueWebDTO;
    }
    public IssueWebDTO queryLastIssue(HttpServletRequest request) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -10);
        Issue issue = issueDao.findTop1ByAddTimeGreaterThanOrderByIdDesc(calendar.getTime());

        if (issue == null) {
            return null;
        }
        issue.issueUserList.clear();
        List<IssueQuestion> tempList = Lists.newArrayList();
        List<IssueQuestion> issueQuestionList = issue.issueQuestionList;
        for (IssueQuestion issueQuestion : issueQuestionList) {
            if (StringUtils.equals(issueQuestion.issueChecked, IssueCheck.CHECKED.getCode())) {
                tempList.add(issueQuestion);
            }
        }

        issue.issueQuestionList = tempList;
        IssueWebDTO issueWebDTO = BeanMapper.map(issue, IssueWebDTO.class);
        issueWebDTO.playDateStr= DateUtils.getDateStr(issueWebDTO.playDate,"yyyy年MM月dd日 hh:mm");

        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(SysConstants.USER_LOGIN_FLAG);


        //表示用户未登录
        if (userDTO == null) {
            return issueWebDTO;
        }
        User u=userDao.findByUserName(userDTO.userName);

        IssueUser issueUser = issueUserDao.findByUserIdAndIssueId(u.id, issueWebDTO.id);

        if (issueUser == null) {
            return issueWebDTO;
        }
        IssueUserWebDTO issueUserWebDTO = BeanMapper.map(issueUser, IssueUserWebDTO.class);
        issueWebDTO.issueUserWebDTO = issueUserWebDTO;

        if (StringUtils.isNotEmpty(issueUserWebDTO.answer)) {
            String[] asw = issueUserWebDTO.answer.split("-");
            int i = 0;
            if(issueWebDTO.issueQuestionList.size()!=asw.length){
                return issueWebDTO;
            }
            for (IssueQuestionWebDTO question : issueWebDTO.issueQuestionList) {
                for (int j = 0; j < question.issueQuestionContentList.size(); j++) {
                    try {
                        if (StringUtils.isNotEmpty(asw[i])) {
                            try {
                                Integer a = Integer.parseInt(asw[i]);
                                if (a - 1 == j) {
                                    question.issueQuestionContentList.get(j).userChecked = IssueCheck.CHECKED.getCode();
                                } else {
                                    question.issueQuestionContentList.get(j).userChecked = IssueCheck.UNCHECKED.getCode();
                                }
                            } catch (Exception e) {
                                question.issueQuestionContentList.get(j).userChecked = IssueCheck.UNCHECKED.getCode();

                            }
                        }
                    } catch (Exception e) {

                    }
                }
                i++;
            }
        }

        return issueWebDTO;
    }

    //用户参与竞猜
    public ReturnResponse addIssue(AddIssueDTO addIssueDTO,HttpServletRequest request){
        ReturnResponse returnResponse=new ReturnResponse();
        try {
            if(addIssueDTO==null
                    ||StringUtils.isEmpty(addIssueDTO.issue)
                    ||StringUtils.isEmpty(addIssueDTO.userWx)
                    ||StringUtils.isEmpty(addIssueDTO.answer)
                    ){
                returnResponse.code="0001";
                returnResponse.message="请求参数不能为空";
                return   returnResponse;
            }
            UserDTO userDTO= (UserDTO) request.getSession().getAttribute(SysConstants.USER_LOGIN_FLAG);

            if(userDTO==null){
                returnResponse.code="0002";
                returnResponse.message="用户未登录";
                return   returnResponse;
            }

            Issue issue=issueDao.findByIssue(addIssueDTO.issue);
            if(issue==null){
                returnResponse.code="0003";
                returnResponse.message="期次不存在";
                return   returnResponse;
            }
            Match match=issue.match;
           Date playDate= match.playDate;
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MINUTE,10);
            if(calendar.getTime().after(playDate)){
                returnResponse.code="0005";
                returnResponse.message="比赛开始10分钟前不能竞猜";
                return   returnResponse;
            }
            String[] asw=addIssueDTO.answer.split("-");
            List<IssueQuestion> tempList = Lists.newArrayList();
            List<IssueQuestion> issueQuestionList = issue.issueQuestionList;
            for (IssueQuestion issueQuestion : issueQuestionList) {
                if (StringUtils.equals(issueQuestion.issueChecked, IssueCheck.CHECKED.getCode())) {
                    tempList.add(issueQuestion);
                }
            }
            if(asw.length!=tempList.size()){
                returnResponse.code="0004";
                returnResponse.message="竞猜答案不合法";
                return   returnResponse;
            }
            for(String str:asw){
                if(StringUtils.isNotEmpty(str)){
                    Integer.parseInt(str);
                }

            }
            User u=userDao.findByUserName(userDTO.userName);

            IssueUser issueUser = issueUserDao.findByUserIdAndIssueId(u.id, issue.id);
            if(issueUser!=null){
                returnResponse.code="0005";
                returnResponse.message="您已经参与过来，请勿重复操作";
                return   returnResponse;
            }
            returnResponse.code="0000";
            returnResponse.message="竞猜成功";

            IssueUser issueUserTemp=new IssueUser();
            issueUserTemp.status= IssueUserStatus.INIT.getCode();
            issueUserTemp.addTime=new Date();
            issueUserTemp.updateTime=issueUserTemp.addTime;
            issueUserTemp.issue=issue;

            issueUserTemp.user=u;
            issueUserTemp.userMobile=u.userName;
            issueUserTemp.answer=addIssueDTO.answer;
            issueUserTemp.userWx=addIssueDTO.userWx;

//            issueUserDao.save(issueUserTemp);
            issue.partNum=StringUtils.isEmpty(issue.partNum)?"1":(Integer.parseInt(issue.partNum)+1)+"";
            issue.issueUserList.add(issueUserTemp);
            issueDao.save(issue);
        }catch (Exception e){
            e.printStackTrace();
            returnResponse.code="0007";
            returnResponse.message="系统异常稍后再试";
        }

        return   returnResponse;
    }
}
