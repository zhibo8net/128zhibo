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
@Table(name = "zhibo_image")
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;

    public String name;

    @ManyToOne
    @JoinColumn(name = "bag_id")
    public ImageBag bag;

    
}
