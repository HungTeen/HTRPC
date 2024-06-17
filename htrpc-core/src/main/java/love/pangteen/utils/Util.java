package love.pangteen.utils;

import love.pangteen.codec.compress.Compress;
import love.pangteen.codec.serialize.Serializer;
import love.pangteen.constant.Constants;
import love.pangteen.enums.CompressType;
import love.pangteen.enums.RequestTransportType;
import love.pangteen.enums.SerializationType;
import love.pangteen.enums.ServiceProviderType;
import love.pangteen.provider.ServiceDiscovery;
import love.pangteen.provider.ServiceProvider;
import love.pangteen.provider.ServiceRegistry;
import love.pangteen.remoting.transport.RpcRequestTransport;
import love.pangteen.utils.extension.ExtensionLoader;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 10:24
 **/
public class Util {

    public static InetSocketAddress localAddress(int port) {
        try {
            return new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static Compress getCompress(CompressType compressType){
        return ExtensionLoader.getExtensionLoader(Compress.class).getExtension(compressType.getName());
    }

    public static RpcRequestTransport getRpcTransport(RequestTransportType requestTransportType){
        return ExtensionLoader.getExtensionLoader(RpcRequestTransport.class).getExtension(requestTransportType.getName());
    }

    public static Serializer getSerializer(SerializationType serializationType){
        return ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(serializationType.getName());
    }

    public static ServiceProvider getServiceProvider(ServiceProviderType serviceProviderType){
        return ExtensionLoader.getExtensionLoader(ServiceProvider.class).getExtension(serviceProviderType.getName());
    }

    public static ServiceDiscovery getServiceDiscovery(ServiceProviderType serviceProviderType){
        return ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension(serviceProviderType.getName());
    }

    public static ServiceRegistry getServiceRegistry(ServiceProviderType serviceProviderType){
        return ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension(serviceProviderType.getName());
    }

    public static boolean isHeartBeat(byte messageType) {
        return messageType == Constants.HEARTBEAT_REQUEST_TYPE || messageType == Constants.HEARTBEAT_RESPONSE_TYPE;
    }

    public static boolean isResponse(byte messageType) {
        return messageType == Constants.RESPONSE_TYPE || messageType == Constants.HEARTBEAT_RESPONSE_TYPE;
    }

    public static boolean isRequest(byte messageType) {
        return messageType == Constants.REQUEST_TYPE || messageType == Constants.HEARTBEAT_REQUEST_TYPE;
    }

    /**
     * 将指定值转化为指定类型
     *
     * @param <T> 泛型
     * @param obj 值
     * @param cs  类型
     * @return 转换后的值
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValueByType(Object obj, Class<T> cs) {
        // 如果 obj 为 null 或者本来就是 cs 类型
        if (obj == null || obj.getClass().equals(cs)) {
            return (T) obj;
        }
        // 开始转换
        String text = String.valueOf(obj);
        Object ans;
        if (cs.equals(String.class)) {
            ans = text;
        } else if (cs.equals(int.class) || cs.equals(Integer.class)) {
            ans = Integer.valueOf(text);
        } else if (cs.equals(long.class) || cs.equals(Long.class)) {
            ans = Long.valueOf(text);
        } else if (cs.equals(short.class) || cs.equals(Short.class)) {
            ans = Short.valueOf(text);
        } else if (cs.equals(byte.class) || cs.equals(Byte.class)) {
            ans = Byte.valueOf(text);
        } else if (cs.equals(float.class) || cs.equals(Float.class)) {
            ans = Float.valueOf(text);
        } else if (cs.equals(double.class) || cs.equals(Double.class)) {
            ans = Double.valueOf(text);
        } else if (cs.equals(boolean.class) || cs.equals(Boolean.class)) {
            ans = Boolean.valueOf(text);
        } else if (cs.equals(char.class) || cs.equals(Character.class)) {
            ans = text.charAt(0);
        } else if(Enum.class.isAssignableFrom(cs)) {
            if(cs.equals(CompressType.class)) {
                ans = fromString(CompressType.class, text);
            } else if(cs.equals(RequestTransportType.class)){
                ans = fromString(RequestTransportType.class, text);
            } else if(cs.equals(SerializationType.class)){
                ans = fromString(SerializationType.class, text);
            } else if(cs.equals(ServiceProviderType.class)){
                ans = fromString(ServiceProviderType.class, text);
            } else {
                throw new RuntimeException("未被处理的枚举类型");
            }
        } else {
            ans = obj;
        }
        return (T) ans;
    }

    public static <E extends Enum<E>> E fromString(Class<E> enumClass, String value) {
        return Enum.valueOf(enumClass, value.toUpperCase());
    }

}
