package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.FootballJsb;
import website2018.domain.FootballSsb;

import java.util.List;


public interface FootballSsbDao extends PagingAndSortingRepository<FootballSsb, Long>, JpaSpecificationExecutor<FootballSsb> {


    List<FootballSsb> findByTypeAndTypeMoldAndTeamNameAndTeamMember(String type, String typeMold, String teamName,String teamMember);
    List<FootballSsb> findTop50ByTypeAndTypeMoldOrderBySumNumDesc(String type,String typeMold);

}
