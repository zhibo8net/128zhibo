package website2018.dto.admin;


import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class ProblemAdminDb {
    public Long id;

    //题目标志 是否默认
    public String problemFlag;

    //题目类型
    public String problemType;

    //题目类型
    public String problemTypeStr;
    //题目标题
    public String problemTitle;

    public String inputFlag;

    public String inputContent;

    public String project;

    //正确答案
    public String answer;

    public Date addTime;

    public Date updateTime;

    public String updateTimeStr;

    public List<ProblemContentAdminDTO> problemContentList = Lists.newArrayList();

}
