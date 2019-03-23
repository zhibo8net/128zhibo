package website2018.dto.admin;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import website2018.base.BaseEntity;

public class ConfigAdminDTO extends BaseEntity {
    public Long id;
    public Date addTime;
    
    public String ckey; //键
    public String cvalue; //值
    public String cdesc; //说明
    
}
