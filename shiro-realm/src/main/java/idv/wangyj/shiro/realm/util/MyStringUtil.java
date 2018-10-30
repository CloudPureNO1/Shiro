package idv.wangyj.shiro.realm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyStringUtil {
    /**
     * 1、把字符串中，以某个或些字符串（旧字符串） 后面的第一个字符大写
     * 2、如果 新字符串不为null,则替换旧字符串
     * @param str 源字符串
     * @param oldStr 旧字符串
     * @param newStr  新字符串
     * @return
     */
    public static String replaceAndUper(String str,String oldStr,String newStr){
            StringBuffer buffer = new StringBuffer();
            Pattern p = Pattern.compile(oldStr+"[a-z|A-Z]");
            Matcher m = p.matcher(str);
            while (m.find()) {
                // Find each match in turn; String can't do this.
                //String name = m.group(1);
                // Access a submatch group; String can't do this.
                m.appendReplacement(buffer, m.group().toUpperCase());
            }
            m.appendTail(buffer);
          //  System.out.println("sb is= " + buffer.toString());
            if(newStr!=null){
                return buffer.toString().replace(oldStr,newStr);
            }
            return buffer.toString();
    }

    /**
     * 还回收个一分割的字符串
     * @param sql
     * @return
     */
    public static String getStartWith(String sql,String splitStr){
        String str="";
        if(!isBlank(sql)){
            sql=sql.trim();
            return sql.split(splitStr)[0];
        }
        return str;
    }

    public static boolean isBlank(String str){
        if(str==null||"".equals(str)||"null".equals(str.trim().toLowerCase())) {
            return true;
        }
        return false;
    }

    public static boolean isStartWithKey(String key,String sql){
        if(sql.trim().startsWith(key)){
            return true;
        }
        return false;
    }
}
