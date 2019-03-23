package website2018.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.Comment;

import java.util.Date;
import java.util.List;

public interface CommentDao extends PagingAndSortingRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

        List<Comment> findByRelIdAndTypeOrderByAddTimeDesc(Integer relId,Integer type);

        List<Comment> findByRelIdAndTypeAndUserTypeOrderByAddTimeDesc(Integer relId,Integer type,Integer userType);

        List<Comment> findByRelIdAndTypeOrderByAddTimeDesc(Integer relId,Integer type, Pageable pageable);

        List<Comment> findByRelIdAndTypeAndUserTypeOrderByAddTimeDesc(Integer relId,Integer type,Integer userType, Pageable pageable);

        List<Comment> findByRelIdAndTypeAndUserTypeAndCommentAndAddTime(Integer relId,Integer type,Integer userType,String comment,Date addTime);

}
