package website2018.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/12/8.
 */
public class DateUtils {

    public static String getDateStr(Date date,String format){
        date=date==null?new Date():date;
        SimpleDateFormat dateStrForQueryFormat = new SimpleDateFormat(format);
        return dateStrForQueryFormat.format(date);
    }

    public static String getDefaultDateStr(Date date){
        date=date==null?new Date():date;
        SimpleDateFormat dateStrForQueryFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateStrForQueryFormat.format(date);
    }
    public static Date getDate(String data,String format){
        if(StringUtils.isEmpty(data)){
            return new Date();
        }
        SimpleDateFormat dateStrForQueryFormat = new SimpleDateFormat(format);
        try {
            return  dateStrForQueryFormat.parse(data);
        } catch (ParseException e) {

        }
        return new Date();
    }

    public static String to16Hex(Date date) {
        Long ab = date.getTime()/1000;
        String a = Long.toHexString(ab);
        return a;
    }
}
