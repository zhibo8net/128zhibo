package website2018.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website2018.base.BaseEndPoint;
import website2018.cache.CacheUtils;
import website2018.domain.FriendLink;
import website2018.domain.PageAd;
import website2018.service.IndexService;

import java.util.List;

@RestController
public class PageAdController extends BaseEndPoint {

    @Autowired
    IndexService indexService;

    @RequestMapping(value = "/api/web/pageAds")
    public   List<PageAd> pageAds() {

        return indexService.pageAds();
    }


}
