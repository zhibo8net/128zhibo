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
@Table(name = "zhibo_image_bag")
public class ImageBag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;

    public String title;
    public String source;
    public String project;
    public Integer readCount;
    @OneToMany(mappedBy = "bag", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Image> images = Lists.newArrayList();
    
}
