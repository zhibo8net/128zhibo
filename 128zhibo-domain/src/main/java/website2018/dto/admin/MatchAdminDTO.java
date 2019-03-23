package website2018.dto.admin;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class MatchAdminDTO {

    public Long id;
    
    public Date playDate;
    public String playDateStr;
    public String playTime;
    public String project;
    public String game;
    public String name;
    public int locked;// 是否锁定
    public Date unlockTime;// 解锁时间
    public int emphasis;// 是否重点
    public String source;

    public List<LiveAdminDTO> lives = Lists.newArrayList();
    public List<AdAdminDTO> ads = Lists.newArrayList();
    
    public String unlockDateStr;
    public String unlockTimeStr;

    //新浪直播url
    public String sinaLiveUrl;
    //新浪数据Url
    public String sinaShujuUrl;

    //sstream365 url
    public String matchStreamUrl;
}
