package love.pangteen.utils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 10:24
 **/
public class Util {

    public static InetSocketAddress localAddress(int port){
        try {
            return new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
