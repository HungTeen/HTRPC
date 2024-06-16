package love.pangteen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/16 15:43
 **/
@SpringBootTest(classes = HTRpcAutoConfiguration.class)
@RunWith(SpringRunner.class)
public class HTRpcAutoConfigurationTest {

    @Test
    public void test() {
        System.out.println("test");
    }

}
