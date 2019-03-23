package website2018.dto.admin;

import java.util.List;

import com.google.common.collect.Lists;

public class ImageBagAdminDTO {

    public Long id;
    
    public String title;
    public String source;
    public String project;

    public List<ImageAdminDTO> images = Lists.newArrayList();
}
