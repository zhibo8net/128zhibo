package website2018.Enum;

/**
 * Created by Administrator on 2019/1/27.
 */
public enum ProblemType {

    RADIO("RADIO","单选-选项"),
    CHECKBOX("CHECKBOX","多选-选项")
    ;
    private ProblemType(String code, String desc){
        this.code=code;
        this.desc=desc;
    }
    private String code;
    private String desc;
    public static ProblemType getEnum(String value) {
        for (ProblemType paymentTypeEnum : ProblemType.values()) {
            if (value.equals(paymentTypeEnum.getCode())) {
                return paymentTypeEnum;
            }
        }
        return null;
    }
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
