package website2018.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Maps;

import org.springside.modules.utils.mapper.BeanMapper;
import website2018.base.BaseEndPoint;
import website2018.domain.News;
import website2018.domain.Video;
import website2018.dto.NewsDTO;
import website2018.dto.match.BasketMatchRankDTO;
import website2018.dto.match.BasketMatchTelRankDTO;
import website2018.dto.match.FootBallJSBRankDTO;
import website2018.dto.match.FootBallSSBRankDTO;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.repository.EndedDao;
import website2018.repository.NewsDao;
import website2018.repository.VideoDao;
import website2018.service.MatchRankService;
import website2018.utils.StrUtils;

@Controller
public class NewsController extends BaseEndPoint {

    @Autowired EndedDao endedDao;
    @Autowired VideoDao videoDao;
    @Autowired NewsDao newsDao;
    @Autowired
    MatchRankService matchRankService;

    @RequestMapping(value = "/mnews")
   public String mnews(Model model) {
            List<News> mnewsList=newsDao.findTop100ByMatchPreFlagOrderByIdDesc("0");
            for(News n:mnewsList){
                n.content=null;
            }
          model.addAttribute("mnewsList", mnewsList);
             return "mnews";
}

    @RequestMapping(value = "/news_1", method = RequestMethod.GET)
    public String news(@RequestParam(defaultValue="") String project,@RequestParam(defaultValue="") String game, @RequestParam(defaultValue="0") Integer pageNumber, Model model) {

        boolean all = project.equals("") && game.equals("");
        boolean other = project.equals("") && game.equals("其他");
        
        if(! (all || other || projectWhiteList.contains(project))) {
            return "redirect:http://www.zhibo8.net/";
        }
        if(! (all || other || gameWhiteList.contains(game))) {
            return "redirect:http://www.zhibo8.net/";
        }
        
        model.addAttribute("project", project);
        model.addAttribute("projectEnglish", project.equals("足球") ? "football" : "basketball");
        model.addAttribute("game", game);
        model.addAttribute("pageTitle", "新闻资讯");
        Map<String, Object> requestMap = Maps.newHashMap();
        if (!all) {
            requestMap.put("project", project);
            requestMap.put("game", game);
        }
        Page page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(pageNumber, 12, getSort("id", "DIRE")));
        model.addAttribute("page", page);
        String search = BaseEndPoint.encodeParameterStringWithPrefix(requestMap, "");
        model.addAttribute("pageUrl", "?" + search);

        List<Video> videos;
        if(all) {
            videos = videoQueryer.findByProjectGameTypeCount(null, null, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
        }else if(other) {
            videos = videoQueryer.findByProjectGameTypeCount(null, "其他", "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
        }else {
            videos = videoQueryer.findByProjectGameTypeCount(project, game, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT);
        }
        model.addAttribute("videos", videos);

        List<Video> luxiangs;
        if(all) {
            luxiangs = videoQueryer.findByProjectGameTypeCount(null, null, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true);
        }else if(other) {
            luxiangs = videoQueryer.findByProjectGameTypeCount(null, "其他", "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true);
        }else {
            luxiangs = videoQueryer.findByProjectGameTypeCount(project, game, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true);
        }
        model.addAttribute("luxiangs", luxiangs);

        model.addAttribute("menu", "news");
        return "news";
    }


        @RequestMapping(value = "/nba_news", method = RequestMethod.GET)
    public String nbaNews(Model model) {

        //查询滚动图片
        List<News> sliderList=newsDao.findTop5ByProjectAndGameAndImageNotAndMatchPreFlagOrderByUpdateTimeDesc("篮球", "NBA", "", "0");
        model.addAttribute("sliderList", sliderList);
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("project", "篮球");
        requestMap.put("game", "NBA");
        requestMap.put("matchPreFlag","0");
        Page page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 33, getSort("updateTime", "DIRE")));
        List<News> topNewsList1=page.getContent();
        for(News n:topNewsList1){
            if(StringUtils.isNotEmpty(n.title)&&n.title.length()>=15){
                n.title=n.title.substring(0,15);
            }
        }
            if(topNewsList1.size()>=2){
                model.addAttribute("h2TopNewsList1", topNewsList1.subList(0,2));
            }
            if(topNewsList1.size()>=11){
                model.addAttribute("pTopNewsList1", topNewsList1.subList(2,11));
            }
            if(topNewsList1.size()>=13){
                model.addAttribute("h2TopNewsList2", topNewsList1.subList(11,13));
            }
            if(topNewsList1.size()>=22){
                model.addAttribute("pTopNewsList2", topNewsList1.subList(13,22));
            }
            if(topNewsList1.size()>=24){
                model.addAttribute("h2TopNewsList3", topNewsList1.subList(22,24));
            }
            if(topNewsList1.size()>=33){
                model.addAttribute("pTopNewsList3", topNewsList1.subList(24,33));
            }
             page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(1, 33, getSort("updateTime", "DIRE")));
             List<News> newsListNBA=page.getContent();
            if(newsListNBA.size()>=20){
                model.addAttribute("newsListNBA", newsListNBA.subList(0,20));
            }else{
                model.addAttribute("newsListNBA",newsListNBA);
            }
        requestMap = Maps.newHashMap();
        requestMap.put("project", "篮球");
        requestMap.put("game", "CBA");
        requestMap.put("matchPreFlag","0");
        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListCBA=page.getContent();
            model.addAttribute("newsListCBA", newsListCBA);
        requestMap = Maps.newHashMap();
        requestMap.put("project", "篮球");
        requestMap.put("game", "其他");
        requestMap.put("matchPreFlag","0");
        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListOther=page.getContent();
        model.addAttribute("newsListOther", newsListOther);


        BasketMatchRankDTO basketMatchRankDTO= matchRankService.queryBasketballRankDTO();
        if(basketMatchRankDTO!=null){
            model.addAttribute("NBAWestMatchRankList", basketMatchRankDTO.NBAWestMatchRankList);
            model.addAttribute("NBAEastMatchRankList", basketMatchRankDTO.NBAEastMatchRankList);
            model.addAttribute("CBAMatchRankList", basketMatchRankDTO.CBAMatchRankList);
        }

        BasketMatchTelRankDTO basketMatchTelRankDTO= matchRankService.queryBasketMatchTelRankDTO();
        if(basketMatchTelRankDTO!=null){
            model.addAttribute("DSMatchTelRankList", basketMatchTelRankDTO.DSMatchTelRankList);
            model.addAttribute("LBMatchTelRankList", basketMatchTelRankDTO.LBMatchTelRankList);
            model.addAttribute("ZGMatchTelRankList", basketMatchTelRankDTO.ZGMatchTelRankList);
            model.addAttribute("QDMatchTelRankList", basketMatchTelRankDTO.QDMatchTelRankList);
            model.addAttribute("GMMatchTelRankList", basketMatchTelRankDTO.GMMatchTelRankList);
        }
        return "nba_news";
    }

    @RequestMapping(value = "/footer_news", method = RequestMethod.GET)
    public String footerNews(Model model) {

        //查询滚动图片
        List<News> sliderList=newsDao.findTop5ByProjectAndMatchPreFlagAndImageNotOrderByUpdateTimeDesc("足球", "0", "");
        model.addAttribute("sliderList", sliderList);
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("project", "足球");
        requestMap.put("matchPreFlag","0");
        Page page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 33, getSort("updateTime", "DIRE")));
        List<News> topNewsList1=page.getContent();
        for(News n:topNewsList1){
            if(StringUtils.isNotEmpty(n.title)&&n.title.length()>=15){
                n.title=n.title.substring(0,15);
            }
        }
        if(topNewsList1.size()>=2){
            model.addAttribute("h2TopNewsList1", topNewsList1.subList(0,2));
        }
        if(topNewsList1.size()>=11){
            model.addAttribute("pTopNewsList1", topNewsList1.subList(2,11));
        }
        if(topNewsList1.size()>=13){
            model.addAttribute("h2TopNewsList2", topNewsList1.subList(11,13));
        }
        if(topNewsList1.size()>=22){
            model.addAttribute("pTopNewsList2", topNewsList1.subList(13,22));
        }
        if(topNewsList1.size()>=24){
            model.addAttribute("h2TopNewsList3", topNewsList1.subList(22,24));
        }
        if(topNewsList1.size()>=33){
            model.addAttribute("pTopNewsList3", topNewsList1.subList(24,33));
        }
        requestMap = Maps.newHashMap();
        requestMap.put("project", "足球");
        requestMap.put("game", "英超");
        requestMap.put("matchPreFlag","0");

        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListyc=page.getContent();
        model.addAttribute("newsListyc", newsListyc);

        requestMap.put("game", "意甲");
        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListyj=page.getContent();
        model.addAttribute("newsListyj", newsListyj);


        requestMap.put("game", "西甲");
        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListxj=page.getContent();
        model.addAttribute("newsListxj", newsListxj);


        requestMap.put("game", "法甲");
        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListfj=page.getContent();
        model.addAttribute("newsListfj", newsListfj);

        requestMap.put("game", "德甲");
        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListdj=page.getContent();
        model.addAttribute("newsListdj", newsListdj);

        requestMap.put("game", "中超");
        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListzc=page.getContent();
        model.addAttribute("newsListzc", newsListzc);

        requestMap.put("game", "欧冠");
        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListog=page.getContent();
        model.addAttribute("newsListog", newsListog);


        requestMap.put("game", "世界杯");
        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListsjb=page.getContent();
        model.addAttribute("newsListsjb", newsListsjb);
        requestMap = Maps.newHashMap();
        requestMap.put("project", "足球");
        requestMap.put("game", "其他");
        requestMap.put("matchPreFlag","0");

        page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(0, 20, getSort("updateTime", "DIRE")));
        List<News> newsListOther=page.getContent();
        model.addAttribute("newsListOther", newsListOther);


        FootBallJSBRankDTO footBallJSBRankDTO= matchRankService.queryFootBallJSBRankDTO();
        if(footBallJSBRankDTO!=null){
            model.addAttribute("zcFootballJsbList", footBallJSBRankDTO.zcFootballJsbList);
            model.addAttribute("ycFootballJsbList", footBallJSBRankDTO.ycFootballJsbList);
            model.addAttribute("xjFootballJsbList", footBallJSBRankDTO.xjFootballJsbList);

            model.addAttribute("yjFootballJsbList", footBallJSBRankDTO.yjFootballJsbList);
            model.addAttribute("djFootballJsbList", footBallJSBRankDTO.djFootballJsbList);
            model.addAttribute("fjFootballJsbList", footBallJSBRankDTO.fjFootballJsbList);
        }
        FootBallSSBRankDTO footBallSSBRankDTO= matchRankService.queryFootBallSSBRankDTO();
        if(footBallSSBRankDTO!=null){
            model.addAttribute("zcFootballSsbList", footBallSSBRankDTO.zcFootballSsbList);
            model.addAttribute("ycFootballSsbList", footBallSSBRankDTO.ycFootballSsbList);
            model.addAttribute("xjFootballSsbList", footBallSSBRankDTO.xjFootballSsbList);

            model.addAttribute("yjFootballSsbList", footBallSSBRankDTO.yjFootballSsbList);
            model.addAttribute("djFootballSsbList", footBallSSBRankDTO.djFootballSsbList);
            model.addAttribute("fjFootballSsbList", footBallSSBRankDTO.fjFootballSsbList);

            model.addAttribute("ogFootballSsbList", footBallSSBRankDTO.ogFootballSsbList);
            model.addAttribute("ygFootballSsbList", footBallSSBRankDTO.ygFootballSsbList);
        }
        return "footer_news";
    }
        @RequestMapping(value = "/news_1/{id}", method = RequestMethod.GET)
    public String newsInner(@PathVariable Long id, Model model) {
        News news = newsDao.findOne(id);
        model.addAttribute("news", news);
            if(news==null){
                return "redirect:http://www.zhibo8.net/";
            }
        news.readCount=   news.readCount==null?1: news.readCount+1;
        newsDao.save(news);

        String project = news.project;
        String game = news.game;
        model.addAttribute("pageTitle", news.title);
        model.addAttribute("game", game);
        model.addAttribute("videos", videoQueryer.findByProjectGameTypeCount(project, game, "视频", BaseEndPoint.RIGHT_VIDEO_COUNT));
        model.addAttribute("luxiangs", videoQueryer.findByProjectGameTypeCount(project, game, "录像", BaseEndPoint.RIGHT_LUXIANG_COUNT, true));

        model.addAttribute("menu", "news");
        return "newsInner";
    }


    @RequestMapping(value = "/news_detail/{id}", method = RequestMethod.GET)
    public String news_new_1(@PathVariable Long id, Model model) {
        News news = newsDao.findOne(id);
        if(news==null){
            return "redirect:http://www.zhibo8.net/";
        }


        news.readCount=   news.readCount==null?1: news.readCount+1;
        newsDao.save(news);

        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        for(int i=1;i<100;i++){
            News nextNews = newsDao.findOne(id+i);

            if(nextNews!=null){
                NewsDTO nextmdto = BeanMapper.map(nextNews, NewsDTO.class);
                nextmdto.createTime=sdf.format(news.addTime==null?new Date():news.addTime);
                model.addAttribute("nextNews", nextmdto);
                break;
            }
        }

        for(int j=1;j<100;j++){
            News preNews = newsDao.findOne(id-j);

            if(preNews!=null) {
                NewsDTO pretmdto = BeanMapper.map(preNews, NewsDTO.class);

                pretmdto.createTime = sdf.format(news.addTime == null ? new Date() : news.addTime);
                model.addAttribute("preNews", pretmdto);
                break;
            }
        }
        NewsDTO mdto = BeanMapper.map(news, NewsDTO.class);

        mdto.createTime=sdf.format(news.addTime==null?new Date():news.addTime);
        model.addAttribute("news", mdto);
        String newsPageDesc=StrUtils.delHTMLTag(StringUtils.isEmpty(mdto.content) ? "" : mdto.content);
        newsPageDesc=newsPageDesc.length()>=250?newsPageDesc.substring(0,200):newsPageDesc;
        newsPageDesc=  newsPageDesc.replaceAll("  ","").replaceAll("\\r\\n","").replaceAll("\\r","").replaceAll("\\n","");
        model.addAttribute("newsPageDesc", newsPageDesc );
        return "news_detail";
    }

    @RequestMapping(value = "/mdetail/{id}", method = RequestMethod.GET)
    public String mdetail(@PathVariable Long id, Model model) {
        News news = newsDao.findOne(id);
        if(news==null){
            return "redirect:http://www.zhibo8.net/mindex.html";
        }


        news.readCount=   news.readCount==null?1: news.readCount+1;
        newsDao.save(news);

        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        for(int i=1;i<100;i++){
            News nextNews = newsDao.findOne(id+i);

            if(nextNews!=null){
                NewsDTO nextmdto = BeanMapper.map(nextNews, NewsDTO.class);
                nextmdto.createTime=sdf.format(news.addTime==null?new Date():news.addTime);
                model.addAttribute("nextNews", nextmdto);
                break;
            }
        }

        for(int j=1;j<100;j++){
            News preNews = newsDao.findOne(id-j);

            if(preNews!=null) {
                NewsDTO pretmdto = BeanMapper.map(preNews, NewsDTO.class);

                pretmdto.createTime = sdf.format(news.addTime == null ? new Date() : news.addTime);
                model.addAttribute("preNews", pretmdto);
                break;
            }
        }
        NewsDTO mdto = BeanMapper.map(news, NewsDTO.class);

        mdto.createTime=sdf.format(news.addTime==null?new Date():news.addTime);
        model.addAttribute("news", mdto);
        String newsPageDesc=StrUtils.delHTMLTag(StringUtils.isEmpty(mdto.content) ? "" : mdto.content);
        newsPageDesc=newsPageDesc.length()>=250?newsPageDesc.substring(0,200):newsPageDesc;
        newsPageDesc=  newsPageDesc.replaceAll("  ","").replaceAll("\\r\\n","").replaceAll("\\r","").replaceAll("\\n","");
        model.addAttribute("newsPageDesc", newsPageDesc );
        return "mdetail";
    }
    @RequestMapping(value = "/prospect", method = RequestMethod.GET)
    public String prospect(@RequestParam(defaultValue="0") Integer pageNumber, Model model) {


        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("matchPreFlag","1");
        Page page = newsDao.findAll(buildSpecification(requestMap, News.class), new PageRequest(pageNumber, 20, getSort("id", "DIRE")));

        model.addAttribute("page", page);
        String search = BaseEndPoint.encodeParameterStringWithPrefix(requestMap, "");
        model.addAttribute("pageUrl", "?" + search);

        return "prospect";
    }
}
