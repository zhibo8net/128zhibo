package website2018.dto;

import java.util.List;

import com.google.common.collect.Lists;

public class DailyLivesDTO {

    public String dateStr;
    public String playDateStr;
    public List<MatchDTO> matches = Lists.newArrayList();

}
