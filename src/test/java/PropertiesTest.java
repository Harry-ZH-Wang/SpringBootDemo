import base.BaseJunit;
import com.wzh.config.utils.PropertiesUtils;
import com.wzh.demo.domain.PropertiesTestBean;
import com.wzh.demo.domain.PropertiesTestBean1;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * <一句话功能描述>
 * <功能详细描述>
 *
 * @author wzh
 * @version 2018-06-24 20:20
 * @see [相关类/方法] (可选)
 **/
public class PropertiesTest extends BaseJunit{

    @Autowired
    PropertiesTestBean propertiesTestBean;

    @Autowired
    PropertiesTestBean1 propertiesTestBean1;

    @Test
    public void test()
    {
        System.out.println(propertiesTestBean1.toString());
    }

}
