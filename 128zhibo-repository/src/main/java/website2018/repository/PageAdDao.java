package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.PageAd;

public interface PageAdDao extends PagingAndSortingRepository<PageAd, Long>, JpaSpecificationExecutor<PageAd> {

}
