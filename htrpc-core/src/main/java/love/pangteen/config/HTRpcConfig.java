package love.pangteen.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import love.pangteen.enums.CompressType;
import love.pangteen.enums.RequestTransportType;
import love.pangteen.enums.SerializationType;
import love.pangteen.enums.ServiceProviderType;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/17 9:39
 **/
@Data
@NoArgsConstructor
public class HTRpcConfig {

    /* Registry Center Config */

    /**
     * Register center address
     */
    private String registryCenterAddress;

    /**
     * Username to login register center
     */
    private String registryCenterUsername;

    /**
     * Password to login register center
     */
    private String registryCenterPassword;

    /**
     * Default port for register center
     */
    private Integer registryCenterPort;

    /**
     * Namespace for register center
     */
    private String registryCenterNamespace;

    /* RPC Config */

    private Integer servicePort = 6666;

    private CompressType compressType = CompressType.IGNORE;

    private RequestTransportType requestTransportType = RequestTransportType.NETTY;

    private SerializationType serializationType = SerializationType.KRYO;

    private ServiceProviderType serviceProviderType;

    private Integer retries = 3;

    private Long timeout = 1000L;

}
