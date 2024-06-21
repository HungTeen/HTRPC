package love.pangteen.dto;

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
public class RpcRequest {
    private String interfaceName;
    private String methodName;
}
