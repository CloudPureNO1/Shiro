package idv.wangyj.shrio.authentication.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

import java.util.Calendar;
import java.util.Date;

/**
 * Shiro Realm Shiro域 类似于一个安全数据源，可以校验登录等，以及授权等
 */
public class MyRealm2 implements Realm {
    @Override
    public String getName() {//取得ini配置文件中声明的域名 Realm 名
        return "myRealm2";
    }

    /**
     * 指定支持的 认证token 类型
     * 此处以登录校验用户名和密码为例
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        if(token instanceof UsernamePasswordToken){
            return true;
        }
        return false;
    }

    /**
     * 根据token,验证，生成对应的验证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName= (String) token.getPrincipal();
        char[] pwchars= (char[]) token.getCredentials();//UsernamePasswordToken 中的passwd 是char数组
        String passwd=new String(pwchars);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Myrealm2>>>>>>>>>>>"+Calendar.getInstance().getTimeInMillis());
        //数据库查询校验token中的用户信息,此处写死
        if(!"wangjx".equals(userName)){
            throw new UnknownAccountException();
        }
        if(!"wangjx123456".equals(passwd)){
            throw new IncorrectCredentialsException();
        }

        //还回token验证信息
        return new SimpleAuthenticationInfo(userName,passwd,getName());
    }
}
