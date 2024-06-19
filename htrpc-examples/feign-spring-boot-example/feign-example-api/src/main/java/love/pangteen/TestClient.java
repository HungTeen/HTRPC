package love.pangteen;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/19 16:19
 **/
@FeignClient("test-service")
public interface TestClient {

    @PostMapping
    Result test(
            @RequestParam("byteValue")byte byteValue,
            @RequestParam("shortValue") short shortValue,
            @RequestParam("intValue") int intValue,
            @RequestParam("longValue") long longValue,
            @RequestParam("floatValue") float floatValue,
            @RequestParam("doubleValue") double doubleValue,
            @RequestParam("booleanValue") boolean booleanValue,
            @RequestParam("charValue") char charValue,
            @RequestBody Activity[] activities
    );
}
