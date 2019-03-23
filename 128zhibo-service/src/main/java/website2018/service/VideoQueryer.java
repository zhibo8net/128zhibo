package website2018.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.BeanMapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import website2018.domain.Video;
import website2018.repository.VideoDao;
import website2018.utils.DynamicSpecifications;
import website2018.utils.SearchFilter;

@Service
public class VideoQueryer {

    @Autowired
    VideoDao videoDao;
    
    public List<Video> findByProjectGameTypeCount(String project, String game, String type, int count){
        return findByProjectGameTypeCount(project, game, type, count, false);
    }
    
    public List<Video> findByProjectGameTypeCount(String project, String game, String type, int count, boolean editName){
        Pattern p = Pattern.compile("(\\[.*\\])?.*\\s(\\S+vs\\S+)\\s.*");
        
        Map<String, Object> searchParams = Maps.newHashMap();
        if(StringUtils.isNotBlank(project)) {
            searchParams.put("EQ_project", project);
        }
        if(StringUtils.isNotBlank(game)) {
            searchParams.put("EQ_game", game);
        }
        if(type.equals("视频")) {
            searchParams.put("IN_type", "视频,片段");
        }else {
            searchParams.put("EQ_type", type);
        }
        if("足球".equals(project) && "录像".equals(type)) {
            searchParams.put("LIKE_name", "全场");
        }

        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<Video> spec = DynamicSpecifications.bySearchFilter(filters.values(), Video.class);
        
        //如果是录像，就多取几个
        int fetchCount = (editName && "录像".equals(type)) ? count * 10 : count;
        Page page = videoDao.findAll(spec, new PageRequest(0, fetchCount, new Sort(Direction.DESC, "id")));
        List<Video> list = page.getContent();
        if(editName && "录像".equals(type)) {
            List<Video> result = Lists.newArrayList();
            Set<String> matchNames = Sets.newHashSet();
            for(Video v : list) {
                Video newOne = BeanMapper.map(v, Video.class);
                
                Matcher m = p.matcher(newOne.name);
                if(m.matches()) {
                    String channel = m.group(1);
                    String matchName = m.group(2);
                    if(StringUtils.isNotBlank(channel) && (! matchNames.contains(matchName)) && (result.size() < count)) {
                        newOne.name = StringUtils.defaultString(channel, "") + matchName;
                        result.add(newOne);
                        matchNames.add(matchName);
                    }
                }
            }
            list = result;
        }
        
        return list;
    }
    
}
