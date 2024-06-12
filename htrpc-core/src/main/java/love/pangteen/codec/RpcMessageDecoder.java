package love.pangteen.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.codec.compress.Compress;
import love.pangteen.codec.serialize.Serializer;
import love.pangteen.constant.Constants;
import love.pangteen.enums.CompressType;
import love.pangteen.enums.SerializationType;
import love.pangteen.remoting.dto.RpcMessage;
import love.pangteen.remoting.dto.RpcRequest;
import love.pangteen.remoting.dto.RpcResponse;
import love.pangteen.utils.extension.ExtensionLoader;

/**
 * <p>
 * custom protocol decoder
 * <p>
 * <pre>
 *   0     1     2     3     4        5    6    7    8      9           10      11       12    13   14   15 16
 *   +-----+-----+-----+-----+--------+----+----+----+------+-----------+-------+--------+-----+----+----+---+
 *   |   magic   code        |version | full length         |messageType| codec |compress|    RequestId      |
 *   +-----------------------+--------+---------------------+-----------+-------+--------+-------------------+
 *   |                                                                                                       |
 *   |                                         body                                                          |
 *   |                                                                                                       |
 *   |                                        ... ...                                                        |
 *   +-------------------------------------------------------------------------------------------------------+
 * 4B  magic code（魔法数）   1B version（版本）   4B full length（消息长度）    1B messageType（消息类型）
 * 1B compress（压缩类型） 1B codec（序列化类型）    4B  requestId（请求的Id）
 * body（object类型数据）
 * </pre>
 *
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/12 9:57
 **/
@Slf4j
public class RpcMessageDecoder extends LengthFieldBasedFrameDecoder {

    /**
     * Full Length 记录的是整个包的长度，包括头部和body部分。
     */
    public RpcMessageDecoder() {
        super(Constants.MAX_FRAME_LENGTH, Constants.LENGTH_FIELD_OFFSET, Constants.LENGTH_LENGTH, -(Constants.LENGTH_FIELD_OFFSET + Constants.LENGTH_LENGTH), 0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decoded = super.decode(ctx, in);
        if(decoded instanceof ByteBuf frame) {
            if (frame.readableBytes() >= Constants.HEAD_LENGTH) {
                // Check magic number.
                byte[] magic = new byte[Constants.MAGIC_NUMBER.length];
                frame.readBytes(magic);
                for (int i = 0; i < magic.length; i++) {
                    if (magic[i] != Constants.MAGIC_NUMBER[i]) {
                        throw new IllegalArgumentException("Unknown magic number: " + new String(magic));
                    }
                }
                // Check version.
                byte version = frame.readByte();
                if (version != Constants.VERSION) {
                    throw new IllegalArgumentException("Unknown version: " + version);
                }
                // Read Frame.
                int fullLength = frame.readInt();
                byte messageType = frame.readByte();
                byte codec = frame.readByte();
                byte compressType = frame.readByte();
                int requestId = frame.readInt();
                RpcMessage rpcMessage = RpcMessage.builder()
                        .messageType(messageType)
                        .codec(codec)
                        .compressType(compressType)
                        .requestId(requestId)
                        .build();
                if(messageType == Constants.HEARTBEAT_REQUEST_TYPE){
                    rpcMessage.setData(Constants.PING);
                } else if(messageType == Constants.HEARTBEAT_RESPONSE_TYPE){
                    rpcMessage.setData(Constants.PONG);
                } else {
                    int bodyLength = fullLength - Constants.HEAD_LENGTH;
                    byte[] body = new byte[bodyLength];
                    frame.readBytes(body);

                    // Decompress the bytes.
                    String compressName = CompressType.getName(compressType);
                    Compress compress = ExtensionLoader.getExtensionLoader(Compress.class)
                            .getExtension(compressName);
                    body = compress.decompress(body);

                    // Deserialize the object.
                    String codecName = SerializationType.getName(codec);
                    Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(codecName);
                    if(messageType == Constants.REQUEST_TYPE){
                        RpcRequest data = serializer.deserialize(body, RpcRequest.class);
                        rpcMessage.setData(data);
                    } else if(messageType == Constants.RESPONSE_TYPE){
                        RpcResponse data = serializer.deserialize(body, RpcResponse.class);
                        rpcMessage.setData(data);
                    } else {
                        log.warn("Unknown message type: [{}]", messageType);
                        Object data = serializer.deserialize(body, Object.class);
                        rpcMessage.setData(data);
                    }
                }
                return rpcMessage;
            }
        }
        return decoded;
    }
}
