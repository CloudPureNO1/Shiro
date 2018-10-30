package idv.wangyj.shiro.password.encryption;

import org.apache.commons.logging.LogFactory;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.junit.Assert;
import org.junit.Test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.junit.Assert.*;

public class MyPasswordEncryptionTest {
    private Logger log=LoggerFactory.getLogger(MyPasswordEncryptionTest.class);
    @Test
    public void encryption() {
         String password="wangyj";
         String str=Base64.encodeToString(password.getBytes());
         log.info(">>>>>>>>>>>>"+password+"64位密文为："+str);
         System.out.println(">>>>>>>>>>>>"+password+"64位密文为："+str);
         String str2=Base64.decodeToString(str);
        Assert.assertEquals("解密失败",password,str2);

        System.out.println("*************"+new Md5Hash(password));

        String str3=Hex.encodeToString(password.getBytes());
        System.out.println(">>>>>>>>>>>>"+password+"16位密文为："+str3);
        String str4=new String(Hex.decode(str3.getBytes()));
        Assert.assertEquals("解密失败",password,str4);


        DefaultHashService defaultHashService=new DefaultHashService();
        defaultHashService.setHashAlgorithmName("MD5");
        defaultHashService.setHashIterations(1);
        defaultHashService.setPrivateSalt(ByteSource.Util.bytes("wangyj"));


    }

}