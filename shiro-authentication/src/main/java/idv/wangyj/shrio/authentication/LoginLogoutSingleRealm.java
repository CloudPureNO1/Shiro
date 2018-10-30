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


public class LoginLogoutSingleRealm {
    public void check(String userName,String passwd){
        //1、通过new IniSecurityManagerFactory 制定一个ini文件创建一个SecuritManager 的工厂 Factory<SecurityManager>
        Factory<SecurityManager>factory= new IniSecurityManagerFactory("classpath:shiro-single.ini");
        //2、实例化SecurityManager
        SecurityManager securityManager=factory.getInstance();
        //3、把实例化的SecurityManager绑定到SecurityUtils上，这是个全局的，只要绑定一次即可
        SecurityUtils.setSecurityManager(securityManager);
        //4、取得Subject
        Subject subject=SecurityUtils.getSubject();
        //5、创建登录的token
        UsernamePasswordToken token=new UsernamePasswordToken(userName,passwd);

        try{
            //6、登录
            subject.login(token);
        }catch (AuthenticationException ae){
            //7、如过登录失败，处理AuthenticationException或其子类
            if((ae instanceof UnknownAccountException)||(ae instanceof IncorrectCredentialsException)){
                System.err.println("用户名或密码错误");//不采用用户名错误，密码错误，防止恶意用户非法扫描用户库
            }else{
                System.err.println(">>>>>>>>>>>>>>>"+ae.getMessage());
            }
        }

        /**
         * 断言判断
         */
        Assert.assertEquals(true,subject.isAuthenticated());

        //8、退出登录
        subject.logout();
    }
}
