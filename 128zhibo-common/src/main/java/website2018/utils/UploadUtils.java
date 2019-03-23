package website2018.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadUtils {
    
    //返回例如2018/2/2/aakjfkasjfk.jpeg
    public static String nameWithTimePrefix(String filename) {
        String timePrefix = new SimpleDateFormat("yyyy"+File.separator+"MM"+File.separator+"dd").format(new Date());
        return timePrefix + File.separator + filename;
    }
}
