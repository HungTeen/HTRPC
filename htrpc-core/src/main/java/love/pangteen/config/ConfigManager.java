package love.pangteen.config;

import love.pangteen.codec.compress.Compress;
import love.pangteen.codec.serialize.Serializer;
import love.pangteen.enums.CompressType;
import love.pangteen.enums.RequestTransportType;
import love.pangteen.enums.SerializationType;
import love.pangteen.enums.ServiceProviderType;
import love.pangteen.provider.ServiceDiscovery;
import love.pangteen.provider.ServiceProvider;
import love.pangteen.provider.ServiceRegistry;
import love.pangteen.remoting.transport.RpcRequestTransport;
import love.pangteen.utils.Util;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/16 10:17
 **/
public class ConfigManager {

    private static volatile HTRpcConfig htrpcConfig;

    public static HTRpcConfig getConfig() {
        if (htrpcConfig == null) {
            synchronized (ConfigManager.class) {
                if (htrpcConfig == null) {
                    setConfigMethod(HTRpcConfigFactory.createConfig());
                }
            }
        }
        return htrpcConfig;
    }

    public static synchronized void setConfig(HTRpcConfig config) {
        setConfigMethod(config);
    }

    private static void setConfigMethod(HTRpcConfig config) {
        htrpcConfig = config;
    }

    /* Registry Center Config */

    public static String getRegistryAddressWithPort() {
        return getConfig().getRegistryCenterAddress() + ":" + getConfig().getRegistryCenterPort();
    }

    public static Optional<String> getRegistryCenterUsername() {
        return Optional.ofNullable(getConfig().getRegistryCenterUsername());
    }

    public static Optional<String> getRegistryCenterPassword() {
        return Optional.ofNullable(getConfig().getRegistryCenterPassword());
    }

    /* RPC Config */

    public static InetSocketAddress localRpcServiceAddress() {
        return Util.localAddress(getConfig().getServicePort());
    }

    public static CompressType getCompressType() {
        return getConfig().getCompressType();
    }

    public static Compress getCompress() {
        return Util.getCompress(getCompressType());
    }

    public static RequestTransportType getRequestTransportType() {
        return getConfig().getRequestTransportType();
    }

    public static RpcRequestTransport getRpcRequestTransport() {
        return Util.getRpcTransport(getRequestTransportType());
    }

    public static SerializationType getSerializationType() {
        return getConfig().getSerializationType();
    }

    public static Serializer getSerializer() {
        return Util.getSerializer(getSerializationType());
    }

    public static ServiceProviderType getServiceProviderType() {
        return getConfig().getServiceProviderType();
    }

    public static ServiceProvider getServiceProvider() {
        return Util.getServiceProvider(getServiceProviderType());
    }

    public static ServiceDiscovery getServiceDiscovery() {
        return Util.getServiceDiscovery(getServiceProviderType());
    }

    public static ServiceRegistry getServiceRegistry() {
        return Util.getServiceRegistry(getServiceProviderType());
    }

}
