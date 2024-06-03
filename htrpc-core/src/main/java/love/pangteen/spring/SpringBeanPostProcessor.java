package love.pangteen.spring;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.provider.ServiceProvider;
import love.pangteen.provider.nacos.NacosServiceProvider;
import love.pangteen.utils.factory.SingletonFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/3 11:08
 **/
@Slf4j
@Component
public class SpringBeanPostProcessor implements BeanPostProcessor {

    private final ServiceProvider serviceProvider;
//    private final RpcRequestTransport requestTransport;

    public SpringBeanPostProcessor() {
        this.serviceProvider = SingletonFactory.getInstance(NacosServiceProvider.class);
//        this.requestTransport = requestTransport;
    }

//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        if(bean instanceof HTRpcService rpcService){
//            log.info("[{}] is annotated with  [{}]", bean.getClass().getName(), HTRpcService.class.getCanonicalName());
//
//        }
//    }
}
