package love.pangteen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/6 17:19
 **/
@Getter
@AllArgsConstructor
public enum ServiceProviderTypes {

    LOCAL("local"),

    NACOS("nacos"),

    ;

    private final String name;

}
