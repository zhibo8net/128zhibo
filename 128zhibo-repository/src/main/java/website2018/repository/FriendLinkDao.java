package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import website2018.domain.FriendLink;

public interface FriendLinkDao extends PagingAndSortingRepository<FriendLink, Long>, JpaSpecificationExecutor<FriendLink> {

}
