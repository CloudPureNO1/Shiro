package idv.wangyj.shiro.authority;

import idv.wangyj.shiro.authority.exception.MyException;
import org.junit.Test;

import static org.junit.Assert.*;

public class JdbcRealmAuthorityTest {

    @Test
    public void authority() {
        JdbcRealmAuthority jdbcRealmAuthority=new JdbcRealmAuthority();
        try {
            System.out.println("**********wangyj**wangyj777****System***********");
            jdbcRealmAuthority.authority("classpath:shiro-jdbc-permission2.ini","wangyj","wangyj777","System",null);
            System.out.println("**********wangyj**wangyj777****String[]***********");
            String [] strs={"user:curd","news:curd","System"};
            jdbcRealmAuthority.authority("classpath:shiro-jdbc-permission2.ini","wangyj","wangyj777",null,strs);
        } catch (MyException e) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+e.getMessage());
            e.printStackTrace();
        }
    }
}