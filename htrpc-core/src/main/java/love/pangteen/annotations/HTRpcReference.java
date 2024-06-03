package love.pangteen.annotations;

import java.lang.annotation.*;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/31 10:06
 **/
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HTRpcReference {

    /**
     * Service version, default value is empty string
     */
    String version() default "";

    /**
     * Service group, default value is empty string
     */
    String group() default "";
}
