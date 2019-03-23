package website2018.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.Ad;

public interface AdDao extends PagingAndSortingRepository<Ad, Long>, JpaSpecificationExecutor<Ad> {

    public List<Ad> findByType(String type);

}
