package love.pangteen.controller;

import love.pangteen.WorkService;
import love.pangteen.annotations.HTRpcReference;
import org.springframework.stereotype.Component;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/7 9:45
 **/
@Component
public class WorkController {

    @HTRpcReference
    private WorkService workService;

    public void doWorkDayAndNight() {
        for(int i = 0; i < 10; ++ i){
            try {
                workService.work("Coding", i);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
