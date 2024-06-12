package love.pangteen.remoting.transport.netty.server;

import cn.hutool.core.util.RuntimeUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.codec.RpcMessageDecoder;
import love.pangteen.codec.RpcMessageEncoder;
import love.pangteen.constant.RpcProperties;
import love.pangteen.utils.Util;
import love.pangteen.utils.factory.ThreadPoolFactory;

import java.util.concurrent.TimeUnit;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/12 16:06
 **/
@Slf4j
public class NettyRpcServer {

    public void start() {
        addShutdownHook();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        DefaultEventExecutorGroup serviceGroup = new DefaultEventExecutorGroup(
                RuntimeUtil.getProcessorCount() * 2,
                ThreadPoolFactory.createThreadFactory("rpc-server", false)
        );
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // TCP默认开启了 Nagle 算法，该算法的作用是尽可能的发送大数据快，减少网络传输。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 是否开启 TCP 底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // If no data is sent to the server within 30 seconds, the server sends a heartbeat request.
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            pipeline.addLast(new RpcMessageEncoder());
                            pipeline.addLast(new RpcMessageDecoder());
                            pipeline.addLast(serviceGroup, new NettyRpcServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(Util.localAddress(RpcProperties.PORT));
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("occur exception when start server:", e);
        } finally {
            log.info("shutdown bossGroup and workerGroup");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }
    }

    public static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ThreadPoolFactory.shutDownAllThreadPool();
        }));
    }
}
