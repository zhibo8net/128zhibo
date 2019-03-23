package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.Live;

public interface LiveDao extends PagingAndSortingRepository<Live, Long>, JpaSpecificationExecutor<Live> {
    Live findById(long id);


}
