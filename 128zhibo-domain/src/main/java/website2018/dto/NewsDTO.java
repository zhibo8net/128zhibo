package website2018.dto;

import website2018.domain.News;

import java.util.Date;

public class NewsDTO {

    public Long id;
    public Date addTime;
    public String createTime;

    public String title;
    public String source;
    public String project;
    public String game;
    public String image;
    public String content;
    public Integer readCount;
    public boolean isFirstRow;
}
