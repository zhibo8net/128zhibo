package website2018.dto;

import website2018.base.BaseEntity;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/5.
 */

public class FootballJsbDTO extends BaseEntity {


    public Long id;

    public Date createTime;
    public Date updateTime;
    //积分类型
    public String  type ;
    //球队名称
    public String teamName ;
    //类型  如：德甲
    public String typeMold;
    //积分
    public String score;

    //场次
    public String matchNum ;
    //净胜球
    public String ballNum;
}
