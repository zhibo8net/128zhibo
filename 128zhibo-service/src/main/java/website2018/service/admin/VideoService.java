package website2018.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import website2018.domain.Video;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.VideoDao;

@Service
public class VideoService {

    @Autowired
    VideoDao videoDao;

    @Transactional(readOnly = true)
    public List<Video> findAll(Specification spec) {
        return videoDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<Video> findAll(Pageable pageable) {
        return videoDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Video> findAll(Specification<Video> specification, Pageable pageable) {
        return videoDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Video findOne(Long id) {
        return videoDao.findOne(id);
    }

    @Transactional
    public void create(Video video) {
        videoDao.save(video);
    }

    @Transactional
    public void modify(Video video) {

        Video orginalVideo = videoDao.findOne(video.id);

        if (orginalVideo == null) {
            throw new ServiceException("文章分类不存在", ErrorCode.BAD_REQUEST);
        }
        
        orginalVideo.name = video.name;
        orginalVideo.link = video.link;
        orginalVideo.type = video.type;
        orginalVideo.project = video.project;
        orginalVideo.game = video.game;
        orginalVideo.image = video.image;

        videoDao.save(orginalVideo);
    }

    @Transactional
    public void delete(Long id) {
        Video video = videoDao.findOne(id);

        if (video == null) {
            throw new ServiceException("文章分类不存在", ErrorCode.BAD_REQUEST);
        }

        videoDao.delete(id);
    }

}
