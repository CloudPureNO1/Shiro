package idv.wangyj.shrio.authentication;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginAuthenticatorAllSuccessTest {

    @Test
    public void login() {
        LoginAuthenticatorAllSuccess login=new LoginAuthenticatorAllSuccess();
        login.login("wangyj","wangyj123","classpath:shiro-authenticator-all-success.ini");
        login.login("wangjx","wangjx123456","classpath:shiro-authenticator-all-success.ini");
    }
}