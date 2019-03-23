package website2018.domain;

import com.google.common.collect.Lists;
import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "zhibo_problem_db")
public class ProblemDb extends BaseEntity {
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

    public Date addTime;

    public Date updateTime;

    public String inputFlag;

    public String inputContent;

    public String project;

    @OneToMany(mappedBy = "problemDb", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ProblemContent> problemContentList = Lists.newArrayList();



}
