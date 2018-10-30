package idv.wangyj.shiro.realm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyRealmAuthorityTest {

    @Test
    public void authority() {
        //角色测试
        MyRealmAuthority myRealmAuthority=new MyRealmAuthority();
       // myRealmAuthority.authority("classpath:shiro-jdbc.ini","wangyj","wangyj777","admin",null,null,null);

        List<String>roles=new ArrayList<String>();
        roles.add("admin");
        roles.add("manager");
        roles.add("user");
      //  myRealmAuthority.authority("classpath:shiro-jdbc.ini","wangyj","wangyj777","admin",roles,null,null);


        //权限测试
        roles.remove("user");
        myRealmAuthority.authority("classpath:shiro-jdbc.ini","wangyj","wangyj777","admin",roles,"System",null);

        String [] permissions={"System","user:curd","news:curd"};
        myRealmAuthority.authority("classpath:shiro-jdbc.ini","wangyj","wangyj777","admin",roles,"System",permissions);




    }
}