package love.pangteen.exception;

import love.pangteen.enums.RpcErrorMessage;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:46
 **/
public class RpcException extends RuntimeException {
    public RpcException(RpcErrorMessage errorMessage, String detail) {
        super(errorMessage.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
