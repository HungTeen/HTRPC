package love.pangteen.config;

import love.pangteen.enums.CompressType;
import love.pangteen.enums.ServiceProviderType;
import org.junit.Assert;
import org.junit.Test;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/17 9:58
 **/
public class ConfigTest {

    @Test
    public void testHTRpcConfig() {
        HTRpcConfig htrpcConfig = ConfigManager.getConfig();
        Assert.assertEquals((int)htrpcConfig.getServicePort(), 8888);
        Assert.assertEquals(htrpcConfig.getCompressType(), CompressType.IGNORE);
        Assert.assertEquals(htrpcConfig.getServiceProviderType(), ServiceProviderType.NACOS);
    }
}
