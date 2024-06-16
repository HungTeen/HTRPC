package love.pangteen.constant;

import love.pangteen.enums.CompressType;
import love.pangteen.enums.RpcRequestTransportType;
import love.pangteen.enums.SerializationType;
import love.pangteen.enums.ServiceProviderType;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:56
 **/
public interface RpcProperties {

    int PORT = 9999;

    CompressType COMPRESS_TYPE = CompressType.IGNORE;

    RpcRequestTransportType RPC_REQUEST_TRANSPORT_TYPE = RpcRequestTransportType.NETTY;

    SerializationType SERIALIZATION_TYPE = SerializationType.KRYO;

    ServiceProviderType SERVICE_PROVIDER_TYPE = ServiceProviderType.NACOS;

}
