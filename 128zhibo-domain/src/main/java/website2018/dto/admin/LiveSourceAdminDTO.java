package website2018.dto.admin;

import java.util.Date;

public class LiveSourceAdminDTO {

    public Long id;
    public String name;
    public String link;
    public int fetchInterval;
    public Date lastFetch;
    public String channels;
    public int active;
    public String innerPlayChannels;

}
