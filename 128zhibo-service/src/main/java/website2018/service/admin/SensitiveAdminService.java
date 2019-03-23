package website2018.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website2018.domain.Sensitive;
import website2018.domain.Team;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.ImageBagDao;
import website2018.repository.ImageDao;
import website2018.repository.SensitiveDao;
import website2018.repository.TeamDao;

import java.util.List;

@Service
public class SensitiveAdminService {

    @Value("${upload.webImageBase}")
    public String webImageBase;
    @Value("${upload.uploadPath}")
    public String uploadPath;

    @Autowired
    SensitiveDao sensitiveDao;


    @Transactional(readOnly = true)
    public List<Sensitive> findAll(Specification spec) {
        return sensitiveDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<Sensitive> findAll(Pageable pageable) {
        return sensitiveDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Sensitive> findAll(Specification<Sensitive> specification, Pageable pageable) {
        return sensitiveDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Sensitive findOne(Long id) {
        return sensitiveDao.findOne(id);
    }

    @Transactional
    public void create(Sensitive sensitive) {

        sensitiveDao.save(sensitive);
    }

    @Transactional
    public void modify(Sensitive sensitive) {

        Sensitive orginalSensitive = sensitiveDao.findOne(sensitive.id);

        if (orginalSensitive == null) {
            throw new ServiceException("原数据不存在", ErrorCode.BAD_REQUEST);
        }


        sensitiveDao.save(sensitive);
    }

    @Transactional
    public void delete(Long id) {
        Sensitive orginalSensitive = sensitiveDao.findOne(id);

        if (orginalSensitive == null) {
            throw new ServiceException("原数据不存在", ErrorCode.BAD_REQUEST);
        }

        sensitiveDao.delete(id);
    }

}
