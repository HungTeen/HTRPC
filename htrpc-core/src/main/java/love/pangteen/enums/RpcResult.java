package love.pangteen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:23
 **/
@Getter
@AllArgsConstructor
public enum RpcResult {

    SUCCESS(200, "the remote call is successful"),

    FAIL(500, "the remote call is failed"),

    ;

    private final int code;
    private final String message;
}
