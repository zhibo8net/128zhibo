package website2018.dto.admin;

import com.google.common.collect.Lists;
import website2018.base.BaseEntity;
import website2018.domain.Issue;
import website2018.domain.IssueQuestionContent;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class IssueQuestionAdminDTO  {

    public Long id;

    //题目标志 是否默认
    public String problemFlag;

    //题目类型
    public String problemType;

    //题目标题
    public String problemTitle;


    //正确答案
    public String answer;

    //用户答案
    public String userAnswer;

    //其次是否选择该题目 checked 选中
    public String issueChecked;

    //输入值 标示 input
    public String inputFlag;

    public String inputContent;

    public Date addTime;

    public Date updateTime;

    public List<IssueQuestionContentAdminDTO> problemContentList = Lists.newArrayList();

    public List<IssueQuestionContentAdminDTO> issueQuestionContentList = Lists.newArrayList();

}
