package website2018.dto.web;

import java.util.Date;

public class IssueQuestionContentWebDTO {
    public Long id;
    public Date addTime;
    public Date updateTime;


    //用户是否选择 checked
    public String userChecked;

    //是否 答对 true
    public String answerFlag;

    public String questionContent;

}
