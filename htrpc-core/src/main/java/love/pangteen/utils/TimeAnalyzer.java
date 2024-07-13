package love.pangteen.utils;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/7/13 17:16
 **/
public class TimeAnalyzer {

    private long startTime;
    private long endTime;

    public TimeAnalyzer() {
        this.startTime = System.currentTimeMillis();
    }

    public long query(){
        this.endTime = System.currentTimeMillis();
        long time = this.endTime - this.startTime;
        this.startTime = this.endTime;
        return time;
    }

}
