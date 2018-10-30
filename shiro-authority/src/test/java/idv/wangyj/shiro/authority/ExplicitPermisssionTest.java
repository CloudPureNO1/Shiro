package idv.wangyj.shiro.authority;


import idv.wangyj.shiro.authority.exception.MyException;
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
import org.junit.Before;
import org.junit.Test;

public class ExplicitPermisssionTest {

    public void setSecurityManager(String filePath){
        //1、通过newIniSecrutiyManagerFactory制定一个ini配置文件，创建SecurityManager工厂Facroty<SecurityManager>facroty
        Factory<SecurityManager> factory=new IniSecurityManagerFactory(filePath);
        //2、实例化SecurityManager
        SecurityManager securityManager=factory.getInstance();
        //3、把实例化的SecurityManager对象绑定到SecurtiyUtils 中，这个是全局的，只要绑定一次
        SecurityUtils.setSecurityManager(securityManager);
    }

    public Subject getSubject(String userName,String passwd) throws MyException {
        UsernamePasswordToken token=new UsernamePasswordToken(userName,passwd);
        Subject subject=SecurityUtils.getSubject();
        try{
            subject.login(token);
        }catch (AuthenticationException ae){
            if((ae instanceof UnknownAccountException)||(ae instanceof IncorrectCredentialsException)){
                System.out.println(">>>>>>>>>>>>>用户名或密码错误");
                throw new MyException("用户名或密码错误",ae);
            }
        }
        return subject;
    }

    @Before
    public void before(){
        setSecurityManager("classpath:shiro-permission.ini");
    }

    @Test
    public void testPermission() throws MyException {
        Subject subject=getSubject("wangyj","wangyj123");
        Assert.assertEquals("用户没有role1角色权限",true,subject.hasRole("role1"));
        Assert.assertEquals("用户没有user:create 权限",true,subject.isPermitted("user:create"));
        Assert.assertEquals(true,subject.isPermittedAll("user:create","user:update"));
        Assert.assertEquals("没有user：view权限",true,subject.isPermitted());
    }
}
