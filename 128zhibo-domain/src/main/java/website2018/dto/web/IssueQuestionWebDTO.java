package website2018.dto.web;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class IssueQuestionWebDTO {

    public Long id;

    //题目标志 是否默认
    public String problemFlag;

    //题目类型
    public String problemType;

    //题目标题
    public String problemTitle;


    //正确答案
    public String answer;

    //期次是否选择该题目 checked 选中
    public String issueChecked;

    //输入值 标示 input
    public String inputFlag;

    public String inputContent;

    public Date addTime;

    public Date updateTime;

    public List<IssueQuestionContentWebDTO> issueQuestionContentList = Lists.newArrayList();


}
