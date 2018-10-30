package idv.wangyj.shiro.authority.util;


import com.alibaba.druid.pool.DruidDataSource;
import idv.wangyj.shiro.authority.exception.MyException;
import org.apache.shiro.realm.jdbc.JdbcRealm;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JdbcUtil{
    private DruidDataSource dataSource;

    public DruidDataSource getDataSource() {
        if(dataSource==null){
            dataSource=new DruidDataSource();
            dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
            dataSource.setUsername("my_test");
            dataSource.setPassword("my_test");

            /**
             * 选择参数
             */
            //配置初始化大小、最小、最大
            dataSource.setInitialSize(1);
            dataSource.setMinIdle(1);
            dataSource.setMaxActive(20);
            //连接泄漏监测
            dataSource.setRemoveAbandoned(true);
            dataSource.setRemoveAbandonedTimeout(30);
            //配置获取连接等待超时的时间
            dataSource.setMaxWait(20000);
            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            dataSource.setTimeBetweenEvictionRunsMillis(20000);

        }
        return dataSource;
    }

    /**
     * 根据{@link JdbcRealm }取得数据源
     * @return
     * @throws SQLException
     */
    public  Connection getConn() throws SQLException {

        return  getDataSource().getConnection();
    }


    public boolean query(List<Object> params, String sql) throws MyException {
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn=getConn();
            pstmt=conn.prepareStatement(sql);
            if(params!=null){
                for(int i=0;i<params.size();i++){
                    pstmt.setObject(i+1,params.get(i));
                }
            }

            rs= pstmt.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MyException("查询数据库异常："+e.getMessage());
        }finally {
            coloseAll(rs,null,pstmt,conn);
        }
        return false;
    }



    public ResultSet queryRs(List<Object> params, String sql) throws MyException {
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn=getConn();
            pstmt=conn.prepareStatement(sql);
            if(params!=null){
                for(int i=0;i<params.size();i++){
                    pstmt.setObject(i+1,params.get(i));
                }
            }

            rs= pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MyException("查询数据库异常："+e.getMessage());
        }finally {
            coloseAll(rs,null,pstmt,conn);
        }
        return rs;
    }

    public List<String> queryListString(List<Object> params, String sql) throws MyException {
        List<String>list=new ArrayList<String>();
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn=getConn();
            pstmt=conn.prepareStatement(sql);
            if(params!=null){
                for(int i=0;i<params.size();i++){
                    pstmt.setObject(i+1,params.get(i));
                }
            }

            rs= pstmt.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            int columnCount=rsmd.getColumnCount();
            while (rs.next()){
                for(int i=0;i<columnCount;i++){
                    list.add(rs.getString(i+1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MyException("查询数据库异常："+e.getMessage());
        }finally {
            coloseAll(rs,null,pstmt,conn);
        }
        return list;
    }

    public List<Map<String,Object>> queryList(List<Object> params, String sql) throws MyException {
        List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=null;
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn=getConn();
            pstmt=conn.prepareStatement(sql);
            if(params!=null){
                for(int i=0;i<params.size();i++){
                    pstmt.setObject(i+1,params.get(i));
                }
            }

            rs= pstmt.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            int columnCount=rsmd.getColumnCount();
            while (rs.next()){
                map=new HashMap<String,Object>();
                for(int i=0;i<columnCount;i++){
                    map.put(rsmd.getColumnName(i+1),rs.getObject(i+1));
                }
                list.add(map);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new MyException("查询数据库异常："+e.getMessage());
        }finally {
            coloseAll(rs,null,pstmt,conn);
        }
        return list;
    }


    private void coloseAll(ResultSet rs,Statement stmt,PreparedStatement pstmt,Connection conn) throws MyException{
            try {
                if(rs!=null) {
                    rs.close();
                }
                if(stmt!=null){
                    pstmt.close();
                }
                if(pstmt!=null){
                    pstmt.close();
                }
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                throw new MyException("-1","关闭数据库连接发生异常："+e.getMessage());
            }
    }

}
