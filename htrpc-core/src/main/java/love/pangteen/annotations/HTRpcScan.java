package love.pangteen.annotations;

import love.pangteen.spring.RpcImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/6 16:04
 **/
@Documented
@Import(RpcImportBeanDefinitionRegistrar.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HTRpcScan {

    String[] basePackages() default {};

}
