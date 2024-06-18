package love.pangteen;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/18 10:40
 **/
@Documented
@Import(HTRpcBeanInject.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableHTRpcConfig {
}
