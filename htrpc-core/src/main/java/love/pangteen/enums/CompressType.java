package love.pangteen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/11 17:00
 **/
@Getter
@AllArgsConstructor
public enum CompressType {

    IGNORE((byte) 0x00, "ignore"),

    GZIP((byte) 0x01, "gzip"),

    ;

    private final byte code;
    private final String name;

    public static String getName(byte code) {
        for (CompressType c : CompressType.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }

}
