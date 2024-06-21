package love.pangteen;

import java.util.Map;
import java.util.TreeMap;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/25 15:45
 **/
public class ProxyTest {

    public static void main(String[] args) {
        SmsService smsService = new SmsServiceImpl();
        SmsProxy smsProxy = new SmsProxy(smsService);
        smsProxy.send("Static Proxy");

        SmsService smsService1 = new SmsServiceImpl();
        SmsService smsServiceProxy = (SmsService) ProxyFactory.getJDKProxy(smsService1);
        smsServiceProxy.send("JDK Dynamic Proxy");
        Map<Integer, Integer> map = new TreeMap<>();
        Map<Integer, Integer> mapProxy = (Map<Integer, Integer>) ProxyFactory.getJDKProxy(map);
        mapProxy.put(1, 1);
        map.put(2, 1);

        // 需要添加VM选项 --add-opens java.base/java.lang=ALL-UNNAMED
        SmsService smsService2 = new SmsServiceImpl();
        SmsService smsServiceProxy1 = (SmsService) ProxyFactory.getCglibProxy(smsService2);
        smsServiceProxy1.send("Cglib Dynamic Proxy");

        MySmsService mySmsService = new MySmsService();
        MySmsService mySmsServiceProxy = (MySmsService) ProxyFactory.getCglibProxy(mySmsService);
        mySmsServiceProxy.send("Cglib Dynamic Proxy");
    }

}
