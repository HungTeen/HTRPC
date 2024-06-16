package love.pangteen.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/16 10:18
 **/
@Data
@NoArgsConstructor
public class RegistryConfig {

    /**
     * Register center address
     */
    private String address;

    /**
     * Username to login register center
     */
    private String username;

    /**
     * Password to login register center
     */
    private String password;

    /**
     * Default port for register center
     */
    private Integer port;

    /**
     * Protocol for register center
     */
    private String protocol;

    /**
     * Network transmission type
     */
    private String transporter;


}
