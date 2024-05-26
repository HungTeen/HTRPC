package love.pangteen.remoting.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:10
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String requestId;
    private String interfaceName;
    private String methodName;

    /**
     * 参数。
     */
    private Object[] parameters;

    /**
     * 参数类型。
     */
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    public String getRpcServiceName() {
        return this.getInterfaceName() + this.getGroup() + this.getVersion();
    }

}
