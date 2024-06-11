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
    Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    byte TOTAL_LENGTH = 16;
    byte REQUEST_TYPE = 1;
    byte RESPONSE_TYPE = 2;
    //ping
    byte HEARTBEAT_REQUEST_TYPE = 3;
    //pong
    byte HEARTBEAT_RESPONSE_TYPE = 4;
    int HEAD_LENGTH = 16;
    String PING = "ping";
    String PONG = "pong";
    int MAX_FRAME_LENGTH = 8 * 1024 * 1024;

}
