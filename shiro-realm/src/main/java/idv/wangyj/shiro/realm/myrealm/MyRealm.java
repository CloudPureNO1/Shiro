package idv.wangyj.shiro.realm.myrealm;

import idv.wangyj.shiro.realm.exception.AppException;
import idv.wangyj.shiro.realm.service.CommonsService;
import idv.wangyj.shiro.realm.service.imp.CommonsServiceImp;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyRealm extends AuthorizingRealm {

    @Override
    public String getName() {
        return "myRealm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
         String username= (String) principals.getPrimaryPrincipal();
         List<String>listRoles=null;
         List<String>listPermissions=null;
         List<Object>listParams=new ArrayList<Object>();
         CommonsService commonsService=new CommonsServiceImp();
         String sql="select distinct r.role_name from t_shiro_roles r,t_shiro_user_role ur,my_users u where r.role_id=ur.role_id and u.user_id=ur.user_id and u.user_name=?";
         listParams.add(username);
        try {
            listRoles=commonsService.getListString(sql,listParams,false);
        } catch (AppException e) {
            e.printStackTrace();
            System.out.println(">>>>>>>>Shiro用户信息角色>>>>>>>>:"+e.getMessage());
            System.out.println(">>>>>>>>Shiro用户信息角色>>>>>sql:"+e.getSql());
            System.out.println(">>>>>>>>Shiro用户信息角色>>params:"+e.getParams());
        }

        sql="select distinct p.permission_name from t_shiro_permission p,t_shiro_role_permission rp,t_shiro_roles r " +
                 "where p.permission_id=rp.permission_id and rp.role_id=r.role_id  and r.role_id in (" +
                 "select ur.role_id from t_shiro_user_role ur,my_users u where u.user_id=ur.user_id and u.user_name=? )";
         try {
             listPermissions=commonsService.getListString(sql,listParams,false);
         } catch (AppException e) {
             e.printStackTrace();
             System.out.println(">>>>>>>>Shiro用户信息权限>>>>>>>>:"+e.getMessage());
             System.out.println(">>>>>>>>Shiro用户信息权限>>>>>sql:"+e.getSql());
             System.out.println(">>>>>>>>Shiro用户信息权限>>params:"+e.getParams());
         }
         SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
         simpleAuthorizationInfo.addRoles(listRoles);
         simpleAuthorizationInfo.addStringPermissions(listPermissions);
         return  simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username= (String) token.getPrincipal();
        String password=new String((char[])token.getCredentials());
        List<Object>listParams=null;
        List<Map<String,Object>> list=null;
        Map<String,Object>map=null;
        CommonsService commonsService=new CommonsServiceImp();

        //校验账号
        listParams=new ArrayList<Object>();
        listParams.add(username);
        try {
            list=commonsService.getList("select user_name  from my_users where user_name=?",listParams,false);
            if(list==null||list.size()<1){
                throw new UnknownAccountException();//账号不存在
            }else{
                //校验密码,防止数据库修改密码，直接查询数据库，不取缓存
                listParams.add(password);
                list=commonsService.getList("select user_pwd from my_users where user_name=? and user_pwd=?",listParams,false);
                if(list==null||list.size()<1){
                    throw new IncorrectCredentialsException();//密码错误
                }
            /*    else{
                    //其他校验处理，比如是否锁住等
                }*/
            }
        } catch (AppException e) {
            e.printStackTrace();
            System.out.println(">>>>>>>>Shiro用户信息Token校验>>>>>>>>:"+e.getMessage());
            System.out.println(">>>>>>>>Shiro用户信息Token校验>>>>>sql:"+e.getSql());
            System.out.println(">>>>>>>>Shiro用户信息Token校验>>params:"+e.getParams());
        }
        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
