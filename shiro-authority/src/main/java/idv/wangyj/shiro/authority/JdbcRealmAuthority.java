package idv.wangyj.shiro.authority;

import idv.wangyj.shiro.authority.exception.MyException;
import idv.wangyj.shiro.authority.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;

public class JdbcRealmAuthority {
    public void authority(String filePath,String userName,String password,String permission1,String [] permissions) throws MyException {
        //1、通过new IniSecurityManagerFactory 指定一个ini配置文件创建一个SecurityManager工厂Facorty<SecurityManager>facroty
        Factory<SecurityManager>factory=new IniSecurityManagerFactory(filePath);
        //2、实例化SecurityManager
        SecurityManager securityManager=factory.getInstance();
        //3、吧实例化的SecurityManager对象 绑定到SecurityUtiles 上，以上是一次性加载，只需一次
        SecurityUtils.setSecurityManager(securityManager);
        //4、取得当前的Subject对象
        Subject subject=SecurityUtils.getSubject();
        //5、创建要验证的token
        UsernamePasswordToken token=new UsernamePasswordToken(userName,password);
        try{
            //6、Subject对象用token登录校验
            subject.login(token);
        }catch (AuthenticationException ae){
             //7、校验失败处理异常：AuthenticationException
            if((ae instanceof UnknownAccountException)||(ae instanceof IncorrectCredentialsException)){
                System.out.println(">>>>>>>>>用户名或密码错误>>>>>>>>>>>");
                throw new MyException("用户名或密码错误",ae);
            }else if(ae instanceof ExcessiveAttemptsException){
                System.out.println(">>>>>>>>>登录失败次数过多>>>>>>>>>>>>");
                throw new MyException("登录失败次数过多",ae);
            }else{
                System.out.println("》》》》》》登录失败》》》》》》》："+ae.getMessage());
                throw new MyException("登录失败："+ae.getMessage(),ae);
            }
        }

        //8、断言判断是否通过校验，如果通过校验继续执行：比如：退出登录，是否拥有角色，是否拥有权限等
        Assert.assertEquals("校验不通过",true,subject.isAuthenticated());

        //9、登录成功后的操作
        if(!StringUtils.isBlank(permission1)){
            Assert.assertEquals("用户"+userName+"没有"+permission1+"权限",true,subject.isPermitted(permission1));
        }
        if(!StringUtils.isBlank(permissions)){
           // Assert.assertEquals(permissions.length,subject.isPermitted(permissions).length);
            boolean[] bArys=subject.isPermitted(permissions);//用户其中一个权限即通过
            for(int i=0;i<bArys.length;i++){
                System.out.println(i+">>>>>>>>>>>>"+bArys[i]);
            }
            Assert.assertEquals("用户"+userName+"没有"+permissions+"权限",true,subject.isPermittedAll(permissions));
        }



        /*
        if(!StringUtils.isBlank(permission1)){
            try{
                 subject.checkPermission(permission1);//没有权限时抛出异常
            }catch(AuthorizationException e){
                throw new MyException("用户"+userName+"没有"+permission1+"权限");
            }
        }

        if(!StringUtils.isBlank(permissions)){
            try{
                 subject.checkPermissions(permissions);//
            }catch (AuthorizationException e){
                throw new MyException("用户"+userName+"没有"+permissions+"权限");
            }
        }*/


    }
}
