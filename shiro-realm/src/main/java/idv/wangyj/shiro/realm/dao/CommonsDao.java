package idv.wangyj.shiro.realm.dao;

import idv.wangyj.shiro.realm.exception.MyException;

import java.util.List;
import java.util.Map;

public  interface CommonsDao {
    int execDML(String sql,List<Object> listParams) throws MyException;
    List<Map<String,Object>> getList(String sql, List<Object>listParams) throws MyException ;
    List<String> getListString(String sql, List<Object>listParams) throws MyException ;
}
