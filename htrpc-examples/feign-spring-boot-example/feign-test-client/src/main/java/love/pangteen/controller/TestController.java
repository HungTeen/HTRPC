package love.pangteen.controller;

import love.pangteen.Activity;
import love.pangteen.Result;
import love.pangteen.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/18 16:07
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestClient testClient;

    @PostMapping
    public Result test(){
        // 使用当前时间的毫秒数作为种子
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        return testClient.test(
                (byte) random.nextInt(0, 100),
                (short) random.nextInt(0, 1000),
                random.nextInt(0, 10000),
                random.nextLong(0, 10000000),
                random.nextFloat(),
                random.nextDouble(),
                random.nextBoolean(),
                (char)(random.nextInt() % 255),
                new Activity[]{
                        new Activity("Coding", random.nextInt(0, 100)),
                        new Activity("Sleep", random.nextInt(0, 100))
                }
        );
    }

}
