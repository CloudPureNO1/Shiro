package idv.wangyj.shiro.realm.service;

import idv.wangyj.shiro.realm.exception.AppException;

import java.util.List;
import java.util.Map;

public interface CommonsService {
    boolean add(String sql,List<Object> listParams) throws AppException;
    List<Map<String,Object>> getList(String sql, List<Object>list,boolean isThrowBlankException) throws  AppException;
    List<String> getListString(String sql, List<Object>list,boolean isThrowBlankException) throws  AppException;
}
