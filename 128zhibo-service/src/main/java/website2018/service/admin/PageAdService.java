package website2018.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import website2018.domain.PageAd;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.PageAdDao;

@Service
public class PageAdService {

    @Autowired
    PageAdDao pageAdDao;

    @Transactional(readOnly = true)
    public List<PageAd> findAll(Specification spec) {
        return pageAdDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<PageAd> findAll(Pageable pageable) {
        return pageAdDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<PageAd> findAll(Specification<PageAd> specification, Pageable pageable) {
        return pageAdDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public PageAd findOne(Long id) {
        return pageAdDao.findOne(id);
    }

    @Transactional
    public void create(PageAd pageAd) {
        pageAdDao.save(pageAd);
    }

    @Transactional
    public void modify(PageAd pageAd) {

        PageAd orginalPageAd = pageAdDao.findOne(pageAd.id);

        if (orginalPageAd == null) {
            throw new ServiceException("页面广告不存在", ErrorCode.BAD_REQUEST);
        }
        
        orginalPageAd.adKey = pageAd.adKey;
        orginalPageAd.message = pageAd.message;
        orginalPageAd.link = pageAd.link;
        orginalPageAd.image = pageAd.image;

        pageAdDao.save(orginalPageAd);
    }

    @Transactional
    public void delete(Long id) {
        PageAd pageAd = pageAdDao.findOne(id);

        if (pageAd == null) {
            throw new ServiceException("页面广告不存在", ErrorCode.BAD_REQUEST);
        }

        pageAdDao.delete(id);
    }

}
