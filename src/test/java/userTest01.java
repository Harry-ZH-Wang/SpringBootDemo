import base.BaseJunit;
import com.wzh.config.framework.service.InitFrameWorkConstantService;
import com.wzh.demo.dao.UserDao;
import com.wzh.demo.domain.UserBean;
import com.wzh.demo.service.IUserService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.List;


/**
 * user表测试类
 */
public class userTest01 extends BaseJunit{

    @Autowired
    @Qualifier(value = "userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier(value = "userService")
    private IUserService userService;

    @Resource
    @Qualifier(value = "initFrameWorkConstantService")
    private InitFrameWorkConstantService initFrameWorkConstantService;


    @Ignore
    public void testBean()
    {
        UserBean bean = userDao.selectTest();
        List<UserBean> list = userDao.selectTestList();

        if(null != bean)
        {
            System.out.println(bean.toString());
        }
        else {
            System.out.println(null == bean);
        }
        if(null != list)
        {
            for (UserBean temp : list)
            {
                System.out.println(temp.toString());
            }
            System.out.println(list.size());
            System.out.println(list.toString());
        }

    }

    @Ignore
    public void testInit()
    {
        System.out.println(initFrameWorkConstantService.initFtpInfo());
    }

    /**
     * 新增测试
     */
//    @Ignore
//    public void testAddUser()
//    {
//        UserBean bean = new UserBean();
//        bean.setName("编程事务测试");
//        bean.setSex("男");
//        bean.setAge(33L);
//        int i = userService.addUserByProgramming(bean);
//        Assert.assertSame(2,i);
//
//    }
//
//    @Ignore
//    public void testJDBC()
//    {
//        System.out.println(userDao.test());
//    }



}
