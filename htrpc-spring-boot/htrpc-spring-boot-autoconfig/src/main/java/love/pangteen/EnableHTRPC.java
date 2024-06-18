package love.pangteen;

import love.pangteen.annotations.HTRpcScan;

import java.lang.annotation.*;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/18 11:01
 **/
@Documented
@EnableHTRpcConfig
@HTRpcScan
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableHTRPC {
}
