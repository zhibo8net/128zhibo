package website2018.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.ImageBag;

public interface ImageBagDao extends PagingAndSortingRepository<ImageBag, Long>, JpaSpecificationExecutor<ImageBag> {

    public List<ImageBag> findBySource(String source);

    public List<ImageBag> findTop8ByProjectOrderByIdDesc(String project);

    public List<ImageBag> findTop10ByAddTimeGreaterThanOrderByIdDesc(Date addTime);
}
