package website2018.dto;

import website2018.base.BaseEntity;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/5.
 */

public class BasketballTelCommonDTO extends BaseEntity {

    public Long id;

    public Date createTime;
    public Date updateTime;

    public String  type ;
    public String teamName ;
    //得分
    public String score;
    //命中率
    public String rate;
    //场次
    public String matchNum ;
    //时间
    public String timeNum;
    //	篮板
    public String backboard;
    //总数
    public String sumNum;
    //	场均
    public String matchAvg;
}
