package website2018.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.google.common.collect.Lists;

import website2018.base.BaseEntity;

@Entity
@Table(name = "zhibo_match")
public class Match extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;

    public Date playDate;
    public String playDateStr;
    public String playTime;
    public String project;
    public String game;
    public String rotation;
    public String name;
    public int locked;// 是否锁定
    public Date unlockTime;// 解锁时间
    public int emphasis;// 是否重点
    public String source;

    //新浪直播url
    public String sinaLiveUrl;
    //新浪数据Url
    public String sinaShujuUrl;

    //sstream365 url
    public String matchStreamUrl;

    public String matchNewFlag;

    public Integer masterTeamSupport;

    public Integer guestTeamSupport;

    public String matchActiveFlag;

    @OneToMany(mappedBy = "match", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Live> lives = Lists.newArrayList();


    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Ad> ads = Lists.newArrayList();

    @OneToOne
    @JoinColumn(name = "master_team_id")
    public Team masterTeam;

    @OneToOne
    @JoinColumn(name = "guest_team_id")
    public Team guestTeam;

    @OneToOne
    @JoinColumn(name = "league_id")
    public League league;
}
