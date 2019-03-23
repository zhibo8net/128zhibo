package website2018.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import website2018.domain.Ad;
import website2018.domain.User;

import java.util.List;

public interface UserDao extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<Ad> {

    public User findByUserName(String userName);

    public List<User> findByUserNameLessThan(String userName);
}
