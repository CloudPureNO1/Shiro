package idv.wangyj.shrio.authentication;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginLogoutJdbcRealmTest {

    @Test
    public void loginLogout() {
        LoginLogoutJdbcRealm jdbcRealm=new LoginLogoutJdbcRealm();
        jdbcRealm.loginLogout("wangyj","wangyj666");
    }
}