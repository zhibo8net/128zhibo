package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.Issue;

import java.util.Date;
import java.util.List;


public interface IssueDao extends PagingAndSortingRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

    @Query(" select max(issue) from Issue ")
    String findMaxIssue();

    Issue findById(long id);

    Issue findByIssue(String issue);

    Issue findTop1ByAddTimeGreaterThanOrderByIdDesc(Date addTime);

    Issue findTop1ByStatusOrderByIdDesc(String status);

    List<Issue> findByStatus(String status);
}
