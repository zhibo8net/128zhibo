package website2018.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.text.EncodeUtil;
import org.springside.modules.utils.text.HashUtil;

import website2018.domain.Config;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.ConfigDao;

@Service
public class ConfigService {

    @Autowired
    ConfigDao configDao;

    @Transactional(readOnly = true)
    public List<Config> findAll(Specification spec) {
        return configDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<Config> findAll(Pageable pageable) {
        return configDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Config> findAll(Specification<Config> specification, Pageable pageable) {
        return configDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Config findOne(Long id) {
        return configDao.findOne(id);
    }

    @Transactional
    public void create(Config config) {
        configDao.save(config);
    }

    @Transactional
    public void modify(Config config) {

        Config orginalConfig = configDao.findOne(config.id);

        if (orginalConfig == null) {
            throw new ServiceException("配置不存在", ErrorCode.BAD_REQUEST);
        }
        
        orginalConfig.ckey = config.ckey;
        orginalConfig.cvalue = config.cvalue;
        orginalConfig.cdesc = config.cdesc;
        
        configDao.save(orginalConfig);
    }

    @Transactional
    public void delete(Long id) {
        Config config = configDao.findOne(id);

        if (config == null) {
            throw new ServiceException("配置不存在", ErrorCode.BAD_REQUEST);
        }

        configDao.delete(id);
    }

}
