import base.BaseJunit;
import com.wzh.demo.service.ListenerTestService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * 事件监听测试
 */
public class listenerTest extends BaseJunit {

    @Autowired
    @Qualifier(value = "listenerService")
    private ListenerTestService listenerTestService;

    @Autowired
    @Qualifier(value="asyncServiceExecutor")
    private Executor asyncServiceExecutor;

    @Ignore
    public void testEvent()
    {
        listenerTestService.publish("测试监听");
        //listenerTestService.test();
    }

    @Test
    public void treeTest(){

        for(int i=0;i<10;i++){
            asyncServiceExecutor.execute(new Runnable(){
                @Override
                public void run() {
                    try {
                        System.out.println("342342342");

                        Thread.sleep(10000);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });

        }
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
