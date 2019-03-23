package website2018.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import website2018.domain.Ad;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.AdDao;

@Service
public class AdService {

    @Autowired
    AdDao adDao;

    @Transactional(readOnly = true)
    public List<Ad> findAll(Specification spec) {
        return adDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<Ad> findAll(Pageable pageable) {
        return adDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Ad> findAll(Specification<Ad> specification, Pageable pageable) {
        return adDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Ad findOne(Long id) {
        return adDao.findOne(id);
    }

    @Transactional
    public void create(Ad ad) {
        adDao.save(ad);
    }

    @Transactional
    public void modify(Ad ad) {

        Ad orginalAd = adDao.findOne(ad.id);

        if (orginalAd == null) {
            throw new ServiceException("电视不存在", ErrorCode.BAD_REQUEST);
        }
        
        orginalAd.type = ad.type;
        orginalAd.name = ad.name;
        orginalAd.link = ad.link;
        orginalAd.endDate = ad.endDate;
        orginalAd.teams = ad.teams;
        orginalAd.important = ad.important;
        orginalAd.light = ad.light;
        
        adDao.save(orginalAd);
    }

    @Transactional
    public void delete(Long id) {
        Ad ad = adDao.findOne(id);

        if (ad == null) {
            throw new ServiceException("电视不存在", ErrorCode.BAD_REQUEST);
        }

        adDao.delete(id);
    }

}
