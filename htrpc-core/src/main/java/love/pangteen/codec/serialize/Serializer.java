package love.pangteen.codec.serialize;

import love.pangteen.annotations.SPI;

/**
 * 序列化接口，所有序列化类都要实现这个接口。
 *
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/11 17:18
 **/
@SPI
public interface Serializer {

    /**
     * 序列化。
     *
     * @param obj 要序列化的对象。
     * @return 序列化后的字节数组。
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化。
     *
     * @param bytes 字节数组。
     * @param clazz 反序列化的类。
     * @param <T>   反序列化的类。
     * @return 反序列化的对象。
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
