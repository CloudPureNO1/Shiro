package idv.wangyj.shiro.authority.realm;

import idv.wangyj.shiro.authority.exception.MyException;
import idv.wangyj.shiro.authority.util.JdbcUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRealm extends AuthorizingRealm {

    @Override
    public void setName(String name) {
        super.setName("myRealm");
    }

    /**
     * 授权信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        //得到认证的用户名
       String userName= (String) principals.getPrimaryPrincipal();

       JdbcUtil jdbcUtil=new JdbcUtil();
       List<Object>parms=new ArrayList<Object>();
       List<String> listPermission=null;

       String sql="select p.Permission_Name" +
               "  from t_shiro_roles r,t_shiro_permission p ,t_shiro_role_permission rp" +
               "  where rp.rp_id=r.role_id and rp.permission_id=p.permission_id" +
               "   and r.role_id in (select role_id from t_shiro_user_role ur, my_users  u" +
               "  where ur.user_id = u.user_id and u.user_name =?)";
       parms.add(userName);
       System.out.println(">>>>>>>>>>>1111sql:"+sql);
        try {
            listPermission=jdbcUtil.queryListString(parms,sql);
            if(listPermission!=null&&listPermission.size()>0){
                 simpleAuthorizationInfo.addStringPermissions(listPermission);
            }
        } catch (MyException e) {
            e.printStackTrace();
        }
        return simpleAuthorizationInfo;
    }


    /**
     * 对登录用户，进行token校验，并还回校验信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName= (String) token.getPrincipal();//token中的用户名
        String passwd= new String((char[])token.getCredentials());//pqsswd

        JdbcUtil jdbcUtil=new JdbcUtil();
        List<Object> params=null;
        //校验用户名
        String sql="select count(1) from my_users where user_name=?";
        params=new ArrayList<Object>();
        params.add(userName);
        try {
            if(!jdbcUtil.query(params,sql)){
                System.out.println(">>>>>>>>>>>>>>>未知账号："+userName);
                throw new UnknownAccountException();
            };
        } catch (MyException e) {
            System.out.println("账号校验发生异常："+e.getMessage());
            e.printStackTrace();
        }

        sql="select count(1) from my_users where user_name=? and user_pwd=?";
        params=new ArrayList<Object>();
        params.add(userName);
        params.add(passwd);
        try {
            if(!jdbcUtil.query(params,sql)){
                System.out.println(">>>>"+userName+">>>>>>密码错误："+passwd);
                throw new IncorrectCredentialsException();
            }
        } catch (MyException e) {
            e.printStackTrace();
        }

        //校验成功，还回校验信息
        return new SimpleAuthenticationInfo(userName,passwd,getName());
    }
}
