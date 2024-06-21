package love.pangteen;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/25 15:35
 **/
public class MySmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}