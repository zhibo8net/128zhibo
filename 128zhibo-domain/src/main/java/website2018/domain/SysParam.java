package website2018.domain;

import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/5.
 */
@Entity
@Table(name = "zhibo_sys_param")
public class SysParam extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Date addTime;
    public Date updateTime;

    public String  sysKey ;
    public String sysValue ;
    public String sysRemark;
}
