package love.pangteen.utils.extension;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/3 16:03
 **/
public class Holder<T> {

    private volatile T value;

    public T get(){
        return value;
    }

    public void set(T value){
        this.value = value;
    }

}
