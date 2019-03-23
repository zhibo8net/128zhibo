package website2018.dto;

import website2018.base.BaseEntity;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/5.
 */

public class FootballSsbDTO extends BaseEntity {

    public Long id;

    public Date createTime;
    public Date updateTime;
    //类型
    public String  type ;
    //球队名称
    public String teamName ;
    //类型  如：德甲
    public String typeMold;
    //队员
    public String teamMember;

    //总球数
    public String sumNum ;
    //点球数
    public String dianBallNum;
}
