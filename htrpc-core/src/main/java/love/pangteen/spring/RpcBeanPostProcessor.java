package love.pangteen.spring;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.annotations.HTRpcReference;
import love.pangteen.annotations.HTRpcService;
import love.pangteen.config.RpcServiceConfig;
import love.pangteen.enums.RpcRequestTransportTypes;
import love.pangteen.provider.ServiceProvider;
import love.pangteen.provider.nacos.NacosServiceProvider;
import love.pangteen.proxy.RpcClientProxy;
import love.pangteen.remoting.transport.RpcRequestTransport;
import love.pangteen.utils.extension.ExtensionLoader;
import love.pangteen.utils.factory.SingletonFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/3 11:08
 **/
@Slf4j
@Component
public class RpcBeanPostProcessor implements BeanPostProcessor {

    private final ServiceProvider serviceProvider;
    private final RpcRequestTransport requestTransport;

    public RpcBeanPostProcessor() {
        this.serviceProvider = SingletonFactory.getInstance(NacosServiceProvider.class);
        this.requestTransport = ExtensionLoader.getExtensionLoader(RpcRequestTransport.class).getExtension(RpcRequestTransportTypes.SOCKET.getName());
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 是否有@HTRpcService注解。
        if(bean.getClass().isAnnotationPresent(HTRpcService.class)){
            log.info("[{}] is annotated with  [{}]", bean.getClass().getName(), HTRpcService.class.getCanonicalName());

            // 获取注解信息。
            HTRpcService service = bean.getClass().getAnnotation(HTRpcService.class);
            RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                    .group(service.group())
                    .version(service.version())
                    .service(bean)
                    .build();

            // 发布服务信息。
            serviceProvider.publishService(rpcServiceConfig);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        // 检查类的字段是否有@HTRpcReference注解。
        for (Field field : targetClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(HTRpcReference.class)) {
                // 获取注解信息。
                HTRpcReference reference = field.getAnnotation(HTRpcReference.class);
                RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                        .group(reference.group())
                        .version(reference.version())
                        .build();

                // 代理字段。
                RpcClientProxy rpcClientProxy = new RpcClientProxy(requestTransport, rpcServiceConfig);
                Object proxy = rpcClientProxy.getProxy(field.getType());
                field.setAccessible(true);
                try {
                    field.set(bean, proxy);
                } catch (IllegalAccessException e) {
                    log.error("Failed to set value to : [{}]", field);
                }
            }
        }
        return bean;
    }
}
