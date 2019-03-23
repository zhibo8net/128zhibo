package website2018.dto;

import java.util.List;

import com.google.common.collect.Lists;

import website2018.domain.Video;

public class ProjectVideo {
    public String name;
    public List<Video> videos = Lists.newArrayList();
}
