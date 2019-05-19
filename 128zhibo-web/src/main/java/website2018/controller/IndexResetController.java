package website2018.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import website2018.base.BaseEndPoint;
import website2018.dto.DailyLivesDTO;
import website2018.service.IndexService;

import java.util.List;

@RestController
public class IndexResetController extends BaseEndPoint {

    @Autowired
    IndexService indexService;

    @RequestMapping(value = "/api/web/indexMatchList")
    public  List<DailyLivesDTO>  queryDailyLives(@RequestParam(defaultValue="")String game) {

        return indexService.dailyLives(game);
    }


}
