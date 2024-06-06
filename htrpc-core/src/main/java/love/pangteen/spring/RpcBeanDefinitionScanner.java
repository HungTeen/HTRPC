package love.pangteen.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/6 16:31
 **/
public class RpcBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public RpcBeanDefinitionScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> annotationType) {
        super(registry);
        super.addIncludeFilter(new AnnotationTypeFilter(annotationType));
    }

}
