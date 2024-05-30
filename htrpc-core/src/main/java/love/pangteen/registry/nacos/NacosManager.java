package love.pangteen.registry.nacos;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;

import java.util.Properties;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/30 11:00
 **/
public class NacosManager {

    public static NamingService getNamingService(){
        NamingService naming = null;
        try {
            Properties properties = new Properties();
            // 多个地址使用逗号隔开
            properties.setProperty(PropertyKeyConst.SERVER_ADDR, "127.0.0.1:8848");
            properties.setProperty(PropertyKeyConst.NAMESPACE, "public");
//            properties.put(PropertyKeyConst.USERNAME, "nacos");
//            properties.put(PropertyKeyConst.PASSWORD, "nacos");
            naming = NamingFactory.createNamingService(properties);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
        return naming;
    }
}
