package website2018.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import website2018.base.BaseEntity;

@Entity
@Table(name = "zhibo_video")
public class Video extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;

    public String project;
    public String game;
    public String type;//集锦 录像 片段 视频
    public String name;
    public String link;
    public String image;
    @ManyToOne
    @JoinColumn(name = "ended_id")
    public Ended ended;
    public String source;

}
