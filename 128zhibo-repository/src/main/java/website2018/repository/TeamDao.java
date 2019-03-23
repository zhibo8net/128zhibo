package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.Team;

import java.util.List;

public interface TeamDao extends PagingAndSortingRepository<Team, Long>, JpaSpecificationExecutor<Team> {

    public List<Team> findByTeamZh(String teamZh);
    public List<Team> findByTeamName1(String teamName1);
    public List<Team> findByTeamName2(String teamName2);
    public List<Team> findByTeamName3(String teamName3);
}
