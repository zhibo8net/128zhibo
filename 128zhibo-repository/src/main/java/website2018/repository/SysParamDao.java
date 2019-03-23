package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.SysParam;

import java.util.List;

public interface SysParamDao extends PagingAndSortingRepository<SysParam, Long>, JpaSpecificationExecutor<SysParam> {


}
