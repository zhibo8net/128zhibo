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
@Table(name = "zhibo_ad")
public class Ad extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;

    public String type;// 类别（比赛广告、通用广告、高级广告）
    public String name;// 名称
    public String link;// 链接
    public Date endDate;// 失效日期
    public String teams;// 匹配球队
    public int important;// 是否重要
    public int light;// 是否高亮

    @ManyToOne
    @JoinColumn(name = "match_id")
    public Match match;// 所属比赛
}
