package website2018.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.News;

public interface NewsDao extends PagingAndSortingRepository<News, Long>, JpaSpecificationExecutor<News> {

    public List<News> findBySource(String source);

    public List<News> findByMatchName(String matchName);

    public List<News> findTop100ByMatchPreFlagOrderByIdDesc(String matchPreFlag);
    public List<News> findTop16ByProjectAndMatchPreFlagOrderByAddTimeDesc( String  project,String matchPreFlag);

    public List<News> findTop5ByProjectAndGameAndImageNotAndMatchPreFlagOrderByUpdateTimeDesc( String  project,String game,String iamge,String matchPreFlag);

    public List<News> findTop5ByProjectAndMatchPreFlagAndImageNotOrderByUpdateTimeDesc( String  project,String matchPreFlag, String iamge);

}
