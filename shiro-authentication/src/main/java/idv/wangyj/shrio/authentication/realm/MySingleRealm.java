package idv.wangyj.shrio.authentication.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Realm：域，Shiro 从从 Realm 获取安全数据（如用户、角色、权限），就是说 SecurityManager 要验证用户身份，那么它需要从 Realm 获取相应的用户进行比较以确定用户身份是否合法；也需要从 Realm 得到用户相应的角色 / 权限进行验证用户是否能进行操作；可以把 Realm 看成 DataSource，即安全数据源。如我们之前的 ini 配置方式将使用 org.apache.shiro.myrealm.text.IniRealm。
 */
public class MySingleRealm implements Realm {
    /**
     * 还回唯一的域名：myrealm
     * @return  指定的realm 名，对应于配置文件中的名称
     */
    @Override
    public String getName() {
        return "mySingleRealm";
    }

    /**
     * 判断realm 是否支持对应的Token
     * @param authenticationToken
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        if(authenticationToken instanceof UsernamePasswordToken){//支持 用户名密码Token
            return true;
        }
        return false;
    }

    /**
     * 根据Token获取对应的认证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName= (String) authenticationToken.getPrincipal();//此处校验用户名/密码的Token:用户名
        String passwd=  new String((char[])authenticationToken.getCredentials()); //得到密码
        //正常应该重数据库判断，此处测试，写死
        if(!"wangyj".equals(userName)){//如果用户名错误
            throw  new UnknownAccountException();
        }
        if(!"wangyj123".equals(passwd)){//如果密码错误
            throw new IncorrectCredentialsException();
        }

        //认证成功，还回AuthenticationInfo

        return new SimpleAuthenticationInfo(userName,passwd,getName());
    }
}
