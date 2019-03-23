package website2018.dto.match;

import website2018.domain.BasketballRank;

import java.util.List;

/**
 * Created by Administrator on 2018/10/14.
 */
public class BasketMatchRankDTO {

    //NBA西部排名
    public List<BasketballRank> NBAWestMatchRankList;
    //NBA东部排名
    public List<BasketballRank> NBAEastMatchRankList;

    //CBA排名
    public List<BasketballRank> CBAMatchRankList;

}
