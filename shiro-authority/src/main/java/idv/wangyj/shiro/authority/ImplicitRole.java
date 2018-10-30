package idv.wangyj.shiro.authority;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.List;
import java.util.Map;

public class ImplicitRole {
    public Subject getSubject(String userName,String password,String filePath){
        //1、通过new IniSecurityManagerFactory指定一个ini配置文件创建SecurityManager的工厂Facroty<SecurityManager>facroty
        Factory<SecurityManager>factory=new IniSecurityManagerFactory(filePath);
        //2、实例化SecurityManager
        SecurityManager securityManager=factory.getInstance();
        //3、吧实例化的SecurityManager对象，绑定到SecurityUtils上，这个是全局的，只要绑定一次
        SecurityUtils.setSecurityManager(securityManager);
        //4、取得Subjecty 对象（当前用户对象）
        Subject subject=SecurityUtils.getSubject();
        return subject;
    }

}
