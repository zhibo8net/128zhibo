package website2018.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website2018.domain.BasketballRank;
import website2018.dto.match.BasketMatchRankDTO;
import website2018.dto.match.BasketMatchTelRankDTO;
import website2018.dto.match.FootBallJSBRankDTO;
import website2018.dto.match.FootBallSSBRankDTO;
import website2018.repository.BasketballRankDao;
import website2018.repository.BasketballTelCommonDao;
import website2018.repository.FootballJsbDao;
import website2018.repository.FootballSsbDao;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/10/14.
 */
@Service
public class MatchRankService {
    // 缓存数据
    private Cache<String, Object> matchRankCache;

    @Autowired
    BasketballRankDao basketballRankDao;

    @Autowired
    BasketballTelCommonDao basketballTelCommonDao;

    @Autowired
    FootballJsbDao footballJsbDao;


    @Autowired
    FootballSsbDao footballSsbDao;

    @PostConstruct
    public void init() {
        matchRankCache = CacheBuilder.newBuilder().maximumSize(10).expireAfterWrite(10, TimeUnit.MINUTES).build();
    }
    public BasketMatchRankDTO queryBasketballRankDTO(){
        BasketMatchRankDTO basketMatchRankDTO= (BasketMatchRankDTO) matchRankCache.getIfPresent("basketMatchRankDTO");
        if(basketMatchRankDTO!=null){
            return basketMatchRankDTO;
        }else{
            basketMatchRankDTO=getBasketMatchRankDTO();
            matchRankCache.put("basketMatchRankDTO",basketMatchRankDTO);
            return basketMatchRankDTO;
        }
    }



    //技术排名
    public BasketMatchTelRankDTO queryBasketMatchTelRankDTO(){

        BasketMatchTelRankDTO basketMatchTelRankDTO= (BasketMatchTelRankDTO) matchRankCache.getIfPresent("basketMatchTelRankDTO");
        if(basketMatchTelRankDTO!=null){
            return basketMatchTelRankDTO;
        }else{
            basketMatchTelRankDTO=getBasketMatchTelRankDTO();
            matchRankCache.put("basketMatchTelRankDTO",basketMatchTelRankDTO);
            return basketMatchTelRankDTO;
        }


    }


    //积分榜
    public FootBallJSBRankDTO queryFootBallJSBRankDTO(){
        FootBallJSBRankDTO footBallJSBRankDTO= (FootBallJSBRankDTO) matchRankCache.getIfPresent("footBallJSBRankDTO");
        if(footBallJSBRankDTO!=null){
            return footBallJSBRankDTO;
        }else{
            footBallJSBRankDTO=getFootBallJSBRankDTO();
            matchRankCache.put("footBallJSBRankDTO",footBallJSBRankDTO);
            return footBallJSBRankDTO;
        }
    }
    //射手榜榜
    public FootBallSSBRankDTO queryFootBallSSBRankDTO(){
        FootBallSSBRankDTO footBallSSBRankDTO= (FootBallSSBRankDTO) matchRankCache.getIfPresent("footBallSSBRankDTO");
        if(footBallSSBRankDTO!=null){
            return footBallSSBRankDTO;
        }else{
            footBallSSBRankDTO=getFootBallSSBRankDTO();
            matchRankCache.put("footBallSSBRankDTO",footBallSSBRankDTO);
            return footBallSSBRankDTO;
        }
    }


//    @Scheduled(cron = "0 0/10 * * * *")
//    @Transactional
    public void refreshCache() {// 每10分钟刷新一次缓存

        BasketMatchRankDTO basketMatchRankDTO = getBasketMatchRankDTO();
        matchRankCache.put("basketMatchRankDTO", basketMatchRankDTO);

        BasketMatchTelRankDTO basketMatchTelRankDTO= getBasketMatchTelRankDTO();

        matchRankCache.put("basketMatchTelRankDTO", basketMatchTelRankDTO);

       FootBallJSBRankDTO footBallJSBRankDTO = getFootBallJSBRankDTO();
        matchRankCache.put("footBallJSBRankDTO", footBallJSBRankDTO);

    }

    public BasketMatchRankDTO getBasketMatchRankDTO(){
        BasketMatchRankDTO basketMatchRankDTO=new BasketMatchRankDTO();
        List<BasketballRank> basketballRankList=basketballRankDao.findTop20ByTypeOrderByWinRateDesc("NBA西部");
        basketMatchRankDTO.NBAWestMatchRankList=basketballRankList;
        List<BasketballRank> basketballRankList1=basketballRankDao.findTop20ByTypeOrderByWinRateDesc("NBA东部");
        basketMatchRankDTO.NBAEastMatchRankList=basketballRankList1;
        List<BasketballRank> basketballRankList2=basketballRankDao.findTop20ByTypeOrderByWinRateDesc("CBA排名");
        basketMatchRankDTO.CBAMatchRankList=basketballRankList2;

        return basketMatchRankDTO;
    }

    public BasketMatchTelRankDTO getBasketMatchTelRankDTO(){
        BasketMatchTelRankDTO basketMatchTelRankDTO=new BasketMatchTelRankDTO();
        basketMatchTelRankDTO.DSMatchTelRankList= basketballTelCommonDao.findTop30ByTypeOrderByScoreDesc("得分");
        basketMatchTelRankDTO.LBMatchTelRankList= basketballTelCommonDao.findTop30ByTypeOrderByBackboardDesc("篮板");
        basketMatchTelRankDTO.ZGMatchTelRankList= basketballTelCommonDao.findTop30ByTypeOrderByMatchAvgDesc("助攻");
        basketMatchTelRankDTO.QDMatchTelRankList= basketballTelCommonDao.findTop30ByTypeOrderByMatchAvgDesc("抢断");
        basketMatchTelRankDTO.GMMatchTelRankList= basketballTelCommonDao.findTop30ByTypeOrderByMatchAvgDesc("盖帽");

        return basketMatchTelRankDTO;
    }

    public FootBallJSBRankDTO getFootBallJSBRankDTO(){
        FootBallJSBRankDTO footBallJSBRankDTO =new FootBallJSBRankDTO();
        footBallJSBRankDTO.zcFootballJsbList=footballJsbDao.findTop20ByTypeAndTypeMoldOrderByScoreDesc("积分榜", "中超");
        footBallJSBRankDTO.ycFootballJsbList=footballJsbDao.findTop20ByTypeAndTypeMoldOrderByScoreDesc("积分榜", "英超");

        footBallJSBRankDTO.xjFootballJsbList=footballJsbDao.findTop20ByTypeAndTypeMoldOrderByScoreDesc("积分榜", "西甲");
        footBallJSBRankDTO.yjFootballJsbList=footballJsbDao.findTop20ByTypeAndTypeMoldOrderByScoreDesc("积分榜", "意甲");
        footBallJSBRankDTO.djFootballJsbList=footballJsbDao.findTop20ByTypeAndTypeMoldOrderByScoreDesc("积分榜", "德甲");
        footBallJSBRankDTO.fjFootballJsbList=footballJsbDao.findTop20ByTypeAndTypeMoldOrderByScoreDesc("积分榜", "法甲");

        return footBallJSBRankDTO;
    }

    public FootBallSSBRankDTO getFootBallSSBRankDTO(){
        FootBallSSBRankDTO footBallSSBRankDTO =new FootBallSSBRankDTO();
        footBallSSBRankDTO.zcFootballSsbList=footballSsbDao.findTop50ByTypeAndTypeMoldOrderBySumNumDesc("射手榜", "中超");
        footBallSSBRankDTO.ycFootballSsbList=footballSsbDao.findTop50ByTypeAndTypeMoldOrderBySumNumDesc("射手榜", "英超");

        footBallSSBRankDTO.xjFootballSsbList=footballSsbDao.findTop50ByTypeAndTypeMoldOrderBySumNumDesc("射手榜", "西甲");
        footBallSSBRankDTO.yjFootballSsbList=footballSsbDao.findTop50ByTypeAndTypeMoldOrderBySumNumDesc("射手榜", "意甲");
        footBallSSBRankDTO.djFootballSsbList=footballSsbDao.findTop50ByTypeAndTypeMoldOrderBySumNumDesc("射手榜", "德甲");
        footBallSSBRankDTO.fjFootballSsbList=footballSsbDao.findTop50ByTypeAndTypeMoldOrderBySumNumDesc("射手榜", "法甲");
        footBallSSBRankDTO.ogFootballSsbList=footballSsbDao.findTop50ByTypeAndTypeMoldOrderBySumNumDesc("射手榜", "欧冠");
        footBallSSBRankDTO.ygFootballSsbList=footballSsbDao.findTop50ByTypeAndTypeMoldOrderBySumNumDesc("射手榜", "亚冠");

        return footBallSSBRankDTO;
    }
}
