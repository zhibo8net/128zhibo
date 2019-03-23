package website2018.dto.admin;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class VideoAdminDTO {

    public Long id;
    @JsonFormat(pattern="yyyy-MM-dd") public Date addTime;

    public String project;
    public String game;
    public String type;//集锦 录像 片段 视频
    public String name;
    public String link;
    public String image;

    public EndedAdminDTO ended;
}
