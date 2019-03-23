package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.League;
import website2018.domain.Project;

public interface LeagueDao extends PagingAndSortingRepository<League, Long>, JpaSpecificationExecutor<League> {

    public League findByLeagueZh(String leagueZh);

}
