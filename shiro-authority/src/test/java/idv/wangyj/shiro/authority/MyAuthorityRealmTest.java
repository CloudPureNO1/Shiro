package idv.wangyj.shiro.authority;

import idv.wangyj.shiro.authority.exception.MyException;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyAuthorityRealmTest {

    @Test
    public void myAuthority() {
        MyAuthorityRealm myAuthorityRealm=new MyAuthorityRealm();
        try {
            String [] permissions={"user:curd","news:curd"};
            myAuthorityRealm.myAuthority("classpath:shiro-jdbc-permission.ini","wangyj","wangyj777","System",null);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}