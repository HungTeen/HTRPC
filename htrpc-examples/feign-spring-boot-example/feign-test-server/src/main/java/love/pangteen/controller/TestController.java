package love.pangteen.controller;

import love.pangteen.Activity;
import love.pangteen.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/18 16:07
 **/
@RestController
public class TestController {

    @PostMapping
    public Result test(
            @RequestParam("byteValue")byte byteValue,
            @RequestParam("shortValue") short shortValue,
            @RequestParam("intValue") int intValue,
            @RequestParam("longValue") long longValue,
            @RequestParam("floatValue") float floatValue,
            @RequestParam("doubleValue") double doubleValue,
            @RequestParam("booleanValue") boolean booleanValue,
            @RequestParam("charValue") char charValue,
            @RequestBody Activity[] activities
    ){
        return Result.success(activities[0]);
    }

}
