package love.pangteen;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ReferenceCountUtil;
import love.pangteen.dto.RpcRequest;
import love.pangteen.dto.RpcResponse;
import love.pangteen.serialize.KryoSerializer;
import love.pangteen.serialize.NettyKryoDecoder;
import love.pangteen.serialize.NettyKryoEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/24 16:49
 **/
public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private final int port;

    private NettyServer(int port) {
        this.port = port;
    }

    private void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        KryoSerializer kryoSerializer = new KryoSerializer();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // TCP默认开启了 Nagle 算法，该算法的作用是尽可能的发送大数据快，减少网络传输。TCP_NODELAY 参数的作用就是控制是否启用 Nagle 算法。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 是否开启 TCP 底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new NettyKryoDecoder(kryoSerializer, RpcRequest.class));
                            ch.pipeline().addLast(new NettyKryoEncoder(kryoSerializer, RpcResponse.class));
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            // 绑定端口，同步等待绑定成功
            ChannelFuture f = b.bind(port).sync();
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("occur exception when start server:", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static class NettyServerHandler extends ChannelInboundHandlerAdapter {

        private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
        private static final AtomicInteger atomicInteger = new AtomicInteger(1);

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            try {
                RpcRequest rpcRequest = (RpcRequest) msg;
                logger.info("server receive msg: [{}] ,times:[{}]", rpcRequest, atomicInteger.getAndIncrement());
                RpcResponse messageFromServer = RpcResponse.builder().message("message from server").build();
                ChannelFuture f = ctx.writeAndFlush(messageFromServer);
                f.addListener(ChannelFutureListener.CLOSE);
            } finally {
                ReferenceCountUtil.release(msg);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error("server catch exception",cause);
            ctx.close();
        }
    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(9999);
        nettyServer.run();
    }

}
