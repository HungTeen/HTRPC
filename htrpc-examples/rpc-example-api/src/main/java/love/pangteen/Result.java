package love.pangteen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/18 11:05
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    private int code;

    private String message;

    private Object data;

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
