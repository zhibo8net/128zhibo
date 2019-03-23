package website2018.dto.match;

import website2018.domain.BasketballTelCommon;
import website2018.dto.BasketballRankDTO;
import website2018.dto.BasketballTelCommonDTO;

import java.util.List;

/**
 * Created by Administrator on 2018/10/14.
 */
public class BasketMatchTelRankDTO {

    //得分排名
    public List<BasketballTelCommon> DSMatchTelRankList;
    //篮板排名
    public List<BasketballTelCommon> LBMatchTelRankList;

    //助攻排名
    public List<BasketballTelCommon> ZGMatchTelRankList;
    //抢断排名
    public List<BasketballTelCommon> QDMatchTelRankList;
    //盖帽排名
    public List<BasketballTelCommon> GMMatchTelRankList;

}
