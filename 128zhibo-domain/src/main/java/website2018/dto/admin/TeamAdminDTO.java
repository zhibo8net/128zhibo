package website2018.dto.admin;

import website2018.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/5.
 */

public class TeamAdminDTO extends BaseEntity {

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
