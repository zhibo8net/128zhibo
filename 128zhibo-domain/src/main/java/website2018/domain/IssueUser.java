package website2018.domain;

import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "zhibo_issue_user")
public class IssueUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;
    public Date updateTime;


    @OneToOne
    @JoinColumn(name = "user_id")
    public User user;

    //手机
    public String userMobile;

    //微信
    public String userWx;

    public String status;

    //竞猜答案
    public String answer;

    //竞猜正确率
    public double answerRate;
    @ManyToOne
    @JoinColumn(name = "issue_id")
    public Issue issue;
}
