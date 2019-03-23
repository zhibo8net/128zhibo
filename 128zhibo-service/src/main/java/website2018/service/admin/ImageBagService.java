package website2018.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import website2018.domain.Image;
import website2018.domain.ImageBag;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.ImageBagDao;
import website2018.repository.ImageDao;

@Service
public class ImageBagService {

    @Value("${upload.webImageBase}")
    public String webImageBase;
    @Value("${upload.uploadPath}")
    public String uploadPath;
    
    @Autowired
    ImageBagDao imageBagDao;
    @Autowired
    ImageDao imageDao;

    @Transactional(readOnly = true)
    public List<ImageBag> findAll(Specification spec) {
        return imageBagDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<ImageBag> findAll(Pageable pageable) {
        return imageBagDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<ImageBag> findAll(Specification<ImageBag> specification, Pageable pageable) {
        return imageBagDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public ImageBag findOne(Long id) {
        return imageBagDao.findOne(id);
    }

    @Transactional
    public void create(ImageBag imageBag) {
        //建立关系
        for (Image image : imageBag.images) {
            image.name = image.name.replace(webImageBase, "");//传来的数据含有webImageBase的前缀，洗掉他
            image.bag = imageBag;
        }
        imageBagDao.save(imageBag);
    }

    @Transactional
    public void modify(ImageBag imageBag) {

        ImageBag orginalImageBag = imageBagDao.findOne(imageBag.id);

        if (orginalImageBag == null) {
            throw new ServiceException("文章分类不存在", ErrorCode.BAD_REQUEST);
        }
        
        orginalImageBag.project = imageBag.project;
        orginalImageBag.title = imageBag.title;
        orginalImageBag.source = imageBag.source;
        
        // 删掉原有的图片关系
        for (Image image : orginalImageBag.images) {
            imageDao.delete(image);
        }
        orginalImageBag.images.clear();
        // 添加新的图片关系
        for (Image image : imageBag.images) {
            image.name = image.name.replace(webImageBase, "");//传来的数据含有webImageBase的前缀，洗掉他
            orginalImageBag.images.add(image);
            image.bag = orginalImageBag;
        }

        imageBagDao.save(orginalImageBag);
    }

    @Transactional
    public void delete(Long id) {
        ImageBag imageBag = imageBagDao.findOne(id);

        if (imageBag == null) {
            throw new ServiceException("文章分类不存在", ErrorCode.BAD_REQUEST);
        }

        imageBagDao.delete(id);
    }

}
