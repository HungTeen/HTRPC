package love.pangteen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/6 15:08
 **/
@Getter
@AllArgsConstructor
public enum RpcRequestTransportTypes {

    SOCKET("socket"),

    NETTY("netty"),

    ;

    private final String name;
}
