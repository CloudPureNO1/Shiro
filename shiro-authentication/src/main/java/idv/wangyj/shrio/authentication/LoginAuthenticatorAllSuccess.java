package idv.wangyj.shrio.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;

import java.util.List;


public class LoginAuthenticatorAllSuccess {
    public void login(String userName,String passwd,String filePath){
        //1、通过new IniSecurityManagerFacroty 指定一个ini配置文件创建SecurityManager的工厂Facroty<SecurityManager>facroty
        Factory<SecurityManager> factory=new IniSecurityManagerFactory(filePath);
        //2、实例化SecurityManager对象
        SecurityManager securityManager=factory.getInstance();
        //3、把SecurityManager实例化对象绑定到SecurityUtils ,这个是全局的，绑定一次即可
        SecurityUtils.setSecurityManager(securityManager);
        //4、取得当前用户（对象）Subject
         Subject subject=SecurityUtils.getSubject();
        //5、创建token（用户名和密码校验的token)
        UsernamePasswordToken token=new UsernamePasswordToken(userName,passwd);
        try{
        //6、登录
            subject.login(token);
        }catch (AuthenticationException ae){
         //7、校验失败，处理异常
            if((ae instanceof UnknownAccountException)||(ae instanceof IncorrectCredentialsException)){
                System.out.println("用户名密码错误");
            }
        }
        //8、Assert 判断校验是否通过
        Assert.assertEquals(true,subject.isAuthenticated());
        //9、输出校验信息

        PrincipalCollection principalCollection=subject.getPrincipals();
        List list=principalCollection.asList();
        if(list!=null&&list.size()>0){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>"+list.size());
            for(int i=0;i<list.size();i++){
                System.out.println("***************"+list.toString());
            }
        }
        //10、退出
        subject.logout();
    }
}
