package love.pangteen.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.codec.compress.Compress;
import love.pangteen.codec.serialize.Serializer;
import love.pangteen.constant.Constants;
import love.pangteen.constant.RpcProperties;
import love.pangteen.enums.CompressType;
import love.pangteen.enums.SerializationType;
import love.pangteen.remoting.dto.RpcMessage;
import love.pangteen.utils.Util;
import love.pangteen.utils.extension.ExtensionLoader;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * custom protocol encoder
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
 * @create: 2024/6/11 15:30
 **/
@Slf4j
public class RpcMessageEncoder extends MessageToByteEncoder<RpcMessage> {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage msg, ByteBuf out) throws Exception {
        try {
            out.writeBytes(Constants.MAGIC_NUMBER);
            out.writeByte(Constants.VERSION);
            // leave a place to write the value of full length.
            out.writerIndex(out.writerIndex() + Constants.LENGTH_LENGTH);
            out.writeByte(msg.getMessageType());
            out.writeByte(msg.getCodec());
            out.writeByte(RpcProperties.COMPRESS_TYPE.getCode());
            out.writeInt(ATOMIC_INTEGER.getAndIncrement());
            byte[] bodyBytes = null;
            int fullLength = Constants.HEAD_LENGTH;
            if(! Util.isHeartBeat(msg.getMessageType())){
                // serialize the object.
                String codecName = SerializationType.getName(msg.getCodec());
                log.info("codecName: [{}]", codecName);
                Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(codecName);
                bodyBytes = serializer.serialize(msg.getData());

                // compress the bytes.
                int length = bodyBytes.length;
                String compressName = CompressType.getName(msg.getCompressType());
                Compress compress = ExtensionLoader.getExtensionLoader(Compress.class)
                        .getExtension(compressName);
                bodyBytes = compress.compress(bodyBytes);
                log.info("compressName: [{}], original length: [{}], compressed length: [{}]",
                        compressName, length, bodyBytes.length);
                fullLength += bodyBytes.length;
            }

            if(bodyBytes != null){
                out.writeBytes(bodyBytes);
            }

            // write the value of full length.
            int currentWriterIndex = out.writerIndex();
            out.writerIndex(currentWriterIndex - fullLength + Constants.MAGIC_NUMBER.length + 1);
            out.writeInt(fullLength);
            out.writerIndex(currentWriterIndex);
        } catch (Exception e) {
            log.error("Encode request error!", e);
        }
    }

}
