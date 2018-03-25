import base.BaseJunit;
import com.wzh.demo.dao.UserDao;
import com.wzh.demo.domain.UserBean;
import com.wzh.demo.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


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

    /**
     * 新增测试
     */
    @Test
    public void testAddUser()
    {
        UserBean bean = new UserBean();
        bean.setName("编程事务测试");
        bean.setSex("男");
        bean.setAge(33L);
        int i = userService.addUserByProgramming(bean);
        Assert.assertSame(2,i);

    }



}
