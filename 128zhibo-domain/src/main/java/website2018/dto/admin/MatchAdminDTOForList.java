package website2018.dto.admin;

import java.util.Date;

public class MatchAdminDTOForList {

    public Long id;
    
    public Date playDate;
    public String playDateStr;
    public String playTime;
    public String project;
    public String game;
    public String name;
    public int locked;// 是否锁定
    public Date unlockTime;// 解锁时间
    public int emphasis;// 是否重点
    public String source;

    public String unlockDateStr;
    public String unlockTimeStr;
}
