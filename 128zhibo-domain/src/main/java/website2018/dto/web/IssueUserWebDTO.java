package website2018.dto.web;

import website2018.dto.user.UserDTO;

import java.util.Date;

public class IssueUserWebDTO {
    public Long id;
    public Date addTime;
    public Date updateTime;


    public UserDTO user;

    //手机
    public String userMobile;

    //微信
    public String userWx;

    public String status;

    //竞猜答案
    public String answer;

    //竞猜正确率
    public double answerRate;
}
