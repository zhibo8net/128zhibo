package website2018.dto.video;

import website2018.domain.Ended;

import java.util.Date;

/**
 * Created by Administrator on 2018/10/14.
 */
public class VideoDTO {

    public String isFisrt;
    public Long id;
    public Date addTime;

    public String project;
    public String game;
    public String type;//集锦 录像 片段 视频
    public String name;
    //播放地址
    public String link;
    public String image;

    public Ended ended;
    public String source;
}
