package love.pangteen.controller;

import love.pangteen.Result;
import love.pangteen.WorkService;
import love.pangteen.annotations.HTRpcReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/7 9:45
 **/
@RestController
@RequestMapping("/work")
public class WorkController {

    @HTRpcReference
    private WorkService workService;

    @PostMapping("/doWorkDayAndNight")
    public Result doWorkDayAndNight() {
        workService.work("Coding", 100);
        return Result.success();
    }

}
