package love.pangteen;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/24 15:26
 **/
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
