package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.Project;
import website2018.domain.Sensitive;

public interface SensitiveDao extends PagingAndSortingRepository<Sensitive, Long>, JpaSpecificationExecutor<Sensitive> {

}
