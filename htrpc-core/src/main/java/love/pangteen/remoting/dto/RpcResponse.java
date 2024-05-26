package love.pangteen.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import love.pangteen.enums.RpcResult;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:17
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String requestId;
    private Integer code;
    private String message;
    private T data;

    public static <T> RpcResponse<T> success(T data, String requestId) {
        return RpcResponse.<T>builder()
                .requestId(requestId)
                .code(RpcResult.SUCCESS.getCode())
                .message(RpcResult.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> RpcResponse<T> fail(RpcResult rpcResult, String requestId) {
        return RpcResponse.<T>builder()
                .requestId(requestId)
                .code(rpcResult.getCode())
                .message(rpcResult.getMessage())
                .build();
    }

}
