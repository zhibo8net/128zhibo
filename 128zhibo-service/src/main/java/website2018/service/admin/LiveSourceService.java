package website2018.service.admin;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import website2018.domain.LiveSource;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.LiveSourceDao;
import website2018.repository.MatchDao;

@Service
public class LiveSourceService {

    @Autowired
    LiveSourceDao liveSourceDao;
    
    @Autowired
    MatchDao matchDao;

    @Transactional(readOnly = true)
    public List<LiveSource> findAll(Specification spec) {
        return liveSourceDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<LiveSource> findAll(Pageable pageable) {
        return liveSourceDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<LiveSource> findAll(Specification<LiveSource> specification, Pageable pageable) {
        return liveSourceDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public LiveSource findOne(Long id) {
        return liveSourceDao.findOne(id);
    }

    @Transactional
    public void create(LiveSource liveSource) {
        liveSourceDao.save(liveSource);
    }

    @Transactional
    public void modify(LiveSource liveSource) {

        LiveSource orginalLiveSource = liveSourceDao.findOne(liveSource.id);

        if (orginalLiveSource == null) {
            throw new ServiceException("文章分类不存在", ErrorCode.BAD_REQUEST);
        }

        orginalLiveSource.link = liveSource.link;
        orginalLiveSource.active = liveSource.active;
        orginalLiveSource.fetchInterval = liveSource.fetchInterval;
        orginalLiveSource.channels = liveSource.channels;
        
        liveSourceDao.save(orginalLiveSource);
    }

    @Transactional
    public void delete(Long id) {
        LiveSource liveSource = liveSourceDao.findOne(id);

        if (liveSource == null) {
            throw new ServiceException("文章分类不存在", ErrorCode.BAD_REQUEST);
        }

        liveSourceDao.delete(id);
    }

    /**
     * 指定某一ID的直播源为活动直播源，其他直播源均设为非活动状态
     * @param id
     */
    @Transactional
    public void active(Long id) {
        matchDao.deleteAll();
        
        List<LiveSource> liveSources = (List<LiveSource>)liveSourceDao.findAll();
        for(LiveSource ls : liveSources) {
            if(ls.id.equals(id)) {
                ls.active = 1;
                
                //将lastFetch设为很早的一个时间，保证立刻抓取
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, 2017);
                ls.lastFetch = c.getTime();
            }else {
                ls.active = 0;
            }
        }
        liveSourceDao.save(liveSources);
    }
}
