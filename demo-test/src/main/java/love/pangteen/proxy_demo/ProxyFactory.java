package love.pangteen.proxy_demo;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/25 15:46
 **/
public class ProxyFactory {

    public static Object getJDKProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new DebugInvocationHandler(target)
        );
    }

    public static Object getCglibProxy(Object target) {
        return Enhancer.create(
                target.getClass(),
                new DebugMethodInterceptor()
        );
    }

}
