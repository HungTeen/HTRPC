package love.pangteen.service;

import love.pangteen.Activity;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/7 9:11
 **/
public interface WorkService {

    void work(String workName, int workTime);

    void touchFish(Activity activity, int time);

    String getCurrentWork();

    boolean canRelax(int time);

}
