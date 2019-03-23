package website2018.service.admin;


import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website2018.domain.ProblemContent;
import website2018.domain.ProblemDb;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.ProblemContentDao;
import website2018.repository.ProblemDbDao;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/1/27.
 */
@Service
public class ProblemDbAdminService {


    @Autowired
    ProblemDbDao problemDbDao;

    @Autowired
    ProblemContentDao problemContentDao;

    @Transactional(readOnly = true)
    public Page<ProblemDb> findAll(Specification<ProblemDb> specification, Pageable pageable) {
        return problemDbDao.findAll(specification, pageable);
    }
    @Transactional(readOnly = true)
    public List<ProblemDb> findByProblemFlag(String problemFlag) {
        return problemDbDao.findByProblemFlagOrderByIdDesc(problemFlag);
    }

    @Transactional
    public void delete(Long id) {
        ProblemDb problemDb = problemDbDao.findOne(id);

        if (problemDb == null) {
            throw new ServiceException("该题目不存在", ErrorCode.BAD_MESSAGE_REQUEST);
        }

        problemDbDao.delete(id);
    }

    @Transactional(readOnly = true)
    public ProblemDb findOne(Long id) {
        return problemDbDao.findOne(id);
    }
    @Transactional
    public void create(ProblemDb problemDb) {
      if(problemDb==null){
          throw new ServiceException("请求数据不正确", ErrorCode.BAD_MESSAGE_REQUEST);
      }
        Date d=new Date();
        problemDb.addTime=d;
        problemDb.updateTime=d;

        List<ProblemContent> problemContentList= Lists.newArrayList();
        problemContentList.addAll(problemDb.problemContentList);

        problemDb.problemContentList.clear();
        problemDbDao.save(problemDb);

        for(ProblemContent problemContent:problemContentList){
            problemContent.problemDb=problemDb;
            problemDb.problemContentList.add(problemContent);
        }
        problemDbDao.save(problemDb);
    }

    @Transactional
    public void modify(ProblemDb problemDb) {

        ProblemDb orginalProblemDb = problemDbDao.findOne(problemDb.id);

        if (orginalProblemDb == null) {
            throw new ServiceException("比赛不存在", ErrorCode.BAD_MESSAGE_REQUEST);
        }

        orginalProblemDb.problemFlag=problemDb.problemFlag;
        orginalProblemDb.problemType=problemDb.problemType;
        orginalProblemDb.problemTitle=problemDb.problemTitle;
        orginalProblemDb.answer = problemDb.answer;
        orginalProblemDb.updateTime = new Date();
        orginalProblemDb.inputContent=problemDb.inputContent;
        orginalProblemDb.inputFlag=problemDb.inputFlag;
        // 删掉原有的数据
        for (ProblemContent problemContent : orginalProblemDb.problemContentList) {
            problemContentDao.delete(problemContent.id);
        }
        orginalProblemDb.problemContentList.clear();
        // 添加新的数据
        for (ProblemContent problemContent : problemDb.problemContentList) {
            orginalProblemDb.problemContentList.add(problemContent);
            problemContent.problemDb = orginalProblemDb;
        }


        problemDbDao.save(orginalProblemDb);
    }
}
