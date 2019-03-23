package website2018.Enum;

/**
 * Created by Administrator on 2019/1/27.
 */
public enum IssueUserStatus {
    INIT("INIT","待开奖"),
    AWARD("AWARD","中奖"),
    UMAWARD("UNAWARD","未中奖")
    ;
    private IssueUserStatus(String code, String desc){
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
