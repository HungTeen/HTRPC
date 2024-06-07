package love.pangteen.extension;

import love.pangteen.provider.ServiceDiscovery;
import love.pangteen.utils.extension.ExtensionLoader;
import org.junit.Assert;
import org.junit.Test;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/3 16:50
 **/
public class ExtensionTest {

    @Test
    public void testSPI() {
        ExtensionLoader<ServiceDiscovery> loader = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class);
        Assert.assertNotNull(loader);

        ServiceDiscovery local = loader.getExtension("local");
        Assert.assertEquals(loader.getClassSize(), 2);
        Assert.assertEquals(loader.getInstanceSize(), 1);
        Assert.assertNotNull(local);

        ServiceDiscovery nacos = loader.getExtension("nacos");
        Assert.assertEquals(loader.getClassSize(), 2);
        Assert.assertEquals(loader.getInstanceSize(), 2);
        Assert.assertNotNull(nacos);
        Assert.assertNotEquals(local, nacos);
    }
}
