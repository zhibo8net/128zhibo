package website2018.domain;

import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "zhibo_match_stream")
public class MatchStream extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;
    public Date updateTime;
    public String playTime;
    public String project;
    public String game;
    public String matchName;
    public String matchStreamId;
    public String matchStreamName;

    @OneToOne
    @JoinColumn(name = "master_team_id")
    public Team masterTeam;

    @OneToOne
    @JoinColumn(name = "guest_team_id")
    public Team guestTeam;

}
