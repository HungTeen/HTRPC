package love.pangteen.remoting.transport.netty.client;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.constant.Constants;
import love.pangteen.enums.CompressType;
import love.pangteen.enums.SerializationType;
import love.pangteen.remoting.dto.RpcMessage;
import love.pangteen.remoting.dto.RpcResponse;
import love.pangteen.utils.Util;

/**
 * Customize the client ChannelHandler to process the data sent by the server
 *
 * <p>
 * 如果继承自 SimpleChannelInboundHandler 的话就不要考虑 ByteBuf 的释放 ，{@link SimpleChannelInboundHandler} 内部的
 * channelRead 方法会替你释放 ByteBuf ，避免可能导致的内存泄露问题。详见《Netty进阶之路 跟着案例学 Netty》
 *
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/12 15:37
 **/
@Slf4j
public class NettyRpcClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if(msg instanceof RpcMessage message){
                if(Util.isResponse(message.getMessageType())){
                    if(message.getMessageType() == Constants.HEARTBEAT_RESPONSE_TYPE){
                        log.info("heart [{}]", message.getData());
                    } else {
                        //处理响应
                        RpcResponse<Object> response = (RpcResponse<Object>) message.getData();
                        log.info("client receive response: [{}]", response.getRequestId());
                        NettyRpcClient.complete(response);
                    }
                }
            }
        } finally {
            ReferenceCountUtil.release(msg); // TODO 真的释放了吗？好像没继承接口吧？
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent idleStateEvent){
            if(idleStateEvent.state() == IdleState.WRITER_IDLE){
                log.info("write idle happen [{}]", ctx.channel().remoteAddress());
                RpcMessage pingMessage = RpcMessage.builder()
                        .codec(SerializationType.KRYO.getCode())
                        .compressType(CompressType.GZIP.getCode())
                        .messageType(Constants.HEARTBEAT_REQUEST_TYPE)
                        .data(Constants.PING)
                        .build();
//                Channel channel = ChannelManager.getChannel((InetSocketAddress) ctx.channel().remoteAddress());
                Channel channel = ctx.channel();
                channel.writeAndFlush(pingMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("client catch exception", cause);
        cause.printStackTrace();
        ctx.close();
    }

}
