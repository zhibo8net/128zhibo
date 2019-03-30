package website2018.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website2018.base.BaseEndPoint;
import website2018.cache.CacheUtils;
import website2018.domain.FriendLink;
import java.util.List;

@RestController
public class FriendLinkController extends BaseEndPoint {


    @RequestMapping(value = "/api/web/friendLink")
    public List<FriendLink> addIssue() {
        List<FriendLink> friendLinks = CacheUtils.getListFriendLink();
        return friendLinks;
    }


}
