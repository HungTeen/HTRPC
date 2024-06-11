package love.pangteen.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/11 15:52
 **/
@Data
@Builder
@AllArgsConstructor
public class RpcMessage {

    private byte messageType;

    /**
     * Serialization type.
     */
    private byte codec;

    private byte compressType;

    private int requestId;

    private Object data;
}
