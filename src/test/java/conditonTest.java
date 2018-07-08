import base.BaseJunit;
import com.wzh.demo.service.ConditionService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class conditonTest extends BaseJunit {

    @Autowired
    @Qualifier(value = "conditionService")
    private ConditionService service;

    @Ignore
    public void test()
    {
        service.computerPrice();
    }
}
