package love.pangteen;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/18 11:05
 **/
@Data
@AllArgsConstructor
public class Result {

    private final int code;

    private final String message;

    private final Object data;

    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public static Result fail(String message) {
        return new Result(500, message, null);
    }
}
