package website2018.domain;

import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "zhibo_issue_question_content")
public class IssueQuestionContent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;
    public Date updateTime;


    public String questionContent;

    @ManyToOne
    @JoinColumn(name = "question_id")
    public IssueQuestion issueQuestion;

}
