package website2018.dto.admin;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LogAdminDTO {

    public Long id;
    
    public AccountAdminDTO account;
    @JsonFormat(pattern="yyyy-MM-dd") public Date addTime;
    public String addTimeStr;
    public String ip;
    public String behaviour;
    public Long entityId;
    public String link;

}
