package love.pangteen;

import love.pangteen.config.HTRpcConfig;
import love.pangteen.utils.PropertySourcesUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@ConditionalOnProperty(prefix = Utils.HTRPC_PREFIX, name = "enabled", matchIfMissing = true)
@Configuration
public class HTRpcAutoConfiguration {

    /**
     * The bean is used to scan the packages of HTRPC Service classes
     */
    @ConditionalOnMissingBean(name = Utils.BASE_PACKAGES_BEAN_NAME)
    @Bean(name = Utils.BASE_PACKAGES_BEAN_NAME)
    public Set<String> dubboBasePackages(ConfigurableEnvironment environment) {
        PropertyResolver propertyResolver = scanBasePackagesPropertyResolver(environment);
        return propertyResolver.getProperty(Utils.BASE_PACKAGES_PROPERTY_NAME, Set.class, Collections.emptySet());
    }

    /**
     * 获取配置Bean。
     *
     * @return 配置对象
     */
    @Bean
    @ConfigurationProperties(prefix = "htrpc")
    public HTRpcConfig getHTRpcConfig() {
        return new HTRpcConfig();
    }

//    /**
//     * 注入 ServiceBean 后置处理器。
//     * @param packagesToScan 获取扫描的包路径。
//     */
//    @ConditionalOnProperty(prefix = Utils.HTRPC_SCAN_PREFIX, name = Utils.BASE_PACKAGES_PROPERTY_NAME)
//    @ConditionalOnBean(name = Utils.BASE_PACKAGES_BEAN_NAME)
//    @Bean
//    public ServiceAnnotationPostProcessor serviceAnnotationBeanProcessor(@Qualifier(Utils.BASE_PACKAGES_BEAN_NAME)
//                                                                         Set<String> packagesToScan) {
//        return new ServiceAnnotationPostProcessor(packagesToScan);
//    }

    public PropertyResolver scanBasePackagesPropertyResolver(ConfigurableEnvironment environment) {
        ConfigurableEnvironment propertyResolver = new AbstractEnvironment() {
            @Override
            protected void customizePropertySources(MutablePropertySources propertySources) {
                Map<String, Object> dubboScanProperties =
                        PropertySourcesUtil.getSubProperties(environment.getPropertySources(), Utils.HTRPC_SCAN_PREFIX);
                propertySources.addLast(new MapPropertySource(Utils.HTRPC_SCAN_PROPERTIES, dubboScanProperties));
            }
        };
        ConfigurationPropertySources.attach(propertyResolver);
        return propertyResolver;
    }

}