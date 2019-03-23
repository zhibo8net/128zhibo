package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.IssueQuestion;

import java.util.List;


public interface IssueQuestionDao extends PagingAndSortingRepository<IssueQuestion, Long>, JpaSpecificationExecutor<IssueQuestion> {

List<IssueQuestion> findByIssueId(long id);
}
