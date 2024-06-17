package love.pangteen.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/30 10:34
 **/
public interface Constants {

    /**
     * Magic number. Verify RpcMessage.
     */
    byte[] MAGIC_NUMBER = {(byte) 'g', (byte) 'r', (byte) 'p', (byte) 'c'};

    byte VERSION = 1;

    /**
     * Length of the length field in the RpcMessage.
     */
    int LENGTH_LENGTH = 4;
    int LENGTH_FIELD_OFFSET = MAGIC_NUMBER.length + 1;

    int HEAD_LENGTH = 16;

    int MAX_FRAME_LENGTH = 8 * 1024 * 1024;

    byte REQUEST_TYPE = 1;
    byte RESPONSE_TYPE = 2;
    //ping
    byte HEARTBEAT_REQUEST_TYPE = 3;
    //pong
    byte HEARTBEAT_RESPONSE_TYPE = 4;

    String PING = "ping";
    String PONG = "pong";

    Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 配置文件地址
     */
    String CONFIG_PATH = "htrpc.properties";

}
