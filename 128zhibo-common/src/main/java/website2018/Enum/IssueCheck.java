package website2018.Enum;

/**
 * Created by Administrator on 2019/1/27.
 */
public enum IssueCheck {
    CHECKED("CHECKED","选中"),
    UNCHECKED("UNCHECKED","未选中"),
    ;
    private IssueCheck(String code, String desc){
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
