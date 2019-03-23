package website2018.Enum;

/**
 * Created by Administrator on 2019/1/27.
 */
public enum MatchFlag {

    NEWS_FLAG("1","赛事前瞻"),
    ATIVE_FLAG("2","竞猜活动"),
    NEWS_ATIVE_FLAG("3","赛事前瞻") ;
    private MatchFlag(String code, String desc){
        this.code=code;
        this.desc=desc;
    }
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
