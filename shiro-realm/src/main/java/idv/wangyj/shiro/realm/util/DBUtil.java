package idv.wangyj.shiro.realm.util;

import com.alibaba.druid.pool.DruidDataSource;
import idv.wangyj.shiro.realm.constant.MyConstants;
import idv.wangyj.shiro.realm.exception.MyException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {
    //数据库关闭异常
    private static final String CLOSE_ERROR=MyConstants.PERSISTENT_LAYER+"-"+MyConstants.DATABASE_CLOSE_EXCEPTION+":";
    //数据库调用异常
    private static final String CALL_ERROR=MyConstants.PERSISTENT_LAYER+"-"+MyConstants.DATABASE_CALL_EXCEPTION+":";
    //增加异常
    private static final String ADD_ERROR=MyConstants.PERSISTENT_LAYER+"-"+MyConstants.ADD_EXCEPTION+":";
    //修改异常
    private static final String MODIFY_ERROR=MyConstants.PERSISTENT_LAYER+"-"+MyConstants.MODIFY_EXCEPTION+":";
    //删除异常
    private static final String REMOVE_ERROR=MyConstants.PERSISTENT_LAYER+"-"+MyConstants.REMOVE_EXCEPTION+":";
    //查询异常
    private static final String QUERY_ERROR=MyConstants.PERSISTENT_LAYER+"-"+MyConstants.QUERY_EXCEPTION+":";

    private String dml_error="";

    protected DruidDataSource dataSource(){
            DruidDataSource druidDataSource=new DruidDataSource();
            druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            druidDataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
            druidDataSource.setUsername("my_test");
            druidDataSource.setPassword("my_test");
            druidDataSource.setMaxActive(5);
            return druidDataSource;
    }



    protected void setParams(PreparedStatement pstmt,List<Object> listParms) throws SQLException {
        if(listParms!=null&&listParms.size()>0){
            for(int i=0;i<listParms.size();i++){
                pstmt.setObject(i+1,listParms.get(i));
            }
        }
    }

    public ResultSet getRs(String sql,List<Object>listParams) throws MyException {
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=dataSource().getConnection();
            pstmt=conn.prepareStatement(sql);
            setParams(pstmt,listParams);
            rs=pstmt.executeQuery();
        }catch (SQLException e){
            throw new MyException(QUERY_ERROR+e.getMessage(),sql,(listParams==null?"":listParams.toString()),e);
        }finally {
            close(rs,pstmt,null,conn);
        }
        return rs;
    }

    public List<Map<String,Object>> getList(String sql,List<Object>listParams) throws MyException {
        List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
        Map<String,Object>map=null;
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=dataSource().getConnection();
            pstmt=conn.prepareStatement(sql);
            setParams(pstmt,listParams);
            rs=pstmt.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            int columnCount= rsmd.getColumnCount();
            while(rs.next()){
                map=new HashMap<String,Object>();
                for(int i=0;i<columnCount;i++){
                    map.put(MyStringUtil.replaceAndUper((rsmd.getColumnName(i+1)).toLowerCase(),"_",""),rs.getObject(i+1));
                }
                list.add(map);
            }
        }catch (SQLException e){
            throw new MyException(QUERY_ERROR+e.getMessage(),sql,(listParams==null?"":listParams.toString()),e);
        }finally {
            close(rs,pstmt,null,conn);
        }
        return list;
    }

    public List<String> getListString(String sql,List<Object>listParams) throws MyException {
        List<String>list=new ArrayList<String>();
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=dataSource().getConnection();
            pstmt=conn.prepareStatement(sql);
            setParams(pstmt,listParams);
            rs=pstmt.executeQuery();
            while(rs.next()){
               // list.add(String.valueOf(rs.getObject(1)));
                if(rs.getObject(1) instanceof String){
                   String str= (String)rs.getObject(1);
                   String [] strs=str.split(",");
                   for(String string:strs){
                       list.add(string);
                   }
                }
            }
        }catch (SQLException e){
            throw new MyException(QUERY_ERROR+e.getMessage(),sql,(listParams==null?"":listParams.toString()),e);
        }finally {
            close(rs,pstmt,null,conn);
        }
        return list;
    }


    public int execDML(String sql,List<Object>listParams) throws MyException {
        int i=0;
        Connection conn=null;
        PreparedStatement pstmt=null;
        try{
            conn=dataSource().getConnection();
            pstmt=conn.prepareStatement(sql);
            setParams(pstmt,listParams);
            i= pstmt.executeUpdate();
        }catch (SQLException e){
            if(MyStringUtil.isStartWithKey("insert",sql)){
                 //throw new MyException(ADD_ERROR+e.getMessage(),e);
                throw new MyException(ADD_ERROR+e.getMessage(),sql,(listParams==null?"":listParams.toString()),e);
            }else if(MyStringUtil.isStartWithKey("update",sql)){
                throw new MyException(MODIFY_ERROR+e.getMessage(),sql,(listParams==null?"":listParams.toString()),e);
            }else if(MyStringUtil.isStartWithKey("delete",sql)) {
                throw new MyException(REMOVE_ERROR + e.getMessage(), sql, (listParams==null?"":listParams.toString()), e);
            }else{
                throw new MyException(CALL_ERROR+e.getMessage(),sql,(listParams==null?"":listParams.toString()),e);
            }

        }finally {
            close(null,pstmt,null,conn);
        }
        return i;
    }








    public void close(ResultSet rs, PreparedStatement pstmt, Statement stmt,Connection conn) throws MyException {
            try {
                if(rs!=null){
                    rs.close();
                }
                if(pstmt!=null){
                    pstmt.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                throw new MyException(CLOSE_ERROR+e,e);
            }
    }
}















