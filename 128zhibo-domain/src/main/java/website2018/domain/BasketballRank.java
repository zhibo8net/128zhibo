package website2018.domain;

import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/5.
 */
@Entity
@Table(name = "zhibo_basketball_rank")
public class BasketballRank extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Date createTime;
    public Date updateTime;

    public String  type ;
    public String teamName ;
    //胜
    public String winNum;
    //负
    public String failNum;
    //胜率
    public String winRate ;
    //胜差
    public String victories;
    //近况
    public String currentRemark;
}
