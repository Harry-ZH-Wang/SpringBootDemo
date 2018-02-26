import com.wzh.application.Application;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * junit 测试基类
 */
//SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)
//启动类
@SpringBootTest(classes = Application.class)
//web项目支持
@WebAppConfiguration
public class BaseJunit {

    @Ignore
    public void runJunitTest()
    {
        System.out.println("Junit 启动测试");
    }
}
