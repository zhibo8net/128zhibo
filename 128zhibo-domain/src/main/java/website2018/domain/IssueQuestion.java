package website2018.domain;

import com.google.common.collect.Lists;
import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "zhibo_issue_question")
public class IssueQuestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    //题目标志 是否默认
    public String problemFlag;

    //题目类型
    public String problemType;

    //题目标题
    public String problemTitle;


    //正确答案
    public String answer;

    //其次是否选择该题目 checked 选中
    public String issueChecked;

    //输入值 标示 input
    public String inputFlag;

    public String inputContent;

    public Date addTime;

    public Date updateTime;

    @OneToMany(mappedBy = "issueQuestion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<IssueQuestionContent> issueQuestionContentList = Lists.newArrayList();

    @ManyToOne
    @JoinColumn(name = "issue_id")
    public Issue issue;

}
