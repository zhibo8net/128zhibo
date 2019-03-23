package website2018.Enum;

/**
 * Created by Administrator on 2019/1/27.
 */
public enum IssueStatus {
    INIT("INIT","初始化"),
    DOING("DOING","竞猜中"),
    MATCH_END("MATCHEND","比赛结束"),
    DRAWING("DRAWING","开奖中"),
    DRAW("DRAW","已开奖"),
    PIE_AWARD("PIEAWARD","已派奖")
    ;
    private  IssueStatus(String code,String desc){
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
