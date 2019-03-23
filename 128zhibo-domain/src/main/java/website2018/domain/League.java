package website2018.domain;

import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/5.
 */
@Entity
@Table(name = "zhibo_league")
public class League extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long projectId;

    public String projectZh;

    public String leagueEn;
    public String leagueZh;
    public String leagueName1;
    public String leagueName2;

    public String leagueName3;
    public String leagueImgLink;
    public Date addTime;
    public Date updateTime;
    public Integer teamNum;

}
