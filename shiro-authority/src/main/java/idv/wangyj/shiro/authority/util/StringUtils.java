package idv.wangyj.shiro.authority.util;

import java.util.List;

public class StringUtils {
    public static boolean isBlank(String str){
        if(str==null||"".equals(str.trim())||"null".equals(str.trim().toLowerCase())){
            return true;
        }
        return false;
    }

    public static boolean isBlank(String [] sArys){
        if(sArys==null||sArys.length==0){
            return true;
        }
        return false;
    }

    public static boolean isBlank(List<Object> list){
        if(list==null||list.size()==0){
            return true;
        }
        return false;
    }
}
