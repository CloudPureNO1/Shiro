package idv.wangyj.shrio.authentication;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginLogoutMultiRealmTest {

    @Test
    public void loginLogout() {
        LoginLogoutMultiRealm loginLogoutMultiRealm=new LoginLogoutMultiRealm();
        loginLogoutMultiRealm.LoginLogout("wangyj","wangyj777");
    }
}