package website2018.dto;

import website2018.base.BaseEntity;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/5.
 */

public class BasketballRankDTO extends BaseEntity {


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
