package love.pangteen.remoting.transport.netty.client;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/12 11:05
 **/
public class ChannelManager {

    private static final Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

    public static void setChannel(InetSocketAddress address, Channel channel) {
        CHANNEL_MAP.put(address.toString(), channel);
    }

    public static void removeChannel(InetSocketAddress address) {
        CHANNEL_MAP.remove(address.toString());
    }

    public static Channel getChannel(InetSocketAddress address) {
        return CHANNEL_MAP.compute(address.toString(), (k, v) -> v != null && v.isActive() ? v : null);
    }
}
