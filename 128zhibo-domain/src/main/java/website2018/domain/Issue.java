package website2018.domain;

import com.google.common.collect.Lists;
import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "zhibo_issue")
public class Issue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    //状态
    public String status;

    //状态描述
    public String statusDesc;
    //比赛日期
    public Date playDate;

    //竞猜人数
    public String partNum;

    //中奖人数
    public String awardNum;
    //问题数
    public String problemNum;
    //开奖结果
    public String issueAnswer;

    public Date addTime;

    public Date updateTime;



    @ManyToOne
    @JoinColumn(name = "match_id")
    public Match match;


    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<IssueUser> issueUserList = Lists.newArrayList();

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<IssueQuestion> issueQuestionList = Lists.newArrayList();

}
