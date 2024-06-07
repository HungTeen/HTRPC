package love.pangteen.impl;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.Activity;
import love.pangteen.WorkService;
import love.pangteen.annotations.HTRpcService;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/7 9:21
 **/
@Slf4j
@HTRpcService
public class HardWorkService implements WorkService {

    static {
        log.info("HardWorkService is loaded !");
    }

    @Override
    public void work(String workName, int workTime) {
        log.info("I am working on {} for {} hours", workName, workTime);
    }

    @Override
    public void touchFish(Activity activity, int time) {
        log.info("I am touching fish for {} hours", time);
    }

    @Override
    public String getCurrentWork() {
        log.info("Get current work !");
        return "Hard Working !";
    }

    @Override
    public boolean canRelax(int time) {
        log.info("You must hard work without relax !");
        return false;
    }

}
