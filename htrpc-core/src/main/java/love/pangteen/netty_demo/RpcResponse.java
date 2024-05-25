package love.pangteen.netty_demo;

import lombok.*;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/24 16:22
 **/
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
@ToString
public class RpcResponse {
    private String message;
}
