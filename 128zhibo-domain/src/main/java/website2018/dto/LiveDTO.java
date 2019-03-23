package website2018.dto;

import java.util.List;

import com.google.common.collect.Lists;

public class LiveDTO {

    public String name;
   public String link;
    public List<SignalDTO> signals = Lists.newArrayList();
}
