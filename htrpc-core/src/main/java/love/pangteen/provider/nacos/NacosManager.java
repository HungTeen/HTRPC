package love.pangteen.provider.nacos;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import love.pangteen.config.ConfigManager;

import java.util.Properties;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/30 11:00
 **/
public class NacosManager {

    private static volatile NamingService namingService;

    public static NamingService getNamingService() {
        if (namingService == null) {
            synchronized (NacosManager.class) {
                if (namingService == null) {
                    namingService = createNamingService();
                }
            }
        }
        return namingService;
    }

    private static NamingService createNamingService(){
        NamingService naming = null;
        try {
            Properties properties = new Properties();
            // 多个地址使用逗号隔开
            properties.setProperty(PropertyKeyConst.SERVER_ADDR, ConfigManager.getRegistryAddressWithPort());
            properties.setProperty(PropertyKeyConst.NAMESPACE, ConfigManager.getConfig().getRegistryCenterNamespace());
            ConfigManager.getRegistryCenterUsername().ifPresent(username -> {
                if(!username.isEmpty()) {
                    properties.setProperty(PropertyKeyConst.USERNAME, username);
                }
            });
            ConfigManager.getRegistryCenterPassword().ifPresent(password -> {
                if(!password.isEmpty()) {
                    properties.setProperty(PropertyKeyConst.PASSWORD, password);
                }
            });
            naming = NamingFactory.createNamingService(properties);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
        return naming;
    }
}
