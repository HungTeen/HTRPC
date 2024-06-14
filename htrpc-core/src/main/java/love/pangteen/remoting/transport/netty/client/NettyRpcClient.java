package love.pangteen.remoting.transport.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.codec.RpcMessageDecoder;
import love.pangteen.codec.RpcMessageEncoder;
import love.pangteen.constant.Constants;
import love.pangteen.constant.RpcProperties;
import love.pangteen.provider.ServiceDiscovery;
import love.pangteen.remoting.dto.RpcMessage;
import love.pangteen.remoting.dto.RpcRequest;
import love.pangteen.remoting.dto.RpcResponse;
import love.pangteen.remoting.transport.RpcRequestTransport;
import love.pangteen.utils.extension.ExtensionLoader;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/11 11:13
 **/
@Slf4j
public class NettyRpcClient implements RpcRequestTransport {

    private static final Map<String, CompletableFuture<RpcResponse<Object>>> REQUEST_MAP = new ConcurrentHashMap<>();
    private final ServiceDiscovery serviceDiscovery;
    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;

    public NettyRpcClient() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension(RpcProperties.SERVICE_PROVIDER_TYPE.getName());
        this.eventLoopGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.bootstrap.group(this.eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                //  The timeout period of the connection.
                //  If this time is exceeded or the connection cannot be established, the connection fails.
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // If no data is sent to the server within 5 seconds, a heartbeat request is sent.
                        pipeline.addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));
                        pipeline.addLast(new RpcMessageEncoder());
                        pipeline.addLast(new RpcMessageDecoder());
                        pipeline.addLast(new NettyRpcClientHandler());
                    }
                });
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        // build return value
        CompletableFuture<RpcResponse<Object>> resultFuture = new CompletableFuture<>();
        InetSocketAddress address = this.serviceDiscovery.lookupService(rpcRequest.getRpcServiceName());
        Channel channel = getChannel(address);
        if (channel.isActive()) {
            put(rpcRequest.getRequestId(), resultFuture);
            RpcMessage message = RpcMessage.builder()
                    .data(rpcRequest)
                    .codec(RpcProperties.SERIALIZATION_TYPE.getCode())
                    .compressType(RpcProperties.COMPRESS_TYPE.getCode())
                    .messageType(Constants.REQUEST_TYPE)
                    .build();
            ChannelFuture channelFuture = channel.writeAndFlush(message);
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info("The client has sent the message: [{}]", rpcRequest);
                } else {
                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                }
            });
        } else {
            throw new IllegalStateException("Channel is not active!");
        }
        return resultFuture;
    }

    @SneakyThrows
    public Channel doConnect(InetSocketAddress address) {
        CompletableFuture<Channel> completableFuture = new CompletableFuture<>();
        bootstrap.connect(address).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("The client has connected [{}] successfully!", address.toString());
                completableFuture.complete(future.channel());
            } else {
                log.info("The client failed to connect [{}]!", address.toString());
            }
        });
        return completableFuture.get();
    }

    public Channel getChannel(InetSocketAddress address) {
        Channel channel = ChannelManager.getChannel(address);
        if (channel == null) {
            channel = doConnect(address);
            ChannelManager.setChannel(address, channel);
        }
        return channel;
    }

    public static void put(String requestId, CompletableFuture<RpcResponse<Object>> future) {
        REQUEST_MAP.put(requestId, future);
    }

    public static void complete(RpcResponse<Object> rpcResponse) {
        CompletableFuture<RpcResponse<Object>> future = REQUEST_MAP.remove(rpcResponse.getRequestId());
        if (future != null) {
            future.complete(rpcResponse);
        } else {
            throw new IllegalStateException();
        }
    }

}
