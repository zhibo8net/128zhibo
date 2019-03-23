package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.Account;

public interface AccountDao extends PagingAndSortingRepository<Account, Long>, JpaSpecificationExecutor<Account>  {

    Account findByMobile(String mobile);

}
