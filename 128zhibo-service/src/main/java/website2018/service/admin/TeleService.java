package website2018.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import website2018.domain.Tele;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.TeleDao;

@Service
public class TeleService {

    @Autowired
    TeleDao teleDao;

    @Transactional(readOnly = true)
    public List<Tele> findAll(Specification spec) {
        return teleDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<Tele> findAll(Pageable pageable) {
        return teleDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Tele> findAll(Specification<Tele> specification, Pageable pageable) {
        return teleDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Tele findOne(Long id) {
        return teleDao.findOne(id);
    }

    @Transactional
    public void create(Tele tele) {
        teleDao.save(tele);
    }

    @Transactional
    public void modify(Tele tele) {

        Tele orginalTele = teleDao.findOne(tele.id);

        if (orginalTele == null) {
            throw new ServiceException("电视不存在", ErrorCode.BAD_REQUEST);
        }
        
        orginalTele.name = tele.name;
        orginalTele.link = tele.link;
        
        teleDao.save(orginalTele);
    }

    @Transactional
    public void delete(Long id) {
        Tele tele = teleDao.findOne(id);

        if (tele == null) {
            throw new ServiceException("电视不存在", ErrorCode.BAD_REQUEST);
        }

        teleDao.delete(id);
    }

}
