import base.BaseJunit;
import com.wzh.demo.service.ListenerTestService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 事件监听测试
 */
public class listenerTest extends BaseJunit {

    @Autowired
    @Qualifier(value = "listenerService")
    private ListenerTestService listenerTestService;

    @Ignore
    public void testEvent()
    {
        listenerTestService.publish("测试监听");
        //listenerTestService.test();
    }
}
