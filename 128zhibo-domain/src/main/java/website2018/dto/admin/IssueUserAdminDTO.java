package website2018.dto.admin;


import website2018.dto.user.UserDTO;

import java.util.Date;

public class IssueUserAdminDTO {
    public Long id;
    public Date addTime;
    public Date updateTime;
    public String addTimeStr;

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
