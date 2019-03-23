package website2018.service.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import website2018.domain.Log;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.filter.RestFilter;
import website2018.repository.LogDao;

@Service
public class LogService {

    @Autowired
    LogDao logDao;

    public void log(String behaviour, String link) {
        Log l = new Log();
        l.account = RestFilter.accountHolder.get();
        l.addTime = new Date();
        l.ip = RestFilter.requestHolder.get().getRemoteAddr();
        l.behaviour = behaviour;
        l.link = link;
        logDao.save(l);
    }
    
    @Transactional(readOnly = true)
    public List<Log> findAll(Specification spec) {
        return logDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<Log> findAll(Pageable pageable) {
        return logDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Log> findAll(Specification<Log> specification, Pageable pageable) {
        return logDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Log findOne(Long id) {
        return logDao.findOne(id);
    }

    @Transactional
    public void create(Log log) {
        logDao.save(log);
    }

    @Transactional
    public void modify(Log log) {

        Log orginalLog = logDao.findOne(log.id);

        if (orginalLog == null) {
            throw new ServiceException("电视不存在", ErrorCode.BAD_REQUEST);
        }
        
        logDao.save(orginalLog);
    }

    @Transactional
    public void delete(Long id) {
        Log log = logDao.findOne(id);

        if (log == null) {
            throw new ServiceException("电视不存在", ErrorCode.BAD_REQUEST);
        }

        logDao.delete(id);
    }

}
