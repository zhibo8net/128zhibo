package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.ProblemContent;


public interface ProblemContentDao extends PagingAndSortingRepository<ProblemContent, Long>, JpaSpecificationExecutor<ProblemContent> {


}
