package idv.wangyj.shrio.authentication;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginLogoutSingleRealmTest {

    @Test
    public void check() {
        LoginLogoutSingleRealm logoutSingleRealm=new LoginLogoutSingleRealm();
        logoutSingleRealm.check("wangyj","wangyj123456");
    }
}