package love.pangteen.controller;

import love.pangteen.WorkService;
import love.pangteen.annotations.HTRpcReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/7 9:45
 **/
@Controller
@@RequestMapping("/work")
public class WorkController {

    @HTRpcReference
    private WorkService workService;

    @PostMapping("/doWorkDayAndNight")
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
