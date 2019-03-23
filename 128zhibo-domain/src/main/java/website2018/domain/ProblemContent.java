package website2018.domain;

import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "zhibo_problem_content")
public class ProblemContent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;
    public Date updateTime;

    public String questionContent;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    public ProblemDb problemDb;

}
