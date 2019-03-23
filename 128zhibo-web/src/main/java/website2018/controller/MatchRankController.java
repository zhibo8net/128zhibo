package website2018.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;
import website2018.base.BaseEndPoint;
import website2018.domain.Match;
import website2018.dto.match.BasketMatchRankDTO;
import website2018.dto.match.BasketMatchTelRankDTO;
import website2018.dto.match.FootBallJSBRankDTO;
import website2018.dto.match.FootBallSSBRankDTO;
import website2018.dto.user.ReturnResponse;
import website2018.dto.user.UserDTO;
import website2018.repository.MatchDao;
import website2018.service.MatchRankService;
import website2018.service.user.UserLoginService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MatchRankController extends BaseEndPoint {
    
    @Autowired
    MatchRankService matchRankService;

    @Autowired
    MatchDao matchDao;

    @RequestMapping(value = "/api/matchRank/basketBallRank", produces = MediaTypes.JSON_UTF_8)
    public BasketMatchRankDTO basketBallRank() {



        return matchRankService.queryBasketballRankDTO();
    }
    @RequestMapping(value = "/api/matchRank/basketBallRankTel", produces = MediaTypes.JSON_UTF_8)
    public BasketMatchTelRankDTO basketBallRankTel() {



        return matchRankService.queryBasketMatchTelRankDTO();
    }

    @RequestMapping(value = "/api/matchRank/footballJsb", produces = MediaTypes.JSON_UTF_8)
    public FootBallJSBRankDTO footballJsb() {



        return matchRankService.queryFootBallJSBRankDTO();
    }

    @RequestMapping(value = "/api/matchRank/footballSsb", produces = MediaTypes.JSON_UTF_8)
    public FootBallSSBRankDTO footballSsb() {



        return matchRankService.queryFootBallSSBRankDTO();
    }


    @RequestMapping(value = "/api/matchRank/{id}", produces = MediaTypes.JSON_UTF_8)
    public ReturnResponse userLogin(@PathVariable Long id,@RequestBody(required=false) String type,HttpServletRequest request) {
        ReturnResponse returnResponse=new ReturnResponse();
            Match match=matchDao.findById(id);
            if(match==null){
                returnResponse.code="0001";
                returnResponse.message="比赛不存在";
                return returnResponse;
            }

        if("master".equals(type)){
            match.masterTeamSupport=match.masterTeamSupport+1;
        }else{
            match.guestTeamSupport=match.guestTeamSupport+1;
        }
        matchDao.save(match);
        returnResponse.code="0000";
        returnResponse.message="操作成功";
        return returnResponse;
    }
}
