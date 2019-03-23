package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.IssueQuestionContent;


public interface IssueQuestionContentDao extends PagingAndSortingRepository<IssueQuestionContent, Long>, JpaSpecificationExecutor<IssueQuestionContent> {


}
