package website2018.dto.admin;

import java.util.Date;

public class AdAdminDTO {

    public Long id;
    
    public String type;// 类别（比赛广告、通用广告、高级广告）
    public String name;// 名称
    public String link;// 链接
    public Date endDate;// 失效日期
    public String endDateStr;//失效日期字符串类型
    public String endTimeStr;//失效时间字符串类型
    public String teams;// 匹配球队
    public int important;// 是否重要
    public int light;// 是否高亮

}
