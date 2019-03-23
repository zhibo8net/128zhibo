package website2018.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/12/8.
 */
public class StrUtils {

  //过滤通过页面表单提交的字符   
       private static String[][] FilterChars={{"<","&lt;"},{">","&gt;"},{" ","&nbsp;"},{"\"","&quot;"},{"&","&amp;"},   
                                       {"/","&#47;"},{"\\","&#92;"},{"\n","<br>"}};   
       //过滤通过javascript脚本处理并提交的字符   
       private static String[][] FilterScriptChars={{"\n","\'+\'\\n\'+\'"},   
                                                           {"\r"," "},{"\\","\'+\'\\\\\'+\'"},   
                                                                   {"\'","\'+\'\\\'\'+\'"}};   
  
       /**  
        * 用特殊的字符连接字符串  
        * @param strings 要连接的字符串数组  
        * @param spilit_sign 连接字符  
        * @return 连接字符串  
        */  
       public static String stringConnect(String[] strings,String spilit_sign){   
         String str="";   
         for(int i=0;i<strings.length;i++){   
           str+=strings[i]+spilit_sign;   
         }   
         return str;   
       }   
  
       /**  
        * 过滤字符串里的的特殊字符  
        * @param str 要过滤的字符串  
        * @return 过滤后的字符串  
        */  
       public static String stringFilter(String str){   
         String[] str_arr=stringSpilit(str,"");   
         for(int i=0;i<str_arr.length;i++){   
           for(int j=0;j<FilterChars.length;j++){   
             if(FilterChars[j][0].equals(str_arr[i]))   
               str_arr[i]=FilterChars[j][1];   
           }   
         }   
         return (stringConnect(str_arr,"")).trim();   
       }   
  
       /**  
* 过滤脚本中的特殊字符（包括回车符(\n)和换行符(\r)）  
* @param str 要进行过滤的字符串  
* @return 过滤后的字符串  
* 2004-12-21 闫  
*/  
public static String stringFilterScriptChar(String str){   
String[] str_arr=stringSpilit(str,"");   
for(int i=0;i<str_arr.length;i++){   
   for (int j = 0; j < FilterScriptChars.length; j++) {   
     if (FilterScriptChars[j][0].equals(str_arr[i]))   
       str_arr[i] = FilterScriptChars[j][1];   
   }   
}   
return(stringConnect(str_arr,"")).trim();   
}   
  
  
       /**  
        * 分割字符串  
        * @param str 要分割的字符串  
        * @param spilit_sign 字符串的分割标志  
        * @return 分割后得到的字符串数组  
        */  
       public static String[] stringSpilit(String str,String spilit_sign){   
         String[] spilit_string=str.split(spilit_sign);   
         if(spilit_string[0].equals(""))   
         {   
           String[] new_string=new String[spilit_string.length-1];   
           for(int i=1;i<spilit_string.length;i++)   
             new_string[i-1]=spilit_string[i];   
             return new_string;   
         }   
         else  
           return spilit_string;   
       }   
  
       /**  
        * 字符串字符集转换  
        * @param str 要转换的字符串  
        * @return 转换过的字符串  
        */  
       public static String stringTransCharset(String str){   
         String new_str=null;   
         try{   
             new_str=new String(str.getBytes("iso-8859-1"),"GBK");   
         }   
         catch(Exception e){   
           e.printStackTrace();   
         }   
         return new_str;   
       }


    public static String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    public static String fliterFourUnicode(String source) throws Exception {
        try {
            byte[] sourceBytes;

            sourceBytes = source.getBytes("utf-8");
            int subIndex = 0;
            String str = "";
            do {
                int curByte = Byte.toUnsignedInt(sourceBytes[subIndex]);
                if (curByte > 0x00 && curByte <= 0x7f) { //0xxxxxxx
                    str = str + (char) sourceBytes[subIndex];
                    subIndex++;
                } else if (curByte >= 0xc0 && curByte <= 0xdf) { //110xxxxx
                    byte[] bytes = {sourceBytes[subIndex], sourceBytes[subIndex + 1]};
                    str = str + new String(bytes, "utf-8");
                    subIndex += 2;
                } else if (curByte >= 0xe0 && curByte <= 0xef) { //1110xxxx
                    byte[] bytes = {sourceBytes[subIndex], sourceBytes[subIndex + 1], sourceBytes[subIndex + 2]};
                    str = str + new String(bytes, "utf-8");
                    subIndex += 3;
                } else if (curByte >= 0xf0 && curByte <= 0xf7) { //11110xxx
                    str = str + "*";
                    subIndex += 4;
                } else if (curByte >= 0xf8 && curByte <= 0xfb) { //111110xx
                    str = str + "*";
                    subIndex += 5;
                } else if (curByte >= 0xfc && curByte <= 0xfd) { //1111110x
                    str = str + "*";
                    subIndex += 6;
                } else if (curByte >= 0xfe) { //11111110
                    str = str + "*";
                    subIndex += 7;
                } else { //解析失败不是UTF-8编码开头字符
                    return str + "*";
                }

            } while (subIndex < sourceBytes.length);
            return str;
        }catch (Exception e){

        }
      return null;

    }


    public static String addZeroForNum(String str,int strLength) {
        int strLen =str.length();
        if (strLen <strLength) {
            while (strLen< strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);//左补0
//    sb.append(str).append("0");//右补0
                str= sb.toString();
                strLen= str.length();
            }
        }

        return str;
    }
}
