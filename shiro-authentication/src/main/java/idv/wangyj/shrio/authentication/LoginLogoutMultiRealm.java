package idv.wangyj.shrio.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;


/**
 * SecurityManager  默认的策略是
 * 只要其中一个通 ，就认为是校验通过
 * 策略，可以在ini文件中配置
 */
public class LoginLogoutMultiRealm {
    public void LoginLogout(String userName,String passwd){
        //1、通过new IniSecurityManagerFactory 指定一个ini文件创建一个SecurityManager工厂 Facroty<SecurityManager> factory
        Factory<SecurityManager>factory= new IniSecurityManagerFactory("classpath:shiro-multi.ini");
        //2、实例化SecurityManager
        SecurityManager securityManager=factory.getInstance();
        //3、把实例化的SecurityManager绑定到SecurtiyUtils上，这个是全局的，只需要绑定一次
        SecurityUtils.setSecurityManager(securityManager);
        //4、取得Subject
        Subject subject=SecurityUtils.getSubject();
        //5、创建token
        UsernamePasswordToken token=new UsernamePasswordToken(userName,passwd);
        try{
             //6、用token登录
            subject.login(token);
        }catch (AuthenticationException ae){
             //7、登录失败，异常处理
            //此处测试，userName 和 passwd
            if((ae instanceof UnknownAccountException)||(ae instanceof IncorrectCredentialsException)){
                System.out.println(">>>>>>>>>>>>>用户名或密码错误>>>>>>>>>>>>>>");
            }else{
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ae.getMessage());
            }
        }
        //8、Assert,判断是否验证成功
        Assert.assertEquals(true,subject.isAuthenticated());
        //9、退出登录
        subject.logout();
    }
}
