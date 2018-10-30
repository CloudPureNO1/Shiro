package idv.wangyj.shrio.authentication;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginLogoutTest {

    @Test
    public void hasAuthentication() {
        LoginLogout loginLogout=new LoginLogout();
       // loginLogout.hasAuthentication("wangyj","wangyj123456456");
        loginLogout.hasAuthentication("wangyj","wangyj12323");
    }
}