package idv.wangyj.shiro.realm.service.imp;

import idv.wangyj.shiro.realm.constant.MyConstants;
import idv.wangyj.shiro.realm.dao.CommonsDao;
import idv.wangyj.shiro.realm.dao.imp.CommonsDaoImp;
import idv.wangyj.shiro.realm.exception.AppException;
import idv.wangyj.shiro.realm.exception.MyException;
import idv.wangyj.shiro.realm.service.CommonsService;

import java.util.List;
import java.util.Map;

public class CommonsServiceImp implements CommonsService {
    @Override
    public boolean add(String sql, List<Object> listParams) throws AppException {
        CommonsDao dao=new CommonsDaoImp();
        try {
            if((dao.execDML(sql,listParams))>0){
                return true;
            }
        } catch (MyException e) {
           // throw new AppException(MyConstants.BUSINESS_LAYER+"-"+MyConstants.ADD_EXCEPTION +"-->"+e.getMessage(),e);
            throw new AppException(MyConstants.BUSINESS_LAYER+"-"+MyConstants.ADD_EXCEPTION +"-->"+e.getMessage(),e.getSql(),e.getParams(),e);

        }
        return false;
    }

    @Override
    public List<Map<String,Object>> getList(String sql, List<Object> listParams,boolean isThrowBlankException) throws AppException {
        CommonsDao dao=new CommonsDaoImp();
        List list=null;
        try {
            list= dao.getList(sql,listParams);
            if((list==null||list.size()<1)&&isThrowBlankException){//如果结果为空，且需要手动抛出结果为空的异常
                throw new AppException(MyConstants.NO_RESULTS_CODE,MyConstants.NO_RESULT_DESC);
            }
        } catch (MyException e) {
            throw new AppException(MyConstants.BUSINESS_LAYER+"-"+MyConstants.QUERY_EXCEPTION+"-->"+e.getMessage(),e.getSql(),e.getParams(),e);
        }
        return list;
    }

    @Override
    public List<String> getListString(String sql, List<Object> listParams,boolean isThrowBlankException) throws AppException {
        CommonsDao dao=new CommonsDaoImp();
        List list=null;
        try {
            list= dao.getListString(sql,listParams);
            if((list==null||list.size()<1)&&isThrowBlankException){//如果结果为空，且需要手动抛出结果为空的异常
                throw new AppException(MyConstants.NO_RESULTS_CODE,MyConstants.NO_RESULT_DESC);
            }
        } catch (MyException e) {
            throw new AppException(MyConstants.BUSINESS_LAYER+"-"+MyConstants.QUERY_EXCEPTION+"-->"+e.getMessage(),e.getSql(),e.getParams(),e);
        }
        return list;
    }
}
