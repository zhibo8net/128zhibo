package website2018.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import website2018.domain.FriendLink;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.FriendLinkDao;

@Service
public class FriendLinkService {

    @Autowired
    FriendLinkDao friendLinkDao;

    @Transactional(readOnly = true)
    public List<FriendLink> findAll(Specification spec) {
        return friendLinkDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<FriendLink> findAll(Pageable pageable) {
        return friendLinkDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<FriendLink> findAll(Specification<FriendLink> specification, Pageable pageable) {
        return friendLinkDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public FriendLink findOne(Long id) {
        return friendLinkDao.findOne(id);
    }

    @Transactional
    public void create(FriendLink friendLink) {
        friendLinkDao.save(friendLink);
    }

    @Transactional
    public void modify(FriendLink friendLink) {

        FriendLink orginalFriendLink = friendLinkDao.findOne(friendLink.id);

        if (orginalFriendLink == null) {
            throw new ServiceException("文章分类不存在", ErrorCode.BAD_REQUEST);
        }
        
        orginalFriendLink.name = friendLink.name;
        orginalFriendLink.link = friendLink.link;
        
        friendLinkDao.save(orginalFriendLink);
    }

    @Transactional
    public void delete(Long id) {
        FriendLink friendLink = friendLinkDao.findOne(id);

        if (friendLink == null) {
            throw new ServiceException("文章分类不存在", ErrorCode.BAD_REQUEST);
        }

        friendLinkDao.delete(id);
    }

}
