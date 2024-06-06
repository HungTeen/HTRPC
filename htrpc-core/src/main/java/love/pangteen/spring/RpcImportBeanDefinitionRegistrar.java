package love.pangteen.spring;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.annotations.HTRpcScan;
import love.pangteen.annotations.HTRpcService;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/6 16:01
 **/
@Slf4j
public class RpcImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private static final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackages";
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(HTRpcScan.class.getName()));
        if(attributes != null){
            // 获取扫描的包路径。
            String[] basePackages = attributes.getStringArray(BASE_PACKAGE_ATTRIBUTE_NAME);
            if(basePackages.length == 0){
                basePackages = new String[]{((StandardAnnotationMetadata) importingClassMetadata).getIntrospectedClass().getPackage().getName()};
            }

            RpcBeanDefinitionScanner serviceScanner = new RpcBeanDefinitionScanner(registry, HTRpcService.class);
            RpcBeanDefinitionScanner componentScanner = new RpcBeanDefinitionScanner(registry, Component.class);

            if(resourceLoader != null){
                serviceScanner.setResourceLoader(resourceLoader);
                componentScanner.setResourceLoader(resourceLoader);
            }

            int serviceCount = serviceScanner.scan(basePackages);
            int componentCount = componentScanner.scan(basePackages);
            log.info("扫描到[{}]个服务类，[{}]个Bean", serviceCount, componentCount);
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
