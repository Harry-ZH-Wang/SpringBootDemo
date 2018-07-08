import base.BaseJunit;
import com.wzh.config.utils.FtpManagerUtils;
import domin.User;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.List;

/**
 * <一句话功能描述>
 * <功能详细描述>
 * @author wzh
 * @version 2018-06-18 16:16
 * @see [相关类/方法] (可选)
 **/
public class ftpTest extends BaseJunit {


    @Autowired
    @Qualifier(value = "ftpManagerUtils")
    private FtpManagerUtils ftpManagerUtils;

    @Ignore
    public void readTextTest()
    {

        User user = new User();
        try {
            //对象文本文件
            List<User> list = ftpManagerUtils.readFileWithLine("FTP_USER_SYSTEM",
                    "/file/userinfo.txt","utf-8","\\|","yyyy-MM-dd",user);

            //字符串集合文件文件
            List<String> strlist = ftpManagerUtils.readFileWithLine("FTP_USER_SYSTEM",
                    "/file/userinfo.txt","utf-8","","yyyy-MM-dd",new String());
            System.out.println(list);
            System.out.println(strlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
