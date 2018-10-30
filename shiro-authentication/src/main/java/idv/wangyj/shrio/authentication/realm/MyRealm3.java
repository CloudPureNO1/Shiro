package idv.wangyj.shrio.authentication.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

import java.util.Calendar;

public class MyRealm3 implements Realm {
    @Override
    public String getName() {
        return "myRealm3";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if(token instanceof UsernamePasswordToken) return true;  //设定支持的token校验类型 为用户名和密码
        return false;
    }

    /**
     * 校验token，并还回验证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName= (String) token.getPrincipal();
        String passwd=new String((char[])token.getCredentials());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Myrealm2>>>>>>>>>>>"+Calendar.getInstance().getTimeInMillis());
        if(!"wangjx".equals(userName)){
            throw new UnknownAccountException();
        }
        if(!"wangjx123".equals(passwd)){
            throw new IncorrectCredentialsException();
        }

        //还回验证信息
        return new SimpleAuthenticationInfo(userName,passwd,getName());
    }
}
