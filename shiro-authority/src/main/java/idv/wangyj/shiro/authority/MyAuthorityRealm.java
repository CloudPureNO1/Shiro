package idv.wangyj.shiro.authority;

import idv.wangyj.shiro.authority.exception.MyException;
import idv.wangyj.shiro.authority.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;

import java.util.Collections;
import java.util.List;


public class MyAuthorityRealm {
    public void myAuthority(String filePath,String userNama,String passwd,String permission,String [] permissions) throws MyException {
        //1、通过new IniSecurityManagerFacorty 指定一个ini配置文件创建SecurrityManager的工厂Facroty<SecurityManager>facroty
        Factory<SecurityManager>factory= new IniSecurityManagerFactory(filePath);
        //2、实例化SecurityManager对象
         SecurityManager securityManager=factory.getInstance();
        //3、把实例化的SecrityManager对象绑定到SecurityUtils 上  ，上面这三步是一个全局设置，只需设置一次
        SecurityUtils.setSecurityManager(securityManager);


        //4、通过SecurityUtils取得当期对象Subject
        Subject subject=SecurityUtils.getSubject();
        //5、创建要验证用户名和密码的Token
        UsernamePasswordToken token=new UsernamePasswordToken(userNama,passwd);
        try{
             //6、通过subject调用创建的token 登录
            subject.login(token);
        }catch (AuthenticationException ae){
             //7、异常捕获（登录不通过，抛出AuthenticationException或其子异常
             if((ae instanceof UnknownAccountException)||(ae instanceof IncorrectCredentialsException)){
                 throw new MyException("用户名或密码错误",ae);
             }else{
                 throw new MyException("用户登录异常："+ae.getMessage(),ae);
             }
        }

        //8、断言判断是否通过,避免了if(subject.isAuthenticated()){} 块
        Assert.assertEquals("shiro校验没有通过",true,subject.isAuthenticated());
        //9、如果通过，继续执行其他的操作，比如退出登录,比如授权校验
        //System.out.println(">>>>>>>>>>>>>>>>>开始退出>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        // subject.logout();

        //授权校验
        if(!StringUtils.isBlank(permission)){
             Assert.assertEquals("是否拥有"+permission+"权限：",true,subject.isPermitted(permission));
             try{
                 subject.checkPermission(permission);//没有权限时，抛出异常
             }catch (AuthorizationException e){
                 throw new MyException("校验是否拥有"+permission+"权限时发生异常："+e.getMessage(),e);
             }
        }
        if(!StringUtils.isBlank(permissions)){
            boolean [] bResults=subject.isPermitted(permissions);
            for(boolean b:bResults){
                System.out.println(">>>>>>>>>>权限校验结果："+b);
            }
            try{
                subject.checkPermissions(permission);//没有权限时，抛出异常
            }catch (AuthorizationException e){
                throw new MyException("校验是否拥有"+permission+"权限时发生异常："+e.getMessage(),e);
            }
        }
    }
}
