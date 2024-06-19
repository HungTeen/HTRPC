package love.pangteen.service;

import love.pangteen.Activity;
import love.pangteen.Result;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/18 16:01
 **/
public interface RpcTestService {

    Result test(byte byteValue, short shortValue, int intValue, long longValue, float floatValue, double doubleValue, boolean booleanValue, char charValue, Activity[] activity);

}
