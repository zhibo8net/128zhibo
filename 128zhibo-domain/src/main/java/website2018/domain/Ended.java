package website2018.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;

import website2018.base.BaseEntity;

@Entity
@Table(name = "zhibo_ended")
public class Ended extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;

    public String project;
    public String game;
    public String name;
    public String source;

    @OneToMany(mappedBy = "ended", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Video> videos = Lists.newArrayList();
}
