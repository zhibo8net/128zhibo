package website2018.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import website2018.domain.Video;

public interface VideoDao extends PagingAndSortingRepository<Video, Long>, JpaSpecificationExecutor<Video> {

    public List<Video> findBySource(String source);

    public List<Video> findByAddTimeGreaterThanOrderByIdDesc(Date addTime);


    @Modifying //该注解表示允许修改
    @Query("update Video as c set c.link = ?1 where c.id=?2")
    @Transactional
    public void update(String link,Long id);

}
