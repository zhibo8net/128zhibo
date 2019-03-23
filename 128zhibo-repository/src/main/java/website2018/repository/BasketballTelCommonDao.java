package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.BasketballRank;
import website2018.domain.BasketballTelCommon;

import java.util.List;


public interface BasketballTelCommonDao extends PagingAndSortingRepository<BasketballTelCommon, Long>, JpaSpecificationExecutor<BasketballTelCommon> {


    BasketballTelCommon findByTypeAndTeamName(String type, String teamName);

    //得分
    List<BasketballTelCommon> findTop30ByTypeOrderByScoreDesc(String type);
    //篮板
    List<BasketballTelCommon> findTop30ByTypeOrderByBackboardDesc(String type);

    //助攻 抢断 盖帽
    List<BasketballTelCommon> findTop30ByTypeOrderByMatchAvgDesc(String type);


}
