package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.FootballJsb;

import java.util.List;


public interface FootballJsbDao extends PagingAndSortingRepository<FootballJsb, Long>, JpaSpecificationExecutor<FootballJsb> {


    FootballJsb findByTypeAndTypeMoldAndTeamName(String type,String typeMold, String teamName);

    List<FootballJsb> findTop20ByTypeAndTypeMoldOrderByScoreDesc(String type,String typeMold);

}
