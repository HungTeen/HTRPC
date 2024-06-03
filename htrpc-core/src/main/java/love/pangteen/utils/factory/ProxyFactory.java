package love.pangteen.utils.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/31 9:38
 **/
public class ProxyFactory {

    public static <T> T getProxy(Class<T> clazz, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                handler
        );
    }
}
