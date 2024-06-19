package love.pangteen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/7 9:14
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity implements Serializable {

    private String activityName;
    private int activityTime;

}
