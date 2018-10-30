package idv.wangyj.shiro.authority.util;

import idv.wangyj.shiro.authority.exception.MyException;
import idv.wangyj.shiro.authority.realm.MyRealm;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JdbcUtilTest {

    @Test
    public void queryListString() {
        JdbcUtil jdbcUtil=new JdbcUtil();
        try {
           List<Map<String,Object>> list= jdbcUtil.queryList(null,"select * from my_users");
           System.out.println(list.toString());
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}