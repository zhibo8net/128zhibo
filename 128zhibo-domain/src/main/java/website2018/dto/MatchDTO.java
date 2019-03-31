package website2018.dto;

import java.util.List;

import com.google.common.collect.Lists;
import website2018.domain.News;

public class MatchDTO {

    public String playTime;
    public String project;
    public String game;
    public String rotation;
    public String name;
    public Long id;

    public List<NewsNoContentDTO> newsNoContentDTOList =Lists.newArrayList();
    public List<LiveDTO> lives = Lists.newArrayList();
    public List<AdDTO> ads = Lists.newArrayList();

    public int emphasis;
    public int important;

    public String teamFlag="FALSE";
    public String guestTeamName;
    public String guestTeamLink;
    public String masterTeamName;
    public String masterTeamLink;
    //新浪直播url
    public String sinaLiveUrl;
    //新浪数据Url
    public String sinaShujuUrl;

    public String matchStreamUrl;

    public String playDateStr;

    public Integer masterTeamSupport;

    public Integer guestTeamSupport;




}
