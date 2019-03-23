package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.MatchStream;

import java.util.Date;
import java.util.List;


public interface MatchStreamDao extends PagingAndSortingRepository<MatchStream, Long>, JpaSpecificationExecutor<MatchStream> {

    List<MatchStream> findByMatchNameAndUpdateTimeGreaterThan(String matchName,Date updateTime);
    List<MatchStream> findByUpdateTimeGreaterThanAndUpdateTimeLessThan(Date updateTime,Date d1);
}
