package love.pangteen;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/16 10:06
 **/
public class HTRpcBootstrap {

    private static volatile HTRpcBootstrap instance = null;

    /**
     * 双重校验锁获取单例。
     */
    public static HTRpcBootstrap getInstance() {
        if(instance == null){
            synchronized (HTRpcBootstrap.class){
                if(instance == null){
                    instance = new HTRpcBootstrap();
                }
            }
        }
        return instance;
    }

}
