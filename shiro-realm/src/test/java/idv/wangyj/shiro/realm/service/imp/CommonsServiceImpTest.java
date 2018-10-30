package idv.wangyj.shiro.realm.service.imp;

import idv.wangyj.shiro.realm.exception.AppException;
import idv.wangyj.shiro.realm.exception.MyException;
import idv.wangyj.shiro.realm.service.CommonsService;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CommonsServiceImpTest {
    CommonsService commonsService=null;

    @Before
    public void init(){
        commonsService=new CommonsServiceImp();
    }

    @After
    public void destroy(){
        commonsService=null;
    }

    @Test
    public void add() {
        List<Object> listParams=new ArrayList<Object>();
        listParams.add(1001);
        listParams.add("test");
        listParams.add("test");
        try {
           boolean bFlag= commonsService.add("insert into my_users values (?,?,?)",listParams);

            Assert.assertEquals("用户增加失败",true,bFlag);

        } catch (AppException e) {
            e.printStackTrace();
            System.out.println(">>>>>>>>>>>>用户增加>>"+e.getMessage());
            System.out.println(">>>>>>>>>>>>sql:"+e.getSql());
            System.out.println(">>>>>>>>>>>>params:"+e.getParams());
        }
    }

    @Test
    public void getList() {
        List<Map<String,Object>> list=null;
        Map<String,Object> map=null;
        try {
            list=  commonsService.getList("select *   from my_users",null,true);
            System.out.println(">>>>>>>>>>>>"+list.toString());
            for(int i=0;i<list.size();i++){
                map=list.get(i);
                System.out.println(">>用户：>>>>>>"+map.get("userName")+">>>>");
            }
        } catch (AppException e) {
            e.printStackTrace();
            System.out.println(">>>>>>>>>>>>权限："+e.getMessage());
            System.out.println(">>>>>>>>>>>>sql:"+e.getSql());
            System.out.println(">>>>>>>>>>>>params:"+e.getParams());
        }


        List<Object>listParams=new ArrayList<Object>();
        listParams.add("test");
        try {
            list= commonsService.getList("select * from my_users where user_name=?",listParams,true);
            for(int i=0;i<list.size();i++){
                map=list.get(i);
                System.out.println("******用户：>>>>>>"+map.get("userName")+">>>>");
            }
        } catch (AppException e) {
            e.printStackTrace();
            System.out.println(">>>>>用户：>>>"+e.getMessage());
            System.out.println(">>>>>sql:"+e.getSql());
            System.out.println(">>>>>params:"+e.getParams());
        }

    }


}