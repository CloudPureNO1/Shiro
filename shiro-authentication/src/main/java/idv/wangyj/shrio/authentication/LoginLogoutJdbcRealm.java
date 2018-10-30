package idv.wangyj.shrio.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;


/**
 * 用Shiro 自带的 {@link org.apache.shiro.realm.jdbc.JdbcRealm}
 * 创建的users等表的字段，要与JdbcRealm中用的的一样
 * 但是不需要自己再实现{@link sun.security.krb5.Realm}
 * 方便，但是很死
 */
public class LoginLogoutJdbcRealm {
    public void loginLogout(String userName,String passwd){
        //1、通过new IniSecurtityManagerFactory 指定一个ini配置文件创建SecurityManager的工厂Facrory<SecurityManager>facroty
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro-jdbc.ini");
        //2、实例化SecurityManager
        SecurityManager securityManager=factory.getInstance();
        //3、吧实例化的SecurityManager实例绑定到SecurityUtils中，这个是全局设定，只需设置一次
        SecurityUtils.setSecurityManager(securityManager);
        //4、取得Subject对象
        Subject subject=SecurityUtils.getSubject();
        //5、创建用户名和密码校验的token
        UsernamePasswordToken token=new UsernamePasswordToken(userName,passwd);
        try{
        //6、subject 调用token登录
            subject.login(token);
        }catch (AuthenticationException ae){
        //7、如果校验不通过处理异常
            if((ae instanceof UnknownAccountException)||(ae instanceof IncorrectCredentialsException)){
                System.out.println(">>>>>>>>>>>>>用户名或密码错误>>>>>>>>>>>>>>>>>>>..");
            }
        }

        //8、Assert 判断是否校验通过
        Assert.assertEquals(true,subject.isAuthenticated());
        //9、退出校验
        subject.logout();
    }
}
