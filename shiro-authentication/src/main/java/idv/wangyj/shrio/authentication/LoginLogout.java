package idv.wangyj.shrio.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;

public class LoginLogout {
    public void hasAuthentication(String userName,String passwd){
        //1、获取SecurityManagerFactory ,通过shiro.ini配置文件初始化SecurityManager
        //通过new IniSecurityManagerFactory 指定ini配置文件来创建一个SecurityManager 的工厂Factory
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro.ini");

        //2、得到SecurityManager 实例
        SecurityManager securityManager=factory.getInstance();

        //3、把SecurityManager实例绑定给SecurityUtils ，这是一个全局设置，绑定一次即可
        SecurityUtils.setSecurityManager(securityManager);

        //4、得到Subject实例
        //通过SecurityUtills取得Subject,其会自动绑定到当前线程
        //如果在web环境，在请求结束后需要解除绑定
        Subject subject=SecurityUtils.getSubject();

        //5、创建身份验证Token或凭证(此处用户名和密码)
        UsernamePasswordToken token=new UsernamePasswordToken(userName,passwd);

        try{
            //6、登录，即身份证验证,通过捕获AuthenticationException异常类或其子类进行验证不通过处理
            //调用subject.login方法登录，其会自动调用SecurityManager.login方法进行登录
            subject.login(token);
        }catch (AuthenticationException ae){
            //7、登录失败，即身份校验不通过处理
            /**
             * 如果身份验证失败，请捕获：{@link AuthenticationException}或其子类：
             * @see org.apache.shiro.authc.DisabledAccountException （禁用的账号）
             * @see org.apache.shiro.authc.LockedAccountException    (锁定的账号）
             * @see org.apache.shiro.authc.UnknownAccountException   (未知账号异常）
             * @see org.apache.shiro.authc.ExcessiveAttemptsException (登录错误次数过多）
             * @see org.apache.shiro.authc.IncorrectCredentialsException (错误凭证)
             * @see org.apache.shiro.authc.ExpiredCredentialsException  (过期的凭证）
             * @see org.apache.shiro.authc.pam.UnsupportedTokenException (未支持的Token)
             * 错误信息提示：最好使用“用户名或密码错误”，而不是“用户名错误”或“密码不对”，
             * 防止恶意用户非法扫描用户库
             */
            if(ae instanceof IncorrectCredentialsException){
                System.err.println(">>>>>>>>>>>用户名或密码错误>>>>>>>>>>>>>"+ae.getMessage());
            }else{
                System.err.println(">>>>>>>>>>>>>>>>>>>>"+ae.getMessage());
            }

        }

        Assert.assertEquals(true,subject.isAuthenticated());//断言登录成功

        //8、退出登录，身份验证
        //调用subject.logout方法退出登录，其会自动调用SecurityManager.logout方法退出登录
        subject.logout();


    }
}
