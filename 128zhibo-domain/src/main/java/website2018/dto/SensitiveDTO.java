package website2018.dto;

import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;


public class SensitiveDTO extends BaseEntity {
    public Long id;
    public String content;
    public Date addTime;
    public Date updateTime;
}
