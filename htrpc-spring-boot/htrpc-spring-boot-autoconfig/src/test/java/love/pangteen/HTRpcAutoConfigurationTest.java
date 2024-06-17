package love.pangteen;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.config.ConfigManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/16 15:43
 **/
@Slf4j
@SpringBootTest(classes = {HTRpcAutoConfigurationTest.class})
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@ComponentScan
public class HTRpcAutoConfigurationTest {

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void test() {
        for (String definitionName : applicationContext.getBeanDefinitionNames()) {
            log.info("Bean Name : [{}]", definitionName);
        }
        log.info("Application Name : [{}]", applicationContext.getDisplayName());
        Assert.assertEquals(9999, (int) ConfigManager.getConfig().getServicePort());
    }

}
