package love.pangteen.config;

import love.pangteen.constant.Constants;
import love.pangteen.utils.Util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/17 9:38
 **/
public class HTRpcConfigFactory {

    /**
     * 根据 configPath 路径获取配置信息。
     *
     * @return 一个HTRpcConfig对象
     */
    public static HTRpcConfig createConfig() {
        return createConfig(Constants.CONFIG_PATH);
    }

    /**
     * 根据指定路径路径获取配置信息。
     *
     * @param path 配置文件路径
     * @return 一个 HTRpcConfig 对象
     */
    public static HTRpcConfig createConfig(String path) {
        Map<String, String> map = readPropToMap(path);
        return (HTRpcConfig) initPropByMap(map, new HTRpcConfig());
    }

    /**
     * 工具方法: 将指定路径的properties配置文件读取到Map中。
     *
     * @param propertiesPath 配置文件地址
     * @return 一个Map
     */
    private static Map<String, String> readPropToMap(String propertiesPath) {
        Map<String, String> map = new HashMap<>(16);
        try {
            InputStream is = HTRpcConfigFactory.class.getClassLoader().getResourceAsStream(propertiesPath);
            if (is == null) {
                return null;
            }
            Properties prop = new Properties();
            prop.load(is);
            for (String key : prop.stringPropertyNames()) {
                map.put(key, prop.getProperty(key));
            }
        } catch (IOException e) {
            throw new RuntimeException("配置文件(" + propertiesPath + ")加载失败", e);
        }
        return map;
    }

    /**
     * 工具方法: 将 Map 的值映射到一个 Model 上。
     *
     * @param map 属性集合
     * @param obj 对象, 或类型
     * @return 返回实例化后的对象
     */
    private static Object initPropByMap(Map<String, String> map, Object obj) {
        if (map == null) {
            map = new HashMap<>(16);
        }

        // 1、取出类型
        Class<?> cs;
        if (obj instanceof Class) {
            // 如果是一个类型，则将obj=null，以便完成静态属性反射赋值
            cs = (Class<?>) obj;
            obj = null;
        } else {
            // 如果是一个对象，则取出其类型
            cs = obj.getClass();
        }

        // 2、遍历类型属性，反射赋值
        for (Field field : cs.getDeclaredFields()) {
            String value = map.get(field.getName());
            if (value == null) {
                // 如果为空代表没有配置此项
                continue;
            }
            try {
                Object valueConvert = Util.getValueByType(value, field.getType());
                field.setAccessible(true);
                field.set(obj, valueConvert);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("属性赋值出错：" + field.getName(), e);
            }
        }
        return obj;
    }
}
