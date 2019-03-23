package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.ReplaceWord;

public interface ReplaceWordDao extends PagingAndSortingRepository<ReplaceWord, Long>, JpaSpecificationExecutor<ReplaceWord> {

}
