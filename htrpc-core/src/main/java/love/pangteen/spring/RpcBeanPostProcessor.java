package love.pangteen.spring;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.annotations.HTRpcReference;
import love.pangteen.annotations.HTRpcService;
import love.pangteen.config.ConfigManager;
import love.pangteen.config.RpcServiceConfig;
import love.pangteen.proxy.RpcClientProxy;
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

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 是否有@HTRpcService注解。
        if(bean.getClass().isAnnotationPresent(HTRpcService.class)){
            log.info("Find HTRPC service [{}]", bean.getClass().getName());

            // 获取注解信息。
            HTRpcService service = bean.getClass().getAnnotation(HTRpcService.class);
            RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                    .group(service.group())
                    .version(service.version())
                    .service(bean)
                    .build();

            // 发布服务信息。
            ConfigManager.getServiceProvider().publishService(rpcServiceConfig);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        // 检查类的字段是否有@HTRpcReference注解。
        for (Field field : targetClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(HTRpcReference.class)) {
                log.info("Find HTRPC reference [{}] is in [{}]", field.getName(), targetClass.getCanonicalName());

                // 获取注解信息。
                HTRpcReference reference = field.getAnnotation(HTRpcReference.class);
                RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                        .group(reference.group())
                        .version(reference.version())
                        .build();

                // 代理字段。
                RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcServiceConfig);
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
