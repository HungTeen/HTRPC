package love.pangteen.utils.factory;

import lombok.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理所有的单例对象。
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 10:24
 **/
public final class SingletonFactory {

    private static final Map<String, Object> SINGLETON_MAP = new ConcurrentHashMap<>();

    public static <T> T getInstance(@NonNull Class<T> clazz) {
        String key = clazz.toString();
        Object value = SINGLETON_MAP.computeIfAbsent(key, k -> {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        return clazz.cast(value);
    }
}
