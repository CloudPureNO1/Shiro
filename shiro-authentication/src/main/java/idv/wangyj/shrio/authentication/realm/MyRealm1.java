package idv.wangyj.shrio.authentication.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

import java.util.Calendar;

public class MyRealm1 implements Realm {
    @Override
    public String getName() {//ini配置文件中声明的名称
        return "myRealm1";
    }

    /**
     * 是否支持权限Token
     * @param authenticationToken
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        //这里是校验登录的用户密码和密码
        if(authenticationToken instanceof UsernamePasswordToken){
            return true;
        }
        return false;
    }


    /**
     * 授权token校验，并返回验证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName= (String) authenticationToken.getPrincipal();
        /**
         * token中的passwd 是char[]类型的 {@link UsernamePasswordToken}
         */
        char [] pwchars= (char[]) authenticationToken.getCredentials();
       String passwe= new String(pwchars);//char[] passwd


        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Myrealm1>>>>>>>>>>>"+Calendar.getInstance().getTimeInMillis());
        //应该从数据库取得校验的用户名和密码（并且是脱敏的），与传入的用户名和密码对比，此处，测试，直接写死
        if(!"wangyj".equals(userName)){
            throw new UnknownAccountException();//用户名不对
        }
        if(!"wangyj123".equals(passwe)){
            throw new IncorrectCredentialsException();//密码不对
        }

        //还回验证的详信息
        return new SimpleAuthenticationInfo(userName,passwe,getName());
    }
}
