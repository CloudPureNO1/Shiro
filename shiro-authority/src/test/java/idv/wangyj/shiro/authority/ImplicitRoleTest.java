package idv.wangyj.shiro.authority;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ImplicitRoleTest {

    public Subject getSubject(){
        Factory<SecurityManager> facroty=new IniSecurityManagerFactory("classpath:shiro-role.ini");
        SecurityManager securityManager=facroty.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("wangyj","wangyj123");
        try{
          subject.login(token);
        }catch (AuthenticationException ae){
            if((ae instanceof UnknownAccountException )||(ae instanceof IncorrectCredentialsException)){
                System.out.println(">>>>>>>>>>>>>>>>>用户名或密码错误！");
            }
        }
        Assert.assertEquals("用户验证不通过",true,subject.isAuthenticated());

        return subject;
    }

    @Test
    public void testHasRole(){
        Assert.assertEquals("没有rolel权限",true,getSubject().hasRole("role1"));
        Assert.assertEquals("没有全部权限",true,getSubject().hasAllRoles(Arrays.asList("role1","role2","role3")));
        boolean []results= getSubject().hasRoles(Arrays.asList("role1","role2","role3","role4"));
        Assert.assertEquals(true,results[0]);
        Assert.assertEquals(true,results[1]);
        Assert.assertEquals(true,results[2]);
        Assert.assertEquals("没有role4的角色权限",true,results[3]);
    }



    @Test
    public void checkRole(){
        getSubject().checkRole("role1");
        getSubject().checkRoles("role1","role4");
      //  getSubject().checkRole("role4");
    }













}