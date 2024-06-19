package love.pangteen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/11 17:11
 **/
@Getter
@AllArgsConstructor
public enum SerializationType {

    KRYO((byte) 0x01, "kryo"),
    PROTOBUF((byte) 0x02, "protobuf"),
    HESSIAN((byte) 0x03, "hessian"),

    ;

    private final byte code;
    private final String name;

    public static String getName(byte code) {
        for (SerializationType c : SerializationType.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }

}
