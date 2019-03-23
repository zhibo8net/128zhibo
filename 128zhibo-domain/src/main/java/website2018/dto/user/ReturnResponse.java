package website2018.dto.user;

/**
 * Created by Administrator on 2018/9/24.
 */
public class ReturnResponse {

    public String message;

    public String code;

    public Object data;
    public ReturnResponse(){

    }
    public ReturnResponse(String code, String message){
        this.code=code;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
