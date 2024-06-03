package love.pangteen.annotations;

import java.lang.annotation.*;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/31 10:01
 **/
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HTRpcService {

    /**
     * Service version, default value is empty string
     */
    String version() default "";

    /**
     * Service group, default value is empty string
     */
    String group() default "";
}
