package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.BasketballRank;

import java.util.List;


public interface BasketballRankDao extends PagingAndSortingRepository<BasketballRank, Long>, JpaSpecificationExecutor<BasketballRank> {


    BasketballRank findByTypeAndTeamName(String type,String teamName);

    List<BasketballRank> findTop20ByTypeOrderByWinRateDesc(String type);
}
