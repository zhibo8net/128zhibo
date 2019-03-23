package website2018.Enum;

/**
 * Created by Administrator on 2019/1/27.
 */
public enum ProblemFlag {

    DEFALUT("DEFAULT","默认题目"),
    NORMAL("NORMAL","一般题目")
    ;
    private ProblemFlag(String code, String desc){
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
