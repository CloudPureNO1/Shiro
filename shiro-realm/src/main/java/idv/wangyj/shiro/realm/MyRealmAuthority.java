package idv.wangyj.shiro.realm;

import idv.wangyj.shiro.realm.util.MyStringUtil;
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

import java.util.List;

public class MyRealmAuthority {
    public void authority(String file, String username, String password, String role, List<String> roles, String permission, String [] permissions) {
        //1、通过new IniSecurityManagerFactory 指定一个ini文件，创建SecurityManager工厂Factory<SecurityManager>facroty
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(file);
        //2、实例化SecurityMananger
        SecurityManager securityManager = factory.getInstance();
        // 3、把实例化的SecurityManager绑定到SecurityUtils中，一次性绑定
        SecurityUtils.setSecurityManager(securityManager);
        //4、通过SecurityUtils 取得Subject对象
        Subject subject = SecurityUtils.getSubject();
        //5、创建用户校验Token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //6、Subject对象用户token登录，校验失败会抛出AuthenticationException
            subject.login(token);
        } catch (AuthenticationException e) {
            //7、异常处理
            if ((e instanceof UnknownAccountException) || (e instanceof IncorrectCredentialsException)) {
                System.out.println(">>>>>>>>>>>用户名或密码错误>>>>>>>>>>>>>>>>>>>>>");
            } else {
                System.out.println(">>>>>>>>>>>>其他异常>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
        }

        //8、Assert判断是否校验通过
        Assert.assertEquals("用户信息校验不通过", true, subject.isAuthenticated());
        //9、其他处理，比如角色、权限判断，退出登录等
        if(!MyStringUtil.isBlank(role)){
            Assert.assertTrue(username+"没有"+role+"角色",subject.hasRole(role));
        }
        if(roles!=null&&!roles.isEmpty()){
            //拥有权限，其中之一
            boolean [] bs=subject.hasRoles(roles);
            for(boolean b:bs){
                System.out.println("********角色************"+b);
            }

            //是否拥有全部角色
            Assert.assertTrue(username+"没有"+roles.toString()+"全部角色",subject.hasAllRoles(roles));

        }





        if (!MyStringUtil.isBlank(permission)) {
            Assert.assertTrue(username+"没有" + permission + "权限", subject.isPermitted(permission));
        }
        if (permissions != null && permissions.length > 0) {
            for(String str:permissions){
                System.out.println(username+"是否拥有"+str+"权限? "+subject.isPermitted(str));
            }

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            //部分权限
            boolean [] bs=subject.isPermitted(permissions);
            for(boolean b:bs){
                System.out.println("*************************"+b);
            }


            //是否拥有全部权限
            Assert.assertTrue(username+"没有"+permissions+"全部权限",subject.isPermittedAll(permissions));

        }
    }
}
