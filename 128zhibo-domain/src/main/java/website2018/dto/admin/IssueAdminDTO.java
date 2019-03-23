package website2018.dto.admin;


import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class IssueAdminDTO  {

    public Long id;

    //期数
    public String issue;


    //比赛名称
    public String matchName;

    //比赛名称
    public String matchNameAndId;
    //项目
    public String project;

    public String game;

    public String userShowFlag;
    //状态
    public String status;

    //状态描述
    public String statusDesc;
    //比赛日期
    public Date playDate;

    //比赛日期
    public String playDateStr;
    //竞猜人数
    public String partNum;

    //问题数
    public String problemNum;

    //中奖人数
    public String awardNum;

    //开奖结果
    public String issueAnswer;

    public Date addTime;

    public Date updateTime;
    public String addTimeStr;
    public String updateTimeStr;


    public List<IssueUserAdminDTO> issueUserList= Lists.newArrayList();

    public List<IssueQuestionAdminDTO> issueQuestionList= Lists.newArrayList();
    public List<IssueQuestionAdminDTO> otherQuestionList= Lists.newArrayList();
}
