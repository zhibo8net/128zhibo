package website2018.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/11/24.
 */
public class MobileUtils {

    public static boolean isMobileNO(String mobiles) {

        // Pattern p =
        // Pattern.compile("^((147)|(17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Pattern p = Pattern.compile("^((19[0-9])|(18[0-9])|(17[0-9])|(16[0-9])|(14[0-9])|(13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String hiddenMobile(String str){
        return hiddenStr(str,3,7,"*");
    }
    public static String hiddenStr(String str, Integer startIndex, Integer endIndex, String encryptionStr){
        try {
            if (str.length()<=1) {
                return str;
            }
            if (null == encryptionStr){
                encryptionStr = "";
            }
            String xxStr = "";
            String lenStr = str.trim().substring(startIndex,endIndex);
            for (int i = 0, len = lenStr.length(); i < len; i++) {
                xxStr += encryptionStr;
            }
            xxStr = str.replaceFirst(lenStr, xxStr);
            return xxStr;
        }
        catch (Exception e){
            return str;
        }
    }
}
