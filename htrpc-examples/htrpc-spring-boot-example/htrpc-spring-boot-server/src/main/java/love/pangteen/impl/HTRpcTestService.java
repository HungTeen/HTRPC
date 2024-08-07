package love.pangteen.impl;

import love.pangteen.Activity;
import love.pangteen.Result;
import love.pangteen.annotations.HTRpcService;
import love.pangteen.service.RpcTestService;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/18 16:06
 **/
@HTRpcService
public class HTRpcTestService implements RpcTestService {

    @Override
    public Result test(byte byteValue, short shortValue, int intValue, long longValue, float floatValue, double doubleValue, boolean booleanValue, char charValue, Activity[] activity) {
        return Result.success(activity[0]);
    }

}
