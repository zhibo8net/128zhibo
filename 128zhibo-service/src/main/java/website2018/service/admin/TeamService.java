package website2018.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website2018.domain.Image;
import website2018.domain.ImageBag;
import website2018.domain.Team;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.ImageBagDao;
import website2018.repository.ImageDao;
import website2018.repository.TeamDao;

import java.util.List;

@Service
public class TeamService {

    @Value("${upload.webImageBase}")
    public String webImageBase;
    @Value("${upload.uploadPath}")
    public String uploadPath;

    @Autowired
    TeamDao teamDao;

    @Autowired
    ImageBagDao imageBagDao;
    @Autowired
    ImageDao imageDao;

    @Transactional(readOnly = true)
    public List<Team> findAll(Specification spec) {
        return teamDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<Team> findAll(Pageable pageable) {
        return teamDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Team> findAll(Specification<Team> specification, Pageable pageable) {
        return teamDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Team findOne(Long id) {
        return teamDao.findOne(id);
    }

    @Transactional
    public void create(Team team) {

        teamDao.save(team);
    }

    @Transactional
    public void modify(Team team) {

        Team orginalTeam = teamDao.findOne(team.id);

        if (orginalTeam == null) {
            throw new ServiceException("球队不存在", ErrorCode.BAD_REQUEST);
        }


        teamDao.save(team);
    }

    @Transactional
    public void delete(Long id) {
        Team team = teamDao.findOne(id);

        if (team == null) {
            throw new ServiceException("球队不存在", ErrorCode.BAD_REQUEST);
        }

        teamDao.delete(id);
    }

}
