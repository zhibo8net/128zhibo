package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.Tele;

public interface TeleDao extends PagingAndSortingRepository<Tele, Long>, JpaSpecificationExecutor<Tele> {

}
