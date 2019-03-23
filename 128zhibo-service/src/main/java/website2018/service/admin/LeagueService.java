package website2018.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website2018.domain.League;
import website2018.domain.Team;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.ImageBagDao;
import website2018.repository.ImageDao;
import website2018.repository.LeagueDao;
import website2018.repository.TeamDao;

import java.util.List;

@Service
public class LeagueService {

    @Value("${upload.webImageBase}")
    public String webImageBase;
    @Value("${upload.uploadPath}")
    public String uploadPath;

    @Autowired
    LeagueDao leagueDao;

    @Autowired
    ImageBagDao imageBagDao;
    @Autowired
    ImageDao imageDao;

    @Transactional(readOnly = true)
    public List<League> findAll(Specification spec) {
        return leagueDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<League> findAll(Pageable pageable) {
        return leagueDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<League> findAll(Specification<League> specification, Pageable pageable) {
        return leagueDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public League findOne(Long id) {
        return leagueDao.findOne(id);
    }

    @Transactional
    public void create(League league) {

        leagueDao.save(league);
    }

    @Transactional
    public void modify(League league) {

        League orginalTeam = leagueDao.findOne(league.id);

        if (orginalTeam == null) {
            throw new ServiceException("联赛不存在", ErrorCode.BAD_REQUEST);
        }


        leagueDao.save(league);
    }

    @Transactional
    public void delete(Long id) {
        League league = leagueDao.findOne(id);

        if (league == null) {
            throw new ServiceException("联赛不存在", ErrorCode.BAD_REQUEST);
        }

        leagueDao.delete(id);
    }

}
