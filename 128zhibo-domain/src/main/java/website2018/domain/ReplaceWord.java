package website2018.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import website2018.base.BaseEntity;

@Entity
@Table(name = "zhibo_replace_word")
public class ReplaceWord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;

    public String fromWord;// 名称
    public String toWord;// 链接
}
