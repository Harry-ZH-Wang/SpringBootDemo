import base.BaseJunit;
        import com.wzh.config.utils.BeanHeader;
        import com.wzh.demo.service.SystemOutService;
        import org.junit.Test;

/**
 * <一句话功能描述>
 * <功能详细描述>
 * @author wzh
 * @version 2018-09-23 23:18
 * @see [相关类/方法] (可选)
 **/
public class BeanHeaderTest extends BaseJunit {

    @Test
    public void testBeanHeader()
    {
        SystemOutService service = BeanHeader.getBean("systemOutService");
        service.sysout();
    }
}
