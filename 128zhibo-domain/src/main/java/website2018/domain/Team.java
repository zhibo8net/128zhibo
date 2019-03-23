package website2018.domain;

import website2018.base.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/5.
 */
@Entity
@Table(name = "zhibo_team")
public class Team extends BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String teamEn;
    public String teamZh;
    public String teamName1;
    public String teamName2;

    public String teamName3;
    public String teamImgLink;
    public Date addTime;
    public Date updateTime;

}
