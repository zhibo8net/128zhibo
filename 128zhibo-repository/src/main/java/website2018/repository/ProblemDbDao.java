package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.ProblemDb;

import java.util.List;


public interface ProblemDbDao extends PagingAndSortingRepository<ProblemDb, Long>, JpaSpecificationExecutor<ProblemDb> {

    List<ProblemDb> findByProblemFlagOrderByIdDesc(String problemFlag );

    ProblemDb findById(Long id);

    List<ProblemDb>  findByProjectOrderByIdDesc(String project);
}
