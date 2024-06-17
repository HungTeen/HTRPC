package love.pangteen.remoting.transport.netty.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.config.ConfigManager;
import love.pangteen.constant.Constants;
import love.pangteen.enums.RpcResult;
import love.pangteen.remoting.dto.RpcMessage;
import love.pangteen.remoting.dto.RpcRequest;
import love.pangteen.remoting.dto.RpcResponse;
import love.pangteen.remoting.transport.RpcRequestHandler;
import love.pangteen.utils.Util;
import love.pangteen.utils.factory.SingletonFactory;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/12 16:20
 **/
@Slf4j
public class NettyRpcServerHandler extends ChannelInboundHandlerAdapter {

    private final RpcRequestHandler requestHandler;

    public NettyRpcServerHandler() {
        this.requestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof RpcMessage message) {
                if (Util.isRequest(message.getMessageType())) {
                    RpcMessage.RpcMessageBuilder responseBuilder = RpcMessage.builder()
                            .codec(ConfigManager.getSerializationType().getCode())
                            .compressType(ConfigManager.getCompressType().getCode());
                    if (message.getMessageType() == Constants.HEARTBEAT_REQUEST_TYPE) {
                        log.info("heart [{}]", message.getData());
                        responseBuilder.messageType(Constants.HEARTBEAT_RESPONSE_TYPE).data(Constants.PONG);
                    } else {
                        RpcRequest request = (RpcRequest) message.getData();
                        log.info("server receive request: [{}]", request.getRequestId());
                        responseBuilder.messageType(Constants.RESPONSE_TYPE);
                        Object result = requestHandler.handle(request);
                        if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                            responseBuilder.data(RpcResponse.success(result, request.getRequestId()));
                        } else {
                            responseBuilder.data(RpcResponse.fail(RpcResult.FAIL, request.getRequestId()));
                            log.error("not writable now, message dropped");
                        }
                    }
                    RpcMessage response = responseBuilder.build();
                    ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                }
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent idleStateEvent) {
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("idle happen [{}]", ctx.channel().remoteAddress());
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("server catch exception", cause);
        ctx.close();
    }
}
