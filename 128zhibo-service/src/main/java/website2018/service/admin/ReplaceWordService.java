package website2018.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import website2018.domain.ReplaceWord;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.ReplaceWordDao;

@Service
public class ReplaceWordService {

    @Autowired
    ReplaceWordDao replaceWordDao;

    @Transactional(readOnly = true)
    public List<ReplaceWord> findAll(Specification spec) {
        return replaceWordDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<ReplaceWord> findAll(Pageable pageable) {
        return replaceWordDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<ReplaceWord> findAll(Specification<ReplaceWord> specification, Pageable pageable) {
        return replaceWordDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public ReplaceWord findOne(Long id) {
        return replaceWordDao.findOne(id);
    }

    @Transactional
    public void create(ReplaceWord replaceWord) {
        replaceWordDao.save(replaceWord);
    }

    @Transactional
    public void modify(ReplaceWord replaceWord) {

        ReplaceWord orginalReplaceWord = replaceWordDao.findOne(replaceWord.id);

        if (orginalReplaceWord == null) {
            throw new ServiceException("替换词不存在", ErrorCode.BAD_REQUEST);
        }
        
        orginalReplaceWord.fromWord = replaceWord.fromWord;
        orginalReplaceWord.toWord = replaceWord.toWord;
        
        replaceWordDao.save(orginalReplaceWord);
    }

    @Transactional
    public void delete(Long id) {
        ReplaceWord replaceWord = replaceWordDao.findOne(id);

        if (replaceWord == null) {
            throw new ServiceException("替换词不存在", ErrorCode.BAD_REQUEST);
        }

        replaceWordDao.delete(id);
    }

}
