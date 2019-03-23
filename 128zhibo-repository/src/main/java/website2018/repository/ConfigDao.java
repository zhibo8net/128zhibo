package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.Config;

public interface ConfigDao extends PagingAndSortingRepository<Config, Long>, JpaSpecificationExecutor<Config>  {
    public Config findByCkey(String ckey);
}
